/*
 * プッシュ除外リスト作成＆リマインダプッシュリスト作成
 */

package phr.execute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import phr.config.ConfigConst;
import phr.config.PhrConfig;
import phr.datadomain.entity.ReminderMessageEntity;
import phr.datadomain.entity.ReminderTargetPhrIdItemEntity;
import phr.service.impl.ReminderPushService;

/**
 *
 * @author kis-note-025
 */
public class SelectReminderPushMessageTargetExecute {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SelectReminderPushMessageTargetExecute.class);

    public static void main(String[] args) throws IOException {
        logger.trace("Start");
        int exitCode = 0;
        try{
            Map<String, List<ReminderMessageEntity>> phrIdMessageMap = new TreeMap<>();
            ReminderPushService service = new ReminderPushService();
            for (ReminderMessageEntity message : service.getPushMessageList()) {
                List<ReminderTargetPhrIdItemEntity> phrIdList = service.getPushTargetListPeriod(
                        message.getReminderTypeId(), message.getReminderLevel());
                phrIdList.stream().map((e) -> {
                    return e.getPhrId();
                }).distinct().forEach((e) -> {
                    List<ReminderMessageEntity> list = phrIdMessageMap.get(e);
                    if (list == null) {
                        list = new ArrayList<>();
                        phrIdMessageMap.put(e, list);
                    }
                    list.add(message);
                });
            }
            
            //--　チェック対象者出力用
            logger.info("★プッシュ対象者★ st" );
            String concat = null;
            for(String onePhrId:phrIdMessageMap.keySet()){
                concat = "";
                for(ReminderMessageEntity oneData:phrIdMessageMap.get( onePhrId )){
                    concat += "[" + oneData.getReminderTypeId() +","+oneData.getReminderLevel() +"]";
                }
                logger.info("[" + onePhrId + "]"+concat);
            }
            logger.info( "★プッシュ対象者★ en" );
            //-- ここまで
            
            JSONObject root = new JSONObject();
            JSONArray contents = new JSONArray();
            root.put("contents", contents);
            for (Map.Entry<String, List<ReminderMessageEntity>> e : phrIdMessageMap.entrySet()) {
                JSONObject content = new JSONObject();
                contents.add(content);
                content.put("phrId", e.getKey());
                JSONArray messages = new JSONArray();
                content.put("messages", messages);
                e.getValue().stream().sorted((ReminderMessageEntity o1, ReminderMessageEntity o2) -> {
                    int r;
                    r = o1.getReminderTypeId().compareTo(o2.getReminderTypeId());
                    if (r != 0) {
                        return r;
                    }
                    return o1.getReminderLevel() - o2.getReminderLevel();
                }).forEach((e1) -> {
                    JSONObject message = new JSONObject();
                    messages.add(message);
                    message.put("reminderTypeId", e1.getReminderTypeId());
                    message.put("reminderLevel", e1.getReminderLevel());
                });
            }
            File file = new File(PhrConfig.getConfigProperty(ConfigConst.REMINDER_TARGET_FILE_PATH));
            if (file.exists()) {
                file.delete();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(root.toJSONString());
            }
        }
        catch(Throwable th)
        {
            logger.error(th);
            // 戻り値(エラー)
            exitCode=1;
        }
        logger.trace("end");
        System.exit(exitCode);
    }
}
