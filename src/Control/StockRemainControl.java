/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.StockHub;
import Model.StockRemainModel;
import Utility.DBFactory;
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
public class StockRemainControl implements ControlInterface {

    @Override
    public int[] dataLimit() {
        
        int[] step = new int[2];

        int curPage = Integer.parseInt(StockHub.stock.getTxtStockRemainPage().getText());
        int perPage = Integer.parseInt(StockHub.stock.getCbxStockRemainPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(StockHub.stock.getCbxStockRemainPerPage(), FormStock.vectorStockRemain.size()) == 1) {
                step[0] = 0;
                step[1] = FormStock.vectorStockRemain.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(StockHub.stock.getCbxStockRemainPerPage(), FormStock.vectorStockRemain.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(StockHub.stock.getCbxStockRemainPerPage(), FormStock.vectorStockRemain.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormStock.vectorStockRemain.size();

        }

        return step;
        
    }

    @Override
    public String dataSort() {

        int cbxSel = StockHub.stock.getCbxStockRemainSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY b.productName ASC";
                break;
            case 1:
                sql += " ORDER BY SUM(a.stockRemaining) ASC";
                break;
        }

        return sql;
    
    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT b.productName, SUM(a.stockRemaining) FROM tbstock a INNER JOIN tbproduct b ON a.productId = b.productId WHERE a.stockRemaining > 0";

        if (search) {

            int cbxSearch = StockHub.stock.getCbxStockRemainSearchFrom().getSelectedIndex();
            String word = StockHub.stock.getTxtStockRemainSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += " AND b.productName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += " AND a.stockRemaining >= '" + wordFillter(word) + "'";
                    break;
            }
        }

        sql += " GROUP BY b.productName";
        
        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormStock.vectorStockRemain = new Vector();
            while (rs.next()) {
                StockRemainModel stockRemain = new StockRemainModel();
                stockRemain.setProductName(rs.getString(1));
                stockRemain.setStockRemaining(rs.getInt(2));
                FormStock.vectorStockRemain.add(stockRemain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockRemainControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!StockHub.stock.getTxtStockRemainSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("stockremain");

        int row = 0;
        int sortFrom = StockHub.stock.getCbxStockRemainSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    StockRemainModel stockRemain = (StockRemainModel) FormStock.vectorStockRemain.elementAt(i);

                    StockHub.stock.getModelStockRemain().addRow(new Object[0]);
                    StockHub.stock.getModelStockRemain().setValueAt(stockRemain.getProductName(), row, 0);
                    StockHub.stock.getModelStockRemain().setValueAt(stockRemain.getStockRemaining(), row, 1);

                    row++;
                }
                break;

            case 1:
                FormStock.vectorStockRemainInverse = new Vector();

                for (int i = FormStock.vectorStockRemain.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormStock.vectorStockRemainInverse.add(FormStock.vectorStockRemain.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    StockRemainModel stockRemain = (StockRemainModel) FormStock.vectorStockRemainInverse.elementAt(i);

                    StockHub.stock.getModelStockRemain().addRow(new Object[0]);
                    StockHub.stock.getModelStockRemain().setValueAt(stockRemain.getProductName(), row, 0);
                    StockHub.stock.getModelStockRemain().setValueAt(stockRemain.getStockRemaining(), row, 1);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, StockHub.stock.getTxtStockRemainPage(), StockHub.stock.getCbxStockRemainPerPage(), FormStock.vectorStockRemain.size());
        Util.controlPreNext(StockHub.stock.getTxtStockRemainPage(), StockHub.stock.getBtnStockRemainPre(), StockHub.stock.getBtnStockRemainNext(), StockHub.stock.getCbxStockRemainPerPage(), FormStock.vectorStockRemain.size());
    
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
