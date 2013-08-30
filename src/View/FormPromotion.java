/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.PromotionControl;
import Control.PromotionViewControl;
import Model.Hub.MainHub;
import Model.Hub.PromotionHub;
import Utility.DBFactory;
import Utility.JTextFieldLimit;
import Utility.Message;
import Utility.SetCbxDateChange;
import Utility.Util;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author java-dev
 */
public class FormPromotion extends javax.swing.JInternalFrame {
    PromotionControl promotionControl;
    PromotionViewControl promotionViewControl;
    SetCbxDateChange setPromotionStartDate, setPromotionEndDate;
    
    private DefaultTableModel modelPromotion, modelPromotionView;
    
    public static Vector vectorPromotion, vectorPromotionInverse;
    public static Vector vectorPromotionView, vectorPromotionViewInverse;
    private boolean cbx2Change = false;

    /**
     * Creates new form FormPromotion
     */
    public FormPromotion() {
        initComponents();
        PromotionHub.setPromotion(this);
        setPromotionStartDate = new SetCbxDateChange(cbxPromotionStartYear, cbxPromotionStartMonth, cbxPromotionStartDate);
        setPromotionStartDate.setYearByDemand(10,10);
        setPromotionStartDate.setMonth();
        setPromotionStartDate.setDate();
        setPromotionEndDate = new SetCbxDateChange(cbxPromotionEndYear, cbxPromotionEndMonth, cbxPromotionEndDate);
        setPromotionEndDate.setYear();
        setPromotionEndDate.setMonthByCurrYear();
        setPromotionEndDate.setDateByCurrMonth();
        
        cbxPromotionStartYear.setSelectedItem(cbxPromotionEndYear.getSelectedItem());
        cbxPromotionStartMonth.setSelectedItem(cbxPromotionEndMonth.getSelectedItem());
        cbxPromotionStartDate.setSelectedItem(cbxPromotionEndDate.getSelectedItem());
        
        setDefaultDisable();
        setCbxProductName(cbxAmount1);
        setCbxProductName(cbxAmount2, cbxAmount1.getSelectedItem().toString());
        setCbxProductName(cbxAmount3, cbxAmount1.getSelectedItem().toString(), cbxAmount2.getSelectedItem().toString());
        setCbxDiscountResult();
        setCbxProductName(cbxPlusResult);
        
        txtAmount1.setDocument(new JTextFieldLimit(1));
        txtAmount2.setDocument(new JTextFieldLimit(1));
        txtAmount3.setDocument(new JTextFieldLimit(1));
        txtAmount4.setDocument(new JTextFieldLimit(1));
        txtDiscountResultDecimal.setDocument(new JTextFieldLimit(2));
        txtDiscountResultDecimal.setText("00");
        
        promotionControl = new PromotionControl();
        promotionViewControl = new PromotionViewControl();
        
        vectorPromotion = new Vector();
        vectorPromotionInverse = new Vector();
        
        tbPromotion.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        modelPromotion = (DefaultTableModel) tbPromotion.getModel();
        tbPromotionView.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        modelPromotionView = (DefaultTableModel) tbPromotionView.getModel();
        
        promotionControl.showData(true);
        promotionViewControl.showData(true);
    }
    
    public void setDefaultDisable() {
        
        cbxAmount2.setEnabled(false);
        txtAmount2.setEnabled(false);
        cbxAmount3.setEnabled(false);
        txtAmount3.setEnabled(false);
        cbxPlusResult.setEnabled(false);
        txtAmount4.setEnabled(false);
        
    }
    
    private void setCbxProductName(JComboBox cbx, String...item) {
        
        Vector<String> v = new Vector();
        
        String sql = "SELECT DISTINCT(productName) FROM tbproduct";
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            
            while(rs.next()) {            
                v.add(rs.getString(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FormPromotion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (item.length > 0) {
            for (int i = 0; i < item.length; i++) {
                v.removeElement(item[i]);
            }
        }
        cbx.removeAllItems();
        cbx.setModel(new DefaultComboBoxModel(v));
    }
    
    private void setCbxDiscountResult() {
        
        int numCon = cbxNumOfConditionProduct.getSelectedIndex()+1;
        
        cbxDiscountResult.removeAllItems();
        
        cbxDiscountResult.addItem(cbxAmount1.getSelectedItem());
        
        if (numCon > 1) {
            cbxDiscountResult.addItem(cbxAmount2.getSelectedItem());
        }
     
        if (numCon > 2) {
            cbxDiscountResult.addItem(cbxAmount3.getSelectedItem());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getter Promotion">
    public DefaultTableModel getModelPromotion() {
        return modelPromotion;
    }
    
    public JTable getTbPromotion() {
        return tbPromotion;
    }
    
    public JTextField getTxtAmount1() {
        return txtAmount1;
    }
    
    public JTextField getTxtAmount2() {
        return txtAmount2;
    }
    
    public JTextField getTxtAmount3() {
        return txtAmount3;
    }
    
    public JTextField getTxtAmount4() {
        return txtAmount4;
    }
    
    public JTextField getTxtDiscountResult() {
        return txtDiscountResult;
    }
    
    public JTextField getTxtDiscountResultDecimal() {
        return txtDiscountResultDecimal;
    }
    
    public JTextField getTxtPromotionDetail() {
        return txtPromotionDetail;
    }
    
    public JTextField getTxtPromotionName() {
        return txtPromotionName;
    }
    
    public JTextField getTxtPromotionPage() {
        return txtPromotionPage;
    }
    
    public JTextField getTxtPromotionSearch() {
        return txtPromotionSearch;
    }
    
    public JComboBox getCbxAmount1() {
        return cbxAmount1;
    }
    
    public JComboBox getCbxAmount2() {
        return cbxAmount2;
    }
    
    public JComboBox getCbxAmount3() {
        return cbxAmount3;
    }
    
    public JComboBox getCbxNumOfConditionProduct() {
        return cbxNumOfConditionProduct;
    }

    public JComboBox getCbxDiscountResult() {
        return cbxDiscountResult;
    }
    
    public JComboBox getCbxPlusResult() {
        return cbxPlusResult;
    }
    
    public JComboBox getCbxPromotionEndDate() {
        return cbxPromotionEndDate;
    }
    
    public JComboBox getCbxPromotionEndMonth() {
        return cbxPromotionEndMonth;
    }
    
    public JComboBox getCbxPromotionEndYear() {
        return cbxPromotionEndYear;
    }
    
    public JComboBox getCbxPromotionPerPage() {
        return cbxPromotionPerPage;
    }
    
    public JComboBox getCbxPromotionSearchFrom() {
        return cbxPromotionSearchFrom;
    }
    
    public JComboBox getCbxPromotionSortBy() {
        return cbxPromotionSortBy;
    }
    
    public JComboBox getCbxPromotionSortFrom() {
        return cbxPromotionSortFrom;
    }
    
    public JComboBox getCbxPromotionStartDate() {
        return cbxPromotionStartDate;
    }
    
    public JComboBox getCbxPromotionStartMonth() {
        return cbxPromotionStartMonth;
    }
    
    public JComboBox getCbxPromotionStartYear() {
        return cbxPromotionStartYear;
    }
    
    public JComboBox getCbxPromotionType() {
        return cbxPromotionType;
    }
    
    public JButton getBtnPromotionNext() {
        return btnPromotionNext;
    }
    
    public JButton getBtnPromotionPre() {
        return btnPromotionPre;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter PromotionView">
    
    public DefaultTableModel getModelPromotionView() {
        return modelPromotionView;
    }

    public JTable getTbPromotionView() {
        return tbPromotionView;
    }
    
    public JButton getBtnPromotionViewNext() {
        return btnPromotionViewNext;
    }
    
    public JButton getBtnPromotionViewPre() {
        return btnPromotionViewPre;
    }
    
    public JComboBox getCbxPromotionViewPerPage() {
        return cbxPromotionViewPerPage;
    }
    
    public JComboBox getCbxPromotionViewSearchFrom() {
        return cbxPromotionViewSearchFrom;
    }
    
    public JComboBox getCbxPromotionViewSortBy() {
        return cbxPromotionViewSortBy;
    }
    
    public JComboBox getCbxPromotionViewSortFrom() {
        return cbxPromotionViewSortFrom;
    }
    
    public JTextField getTxtPromotionViewPage() {
        return txtPromotionViewPage;
    }
    
    public JTextField getTxtPromotionViewSearch() {
        return txtPromotionViewSearch;
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

        tbpPromotionMain = new javax.swing.JTabbedPane();
        pnPromotion = new javax.swing.JPanel();
        pbInputTbPromotion = new javax.swing.JPanel();
        scpTablePromotion = new javax.swing.JScrollPane();
        tbPromotion = new javax.swing.JTable();
        pnPromotionPagination = new javax.swing.JPanel();
        btnPromotionNext = new javax.swing.JButton();
        btnPromotionPre = new javax.swing.JButton();
        txtPromotionPage = new javax.swing.JTextField();
        btnPromotionShowSearch = new javax.swing.JButton();
        scpPromotionRight = new javax.swing.JScrollPane();
        pnPromotionRight = new javax.swing.JPanel();
        pnPromotionManage = new javax.swing.JPanel();
        lblPromotionName = new javax.swing.JLabel();
        cbxPromotionStartYear = new javax.swing.JComboBox();
        lblPromotionType = new javax.swing.JLabel();
        lblPromotionStartDate = new javax.swing.JLabel();
        btnPromotionInsert = new javax.swing.JButton();
        btnPromotionUpdate = new javax.swing.JButton();
        btnPromotionClear = new javax.swing.JButton();
        cbxPromotionStartMonth = new javax.swing.JComboBox();
        cbxPromotionStartDate = new javax.swing.JComboBox();
        txtPromotionName = new javax.swing.JTextField();
        lblPromotionDetail = new javax.swing.JLabel();
        txtPromotionDetail = new javax.swing.JTextField();
        cbxPromotionType = new javax.swing.JComboBox();
        lblPromotionEndDate = new javax.swing.JLabel();
        cbxPromotionEndYear = new javax.swing.JComboBox();
        cbxPromotionEndMonth = new javax.swing.JComboBox();
        cbxPromotionEndDate = new javax.swing.JComboBox();
        lblNumOfConditionProduct = new javax.swing.JLabel();
        cbxNumOfConditionProduct = new javax.swing.JComboBox();
        lbllNumOfConditionProductList = new javax.swing.JLabel();
        lblConditionProduct = new javax.swing.JLabel();
        cbxAmount1 = new javax.swing.JComboBox();
        cbxAmount2 = new javax.swing.JComboBox();
        cbxAmount3 = new javax.swing.JComboBox();
        lblAmount1 = new javax.swing.JLabel();
        lblAmount2 = new javax.swing.JLabel();
        lblAmount3 = new javax.swing.JLabel();
        txtAmount1 = new javax.swing.JTextField();
        txtAmount2 = new javax.swing.JTextField();
        txtAmount3 = new javax.swing.JTextField();
        lblDiscountResult = new javax.swing.JLabel();
        txtDiscountResult = new javax.swing.JTextField();
        lblPlusResult = new javax.swing.JLabel();
        cbxPlusResult = new javax.swing.JComboBox();
        lblAmount4 = new javax.swing.JLabel();
        txtAmount4 = new javax.swing.JTextField();
        lblDiscountResultDot = new javax.swing.JLabel();
        txtDiscountResultDecimal = new javax.swing.JTextField();
        cbxDiscountResult = new javax.swing.JComboBox();
        btnPromotionDelete = new javax.swing.JButton();
        pnPromotionSearch = new javax.swing.JPanel();
        lbPromotionSearch = new javax.swing.JLabel();
        lblPromotionSearchForm = new javax.swing.JLabel();
        lblPromotionSortBy = new javax.swing.JLabel();
        lblPromotionSortFrom = new javax.swing.JLabel();
        lblPromotionNumber = new javax.swing.JLabel();
        lblPromotionPerPage = new javax.swing.JLabel();
        txtPromotionSearch = new javax.swing.JTextField();
        cbxPromotionSearchFrom = new javax.swing.JComboBox();
        cbxPromotionSortBy = new javax.swing.JComboBox();
        cbxPromotionSortFrom = new javax.swing.JComboBox();
        cbxPromotionPerPage = new javax.swing.JComboBox();
        btnPromotionClearSearch = new javax.swing.JButton();
        pnPromotionView = new javax.swing.JPanel();
        pbInputTbPromotionView = new javax.swing.JPanel();
        scpTablePromotionView = new javax.swing.JScrollPane();
        tbPromotionView = new javax.swing.JTable();
        pnPromotionViewPagination = new javax.swing.JPanel();
        btnPromotionViewNext = new javax.swing.JButton();
        btnPromotionViewPre = new javax.swing.JButton();
        txtPromotionViewPage = new javax.swing.JTextField();
        btnPromotionViewShowSearch = new javax.swing.JButton();
        scpPromotionViewRight = new javax.swing.JScrollPane();
        pnPromotionViewRight = new javax.swing.JPanel();
        pnPromotionViewSearch = new javax.swing.JPanel();
        lbPromotionViewSearch = new javax.swing.JLabel();
        lblPromotionViewSearchFrom = new javax.swing.JLabel();
        lblPromotionViewSortBy = new javax.swing.JLabel();
        lblPromotionViewSortForm = new javax.swing.JLabel();
        lblPromotionViewNumber = new javax.swing.JLabel();
        lblPromotionViewPerPage = new javax.swing.JLabel();
        txtPromotionViewSearch = new javax.swing.JTextField();
        cbxPromotionViewSearchFrom = new javax.swing.JComboBox();
        cbxPromotionViewSortBy = new javax.swing.JComboBox();
        cbxPromotionViewSortFrom = new javax.swing.JComboBox();
        cbxPromotionViewPerPage = new javax.swing.JComboBox();
        btnPromotionViewClearSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("จัดการโปรโมชัน");
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        tbpPromotionMain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pnPromotion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pnPromotion.setLayout(new java.awt.BorderLayout());

        pbInputTbPromotion.setLayout(new java.awt.BorderLayout());

        tbPromotion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPromotion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขที่โปรโมชัน", "ชื่อโปรโมชัน", "รายละเอียด", "ประเภท", "เงื่อนไขโปรโมชัน", "ส่วนลด", "ของแถม", "วันเริ่มต้น", "วันสิ้นสุด"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPromotion.setRowHeight(25);
        tbPromotion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPromotionMouseClicked(evt);
            }
        });
        scpTablePromotion.setViewportView(tbPromotion);
        tbPromotion.getColumnModel().getColumn(0).setPreferredWidth(25);
        tbPromotion.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbPromotion.getColumnModel().getColumn(2).setPreferredWidth(200);
        tbPromotion.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbPromotion.getColumnModel().getColumn(4).setPreferredWidth(180);
        tbPromotion.getColumnModel().getColumn(5).setPreferredWidth(55);
        tbPromotion.getColumnModel().getColumn(6).setPreferredWidth(100);
        tbPromotion.getColumnModel().getColumn(7).setPreferredWidth(70);
        tbPromotion.getColumnModel().getColumn(8).setPreferredWidth(70);

        pbInputTbPromotion.add(scpTablePromotion, java.awt.BorderLayout.CENTER);

        pnPromotionPagination.setPreferredSize(new java.awt.Dimension(779, 50));

        btnPromotionNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionNext.setText("ถัดไป >>");
        btnPromotionNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionNextMouseClicked(evt);
            }
        });

        btnPromotionPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionPre.setText("<< ก่อนหน้า");
        btnPromotionPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnPromotionPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionPreMouseClicked(evt);
            }
        });

        txtPromotionPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPromotionPage.setText("1");
        txtPromotionPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtPromotionPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionPageFocusGained(evt);
            }
        });

        btnPromotionShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionShowSearch.setText("ปิดการค้นหา");
        btnPromotionShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnPromotionShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPromotionPaginationLayout = new javax.swing.GroupLayout(pnPromotionPagination);
        pnPromotionPagination.setLayout(pnPromotionPaginationLayout);
        pnPromotionPaginationLayout.setHorizontalGroup(
            pnPromotionPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPromotionPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPromotionPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromotionNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                .addComponent(btnPromotionShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnPromotionPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPromotionNext, btnPromotionPre});

        pnPromotionPaginationLayout.setVerticalGroup(
            pnPromotionPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPromotionPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPromotionPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromotionNext)
                    .addComponent(txtPromotionPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromotionShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnPromotionPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPromotionNext, btnPromotionPre, txtPromotionPage});

        pbInputTbPromotion.add(pnPromotionPagination, java.awt.BorderLayout.PAGE_END);

        pnPromotion.add(pbInputTbPromotion, java.awt.BorderLayout.CENTER);

        scpPromotionRight.setBorder(null);
        scpPromotionRight.setPreferredSize(new java.awt.Dimension(425, 450));

        pnPromotionRight.setPreferredSize(new java.awt.Dimension(410, 730));

        pnPromotionManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการโปรโมชัน ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnPromotionManage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pnPromotionManage.setPreferredSize(new java.awt.Dimension(410, 213));

        lblPromotionName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionName.setText("ชื่อโปรโมชัน : ");

        cbxPromotionStartYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionStartYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionStartYearItemStateChanged(evt);
            }
        });

        lblPromotionType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionType.setText("ประเภท : ");

        lblPromotionStartDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionStartDate.setText("วันเริ่มต้น : ");

        btnPromotionInsert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionInsert.setText("เพิ่ม");
        btnPromotionInsert.setPreferredSize(new java.awt.Dimension(51, 25));
        btnPromotionInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionInsertMouseClicked(evt);
            }
        });

        btnPromotionUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionUpdate.setText("แก้ไข");
        btnPromotionUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionUpdateMouseClicked(evt);
            }
        });

        btnPromotionClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionClear.setText("ล้าง");
        btnPromotionClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionClearMouseClicked(evt);
            }
        });

        cbxPromotionStartMonth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionStartMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionStartMonthItemStateChanged(evt);
            }
        });

        cbxPromotionStartDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtPromotionName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionName.setPreferredSize(new java.awt.Dimension(6, 25));
        txtPromotionName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionNameFocusGained(evt);
            }
        });

        lblPromotionDetail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionDetail.setText("รายละเอียด : ");

        txtPromotionDetail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionDetail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionDetailFocusGained(evt);
            }
        });

        cbxPromotionType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ส่วนลด", "ของแถม" }));
        cbxPromotionType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionTypeItemStateChanged(evt);
            }
        });

        lblPromotionEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionEndDate.setText("วันสิ้นสุด : ");

        cbxPromotionEndYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionEndYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionEndYearItemStateChanged(evt);
            }
        });

        cbxPromotionEndMonth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionEndMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionEndMonthItemStateChanged(evt);
            }
        });

        cbxPromotionEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblNumOfConditionProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNumOfConditionProduct.setText("จำนวนเงื่อนไข : ");

        cbxNumOfConditionProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxNumOfConditionProduct.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3" }));
        cbxNumOfConditionProduct.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNumOfConditionProductItemStateChanged(evt);
            }
        });

        lbllNumOfConditionProductList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbllNumOfConditionProductList.setText("รายการ");

        lblConditionProduct.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblConditionProduct.setText("สินค้าเงื่อนไข : ");

        cbxAmount1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxAmount1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAmount1ItemStateChanged(evt);
            }
        });

        cbxAmount2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxAmount2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxAmount2MouseClicked(evt);
            }
        });
        cbxAmount2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAmount2ItemStateChanged(evt);
            }
        });

        cbxAmount3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxAmount3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxAmount3ItemStateChanged(evt);
            }
        });

        lblAmount1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAmount1.setText("จำนวน : ");

        lblAmount2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAmount2.setText("จำนวน : ");

        lblAmount3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAmount3.setText("จำนวน : ");

        txtAmount1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAmount1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAmount1FocusGained(evt);
            }
        });
        txtAmount1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmount1KeyTyped(evt);
            }
        });

        txtAmount2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAmount2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAmount2FocusGained(evt);
            }
        });
        txtAmount2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmount2KeyTyped(evt);
            }
        });

        txtAmount3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAmount3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAmount3FocusGained(evt);
            }
        });
        txtAmount3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmount3KeyTyped(evt);
            }
        });

        lblDiscountResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiscountResult.setText("ส่วนลด : ");

        txtDiscountResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDiscountResult.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountResultFocusGained(evt);
            }
        });
        txtDiscountResult.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountResultKeyTyped(evt);
            }
        });

        lblPlusResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPlusResult.setText("สินค้าแถม : ");

        cbxPlusResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblAmount4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAmount4.setText("จำนวน : ");

        txtAmount4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAmount4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAmount4FocusGained(evt);
            }
        });
        txtAmount4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmount4KeyTyped(evt);
            }
        });

        lblDiscountResultDot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDiscountResultDot.setText(".");

        txtDiscountResultDecimal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDiscountResultDecimal.setText("00");
        txtDiscountResultDecimal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountResultDecimalFocusGained(evt);
            }
        });
        txtDiscountResultDecimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDiscountResultDecimalKeyTyped(evt);
            }
        });

        cbxDiscountResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnPromotionDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionDelete.setText("ลบ");
        btnPromotionDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionDeleteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPromotionManageLayout = new javax.swing.GroupLayout(pnPromotionManage);
        pnPromotionManage.setLayout(pnPromotionManageLayout);
        pnPromotionManageLayout.setHorizontalGroup(
            pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPlusResult)
                    .addComponent(lblDiscountResult)
                    .addComponent(lblConditionProduct)
                    .addComponent(lblNumOfConditionProduct)
                    .addComponent(lblPromotionType)
                    .addComponent(lblPromotionStartDate)
                    .addComponent(lblPromotionEndDate)
                    .addComponent(lblPromotionName)
                    .addComponent(lblPromotionDetail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPromotionDetail)
                    .addGroup(pnPromotionManageLayout.createSequentialGroup()
                        .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbxAmount3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxDiscountResult, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAmount3)
                                    .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                        .addComponent(txtDiscountResult, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(lblDiscountResultDot)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDiscountResultDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                        .addComponent(cbxAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblAmount1))
                                    .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                        .addComponent(cbxAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblAmount2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtPromotionName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addComponent(cbxPlusResult, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAmount4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAmount4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addComponent(cbxPromotionEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxPromotionEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxPromotionEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addComponent(cbxNumOfConditionProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbllNumOfConditionProductList))
                            .addComponent(cbxPromotionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                                .addComponent(cbxPromotionStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxPromotionStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxPromotionStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 53, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnPromotionManageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPromotionInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromotionUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromotionDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromotionClear)
                .addGap(52, 52, 52))
        );

        pnPromotionManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbxAmount1, cbxAmount2, cbxAmount3});

        pnPromotionManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPromotionClear, btnPromotionDelete, btnPromotionInsert, btnPromotionUpdate});

        pnPromotionManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbxDiscountResult, cbxPlusResult});

        pnPromotionManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAmount1, txtAmount2, txtAmount3, txtAmount4});

        pnPromotionManageLayout.setVerticalGroup(
            pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionName)
                    .addComponent(txtPromotionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionDetail)
                    .addComponent(txtPromotionDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionType)
                    .addComponent(cbxPromotionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPromotionStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPromotionStartDate)
                    .addComponent(cbxPromotionStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxPromotionStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPromotionEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPromotionEndDate)
                    .addComponent(cbxPromotionEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxPromotionEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumOfConditionProduct)
                    .addComponent(cbxNumOfConditionProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbllNumOfConditionProductList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConditionProduct)
                    .addComponent(cbxAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount1)
                    .addComponent(txtAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount2)
                    .addComponent(txtAmount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount3)
                    .addComponent(txtAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiscountResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiscountResultDot)
                    .addComponent(txtDiscountResultDecimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiscountResult)
                    .addComponent(cbxDiscountResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPlusResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAmount4)
                    .addComponent(txtAmount4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlusResult))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPromotionManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPromotionInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromotionUpdate)
                    .addComponent(btnPromotionClear)
                    .addComponent(btnPromotionDelete))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pnPromotionManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPromotionClear, btnPromotionDelete, btnPromotionInsert, btnPromotionUpdate, cbxAmount1, cbxAmount2, cbxAmount3, cbxDiscountResult, cbxNumOfConditionProduct, cbxPlusResult, cbxPromotionEndDate, cbxPromotionEndMonth, cbxPromotionEndYear, cbxPromotionStartDate, cbxPromotionStartMonth, cbxPromotionStartYear, cbxPromotionType, txtAmount1, txtAmount2, txtAmount3, txtAmount4, txtDiscountResult, txtDiscountResultDecimal, txtPromotionDetail, txtPromotionName});

        pnPromotionSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnPromotionSearch.setPreferredSize(new java.awt.Dimension(410, 215));

        lbPromotionSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbPromotionSearch.setText("ค้นหา : ");

        lblPromotionSearchForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionSearchForm.setText("ค้นหาจาก : ");

        lblPromotionSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionSortBy.setText("เรียงโดย : ");

        lblPromotionSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionSortFrom.setText("เรียงจาก : ");

        lblPromotionNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionNumber.setText("จำนวน : ");

        lblPromotionPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionPerPage.setText("รายการ / หน้า");

        txtPromotionSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtPromotionSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionSearchFocusGained(evt);
            }
        });
        txtPromotionSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPromotionSearchKeyReleased(evt);
            }
        });

        cbxPromotionSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อโปรโมชัน", "วันเริ่มต้น(เริ่มจาก)", "วันสิ้นสุด(เริ่มจาก)" }));
        cbxPromotionSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionSearchFromItemStateChanged(evt);
            }
        });

        cbxPromotionSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "เลขที่โปรโมชัน", "ชื่อโปรโมชัน", "ประเภท", "วันเริ่มต้น", "วันสิ้นสุด" }));
        cbxPromotionSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionSortByItemStateChanged(evt);
            }
        });

        cbxPromotionSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxPromotionSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionSortFromItemStateChanged(evt);
            }
        });

        cbxPromotionPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxPromotionPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionPerPageItemStateChanged(evt);
            }
        });

        btnPromotionClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionClearSearch.setText("ล้าง");
        btnPromotionClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnPromotionClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPromotionSearchLayout = new javax.swing.GroupLayout(pnPromotionSearch);
        pnPromotionSearch.setLayout(pnPromotionSearchLayout);
        pnPromotionSearchLayout.setHorizontalGroup(
            pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPromotionSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionSearchForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionSortFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPromotionClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnPromotionSearchLayout.createSequentialGroup()
                        .addComponent(cbxPromotionPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPromotionPerPage))
                    .addComponent(cbxPromotionSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPromotionSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxPromotionSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)
                        .addComponent(cbxPromotionSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnPromotionSearchLayout.setVerticalGroup(
            pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPromotionSearch)
                    .addComponent(txtPromotionSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionSearchForm)
                    .addComponent(cbxPromotionSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionSortBy)
                    .addComponent(cbxPromotionSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionSortFrom)
                    .addComponent(cbxPromotionSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionNumber)
                    .addComponent(lblPromotionPerPage)
                    .addComponent(cbxPromotionPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPromotionClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnPromotionSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPromotionClearSearch, cbxPromotionPerPage, cbxPromotionSearchFrom, cbxPromotionSortBy, cbxPromotionSortFrom, txtPromotionSearch});

        javax.swing.GroupLayout pnPromotionRightLayout = new javax.swing.GroupLayout(pnPromotionRight);
        pnPromotionRight.setLayout(pnPromotionRightLayout);
        pnPromotionRightLayout.setHorizontalGroup(
            pnPromotionRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionRightLayout.createSequentialGroup()
                .addGroup(pnPromotionRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnPromotionSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnPromotionManage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnPromotionRightLayout.setVerticalGroup(
            pnPromotionRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionRightLayout.createSequentialGroup()
                .addComponent(pnPromotionManage, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnPromotionSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        scpPromotionRight.setViewportView(pnPromotionRight);

        pnPromotion.add(scpPromotionRight, java.awt.BorderLayout.LINE_END);

        tbpPromotionMain.addTab("จัดการโปรโมชัน", pnPromotion);

        pnPromotionView.setLayout(new java.awt.BorderLayout());

        pbInputTbPromotionView.setLayout(new java.awt.BorderLayout());

        tbPromotionView.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPromotionView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขที่โปรโมชัน", "ชื่อโปรโมชัน", "รายละเอียด", "ประเภท", "เงื่อนไขโปรโมชัน", "ส่วนลด", "ของแถม", "วันเริ่มต้น", "วันสิ้นสุด"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPromotionView.setRowHeight(25);
        scpTablePromotionView.setViewportView(tbPromotionView);
        tbPromotionView.getColumnModel().getColumn(0).setPreferredWidth(25);
        tbPromotionView.getColumnModel().getColumn(1).setPreferredWidth(100);
        tbPromotionView.getColumnModel().getColumn(2).setPreferredWidth(200);
        tbPromotionView.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbPromotionView.getColumnModel().getColumn(4).setPreferredWidth(180);
        tbPromotionView.getColumnModel().getColumn(5).setPreferredWidth(55);
        tbPromotionView.getColumnModel().getColumn(6).setPreferredWidth(100);
        tbPromotionView.getColumnModel().getColumn(7).setPreferredWidth(70);
        tbPromotionView.getColumnModel().getColumn(8).setPreferredWidth(70);

        pbInputTbPromotionView.add(scpTablePromotionView, java.awt.BorderLayout.CENTER);

        pnPromotionViewPagination.setPreferredSize(new java.awt.Dimension(779, 50));

        btnPromotionViewNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionViewNext.setText("ถัดไป >>");
        btnPromotionViewNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionViewNextMouseClicked(evt);
            }
        });

        btnPromotionViewPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionViewPre.setText("<< ก่อนหน้า");
        btnPromotionViewPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnPromotionViewPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionViewPreMouseClicked(evt);
            }
        });

        txtPromotionViewPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionViewPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPromotionViewPage.setText("1");
        txtPromotionViewPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtPromotionViewPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionViewPageFocusGained(evt);
            }
        });

        btnPromotionViewShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionViewShowSearch.setText("ปิดการค้นหา");
        btnPromotionViewShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnPromotionViewShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionViewShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPromotionViewPaginationLayout = new javax.swing.GroupLayout(pnPromotionViewPagination);
        pnPromotionViewPagination.setLayout(pnPromotionViewPaginationLayout);
        pnPromotionViewPaginationLayout.setHorizontalGroup(
            pnPromotionViewPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPromotionViewPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPromotionViewPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromotionViewNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 430, Short.MAX_VALUE)
                .addComponent(btnPromotionViewShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnPromotionViewPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPromotionViewNext, btnPromotionViewPre});

        pnPromotionViewPaginationLayout.setVerticalGroup(
            pnPromotionViewPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPromotionViewPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPromotionViewPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromotionViewNext)
                    .addComponent(txtPromotionViewPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromotionViewShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnPromotionViewPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPromotionViewNext, btnPromotionViewPre, txtPromotionViewPage});

        pbInputTbPromotionView.add(pnPromotionViewPagination, java.awt.BorderLayout.PAGE_END);

        pnPromotionView.add(pbInputTbPromotionView, java.awt.BorderLayout.CENTER);

        scpPromotionViewRight.setBorder(null);
        scpPromotionViewRight.setPreferredSize(new java.awt.Dimension(315, 450));

        pnPromotionViewRight.setPreferredSize(new java.awt.Dimension(300, 450));

        pnPromotionViewSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnPromotionViewSearch.setPreferredSize(new java.awt.Dimension(300, 215));

        lbPromotionViewSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbPromotionViewSearch.setText("ค้นหา : ");

        lblPromotionViewSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionViewSearchFrom.setText("ค้นหาจาก : ");

        lblPromotionViewSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionViewSortBy.setText("เรียงโดย : ");

        lblPromotionViewSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionViewSortForm.setText("เรียงจาก : ");

        lblPromotionViewNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionViewNumber.setText("จำนวน : ");

        lblPromotionViewPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPromotionViewPerPage.setText("รายการ / หน้า");

        txtPromotionViewSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPromotionViewSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtPromotionViewSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPromotionViewSearchFocusGained(evt);
            }
        });
        txtPromotionViewSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPromotionViewSearchKeyReleased(evt);
            }
        });

        cbxPromotionViewSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionViewSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อโปรโมชัน", "วันเริ่มต้น(เริ่มจาก)", "วันสิ้นสุด(เริ่มจาก)" }));
        cbxPromotionViewSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionViewSearchFromItemStateChanged(evt);
            }
        });

        cbxPromotionViewSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionViewSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อโปรโมชัน", "ประเภท", "วันเริ่มต้น", "วันสิ้นสุด" }));
        cbxPromotionViewSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionViewSortByItemStateChanged(evt);
            }
        });

        cbxPromotionViewSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionViewSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxPromotionViewSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionViewSortFromItemStateChanged(evt);
            }
        });

        cbxPromotionViewPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxPromotionViewPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxPromotionViewPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPromotionViewPerPageItemStateChanged(evt);
            }
        });

        btnPromotionViewClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPromotionViewClearSearch.setText("ล้าง");
        btnPromotionViewClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnPromotionViewClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPromotionViewClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPromotionViewSearchLayout = new javax.swing.GroupLayout(pnPromotionViewSearch);
        pnPromotionViewSearch.setLayout(pnPromotionViewSearchLayout);
        pnPromotionViewSearchLayout.setHorizontalGroup(
            pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPromotionViewSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionViewSearchFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionViewSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionViewSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPromotionViewNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPromotionViewClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnPromotionViewSearchLayout.createSequentialGroup()
                        .addComponent(cbxPromotionViewPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPromotionViewPerPage))
                    .addComponent(cbxPromotionViewSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPromotionViewSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxPromotionViewSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxPromotionViewSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        pnPromotionViewSearchLayout.setVerticalGroup(
            pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPromotionViewSearch)
                    .addComponent(txtPromotionViewSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionViewSearchFrom)
                    .addComponent(cbxPromotionViewSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionViewSortBy)
                    .addComponent(cbxPromotionViewSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionViewSortForm)
                    .addComponent(cbxPromotionViewSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnPromotionViewSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPromotionViewNumber)
                    .addComponent(lblPromotionViewPerPage)
                    .addComponent(cbxPromotionViewPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPromotionViewClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnPromotionViewSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPromotionViewClearSearch, cbxPromotionViewPerPage, cbxPromotionViewSearchFrom, cbxPromotionViewSortBy, cbxPromotionViewSortFrom, txtPromotionViewSearch});

        javax.swing.GroupLayout pnPromotionViewRightLayout = new javax.swing.GroupLayout(pnPromotionViewRight);
        pnPromotionViewRight.setLayout(pnPromotionViewRightLayout);
        pnPromotionViewRightLayout.setHorizontalGroup(
            pnPromotionViewRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewRightLayout.createSequentialGroup()
                .addComponent(pnPromotionViewSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        pnPromotionViewRightLayout.setVerticalGroup(
            pnPromotionViewRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPromotionViewRightLayout.createSequentialGroup()
                .addComponent(pnPromotionViewSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 252, Short.MAX_VALUE))
        );

        scpPromotionViewRight.setViewportView(pnPromotionViewRight);

        pnPromotionView.add(scpPromotionViewRight, java.awt.BorderLayout.LINE_END);

        tbpPromotionMain.addTab("โปรโมชันทั้งหมด", pnPromotionView);

        getContentPane().add(tbpPromotionMain, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPromotionNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", promotionControl, btnPromotionNext, txtPromotionPage);
    }//GEN-LAST:event_btnPromotionNextMouseClicked

    private void btnPromotionPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", promotionControl, btnPromotionPre, txtPromotionPage);
    }//GEN-LAST:event_btnPromotionPreMouseClicked

    private void txtPromotionPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionPageFocusGained
        // TODO add your handling code here:
        txtPromotionPage.nextFocus();
    }//GEN-LAST:event_txtPromotionPageFocusGained

    private void cbxPromotionStartYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionStartYearItemStateChanged
        // TODO add your handling code here:
        setPromotionStartDate.setMonth();
        setPromotionStartDate.setDate();
    }//GEN-LAST:event_cbxPromotionStartYearItemStateChanged

    private void btnPromotionInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionInsertMouseClicked
        // TODO add your handling code here:
        promotionControl.insertData();
    }//GEN-LAST:event_btnPromotionInsertMouseClicked

    private void btnPromotionClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(promotionControl);
    }//GEN-LAST:event_btnPromotionClearMouseClicked

    private void cbxPromotionStartMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionStartMonthItemStateChanged
        // TODO add your handling code here:
        setPromotionStartDate.setDate();
    }//GEN-LAST:event_cbxPromotionStartMonthItemStateChanged

    private void txtPromotionSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionSearchFocusGained
        // TODO add your handling code here:
        txtPromotionSearch.selectAll();
    }//GEN-LAST:event_txtPromotionSearchFocusGained

    private void txtPromotionSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPromotionSearchKeyReleased
        // TODO add your handling code here:
        promotionControl.showData(true);
    }//GEN-LAST:event_txtPromotionSearchKeyReleased

    private void cbxPromotionSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionSearchFromItemStateChanged
        // TODO add your handling code here:
        promotionControl.showData(true);
    }//GEN-LAST:event_cbxPromotionSearchFromItemStateChanged

    private void cbxPromotionSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionSortByItemStateChanged
        // TODO add your handling code here:
        promotionControl.showData(true);
    }//GEN-LAST:event_cbxPromotionSortByItemStateChanged

    private void cbxPromotionSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionSortFromItemStateChanged
        // TODO add your handling code here:
        promotionControl.showData(false);
    }//GEN-LAST:event_cbxPromotionSortFromItemStateChanged

    private void cbxPromotionPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionPerPageItemStateChanged
        // TODO add your handling code here:
        promotionControl.showData(false);
    }//GEN-LAST:event_cbxPromotionPerPageItemStateChanged

    private void btnPromotionClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(promotionControl);
    }//GEN-LAST:event_btnPromotionClearSearchMouseClicked

    private void btnPromotionShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnPromotionSearch.isShowing()) {
            pnPromotionSearch.setVisible(true);
            btnPromotionShowSearch.setText("ปิดการค้นหา");
        } else {
            pnPromotionSearch.setVisible(false);
            btnPromotionShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnPromotionShowSearchMouseClicked

    private void cbxPromotionEndYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionEndYearItemStateChanged
        // TODO add your handling code here:
        setPromotionEndDate.setMonthByCurrYear();
        setPromotionEndDate.setDateByCurrMonth();
    }//GEN-LAST:event_cbxPromotionEndYearItemStateChanged

    private void cbxPromotionEndMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionEndMonthItemStateChanged
        // TODO add your handling code here:
        setPromotionEndDate.setDateByCurrMonth();
    }//GEN-LAST:event_cbxPromotionEndMonthItemStateChanged

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
        MainHub.main.openPromotion = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void txtPromotionNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionNameFocusGained
        // TODO add your handling code here:
        txtPromotionName.selectAll();
    }//GEN-LAST:event_txtPromotionNameFocusGained

    private void txtPromotionDetailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionDetailFocusGained
        // TODO add your handling code here:
        txtPromotionDetail.selectAll();
    }//GEN-LAST:event_txtPromotionDetailFocusGained

    private void txtAmount1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmount1FocusGained
        // TODO add your handling code here:
        txtAmount1.selectAll();
    }//GEN-LAST:event_txtAmount1FocusGained

    private void txtAmount4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmount4FocusGained
        // TODO add your handling code here:
        txtAmount4.selectAll();
    }//GEN-LAST:event_txtAmount4FocusGained

    private void cbxPromotionTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionTypeItemStateChanged
        // TODO add your handling code here:
        int index = cbxPromotionType.getSelectedIndex();
        
        switch(index) {
            case 0:
                cbxDiscountResult.setEnabled(true);
                txtDiscountResult.setEnabled(true);
                txtDiscountResultDecimal.setEnabled(true);
                cbxPlusResult.setEnabled(false);
                txtAmount4.setEnabled(false);
                break;
            case 1:
                cbxDiscountResult.setEnabled(false);
                txtDiscountResult.setEnabled(false);
                txtDiscountResultDecimal.setEnabled(false);
                cbxPlusResult.setEnabled(true);
                txtAmount4.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_cbxPromotionTypeItemStateChanged

    private void cbxNumOfConditionProductItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNumOfConditionProductItemStateChanged
        // TODO add your handling code here:
        int index = cbxNumOfConditionProduct.getSelectedIndex();
        
        switch(index) {
            case 0:
                cbxAmount1.setEnabled(true);
                txtAmount1.setEnabled(true);
                cbxAmount2.setEnabled(false);
                txtAmount2.setEnabled(false);
                cbxAmount3.setEnabled(false);
                txtAmount3.setEnabled(false);
                break;
            case 1:
                cbxAmount1.setEnabled(true);
                txtAmount1.setEnabled(true);
                cbxAmount2.setEnabled(true);
                txtAmount2.setEnabled(true);
                cbxAmount3.setEnabled(false);
                txtAmount3.setEnabled(false);
                break;
            case 2:
                cbxAmount1.setEnabled(true);
                txtAmount1.setEnabled(true);
                cbxAmount2.setEnabled(true);
                txtAmount2.setEnabled(true);
                cbxAmount3.setEnabled(true);
                txtAmount3.setEnabled(true);
                break;
                
        }
        
        setCbxDiscountResult();
    }//GEN-LAST:event_cbxNumOfConditionProductItemStateChanged

    private void txtAmount1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmount1KeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAmount1KeyTyped

    private void txtAmount2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmount2FocusGained
        // TODO add your handling code here:
        txtAmount2.selectAll();
    }//GEN-LAST:event_txtAmount2FocusGained

    private void txtAmount3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAmount3FocusGained
        // TODO add your handling code here:
        txtAmount3.selectAll();
    }//GEN-LAST:event_txtAmount3FocusGained

    private void txtAmount2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmount2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAmount2KeyTyped

    private void txtAmount3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmount3KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAmount3KeyTyped

    private void txtDiscountResultFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountResultFocusGained
        // TODO add your handling code here:
        txtDiscountResult.selectAll();
    }//GEN-LAST:event_txtDiscountResultFocusGained

    private void txtDiscountResultDecimalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountResultDecimalFocusGained
        // TODO add your handling code here:
        txtDiscountResultDecimal.selectAll();
    }//GEN-LAST:event_txtDiscountResultDecimalFocusGained

    private void txtDiscountResultKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountResultKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscountResultKeyTyped

    private void txtDiscountResultDecimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountResultDecimalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDiscountResultDecimalKeyTyped

    private void txtAmount4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmount4KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAmount4KeyTyped

    private void btnPromotionViewNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionViewNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", promotionViewControl, btnPromotionViewNext, txtPromotionViewPage);
    }//GEN-LAST:event_btnPromotionViewNextMouseClicked

    private void btnPromotionViewPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionViewPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", promotionViewControl, btnPromotionViewPre, txtPromotionViewPage);
    }//GEN-LAST:event_btnPromotionViewPreMouseClicked

    private void txtPromotionViewPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionViewPageFocusGained
        // TODO add your handling code here:
        txtPromotionViewPage.nextFocus();
    }//GEN-LAST:event_txtPromotionViewPageFocusGained

    private void btnPromotionViewShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionViewShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnPromotionViewSearch.isShowing()) {
            pnPromotionViewSearch.setVisible(true);
            btnPromotionViewShowSearch.setText("ปิดการค้นหา");
        } else {
            pnPromotionViewSearch.setVisible(false);
            btnPromotionViewShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnPromotionViewShowSearchMouseClicked

    private void txtPromotionViewSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPromotionViewSearchFocusGained
        // TODO add your handling code here:
        txtPromotionViewSearch.selectAll();
    }//GEN-LAST:event_txtPromotionViewSearchFocusGained

    private void txtPromotionViewSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPromotionViewSearchKeyReleased
        // TODO add your handling code here:
        promotionViewControl.showData(true);
    }//GEN-LAST:event_txtPromotionViewSearchKeyReleased

    private void cbxPromotionViewSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionViewSearchFromItemStateChanged
        // TODO add your handling code here:
        promotionViewControl.showData(true);
    }//GEN-LAST:event_cbxPromotionViewSearchFromItemStateChanged

    private void cbxPromotionViewSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionViewSortByItemStateChanged
        // TODO add your handling code here:
        promotionViewControl.showData(true);
    }//GEN-LAST:event_cbxPromotionViewSortByItemStateChanged

    private void cbxPromotionViewSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionViewSortFromItemStateChanged
        // TODO add your handling code here:
        promotionViewControl.showData(false);
    }//GEN-LAST:event_cbxPromotionViewSortFromItemStateChanged

    private void cbxPromotionViewPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPromotionViewPerPageItemStateChanged
        // TODO add your handling code here:
        promotionViewControl.showData(false);
    }//GEN-LAST:event_cbxPromotionViewPerPageItemStateChanged

    private void btnPromotionViewClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionViewClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(promotionViewControl);
    }//GEN-LAST:event_btnPromotionViewClearSearchMouseClicked

    private void btnPromotionUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionUpdateMouseClicked
        // TODO add your handling code here:
        promotionControl.updateData();
    }//GEN-LAST:event_btnPromotionUpdateMouseClicked

    private void btnPromotionDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPromotionDeleteMouseClicked
        // TODO add your handling code here:
        promotionControl.deleteData();
    }//GEN-LAST:event_btnPromotionDeleteMouseClicked

    private void tbPromotionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPromotionMouseClicked
        // TODO add your handling code here:
        Util.tableToText(promotionControl);
    }//GEN-LAST:event_tbPromotionMouseClicked

    private void cbxAmount2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAmount2ItemStateChanged
        // TODO add your handling code here:
        
        if (cbx2Change) {
            setCbxProductName(cbxAmount3, cbxAmount1.getSelectedItem().toString(), cbxAmount2.getSelectedItem().toString());
            cbx2Change = false;
        }
        setCbxDiscountResult();
    }//GEN-LAST:event_cbxAmount2ItemStateChanged

    private void cbxAmount3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAmount3ItemStateChanged
        // TODO add your handling code here:
        setCbxDiscountResult();
    }//GEN-LAST:event_cbxAmount3ItemStateChanged

    private void cbxAmount1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxAmount1ItemStateChanged
        // TODO add your handling code here:
        cbx2Change = false;
        setCbxProductName(cbxAmount2, cbxAmount1.getSelectedItem().toString());
        setCbxProductName(cbxAmount3, cbxAmount1.getSelectedItem().toString(), cbxAmount2.getSelectedItem().toString());
        setCbxDiscountResult();
    }//GEN-LAST:event_cbxAmount1ItemStateChanged

    private void cbxAmount2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxAmount2MouseClicked
        // TODO add your handling code here:
        cbx2Change = true; //ตัวส่งสัญญานว่าเป็นการเลือกจาก cbxAmount2 จริง ไม่ได้เกิดจากผลกระทบของการเลือก cbxAmount1
    }//GEN-LAST:event_cbxAmount2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPromotionClear;
    private javax.swing.JButton btnPromotionClearSearch;
    private javax.swing.JButton btnPromotionDelete;
    private javax.swing.JButton btnPromotionInsert;
    private javax.swing.JButton btnPromotionNext;
    private javax.swing.JButton btnPromotionPre;
    private javax.swing.JButton btnPromotionShowSearch;
    private javax.swing.JButton btnPromotionUpdate;
    private javax.swing.JButton btnPromotionViewClearSearch;
    private javax.swing.JButton btnPromotionViewNext;
    private javax.swing.JButton btnPromotionViewPre;
    private javax.swing.JButton btnPromotionViewShowSearch;
    private javax.swing.JComboBox cbxAmount1;
    private javax.swing.JComboBox cbxAmount2;
    private javax.swing.JComboBox cbxAmount3;
    private javax.swing.JComboBox cbxDiscountResult;
    private javax.swing.JComboBox cbxNumOfConditionProduct;
    private javax.swing.JComboBox cbxPlusResult;
    private javax.swing.JComboBox cbxPromotionEndDate;
    private javax.swing.JComboBox cbxPromotionEndMonth;
    private javax.swing.JComboBox cbxPromotionEndYear;
    private javax.swing.JComboBox cbxPromotionPerPage;
    private javax.swing.JComboBox cbxPromotionSearchFrom;
    private javax.swing.JComboBox cbxPromotionSortBy;
    private javax.swing.JComboBox cbxPromotionSortFrom;
    private javax.swing.JComboBox cbxPromotionStartDate;
    private javax.swing.JComboBox cbxPromotionStartMonth;
    private javax.swing.JComboBox cbxPromotionStartYear;
    private javax.swing.JComboBox cbxPromotionType;
    private javax.swing.JComboBox cbxPromotionViewPerPage;
    private javax.swing.JComboBox cbxPromotionViewSearchFrom;
    private javax.swing.JComboBox cbxPromotionViewSortBy;
    private javax.swing.JComboBox cbxPromotionViewSortFrom;
    private javax.swing.JLabel lbPromotionSearch;
    private javax.swing.JLabel lbPromotionViewSearch;
    private javax.swing.JLabel lblAmount1;
    private javax.swing.JLabel lblAmount2;
    private javax.swing.JLabel lblAmount3;
    private javax.swing.JLabel lblAmount4;
    private javax.swing.JLabel lblConditionProduct;
    private javax.swing.JLabel lblDiscountResult;
    private javax.swing.JLabel lblDiscountResultDot;
    private javax.swing.JLabel lblNumOfConditionProduct;
    private javax.swing.JLabel lblPlusResult;
    private javax.swing.JLabel lblPromotionDetail;
    private javax.swing.JLabel lblPromotionEndDate;
    private javax.swing.JLabel lblPromotionName;
    private javax.swing.JLabel lblPromotionNumber;
    private javax.swing.JLabel lblPromotionPerPage;
    private javax.swing.JLabel lblPromotionSearchForm;
    private javax.swing.JLabel lblPromotionSortBy;
    private javax.swing.JLabel lblPromotionSortFrom;
    private javax.swing.JLabel lblPromotionStartDate;
    private javax.swing.JLabel lblPromotionType;
    private javax.swing.JLabel lblPromotionViewNumber;
    private javax.swing.JLabel lblPromotionViewPerPage;
    private javax.swing.JLabel lblPromotionViewSearchFrom;
    private javax.swing.JLabel lblPromotionViewSortBy;
    private javax.swing.JLabel lblPromotionViewSortForm;
    private javax.swing.JLabel lbllNumOfConditionProductList;
    private javax.swing.JPanel pbInputTbPromotion;
    private javax.swing.JPanel pbInputTbPromotionView;
    private javax.swing.JPanel pnPromotion;
    private javax.swing.JPanel pnPromotionManage;
    private javax.swing.JPanel pnPromotionPagination;
    private javax.swing.JPanel pnPromotionRight;
    private javax.swing.JPanel pnPromotionSearch;
    private javax.swing.JPanel pnPromotionView;
    private javax.swing.JPanel pnPromotionViewPagination;
    private javax.swing.JPanel pnPromotionViewRight;
    private javax.swing.JPanel pnPromotionViewSearch;
    private javax.swing.JScrollPane scpPromotionRight;
    private javax.swing.JScrollPane scpPromotionViewRight;
    private javax.swing.JScrollPane scpTablePromotion;
    private javax.swing.JScrollPane scpTablePromotionView;
    private javax.swing.JTable tbPromotion;
    private javax.swing.JTable tbPromotionView;
    private javax.swing.JTabbedPane tbpPromotionMain;
    private javax.swing.JTextField txtAmount1;
    private javax.swing.JTextField txtAmount2;
    private javax.swing.JTextField txtAmount3;
    private javax.swing.JTextField txtAmount4;
    private javax.swing.JTextField txtDiscountResult;
    private javax.swing.JTextField txtDiscountResultDecimal;
    private javax.swing.JTextField txtPromotionDetail;
    private javax.swing.JTextField txtPromotionName;
    private javax.swing.JTextField txtPromotionPage;
    private javax.swing.JTextField txtPromotionSearch;
    private javax.swing.JTextField txtPromotionViewPage;
    private javax.swing.JTextField txtPromotionViewSearch;
    // End of variables declaration//GEN-END:variables
}
