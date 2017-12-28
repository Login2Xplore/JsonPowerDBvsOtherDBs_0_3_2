/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test;

/**
 *
 * @author dev
 */
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Java MongoDB : Insert a Document
 *
 */
public class MongodbOp {

    public static void main(String[] args) throws IOException {
        MongoClient mongo = new MongoClient();
        DB db = mongo.getDB("chicago");
        System.out.println("BasicDBObject example...");
        BasicDBObject document = new BasicDBObject();
        DBObject dbObject;

        DBCollection collection = db.getCollection("ChicagoBL");
        collection.drop();

        String inputFileName = "/home/hemant/nbPiDemo/DataSets/City of Chicago/ChicagoBL-500k.csv";
        //String outputFile = "/home/dev/dev/dataset/BussLics/ChicagoBL-500k_2.json";
        ArrayList<String> columnNameArrList = new ArrayList<>();
        FileInputStream fstream = null;
        String strLine;
        try {
            fstream = new FileInputStream(inputFileName);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1)) {
                strLine = br.readLine();
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
                int count = 0;
                JSONArray jsonArray = new JSONArray();
                while ((strLine = br.readLine()) != null) {
                    JSONObject jSONObject = getJsonObjForLine(strLine, columnNameArrList);
                    dbObject = (DBObject) JSON.parse(jSONObject.toString());

                    collection.insert(dbObject);
//                    System.out.println(jSONObject);
//                    count++;
//                    if (count == 5) {
//                        break;
//                    }

                    
                }
                
                DBCursor cursorDoc = collection.find();
//                
//                    while (cursorDoc.hasNext()) {
////                        System.out.println(cursorDoc.next());
//                    }

            }

//
//	collection.remove(new BasicDBObject());
//        collection.createIndex("LEGAL NAME");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JSONObject getJsonObjForLine(String line, ArrayList<String> columnName) {
        StringBuilder sb = new StringBuilder();
        JSONObject resultantJsonObj = new JSONObject();
        int flag = 0;
        char ch;
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            ch = line.charAt(i);
            if (ch == '"' && flag == 0) {
                flag++;
            } else if (ch == '"' && flag == 1) {
                flag = 0;
            } else if (ch == ',' && flag == 0) {
                String str = sb.toString();
                double dblVal;
                boolean boolVal;
                long longVal;
                try {

                    dblVal = Double.parseDouble(str);
                    int intVal = (int) dblVal;
                    if ((dblVal - intVal) == 0.0) {
                        longVal = Long.parseLong(str);
                        resultantJsonObj.put(columnName.get(count), longVal);
                    } else {
                        resultantJsonObj.put(columnName.get(count), dblVal);
                    }

                } catch (NumberFormatException nfe) {
                    if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                        boolVal = Boolean.parseBoolean(str);
                        resultantJsonObj.put(columnName.get(count), boolVal);

                    } else {
                        resultantJsonObj.put(columnName.get(count), str);
                    }
                } finally {
                    if (count > 12) {
                        break;
                    }
                }

                count++;
                sb.setLength(0);
            } else {
                sb.append(ch);
            }
        }
        // System.out.println(resultantJsonObj.toJSONString());
        return resultantJsonObj;
    }

}