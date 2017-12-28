/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.db.crud;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dev
 */
public class Queries {

    public static void main(String args[]) {
//         selectLegalNamewhrP1OrP2();
//        long t1 = System.currentTimeMillis();
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        searchLicenseID();
//        selectLegalNamewhrP1OrP2();
//        selColWhrGivenBussNameAndCity();
//        selBussNameForGivenZipCode();

//        long t2 = System.currentTimeMillis();
//        System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
    }

//    public static void createIndexInMongoDB() {
//        MongoClient mongo;
//        try {
//            mongo = new MongoClient();
//            DB db = mongo.getDB("BUSSLICS");
//            DBCollection collection = db.getCollection("CHICAGO");
//            DBObject dbObject;
//            dbObject.put(string, db)
//            collection.createIndex("ID");
//            collection.createIndex("LICENSE ID");
//            collection.createIndex("");
//            collection.createIndex("");
//            collection.createIndex("");
//            collection.createIndex("");
//            collection.createIndex("");
//            collection.createIndex("");
//
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void searchLicenseID() {
        /*Search Given LICENSE_ID from CHICAGO_BL.*/

        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            long t1 = System.currentTimeMillis();

            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query = new BasicDBObject();
            query.put("LICENSE ID", 1404362);
            DBCursor c = collection.find(query);
            int rows = c.count();
            long t2 = System.currentTimeMillis();
            System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
            System.out.println("Rows found: " + rows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

//    public static void searchLicenseID2() {
//        /*Search Given LICENSE_ID from CHICAGO_BL.*/
//
//        MongoClient mongo;
//        try {
//            mongo = new MongoClient();
//            MongoDatabase db = mongo.getDatabase("BUSSLICS");
////            DB db = mongo.getDB("BUSSLICS");
//            long t1 = System.currentTimeMillis();
//
//            MongoCollection collection = db.getCollection("CHICAGO");
////            DBCollection collection = db.getCollection("CHICAGO");
//            // Query 1
//            BasicDBObject query = new BasicDBObject();
//            query.put("LICENSE ID", 1404362);
//            
//            MongoCursor c = collection.
////            DBCursor c = collection.find(query);
//            long t2 = System.currentTimeMillis();
//            System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
//            System.out.println(c.length());
//        } catch (Exception ex) {
//            Logger.getLogger(Queries.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
    public static void selectLegalNamewhrP1OrP2() {
        /*Select all records where legal name is 'BERGHOFF' or 'JO-KIM LOUNGE CORP.'.*/
        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            long t1 = System.currentTimeMillis();
            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query1 = new BasicDBObject();
            BasicDBObject query2 = new BasicDBObject();
            query1.put("LEGAL NAME", "BERGHOFF");
            query2.put("LEGAL NAME", "JO-KIM LOUNGE CORP.");
            BasicDBList or = new BasicDBList();
            or.add(query1);
            or.add(query2);
            DBObject query = new BasicDBObject("$or", or);
            DBCursor c = collection.find(query);
            int rows = c.count();
            long t2 = System.currentTimeMillis();
            System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
            System.out.println("Rows found: " + rows);
            //Query 2
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void selColWhrGivenBussNameAndCity() {
        /*select all columns where business name is 'ALLAN MACK & SONS INC' and CITY is "ORLAND PARK".*/
        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            long t1 = System.currentTimeMillis();
            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query1 = new BasicDBObject();
            BasicDBObject query2 = new BasicDBObject();
            query1.put("DOING BUSINESS AS NAME", "ALLAN MACK & SONS INC");
            query2.put("CITY", "ORLAND PARK");

            BasicDBList and = new BasicDBList();
            and.add(query1);
            and.add(query2);
            DBObject query = new BasicDBObject("$and", and);

            DBCursor c = collection.find(query);
            int rows = c.count();
            long t2 = System.currentTimeMillis();
            System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
            System.out.println("Rows found: " + rows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void selBussNameForGivenZipCode() {
        /*select all DOING BUSINESS AS NAME for a given zip code = 60642.*/
        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            long t1 = System.currentTimeMillis();

            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query1 = new BasicDBObject();
            query1.put("ZIP CODE", 60642);
            DBCursor c = collection.find(query1);
            int rows = c.count();
            long t2 = System.currentTimeMillis();
            System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");
            System.out.println("Rows found: " + rows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void selRecordForGivenDateIssued() {
        /*select all record where license issue date is '05/08/2002'.*/
        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query1 = new BasicDBObject();
            query1.put("DATE ISSUED", "05/08/2002");
            DBCursor c = collection.find(query1);
            System.out.println("Rows found: " + c.length());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void mongoQuery5() {
        /*select LICENSE_ID, BUSINESS_NAME and DATE_ISSUED 
        from all records where license issue date either '05/08/2002' or '10/24/2006'.*/
        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");

            DBCollection collection = db.getCollection("CHICAGO");

            // Query 1
            BasicDBObject query1 = new BasicDBObject();
            BasicDBObject query2 = new BasicDBObject();
            query1.put("ACCOUNT NUMBER", 1);
            query2.put("ACCOUNT NUMBER", 2);

            BasicDBList or = new BasicDBList();
            or.add(query1);
            or.add(query2);
            DBObject query = new BasicDBObject("$or", or);

            DBCursor c = collection.find(query);
            System.out.println("Rows found: " + c.length());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void mongoQuery6() {
        /*Select all LICENSE_ID from CHICAGO_BL.*/

        MongoClient mongo;
        try {
            mongo = new MongoClient();
            DB db = mongo.getDB("BUSSLICS");
            DBCollection collection = db.getCollection("CHICAGO");
            // Query 1
            BasicDBObject query = new BasicDBObject();
            BasicDBObject field = new BasicDBObject();
            field.put("LICENSE ID", 1);

            DBCursor cursor = collection.find(query, field);
            // System.out.println(cursor.length());
            ArrayList result = new ArrayList();
            while (cursor.hasNext()) {
                BasicDBObject obj = (BasicDBObject) cursor.next();
                result.add(obj.getString("LICENSE ID"));
            }
            System.out.println(result.size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
