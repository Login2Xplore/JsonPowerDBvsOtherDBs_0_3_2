/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test.himanshu;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.Arrays;
import org.bson.Document;

/**
 *
 * @author himanshu dugar
 */
public class mongoBDoperations {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient();
        //Inserting Data
        MongoDatabase database = mongoClient.getDatabase("Himdb");
        MongoCollection<Document> collection = database.getCollection("test");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 12)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
        //System.out.println(collection.count());
//        Document myDoc = collection.find().first();
//        System.out.println(myDoc.toJson());
        
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        
        //Indexing
        //collection.createIndex(new Document("count", 1));
        
        //Updating Data
        collection.updateOne(eq("count", 1), new Document("$set", new Document("count", 110)));
        System.out.println(collection.count());
        Document myDoc1 = collection.find().first();
        System.out.println(myDoc1.toJson());
        
        //Deleting Data
        collection.deleteOne(eq("count", 12));
        MongoCursor<Document> cursor1 = collection.find().iterator();
                try {
            while (cursor1.hasNext()) {
                System.out.println(cursor1.next().toJson());
            }
        } finally {
            cursor1.close();
        }
    }
}
