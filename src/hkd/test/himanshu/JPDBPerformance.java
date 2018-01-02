/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test.himanshu;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dev
 */
public class JPDBPerformance {

//    private static final String CON_TOKEN_CREDENTIAL = "email=himanshu.dugar@login2explore.com&password=dfdfdf";
    private static final String CON_TOKEN_CREDENTIAL = "email=nunna2000@gmail.com&password=dfdfdf";

//    private static final String BASE_URL = "http://dev1api.login2explore.com:5577";
    private static final String BASE_URL = "http://localhost:5577";
    private static final String IML_PART_URL = "/api/iml";
    private static final String IDL_PART_URL = "/api/idl";
    private static final String LOGIN_PART_URL = "/user/login";

    private static final int TOTAL_COLS = 14;
    private static final String SEARCH_COL_NAME = "LICENSE ID";
    private static final String DB_NAME = "BussLic";
    private static final String REL_NAME = "Chicago";
    private static final String FILE_PATH = "./data/cbl/csv/";
    private static final String FILE_NAME = "ChicagoBL-001k.csv";

    public static void main(String[] args) throws ParseException, IOException {

        String token = getConnectionToken();

        deleteDatabase(token);

        jpdbOp(token);
    }

    private static void jpdbOp(String token) throws ParseException, IOException {

        System.out.println("JsonPowerDB Performance for Creating Indexes, Inserting , Updating, Deleting Documents");
        System.out.println("Data file used = " + FILE_NAME);
        System.out.println("");

        ArrayList<String> columnNameArrayList = getColumnNameFromCSV(FILE_PATH + FILE_NAME);

        long totalTime = 0;
        // Creating indexes and calculating time to create indexes
        long it1 = System.currentTimeMillis();
        for (int colNo = 0; colNo < TOTAL_COLS; colNo++) //Creating indexes 
        {
        }
        long it2 = System.currentTimeMillis();
        System.out.println("Time taken for Creating Index Columns: " + (it2 - it1) + "ms");

        // Reading rows as String from file
        long dt1 = System.currentTimeMillis();
        ArrayList<String> rowsArrList = getRowsFromFile(FILE_PATH + FILE_NAME);
        long dt2 = System.currentTimeMillis();
        System.out.println("Time taken for Reading from file: " + (dt2 - dt1) + "ms");

        // Creating documents from rows read from file
        long t1 = System.currentTimeMillis();
        ArrayList<JSONObject> jsonObjsArrayList = getAllJsonObjects(rowsArrList, columnNameArrayList);
        long t2 = System.currentTimeMillis();
        System.out.println("Time taken for Making Documents: " + (t2 - t1) + "ms");

//        printData(jsonObjsArrayList);
        long ta1 = System.currentTimeMillis();
        insertDataToJSONPowerDb(token, jsonObjsArrayList);
        long ta2 = System.currentTimeMillis();
        long taDiff = ta2 - ta1;
        totalTime += taDiff;
        System.out.println("Time taken for Inserting:" + taDiff + "ms");

    }

    public static void deleteDatabase(String token) throws IOException {
        String delRequest = "{\n"
                + "\"token\" : \"" + token + "\","
                + "\"cmd\" : \"REMOVE_DB\",\n"
                + "\"dbName\" : \""
                + DB_NAME
                + "\""
                + "}";
        executeCommand(delRequest, IDL_PART_URL);
    }

    public static void insertDataToJSONPowerDb(String token, ArrayList<JSONObject> jsonArray) throws MalformedURLException, IOException {
        for (int i = 0; i < jsonArray.size(); i++) {
            insertJSONObjToJSONPowerDb(token, jsonArray.get(i));
        }
    }

    public static StringBuffer executeCommand(String imlRequest, String commandType) throws MalformedURLException, IOException {
        String urlParameters = null;
        String urlStr = BASE_URL + commandType;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "/");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");

        urlParameters = imlRequest;
        // Send post request
        con.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(urlParameters);

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

    public static StringBuffer insertJSONObjToJSONPowerDb(String token, JSONObject jsonObj) throws MalformedURLException, IOException {
        String putRequest = "{\n"
                + "\"token\" : \"" + token + "\","
                + "\"dbName\": \""
                + DB_NAME
                + "\",\n"
                + "\"cmd\" : \"PUT\",\n"
                + "\"rel\" : \""
                + REL_NAME
                + "\","
                //                + "\"templateStr\": {\n"
                //                + "\"LICENSE ID\": 1234"
                //                + "},\n"
                + "\"jsonStr\": \n"
                + jsonObj
                + "\n"
                + "}";

        return executeCommand(putRequest, IML_PART_URL);
    }

    public static ArrayList<String> getColumnNameFromCSV(String inputFileName) {
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
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                // Ignore this exception
            }
        }
        return columnNameArrList;

    }

    public static final ArrayList<String> getRowsFromFile(String inputFileName) {
        ArrayList<String> rowsArrList = new ArrayList();
        String strLine;
        FileInputStream fstream = null;
        BufferedReader br = null;
        try {
            fstream = new FileInputStream(inputFileName);

            br = new BufferedReader(new InputStreamReader(fstream), 1024 * 1024 * 1);

            boolean columnFlag = true;
            while ((strLine = br.readLine()) != null) //Reading from file
            {
                if (columnFlag) {
                    columnFlag = false;
                    continue;
                }
                rowsArrList.add(strLine);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                fstream.close();
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Ignoring Exception
            }
        }
        return rowsArrList;
    }

    public static ArrayList<JSONObject> getAllJsonObjects(ArrayList<String> allLinesArrayList, ArrayList<String> allColsArrayList) {
        ArrayList<JSONObject> jsonObjArrayList = new ArrayList<>();

        for (int i = 0; i < allLinesArrayList.size(); i++) {
            jsonObjArrayList.add(getJsonObjForLine(allLinesArrayList.get(i), allColsArrayList));
        }

        return jsonObjArrayList;
    }

    public static JSONObject getJsonObjForLine(String line, ArrayList<String> allColsArrayList) {
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
                        resultantJsonObj.put(allColsArrayList.get(count), longVal);
                    } else {
                        resultantJsonObj.put(allColsArrayList.get(count), dblVal);
                    }

                } catch (NumberFormatException nfe) {
                    if ("true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str)) {
                        boolVal = Boolean.parseBoolean(str);
                        resultantJsonObj.put(allColsArrayList.get(count), boolVal);

                    } else {
                        resultantJsonObj.put(allColsArrayList.get(count), str);
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

    public static final String getConnectionToken() throws ParseException, IOException {
        StringBuffer sbLogin = sendPostDevLogin();
        JSONObject loginJsonResponse = (JSONObject) new JSONParser().parse(sbLogin.toString());
        String token = (String) loginJsonResponse.get("token");
        return token;
    }

    public static final StringBuffer sendPostDevLogin() throws MalformedURLException, IOException {
        byte[] postData = CON_TOKEN_CREDENTIAL.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String urlStr = BASE_URL + LOGIN_PART_URL;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "JsonPowerDB_0_3_2");
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

    public static void printData(ArrayList<JSONObject> jsonObjArrayList) {
        for (int i = 0; i < jsonObjArrayList.size(); i++) {
            System.out.println(jsonObjArrayList.get(i));
        }
    }
}
