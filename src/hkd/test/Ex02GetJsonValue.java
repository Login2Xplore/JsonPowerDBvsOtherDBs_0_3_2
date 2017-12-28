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

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hemant
 */
public class Ex02GetJsonValue {

    public static void main(String[] args) throws ParseException {
        testForJsonDepthN();

    }

    public static void testForJsonDepthN() throws ParseException {
        String str = getComplexJson1();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;
        Object result;
        //keyPath = new String[]{"Array3", "Array3_1", "Array_3_1_2", "phone"};
        keyPath = new String[]{"topping2", "topping2_1", "key_1"};
//        keyPath = new String[]{"Array2","key1"};

//        keyPath = new String[]{"topping2", "topping2_1"};
//        keyPath = new String[]{"Array3", "Array3_1", "Array_3_1_1"};
        keyPath = new String[]{"topping2", "topping2_1", "key_1"};
        keyArrLoc = new int[]{-1, -1, -1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

    }

    public static void testForJsonDepth2() throws ParseException {
        String str = getComplexJson1();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;
        Object result;
        //keyPath = new String[]{"Array3", "Array3_1", "Array_3_1_2", "phone"};
//        keyPath = new String[]{"topping2", "topping2_1","key_1"};
//        keyPath = new String[]{"Array2","key1"};

//        keyPath = new String[]{"topping2", "topping2_1"};
        keyPath = new String[]{"batters", "ppu"};
        keyArrLoc = new int[]{-1, -1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "batter"};
        keyArrLoc = new int[]{-1, -1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "batter"};
        keyArrLoc = new int[]{-1, 0};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "batter"};
        keyArrLoc = new int[]{-1, 2};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "batter"};
        keyArrLoc = new int[]{-1, 7};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "bat"};
        keyArrLoc = new int[]{-1, -1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"batters", "batter", "type"};
        keyArrLoc = new int[]{-1, 2, -1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

    }

    public static void testForJsonDepth1() throws ParseException {
        String str = getComplexJson1();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;
        //keyPath = new String[]{"Array3", "Array3_1", "Array_3_1_2", "phone"};
//        keyPath = new String[]{"topping2", "topping2_1","key_1"};
//        keyPath = new String[]{"Array2","key1"};

//        keyPath = new String[]{"topping2", "topping2_1"};
        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{-1};
        Object result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"abc"};
        keyArrLoc = new int[]{0};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{-2};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{-1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{0};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{1};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{2};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{3};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);

        keyPath = new String[]{"Array1"};
        keyArrLoc = new int[]{4};
        result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);
    }

    public static void testComplexJson2() throws ParseException {
        String str = getComplexJson2();

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(str);
        String[] keyPath;
        int[] keyArrLoc;

//        keyPath = new String[]{"medications", "betaBlocker","route"};
        keyPath = new String[]{"labs", "time"};
        keyArrLoc = new int[]{-1, -1};

        Object result = getValue(keyPath, keyArrLoc, jsonObj);
        System.out.println(result);
    }

    private static Object[] getValueFromSimpleJson(String key, int arrLoc, Object obj) {
        Object[] retObj = new Object[1];

        if (obj instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) obj;
            Object lvObj = jsonObj.get(key);
            if (lvObj == null) {
                return null;
            }
            if (arrLoc >= 0) {
                if (lvObj instanceof JSONArray) {
                    JSONArray lvArrObj = (JSONArray) lvObj;
                    if (arrLoc < lvArrObj.size()) {
                        retObj[0] = lvArrObj.get(arrLoc);
                        return retObj;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            retObj[0] = lvObj;
            return retObj;
        }

        if (obj instanceof JSONArray) {
            JSONArray lvArrObj = (JSONArray) obj;
            if (arrLoc < lvArrObj.size()) {
                if (arrLoc >= 0) {
                    retObj[0] = lvArrObj.get(arrLoc);
                    return retObj;
                } else {
                    for (int i = 0; i < lvArrObj.size(); i++) {
                        Object inArrayObj = lvArrObj.get(i);
                        if (inArrayObj instanceof JSONObject) {
                            JSONObject inJsonObj = (JSONObject) inArrayObj;
                            if (inJsonObj.containsKey(key)) {
                                retObj[0] = inJsonObj.get(key);
                                return retObj;
                            }
                        }
                    }
                    return null;
                }
            }else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static ArrayList getValue(String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
        ArrayList valuesArrayList = new ArrayList();
        if (keyPath.length == 1) {
            Object lvObj;

//            lvObj = jsonObj.get(keyPath[0]);
//            if (lvObj == null) {
//                return new ArrayList();
//            }
            Object[] arrObj = getValueFromSimpleJson(keyPath[0], arrLoc[0], jsonObj);
            if (arrObj == null) {
                return new ArrayList();
            }
            valuesArrayList.add(arrObj[0]);
            return valuesArrayList;
        }

        // This condition is here to improve the performance as most jsonObj are of keyPath.length == 1
        if (keyPath.length <= 0) {
            return new ArrayList();
        }

        Object tempObj = jsonObj.get(keyPath[0]);

        // If first key not found in jsonObj we should return empty ArrayList
        if (tempObj == null) {
            return new ArrayList();
        }

        for (int i = 1; i < keyPath.length; i++) {
            if (i == keyPath.length - 1) {
                Object[] lvObj = getValueFromSimpleJson(keyPath[i], arrLoc[i], tempObj);
                if (lvObj == null) {
                    return new ArrayList();
                }
                valuesArrayList.add(lvObj[0]);
                return valuesArrayList;
            }
            Object[] lvObj = getValueFromSimpleJson(keyPath[i], arrLoc[i], tempObj);
            if (lvObj == null) {
                return new ArrayList();
            }
            tempObj = lvObj[0];
        }
        return new ArrayList();
//
//        for (int i = 1; i < keyPath.length; i++) {
//            if (i == keyPath.length - 1) {
//                if (tempObj instanceof JSONArray) {
//                    JSONArray tempJsonArr = (JSONArray) tempObj;
//                    ArrayList list = new ArrayList();
//                    for (int j = 0; j < tempJsonArr.size(); j++) {
//                        tempObj = tempJsonArr.get(j);
//                        if (tempObj instanceof JSONObject) {
//                            Object lvTempObj = ((JSONObject) tempObj).get(keyPath[i]);
//                            if (lvTempObj != null) {
//                                list.add(lvTempObj);
//                            }
//                        }
//                    }
//                    return list;
//                }
//                if (!((JSONObject) tempObj).containsKey(keyPath[i])) {
//                    return new ArrayList();
//                }
//                Object lvObj = ((JSONObject) tempObj).get(keyPath[i]);
//                valuesArrayList.add(lvObj);
//                return valuesArrayList;
//            }
//            if (tempObj instanceof JSONArray) {
//                JSONArray tempJsonArr = (JSONArray) tempObj;
//                boolean foundFlag = false;
//                //ArrayList list = new ArrayList();
//                for (int j = 0; j < tempJsonArr.size(); j++) {
//                    tempObj = tempJsonArr.get(j);
//                    if (tempObj instanceof JSONObject) {
//                        Object lvTempObj = ((JSONObject) tempObj).get(keyPath[i]);
//                        if (lvTempObj == null) {
//                            //return new ArrayList();
//                            continue;
//                        }
//                        if (lvTempObj instanceof JSONObject || lvTempObj instanceof JSONArray) {
//                            foundFlag = true;
//                            tempObj = lvTempObj;
//                            break;
//                            //list.add(lvTempObj);
//                        }
//                        return new ArrayList();
//                    }
//                    return new ArrayList();
//                }
//                if (!foundFlag) {
//                    return new ArrayList();
//                }
//                continue;
//            }
//            if (tempObj instanceof JSONObject) {
//                tempObj = ((JSONObject) tempObj).get(keyPath[i]);
//                if (tempObj == null) {
//                    return new ArrayList();
//                }
//                if (tempObj instanceof JSONObject || tempObj instanceof JSONArray) { //
//                    continue;
//                }
//                return new ArrayList();
//            }
//            return new ArrayList();
//        }
//        return new ArrayList();
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
}
