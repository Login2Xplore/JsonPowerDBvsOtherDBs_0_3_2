/*
 *  
 * Copyright Notice
 * 
 * This source code file is created by Hemant Kumar Dugar and is his sole property. 
 * Hemant Kumar Dugar  reserves the rights to all information contained within this 
 * source code file. Any copying of this source code file, in whole or in part including 
 * the concept, idea and logic explained herein by any means for any purpose, 
 * without the expressed written consent of Hemant Kumar Dugar is a violation 
 * of copyright law. 
 * 
 * Copyright © 2018 - Hemant Kumar Dugar - All Rights Reserved
 */
package hkd.db.crud;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author hkd
 */
public class CommonUtils {
    
    // >>
    public static String[] getColumnNameFromCSV(String inputFileName) {
        FileInputStream fstream = null;
        ArrayList<String> columnNameArrList = new ArrayList<>();
        String strLine;
        try {
            fstream = new FileInputStream(inputFileName);
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1)) {
                strLine = br.readLine();
                @SuppressWarnings(value = "StringBufferWithoutInitialCapacity")
                StringBuilder sb = new StringBuilder();
                char ch;
                for (int i = 0; i < strLine.length(); i++) {
                    ch = strLine.charAt(i);
                    if (ch == ',') {
                        columnNameArrList.add(sb.toString());
                        sb.setLength(0);
                    } else {
                        sb.append(ch);
                    }
                }
                columnNameArrList.add(sb.toString());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                // Ignore this exception
            }
        }
        return columnNameArrList.toArray(new String[0]);
    }

}
