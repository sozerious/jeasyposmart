/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormLogin;

/**
 *
 * @author JAVA DEV
 */
public class LoginHub {
    
    public static FormLogin login;

    public static FormLogin getLogin() {
        return login;
    }

    public static void setLogin(FormLogin login) {
        LoginHub.login = login;
    }

}
