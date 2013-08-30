/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.UserLoginControl;
import Model.Hub.LoginHub;
import Utility.DBFactory;
import Utility.Message;
import Utility.Util;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author JAVA DEV
 */
public class FormLogin extends javax.swing.JFrame {

    /**
     * Creates new form LoginForm
     */
    public FormLogin() {
        initComponents();
        setEnv();
        LoginHub.setLogin(this);
    }

    private void setEnv() {
        
        setLocationRelativeTo(null);
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getter/Setter Login">
    public JComboBox getCbxUserType() {
        return cbxUserType;
    }
    
    public void setCbxUserType(JComboBox cbxUserType) {
        this.cbxUserType = cbxUserType;
    }
    
    public JPasswordField getTxtPassword() {
        return txtPassword;
    }
    
    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }
    
    public JTextField getTxtUsername() {
        return txtUsername;
    }
    
    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }
    //</editor-fold>

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnLoginMain = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        btnClear = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        lblUserPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblUserType = new javax.swing.JLabel();
        cbxUserType = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setPreferredSize(new java.awt.Dimension(300, 185));
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnLoginMain.setPreferredSize(new java.awt.Dimension(300, 170));

        txtPassword.setText("1");
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btnClear.setText("ล้าง");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUsername.setText("ชื่อผู้ใช้ :");

        btnLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/lock_open.png"))); // NOI18N
        btnLogin.setText("เข้าสู่ระบบ");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });

        lblUserPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserPassword.setText("รหัสผ่าน :");

        txtUsername.setText("1");
        txtUsername.setPreferredSize(new java.awt.Dimension(6, 25));
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
        });

        lblUserType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserType.setText("ระดับ :");

        cbxUserType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxUserType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Master", "Admin", "User" }));

        javax.swing.GroupLayout pnLoginMainLayout = new javax.swing.GroupLayout(pnLoginMain);
        pnLoginMain.setLayout(pnLoginMainLayout);
        pnLoginMainLayout.setHorizontalGroup(
            pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLoginMainLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserType, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword)
                    .addGroup(pnLoginMainLayout.createSequentialGroup()
                        .addComponent(cbxUserType, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLoginMainLayout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(btnLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        pnLoginMainLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClear, btnLogin});

        pnLoginMainLayout.setVerticalGroup(
            pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLoginMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserType)
                    .addComponent(cbxUserType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnLoginMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnLoginMainLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnLogin});

        pnLoginMainLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbxUserType, txtPassword, txtUsername});

        getContentPane().add(pnLoginMain, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        // TODO add your handling code here:
        if (Util.checkEmpty(new UserLoginControl())) {
            UserLoginControl login = new UserLoginControl(txtUsername.getText(), txtPassword.getText(), cbxUserType.getSelectedItem().toString());
            login.checkLogin();
        }
    }//GEN-LAST:event_btnLoginMouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(new UserLoginControl());
    }//GEN-LAST:event_btnClearMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if(!DBFactory.checkConnection()) {
            Message.showErrorMessage("โปรแกรมมีปัญหา", "โปรแกรมไม่สามารถทำงานได้");
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowOpened

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        // TODO add your handling code here:
        txtUsername.selectAll();
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        // TODO add your handling code here:
        txtPassword.selectAll();
    }//GEN-LAST:event_txtPasswordFocusGained

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
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLogin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox cbxUserType;
    private javax.swing.JLabel lblUserPassword;
    private javax.swing.JLabel lblUserType;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnLoginMain;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
