/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormStock;

/**
 *
 * @author java-dev
 */
public class StockHub {
    
    public static FormStock stock;

    public static FormStock getStock() {
        return stock;
    }

    public static void setStock(FormStock stock) {
        StockHub.stock = stock;
    }

}
