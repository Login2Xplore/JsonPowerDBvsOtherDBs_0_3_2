/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.db.crud;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dev
 */
public class JPDBBulkOpPerformance {

//    private static final String CON_TOKEN_CREDENTIAL = "email=himanshu.dugar@login2explore.com&password=dfdfdf";
    private static final String CON_TOKEN_CREDENTIAL = "email=nunna2000@gmail.com&password=dfdfdf";

//    private static final String BASE_URL = "http://dev1api.login2explore.com:5577";
    private static final String BASE_URL = "http://localhost:5577";

    private static final int TOTAL_COLS = 14;
    private static final int BLOCK_SIZE = 990;
    private static final String SEARCH_COL_NAME = "LICENSE ID";
    private static final String DB_NAME = "BussLic";
    private static final String REL_NAME = "Chicago";
    private static final String FILE_PATH = "./data/cbl/csv/";
    private static final String FILE_NAME_SMALL = "ChicagoBL-000f.csv";
    private static final String FILE_NAME = "ChicagoBL-010k.csv";

    public static void main(String[] args) throws ParseException, IOException {

        String token = JPDBUtils.getConnectionToken(CON_TOKEN_CREDENTIAL, BASE_URL);

        JPDBUtils.deleteDatabase(token, DB_NAME, BASE_URL);
        jpdbBulkOp(token, FILE_NAME_SMALL);
//
        JPDBUtils.deleteDatabase(token, DB_NAME, BASE_URL);
        jpdbBulkOp(token, FILE_NAME);
    }


    // >>
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private static void jpdbBulkOp(String token, String csvFileName) throws ParseException, IOException {

        System.out.println(">>>>>>>>>>");
        System.out.println("JsonPowerDB Performance for Bulk Insert of Json Documents");
        System.out.println("Data file used = " + csvFileName);
        System.out.println("Block size = " + BLOCK_SIZE);
        System.out.println("");

        String[] columnNameArray = JPDBUtils.getColumnNameFromCSV(FILE_PATH + csvFileName);

        // Bulk insert from file to JsonPowerDB 
        long bit1 = System.currentTimeMillis();
        bulkInsertFromFile(token, FILE_PATH + csvFileName, columnNameArray);
        long bit2 = System.currentTimeMillis();
        System.out.println("Time taken for Inserting from file: " + (bit2 - bit1) + " ms");
    }

    // >>
    private static final void bulkInsertFromFile(String token, String inputFileName, String[] columnNameArray) {
        String strLine;
        FileInputStream fstream = null;
        BufferedReader br = null;
        try {
            fstream = new FileInputStream(inputFileName);

            br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1);

            int co = 0;
            boolean columnFlag = true;
            JSONArray jsonArr = new JSONArray();
            jsonArr.ensureCapacity(BLOCK_SIZE);
            while ((strLine = br.readLine()) != null) //Reading from file
            {
                if (columnFlag) {
                    columnFlag = false;
                    continue;
                }
                jsonArr.add(JPDBUtils.getJsonObjForLine(strLine, columnNameArray, TOTAL_COLS));
                co++;
                if (co >= BLOCK_SIZE) {
                    JPDBUtils.insertJSONArray2JsonPowerDB(token, jsonArr, DB_NAME, REL_NAME, BASE_URL);
                    jsonArr.clear();
                    co = 0;
                }
            }
            JPDBUtils.insertJSONArray2JsonPowerDB(token, jsonArr, DB_NAME, REL_NAME, BASE_URL);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                fstream.close();
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Ignoring Exception
            }
        }
    }
}
