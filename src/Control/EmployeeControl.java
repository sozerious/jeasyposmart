/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.EmployeeModel;
import Model.Hub.MainHub;
import Model.Hub.UserHub;
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
public class EmployeeControl implements ControlInterface {

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(UserHub.user.getTxtEmployeePage().getText());
        int perPage = Integer.parseInt(UserHub.user.getCbxEmployeePerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(UserHub.user.getCbxEmployeePerPage(), FormUser.vectorEmployee.size()) == 1) {
                step[0] = 0;
                step[1] = FormUser.vectorEmployee.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(UserHub.user.getCbxEmployeePerPage(), FormUser.vectorEmployee.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(UserHub.user.getCbxEmployeePerPage(), FormUser.vectorEmployee.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormUser.vectorEmployee.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = UserHub.user.getCbxEmployeeSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY a.employeeId ASC";
                break;
            case 1:
                sql += " ORDER BY a.employeeFirstname ASC";
                break;
            case 2:
                sql += " ORDER BY a.employeeLastname ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT a.employeeId, a.employeeNamePrefix, a.employeeFirstname, a.employeeLastname, a.employeePosition, a.employeeIdCardNumber, "
                + "a.employeeAddress, a.employeeTel, a.userActivated, b.userName, b.userType FROM tbemployee a LEFT JOIN tbuser b ON a.employeeId = b.employeeId";

        if (FormMain.user.getUserType().equals("Admin")) {

            sql += " WHERE (b.userType != 'Master' AND b.userType != 'Admin') OR (b.userType = 'Admin' AND b.userName = '" + FormMain.user.getUserName() + "') OR (a.userActivated=0)";

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

            int cbxSearch = UserHub.user.getCbxEmployeeSearchFrom().getSelectedIndex();
            String word = UserHub.user.getTxtEmployeeSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += "a.employeeFirstname LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += "a.employeeLastname LIKE '%" + wordFillter(word) + "%'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormUser.vectorEmployee = new Vector();
            while (rs.next()) {
                EmployeeModel employee = new EmployeeModel();
                employee.setEmployeeId(rs.getInt(1));
                switch (rs.getString(2)) {
                    case "Mr.":
                        employee.setEmployeeNamePrefix("นาย");
                        break;
                    case "Mrs.":
                        employee.setEmployeeNamePrefix("นาง");
                        break;
                    default:
                        employee.setEmployeeNamePrefix("นางสาว");
                        break;
                }

                employee.setEmployeeFirstname(rs.getString(3));
                employee.setEmployeeLastname(rs.getString(4));
                employee.setEmployeePosition(rs.getString(5).equals("Manager") ? "ผู้จัดการ" : "พนักงานขาย");
                employee.setEmployeeIdCardNumber(rs.getString(6));
                employee.setEmployeeAddress(rs.getString(7));
                employee.setEmployeeTel(rs.getString(8));
                FormUser.vectorEmployee.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!UserHub.user.getTxtEmployeeSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("employee");

        int row = 0;
        int sortFrom = UserHub.user.getCbxEmployeeSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    EmployeeModel employee = (EmployeeModel) FormUser.vectorEmployee.elementAt(i);

                    UserHub.user.getModelEmployee().addRow(new Object[0]);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeId(), row, 0);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeNamePrefix(), row, 1);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeFirstname(), row, 2);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeLastname(), row, 3);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeePosition(), row, 4);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeIdCardNumber(), row, 5);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeAddress(), row, 6);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeTel(), row, 7);

                    row++;
                }
                break;

            case 1:
                FormUser.vectorEmployeeInverse = new Vector();

                for (int i = FormUser.vectorEmployee.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormUser.vectorEmployeeInverse.add(FormUser.vectorEmployee.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    EmployeeModel employee = (EmployeeModel) FormUser.vectorEmployeeInverse.elementAt(i);

                    UserHub.user.getModelEmployee().addRow(new Object[0]);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeId(), row, 0);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeNamePrefix(), row, 1);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeFirstname(), row, 2);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeLastname(), row, 3);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeePosition(), row, 4);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeIdCardNumber(), row, 5);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeAddress(), row, 6);
                    UserHub.user.getModelEmployee().setValueAt(employee.getEmployeeTel(), row, 7);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, UserHub.user.getTxtEmployeePage(), UserHub.user.getCbxEmployeePerPage(), FormUser.vectorEmployee.size());
        Util.controlPreNext(UserHub.user.getTxtEmployeePage(), UserHub.user.getBtnEmployeePre(), UserHub.user.getBtnEmployeeNext(), UserHub.user.getCbxEmployeePerPage(), FormUser.vectorEmployee.size());

    }

    @Override
    public void insertData() {

        if (Util.checkEmpty(this)) {

            String employeeNamePrefix;
            switch (UserHub.user.getCbxEmployeePrefix().getSelectedItem().toString()) {
                case "นาย":
                    employeeNamePrefix = "Mr.";
                    break;
                case "นาง":
                    employeeNamePrefix = "Mrs.";
                    break;
                default:
                    employeeNamePrefix = "Miss";
                    break;
            }
            String employeeFirstname = UserHub.user.getTxtEmployeeFirstname().getText();
            String employeeLastname = UserHub.user.getTxtEmployeeLastname().getText();
            String employeePosition = (UserHub.user.getCbxEmployeePosition().getSelectedItem().toString().equals("ผู้จัดการ")? "Manager" : "Employee");
            String employeeIdCardNumber = UserHub.user.getTxtEmployeeIdCardNumber().getText();
            String employeeAddress = UserHub.user.getTxtEmployeeAddress().getText();
            String employeetel = UserHub.user.getTxtEmployeeTel().getText();

            String sql = "INSERT INTO tbemployee VALUES(null,'" + employeeNamePrefix + "','" + employeeFirstname + "','" + employeeLastname + "','" + employeePosition + "',"
                    + "'" + employeeIdCardNumber + "','" + employeeAddress + "','" + employeetel + "','0')";

            String sqlCheckEmployee = "SELECT employeeId FROM tbemployee WHERE employeeFirstname = '" + employeeFirstname + "' AND employeeLastname = '" + employeeLastname + "'";

            //ตรวจชื่อ นามสกุลซ้ำ
            if (Util.getRowCount(sqlCheckEmployee) > 0) {
                Message.showErrorMessage("พบข้อผิดพลาด", "มีข้อมูลพนักงานคนนี้อยู่ในระบบแล้ว");
                return;
            }

            //ตรวจสอบเลขบัตร ที่อยู่ เบอร์โทรถ้าไม่ว่างก็ต้องไม่น้อยกว่าที่กำหนด
            //เลขบัตรประชาชนถ้าไม่ว่างและน้อยกว่า 13 หลักให้เตือน
            if (!UserHub.user.getTxtEmployeeIdCardNumber().getText().isEmpty() && UserHub.user.getTxtEmployeeIdCardNumber().getText().length() < 13) {
                Message.showWarningMessage("พบข้อผิดพลาด", "เลขบัตรประชาชนต้องไม่น้อยกว่า 13 หลัก");
                return;
            }

            //ถ้าไม่ว่าง ตรวจดูว่าซ้ำหรือเปล่า
            if (!UserHub.user.getTxtEmployeeIdCardNumber().getText().isEmpty()) {

                //ตรวจเลขบัตรซ้ำด้วย
                sqlCheckEmployee = "SELECT employeeIdCardNumber FROM tbemployee WHERE employeeIdCardNumber = '" + employeeIdCardNumber + "'";
                if (Util.getRowCount(sqlCheckEmployee) > 0) {
                    Message.showErrorMessage("พบข้อผิดพลาด", "รหัสบัตรประชาชนนี้ถูกใช้แล้ว");
                    return;
                }

            }


            //เบอร์โทรศัพท์ต้องไม่น้อยกว่า 10 หลัก
            if (!UserHub.user.getTxtEmployeeTel().getText().isEmpty() && UserHub.user.getTxtEmployeeTel().getText().length() < 10) {
                Message.showWarningMessage("พบข้อผิดพลาด", "เบอร์โทรศัพท์ต้องไม่น้อยกว่า 10 หลัก");
                return;
            }

            try {

                int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                if (result != -1) {

                    Message.showInfoMessage("การบันทึกสำเร็จ", "เพิ่มข้อมูลพนักงานแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถเพิ่มข้อมูลพนักงาน");
                }

            } catch (SQLException ex) {
                Logger.getLogger(EmployeeControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void updateData() {

        int rowSel = UserHub.user.getTbEmployee().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        if (Util.checkEmpty(this)) {

            String employeeFirstnameOld = UserHub.user.getTbEmployee().getValueAt(rowSel, 2).toString();
            String employeeLastnameOld = UserHub.user.getTbEmployee().getValueAt(rowSel, 3).toString();
            String emploYeeIdCardNumberOld = UserHub.user.getTbEmployee().getValueAt(rowSel, 5).toString();

            String employeeId = UserHub.user.getTbEmployee().getValueAt(rowSel, 0).toString();
            String employeeNamePrefix;
            switch (UserHub.user.getCbxEmployeePrefix().getSelectedItem().toString()) {
                case "นาย":
                    employeeNamePrefix = "Mr.";
                    break;
                case "นาง":
                    employeeNamePrefix = "Mrs.";
                    break;
                default:
                    employeeNamePrefix = "Miss";
                    break;
            }
            String employeeFirstname = UserHub.user.getTxtEmployeeFirstname().getText();
            String employeeLastname = UserHub.user.getTxtEmployeeLastname().getText();
            String employeePosition = (UserHub.user.getCbxEmployeePosition().getSelectedItem().toString().equals("ผู้จัดการ")? "Manager" : "Employee");
            String employeeIdCardNumber = UserHub.user.getTxtEmployeeIdCardNumber().getText();
            String employeeAddress = UserHub.user.getTxtEmployeeAddress().getText();
            String employeeTel = UserHub.user.getTxtEmployeeTel().getText();

            if (!employeeFirstnameOld.equals(employeeFirstname) || !employeeLastnameOld.equals(employeeLastname)) { //ถ้าชื่อผู้ใช้ใหม่ไม่ตรงกันชื่อเดิม ให้ทำการค้นหาว่าซ้ำหรือไม่ แต่ถ้าเป็นชื่อเดิมไม่ต้องค้นหาป้องกันการแก้พาสเวิร์ดไม่ได้เพราะติดชื่อซ้ำ

                String sqlCheckEmployee = "SELECT employeeId FROM tbemployee WHERE employeeFirstname = '" + employeeFirstname + "' AND employeeLastname = '" + employeeLastname + "'";

                //ตรวจชื่อ นามสกุลซ้ำ
                if (Util.getRowCount(sqlCheckEmployee) > 0) {
                    Message.showErrorMessage("พบข้อผิดพลาด", "มีข้อมูลพนักงานคนนี้อยู่ในระบบแล้ว");
                    return;
                }

            }

            //ตรวจสอบเลขบัตร ที่อยู่ เบอร์โทรถ้าไม่ว่างก็ต้องไม่น้อยกว่าที่กำหนด
            //เลขบัตรประชาชนถ้าไม่ว่างและน้อยกว่า 13 หลักให้เตือน
            if (!UserHub.user.getTxtEmployeeIdCardNumber().getText().isEmpty() && UserHub.user.getTxtEmployeeIdCardNumber().getText().length() < 13) {
                Message.showWarningMessage("พบข้อผิดพลาด", "เลขบัตรประชาชนต้องไม่น้อยกว่า 13 หลัก");
                return;
            }

            //ถ้าไม่ว่าง ตรวจดูว่าซ้ำหรือเปล่า
            if (!UserHub.user.getTxtEmployeeIdCardNumber().getText().isEmpty() && !emploYeeIdCardNumberOld.equals(employeeIdCardNumber)) {

                //ตรวจเลขบัตรซ้ำด้วย
                String sqlCheckEmployee = "SELECT employeeIdCardNumber FROM tbemployee WHERE employeeIdCardNumber = '" + employeeIdCardNumber + "'";
                if (Util.getRowCount(sqlCheckEmployee) > 0) {
                    Message.showErrorMessage("พบข้อผิดพลาด", "รหัสบัตรประชาชนนี้ถูกใช้แล้ว");
                    return;
                }

            }


            //เบอร์โทรศัพท์ต้องไม่น้อยกว่า 10 หลัก
            if (!UserHub.user.getTxtEmployeeTel().getText().isEmpty() && UserHub.user.getTxtEmployeeTel().getText().length() < 10) {
                Message.showWarningMessage("พบข้อผิดพลาด", "เบอร์โทรศัพท์ต้องไม่น้อยกว่า 10 หลัก");
                return;
            }

            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                String sql = "UPDATE tbemployee SET employeeNamePrefix ='"+employeeNamePrefix+"', employeeFirstname ='"+employeeFirstname+"', "
                        + "employeeLastname='"+employeeLastname+"', employeePosition='"+employeePosition+"', employeeIdCardNumber='"+employeeIdCardNumber+"', "
                        + "employeeAddress='"+employeeAddress+"', employeeTel='"+employeeTel+"' WHERE employeeId ='"+employeeId+"'";
                
                try { 
                    
                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        Message.showInfoMessage("การแก้ไขสำเร็จ", "แก้ไขข้อมูลพนักงานแล้ว");
                        showData(true);
                        Util.clearText(this);
                    } else {
                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขข้อมูลพนักงาน");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } //ถ้าต้องการแก้ไข

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void deleteData() {

        int rowSel = UserHub.user.getTbEmployee().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการลบ");
            return;
        }

        if (Message.showMessageDelete2Btn(this) == 0) { //ถ้าต้องการลบ

            String employeeId = UserHub.user.getTbEmployee().getValueAt(rowSel, 0).toString();
//            System.out.println("employeeId = " + employeeId);
            
            String userId = Util.getUserId(employeeId); //เก็บเลขผู้ใช้จากเลขหนักงาน
//            System.out.println("userId = " + userId);
            
            String sql = "DELETE FROM tbemployee WHERE employeeId = '" + employeeId + "'"; //ลบข้อมูลพนักงาน

            try {

                int result = DBFactory.getConnection().createStatement().executeUpdate(sql); //ทำลบข้อมูลพนักงาน

                if (result != -1) {
      
                    sql = "DELETE FROM tbuser WHERE employeeId = '"+employeeId+"'"; //ลบผู้ใช้จากเลขพนักงาน
                    
                    DBFactory.getConnection().createStatement().executeUpdate(sql);
                    
                    UserHub.user.getUserControl().showData(true); //เรียกแสดงผู้ใช้ระบบใหม่ด้วย

                    Message.showInfoMessage("การลบสำเร็จ", "ลบข้อมูลพนักงานแล้ว");
                    showData(true);
                    Util.clearText(this);

                    if (FormMain.user.getUserId() == Integer.parseInt(userId)) {
                        Message.showErrorMessage("คำเตือนจากระบบ", "กรุณาเข้าสู่ระบบอีกครั้ง เนื่องจากข้อมูลผู้ใช้ระบบในขณะนี้ถูกยกเลิก");
                        MainHub.main.setEnvLogout();
                    }

                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถลบข้อมูลพนักงาน");
                }

            } catch (SQLException ex) {
                Logger.getLogger(EmployeeControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //ถ้าต้องการลบ
        
    }
}
