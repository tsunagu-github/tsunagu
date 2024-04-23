/*
 * リマインダプッシュ送信
 */
package phr.execute;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.service.impl.CommunicationService;

/**
 *
 * @author kis-note-025
 */
public class SendReminderPushMessageExecute {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SendReminderPushMessageExecute.class);

    public static void main(String[] args) throws IOException {
        logger.trace("Start");
        int exitCode = 0;
        try{
            CommunicationService communicationService = new CommunicationService();

            JSONParser p = new JSONParser();
            JSONObject root = (JSONObject)p.parse(readAll());
            JSONArray contents = (JSONArray)root.get("contents");
            logger.info( "number of push :" + contents.size() );
            for (Object e : contents) {
                JSONObject content = (JSONObject)e;
                String phrId = (String)content.get("phrId");
                JSONArray messages = (JSONArray)content.get("messages");
                
                List<ReminderMessageEntity> reminderMessageList = new ArrayList<>();
                for( Object e1 : messages ){
                    JSONObject message = (JSONObject)e1;
                    String reminderTypeId = (String)message.get("reminderTypeId");
                    int reminderLevel = ((Long)message.get("reminderLevel")).intValue();

                    //  同じリマインダでレベルの異なるものは最高レベルのみにする
                    boolean pushRegist=true;
                    for(ReminderMessageEntity oneData : reminderMessageList){
                        if(oneData.getReminderTypeId().equals(reminderTypeId)){
                            if(oneData.getReminderLevel() <= reminderLevel){
                                reminderMessageList.remove(oneData);
                            }else{
                                pushRegist = false;
                            }
                            break;
                        }
                    }
                    if( !pushRegist ){
                        continue;
                    }

                    ReminderMessageEntity entity = new ReminderMessageEntity();
                    entity.setReminderTypeId(reminderTypeId);
                    entity.setReminderLevel(reminderLevel);
                    reminderMessageList.add(entity);
                }

                logger.info( "push:"+phrId );
                reminderMessageList.stream().forEach((oneData) -> {
                    logger.info(" ->"+oneData.getReminderTypeId() + "," + oneData.getReminderLevel());
                });
                
                try {
                    communicationService.CreateCommunicationForReminderPush(
                        phrId, reminderMessageList );
                } catch(Throwable th){
                    logger.error(th);
                }
            }
        }
        catch(IOException | ParseException | RuntimeException | Error th){
            logger.error(th);
            // 戻り値(エラー)
            exitCode=1;
        }
        logger.trace("end");
        System.exit(exitCode);
    }
    private static String readAll() throws IOException {
        return Files.lines(Paths.get(PhrConfig.getConfigProperty(
                ConfigConst.REMINDER_TARGET_FILE_PATH)), Charset.forName("UTF-8"))
            .collect(Collectors.joining(System.getProperty("line.separator")));
    }
}
