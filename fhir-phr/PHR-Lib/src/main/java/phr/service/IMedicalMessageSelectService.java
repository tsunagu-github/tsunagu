/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.CommunicationEntity;

/**
 *
 * @author kis.o-note-003
 */
public interface IMedicalMessageSelectService {
    /**
     * メッセージ詳細の取得を行う
     * @param CommunicationId
     * @return
     * @throws Throwable 
     */
    public CommunicationEntity getMessageDetail(String medicalCd ,String CommunicationId) throws Throwable;

    /**
     * 未読を既読に変更する
     * @param CommunicationId
     * @param MedicalCd
     * @return
     * @throws Throwable 
     */
    public boolean changeReadFlg(String CommunicationId,String MedicalCd) throws Throwable;
}
