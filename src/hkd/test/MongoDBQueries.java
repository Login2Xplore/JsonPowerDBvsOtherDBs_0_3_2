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
 * Copyright © 2014 - Hemant Kumar Dugar - All Rights Reserved
 */
package hkd.test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;

/**
 *
 * @author hemant
 */
public class MongoDBQueries {

    public static void main(String args[]) {
        // mongoQuery1();
        mongoQuery7();
    }

    public static void mongoQuery1() {
        /*Select all records where legal name is 'BERGHOFF' or 'JO-KIM LOUNGE CORP.'.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("chicago");
        DBCollection collection = db.getCollection("ChicagoBL");
//        collection.createIndex("LEGAL NAME");

        long t1 = System.currentTimeMillis();

        BasicDBObject query1 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        query1.put("LEGAL NAME", "BERGHOFF");
        query2.put("LEGAL NAME", "JO-KIM LOUNGE CORP.");
        BasicDBList or = new BasicDBList();
        or.add(query1);
        or.add(query2);
        DBObject query = new BasicDBObject("$or", or);
        DBCursor c = collection.find(query);
        
        long t2 = System.currentTimeMillis();
        System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");

        System.out.println(c.length());

    }

    public static void mongoQuery7() {
        /*Select all records where legal name is 'BERGHOFF' or 'JO-KIM LOUNGE CORP.'.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("chicago");
        
        long t1 = System.currentTimeMillis();

        DBCollection collection = db.getCollection("ChicagoBL");

        BasicDBObject query1 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        query1.put("LICENSE ID", 1404362);
        DBCursor c = collection.find(query1);
        
        long t2 = System.currentTimeMillis();
        System.out.println("MongoDB time:  " + (t2 - t1) + " miliseconds");

        System.out.println(c.length());

    }

    public static void mongoQuery2() {
        /*select all columns where business name is 'HONEYBEERS' and LICENSE_CODE is 1781.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("BussLics");
        DBCollection collection = db.getCollection("ChicagoBL");
        BasicDBObject query1 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        query1.put("DOING BUSINESS AS NAME", "HONEYBEERS");
        query2.put("LICENSE CODE", 1781);
        BasicDBList and = new BasicDBList();
        and.add(query1);
        and.add(query2);
        DBObject query = new BasicDBObject("$and", and);
        DBCursor c = collection.find(query);
        System.out.println(c.length());

    }

    public static void mongoQuery3() {
        /*select all DOING BUSINESS AS NAME for a given zip code = 60642.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("BussLics");
        DBCollection collection = db.getCollection("ChicagoBL");
        BasicDBObject query1 = new BasicDBObject();
        query1.put("ZIP CODE", 60642);
        DBCursor c = collection.find(query1);
        System.out.println(c.length());

    }

    public static void mongoQuery4() {
        /*select all record where license issue date is '05/08/2002'.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("BussLics");
        DBCollection collection = db.getCollection("ChicagoBL");
        BasicDBObject query1 = new BasicDBObject();
        query1.put("DATE ISSUED", "05/08/2002");
        DBCursor c = collection.find(query1);
        System.out.println(c.length());

    }

    public static void mongoQuery5() {
        /*select LICENSE_ID, BUSINESS_NAME and DATE_ISSUED 
        from all records where license issue date either '05/08/2002' or '10/24/2006'.*/
        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("BussLics");
        DBCollection collection = db.getCollection("ChicagoBL");
        BasicDBObject query1 = new BasicDBObject();
        BasicDBObject query2 = new BasicDBObject();
        query1.put("ACCOUNT NUMBER", 1);
        query2.put("ACCOUNT NUMBER", 2);
        BasicDBList or = new BasicDBList();
        or.add(query1);
        or.add(query2);
        DBObject query = new BasicDBObject("$or", or);
        DBCursor c = collection.find(query);
        System.out.println(c.length());

    }

    public static void mongoQuery6() {
        /*Select all LICENSE_ID from CHICAGO_BL.*/

        MongoClient mongo;
        mongo = new MongoClient();
        DB db = mongo.getDB("chicago");
        DBCollection collection = db.getCollection("ChicagoBL");
        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        field.put("LICENSE ID", 1);
        DBCursor cursor = collection.find(query, field);
        ArrayList result = new ArrayList();
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            result.add(obj.getString("LICENSE ID"));
        }
        System.out.println(result.size());

    }
}
