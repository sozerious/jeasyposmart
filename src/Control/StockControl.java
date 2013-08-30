/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.StockHub;
import Model.PromotionResultModel;
import Model.SellModel;
import Model.StockModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import static Utility.Util.clearRow;
import static Utility.Util.wordFillter;
import View.FormStock;
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
public class StockControl implements ControlInterface {

    SellControl sellControl;

    public StockControl() {
    }

    public StockControl(SellControl sellControl) {
        this.sellControl = sellControl;
    }

    @Override
    public int[] dataLimit() {

        int[] step = new int[2];

        int curPage = Integer.parseInt(StockHub.stock.getTxtStockPage().getText());
        int perPage = Integer.parseInt(StockHub.stock.getCbxStockPerPage().getSelectedItem().toString());

        if (curPage == 1) { // ถ้าเป็นหน้าที่ 1 หรืออาจจะมีหน้าเดียว ถ้าเป็นคำสั่ง limit มีแค่ไหนแสดงแค่นั้น แต่เป็นเวคเตอร์ต้องกรองข้อมูลว่ามีหน้าเดียวหรือเปล่า

            if (Util.checkTotalPage(StockHub.stock.getCbxStockPerPage(), FormStock.vectorStock.size()) == 1) {
                step[0] = 0;
                step[1] = FormStock.vectorStock.size();
            } else {
                step[0] = 0;
                step[1] = perPage;
            }

        } else if (curPage > 1 && curPage < Util.checkTotalPage(StockHub.stock.getCbxStockPerPage(), FormStock.vectorStock.size())) { // ถ้าไม่ใช่หน้า 1 และไม่ใช่หน้าสุดท้าย

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = pageGen + (perPage);

        } else if (curPage == Util.checkTotalPage(StockHub.stock.getCbxStockPerPage(), FormStock.vectorStock.size())) { // ถ้าเป็นหน้าสุดท้ายหรือมีหน้าเดียว

            int pageGen = perPage * (curPage - 1);
            step[0] = pageGen;
            step[1] = FormStock.vectorStock.size();

        }

        return step;

    }

    @Override
    public String dataSort() {

        int cbxSel = StockHub.stock.getCbxStockSortBy().getSelectedIndex();

        String sql = "";

        switch (cbxSel) {
            case 0:
                sql += " ORDER BY b.productName ASC";
                break;
            case 1:
                sql += " ORDER BY a.lotId ASC";
                break;
            case 2:
                sql += " ORDER BY a.stockAmount ASC";
                break;
            case 3:
                sql += " ORDER BY a.stockRemaining ASC";
                break;
            case 4:
                sql += " ORDER BY a.stockDate ASC";
                break;
            case 5:
                sql += " ORDER BY a.expireDate ASC";
                break;
        }

        return sql;

    }

    @Override
    public void putDataToVector(boolean search) {

        String sql = "SELECT a.stockId, b.productName, a.lotId, a.stockAmount, a.stockRemaining, a.stockDate, a.expireDate "
                + "FROM tbstock a INNER JOIN tbproduct b ON a.productId = b.productId WHERE a.stockRemaining > 0";

        if (search) {

            int cbxSearch = StockHub.stock.getCbxStockSearchFrom().getSelectedIndex();
            String word = StockHub.stock.getTxtStockSearch().getText();

            switch (cbxSearch) {
                case 0:
                    sql += " AND b.productName LIKE '%" + wordFillter(word) + "%'";
                    break;
                case 1:
                    sql += " AND a.stockAmount >= '" + wordFillter(word) + "'";
                    break;
                case 2:
                    sql += " AND a.stockRemaining >= '" + wordFillter(word) + "'";
                    break;
                case 3:
                    sql += " AND a.stockDate >= '" + wordFillter(word) + "'";
                    break;
                case 4:
                    sql += " AND a.expireDate >= '" + wordFillter(word) + "'";
                    break;
            }
        }

        sql += dataSort(); //บวกด้วย sql เรียงข้อมูล  

        try {
            //เสร็จแล้วจับยัดใส่ vector
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            FormStock.vectorStock = new Vector();
            while (rs.next()) {
                StockModel stock = new StockModel();
                stock.setStockId(rs.getInt(1));
                stock.setProductName(rs.getString(2));
                stock.setLotId(rs.getInt(3));
                stock.setStockAmount(rs.getInt(4));
                stock.setStockRemaining(rs.getInt(5));
                stock.setStockDate(rs.getString(6));
                stock.setStockExpire(rs.getString(7));
                FormStock.vectorStock.add(stock);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void showData(boolean update) {

        if (update) {
            if (!StockHub.stock.getTxtStockSearch().getText().isEmpty()) {
                putDataToVector(true);
            } else {
                putDataToVector(false);
            }
        }

        clearRow("stock");

        int row = 0;
        int sortFrom = StockHub.stock.getCbxStockSortFrom().getSelectedIndex();
        int[] step = dataLimit();

        switch (sortFrom) {
            case 0:
                for (int i = step[0]; i < step[1]; i++) {

                    StockModel stock = (StockModel) FormStock.vectorStock.elementAt(i);

                    StockHub.stock.getModelStock().addRow(new Object[0]);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockId(), row, 0);
                    StockHub.stock.getModelStock().setValueAt(stock.getProductName(), row, 1);
                    StockHub.stock.getModelStock().setValueAt(stock.getLotId(), row, 2);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockAmount(), row, 3);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockRemaining(), row, 4);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockDate(), row, 5);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockExpire(), row, 6);

                    row++;
                }
                break;

            case 1:
                FormStock.vectorStockInverse = new Vector();

                for (int i = FormStock.vectorStock.size() - 1; i >= 0; i--) { //ย้ายข้อมูลกลับด้าน
                    FormStock.vectorStockInverse.add(FormStock.vectorStock.elementAt(i));
                }

                for (int i = step[0]; i < step[1]; i++) {

                    StockModel stock = (StockModel) FormStock.vectorStockInverse.elementAt(i);

                    StockHub.stock.getModelStock().addRow(new Object[0]);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockId(), row, 0);
                    StockHub.stock.getModelStock().setValueAt(stock.getProductName(), row, 1);
                    StockHub.stock.getModelStock().setValueAt(stock.getLotId(), row, 2);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockAmount(), row, 3);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockRemaining(), row, 4);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockDate(), row, 5);
                    StockHub.stock.getModelStock().setValueAt(stock.getStockExpire(), row, 6);

                    row++;
                }
                break;
        }

        Util.checkCurrentPage(this, StockHub.stock.getTxtStockPage(), StockHub.stock.getCbxStockPerPage(), FormStock.vectorStock.size());
        Util.controlPreNext(StockHub.stock.getTxtStockPage(), StockHub.stock.getBtnStockPre(), StockHub.stock.getBtnStockNext(), StockHub.stock.getCbxStockPerPage(), FormStock.vectorStock.size());

    }

    @Override
    public void insertData() {

        if (Util.checkEmpty(this)) {

            String productId = Util.getLastProductIdFromProductName(StockHub.stock.getCbxStockProductName().getSelectedItem().toString());
            String sqlLotId = "SELECT COUNT(lotId)+1 FROM tbstock WHERE productId = '" + productId + "'";
            String lotId = Util.getStringResult(sqlLotId);
            String stockAmount = StockHub.stock.getTxtStockAmount().getText();
            String stockRemaining = stockAmount;
            String expireDate = (StockHub.stock.getCbxStockExpireDate().isEnabled() ? (Integer.parseInt(StockHub.stock.getCbxStockExpireYear().getSelectedItem().toString()) - 543)
                    + "-" + Util.checkNumMonth(StockHub.stock.getCbxStockExpireMonth().getSelectedItem().toString()) + "-" + StockHub.stock.getCbxStockExpireDate().getSelectedItem().toString() : null);

            if (Integer.parseInt(stockAmount) < 1) {
                Message.showWarningMessage("พบข้อผิดพลาด", "จำนวนสินค้าต้องไม่น้อยกว่า 1");
                return;
            }
            
            String sql = "INSERT INTO tbstock VALUES(null,?,?,?,?,NOW(),?)";
            int result = -1;

            try {
                PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
                pre.setString(1, productId);
                pre.setString(2, lotId);
                pre.setString(3, stockAmount);
                pre.setString(4, stockRemaining);
                pre.setString(5, expireDate);

                result = pre.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result != -1) {
                
                int resultWarning = -1;
                sql = "INSERT INTO tbwarning VALUES(null,(SELECT stockId FROM tbstock ORDER BY stockId DESC LIMIT 0,1),true)";
                try {
                    resultWarning = DBFactory.getConnection().createStatement().executeUpdate(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (resultWarning != -1) {
                    Message.showInfoMessage("การบันทึกสำเร็จ", "เพิ่มสินค้าลงสต๊อกแล้ว");
                    showData(true);
                    Util.clearText(this);
                } else {
                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกรายการเตือนสินค้าหมดอายุ");
                }
            } else {
                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถเพิ่มสินค้าลงสต๊อก");
            }
        } else {
            Message.showMessageEmpty();
        }
    }

    @Override
    public void updateData() {

        int rowSel = StockHub.stock.getTbStock().getSelectedRow();

        if (rowSel == -1) {
            Message.showWarningMessage("พบข้อผิดพลาด", "โปรดเลือกรายการที่ต้องการแก้ไข");
            return;
        }

        String StockAmountOld = StockHub.stock.getTbStock().getValueAt(rowSel, 3).toString();
        String StockRemainingOld = StockHub.stock.getTbStock().getValueAt(rowSel, 4).toString(); 

        if (!StockAmountOld.equals(StockRemainingOld)) {
            Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขได้ เนื่องจากสต๊อกถูกใช้งานแล้ว");
            return;
        }

        if (Util.checkEmpty(this)) {

            String stockId = StockHub.stock.getTbStock().getValueAt(rowSel, 0).toString();
            String stockAmount = StockHub.stock.getTxtStockAmount().getText();
            String stockRemaining = stockAmount;
            String expireDate = (StockHub.stock.getCbxStockExpireDate().isEnabled() ? (Integer.parseInt(StockHub.stock.getCbxStockExpireYear().getSelectedItem().toString()) - 543)
                    + "-" + Util.checkNumMonth(StockHub.stock.getCbxStockExpireMonth().getSelectedItem().toString()) + "-" + StockHub.stock.getCbxStockExpireDate().getSelectedItem().toString() : null);

            if (Integer.parseInt(stockAmount) < 1) {
                Message.showWarningMessage("พบข้อผิดพลาด", "จำนวนสินค้าต้องไม่น้อยกว่า 1");
                return;
            }
            
            if (Message.showMessageUpdate2Btn() == 0) { //ถ้าต้องการแก้ไข

                String sql = "UPDATE tbstock SET stockAmount = '" + stockAmount + "', stockRemaining = '" + stockRemaining + "', expireDate = '" + expireDate + "' WHERE stockId = '" + stockId + "'";

                try {

                    int result = DBFactory.getConnection().createStatement().executeUpdate(sql);

                    if (result != -1) {
                        Message.showInfoMessage("การแก้ไขสำเร็จ", "แก้ไขข้อมูลสต๊อกสินค้าแล้ว");
                        showData(true);
                        Util.clearText(this);
                    } else {
                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถแก้ไขข้อมูลสต๊อกสินค้า");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } //ถ้าต้องการแก้ไข

        } else {
            Message.showMessageEmpty();
        }

    }

    @Override
    public void deleteData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //เช็คว่ามีสินค้าพอให้ตัดสต๊อกหรือไม่
    public Vector<CutOffStockModel> resultCutOffStock(SellModel sell) {

        Vector<CutOffStockModel> stock = new Vector(); //สร้างเวคเตอร์ไว้เก็บค่า

        int amount = 0;
        int sellAmount = sell.getSellAmount(); //เก็บจำนวนที่ยังขาดอยู่
        String barcode = sell.getProductBarcode();
        int round = 0; //ตัวบอกรอบ
        ResultSet rs;

        //หาสินค้าที่มีบาร์โค๊ดนี้ที่จำนวนสินค้ายังไม่หมด เรียงตามเลขสินค้าและเลขล๊อต
        String sql = "SELECT a.productId, a.lotId, b.productBarcode, b.productFund, a.stockRemaining FROM tbstock a "
                + "INNER JOIN tbproduct b ON a.productId = b.productId WHERE b.productBarcode = '" + barcode + "' AND "
                + "a.stockRemaining > 0 ORDER BY a.productId, a.lotId LIMIT " + round + ",1 ";

        try {

            do {
//                System.out.println("amount : " + amount);
//                System.out.println("sellAmount : " + sellAmount);
//                System.out.println("barcode : " + barcode);
//                System.out.println("round : " + round);
//                System.out.println("sql : " + sql);
//                System.out.println("\n");
                rs = DBFactory.getConnection().createStatement().executeQuery(sql);

                while (rs.next()) {
                    int stockRemain = rs.getInt(5); //เก็บรีเมนนิ่งของผลลัพธ์แต่ละแถว
//                    System.out.println("stockRemain : " + stockRemain);

                    if (stockRemain >= sellAmount) { //ถ้าสต๊อกที่เหลือมากกว่าหรือเท่ากับจำนวนที่ซื้อและตัวผ่านยังเป็นเท็จให้เพิ่มใส่เวคเตอร์แล้วส่งออกไปเลย
                        stock.add(new CutOffStockModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getInt(5)));
                        return stock;
                    } else if (stockRemain < sellAmount) { //ถ้าเหลือน้อยกว่า

                        if (amount + stockRemain < sell.getSellAmount()) { //ถ้ารวมกันแล้วยังเหลือน้อยกว่าอยู่ก็ให้รวมยอดกันไปเรื่อยๆ
                            amount += stockRemain; //รวมยอด
                            sellAmount -= stockRemain; //ตัดยอดที่ต้องการออกด้วยจำนวนสต๊อกที่ได้

                            stock.add(new CutOffStockModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getInt(5))); //ก็เพิ่มสต๊อกเข้าไปในรายการ
                            round++;
                            sql = "SELECT a.productId, a.lotId, b.productBarcode, b.productFund, a.stockRemaining FROM tbstock a "
                                    + "INNER JOIN tbproduct b ON a.productId = b.productId WHERE b.productBarcode = '" + barcode + "' AND "
                                    + "a.stockRemaining > 0 ORDER BY a.productId, a.lotId LIMIT " + round + ",1 ";
                            if (Util.getRowCount(sql) < 1) {
                                return null;
                            }

                        } else { //ถ้ารวมกันได้พอแล้ว คือเท่ากับหรือมากกว่า
                            stock.add(new CutOffStockModel(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getInt(5)));
                            return stock;
                        }

                    } //จบ if มากกว่าน้อยกว่า
                } //จบ while rs.next()

            } while (Util.getRowCount(sql) == 1);

        } catch (SQLException ex) {
            Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //กันเหนียว จะหลุดมาตรงนี้ต่อเมื่อหมดผลลัพธ์แต่เราไม่รู้ว่าจำนวนสต๊อกที่ได้เพียงพอหรือยัง จึงต้องตรวจสอบจากจำนวนความต้องการที่เหลือ
        return (sellAmount > 0 ? null : stock); //ถ้าหลุดมาถึงตรงกันนี้ ถ้า sellAmount ยังมากกว่า 0 คือมีสินค้าไม่พอขายให้รีเทิร์น null

    }

    //ตรวจสอบว่ามีของพร้อมตัดสต๊อกหรือเปล่า
    private boolean checkCutOffStock() {

        boolean check = true; //ตั้งเป็น true ไว้ ถ้าไม่มีอะไรผิดพลาดก็ยังเป็น true เหมือนเดิม

        //======= ตรวจสอบเซลแบคอัพ =======
//        System.out.println(sellControl.getVectorSellBackup().size());
        for (int i = 0; i < sellControl.getVectorSellBackup().size(); i++) {

            //ส่งรายการสินค้าที่ซื้อไปที่ละรายการ
            SellModel sellBackup = (SellModel) sellControl.getVectorSellBackup().elementAt(i);
            //======= ชื่อสินค้าที่อยู่ในเซลแบคอัพ =======
//            System.out.println(sell.getProductName());
            if (resultCutOffStock(sellBackup) == null) { //ถ้ามีอะไรใดอันหนึ่งเป็น null คือสินค้าไม่พอให้ตัดยอด ให้เตือนแล้วรีเทิร์น false เลย
                Message.showErrorMessage("พอข้อผิดพลาด", "สินค้า \"" + sellBackup.getProductName() + "\" ในสต๊อกไม่เพียงพอต่อการจำหน่าย");
                check = false;
                break;
            }

        }

        return check; //ส่งออกผลลัพธ์ว่าผ่านหรือไม่ผ่าน

    }

    //ตัดยอดสต๊อก
    //<editor-fold defaultstate="collapsed" desc="cutOffStock">
    //    public boolean cutOffStock() {
    //
    //        if (checkCutOffStock()) { //ถ้าเป็น true คือมียอดพอให้ตัด ก็เพิ่มหมายเลขบิลเพื่อใช้อ้างอิง
    //
    //            if (!sellControl.insertBill()) { //ถ้าบันทึกบิลไม่ผ่าน
    //                Message.showWarningMessage("พบข้อผิดพลาด", "การบันทึกบิลรายการผิดพลาด");
    //                return false;
    //            } else { //เพิ่มบิลถ้าผ่านจึงทำต่อไป
    //
    //                for (int i = 0; i < sellControl.getVectorSellBackup().size(); i++) { //ในแต่ละสินค้า
    //
    //                    //ส่งรายการสินค้าที่ซื้อไปที่ละรายการ
    //                    SellModel sellBackup = (SellModel) sellControl.getVectorSellBackup().elementAt(i);
    //                    Vector<CutOffStockModel> cutOff = resultCutOffStock(sellBackup);
    //                    int sellBackupAmount = sellBackup.getSellAmount(); //เก็บจำนวนที่ขายแบ๊คอัพไว้ก่อน
    //
    //                    int index = -1;
    //                    int sellAmount = 0; //เก็บจำนวนที่ขายไว้ก่อน
    //                    SellModel sell = null;
    //
    //                    //<editor-fold defaultstate="collapsed" desc="for ตรวจสอบว่าเป็นสินค้าที่ยังเหลืออยู่ในรายการซื้อหรือเปล่า">
    //                    for (int j = 0; j < sellControl.getVectorSell().size(); j++) { //วนเซลเพื่อเทียบกับเซลแบ็คอัพว่าสินค้านี้ยังเหลือในรายการขายหรือเปล่า
    //
    //                        //เทียบกับรายการขายว่าตัดโปรแล้วยังเหลือที่เป็นสินค้าขายหรือเปล่า ถ้าเหลือ insertBill ถ้าไม่เหลือไม่ต้อง insertBill
    //                        SellModel sellList = (SellModel) sellControl.getVectorSell().elementAt(j);
    //
    //                        if (sellBackup.getProductBarcode().equals(sellList.getProductBarcode())) { //ถ้ายังเหลือเป็นสินค้าขายอยู่
    //
    //                            index = j;
    //                            sellAmount = sellList.getSellAmount(); //ถ้ามีตรงกันให้เก็บจำนวนที่เป็นการซื้อจริงๆไว้ด้วย เพื่อบันทึกลงตารางการขาย
    //                            sell = sellList;
    //
    //                        }
    //
    //                    }
    //                    //</editor-fold>
    //
    //                    for (int j = 0; j < cutOff.size(); j++) {
    //
    //                        CutOffStockModel cutStock = cutOff.elementAt(j); //จำนวนที่มีในสต๊อกแต่ละล๊อต
    //
    //                        if (cutStock.getStockRemaining() >= sellBackupAmount) { //ถ้าจำนวนที่มีอยู่ในสต๊อกมากกว่าหรือเท่ากับ จำนวนทั้งหมดที่ต้องหัก
    //
    //                            //ตัดสต๊อก (เอาเลขโปรดักเลขล๊อตมาที่สต๊อกด้วย sellAmount ได้เลย)และ insertSell ได้เลย
    //
    //                            String sql = "UPDATE tbstock set stockRemaining = '" + (cutStock.getStockRemaining() - sellBackupAmount) + "' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
    //                            int result = -1;
    //                            try {
    //                                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
    //                            } catch (SQLException ex) {
    //                                Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
    //                            }
    //                            if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
    //
    //                                if (index != -1) { //ถ้ามีในรายการขายให้ insertSell ถ้าไม่มีก็ไม่ต้อง
    //
    //                                    if (sellControl.insertSell(cutStock.getProductId(), sell.getProductId(), sell.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productfund", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sell.getProductId())), sellAmount)) {
    //                                        System.out.println("บันทึกเซลส่วนมากกว่า ผ่านฉลุย..........");
    //                                    } else {
    //                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนมากกว่าได้");
    //                                        //===========
    //                                        return false;
    //                                    }
    //                                }
    //                            } else {
    //                                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนมากกว่าได้");
    //                                //===========
    //                                return false;
    //                            }
    //
    //                        } else { //ถ้าน้อยกว่าให้ตัดเท่าที่ตัดได้ คือตัดให้เหลือ 0 แล้วหักลบ sellAmount ออกด้วยค่าที่ตัด เสร็จแล้วก็ insertSell ตามปกติ
    //
    //                            String sql = "UPDATE tbstock set stockRemaining = '0' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
    //                            sellBackupAmount -= cutStock.getStockRemaining(); //จำนวนทั้งหมดที่ต้องหัก ลบออกด้วยจำนวนสต๊อกที่มีในขณะนั้น
    //                            System.out.println("sellAmount - stockRemaining : " + sellAmount + "-" + cutStock.getStockRemaining() + " = " + (sellAmount - cutStock.getStockRemaining()) + "\n");
    //                            sellAmount -= cutStock.getStockRemaining(); //
    //
    //                            int result = -1;
    //                            try {
    //                                result = DBFactory.getConnection().createStatement().executeUpdate(sql);
    //                            } catch (SQLException ex) {
    //                                Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
    //                            }
    //                            if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
    //
    //                                if (index != -1) { //ถ้ามีในรายการขายให้ insertSell ถ้าไม่มีก็ไม่ต้อง
    //
    //                                    if (sellControl.insertSell(cutStock.getProductId(), sell.getProductId(), sell.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productprice", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sell.getProductId())), cutStock.stockRemaining)) {
    //                                        System.out.println("บันทึกเซลส่วนน้อยกว่า ผ่านฉลุย..........");
    //                                    } else {
    //                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนน้อยกว่าได้");
    //                                        //===========
    //                                        return false;
    //                                    }
    //                                }
    //                            } else {
    //                                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนน้อยกว่าได้");
    //                                //===========
    //                                return false;
    //                            }
    //                        }
    //                    } //จบ for แต่ละสต๊อกของแต่ละสินค้า
    //                } //จบ for แต่ละสินค้าก่อนตัดยอด
    //            } //จบผ่านบิล
    //
    //            if (sellControl.insertPromotionActive()) {
    //                System.out.println("บันทึกโปรโมชั่นก็ผ่านฉลุย....................");
    //                Message.showInfoMessage("ผลการทำงาน", "บันทึกรายการขายเรียบร้อย");
    //                return true;
    //            } else {
    //                Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกโปรโมชั่นได้");
    //                return false;
    //            }
    //        } else {
    //            return false;
    ////            Message.showErrorMessage("พอข้อผิดพลาด", "สินค้าในสต๊อกไม่เพียงพอต่อการจำหน่าย");
    //        }
    //    }
    //</editor-fold>
    public boolean cutOffStock() {

        if (checkCutOffStock()) { //ถ้าเป็น true คือมียอดพอให้ตัด ก็เพิ่มหมายเลขบิลเพื่อใช้อ้างอิง
            if (!sellControl.insertBill()) { //ถ้าบันทึกบิลไม่ผ่าน
                Message.showWarningMessage("พบข้อผิดพลาด", "การบันทึกบิลรายการผิดพลาด");
                return false;
            } else { //เมื่อบันทึกบิลผ่าน
                Object[][] sellDetail = getSellDetail();
                boolean pass[] = new boolean[sellControl.getVectorSellBackup().size()];

                for (int i = 0; i < sellControl.getVectorSellBackup().size(); i++) { //ในแต่ละสินค้า

                    SellModel sellBackup = (SellModel) sellControl.getVectorSellBackup().elementAt(i);
                    Vector<CutOffStockModel> cutOff = new Vector<>();
                    int sellBackupAmount = sellBackup.getSellAmount();
                    //มีทางเป็นไปได้ 3 อย่างคือ
                    //1 เป็นสินค้าในรายการขายอย่างเดียว จะส่งค่าออกมาเป็น 0
                    //2 เป็นสินค้าในรายการของแถมอย่างเดียว จะส่งค่า -1 ออกมาเพราะไม่เหลืออยู่ในรายการขายแล้ว
                    //3 เป็นสินค้าที่คละกันอยู่ทั้งสองอย่าง จะส่งค่าออกมามากกว่า 0
                    int check = checkFromGetSellDetail(sellBackup, sellDetail);

                        //<editor-fold defaultstate="collapsed" desc="check == 0">
                    if (check == 0) { //1 เป็นสินค้าในรายการขายอย่างเดียว จะส่งค่าออกมาเป็น 0
//                        System.out.println("Working at check == 0");
                        cutOff = resultCutOffStock(sellBackup);
                        for (int j = 0; j < cutOff.size(); j++) {
                            CutOffStockModel cutStock = cutOff.elementAt(j);

                            //ถ้าสต๊อกที่มีอยู่มากกว่าหรือเท่ากับ
                            if (cutStock.getStockRemaining() >= sellBackupAmount) {
                                String sql = "UPDATE tbstock set stockRemaining = '" + (cutStock.getStockRemaining() - sellBackupAmount) + "' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย

                                    if (sellControl.insertSell(cutStock.getProductId(), sellBackup.getProductId(), sellBackup.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productfund", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sellBackup.getProductId())), sellBackupAmount)) {
//                                        System.out.println("บันทึกเซลส่วนมากกว่า ผ่านฉลุย..........");
                                        pass[i] = true;
                                    } else {
                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนมากกว่าได้");
                                        pass[i] = false;
                                    }
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนมากกว่าได้");
                                }
                                //ถ้าสต๊อกที่มีอยู่น้อยกว่า
                            } else {
                                String sql = "UPDATE tbstock set stockRemaining = '0' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                sellBackupAmount -= cutStock.getStockRemaining(); //จำนวนทั้งหมดที่ต้องหัก ลบออกด้วยจำนวนสต๊อกที่มีในขณะนั้น
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    if (sellControl.insertSell(cutStock.getProductId(), sellBackup.getProductId(), sellBackup.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productfund", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sellBackup.getProductId())), cutStock.stockRemaining)) {
//                                        System.out.println("บันทึกเซลส่วนน้อยกว่า ผ่านฉลุย..........");
                                        pass[i] = true;
                                    } else {
                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนน้อยกว่าได้");
                                        pass[i] = false;
                                    }
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนน้อยกว่าได้");
                                }
                            }

                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="check == -1">
                    } else if (check == -1) { //2 เป็นสินค้าในรายการของแถมอย่างเดียว จะส่งค่า -1 ออกมาเพราะไม่เหลืออยู่ในรายการขายแล้ว
//                        System.out.println("Working at check == -1");
                        cutOff = resultCutOffStock(sellBackup);
                        for (int j = 0; j < cutOff.size(); j++) {
                            CutOffStockModel cutStock = cutOff.elementAt(j);

                            //ถ้าสต๊อกที่มีอยู่มากกว่าหรือเท่ากับ
                            if (cutStock.getStockRemaining() >= sellBackupAmount) {
                                String sql = "UPDATE tbstock set stockRemaining = '" + (cutStock.getStockRemaining() - sellBackupAmount) + "' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    pass[i] = true;
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนมากกว่าได้");
                                    pass[i] = false;
                                }
                                //ถ้าสต๊อกที่มีอยู่น้อยกว่า
                            } else {
                                String sql = "UPDATE tbstock set stockRemaining = '0' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                sellBackupAmount -= cutStock.getStockRemaining(); //จำนวนทั้งหมดที่ต้องหัก ลบออกด้วยจำนวนสต๊อกที่มีในขณะนั้น
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    pass[i] = true;
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนน้อยกว่าได้");
                                    pass[i] = false;
                                }
                            }
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="check > 0">
                    } else if (check > 0) { //3 เป็นสินค้าที่คละกันอยู่ทั้งสองอย่าง จะส่งค่าออกมามากกว่า 0 คือสวนเกินที่ถูกตัดไปเป็นสินค้าของแถม
//                        System.out.println("Working at check > 0 check = " + check);
                        //============================================ ส่วนที่เป็นสินค้าแถมให้ตัดสต๊อกอย่างเดียวก่อน ============================================
                        //ทำตรงที่ตัดสต๊อดอย่างเดียวก่อน แล้วก็ตัดยอดในสต๊อก Remaining ออกด้วย
                        //ให้ sellBackupAmount = check คือส่วนที่เกินมาจากราคาขาย หมายถึงเป็นสินค้าของแถม ให้ตัดยอดอย่างเดียวก่อน
                        
//                        System.out.println("===== ทำส่วนแรก =====");
                        cutOff = resultCutOffStock(sellBackup);
                        int sellBackupAmount1 = check;
                        
                        for (int j = 0; j < cutOff.size(); j++) {
                            CutOffStockModel cutStock = cutOff.elementAt(j);
//                            System.out.println("stockRemaining = " + cutStock.getStockRemaining());
//                            System.out.println("sellBackupAmount1 = " + sellBackupAmount1);
                            //ถ้าสต๊อกที่มีอยู่มากกว่าหรือเท่ากับ
                            if (cutStock.getStockRemaining() >= sellBackupAmount1 && sellBackupAmount1>0) {
//                                System.out.println("มันมากกว่า......");
                                String sql = "UPDATE tbstock set stockRemaining = '" + (cutStock.getStockRemaining() - sellBackupAmount1) + "' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                sellBackupAmount1 = 0;
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    pass[i] = true;
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนมากกว่าได้");
                                    pass[i] = false;
                                }
                                //ถ้าสต๊อกที่มีอยู่น้อยกว่า
                            } else if(cutStock.getStockRemaining() < sellBackupAmount1 && sellBackupAmount1>0){
//                                System.out.println("มันน้อยกว่า......");
                                String sql = "UPDATE tbstock set stockRemaining = '0' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
                                sellBackupAmount1 -= cutStock.getStockRemaining(); //จำนวนทั้งหมดที่ต้องหัก ลบออกด้วยจำนวนสต๊อกที่มีในขณะนั้น
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    pass[i] = true;
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนน้อยกว่าได้");
                                    pass[i] = false;
                                }
                            }
                        }
//                        System.out.println("===== จบส่วนแรก =====\n\n");
                        //============================================ ส่วนที่เป็นสินค้าขายตัดสต๊อกและบันทึกรายการขาย ============================================
//                        System.out.println("===== ทำส่วนสอง =====");
                        cutOff = resultCutOffStock(sellBackup);
                        int sellBackupAmount2 = sellBackup.getSellAmount() - check;

                        for (int j = 0; j < cutOff.size(); j++) {
                            CutOffStockModel cutStock = cutOff.elementAt(j);
//                            System.out.println("stockRemaining = " + cutStock.getStockRemaining());
//                            System.out.println("sellBackupAmount2 = " + sellBackupAmount2);
                            
                            //ถ้าสต๊อกที่มีอยู่มากกว่าหรือเท่ากับ
                            if (cutStock.getStockRemaining() >= sellBackupAmount2 && sellBackupAmount2>0) {
//                                System.out.println("มันมากกว่า......");
                                String sql = "UPDATE tbstock set stockRemaining = '" + (cutStock.getStockRemaining() - sellBackupAmount2) + "' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
//                                System.out.println("sql = " + sql);
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย

                                    if (sellControl.insertSell(cutStock.getProductId(), sellBackup.getProductId(), sellBackup.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productfund", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sellBackup.getProductId())), sellBackupAmount2)) {
//                                        System.out.println("บันทึกเซลส่วนมากกว่า ผ่านฉลุย..........");
                                        sellBackupAmount2 = 0;
                                        pass[i] = true;
                                    } else {
                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนมากกว่าได้");
                                        pass[i] = false;
                                    }
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนมากกว่าได้");
                                }
                                //ถ้าสต๊อกที่มีอยู่น้อยกว่า
                            } else if (cutStock.getStockRemaining() < sellBackupAmount2 && sellBackupAmount2>0){
//                                System.out.println("มันน้อยกว่า......");
                                String sql = "UPDATE tbstock set stockRemaining = '0' WHERE productId = '" + cutStock.getProductId() + "' AND lotId = '" + cutStock.getLotId() + "'";
//                                System.out.println("sql = " + sql);
                                sellBackupAmount2 -= cutStock.getStockRemaining(); //จำนวนทั้งหมดที่ต้องหัก ลบออกด้วยจำนวนสต๊อกที่มีในขณะนั้น
                                int result = -1;
                                try {
                                    result = DBFactory.getConnection().createStatement().executeUpdate(sql);
                                } catch (SQLException ex) {
                                    Logger.getLogger(StockControl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (result != -1) { //ถ้าอัพเดตสต๊อกผ่านเรียบร้อย
                                    if (sellControl.insertSell(cutStock.getProductId(), sellBackup.getProductId(), sellBackup.getProductBarcode(), Double.parseDouble(Util.getResultFromId("productfund", cutStock.getProductId())), Double.parseDouble(Util.getResultFromId("productprice", sellBackup.getProductId())), cutStock.stockRemaining)) {
//                                        System.out.println("บันทึกเซลส่วนน้อยกว่า ผ่านฉลุย..........");
                                        pass[i] = true;
                                    } else {
                                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกเซลส่วนน้อยกว่าได้");
                                        pass[i] = false;
                                    }
                                } else {
                                    Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถอัพเดตสต๊อกส่วนน้อยกว่าได้");
                                }
                            }
                        }
                        
//                        System.out.println("===== จบส่วนสอง =====\n\n");
                    }
                    //</editor-fold>
                }
                //เช็ค pass ตรงนี้
                boolean passed = true;

                for (int i = 0; i < pass.length; i++) {
                    if (!pass[i]) {
                        passed = false;
                        break;
                    }
                }

                if (passed) {
                    if (sellControl.insertPromotionActive()) {
//                        System.out.println("บันทึกโปรโมชั่นก็ผ่านฉลุย....................");
                        Message.showInfoMessage("ผลการทำงาน", "บันทึกรายการขายเรียบร้อย");
                        return true;
                    } else {
                        Message.showErrorMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกโปรโมชั่นได้");
                        return false;
                    }
                }
                //final false
                System.out.println("หลุดมาถึง final false!!!");
                return false;
            }
        } else { //ถ้าไม่มีสินค้าพอให้ได้ยอด
            return false;
        }
    }

    public int checkFromGetSellDetail(SellModel sellBackup, Object[][] sellDetail) {

        String sellBackUpBarcode = sellBackup.getProductBarcode();
        int sellBackupAmount = sellBackup.getSellAmount();

//        System.out.println("(in Method) sellBackupBarcode : " + sellBackUpBarcode);
//        System.out.println("(in Method) sellBackupAmount : " + sellBackupAmount);

        for (int i = 0; i < sellDetail.length; i++) {

            String sellBarcode = sellDetail[i][0].toString();

            if (sellBackUpBarcode.equals(sellBarcode)) { //ถ้าตรงกันให้หาว่ามากกว่าหรือเท่ากัน
//                System.out.println("same : sellBackupBarcode = " + sellBackUpBarcode + ", sellBarcode = " + sellBarcode);
                return sellBackupAmount - ((Integer) sellDetail[i][1]);
            }

        }

        return -1;
    }

    public Object[][] getSellDetail() {

        //สร้างอาเรย์ int ขึ้นเท่าจำนวนข้อมูลเซล
        Object[][] sellDetail = new Object[sellControl.getVectorSell().size()][];
        for (int i = 0; i < sellControl.getVectorSell().size(); i++) {
            SellModel sell = (SellModel) sellControl.getVectorSell().elementAt(i);
            sellDetail[i] = new Object[]{sell.getProductBarcode(), sell.getSellAmount()};
        }

        return sellDetail;
    }

    public Object[][] getProDetail() {

        //สร้างอาเรยฺ int ขึ้นเท่าจำนวนข้อมูลเซล
        Object[][] proDetail = new Object[sellControl.getVectorPromotionActive().size()][];
        for (int i = 0; i < sellControl.getVectorPromotionActive().size(); i++) {
            PromotionResultModel proActive = (PromotionResultModel) sellControl.getVectorPromotionActive().elementAt(i);
            if (proActive.getPromotionType().equals("Plus")) {
                proDetail[i] = new Object[]{proActive.getProductPlusBarcode(), (proActive.getPlusAmount() * proActive.getSumPromotion())};
            }

        }

        return proDetail;
    }

    //โมเดลเก็บข้อมูลสต๊อกสินค้าที่สามารถตัดได้
    public class CutOffStockModel {

        int lotId, productId, stockRemaining;
        double productFund;
        String productBarcode;

        public CutOffStockModel(int productId, int lotId, String productBarcode, double productFund, int stockRemaining) {
            this.productId = productId;
            this.lotId = lotId;
            this.productBarcode = productBarcode;
            this.productFund = productFund;
            this.stockRemaining = stockRemaining;
        }

        public int getLotId() {
            return lotId;
        }

        public void setLotId(int lotId) {
            this.lotId = lotId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductBarcode() {
            return productBarcode;
        }

        public void setProductBarcode(String productBarcode) {
            this.productBarcode = productBarcode;
        }

        public double getProductFund() {
            return productFund;
        }

        public void setProductFund(double productFund) {
            this.productFund = productFund;
        }

        public int getStockRemaining() {
            return stockRemaining;
        }

        public void setStockRemaining(int stockRemaining) {
            this.stockRemaining = stockRemaining;
        }
    }
}
