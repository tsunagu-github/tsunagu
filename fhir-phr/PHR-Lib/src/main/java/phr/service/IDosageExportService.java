/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.service;

import java.util.List;
import phr.dto.DosageEntitySetDto;

/**
 *
 * @author kis-note-027_user
 */
public interface IDosageExportService {
    List<DosageEntitySetDto> getDosageSetList(String phrId)throws Throwable;
}
