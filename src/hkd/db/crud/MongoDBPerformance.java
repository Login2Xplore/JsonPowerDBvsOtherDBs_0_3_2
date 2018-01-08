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
    private static final String DB_NAME = "BUSSLICS";
    private static final String COLL_NAME = "CHICAGO";
    private static final String FILE_PATH = "./data/cbl/csv/";
    private static final String FILE_NAME_SMALL = "ChicagoBL-000f.csv";
    private static final String FILE_NAME = "ChicagoBL-001h.csv";

    public static void main(String[] args) {
        MongoDBUtils.delDatabase(DB_NAME);
        mongoDBOp(FILE_NAME_SMALL);
//        MongoDBUtils.delDatabase(DB_NAME);
//        mongoDBOp(FILE_NAME);
    }

    public static void mongoDBOp(String csvFileName) {
        MongoClient mongoClient = new MongoClient();
        mongoClient.dropDatabase(DB_NAME);
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        MongoCollection<Document> collection = database.getCollection(COLL_NAME);

        System.out.println("");
        System.out.println("MongoDB Performance for Creating Indexes, Inserting , Updating, Deleting Documents");
        System.out.println("Data file used = " + csvFileName);
        System.out.println("");

        String inputFileName = FILE_PATH + csvFileName;
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
            System.out.println("Time taken for Creating Index Columns: " + tiDiff + " ms");

            // Reading from file
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

            // Creating documents from rows read from file
            ArrayList<Document> docs = new ArrayList<Document>();
            long td1 = System.currentTimeMillis();
            for (int i = 0; i < rowsArrList.size(); i++) //Creating Documents
            {
                docs.add(MongoDBUtils.getDocument(rowsArrList.get(i), 
                        columnNameArrayList.toArray(new String[0]), TOTAL_COLS));
            }
            long td2 = System.currentTimeMillis();
            long tdDiff = td2 - td1;
            totalTime += tdDiff;
            System.out.println("Time taken for Making Documents: " + tdDiff + " ms");

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

//            printData(collection);

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
        } finally {
            mongoClient.close();

        }
    }
}