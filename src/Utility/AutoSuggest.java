/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

/**
 *
 * @author JAVA DEV
 */
import Model.Hub.MainHub;
import View.FormMain;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AutoSuggest extends JPanel {

    private JTextField tf;
    private JComboBox combo;
    private String sql;
    private Vector<String> v = new Vector<>();

    public AutoSuggest(JComboBox cbx, String sql) {

        this.combo = cbx;
        this.sql = sql;
        combo.setEditable(true);
        tf = (JTextField) combo.getEditor().getEditorComponent();
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = tf.getText();
                        if (text.length() == 0) {
                            combo.hidePopup();
                            setModel(new DefaultComboBoxModel(v), "");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if (m.getSize() == 0 || hide_flag) {
                                combo.hidePopup();
                                hide_flag = false;
                            } else {
                                setModel(m, text);
                                combo.showPopup();
                            }
                        }
                    }
                });

                    if (evt.getKeyChar() == '*') { //ส่งโฟกัสไปเพื่อเปิดตัวคูณ
                        MainHub.main.multiplyAmount = true;
                        MainHub.main.swapMulti = true;
                        MainHub.main.getLblAmount().setText("@");
                        MainHub.main.getFrom = "sellproductname";
                        MainHub.main.getPnTextCost().requestFocus();
                        evt.consume();

                    } else if ((evt.getKeyCode() == 109 || evt.getKeyCode() == KeyEvent.VK_MINUS) && !MainHub.main.doCash && !MainHub.main.submitPro) {
                        MainHub.main.getSellControl().submitPro();
                    } else if ((evt.getKeyCode() == KeyEvent.VK_ADD || evt.getKeyCode() == 0) && !MainHub.main.getLblTotalCost().getText().isEmpty() && !MainHub.main.getSellControl().getVectorSell().isEmpty()) { //กด + คือเพิ่มเงินที่ลูกค้าจ่าย

                        MainHub.main.doCash = true;
                        MainHub.main.getLblCash().setText("เงินจ่าย");
                        MainHub.main.getLblCashCost().setText("");
                        MainHub.main.getPnTextCost().requestFocus();

                    }

            }

            @Override
            public void keyPressed(KeyEvent evt) {

                String text = tf.getText();
                int code = evt.getKeyCode();
                if (code == KeyEvent.VK_ENTER) {
                    if (!v.contains(text)) {
                        v.addElement(text);
                        Collections.sort(v);
                        setModel(getSuggestedModel(v, text), text);
                    }
                    hide_flag = true;
                } else if (code == KeyEvent.VK_ESCAPE) {
                    hide_flag = true;
                } else if (code == KeyEvent.VK_RIGHT) {
                    for (int i = 0; i < v.size(); i++) {
                        String str = v.elementAt(i);
                        if (str.startsWith(text)) {
                            combo.setSelectedIndex(-1);
                            tf.setText(str);
                            return;
                        }
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent evt) {

                if (evt.getKeyCode() == KeyEvent.VK_ENTER) { //ถ้าเป็นการกด enter
                    if (MainHub.main.getCbxSellProductName().getSelectedIndex() > -1) { //รายการที่เลือกอยู่ต้องมากกว่า -1
                        MainHub.main.getSellControl().putDataToVector("productname"); //ทำค้นหาจากโปรดักเนม
                        MainHub.main.getSellControl().showData();
                    }
                    MainHub.main.getCbxSellProductName().setSelectedIndex(-1);
                    MainHub.main.getCbxSellProductName().setSelectedItem("");
                    MainHub.main.getTxtSellBarcode().requestFocus(); //ส่งโฟกัสกลับไปที่ txtsellbarcode
                }



            }
        });

        queryResult(); //เซตข้อมูลเริ่มต้นทั้งหมดลงในเวคเตอร์ V
        setModel(new DefaultComboBoxModel(v), "");
        JPanel p = new JPanel(new BorderLayout());

    }
    private boolean hide_flag = false;

    public void queryResult() {

        if (Util.getRowCount(sql) > 0) { //ถ้ามีผลลัพธ์มากกว่า 0 ให้ทำ ถ้ามีไม่มีเลยก็ไม่ต้องทำ
            try {
                ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
                v.clear();
                while (rs.next()) {
                    v.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AutoSuggest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void setModel(DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        tf.setText(str);
    }

    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String s : list) {
            if (s.startsWith(text)) {
                m.addElement(s);
            }
        }
        return m;
    }
}