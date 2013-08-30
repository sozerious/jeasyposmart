/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.MainHub;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import View.FormMain;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JAVA DEV
 */
//ควบคุมการ Login เข้าสู่ระบบ
public class UserLoginControl implements ControlInterface{
    
    private String userName, userPassword, userType;

    public UserLoginControl() {
    }
    
    public UserLoginControl(String userName, String userPassword, String userType) {
        
        this.userName = userName;
        this.userPassword = userPassword;
        this.userType = userType;
        
    }

    public void checkLogin() {

//        String userName = LoginHub.login.getTxtUsername().getText();
//        String userPassword = LoginHub.login.getTxtPassword().getText();
//        String userType = LoginHub.login.getCbxUserType().getSelectedItem().toString();

        String sql = "SELECT * FROM tbuser WHERE userName='" + userName + "' AND userPassword='" + userPassword + "' AND "
                + "userType='" + userType + "'";

        if (Util.getRowCount(sql) == 1) { //ถ้า user, password และ type ถูกต้อง
            try {
                
                //แจ้งข้อความเข้าสู่ระบบสำเร็จ
                Message.showInfoMessage("เข้าสู่ระบบสำเร็จ", "ยินดีต้อนรับคุณ : " + userName);
                new FormMain().setVisible(true); //สร้างฟอร์มเพื่อสามารถกำหนด property ให้กับ user ได้
                
                ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

                while (rs.next()) {

                    FormMain.user.setUserId(rs.getInt(1));
                    FormMain.user.setEmployeeId(rs.getInt(2));
                    FormMain.user.setUserName(rs.getString(3));
                    FormMain.user.setUserPassword(rs.getString(4));
                    FormMain.user.setUserType(rs.getString(5));
                }
                
                MainHub.main.setEnvLogin();

            } catch (SQLException ex) {
                Logger.getLogger(UserLoginControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Message.showErrorMessage("การเข้าสู่ระบบล้มเหลว", "ชื่อผู้ใช้ รหัสผ่าน หรือประเภทผู้ใช้ไม่ถูกต้อง");
        }
    }

    @Override
    public int[] dataLimit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String dataSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putDataToVector(boolean search) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showData(boolean update) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
