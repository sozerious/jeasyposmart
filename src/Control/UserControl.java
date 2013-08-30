/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.MainHub;
import Model.Hub.UserHub;
import Model.UserModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.clearRow;
import static Utility.Util.wordFillter;
import View.FormMain;
import View.FormUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author java-dev
 */
public class UserControl implements ControlInterface {

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(UserHub.user.getTxtUserPage().getText());
        int perPage = Integer.parseInt(UserHub.user.getCbxUserPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(UserHub.user.getCbxUserPerPage(), FormUser.vectorUser.size()) == 1) {
                step[0] = 0;
                step[1] = FormUser.vectorUser.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(UserHub.user.getCbxUserPerPage(), FormUser.vectorUser.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(UserHub.user.getCbxUserPerPage(), FormUser.vectorUser.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormUser.vectorUser.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = UserHub.user.getCbxUserSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY a.userId ASC";
                break;
            case 1:
                sql += " ORDER BY b.employeeFirstname ASC";
                break;
            case 2:
                sql += " ORDER BY b.employeeLastname ASC";
                break;
            case 3:
                sql += " ORDER BY a.userName ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT a.userId, b.employeeFirstname, b.employeeLastname, a.userName, a.userPassword, a.userType "
                + "FROM tbuser a INNER JOIN tbemployee b ON a.employeeId = b.employeeId";

        if (FormMain.user.getUserType().equals("Admin")) {

            sql += " WHERE (a.userType != 'Master' AND a.userType != 'Admin') OR (a.userType ='Admin' AND a.userName = '" + FormMain.user.getUserName() + "')";

        }

        if (search) {

            switch (FormMain.user.getUserType()) {
                case "Master":
                    sql += " WHERE ";
                    break;
                case "Admin":
                    sql += " AND ";
                    break;
            }

            int cbxSearch = UserHub.user.getCbxUserSearchFrom().getSelectedIndex();
            String word = UserHub.user.getTxtUserSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += "b.employeeFirstname LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += "b.employeeLastname LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 2:
                    sql += "a.userName LIKE '%" + wordFillter(word) + "%'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormUser.vectorUser = new Vector();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt(1));
                user.setEmployeeFirstname(rs.getString(2));
                user.setEmployeeLastname(rs.getString(3));
                user.setUserName(rs.getString(4));
                user.setUserPassword(rs.getString(5));
                user.setUserType(rs.getString(6));
                FormUser.vectorUser.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!UserHub.user.getTxtUserSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("user");

        int row = 0;
        int sortFrom = UserHub.user.getCbxUserSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    UserModel user = (UserModel) FormUser.vectorUser.elementAt(i);

                    UserHub.user.getModelUser().addRow(new Object[0]);
                    UserHub.user.getModelUser().setValueAt(user.getUserId(), row, 0);
                    UserHub.user.getModelUser().setValueAt(user.getEmployeeFirstname(), row, 1);
                    UserHub.user.getModelUser().setValueAt(user.getEmployeeLastname(), row, 2);
                    UserHub.user.getModelUser().setValueAt(user.getUserName(), row, 3);
                    UserHub.user.getModelUser().setValueAt(user.getUserPassword(), row, 4);
                    UserHub.user.getModelUser().setValueAt(user.getUserType(), row, 5);

                    row++;
                }
                break;

            case 1:
                FormUser.vectorUserInverse = new Vector();

                for (int i = FormUser.vectorUser.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormUser.vectorUserInverse.add(FormUser.vectorUser.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    UserModel user = (UserModel) FormUser.vectorUserInverse.elementAt(i);

                    UserHub.user.getModelUser().addRow(new Object[0]);
                    UserHub.user.getModelUser().setValueAt(user.getUserId(), row, 0);
                    UserHub.user.getModelUser().setValueAt(user.getEmployeeFirstname(), row, 1);
                    UserHub.user.getModelUser().setValueAt(user.getEmployeeLastname(), row, 2);
                    UserHub.user.getModelUser().setValueAt(user.getUserName(), row, 3);
                    UserHub.user.getModelUser().setValueAt(user.getUserPassword(), row, 4);
                    UserHub.user.getModelUser().setValueAt(user.getUserType(), row, 5);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, UserHub.user.getTxtUserPage(), UserHub.user.getCbxUserPerPage(), FormUser.vectorUser.size());
        Util.controlPreNext(UserHub.user.getTxtUserPage(), UserHub.user.getBtnUserPre(), UserHub.user.getBtnUserNext(), UserHub.user.getCbxUserPerPage(), FormUser.vectorUser.size());

    }

    @Override
    public void insertData() {

        if (Util.checkEmpty(this)) {

            if (UserHub.user.getCbxEmployeeName().getSelectedIndex() == 0) {

                Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกพนักงานจากรายชื่อพนักงาน");
                return;
            }

            String fullName = UserHub.user.getCbxEmployeeName().getSelectedItem().toString();
            String firstName = fullName.substring(0, fullName.indexOf(" "));
            String lastName = fullName.substring(fullName.indexOf(" ") + 2);

            String userName = UserHub.user.getTxtUsername().getText();
            String userPassword = UserHub.user.getTxtPassword().getText();
            String userType = (UserHub.user.getRdbAdminType().isSelected() ? "Admin" : "User");

            String sql = "INSERT INTO tbuser VALUES(null,(SELECT employeeId FROM tbemployee WHERE employeeFirstname = '" + firstName + "' AND "
                    + "employeeLastname = '" + lastName + "'),'" + userName + "','" + userPassword + "','" + userType + "')";

            String sqlCheckUser = "SELECT userName FROM tbuser WHERE userName = '" + userName + "'";

            if (Util.getRowCount(sqlCheckUser) > 0) {
                Message.showErrorMessage("พบข้อผิดพลาด", "มีชื่อผู้ใช้นี้อยู่ในระบบแล้ว");
                return;
            }

            if (UserHub.user.getLblCheckPassword().getText().equals("รหัสผ่านไม่ตรงกัน")) {
                Message.showWarningMessage("พบข้อผิดพลาด", "โปรดใส่รหัสผ่านให้ตรงกัน");
                return;
            }

            try {

                int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                if (result != -1) {

                    String employeeId = Util.getEmployeeId(firstName, lastName);

                    sql = "UPDATE tbemployee SET userActivated = '1' WHERE employeeId = '" + employeeId + "'";

                    DBFactory.getConnection().createStatement().executeUpdate(sql);

                    Message.showInfoMessage("การบันทึกสำเร็จ", "เพิ่มผู้ใช้แล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถเพิ่มผู้ใช้");
                }

            } catch (SQLException ex) {
                Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void updateData() {

        int rowSel = UserHub.user.getTbUser().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        if (Util.checkEmpty(this)) {

            String userId = UserHub.user.getTbUser().getValueAt(rowSel, 0).toString();
            String userNameOld = UserHub.user.getTbUser().getValueAt(rowSel, 3).toString();
            String userName = UserHub.user.getTxtUsername().getText();
            String userPassword = UserHub.user.getTxtPassword().getText();
            String userType = (UserHub.user.getRdbAdminType().isSelected() ? "Admin" : "User");

            if (!userNameOld.equals(userName)) { //ถ้าชื่อผู้ใช้ใหม่ไม่ตรงกันชื่อเดิม ให้ทำการค้นหาว่าซ้ำหรือไม่ แต่ถ้าเป็นชื่อเดิมไม่ต้องค้นหาป้องกันการแก้พาสเวิร์ดไม่ได้เพราะติดชื่อซ้ำ

                String sqlCheckUser = "SELECT userName FROM tbuser WHERE userName = '" + userName + "'";

                if (Util.getRowCount(sqlCheckUser) > 0) {
                    Message.showErrorMessage("พบข้อผิดพลาด", "มีชื่อผู้ใช้นี้อยู่ในระบบแล้ว");
                    return;
                }

            }

            if (UserHub.user.getLblCheckPassword().getText().equals("รหัสผ่านไม่ตรงกัน")) {
                Message.showWarningMessage("พบข้อผิดพลาด", "โปรดใส่รหัสผ่านให้ตรงกัน");
                return;
            }

            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                String sql = "UPDATE tbuser SET userName = '" + userName + "', userPassword = '" + userPassword + "', userType = '" + userType + "' WHERE userId = '" + userId + "'";

                if (FormMain.user.getUserType().equals("Admin")) {
                    sql = "UPDATE tbuser SET userName = '" + userName + "', userPassword = '" + userPassword + "' WHERE userId = '" + userId + "'";
                }

                try {
                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        Message.showInfoMessage("การแก้ไขสำเร็จ", "แก้ไขผู้ใช้แล้ว");
                        showData(true);
                        Util.clearText(this);
                    } else {
                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขผู้ใช้");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } //ถ้าต้องการแก้ไข

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void deleteData() {

        int rowSel = UserHub.user.getTbUser().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการลบ");
            return;
        }

        if (Message.showMessageDelete2Btn(this) == 0) { //ถ้าต้องการลบ

            String userId = UserHub.user.getTbUser().getValueAt(rowSel, 0).toString();

            String sql = "DELETE FROM tbuser WHERE userId = '" + userId + "'";

            try {

                int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                if (result != -1) {

                    //ปรับ userActivated ใน employee เป็น 0
                    String firstName = UserHub.user.getTbUser().getValueAt(rowSel, 1).toString();
                    String lastName = UserHub.user.getTbUser().getValueAt(rowSel, 2).toString();

                    String employeeId = Util.getEmployeeId(firstName, lastName);

                    sql = "UPDATE tbemployee SET userActivated = '0' WHERE employeeId = '" + employeeId + "'";

                    DBFactory.getConnection().createStatement().executeUpdate(sql);

                    Message.showInfoMessage("การลบสำเร็จ", "ลบผู้ใช้แล้ว");
                    showData(true);
                    Util.clearText(this);

                    if (FormMain.user.getUserId() == Integer.parseInt(userId)) {
                        Message.showErrorMessage("คำเตือนจากระบบ", "กรุณาเข้าสู่ระบบอีกครั้ง เนื่องจากข้อมูลผู้ใช้ระบบในขณะนี้ถูกยกเลิก");
                        MainHub.main.setEnvLogout();
                    }

                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถลบผู้ใช้");
                }

            } catch (SQLException ex) {
                Logger.getLogger(UserControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //ถ้าต้องการลบ

    }
}
