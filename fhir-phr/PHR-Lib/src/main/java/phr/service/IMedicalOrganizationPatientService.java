package phr.service;

import phr.datadomain.entity.MedicalOrganizationPatientEntity;

/**
 * 
 * @author kisvdi017
 *
 */
public interface IMedicalOrganizationPatientService {

    /**
     * 対象患者の閲覧同意の確認
     * @param medicalOrganizationCd
     * @param phrId
     * @return
     * @throws Throwable
     */
    MedicalOrganizationPatientEntity findByPrimaryKey(String medicalOrganizationCd, String phrId) throws Throwable;

}
