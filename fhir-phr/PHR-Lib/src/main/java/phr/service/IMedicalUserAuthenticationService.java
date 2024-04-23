/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.HPKIAccountEntity;
import phr.datadomain.entity.MedicalOrganizationEntity;
import phr.datadomain.entity.OneTimeIdInfoEntity;

/**
 *
 * @author daisuke
 */
public interface IMedicalUserAuthenticationService {

    /**
     * 医療機関Userの認証を行う
     *
     * @param loingId
     * @param password
     * @return
     * @throws java.lang.Throwable
     */
    HPKIAccountEntity authenticationInsurerUser(String loingId,String medicalOrganizationCd, String password) throws Throwable;
//    MedicalOrganizationEntity authenticationInsurerUser(String loingId, String password) throws Throwable;

    /**
     * HPKI医師の認証を行う
     *
     * @param commonName
     * @param serialNo
     * @return
     * @throws java.lang.Throwable
     */
    OneTimeIdInfoEntity authenticationHPKIUser(String commonName, String serialNo) throws Throwable;

    /**
     * 医療機関の認証を行う
     *
     * @param loingId
     * @param password
     * @return
     */
    OneTimeIdInfoEntity authenticationNonHPKIUser(String loingId, String password) throws Throwable;

    /**
     * ワンタイムIDの認証を行う
     *
     * @param oneTimeId
     * @return
     * @throws java.lang.Throwable
     */
    MedicalOrganizationEntity authenticationOneTimeId(String oneTimeId) throws Throwable;
}
