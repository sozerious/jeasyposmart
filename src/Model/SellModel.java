/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author JAVA DEV
 */
public class SellModel {
    
    private int productId, sellAmount;
    private double productPrice;
    private String productBarcode, productName;

    public SellModel() {
    }

    public SellModel(int productId, int sellAmount, double productPrice, String productBarcode, String productName) {
        this.productId = productId;
        this.sellAmount = sellAmount;
        this.productPrice = productPrice;
        this.productBarcode = productBarcode;
        this.productName = productName;
    }

    
    public SellModel copy() {
        return new SellModel(getProductId(), getSellAmount(), getProductPrice(), getProductBarcode(), getProductName());
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
  
}
