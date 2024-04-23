/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.datadomain.entity.DosageRecipeEntity;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageRecipeService {
    List<DosageRecipeEntity> searchRecipeByDosageId(String dosageId, int seq) throws Throwable;
}
