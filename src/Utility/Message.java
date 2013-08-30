/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Control.ControlInterface;
import Control.EmployeeControl;
import Control.UserControl;
import javax.swing.JOptionPane;

/**
 *
 * @author SoSerious
 */
public class Message {

    //ข้อความแสดงผลลัพธ์
    public static void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showInfoMessage(String title, String content) {

        JOptionPane.showMessageDialog(null, content, title, JOptionPane.INFORMATION_MESSAGE);

    }

    public static void showWarningMessage(String title, String content) {

        JOptionPane.showMessageDialog(null, content, title, JOptionPane.WARNING_MESSAGE);

    }

    public static void showErrorMessage(String title, String content) {

        JOptionPane.showMessageDialog(null, content, title, JOptionPane.ERROR_MESSAGE);

    }

    public static int showConfirmMessage(String title, String content) {

        return JOptionPane.showOptionDialog(null, content, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"ตกลง", "ยกเลิก"}, JOptionPane.NO_OPTION);

    }

    public static void showMessageNotSelect() {
        JOptionPane.showMessageDialog(null, "ยังไม่มีรายการที่เลือก", "พบข้อผิดพลาด", JOptionPane.WARNING_MESSAGE);
    }

    public static void showMessageEmpty() {
        JOptionPane.showMessageDialog(null, "กรอกข้อมูลให้ครบถ้วน", "พบข้อผิดพลาด", JOptionPane.WARNING_MESSAGE);
    }

    public static void showMessageInsert() {
        JOptionPane.showMessageDialog(null, "บันทึกรายการแล้ว", "ผลการทำงาน", JOptionPane.INFORMATION_MESSAGE);
    }

    // กล่องข้อความถามการเพิ่ม 2 ปุ่ม
    public static int showMessageInsert2Btn() {
        return JOptionPane.showOptionDialog(null, "ต้องการบันทึกรายการ?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"บันทึก", "ยกเลิก"}, null);
    }

    public static void showMessageUpdate() {
        JOptionPane.showMessageDialog(null, "แก้ไขรายการแล้ว", "ผลการทำงาน", JOptionPane.INFORMATION_MESSAGE);
    }

    // กล่องข้อความถามการแก้ไข 2 ปุ่ม
    public static int showMessageUpdate2Btn() {
        return JOptionPane.showOptionDialog(null, "ต้องการแก้ไขรายการที่เลือก?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"แก้ไข", "ยกเลิก"}, null);
    }

    // กล่องข้อความถามการแก้ไข 2 ปุ่ม
    public static int showMessageUpdate2Btn(String type) {

        switch(type) {
            case "cancelwarning" :
                return JOptionPane.showOptionDialog(null, "ต้องการยกเลิกการเตือนรายการที่เลือก?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"ตกลง", "ยกเลิก"}, null);
            case "cancelwarningall" :
                return JOptionPane.showOptionDialog(null, "ต้องการยกเลิกการเตือนรายการทั้งหมด?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"ตกลง", "ยกเลิก"}, null);
            case "warningall" :
                return JOptionPane.showOptionDialog(null, "ต้องการเปิดการเตือนรายการทั้งหมด?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"ตกลง", "ยกเลิก"}, null);

        }
        
        return -1;
    }
    
    public static void showMessageDelete() {
        JOptionPane.showMessageDialog(null, "ลบรายการแล้ว", "ผลการทำงาน", JOptionPane.INFORMATION_MESSAGE);
    }

    // กล่องข้อความถามการลบ 2 ปุ่ม
    public static int showMessageDelete2Btn() {
        return JOptionPane.showOptionDialog(null, "ต้องการลบรายการที่เลือก?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"ลบ", "ยกเลิก"}, null);
    }

    // กล่องข้อความถามการลบ 2 ปุ่ม
    public static int showMessageDelete2Btn(ControlInterface control) {

        if (control instanceof UserControl) {
            return JOptionPane.showOptionDialog(null, "ต้องการลบผู้ใช้? การลบอาจมีผลต่อระบบ ไม่ควรลบขณะทำรายการขาย", "สอบถามความต้องการ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    new Object[]{"ลบ", "ยกเลิก"}, null);
        } else if (control instanceof EmployeeControl) {
            return JOptionPane.showOptionDialog(null, "ต้องการลบพนักงาน? การลบอาจมีผลต่อระบบเนื่องจากชื่อผู้ใช้ที่เกี่ยวเนื่องจะถูกลบไปด้วย ไม่ควรลบขณะทำรายการขาย", "สอบถามความต้องการ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                    new Object[]{"ลบ", "ยกเลิก"}, null);
        }

        return -1;
    }

    // กล่องข้อความถามการแก้ไข 2 ปุ่มแบบหลายรายการ
    public static int showMessageDelete2BtnManyList(int rowSelLenght) {
        return JOptionPane.showOptionDialog(null, "ต้องการลบ " + rowSelLenght + " รายการที่เลือก?", "สอบถามความต้องการ",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[]{"ลบ", "ยกเลิก"}, null);
    }
}
