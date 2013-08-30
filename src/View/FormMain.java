/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.SellControl;
import Control.StockControl;
import Model.Hub.ChangePasswordHub;
import Model.Hub.LoginHub;
import Model.Hub.MainHub;
import Model.Hub.ProductHub;
import Model.Hub.PromotionHub;
import Model.Hub.StockHub;
import Model.Hub.UserHub;
import Model.SellModel;
import Model.UserLoginModel;
import Utility.AutoSuggest;
import Utility.JTextFieldLimit;
import Utility.Message;
import Utility.Util;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import static java.lang.Thread.sleep;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JAVA DEV
 */
public class FormMain extends javax.swing.JFrame {

    //======================== ส่งสัญญาณเปลี่ยน defaultCloseOperation
    private boolean unExit = false; //เปลี่ยน defaultclose กลับเป็น Exit หลังยกเลิกการปิดฟอร์ม
    //======================== boolean ส่งสัญญาณ การเปิด-ปิดฟอร์ม
    public boolean openProduct = false; //ส่งสัญญาณว่ามีการเปิดฟอร์ม manageproduct อยู่หรือไม่
    public boolean openUser = false; //ส่งสัญญาณว่ามีการเปิดฟอร์ม register อยู่หรือไม่
    public boolean openChangePassword = false;
    public boolean openStock = false;
    public boolean openPromotion = false;
    public boolean multiplyAmount = false; //เช็คว่ามีการกดตัวคูณหรือเปล่า
    public boolean doCash = false; //================================================= ต้องเปลี่ยนกลับ
    public boolean swapMulti = false; //บอกว่าพึ่งสลับไปอย่าพึ่งสลับกลับ
    public boolean submitPro = false; //================================================= ต้องเปลี่ยนกลับ
    public boolean reserve = false;
    public String getFrom = "";
    public String lastFocus = "";
    public String[] tbSellSelectedValue; //เก็บค่าข้อมูลล่าสุดจากตารางแถวที่คลิก
    //======================== totalCost
    public double gbTotalCost, gbTotalCostPro = 0;
    //เวคเตอร์ใช้เก็บBackup จาก sell
    public Vector vectorSellBackup;
    //======================== row sorter
    private TableRowSorter<TableModel> sellRowSorter, proActiveRowSorter, proRecommendRowSorter;
    //======================== defaultTableModel
    private DefaultTableModel modelSell, modelActive, modelRecom;
    //======================== static user
    public static UserLoginModel user; //เก็บค่า User ปัจจุบันที่ Login เข้ามา
    //======================== AutoSuggest
    AutoSuggest suggestProductName;
    //======================== Document Listener
    DocumentListener docList;
    //======================== instance
    SellControl sellControl;
    StockControl stockControl;
    //======================== inputMap
    InputMap inputMap;
    final KeyStroke keyStroke;

    /**
     * Creates new form MainForm
     */
    public FormMain() {

        initComponents();
        setEnv();
        MainHub.setMain(this);
        Thread clock = new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        sleep(1000);
                        Util.setCurrentDate();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        };
        clock.start();

        user = new UserLoginModel();
        //เทเบิลโมเดล
        modelSell = (DefaultTableModel) tbSell.getModel();
        modelActive = (DefaultTableModel) tbActive.getModel();
        modelRecom = (DefaultTableModel) tbRecom.getModel();
        //vectorSellBackup
        vectorSellBackup = new Vector();
        //set rowsorter
        sellRowSorter = new TableRowSorter<>(tbSell.getModel());
        tbSell.setRowSorter(sellRowSorter);
        proActiveRowSorter = new TableRowSorter<>(tbActive.getModel());
        tbActive.setRowSorter(proActiveRowSorter);
        proRecommendRowSorter = new TableRowSorter<>(tbRecom.getModel());
        tbRecom.setRowSorter(proRecommendRowSorter);
        //เปลี่ยนขนาดฟอนต์หัวเทเบิล
        tbSell.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tbActive.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tbRecom.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        //maxlength 13 หลัก
        txtSellBarcode.setDocument(new JTextFieldLimit(13));
        //Autosuggest
        suggestProductName = new AutoSuggest(cbxSellProductName, "SELECT DISTINCT(productName) FROM tbproduct ORDER BY productId ASC");
        //instance
        sellControl = new SellControl();
        stockControl = new StockControl(sellControl); //เอาไว้จัดการกับการตัดสต๊อก
        //Doct List
        docList = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                //ถ้ามีตัวอักษรครบ 13 ตัวให้เรียก puttovector เพื่อเพิ่มรายการสินค้าใส่เวคเตอร์
                //ต้องใช้เมธอดตรวจสอบคัดเอาเฉพาะตัวเลข
                if (!"".equals(txtSellBarcode.getText()) && txtSellBarcode.getText().length() == 13) {
                    //เมื่อเกิดอีเวนต์ต้องเซต textCost ให้เรียบร้อยก่อน
                    String sql = "SELECT productId FROM tbproduct WHERE productBarcode = '" + txtSellBarcode.getText() + "'";

                    if (Util.getRowCount(sql) > 0) {
                        sellControl.putDataToVector("barcode");
                        sellControl.showData();
                    }

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!"".equals(txtSellBarcode.getText()) && txtSellBarcode.getText().length() == 13) {

                    String sql = "SELECT productId FROM tbproduct WHERE productBarcode = '" + txtSellBarcode.getText() + "'";

                    if (Util.getRowCount(sql) > 0) {
                        sellControl.putDataToVector("barcode");
                        sellControl.showData();
                    }

                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (!"".equals(txtSellBarcode.getText()) && txtSellBarcode.getText().length() == 13) {

                    String sql = "SELECT productId FROM tbproduct WHERE productBarcode = '" + txtSellBarcode.getText() + "'";

                    if (Util.getRowCount(sql) > 0) {
                        sellControl.putDataToVector("barcode");
                        sellControl.showData();
                    }

                }
            }
        };

        //=============== เพิ่ม Event ดักจับการโฟกัสวินโดว์เพื่อเปลี่ยน DefaultCloseOperation หลังจากยกเลิกการปิดฟอร์ม
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent evt) {
                if (unExit) {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    unExit = false;
                }
            }
        });

        //=============== เพิ่ม Event ดักจับการเปลี่ยนแปลงข้อความ
        txtSellBarcode.getDocument().addDocumentListener(docList);

        keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK);
        inputMap = txtSellBarcode.getInputMap();
        inputMap.put(keyStroke, "submit");
        txtSellBarcode.getActionMap().put("submit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sunmitPayment();
            }

            private void sunmitPayment() {

                if (!sellControl.getVectorPromotionActive().isEmpty() && !submitPro) {
                    Message.showWarningMessage("พบข้อผิดพลาด", "ยังไม่คิดโปรโมชั่น");
                } else if (!sellControl.getVectorSell().isEmpty()) {
                    if (Message.showConfirmMessage("สอบถามความต้องการ", "ต้องการคิดเงินหรือไม่?") == 0) {
                        boolean cutOffStock = stockControl.cutOffStock(); //ตัดยอดสินค้าที่ขายออกจากสต๊อก เพิ่มบิล เซล โปรแอคทีฟรวดเดียวเสร็จ
                        if (cutOffStock) {
                            sellControl = new SellControl(); // เคลียร์เวคเตอร์
                            stockControl = new StockControl(sellControl);
                            sellControl.showData(); //ดรียกดชว์เพื่อเคลียร์ตารางการขาย
                            setLabelToEmpty(); //เซต textCost ต่างๆเป็นค่าว่าง
                        }

                    }
                }
            }
        });



        tbSellSelectedValue = new String[2];
    } //end Constructor

    //<editor-fold defaultstate="collapsed" desc="Getter">
    public JLabel getLblSellBarcode() {
        return lblSellBarcode;
    }

    public JLabel getLblDate() {
        return lblDate;
    }

    public JLabel getLblTime() {
        return lblTime;
    }

    public JPanel getPnTextCost() {
        return pnTextCost;
    }

    public JTextField getTxtSellBarcode() {
        return txtSellBarcode;
    }

    public JComboBox getCbxSellProductName() {
        return cbxSellProductName;
    }

    public JLabel getLblAmount() {
        return lblAmount;
    }

    public JLabel getLblCost() {
        return lblCost;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public JLabel getLblTotalCost() {
        return lblTotalCost;
    }

    public JLabel getLblCash() {
        return lblCash;
    }

    public JLabel getLblCashCost() {
        return lblCashCost;
    }

    public JLabel getLblChange() {
        return lblChange;
    }

    public JLabel getLblChangeCost() {
        return lblChangeCost;
    }

    public DefaultTableModel getModelSell() {
        return modelSell;
    }

    public JTable getTbSell() {
        return tbSell;
    }

    public DefaultTableModel getModelActive() {
        return modelActive;
    }

    public DefaultTableModel getModelRecom() {
        return modelRecom;
    }

    public JTable getTbActive() {
        return tbActive;
    }

    public JTable getTbRecom() {
        return tbRecom;
    }

    public SellControl getSellControl() {
        return sellControl;
    }

    public JDesktopPane getDspMain() {
        return dspMain;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Setter">
    public void setTxtSellBarcode(JTextField txtSellBarcode) {
        this.txtSellBarcode = txtSellBarcode;
    }
    //</editor-fold>

    //set Environmet เบื้องต้น
    private void setEnv() {

        setLocationRelativeTo(null);
        LoginHub.login.dispose(); //ปิด Login

    }

    private void setLabelToEmpty() {
        lblAmount.setText("");
        lblCost.setText("");
        lblTotal.setText("");
        lblTotalCost.setText("");
        lblCash.setText("");
        lblCashCost.setText("");
        lblChange.setText("");
        lblChangeCost.setText("");
    }

    //set Environment หลังจาก Login
    public void setEnvLogin() {

        btnRecovery.setSelected(true);

        //set label textcost
        setLabelToEmpty();

        //set menu and tital bar
        mnSystem_Logout.setText("ออกจากระบบ (" + user.getUserName() + ")"); //เซตเมนู Logout ตามด้วยชื่อผู้ใช้
        String title = getTitle();
        setTitle(title + " - ( " + user.getUserName() + " : " + user.getUserType() + " )"); //set title บวกชื่อบวกประเภท

        switch (user.getUserType()) {

            case "Admin":
                break;
            case "User":
                mnUser_ManageUser.setEnabled(false);
                mnOption_SetDatabase.setEnabled(false);
                btnUser.setEnabled(false);
                break;

        }

//        //เมนูสำหรับแอดมิน
//        if (user.getUserType().equals("Admin")) {
//            UserHub.user.getRdbAdminType().setEnabled(false);
//            UserHub.user.getBtnUserDelete().setEnabled(false);
//        }
//        //เมนูสำหรับยูสเซอร์
//        if (user.getUserType().equals("User")) {
//            //เมนูที่ต้องการบล็อกไม่ให้ user ใช้งาน
//            mnUser_ManageUser.setEnabled(false);
//            mnOption_SetDatabase.setEnabled(false);
//            btnUser.setEnabled(false);
//        }
    }

    //set Environment หลังจาก Logout
    public void setEnvLogout() {
        dispose(); //ปิด Main
        new FormLogin().setVisible(true); //เปิด Login
    }

    //พักรายการขาย
    public void reserveFunc() {
        if (btnReserve.isEnabled() && !btnRecovery.isEnabled()) {
            if (!reserve && !sellControl.getVectorSell().isEmpty() && vectorSellBackup.isEmpty()) {
                sellControl.moveFromVectorSell();
                reserve = true;
                btnReserve.setSelected(true);
                btnReserve.setEnabled(false);
                btnRecovery.setEnabled(true);
            } else {
                btnRecovery.setSelected(true);
            }
        }
    }

    //กู้รายการขาย
    public void recoveryFunc() {
        if (btnRecovery.isEnabled() && !btnReserve.isEnabled()) {
            if (reserve && !vectorSellBackup.isEmpty() && sellControl.getVectorSell().isEmpty()) {
                sellControl.moveToVectorSell();
                reserve = false;
                btnRecovery.setSelected(true);
                btnRecovery.setEnabled(false);
                btnReserve.setEnabled(true);
            } else {
                btnReserve.setSelected(true);
            }
        }
    }

    //ควบคุมการเปิดฟอร์มสินค้า
    private void openProduct() {

        tbpMain.setSelectedIndex(1); //เลือกมาที่แท็บจัดการร้านค้า

        if (!openProduct) { //ถ้าโปรดักไม่ได้เปิดอยู่
            FormProduct mProduct = new FormProduct(); //สร้างขึ้นมาใหม่
            dspMain.add(mProduct, JDesktopPane.POPUP_LAYER); //เพิ่มลงใน desktop pane
            try {
                mProduct.setMaximum(true); //แล้วจึงค่อยเซตให้ขยายเต็มพื้นที่
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            mProduct.getTbpProduct().setSelectedIndex(0);
            mProduct.setVisible(true); //เปิดการมองเห็น
            openProduct = true; //ปรับตัวเช็คเป็น true ว่าเปิดแล้ว

        } else if (openProduct) { //ถ้าโปรดักเปิดอยู่

            if (ProductHub.product.isIcon()) { //ถ้าพักอยู่
                dspMain.getDesktopManager().deiconifyFrame(ProductHub.product);
                dspMain.getDesktopManager().minimizeFrame(ProductHub.product);
                dspMain.getDesktopManager().maximizeFrame(ProductHub.product);
            }
            try {
                ProductHub.product.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            ProductHub.product.moveToFront();
        }

    }

    //ควบคุมการเปิดฟอร์มผู้ใช้
    private void openUser() {

        tbpMain.setSelectedIndex(1); //เลือกไปที่แท็บหลังร้าน

        if (!openUser) { //ถ้ายังไม่ได้มีการเปิดอยู่

            FormUser mUser = new FormUser(); //สร้าง Ref ขึ้นมา
            dspMain.add(mUser, JDesktopPane.POPUP_LAYER); //แอดลงไปใน desktopPane
            try {
                mUser.setMaximum(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            mUser.getTbpRegisterMain().setSelectedIndex(0); //เลือกไปที่แท็บผู้ใช้
            mUser.setVisible(true); //เปิดการมองเห็น
            openUser = true; //ปรับเป็น true เพื่อแสดงว่ามีการเปิดอยู่

        } else if (openUser) { //ถ้ามีการเปิดอยู่

            if (UserHub.user.isIcon()) { //แสเงว่ามีการ new แล้ว อ้างอิงจาก Hub ได้เลย
                dspMain.getDesktopManager().deiconifyFrame(UserHub.user);
                dspMain.getDesktopManager().minimizeFrame(UserHub.user);
                dspMain.getDesktopManager().maximizeFrame(UserHub.user);
            }
            try {
                UserHub.user.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            UserHub.user.moveToFront();

        }

    }

    //ควบคุมการเปิดฟอร์มเปลี่ยนรหัสผ่าน
    private void openChangePassword() {

        tbpMain.setSelectedIndex(1); //เลือกไปที่แท็บหลังร้าน

        if (!openChangePassword) {

            FormChangePassword mChange = new FormChangePassword();
            dspMain.add(mChange, JDesktopPane.POPUP_LAYER); //แอดลงไปใน desktopPane
            mChange.setVisible(true);
            openChangePassword = true;

        } else if (openChangePassword) {

            if (ChangePasswordHub.changePassword.isIcon()) { //แสดงว่ามีการ new แล้ว อ้างอิงจาก Hub ได้เลย
                dspMain.getDesktopManager().deiconifyFrame(ChangePasswordHub.changePassword);
                dspMain.getDesktopManager().minimizeFrame(ChangePasswordHub.changePassword);
                dspMain.getDesktopManager().maximizeFrame(ChangePasswordHub.changePassword);
            }
            try {
                ChangePasswordHub.changePassword.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            ChangePasswordHub.changePassword.moveToFront();

        }

    }

    //ควบคุมการเปิดฟอร์มคลังสินค้า
    private void openStock(int index) {

        tbpMain.setSelectedIndex(1); //เลือกไปที่แท็บหลังร้าน

        if (!openStock) {

            FormStock mStock = new FormStock();
            dspMain.add(mStock, JDesktopPane.POPUP_LAYER); //แอดลงไปใน desktopPane
            try {
                mStock.setMaximum(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            mStock.getTbpStockMain().setSelectedIndex(index); //เลือกไปที่แท็บผู้ใช้
            mStock.setVisible(true); //เปิดการมองเห็น
            openStock = true; //ปรับเป็น true เพื่อแสดงว่ามีการเปิดอยู่

        } else if (openStock) {

            if (StockHub.stock.isIcon()) { //แสเงว่ามีการ new แล้ว อ้างอิงจาก Hub ได้เลย
                dspMain.getDesktopManager().deiconifyFrame(StockHub.stock);
                dspMain.getDesktopManager().minimizeFrame(StockHub.stock);
                dspMain.getDesktopManager().maximizeFrame(StockHub.stock);
            }
            try {
                StockHub.stock.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            StockHub.stock.moveToFront();

        }

    }
    
    //ควบคุมการเปิดฟอร์มโปรโมชัน
    private void openPromotion() {
        
        tbpMain.setSelectedIndex(1); //เลือกไปที่แท็บหลังร้าน

        if (!openPromotion) {

            FormPromotion mPromotion = new FormPromotion();
            dspMain.add(mPromotion, JDesktopPane.POPUP_LAYER); //แอดลงไปใน desktopPane
            try {
                mPromotion.setMaximum(true);

            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
//            mPromotion.getTbpStockMain().setSelectedIndex(0); //เลือกไปที่แท็บผู้ใช้
            mPromotion.setVisible(true); //เปิดการมองเห็น
            openPromotion = true; //ปรับเป็น true เพื่อแสดงว่ามีการเปิดอยู่

        } else if (openPromotion) {

            if (PromotionHub.promotion.isIcon()) { //แสดงว่ามีการ new แล้ว อ้างอิงจาก Hub ได้เลย
                dspMain.getDesktopManager().deiconifyFrame(PromotionHub.promotion);
                dspMain.getDesktopManager().minimizeFrame(PromotionHub.promotion);
                dspMain.getDesktopManager().maximizeFrame(PromotionHub.promotion);
            }
            try {
                PromotionHub.promotion.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            PromotionHub.promotion.moveToFront();

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

        btnGroup = new javax.swing.ButtonGroup();
        pnMain = new javax.swing.JPanel();
        tb = new javax.swing.JToolBar();
        btnProduct = new javax.swing.JButton();
        btnPromotion = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnUser = new javax.swing.JButton();
        btnChangePassword = new javax.swing.JButton();
        btnSummarySell = new javax.swing.JButton();
        btnSetProgram = new javax.swing.JButton();
        tbpMain = new javax.swing.JTabbedPane();
        pnFrontShop = new javax.swing.JPanel();
        pnForntTop = new javax.swing.JPanel();
        txtSellBarcode = new javax.swing.JTextField();
        cbxSellProductName = new javax.swing.JComboBox();
        btnAddtoSell = new javax.swing.JButton();
        lblSellBarcode = new javax.swing.JLabel();
        lblSellProductName = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        btnClearSell = new javax.swing.JButton();
        pnFrontCenter = new javax.swing.JPanel();
        pnFronTableSell = new javax.swing.JPanel();
        scpTableSell = new javax.swing.JScrollPane();
        tbSell = new javax.swing.JTable();
        pnFrontCenterBottom = new javax.swing.JPanel();
        scpTableActive = new javax.swing.JScrollPane();
        tbActive = new javax.swing.JTable();
        scpTableRecom = new javax.swing.JScrollPane();
        tbRecom = new javax.swing.JTable();
        pnFrontRigth = new javax.swing.JPanel();
        pnTextCost = new javax.swing.JPanel();
        lblAmount = new javax.swing.JLabel();
        lblCost = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTotalCost = new javax.swing.JLabel();
        lblCash = new javax.swing.JLabel();
        lblCashCost = new javax.swing.JLabel();
        lblChange = new javax.swing.JLabel();
        lblChangeCost = new javax.swing.JLabel();
        pnOper = new javax.swing.JPanel();
        btnNum7 = new javax.swing.JButton();
        btnNum8 = new javax.swing.JButton();
        btnNum9 = new javax.swing.JButton();
        btnQty = new javax.swing.JButton();
        btnBaspace = new javax.swing.JButton();
        btnNum4 = new javax.swing.JButton();
        btnNum5 = new javax.swing.JButton();
        btnNum6 = new javax.swing.JButton();
        btnDot = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnNum1 = new javax.swing.JButton();
        btnNum2 = new javax.swing.JButton();
        btnNum3 = new javax.swing.JButton();
        btnNum0 = new javax.swing.JButton();
        btnEnter = new javax.swing.JButton();
        btnPro = new javax.swing.JButton();
        btnCash = new javax.swing.JButton();
        btnReserve = new javax.swing.JToggleButton();
        btnRecovery = new javax.swing.JToggleButton();
        btnPayment = new javax.swing.JButton();
        pnBackShop = new javax.swing.JPanel();
        dspMain = new javax.swing.JDesktopPane();
        mnb = new javax.swing.JMenuBar();
        mnSystem = new javax.swing.JMenu();
        mnSystem_Logout = new javax.swing.JMenuItem();
        mnSystem_Exit = new javax.swing.JMenuItem();
        mnProduct = new javax.swing.JMenu();
        mnProduct_ManageProduct = new javax.swing.JMenuItem();
        mnProduct_Promotion = new javax.swing.JMenuItem();
        mnProduct_Stock = new javax.swing.JMenu();
        mnProduct_ManageStock = new javax.swing.JMenuItem();
        mnProduct_AmountProduct = new javax.swing.JMenuItem();
        mnProduct_ExpireSoonProduct = new javax.swing.JMenuItem();
        mnUser = new javax.swing.JMenu();
        mnUser_ManageUser = new javax.swing.JMenuItem();
        mnUser_ChangePassword = new javax.swing.JMenuItem();
        mnReport = new javax.swing.JMenu();
        menuSell_SummarySell = new javax.swing.JMenuItem();
        mnOption = new javax.swing.JMenu();
        mnOption_SetDatabase = new javax.swing.JMenuItem();
        mnOption_setProgram = new javax.swing.JMenuItem();
        mnOther = new javax.swing.JMenu();
        mnOther_PrintBarcode = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenu();
        menuAbout_AboutEasyPosMart = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JEasy POS Mart 1.0");
        setExtendedState(6);
        setMinimumSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9)));
        setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9)));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        pnMain.setLayout(new java.awt.BorderLayout());

        tb.setBorder(null);
        tb.setFloatable(false);
        tb.setAlignmentY(0.5F);

        btnProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/package_add_32.png"))); // NOI18N
        btnProduct.setText(" จัดการสินค้า ");
        btnProduct.setFocusable(false);
        btnProduct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProduct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductMouseClicked(evt);
            }
        });
        tb.add(btnProduct);

        btnPromotion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/calendar_add_32.png"))); // NOI18N
        btnPromotion.setText("   โปรโมชัน   ");
        btnPromotion.setFocusable(false);
        btnPromotion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPromotion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionMouseClicked(evt);
            }
        });
        tb.add(btnPromotion);

        btnStock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bricks_32.png"))); // NOI18N
        btnStock.setText("   คลังสินค้า   ");
        btnStock.setFocusable(false);
        btnStock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockMouseClicked(evt);
            }
        });
        tb.add(btnStock);

        btnUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_add_32.png"))); // NOI18N
        btnUser.setText("  จัดการผู้ใช้  ");
        btnUser.setFocusable(false);
        btnUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserMouseClicked(evt);
            }
        });
        tb.add(btnUser);

        btnChangePassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/key_32.png"))); // NOI18N
        btnChangePassword.setText("เปลี่ยนรหัสผ่าน");
        btnChangePassword.setFocusable(false);
        btnChangePassword.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChangePassword.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChangePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangePasswordMouseClicked(evt);
            }
        });
        tb.add(btnChangePassword);

        btnSummarySell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSummarySell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/report_32.png"))); // NOI18N
        btnSummarySell.setText("รายงานสรุปยอด");
        btnSummarySell.setFocusable(false);
        btnSummarySell.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSummarySell.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb.add(btnSummarySell);

        btnSetProgram.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSetProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/asterisk_yellow_32.png"))); // NOI18N
        btnSetProgram.setText("ตั้งค่าโปรแกรม");
        btnSetProgram.setFocusable(false);
        btnSetProgram.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSetProgram.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tb.add(btnSetProgram);

        pnMain.add(tb, java.awt.BorderLayout.PAGE_START);

        tbpMain.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnFrontShop.setLayout(new java.awt.BorderLayout());

        pnForntTop.setPreferredSize(new java.awt.Dimension(10, 50));

        txtSellBarcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSellBarcode.setName("txtSellBarcode"); // NOI18N
        txtSellBarcode.setPreferredSize(new java.awt.Dimension(59, 25));
        txtSellBarcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSellBarcodeFocusGained(evt);
            }
        });
        txtSellBarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSellBarcodeKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSellBarcodeKeyReleased(evt);
            }
        });

        cbxSellProductName.setEditable(true);
        cbxSellProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxSellProductName.setPreferredSize(new java.awt.Dimension(56, 25));
        cbxSellProductName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxSellProductNamePopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbxSellProductName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbxSellProductNameFocusGained(evt);
            }
        });

        btnAddtoSell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddtoSell.setText("เพิ่ม");
        btnAddtoSell.setPreferredSize(new java.awt.Dimension(73, 25));
        btnAddtoSell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddtoSellMouseClicked(evt);
            }
        });

        lblSellBarcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSellBarcode.setText("รหัสบาร์โค๊ด :");
        lblSellBarcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblSellBarcodeFocusGained(evt);
            }
        });

        lblSellProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSellProductName.setText("ชื่อสินค้า :");

        lblDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDate.setText("วันเดือนปี");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTime.setText("เวลา");

        btnClearSell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnClearSell.setText("ล้างรายการขาย");
        btnClearSell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearSellMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnForntTopLayout = new javax.swing.GroupLayout(pnForntTop);
        pnForntTop.setLayout(pnForntTopLayout);
        pnForntTopLayout.setHorizontalGroup(
            pnForntTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnForntTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSellBarcode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSellBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSellProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxSellProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddtoSell, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClearSell)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 451, Short.MAX_VALUE)
                .addComponent(lblDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTime)
                .addContainerGap())
        );
        pnForntTopLayout.setVerticalGroup(
            pnForntTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnForntTopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnForntTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSellBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxSellProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddtoSell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSellBarcode)
                    .addComponent(lblSellProductName)
                    .addComponent(lblDate)
                    .addComponent(lblTime)
                    .addComponent(btnClearSell))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnForntTopLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddtoSell, btnClearSell, cbxSellProductName});

        pnFrontShop.add(pnForntTop, java.awt.BorderLayout.PAGE_START);

        pnFrontCenter.setLayout(new java.awt.BorderLayout());

        pnFronTableSell.setLayout(new java.awt.CardLayout());

        tbSell.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbSell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "รหัสบาร์โค๊ด", "ชื่อสินค้า", "จำนวน", "ราคา"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSell.setName("tbSell"); // NOI18N
        tbSell.setRowHeight(25);
        tbSell.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSellMouseClicked(evt);
            }
        });
        tbSell.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tbSellFocusGained(evt);
            }
        });
        tbSell.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSellKeyReleased(evt);
            }
        });
        scpTableSell.setViewportView(tbSell);
        tbSell.getColumnModel().getColumn(0).setResizable(false);
        tbSell.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbSell.getColumnModel().getColumn(1).setResizable(false);
        tbSell.getColumnModel().getColumn(2).setResizable(false);
        tbSell.getColumnModel().getColumn(2).setPreferredWidth(100);
        tbSell.getColumnModel().getColumn(3).setResizable(false);
        tbSell.getColumnModel().getColumn(3).setPreferredWidth(150);

        pnFronTableSell.add(scpTableSell, "card2");

        pnFrontCenter.add(pnFronTableSell, java.awt.BorderLayout.CENTER);

        pnFrontCenterBottom.setLayout(new java.awt.GridLayout(1, 2));

        scpTableActive.setPreferredSize(new java.awt.Dimension(452, 180));

        tbActive.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbActive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "โปรโมชั่น", "เงื่อนไข", "ผลลัพธ์"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbActive.setRowHeight(25);
        scpTableActive.setViewportView(tbActive);

        pnFrontCenterBottom.add(scpTableActive);
        scpTableActive.getAccessibleContext().setAccessibleName("");

        scpTableRecom.setPreferredSize(new java.awt.Dimension(452, 180));

        tbRecom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbRecom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "โปรโมชั่นแนะนำ", "เงื่อนไข", "ผลลัพธ์"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbRecom.setRowHeight(25);
        scpTableRecom.setViewportView(tbRecom);

        pnFrontCenterBottom.add(scpTableRecom);

        pnFrontCenter.add(pnFrontCenterBottom, java.awt.BorderLayout.PAGE_END);

        pnFrontShop.add(pnFrontCenter, java.awt.BorderLayout.CENTER);

        pnFrontRigth.setMinimumSize(new java.awt.Dimension(370, 75));
        pnFrontRigth.setPreferredSize(new java.awt.Dimension(370, 321));
        pnFrontRigth.setLayout(new java.awt.BorderLayout());

        pnTextCost.setBackground(new java.awt.Color(0, 0, 0));
        pnTextCost.setName("pnTxtCost"); // NOI18N
        pnTextCost.setPreferredSize(new java.awt.Dimension(300, 220));
        pnTextCost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnTextCostMouseClicked(evt);
            }
        });
        pnTextCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pnTextCostFocusGained(evt);
            }
        });
        pnTextCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pnTextCostKeyReleased(evt);
            }
        });

        lblAmount.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblAmount.setForeground(new java.awt.Color(255, 255, 51));
        lblAmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAmount.setText("Amount");

        lblCost.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblCost.setForeground(new java.awt.Color(255, 255, 51));
        lblCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCost.setText("Cost");

        lblTotal.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 51));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setText("Total");

        lblTotalCost.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblTotalCost.setForeground(new java.awt.Color(255, 255, 51));
        lblTotalCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalCost.setText("TotalCost");

        lblCash.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblCash.setForeground(new java.awt.Color(255, 255, 51));
        lblCash.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCash.setText("Cash");

        lblCashCost.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblCashCost.setForeground(new java.awt.Color(255, 255, 51));
        lblCashCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCashCost.setText("CashCost");

        lblChange.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblChange.setForeground(new java.awt.Color(255, 255, 51));
        lblChange.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblChange.setText("Change");

        lblChangeCost.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        lblChangeCost.setForeground(new java.awt.Color(255, 255, 51));
        lblChangeCost.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangeCost.setText("ChangeCost");

        javax.swing.GroupLayout pnTextCostLayout = new javax.swing.GroupLayout(pnTextCost);
        pnTextCost.setLayout(pnTextCostLayout);
        pnTextCostLayout.setHorizontalGroup(
            pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTextCostLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTextCostLayout.createSequentialGroup()
                        .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblChange, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblChangeCost, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(lblCashCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnTextCostLayout.createSequentialGroup()
                        .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnTextCostLayout.setVerticalGroup(
            pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTextCostLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCost)
                    .addComponent(lblAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(lblTotalCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCash)
                    .addComponent(lblCashCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTextCostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChange)
                    .addComponent(lblChangeCost))
                .addGap(23, 23, 23))
        );

        pnFrontRigth.add(pnTextCost, java.awt.BorderLayout.PAGE_START);

        pnOper.setMinimumSize(new java.awt.Dimension(330, 75));
        pnOper.setPreferredSize(new java.awt.Dimension(330, 270));
        pnOper.setRequestFocusEnabled(false);
        pnOper.setLayout(new java.awt.GridLayout(4, 5));

        btnNum7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum7.setText("7");
        btnNum7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum7MouseClicked(evt);
            }
        });
        pnOper.add(btnNum7);

        btnNum8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum8.setText("8");
        btnNum8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum8MouseClicked(evt);
            }
        });
        pnOper.add(btnNum8);

        btnNum9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum9.setText("9");
        btnNum9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum9MouseClicked(evt);
            }
        });
        pnOper.add(btnNum9);

        btnQty.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnQty.setText("*");
        btnQty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQtyMouseClicked(evt);
            }
        });
        pnOper.add(btnQty);

        btnBaspace.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnBaspace.setText("<--");
        btnBaspace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBaspaceMouseClicked(evt);
            }
        });
        pnOper.add(btnBaspace);

        btnNum4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum4.setText("4");
        btnNum4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum4MouseClicked(evt);
            }
        });
        pnOper.add(btnNum4);

        btnNum5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum5.setText("5");
        btnNum5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum5MouseClicked(evt);
            }
        });
        pnOper.add(btnNum5);

        btnNum6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum6.setText("6");
        btnNum6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum6MouseClicked(evt);
            }
        });
        pnOper.add(btnNum6);

        btnDot.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDot.setText(".");
        btnDot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDotMouseClicked(evt);
            }
        });
        pnOper.add(btnDot);

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDelete.setText("Del");
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        pnOper.add(btnDelete);

        btnNum1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum1.setText("1");
        btnNum1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum1MouseClicked(evt);
            }
        });
        pnOper.add(btnNum1);

        btnNum2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum2.setText("2");
        btnNum2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum2MouseClicked(evt);
            }
        });
        pnOper.add(btnNum2);

        btnNum3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum3.setText("3");
        btnNum3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum3MouseClicked(evt);
            }
        });
        pnOper.add(btnNum3);

        btnNum0.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnNum0.setText("0");
        btnNum0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNum0MouseClicked(evt);
            }
        });
        pnOper.add(btnNum0);

        btnEnter.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnEnter.setText("Ent");
        btnEnter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEnterMouseClicked(evt);
            }
        });
        pnOper.add(btnEnter);

        btnPro.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnPro.setText("Pro");
        btnPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProMouseClicked(evt);
            }
        });
        pnOper.add(btnPro);

        btnCash.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnCash.setText("Cash");
        btnCash.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCashMouseClicked(evt);
            }
        });
        pnOper.add(btnCash);

        btnGroup.add(btnReserve);
        btnReserve.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnReserve.setText("Res");
        btnReserve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReserveMouseClicked(evt);
            }
        });
        btnReserve.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnReserveItemStateChanged(evt);
            }
        });
        pnOper.add(btnReserve);

        btnGroup.add(btnRecovery);
        btnRecovery.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnRecovery.setText("Rec");
        btnRecovery.setEnabled(false);
        btnRecovery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRecoveryMouseClicked(evt);
            }
        });
        btnRecovery.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                btnRecoveryItemStateChanged(evt);
            }
        });
        pnOper.add(btnRecovery);

        btnPayment.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnPayment.setText("Pay");
        btnPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPaymentMouseClicked(evt);
            }
        });
        pnOper.add(btnPayment);

        pnFrontRigth.add(pnOper, java.awt.BorderLayout.CENTER);

        pnFrontShop.add(pnFrontRigth, java.awt.BorderLayout.LINE_END);

        tbpMain.addTab("  หน้าจอขายสินค้า  ", pnFrontShop);

        pnBackShop.setLayout(new java.awt.CardLayout());
        pnBackShop.add(dspMain, "card2");

        tbpMain.addTab("  จัดการร้านค้า / อื่นๆ  ", pnBackShop);

        pnMain.add(tbpMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnMain, "card2");

        mnSystem.setText("ระบบ");
        mnSystem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnSystem_Logout.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnSystem_Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/computer_key.png"))); // NOI18N
        mnSystem_Logout.setText("ออกจากระบบ");
        mnSystem_Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSystem_LogoutActionPerformed(evt);
            }
        });
        mnSystem.add(mnSystem_Logout);

        mnSystem_Exit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnSystem_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/computer_go.png"))); // NOI18N
        mnSystem_Exit.setText("ออกจากโปรแกรม");
        mnSystem_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSystem_ExitActionPerformed(evt);
            }
        });
        mnSystem.add(mnSystem_Exit);

        mnb.add(mnSystem);

        mnProduct.setText("สินค้า");
        mnProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnProduct_ManageProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnProduct_ManageProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/package_add.png"))); // NOI18N
        mnProduct_ManageProduct.setText("จัดการสินค้า/ประเภทสินค้า");
        mnProduct_ManageProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProduct_ManageProductActionPerformed(evt);
            }
        });
        mnProduct.add(mnProduct_ManageProduct);

        mnProduct_Promotion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnProduct_Promotion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/calendar_add.png"))); // NOI18N
        mnProduct_Promotion.setText("จัดการโปรโมชัน");
        mnProduct_Promotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProduct_PromotionActionPerformed(evt);
            }
        });
        mnProduct.add(mnProduct_Promotion);

        mnProduct_Stock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bricks.png"))); // NOI18N
        mnProduct_Stock.setText("คลังสินค้า");
        mnProduct_Stock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnProduct_ManageStock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnProduct_ManageStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/brick_add.png"))); // NOI18N
        mnProduct_ManageStock.setText("จัดการคลังสินค้า");
        mnProduct_ManageStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProduct_ManageStockActionPerformed(evt);
            }
        });
        mnProduct_Stock.add(mnProduct_ManageStock);

        mnProduct_AmountProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnProduct_AmountProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bricks.png"))); // NOI18N
        mnProduct_AmountProduct.setText("ยอดสินค้าคงเหลือ");
        mnProduct_AmountProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProduct_AmountProductActionPerformed(evt);
            }
        });
        mnProduct_Stock.add(mnProduct_AmountProduct);

        mnProduct_ExpireSoonProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnProduct_ExpireSoonProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/brick_error.png"))); // NOI18N
        mnProduct_ExpireSoonProduct.setText("สินค้าใกล้หมดอายุ");
        mnProduct_ExpireSoonProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnProduct_ExpireSoonProductActionPerformed(evt);
            }
        });
        mnProduct_Stock.add(mnProduct_ExpireSoonProduct);

        mnProduct.add(mnProduct_Stock);

        mnb.add(mnProduct);

        mnUser.setText("ผู้ใช้");
        mnUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnUser_ManageUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnUser_ManageUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_add.png"))); // NOI18N
        mnUser_ManageUser.setText("เพิ่มผู้ใช้/พนักงาน");
        mnUser_ManageUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUser_ManageUserActionPerformed(evt);
            }
        });
        mnUser.add(mnUser_ManageUser);

        mnUser_ChangePassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnUser_ChangePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/key.png"))); // NOI18N
        mnUser_ChangePassword.setText("เปลี่ยนรหัสผ่าน");
        mnUser_ChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUser_ChangePasswordActionPerformed(evt);
            }
        });
        mnUser.add(mnUser_ChangePassword);

        mnb.add(mnUser);

        mnReport.setText("รายงาน");
        mnReport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        menuSell_SummarySell.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuSell_SummarySell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/report.png"))); // NOI18N
        menuSell_SummarySell.setText("สรุปยอดขาย");
        mnReport.add(menuSell_SummarySell);

        mnb.add(mnReport);

        mnOption.setText("การตั้งค่า");
        mnOption.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnOption_SetDatabase.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnOption_SetDatabase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/database_gear.png"))); // NOI18N
        mnOption_SetDatabase.setText("ตั้งค่าฐานข้อมูล");
        mnOption.add(mnOption_SetDatabase);

        mnOption_setProgram.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnOption_setProgram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/asterisk_yellow.png"))); // NOI18N
        mnOption_setProgram.setText("ตั้งค่าโปรแกรม");
        mnOption.add(mnOption_setProgram);

        mnb.add(mnOption);

        mnOther.setText("อื่นๆ");
        mnOther.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        mnOther_PrintBarcode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mnOther_PrintBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        mnOther_PrintBarcode.setText("พิมพ์บาร์โค๊ด");
        mnOther.add(mnOther_PrintBarcode);

        mnb.add(mnOther);

        mnAbout.setText("เกี่ยวกับ");
        mnAbout.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        menuAbout_AboutEasyPosMart.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuAbout_AboutEasyPosMart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/information.png"))); // NOI18N
        menuAbout_AboutEasyPosMart.setText("เกี่ยวกับโปรแกรม");
        mnAbout.add(menuAbout_AboutEasyPosMart);

        mnb.add(mnAbout);

        setJMenuBar(mnb);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductMouseClicked
        // TODO add your handling code here:

        if (btnProduct.isEnabled() && evt.getButton() == MouseEvent.BUTTON1) { //ถ้าเป็นการคลิ๊กด้วยเมาส์ซ้าย
            openProduct();
        }
    }//GEN-LAST:event_btnProductMouseClicked
    private void mnSystem_LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSystem_LogoutActionPerformed
        // TODO add your handling code here:
        if (Message.showConfirmMessage("ออกจากระบบ", "ต้องการออกจากระบบ?") == 0) {
            setEnvLogout();
        }
    }//GEN-LAST:event_mnSystem_LogoutActionPerformed

    private void mnSystem_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSystem_ExitActionPerformed
        // TODO add your handling code here:
        if (Message.showConfirmMessage("ออกจากโปรแกรม", "ต้องการออกจากโปรแกรม?") == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_mnSystem_ExitActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (Message.showConfirmMessage("ออกจากโปรแกรม", "ต้องการออกจากโปรแกรม?") == 0) {
            System.exit(0);
        } else {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            unExit = true;
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMouseClicked
        // TODO add your handling code here:   

        if (btnUser.isEnabled() && evt.getButton() == MouseEvent.BUTTON1) { //ถ้าเป็นการคลิ๊กด้วยเมาส์ซ้าย
            openUser();
        }
    }//GEN-LAST:event_btnUserMouseClicked

    private void mnProduct_ManageProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProduct_ManageProductActionPerformed
        // TODO add your handling code here:
        openProduct();
    }//GEN-LAST:event_mnProduct_ManageProductActionPerformed

    private void mnUser_ManageUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUser_ManageUserActionPerformed
        // TODO add your handling code here:
        openUser();
    }//GEN-LAST:event_mnUser_ManageUserActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void btnAddtoSellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddtoSellMouseClicked
        // TODO add your handling code here:

        if (cbxSellProductName.getSelectedIndex() > -1) {
            sellControl.putDataToVector("productname");
            sellControl.showData();
        }
        cbxSellProductName.setSelectedIndex(-1);
        txtSellBarcode.requestFocus(); //ส่งโฟกัสกลับไปที่ txtsellbarcode

    }//GEN-LAST:event_btnAddtoSellMouseClicked

    private void tbSellFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tbSellFocusGained
        // TODO add your handling code here:
        lastFocus = "tbsell";
    }//GEN-LAST:event_tbSellFocusGained

    private void btnQtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQtyMouseClicked
        // TODO add your handling code here:
        if (!multiplyAmount) {
            multiplyAmount = true;
            lblAmount.setText("@");
            pnTextCost.requestFocus();
        } else {
            multiplyAmount = false;
            lblAmount.setText("@1");
            txtSellBarcode.requestFocus();
        }

    }//GEN-LAST:event_btnQtyMouseClicked

    private void txtSellBarcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellBarcodeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            if (c == '*') {
                multiplyAmount = true;
                swapMulti = true;
                lblAmount.setText("@");
                getFrom = "sellbarcode";
                pnTextCost.requestFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_txtSellBarcodeKeyTyped

    private void pnTextCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnTextCostKeyReleased
        // TODO add your handling code here:

        //ทำกับจำนวนสินค้า
        char c = evt.getKeyChar(); //ป้อนตัวเลข
        if (multiplyAmount && Character.isDigit(c)) {
            lblAmount.setText(lblAmount.getText() + c);
        } else if (multiplyAmount && evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && lblAmount.getText().length() > 1) { //การลบ
            lblAmount.setText(lblAmount.getText().substring(0, lblAmount.getText().length() - 1));
        }

        //ทำกับเงินจ่าย
        if (doCash) { //ถ้าเป็นตัวเลขอย่างเดียว ไม่มีตัวคูณ
            char d = evt.getKeyChar();

            if (MainHub.main.getFocusOwner().getName().equals("pnTxtCost")) {

                if (Character.isDigit(d) || d == '.') { //ถ้าเป็นตัวเลขหรือจุดทศนิยม
                    String text = MainHub.main.getLblCashCost().getText();
                    MainHub.main.getLblCashCost().setText(text + Character.toString(d));
                } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && !lblCashCost.getText().isEmpty()) { //ถ้าเป็นปุ่มลบ
                    String text = MainHub.main.getLblCashCost().getText();
                    MainHub.main.getLblCashCost().setText(text.substring(0, text.length() - 1));
                }
            }
        }

        //ยกเลิกตัวคูณ
        //ต้องมี swap ดักไว้เพราะส่งมาจาก txtsellBarcode ด้วย keyType มันจะมีตัว * ติดมาด้วยซึ่งมาจะทำงานด้านล่างคือสิ่งยกเลิกคูณทันที
        if (multiplyAmount && !swapMulti && c == '*') { //กดคูณทำเพื่อปิด
            multiplyAmount = false;
            lblAmount.setText("@1");
            if (getFrom.equals("sellbarcode")) {
                txtSellBarcode.requestFocus();
            } else if (getFrom.equals("sellproductname")) {
                cbxSellProductName.requestFocus();
            }
            getFrom = "";
        }

        if ((evt.getKeyCode() == 109 || evt.getKeyCode() == KeyEvent.VK_MINUS) && !doCash && !submitPro) { //ปุ่มลบ
            sellControl.submitPro();
        }

        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            reserveFunc();
        }

        if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            recoveryFunc();
        }

        //ป้อนจำนวนสินค้าเสร็จแล้ว กด enter ให้โฟกัสกลับไปที่ txtSellBarcode
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && (!doCash || multiplyAmount)) {
            switch (getFrom) {
                case "sellbarcode":
                    txtSellBarcode.requestFocus();
                    break;
                case "sellproductname":
                    cbxSellProductName.requestFocus();
                    break;
                default:
                    txtSellBarcode.requestFocus();
            }
            getFrom = "";

        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER && doCash) { //ถ้ากด enter ตอน doCash เป็น true แสดงว่าป้อนเงินจ่ายเสร็จแล้ว
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            doCash = false;

            //เซตจำนวนเป็นว่าง
            lblAmount.setText("");
            lblCost.setText("");

            //รับ cashCost มาแปลงให้แสดงเป็นรูปแบบเงิน
            if (!lblCashCost.getText().isEmpty()) {
                double cashCost = Double.parseDouble(lblCashCost.getText());
                lblCashCost.setText(nf.format(cashCost));

                //ส่วนของเงินทอน
                lblChange.setText("เงินทอน");
//            System.out.println("CashCost : " + cashCost);

                //รับ totalCost มาเป็น double
                double totalCost = Util.fillterNumberToDouble(lblTotalCost.getText());
//            System.out.println("TotalCost : " + totalCost);

                //คิดเงินทอน
                double changeCost = (cashCost - totalCost);
//            System.out.println("ChangeCost : " + changeCost);

                //เซต changeCost ด้วยเงินทอน
                lblChangeCost.setText(nf.format(changeCost));
            }

            txtSellBarcode.requestFocus();

        }

        //ปรับ swap เพื่อยกเลิกเงื่อนไข สามารถกด * ซ้ำเพื่อคิดการคูณ
        swapMulti = false;
    }//GEN-LAST:event_pnTextCostKeyReleased

    private void txtSellBarcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellBarcodeKeyReleased
        // TODO add your handling code here:

        if ((evt.getKeyCode() == KeyEvent.VK_ADD || evt.getKeyCode() == 61) && !lblTotalCost.getText().isEmpty() && !sellControl.getVectorSell().isEmpty()) { //กด + คือเพิ่มเงินที่ลูกค้าจ่าย

            doCash = true;
            lblCash.setText("เงินจ่าย");
            lblCashCost.setText("");
            pnTextCost.requestFocus();

        }

        if ((evt.getKeyCode() == KeyEvent.VK_MINUS || evt.getKeyCode() == 109) && !doCash && !submitPro) {
            sellControl.submitPro();
        }

        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            reserveFunc();
        }

        if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            recoveryFunc();
        }

//        KeyStroke keystr = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK);

//        if (!lblTotalCost.getText().isEmpty() && !lblCashCost.getText().isEmpty() && !lblChangeCost.getText().isEmpty()) { //กด enter คือคิดยอด
//
//            keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK,true);
//            inputMap.put(keyStroke, "submit");
//
//        } else {
//
//            keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK,false);
//            inputMap.put(keyStroke, "submit");
////            inputMap.remove(keystr);
//        }

    }//GEN-LAST:event_txtSellBarcodeKeyReleased

    private void pnTextCostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnTextCostMouseClicked
        // TODO add your handling code here:
        pnTextCost.requestFocus();
        System.out.println(getFocusOwner().getName());
    }//GEN-LAST:event_pnTextCostMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        txtSellBarcode.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void cbxSellProductNamePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxSellProductNamePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        suggestProductName.queryResult();
    }//GEN-LAST:event_cbxSellProductNamePopupMenuWillBecomeVisible

    private void btnNum1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum1MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "1");
    }//GEN-LAST:event_btnNum1MouseClicked

    private void txtSellBarcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSellBarcodeFocusGained
        // TODO add your handling code here:
        lastFocus = "txtsellbarcode";
    }//GEN-LAST:event_txtSellBarcodeFocusGained

    private void pnTextCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pnTextCostFocusGained
        // TODO add your handling code here:
        lastFocus = "pntxtcost";
    }//GEN-LAST:event_pnTextCostFocusGained

    private void btnNum2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum2MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "2");
    }//GEN-LAST:event_btnNum2MouseClicked

    private void btnNum3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum3MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "3");
    }//GEN-LAST:event_btnNum3MouseClicked

    private void btnNum4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum4MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "4");
    }//GEN-LAST:event_btnNum4MouseClicked

    private void btnNum5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum5MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "5");
    }//GEN-LAST:event_btnNum5MouseClicked

    private void btnNum6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum6MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "6");
    }//GEN-LAST:event_btnNum6MouseClicked

    private void btnNum7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum7MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "7");
    }//GEN-LAST:event_btnNum7MouseClicked

    private void btnNum8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum8MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "8");
    }//GEN-LAST:event_btnNum8MouseClicked

    private void btnNum9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum9MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "9");
    }//GEN-LAST:event_btnNum9MouseClicked

    private void btnNum0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNum0MouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, "0");
    }//GEN-LAST:event_btnNum0MouseClicked

    private void btnDotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDotMouseClicked
        // TODO add your handling code here:
        Util.buttonNumberControl(evt, ".");
    }//GEN-LAST:event_btnDotMouseClicked

    private void btnBaspaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBaspaceMouseClicked
        // TODO add your handling code here:
        Util.buttonBackSpaceControl(evt);
    }//GEN-LAST:event_btnBaspaceMouseClicked

    private void btnEnterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnterMouseClicked
        // TODO add your handling code here:
        if (!doCash || multiplyAmount) { //ป้อนตัวคูณเสร็จแล้วยังอยู่ในขั้นตอนป้อนรายการสินค้า ก็ต้องกลับไปที่ txtsellbarcode หรือ cbxproductname 
            switch (lastFocus) {
                case "pntxtcost":
                    txtSellBarcode.requestFocus();
                case "txtsellbarcode":
                    txtSellBarcode.requestFocus();
                    break;
                case "cbxsellproductname":
                    cbxSellProductName.requestFocus();
                    break;
            }
        }

        if (doCash && !lblCashCost.getText().isEmpty()) { //ถ้าเป็นการ enter ป้อนเงินจ่าย
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            doCash = false;

            //เซตจำนวนเป็นว่าง
            lblAmount.setText("");
            lblCost.setText("");

            //รับ cashCost มาแปลงให้แสดงเป็นรูปแบบเงิน
            if (!lblCashCost.getText().isEmpty()) {
                double cashCost = Double.parseDouble(lblCashCost.getText());
                lblCashCost.setText(nf.format(cashCost));

                //ส่วนของเงินทอน
                lblChange.setText("เงินทอน");
//            System.out.println("CashCost : " + cashCost);

                //รับ totalCost มาเป็น double
                double totalCost = Util.fillterNumberToDouble(lblTotalCost.getText());
//            System.out.println("TotalCost : " + totalCost);

                //คิดเงินทอน
                double changeCost = (cashCost - totalCost);
//            System.out.println("ChangeCost : " + changeCost);

                //เซต changeCost ด้วยเงินทอน
                lblChangeCost.setText(nf.format(changeCost));
            }

            txtSellBarcode.requestFocus();


        }
    }//GEN-LAST:event_btnEnterMouseClicked

    private void cbxSellProductNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxSellProductNameFocusGained
        // TODO add your handling code here:
        lastFocus = "cbxsellproductname";
    }//GEN-LAST:event_cbxSellProductNameFocusGained

    private void btnCashMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCashMouseClicked
        // TODO add your handling code here:
        if (!doCash && !lblTotalCost.getText().isEmpty() && !sellControl.getVectorSell().isEmpty()) {
            doCash = true;
            lblCash.setText("เงินจ่าย");
            lblCashCost.setText("");
            pnTextCost.requestFocus();
        }
    }//GEN-LAST:event_btnCashMouseClicked

    private void btnPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPaymentMouseClicked
        // TODO add your handling code here:
        if (!lblTotalCost.getText().isEmpty() && !lblCashCost.getText().isEmpty() && !lblChangeCost.getText().isEmpty()) {
            if (Message.showConfirmMessage("คิดเงิน", "ต้องการคิดเงินหรือไม่?") == 0) {
                if (!sellControl.getVectorPromotionActive().isEmpty() && !submitPro) {
                    Message.showWarningMessage("มีข้อผิดพลาด", "ยังไม่ได้คิดโปรโมชั่น");
                } else if (!sellControl.getVectorPromotionActive().isEmpty()) {
                    boolean cutOffStock = stockControl.cutOffStock(); //ตัดยอดสินค้าที่ขายออกจากสต๊อก เพิ่มบิล เซล โปรแอคทีฟรวดเดียวเสร็จ
                    if (cutOffStock) {
                        sellControl = new SellControl(); // เคลียร์เวคเตอร์
                        stockControl = new StockControl(sellControl);
                        sellControl.showData(); //ดรียกดชว์เพื่อเคลียร์ตารางการขาย
                        setLabelToEmpty(); //เซต textCost ต่างๆเป็นค่าว่าง
                    }

                }
            }
        }
    }//GEN-LAST:event_btnPaymentMouseClicked

    private void btnProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProMouseClicked
        // TODO add your handling code here:
        if ((lastFocus.equals("txtsellbarcode") || lastFocus.equals("pntxtcost")) && !doCash && !submitPro) {
            sellControl.submitPro();
        }
    }//GEN-LAST:event_btnProMouseClicked

    private void tbSellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSellMouseClicked
        // TODO add your handling code here:
        int selRow = tbSell.getSelectedRow();

        tbSellSelectedValue[0] = tbSell.getValueAt(selRow, 0).toString();
        tbSellSelectedValue[1] = tbSell.getValueAt(selRow, 3).toString();

    }//GEN-LAST:event_tbSellMouseClicked

    private void tbSellKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSellKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int selRow = tbSell.getSelectedRow();

            sellControl.editSellTable(tbSell.getValueAt(selRow, 0).toString(), tbSell.getValueAt(selRow, 2).toString());

        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {

            for (int i = 0; i < sellControl.getVectorSell().size(); i++) { //วน vectorsell

                SellModel sell = (SellModel) sellControl.getVectorSell().elementAt(i); //คลี่ออกมา

                if (sell.getProductBarcode().equals(tbSellSelectedValue[0])) { //จับเอาที่โปรดักไอดีตรงกัน
                    sellControl.getVectorSell().remove(i);
                }
            }

            sellControl.showData();
            sellControl.getTotal();

        }
    }//GEN-LAST:event_tbSellKeyReleased

    private void lblSellBarcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblSellBarcodeFocusGained
        // TODO add your handling code here:
        txtSellBarcode.requestFocus();
        txtSellBarcode.setText("");
    }//GEN-LAST:event_lblSellBarcodeFocusGained

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        // TODO add your handling code here:
        if (lastFocus.equals("tbsell")) {
            sellControl.deleteSellTable(tbSellSelectedValue[0]);
        }
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void btnReserveItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnReserveItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReserveItemStateChanged

    private void btnRecoveryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_btnRecoveryItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRecoveryItemStateChanged

    private void btnReserveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReserveMouseClicked
        // TODO add your handling code here:
        reserveFunc();
    }//GEN-LAST:event_btnReserveMouseClicked

    private void btnRecoveryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRecoveryMouseClicked
        // TODO add your handling code here:
        recoveryFunc();
    }//GEN-LAST:event_btnRecoveryMouseClicked

    private void btnClearSellMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearSellMouseClicked
        // TODO add your handling code here:



        if (!sellControl.getVectorSell().isEmpty()) {

            int result = JOptionPane.showOptionDialog(null, "ต้องการล้างรายการขาย?", "สอบถามความต้องการ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"ลบ", "ยกเลิก"}, null);

            if (result == 0) {
                sellControl = new SellControl();
                stockControl = new StockControl(sellControl);
                sellControl.showData();
                setLabelToEmpty();
            }

        }

    }//GEN-LAST:event_btnClearSellMouseClicked

    private void mnUser_ChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUser_ChangePasswordActionPerformed
        // TODO add your handling code here:
        openChangePassword();
    }//GEN-LAST:event_mnUser_ChangePasswordActionPerformed

    private void btnChangePasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangePasswordMouseClicked
        // TODO add your handling code here:
        if (btnChangePassword.isEnabled() && evt.getButton() == MouseEvent.BUTTON1) {
            openChangePassword();
        }
    }//GEN-LAST:event_btnChangePasswordMouseClicked

    private void btnStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseClicked
        // TODO add your handling code here:
        if (btnStock.isEnabled() && evt.getButton() == MouseEvent.BUTTON1) {
            openStock(0);
        }
    }//GEN-LAST:event_btnStockMouseClicked

    private void mnProduct_ManageStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProduct_ManageStockActionPerformed
        // TODO add your handling code here:
        openStock(0);
    }//GEN-LAST:event_mnProduct_ManageStockActionPerformed

    private void mnProduct_AmountProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProduct_AmountProductActionPerformed
        // TODO add your handling code here:
        openStock(1);
    }//GEN-LAST:event_mnProduct_AmountProductActionPerformed

    private void mnProduct_ExpireSoonProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProduct_ExpireSoonProductActionPerformed
        // TODO add your handling code here:
        openStock(2);
    }//GEN-LAST:event_mnProduct_ExpireSoonProductActionPerformed

    private void btnPromotionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionMouseClicked
        // TODO add your handling code here:
        if (btnPromotion.isEnabled() && evt.getButton() == MouseEvent.BUTTON1) {
            openPromotion();
        }
    }//GEN-LAST:event_btnPromotionMouseClicked

    private void mnProduct_PromotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnProduct_PromotionActionPerformed
        // TODO add your handling code here:
        openPromotion();
    }//GEN-LAST:event_mnProduct_PromotionActionPerformed

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
            java.util.logging.Logger.getLogger(FormMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMain.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddtoSell;
    private javax.swing.JButton btnBaspace;
    private javax.swing.JButton btnCash;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnClearSell;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDot;
    private javax.swing.JButton btnEnter;
    private javax.swing.ButtonGroup btnGroup;
    private javax.swing.JButton btnNum0;
    private javax.swing.JButton btnNum1;
    private javax.swing.JButton btnNum2;
    private javax.swing.JButton btnNum3;
    private javax.swing.JButton btnNum4;
    private javax.swing.JButton btnNum5;
    private javax.swing.JButton btnNum6;
    private javax.swing.JButton btnNum7;
    private javax.swing.JButton btnNum8;
    private javax.swing.JButton btnNum9;
    private javax.swing.JButton btnPayment;
    private javax.swing.JButton btnPro;
    private javax.swing.JButton btnProduct;
    private javax.swing.JButton btnPromotion;
    private javax.swing.JButton btnQty;
    private javax.swing.JToggleButton btnRecovery;
    private javax.swing.JToggleButton btnReserve;
    private javax.swing.JButton btnSetProgram;
    private javax.swing.JButton btnStock;
    private javax.swing.JButton btnSummarySell;
    private javax.swing.JButton btnUser;
    private javax.swing.JComboBox cbxSellProductName;
    private javax.swing.JDesktopPane dspMain;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblCash;
    private javax.swing.JLabel lblCashCost;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblChangeCost;
    private javax.swing.JLabel lblCost;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblSellBarcode;
    private javax.swing.JLabel lblSellProductName;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JMenuItem menuAbout_AboutEasyPosMart;
    private javax.swing.JMenuItem menuSell_SummarySell;
    private javax.swing.JMenu mnAbout;
    private javax.swing.JMenu mnOption;
    private javax.swing.JMenuItem mnOption_SetDatabase;
    private javax.swing.JMenuItem mnOption_setProgram;
    private javax.swing.JMenu mnOther;
    private javax.swing.JMenuItem mnOther_PrintBarcode;
    private javax.swing.JMenu mnProduct;
    private javax.swing.JMenuItem mnProduct_AmountProduct;
    private javax.swing.JMenuItem mnProduct_ExpireSoonProduct;
    private javax.swing.JMenuItem mnProduct_ManageProduct;
    private javax.swing.JMenuItem mnProduct_ManageStock;
    private javax.swing.JMenuItem mnProduct_Promotion;
    private javax.swing.JMenu mnProduct_Stock;
    private javax.swing.JMenu mnReport;
    private javax.swing.JMenu mnSystem;
    private javax.swing.JMenuItem mnSystem_Exit;
    private javax.swing.JMenuItem mnSystem_Logout;
    private javax.swing.JMenu mnUser;
    private javax.swing.JMenuItem mnUser_ChangePassword;
    private javax.swing.JMenuItem mnUser_ManageUser;
    private javax.swing.JMenuBar mnb;
    private javax.swing.JPanel pnBackShop;
    private javax.swing.JPanel pnForntTop;
    private javax.swing.JPanel pnFronTableSell;
    private javax.swing.JPanel pnFrontCenter;
    private javax.swing.JPanel pnFrontCenterBottom;
    private javax.swing.JPanel pnFrontRigth;
    private javax.swing.JPanel pnFrontShop;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnOper;
    private javax.swing.JPanel pnTextCost;
    private javax.swing.JScrollPane scpTableActive;
    private javax.swing.JScrollPane scpTableRecom;
    private javax.swing.JScrollPane scpTableSell;
    private javax.swing.JToolBar tb;
    private javax.swing.JTable tbActive;
    private javax.swing.JTable tbRecom;
    private javax.swing.JTable tbSell;
    private javax.swing.JTabbedPane tbpMain;
    private javax.swing.JTextField txtSellBarcode;
    // End of variables declaration//GEN-END:variables
}
