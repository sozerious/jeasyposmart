/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormUser;

/**
 *
 * @author JAVA DEV
 */
public class UserHub {

    public static FormUser user;

    public static FormUser getUser() {
        return user;
    }

    public static void setUser(FormUser register) {
        UserHub.user = register;
    }

}
