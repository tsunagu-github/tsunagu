/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：引継ぎコード発行クラス
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
import phr.service.ITakeOverService;
import phr.service.impl.TakeOverService;
import phr.service.impl.TakeOverService.TakeOverResultCd;
import phr.web.phone.dto.RequestEntryTakeOverCodeDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponsePhrIdNumberingDto;

/**
 *
 * @author chiba
 */
public class EntryTakeOverCodeFacade extends PhoneFacade {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(EntryTakeOverCodeFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        ITakeOverService service = new TakeOverService();
        
        RequestEntryTakeOverCodeDto requestDto = gson.fromJson(json, RequestEntryTakeOverCodeDto.class);
        
        TakeOverService.TakeOverServiceResult result = service.checkTakeOverCode( requestDto.getPhrId(), requestDto.getTakeOverPassword(), requestDto.getTakeOverCode() );
        ResponsePhrIdNumberingDto responseDto = new ResponsePhrIdNumberingDto();
        
        if( result.getResultCd() == TakeOverResultCd.SUCCCESS ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "引継ぎ成功しました。" );
            responseDto.setPhrId( result.getPhrId() );
            responseDto.setKeyId( result.getKeyId() );
        }else{
              //  失敗
            responseDto.setStatus( ResponseBaseDto.ERROR );
            responseDto.setMessage( "引継ぎに失敗しました。" );
            responseDto.setPhrId("");
            responseDto.setKeyId("");
        }
        return responseDto;
    }
    
}
