/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.sisop.sisop;

import com.sisop.sisop.UcuLang.UcuLang;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author ucu
 */
public class SisOp {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        var uculang = new UcuLang(FileReader.readFile("src/fuente.uculang"));
        while (uculang.next()) {}
    }
}
