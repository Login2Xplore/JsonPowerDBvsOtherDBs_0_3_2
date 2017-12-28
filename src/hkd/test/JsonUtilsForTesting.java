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

/**
 *
 * @author hemant
 */
public class JsonUtilsForTesting {

//    public static Object setValue(Object newValue, String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
//        if (newValue == null || newValue instanceof Number || newValue instanceof String || newValue instanceof Boolean) {
//            // left empty as continue to the next statment of this if.
//        } else {
//            throw new IllegalArgumentException("newValue must be one of String, Long, Boolen, Double or null");
//        }
//        ArrayList valuesArrayList = new ArrayList();
//        int keyPathLen = keyPath.length;
//        if (keyPathLen == 0) {
//            return valuesArrayList;
//        }
//        Object lvJsonObj = jsonObj;
//        for (int i = 0; i < keyPath.length; i++) {
//            Object[] jsonValueArr = setValueInSimpleJson(newValue, keyPath[i], arrLoc[i], lvJsonObj);
//
//            // Didnt find anything associated to the key so empty array is returned.
//            if (jsonValueArr == null) {
//                return valuesArrayList;
//            }
//
//            // Found a null value associated to key in the path or as last key in the path.
//            if (jsonValueArr[0] == null) {
//                // null value of a last key in key path
//                if (i == keyPathLen - 1) {
//                    valuesArrayList.add(null);
//                    return valuesArrayList;
//                }
//                // null value in the key path and not the last key in the key path
//                return valuesArrayList;
//            }
//            // Reterieved the object of getValue() call result.
//            lvJsonObj = jsonValueArr[0];
//            // If its the last key in key path we should return that result.
//            if (i == keyPathLen - 1) {
//                valuesArrayList.add(lvJsonObj);
//                return valuesArrayList;
//            }
//            if (lvJsonObj instanceof JSONObject || lvJsonObj instanceof JSONArray) {
//                continue;
//            }
//            break;
//        }
//        return new ArrayList();
//    }
    private static final int GET_FLAG = 0;
    private static final int SET_FLAG = 1;
    private static final int REMOVE_FLAG = 2;

    public static ArrayList getValue(String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
        JSONObject simpleJsonObj = traverse(keyPath, arrLoc, jsonObj);
        if (simpleJsonObj == null) {
            return null;
        }
        Object[] retValue = operateOnSimpleJson(null, keyPath[keyPath.length - 1], arrLoc[keyPath.length - 1], simpleJsonObj, GET_FLAG);
        if (retValue == null) {
            return null;
        }
        ArrayList valuesArrayList = new ArrayList();
        valuesArrayList.add(retValue[0]);
        return valuesArrayList;
    }

    public static ArrayList removeValue(Object newValue, String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
        if (newValue == null || newValue instanceof Number || newValue instanceof String || newValue instanceof Boolean) {
            // left empty as continue to the next statment of this if.
        } else {
            throw new IllegalArgumentException("newValue must be one of String, Long, Boolen, Double or null");
        }
        JSONObject simpleJsonObj = traverse(keyPath, arrLoc, jsonObj);
        if (simpleJsonObj == null) {
            return null;
        }
        Object[] retValue = operateOnSimpleJson(newValue, keyPath[keyPath.length - 1], arrLoc[keyPath.length - 1], simpleJsonObj, REMOVE_FLAG);
        if (retValue == null) {
            return null;
        }
        ArrayList valuesArrayList = new ArrayList();
        valuesArrayList.add(retValue[0]);
        return valuesArrayList;
    }

    public static ArrayList setValue(Object newValue, String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
        if (newValue == null || newValue instanceof Number || newValue instanceof String || newValue instanceof Boolean) {
            // left empty as continue to the next statment of this if.
        } else {
            throw new IllegalArgumentException("newValue must be one of String, Long, Boolen, Double or null");
        }
        JSONObject simpleJsonObj = traverse(keyPath, arrLoc, jsonObj);
        if (simpleJsonObj == null) {
            return null;
        }
        Object[] retValue = operateOnSimpleJson(newValue, keyPath[keyPath.length - 1], arrLoc[keyPath.length - 1], simpleJsonObj, SET_FLAG);
        if (retValue == null) {
            return null;
        }
        ArrayList valuesArrayList = new ArrayList();
        valuesArrayList.add(retValue[0]);
        return valuesArrayList;
    }

    private static JSONObject traverse(String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
        JSONObject parent = jsonObj;

        int keyPathLen = keyPath.length;
        if (keyPathLen == 0) {
            return null;
        }
        JSONObject inJsonObj = jsonObj;
        for (int i = 0; i < keyPath.length; i++) {
            if (inJsonObj.containsKey(keyPath[i])) {
                Object lvObj = inJsonObj.get(keyPath[i]);
                if (i == keyPathLen - 1) {
                    return inJsonObj;
                }
                if (lvObj instanceof JSONObject) {
                    inJsonObj = (JSONObject) lvObj;
                    continue;
                } else if (lvObj instanceof JSONArray) {
                    JSONArray lvJsonArr = (JSONArray) lvObj;
                    if (arrLoc[i] < 0 || arrLoc[i] >= lvJsonArr.size()) {
                        break;
                    }
                    Object tempObj = lvJsonArr.get(arrLoc[i]);
                    if (tempObj instanceof JSONObject) {
                        inJsonObj = (JSONObject) tempObj;
                        continue;
                    }
                    break;
                } else {
                    break;
                }
            } else {
                return inJsonObj;
            }
        }
        return null;
    }

    private static Object[] operateOnSimpleJson(Object newValue, String key, int arrLoc, Object jsonSimple, int opFlag) {
        Object[] retObj = new Object[1];

        if (jsonSimple instanceof JSONObject) {
            JSONObject inJsonObj = (JSONObject) jsonSimple;
            if (inJsonObj.containsKey(key)) {
                Object lvObj = inJsonObj.get(key);
                if (lvObj instanceof JSONArray) {
                    JSONArray lvJsonArr = (JSONArray) lvObj;
                    if (arrLoc < 0) {
                        opJsonObject(retObj, newValue, key, inJsonObj, opFlag);
                        return retObj;
                    }
                    if (arrLoc < lvJsonArr.size()) {
                        // individual value in JSONArray in String, Long, Double and Boolean at location arrLoc
                        opJsonArray(retObj, newValue, arrLoc, lvJsonArr, opFlag);
                        return retObj;
                    } else {
                        addToJsonArray(retObj, newValue, lvJsonArr, opFlag);
                        return null;
                    }
                }
                // individual value in String, Long, Double and Boolean
                opJsonObject(retObj, newValue, key, inJsonObj, opFlag);
                return retObj;
            } else {
                opJsonObject(retObj, newValue, key, inJsonObj, opFlag);
                return retObj;
            }
        }
        return null;
    }

    private static void addToJsonArray(Object[] retObj, Object newValue, JSONArray lvJsonArr, int opFlag) {
        switch (opFlag) {
            case SET_FLAG:
                lvJsonArr.add(newValue);
                break;
            case REMOVE_FLAG:
                break;
            case GET_FLAG:
                break;
        }
    }

    private static void opJsonArray(Object[] retObj, Object newValue, int arrLoc, JSONArray lvJsonArr, int opFlag) {
        switch (opFlag) {
            case SET_FLAG:
                retObj[0] = lvJsonArr.set(arrLoc, newValue);
                break;
            case REMOVE_FLAG:
                retObj[0] = lvJsonArr.remove(arrLoc);
                break;
            case GET_FLAG:
                retObj[0] = lvJsonArr.get(arrLoc);
                break;
        }
    }

    private static void opJsonObject(Object[] retObj, Object newValue, String key, JSONObject inJsonObj, int opFlag) {
        switch (opFlag) {
            case SET_FLAG:
                retObj[0] = inJsonObj.put(key, newValue);
                break;
            case REMOVE_FLAG:
                retObj[0] = inJsonObj.remove(key);
                break;
            case GET_FLAG:
                retObj[0] = inJsonObj.get(key);
                break;
        }
    }

//    public static ArrayList getValue(String[] keyPath, int[] arrLoc, JSONObject jsonObj) {
//        ArrayList valuesArrayList = new ArrayList();
//        int keyPathLen = keyPath.length;
//        if (keyPathLen == 0) {
//            return valuesArrayList;
//        }
//        Object lvJsonObj = jsonObj;
//        for (int i = 0; i < keyPath.length; i++) {
//            Object[] jsonValueArr = getValueFromSimpleJson(keyPath[i], arrLoc[i], lvJsonObj);
//
//            // Didnt find anything associated to the key so empty array is returned.
//            if (jsonValueArr == null) {
//                return valuesArrayList;
//            }
//
//            // Found a null value associated to key in the path or as last key in the path.
//            if (jsonValueArr[0] == null) {
//                // null value of a last key in key path
//                if (i == keyPathLen - 1) {
//                    valuesArrayList.add(null);
//                    return valuesArrayList;
//                }
//                // null value in the key path and not the last key in the key path
//                return valuesArrayList;
//            }
//            // Reterieved the object of getValue() call result.
//            lvJsonObj = jsonValueArr[0];
//            // If its the last key in key path we should return that result.
//            if (i == keyPathLen - 1) {
//                valuesArrayList.add(lvJsonObj);
//                return valuesArrayList;
//            }
//            if (lvJsonObj instanceof JSONObject || lvJsonObj instanceof JSONArray) {
//                continue;
//            }
//            break;
//        }
//        return new ArrayList();
//    }
//
//    private static Object[] getValueFromSimpleJson(String key, int arrLoc, Object jsonSimple) {
//        Object[] retObj = new Object[1];
//
//        if (jsonSimple instanceof JSONObject) {
//            JSONObject inJsonObj = (JSONObject) jsonSimple;
//            if (inJsonObj.containsKey(key)) {
//                Object lvObj = inJsonObj.get(key);
//                if (lvObj instanceof JSONArray) {
//                    JSONArray lvJsonArr = (JSONArray) lvObj;
//                    if (arrLoc < 0) {
//                        retObj[0] = lvJsonArr;
//                        return retObj;
//                    }
//                    if (arrLoc < lvJsonArr.size()) {
//                        // individual value in JSONArray in String, Long, Double and Boolean at location arrLoc
//                        retObj[0] = lvJsonArr.get(arrLoc);
//                        return retObj;
//                    } else {
//                        return null;
//                    }
//                }
//                // individual value in String, Long, Double and Boolean
//                retObj[0] = lvObj;
//                return retObj;
//            }
//            // The following else if will not execute based on our testing.
//            // As JsonArray will not contain another Json array.
//            // So every call to this method happens only when there is JSONObject.
//            // Only the last item if JSONArray will be returned by above code
//            // And will be returned to client by the caller of this method i.e. getValue().
////        } else if (jsonSimple instanceof JSONArray) {
////            if (arrLoc < 0) {
////                return null;
////            }
////            JSONArray jsonArr = (JSONArray) jsonSimple;
////            if (arrLoc >= jsonArr.size()) {
////                return null;
////            } else {
////                Object inArrayObj = jsonArr.get(arrLoc);
////                if (inArrayObj instanceof JSONObject) {
////                    JSONObject inJsonObj = (JSONObject) inArrayObj;
////                    if (!inJsonObj.containsKey(key)) {
////                        return null;
////                    } else {
////                        Object lvObj = inJsonObj.get(key);
////                        retObj[0] = lvObj;
////                        return retObj;
////                    }
////                }
////            }
//        }
//        return null;
//    }

}
