/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.ProductHub;
import Model.ProductTypeModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.checkEmpty;
import static Utility.Util.clearRow;
import static Utility.Util.getRowCount;
import static Utility.Util.wordFillter;
import View.FormProduct;
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
public class ProductTypeControl implements ControlInterface {

    @Override
    public int[] dataLimit() {
        int[] step = new int[2];

        int curPage = Integer.parseInt(ProductHub.product.getTxtProductTypePage().getText());
        int perPage = Integer.parseInt(ProductHub.product.getCbxProductTypePerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(ProductHub.product.getCbxProductTypePerPage(), FormProduct.vectorProductType.size()) == 1) {
                step[0] = 0;
                step[1] = FormProduct.vectorProductType.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(ProductHub.product.getCbxProductTypePerPage(), FormProduct.vectorProductType.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(ProductHub.product.getCbxProductTypePerPage(), FormProduct.vectorProductType.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormProduct.vectorProductType.size();

        }

        return step;
    }

    @Override
    public String dataSort() {

        int cbxSel = ProductHub.product.getCbxProductTypeSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {

            case 0:
                sql += " ORDER BY productTypeId ASC";
                break;
            case 1:
                sql += " ORDER BY productType ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "";

        if (!search) {
            sql = "SELECT productTypeId,productType FROM tbproducttype";
        } else {
            int cbxSearch = ProductHub.product.getCbxProductTypeSearchFrom().getSelectedIndex();
            String word = ProductHub.product.getTxtProductTypeSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql = "SELECT productTypeId,productType FROM tbproducttype WHERE productType LIKE '%" + wordFillter(word) + "%'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormProduct.vectorProductType = new Vector();
            while (rs.next()) {
                ProductTypeModel productType = new ProductTypeModel();
                productType.setProductTypeId(rs.getInt(1));
                productType.setProductType(rs.getString(2));
                FormProduct.vectorProductType.add(productType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductTypeControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!ProductHub.product.getTxtProductTypeSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("producttype");

        int row = 0;
        int sortFrom = ProductHub.product.getCbxProductTypeSortFrom().getSelectedIndex();
        int[] step = dataLimit();
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    ProductTypeModel productType = (ProductTypeModel) FormProduct.vectorProductType.elementAt(i);

                    ProductHub.product.getModelProductType().addRow(new Object[0]);
                    ProductHub.product.getModelProductType().setValueAt(productType.getProductTypeId(), row, 0);
                    ProductHub.product.getModelProductType().setValueAt(productType.getProductType(), row, 1);

                    row++;
                }
                break;

            case 1:
                FormProduct.vectorProductTypeInverse = new Vector();

                for (int i = FormProduct.vectorProductType.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormProduct.vectorProductTypeInverse.add(FormProduct.vectorProductType.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    ProductTypeModel productType = (ProductTypeModel) FormProduct.vectorProductTypeInverse.elementAt(i);

                    ProductHub.product.getModelProductType().addRow(new Object[0]);
                    ProductHub.product.getModelProductType().setValueAt(productType.getProductTypeId(), row, 0);
                    ProductHub.product.getModelProductType().setValueAt(productType.getProductType(), row, 1);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, ProductHub.product.getTxtProductTypePage(), ProductHub.product.getCbxProductTypePerPage(), FormProduct.vectorProductType.size());
        Util.controlPreNext(ProductHub.product.getTxtProductTypePage(), ProductHub.product.getBtnProductTypePre(), ProductHub.product.getBtnProductTypeNext(), ProductHub.product.getCbxProductTypePerPage(), FormProduct.vectorProductType.size());

    }

    @Override
    public void insertData() {

        String productType = ProductHub.product.getTxtProductType_ProductType().getText();

        //ต้องการหาว่ามีสินค้าที่บาร์โค๊ดเดียวกันแล้วหรือไม่
        String sql = "SELECT productType FROM tbproducttype WHERE productType = '" + productType + "'";

        if (getRowCount(sql) > 0) {
            Message.showErrorMessage("พบข้อผิดพลาด", "มีประเภทสินค้านี้อยู่ในรายการแล้ว");
            return;
        }

        sql = "INSERT INTO tbproducttype(productType) VALUES('" + productType + "')";

        if (checkEmpty(this)) {

            try {

                int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                if (result != -1) {
                    Message.showInfoMessage("การบันทึกสำเร็จ", "เพิ่มรายการประเภทสินค้าแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถเพิ่มรายการประเภทสินค้า");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductTypeControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void updateData() {

        int rowSel = ProductHub.product.getTbProductType().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        String productTypeId = ProductHub.product.getTbProductType().getValueAt(rowSel, 0).toString();
        String productType = ProductHub.product.getTxtProductType_ProductType().getText();

        if (checkEmpty(this)) {

            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                String sql = "UPDATE tbproducttype SET productType = '" + productType + "' WHERE productTypeId = '" + productTypeId + "'";

                try {
                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        Message.showInfoMessage("การแก้ไขสำเร็จ", "แก้ไขรายการประเภทสินค้าแล้ว");
                        showData(true);
                        Util.clearText(this);
                    } else {
                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขรายการประเภทสินค้า");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(ProductTypeControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } //ถ้าต้องการแก้ไข

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void deleteData() {

        int rowSel = ProductHub.product.getTbProductType().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการลบ");
            return;
        }

        if (Message.showMessageDelete2Btn() == 0) { //ถ้าต้องการลบ

            String productTypeId = ProductHub.product.getTbProductType().getValueAt(rowSel, 0).toString();

            String sql = "SELECT productTypeId FROM tbproduct WHERE productTypeId = '" + productTypeId + "'";

            if (Util.getRowCount(sql) > 0) {
                Message.showErrorMessage("พบข้อผิดพลาด", "ข้อมูลถูกใช้งานอยู่ ไม่สามารถลบได้");
                return;
            }

            sql = "DELETE FROM tbproducttype WHERE productTypeId = '" + productTypeId + "'";

            try {
                int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                if (result != -1) {
                    Message.showInfoMessage("การลบสำเร็จ", "ลบรายการสินค้าแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถลบรายการสินค้า");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ProductTypeControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //ถ้าต้องการลบ

    }
}
