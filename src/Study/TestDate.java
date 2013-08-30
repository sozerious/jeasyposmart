/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import sun.util.BuddhistCalendar;

/**
 *
 * @author java-dev
 */
public class TestDate {
    
    public static void main(String[] args) {
        
        Date d = new Date();
        Calendar c = new BuddhistCalendar();

        int date = d.getDate();
        int month = d.getMonth();
        int year = d.getYear();
        SimpleDateFormat sp = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
//        System.out.println(sp.format(d));
        System.out.println(date);
        System.out.println(month);
        System.out.println(year);
    }
    
}
