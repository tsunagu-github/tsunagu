package phr.service;

import phr.datadomain.entity.DosageProviderEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageProviderService {
    
    DosageProviderEntity searchDosageProviderByDosageid(String dosageid,int Seq) throws Throwable; 
    
}
