/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.PromotionHub;
import Model.PromotionModel;
import Utility.DBFactory;
import Utility.Util;
import static Utility.Util.clearRow;
import static Utility.Util.wordFillter;
import View.FormPromotion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author java-dev
 */

//ใช้แสดงโปรโมชันทั้งหมด ทั้งที่หมดอายุแล้วและยังไม่หมดอายุ
//ใช้ PromotionModel ในการแสดงข้อมูลเหมือนกัน
public class PromotionViewControl implements ControlInterface {

    @Override
    public int[] dataLimit() {
        
        int[] step = new int[2];

        int curPage = Integer.parseInt(PromotionHub.promotion.getTxtPromotionViewPage().getText());
        int perPage = Integer.parseInt(PromotionHub.promotion.getCbxPromotionViewPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionViewPerPage(), FormPromotion.vectorPromotionView.size()) == 1) {
                step[0] = 0;
                step[1] = FormPromotion.vectorPromotionView.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionViewPerPage(), FormPromotion.vectorPromotionView.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(PromotionHub.promotion.getCbxPromotionViewPerPage(), FormPromotion.vectorPromotionView.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormPromotion.vectorPromotionView.size();

        }

        return step;
        
    }

    @Override
    public String dataSort() {

        int cbxSel = PromotionHub.promotion.getCbxPromotionViewSortBy().getSelectedIndex();

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
                + "d.promotionId LEFT JOIN tbplus e ON a.promotionId = e.promotionId";

        if (search) {

            int cbxSearch = PromotionHub.promotion.getCbxPromotionViewSearchFrom().getSelectedIndex();
            String word = PromotionHub.promotion.getTxtPromotionViewSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += " WHERE a.promotionName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += " WHERE a.strDate >= '" + wordFillter(word) + "'";
                    break;
                case 2:
                    sql += " WHERE a.endDate >= '" + wordFillter(word) + "'";
                    break;
            }
        }

        sql += " GROUP BY a.promotionName";

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  
        
        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormPromotion.vectorPromotionView = new Vector();
            while (rs.next()) {
                PromotionModel promotionView = new PromotionModel();
                promotionView.setPromotionId(rs.getInt(1));
                promotionView.setPromotionName(rs.getString(2));
                promotionView.setPromotionDetail(rs.getString(3));
                promotionView.setPromotionType((rs.getString(4).equals("Discount") ? "ส่วนลด" : "ของแถม"));
                promotionView.setConditionProduct(rs.getString(5));
                promotionView.setDiscount(rs.getString(6));
                promotionView.setPlusProduct(rs.getString(7));
                promotionView.setStartDate(rs.getString(8));
                promotionView.setEndDate(rs.getString(9));
                FormPromotion.vectorPromotionView.add(promotionView);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PromotionViewControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!PromotionHub.promotion.getTxtPromotionViewSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("promotionview");

        int row = 0;
        int sortFrom = PromotionHub.promotion.getCbxPromotionViewSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    PromotionModel promotionView = (PromotionModel) FormPromotion.vectorPromotionView.elementAt(i);

                    PromotionHub.promotion.getModelPromotionView().addRow(new Object[0]);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionId(), row, 0);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionName(), row, 1);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getPromotionDetail() == null ? "-" : promotionView.getPromotionDetail()), row, 2);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionType(), row, 3);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getConditionProduct(), row, 4);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getDiscount() == null ? "-" : promotionView.getDiscount()), row, 5);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getPlusProduct() == null ? "-" : promotionView.getPlusProduct()), row, 6);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getStartDate(), row, 7);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getEndDate(), row, 8);

                    row++;
                }
                break;

            case 1:
                FormPromotion.vectorPromotionViewInverse = new Vector();

                for (int i = FormPromotion.vectorPromotionView.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormPromotion.vectorPromotionViewInverse.add(FormPromotion.vectorPromotionView.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    PromotionModel promotionView = (PromotionModel) FormPromotion.vectorPromotionViewInverse.elementAt(i);

                    PromotionHub.promotion.getModelPromotionView().addRow(new Object[0]);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionId(), row, 0);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionName(), row, 1);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getPromotionDetail() == null ? "-" : promotionView.getPromotionDetail()), row, 2);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getPromotionType(), row, 3);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getConditionProduct(), row, 4);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getDiscount() == null ? "-" : promotionView.getDiscount()), row, 5);
                    PromotionHub.promotion.getModelPromotionView().setValueAt((promotionView.getPlusProduct() == null ? "-" : promotionView.getPlusProduct()), row, 6);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getStartDate(), row, 7);
                    PromotionHub.promotion.getModelPromotionView().setValueAt(promotionView.getEndDate(), row, 8);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, PromotionHub.promotion.getTxtPromotionViewPage(), PromotionHub.promotion.getCbxPromotionViewPerPage(), FormPromotion.vectorPromotionView.size());
        Util.controlPreNext(PromotionHub.promotion.getTxtPromotionViewPage(), PromotionHub.promotion.getBtnPromotionViewPre(), PromotionHub.promotion.getBtnPromotionViewNext(), PromotionHub.promotion.getCbxPromotionViewPerPage(), FormPromotion.vectorPromotionView.size());
    
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
