/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author java-dev
 */
public class StockModel {
    
    int stockId, lotId, stockAmount, stockRemaining;
    String productName, stockDate, stockExpire;

    public StockModel() {
    }

    public StockModel(int stockId, int lotId, int stockAmount, int stockRemaining, String productName, String stockDate, String stockExpire) {
        this.stockId = stockId;
        this.lotId = lotId;
        this.stockAmount = stockAmount;
        this.stockRemaining = stockRemaining;
        this.productName = productName;
        this.stockDate = stockDate;
        this.stockExpire = stockExpire;
    }
    
    public StockModel copy() {
        return new StockModel(getStockId(), getLotId(), getStockAmount(), getStockRemaining(), getProductName(), getStockDate(), getStockExpire());
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
    
    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public int getStockRemaining() {
        return stockRemaining;
    }

    public void setStockRemaining(int stockRemaining) {
        this.stockRemaining = stockRemaining;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public String getStockExpire() {
        return stockExpire;
    }

    public void setStockExpire(String stockExpire) {
        this.stockExpire = stockExpire;
    }

}
