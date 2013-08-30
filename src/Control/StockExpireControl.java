/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.StockHub;
import Model.StockExpireModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.clearRow;
import static Utility.Util.wordFillter;
import View.FormStock;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author java-dev
 */
public class StockExpireControl implements ControlInterface {

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(StockHub.stock.getTxtStockExpirePage().getText());
        int perPage = Integer.parseInt(StockHub.stock.getCbxStockExpirePerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(StockHub.stock.getCbxStockExpirePerPage(), FormStock.vectorStockExpire.size()) == 1) {
                step[0] = 0;
                step[1] = FormStock.vectorStockExpire.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(StockHub.stock.getCbxStockExpirePerPage(), FormStock.vectorStockExpire.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(StockHub.stock.getCbxStockExpirePerPage(), FormStock.vectorStockExpire.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormStock.vectorStockExpire.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = StockHub.stock.getCbxStockExpireSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY b.productName ASC";
                break;
            case 1:
                sql += " ORDER BY a.lotId ASC";
                break;
            case 2:
                sql += " ORDER BY a.stockRemaining ASC";
                break;
            case 3:
                sql += " ORDER BY a.expireDate ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT a.stockId, b.productName, a.lotId, a.stockRemaining, a.expireDate FROM tbstock a INNER JOIN "
                + "tbproduct b ON a.productId = b.productId INNER JOIN tbwarning c ON a.stockId = c.stockId "
                + "WHERE a.stockRemaining >0 AND SUBDATE(a.expireDate, INTERVAL 7 DAY) <= NOW() AND c.warning = true";

        if (search) {

            int cbxSearch = StockHub.stock.getCbxStockExpireSearchFrom().getSelectedIndex();
            String word = StockHub.stock.getTxtStockExpireSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += " AND b.productName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += " AND a.stockRemaining >= '" + wordFillter(word) + "'";
                    break;
                case 2:
                    sql += " AND a.expireDate >= '" + wordFillter(word) + "'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormStock.vectorStockExpire = new Vector();
            while (rs.next()) {
                StockExpireModel stockExpire = new StockExpireModel();
                stockExpire.setStockId(rs.getInt(1));
                stockExpire.setProductName(rs.getString(2));
                stockExpire.setLotId(rs.getInt(3));
                stockExpire.setStockRemaining(rs.getInt(4));
                stockExpire.setStockExpire(rs.getString(5));
                FormStock.vectorStockExpire.add(stockExpire);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockExpireControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!StockHub.stock.getTxtStockExpireSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("stockexpire");

        int row = 0;
        int sortFrom = StockHub.stock.getCbxStockExpireSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    StockExpireModel stockExpire = (StockExpireModel) FormStock.vectorStockExpire.elementAt(i);

                    StockHub.stock.getModelStockExpire().addRow(new Object[0]);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockId(), row, 0);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getProductName(), row, 1);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getLotId(), row, 2);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockRemaining(), row, 3);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockExpire(), row, 4);

                    row++;
                }
                break;

            case 1:
                FormStock.vectorStockExpireInverse = new Vector();

                for (int i = FormStock.vectorStockExpire.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormStock.vectorStockExpireInverse.add(FormStock.vectorStockExpire.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    StockExpireModel stockExpire = (StockExpireModel) FormStock.vectorStockExpireInverse.elementAt(i);

                    StockHub.stock.getModelStockExpire().addRow(new Object[0]);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockId(), row, 0);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getProductName(), row, 1);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getLotId(), row, 2);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockRemaining(), row, 3);
                    StockHub.stock.getModelStockExpire().setValueAt(stockExpire.getStockExpire(), row, 4);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, StockHub.stock.getTxtStockExpirePage(), StockHub.stock.getCbxStockExpirePerPage(), FormStock.vectorStockExpire.size());
        Util.controlPreNext(StockHub.stock.getTxtStockExpirePage(), StockHub.stock.getBtnStockExpirePre(), StockHub.stock.getBtnStockExpireNext(), StockHub.stock.getCbxStockExpirePerPage(), FormStock.vectorStockExpire.size());

    }

    public void CancelWarning() {

        int rowSel = StockHub.stock.getTbStockExpire().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการยกเลิก");
            return;
        }

        if (Message.showMessageUpdate2Btn("cancelwarning") == 0) { //ถ้าต้องการยกเลิก
            String stockId = StockHub.stock.getTbStockExpire().getValueAt(rowSel, 0).toString();

            String sql = "UPDATE tbwarning SET warning = false WHERE stockId = '" + stockId + "'";
            int result = -1;

            try {
                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(StockExpireControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (result != -1) {
                Message.showInfoMessage("ผลการทำงาน", "ยกเลิกการเตือนแล้ว");
                showData(true);
            }
            
        }

    }

    public void cancelWarningAll() {
        
        if (Message.showMessageUpdate2Btn("cancelwarningall") == 0) { //ถ้าต้องการยกเลิกทั้งหมด
            
            String sql = "UPDATE tbwarning SET warning = false WHERE warning = true";
            
            int result = -1;
            try {
                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(StockExpireControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (result != -1) {
                Message.showInfoMessage("ผลการทำงาน", "ยกเลิกการเตือนทั้งหมดแล้ว");
                showData(true);
            }
            
        }
        
    }
    
    public void warningAll() {
        
         if (Message.showMessageUpdate2Btn("warningall") == 0) { //ถ้าต้องการเตือนทั้งหมด
             
             String sql = "UPDATE tbwarning SET warning = true WHERE warning = false";
            
            int result = -1;
            try {
                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(StockExpireControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (result != -1) {
                Message.showInfoMessage("ผลการทำงาน", "เปิดการเตือนทั้งหมดแล้ว");
                showData(true);
            }
             
         }
        
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
