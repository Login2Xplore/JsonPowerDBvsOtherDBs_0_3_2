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

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.bson.Document;
import org.json.simple.JSONArray;

/**
 *
 * @author hkd
 */
public class MongoDBBulkOpPerformance {

    private static final int TOTAL_COLS = 14;
    private static final int BLOCK_SIZE = 1000;
    private static final String SEARCH_COL_NAME = "LICENSE ID";
    private static final String DB_NAME = "BussLics";
    private static final String COLL_NAME = "Chicago";
    private static final String FILE_PATH = "./data/cbl/csv/";
    private static final String FILE_NAME_SMALL = "ChicagoBL-000f.csv";
    private static final String FILE_NAME = "ChicagoBL-500k.csv";

    public static void main(String[] args) {
//        MongoDBUtils.delDatabase(DB_NAME);
//        mongoDBBulkOp(FILE_NAME_SMALL);
        MongoDBUtils.delDatabase(DB_NAME);
        mongoDBBulkOp(FILE_NAME);
    }

    private static void mongoDBBulkOp(String csvFileName) {
        System.out.println("<< MongoDB >>");
        System.out.println("Performance for Bulk Insert of Json Documents");
        System.out.println("Data file used = " + csvFileName);
        System.out.println("Block size = " + BLOCK_SIZE);
        System.out.println("Number of Indexed Columns = " + TOTAL_COLS);
        System.out.println("");

        String[] columnNameArray = CommonUtils.getColumnNameFromCSV(FILE_PATH + csvFileName);

        // Bulk insert from file to JsonPowerDB 
        long bit1 = System.currentTimeMillis();
        bulkInsertFromFile(FILE_PATH + csvFileName, columnNameArray);
        long bit2 = System.currentTimeMillis();
        System.out.println("Time taken for Inserting from file: " + (bit2 - bit1) + " ms");
    }

    private static void bulkInsertFromFile(String inputFileName, String[] columnNameArray) {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(COLL_NAME);
        for (int colNo = 0; colNo < TOTAL_COLS; colNo++) //Creating indexes 
        {
            collection.createIndex(Indexes.ascending(columnNameArray[colNo]));
        }

        String strLine;
        FileInputStream fstream = null;
        BufferedReader br = null;
        try {
            fstream = new FileInputStream(inputFileName);

            br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1);

            int co = 0;
            boolean columnFlag = true;
            ArrayList<Document> docsArray = new ArrayList<>();
            docsArray.ensureCapacity(BLOCK_SIZE);
            while ((strLine = br.readLine()) != null) //Reading from file
            {
                if (columnFlag) {
                    columnFlag = false;
                    continue;
                }
                docsArray.add(MongoDBUtils.getDocument(strLine, columnNameArray, TOTAL_COLS));
                co++;
                if (co >= BLOCK_SIZE) {
                    MongoDBUtils.insertDocsArray2MongoDB(docsArray, collection);
                    docsArray.clear();
                    co = 0;
                }
            }
            if (docsArray.size() != 0) {
                MongoDBUtils.insertDocsArray2MongoDB(docsArray, collection);
            }
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
