/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hkd.test;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 *
 * @author hemant
 */
public class Test01 {

    public static void main(String[] args) {
        test06();
//        System.out.println(getQualifiedColumnName("a|b|c.a.b", "id"));

//        test02();
//        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
    }

    public static void test06() {

        //get Calendar instance
        Calendar now = Calendar.getInstance();

        //get current TimeZone using getTimeZone method of Calendar class
        TimeZone timeZone = now.getTimeZone();
        
//        timeZone.
        //display current TimeZone using getDisplayName() method of TimeZone class
        System.out.println("Current TimeZone is : " + timeZone.getDisplayName());
        System.out.println("Current TimeZone is : " + timeZone.getID());
    }

    static void test03() {
        TreeMap<String, String> tm = new TreeMap();
        tm.put("1|db|r1", "abc");
        tm.put("1|db|r2", "abc");
        tm.put("1|db|r13", "abc");
        tm.put("1|db|r14", "abc");
        tm.put("2|db|r1", "abc");
        tm.put("2|db2|r2", "abc");
        tm.put("2|db|r13", "abc");
        tm.put("2|db2|r14", "abc");

        Map<String, String> stm = tm.subMap("2|db2|", "2|db2|" + Character.MAX_VALUE);
        String[] strArr = stm.keySet().toArray(new String[0]);
        System.out.println(stm.size());
    }

    public static String getQualifiedColumnName(String fullSubRelName, String colName) {
        int i1 = fullSubRelName.indexOf("|");
        int i2 = fullSubRelName.indexOf("|", i1 + 1);
        int i3 = fullSubRelName.indexOf(".", i2 + 1);
        return i3 == -1 ? colName : fullSubRelName.substring(i3 + 1) + "." + colName;
    }

    static void test01() {
        System.out.println("Hello World");
        long t1 = System.currentTimeMillis();
        long pr;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 100000000; j++) {
                pr = j * 179;
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
    }

    static void test02() {
        int a = 1;
        int b = -1;
        System.out.println(~a + 1);
        System.out.println(~b + 1);
        System.out.println(a * -1);
        System.out.println(b * -1);

    }

    private static void test04() {
        System.out.println(Integer.MAX_VALUE);
    }

    private static void test05() {
        String str = "abcd ; aaa . bbb";
        int i = str.indexOf(".", str.indexOf("-") + 1);
        System.out.println(i);
    }

}
