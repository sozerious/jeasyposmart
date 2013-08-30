/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ProductControl;
import Control.ProductTypeControl;
import Model.Hub.MainHub;
import Model.Hub.ProductHub;
import Utility.DBFactory;
import Utility.JTextFieldLimit;
import Utility.Util;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JAVA DEV
 */
public class FormProduct extends javax.swing.JInternalFrame {

    ProductControl productControl;
    ProductTypeControl productTypeControl;
    
    private DefaultTableModel modelProduct, modelProductType;
    //======================== vector
    public static Vector vectorProduct, vectorProductInverse;
    public static Vector vectorProductType, vectorProductTypeInverse;
    
    /**
     * Creates new form ManageProductForm
     */
    public FormProduct() {
        initComponents();
        ProductHub.setProduct(this);
        
        setCbxProductType();
        
        modelProduct = (DefaultTableModel) tbProduct.getModel();
        modelProductType = (DefaultTableModel) tbProductType.getModel();
        
        tbProduct.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        tbProductType.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        txtProductBarcode.setDocument(new JTextFieldLimit(13));
        txtProductFundDecimal.setDocument(new JTextFieldLimit(2));
        txtProductPriceDecimal.setDocument(new JTextFieldLimit(2));
        txtProductFundDecimal.setText("00");
        txtProductPriceDecimal.setText("00");
        
        productControl = new ProductControl();
        productTypeControl = new ProductTypeControl();
        
        vectorProduct = new Vector();
        vectorProductInverse = new Vector();
        vectorProductType = new Vector();
        vectorProductTypeInverse = new Vector();
        
        productControl.showData(true);
        productTypeControl.showData(true);
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getter Product">
    
    public DefaultTableModel getModelProduct() {
        return modelProduct;
    }

    public JTable getTbProduct() {
        return tbProduct;
    }

    public JTabbedPane getTbpProduct() {
        return tbpProduct;
    }

    public JButton getBtnProductNext() {
        return btnProductNext;
    }

    public JButton getBtnProductPre() {
        return btnProductPre;
    }

    public JTextField getTxtProductBarcode() {
        return txtProductBarcode;
    }

    public JTextField getTxtProductFund() {
        return txtProductFund;
    }

    public JTextField getTxtProductFundDecimal() {
        return txtProductFundDecimal;
    }

    public JTextField getTxtProductName() {
        return txtProductName;
    }

    public JTextField getTxtProductPage() {
        return txtProductPage;
    }

    public JTextField getTxtProductPrice() {
        return txtProductPrice;
    }

    public JTextField getTxtProductPriceDecimal() {
        return txtProductPriceDecimal;
    }

    public JTextField getTxtProductSearch() {
        return txtProductSearch;
    }

    public JComboBox getCbxProductExpire() {
        return cbxProductExpire;
    }

    public JComboBox getCbxProductPerPage() {
        return cbxProductPerPage;
    }

    public JComboBox getCbxProductSearchFrom() {
        return cbxProductSearchFrom;
    }

    public JComboBox getCbxProductSortBy() {
        return cbxProductSortBy;
    }

    public JComboBox getCbxProductSortFrom() {
        return cbxProductSortFrom;
    }

    public JComboBox getCbxProductType() {
        return cbxProductType;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter ProductType">
    public JTextField getTxtProductTypePage() {
        return txtProductTypePage;
    }
    
    public JTextField getTxtProductTypeSearch() {
        return txtProductTypeSearch;
    }
    
    public JTextField getTxtProductType_ProductType() {
        return txtProductType_ProductType;
    }

    public DefaultTableModel getModelProductType() {
        return modelProductType;
    }

    public JTable getTbProductType() {
        return tbProductType;
    }

    public JButton getBtnProductTypeNext() {
        return btnProductTypeNext;
    }

    public JButton getBtnProductTypePre() {
        return btnProductTypePre;
    }

    public JComboBox getCbxProductTypePerPage() {
        return cbxProductTypePerPage;
    }

    public JComboBox getCbxProductTypeSearchFrom() {
        return cbxProductTypeSearchFrom;
    }

    public JComboBox getCbxProductTypeSortBy() {
        return cbxProductTypeSortBy;
    }

    public JComboBox getCbxProductTypeSortFrom() {
        return cbxProductTypeSortFrom;
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

        tbpProduct = new javax.swing.JTabbedPane();
        pnProductMain = new javax.swing.JPanel();
        pnInputTbProduct = new javax.swing.JPanel();
        scpTableProduct = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        pnProductPagination = new javax.swing.JPanel();
        btnProductNext = new javax.swing.JButton();
        btnProductPre = new javax.swing.JButton();
        txtProductPage = new javax.swing.JTextField();
        btnProductShowSearch = new javax.swing.JButton();
        scpProductRight = new javax.swing.JScrollPane();
        pnProductRight = new javax.swing.JPanel();
        pnProductManage = new javax.swing.JPanel();
        txtProductBarcode = new javax.swing.JTextField();
        cbxProductType = new javax.swing.JComboBox();
        lblProductType = new javax.swing.JLabel();
        cbxProductExpire = new javax.swing.JComboBox();
        txtProductFundDecimal = new javax.swing.JTextField();
        txtProductName = new javax.swing.JTextField();
        txtProductPrice = new javax.swing.JTextField();
        txtProductPriceDecimal = new javax.swing.JTextField();
        txtProductFund = new javax.swing.JTextField();
        lblProductBarcode = new javax.swing.JLabel();
        lblProductName = new javax.swing.JLabel();
        lblProductFund = new javax.swing.JLabel();
        lblProductProfit = new javax.swing.JLabel();
        lblProductExpire = new javax.swing.JLabel();
        lblProductFundDot = new javax.swing.JLabel();
        lblProductProfitDot = new javax.swing.JLabel();
        btnProductInsert = new javax.swing.JButton();
        btnProductEdit = new javax.swing.JButton();
        btnProductClear = new javax.swing.JButton();
        btnProduct_AddProductType = new javax.swing.JButton();
        btnProductDelete = new javax.swing.JButton();
        pnProductSearch = new javax.swing.JPanel();
        lbProductSearch = new javax.swing.JLabel();
        lblProductSearchForm = new javax.swing.JLabel();
        lblProductSortBy = new javax.swing.JLabel();
        lblProductSortForm = new javax.swing.JLabel();
        lblProductNumber = new javax.swing.JLabel();
        lblProductPerPage = new javax.swing.JLabel();
        txtProductSearch = new javax.swing.JTextField();
        cbxProductSearchFrom = new javax.swing.JComboBox();
        cbxProductSortBy = new javax.swing.JComboBox();
        cbxProductSortFrom = new javax.swing.JComboBox();
        cbxProductPerPage = new javax.swing.JComboBox();
        btnProductClearSearch = new javax.swing.JButton();
        pnProductTypeMain = new javax.swing.JPanel();
        pnInputTbProductType = new javax.swing.JPanel();
        scpTableProductType = new javax.swing.JScrollPane();
        tbProductType = new javax.swing.JTable();
        pnProductTypePagination = new javax.swing.JPanel();
        btnProductTypePre = new javax.swing.JButton();
        txtProductTypePage = new javax.swing.JTextField();
        btnProductTypeNext = new javax.swing.JButton();
        btnProductTypeShowSearch = new javax.swing.JButton();
        scpProductTypeRight = new javax.swing.JScrollPane();
        pnProductTypeRight = new javax.swing.JPanel();
        pnProductTypeManage = new javax.swing.JPanel();
        txtProductType_ProductType = new javax.swing.JTextField();
        lblProductType_ProductType = new javax.swing.JLabel();
        btnProductTypeInsert = new javax.swing.JButton();
        btnProductTypeUpdate = new javax.swing.JButton();
        btnProductTypeClear = new javax.swing.JButton();
        btnProductTypeDelete = new javax.swing.JButton();
        pnProductTypeSearch = new javax.swing.JPanel();
        lbProductTypeSearch = new javax.swing.JLabel();
        lblProductTypeSearchForm = new javax.swing.JLabel();
        lblProductTypeSortBy = new javax.swing.JLabel();
        lblProductTypeSortForm = new javax.swing.JLabel();
        lblProductTypeNumber = new javax.swing.JLabel();
        lblProductTypePerPage = new javax.swing.JLabel();
        txtProductTypeSearch = new javax.swing.JTextField();
        cbxProductTypeSearchFrom = new javax.swing.JComboBox();
        cbxProductTypeSortBy = new javax.swing.JComboBox();
        cbxProductTypeSortFrom = new javax.swing.JComboBox();
        cbxProductTypePerPage = new javax.swing.JComboBox();
        btnProductTypeClearSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("จัดการสินค้า / ประเภทสินค้า");
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(600, 450));
        setPreferredSize(new java.awt.Dimension(600, 450));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        tbpProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pnProductMain.setLayout(new java.awt.BorderLayout());

        pnInputTbProduct.setLayout(new java.awt.BorderLayout());

        tbProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ประเภทสินค้า", "รหัสบาร์โค๊ด", "ชื่อสินค้า", "ราคาทุน", "ราคาขาย", "หมดอายุ", "วันที่เพิ่ม"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProduct.setRowHeight(25);
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductMouseClicked(evt);
            }
        });
        scpTableProduct.setViewportView(tbProduct);
        tbProduct.getColumnModel().getColumn(0).setPreferredWidth(250);
        tbProduct.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbProduct.getColumnModel().getColumn(2).setPreferredWidth(350);
        tbProduct.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbProduct.getColumnModel().getColumn(4).setPreferredWidth(150);
        tbProduct.getColumnModel().getColumn(5).setPreferredWidth(100);
        tbProduct.getColumnModel().getColumn(6).setPreferredWidth(150);

        pnInputTbProduct.add(scpTableProduct, java.awt.BorderLayout.CENTER);

        pnProductPagination.setPreferredSize(new java.awt.Dimension(704, 50));

        btnProductNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductNext.setText("ถัดไป >>");
        btnProductNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductNextMouseClicked(evt);
            }
        });

        btnProductPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductPre.setText("<< ก่อนหน้า");
        btnProductPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnProductPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductPreMouseClicked(evt);
            }
        });

        txtProductPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProductPage.setText("1");
        txtProductPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtProductPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductPageFocusGained(evt);
            }
        });

        btnProductShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductShowSearch.setText("ปิดการค้นหา");
        btnProductShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnProductShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductPaginationLayout = new javax.swing.GroupLayout(pnProductPagination);
        pnProductPagination.setLayout(pnProductPaginationLayout);
        pnProductPaginationLayout.setHorizontalGroup(
            pnProductPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProductPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProductNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE)
                .addComponent(btnProductShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnProductPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnProductNext, btnProductPre});

        pnProductPaginationLayout.setVerticalGroup(
            pnProductPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProductPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProductPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductNext)
                    .addComponent(txtProductPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnProductPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductNext, btnProductPre, txtProductPage});

        pnInputTbProduct.add(pnProductPagination, java.awt.BorderLayout.PAGE_END);

        pnProductMain.add(pnInputTbProduct, java.awt.BorderLayout.CENTER);

        scpProductRight.setBorder(null);
        scpProductRight.setPreferredSize(new java.awt.Dimension(437, 526));

        pnProductRight.setPreferredSize(new java.awt.Dimension(422, 526));

        pnProductManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการสินค้า ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnProductManage.setPreferredSize(new java.awt.Dimension(415, 213));

        txtProductBarcode.setBackground(new java.awt.Color(255, 255, 0));
        txtProductBarcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductBarcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductBarcodeFocusGained(evt);
            }
        });
        txtProductBarcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductBarcodeKeyTyped(evt);
            }
        });

        cbxProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductType.setPreferredSize(new java.awt.Dimension(30, 25));
        cbxProductType.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxProductTypePopupMenuWillBecomeVisible(evt);
            }
        });

        lblProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductType.setText("ประเภทสินค้า : ");

        cbxProductExpire.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductExpire.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ไม่ใช่", "ใช่" }));

        txtProductFundDecimal.setBackground(new java.awt.Color(255, 255, 0));
        txtProductFundDecimal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductFundDecimal.setText("00");
        txtProductFundDecimal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductFundDecimalFocusGained(evt);
            }
        });
        txtProductFundDecimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductFundDecimalKeyTyped(evt);
            }
        });

        txtProductName.setBackground(new java.awt.Color(255, 255, 0));
        txtProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductNameFocusGained(evt);
            }
        });

        txtProductPrice.setBackground(new java.awt.Color(255, 255, 0));
        txtProductPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductPriceFocusGained(evt);
            }
        });
        txtProductPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductPriceKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductPriceKeyTyped(evt);
            }
        });

        txtProductPriceDecimal.setBackground(new java.awt.Color(255, 255, 0));
        txtProductPriceDecimal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductPriceDecimal.setText("00");
        txtProductPriceDecimal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductPriceDecimalFocusGained(evt);
            }
        });
        txtProductPriceDecimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductPriceDecimalKeyTyped(evt);
            }
        });

        txtProductFund.setBackground(new java.awt.Color(255, 255, 0));
        txtProductFund.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductFund.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductFundFocusGained(evt);
            }
        });
        txtProductFund.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductFundKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProductFundKeyTyped(evt);
            }
        });

        lblProductBarcode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductBarcode.setText("รหัสบาร์โค๊ด : ");

        lblProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductName.setText("ชื่อสินค้า : ");

        lblProductFund.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductFund.setText("ราคาทุน : ");

        lblProductProfit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductProfit.setText("ราคาขาย : ");

        lblProductExpire.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductExpire.setText("หมดอายุ : ");

        lblProductFundDot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductFundDot.setText(".");
        lblProductFundDot.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        lblProductProfitDot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductProfitDot.setText(".");
        lblProductProfitDot.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        btnProductInsert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductInsert.setText("เพิ่ม");
        btnProductInsert.setPreferredSize(new java.awt.Dimension(51, 25));
        btnProductInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductInsertMouseClicked(evt);
            }
        });

        btnProductEdit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductEdit.setText("แก้ไข");
        btnProductEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductEditMouseClicked(evt);
            }
        });

        btnProductClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductClear.setText("ล้าง");
        btnProductClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductClearMouseClicked(evt);
            }
        });

        btnProduct_AddProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProduct_AddProductType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/package_add.png"))); // NOI18N
        btnProduct_AddProductType.setText("เพิ่มประเภท");
        btnProduct_AddProductType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProduct_AddProductTypeMouseClicked(evt);
            }
        });

        btnProductDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductDelete.setText("ลบ");
        btnProductDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductManageLayout = new javax.swing.GroupLayout(pnProductManage);
        pnProductManage.setLayout(pnProductManageLayout);
        pnProductManageLayout.setHorizontalGroup(
            pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductManageLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnProductManageLayout.createSequentialGroup()
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProductType, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProductBarcode, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProductName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProductFund, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProductProfit, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProductExpire, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxProductExpire, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnProductManageLayout.createSequentialGroup()
                                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtProductFund)
                                    .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblProductProfitDot)
                                    .addComponent(lblProductFundDot))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProductFundDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtProductPriceDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnProductManageLayout.createSequentialGroup()
                                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtProductName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxProductType, javax.swing.GroupLayout.Alignment.LEADING, 0, 175, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProduct_AddProductType))))
                    .addGroup(pnProductManageLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnProductInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductClear)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnProductManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtProductFundDecimal, txtProductPriceDecimal});

        pnProductManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnProductClear, btnProductDelete, btnProductEdit, btnProductInsert});

        pnProductManageLayout.setVerticalGroup(
            pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductManageLayout.createSequentialGroup()
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductType)
                    .addComponent(btnProduct_AddProductType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductBarcode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductName))
                .addGap(6, 6, 6)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnProductManageLayout.createSequentialGroup()
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProductFund)
                            .addComponent(txtProductFund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductProfit)))
                    .addGroup(pnProductManageLayout.createSequentialGroup()
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductFundDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductFundDot))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProductPriceDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProductProfitDot))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxProductExpire, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductExpire))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnProductManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProductInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductEdit)
                    .addComponent(btnProductClear)
                    .addComponent(btnProductDelete))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pnProductManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductClear, btnProductDelete, btnProductEdit, btnProductInsert, btnProduct_AddProductType, cbxProductExpire, cbxProductType, txtProductBarcode, txtProductFund, txtProductFundDecimal, txtProductName, txtProductPrice, txtProductPriceDecimal});

        pnProductSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnProductSearch.setPreferredSize(new java.awt.Dimension(415, 215));

        lbProductSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbProductSearch.setText("ค้นหา : ");

        lblProductSearchForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductSearchForm.setText("ค้นหาจาก : ");

        lblProductSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductSortBy.setText("เรียงโดย : ");

        lblProductSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductSortForm.setText("เรียงจาก : ");

        lblProductNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductNumber.setText("จำนวน : ");

        lblProductPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductPerPage.setText("รายการ / หน้า");

        txtProductSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtProductSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductSearchFocusGained(evt);
            }
        });
        txtProductSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductSearchKeyReleased(evt);
            }
        });

        cbxProductSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ประเภทสินค้า", "รหัสบาร์โค๊ด", "ชื่อสินค้า", "ราคาทุน(ต่ำสุด)", "ราคาขาย(ต่ำสุด)" }));
        cbxProductSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductSearchFromItemStateChanged(evt);
            }
        });

        cbxProductSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ประเภทสินค้า", "รหัสบาร์โค๊ด", "ชื่อสินค้า", "ราคาทุน", "ราคาขาย" }));
        cbxProductSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductSortByItemStateChanged(evt);
            }
        });

        cbxProductSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxProductSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductSortFromItemStateChanged(evt);
            }
        });

        cbxProductPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxProductPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductPerPageItemStateChanged(evt);
            }
        });

        btnProductClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductClearSearch.setText("ล้าง");
        btnProductClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnProductClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductSearchLayout = new javax.swing.GroupLayout(pnProductSearch);
        pnProductSearch.setLayout(pnProductSearchLayout);
        pnProductSearchLayout.setHorizontalGroup(
            pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductSearchLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbProductSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductSearchForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProductClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnProductSearchLayout.createSequentialGroup()
                        .addComponent(cbxProductPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProductPerPage))
                    .addComponent(cbxProductSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxProductSortBy, 0, 128, Short.MAX_VALUE)
                    .addComponent(cbxProductSearchFrom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        pnProductSearchLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbxProductSearchFrom, cbxProductSortBy});

        pnProductSearchLayout.setVerticalGroup(
            pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProductSearch)
                    .addComponent(txtProductSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductSearchForm)
                    .addComponent(cbxProductSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductSortBy)
                    .addComponent(cbxProductSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductSortForm)
                    .addComponent(cbxProductSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductNumber)
                    .addComponent(lblProductPerPage)
                    .addComponent(cbxProductPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProductClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnProductSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductClearSearch, cbxProductPerPage, cbxProductSearchFrom, cbxProductSortBy, cbxProductSortFrom, txtProductSearch});

        javax.swing.GroupLayout pnProductRightLayout = new javax.swing.GroupLayout(pnProductRight);
        pnProductRight.setLayout(pnProductRightLayout);
        pnProductRightLayout.setHorizontalGroup(
            pnProductRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductRightLayout.createSequentialGroup()
                .addGroup(pnProductRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnProductManage, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addComponent(pnProductSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        pnProductRightLayout.setVerticalGroup(
            pnProductRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductRightLayout.createSequentialGroup()
                .addComponent(pnProductManage, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnProductSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scpProductRight.setViewportView(pnProductRight);
        pnProductRight.getAccessibleContext().setAccessibleName("");

        pnProductMain.add(scpProductRight, java.awt.BorderLayout.LINE_END);

        tbpProduct.addTab("จัดการสินค้า", pnProductMain);

        pnProductTypeMain.setLayout(new java.awt.BorderLayout());

        pnInputTbProductType.setLayout(new java.awt.BorderLayout());

        tbProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbProductType.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "หมายเลขประเภท", "ประเภทสินค้า"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProductType.setRowHeight(25);
        tbProductType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductTypeMouseClicked(evt);
            }
        });
        scpTableProductType.setViewportView(tbProductType);
        tbProductType.getColumnModel().getColumn(0).setPreferredWidth(70);
        tbProductType.getColumnModel().getColumn(1).setPreferredWidth(650);

        pnInputTbProductType.add(scpTableProductType, java.awt.BorderLayout.CENTER);

        pnProductTypePagination.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pnProductTypePagination.setPreferredSize(new java.awt.Dimension(766, 50));

        btnProductTypePre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypePre.setText("<< ก่อนหน้า");
        btnProductTypePre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnProductTypePre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypePreMouseClicked(evt);
            }
        });

        txtProductTypePage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductTypePage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProductTypePage.setText("1");
        txtProductTypePage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtProductTypePage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductTypePageFocusGained(evt);
            }
        });

        btnProductTypeNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeNext.setText("ถัดไป >>");
        btnProductTypeNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeNextMouseClicked(evt);
            }
        });

        btnProductTypeShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeShowSearch.setText("ปิดการค้นหา");
        btnProductTypeShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnProductTypeShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductTypePaginationLayout = new javax.swing.GroupLayout(pnProductTypePagination);
        pnProductTypePagination.setLayout(pnProductTypePaginationLayout);
        pnProductTypePaginationLayout.setHorizontalGroup(
            pnProductTypePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnProductTypePre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductTypePage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProductTypeNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 327, Short.MAX_VALUE)
                .addComponent(btnProductTypeShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnProductTypePaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnProductTypeNext, btnProductTypePre});

        pnProductTypePaginationLayout.setVerticalGroup(
            pnProductTypePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProductTypePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProductTypePre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductTypeNext)
                    .addComponent(txtProductTypePage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductTypeShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnProductTypePaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductTypeNext, btnProductTypePre, txtProductTypePage});

        pnInputTbProductType.add(pnProductTypePagination, java.awt.BorderLayout.PAGE_END);

        pnProductTypeMain.add(pnInputTbProductType, java.awt.BorderLayout.CENTER);

        scpProductTypeRight.setBorder(null);
        scpProductTypeRight.setPreferredSize(new java.awt.Dimension(353, 470));

        pnProductTypeRight.setPreferredSize(new java.awt.Dimension(338, 470));

        pnProductTypeManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการประเภทสินค้า ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        txtProductType_ProductType.setBackground(new java.awt.Color(255, 255, 0));
        txtProductType_ProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductType_ProductType.setPreferredSize(new java.awt.Dimension(59, 25));
        txtProductType_ProductType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductType_ProductTypeFocusGained(evt);
            }
        });

        lblProductType_ProductType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductType_ProductType.setText("ประเภทสินค้า : ");

        btnProductTypeInsert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeInsert.setText("เพิ่ม");
        btnProductTypeInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeInsertMouseClicked(evt);
            }
        });

        btnProductTypeUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeUpdate.setText("แก้ไข");
        btnProductTypeUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeUpdateMouseClicked(evt);
            }
        });

        btnProductTypeClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeClear.setText("ล้าง");
        btnProductTypeClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeClearMouseClicked(evt);
            }
        });

        btnProductTypeDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeDelete.setText("ลบ");
        btnProductTypeDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductTypeManageLayout = new javax.swing.GroupLayout(pnProductTypeManage);
        pnProductTypeManage.setLayout(pnProductTypeManageLayout);
        pnProductTypeManageLayout.setHorizontalGroup(
            pnProductTypeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeManageLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnProductTypeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnProductTypeManageLayout.createSequentialGroup()
                        .addComponent(btnProductTypeInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductTypeUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductTypeDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductTypeClear))
                    .addGroup(pnProductTypeManageLayout.createSequentialGroup()
                        .addComponent(lblProductType_ProductType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductType_ProductType, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnProductTypeManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnProductTypeClear, btnProductTypeDelete, btnProductTypeInsert, btnProductTypeUpdate});

        pnProductTypeManageLayout.setVerticalGroup(
            pnProductTypeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProductTypeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductType_ProductType)
                    .addComponent(txtProductType_ProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(pnProductTypeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProductTypeInsert)
                    .addComponent(btnProductTypeUpdate)
                    .addComponent(btnProductTypeClear)
                    .addComponent(btnProductTypeDelete))
                .addContainerGap())
        );

        pnProductTypeManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductTypeClear, btnProductTypeDelete, btnProductTypeInsert, btnProductTypeUpdate, txtProductType_ProductType});

        pnProductTypeSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnProductTypeSearch.setPreferredSize(new java.awt.Dimension(320, 226));

        lbProductTypeSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbProductTypeSearch.setText("ค้นหา : ");

        lblProductTypeSearchForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductTypeSearchForm.setText("ค้นหาจาก : ");

        lblProductTypeSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductTypeSortBy.setText("เรียงโดย : ");

        lblProductTypeSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductTypeSortForm.setText("เรียงจาก : ");

        lblProductTypeNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductTypeNumber.setText("จำนวน : ");

        lblProductTypePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProductTypePerPage.setText("รายการ / หน้า");

        txtProductTypeSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtProductTypeSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtProductTypeSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProductTypeSearchFocusGained(evt);
            }
        });
        txtProductTypeSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductTypeSearchKeyReleased(evt);
            }
        });

        cbxProductTypeSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductTypeSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ประเภทสินค้า" }));
        cbxProductTypeSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductTypeSearchFromItemStateChanged(evt);
            }
        });

        cbxProductTypeSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductTypeSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "หมายเลขประเภท", "ประเภทสินค้า" }));
        cbxProductTypeSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductTypeSortByItemStateChanged(evt);
            }
        });

        cbxProductTypeSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductTypeSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxProductTypeSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductTypeSortFromItemStateChanged(evt);
            }
        });

        cbxProductTypePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxProductTypePerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxProductTypePerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxProductTypePerPageItemStateChanged(evt);
            }
        });

        btnProductTypeClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnProductTypeClearSearch.setText("ล้าง");
        btnProductTypeClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnProductTypeClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductTypeClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnProductTypeSearchLayout = new javax.swing.GroupLayout(pnProductTypeSearch);
        pnProductTypeSearch.setLayout(pnProductTypeSearchLayout);
        pnProductTypeSearchLayout.setHorizontalGroup(
            pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbProductTypeSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductTypeSearchForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductTypeSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductTypeSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProductTypeNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProductTypeClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnProductTypeSearchLayout.createSequentialGroup()
                        .addComponent(cbxProductTypePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProductTypePerPage))
                    .addComponent(cbxProductTypeSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxProductTypeSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxProductTypeSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnProductTypeSearchLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbxProductTypeSearchFrom, cbxProductTypeSortBy});

        pnProductTypeSearchLayout.setVerticalGroup(
            pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbProductTypeSearch)
                    .addComponent(txtProductTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductTypeSearchForm)
                    .addComponent(cbxProductTypeSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductTypeSortBy)
                    .addComponent(cbxProductTypeSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductTypeSortForm)
                    .addComponent(cbxProductTypeSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnProductTypeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductTypeNumber)
                    .addComponent(lblProductTypePerPage)
                    .addComponent(cbxProductTypePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProductTypeClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnProductTypeSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnProductTypeClearSearch, cbxProductTypePerPage, cbxProductTypeSearchFrom, cbxProductTypeSortBy, cbxProductTypeSortFrom, txtProductTypeSearch});

        javax.swing.GroupLayout pnProductTypeRightLayout = new javax.swing.GroupLayout(pnProductTypeRight);
        pnProductTypeRight.setLayout(pnProductTypeRightLayout);
        pnProductTypeRightLayout.setHorizontalGroup(
            pnProductTypeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeRightLayout.createSequentialGroup()
                .addGroup(pnProductTypeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnProductTypeSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnProductTypeManage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 33, Short.MAX_VALUE))
        );
        pnProductTypeRightLayout.setVerticalGroup(
            pnProductTypeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductTypeRightLayout.createSequentialGroup()
                .addComponent(pnProductTypeManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnProductTypeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        scpProductTypeRight.setViewportView(pnProductTypeRight);

        pnProductTypeMain.add(scpProductTypeRight, java.awt.BorderLayout.LINE_END);

        tbpProduct.addTab("จัดการประเภทสินค้า", pnProductTypeMain);

        getContentPane().add(tbpProduct, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
        MainHub.main.openProduct = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void cbxProductTypePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxProductTypePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        setCbxProductType();
    }//GEN-LAST:event_cbxProductTypePopupMenuWillBecomeVisible

    private void txtProductFundDecimalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductFundDecimalFocusGained
        // TODO add your handling code here:
        txtProductFundDecimal.selectAll();
    }//GEN-LAST:event_txtProductFundDecimalFocusGained

    private void txtProductPriceDecimalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductPriceDecimalFocusGained
        // TODO add your handling code here:
        txtProductPriceDecimal.selectAll();
    }//GEN-LAST:event_txtProductPriceDecimalFocusGained

    private void txtProductBarcodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductBarcodeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProductBarcodeKeyTyped

    private void txtProductBarcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductBarcodeFocusGained
        // TODO add your handling code here:
        txtProductBarcode.selectAll();
    }//GEN-LAST:event_txtProductBarcodeFocusGained

    private void txtProductNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductNameFocusGained
        // TODO add your handling code here:
        txtProductName.selectAll();
    }//GEN-LAST:event_txtProductNameFocusGained

    private void txtProductFundFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductFundFocusGained
        // TODO add your handling code here:
        txtProductFund.selectAll();
    }//GEN-LAST:event_txtProductFundFocusGained

    private void txtProductPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductPriceFocusGained
        // TODO add your handling code here:
        txtProductPrice.selectAll();
    }//GEN-LAST:event_txtProductPriceFocusGained

    private void btnProductShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnProductSearch.isShowing()) {
            pnProductSearch.setVisible(true);
            btnProductShowSearch.setText("ปิดการค้นหา");
        } else {
            pnProductSearch.setVisible(false);
            btnProductShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnProductShowSearchMouseClicked

    private void btnProductNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", productControl, btnProductNext, txtProductPage);
    }//GEN-LAST:event_btnProductNextMouseClicked

    private void btnProductPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", productControl, btnProductPre, txtProductPage);
    }//GEN-LAST:event_btnProductPreMouseClicked

    private void txtProductPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductPageFocusGained
        // TODO add your handling code here:
        txtProductPage.nextFocus();
    }//GEN-LAST:event_txtProductPageFocusGained

    private void btnProductInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductInsertMouseClicked
        // TODO add your handling code here:
        productControl.insertData();
    }//GEN-LAST:event_btnProductInsertMouseClicked

    private void btnProductEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductEditMouseClicked
        // TODO add your handling code here:
        productControl.updateData();
    }//GEN-LAST:event_btnProductEditMouseClicked

    private void btnProductClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(productControl);
    }//GEN-LAST:event_btnProductClearMouseClicked

    private void txtProductSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductSearchKeyReleased
        // TODO add your handling code here:
        productControl.showData(true);
    }//GEN-LAST:event_txtProductSearchKeyReleased

    private void cbxProductSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductSearchFromItemStateChanged
        // TODO add your handling code here:
        productControl.showData(true);
    }//GEN-LAST:event_cbxProductSearchFromItemStateChanged

    private void cbxProductSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductSortByItemStateChanged
        // TODO add your handling code here:
        productControl.showData(true);
    }//GEN-LAST:event_cbxProductSortByItemStateChanged

    private void cbxProductSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductSortFromItemStateChanged
        // TODO add your handling code here:
        productControl.showData(false);
    }//GEN-LAST:event_cbxProductSortFromItemStateChanged

    private void cbxProductPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductPerPageItemStateChanged
        // TODO add your handling code here:
        productControl.showData(false);
    }//GEN-LAST:event_cbxProductPerPageItemStateChanged

    private void tbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMouseClicked
        // TODO add your handling code here:
        Util.tableToText(productControl);
    }//GEN-LAST:event_tbProductMouseClicked

    private void btnProductClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(productControl);
    }//GEN-LAST:event_btnProductClearSearchMouseClicked

    private void txtProductSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductSearchFocusGained
        // TODO add your handling code here:
        txtProductSearch.selectAll();
    }//GEN-LAST:event_txtProductSearchFocusGained

    private void txtProductFundKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductFundKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProductFundKeyTyped

    private void txtProductFundDecimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductFundDecimalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProductFundDecimalKeyTyped

    private void txtProductPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductPriceKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProductPriceKeyTyped

    private void txtProductPriceDecimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductPriceDecimalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProductPriceDecimalKeyTyped

    private void txtProductFundKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductFundKeyReleased
        // TODO add your handling code here:
        if (!txtProductFund.getText().isEmpty()) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            txtProductFund.setText(nf.format(Long.parseLong(Util.fillterNumberToString(txtProductFund.getText()))));
        }
        
    }//GEN-LAST:event_txtProductFundKeyReleased

    private void txtProductPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductPriceKeyReleased
        // TODO add your handling code here:
        if (!txtProductPrice.getText().isEmpty()) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            txtProductPrice.setText(nf.format(Long.parseLong(Util.fillterNumberToString(txtProductPrice.getText()))));
        }
    }//GEN-LAST:event_txtProductPriceKeyReleased

    private void btnProduct_AddProductTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProduct_AddProductTypeMouseClicked
        // TODO add your handling code here:
        tbpProduct.setSelectedIndex(1);
    }//GEN-LAST:event_btnProduct_AddProductTypeMouseClicked

    private void txtProductTypeSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductTypeSearchFocusGained
        // TODO add your handling code here:
        txtProductTypeSearch.selectAll();
    }//GEN-LAST:event_txtProductTypeSearchFocusGained

    private void txtProductTypeSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductTypeSearchKeyReleased
        // TODO add your handling code here:
        productTypeControl.showData(true);
    }//GEN-LAST:event_txtProductTypeSearchKeyReleased

    private void cbxProductTypeSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductTypeSearchFromItemStateChanged
        // TODO add your handling code here:
        productTypeControl.showData(true);
    }//GEN-LAST:event_cbxProductTypeSearchFromItemStateChanged

    private void cbxProductTypeSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductTypeSortByItemStateChanged
        // TODO add your handling code here:
        productTypeControl.showData(true);
    }//GEN-LAST:event_cbxProductTypeSortByItemStateChanged

    private void cbxProductTypeSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductTypeSortFromItemStateChanged
        // TODO add your handling code here:
        productTypeControl.showData(false);
    }//GEN-LAST:event_cbxProductTypeSortFromItemStateChanged

    private void cbxProductTypePerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxProductTypePerPageItemStateChanged
        // TODO add your handling code here:
        productTypeControl.showData(false);
    }//GEN-LAST:event_cbxProductTypePerPageItemStateChanged

    private void btnProductTypeClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(productTypeControl);
    }//GEN-LAST:event_btnProductTypeClearSearchMouseClicked

    private void btnProductTypePreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypePreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", productTypeControl, btnProductTypePre, txtProductTypePage);
    }//GEN-LAST:event_btnProductTypePreMouseClicked

    private void txtProductTypePageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductTypePageFocusGained
        // TODO add your handling code here:
        txtProductTypePage.nextFocus();
    }//GEN-LAST:event_txtProductTypePageFocusGained

    private void btnProductTypeNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", productTypeControl, btnProductTypeNext, txtProductTypePage);
    }//GEN-LAST:event_btnProductTypeNextMouseClicked

    private void btnProductTypeShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnProductTypeSearch.isShowing()) {
            pnProductTypeSearch.setVisible(true);
            btnProductTypeShowSearch.setText("ปิดการค้นหา");
        } else {
            pnProductTypeSearch.setVisible(false);
            btnProductTypeShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnProductTypeShowSearchMouseClicked

    private void txtProductType_ProductTypeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductType_ProductTypeFocusGained
        // TODO add your handling code here:
        txtProductType_ProductType.selectAll();
    }//GEN-LAST:event_txtProductType_ProductTypeFocusGained

    private void btnProductTypeInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeInsertMouseClicked
        // TODO add your handling code here:
        productTypeControl.insertData();
    }//GEN-LAST:event_btnProductTypeInsertMouseClicked

    private void btnProductTypeUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeUpdateMouseClicked
        // TODO add your handling code here:
        productTypeControl.updateData();
    }//GEN-LAST:event_btnProductTypeUpdateMouseClicked

    private void btnProductTypeClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(productTypeControl);
    }//GEN-LAST:event_btnProductTypeClearMouseClicked

    private void tbProductTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductTypeMouseClicked
        // TODO add your handling code here:
        Util.tableToText(productTypeControl);
    }//GEN-LAST:event_tbProductTypeMouseClicked

    private void btnProductDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductDeleteMouseClicked
        // TODO add your handling code here:
        productControl.deleteData();
    }//GEN-LAST:event_btnProductDeleteMouseClicked

    private void btnProductTypeDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductTypeDeleteMouseClicked
        // TODO add your handling code here:
        productTypeControl.deleteData();
    }//GEN-LAST:event_btnProductTypeDeleteMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProductClear;
    private javax.swing.JButton btnProductClearSearch;
    private javax.swing.JButton btnProductDelete;
    private javax.swing.JButton btnProductEdit;
    private javax.swing.JButton btnProductInsert;
    private javax.swing.JButton btnProductNext;
    private javax.swing.JButton btnProductPre;
    private javax.swing.JButton btnProductShowSearch;
    private javax.swing.JButton btnProductTypeClear;
    private javax.swing.JButton btnProductTypeClearSearch;
    private javax.swing.JButton btnProductTypeDelete;
    private javax.swing.JButton btnProductTypeInsert;
    private javax.swing.JButton btnProductTypeNext;
    private javax.swing.JButton btnProductTypePre;
    private javax.swing.JButton btnProductTypeShowSearch;
    private javax.swing.JButton btnProductTypeUpdate;
    private javax.swing.JButton btnProduct_AddProductType;
    private javax.swing.JComboBox cbxProductExpire;
    private javax.swing.JComboBox cbxProductPerPage;
    private javax.swing.JComboBox cbxProductSearchFrom;
    private javax.swing.JComboBox cbxProductSortBy;
    private javax.swing.JComboBox cbxProductSortFrom;
    private javax.swing.JComboBox cbxProductType;
    private javax.swing.JComboBox cbxProductTypePerPage;
    private javax.swing.JComboBox cbxProductTypeSearchFrom;
    private javax.swing.JComboBox cbxProductTypeSortBy;
    private javax.swing.JComboBox cbxProductTypeSortFrom;
    private javax.swing.JLabel lbProductSearch;
    private javax.swing.JLabel lbProductTypeSearch;
    private javax.swing.JLabel lblProductBarcode;
    private javax.swing.JLabel lblProductExpire;
    private javax.swing.JLabel lblProductFund;
    private javax.swing.JLabel lblProductFundDot;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductNumber;
    private javax.swing.JLabel lblProductPerPage;
    private javax.swing.JLabel lblProductProfit;
    private javax.swing.JLabel lblProductProfitDot;
    private javax.swing.JLabel lblProductSearchForm;
    private javax.swing.JLabel lblProductSortBy;
    private javax.swing.JLabel lblProductSortForm;
    private javax.swing.JLabel lblProductType;
    private javax.swing.JLabel lblProductTypeNumber;
    private javax.swing.JLabel lblProductTypePerPage;
    private javax.swing.JLabel lblProductTypeSearchForm;
    private javax.swing.JLabel lblProductTypeSortBy;
    private javax.swing.JLabel lblProductTypeSortForm;
    private javax.swing.JLabel lblProductType_ProductType;
    private javax.swing.JPanel pnInputTbProduct;
    private javax.swing.JPanel pnInputTbProductType;
    private javax.swing.JPanel pnProductMain;
    private javax.swing.JPanel pnProductManage;
    private javax.swing.JPanel pnProductPagination;
    private javax.swing.JPanel pnProductRight;
    private javax.swing.JPanel pnProductSearch;
    private javax.swing.JPanel pnProductTypeMain;
    private javax.swing.JPanel pnProductTypeManage;
    private javax.swing.JPanel pnProductTypePagination;
    private javax.swing.JPanel pnProductTypeRight;
    private javax.swing.JPanel pnProductTypeSearch;
    private javax.swing.JScrollPane scpProductRight;
    private javax.swing.JScrollPane scpProductTypeRight;
    private javax.swing.JScrollPane scpTableProduct;
    private javax.swing.JScrollPane scpTableProductType;
    private javax.swing.JTable tbProduct;
    private javax.swing.JTable tbProductType;
    private javax.swing.JTabbedPane tbpProduct;
    private javax.swing.JTextField txtProductBarcode;
    private javax.swing.JTextField txtProductFund;
    private javax.swing.JTextField txtProductFundDecimal;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPage;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtProductPriceDecimal;
    private javax.swing.JTextField txtProductSearch;
    private javax.swing.JTextField txtProductTypePage;
    private javax.swing.JTextField txtProductTypeSearch;
    private javax.swing.JTextField txtProductType_ProductType;
    // End of variables declaration//GEN-END:variables

    private void setCbxProductType() {
        
        Vector<String> v = new Vector<>();
        
        String sql = "SELECT productType FROM tbproducttype";
        try {
            
            v.clear();
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            
            while(rs.next()) {
                
                v.add(rs.getString(1));
                
            }
            
            cbxProductType.setModel(new DefaultComboBoxModel(v));
            
        } catch (SQLException ex) {
            Logger.getLogger(FormProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
