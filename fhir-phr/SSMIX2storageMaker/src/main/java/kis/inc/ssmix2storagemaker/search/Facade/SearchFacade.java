/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.search.Facade;

import java.util.ArrayList;
import java.util.List;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchDto;
import kis.inc.ssmix2storagemaker.search.dto.SSMIXSearchResultDto;
import kis.inc.ssmix2storagemaker.search.validate.SearchValidate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 検索処理のメインFacade
 * @author kis-note
 */
public class SearchFacade {
    /**
     * ロギングコンポーネント
     */
    private static final Log logger = LogFactory.getLog(SearchFacade.class);
    
    public List<SSMIXSearchResultDto> searchExecute(SSMIXSearchDto searchDto){
        logger.debug("検索処理開始");
        List<SSMIXSearchResultDto> resultList = new ArrayList<>();
        
        //バリデーション
        SearchValidate sv = new SearchValidate();
        //条件取得
        Integer searchType = searchDto.getSearchType();
        
        if(searchType == 1){
            logger.debug("患者指定検索");
            PatientSearchFacade psf = new PatientSearchFacade();
            resultList = psf.search(searchDto);
            
            
        }else{
            logger.debug("集団検索");
            PatientListFacade pf = new PatientListFacade();
            List<String> patientList = pf.getPatientList(searchDto);
            
            int count = 1;
            for(String patientId : patientList){
                logger.info(count + "人目の検索開始");
                SSMIXSearchDto dto = copyDto(searchDto , patientId);
                PatientSearchFacade psf = new PatientSearchFacade();
                List<SSMIXSearchResultDto> result = psf.search(searchDto);
                for(SSMIXSearchResultDto resultDto : result){
                    resultList.add(resultDto);
                }
                logger.info(count + "人目の検索終了");
                count++;
            }
        }
        
        
        logger.debug("検索処理終了");
        return resultList;
    }

    public List<SSMIXSearchResultDto> searchExecute2(SSMIXSearchDto searchDto){
        logger.debug("検索処理開始");
        List<SSMIXSearchResultDto> resultList = new ArrayList<>();
        
        //条件取得
        Integer searchType = searchDto.getSearchType();
        
        if(searchType == 1){
            logger.debug("患者指定検索");
            PatientSearchFacade psf = new PatientSearchFacade();
            resultList = psf.search2(searchDto);
            
            
        }else{
            logger.debug("集団検索");
            PatientListFacade pf = new PatientListFacade();
            List<String> patientList = pf.getPatientList(searchDto);
            
            int count = 1;
            for(String patientId : patientList){
                logger.info(count + "人目の検索開始");
                SSMIXSearchDto dto = copyDto(searchDto , patientId);
                PatientSearchFacade psf = new PatientSearchFacade();
                List<SSMIXSearchResultDto> result = psf.search2(searchDto);
                for(SSMIXSearchResultDto resultDto : result){
                    resultList.add(resultDto);
                }
                logger.info(count + "人目の検索終了");
                count++;
            }
        }
        
        
        logger.debug("検索処理終了");
        return resultList;
    }

    private SSMIXSearchDto copyDto(SSMIXSearchDto searchDto, String patientId) {
        SSMIXSearchDto dto = new SSMIXSearchDto();
        
        dto.setSearchType(1);
        dto.setPatientId(patientId);

        dto.setBaseDate(searchDto.getBaseDate());
        dto.setCount(searchDto.getCount());
        dto.setDataType(searchDto.getDataType());
        dto.setSearchDirection(searchDto.getSearchDirection());
        dto.setStdRootPath(searchDto.getStdRootPath());
        dto.setExRootPath(searchDto.getExRootPath());
 
        return dto;
    }
    
}
