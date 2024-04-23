/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：自己測定一覧取得クラス
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.service.impl.SelfCheckService;
import phr.service.impl.SelfCheckService.SelfCheckResultCd;
import phr.web.phone.dto.RequestGetSelfCheckListDto;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.ResponseGetSelfCheckListDto;
import phr.web.phone.dto.SelfCheckListDto;

/**
 *
 * @author chiba
 */
public class GetSelfCheckListFacade extends PhoneFacade {
    
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog( GetSelfCheckListFacade.class );
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        SelfCheckService service = new SelfCheckService();
        RequestGetSelfCheckListDto requestDto = gson.fromJson(json, RequestGetSelfCheckListDto.class);
        SelfCheckService.SelfCheckResult result = service.getSelfCheckList( requestDto.getPhrId(), null, null );
        ResponseGetSelfCheckListDto responseDto = new ResponseGetSelfCheckListDto();
        
        int agoDays=60;
        if( requestDto.getAgoDays() != null && !requestDto.getAgoDays().isEmpty() ){
            agoDays = Integer.valueOf( requestDto.getAgoDays() );
        }
        
        if( result.getResultCd() ==SelfCheckResultCd.SUCCCESS ){
            //　成功
            responseDto.setStatus( ResponseBaseDto.SUCCESS );
            responseDto.setMessage( "成功しました。" );
            List<SelfCheckListDto> allResponseData = new ArrayList<>();

            int seqCnt = 0;
            //  データ列は下記の順番
            //  ①日時
            //  ②家庭血圧（拡張）
            //  ③家庭血圧（収縮）
            //  ④家庭体重
            //  ⑤家庭血糖
            //  ⑥検査結果ID
            for( List<String> oneData : result.getSelfCheckList()){
                SelfCheckListDto resultOneData = new SelfCheckListDto();
                resultOneData.setSeqNo( String.valueOf( seqCnt++ ) );
                
                // 指定日付より古いのは捨てる
                if( kikanDays( oneData.get(0) ) > agoDays ){
                    continue;
                }
                
                Date formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse( oneData.get(0));
                resultOneData.setCheckDate( new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( formatDate ) );
                resultOneData.setCheckDateView( new SimpleDateFormat("yy/MM/dd HH:mm").format( formatDate ) );

                resultOneData.setDiastolicBp( oneData.get(1) );
                resultOneData.setSystolicBp( oneData.get(2) );
                resultOneData.setWeight( oneData.get(3) );
                resultOneData.setBloodGlucose( oneData.get(4) );
                resultOneData.setObservationEventId(oneData.get(5));
                allResponseData.add( resultOneData );
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

    /**
     * 今日までの経過日数を求める
     * @param current
     * @return 
     */
    private long kikanDays( String current ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = new Date();

        long dateTimeTo = dateTo.getTime();
        long dateTimeFrom;
        long dayDiff=9999999;
        try {
            dateFrom = sdf.parse( current.substring(0, 10));
            dateTimeFrom = dateFrom.getTime();
            dayDiff = ( dateTimeTo - dateTimeFrom  ) / (1000 * 60 * 60 * 24 );
        } catch (ParseException e) {
        }
        return dayDiff;
    }
    
}
