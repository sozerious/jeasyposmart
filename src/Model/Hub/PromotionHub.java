/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormPromotion;

/**
 *
 * @author java-dev
 */
public class PromotionHub {
    
    public static FormPromotion promotion;

    public static FormPromotion getPromotion() {
        return promotion;
    }

    public static void setPromotion(FormPromotion promotion) {
        PromotionHub.promotion = promotion;
    }

}
