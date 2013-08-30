/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Hub.MainHub;
import Model.PromotionResultModel;
import Model.PromotionShow;
import Model.SellModel;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import View.FormMain;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JAVA DEV
 */
//ควบคุมหน้าจอการขาย
public class SellControl implements ControlInterface {

    private Vector vectorSell, vectorSellEdit, vectorSellBackup, vectorPromotionActive, vectorPromotionRecommend, vectorPromotionActiveShow, vectorPromotionRecommendShow;
    private PromotionResultControl proControl;

    public SellControl() {

        proControl = new PromotionResultControl(this);
        proControl.addPromotionResult(); //สั่งรันโปรโมชั่นใหม่
        vectorSell = new Vector();
        vectorSellEdit = new Vector();
        vectorSellBackup = new Vector();
        vectorPromotionActive = new Vector();
        vectorPromotionRecommend = new Vector();
        vectorPromotionActiveShow = new Vector();
        vectorPromotionRecommendShow = new Vector();
    }

    @Override
    public int[] dataLimit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String dataSort() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //ควบคุมปุ่ม rollback
    public void rollBack() {
    }

    //รับสตริงบาร์โค๊ดเข้ามาเพื่อค้นหาจากเวคเตอร์และรีเทิร์นตำแหน่งเมื่อเจอ
    public int checkBarcodeFromVector(String barcode) {

        for (int i = 0; i < vectorSell.size(); i++) {

            SellModel sell = (SellModel) vectorSell.elementAt(i); //แงะออกมาเป็นออบเจ็ค
            if (sell.getProductBarcode().equals(barcode)) { //ถ้าตรวจเจอ
                return i; //ส่งกลับค่า index
            }
        }
        return -1;
    }

    //รับสตริงชือสินค้าเข้ามาเพื่อค้นหาจากเวคเตอร์และรีเทิร์นตำแหน่งเมื่อเจอ
    public int checkProductNameFromVector(String productName) {

        for (int i = 0; i < vectorSell.size(); i++) {

            SellModel sell = (SellModel) vectorSell.elementAt(i); //แงะออกมาเป็นออบเจ็ค
            if (sell.getProductName().equals(productName)) { //ถ้าตรวจเจอ
                return i; //ส่งกลับค่า index

            }
        }
        return -1;
    }

    //ค้นหาบาร์โค๊ดจากฐานข้อมูลสร้าง SellModel แล้ว add ใส่เวคเตอร์เลย
    public void checkBarcodeFromDatabase(String barcode) {

//        String sql = "SELECT productId, productBarcode, productName, productPrice FROM tbproduct WHERE productBarcode = '" + barcode + "'";
        String sql = "SELECT a.productId, a.productBarcode, a.productName, a.productPrice FROM tbproduct a WHERE productBarcode = '" + barcode + "' "
                + "AND productId = (SELECT productId FROM tbproduct WHERE productname = a.productName ORDER by productId DESC LIMIT 0,1)";

        int rowCount = Util.getRowCount(sql); //ตรวจดูว่ามีสินค้าที่มีบาร์โค๊ดตรงกันหรือเปล่า
        if (rowCount == 1) {

            try {

                Util.textAmountControl(); //ถ้าไม่เปิดตัวคูณให้จำนวนเท่ากับ 1
                ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

                while (rs.next()) { //ถ้าทำตรงนี้คือกรณีที่ในเวคเตอร์ไม่มีรายการนี้อยู่ ให้แอดเพิ่มเข้าไปใน SellModel เลย

                    SellModel sell = new SellModel();
                    sell.setProductId(rs.getInt(1)); // 1 -- productId
                    sell.setProductBarcode(rs.getString(2)); // 2 -- productBarcode
                    sell.setProductName(rs.getString(3)); // 3 -- productName
                    // 4 -- sellAmount
                    int amount = Util.fillterNumberToInt(MainHub.main.getLblAmount().getText());
                    sell.setSellAmount(amount); //จำนวน
                    // 5 -- productPrice
                    Double priceCost = (rs.getDouble(4) * amount);
                    sell.setProductPrice(priceCost); //ราคาสินค้าคูณด้วยจำนวน

                    vectorSell.add(sell);

                    //เซตค่าใน textcost
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    MainHub.main.getLblCost().setText(nf.format(priceCost));

                }
            } catch (SQLException ex) {
                Logger.getLogger(SellControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            //ก่อนจะรีเทิร์นไปใส่เวคเตอร์ตรวจดูว่าตัวคูณเปิดอยู่หรือเปล่า ถ้าเปิดให้ปิด

            MainHub.main.multiplyAmount = false;
            MainHub.main.submitPro = false; //รีค่าใหม่เพื่อจะได้เชคโปรใหม่ได้
            MainHub.main.getLblSellBarcode().requestFocus(); //โพกัสตารางเพื่อให้ event Focus ของตารางทำงาน
        }
    }

    //ค้นหาชื่อสินค้าจากฐานข้อมูลสร้าง SellModel แล้ว add ใส่เวคเตอร์เลย
    public void checkProductNameFromDatabase(String productName) {

//        String sql = "SELECT productId, productBarcode, productName, productPrice FROM tbproduct WHERE productName = '" + productName + "'";
        String sql = "SELECT a.productId, a.productBarcode, a.productName, a.productPrice FROM tbproduct a WHERE productName = '" + productName + "' "
                + "AND productId = (SELECT productId FROM tbproduct WHERE productname = a.productName ORDER by productId DESC LIMIT 0,1)";

        int rowCount = Util.getRowCount(sql); //ตรวจดูว่ามีสินค้าที่มีบาร์โค๊ดตรงกันหรือเปล่า
        if (rowCount == 1) {

            try {

                Util.textAmountControl(); //ถ้าไม่เปิดตัวคูณให้จำนวนเท่ากับ 1
                ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

                while (rs.next()) { //ถ้าทำตรงนี้คือกรณีที่ในเวคเตอร์ไม่มีรายการนี้อยู่ ให้แอดเพิ่มเข้าไปใน SellModel เลย

                    SellModel sell = new SellModel();
                    sell.setProductId(rs.getInt(1)); // 1 -- productId
                    sell.setProductBarcode(rs.getString(2)); // 2 -- productBarcode
                    sell.setProductName(rs.getString(3)); // 3 -- productName
                    // 4 -- sellAmount
                    int amount = Util.fillterNumberToInt(MainHub.main.getLblAmount().getText());
                    sell.setSellAmount(amount); //จำนวน
                    // 5 -- productPrice
                    Double priceCost = (rs.getDouble(4) * amount);
                    sell.setProductPrice(priceCost); //ราคาสินค้าคูณด้วยจำนวน

                    vectorSell.add(sell);

                    //เซตค่าใน textcost
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    MainHub.main.getLblCost().setText(nf.format(priceCost));

                }
            } catch (SQLException ex) {
                Logger.getLogger(SellControl.class.getName()).log(Level.SEVERE, null, ex);
            }

            //ก่อนจะรีเทิร์นไปใส่เวคเตอร์ตรวจดูว่าตัวคูณเปิดอยู่หรือเปล่า ถ้าเปิดให้ปิด

            MainHub.main.multiplyAmount = false;
            MainHub.main.submitPro = false; //รีค่าใหม่เพื่อจะได้เชคโปรใหม่ได้
            MainHub.main.getLblSellBarcode().requestFocus(); //โพกัสตารางเพื่อให้ event Focus ของตารางทำงาน
        }

    }

    //ใช้อันนี้แทนเพราะไม่ต้องการใช้ boolean Update
    public void putDataToVector(String type) {

        int index = 0;

        switch (type) {

            case "barcode":
                //เช็คก่อนว่ามีอยู่ในเวคเตอร์แล้วหรือไม่
                index = checkBarcodeFromVector(MainHub.main.getTxtSellBarcode().getText()); //ส่งบาร์โค๊ดเข้าไปเช็คแล้วรับค่ามา

                if (index != -1) { //มีในเวคเตอร์

                    Util.textAmountControl(); //ถ้าไม่เปิดตัวคูณให้จำนวนเท่ากับ 1

                    //==============ทำกับเวคเตอร์
                    SellModel sell = (SellModel) vectorSell.elementAt(index); //แงะออกมา
                    int amount = sell.getSellAmount(); //เก็บจำนวนเดิมไว้
                    double cost = sell.getProductPrice() / amount; //เอาราคาทั้งหมดหารด้วยจำนวนได้ราคาต่อหน่วย
                    int newAmount = Util.fillterNumberToInt(MainHub.main.getLblAmount().getText());
                    amount += newAmount; //บวกด้วยค่าใหม่
                    sell.setSellAmount(amount); //บวกด้วยจำนวนที่เพิ่มขึ้นมา
                    double priceCost = cost * amount;
                    sell.setProductPrice(priceCost); //เอาราคาต่อหน่วยคูณด้วยจำนวนที่เพิ่มแล้ว

                    vectorSell.remove(index); //เอาของเดิมออกไปก่อน
                    vectorSell.add(index, sell); //ยัดกลับเข้าที่เดิม

                    //==============ทำกับ textcost
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    MainHub.main.getLblCost().setText(nf.format(cost * newAmount)); //ราคาคูณด้วยจำนวน

                    //ก่อนจะรีเทิร์นไปใส่เวคเตอร์ตรวจดูว่าตัวคุณเปิดอยู่หรือเปล่า ถ้าเปิดให้ปิด
                    MainHub.main.multiplyAmount = false;
                    MainHub.main.submitPro = false; //รีค่าใหม่เพื่อจะได้เชคโปรใหม่ได้
                    MainHub.main.getLblSellBarcode().requestFocus(); //โพกัสตารางเพื่อให้ event Focus ของตารางทำงาน

                } else { //มีในฐานข้อมูล

                    checkBarcodeFromDatabase(MainHub.main.getTxtSellBarcode().getText());

                }
                break;
            case "productname":
                //เช็คก่อนว่ามีอยู่ในเวคเตอร์แล้วหรือไม่
                index = checkProductNameFromVector(MainHub.main.getCbxSellProductName().getSelectedItem().toString()); //ส่งชื่อสินค้าเข้าไปเช็คแล้วรับค่ามา
                if (index != -1) { //มีในเวคเตอร์

                    Util.textAmountControl(); //ถ้าไม่เปิดตัวคูณให้จำนวนเท่ากับ 1

                    //==============ทำกับเวคเตอร์
                    SellModel sell = (SellModel) vectorSell.elementAt(index); //แงะออกมา
                    int amount = sell.getSellAmount(); //เก็บจำนวนเดิมไว้
                    double cost = sell.getProductPrice() / amount; //เอาราคาทั้งหมดหารด้วยจำนวนได้ราคาต่อหน่วย
                    int newAmount = Util.fillterNumberToInt(MainHub.main.getLblAmount().getText());
                    amount += newAmount; //บวกด้วยค่าใหม่
                    sell.setSellAmount(amount); //บวกด้วยจำนวนที่เพิ่มขึ้นมา
                    double priceCost = cost * amount;
                    sell.setProductPrice(priceCost); //เอาราคาต่อหน่วยคูณด้วยจำนวนที่เพิ่มแล้ว

                    vectorSell.remove(index); //เอาของเดิมออกไปก่อน
                    vectorSell.add(index, sell); //ยัดกลับเข้าที่เดิม

                    //==============ทำกับ textcost
                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    MainHub.main.getLblCost().setText(nf.format(cost * newAmount)); //ราคาคูณด้วยจำนวน

                    //ก่อนจะรีเทิร์นไปใส่เวคเตอร์ตรวจดูว่าตัวคุณเปิดอยู่หรือเปล่า ถ้าเปิดให้ปิด
                    MainHub.main.multiplyAmount = false;
                    MainHub.main.submitPro = false; //รีค่าใหม่เพื่อจะได้เชคโปรใหม่ได้
                    MainHub.main.getLblSellBarcode().requestFocus(); //โพกัสตารางเพื่อให้ event Focus ของตารางทำงาน

                } else { //มีในฐานข้อมูล

                    checkProductNameFromDatabase(MainHub.main.getCbxSellProductName().getSelectedItem().toString());

                }
                break;
        }
        
        //คัดลอกใส่เวคเตอร์แบคอัพไว้ตัดสต๊อกตามจำนวนจริงก่อนการตัดสินค้าโปรโมชัน
        copyToVectorSellBackup();

        //เซต TotalCost
        MainHub.main.getLblTotal().setText("ยอดรวม");
        getTotal(); //คิดราคารวมก่อน

    } //จบ putToVector
    
    //แก้ไขจำนวนในรายการซื้อสินค้า
    public void editSellTable(String barcode, String strAmount) {
        
        int amount = (!strAmount.isEmpty() ? Integer.parseInt(strAmount): 1);

        for (int i = 0; i < vectorSell.size(); i++) { //วน vectorsell

            SellModel sell = (SellModel) vectorSell.elementAt(i); //คลี่ออกมา

            if (sell.getProductBarcode().equals(barcode)) { //จับเอาที่โปรดักไอดีตรงกัน
                sell.setSellAmount(amount); //setด้วยค่าใหม่
                sell.setProductPrice(amount * Util.getLastProductPriceFromProductBarcode(barcode));
            }
        }

        MainHub.main.submitPro = false; //ถ้ามีการแก้ไข ให้รีค่าเพื่อคิดโปรใหม่ได้
        copyToVectorSellBackup();
        showData();
        getTotal();

    }

    //ลบรายการสินค้าออกจากตารางขาย
    public void deleteSellTable(String productBarcode) {

        for (int i = 0; i < vectorSell.size(); i++) { //วน vectorsell

            SellModel sell = (SellModel) vectorSell.elementAt(i); //คลี่ออกมา

            if (sell.getProductBarcode().equals(productBarcode)) { //จับเอาที่โปรดักไอดีตรงกัน
                vectorSell.remove(i);
            }
        }

        MainHub.main.submitPro = false; //ถ้ามีการลบก็ต้องคิดโปรใหม่เหมือนกัน
        copyToVectorSellBackup();
        showData();
        getTotal();

    }
    
    //คัดลอกค่าจาก vectorsell ใส่ vectorsellEdit เพื่อหักลบค่าได้ ป้องกันการคิดโปรโมชั่นซ้ำซ้อน
    private void copyToVectorSellBackup() {

        vectorSellBackup.clear();

        for (int i = 0; i < vectorSell.size(); i++) {
            SellModel sell = (SellModel) vectorSell.elementAt(i);
            SellModel sellBackup = sell.copy();
            vectorSellBackup.add(sellBackup);
        }

    }

    public void showData() {

        showSellData();
        showPromotion();

    }

    public void showSellData() {
        Util.clearRow("sell");

        for (int i = 0; i < vectorSell.size(); i++) {

            SellModel sell = (SellModel) vectorSell.elementAt(i);

            MainHub.main.getModelSell().addRow(new Object[0]);
//            MainHub.main.getModelSell().setValueAt(sell.getProductId(), i, 0);
            MainHub.main.getModelSell().setValueAt(sell.getProductBarcode(), i, 0);
            MainHub.main.getModelSell().setValueAt(sell.getProductName(), i, 1);
            MainHub.main.getModelSell().setValueAt(sell.getSellAmount(), i, 2);
            MainHub.main.getModelSell().setValueAt(sell.getProductPrice(), i, 3);

        }
    }

    public void showPromotion() {

        proControl.checkPromotionResult(); //แอดโปรโมชั่นใช้งานและแนะนำลงในเวคเตอร์

        //เรียกแสดงโปรโมชั้น
        showPromotionActiveData();
        showPromotionRecommedData();
    }

    public void showPromotionActiveData() {
        //จะเอาอะไรมาแสดงบ้างก็แล้วแต่

        convertProActiveToShow(); //เตรียมข้อมูลเพื่อแสดงในตาราง

        Util.clearRow("active");
        for (int i = 0; i < vectorPromotionActiveShow.size(); i++) {

            PromotionShow proShow = (PromotionShow) vectorPromotionActiveShow.elementAt(i);

            MainHub.main.getModelActive().addRow(new Object[0]);
            MainHub.main.getModelActive().setValueAt(proShow.getPromotionName(), i, 0);
            MainHub.main.getModelActive().setValueAt(proShow.getPromotionCondition(), i, 1);
            MainHub.main.getModelActive().setValueAt(proShow.getPromotionResult(), i, 2);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="convertToShow Backup">
    //แปลงข้อมูลโปรโมชั่นใช้งานเพื่อแสดงในตาราง
    //    public void convertProActiveToShow() {
    //
    //        vectorPromotionActiveShow.clear();
    //
    //        for (int i = 0; i < vectorPromotionActive.size(); i++) {
    //
    //            PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);
    //            Object[][] productId = proActive.getProductId();
    //            PromotionShow proShow = new PromotionShow();
    //
    //            //เพิ่มชื่อโปรโมชั่น
    //            proShow.setPromotionName(Util.getResultString("promotion", proActive.getPromotionId()));
    //
    //            //เงื่อนไข
    //            for (int j = 0; j < productId.length; j++) { //เพิ่มสินค้าร่วมรายการ
    ////                String proCondition = proShow.getPromotionCondition(); //เงื่อนไขเดิม
    ////                String prepare = (j < productId.length - 1 ? Util.getResultString("product", productId[j][0]) + "[" + productId[j][1] + "]+" : Util.getResultString("product", productId[j][0]) + "[" + productId[j][1] + "]"); //เงื่อนไขใหม่
    ////                proShow.setPromotionCondition(proCondition + prepare);
    //                String proCondition = proShow.getPromotionCondition(); //เงื่อนไขเดิม
    //                String prepare = (j < productId.length - 1 ? Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]+" : Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]"); //เงื่อนไขใหม่
    //                String result = (j == 0 ? prepare : proCondition + prepare); //ถ้าเป็นตัวแรกใช้เงื่อนไขเดิม แต่ถ้าไม่ใช่ให้ใช้เงื่อนไขเดิมบวกเงื่อนไขใหม่
    //                proShow.setPromotionCondition(result);
    //            }
    //
    //            //เพิ่มผลลัพธ์
    //            if (proActive.getPromotionType().equals("Discount")) { //ถ้าเป็น Discount
    //                proShow.setPromotionResult("ลด " + proActive.getDiscount() * proActive.getSumPromotion() + " บาท");
    //            } else { //ถ้าเป็น Plus
    //                String productPlus = Util.getProductNameFormBarcode(proActive.getProductPlusBarcode());
    ////                proShow.setPromotionResult("แถม " + productPlus + "*" + proActive.getPlusAmount() * proActive.getSumPromotion() + " (รวม " + proActive.getDiscount() * proActive.getSumPromotion() + " บาท)"); //หาราคาของจาก productPludId
    //                proShow.setPromotionResult("แถม " + productPlus + "*" + proActive.getPlusAmount() * proActive.getSumPromotion() + " (รวม " + Util.getLastProductPriceFromProductBarcode(proActive.getProductPlusBarcode()) * proActive.getSumPromotion() + " บาท)"); //หาราคาของจาก productPludId
    //            }
    //
    //            vectorPromotionActiveShow.add(proShow); //เพิ่มใส่เวคเตอร์
    //        }
    //    }
    //</editor-fold>
    //แปลงข้อมูลโปรโมชั่นใช้งานเพื่อแสดงในตาราง
    public void convertProActiveToShow() {

        vectorPromotionActiveShow.clear();

        for (int i = 0; i < vectorPromotionActive.size(); i++) {

            PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);
            Object[][] productId = proActive.getProductId();
            PromotionShow proShow = new PromotionShow();

            //เพิ่มชื่อโปรโมชั่น
            proShow.setPromotionName(Util.getResultFromId("promotion", proActive.getPromotionId()));

            //เงื่อนไข
            for (int j = 0; j < productId.length; j++) { //เพิ่มสินค้าร่วมรายการ
                //                String proCondition = proShow.getPromotionCondition(); //เงื่อนไขเดิม
                //                String prepare = (j < productId.length - 1 ? Util.getResultString("product", productId[j][0]) + "[" + productId[j][1] + "]+" : Util.getResultString("product", productId[j][0]) + "[" + productId[j][1] + "]"); //เงื่อนไขใหม่
                //                proShow.setPromotionCondition(proCondition + prepare);
                String proCondition = proShow.getPromotionCondition(); //เงื่อนไขเดิม
                String prepare = (j < productId.length - 1 ? Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]+" : Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]"); //เงื่อนไขใหม่
                String result = (j == 0 ? prepare : proCondition + prepare); //ถ้าเป็นตัวแรกใช้เงื่อนไขเดิม แต่ถ้าไม่ใช่ให้ใช้เงื่อนไขเดิมบวกเงื่อนไขใหม่
                proShow.setPromotionCondition(result);
            }

            //เพิ่มผลลัพธ์
            if (proActive.getPromotionType().equals("Discount")) { //ถ้าเป็น Discount
                proShow.setPromotionResult("ลด " + proActive.getDiscount() * proActive.getSumPromotion() + " บาท");
            } else { //ถ้าเป็น Plus
                String productPlus = Util.getProductNameFormBarcode(proActive.getProductPlusBarcode());
                //                proShow.setPromotionResult("แถม " + productPlus + "*" + proActive.getPlusAmount() * proActive.getSumPromotion() + " (รวม " + proActive.getDiscount() * proActive.getSumPromotion() + " บาท)"); //หาราคาของจาก productPludId
                proShow.setPromotionResult("แถม " + productPlus + "*" + proActive.getPlusAmount() * proActive.getSumPromotion() + " (รวม " + Util.getLastProductPriceFromProductBarcode(proActive.getProductPlusBarcode()) * proActive.getSumPromotion() + " บาท)"); //หาราคาของจาก productPludId
            }

            vectorPromotionActiveShow.add(proShow); //เพิ่มใส่เวคเตอร์
        }
    }

    public void showPromotionRecommedData() {
        //จะเอาอะไรมาแสดงบ้างก็แล้วแต่

        convertProRecommendToShow(); //เตรียมข้อมูลเพื่อแสดงในตาราง

        Util.clearRow("recommend");
        for (int i = 0; i < vectorPromotionRecommendShow.size(); i++) {

            PromotionShow proShow = (PromotionShow) vectorPromotionRecommendShow.elementAt(i);

            MainHub.main.getModelRecom().addRow(new Object[0]);
            MainHub.main.getModelRecom().setValueAt(proShow.getPromotionName(), i, 0);
            MainHub.main.getModelRecom().setValueAt(proShow.getPromotionCondition(), i, 1);
            MainHub.main.getModelRecom().setValueAt(proShow.getPromotionResult(), i, 2);
        }

    }

    //แปลงข้อมูลโปรโมชั่นใช้งานเพื่อแสดงในตาราง
    public void convertProRecommendToShow() {

        vectorPromotionRecommendShow.clear();

        for (int i = 0; i < vectorPromotionRecommend.size(); i++) {

            PromotionResultModel proRecom = (PromotionResultModel) vectorPromotionRecommend.elementAt(i);
            Object[][] productId = proRecom.getProductId();
            PromotionShow proShow = new PromotionShow();

            proShow.setPromotionName(Util.getResultFromId("promotion", proRecom.getPromotionId())); //เพิ่มชื่อโปรโมชั่น
            for (int j = 0; j < productId.length; j++) { //เพิ่มสินค้าร่วมรายการ
                String proCondition = proShow.getPromotionCondition(); //เงื่อนไขเดิม
                String prepare = (j < productId.length - 1 ? Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]+" : Util.getProductNameFormBarcode(productId[j][0].toString()) + "[" + productId[j][1] + "]"); //เงื่อนไขใหม่
                String result = (j == 0 ? prepare : proCondition + prepare);
                proShow.setPromotionCondition(result);
            }

            if (proRecom.getPromotionType().equals("Discount")) { //เพิ่มผลลัพธ์
                proShow.setPromotionResult("ลด " + proRecom.getDiscount() + " บาท");
            } else {
                String productPlus = Util.getProductNameFormBarcode(proRecom.getProductPlusBarcode());
                proShow.setPromotionResult("แถม " + productPlus + "*" + proRecom.getPlusAmount());
            }

            vectorPromotionRecommendShow.add(proShow); //เพิ่มใส่เวคเตอร์
        }
    }

    @Override
    //ไม่ต้องหาจากฐานข้อมูล ดึงมาจาก vector ที่เก็บรายการซื้อขายไว้ได้เลย อันนี้ไม่ต้องใช้แต่ลบไม่ได้เพราะ implement มาจาก interface
    public void putDataToVector(boolean search) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    //เรียกจาก vector ที่เก็บรายการซื้อสินค้าออกมาแสดงทุกครั้งที่มีการเปลี่ยนแปลง ให้เรียกใหม่ทุกครั้ง
    public void showData(boolean update) {
    }

    //คำนวณราคาสินค้าทั้งหมด
    public void getTotal() { //คิดราคารวมทั้งหมด

        double totalCost = 0.00;

        //หาราคารวม
        for (int i = 0; i < vectorSell.size(); i++) {
            SellModel sell = (SellModel) vectorSell.elementAt(i);
            totalCost += sell.getProductPrice(); //บวกค่ารวม
        }

        MainHub.main.gbTotalCost = totalCost;

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        MainHub.main.getLblTotalCost().setText(nf.format(MainHub.main.gbTotalCost)); //เซตยอดรวม

    }

    //ตรวจสอบว่ามีสินค้าของแถมพร้อมให้หักออกหรือเปล่า
    public boolean checkPromotionCountToDelete() {

        boolean[] passed = new boolean[vectorPromotionActive.size()];

        for (int i = 0; i < vectorPromotionActive.size(); i++) { //แต่ละโปรโมชั่น

            PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);

            switch (proActive.getPromotionType()) {
                case "Discount"://ตรวจเฉพาะโปรที่เป็นของแแถม
                    passed[i] = true;
                    break;

                case "Plus": //ของแถม ต้องมาหาว่ามีรายการของแถมที่ตรงกับในรายการซื้อหรือเปล่า
                    //ถ้าเข้ามาตรงนี้คือเป็นโปรที่เป็นรายการแถมเท่านั้น สามารถดึงค่า productidplus มาตรวจสอบได้เลย

                    String plusBarcode = proActive.getProductPlusBarcode(); //ไอดีสินค้าของแถม
                    int plusAmount = proActive.getPlusAmount(); //จำนวนแถม
                    int sumPro = proActive.getSumPromotion(); //ตัวคูณ (จำนวนโปรโมชั่น)

                    boolean check[] = new boolean[vectorSell.size()];

                    for (int j = 0; j < vectorSell.size(); j++) { //แต่ละสินค้าในรายการขาย

                        SellModel sell = (SellModel) vectorSell.elementAt(j); //คลี่ออบเจคการขายออกมา

                        if (sell.getProductBarcode().equals(plusBarcode)) { //ถ้าสินค้าของแถมกับสินค้าที่ซื้อตรงกัน

                            Object[][] productId = proActive.getProductId();
                            boolean same = false;
                            int index = 0;

                            for (int k = 0; k < productId.length; k++) {

                                if (productId[k][0] == plusBarcode) { //เช็คว่าตรงกันหรือเปล่า
                                    same = true;
                                    index = k;
                                    break;
                                }

                                same = false;
                            }

                            if (same) { //ถ้าเป็นสินค้าที่เงื่อนไขกับผลลัพธ์เป็นตัวเดียวกัน

                                int resultMod = sell.getSellAmount() % (((Integer) productId[index][1]) + proActive.getPlusAmount()); //เอาจำนวนที่ซื้อมา mod หาเศษว่าขาดอีกแค่ไหน
                                if (resultMod == 0) {
                                    check[j] = true; //ถ้าไม่เหลือเศษให้ผ่าน

                                } else if (resultMod > 0) { //ถ้าเหลือ

                                    if (((((Integer) productId[index][1]) + proActive.getPlusAmount()) - resultMod) > proActive.getPlusAmount()) { //ขาดเกินกว่าจำนวนต้องแถมให้ผ่าน
                                        check[j] = true;
                                    } else if (((((Integer) productId[index][1]) + proActive.getPlusAmount()) - resultMod) <= proActive.getPlusAmount()) { //ขาดเท่าหรือน้อยกว่าให้เตือน
                                        check[j] = false;
                                    }
                                }

                            } else { //ถ้าไม่ตรงก็เช็คแบบปกติ
                                if (sell.getSellAmount() > (plusAmount * sumPro)) { //ถ้าจำนวนซื้อมากกว่าหรือเท่ากับจำนวนที่ต้องแถม

                                    check[j] = true;
                                    break;
                                } else if (sell.getSellAmount() == (plusAmount * sumPro)) { //ถ้าเท่ากัน
                                    check[j] = true;
                                    break;
                                } else { //ถ้าน้อยกว่า
                                    check[j] = false;
                                }
                            }

                        } else { //ถ้าไม่เจอสินค้าในรายการซื้อ
                            check[j] = false;
                        }
                    }

                    boolean found = false;
                    for (int j = 0; j < check.length; j++) {
                        if (check[j]) {
                            found = true;
                            break;
                        }
                        found = false;
                    }

                    if (found) {
                        passed[i] = true;
                    } else {
                        passed[i] = false;
                    }

                    break; // เบรคของสวิต

            } //จบ switch

        } //จบ 1 โปร

        boolean pass = true;

        for (int i = 0; i < passed.length; i++) {

            if (!passed[i]) {
                pass = false;
                break;
            }
            pass = true;
        }

        return pass; //ตัวบอกว่าพร้อมหรือไม่พร้อม

    }
    
    //หักลบราคาส่วนลดและสินค้าของแถมทั้งหมด
    public void submitPro() {

        getTotal(); //เพื่อเซตค่า

        if (checkPromotionCountToDelete()) {
            MainHub.main.gbTotalCostPro = getTotalPromotion(); //หักสินค้าออกจากเวคเตอร์แล้ว

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            MainHub.main.getLblTotalCost().setText(nf.format(MainHub.main.gbTotalCost - MainHub.main.gbTotalCostPro));

            showSellData();
            MainHub.main.submitPro = true;
        } else {
            Message.showWarningMessage("ผิดพลาด", "ยังไม่ได้เพิ่มสินค้าของแถมหรือสินค้าไม่ครบจำนวน");
        }

    }

    //<editor-fold defaultstate="collapsed" desc="getTotal Backup">
    //คำนวณราคารวมของโปรโมชั่นที่ต้องหักออก
    //    public double getTotalPromotion() {
    //
    //        double totalPro = 0.00;
    //
    //        for (int i = 0; i < vectorPromotionActive.size(); i++) { //โปรโมชั่นที่ทำงาน
    //
    //            PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);
    //
    //            switch (proActive.getPromotionType()) {
    //
    //                //ถ้าเป็นการลด
    //                case "Discount":
    //
    //                    totalPro += (proActive.getDiscount() * proActive.getSumPromotion()); //ลดแบบลดจริงๆ
    //
    //                    String discountBarcode = proActive.getProductDicountBarcode();
    //
    //                    for (int j = 0; j < vectorSell.size(); j++) {
    //
    //                        SellModel sell = (SellModel) vectorSell.elementAt(j);
    //
    //                        if (sell.getProductBarcode().equals(discountBarcode)) {
    //                            sell.setProductPrice(sell.getProductPrice() - (proActive.getDiscount() * proActive.getSumPromotion()));
    //                        }
    //
    //                    }
    //
    //                    break;
    //
    //                case "Plus": //ของแถม ต้องมาหาว่ามีรายการของแถมที่ตรงกับในรายการซื้อหรือเปล่า
    //
    //                    //ถ้าเข้ามาตรงนี้คือเป็นโปรที่เป็นรายการแถมเท่านั้น สามารถดึงค่า productidplus มาตรวจสอบได้เลย
    //
    //                    String plusBarcode = proActive.getProductPlusBarcode(); //ไอดีสินค้าของแถม
    //                    int plusAmount = proActive.getPlusAmount(); //จำนวนแถม
    //                    int sumPro = proActive.getSumPromotion(); //ตัวคูณ (จำนวนโปรโมชั่น)
    //
    //                    for (int j = 0; j < vectorSell.size(); j++) { //ไอดีสินค้าในรายการขาย
    //
    //                        SellModel sell = (SellModel) vectorSell.elementAt(j); //คลี่ออบเจคการขายออกมา
    //
    //                        if (sell.getProductBarcode().equals(plusBarcode)) { //ถ้าสินค้าของแถมกับสินค้าที่ซื้อตรงกัน
    //
    //                            if (sell.getSellAmount() > (plusAmount * sumPro)) { //ถ้าจำนวนซื้อมากกว่าหรือเท่ากับจำนวนที่ต้องแถม
    //                                double productPrice = Double.parseDouble(Util.getResultString("productprice", sell.getProductId()));
    //                                double discount = productPrice * (plusAmount * sumPro);
    //                                totalPro += discount;//ราคาสินค้าแถม*จำนวนแถม*จำนวนโปร
    //                                sell.setSellAmount(sell.getSellAmount() - (plusAmount * sumPro)); //ลบจำนวนออกเท่าจำนวนโปร
    //                                sell.setProductPrice(sell.getProductPrice() - discount); //ลบราคาออก
    //                                break;
    //                            } else if (sell.getSellAmount() == (plusAmount * sumPro)) { //ถ้าเท่ากัน
    //                                totalPro += Double.parseDouble(Util.getResultString("productprice", sell.getProductId())) * (plusAmount * sumPro);//ราคาสินค้าแถม*จำนวนแถม*จำนวนโปร
    //                                vectorSell.remove(j); //รายการนี้ออกไปเลย
    //                                break;
    //                            }
    //
    //                        }
    //
    //                    }
    //
    //                    break;
    //            }
    //
    //        } //จบ 1 โปร
    //
    //        return totalPro;
    //
    //    }
    //</editor-fold>
    
    //คำนวณราคารวมของโปรโมชั่นที่ต้องหักออก
    public double getTotalPromotion() {

        double totalPro = 0.00;

        for (int i = 0; i < vectorPromotionActive.size(); i++) { //โปรโมชั่นที่ทำงาน

            PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);

            switch (proActive.getPromotionType()) {

                //ถ้าเป็นการลด
                case "Discount":

                    totalPro += (proActive.getDiscount() * proActive.getSumPromotion());

                    String discountBarcode = proActive.getProductDicountBarcode();

                    for (int j = 0; j < vectorSell.size(); j++) {

                        SellModel sell = (SellModel) vectorSell.elementAt(j);

                        if (sell.getProductBarcode().equals(discountBarcode)) {
                            sell.setProductPrice(sell.getProductPrice() - (proActive.getDiscount() * proActive.getSumPromotion()));
                        }

                    }

                    break;

                case "Plus": //ของแถม ต้องมาหาว่ามีรายการของแถมที่ตรงกับในรายการซื้อหรือเปล่า

                    //ถ้าเข้ามาตรงนี้คือเป็นโปรที่เป็นรายการแถมเท่านั้น สามารถดึงค่า productidplus มาตรวจสอบได้เลย

                    String plusBarcode = proActive.getProductPlusBarcode(); //ไอดีสินค้าของแถม
                    int plusAmount = proActive.getPlusAmount(); //จำนวนแถม
                    int sumPro = proActive.getSumPromotion(); //ตัวคูณ (จำนวนโปรโมชั่น)

                    for (int j = 0; j < vectorSell.size(); j++) { //ไอดีสินค้าในรายการขาย

                        SellModel sell = (SellModel) vectorSell.elementAt(j); //คลี่ออบเจคการขายออกมา

                        if (sell.getProductBarcode().equals(plusBarcode)) { //ถ้าสินค้าของแถมกับสินค้าที่ซื้อตรงกัน

                            if (sell.getSellAmount() > (plusAmount * sumPro)) { //ถ้าจำนวนซื้อมากกว่าหรือเท่ากับจำนวนที่ต้องแถม
                                double productPrice = Double.parseDouble(Util.getResultFromId("productprice", sell.getProductId()));
                                double discount = productPrice * (plusAmount * sumPro);
                                totalPro += discount;//ราคาสินค้าแถม*จำนวนแถม*จำนวนโปร
                                sell.setSellAmount(sell.getSellAmount() - (plusAmount * sumPro)); //ลบจำนวนออกเท่าจำนวนโปร
                                sell.setProductPrice(sell.getProductPrice() - discount); //ลบราคาออก
                                break;
                            } else if (sell.getSellAmount() == (plusAmount * sumPro)) { //ถ้าเท่ากัน
                                totalPro += Double.parseDouble(Util.getResultFromId("productprice", sell.getProductId())) * (plusAmount * sumPro);//ราคาสินค้าแถม*จำนวนแถม*จำนวนโปร
                                vectorSell.remove(j); //รายการนี้ออกไปเลย
                                break;
                            }

                        }

                    }

                    break;
            }

        } //จบ 1 โปร

        return totalPro;

    }

    public void moveFromVectorSell() {

        for (int i = 0; i < vectorSell.size(); i++) {

            SellModel sell = (SellModel) vectorSell.elementAt(i);
            SellModel sellBackup = sell.copy();
            MainHub.main.vectorSellBackup.add(sellBackup);

        }
        vectorSell.clear();
        showData();
        getTotal();


    }

    public void moveToVectorSell() {

        for (int i = 0; i < MainHub.main.vectorSellBackup.size(); i++) {

            SellModel sellBackup = (SellModel) MainHub.main.vectorSellBackup.elementAt(i);
            SellModel sell = sellBackup.copy();
            vectorSell.add(sell);

        }
        MainHub.main.vectorSellBackup.clear();
        showData();
        getTotal();

    }

    //บันทึกรายการขาย
//    public void submitSell() {
//
//        if (insertBill()) {
//            if (insertSell() && insertPromotionActive()) {
//                Message.showInfoMessage("บันทึกแล้ว", "บันทึกรายการเรียบร้อยแล้ว");
//            } else {
//                Message.showWarningMessage("พบข้อผิดพลาด", "ไม่สามารถบันทึกรายการได้");
//            }
//        } else {
//            Message.showWarningMessage("พบข้อผิดพลาด", "การบันทึกบิลรายการผิดพลาด");
//        }
//
//    }

    //บันทึกลงตาราง bill
    public boolean insertBill() {

        String sql = "INSERT INTO tbbill(subTotalPrice, totalPrice, cashPrice, changePrice, sellUser, sellDate) VALUES(?,?,?,?,?,NOW())";
        try {

            PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);
            pre.setDouble(1, (vectorPromotionActive.isEmpty() ? 0 : MainHub.main.gbTotalCost));
//            System.out.println(FormMain.gbTotalCost);
            pre.setDouble(2, (vectorPromotionActive.isEmpty() ? MainHub.main.gbTotalCost : (MainHub.main.gbTotalCost - MainHub.main.gbTotalCostPro)));
//            System.out.println(FormMain.gbTotalCost-FormMain.gbTotalCostPro);
            pre.setDouble(3, Util.fillterNumberToDouble(MainHub.main.getLblCashCost().getText()));
//            System.out.println(Util.fillterNumberToDouble(MainHub.main.getLblCashCost().getText()));
            pre.setDouble(4, Util.fillterNumberToDouble(MainHub.main.getLblChangeCost().getText()));
//            System.out.println(Util.fillterNumberToDouble(MainHub.main.getLblChangeCost().getText()));
            pre.setString(5, FormMain.user.getUserName());
//            System.out.println(FormMain.user.getUserName());

            if (pre.executeUpdate() != -1) {
                return true;
//                Message.showInfoMessage("บันทึกแล้ว", "บันทึกรายการเรียบร้อยแล้ว");
            } else {
                return false;
//                Message.showWarningMessage("ผิดพลาด", "ไม่สามารถบันทึกรายการได้");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SellControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    //เพิ่มข้อมูลลงในตารางรายการขาย
    public boolean insertSell(int productId, int sellPriceByProductId, String productBarcode, double fundPrice, double sellPrice, int sellAmount) {

        String sql = "INSERT INTO tbsell(billId, productId, sellPriceByProductId, productBarcode, fundPrice, sellPrice, sellAmount, sumPrice) VALUES((SELECT billId FROM tbbill ORDER BY billId DESC LIMIT 0,1),?,?,?,?,?,?,?)";

        try {
            //วนตามขนาด vectorsell
            PreparedStatement pre = DBFactory.getConnection().prepareStatement(sql);

                pre.setInt(1, productId);
                pre.setInt(2, sellPriceByProductId);
                pre.setString(3, productBarcode);
                pre.setDouble(4, fundPrice);
                pre.setDouble(5, sellPrice);
                pre.setInt(6, sellAmount);
                pre.setDouble(7, sellAmount*sellPrice);

                if (pre.executeUpdate() != -1) {
                    return true;
                } else {
                    return false;
                }

        } catch (SQLException ex) {
            Logger.getLogger(SellControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //เพิ่มข้อมูลในไปในตารางสินค้ารายการแถมหรือลด
    public boolean insertPromotionActive() {

        String sql = "INSERT INTO tbpromotionactive(billId, promotionId, promotionType, result, sumPromotion) "
                + "VALUES((SELECT billId FROM tbbill ORDER BY billId DESC LIMIT 0,1),?,?,?,? )";
        PreparedStatement pre = null;
        try {
            pre = DBFactory.getConnection().prepareStatement(sql);
            boolean check[] = new boolean[vectorPromotionActive.size()];

            for (int i = 0; i < vectorPromotionActive.size(); i++) {

                PromotionResultModel proActive = (PromotionResultModel) vectorPromotionActive.elementAt(i);

                pre.setInt(1, proActive.getPromotionId());
                pre.setString(2, proActive.getPromotionType());
                switch (proActive.getPromotionType()) {
                    case "Discount":
                        pre.setString(3, proActive.getDiscount() + "");
                        break;
                    case "Plus":
                        pre.setString(3, Util.getProductNameFormBarcode(proActive.getProductPlusBarcode()));
                        break;
                }

                pre.setInt(4, proActive.getSumPromotion());

                if (pre.executeUpdate() != -1) {
                    check[i] = true;
                } else {
                    check[i] = false;
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

            return pass;

        } catch (SQLException ex) {
            Logger.getLogger(SellControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
    
    public Vector getVectorSell() {
        return vectorSell;
    }

    public Vector getVectorPromotionActive() {
        return vectorPromotionActive;
    }

    public Vector getVectorPromotionRecommend() {
        return vectorPromotionRecommend;
    }

    public Vector getVectorPromotionActiveShow() {
        return vectorPromotionActiveShow;
    }

    public Vector getVectorPromotionRecommendShow() {
        return vectorPromotionRecommendShow;
    }
    
    public Vector getVectorSellEdit() {
        return vectorSellEdit;
    }

    public Vector getVectorSellBackup() {
        return vectorSellBackup;
    }
    
}
