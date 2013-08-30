/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author java-dev
 */

//เก็บข้อมูลโปรโมชันไว้แสดงในตาราง
public class PromotionShow {
    
    String promotionName, promotionCondition, promotionResult;

    public PromotionShow() {
    }

    public PromotionShow(String promotionName, String promotionCondition, String promotionResult) {
        this.promotionName = promotionName;
        this.promotionCondition = promotionCondition;
        this.promotionResult = promotionResult;
    }
    
    public PromotionShow copy() {
        return new PromotionShow(getPromotionName(), getPromotionCondition(), getPromotionResult());
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionCondition() {
        return promotionCondition;
    }

    public void setPromotionCondition(String promotionCondition) {
        this.promotionCondition = promotionCondition;
    }

    public String getPromotionResult() {
        return promotionResult;
    }

    public void setPromotionResult(String promotionResult) {
        this.promotionResult = promotionResult;
    }
}
