/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dev
 */
public class Ex02GetJsonValue02 {

    public static void main(String[] args) throws ParseException {
        String str = "{\n"
                + "	\"id\": \"0001\",\n"
                + "	\"type\": \"donut\",\n"
                + "	\"name\": \"Cake\",\n"
                + "	\"ppu\": 0.55,\n"
                + "	\"batters\":\n"
                + "		{\n"
                + "			\"batter\":\n"
                + "				[\n"
                + "					{ \"id\": \"1001\", \"type\": \"Regular\" },\n"
                + "					{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n"
                + "					{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n"
                + "					{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n"
                + "				]\n"
                + "		},\n"
                + "	\"topping\":\n"
                + "		[\n"
                + "			{ \"id\": \"5001\", \"type\": \"None\" },\n"
                + "			{ \"id\": \"5002\", \"type\": \"Glazed\" },\n"
                + "			{ \"id\": \"5005\", \"type\": \"Sugar\" },\n"
                + "			{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },\n"
                + "			{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },\n"
                + "			{ \"id\": \"5003\", \"type\": \"Chocolate\" },\n"
                + "			{ \"id\": \"5004\", \"type\": \"Maple\" }\n"
                + "		]\n"
                + "}";

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
//        String[] keyPath = new String[]{"batters","batter","id"};
        String[] keyPath = new String[]{"topping"};
        Object result = getJsonValue(keyPath, jsonObj);
        System.out.println(result.toString());

    }

    public static Object getJsonValue(String[] keyPath, JSONObject jsonObj) {
        if (keyPath.length <= 0) {
            return null;
        }
        Object tempObj = jsonObj;
        Object[] valueObjArr = new Object[0];
        for (int i = 0; i < keyPath.length; i++) {
            if (tempObj instanceof JSONObject) {
                JSONObject tempjsonObj = (JSONObject) tempObj;
                tempObj = tempjsonObj.get(keyPath[i]);
                if (tempObj == null) {
                    return null;
                }
                continue;

            }
            if (tempObj instanceof JSONArray) {
                JSONArray tempJsonArr = (JSONArray) tempObj;
                int index = 0;
                valueObjArr = new Object[tempJsonArr.size()];
                for (int j = 0; j < tempJsonArr.size(); j++) {
                    Object tempObj1 = tempJsonArr.get(j);
                    if (tempObj1 instanceof JSONObject) {
                        tempObj = ((JSONObject) tempObj1).get(keyPath[i]);
                        if (tempObj == null) {
                            return null;
                        }
                        valueObjArr[j] = tempObj;

                    } else if (tempObj1 instanceof JSONArray) {

                    } else {
                        valueObjArr[j] = tempObj1;

                    }

                }
                tempObj = valueObjArr;
                continue;

            }

        }

        return tempObj;
    }

}
