/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：引継ぎクラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/09/06
 * 更新履歴        ：
 * -----------------------------------------------------------------------------
 *     [更新日] [担当者]
 *     [更新内容]
 ******************************************************************************
 */
package phr.web.phone.facade;

import java.text.SimpleDateFormat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.ITakeOverService;
import phr.service.impl.TakeOverService;
import phr.service.impl.TakeOverService.TakeOverResultCd;
import phr.web.phone.dto.RequestTakeOverCodeDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseTakeOverCodeDto;

/**
 *
 * @author chiba
 */
public class GetTakeOverCodeFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(GetTakeOverCodeFacade.class);
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
        
        ITakeOverService service = new TakeOverService();
        
        RequestTakeOverCodeDto requestDto = gson.fromJson(json, RequestTakeOverCodeDto.class);
        
        TakeOverService.TakeOverServiceResult result = service.createTakeOverCode( requestDto.getPhrId(), requestDto.getTakeOverPassword() );
        ResponseTakeOverCodeDto responseDto = new ResponseTakeOverCodeDto();
        
        if( result.getResultCd() == TakeOverResultCd.SUCCCESS ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "引継ぎコードを取得しました" );
            responseDto.setTakeOverCode( result.getTakeOverCode() );
            responseDto.setExpirationDate( (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( result.getExpirationDate() )).substring(0,16) ) ;
        }else{
              //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "引継ぎコード生成エラーです。" );
            responseDto.setTakeOverCode( "" );
            responseDto.setExpirationDate( "" );
        }
        return responseDto;
    }
}
