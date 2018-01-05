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
import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import org.bson.Document;

/**
 *
 * @author hkd
 */
public class MongoDBUtils {

    public static void delDatabase(String dbName) {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase(dbName);
        mongoClient.close();
    }

    public static void printData(MongoCollection<Document> collection) {
        try (MongoCursor<Document> cursor1 = collection.find().iterator()) {
            while (cursor1.hasNext()) {
                System.out.println(cursor1.next().toJson());
            }
        }
    }

    static void insertDocsArray2MongoDB(MongoCollection<Document> collection) {
    }
    
    //function to create and return document
    public static Document getDocument(String strLine, String[] columnNames, int totalCols) {
        StringBuilder sb = new StringBuilder();
        Document doc = new Document();
        int flag = 0;
        int count = 0;
        char ch;
        for (int i = 0; i < strLine.length(); i++) {
            ch = strLine.charAt(i);
            if (ch == '"' && flag == 0) {
                flag++;
            } else if (ch == '"' && flag == 1) {
                flag = 0;
            } else if (ch == ',' && flag == 0) {
                String s = sb.toString();
                long lval;
                double dval;
                boolean bval;
                try {
                    dval = Double.parseDouble(s);
                    int intval = (int) dval;
                    if (dval - intval == 0.0) {
                        lval = Long.parseLong(s);
                        doc.append(columnNames[count], lval);
                    } else {
                        doc.append(columnNames[count], dval);
                    }
                } catch (NumberFormatException e) {
                    if ("true".equalsIgnoreCase(s) || "false".equalsIgnoreCase(s)) {
                        bval = Boolean.parseBoolean(s);
                        doc.append(columnNames[count], bval);
                    } else {
                        doc.append(columnNames[count], s);
                    }
                } finally {
                    if (count > (totalCols - 2)) {
                        break;
                    }
                }
                count++;
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        return doc;
    }

    static void insertDocsArray2MongoDB(ArrayList<Document> docsArray, MongoCollection<Document> collection) {
        collection.insertMany(docsArray);
    }
    
}
