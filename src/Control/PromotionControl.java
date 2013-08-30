/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.ProductHub;
import Model.Hub.PromotionHub;
import Model.PromotionModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.clearRow;
import static Utility.Util.wordFillter;
import View.FormPromotion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author java-dev
 */
public class PromotionControl implements ControlInterface {

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(PromotionHub.promotion.getTxtPromotionPage().getText());
        int perPage = Integer.parseInt(PromotionHub.promotion.getCbxPromotionPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionPerPage(), FormPromotion.vectorPromotion.size()) == 1) {
                step[0] = 0;
                step[1] = FormPromotion.vectorPromotion.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionPerPage(), FormPromotion.vectorPromotion.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionPerPage(), FormPromotion.vectorPromotion.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormPromotion.vectorPromotion.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = PromotionHub.promotion.getCbxPromotionSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY a.promotionId ASC";
                break;
            case 2:
                sql += " ORDER BY a.promotionName ASC";
                break;
            case 3:
                sql += " ORDER BY a.promotionType ASC";
                break;
            case 4:
                sql += " ORDER BY a.strDate ASC";
                break;
            case 5:
                sql += " ORDER BY a.endDate ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT a.promotionId, a.promotionName, a.promotionDetail, a.promotionType, GROUP_CONCAT((SELECT CONCAT(INSERT(c.productName,(LENGTH(c.productName)"
                + "/3)+1,1,' '), sellAmount, INSERT(' ',1,1,'ชิ้น')) FROM tbproduct c WHERE b.productConditionBarcode = c.productBarcode LIMIT 0,1) "
                + "SEPARATOR ' + ') AS conditionProduct, (SELECT CONCAT(INSERT(c.productName,(LENGTH(c.productName)/3)+1,1,' '), d.discount , "
                + "INSERT(' ',1,1,' บาท')) FROM tbproduct c WHERE d.productDiscountBarcode = c.productBarcode LIMIT 0,1) AS discount, "
                + "(SELECT CONCAT(INSERT(c.productName,(LENGTH(c.productName)/3)+1,1,' '), plusAmount, INSERT(' ',1,1,'ชิ้น')) FROM tbproduct c "
                + "WHERE e.productPlusBarcode = c.productBarcode LIMIT 0,1) AS plusProduct, a.strDate, a.endDate FROM tbpromotion a "
                + "INNER JOIN tbproductsell b ON a.promotionId = b.promotionId LEFT JOIN tbdiscount d ON a.promotionId = "
                + "d.promotionId LEFT JOIN tbplus e ON a.promotionId = e.promotionId WHERE a.endDate>=NOW()";

        if (search) {

            int cbxSearch = PromotionHub.promotion.getCbxPromotionSearchFrom().getSelectedIndex();
            String word = PromotionHub.promotion.getTxtPromotionSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += " AND a.promotionName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += " AND a.strDate >= '" + wordFillter(word) + "'";
                    break;
                case 2:
                    sql += " AND a.endDate >= '" + wordFillter(word) + "'";
                    break;
            }
        }

        sql += " GROUP BY a.promotionName";

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormPromotion.vectorPromotion = new Vector();
            while (rs.next()) {
                PromotionModel promotion = new PromotionModel();
                promotion.setPromotionId(rs.getInt(1));
                promotion.setPromotionName(rs.getString(2));
                promotion.setPromotionDetail(rs.getString(3));
                promotion.setPromotionType((rs.getString(4).equals("Discount") ? "ส่วนลด" : "ของแถม"));
                promotion.setConditionProduct(rs.getString(5));
                promotion.setDiscount(rs.getString(6));
                promotion.setPlusProduct(rs.getString(7));
                promotion.setStartDate(rs.getString(8));
                promotion.setEndDate(rs.getString(9));
                FormPromotion.vectorPromotion.add(promotion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!PromotionHub.promotion.getTxtPromotionSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("promotion");

        int row = 0;
        int sortFrom = PromotionHub.promotion.getCbxPromotionSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    PromotionModel promotion = (PromotionModel) FormPromotion.vectorPromotion.elementAt(i);

                    PromotionHub.promotion.getModelPromotion().addRow(new Object[0]);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionId(), row, 0);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionName(), row, 1);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getPromotionDetail() == null ? "-" : promotion.getPromotionDetail()), row, 2);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionType(), row, 3);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getConditionProduct(), row, 4);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getDiscount() == null ? "-" : promotion.getDiscount()), row, 5);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getPlusProduct() == null ? "-" : promotion.getPlusProduct()), row, 6);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getStartDate(), row, 7);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getEndDate(), row, 8);

                    row++;
                }
                break;

            case 1:
                FormPromotion.vectorPromotionInverse = new Vector();

                for (int i = FormPromotion.vectorPromotion.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormPromotion.vectorPromotionInverse.add(FormPromotion.vectorPromotion.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    PromotionModel promotion = (PromotionModel) FormPromotion.vectorPromotionInverse.elementAt(i);

                    PromotionHub.promotion.getModelPromotion().addRow(new Object[0]);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionId(), row, 0);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionName(), row, 1);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getPromotionDetail() == null ? "-" : promotion.getPromotionDetail()), row, 2);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getPromotionType(), row, 3);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getConditionProduct(), row, 4);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getDiscount() == null ? "-" : promotion.getDiscount()), row, 5);
                    PromotionHub.promotion.getModelPromotion().setValueAt((promotion.getPlusProduct() == null ? "-" : promotion.getPlusProduct()), row, 6);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getStartDate(), row, 7);
                    PromotionHub.promotion.getModelPromotion().setValueAt(promotion.getEndDate(), row, 8);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, PromotionHub.promotion.getTxtPromotionPage(), PromotionHub.promotion.getCbxPromotionPerPage(), FormPromotion.vectorPromotion.size());
        Util.controlPreNext(PromotionHub.promotion.getTxtPromotionPage(), PromotionHub.promotion.getBtnPromotionPre(), PromotionHub.promotion.getBtnPromotionNext(), PromotionHub.promotion.getCbxPromotionPerPage(), FormPromotion.vectorPromotion.size());

    }

    @Override
    public void insertData() {

        if (Util.checkEmpty(this)) {

            //ส่วนของตารางโปรโมชัน
            String promotionName = PromotionHub.promotion.getTxtPromotionName().getText();
            String promotionDetail = PromotionHub.promotion.getTxtPromotionDetail().getText();
            String promotionType = (PromotionHub.promotion.getCbxPromotionType().getSelectedItem().toString().equals("ส่วนลด") ? "Discount" : "Plus");
            String startDate = (Integer.parseInt(PromotionHub.promotion.getCbxPromotionStartYear().getSelectedItem().toString())) - 543
                    + "-" + Util.checkNumMonth(PromotionHub.promotion.getCbxPromotionStartMonth().getSelectedItem().toString())
                    + "-" + PromotionHub.promotion.getCbxPromotionStartDate().getSelectedItem().toString();
            String endDate = (Integer.parseInt(PromotionHub.promotion.getCbxPromotionEndYear().getSelectedItem().toString())) - 543
                    + "-" + Util.checkNumMonth(PromotionHub.promotion.getCbxPromotionEndMonth().getSelectedItem().toString())
                    + "-" + PromotionHub.promotion.getCbxPromotionEndDate().getSelectedItem().toString();

            //ส่วนของตารางสินค้าเงื่อนไข
            int numCondition = PromotionHub.promotion.getCbxNumOfConditionProduct().getSelectedIndex() + 1;
            String barcodeCon1 = Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxAmount1().getSelectedItem().toString());
            String con1Num = PromotionHub.promotion.getTxtAmount1().getText();
            //ส่วนของเงื่อนไขสองสามต้องดักไว้ก่อนเพราะอาจจะไม่มีก็ได้
            String barcodecon2 = (PromotionHub.promotion.getCbxAmount2().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxAmount2().getSelectedItem().toString()) : null);
            String con2Num = (PromotionHub.promotion.getTxtAmount2().isEnabled() ? PromotionHub.promotion.getTxtAmount2().getText() : null);
            String barcodecon3 = (PromotionHub.promotion.getCbxAmount3().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxAmount3().getSelectedItem().toString()) : null);
            String con3Num = (PromotionHub.promotion.getTxtAmount3().isEnabled() ? PromotionHub.promotion.getTxtAmount3().getText() : null);
            String conArr[][] = {new String[]{barcodeCon1, con1Num}, new String[]{barcodecon2, con2Num}, new String[]{barcodecon3, con3Num}};

            //ส่วนของผลลัพธ์
            String barcodeDiscount = (PromotionHub.promotion.getCbxDiscountResult().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxDiscountResult().getSelectedItem().toString()) : null);
            String discount = (PromotionHub.promotion.getTxtDiscountResult().isEnabled() ? PromotionHub.promotion.getTxtDiscountResult().getText() + (!PromotionHub.promotion.getTxtDiscountResultDecimal().getText().isEmpty() ? "." + PromotionHub.promotion.getTxtDiscountResultDecimal().getText() : "") : null);
            String barcodePlus = (PromotionHub.promotion.getCbxPlusResult().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxPlusResult().getSelectedItem().toString()) : null);
            String plusNum = (PromotionHub.promotion.getTxtAmount4().isEnabled() ? PromotionHub.promotion.getTxtAmount4().getText() : null);

            //บันทึกตารางโปรโมชันก่อน
            String sql = "INSERT INTO tbpromotion VALUES(null,?,?,?,?,?)";
            int result = -1;

            try {
                PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);

                pre.setString(1, promotionName);
                pre.setString(2, promotionDetail);
                pre.setString(3, promotionType);
                pre.setString(4, startDate);
                pre.setString(5, endDate);

                result = pre.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            //ผ่านการบันทึกบิลแล้ว ก็มาบันทึกสินค้าเงื่อนไข
            if (result != -1) {

                boolean[] check = new boolean[numCondition];

                for (int i = 0; i < numCondition; i++) {
                    sql = "INSERT INTO tbproductsell VALUES(null,(SELECT promotionId FROM tbpromotion ORDER BY promotionId DESC LIMIT 0,1),?,?)";
                    try {
                        PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                        pre.setString(1, conArr[i][0]);
                        pre.setString(2, conArr[i][1]);

                        if (pre.executeUpdate() != -1) {
                            check[i] = true;
                        } else {
                            check[i] = false;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                boolean pass = true;

                for (int i = 0; i < check.length; i++) {
                    if (!check[i]) {
                        pass = false;
                        break;
                    }
                }

                //ผ่านการบันทึกเงื่อนไขแล้ว มาบันทึกผลลัพธ์ต่อ
                if (pass) {

                    switch (promotionType) {
                        case "Discount":
                            sql = "INSERT INTO tbdiscount VALUES(null,(SELECT promotionId FROM tbpromotion ORDER BY promotionId DESC LIMIT 0,1),?,?)";

                            try {
                                PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                                pre.setString(1, barcodeDiscount);
                                pre.setString(2, discount);

                                if (pre.executeUpdate() != -1) {
                                    Message.showInfoMessage("ผลการทำงาน", "บันทึกรายการโปรโมชันเรียบร้อย");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "Plus":
                            sql = "INSERT INTO tbplus VALUES(null,(SELECT promotionId FROM tbpromotion ORDER BY promotionId DESC LIMIT 0,1),?,?)";

                            try {
                                PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                                pre.setString(1, barcodePlus);
                                pre.setString(2, plusNum);

                                if (pre.executeUpdate() != -1) {
                                    Message.showInfoMessage("ผลการทำงาน", "บันทึกรายการโปรโมชันเรียบร้อย");
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                }
            }
        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void updateData() {

        int rowSel = PromotionHub.promotion.getTbPromotion().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        if (Util.checkEmpty(this)) {

            String promotionId = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 0).toString();
            String promotionType = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 3).toString();
            String promotionName = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 1).toString();
            String promotionDetail = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 2).toString();

            String startDate;
            if (!PromotionHub.promotion.getCbxPromotionStartYear().isEnabled()) {
                startDate = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 7).toString();
            } else {
                startDate = (Integer.parseInt(PromotionHub.promotion.getCbxPromotionStartYear().getSelectedItem().toString())) - 543
                        + "-" + Util.checkNumMonth(PromotionHub.promotion.getCbxPromotionStartMonth().getSelectedItem().toString())
                        + "-" + PromotionHub.promotion.getCbxPromotionStartDate().getSelectedItem().toString();
            }

            String endDate = (Integer.parseInt(PromotionHub.promotion.getCbxPromotionEndYear().getSelectedItem().toString())) - 543
                    + "-" + Util.checkNumMonth(PromotionHub.promotion.getCbxPromotionEndMonth().getSelectedItem().toString())
                    + "-" + PromotionHub.promotion.getCbxPromotionEndDate().getSelectedItem().toString();

            String barcodeDiscount = (PromotionHub.promotion.getCbxDiscountResult().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxDiscountResult().getSelectedItem().toString()) : null);
            String discount = (PromotionHub.promotion.getTxtDiscountResult().isEnabled() ? PromotionHub.promotion.getTxtDiscountResult().getText() + (!PromotionHub.promotion.getTxtDiscountResultDecimal().getText().isEmpty() ? "." + PromotionHub.promotion.getTxtDiscountResultDecimal().getText() : "") : null);
            String barcodePlus = (PromotionHub.promotion.getCbxPlusResult().isEnabled() ? Util.getProductBarcodeFromProductName(PromotionHub.promotion.getCbxPlusResult().getSelectedItem().toString()) : null);
            String plusNum = (PromotionHub.promotion.getTxtAmount4().isEnabled() ? PromotionHub.promotion.getTxtAmount4().getText() : null);

            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                //อัพเดตสองตารางคือตารางโปรโมชัน แล้วก็ตารางผลลัพ
                String sql = "UPDATE tbpromotion SET promotionName = '" + promotionName + "', promotionDetail = '" + promotionDetail + "', strDate = '" + startDate + "', endDate = '" + endDate + "' WHERE promotionId = '" + promotionId + "'";

                int result = -1;
                try {
                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (result != -1) {
                    switch (promotionType) {
                        case "ส่วนลด":
                            sql = "UPDATE tbdiscount SET productDiscountBarcode = '" + barcodeDiscount + "' WHERE promotionId = '" + promotionId + "'";
                            try {
                                if (DBFactory.getConnection().createStatement().executeUpdate(sql) != -1) {
                                    Message.showMessageUpdate();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "ของแถม":
                            sql = "UPDATE tbplus SET productPlusBarcode = '" + barcodePlus + "' WHERE promotionId = '" + promotionId + "'";
                            try {
                                if (DBFactory.getConnection().createStatement().executeUpdate(sql) != -1) {
                                    Message.showMessageUpdate();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                }

            } //ถ้าต้องการแก้ไข
        }

    }

    @Override
    public void deleteData() {

        int rowSel = PromotionHub.promotion.getTbPromotion().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการลบ");
            return;
        }

        String promotionId = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 0).toString();
        String promotionType = PromotionHub.promotion.getTbPromotion().getValueAt(rowSel, 3).toString();

        if (Message.showMessageDelete2Btn() == 0) { //ถ้าต้องการลบ

            String usedSql = "SELECT promotionId FROM tbpromotionactive WHERE promotionId = '" + promotionId + "'";
            int used = Util.getRowCount(usedSql);
            if (used > 0) {
                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถลบได้เนื่องจากโปรโมชันถูกใช้งานแล้ว");
                return;
            }

            //ลบโปรโมชัน
            String sql = "DELETE FROM tbpromotion WHERE promotionId = '" + promotionId + "'";
            int result = -1;

            try {
                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result != -1) {

                result = -1;
                sql = "DELETE FROM tbproductsell WHERE promotionId = '" + promotionId + "'";
                try {
                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (result != -1) {
                    switch (promotionType) {
                        case "ส่วนลด":
                            sql = "DELETE FROM tbdiscount WHERE promotionId = '" + promotionId + "'";
                            try {
                                if (DBFactory.getConnection().createStatement().executeUpdate(sql) != -1) {
                                    Message.showMessageDelete();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "ของแถม":
                            sql = "DELETE FROM tbplus WHERE promotionId = '" + promotionId + "'";
                            try {
                                if (DBFactory.getConnection().createStatement().executeUpdate(sql) != -1) {
                                    Message.showMessageDelete();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(PromotionControl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                }
            }

        } //ถ้าต้องการลบ

    }
}
