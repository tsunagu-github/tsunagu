/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.datadomain.entity;

import java.util.Comparator;

/**
 *
 * @author kis-note-027_user
 */
public class SeparatorComparator implements Comparator<SeparatorInfoEntity>{

    @Override
    public int compare(SeparatorInfoEntity sep1, SeparatorInfoEntity sep2) {
            if(sep1.getSeparateNo()>sep2.getSeparateNo()){
                return 1;
            }else if(sep1.getSeparateNo()==sep2.getSeparateNo()){
                return 0;
            }else {
                return -1;
            }
    }
    
}
