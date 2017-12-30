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

        System.out.println("MongoDB Performance for Creating Indexes, Inserting , Updating, Deleting Documents");
        System.out.println("Data file used = " + FILE_NAME);
        System.out.println("");

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

            long totalTime = 0;
            // Creating indexes and calculating time to create indexes
            long ti1 = System.currentTimeMillis();
            for (int colNo = 0; colNo < TOTAL_COLS; colNo++) //Creating indexes 
            {
                collection.createIndex(Indexes.ascending(columnNameArrayList.get(colNo)));
            }
            long ti2 = System.currentTimeMillis();
            long tiDiff = ti2 - ti1;
            totalTime += tiDiff;
            System.out.println("Time taken for Creating Index Columns: " + tiDiff + "ms");

            ArrayList<String> rowsArrList = new ArrayList();
            long tr1 = System.currentTimeMillis();
            while ((strLine = br.readLine()) != null) //Reading from file
            {
                rowsArrList.add(strLine);
            }
            long tr2 = System.currentTimeMillis();
            long trDiff = tr2 - tr1;
            totalTime += trDiff;
            System.out.println("Time taken for Reading from file: " + trDiff + "ms");

            ArrayList<Document> docs = new ArrayList<Document>();
            long td1 = System.currentTimeMillis();
            for (int i = 0; i < rowsArrList.size(); i++) //Creating Documents
            {
                docs.add(getDocument(rowsArrList.get(i), columnNameArrayList));
            }
            long td2 = System.currentTimeMillis();
            long tdDiff = td2 - td1;
            totalTime += tdDiff;
            System.out.println("Time taken for Making Documents: " + tdDiff + "ms");

            long ta1 = System.currentTimeMillis();
            for (int i = 0; i < rowsArrList.size(); i++) //Making Documents
            {
                collection.insertOne(docs.get(i));
            }
            long ta2 = System.currentTimeMillis();
            long taDiff = ta2 - ta1;
            totalTime += taDiff;
            System.out.println("Time taken for Inserting:" + taDiff + "ms");

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

            long tu1 = System.currentTimeMillis();
            for (int i = 0; i < arrList.size(); i++) { //updating documents
//                Document myDoc = collection.find(eq(SEARCH_COL_NAME, arrList.get(i))).first();
//                collection.updateOne(myDoc, new Document("$set", new Document("ACCOUNT NUMBER", i)));

                collection.updateOne(eq(SEARCH_COL_NAME, arrList.get(i)), new Document("$set", new Document("ACCOUNT NUMBER", i)));
                //System.out.println(myDoc);
            }
            long tu2 = System.currentTimeMillis();
            long tuDiff = tu2 - tu1;
            totalTime += tuDiff;
            System.out.println("Time taken for Updating = " + tuDiff + " ms");

            long tDel1 = System.currentTimeMillis();
            for (int i = 0; i < arrList.size(); i++) //deleting documents
            {
                collection.deleteOne(eq(SEARCH_COL_NAME, arrList.get(i)));
            }
            long tDel2 = System.currentTimeMillis();
            long tDelDiff = tDel2 - tDel1;
            totalTime += tDelDiff;
            System.out.println("Time taken for Deleting: " + tDelDiff + "ms");
            System.out.println("Documents after deletion: " + collection.count());
            
            System.out.println("");
            System.out.println("Total time taken for all the operations = " + totalTime);

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
