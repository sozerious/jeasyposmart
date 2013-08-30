/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.ini4j.Ini;

/**
 *
 * @author JAVA DEV
 */
public class DBFactory {

    private static String db_Name = "";
    private static String db_User = "";
    private static String db_Pass = "";
    
    private static void readDatabaseValue() {

        try {

            //อ่านค่าการเชื่อมต่อฐานข้อมูลจากไฟล์
            Ini ini = new Ini(new File("./src/Utility/DBConnect.ini"));
            db_Name = ini.get("Database", "db_Name");
            db_User = ini.get("Database", "db_User");
            db_Pass = ini.get("Database", "db_Pass");

        } catch (IOException ex) {
            Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void writeDatabaseValue() {

        try {

            //บันทึกค่าการเชื่อมต่อฐานข้อมูลลงไฟล์
            Ini ini = new Ini(new File("./src/Utility/DBConnect.ini"));
            ini.put("Database", "db_Name", db_Name);
            ini.put("Database", "db_User", db_User);
            ini.put("Database", "db_Pass", db_Pass);
            ini.store();
            
        } catch (IOException ex) {
            Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean checkConnection() { //มีหน้าที่เช็คว่าผ่านหรือไม่ผ่านเท่านั้น

        Connection con = null;

        //ถ้าว่างหมายถึงตอนเปิดโปรแกรมตัวแปรยังเป็นค่าว่าง แต่ถ้ามีการอ่านมากำหนดแล้วตัวค่าของตัวแปร static จะอยู่จนกว่าโปรแกรมจะปิดตัวลงจึงไม่ต้องมาอ่านใหม่
        //แต่เราก็เรียกเช็คครั้งเดียวตอนเปิดโปรแกรมนี่หว่า!! จากนั้นก็เรียก getConnect อย่างเดียว
        
            readDatabaseValue(); //อ่านเลยไม่ต้อง if empty แล้ว

        try { //อ่านค่าแล้ว ลองเอามาเชื่อมต่อดู

            Class.forName(com.mysql.jdbc.Driver.class.getName());
            String url = "jdbc:mysql://localhost/" + db_Name + "?useUnicode=true&characterEncoding=UTF-8";
            con = DriverManager.getConnection(url, db_User, db_Pass);

        } catch (ClassNotFoundException | SQLException ex) {

            //ถ้าอ่านมาแล้วยังไม่ได้ ให้มาทำที่นี่
            Message.showErrorMessage("ไม่สามารถเชื่อมต่อฐานข้อมูล", "การเชื่อมต่อล้มเหลว");
            
            do { //ถามจากผู้ใช้แล้วลองเชื่อมต่อก่อน หากยังไม่ได้ให้ถามเรื่อยๆ จนกว่าจะได้หรือผู้ใช้กดยกเลิก
                
                //ถ้ายังเป็นค่าว่างก็ให้ถามไปเรื่อยๆ
                
                do {
                    JTextField text = new JTextField();
                    int action = JOptionPane.showConfirmDialog(null, text,"ชื่อฐานข้อมูล",JOptionPane.OK_CANCEL_OPTION);
                    if (action < 0) { //ถ้าน้อยกว่า 0 หมายถึงกดปุ่ม x ออกไป ให้ return false ออกไปเลย
                        return false;
                    }
                    db_Name = text.getText();
                }while(db_Name.isEmpty());
                do {
                    JTextField text = new JTextField();
                    int action = JOptionPane.showConfirmDialog(null, text,"ชื่อผู้ใช้(ฐานข้อมูล)",JOptionPane.OK_CANCEL_OPTION);
                    if (action < 0) {
                        return false;
                    }
                    db_User = text.getText(); 
                }while(db_User.isEmpty());
                do {
                   JPasswordField pass = new JPasswordField();
                    int action = JOptionPane.showConfirmDialog(null, pass,"รหัสผ่าน(ฐานข้อมูล)",JOptionPane.OK_CANCEL_OPTION);
                    if (action < 0) {
                        return false;
                    }
                    db_Pass = pass.getText();
                }while(db_Pass.isEmpty());
                
                
                try {
                    Class.forName(com.mysql.jdbc.Driver.class.getName());
                    String url = "jdbc:mysql://localhost/" + db_Name + "?useUnicode=true&characterEncoding=UTF-8";
                    con = DriverManager.getConnection(url, db_User, db_Pass);
                } catch (SQLException | ClassNotFoundException ex1) {
//                    JOptionPane.showMessageDialog(null, "ไม่สามารถเชื่อมต่อฐานข้อมูล", "การเชื่อมต่อล้มเหลว", JOptionPane.ERROR_MESSAGE);
                    int need = JOptionPane.showConfirmDialog(null, "ไม่สามารถเชื่อมต่อฐานข้อมูล ต้องการลองใหม่", "การเชื่อมต่อล้มเหลว", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (need == 1) { //ถ้ากดยกเลิก
                        return false; //ออกจากโปรแกรมทันที
                    }
                    continue; //ถ้าเป็น 0 คือกดตกลงให้ continue ไปที่ do ใหม่
                }
            } while (con == null); //ทำจนกว่า con จะไม่ใช่ค่า null

            JOptionPane.showMessageDialog(null, "เชื่อมต่อฐานข้อมูลเรียบร้อยแล้ว", "การเชื่อมต่อสำเร็จ", JOptionPane.INFORMATION_MESSAGE);
            writeDatabaseValue(); //ถ้าแสดงข้อความแปลว่าเชื่อมต่อได้แล้ว ให้เก็บค่าการเชื่อมต่อไว้ในไฟล์ ini ได้เลย
            return true;
        }
        return true; //ถ้าสำเร็จ return true
    }

    public static Connection getConnection() { //คืนค่าการเชื่อมต่อตามปกติ
        try {
            Class.forName(com.mysql.jdbc.Driver.class.getName());
            String url = "jdbc:mysql://localhost/" + db_Name + "?useUnicode=true&characterEncoding=UTF-8";
            return DriverManager.getConnection(url, db_User, db_Pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
