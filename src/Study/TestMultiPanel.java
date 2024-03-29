/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Study;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author java-dev
 */
public class TestMultiPanel extends javax.swing.JFrame {

    MaskFormatter mf;
    
    /**
     * Creates new form TestMultiPanel
     */
    public TestMultiPanel() {
        initComponents();
        pnCard1.show(true);
        pnCard2.show(false);
        try {
//            MaskFormatter mf = new MaskFormatter("##-####-####");
            mf = new MaskFormatter("##/##/####");
             mf.setPlaceholderCharacter('_');
            fmt.setFormatterFactory(new DefaultFormatterFactory(mf));
//            
//            DateFormat df = new SimpleDateFormat("EEEEE ที่ dd MMMMM yyyy", new Locale("th", "TH"));
//            System.out.println("SimpleDateFormat : " + df.format(new Date()));
//            
//            DateFormat d = DateFormat.getDateInstance();
//            System.out.println("getDateInstance : " + d.format(new Date(88, 02, 11)));
            
//            DateFormatter dft = new DateFormatter(d);

            
        } catch (ParseException ex) {
            Logger.getLogger(TestMultiPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnMain = new javax.swing.JPanel();
        cbx = new javax.swing.JComboBox();
        pnBoardMain = new javax.swing.JPanel();
        pnCardMain = new javax.swing.JPanel();
        pnCard1 = new javax.swing.JPanel();
        txt = new javax.swing.JTextField();
        pnCard2 = new javax.swing.JPanel();
        cbx2 = new javax.swing.JComboBox();
        cbx3 = new javax.swing.JComboBox();
        fmt = new javax.swing.JFormattedTextField();
        btn = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        cbx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        cbx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxItemStateChanged(evt);
            }
        });

        pnBoardMain.setPreferredSize(new java.awt.Dimension(100, 100));
        pnBoardMain.setLayout(new java.awt.BorderLayout());

        pnCardMain.setPreferredSize(new java.awt.Dimension(120, 120));
        pnCardMain.setLayout(new java.awt.CardLayout());

        pnCard1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt.setText("jTextField1");

        javax.swing.GroupLayout pnCard1Layout = new javax.swing.GroupLayout(pnCard1);
        pnCard1.setLayout(pnCard1Layout);
        pnCard1Layout.setHorizontalGroup(
            pnCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnCard1Layout.setVerticalGroup(
            pnCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCard1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        pnCardMain.add(pnCard1, "card1");

        pnCard2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        cbx2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbx3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnCard2Layout = new javax.swing.GroupLayout(pnCard2);
        pnCard2.setLayout(pnCard2Layout);
        pnCard2Layout.setHorizontalGroup(
            pnCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCard2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbx2, 0, 76, Short.MAX_VALUE)
                    .addComponent(cbx3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnCard2Layout.setVerticalGroup(
            pnCard2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCard2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(cbx2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbx3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnCardMain.add(pnCard2, "card2");

        pnBoardMain.add(pnCardMain, java.awt.BorderLayout.CENTER);

        fmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fmtKeyReleased(evt);
            }
        });

        btn.setText("Get Text!!");
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMouseClicked(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnMainLayout = new javax.swing.GroupLayout(pnMain);
        pnMain.setLayout(pnMainLayout);
        pnMainLayout.setHorizontalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnBoardMain, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fmt))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        pnMainLayout.setVerticalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(fmt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnBoardMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        getContentPane().add(pnMain, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxItemStateChanged
        // TODO add your handling code here:
        int index = cbx.getSelectedIndex();
        
        switch(index) {
            case 0:
                pnCard1.show(true);
                pnCard2.show(false);
                break;
            case 1:
                pnCard2.show(true);
                pnCard1.show(false);
                break;
            
        }
        
    }//GEN-LAST:event_cbxItemStateChanged

    private void btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMouseClicked
        // TODO add your handling code here:
        if (fmt.getText().equals("__/__/____")) {
            System.out.println("Empty!!");
        } else {
            System.out.println(fmt.getText());
            System.out.println(fmt.getText().length());
        }
    }//GEN-LAST:event_btnMouseClicked

    private void fmtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fmtKeyReleased

    }//GEN-LAST:event_fmtKeyReleased

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // TODO add your handling code here:
        fmt.setValue("");
        fmt.setFormatterFactory(new DefaultFormatterFactory(mf));
    }//GEN-LAST:event_btnClearMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestMultiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestMultiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestMultiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestMultiPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestMultiPanel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn;
    private javax.swing.JButton btnClear;
    private javax.swing.JComboBox cbx;
    private javax.swing.JComboBox cbx2;
    private javax.swing.JComboBox cbx3;
    private javax.swing.JFormattedTextField fmt;
    private javax.swing.JPanel pnBoardMain;
    private javax.swing.JPanel pnCard1;
    private javax.swing.JPanel pnCard2;
    private javax.swing.JPanel pnCardMain;
    private javax.swing.JPanel pnMain;
    private javax.swing.JTextField txt;
    // End of variables declaration//GEN-END:variables
}
