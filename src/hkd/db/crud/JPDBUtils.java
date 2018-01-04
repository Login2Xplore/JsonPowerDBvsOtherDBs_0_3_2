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
 * Copyright © 2018 - Hemant Kumar Dugar - All Rights Reserved
 */
package hkd.db.crud;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hkd
 */
public class JPDBUtils {

    public static final String IML_PART_URL = "/api/iml";
    public static final String IDL_PART_URL = "/api/idl";
    public static final String IRL_PART_URL = "/api/irl";
    public static final String LOGIN_PART_URL = "/user/login";

    // >>
    public static final StringBuffer sendPostDevLogin(String credential, String baseUrl) throws MalformedURLException, IOException {
        byte[] postData = credential.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String urlStr = baseUrl + JPDBUtils.LOGIN_PART_URL;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "JsonPowerDB_0_3_2");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        con.setDoOutput(true);
        try (final DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }
        int responseCode = con.getResponseCode();
        StringBuffer response;
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

    public static void printData(ArrayList<JSONObject> jsonObjArrayList) {
        for (int i = 0; i < jsonObjArrayList.size(); i++) {
            System.out.println(jsonObjArrayList.get(i));
        }
    }

    // >>
    public static final String getConnectionToken(String credential, String baseUrl) throws ParseException, IOException {
        StringBuffer sbLogin = sendPostDevLogin(credential, baseUrl);
        JSONObject loginJsonResponse = (JSONObject) new JSONParser().parse(sbLogin.toString());
        String token = (String) loginJsonResponse.get("token");
        return token;
    }

    // >>
    public static JSONObject getJsonObjForLine(String line, String[] allColsArray, int totalCols) {
        @SuppressWarnings(value = "StringBufferWithoutInitialCapacity")
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
                        resultantJsonObj.put(allColsArray[count], longVal);
                    } else {
                        resultantJsonObj.put(allColsArray[count], dblVal);
                    }
                } catch (NumberFormatException nfe) {
                    if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                        boolVal = Boolean.parseBoolean(str);
                        resultantJsonObj.put(allColsArray[count], boolVal);
                    } else {
                        resultantJsonObj.put(allColsArray[count], str);
                    }
                } finally {
                    if (count > totalCols) {
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

    public static JSONObject[] getAllJsonObjects(String[] allLinesArray, String[] allColsArray, int totalCols) {
        JSONObject[] jsonObjArray = new JSONObject[allLinesArray.length];
        for (int i = 0; i < allLinesArray.length; i++) {
            jsonObjArray[i] = JPDBUtils.getJsonObjForLine(allLinesArray[i], allColsArray,totalCols);
        }
        return jsonObjArray;
    }

    // >>
    public static String[] getColumnNameFromCSV(String inputFileName) {
        FileInputStream fstream = null;
        ArrayList<String> columnNameArrList = new ArrayList<>();
        String strLine;
        try {
            fstream = new FileInputStream(inputFileName);
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1)) {
                strLine = br.readLine();
                @SuppressWarnings(value = "StringBufferWithoutInitialCapacity")
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
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                // Ignore this exception
            }
        }
        return columnNameArrList.toArray(new String[0]);
    }

    // >>
    public static StringBuffer executeCommand(String imlRequest, String commandType, String baseUrl) throws MalformedURLException, IOException {
        String urlStr = baseUrl + commandType;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "/");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        // Send post request
        con.setDoOutput(true);

        // Reading response
        try (final DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(imlRequest);
            wr.flush();
        }
        // System.out.println("\nSending 'POST' request to URL : " + urlStr);
        // System.out.println("Post parameters : " + urlParameters);
        //        System.out.println("Response Code : " + responseCode);
        // System.out.println("Content Type : " + con.getContentType());
        //  System.out.println("Response Default Message : " + con.getResponseMessage());
        //            System.out.println("Content : " + con.getContent());
        int responseCode = con.getResponseCode();
        StringBuffer response;
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        //print result
        // System.out.println("Response\n" + response);
        return response;
    }

    // >>
    public static void deleteDatabase(String token, String dbName, String baseUrl) throws IOException {
        String delRequest = "{\n" + "\"token\" : \"" + token + "\"," + "\"cmd\" : \"REMOVE_DB\",\n" + "\"dbName\" : \"" + dbName + "\"" + "}";
        JPDBUtils.executeCommand(delRequest, JPDBUtils.IDL_PART_URL, baseUrl);
    }

    // >>
    public static StringBuffer insertJSONArray2JsonPowerDB(String token, JSONArray jsonArray, 
            String dbName, String relName, String baseUrl) 
            throws MalformedURLException, IOException {
        String putRequest = "{\n" 
                + "\"token\" : \"" 
                + token 
                + "\"," 
                + "\"dbName\": \"" 
                + dbName
                + "\",\n" + "\"cmd\" : \"PUTALL\",\n" 
                + "\"rel\" : \"" 
                + relName + "\"," 
                + "\"jsonStr\": \n" 
                + jsonArray 
                + "\n" 
                + "}";
        return JPDBUtils.executeCommand(putRequest, JPDBUtils.IML_PART_URL, baseUrl);
    }

}
