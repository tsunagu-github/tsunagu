/**
 * ******************************************************************************
 * システム名      ：AMED-PHR
 * コンポーネント名 ：バックアップリストア操作クラス
 * 作成者          ：kis-inc
 * 作成日          ：2016/12/14
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
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import phr.datadomain.entity.BackupRestoreActionTypeEntity;
import phr.datadomain.entity.BackupRestoreEventEntity;
import phr.service.IOperationBackupRestoreService;
import phr.service.impl.OperationBackupRestoreService;
import phr.web.phone.dto.ResponseBaseDto;
import phr.web.phone.dto.RequestOperationBackupRestoreDto;
import phr.web.phone.dto.ResponseOperationBackupRestoreDto;

/**
 *
 * @author chiba
 */
public class OperationBackupRestoreFacade extends PhoneFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(GetOneTimePasswordFacade.class);
    /**
     * Json変換オブジェクト
     */
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final String STS_NO = "0";
    public static final String STS_OK = "1";
    public static final String STS_BK_NOW = "2";
    public static final String STS_RE_NOW = "3";
    public static final String STS_BK_ERROR = "91";
    public static final String STS_RE_ERROR = "92";
    
    @Override
    public ResponseBaseDto execute(String json) throws Throwable {
        
        RequestOperationBackupRestoreDto requestDto = gson.fromJson(json, RequestOperationBackupRestoreDto.class);
        ResponseOperationBackupRestoreDto responseDto = new ResponseOperationBackupRestoreDto();
        IOperationBackupRestoreService service = new OperationBackupRestoreService();
        int status;
        String operationMode = requestDto.getOperationMode();
        switch (operationMode) {
            case "B":
                // バックアップ
                    BackupRestoreEventEntity buEntity = service.backUpData(requestDto.getPhrId(),requestDto.getRestorePassword());
                
                    responseDto.setStatus( ResponseBaseDto.SUCCESS );
                    responseDto.setMessage( requestDto.getOperationMode()+"-Operation successful" );
                    responseDto.setProgressStatus(STS_BK_NOW);//buEntity.getBackupRestoreStatusCd()+"" );
                    String sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm").format( buEntity.getCreateDateTime() );
                    responseDto.setBackupDate(sdf);
                    responseDto.setPhrAssociationId(buEntity.getRestorePhrAssociationNo());
                    responseDto.setProjectId(buEntity.getRestoreProjectNo());
                    //responseDto.setBackupRestoreEventId(buEntity.getBackupRestoreEventId());
                    
                break;
            case "R":
                //  リストア 
                //  TODO: 実際のリストア処理はサービスを呼び出す
                status = service.restoreData(requestDto.getPhrId(), requestDto.getOldPhrId(), requestDto.getRestorePassword(), requestDto.getPhrAssociationId(), requestDto.getProjectId());
                if(status==0){
                        responseDto.setStatus( ResponseBaseDto.ERROR );            
                        responseDto.setMessage("リストア対象のバックアップデータが見つかりませんでした。バックアップ時のPHR-IDが正しいことをご確認ください。" );                    
                }else if(status==9){
                        responseDto.setStatus( ResponseBaseDto.ERROR );            
                        responseDto.setMessage("パスワードが不一致でした。確認のうえ、再度お試しください。" );                    
                }else if(status>90){
                    switch(status){
                        case 91:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時の事業者IDが不一致でした。確認のうえ、再度お試しください。" );
                            break;
                        case 92:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時のプロジェクトIDが不一致でした。確認のうえ、再度お試しください。" );
                            break;
                        case 93:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時の事業者IDとプロジェクトIDが不一致でした。確認のうえ、再度お試しください。" );
                            break;
                        case 94:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時のPHR-IDが不一致でした。確認のうえ、再度お試しください。" );
                            break;
                        case 95:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時事業者IDとPHR-IDが不一致でした。確認のうえ、再度お試しください。" );                            
                            break;
                        case 96:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時プロジェクトIDとPHR-IDが不一致でした。確認のうえ、再度お試しください。" );                            
                            break;
                        case 97:
                            responseDto.setStatus( ResponseBaseDto.ERROR );
                            responseDto.setMessage("バックアップ時の事業者ID、プロジェクトID、PHR-IDが不一致でした。確認のうえ、再度お試しください。" );                            
                            break;
                    }
                }else{                
                    responseDto.setStatus( ResponseBaseDto.SUCCESS );
                    responseDto.setMessage( requestDto.getOperationMode()+"-Operation successful" );
                    responseDto.setProgressStatus(STS_RE_NOW);//status + ""  ); 
                    responseDto.setBackupDate( "" );
                }
                break;
            case "U":
                // 状態問い合わせ
                //  TODO: サービス呼び出す
                BackupRestoreEventEntity infoEntity = service.getBackUpStatus(requestDto.getPhrId());
                responseDto.setStatus( ResponseBaseDto.SUCCESS );
                responseDto.setMessage( requestDto.getOperationMode()+"-Operation successful" );
                if(infoEntity.getBackupRestoreActionTypeCd()==BackupRestoreActionTypeEntity.ACTION_BACKUP){
                    if(infoEntity.getBackupRestoreStatusCd()==3){
                        responseDto.setProgressStatus(STS_OK);
                    }else if(infoEntity.getBackupRestoreStatusCd()==9){
                        responseDto.setProgressStatus(STS_BK_ERROR);
                        responseDto.setErrorText(infoEntity.getErrorMessage());
                        //responseDto.setStatus( ResponseBaseDto.ERROR );
                        //responseDto.setMessage("エラーが発生しバックアップが完了できませんでした。再度の実行をお試しください。" );
                    }else{
                        responseDto.setProgressStatus(STS_BK_NOW);
                    }
                }else if(infoEntity.getBackupRestoreActionTypeCd()==BackupRestoreActionTypeEntity.ACTION_RESTORE){
                    if(infoEntity.getBackupRestoreStatusCd()==3){   
                        responseDto.setProgressStatus(STS_OK);                       
                    }else if(infoEntity.getBackupRestoreStatusCd()==9){
                        responseDto.setProgressStatus(STS_RE_ERROR);
                        responseDto.setErrorText(infoEntity.getErrorMessage());                        
                        //responseDto.setStatus( ResponseBaseDto.ERROR );            
                        //responseDto.setMessage("エラーが発生しリストアが完了できませんでした。再度の実行をお試しください。" );
                    }else{
                        responseDto.setProgressStatus(STS_RE_NOW);
                    }                    
                }else{
                    responseDto.setProgressStatus( STS_NO );
                }
                if(responseDto.getProgressStatus()==STS_OK){
                    String projNo = requestDto.getPhrId().substring(0,7);
                    if(projNo.equals(infoEntity.getRestoreProjectNo())){
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm").format( infoEntity.getCreateDateTime() );
                        responseDto.setBackupDate(sdf);
                        responseDto.setPhrAssociationId(infoEntity.getRestorePhrAssociationNo());
                        responseDto.setProjectId(infoEntity.getRestoreProjectNo());
                    }else{
                        //responseDto.setBackupDate("");
                        responseDto.setProgressStatus( STS_NO );
                    }
                }else{
                    responseDto.setBackupDate("");
                }
                break;
            case "D":
                //  消去    
                //  TODO: 実際の削除処理はサービスを呼び出せ（作らなければいけない　パスワードが違ったらエラーで返さないといけない）
                status = service.cancelBackup(requestDto.getPhrId(), requestDto.getRestorePassword());
                if(status==0){
                        responseDto.setStatus( ResponseBaseDto.ERROR );            
                        responseDto.setMessage("削除対象のバックアップデータが見つかりませんでした。" );                    
                }else if(status==9){
                        responseDto.setStatus( ResponseBaseDto.ERROR );            
                        responseDto.setMessage("パスワードが不一致でした。確認のうえ、再度お試しください。" );                    
                }else{
                    responseDto.setStatus( ResponseBaseDto.SUCCESS );
                    responseDto.setMessage( requestDto.getOperationMode()+"-Operation successful" );
                    responseDto.setProgressStatus( STS_NO );
                    responseDto.setBackupDate("");
                }
                break;
            default:
                break;
        }
        return responseDto;
    }
    
    
}
