/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.execute;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.ServiceRequest;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.adapter.DataAccessHistoryAdapter;
import phr.datadomain.adapter.DataAccessTimingAdapter;
import phr.datadomain.adapter.MedicalOrganizationSystemAdapter;
import phr.datadomain.entity.DataAccessHistoryEntity;
import phr.datadomain.entity.DataAccessTimingEntity;
import phr.datadomain.entity.MedicalOrganizationSystemEntity;
import phr.datadomain.entity.ObservationImportHistoryEntity;
import phr.service.impl.FHIRCooperationService;
import phr.service.impl.MInCSResultImportService;
import phr.service.impl.ObservationImportHistoryService;

/**
 *
 * @author kis-note-025
 */
public class DataAccessExecute {

	 /**
	 * ロギングコンポーネント
	 */
//	private static final Log logger = LogFactory.getLog(DataAccessExecute.class);
	private static Logger logger = Logger.getLogger(DataAccessExecute.class);

		public static void main(String[] args) {

		logger.debug("Start");

				// 連携システムデータアクセスタイミング情報テーブルからレコードを取得
				DataAccessTimingAdapter dataAccessTimingAdapter = new DataAccessTimingAdapter();
				List<DataAccessTimingEntity> dataAccessTimingEntity = new ArrayList<>();
				try {
					dataAccessTimingEntity = dataAccessTimingAdapter.getRecords();
				} catch (Throwable e) {
					// TODO 自動生成された catch ブロック
					logger.error(e.toString(), e);
					e.printStackTrace();
				}

				// 現在時刻
				java.sql.Date javaSqlDate = new java.sql.Date(System.currentTimeMillis());
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
				String nowTime = dtf.format(now);
				String[] str = nowTime.split(":");
				int hour = Integer.parseInt(str[0]);
				int minite = Integer.parseInt(str[1]);
				// バッチ処理間隔時間
				String timeInterval = PhrConfig.getConfigProperty(ConfigConst.BATCH_TIME_INTERVAL);
				int interval = Integer.parseInt(timeInterval);
				int hour2 = interval / 60;
				int minite2 = interval % 60;
				// 現在時刻と一定時間間隔の範囲を測定
				int hours = hour + hour2;
				int minites = minite + minite2;
				if (minites >= 61) {
					minites = minites - 60;
					hours = hours + 1;
				}
				String rangeTime = hours + ":" + minites;
				// 取得時刻が範囲内に存在しているものだけを抽出
				List<DataAccessTimingEntity> list = new ArrayList<>();
				for (DataAccessTimingEntity entity: dataAccessTimingEntity) {
					String[] times = entity.getDataAccessTime().split(":");
					int eHour = Integer.parseInt(times[0]);
					int eMinite = Integer.parseInt(times[1]);
					// 完全一致の場合
					if ((hour == eHour && minite == eMinite) || (eHour == hours && eMinite == minites)) {
						list.add(entity);
					// 範囲内に存在する場合
					} else if (hour == eHour) {
						if (minite < eMinite) {
							if (eHour == hours) {
								if (eMinite < minites) {
									list.add(entity);
								}
							} else if (eHour < hours) {
								list.add(entity);
							}
						}
					} else if (hour < eHour) {
						if (eHour == hours) {
							if (eMinite < minites) {
								list.add(entity);
							}
						} else if (eHour < hours) {
							list.add(entity);
						}
					}
				}
				// 同一医療機関が複数含まれている場合は取得時刻が最大のものだけ抽出
				for (int i = 0; i < list.size(); i++) {
					for (int ii = 1; ii < list.size(); ii++) {
						if (i != ii) {
							if (list.get(i).getDataCooperationSystemId() == list.get(ii).getDataCooperationSystemId()) {
								String[] times1 = list.get(i).getDataAccessTime().split(":");
								String[] times2 = list.get(ii).getDataAccessTime().split(":");
								if (Integer.parseInt(times1[0]) > Integer.parseInt(times2[0])) {
									list.remove(ii);
								} else {
									list.remove(i);
								}
							}
						}
					}
				}

				// データ連携システムIDごとに、連携医療機関システム情報テーブルからレコードを取得
				List<MedicalOrganizationSystemEntity> medList = new ArrayList<>();
				MedicalOrganizationSystemAdapter medicalOrganizationSystemAdapter = new MedicalOrganizationSystemAdapter();
				for (DataAccessTimingEntity en : list) {
					MedicalOrganizationSystemEntity e = new MedicalOrganizationSystemEntity();
					String systemId = String.format("%09d", en.getDataCooperationSystemId());
					try {
						e = medicalOrganizationSystemAdapter.findBySystemId(systemId);
					} catch (Throwable e1) {
						// TODO 自動生成された catch ブロック
						logger.error(e1.toString(), e1);
						e1.printStackTrace();
					}
					if (e != null && !e.isInvalid()) {
						medList.add(e);
					}
				}

				// 取得したレコードをパラメータとしてPHR-FHIR検査結果連携機能を呼び出し
				FHIRCooperationExecute fcExecute = new FHIRCooperationExecute();
				for (MedicalOrganizationSystemEntity param : medList) {
					try {
						fcExecute.main(param);
					} catch (Throwable error){
						// 呼び出し失敗の場合はシステム連携実行履歴情報にレコードを追加
						logger.error(error.toString(), error);
						DataAccessHistoryAdapter dataAccessHistoryAdapter = new DataAccessHistoryAdapter();
						String id = String.format("%09d", param.getDataCooperationSystemId());
						// 実行履歴IDを取得
						String historyId = null;
						try {
							historyId = dataAccessHistoryAdapter.getHistoryId();
						} catch (Throwable e1) {
							// TODO 自動生成された catch ブロック
							logger.error(e1.toString(), e1);
							e1.printStackTrace();
						}
						try {
							dataAccessHistoryAdapter.insertError(historyId, id, javaSqlDate);
						} catch (Throwable e) {
							// TODO 自動生成された catch ブロック
							logger.error(e.toString(), e);
							e.printStackTrace();
						}
					}
				}

		logger.debug("End");
		}
}
