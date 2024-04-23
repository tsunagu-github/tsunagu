/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.AccessLogsEntity;
import phr.datadomain.entity.PatientEntity;
import phr.service.impl.AccessLogsService.AccessLogResultCd;

/**
 *
 * @author KISNOTE011
 */
public interface IAccessLogsService {

    /**
     * アクセスログの登録
     * @param accessLog
     * @return
     * @throws Throwable 
     */
    AccessLogResultCd insertAccessLog(AccessLogsEntity accessLog) throws Throwable;

    /**
     * Phoneアクセス者名取得
     * @param phrid
     * @return
     * @throws Throwable 
     */
    PatientEntity searchAccessUserName(String phrid) throws Throwable;
}
