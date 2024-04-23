/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.validate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import kis.inc.ssmix2storagemaker.Enums.DataTypeEnum;
import kis.inc.ssmix2storagemaker.Enums.ErrorIdEnum;
import kis.inc.ssmix2storagemaker.config.ErrorConfig;
import kis.inc.ssmix2storagemaker.search.dto.ErrorDefinitionDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.utility.StringUtility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author kis-note
 */
public class SearchValidate {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SearchValidate.class);
    
    /**
     * 検索条件のバリデーション
     * @param searchDto
     * @return 
     */
    public List<SimpleEntry<String, ErrorDefinitionDto>> validation(SSMIXSearchDto searchDto){
        logger.debug("検索条件のバリデーションの開始");
        Integer searchType = searchDto.getSearchType();
        List<ErrorDefinitionDto> message = new ArrayList <ErrorDefinitionDto>();
        
        //searchTypeの確認
        if(searchType == null || searchType > 2 || searchType == 0){
            logger.debug("対応したsearchTypeではないのでError");
             message.add(ErrorConfig.get(ErrorIdEnum.No_SearchType, MessageFormat.format("{0}", searchType)));
        }
        
        //患者指定検索の場合
        if(searchType == 1){
            //患者IDの確認
            String patientId = searchDto.getPatientId();
            
            if(StringUtility.isNullOrEmpty(patientId)){
                logger.debug("患者指定検索なのに患者IDがありません");
                 message.add(ErrorConfig.get(ErrorIdEnum.No_PatientId, MessageFormat.format("{0}", patientId)));
            }
            
            //検索対象のデータ種別
            String dataType = searchDto.getDataType();
            if(DataTypeEnum.selectName(dataType) == null){
                logger.debug("指定されたデータ種別に対応していまん");
                message.add(ErrorConfig.get(ErrorIdEnum.No_DataType, MessageFormat.format("{0}", dataType)));
            }else{
                if(DataTypeEnum.selectType(dataType).equals("STD")){
                    String stdPath = searchDto.getStdRootPath();
                    
                    if(StringUtility.isNullOrEmpty(stdPath)){
                        logger.debug("標準化ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_STDPath, MessageFormat.format("{0}", stdPath)));
                    }
                }else if(DataTypeEnum.selectType(dataType).equals("EXT")){
                    String extPath = searchDto.getExRootPath();
                    
                    if(StringUtility.isNullOrEmpty(extPath)){
                        logger.debug("拡張ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", extPath)));
                    }
                }else if(DataTypeEnum.selectType(dataType).equals("EXT")){
                    String stdPath = searchDto.getStdRootPath();
                    
                    if(StringUtility.isNullOrEmpty(stdPath)){
                        logger.debug("標準化ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_STDPath, MessageFormat.format("{0}", stdPath)));
                    }
                    
                    String extPath = searchDto.getExRootPath();
                    
                    if(StringUtility.isNullOrEmpty(extPath)){
                        logger.debug("拡張ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", extPath)));
                    }
                }
            }
            
            //基準日の確認
            if(DataTypeEnum.selectFlg(dataType)){
                String cdate = searchDto.getBaseDate();
                if(StringUtility.isNullOrEmpty(cdate)){
                    logger.debug("基準日が指定されていません");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", cdate)));
                }else if(cdate.length() != 8){
                    logger.debug("日付の指定はYYYYMMDD形式にして下さい");
                    message.add(ErrorConfig.get(ErrorIdEnum.BaseDateFormat, MessageFormat.format("{0}", cdate)));
                }
                
                Integer dir = searchDto.getSearchDirection();
                if(dir == null || dir > 2 || dir == 0){
                    logger.debug("検索方向の指定が異常です");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_SearchDirection, MessageFormat.format("{0}", dir)));
                }
                
                Integer count = searchDto.getCount();
                if(count == null || count == 0){
                    logger.debug("遡及回数の指定が異常です");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_Count, MessageFormat.format("{0}", count)));
                }
                
            }
        }else{
            //集団検索の場合
            //患者IDの確認は集団検索なので不要とする
            
            //検索対象のデータ種別
            String dataType = searchDto.getDataType();
            if(DataTypeEnum.selectName(dataType) == null){
                logger.debug("指定されたデータ種別に対応していまん");
                message.add(ErrorConfig.get(ErrorIdEnum.No_DataType, MessageFormat.format("{0}", dataType)));
            }else{
                if(DataTypeEnum.selectType(dataType).equals("STD")){
                    String stdPath = searchDto.getStdRootPath();
                    
                    if(StringUtility.isNullOrEmpty(stdPath)){
                        logger.debug("標準化ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_STDPath, MessageFormat.format("{0}", stdPath)));
                    }
                }else if(DataTypeEnum.selectType(dataType).equals("EXT")){
                    String extPath = searchDto.getExRootPath();
                    
                    if(StringUtility.isNullOrEmpty(extPath)){
                        logger.debug("拡張ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", extPath)));
                    }
                }else if(DataTypeEnum.selectType(dataType).equals("EXT")){
                    String stdPath = searchDto.getStdRootPath();
                    
                    if(StringUtility.isNullOrEmpty(stdPath)){
                        logger.debug("標準化ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_STDPath, MessageFormat.format("{0}", stdPath)));
                    }
                    
                    String extPath = searchDto.getExRootPath();
                    
                    if(StringUtility.isNullOrEmpty(extPath)){
                        logger.debug("拡張ストレージ検索なのにで標準化ストレージのルートパスがありません");
                        message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", extPath)));
                    }
                }
            }
            
            //基準日の確認
            if(DataTypeEnum.selectFlg(dataType)){
                String cdate = searchDto.getBaseDate();
                if(StringUtility.isNullOrEmpty(cdate)){
                    logger.debug("基準日が指定されていません");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_EXTPath, MessageFormat.format("{0}", cdate)));
                }else if(cdate.length() != 8){
                    logger.debug("日付の指定はYYYYMMDD形式にして下さい");
                    message.add(ErrorConfig.get(ErrorIdEnum.BaseDateFormat, MessageFormat.format("{0}", cdate)));
                }
                
                Integer dir = searchDto.getSearchDirection();
                if(dir == null || dir > 2 || dir == 0){
                    logger.debug("検索方向の指定が異常です");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_SearchDirection, MessageFormat.format("{0}", dir)));
                }
                
                Integer count = searchDto.getCount();
                if(count == null || count == 0){
                    logger.debug("検索方向の指定が異常です");
                    message.add(ErrorConfig.get(ErrorIdEnum.No_Count, MessageFormat.format("{0}", count)));
                }
                
            }
        }
        
        
        
        ArrayList<SimpleEntry<String, ErrorDefinitionDto>> messages = new ArrayList<SimpleEntry<String , ErrorDefinitionDto>>();
        for(ErrorDefinitionDto e: message){
            messages.add(new SimpleEntry<String, ErrorDefinitionDto>("SearchValidate", e));
        }

        logger.debug("検索条件のバリデーションの終了");

        return messages;
    }
}
