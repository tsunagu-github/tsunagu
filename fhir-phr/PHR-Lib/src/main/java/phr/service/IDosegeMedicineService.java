
package phr.service;

import java.util.List;
import phr.datadomain.entity.DosageMedicineEntity;

/**
 *
 * @author iwaasa
 */
public interface IDosegeMedicineService {
    List<DosageMedicineEntity> searchMedicineByDosageId(String dosageId,int seq,int recipeNo) throws Throwable;
}
