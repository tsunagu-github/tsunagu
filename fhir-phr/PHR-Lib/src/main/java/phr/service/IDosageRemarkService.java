/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import phr.datadomain.entity.DosageRemarkEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageRemarkService {
    DosageRemarkEntity getDosageRemark(String dosageID,int seq,int remarkSeq)throws Throwable;
}
