/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.text.NumberFormat;
import sun.security.krb5.internal.LastReq;

/**
 *
 * @author java-dev
 */
public class TestIndexOf {

    public static void main(String[] args) {

//        String a = "1234.56";
//        
//        String b = a.substring(0, a.indexOf("."));
//        String c = a.substring(a.indexOf(".")+1);
//        
//        System.out.println(b);
//        System.out.println(c);

//        NumberFormat nf = NumberFormat.getNumberInstance();
//        
//        String a = "1234";
//        long c = Long.parseLong(a);
//        String b = nf.format(a);
//        
//        System.out.println(b);

//        String a = "123  456";
//        String b = a.substring(0, a.indexOf(" "));
//        String c = a.substring(a.indexOf(" ") +2);
//        
//        System.out.println(b);
//        System.out.println(c);

//        String a = "1234 + 456 + 789++0";
//        String b = "a+b";
//        System.out.println(findTextNum(a, "+"));
        String test = "sss ssss ass sss";
        String arr[] = test.split(" ");
        for(String a : arr) {
            System.out.println(a);
        }
        
    }

    static int findTextNum(String text, String word) {

        int num = 0;
        int index = -1;

        while (text.indexOf(word, index + 1) != -1) {

            index = text.indexOf(word, index + 1);
            System.out.println("ตัวที่ " + (index+1));
            num += 1;
        }
        System.out.println("");
        return num;
    }
}
