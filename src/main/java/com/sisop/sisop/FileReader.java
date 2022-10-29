package com.sisop.sisop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 */
public class FileReader {
    public static String readFile(String filePath) throws FileNotFoundException, IOException {
        StringBuilder stringBuilder;
        
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            stringBuilder = new StringBuilder();
            String line;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        }

        return stringBuilder.toString();
    }
}
