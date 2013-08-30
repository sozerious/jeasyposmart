/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author java-dev
 */
public class MyComboBoxCellEditor {

    public MyComboBoxCellEditor() {
    }

    public void addComboToTable(JTable table, int column, JComboBox comboBox) {
        TableColumn gradeColumn = table.getColumnModel().getColumn(column);

        comboBox.removeAllItems();
        try {

            comboBox.addItem("นาย");
            comboBox.addItem("นาง");
            comboBox.addItem("นางสาว");

        } catch (Exception e) {
        }
        gradeColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    public void addComboToTable(JTable table, int column, JComboBox comboBox, Object[] value) {
        TableColumn gradeColumn = table.getColumnModel().getColumn(column);

        comboBox.removeAllItems();
        try {

            comboBox.setModel(new DefaultComboBoxModel(value));

        } catch (Exception e) {
        }
        gradeColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }
}
