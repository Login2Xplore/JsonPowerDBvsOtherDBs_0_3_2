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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hemant
 */
public class Ex03GetJsonValue {

    public static void main(String[] args) throws ParseException {
//        testSetJsonDepth1();
        
//        testForJsonDepth1();
//        System.out.println("\n\n");
//
//        testForJsonDepth2();
//        System.out.println("\n\n");

        testForJsonDepth3();
        System.out.println("\n\n");
    }
    
    private static void testSetJsonDepth1() throws ParseException {
        String str = getComplexJson1();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        System.out.println(jsonObj);
        System.out.println("");
        String[] keyPath;
        int[] keyArrLoc;
        Object result;

        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{-1};
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);


        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{0};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"ppu"};
        keyArrLoc = new int[]{-1};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"type"};
        keyArrLoc = new int[]{-2};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"name"};
        keyArrLoc = new int[]{3};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array2"};
        keyArrLoc = new int[]{-2};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array3"};
        keyArrLoc = new int[]{-1};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{0};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{1};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{2};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{3};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{4};
//        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        result = JsonUtilsForTesting.setValue("555555555", keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        System.out.println("----------------------------------------------");
        System.out.println(jsonObj);

    }
    

    public static void testForJsonDepth1() throws ParseException {
        String str = getComplexJson1();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;
        Object result;

        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{-1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"ppu"};
        keyArrLoc = new int[]{-1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"type"};
        keyArrLoc = new int[]{-2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"name"};
        keyArrLoc = new int[]{3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{-2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{-1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{4};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        printResult(keyPath, keyArrLoc, result);
    }

    public static void testForJsonDepth2() throws ParseException {
        String str = getComplexJson1();

        JSONObject rootJsonObj = new JSONObject();
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);

        rootJsonObj.put("id", "123");
        rootJsonObj.put("name", "Anil");
        rootJsonObj.put("food", jsonObj);
        rootJsonObj.put("mobile", null);
        System.out.println(rootJsonObj);

        String[] keyPath;
        int[] keyArrLoc;
        Object result;

        keyPath = new String[]{"food"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"mobile"};
        keyArrLoc = new int[]{-1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "abc"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "abc"};
        keyArrLoc = new int[]{-1, 0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "ppu"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "type"};
        keyArrLoc = new int[]{-1, -2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "name"};
        keyArrLoc = new int[]{-1, 3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, -2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, 0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, 1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, 2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, 3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"food", "Array1"};
        keyArrLoc = new int[]{-1, 4};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);
    }

    public static void testForJsonDepth3() throws ParseException {
        String str = getComplexJson1();

        JSONObject rootJsonObj = new JSONObject();

        JSONObject level2JsonObj = new JSONObject();
        level2JsonObj.put("item-name", "Fast Food");

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);

        rootJsonObj.put("id", "123");
        rootJsonObj.put("name", "Anil");
        rootJsonObj.put("mobile", null);

        level2JsonObj.put("food", jsonObj);

        rootJsonObj.put("item", level2JsonObj);

        System.out.println(rootJsonObj);

        String[] keyPath;
        int[] keyArrLoc;
        Object result;

        keyPath = new String[]{"item", "food"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"mobile", "item"};
        keyArrLoc = new int[]{-1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "abc"};
        keyArrLoc = new int[]{-1, -1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "abc"};
        keyArrLoc = new int[]{-1, -1, 0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "ppu"};
        keyArrLoc = new int[]{-1, -1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "type"};
        keyArrLoc = new int[]{-1, -1, -2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "name"};
        keyArrLoc = new int[]{-1, -1, 3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, -2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, 0};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, 1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, 2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, 3};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array1"};
        keyArrLoc = new int[]{-1, -1, 4};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "batters", "batter"};
        keyArrLoc = new int[]{-1, -1, 4, -1};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);

        keyPath = new String[]{"item", "food", "Array3", "Array3_1"};
        keyArrLoc = new int[]{-1, -1, 0, -1, -1, 2};
        result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, rootJsonObj);
        printResult(keyPath, keyArrLoc, result);
    }

    public static void testComplexJson2() throws ParseException {
        String str = getComplexJson2();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;

//        keyPath = new String[]{"medications", "betaBlocker","route"};
        keyPath = new String[]{"labs", "time"};
        keyArrLoc = new int[]{-1, -1};

        Object result = JsonUtilsForTesting.getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);
    }

    public static String getComplexJson2() {
        String str = "{\n"
                + "    \"medications\":[{\n"
                + "            \"aceInhibitors\":[{\n"
                + "                \"name\":\"lisinopril\",\n"
                + "                \"strength\":\"10 mg Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"PO\",\n"
                + "                \"sig\":\"daily\",\n"
                + "                \"pillCount\":\"#90\",\n"
                + "                \"refills\":\"Refill 3\"\n"
                + "            }],\n"
                + "            \"antianginal\":[{\n"
                + "                \"name\":\"nitroglycerin\",\n"
                + "                \"strength\":\"0.4 mg Sublingual Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"SL\",\n"
                + "                \"sig\":\"q15min PRN\",\n"
                + "                \"pillCount\":\"#30\",\n"
                + "                \"refills\":\"Refill 1\"\n"
                + "            }],\n"
                + "            \"anticoagulants\":[{\n"
                + "                \"name\":\"warfarin sodium\",\n"
                + "                \"strength\":\"3 mg Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"PO\",\n"
                + "                \"sig\":\"daily\",\n"
                + "                \"pillCount\":\"#90\",\n"
                + "                \"refills\":\"Refill 3\"\n"
                + "            }],\n"
                + "            \"betaBlocker\":[{\n"
                + "                \"name\":\"metoprolol tartrate\",\n"
                + "                \"strength\":\"25 mg Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"PO\",\n"
                + "                \"sig\":\"daily\",\n"
                + "                \"pillCount\":\"#90\",\n"
                + "                \"refills\":\"Refill 3\"\n"
                + "            }],\n"
                + "            \"diuretic\":[{\n"
                + "                \"name\":\"furosemide\",\n"
                + "                \"strength\":\"40 mg Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"PO\",\n"
                + "                \"sig\":\"daily\",\n"
                + "                \"pillCount\":\"#90\",\n"
                + "                \"refills\":\"Refill 3\"\n"
                + "            }],\n"
                + "            \"mineral\":[{\n"
                + "                \"name\":\"potassium chloride ER\",\n"
                + "                \"strength\":\"10 mEq Tab\",\n"
                + "                \"dose\":\"1 tab\",\n"
                + "                \"route\":\"PO\",\n"
                + "                \"sig\":\"daily\",\n"
                + "                \"pillCount\":\"#90\",\n"
                + "                \"refills\":\"Refill 3\"\n"
                + "            }]\n"
                + "        }\n"
                + "    ],\n"
                + "    \"labs\":[{\n"
                + "        \"name\":\"Arterial Blood Gas\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Main Hospital Lab\"      \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"BMP\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"BNP\",\n"
                + "        \"time\":\"3 Weeks\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"BUN\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Cardiac Enzymes\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"CBC\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Creatinine\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Main Hospital Lab\"  \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Electrolyte Panel\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Glucose\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Main Hospital Lab\"  \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"PT/INR\",\n"
                + "        \"time\":\"3 Weeks\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"PTT\",\n"
                + "        \"time\":\"3 Weeks\",\n"
                + "        \"location\":\"Coumadin Clinic\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"TSH\",\n"
                + "        \"time\":\"1 Year\",\n"
                + "        \"location\":\"Primary Care Clinic\"    \n"
                + "        }\n"
                + "    ],\n"
                + "    \"imaging\":[{\n"
                + "        \"name\":\"Chest X-Ray\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Main Hospital Radiology\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Chest X-Ray\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Main Hospital Radiology\"    \n"
                + "        },\n"
                + "        {\n"
                + "        \"name\":\"Chest X-Ray\",\n"
                + "        \"time\":\"Today\",\n"
                + "        \"location\":\"Main Hospital Radiology\"    \n"
                + "        }\n"
                + "    ]\n"
                + "}";
        return str;
    }

    public static String getComplexJson1() {
        String str = "{\n"
                + "	\"id\": \"0001\",\n"
                + "	\"type\": \"donut\",\n"
                + "	\"name\": \"Cake\",\n"
                + "	\"ppu\": 0.55,\n"
                + "	\"batters\":\n"
                + "		{\n"
                + "                     \"ppu\": 0.55,\n"
                + "			\"batter\":\n"
                + "				[\n"
                + "					{ \"id\": \"1001\", \"type\": \"Regular\" },\n"
                + "					{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n"
                + "					{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n"
                + "					{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n"
                + "				],\n"
                + "                     \"bat\": {\"batCol\":1234}"
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
                + "		]\n,"
                + "	\"topping1\":\n"
                + "		[\n"
                + "			{ \"id\": [\"5001\",\"5002\",\"5003\"]},\n"
                + "                     {\"types\": [\"Glazed\",\"Sugar\",\"Powdered Sugar\"]}   "
                + "		]\n,"
                + "	\"topping2\":\n"
                + "		{\n"
                + "	              \"topping2_1\": [\"5001\",\"5002\",{\"key_1\": 20 },\"5003\"],\n"
                + "	              \"topping2_2\": {\"key_1\": 20 },\n"
                + "                   \"types\": [\"Glazed\",\"Sugar\",\"Powdered Sugar\"]   "
                + "		}\n,"
                + "     \"Array1\": [10,{\"Key1_1\": 20},30,null],\n"
                + "     \"Array2\": {\"key1\": \"value1\",\"key2\":123,\"key3\": true},\n"
                + "     \"Array3\": [ { \"Array3_1\": [{\"Array_3_1_1\": {\"Name\": \"XYZ\", \"phone\":[10,20,30]}},"
                + "                                    {\"Array_3_1_2\": {\"Name\": \"ABC\", \"phone\":[33,44,22]}}"
                + "                                   ]}],\n"
                + "}";
        return str;
    }

    private static void printResult(String[] keyPath, int[] keyArrLoc, Object result) {
//        System.out.println("");
        for (int i = 0; i < keyPath.length; i++) {
            System.out.print("." + keyPath[i] + "[" + keyArrLoc[i] + "]");
        }
        System.out.println("\t" + result);
    }

}
