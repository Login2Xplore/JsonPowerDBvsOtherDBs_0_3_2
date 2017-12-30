/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.db.crud;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

/**
 *
 * @author himanshu dugar
 */
public class MongoDBPerformance {
    
    private static final int TOTAL_COLS = 14;
    private static final String SEARCH_COL_NAME = "LICENSE ID";
    private static final String DB_NAME = "Himdb" ;
    private static final String COLL_NAME = "test";
    private static final String FILE_PATH = "./data/cbl/csv/";
    private static final String FILE_NAME = "ChicagoBL-000f.csv";

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase(DB_NAME);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(COLL_NAME);
        String inputFileName = FILE_PATH + FILE_NAME;
        ArrayList<String> columnNameArrayList = new ArrayList<String>();
        FileInputStream fstream = null;
        String strLine;
        try {
            fstream = new FileInputStream(inputFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1);
            strLine = br.readLine();
            StringBuilder sb = new StringBuilder();
            char ch;

            // Extracting column names from first row of csv file.
            for (int i = 0; i < strLine.length(); i++) {
                ch = strLine.charAt(i);
                if (ch == ',') {
                    columnNameArrayList.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(ch);
                }
            }
            columnNameArrayList.add(sb.toString());

            // Creating indexes and calculating time to create indexes
            long it1 = System.currentTimeMillis();
            for (int colNo = 0; colNo < TOTAL_COLS; colNo++) //Creating indexes 
            {
                collection.createIndex(Indexes.ascending(columnNameArrayList.get(colNo)));
            }
            long it2 = System.currentTimeMillis();
            System.out.println("Time taken for Creating Index Columns: " + (it2 - it1) + "ms");

            ArrayList<String> rowsArrList = new ArrayList();
            long dt1 = System.currentTimeMillis();
            while ((strLine = br.readLine()) != null) //Reading from file
            {
                rowsArrList.add(strLine);
            }
            long dt2 = System.currentTimeMillis();
            System.out.println("Time taken for Reading from file: " + (dt2 - dt1) + "ms");

            ArrayList<Document> docs = new ArrayList<Document>();
            long t1 = System.currentTimeMillis();
            for (int i = 0; i < rowsArrList.size(); i++) //Creating Documents
            {
                docs.add(getDocument(rowsArrList.get(i), columnNameArrayList));
            }
            long t2 = System.currentTimeMillis();
            System.out.println("Time taken for Making Documents: " + (t2 - t1) + "ms");

            long ft1 = System.currentTimeMillis();
            for (int i = 0; i < rowsArrList.size(); i++) //Making Documents
            {
                collection.insertOne(docs.get(i));
            }
            long ft2 = System.currentTimeMillis();
            System.out.println("Time taken for Inserting:" + (ft2 - ft1) + "ms");

//            while ((strLine = br.readLine()) != null) {
//                Document doc = getDocument(strLine, columnNameArrayList);
//                collection.insertOne(doc);
//
//            }
            // Update starts from here
            ArrayList<Long> arrList = new ArrayList<Long>();

            MongoCursor<Document> cursor = (MongoCursor<Document>) collection.find().iterator();
            try {
                while (cursor.hasNext()) { //getting all LICENSE ID in arrayList 
                    Document doc = cursor.next();
                    long licIdData = doc.getLong(SEARCH_COL_NAME);
                    arrList.add(licIdData);
//                    System.out.println(licIdData);
                }
            } finally {
                cursor.close();
            }

            long ut1 = System.currentTimeMillis();
            for (int i = 0; i < arrList.size(); i++) { //updating documents
//                Document myDoc = collection.find(eq(SEARCH_COL_NAME, arrList.get(i))).first();
//                collection.updateOne(myDoc, new Document("$set", new Document("ACCOUNT NUMBER", i)));

                collection.updateOne(eq(SEARCH_COL_NAME, arrList.get(i)), new Document("$set", new Document("ACCOUNT NUMBER", i)));
                //System.out.println(myDoc);
            }
            long ut2 = System.currentTimeMillis();
            System.out.println("Time taken for Updating = " + (ut2 - ut1) + " ms");

            long dti1 = System.currentTimeMillis();
            for (int i = 0; i < arrList.size(); i++) //deleting documents
            {
                collection.deleteOne(eq(SEARCH_COL_NAME, arrList.get(i)));
            }
            long dti2 = System.currentTimeMillis();
            System.out.println("Time taken for Deleting: " + (dti2 - dti1) + "ms");
            System.out.println("Documents after deletion: " + collection.count());
            
//            printData(collection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printData(MongoCollection<Document> collection) {
        MongoCursor<Document> cursor1 = collection.find().iterator();
        try {
            while (cursor1.hasNext()) {
                System.out.println(cursor1.next().toJson());
            }
        } finally {
            cursor1.close();
        }
    }

    //function to create and return document
    public static Document getDocument(String strLine, ArrayList<String> colName) {
        StringBuilder sb = new StringBuilder();
        Document doc = new Document();
        int flag = 0, count = 0;
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
                        doc.append(colName.get(count), lval);
                    } else {
                        doc.append(colName.get(count), dval);
                    }

                } catch (NumberFormatException e) {
                    if ("true".equalsIgnoreCase(s) || "false".equalsIgnoreCase(s)) {
                        bval = Boolean.parseBoolean(s);
                        doc.append(colName.get(count), bval);
                    } else {
                        doc.append(colName.get(count), s);
                    }
                } finally {
                    if (count > (TOTAL_COLS - 2)) {
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

}
