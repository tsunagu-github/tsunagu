/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：患者連携設定操作クラス
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.MedicalOrganizationPatientEntity;
import phr.service.ICooperationService;
import phr.service.impl.CooperationService;
import phr.service.impl.CooperationService.CooperationResultCd;
import phr.service.impl.CooperationService.CooperationResult;
import phr.web.phone.dto.RequestOperationCooperationDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseOperationCooperationDto;
/**
 *
 * @author chiba
 */
public class OperationCooperationFacade extends PhoneFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(GetOneTimePasswordFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * 処理を開始する
     *
     * @param json
     * @return
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        ICooperationService service = new CooperationService();
        RequestOperationCooperationDto requestDto = gson.fromJson(json, RequestOperationCooperationDto.class);
//        requestDto.setPatientId(requestDto.getPatientId().replaceFirst("^0+", "")); //MInCS連携の為、前[0]を除去

        CooperationResult result = null;
        
        String operationMode = requestDto.getOperationMode();
        MedicalOrganizationPatientEntity targetEntity = new MedicalOrganizationPatientEntity();
        boolean isDuplicate = false;
        switch (operationMode) {
            case "C":
                isDuplicate = service.isDuplication(requestDto.getMedicalCd(), requestDto.getPhrId(), requestDto.getPatientId());
                if (!isDuplicate) {
                    targetEntity.setPHR_ID( requestDto.getPhrId() );
                    targetEntity.setMedicalOrganizationCd( requestDto.getMedicalCd() );
                    targetEntity.setPatientId( requestDto.getPatientId() );
                    targetEntity.setAgreesToShare( requestDto.getAgreesToShare() );
                    result = service.createCooperation( targetEntity );
                }
                break;
            case "D":
                targetEntity = service.getCooperation( requestDto.getPhrId(), requestDto.getMedicalCd() );
                if( !targetEntity.getPHR_ID().isEmpty() ){
                    result = service.deleteCooperation( targetEntity );
                }   
                break;
            case "U":
                isDuplicate = service.isDuplication(requestDto.getMedicalCd(), requestDto.getPhrId(), requestDto.getPatientId());
                if (!isDuplicate) {
                    targetEntity = service.getCooperation( requestDto.getPhrId(), requestDto.getMedicalCd() );
                    targetEntity.setPatientId( requestDto.getPatientId() );
                    targetEntity.setAgreesToShare(requestDto.getAgreesToShare());
                    if( !targetEntity.getPHR_ID().isEmpty() ){
                        result = service.updateCooperation( targetEntity );
                    }
                }
                break;
            default:
                break;
        }
        
        ResponseOperationCooperationDto responseDto = new ResponseOperationCooperationDto();
        
        if (isDuplicate) {
            //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "対象の患者IDは既に登録されています。" );
        } else if( result != null && (result.getResultCd() == CooperationResultCd.SUCCCESS )){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( requestDto.getOperationMode()+"処理成功しました" );
        }else{
            //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( requestDto.getOperationMode()+"処理エラーです" );
        }
        return responseDto;
    }
    
}
