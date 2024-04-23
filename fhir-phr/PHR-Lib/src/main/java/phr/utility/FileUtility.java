/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phr.utility;

import java.io.File;

/**
 *
 * @author daisuke
 */
public class FileUtility {

    /**
     * 指定したファイル又はディレクトリを削除する
     *
     * @param f
     */
    public static void deleteFiles(File f) {

        if (f.exists() == false) {
            return;
        }

        if (f.isFile()) {
            f.delete();
        }

        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFiles(files[i]);
            }
            try {
                f.delete();
            } catch (Exception ex) {

            }
        }
    }
}
