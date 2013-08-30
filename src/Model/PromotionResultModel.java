/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author java-dev
 */
//บรรจุข้อมูลรายการโปรโมชั่น
public class PromotionResultModel {

    int promotionId, productSellId, plusAmount, sumPromotion;
    double discount;
    Object[][] productId;
    String promotionName, promotionType, strDate, endDate, productDicountBarcode, productPlusBarcode;

    public PromotionResultModel(int size) {
        productId = new Object[size][2];
    }

    public PromotionResultModel(int promotionId, int productSellId, int plusAmount, int sumPromotion, double discount, Object[][] productId, String promotionName, String promotionType, String strDate, String endDate, String productDicountBarcode, String productPlusBarcode) {
        this.promotionId = promotionId;
        this.productSellId = productSellId;
        this.plusAmount = plusAmount;
        this.sumPromotion = sumPromotion;
        this.discount = discount;
        this.productId = productId;
        this.promotionName = promotionName;
        this.promotionType = promotionType;
        this.strDate = strDate;
        this.endDate = endDate;
        this.productDicountBarcode = productDicountBarcode;
        this.productPlusBarcode = productPlusBarcode;
    }

    public PromotionResultModel copy() {
        return new PromotionResultModel(getPromotionId(), getProductSellId(), getPlusAmount(), getSumPromotion(), getDiscount(), getProductId(), getPromotionName(), getPromotionType(), getStrDate(), getEndDate(), getProductDicountBarcode(), getProductPlusBarcode());
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public int getProductSellId() {
        return productSellId;
    }

    public void setProductSellId(int productSellId) {
        this.productSellId = productSellId;
    }

    public int getPlusAmount() {
        return plusAmount;
    }

    public void setPlusAmount(int plusAmount) {
        this.plusAmount = plusAmount;
    }

    public int getSumPromotion() {
        return sumPromotion;
    }

    public void setSumPromotion(int sumPromotion) {
        this.sumPromotion = sumPromotion;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Object[][] getProductId() {
        return productId;
    }

    public void setProductId(Object[][] productId) {
        this.productId = productId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProductDicountBarcode() {
        return productDicountBarcode;
    }

    public void setProductDicountBarcode(String productDicountBarcode) {
        this.productDicountBarcode = productDicountBarcode;
    }

    public String getProductPlusBarcode() {
        return productPlusBarcode;
    }

    public void setProductPlusBarcode(String productPlusBarcode) {
        this.productPlusBarcode = productPlusBarcode;
    }
    
}
