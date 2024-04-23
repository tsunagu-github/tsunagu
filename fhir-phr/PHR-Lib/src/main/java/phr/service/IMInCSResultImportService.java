/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author kis-note-025
 */
public interface IMInCSResultImportService {
    
    void ImportSpecimenResult();
    /**
     * インジェクション：各種マスタサービス処理
     */
    List<String> importWebUploadFiles(String laboCd, List<File> fileList) throws IOException;
}
