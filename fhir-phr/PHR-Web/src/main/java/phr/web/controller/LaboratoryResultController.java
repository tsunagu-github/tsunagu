/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.web.controller;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import phr.service.IMInCSResultImportService;
import phr.utility.FileUtility;
import phr.utility.TypeUtility;
import phr.utility.Zip4jUtility;
import phr.web.ActionConst;
import phr.web.ISessionUtility;
import phr.web.PhrWebConfig;
import phr.web.PhrWebConst;
import phr.web.form.LaboratoryResultForm;
import phr.web.form.SpecificMedicalCheakUpForm;

/**
 * 検査結果アップロードコントローラークラス
 *
 * @author daisuke
 */
@Scope("request")
@Controller
@RequestMapping({ActionConst.LABORATORY_RESULT_UPLOAD})
public class LaboratoryResultController {

    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(LaboratoryResultController.class);

    /**
     * ダウンロードボタン制御フラグ
     */
    private static final boolean downloadOK = true;   // クリックOK（データエラー発生時）
    private static final boolean downloadNG = false;   // クリックNG（初期起動 or 正常登録時）

    /**
     * エラーダウンロードファイルのパス
     */
    private static String downloadFileDir = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_ERR_PATH);
    /**
     * ダウンロード一時ファイル
     */
    private static final String downloadTmpFile = "errFile.txt";

    /**
     * ダウンロードファイル名
     */
    private static final String downloadFileHeader = "LaboratoryResultExamination_";
    /**
     * 区切り文字
     */
    private static final String comma = "、";
    private static final String tab = "\t";

    /**
     * インジェクション：セッション保持サービス
     */
    @Autowired
    @Qualifier("SessionUtility")
    private ISessionUtility sessionUtility;

    /**
     * インジェクション：検査結果取込サービス
     */
    @Autowired
    @Qualifier("MInCSResultImportService")
    private IMInCSResultImportService mInCSResultImportService;

    /**
     * <pre>初期アクションメソッド</pre>
     *
     * @param model
     * @param request
     * @return actionPage
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String defaultAction(Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");

        //画面初期化
        LaboratoryResultForm form = new LaboratoryResultForm();
        form.setDownloadBtnCtl(downloadNG);
        form.setUpdateFlg(false);

        ControllerUtility.InitHeader(model);
        model.addAttribute(form);
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.LABORATORY_RESULT_UPLOAD);
    }

    /**
     * <pre>登録ボタン押下時のアクションメソッド</pre>
     *
     * @param form
     * @param result
     * @param model
     * @param request
     * @return returnString
     * @throws Throwable
     */
    @RequestMapping(method = {RequestMethod.POST}, params = "command=upload")
    public String uploadAction(@ModelAttribute LaboratoryResultForm form, @RequestParam("file") MultipartFile csvfile, BindingResult result, Model model, HttpServletRequest request) throws Throwable {
        logger.debug("Start");
        List<String> messageList = new ArrayList<>();
        String fileName = form.getFileCsv();
        String tmpPath = "";
        boolean updateflg = false;
        boolean csvFlg = false;
        List<File> fileList = new ArrayList<>();

        String laboCode = form.getLaboCode();

        if (!fileName.toUpperCase().endsWith(".ZIP")) {
            csvFlg = true;    //CSV単体の処理のフラグ
        } else {
            //zipを解凍し、各PHRIDとファイルパスを取得する
            tmpPath = PhrWebConfig.getPhrWebProperty(PhrWebConst.CHEAKUP_TEMP);   //一時処理及び退避フォルダ
            Date postDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String dateStr = sdf.format(postDate);
            tmpPath = tmpPath + "/unzip/" + dateStr;
            fileList = unZip(csvfile, tmpPath);  //zipファイル処理
        }

        if (fileList.size() > 0) {
            messageList = mInCSResultImportService.importWebUploadFiles(laboCode, fileList);
        } else {
            messageList.add("指定したファイルに登録対象となる情報はありませんでした。");
        }
        
        if (messageList.isEmpty()) {
            messageList.add("検査結果の登録が完了しました。");
        }
        
        form.setInsertResulrArea(messageList);
        FileUtility.deleteFiles(new File(tmpPath));
        
        logger.debug("End");
        return ActionConst.actionPage(ActionConst.LABORATORY_RESULT_UPLOAD);
    }

    /**
     * zipファイル内のxmlファイルからPHRIDのリストを作成
     *
     * @param xmlfile zipファイル(MultipartFile)
     * @param tmpPath 解凍先パス
     */
    private List<File> unZip(MultipartFile zipfile, String tmpPath) throws Throwable {
        logger.debug("Start");

        List<File> fileList = new ArrayList<>();
        //zipファイルの解凍
        Zip4jUtility obZipUtil = new Zip4jUtility();
        if (TypeUtility.isNullOrEmpty(tmpPath)) {
            //一時フォルダ取得失敗
            return fileList;
        } else {
            //一時フォルダ作成
            String fileName;
            File tmpFile = new File(tmpPath);
            if (!tmpFile.mkdirs()) {
                //失敗
                return fileList;
            } else if (zipfile.getSize() > 0) {
                //アップロード(zip)ファイル保存
                fileName = zipfile.getOriginalFilename();
                File saveFile = new File(tmpPath, fileName);
                try (InputStream inStream = zipfile.getInputStream();) {
                    FileUtils.copyInputStreamToFile(inStream, saveFile);
                }
                //解凍（パスワードなし）
                obZipUtil.unzip(tmpPath + "/" + fileName, tmpPath, "");
                readFiles(tmpFile, fileList);
            } else {
                //失敗
                return fileList;
            }
        }

        logger.debug("End");
        return fileList;
    }

    /**
     * ZIPを除くファイルを取得する
     *
     * @param path
     * @param fileList
     */
    private void readFiles(File path, List<File> fileList) {
        String[] list = path.list();
        if (list == null || list.length == 0) {
            return;
        }

        Arrays.sort(list);
        List<File> dirList = new ArrayList<>();
        for (String file : list) {
            File addFile = new File(path, file);
            if (addFile.isDirectory()) {
                dirList.add(addFile);
            } else {
                String suf = getSuffix(addFile.getName());
                if (suf != null && suf.equalsIgnoreCase("ZIP")) {
                    continue;
                }
                if (suf != null && suf.equalsIgnoreCase("FIN")) {
                    continue;
                }
                fileList.add(addFile);
            }
        }
        for (File file : dirList) {
            readFiles(file, fileList);
        }

    }

    /**
     * ファイル名から拡張子を返します。
     *
     * @param fileName ファイル名
     * @return ファイルの拡張子
     */
    public static String getSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            return fileName.substring(point + 1);
        }
        return fileName;
    }
}
