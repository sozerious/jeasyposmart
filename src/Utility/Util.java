/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Control.ControlInterface;
import Control.EmployeeControl;
import Control.ProductControl;
import Control.ProductTypeControl;
import Control.PromotionControl;
import Control.PromotionViewControl;
import Control.StockControl;
import Control.StockRemainControl;
import Control.UserControl;
import Control.UserLoginControl;
import Model.Hub.LoginHub;
import Model.Hub.MainHub;
import Model.Hub.ProductHub;
import Model.Hub.PromotionHub;
import Model.Hub.StockHub;
import Model.Hub.UserHub;
import static Utility.Message.showInfoMessage;
import View.FormMain;
import View.FormProduct;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import sun.util.BuddhistCalendar;

/**
 *
 * @author JAVA DEV
 */
public class Util {

    //ควบคุม textCost
    public static void textAmountControl() {
        //จะทำงานเมื่อมีการตรวจเจอบาร์โค๊ดหน้าที่คือควบคุมหน้าจอแสดงราคาและจำนวน
        if (!MainHub.main.multiplyAmount) { //ถ้าไม่ได้กดตัวคูณให้ปรับจำนวนเป็น 1 ถ้ากดไม่ต้องทำให้อ่านค่าได้เลย
            MainHub.main.getLblAmount().setText("@1");
        } else if (MainHub.main.multiplyAmount && "@".equals(MainHub.main.getLblAmount().getText())) {
            MainHub.main.getLblAmount().setText("@1");
        }
    }

    //กรองว่าประโยคเป็นตัวเลขหรือไม่ แต่มีข้อเสียคือถ้าไม่ใช่ตัวเลขแค่ตัวเดียวจะถือเป็น false ทันที
    public static boolean isInteger(String str) {
        return str.matches("^-?[0-9]+(\\.[0-9]+)?$");
    }

    //กรองตัวเลขแบบเอาข้างบนมาโมเอง กรองเอาแต่เฉพาะตัวเลข โยนตัวหนังสือทิ้งไป
    public static int fillterNumberToInt(String word) {

        String result = "";

        for (int i = 0; i < word.length(); i++) {
            if (isInteger(word.charAt(i) + "")) {
                result += word.charAt(i) + "";
            }
        }
        return Integer.parseInt(result);
    }

    //กรองตัวเลขแบบเอาข้างบนมาโมเอง กรองเอาแต่เฉพาะตัวเลข โยนตัวหนังสือทิ้งไป
    public static double fillterNumberToDouble(String word) {
        String result = "";

        for (int i = 0; i < word.length(); i++) {
            if (isInteger(word.charAt(i) + "")) {
                result += word.charAt(i) + "";
            }
        }
        return (Double.parseDouble(result)) / 100; //แปลงกลับเป็นทศนิยม

    }

    //กรองตัวเลขแบบเอาข้างบนมาโมเอง กรองเอาแต่เฉพาะตัวเลข โยนตัวหนังสือทิ้งไป
    public static String fillterNumberToString(String word) {

        String result = "";

        for (int i = 0; i < word.length(); i++) {
            if (isInteger(word.charAt(i) + "")) {
                result += word.charAt(i) + "";
            }
        }
        return result;
    }

    //ตรวจสอบค่าว่าง ==== เพิ่มเงื่อนไขให้ครบทุกพาเนล
    public static boolean checkEmpty(ControlInterface control) {

        if (control instanceof UserLoginControl) {

            if (LoginHub.login.getTxtUsername().getText().isEmpty()
                    || LoginHub.login.getTxtPassword().getText().isEmpty()) {
                Message.showMessageEmpty();
                return false;
            }
            return true;

        } else if (control instanceof ProductControl) {

            if (ProductHub.product.getTxtProductBarcode().getText().isEmpty()
                    || ProductHub.product.getTxtProductName().getText().isEmpty()
                    || ProductHub.product.getTxtProductFund().getText().isEmpty()
                    || ProductHub.product.getTxtProductPrice().getText().isEmpty()) {
                return false;
            }
            return true;

        } else if (control instanceof ProductTypeControl) {

            if (ProductHub.product.getTxtProductType_ProductType().getText().isEmpty()) {
                return false;
            }
            return true;

        } else if (control instanceof UserControl) {

            if (UserHub.user.getTxtUsername().getText().isEmpty()
                    || UserHub.user.getTxtPassword().getText().isEmpty()
                    || UserHub.user.getTxtPasswordAgain().getText().isEmpty()) {
                return false;
            }
            return true;

        } else if (control instanceof EmployeeControl) {

            if (UserHub.user.getTxtEmployeeFirstname().getText().isEmpty()
                    || UserHub.user.getTxtEmployeeLastname().getText().isEmpty()) {
//                    || UserHub.user.getTxtEmployeeIdCardNumber().getText().isEmpty()
//                    || UserHub.user.getTxtEmployeeAddress().getText().isEmpty()
//                    || UserHub.user.getTxtEmployeeTel().getText().isEmpty()) {
                return false;
            }
            return true;

        } else if (control instanceof StockControl) {

            if (StockHub.stock.getTxtStockAmount().getText().isEmpty()) {
                return false;
            }
            return true;

        } else if (control instanceof PromotionControl) {

            if (PromotionHub.promotion.getTxtPromotionName().getText().isEmpty()
//                    || PromotionHub.promotion.getTxtPromotionDetail().getText().isEmpty()
                    || (PromotionHub.promotion.getTxtAmount1().isEnabled() && PromotionHub.promotion.getTxtAmount1().getText().isEmpty())
                    || (PromotionHub.promotion.getTxtAmount2().isEnabled() && PromotionHub.promotion.getTxtAmount2().getText().isEmpty())
                    || (PromotionHub.promotion.getTxtAmount3().isEnabled() && PromotionHub.promotion.getTxtAmount3().getText().isEmpty())
                    || (PromotionHub.promotion.getTxtDiscountResult().isEnabled() && PromotionHub.promotion.getTxtDiscountResult().getText().isEmpty())
                    || (PromotionHub.promotion.getTxtDiscountResultDecimal().isEnabled() && PromotionHub.promotion.getTxtDiscountResultDecimal().getText().isEmpty())
                    || (PromotionHub.promotion.getTxtAmount4().isEnabled() && PromotionHub.promotion.getTxtAmount4().getText().isEmpty())) {
                return false;
            }
            return true;

        }

        return false;

    }

    //รับชื่อเดือนเป็นภาษาไทยเข้ามาแล้วส่งหมายเลขของเดือนนั้นๆ กลับออกไป
    public static int checkNumMonth(String monthName) {

        int num = 0;
        String[] month = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};

        for (int i = 0; i < month.length; i++) {
            if (monthName.equals(month[i])) {
                num = i + 1;
                break;
            }
        }

        return num;
    }

    //เคลียร์แถวเกิน ==== เพิ่มเงื่อนไขให้ครบทุกพาเนล
    public static void clearRow(String tableName) {

        int total = 0;

        switch (tableName) {

            case "sell":
                total = MainHub.main.getTbSell().getRowCount() - 1;

                while (total > -1) {
                    MainHub.main.getModelSell().removeRow(total);
                    total--;
                }
                break;
            case "active":
                total = MainHub.main.getTbActive().getRowCount() - 1;

                while (total > -1) {
                    MainHub.main.getModelActive().removeRow(total);
                    total--;
                }
                break;
            case "recommend":
                total = MainHub.main.getTbRecom().getRowCount() - 1;

                while (total > -1) {
                    MainHub.main.getModelRecom().removeRow(total);
                    total--;
                }
                break;
            case "product":
                total = ProductHub.product.getTbProduct().getRowCount() - 1;

                while (total > -1) {
                    ProductHub.product.getModelProduct().removeRow(total);
                    total--;
                }
                break;
            case "producttype":
                total = ProductHub.product.getTbProductType().getRowCount() - 1;

                while (total > -1) {
                    ProductHub.product.getModelProductType().removeRow(total);
                    total--;
                }
                break;
            case "user":
                total = UserHub.user.getTbUser().getRowCount() - 1;

                while (total > -1) {
                    UserHub.user.getModelUser().removeRow(total);
                    total--;
                }
                break;
            case "employee":
                total = UserHub.user.getTbEmployee().getRowCount() - 1;

                while (total > -1) {
                    UserHub.user.getModelEmployee().removeRow(total);
                    total--;
                }
                break;
            case "stock":
                total = StockHub.stock.getTbStock().getRowCount() - 1;

                while (total > -1) {
                    StockHub.stock.getModelStock().removeRow(total);
                    total--;
                }
                break;
            case "stockremain":
                total = StockHub.stock.getTbStockRemain().getRowCount() - 1;

                while (total > -1) {
                    StockHub.stock.getModelStockRemain().removeRow(total);
                    total--;
                }
                break;
            case "stockexpire":
                total = StockHub.stock.getTbStockExpire().getRowCount() - 1;

                while (total > -1) {
                    StockHub.stock.getModelStockExpire().removeRow(total);
                    total--;
                }
                break;
            case "promotion":
                total = PromotionHub.promotion.getTbPromotion().getRowCount() - 1;

                while (total > -1) {
                    PromotionHub.promotion.getModelPromotion().removeRow(total);
                    total--;
                }
                break;
            case "promotionview":
                total = PromotionHub.promotion.getTbPromotionView().getRowCount() - 1;

                while (total > -1) {
                    PromotionHub.promotion.getModelPromotionView().removeRow(total);
                    total--;
                }
                break;
            default:
                showInfoMessage("พบข้อผิดพลาด", "ระบุชื่อตารางที่ต้องการเคลียร์");

        }

    }

    //ล้างข้อความ ==== เพิ่มเงื่อนไขให้ครบทุกพาเนล
    public static void clearText(ControlInterface control) {

        if (control instanceof UserLoginControl) {

            LoginHub.login.getTxtUsername().setText("");
            LoginHub.login.getTxtPassword().setText("");
            LoginHub.login.getCbxUserType().setSelectedIndex(0);

        } else if (control instanceof ProductControl) {

            ProductHub.product.getCbxProductType().setSelectedIndex(0);
            ProductHub.product.getTxtProductBarcode().setText("");
            ProductHub.product.getTxtProductName().setText("");
            ProductHub.product.getTxtProductFund().setText("");
            ProductHub.product.getTxtProductFundDecimal().setText("00");
            ProductHub.product.getTxtProductPrice().setText("");
            ProductHub.product.getTxtProductPriceDecimal().setText("00");
            ProductHub.product.getCbxProductExpire().setSelectedIndex(0);
            ProductHub.product.getTbProduct().clearSelection();

        } else if (control instanceof ProductTypeControl) {

            ProductHub.product.getTxtProductType_ProductType().setText("");
            ProductHub.product.getTbProductType().clearSelection();

        } else if (control instanceof UserControl) {

            UserHub.user.getCbxEmployeeName().setSelectedIndex(0);
            UserHub.user.getTxtUsername().setText("");
            UserHub.user.getTxtPassword().setText("");
            UserHub.user.getTxtPasswordAgain().setText("");
            UserHub.user.getRdbUserType().setSelected(true);
            UserHub.user.getTbUser().clearSelection();
            UserHub.user.getLblCheckPassword().setForeground(new Color(255, 153, 0));
            UserHub.user.getLblCheckPassword().setText("ตรวจสอบรหัสผ่าน");

        } else if (control instanceof EmployeeControl) {

            UserHub.user.getCbxEmployeePrefix().setSelectedIndex(0);
            UserHub.user.getTxtEmployeeFirstname().setText("");
            UserHub.user.getTxtEmployeeLastname().setText("");
            UserHub.user.getCbxEmployeePosition().setSelectedIndex(0);
            UserHub.user.getTxtEmployeeIdCardNumber().setText("");
            UserHub.user.getTxtEmployeeAddress().setText("");
            UserHub.user.getTxtEmployeeTel().setText("");
            UserHub.user.getTbEmployee().clearSelection();

        } else if (control instanceof StockControl) {

            StockHub.stock.getCbxStockProductName().setSelectedIndex(0);
            StockHub.stock.getTxtStockAmount().setText("");
            StockHub.stock.getCbxStockExpireYear().setSelectedIndex(0);
            StockHub.stock.getCbxStockExpireMonth().setSelectedIndex(0);
            StockHub.stock.getCbxStockExpireDate().setSelectedIndex(0);
            StockHub.stock.getTbStock().clearSelection();

        } else if (control instanceof PromotionControl) {
            
            PromotionHub.promotion.getTxtPromotionName().setText("");
            PromotionHub.promotion.getTxtPromotionDetail().setText("");
            
            PromotionHub.promotion.getCbxPromotionType().setEnabled(true);
            PromotionHub.promotion.getCbxPromotionType().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionStartYear().setEnabled(true);
            PromotionHub.promotion.getCbxPromotionStartMonth().setEnabled(true);
            PromotionHub.promotion.getCbxPromotionStartDate().setEnabled(true);
            PromotionHub.promotion.getCbxNumOfConditionProduct().setEnabled(true);
            PromotionHub.promotion.getCbxNumOfConditionProduct().setSelectedIndex(0);
            PromotionHub.promotion.getCbxAmount1().setEnabled(true);
            PromotionHub.promotion.getCbxAmount1().setSelectedIndex(0);
            PromotionHub.promotion.getCbxAmount2().setEnabled(true);
            PromotionHub.promotion.getCbxAmount2().setSelectedIndex(0);
            PromotionHub.promotion.getCbxAmount3().setEnabled(true);
            PromotionHub.promotion.getCbxAmount3().setSelectedIndex(0);
            PromotionHub.promotion.getTxtAmount1().setEnabled(true);
            PromotionHub.promotion.getTxtAmount1().setText("");
            PromotionHub.promotion.getTxtAmount2().setEnabled(true);
            PromotionHub.promotion.getTxtAmount2().setText("");
            PromotionHub.promotion.getTxtAmount3().setEnabled(true);
            PromotionHub.promotion.getTxtAmount3().setText("");
            PromotionHub.promotion.getCbxDiscountResult().setEnabled(true);
            PromotionHub.promotion.getCbxDiscountResult().setSelectedIndex(0);
            PromotionHub.promotion.getTxtDiscountResult().setEnabled(true);
            PromotionHub.promotion.getTxtDiscountResult().setText("");
            PromotionHub.promotion.getTxtDiscountResultDecimal().setEnabled(true);
            PromotionHub.promotion.getTxtDiscountResultDecimal().setText("00");
            PromotionHub.promotion.getCbxPlusResult().setEnabled(true);
            PromotionHub.promotion.getCbxPlusResult().setSelectedIndex(0);
            PromotionHub.promotion.getTxtAmount4().setEnabled(true);
            PromotionHub.promotion.getTxtAmount4().setText("");
            
            PromotionHub.promotion.setDefaultDisable();
            PromotionHub.promotion.getTbPromotion().clearSelection();
            
        }

    }

    //เคลียร์รายการค้นหา ==== เพิ่มเงื่อนไขให้ครบทุกพาเนล
    public static void clearSearchText(ControlInterface control) {

        if (control instanceof ProductControl) {

            ProductHub.product.getTxtProductSearch().setText("");
            ProductHub.product.getCbxProductSearchFrom().setSelectedIndex(0);
            ProductHub.product.getCbxProductSortBy().setSelectedIndex(0);
            ProductHub.product.getCbxProductSortFrom().setSelectedIndex(0);
            ProductHub.product.getCbxProductPerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof ProductTypeControl) {

            ProductHub.product.getTxtProductTypeSearch().setText("");
            ProductHub.product.getCbxProductTypeSearchFrom().setSelectedIndex(0);
            ProductHub.product.getCbxProductTypeSortBy().setSelectedIndex(0);
            ProductHub.product.getCbxProductTypeSortFrom().setSelectedIndex(0);
            ProductHub.product.getCbxProductTypePerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof UserControl) {

            UserHub.user.getTxtUserSearch().setText("");
            UserHub.user.getCbxUserSearchFrom().setSelectedIndex(0);
            UserHub.user.getCbxUserSortBy().setSelectedIndex(0);
            UserHub.user.getCbxUserSortFrom().setSelectedIndex(0);
            UserHub.user.getCbxUserPerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof EmployeeControl) {

            UserHub.user.getTxtEmployeeSearch().setText("");
            UserHub.user.getCbxEmployeeSearchFrom().setSelectedIndex(0);
            UserHub.user.getCbxEmployeeSortBy().setSelectedIndex(0);
            UserHub.user.getCbxEmployeeSortFrom().setSelectedIndex(0);
            UserHub.user.getCbxEmployeePerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof StockControl) {

            StockHub.stock.getTxtStockSearch().setText("");
            StockHub.stock.getCbxStockSearchFrom().setSelectedIndex(0);
            StockHub.stock.getCbxStockSortBy().setSelectedIndex(0);
            StockHub.stock.getCbxStockSortFrom().setSelectedIndex(0);
            StockHub.stock.getCbxStockPerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof StockRemainControl) {

            StockHub.stock.getTxtStockRemainSearch().setText("");
            StockHub.stock.getCbxStockRemainSearchFrom().setSelectedIndex(0);
            StockHub.stock.getCbxStockRemainSortBy().setSelectedIndex(0);
            StockHub.stock.getCbxStockRemainSortFrom().setSelectedIndex(0);
            StockHub.stock.getCbxStockRemainPerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof PromotionControl) {

            PromotionHub.promotion.getTxtPromotionSearch().setText("");
            PromotionHub.promotion.getCbxPromotionSearchFrom().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionSortBy().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionSortFrom().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionPerPage().setSelectedIndex(0);
            control.showData(true);

        } else if (control instanceof PromotionViewControl) {

            PromotionHub.promotion.getTxtPromotionViewSearch().setText("");
            PromotionHub.promotion.getCbxPromotionViewSearchFrom().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionViewSortBy().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionViewSortFrom().setSelectedIndex(0);
            PromotionHub.promotion.getCbxPromotionViewPerPage().setSelectedIndex(0);
            control.showData(true);

        }

    }

    //นับจำนวนแถวผลลัพธ์
    public static int getRowCount(String sql) {

        int rowCount = 0;

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {
                rowCount++;
            }

            return rowCount;

        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rowCount;
    }

    //ส่งกลับผลลัพธ์ที่เป็นสตริง
    public static String getStringResult(String sql) {
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {

                return rs.getString(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //หาสตริงผลลัพธ์จากคีย์ที่กำหนด
    public static String getResultFromId(String type, int key) {

        String sql = "";

        switch (type) {
            case "promotion":
                sql = "SELECT promotionName FROM tbpromotion WHERE promotionId = '" + key + "'";
                break;
            case "product":
                sql = "SELECT productName FROM tbproduct WHERE productId = '" + key + "'";
                break;
            case "productfund":
                sql = "SELECT productFund FROM tbproduct WHERE productId = '" + key + "'";
                break;
            case "productprice":
                sql = "SELECT productPrice FROM tbproduct WHERE productId = '" + key + "'";
                break;
        }

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //หาสตริงชื่อสินค้าจากบาร์โค๊ด
    public static String getProductNameFormBarcode(String barcode) {

        String sql = "SELECT productName FROM tbproduct WHERE productBarcode = '" + barcode + "'";


        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //หาสตริงบาร์โค๊ดจากชื่อสินค้า
    public static String getProductBarcodeFromProductName(String productName) {

        String sql = "SELECT productBarcode FROM tbproduct WHERE productName = '" + productName + "'";

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    //หาเลขสินค้าล่าสุดจากชื่อสินค้า
    public static String getLastProductIdFromProductName(String productName) {

        String sql = "SELECT a.productId FROM tbproduct a WHERE productName = '" + productName + "' "
                + "AND productId = (SELECT productId FROM tbproduct WHERE productname = a.productName ORDER by productId DESC LIMIT 0,1)";

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    //หาราคาสินค้าล่าสุดจากบาร์โค๊ดสินค้า
    public static double getLastProductPriceFromProductBarcode(String barcode) {

        String sql = "SELECT a.productPrice FROM tbproduct a WHERE productBarcode = '" + barcode + "' "
                + "AND productId = (SELECT productId FROM tbproduct WHERE productname = a.productName ORDER by productId DESC LIMIT 0,1)";
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    //ส่งกลับหมายเลขพนักงาน
    public static String getEmployeeId(String firstName, String lastName) {

        String sql = "SELECT employeeId FROM tbemployee WHERE employeeFirstname = '" + firstName + "' AND "
                + "employeeLastname = '" + lastName + "'";
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {

                return rs.getString(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    //ส่งกลับหมายเลขผู้ใช้
    public static String getUserId(String employeeId) {

        String sql = "SELECT userId FROM tbuser WHERE employeeId = '" + employeeId + "'";
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {

                return rs.getString(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    //กรองคำป้องกันอักขระที่จะทำให้ sql error
    public static String wordFillter(String word) {

        String result = word;
        String newResult = "";

        for (int i = 0; i < result.length(); i++) { //ถ้าตัวที่ i ไม่ใช่ตัวที่เจออักษรต้องห้าม ให้เอามาเก็บไว้ที่ตัวแปร newResult
            if (i != result.indexOf("\'", i) && i != result.indexOf("\\", i) && i != result.indexOf("\"", i) && i != result.indexOf("/", i)) {
                newResult += result.substring(i, i + 1);
            }
        }
        return newResult;
    }

    //หาจำนวนหน้ารายการ
    public static int checkTotalPage(JComboBox cbxPerPage, int vectorSize) {

        int pageNum = 0;

        int result = vectorSize;
        int perPage = Integer.parseInt(cbxPerPage.getSelectedItem().toString());

        if ((result / perPage > 0) && (result % perPage) > 0) {
            pageNum = result / perPage + 1;
        } else if (result / perPage > 0) {
            pageNum = result / perPage;
        } else if (result / perPage == 0) {
            pageNum = 1;
        }

        return pageNum;

    }

    //ควบคุมเลขหน้าไม่ให้เกินจำนวนหน้าจริง
    public static void checkCurrentPage(ControlInterface control, JTextField txtPage, JComboBox cbxPerPage, int vectorSize) {

        JTextField txt = txtPage; // เก็บตัวแปร TextField ที่ใช้แสดงเลขหน้าไว้ใน txt

        if (Integer.parseInt(txt.getText()) > checkTotalPage(cbxPerPage, vectorSize)) { // ถ้าเลขหน้าที่แสดงมากกว่าจำนวนหน้าที่มีอยู่จริง
            txt.setText(checkTotalPage(cbxPerPage, vectorSize) + ""); // ให้แสดงเลขหน้าเป็นหน้าสุดท้าย

            control.showData(true);

        }
    }

    //เพิ่ม-ลดเลขหน้าจากการกดปุ่ม
    public static void controlPageNum(String type, ControlInterface control, JButton btnPreOrNext, JTextField txtPage) {

        switch (type) {

            case "pre":
                if (btnPreOrNext.isEnabled()) { // ถ้าปุ่มมีการเปิดใช้งาน
                    int curPage = Integer.parseInt(txtPage.getText()); // เก็บเลขที่หน้าไว้ที่ตัวแปร page
                    curPage -= 1; // ให้ page ลบ 1
                    txtPage.setText(curPage + ""); // เซต page ให้เป็นเลขหน้า
                }
                break;
            case "next":
                if (btnPreOrNext.isEnabled()) { // ถ้าปุ่มมีการเปิดใช้งาน
                    int curPage = Integer.parseInt(txtPage.getText()); // เก็บเลขที่หน้าไว้ที่ตัวแปร page
                    curPage += 1; // ให้ page ลบ 1
                    txtPage.setText(curPage + ""); // เซต page ให้เป็นเลขหน้า
                }
                break;

        }

        control.showData(false);

    }

    //ควบคุมปุ่มเลื่อนข้อมูลซ้าย - ขวา
    public static void controlPreNext(JTextField txtPage, JButton btnPre, JButton btnNext, JComboBox cbxPerPage, int vectorSize) {

        int curPage = Integer.parseInt(txtPage.getText());
        JButton pre = btnPre;
        JButton next = btnNext;
        int pageNum = checkTotalPage(cbxPerPage, vectorSize);

        if (pageNum == 1) { // แสดงว่ามีหน้าเดียว
            pre.setEnabled(false);
            next.setEnabled(false);
        } else if (curPage == 1) { // ถ้าผ่านเงือนไขข้างบนลงมาแสดงว่ามีหลายหน้าและอยู่ในหน้าแรก
            pre.setEnabled(false);
            next.setEnabled(true);
        } else if (curPage > 1 && curPage < pageNum) { // ถ้าไม่ได้อยู่หน้าแรก และไม่ได้อยู่หน้าสุดท้าย
            pre.setEnabled(true);
            next.setEnabled(true);
        } else if (curPage == pageNum) {
            // แสดงว่าอยู่ในหน้าสุดท้าย เงื่อนไขนี้ก่ำกึงกับเงื่อนไขแรกคืออาจจะมีหน้าเดียวจึงต้องเอามาไว้ด้านล่างเพราะถ้ามีหน้าเดียว pageNum ต้องเท่ากับ 1
            // จึงติดเงื่อนไขข้างบนไปแล้ว ถ้ามาถึงตรงนี้ได้แสดงว่าไม่ได้มีหน้าเดียวและกำลังอยู่ในหน้าสุดท้าย แต่เราอาจป้องกันได้โดยเขียนเป็น
            // else if (page == pageNum && pageNum != 1)
            pre.setEnabled(true);
            next.setEnabled(false);
        }

    }

    //โชว์ข้อมูลที่ถูกเลือกจากตาราง ==== เพิ่มเงื่อนไขให้ครบทุกพาเนล
    public static void tableToText(ControlInterface control) {

        int rowSel;

        if (control instanceof ProductControl) {

            rowSel = ProductHub.product.getTbProduct().getSelectedRow();

            String fundFull = ProductHub.product.getTbProduct().getValueAt(rowSel, 3).toString();
            String productFund = fundFull.substring(0, fundFull.indexOf("."));
            String productFundDecimal = fundFull.substring(fundFull.indexOf(".") + 1);

            String priceFull = ProductHub.product.getTbProduct().getValueAt(rowSel, 4).toString();
            String productPrice = priceFull.substring(0, priceFull.indexOf("."));
            String productPriceDecimal = priceFull.substring(priceFull.indexOf(".") + 1);

            ProductHub.product.getCbxProductType().setSelectedItem(ProductHub.product.getTbProduct().getValueAt(rowSel, 0));
            ProductHub.product.getTxtProductBarcode().setText(ProductHub.product.getTbProduct().getValueAt(rowSel, 1).toString());
            ProductHub.product.getTxtProductName().setText(ProductHub.product.getTbProduct().getValueAt(rowSel, 2).toString());
            ProductHub.product.getTxtProductFund().setText(productFund);
            ProductHub.product.getTxtProductFundDecimal().setText(productFundDecimal);
            ProductHub.product.getTxtProductPrice().setText(productPrice);
            ProductHub.product.getTxtProductPriceDecimal().setText(productPriceDecimal);
            ProductHub.product.getCbxProductExpire().setSelectedItem(ProductHub.product.getTbProduct().getValueAt(rowSel, 5));

        } else if (control instanceof ProductTypeControl) {

            rowSel = ProductHub.product.getTbProductType().getSelectedRow();

            ProductHub.product.getTxtProductType_ProductType().setText(ProductHub.product.getTbProductType().getValueAt(rowSel, 1).toString());

        } else if (control instanceof UserControl) {

            rowSel = UserHub.user.getTbUser().getSelectedRow();

            UserHub.user.getCbxEmployeeName().setSelectedIndex(0);
            UserHub.user.getTxtUsername().setText(UserHub.user.getTbUser().getValueAt(rowSel, 3).toString());
            UserHub.user.getTxtPassword().setText(UserHub.user.getTbUser().getValueAt(rowSel, 4).toString());
            UserHub.user.getTxtPasswordAgain().setText(UserHub.user.getTbUser().getValueAt(rowSel, 4).toString());
            if (UserHub.user.getTbUser().getValueAt(rowSel, 5).toString().equals("Admin") && UserHub.user.getRdbAdminType().isEnabled()) {
                UserHub.user.getRdbAdminType().setSelected(true);
            } else {
                UserHub.user.getRdbUserType().setSelected(true);
            }

        } else if (control instanceof EmployeeControl) {

            rowSel = UserHub.user.getTbEmployee().getSelectedRow();

            UserHub.user.getCbxEmployeePrefix().setSelectedItem(UserHub.user.getTbEmployee().getValueAt(rowSel, 1));
            UserHub.user.getTxtEmployeeFirstname().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 2).toString());
            UserHub.user.getTxtEmployeeLastname().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 3).toString());
            UserHub.user.getCbxEmployeePosition().setSelectedItem(UserHub.user.getTbEmployee().getValueAt(rowSel, 4));
            UserHub.user.getTxtEmployeeIdCardNumber().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 5).toString());
            UserHub.user.getTxtEmployeeAddress().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 6).toString());
            UserHub.user.getTxtEmployeeTel().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 7).toString());

        } else if (control instanceof StockControl) {

            rowSel = StockHub.stock.getTbStock().getSelectedRow();

            String date = StockHub.stock.getTbStock().getValueAt(rowSel, 6).toString();
            String year = date.substring(0, date.indexOf("-"));
            String month = date.substring(date.indexOf("-") + 1, date.indexOf("-", date.indexOf("-") + 1));
            String day = date.substring(date.lastIndexOf("-") + 1);
            String[] monthArr = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};

            StockHub.stock.getCbxStockProductName().setSelectedItem(StockHub.stock.getTbStock().getValueAt(rowSel, 1));
            StockHub.stock.getTxtStockAmount().setText(StockHub.stock.getTbStock().getValueAt(rowSel, 3).toString());
            StockHub.stock.getCbxStockExpireYear().setSelectedItem(year + 543);
            StockHub.stock.getCbxStockExpireMonth().setSelectedItem(monthArr[Integer.parseInt(month) - 1]);
            StockHub.stock.getCbxStockExpireDate().setSelectedItem(day);

        } else if (control instanceof PromotionControl) {
            //ยาวเลยอันนี้
            rowSel = PromotionHub.promotion.getTbPromotion().getSelectedRow();

            String promotionId = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 0).toString();
            
            String promotionType = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 3).toString();
            String[] monthArr = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
            //startDate จริง startDate ไม่ต้องก็ได้ เพราะวันเริ่มต้นอาจเลยวันปัจจุบันไปแล้ว
            String startDate = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 7).toString();
            String startYear = startDate.substring(0, startDate.indexOf("-"));
            String startMonth = startDate.substring(startDate.indexOf("-") + 1, startDate.indexOf("-", startDate.indexOf("-") + 1));
            String startDay = startDate.substring(startDate.lastIndexOf("-") + 1);

            //endDate
            String endDate = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 8).toString();
            String endYear = endDate.substring(0, endDate.indexOf("-"));
            String endMonth = endDate.substring(endDate.indexOf("-") + 1, endDate.indexOf("-", endDate.indexOf("-") + 1));
            String endDay = endDate.substring(endDate.lastIndexOf("-") + 1);
            //condition
            String condition = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 4).toString();
            int numCon = Util.findTextNum(condition, "+");
            String[] conArr = condition.split(" ");

            //===== เซตค่าข้อมูลเพื่อโชว์ความเหนือ =====
            PromotionHub.promotion.getTxtPromotionName().setText(PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 1).toString());
            PromotionHub.promotion.getTxtPromotionDetail().setText(PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 2).toString());
            //เซตประเภท
            PromotionHub.promotion.getCbxPromotionType().setSelectedItem(promotionType);
            
            String usedSql = "SELECT promotionId FROM tbpromotionactive WHERE promotionId = '" + promotionId + "'";
            int used = Util.getRowCount(usedSql);
            if (used > 0) {
                
                //เซตวันเริ่มต้น
                PromotionHub.promotion.getCbxPromotionStartYear().setEnabled(false);
                PromotionHub.promotion.getCbxPromotionStartMonth().setEnabled(false);
                PromotionHub.promotion.getCbxPromotionStartDate().setEnabled(false);
            } else {
                //เซตวันเริ่มต้น
                PromotionHub.promotion.getCbxPromotionStartYear().setEnabled(true);
                PromotionHub.promotion.getCbxPromotionStartYear().setSelectedItem(startYear + 543);
                PromotionHub.promotion.getCbxPromotionStartMonth().setEnabled(true);
                PromotionHub.promotion.getCbxPromotionStartMonth().setSelectedItem(monthArr[Integer.parseInt(startMonth) - 1]);
                PromotionHub.promotion.getCbxPromotionStartDate().setEnabled(true);
                PromotionHub.promotion.getCbxPromotionStartDate().setSelectedItem(startDay);
            }
            
            //เซตวันสิ้นสุด
            PromotionHub.promotion.getCbxPromotionEndYear().setSelectedItem(endYear + 543);
            PromotionHub.promotion.getCbxPromotionEndMonth().setSelectedItem(monthArr[Integer.parseInt(endMonth) - 1]);
            PromotionHub.promotion.getCbxPromotionEndDate().setSelectedItem(endDay);
            //เซตจำนวนเงื่อนไข
            PromotionHub.promotion.getCbxNumOfConditionProduct().setSelectedIndex(numCon);
            //เซตตัวที่ 1
            PromotionHub.promotion.getCbxAmount1().setSelectedItem(conArr[0]);
            PromotionHub.promotion.getTxtAmount1().setText(conArr[1].substring(0, conArr[1].indexOf("ชิ้น")));
            //เซตตัวที่ 2
            if (conArr.length > 2) { //คือมีตัวที่ 2 เพราะมีเครื่องหมายบวก 1 หรือ 2 ตัว
                PromotionHub.promotion.getCbxAmount2().setSelectedItem(conArr[3]);
                PromotionHub.promotion.getTxtAmount2().setText(conArr[4].substring(0, conArr[4].indexOf("ชิ้น")));
            }
            //เซตตัวที่ 3 //คือมีตัวที่ 3 เพราะมีเครื่องหมายบวก 2 ตัว
            if (conArr.length > 5) {
                PromotionHub.promotion.getCbxAmount3().setSelectedItem(conArr[6]);
                PromotionHub.promotion.getTxtAmount3().setText(conArr[7].substring(0, conArr[7].indexOf("ชิ้น")));
            }
            //เซตผลลัพธ์            
            if (promotionType.equals("ส่วนลด")) { //ถ้าเป็นส่วนลด
                String discount = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 5).toString();
                PromotionHub.promotion.getCbxDiscountResult().setSelectedItem(discount.substring(0, discount.indexOf(" ")));
                PromotionHub.promotion.getTxtDiscountResult().setText(discount.substring(discount.indexOf(" ")+1, discount.indexOf(".")));
                PromotionHub.promotion.getTxtDiscountResultDecimal().setText(discount.substring(discount.indexOf(".")+1, discount.indexOf("บาท")-1));
            } else { //ถ้าเป็นของแถม
                String plus = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 6).toString();
                PromotionHub.promotion.getCbxPlusResult().setSelectedItem(plus.substring(0, plus.indexOf(" ")));
                PromotionHub.promotion.getTxtAmount4().setText(plus.substring(plus.indexOf(" ")+1, plus.indexOf("ชิ้น")));
            }
            
            //===== สิ่งที่ต้องปิดไว้ =====
            PromotionHub.promotion.getCbxPromotionType().setEnabled(false);
            PromotionHub.promotion.getCbxNumOfConditionProduct().setEnabled(false);
            PromotionHub.promotion.getCbxAmount1().setEnabled(false);
            PromotionHub.promotion.getCbxAmount2().setEnabled(false);
            PromotionHub.promotion.getCbxAmount3().setEnabled(false);
            PromotionHub.promotion.getTxtAmount1().setEnabled(false);
            PromotionHub.promotion.getTxtAmount2().setEnabled(false);
            PromotionHub.promotion.getTxtAmount3().setEnabled(false);
            PromotionHub.promotion.getCbxDiscountResult().setEnabled(false);
            PromotionHub.promotion.getTxtDiscountResult().setEnabled(false);
            PromotionHub.promotion.getTxtDiscountResultDecimal().setEnabled(false);
            PromotionHub.promotion.getCbxPlusResult().setEnabled(false);
            PromotionHub.promotion.getTxtAmount4().setEnabled(false);

            if (PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 3).toString().equals("ส่วนลด")) {
                PromotionHub.promotion.getCbxDiscountResult().setEnabled(true);
                PromotionHub.promotion.getTxtDiscountResult().setEnabled(true);
                PromotionHub.promotion.getTxtDiscountResultDecimal().setEnabled(true);
            } else {
                PromotionHub.promotion.getCbxPlusResult().setEnabled(true);
                PromotionHub.promotion.getTxtAmount4().setEnabled(true);
            }
        }

    }

    //หาจำนวนของข้อความนั้นที่มีอยู่ในประโยค
    public static int findTextNum(String text, String word) {

        int num = 0;
        int index = -1;

        while (text.indexOf(word, index + 1) != -1) {

            index = text.indexOf(word, index + 1);
//            System.out.println("ตัวที่ " + (index+1));
            num += 1;
        }
//        System.out.println("");
        return num;
    }

    //ควบคุมแป้นกดตัวเลขหน้าจอขายสินค้า
    public static void buttonNumberControl(MouseEvent mouse, String txtButton) { //ควบคุมปุ่มตัวเลข

        //มีป้อนบาร์โค๊ด, ป้อนจำนวน, ป้อนเงินจ่าย
        switch (MainHub.main.lastFocus) {

            case "txtsellbarcode": //ถ้าเป็นการกดปุ่มในขณะที่โฟกัสอยู่ที่ txtsellbarcode

                if (!MainHub.main.getTxtSellBarcode().getText().isEmpty() && !txtButton.equals(".")) { //มีข้อความอยู่ก่อน
                    String text = MainHub.main.getTxtSellBarcode().getText(); //เก็บ text เดิมไว้ก่อน
                    int curPst = MainHub.main.getTxtSellBarcode().getCaretPosition(); //หาตำแหน่ง cursur

                    if (curPst != 0 && curPst != text.length()) { //ถ้าเคอเซอร์อยู่ระหว่างข้อความ

                        //แบ่งข้อความเป็นสองช่วงแล้วใส่ txtbutton ไว้ตรงกลาง
                        String firstText = text.substring(0, curPst);
                        String lastText = text.substring(curPst);
                        text = firstText + txtButton + lastText;
                        MainHub.main.getTxtSellBarcode().setText(text);
                        MainHub.main.getTxtSellBarcode().setCaretPosition(curPst + 1);

                    } else if (curPst == 0) { //ถ้าเป็นตัวแรก

                        MainHub.main.getTxtSellBarcode().setText(txtButton + text);
                        MainHub.main.getTxtSellBarcode().setCaretPosition(curPst + 1);

                    } else { //ตัวสุดท้าย

                        MainHub.main.getTxtSellBarcode().setText(text + txtButton);

                    }

                } else if (!txtButton.equals(".")) { //ไม่มีข้อความอยู่ก่อนและไม่ใช่จุด กัน error
                    MainHub.main.getTxtSellBarcode().setText(txtButton);
                }
                MainHub.main.getTxtSellBarcode().requestFocus();
                break;
            case "pntxtcost": //ถ้าเป็นการกดปุ่มในขณะที่โฟกัสอยู่ที่ pntxtcost

                if (MainHub.main.multiplyAmount && !txtButton.equals(".")) { //ถ้าเปิด multiply ก็คือการป้อนจำนวนสินค้า และต้องไม่ใช่จุดทศนิยม
                    if (MainHub.main.getLblAmount().getText().length() == 1 && !txtButton.equals("0")) {
                        MainHub.main.getLblAmount().setText(MainHub.main.getLblAmount().getText() + txtButton);
                    } else if (MainHub.main.getLblAmount().getText().length() > 1) {
                        MainHub.main.getLblAmount().setText(MainHub.main.getLblAmount().getText() + txtButton);
                    }

                } else if (MainHub.main.doCash) { //คือการป้อนเงินจ่าย
                    if (MainHub.main.getLblCashCost().getText().isEmpty() && !txtButton.equals("0") && !txtButton.equals(".")) {
                        MainHub.main.getLblCashCost().setText(MainHub.main.getLblCashCost().getText() + txtButton);
                    } else if (!MainHub.main.getLblCashCost().getText().isEmpty()) {
                        MainHub.main.getLblCashCost().setText(MainHub.main.getLblCashCost().getText() + txtButton);
                    }
                }

                break;
        }

    }

    //ควบคุมปุ่มลบหน้าจอขายสินค้า
    public static void buttonBackSpaceControl(MouseEvent mouse) { //ควบคุมปุ่มลบ

        switch (MainHub.main.lastFocus) {

            case "txtsellbarcode": //ถ้าเป็นการกดปุ่มในขณะที่โฟกัสอยู่ที่ txtsellbarcode

                if (!MainHub.main.getTxtSellBarcode().getText().isEmpty()) {
                    String text = MainHub.main.getTxtSellBarcode().getText(); //เก็บ text เดิมไว้ก่อน
                    int curPst = MainHub.main.getTxtSellBarcode().getCaretPosition();

                    if (curPst != 0 && curPst != text.length()) { //ถ้าอยู่ในข้อความ
                        String firstText = text.substring(0, curPst - 1);
                        String lastText = text.substring(curPst);
                        text = firstText + lastText;
                        MainHub.main.getTxtSellBarcode().setText(text);
                        MainHub.main.getTxtSellBarcode().setCaretPosition(curPst - 1);
                    } else if (curPst == text.length()) { //ถ้าอยู่หลังข้อความ
                        String textEdit = text.substring(0, text.length() - 1);
                        MainHub.main.getTxtSellBarcode().setText(textEdit);
                        MainHub.main.getTxtSellBarcode().setCaretPosition(curPst - 1);
                    }
                }
                MainHub.main.getTxtSellBarcode().requestFocus();
                break;
            case "pntxtcost": //ถ้าเป็นการกดปุ่มในขณะที่โฟกัสอยู่ที่ pntxtcost
                if (MainHub.main.multiplyAmount && MainHub.main.getLblAmount().getText().length() > 1) {
                    MainHub.main.getLblAmount().setText(MainHub.main.getLblAmount().getText().substring(0, MainHub.main.getLblAmount().getText().length() - 1));
                } else if (MainHub.main.doCash && !MainHub.main.getLblCashCost().getText().isEmpty()) { //เป็นการจ่ายเงินแล้ว เท็กไม่เท่ากับว่าง คือต้องเหลืออย่างน้อยหนึ่งตัวให้ลบได้
                    MainHub.main.getLblCashCost().setText(MainHub.main.getLblCashCost().getText().substring(0, MainHub.main.getLblCashCost().getText().length() - 1));

                }
                break;

        }
    }

    //ควบคุมลาเบลแสดงวันเวลา
    public static void setCurrentDate() {

        Calendar cal = new BuddhistCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        MainHub.main.getLblDate().setText("วันที่ " + day + "/" + month + "/" + year);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String strHour = (hour < 10 ? "0" + hour : hour + "");
        String strMinute = (minute < 10 ? "0" + minute : minute + "");
        String strSecond = (second < 10 ? "0" + second : second + "");

        MainHub.main.getLblTime().setText("เวลา " + strHour + ":" + strMinute + ":" + strSecond);

    }
}