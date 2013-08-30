/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

/**
 *
 * @author java-dev
 */
public class TestGenericArray {
    
    public static void main(String[] args) {
        
        Object a[] = {"String",6,false,true};
        
        for (int i = 0; i < a.length; i++) {
            
            if (a[i] instanceof String) {
                System.out.println("a["+i+"] is String.");
            } else if (a[i] instanceof Integer) {
                System.out.println("a["+i+"] is Integer.");
            } else if (a[i] instanceof Double) {
                System.out.println("a["+i+"] is Double.");
            } else if (a[i] instanceof Boolean) {
                System.out.println("a["+i+"] is Boolean.");
            }
            
        }
        
    }
    
}
