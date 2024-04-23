/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：ワンタイムパスワードクラス
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
import java.text.SimpleDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.IOnetimePasswordService;
import phr.service.impl.OnetimePasswordService;
import phr.service.impl.OnetimePasswordService.OneTimePasswordResult;
import phr.service.impl.OnetimePasswordService.OnetimePasswordResultCd;
import phr.web.phone.dto.RequestOneTimePasswordDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseOneTimePasswordDto;

/**
 *
 * @author chiba
 */
public class GetOneTimePasswordFacade extends PhoneFacade {

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
     * @throws java.lang.Throwable
     */
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        IOnetimePasswordService service = new OnetimePasswordService();
        
        RequestOneTimePasswordDto requestDto = gson.fromJson(json, RequestOneTimePasswordDto.class);
        OneTimePasswordResult result = service.createOneTimePassword( requestDto.getPhrId() );
        ResponseOneTimePasswordDto responseDto = new ResponseOneTimePasswordDto();
        
        if( result.getResultCd() == OnetimePasswordResultCd.SUCCCESS ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "ワンタイムパスワードを取得しました" );
            responseDto.setOneTimePassword(result.getPassword() );
            responseDto.setExpirationDate( (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( result.getExpirationDate() )).substring(0,16) ) ;
        }else{
            //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "ワンタイムパスワード生成エラーです。" );
            responseDto.setOneTimePassword( "" );
            responseDto.setExpirationDate( "" );
        }
        return responseDto;
    }
}
