/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import kis.inc.ssmix2storagemaker.dto.OML11.OML11BaseDto;
import kis.inc.ssmix2storagemaker.dto.OML11.OrderDto;
import kis.inc.ssmix2storagemaker.dto.fieldModel.CWEDto;
import kis.inc.ssmix2storagemaker.dto.segmentModel.OBXDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.DataAccessObject;
import phr.datadomain.adapter.InsurerPatientAdapter;
import phr.datadomain.adapter.ObservationDefinitionTypeAdapter;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationDefinitionTypeEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IObservationEntryService;
import phr.service.IRegistModelOML11Service;

/**
 *
 * アラート一覧画面のサービスクラス
 * 
 * @author KISO-NOTE-005
 */
public class RegistModelOML11Service implements IRegistModelOML11Service{
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(RegistModelOML11Service.class);
    
    /**
     * 検索用Dtoクラス
     * ObservationDefinitionIDと登録タイプをいれる
     */
    private class RegistDto{
        private ObservationEventEntity event;
        private List<ObservationEntity> obList;

        /**
         * @return the event
         */
        public ObservationEventEntity getEvent() {
            return event;
        }

        /**
         * @param event the event to set
         */
        public void setEvent(ObservationEventEntity event) {
            this.event = event;
        }

        /**
         * @return the obList
         */
        public List<ObservationEntity> getObList() {
            return obList;
        }

        /**
         * @param obList the obList to set
         */
        public void setObList(List<ObservationEntity> obList) {
            this.obList = obList;
        }
        
    }
    /**
     * 検索用Dtoクラス
     * ObservationDefinitionIDと登録タイプをいれる
     */
    private class CodeDto{
        private String id;
        private int type;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the type
         */
        public int getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(int type) {
            this.type = type;
        }
    }
    
    /**
     * リストア時のOML11の登録処理
     * @param phrid
     * @param dto
     * @return
     * @throws Throwable 
     */
    @Override
    public boolean modelOML11(String phrid, OML11BaseDto dto) throws Throwable {
        logger.debug("OML-11の登録準備開始");
        //処理としてはDBよりJLAC10コードをキーにしたMapを作成する。
        //valueはDefinitionIDと登録タイプをあわせもつクラスにする
        //PHR事業者が作成したOML-11に家庭測定と検体検査結果が混じっている可能性があるため
        String insurerNo = getInsurerNo(phrid);
        if(insurerNo == null){
            logger.debug("登録されていないため終了");
            return false;
        }

        //Map作成
        //処理年度はすべてリストア時の年度とする。
        String year  = yearCH();
        logger.debug("対象年度 : " + year);
        
        List<ObservationDefinitionTypeEntity> typeList = getTypeList(insurerNo , year);
        Map<String , CodeDto> codeMap = makeCodeMap(typeList);
        
        //Observationの作成
        //家庭測定と検体検査結果の２つを作成する
        String cdate = dto.getCdate();
        List<OrderDto> orders = dto.getOrderList();
        Map<Integer , List<ObservationEntity>> observationMap = new HashMap<>();
        String oBRDate = null;
        for(OrderDto order : orders){
            List<OBXDto> obxList = order.getObxList();
            for(OBXDto obx : obxList){
                String obx3_3 = obx.getOBX(3, 3, null);
                String obx3_6 = obx.getOBX(3, 6, null);
                String code = "";
                if(obx3_3.equals("JC10")) code = obx.getOBX(3, 1, null);
                if(obx3_6 != null && obx3_6.equals("JC10")) code = obx.getOBX(3, 4, null);
                if(code.equals("")){
                    logger.debug("対象の検査コードはマスター上にありません");
                    logger.debug("code : " + obx.getOBX(3, 1, null) + " , " + obx.getOBX(3, 4, null));
                    break;
                }
                
                CodeDto codeDto = codeMap.get(code);
                if(codeDto==null){
                    logger.debug("対象の検査コードがObservationDefinitionJlac10上にありません");
                    logger.debug("code : " + code);
                    break;                    
                }
                Integer typeCd = codeDto.getType();
                
                ObservationEntity entity = new ObservationEntity();
                entity.setValue(obx.getOBX(5, null, null));
                entity.setObservationDefinitionId(codeDto.getId());
                entity.setJLAC10(code);
                List<ObservationEntity> entityList = new ArrayList<>();
                if(observationMap.containsKey(typeCd)){
                    entityList = observationMap.get(typeCd);
                }                 
                entityList.add(entity);
                
                observationMap.put(typeCd , entityList);
            }
            if(oBRDate==null){
                if(order.getObrDto()!=null){
                    oBRDate = order.getObrDto().getOBR07();
                }
            }
        }
        
        List<RegistDto> registList = new ArrayList<>();
        long l_date = 0l;
        boolean isOBRDate = false;
        if(oBRDate==null){
            l_date = Long.parseLong(cdate);
        }else{
            isOBRDate = true;
        }
        
        for(Integer target : observationMap.keySet()){
            ObservationEventEntity event = new ObservationEventEntity();
            event.setDataInputTypeCd(target);
            logger.debug(new Timestamp(l_date));
            if(isOBRDate){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                Date ldate  = sdf.parse(oBRDate);
                event.setExaminationDate(new Timestamp(ldate.getTime()));
            }else if(cdate.length() < 15){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date c_date  = sdf.parse(cdate);
                event.setExaminationDate(new Timestamp(c_date.getTime()));
            }else{
                event.setExaminationDate(new Timestamp(l_date));
            }
            //イベントイヤーを取得する
            Timestamp tempExDate = event.getExaminationDate();
            Date exDate = new Date(tempExDate.getTime());
            SimpleDateFormat sfY = new SimpleDateFormat("yyyy");
            SimpleDateFormat sfM = new SimpleDateFormat("MM");
            int exYear = Integer.parseInt(sfY.format(exDate));
            int exMonth = Integer.parseInt(sfM.format(exDate));
            if(exMonth < 4){
                exYear--;
            }
            
            //リストアしたデータ用にLaboratoryCdを設ける
            //event.setLaboratoryCd("XXXXXXX");
            event.setPHR_ID(phrid);
            event.setInsurerNo(insurerNo);
            //event.setYear(Integer.parseInt(year));
            event.setYear(exYear);
            RegistDto rDto = new RegistDto();
            rDto.setEvent(event);
            rDto.setObList(observationMap.get(target));
            
            registList.add(rDto);
        }

        boolean flg = registOML11(registList);
        
        if(!flg) logger.debug("一部のデータの登録に失敗しました");
        
        logger.debug("OML-11の登録準備終了");
        return true;
    }

    private String getInsurerNo(String phrid) throws SQLException, ClassNotFoundException {
        DataAccessObject dao = new DataAccessObject();
        InsurerPatientEntity entity = null;
    	try{
            InsurerPatientAdapter adapter = new InsurerPatientAdapter(dao.getConnection());
            dao.beginTransaction();
            
            entity = adapter.findByPhrId(phrid);

     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} catch (Throwable ex) {
            Logger.getLogger(RegistModelOML11Service.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.trace("end");
        return entity.getInsurerNo();
    }

    private String yearCH() {
        SimpleDateFormat sdf_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_m = new SimpleDateFormat("MM");
        Date date = new Date();
        
        String year = sdf_y.format(date);
        String month = sdf_m.format(date);
        month = month.replace("0","");
        
        Integer month_i = Integer.parseInt(month);
        Integer year_i = Integer.parseInt(year);
        if(month_i <= 3){
            year_i = year_i - 1;
        }
        
        return year_i.toString();
    }
    
    private List<ObservationDefinitionTypeEntity> getTypeList(String insurerNo, String year) throws SQLException, ClassNotFoundException {
        DataAccessObject dao = new DataAccessObject();
        List<ObservationDefinitionTypeEntity> entityList =new ArrayList<>();
//        List<ObservationDefinitionTypeEntity> entityList2 = new ArrayList<>();
        List<ObservationDefinitionTypeEntity> entityList3 = new ArrayList<>();
    	try{
            ObservationDefinitionTypeAdapter adapter = new ObservationDefinitionTypeAdapter(dao.getConnection());
            dao.beginTransaction();
            
            entityList = adapter.findByInsurerList(insurerNo, 1);
//            entityList2 = adapter.findByInsurerList(insurerNo, 3);
            entityList3 = adapter.findByInsurerList(insurerNo, 2);

//            if(entityList2.size() > 0){
//                for(ObservationDefinitionTypeEntity entity : entityList2){
//                    entityList.add(entity);
//                }
//            }
            if(entityList3.size() > 0){
                for(ObservationDefinitionTypeEntity entity : entityList3){
                    entityList.add(entity);
                }
            }            
     	}catch(Exception e){
            dao.rollbackTransaction();
            return null;
    	} catch (Throwable ex) {
            Logger.getLogger(RegistModelOML11Service.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if (dao != null) {dao.close();}
            } catch (Throwable ex) {
                logger.error("", ex);
            }
        }

        logger.trace("end");
        return entityList;       
    }
    
    
    private Map<String, CodeDto> makeCodeMap(List<ObservationDefinitionTypeEntity> typeList) {
        logger.debug("Map作成開始");
        Map<String , CodeDto> codeMap = new HashMap<String , CodeDto>();
        
        for(ObservationDefinitionTypeEntity entity : typeList){
            String jlac10 = entity.getJlac10();
            CodeDto dto = new CodeDto();
            dto.setId(entity.getObservationDefinitionId());
            dto.setType(entity.getDataInputTypeCd());
            codeMap.put(jlac10, dto);
        }
        
        logger.debug("Map作成終了");
        return codeMap;
    }
    
    private boolean registOML11(List<RegistDto> registList) {
        logger.debug("リストアデータの検体検査結果のDB登録開始");
        
        for(RegistDto dto : registList){
            IObservationEntryService service = new ObservationEntryService();
            boolean flg = true;
            try {
                flg = service.insertObservationAndObservationEvent(dto.getEvent(), dto.getObList());
            } catch (Throwable ex) {
                Logger.getLogger(RegistModelOML11Service.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(!flg){
                logger.info("対象の検査登録に失敗しました");
                logger.info("診療日 : " + dto.getEvent().getExaminationDate());
            }
        }
        logger.debug("リストアデータの検体検査結果のDB登録終了");
        return true;
    }    
}
