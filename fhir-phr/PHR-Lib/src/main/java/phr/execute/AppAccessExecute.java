/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.execute;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.AppAccessLogEntity;
import phr.service.impl.UserAuthenticationService;
import phr.service.impl.WithDrawActionService;

/**
 *
 * @author kis-note-025
 */
public class AppAccessExecute {

	 /**
	 * ロギングコンポーネント
	 */
	private static Logger logger = Logger.getLogger(AppAccessExecute.class);

		public static void main(String[] args) {

		logger.debug("Start");

		// Javaアプリケーション実行時に引数としてPHR_ID（14桁）を設定しておく
		String phr_Id = null;
		if (!args[0].isEmpty()) {
			// 対象のPHR_ID
			phr_Id = args[0];
			logger.debug("対象者のPHR_ID：" + phr_Id);

			// ダイナミックコンセント利用状況確認時間間隔
			String timeInterval = PhrConfig.getConfigProperty(ConfigConst.DYNAMICCONSENT_STOP_INTERVAL);
			// Appアクセスログテーブルから該当利用者のアクセス日時が最新のレコードを取得
			UserAuthenticationService service = new UserAuthenticationService();
			AppAccessLogEntity entity = new AppAccessLogEntity();
			try {
				entity = service.findByPhrId(phr_Id);
			} catch (Throwable e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			// 最新アクセス時間
			Date targetDate = null;
			if (entity != null && entity.getPHR_ID() != null) {
				targetDate = new Date(entity.getAccessDateTime().getTime());
				logger.debug("最新アクセス時間:" + targetDate);
			}

			// 現在日時
			Calendar calNow = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.debug("現在日時：" + sdf.format(calNow.getTime()));

			// 最新アクセス時間にダイナミックコンセント利用状況確認時間間隔を足す
			Calendar cal = Calendar.getInstance();
			cal.setTime(targetDate);
			cal.add(Calendar.DATE, Integer.parseInt(timeInterval));
			logger.debug("最新アクセス時間＋利用状況確認時間：" + sdf.format(cal.getTime()));

			// 現在日時と比較
			int diff = calNow.compareTo(cal);
			if (diff == 0 || diff > 0) {
				// 最終アクセスから90日以上経過している場合、利用停止処理を実行
				try {
					WithDrawActionService withDrawActionService = new WithDrawActionService();
					int rowCount = withDrawActionService.updateByPhrId(phr_Id);
					if (rowCount == 1) {
						logger.debug(phr_Id + "のダイナミックコンセントの利用を停止しました。");
					} else if (rowCount == 0) {
						logger.debug(phr_Id + "のダイナミックコンセントの利用停止に失敗しました。");
					}
				} catch (Throwable e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			} else {
				// 最終アクセスから90日未満の場合は現状維持
				logger.debug("ダイナミックコンセント利用停止を行いません。");
			}

		} else {
			logger.debug("引数：対象のPHR-IDを入力してください。");
		}

		logger.debug("End");
	}
}
