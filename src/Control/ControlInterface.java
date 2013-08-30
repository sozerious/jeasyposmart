/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author JAVA DEV
 */
public interface ControlInterface {
    
    int[] dataLimit();
    
    String dataSort();
    
    void putDataToVector(boolean search);
    
    void showData(boolean update);
    
    void insertData();
    
    void updateData();
    
    void deleteData();
    
}
