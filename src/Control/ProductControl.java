/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.ProductHub;
import Model.ProductModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.checkEmpty;
import static Utility.Util.clearRow;
import static Utility.Util.getRowCount;
import static Utility.Util.wordFillter;
import View.FormProduct;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author java-dev
 */
public class ProductControl implements ControlInterface {

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(ProductHub.product.getTxtProductPage().getText());
        int perPage = Integer.parseInt(ProductHub.product.getCbxProductPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(ProductHub.product.getCbxProductPerPage(), FormProduct.vectorProduct.size()) == 1) {
                step[0] = 0;
                step[1] = FormProduct.vectorProduct.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(ProductHub.product.getCbxProductPerPage(), FormProduct.vectorProduct.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(ProductHub.product.getCbxProductPerPage(), FormProduct.vectorProduct.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormProduct.vectorProduct.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = ProductHub.product.getCbxProductSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY b.productType ASC";
                break;
            case 1:
                sql += " ORDER BY a.productBarcode ASC";
                break;
            case 2:
                sql += " ORDER BY a.productName ASC";
                break;
            case 3:
                sql += " ORDER BY a.productFund ASC";
                break;
            case 4:
                sql += " ORDER BY a.productPrice ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "";

        if (!search) {
            sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                    + "FROM tbproduct a INNER JOIN tbproducttype b ON a.productTypeId = b.productTypeId WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1)";
        } else {
            int cbxSearch = ProductHub.product.getCbxProductSearchFrom().getSelectedIndex();
            String word = ProductHub.product.getTxtProductSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                            + "FROM tbproduct a INNER JOIN tbproducttype b ON a.productTypeId = b.productTypeId WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1) "
                            + "AND b.productType LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                            + "FROM tbproduct a INNER JOIN tbproducttype b ON a.productTypeId = b.productTypeId WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1) "
                            + "AND a.productBarcode LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 2:
                    sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                            + "FROM tbproduct a INNER JOIN tbproductType b ON a.productTypeID = b.productTypeID WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1) "
                            + "AND a.productName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 3:
                    sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                            + "FROM tbproduct a INNER JOIN tbproductType b ON a.productTypeID = b.productTypeID WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1) "
//                            + "AND a.productFund BETWEEN '0' AND '" + Util.fillterNumberToString(wordFillter(word)) + "'";
                            + "AND a.productFund >= '" + Util.fillterNumberToString(wordFillter(word)) + "'";
                    break;
                case 4:
                    sql = "SELECT a.productId,b.productType,a.productBarcode,a.productName,a.productFund,a.productPrice,a.productExpire,a.dateAdd "
                            + "FROM tbproduct a INNER JOIN tbproductType b ON a.productTypeID = b.productTypeID WHERE a.productId = (SELECT productId FROM tbproduct WHERE productName = a.productName ORDER BY productId DESC LIMIT 0,1) "
//                            + "AND a.productPrice BETWEEN '0' AND '" + Util.fillterNumberToString(wordFillter(word)) + "'";
                            + "AND a.productPrice >= '" + Util.fillterNumberToString(wordFillter(word)) + "'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormProduct.vectorProduct = new Vector();
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.setProductId(rs.getInt(1));
                product.setProductType(rs.getString(2));
                product.setProductBarcode(rs.getString(3));
                product.setProductName(rs.getString(4));
                product.setProductFund(rs.getDouble(5));
                product.setProductPrice(rs.getDouble(6));
                product.setProductExpire(rs.getInt(7));
                product.setDateAdd(rs.getString(8));
                FormProduct.vectorProduct.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!ProductHub.product.getTxtProductSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("product");

        int row = 0;
        int sortFrom = ProductHub.product.getCbxProductSortFrom().getSelectedIndex();
        int[] step = dataLimit();
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    ProductModel product = (ProductModel) FormProduct.vectorProduct.elementAt(i);

                    ProductHub.product.getModelProduct().addRow(new Object[0]);
//                    ProductHub.product.getModelProduct().setValueAt(product.getProductId(), row, 0);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductType(), row, 0);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductBarcode(), row, 1);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductName(), row, 2);
                    ProductHub.product.getModelProduct().setValueAt(nf.format(product.getProductFund()).substring(1), row, 3);
                    ProductHub.product.getModelProduct().setValueAt(nf.format(product.getProductPrice()).substring(1), row, 4);
                    ProductHub.product.getModelProduct().setValueAt((product.getProductExpire() == 0 ? "ไม่ใช่" : "ใช่"), row, 5);
                    ProductHub.product.getModelProduct().setValueAt(product.getDateAdd(), row, 6);

                    row++;
                }
                break;

            case 1:
                FormProduct.vectorProductInverse = new Vector();

                for (int i = FormProduct.vectorProduct.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormProduct.vectorProductInverse.add(FormProduct.vectorProduct.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    ProductModel product = (ProductModel) FormProduct.vectorProductInverse.elementAt(i);

                    ProductHub.product.getModelProduct().addRow(new Object[0]);
//                    ProductHub.product.getModelProduct().setValueAt(product.getProductId(), row, 0);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductType(), row, 0);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductBarcode(), row, 1);
                    ProductHub.product.getModelProduct().setValueAt(product.getProductName(), row, 2);
                    ProductHub.product.getModelProduct().setValueAt(nf.format(product.getProductFund()).substring(1), row, 3);
                    ProductHub.product.getModelProduct().setValueAt(nf.format(product.getProductPrice()).substring(1), row, 4);
                    ProductHub.product.getModelProduct().setValueAt((product.getProductExpire() == 0 ? "ไม่ใช่" : "ใช่"), row, 5);
                    ProductHub.product.getModelProduct().setValueAt(product.getDateAdd(), row, 6);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, ProductHub.product.getTxtProductPage(), ProductHub.product.getCbxProductPerPage(), FormProduct.vectorProduct.size());
        Util.controlPreNext(ProductHub.product.getTxtProductPage(), ProductHub.product.getBtnProductPre(), ProductHub.product.getBtnProductNext(), ProductHub.product.getCbxProductPerPage(), FormProduct.vectorProduct.size());

    }

    @Override
    public void insertData() {

        String productType = ProductHub.product.getCbxProductType().getSelectedItem().toString();
        String productBarcode = ProductHub.product.getTxtProductBarcode().getText();
        String productName = ProductHub.product.getTxtProductName().getText();
        double productFund = Double.parseDouble(ProductHub.product.getTxtProductFund().getText() + (!ProductHub.product.getTxtProductFundDecimal().getText().isEmpty() ? "." + ProductHub.product.getTxtProductFundDecimal().getText() : ""));
        double productPrice = Double.parseDouble(ProductHub.product.getTxtProductPrice().getText() + (!ProductHub.product.getTxtProductPriceDecimal().getText().isEmpty() ? "." + ProductHub.product.getTxtProductPriceDecimal().getText() : ""));
        int productExpire = ProductHub.product.getCbxProductExpire().getSelectedIndex();

        if (productBarcode.length() < 13) {
            Message.showWarningMessage("พบข้อผิดพลาด", "ต้องเป็นตัวเลข 13 หลักเท่านั้น");
            return;
        }

        //ต้องการหาว่ามีสินค้าที่บาร์โค๊ดเดียวกันแล้วหรือไม่
        String sql = "SELECT productId FROM tbproduct WHERE productBarcode = '" + productBarcode + "'";

        if (getRowCount(sql) > 0) {
            Message.showErrorMessage("พบข้อผิดพลาด", "มีสินค้านี้อยู่ในรายการแล้ว");
            return;
        }

        sql = "INSERT INTO tbproduct(productTypeId,productBarcode,productName,productFund,productPrice,productExpire,dateAdd)"
                + " VALUES((SELECT productTypeId FROM tbproducttype WHERE productType = ?),?,?,?,?,?,NOW())";

        try {

            if (checkEmpty(this)) {

                PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                pre.setString(1, productType);
                pre.setString(2, productBarcode);
                pre.setString(3, productName);
                pre.setDouble(4, productFund);
                pre.setDouble(5, productPrice);
                pre.setInt(6, productExpire);

                if (pre.executeUpdate() != -1) {
                    Message.showInfoMessage("การบันทึกสำเร็จ", "เพิ่มรายการสินค้าแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถเพิ่มรายการสินค้า");
                }

            } else {
                Message.showMessageEmpty();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void updateData() {

        int rowSel = ProductHub.product.getTbProduct().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        String productBarcodeOld = ProductHub.product.getTbProduct().getValueAt(rowSel, 1).toString();
        double productFundOld = Util.fillterNumberToDouble(Util.fillterNumberToString(ProductHub.product.getTbProduct().getValueAt(rowSel, 3).toString()));
        double productPriceOld = Util.fillterNumberToDouble(Util.fillterNumberToString(ProductHub.product.getTbProduct().getValueAt(rowSel, 4).toString()));

        String productType = ProductHub.product.getCbxProductType().getSelectedItem().toString();
        String productBarcode = ProductHub.product.getTxtProductBarcode().getText();
        String productName = ProductHub.product.getTxtProductName().getText();
        double productFund = Double.parseDouble(ProductHub.product.getTxtProductFund().getText() + (!ProductHub.product.getTxtProductFundDecimal().getText().isEmpty() ? "." + ProductHub.product.getTxtProductFundDecimal().getText() : ""));
        double productPrice = Double.parseDouble(ProductHub.product.getTxtProductPrice().getText() + (!ProductHub.product.getTxtProductPriceDecimal().getText().isEmpty() ? "." + ProductHub.product.getTxtProductPriceDecimal().getText() : ""));
        int productExpire = ProductHub.product.getCbxProductExpire().getSelectedIndex();

        if (!productBarcode.equals(productBarcodeOld)) {
            Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขรหัสบาร์โค๊ดได้");
            return;
        }

        if (checkEmpty(this)) {

            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                boolean check[] = {true, true};
                //แก้ชื่อสินค้าทั้งหมดที่มีบาร์โค๊ดเดียวกันด้วยชื่อใหม่ก่อน
                String sql = "UPDATE tbproduct SET productTypeId = (SELECT productTypeId FROM tbproducttype WHERE productType = '" + productType + "'),productName = '" + productName + "',productExpire = '" + productExpire + "' WHERE productBarcode = '" + productBarcode + "'";

                try {
                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        check[0] = true;
                    } else {
                        check[0] = false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (productFund != productFundOld || productPrice != productPriceOld) { //ถ้าแก้ราคา

                    //เพิ่มข้อมูลสินค้าด้วยชื่อใหม่ราคาใหม่
                    sql = "INSERT INTO tbproduct(productTypeId,productBarcode,productName,productFund,productPrice,productExpire,dateAdd)"
                            + " VALUES((SELECT productTypeId FROM tbproducttype WHERE productType = ?),?,?,?,?,?,NOW())";

                    try {

                        if (checkEmpty(this)) {

                            PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                            pre.setString(1, productType);
                            pre.setString(2, productBarcode);
                            pre.setString(3, productName);
                            pre.setDouble(4, productFund);
                            pre.setDouble(5, productPrice);
                            pre.setInt(6, productExpire);

                            if (pre.executeUpdate() != -1) {
                                check[1] = true;
                            } else {
                                check[1] = false;
                            }

                        } else {
                            Message.showMessageEmpty();
                            return;
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                boolean pass = true;

                for (int i = 0; i < check.length; i++) {
                    if (!check[i]) {
                        pass = false;
                        break;
                    }

                    pass = true;
                }

                if (pass) {
                    Message.showInfoMessage("การแก้ไขสำเร็จ", "แก้ไขรายการสินค้าแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขรายการสินค้า");
                }

            } //ถ้าต้องการแก้ไข

        } else {
            Message.showMessageEmpty();
        }



    }

    @Override
    public void deleteData() {

        int rowSel = ProductHub.product.getTbProduct().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการลบ");
            return;
        }

        if (Message.showMessageDelete2Btn() == 0) { //ถ้าต้องการลบ

            String productName = ProductHub.product.getTbProduct().getValueAt(rowSel, 2).toString();

            String sql = "SELECT productId FROM tbproduct WHERE productName = '" + productName + "'";

            //นับว่าสินค้าชื่อนี้มีกี่ไอดี
            int count = Util.getRowCount(sql);
            //สร้างอาเรย์สตริงไว้เก็บไอดีสินค้าทั้งหมด
            String productId[] = new String[count];
            //เก็บหมายเลขสินค้าทั้งหมดไว้ในอาเรย์ที่สร้างไว้
            try {
                ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

                int row = 0;
                while (rs.next()) {
                    productId[row] = rs.getString(1);
                    row++;
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            //ถ้ามีไอดีใดไอดีหนึ่งของสินค้าชื่อนี้ถูกใช้งานอยู่จะไม่สามาถลบสินค้าชื่อนี้ได้
            for (int i = 0; i < productId.length; i++) {

                int a, b;

                String query = "SELECT productId FROM tbstock WHERE productId = '" + productId[i] + "'";
                a = Util.getRowCount(query);
                query = "SELECT productId FROM tbsell WHERE productId = '" + productId[i] + "'";
                b = Util.getRowCount(query);

                if (a > 0 || b > 0 ) {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ข้อมูลถูกใช้งานอยู่ ไม่สามารถลบได้");
                    return;
                }
            }

            //ถ้าไม่ได้ถูกใช้งานอยู่
            boolean check[] = new boolean[productId.length];
            for (int i = 0; i < productId.length; i++) {

                sql = "DELETE FROM tbproduct WHERE productId = '" + productId[i] + "'";

                try {
                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        check[i] = true;
                    } else {
                        check[i] = false;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ProductControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            boolean pass = true;
            for (int i = 0; i < check.length; i++) {

                if (!check[i]) {
                    pass = false;
                    break;
                }

                pass = true;
            }

            if (pass) {
                Message.showInfoMessage("การลบสำเร็จ", "ลบรายการสินค้าแล้ว");
                showData(true);
                Util.clearText(this);
            } else {
                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถลบรายการสินค้า");
            }

        } //ถ้าต้องการลบ
    }
}
