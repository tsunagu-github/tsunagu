/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;


/**
 *
 * @author KISNOTE011
 */
public interface IMergeStorageService {
    /**
     * ルートパスから指定したストレージの日付管理しないメッセージのマージを行う
     * 日付管理をするメッセージはマージの判断がつかないので実施しない
     * @param rootPath
     * @return 
     * @throws Throwable 
     */
    boolean mergeExecute(String phrid , String rootPath) throws Throwable;

}
