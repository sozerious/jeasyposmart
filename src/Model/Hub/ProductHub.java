/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormProduct;

/**
 *
 * @author JAVA DEV
 */

//สร้าง static ของ FormManageProduct
public class ProductHub {
    
    public static FormProduct product;

    public static FormProduct getProduct() {
        return product;
    }

    public static void setProduct(FormProduct product) {
        ProductHub.product = product;
    }
}
