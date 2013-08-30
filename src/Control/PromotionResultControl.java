/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.PromotionResultModel;
import Model.SellModel;
import Utility.DBFactory;
import Utility.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JAVA DEV
 */
//คลาสควบคุมการตรวจสอบและค้นหาโปรโมชั่น
public class PromotionResultControl {

    private String promotionResult;
    private Vector vectorPromotionResult, vectorAddedPromotion;
    private SellControl sellControl;

    public PromotionResultControl(SellControl sellControl) {

        this.sellControl = sellControl;
        //ข้อมูลโปรโมชั่น
        promotionResult = "SELECT a.promotionId, a.promotionName, a.promotionType, a.strDate, a.endDate, b.productSellId, b.productConditionBarcode, "
                + "b.sellAmount, c.productDiscountBarcode, c.discount, d.productPlusBarcode, d.plusAmount FROM tbpromotion a LEFT JOIN tbproductsell b "
                + "ON a.promotionId = b.promotionId LEFT JOIN tbdiscount c ON a.promotionId = c.promotionId LEFT JOIN tbplus d ON a.promotionId = d.promotionId WHERE NOW() BETWEEN a.strDate AND a.endDate";
        vectorPromotionResult = new Vector();
        vectorAddedPromotion = new Vector(); //เก็บค่าที่เพิ่มไปแล้ว ไว้ตรวจสอบ

    }

    //เพิ่มข้อมูลโปรโมชั่นลงในเวคเตอร์
    public void addPromotionResult() {

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(promotionResult); //คิวรี

            vectorAddedPromotion.clear();
            vectorPromotionResult.clear();

            while (rs.next()) { // 1 รอบผลลัพธ์
                int index = -1;
                int proMotionId = rs.getInt(1); //เก็บค่า promotionId

                for (int i = 0; i < vectorAddedPromotion.size(); i++) { //วนดูว่าเป็นค่าที่เพิ่มไปแล้วหรือเปล่า
                    if (vectorAddedPromotion.elementAt(i) == proMotionId) { //ถ้าตรงกันคือเพิ่มไปแล้ว
                        index = i; // index เท่ากับตำแหน่งที่พบ
                        break;
                    }
                    index = -1; //ถ้าไม่ให้เป็น -1 เหมือนเดิม
                } //จบ for ค้นหาเลขอินเด็ก

                if (index == -1) { //ถ้าไม่เจอสร้างโมเดลใหม่ โดยขอจำนวนรายการสินค้าโปรโมชั่น

                    String sqlProducIdSize = "SELECT promotionId FROM tbproductsell WHERE promotionId = '" + proMotionId + "'";
                    vectorAddedPromotion.add(proMotionId); //เพิ่ม promotionId ใส่เวคเตอร์เพื่ออ้างอิงการมีอยู่
                    PromotionResultModel proResult = new PromotionResultModel(Util.getRowCount(sqlProducIdSize));
                    proResult.setPromotionId(proMotionId);
                    proResult.setPromotionName(rs.getString(2));
                    proResult.setPromotionType(rs.getString(3));
                    proResult.setStrDate(rs.getString(4));
                    proResult.setEndDate(rs.getString(5));
                    proResult.setProductSellId(rs.getInt(6));
                    Object[][] productId = proResult.getProductId();
                    productId[0] = new Object[]{rs.getString(7), rs.getInt(8)};
                    proResult.setProductId(productId);
                    proResult.setProductDicountBarcode(rs.getString(9));
                    proResult.setDiscount(rs.getDouble(10));
                    proResult.setProductPlusBarcode(rs.getString(11));
                    proResult.setPlusAmount(rs.getInt(12));
                    vectorPromotionResult.add(proResult);

                } else {//ถ้าเจอเอาออกมาแก้ไข

                    PromotionResultModel proResult = (PromotionResultModel) vectorPromotionResult.elementAt(index); //ดึงออกจากเวคเตอร์
                    Object[][] productId = proResult.getProductId(); //ดึงจากออบเจคออกมาเซตค่า
                    String sqlArrIndex = "SELECT productSellId FROM tbproductsell WHERE promotionId = '" + proMotionId + "' AND productSellId <= '" + rs.getInt(6) + "'"; //หาว่าตอนนี้ทำถึง ยroductsellId ที่เท่าไหร่แล้ว
                    int arrIndex = Util.getRowCount(sqlArrIndex) - 1; //นับจำนวนแถวแล้วลบด้วย 1
                    productId[arrIndex] = new Object[]{rs.getString(7), rs.getInt(8)};
                    proResult.setProductId(productId); //เก็บกลับเข้าออบเจค
                    vectorPromotionResult.remove(index); //ลบค่าเดิมออก
                    vectorPromotionResult.add(index, proResult); //เก็บกลับเข้าเวคเตอร์ตรงตำแหน่งเดิม

                }
            } //จบ 1 ผลลัพธ์

//            for (int i = 0; i < vectorPromotionResult.size(); i++) {
//                System.out.println("Promotion Number : " + (i+1));
//                PromotionResultModel pro = (PromotionResultModel) vectorPromotionResult.elementAt(i);
//                System.out.println("PromotionNamr : " + Util.getResultString("promotion", pro.getPromotionId()));
//                int[][] productId = pro.getProductId();
//                for (int j = 0; j < productId.length; j++) {
//                    System.out.println("\tProductNumber : " + (j+1) + " [" +Util.getResultString("product", productId[j][0])+"]["+productId[j][1]+"]");
//                }
//            }
//            System.out.println("\n");

        } catch (SQLException ex) {
            Logger.getLogger(PromotionResultControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //คัดลอกค่าจาก vectorsell ใส่ vectorsellEdit เพื่อหักลบค่าได้ ป้องกันการคิดโปรโมชั่นซ้ำซ้อน
    private void copyToVectorSellEdit() {

        sellControl.getVectorSellEdit().clear();

        for (int i = 0; i < sellControl.getVectorSell().size(); i++) {
            SellModel sell = (SellModel) sellControl.getVectorSell().elementAt(i);
            SellModel sellEdit = sell.copy();
            sellControl.getVectorSellEdit().add(sellEdit);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="checkPro Backup">
    //ตรวจสอบโปรโมชั่น ลองแก้ไขดูก่อน
    //    public void checkPromotionResult() {
    //
    //        //คัดลอกข้อมูลจาก vectorSell
    //        copyToVectorSellEdit();
    //
    //        //เคลียร์เวคเตอร์เก็บค่าทั้งสองก่อน
    //        sellControl.getVectorPromotionActive().clear();
    //        sellControl.getVectorPromotionRecommend().clear();
    //
    //        for (int i = 0; i < vectorPromotionResult.size(); i++) { //แต่ละโปรโมชั่น
    //
    //            PromotionResultModel proResult = (PromotionResultModel) vectorPromotionResult.elementAt(i); //คลี่ออกเจคออกจากเวคเตอร์จะได้แต่ละโปรโมชั่น
    //
    //            Object[][] productId = proResult.getProductId(); //เอาไอดีสินค้าออกมาก่อน
    //            boolean[] check = new boolean[productId.length]; //ตัวเช็คแต่ละสินค้าร่วมรายการ
    //            int[] sellIndex = new int[productId.length]; //เก็บอินเด็กของรายการขายที่ตรงกัน ถ้า active จะได้ตัดยอด ป้องกันการคิดโปรโมชั่นซ้ำ
    //            int[] sumPromotion = new int[productId.length]; //เก็บค่าโปรโมชั่นที่น้อยที่สุดที่เป็นไปได้
    //
    //            for (int j = 0; j < productId.length; j++) { //สินค้าแต่ละตัวในโปรโมชั่น
    //
    //                for (int k = 0; k < sellControl.getVectorSellEdit().size(); k++) { //สินค้าแต่ละตัวในรายการซื้อ
    //
    //                    SellModel sell = (SellModel) sellControl.getVectorSellEdit().elementAt(k); //คลี่ออบเจครายการขายออกมา
    //
    //                    //ตรวจสอบ
    //                    if (productId[j][0].toString().equals(sell.getProductBarcode())) { //ถ้าไอดีสินค้าตรงกัน แสดงว่าเป็นตัวเดียวกัน
    //
    //                        //ตรวจดูก่อนว่าสินค้าเงื่อนไขที่ทำงานอยู่นี้เป็นตัวเดียวกันกับสินค้าของแถมหรือเปล่า
    //                        if (productId[j][0].toString().equals(proResult.getProductPlusBarcode())) {
    //
    //                            check[j] = true;
    //                            //ถ้าสินค้าเงื่อนไขกับสินค้าของแถมเป็นตัวเดียวกัน ก็ต้องเอามารวมกันก่อนแล้วค่อยหารถึงจะได้ sumPromotion ที่ถูกต้อง
    //                            sumPromotion[j] = sell.getSellAmount() / (((Integer)productId[j][1])+proResult.getPlusAmount());
    //                            sellIndex[j] = k; //เก็บค่าอินเด็กสินค้านี้ไว้ใช้ลบจำนวนออกถ้าโปรโมชั่นactive
    //
    //                            break; //ออกจากลูปไปที่สินค้าในโปรโมชั่นตัวต่อไป
    //
    //                        } else {
    //
    //                            check[j] = true;
    //                            sumPromotion[j] = sell.getSellAmount() / ((Integer)productId[j][1]);
    //                            sellIndex[j] = k; //เก็บค่าอินเด็กสินค้านี้ไว้ใช้ลบจำนวนออกถ้าโปรโมชั่นactive
    //
    //                            break; //ออกจากลูปไปที่สินค้าในโปรโมชั่นตัวต่อไป
    //
    //                        }
    //
    //                    }
    //
    //                    check[j] = false;
    //                    sellIndex[j] = -1;
    //                    sumPromotion[j] = 0;
    //
    //                } //for รายการสินค้าที่ซื้อ
    //
    //                //                if (check[j]) {
    //                //                    System.out.print("เจอสินค้า : ");
    //                //                } else {
    //                //                    System.out.print("ไม่เจอสินค้า : ");
    //                //                }
    //                //                System.out.println("productName = " + Util.getResultString("product", productId[j][0]));
    //                //
    //                //                System.out.println("sum Pro = " + sumPromotion[j]);
    //                //                System.out.println("===================");
    //
    //            } //จบแต่ละสินค้าในโปรโมชั่น
    //
    //            //ตรวจสอบถ้า active ต้องจริงทุกตัวแล้ว
    //            boolean active = true;
    //            for (int j = 0; j < check.length; j++) {
    //
    //                //ถ้า false แค่ตัวเดียวคือไม่ผ่าน
    //                if (!check[j]) {
    //                    active = false;
    //                    break;
    //                }
    //
    //            }
    //
    //            if (active) { //ถ้าผ่านทุกตัว มาตรวจว่ามี sumPromotion มากกว่า 0 หรือเปล่า
    //
    //                int sumFinal = 0;
    //
    //                for (int j = 0; j < sumPromotion.length; j++) {
    //                    if (j == 0) {
    //                        sumFinal = sumPromotion[j];
    //                    } else {
    //                        sumFinal = (sumPromotion[j] < sumFinal ? sumPromotion[j] : sumFinal);
    //                    }
    //
    //                }
    //
    //                if (sumFinal > 0) { //active
    //                    addToPromotionActive(i, sumFinal);
    //
    //                    //หักยอดป้องกันการคิดโปรโมชั่นซ้ำซ้อน
    //                    for (int j = 0; j < sellIndex.length; j++) {
    //
    //                        SellModel sell = (SellModel) sellControl.getVectorSellEdit().elementAt(sellIndex[j]); //คลี่ออบเจครายการขายออกมา
    //                        int sellAmount = sell.getSellAmount();
    //                        sell.setSellAmount(sellAmount - (((Integer)productId[j][1]) * sumFinal)); //แก้ไข
    //
    //                        sellControl.getVectorSellEdit().remove(sellIndex[j]); //เอาค่าเดิมออก
    //                        sellControl.getVectorSellEdit().add(sellIndex[j], sell); //ใส่ค่าใหม่เข้าไปแทนที่
    //
    //                    }
    //
    //                } else { //recommend
    //                    addToPromotionRecommend(i);
    //                }
    //
    //            } else { //ถ้าไม่ผ่านทุกตัว มาตรวจว่าผ่านซักตัวไหมผ่านแค่ตัวเดียวใส่ recommend เลย
    //
    //                boolean recom = false;
    //                for (int j = 0; j < check.length; j++) {
    //
    //                    //ถ้า true แค่ตัวเดียวคือผ่าน
    //                    if (check[j]) {
    //                        recom = true;
    //                        break;
    //                    }
    //
    //                }
    //
    //                if (recom) { //recommend
    //                    addToPromotionRecommend(i);
    //                }
    //
    //            }
    //        } //จบแต่ละโปรโมชั่น
    //
    //    } //จบเมธอดตรวจสอบโปรโมชั่น
    //</editor-fold>
    
    //ตรวจสอบโปรโมชั่น ลองแก้ไขดูก่อน
    public void checkPromotionResult() {

        //คัดลอกข้อมูลจาก vectorSell
        copyToVectorSellEdit();

        //เคลียร์เวคเตอร์เก็บค่าทั้งสองก่อน
        sellControl.getVectorPromotionActive().clear();
        sellControl.getVectorPromotionRecommend().clear();

        for (int i = 0; i < vectorPromotionResult.size(); i++) { //แต่ละโปรโมชั่น

            PromotionResultModel proResult = (PromotionResultModel) vectorPromotionResult.elementAt(i); //คลี่ออกเจคออกจากเวคเตอร์จะได้แต่ละโปรโมชั่น

            Object[][] productId = proResult.getProductId(); //เอาไอดีสินค้าออกมาก่อน
            boolean[] check = new boolean[productId.length]; //ตัวเช็คแต่ละสินค้าร่วมรายการ
            int[] sellIndex = new int[productId.length]; //เก็บอินเด็กของรายการขายที่ตรงกัน ถ้า active จะได้ตัดยอด ป้องกันการคิดโปรโมชั่นซ้ำ
            int[] sumPromotion = new int[productId.length]; //เก็บค่าโปรโมชั่นที่น้อยที่สุดที่เป็นไปได้

            for (int j = 0; j < productId.length; j++) { //สินค้าแต่ละตัวในโปรโมชั่น

                for (int k = 0; k < sellControl.getVectorSellEdit().size(); k++) { //สินค้าแต่ละตัวในรายการซื้อ

                    SellModel sell = (SellModel) sellControl.getVectorSellEdit().elementAt(k); //คลี่ออบเจครายการขายออกมา

                    //ตรวจสอบ
                    if (productId[j][0].toString().equals(sell.getProductBarcode())) { //ถ้าไอดีสินค้าตรงกัน แสดงว่าเป็นตัวเดียวกัน

                        //ตรวจดูก่อนว่าสินค้าเงื่อนไขที่ทำงานอยู่นี้เป็นตัวเดียวกันกับสินค้าของแถมหรือเปล่า
                        if (productId[j][0].toString().equals(proResult.getProductPlusBarcode())) {

                            check[j] = true;
                            //ถ้าสินค้าเงื่อนไขกับสินค้าของแถมเป็นตัวเดียวกัน ก็ต้องเอามารวมกันก่อนแล้วค่อยหารถึงจะได้ sumPromotion ที่ถูกต้อง
                            sumPromotion[j] = sell.getSellAmount() / (((Integer)productId[j][1])+proResult.getPlusAmount()); 
                            sellIndex[j] = k; //เก็บค่าอินเด็กสินค้านี้ไว้ใช้ลบจำนวนออกถ้าโปรโมชั่นactive

                            break; //ออกจากลูปไปที่สินค้าในโปรโมชั่นตัวต่อไป
                            
                        } else {
                            
                            check[j] = true;
                            sumPromotion[j] = sell.getSellAmount() / ((Integer)productId[j][1]);
                            sellIndex[j] = k; //เก็บค่าอินเด็กสินค้านี้ไว้ใช้ลบจำนวนออกถ้าโปรโมชั่นactive

                            break; //ออกจากลูปไปที่สินค้าในโปรโมชั่นตัวต่อไป
                            
                        }

                    }

                    check[j] = false;
                    sellIndex[j] = -1;
                    sumPromotion[j] = 0;

                } //for รายการสินค้าที่ซื้อ

//                if (check[j]) {
//                    System.out.print("เจอสินค้า : ");
//                } else {
//                    System.out.print("ไม่เจอสินค้า : ");
//                }
//                System.out.println("productName = " + Util.getResultString("product", productId[j][0]));
//                
//                System.out.println("sum Pro = " + sumPromotion[j]);
//                System.out.println("===================");

            } //จบแต่ละสินค้าในโปรโมชั่น

            //ตรวจสอบถ้า active ต้องจริงทุกตัวแล้ว
            boolean active = true;
            for (int j = 0; j < check.length; j++) {

                //ถ้า false แค่ตัวเดียวคือไม่ผ่าน
                if (!check[j]) {
                    active = false;
                    break;
                }

            }

            if (active) { //ถ้าผ่านทุกตัว มาตรวจว่ามี sumPromotion มากกว่า 0 หรือเปล่า

                int sumFinal = 0;

                for (int j = 0; j < sumPromotion.length; j++) {
                    if (j == 0) {
                        sumFinal = sumPromotion[j];
                    } else {
                        sumFinal = (sumPromotion[j] < sumFinal ? sumPromotion[j] : sumFinal);
                    }

                }

                if (sumFinal > 0) { //active
                    addToPromotionActive(i, sumFinal);

                    //หักยอดป้องกันการคิดโปรโมชั่นซ้ำซ้อน
                    for (int j = 0; j < sellIndex.length; j++) {

                        SellModel sell = (SellModel) sellControl.getVectorSellEdit().elementAt(sellIndex[j]); //คลี่ออบเจครายการขายออกมา
                        int sellAmount = sell.getSellAmount();
                        sell.setSellAmount(sellAmount - (((Integer)productId[j][1]) * sumFinal)); //แก้ไข

                        sellControl.getVectorSellEdit().remove(sellIndex[j]); //เอาค่าเดิมออก
                        sellControl.getVectorSellEdit().add(sellIndex[j], sell); //ใส่ค่าใหม่เข้าไปแทนที่

                    }

                } else { //recommend
                    addToPromotionRecommend(i);
                }

            } else { //ถ้าไม่ผ่านทุกตัว มาตรวจว่าผ่านซักตัวไหมผ่านแค่ตัวเดียวใส่ recommend เลย

                boolean recom = false;
                for (int j = 0; j < check.length; j++) {

                    //ถ้า true แค่ตัวเดียวคือผ่าน
                    if (check[j]) {
                        recom = true;
                        break;
                    }

                }

                if (recom) { //recommend
                    addToPromotionRecommend(i);
                }

            }
        } //จบแต่ละโปรโมชั่น

    } //จบเมธอดตรวจสอบโปรโมชั่น

    //เพิ่มลงโปรโมชั่น Active
    public void addToPromotionActive(int index, int sumPromotion) { //index คือ index ของโปรโมชั่นใน vectorPromotionResult

        PromotionResultModel proActive = (PromotionResultModel) vectorPromotionResult.elementAt(index);
        PromotionResultModel proActiveEdit = proActive.copy();
        proActiveEdit.setSumPromotion(sumPromotion);
        sellControl.getVectorPromotionActive().add(proActiveEdit); //แอดไว้ใช้งาน

    }

    //เพิ่มลงโปรโมชั่น Recommend
    public void addToPromotionRecommend(int index) {

        PromotionResultModel proRecommend = (PromotionResultModel) vectorPromotionResult.elementAt(index);
        PromotionResultModel proRecommendEdit = proRecommend.copy();
        sellControl.getVectorPromotionRecommend().add(proRecommendEdit);

    }
}
