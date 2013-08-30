/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import sun.util.BuddhistCalendar;

/**
 *
 * @author java-dev
 */
public class SetCbxDateChange {
    
    JComboBox cbxYear, cbxMonth, cbxDate;

    public SetCbxDateChange(JComboBox cbxYear, JComboBox cbxMonth, JComboBox cbxDate) {
        this.cbxYear = cbxYear;
        this.cbxMonth = cbxMonth;
        this.cbxDate = cbxDate;
    }
    
    public void setYear() {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));

        //ปี
        int year = c.get(Calendar.YEAR);
        Vector<String> y = new Vector<>();

        for (int i = 0; i < 10; i++) {
            y.add(year + i + "");
        }
        cbxYear.setModel(new DefaultComboBoxModel(y));

    }

    public void setMonthByCurrYear() {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));
        
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH);
        String ySel = cbxYear.getSelectedItem().toString();
        String monthInYear[] = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};


        //เดือน หาปีที่ถูกเลือกมาก่อน
        Vector<String> m = new Vector<>();

        //ถ้าเป็นปีปัจจุบันให้เริ่มต้นจากเดือนปัจจุบัน
        if (ySel.equals(curYear + "")) {

            for (int i = curMonth; i < 12; i++) {
                m.add(monthInYear[i]);
            }

        } else { //ถ้าไม่ใช่ก็ให้วนเพิ่มเดือนจาก 1-12

            for (int i = 0; i < 12; i++) {
                m.add(monthInYear[i]);
            }

        }

        cbxMonth.setModel(new DefaultComboBoxModel(m));

    }

    public void setDateByCurrMonth() {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));
        
        int curMonth = c.get(Calendar.MONTH);
        int curDate = c.get(Calendar.DATE);
        String ySel = cbxYear.getSelectedItem().toString();
        int ySelInt = Integer.parseInt(ySel) - 543;
        String monthInYear[] = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        String mSel = cbxMonth.getSelectedItem().toString();
        int leap = ((ySelInt % 4 == 0) && ((ySelInt % 100 != 0) || (ySelInt % 400 == 0)) ? 29 : 28);
        int dayInMonth[] = {31, leap, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int index = 0;
        Vector<String> d = new Vector<>();
        //หาวันเดือนที่เลือกคือเดือนอะไรเพื่อตรวจสอบว่าเดือนนั้นมีกี่วัน
        for (int i = 0; i < monthInYear.length; i++) {

            if (mSel.equals(monthInYear[i])) {
                index = i;
                break;
            }

        }

        if (curMonth == index) { //ใช่เดือนปัจจุบันหรือเปล่าถ้าใช่เริ่มจากวันปัจจุบัน
            for (int i = curDate - 1; i < dayInMonth[index]; i++) {
                d.add(i + 1 + "");
            }
        } else { //ถ้าไม่ใช่ก็ให้วนจนครบจำนวนวัน
            for (int i = 0; i < dayInMonth[index]; i++) {
                d.add(i + 1 + "");
            }
        }

        cbxDate.setModel(new DefaultComboBoxModel(d));

    }

    //แบบเพิ่มปีย้อนหลังและอนาคตได้ตามต้องการ
    public void setYearByDemand(int pass, int future) {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));

        //ปี
        int year = c.get(Calendar.YEAR)-pass;
        Vector<String> y = new Vector<>();

        for (int i = 0; i < pass+future; i++) {
            y.add(year + i + "");
        }
        cbxYear.setModel(new DefaultComboBoxModel(y));

    }
    
    public void setMonth() {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));
        
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH);
        String ySel = cbxYear.getSelectedItem().toString();
        String monthInYear[] = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};


        //เดือน หาปีที่ถูกเลือกมาก่อน
        Vector<String> m = new Vector<>();

            for (int i = 0; i < 12; i++) {
                m.add(monthInYear[i]);
            }

        cbxMonth.setModel(new DefaultComboBoxModel(m));

    }

    public void setDate() {

        Calendar c = new BuddhistCalendar(new Locale("th", "TH"));
        
        int curMonth = c.get(Calendar.MONTH);
        int curDate = c.get(Calendar.DATE);
        String ySel = cbxYear.getSelectedItem().toString();
        int ySelInt = Integer.parseInt(ySel) - 543;
        String monthInYear[] = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        String mSel = cbxMonth.getSelectedItem().toString();
        int leap = ((ySelInt % 4 == 0) && ((ySelInt % 100 != 0) || (ySelInt % 400 == 0)) ? 29 : 28);
        int dayInMonth[] = {31, leap, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int index = 0;
        Vector<String> d = new Vector<>();
        //หาวันเดือนที่เลือกคือเดือนอะไรเพื่อตรวจสอบว่าเดือนนั้นมีกี่วัน
        for (int i = 0; i < monthInYear.length; i++) {

            if (mSel.equals(monthInYear[i])) {
                index = i;
                break;
            }

        }

            for (int i = 0; i < dayInMonth[index]; i++) {
                d.add(i + 1 + "");
            }

        cbxDate.setModel(new DefaultComboBoxModel(d));

    }
    
}
