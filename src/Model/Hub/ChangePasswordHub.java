/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormChangePassword;

/**
 *
 * @author java-dev
 */
public class ChangePasswordHub {
    
    public static FormChangePassword changePassword;

    public static FormChangePassword getChangePassword() {
        return changePassword;
    }

    public static void setChangePassword(FormChangePassword changePassword) {
        ChangePasswordHub.changePassword = changePassword;
    }

}
