/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Hub;

import View.FormMain;

/**
 *
 * @author JAVA DEV
 */
public class MainHub {
    
    public static FormMain main;

    public static FormMain getMain() {
        return main;
    }

    public static void setMain(FormMain main) {
        MainHub.main = main;
    }
}
