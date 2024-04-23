/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kis.inc.ssmix2storagemaker.Logic.tostorage;

/**
 *
 * @author kis-note-027_user
 */
public class DoEncode {
    public static String doEncode( String str ) {
        String result = "" ;
        if( str != null ) {
            result = str ;
            //result = result.replace( '\u005c', '\uff3c' ) ; //REVERSE SOLIDUS(＼)
            result = result.replace( '\uff5e', '\u301C') ; //WAVE DASH(～)
            result = result.replace( '\u2225', '\u2016' ) ; //DOUBLE VERTICAL LINE(∥)
            result = result.replace( '\uff0d', '\u2212' ) ; //MINUS SIGN(－)
            result = result.replace( '\uffe0', '\u00a2' ) ; //CENT SIGN(￠)
            result = result.replace( '\uffe1', '\u00a3' ) ; //POUND SIGN(￡)
            result = result.replace( '\uffe2', '\u00ac' ) ; //NOT SIGN(￢)
        }
        return result ;
    }    
}
