/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：自己測定操作クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.InsurerPatientEntity;
import phr.datadomain.entity.ObservationEntity;
import phr.datadomain.entity.ObservationEventEntity;
import phr.service.IObservationEntryService;
import phr.service.impl.ObservationEntryService;
import phr.service.impl.SelfCheckService;
import phr.web.phone.dto.RequestOperationSelfCheckDto;
import phr.web.phone.dto.ResponseOperationSelfCheckDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.SelfCheckListDto;
/**
 *
 * @author chiba
 */
public class OperationSelfCheckListFacade extends PhoneFacade {

    //  収縮期
    static final String SYSTOLIC_BP_DEFINITIONID = "0000000032";
    static final String SYSTOLIC_BP_JLAC10="9A751170100000001";
    //  拡張期	
    static final String DIASTOLIC_BP_DEFINITIONID = "0000000033";
    static final String DIASTOLIC_BP_JLAC10="9A761170100000001";
    //  体重
    static final String WEIGHT_DEFINITIONID = "0000000101";
    static final String WEIGHT_JLAC10 = "9N006170100000001";
    //  血糖
    static final String BLOOD_GLUCOSE_DEFINITIONID = "0000000102";
    static final String BLOOD_GLUCOSE_JLAC10 = "3D010170101826201";

    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(UpdatePatienFacade.class);
    
    /**
     * 処理を開始する
     *
     * @param json
     * @return
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {

        RequestOperationSelfCheckDto requestDto = gson.fromJson(json, RequestOperationSelfCheckDto.class);

        boolean result = false;
        String operationMode = requestDto.getOperationMode();
        ObservationEventEntity targetEntityId = new ObservationEventEntity();
//        AlertSetService aService = new AlertSetService();

        SelfCheckService selfCheckService = new SelfCheckService();
        InsurerPatientEntity insurerPatient = selfCheckService.getInsurerPatient( requestDto.getPhrId() );

        SelfCheckListDto addData = new SelfCheckListDto();

        //  ログ
        logger.info( "★Operation mode["+operationMode+"]" );
        
        switch (operationMode) {
            case "C":
                //  登録
                List<ObservationEntity> allTargetEntity = new ArrayList();
                for( int cnt=0;cnt < 4;cnt++ ){
                    String value = null;
                    String definitionId=null;
                    String jlac10=null;
                    switch( cnt ){
                        case 0:
                            //  拡張期
                            if( !requestDto.getDiastolicBp().isEmpty() ){
                                value = requestDto.getDiastolicBp();
                                definitionId = DIASTOLIC_BP_DEFINITIONID;
                                jlac10 = DIASTOLIC_BP_JLAC10;
                                addData.setDiastolicBp(value);
                            }
                            break;
                        case 1:
                            //  収縮期
                            if( !requestDto.getSystolicBp().isEmpty() ){
                                value = requestDto.getSystolicBp();
                                definitionId = SYSTOLIC_BP_DEFINITIONID;
                                jlac10 = SYSTOLIC_BP_JLAC10;
                                addData.setSystolicBp(value);
                            }
                            break;
                        case 2:
                            //  体重
                            if( !requestDto.getWeight().isEmpty() ){
                                value = requestDto.getWeight();
                                definitionId = WEIGHT_DEFINITIONID;
                                jlac10 = WEIGHT_JLAC10;
                                addData.setWeight(value);
                            }
                            break;
                        case 3:
                            //  血糖
                            if( !requestDto.getBloodGlucose().isEmpty() ){
                                value = requestDto.getBloodGlucose();
                                definitionId = BLOOD_GLUCOSE_DEFINITIONID;
                                jlac10 = BLOOD_GLUCOSE_JLAC10;
                                addData.setBloodGlucose(value);
                            }
                            break;
                        default:
                            break;
                    }
                    if( value == null ){
                        continue;
                    }
                    ObservationEntity oneEntityData = new ObservationEntity();
                    oneEntityData.setObservationDefinitionId( definitionId );
                    oneEntityData.setJLAC10( jlac10 );
                    oneEntityData.setValue( value );
                    allTargetEntity.add( oneEntityData );
                }

                //  自己測定情報
                targetEntityId.setDataInputTypeCd( 2 );
                targetEntityId.setPHR_ID( requestDto.getPhrId() );
                String timeStampValue = requestDto.getCheckdate().substring(0,16);
                timeStampValue = timeStampValue.replaceAll( "-","/");
                timeStampValue = timeStampValue.replaceAll( "T"," ");
                Timestamp addTime = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse( timeStampValue).getTime()+32400000);
                targetEntityId.setExaminationDate( addTime );
                targetEntityId.setYear( Integer.valueOf( requestDto.getCheckdate().substring(0,4) ) );
                targetEntityId.setInsurerNo( insurerPatient.getInsurerNo() );
               
                addData.setCheckDate( new SimpleDateFormat("yyyy/MM/dd HH:mm").format(addTime) );
                addData.setCheckDateView( new SimpleDateFormat("yy/MM/dd HH:mm").format(addTime) );
                
                //  DBに登録
                IObservationEntryService service = new ObservationEntryService();
                List<ObservationEntity> insertResultList = null;
                result = service.insertObservationAndObservationEvent( targetEntityId, allTargetEntity );

                logger.info( "★C処理");
                logger.info( "★C処理-登録="+result );

                //  今登録したのを検索する
                String lastObservationId = selfCheckService.getLastSelfCheck( requestDto.getPhrId() );
                if( lastObservationId != null ){
                    addData.setObservationEventId( lastObservationId );
                    logger.info( "★C対象ObservationId=" + lastObservationId );
                }else{
                    result = false;
                }
                logger.info( "★C最終結果="+result );
                break;
            case "D":
                //  削除
                for( int cnt=0;cnt < 4;cnt++ ){
                    String definitionId=null;
                    switch( cnt ){
                        case 0:
                            //  拡張期
                            definitionId = DIASTOLIC_BP_DEFINITIONID;
                            break;
                        case 1:
                            //  収縮期
                            definitionId = SYSTOLIC_BP_DEFINITIONID;
                            break;
                        case 2:
                            //  体重
                            definitionId = WEIGHT_DEFINITIONID;
                            break;
                        case 3:
                            definitionId = BLOOD_GLUCOSE_DEFINITIONID;
                            break;
                        default:
                            break;
                    }
                    if( definitionId == null ){
                        continue;
                    }
                    ObservationEntity targetEntity = new ObservationEntity();
                    targetEntity.setObservationEventId( requestDto.getObservationEventId() );
                    targetEntity.setObservationDefinitionId( definitionId );
                    SelfCheckService serviceObservation = new SelfCheckService();
                    ObservationEntity deleteTragetEntity = serviceObservation.getObservation( targetEntity );
                    //  DBから削除
                    if( !deleteTragetEntity.getObservationEventId().isEmpty() && !deleteTragetEntity.getObservationDefinitionId().isEmpty() ){
                        serviceObservation.deleteObservation(deleteTragetEntity );
                    }
                    logger.info( "★D処理");
                    logger.info( "★D対象ObservationId=" + requestDto.getObservationEventId() );
                }
                result = true;
                logger.info( "★D結果="+result );
                break;
            case "U":
                //  更新
                List<ObservationEntity> allTargetCreateEntity = new ArrayList();
                List<ObservationEntity> allTargetUpdateEntity = new ArrayList();
                
                //  自己測定
                for( int cnt=0;cnt < 4;cnt++ ){
                    String definitionId=null;
                    String newValue = null;
                    String jlac10=null;
                    switch( cnt ){
                        case 0:
                            //  拡張期
                            definitionId = DIASTOLIC_BP_DEFINITIONID;
                            newValue = requestDto.getDiastolicBp();
                            jlac10 = DIASTOLIC_BP_JLAC10;
                            break;
                        case 1:
                            //  収縮期
                            definitionId = SYSTOLIC_BP_DEFINITIONID;
                            newValue = requestDto.getSystolicBp();
                            jlac10 = SYSTOLIC_BP_JLAC10;
                            break;
                        case 2:
                            //  体重
                            definitionId = WEIGHT_DEFINITIONID;
                            newValue = requestDto.getWeight();
                            jlac10 = WEIGHT_JLAC10;
                            break;
                        case 3:
                            definitionId = BLOOD_GLUCOSE_DEFINITIONID;
                            newValue = requestDto.getBloodGlucose();
                            jlac10 = BLOOD_GLUCOSE_JLAC10;
                            break;
                        default:
                            break;
                    }
                    if( definitionId == null ){
                        continue;
                    }
                    ObservationEntity targetEntity = new ObservationEntity();
                    targetEntity.setObservationEventId( requestDto.getObservationEventId() );
                    targetEntity.setObservationDefinitionId( definitionId );
                    SelfCheckService serviceObservation = new SelfCheckService();
                    ObservationEntity updateTargetEntity = serviceObservation.getObservation( targetEntity );
                    if( !updateTargetEntity.getObservationEventId().isEmpty() && !updateTargetEntity.getObservationDefinitionId().isEmpty() ){
                        updateTargetEntity.setValue( newValue );
                        allTargetUpdateEntity.add( updateTargetEntity );
                    }else{
                        if (!newValue.isEmpty()) {
                            ObservationEntity oneInsertData = new ObservationEntity();
                            oneInsertData.setObservationEventId( requestDto.getObservationEventId() );
                            oneInsertData.setObservationDefinitionId( definitionId );
                            oneInsertData.setJLAC10( jlac10 );
                            oneInsertData.setValue( newValue );
                            allTargetCreateEntity.add( oneInsertData );
                        }
                    }
                }
                
                Timestamp stampdate = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(requestDto.getCheckdate().substring(0,16)).getTime()+32400000);

                //  登録＆更新
                IObservationEntryService serviceCU = new ObservationEntryService();
                List<ObservationEntity> updateResultListGo = null;
                List<ObservationEntity> insertResultListGo = null;
                result = serviceCU.updateObservationAndObservationEvent( allTargetUpdateEntity, allTargetCreateEntity );
                logger.info( "★U処理");
                logger.info( "★U対象ObservationId=" + requestDto.getObservationEventId() );
                logger.info( "★U結果="+result );
                
                break;
            default:
                break;
        }
        
        ResponseOperationSelfCheckDto responseDto = new ResponseOperationSelfCheckDto();
        
        if( result ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( requestDto.getOperationMode()+" Operation successful." );
            responseDto.setAddData( addData );
        }else{
            //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( requestDto.getOperationMode()+" Operation failed." );
        }
        return responseDto;
    }

}
