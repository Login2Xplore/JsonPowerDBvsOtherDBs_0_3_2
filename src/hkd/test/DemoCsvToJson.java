/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
 * @author dev
 */
public class DemoCsvToJson {

    public static void main(String[] args) throws IOException, ParseException {
        main01();

    }

    public static void main01() throws IOException, ParseException {
        String inputFileName = "/home/hemant/nbPiDemo/DataSets/City of Chicago/ChicagoBL-500k.csv";
        ArrayList<String> columnNameArrList = new ArrayList<>();
        FileInputStream fstream = null;
        String strLine;
//        String str[] = new String[500000];
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

//                columnNameArrList.add(sb.toString());
//
//                int i = 0;
                long t1 = System.currentTimeMillis();
//                while ((strLine = br.readLine()) != null && i < 500000) {
//                    JSONObject jSONObject = getJsonObjForLine(strLine, columnNameArrList);
//                    str[i++] = jSONObject.toJSONString();
//                }
                columnNameArrList.add(sb.toString());
                //   PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
                int count = 0;
                JSONArray jsonArray = new JSONArray();
                while ((strLine = br.readLine()) != null) {
                    JSONObject jSONObject = getJsonObjForLine(strLine, columnNameArrList);
                    count++;

                    jsonArray.add(jSONObject);
                    if (jsonArray.size() == 1000) {

                        StringBuffer sbLogin = sendPostDevLogin();
                        JSONObject loginJsonResponse = (JSONObject) new JSONParser().parse(sbLogin.toString());
                        String token = (String) loginJsonResponse.get("token");

                        sendDataToJsonPowerDB(token, jsonArray);
                        System.out.println("");
                        System.out.println("------------------------");
                        System.out.println("Records Written: " + count);

                        //  System.out.println(sbInsertResponse.toString());
                        jsonArray.clear();
                    }
                }
                long t2 = System.currentTimeMillis();
                System.out.println(">>>>>>>>>>>>>> Time taken = " + (t2 - t1));
            }
            //generateOutputFileInJson("/home/dev/dev/dataset/ChicagoBL1", jsonArray.toJSONString());

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
//        System.out.println("");

//        ArrayList rows = new ArrayList();
//        JSONObject jsonObj[] = new JSONObject[500000];
//        long t3 = System.currentTimeMillis();
//        for (int i = 0; i < str.length; i++) {
//            jsonObj[i] = (JSONObject) new JSONParser().parse(str[i]);
//        }
//        long t4 = System.currentTimeMillis();
//        System.out.println("Time to convert from Json String to JSONObject - " + (t4 - t3));
//        System.out.println("Number of rows found - " + rows.size());
//
//        long t5 = System.currentTimeMillis();
//        for (int i = 0; i < jsonObj.length; i++) {
//            Object value = jsonObj[i].get("LEGAL NAME");
//            if ("BERGHOFF".equals(value)) {
//                rows.add(i);
//            }
//        }
//        long t6 = System.currentTimeMillis();
//        System.out.println("Search Performance - " + (t6 - t5));

    }

    public static void main02(String[] args) throws IOException, ParseException {
        String inputFileName = "/home/hemant/nbPiDemo/DataSets/City of Chicago/ChicagoBL-500k.csv";
        String outputFile = "/home/hemant/nbPiDemo/DataSets/City of Chicago/ChicagoBL-500k.json";
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

                PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
                int count = 0;
                JSONArray jsonArray = new JSONArray();
                long t1 = System.currentTimeMillis();
                while ((strLine = br.readLine()) != null) {
//                    if (count <= 850000) {
//                        continue;
//                    }

                    JSONObject jSONObject = getJsonObjForLine(strLine, columnNameArrList);
                    count++;
                    jsonArray.add(jSONObject);
//                    pw.write(jSONObject.toJSONString() + ",");

                    if (jsonArray.size() == 10000) {

                        StringBuffer sbLogin = sendPostDevLogin();
                        JSONObject loginJsonResponse = (JSONObject) new JSONParser().parse(sbLogin.toString());
                        String token = (String) loginJsonResponse.get("token");

                        sendDataToJsonPowerDB(token, jsonArray);
                        System.out.println("");
                        System.out.println("------------------------");
                        System.out.println("Records Written: " + count);

                        //  System.out.println(sbInsertResponse.toString());
                        jsonArray.clear();
                    }

                }
                if (jsonArray.size() != 0) {

                    StringBuffer sbLogin = sendPostDevLogin();
                    JSONObject loginJsonResponse = (JSONObject) new JSONParser().parse(sbLogin.toString());
                    String token = (String) loginJsonResponse.get("token");

                    sendDataToJsonPowerDB(token, jsonArray);
                    System.out.println("");
                    System.out.println("------------------------");
                    System.out.println("Records Written: " + count);

                    //  System.out.println(sbInsertResponse.toString());
                    jsonArray.clear();
                }
                long t2 = System.currentTimeMillis();
                System.out.println(">>>>>>>>>>>>>> Time taken = " + (t2 - t1));
                pw.close();

            }
            //generateOutputFileInJson("/home/dev/dev/dataset/ChicagoBL1", jsonArray.toJSONString());

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
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

    public static ArrayList<String> getColumnNameFromCSV(String inputFileName) throws IOException {
        FileInputStream fstream = null;
        ArrayList<String> columnNameArrList = new ArrayList<>();
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
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        fstream.close();
        return columnNameArrList;

    }

    public static void generateOutputFileInJson(String outputFileName, String data) {
        try (FileWriter file = new FileWriter(outputFileName + ".json")) {
            file.write(data);
            file.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void convertCSVtoJSON(String inputFile, String outputFile) throws IOException {
        ArrayList<String> columnNameArrList = getColumnNameFromCSV(inputFile);
        FileInputStream fstream = null;
        String strLine;
        try {
            fstream = new FileInputStream(inputFile);
            try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile)); BufferedReader br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1)) {
                StringBuilder sb = new StringBuilder();
                char ch;
                int iterator = 0;
                while ((strLine = br.readLine()) != null) {
                    if (iterator == 0) {
                        iterator++;
                    }

                    JSONObject resultantJsonObj = new JSONObject();
                    int flag = 0;
                    int count = 0;
                    for (int i = 0; i < strLine.length(); i++) {
                        ch = strLine.charAt(i);
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
                                    resultantJsonObj.put(columnNameArrList.get(count), longVal);
                                } else {
                                    resultantJsonObj.put(columnNameArrList.get(count), dblVal);
                                }

                            } catch (NumberFormatException nfe) {
                                if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                                    boolVal = Boolean.parseBoolean(str);
                                    resultantJsonObj.put(columnNameArrList.get(count), boolVal);

                                } else {
                                    resultantJsonObj.put(columnNameArrList.get(count), str);
                                }
                            }

                            count++;
                            sb.setLength(0);
                        } else {
                            sb.append(ch);
                        }
                    }
                    // System.out.println(resultantJsonObj.toJSONString());
                    pw.write(resultantJsonObj.toJSONString() + ",");

                }
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public static StringBuffer sendDataToJsonPowerDB(String token, JSONArray jsonData) throws MalformedURLException, IOException {

        String putRequest = "{\n"
                + "\"token\" : \"" + token + "\","
                + "\"dbName\": \"BUSSLICS\",\n"
                + "\"cmd\" : \"PUTALL\",\n"
                + "\"rel\" : \"CHICAGO\","
                + "\"templateStr\": {\n"
                + "\"LICENSE ID\": 1234"
                + "},\n"
                + "\"jsonStr\": \n"
                + jsonData
                + "\n"
                + "}";;

        String urlParameters = null;
        String urlStr = "http://localhost:5577/api/iml";
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "/");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        urlParameters = putRequest;
        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);

            wr.flush();
        }
        int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'POST' request to URL : " + urlStr);
        // System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        // System.out.println("Content Type : " + con.getContentType());
        //  System.out.println("Response Default Message : " + con.getResponseMessage());
//            System.out.println("Content : " + con.getContent());

        StringBuffer response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
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

    public static StringBuffer sendPostDevLogin() throws MalformedURLException, IOException {

        String connectionTokenStr;
        connectionTokenStr = "email=nunna2000@gmail.com&password=dfdfdf";
        byte[] postData = connectionTokenStr.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String urlStr = "http://localhost:5577/user/login";
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "/");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        con.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }
        int responseCode = con.getResponseCode();
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

}
