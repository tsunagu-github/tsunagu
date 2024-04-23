/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：患者連携設定一覧取得クラス
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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.ICooperationService;
import phr.service.impl.CooperationService;
import phr.service.impl.CooperationService.CooperationResultCd;
import phr.service.impl.CooperationService.CooperationResult;
import phr.web.phone.dto.CooperationListDto;
import phr.web.phone.dto.RequestGetCooperationListDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseGetCooperationListDto;

/**
 *
 * @author chiba
 */
public class GetCooperationListFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog( GetCooperationListFacade.class );
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        ICooperationService service = new CooperationService();
        
        RequestGetCooperationListDto requestDto = gson.fromJson(json, RequestGetCooperationListDto.class);
        
        CooperationResult result = service.getCooperationList( requestDto.getPhrId() );
        
        ResponseGetCooperationListDto responseDto = new ResponseGetCooperationListDto();
        
        if( result.getResultCd() == CooperationResultCd.SUCCCESS ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "成功しました。" );
            List<String> oneResultData;
            List<CooperationListDto> allResponseData = new ArrayList<>();
            for( int row=0;row < result.getCooperationList().size(); row++ ){
                oneResultData = result.getCooperationList().get(row);
                CooperationListDto oneResponseData = new CooperationListDto();
                oneResponseData.setMedicalAgencyCode( oneResultData.get(0) );
                oneResponseData.setPatientId( oneResultData.get(1) );
                oneResponseData.setIsValid( oneResultData.get(2) );
                oneResponseData.setMessage( oneResultData.get(3) );
                if (String.valueOf(oneResultData.get(4)).equals("true")) {
                    oneResponseData.setAgreesToShare(true);
                } else {
                    oneResponseData.setAgreesToShare(false);
                }
                allResponseData.add( oneResponseData );
            }
            responseDto.setCooperationList( allResponseData );
        }else{
              //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "失敗しました。" );
            responseDto.setCooperationList(null);
        }
        return responseDto;
    }

    
}
