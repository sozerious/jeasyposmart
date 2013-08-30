/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.StockControl;
import Control.StockExpireControl;
import Control.StockRemainControl;
import Model.Hub.MainHub;
import Model.Hub.StockHub;
import Utility.DBFactory;
import Utility.JTextFieldLimit;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author java-dev
 */
public class FormStock extends javax.swing.JInternalFrame {
    
    SetCbxDateChange setDateChange;
    StockControl stockControl;
    StockRemainControl stockRemainControl;
    StockExpireControl stockExpireControl;
    
    private DefaultTableModel modelStock, modelStockRemain, modelStockExpire;
    
    public static Vector vectorStock, vectorStockInverse;
    public static Vector vectorStockRemain, vectorStockRemainInverse;
    public static Vector vectorStockExpire, vectorStockExpireInverse;
    
    
    /**
     * Creates new form FromStock
     */
    public FormStock() {
        
        initComponents();
        StockHub.setStock(this);
        setDateChange = new SetCbxDateChange(cbxStockExpireYear, cbxStockExpireMonth, cbxStockExpireDate);
        setDateChange.setYear();
        setDateChange.setMonthByCurrYear();
        setDateChange.setDateByCurrMonth();
        setCbxProductName();
        
        stockControl = new StockControl();
        stockRemainControl = new StockRemainControl();
        stockExpireControl = new StockExpireControl();
        
        vectorStock = new Vector();
        vectorStockInverse = new Vector();
        vectorStockRemain = new Vector();
        vectorStockRemainInverse = new Vector();
        vectorStockExpire = new Vector();
        vectorStockExpireInverse = new Vector();
        
        tbStock.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        modelStock = (DefaultTableModel) tbStock.getModel();
        tbStockRemain.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        modelStockRemain = (DefaultTableModel) tbStockRemain.getModel();
        tbStockExpire.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        modelStockExpire = (DefaultTableModel) tbStockExpire.getModel();
        
        txtStockAmount.setDocument(new JTextFieldLimit(4));
        
        stockControl.showData(true);
        stockRemainControl.showData(true);
        stockExpireControl.showData(true);
        
    }

    //<editor-fold defaultstate="collapsed" desc="Getter Stock">
    public JTabbedPane getTbpStockMain() {
        return tbpStockMain;
    }

    public JButton getBtnStockNext() {
        return btnStockNext;
    }

    public JButton getBtnStockPre() {
        return btnStockPre;
    }

    public JComboBox getCbxStockExpireDate() {
        return cbxStockExpireDate;
    }

    public JComboBox getCbxStockExpireMonth() {
        return cbxStockExpireMonth;
    }

    public JComboBox getCbxStockExpireYear() {
        return cbxStockExpireYear;
    }

    public JComboBox getCbxStockPerPage() {
        return cbxStockPerPage;
    }

    public JComboBox getCbxStockProductName() {
        return cbxStockProductName;
    }

    public JComboBox getCbxStockSearchFrom() {
        return cbxStockSearchFrom;
    }

    public JComboBox getCbxStockSortBy() {
        return cbxStockSortBy;
    }

    public JComboBox getCbxStockSortFrom() {
        return cbxStockSortFrom;
    }

    public JTable getTbStock() {
        return tbStock;
    }

    public JTextField getTxtStockAmount() {
        return txtStockAmount;
    }

    public JTextField getTxtStockPage() {
        return txtStockPage;
    }

    public JTextField getTxtStockSearch() {
        return txtStockSearch;
    }

    public DefaultTableModel getModelStock() {
        return modelStock;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter StockRemain">
    public DefaultTableModel getModelStockRemain() {
        return modelStockRemain;
    }
    
    public JButton getBtnStockRemainNext() {
        return btnStockRemainNext;
    }
    
    public JButton getBtnStockRemainPre() {
        return btnStockRemainPre;
    }
    
    public JComboBox getCbxStockRemainPerPage() {
        return cbxStockRemainPerPage;
    }
    
    public JComboBox getCbxStockRemainSearchFrom() {
        return cbxStockRemainSearchFrom;
    }
    
    public JComboBox getCbxStockRemainSortBy() {
        return cbxStockRemainSortBy;
    }
    
    public JComboBox getCbxStockRemainSortFrom() {
        return cbxStockRemainSortFrom;
    }
    
    public JTable getTbStockRemain() {
        return tbStockRemain;
    }
    
    public JTextField getTxtStockRemainPage() {
        return txtStockRemainPage;
    }
    
    public JTextField getTxtStockRemainSearch() {
        return txtStockRemainSearch;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Getter StockExpire">
    public DefaultTableModel getModelStockExpire() {
        return modelStockExpire;
    }
    
    public JButton getBtnStockExpireNext() {
        return btnStockExpireNext;
    }
    
    public JButton getBtnStockExpirePre() {
        return btnStockExpirePre;
    }
    
    public JComboBox getCbxStockExpirePerPage() {
        return cbxStockExpirePerPage;
    }
    
    public JComboBox getCbxStockExpireSearchFrom() {
        return cbxStockExpireSearchFrom;
    }
    
    public JComboBox getCbxStockExpireSortBy() {
        return cbxStockExpireSortBy;
    }
    
    public JComboBox getCbxStockExpireSortFrom() {
        return cbxStockExpireSortFrom;
    }
    
    public JTable getTbStockExpire() {
        return tbStockExpire;
    }
    
    public JTextField getTxtStockExpireNumDayWarning() {
        return txtStockExpireNumDayWarning;
    }
    
    public JTextField getTxtStockExpirePage() {
        return txtStockExpirePage;
    }
    
    public JTextField getTxtStockExpireSearch() {
        return txtStockExpireSearch;
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

        tbpStockMain = new javax.swing.JTabbedPane();
        pnStock = new javax.swing.JPanel();
        pnInputTbStock = new javax.swing.JPanel();
        scpTableStock = new javax.swing.JScrollPane();
        tbStock = new javax.swing.JTable();
        pnStockPagination = new javax.swing.JPanel();
        btnStockNext = new javax.swing.JButton();
        btnStockPre = new javax.swing.JButton();
        txtStockPage = new javax.swing.JTextField();
        btnStockShowSearch = new javax.swing.JButton();
        scpStockRight = new javax.swing.JScrollPane();
        pnStockRight = new javax.swing.JPanel();
        pnStockManage = new javax.swing.JPanel();
        txtStockAmount = new javax.swing.JTextField();
        cbxStockProductName = new javax.swing.JComboBox();
        lblStockProductName = new javax.swing.JLabel();
        cbxStockExpireYear = new javax.swing.JComboBox();
        lblStockAmount = new javax.swing.JLabel();
        lblStockExpire = new javax.swing.JLabel();
        btnStockInsert = new javax.swing.JButton();
        btnStockEdit = new javax.swing.JButton();
        btnStockClear = new javax.swing.JButton();
        cbxStockExpireMonth = new javax.swing.JComboBox();
        cbxStockExpireDate = new javax.swing.JComboBox();
        pnStockSearch = new javax.swing.JPanel();
        lbStockSearch = new javax.swing.JLabel();
        lblStockSearchFrom = new javax.swing.JLabel();
        lblStockSortBy = new javax.swing.JLabel();
        lblStockSortForm = new javax.swing.JLabel();
        lblStockNumber = new javax.swing.JLabel();
        lblStockPerPage = new javax.swing.JLabel();
        txtStockSearch = new javax.swing.JTextField();
        cbxStockSearchFrom = new javax.swing.JComboBox();
        cbxStockSortBy = new javax.swing.JComboBox();
        cbxStockSortFrom = new javax.swing.JComboBox();
        cbxStockPerPage = new javax.swing.JComboBox();
        btnStockClearSearch = new javax.swing.JButton();
        pnStockRemain = new javax.swing.JPanel();
        pbInputTbStockRemain = new javax.swing.JPanel();
        scpTableStockRemain = new javax.swing.JScrollPane();
        tbStockRemain = new javax.swing.JTable();
        pnStockRemainPagination = new javax.swing.JPanel();
        btnStockRemainNext = new javax.swing.JButton();
        btnStockRemainPre = new javax.swing.JButton();
        txtStockRemainPage = new javax.swing.JTextField();
        scpStockRemainRight = new javax.swing.JScrollPane();
        pnStockRemainRight = new javax.swing.JPanel();
        pnStockRemainSearch = new javax.swing.JPanel();
        lbStockRemainSearch = new javax.swing.JLabel();
        lblStockRemainSearchFrom = new javax.swing.JLabel();
        lblStockRemainSortBy = new javax.swing.JLabel();
        lblStockRemainSortForm = new javax.swing.JLabel();
        lblStockRemainNumber = new javax.swing.JLabel();
        lblStockRemainPerPage = new javax.swing.JLabel();
        txtStockRemainSearch = new javax.swing.JTextField();
        cbxStockRemainSearchFrom = new javax.swing.JComboBox();
        cbxStockRemainSortBy = new javax.swing.JComboBox();
        cbxStockRemainSortFrom = new javax.swing.JComboBox();
        cbxStockRemainPerPage = new javax.swing.JComboBox();
        btnStockRemainClearSearch = new javax.swing.JButton();
        pnStockExpire = new javax.swing.JPanel();
        pnInputTbStockExpire = new javax.swing.JPanel();
        scpTableStockExpire = new javax.swing.JScrollPane();
        tbStockExpire = new javax.swing.JTable();
        pnStockExpirePagination = new javax.swing.JPanel();
        btnStockExpireNext = new javax.swing.JButton();
        btnStockExpirePre = new javax.swing.JButton();
        txtStockExpirePage = new javax.swing.JTextField();
        btnStockExpireShowSearch = new javax.swing.JButton();
        scpStockExpireRight = new javax.swing.JScrollPane();
        pnStockExpireRight = new javax.swing.JPanel();
        pnStockExpireManage = new javax.swing.JPanel();
        txtStockExpireNumDayWarning = new javax.swing.JTextField();
        lblStockExpireNumDayWarning = new javax.swing.JLabel();
        btnStockExpireCancelWarning = new javax.swing.JButton();
        btnStockExpireCancelWarningAll = new javax.swing.JButton();
        btnStockExpireWarningAll = new javax.swing.JButton();
        lblStockExpireDayWarning = new javax.swing.JLabel();
        pnStockExpireSearch = new javax.swing.JPanel();
        lbStockExpireSearch = new javax.swing.JLabel();
        lblStockExpireSearchFrom = new javax.swing.JLabel();
        lblStockExpireSortBy = new javax.swing.JLabel();
        lblStockExpireSortForm = new javax.swing.JLabel();
        lblStockExpireNumber = new javax.swing.JLabel();
        lblStockExpirePerPage = new javax.swing.JLabel();
        txtStockExpireSearch = new javax.swing.JTextField();
        cbxStockExpireSearchFrom = new javax.swing.JComboBox();
        cbxStockExpireSortBy = new javax.swing.JComboBox();
        cbxStockExpireSortFrom = new javax.swing.JComboBox();
        cbxStockExpirePerPage = new javax.swing.JComboBox();
        btnStockExpireClearSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("จัดการคลังสินค้า");
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        tbpStockMain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pnStock.setLayout(new java.awt.BorderLayout());

        pnInputTbStock.setLayout(new java.awt.BorderLayout());

        tbStock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขที่สต๊อก", "ชื่อสินค้า", "เลขที่ล็อต", "จำนวนสต๊อก", "จำนวนคงเหลือ", "วันที่สต๊อก", "วันหมดอายุ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbStock.setRowHeight(25);
        tbStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbStockMouseClicked(evt);
            }
        });
        scpTableStock.setViewportView(tbStock);

        pnInputTbStock.add(scpTableStock, java.awt.BorderLayout.CENTER);

        pnStockPagination.setPreferredSize(new java.awt.Dimension(826, 50));

        btnStockNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockNext.setText("ถัดไป >>");
        btnStockNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockNextMouseClicked(evt);
            }
        });

        btnStockPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockPre.setText("<< ก่อนหน้า");
        btnStockPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnStockPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockPreMouseClicked(evt);
            }
        });

        txtStockPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStockPage.setText("1");
        txtStockPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtStockPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockPageFocusGained(evt);
            }
        });

        btnStockShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockShowSearch.setText("ปิดการค้นหา");
        btnStockShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnStockShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStockPaginationLayout = new javax.swing.GroupLayout(pnStockPagination);
        pnStockPagination.setLayout(pnStockPaginationLayout);
        pnStockPaginationLayout.setHorizontalGroup(
            pnStockPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStockPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 372, Short.MAX_VALUE)
                .addComponent(btnStockShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnStockPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnStockNext, btnStockPre});

        pnStockPaginationLayout.setVerticalGroup(
            pnStockPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStockPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockNext)
                    .addComponent(txtStockPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockNext, btnStockPre, txtStockPage});

        pnInputTbStock.add(pnStockPagination, java.awt.BorderLayout.PAGE_END);

        pnStock.add(pnInputTbStock, java.awt.BorderLayout.CENTER);

        scpStockRight.setBorder(null);
        scpStockRight.setPreferredSize(new java.awt.Dimension(370, 470));

        pnStockRight.setPreferredSize(new java.awt.Dimension(355, 470));

        pnStockManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการคลังสินค้า ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnStockManage.setPreferredSize(new java.awt.Dimension(345, 213));

        txtStockAmount.setBackground(new java.awt.Color(255, 255, 0));
        txtStockAmount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockAmountFocusGained(evt);
            }
        });
        txtStockAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockAmountKeyTyped(evt);
            }
        });

        cbxStockProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockProductName.setPreferredSize(new java.awt.Dimension(30, 25));
        cbxStockProductName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxStockProductNamePopupMenuWillBecomeVisible(evt);
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbxStockProductName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockProductNameItemStateChanged(evt);
            }
        });

        lblStockProductName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockProductName.setText("สินค้า : ");

        cbxStockExpireYear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpireYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpireYearItemStateChanged(evt);
            }
        });

        lblStockAmount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockAmount.setText("จำนวนสต๊อก : ");

        lblStockExpire.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpire.setText("หมดอายุ : ");

        btnStockInsert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockInsert.setText("เพิ่ม");
        btnStockInsert.setPreferredSize(new java.awt.Dimension(51, 25));
        btnStockInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockInsertMouseClicked(evt);
            }
        });

        btnStockEdit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockEdit.setText("แก้ไข");
        btnStockEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockEditMouseClicked(evt);
            }
        });

        btnStockClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockClear.setText("ล้าง");
        btnStockClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockClearMouseClicked(evt);
            }
        });

        cbxStockExpireMonth.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpireMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpireMonthItemStateChanged(evt);
            }
        });

        cbxStockExpireDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout pnStockManageLayout = new javax.swing.GroupLayout(pnStockManage);
        pnStockManage.setLayout(pnStockManageLayout);
        pnStockManageLayout.setHorizontalGroup(
            pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockManageLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStockProductName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockAmount, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockExpire, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnStockManageLayout.createSequentialGroup()
                        .addComponent(btnStockInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStockEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStockClear))
                    .addGroup(pnStockManageLayout.createSequentialGroup()
                        .addComponent(cbxStockExpireYear, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxStockExpireMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxStockExpireDate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxStockProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pnStockManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnStockClear, btnStockEdit, btnStockInsert});

        pnStockManageLayout.setVerticalGroup(
            pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStockProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockProductName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStockAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStockExpireYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockExpire)
                    .addComponent(cbxStockExpireMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxStockExpireDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnStockManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStockInsert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockEdit)
                    .addComponent(btnStockClear))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pnStockManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockClear, btnStockEdit, btnStockInsert});

        pnStockManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbxStockExpireDate, cbxStockExpireMonth, cbxStockExpireYear, cbxStockProductName, txtStockAmount});

        pnStockSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnStockSearch.setPreferredSize(new java.awt.Dimension(345, 215));

        lbStockSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbStockSearch.setText("ค้นหา : ");

        lblStockSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockSearchFrom.setText("ค้นหาจาก : ");

        lblStockSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockSortBy.setText("เรียงโดย : ");

        lblStockSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockSortForm.setText("เรียงจาก : ");

        lblStockNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockNumber.setText("จำนวน : ");

        lblStockPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockPerPage.setText("รายการ / หน้า");

        txtStockSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtStockSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockSearchFocusGained(evt);
            }
        });
        txtStockSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockSearchKeyReleased(evt);
            }
        });

        cbxStockSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "จำนวนสต๊อก(ต่ำสุด)", "จำนวนคงเหลือ(ต่ำสุด)", "วันที่สต๊อก(เริ่มจาก)", "วันหมดอายุ(เริ่มจาก)" }));
        cbxStockSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockSearchFromItemStateChanged(evt);
            }
        });

        cbxStockSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "เลขที่ล็อต", "จำนวนสต๊อก", "จำนวนคงเหลือ", "วันที่สต๊อก", "วันหมดอายุ" }));
        cbxStockSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockSortByItemStateChanged(evt);
            }
        });

        cbxStockSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxStockSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockSortFromItemStateChanged(evt);
            }
        });

        cbxStockPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxStockPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockPerPageItemStateChanged(evt);
            }
        });

        btnStockClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockClearSearch.setText("ล้าง");
        btnStockClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnStockClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStockSearchLayout = new javax.swing.GroupLayout(pnStockSearch);
        pnStockSearch.setLayout(pnStockSearchLayout);
        pnStockSearchLayout.setHorizontalGroup(
            pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStockSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockSearchFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStockClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockSearchLayout.createSequentialGroup()
                        .addComponent(cbxStockPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStockPerPage))
                    .addComponent(cbxStockSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxStockSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxStockSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnStockSearchLayout.setVerticalGroup(
            pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStockSearch)
                    .addComponent(txtStockSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockSearchFrom)
                    .addComponent(cbxStockSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockSortBy)
                    .addComponent(cbxStockSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockSortForm)
                    .addComponent(cbxStockSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockNumber)
                    .addComponent(lblStockPerPage)
                    .addComponent(cbxStockPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStockClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockClearSearch, cbxStockPerPage, cbxStockSearchFrom, cbxStockSortBy, cbxStockSortFrom, txtStockSearch});

        javax.swing.GroupLayout pnStockRightLayout = new javax.swing.GroupLayout(pnStockRight);
        pnStockRight.setLayout(pnStockRightLayout);
        pnStockRightLayout.setHorizontalGroup(
            pnStockRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRightLayout.createSequentialGroup()
                .addGroup(pnStockRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnStockSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(pnStockManage, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnStockRightLayout.setVerticalGroup(
            pnStockRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRightLayout.createSequentialGroup()
                .addComponent(pnStockManage, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnStockSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        scpStockRight.setViewportView(pnStockRight);

        pnStock.add(scpStockRight, java.awt.BorderLayout.LINE_END);

        tbpStockMain.addTab("จัดการคลังสินค้า", pnStock);

        pnStockRemain.setLayout(new java.awt.BorderLayout());

        pbInputTbStockRemain.setLayout(new java.awt.BorderLayout());

        tbStockRemain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbStockRemain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ชื่อสินค้า", "จำนวนคงเหลือ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbStockRemain.setRowHeight(25);
        scpTableStockRemain.setViewportView(tbStockRemain);
        tbStockRemain.getColumnModel().getColumn(1).setMinWidth(200);
        tbStockRemain.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbStockRemain.getColumnModel().getColumn(1).setMaxWidth(300);

        pbInputTbStockRemain.add(scpTableStockRemain, java.awt.BorderLayout.CENTER);

        pnStockRemainPagination.setPreferredSize(new java.awt.Dimension(779, 50));

        btnStockRemainNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockRemainNext.setText("ถัดไป >>");
        btnStockRemainNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockRemainNextMouseClicked(evt);
            }
        });

        btnStockRemainPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockRemainPre.setText("<< ก่อนหน้า");
        btnStockRemainPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnStockRemainPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockRemainPreMouseClicked(evt);
            }
        });

        txtStockRemainPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockRemainPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStockRemainPage.setText("1");
        txtStockRemainPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtStockRemainPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockRemainPageFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnStockRemainPaginationLayout = new javax.swing.GroupLayout(pnStockRemainPagination);
        pnStockRemainPagination.setLayout(pnStockRemainPaginationLayout);
        pnStockRemainPaginationLayout.setHorizontalGroup(
            pnStockRemainPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStockRemainPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockRemainPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockRemainNext)
                .addContainerGap(551, Short.MAX_VALUE))
        );

        pnStockRemainPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnStockRemainNext, btnStockRemainPre});

        pnStockRemainPaginationLayout.setVerticalGroup(
            pnStockRemainPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockRemainPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStockRemainPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockRemainNext)
                    .addComponent(txtStockRemainPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockRemainPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockRemainNext, btnStockRemainPre, txtStockRemainPage});

        pbInputTbStockRemain.add(pnStockRemainPagination, java.awt.BorderLayout.PAGE_END);

        pnStockRemain.add(pbInputTbStockRemain, java.awt.BorderLayout.CENTER);

        scpStockRemainRight.setBorder(null);
        scpStockRemainRight.setPreferredSize(new java.awt.Dimension(370, 450));

        pnStockRemainRight.setPreferredSize(new java.awt.Dimension(355, 450));

        pnStockRemainSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnStockRemainSearch.setPreferredSize(new java.awt.Dimension(355, 215));

        lbStockRemainSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbStockRemainSearch.setText("ค้นหา : ");

        lblStockRemainSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockRemainSearchFrom.setText("ค้นหาจาก : ");

        lblStockRemainSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockRemainSortBy.setText("เรียงโดย : ");

        lblStockRemainSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockRemainSortForm.setText("เรียงจาก : ");

        lblStockRemainNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockRemainNumber.setText("จำนวน : ");

        lblStockRemainPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockRemainPerPage.setText("รายการ / หน้า");

        txtStockRemainSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockRemainSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtStockRemainSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockRemainSearchFocusGained(evt);
            }
        });
        txtStockRemainSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockRemainSearchKeyReleased(evt);
            }
        });

        cbxStockRemainSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockRemainSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "จำนวนคงเหลือ(ต่ำสุด)" }));
        cbxStockRemainSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockRemainSearchFromItemStateChanged(evt);
            }
        });

        cbxStockRemainSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockRemainSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "จำนวนคงเหลือ" }));
        cbxStockRemainSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockRemainSortByItemStateChanged(evt);
            }
        });

        cbxStockRemainSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockRemainSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxStockRemainSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockRemainSortFromItemStateChanged(evt);
            }
        });

        cbxStockRemainPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockRemainPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxStockRemainPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockRemainPerPageItemStateChanged(evt);
            }
        });

        btnStockRemainClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockRemainClearSearch.setText("ล้าง");
        btnStockRemainClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnStockRemainClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockRemainClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStockRemainSearchLayout = new javax.swing.GroupLayout(pnStockRemainSearch);
        pnStockRemainSearch.setLayout(pnStockRemainSearchLayout);
        pnStockRemainSearchLayout.setHorizontalGroup(
            pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStockRemainSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockRemainSearchFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockRemainSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockRemainSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockRemainNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStockRemainClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockRemainSearchLayout.createSequentialGroup()
                        .addComponent(cbxStockRemainPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStockRemainPerPage))
                    .addComponent(cbxStockRemainSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockRemainSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxStockRemainSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)
                        .addComponent(cbxStockRemainSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        pnStockRemainSearchLayout.setVerticalGroup(
            pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStockRemainSearch)
                    .addComponent(txtStockRemainSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockRemainSearchFrom)
                    .addComponent(cbxStockRemainSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockRemainSortBy)
                    .addComponent(cbxStockRemainSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockRemainSortForm)
                    .addComponent(cbxStockRemainSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockRemainSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockRemainNumber)
                    .addComponent(lblStockRemainPerPage)
                    .addComponent(cbxStockRemainPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStockRemainClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockRemainSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockRemainClearSearch, cbxStockRemainPerPage, cbxStockRemainSearchFrom, cbxStockRemainSortBy, cbxStockRemainSortFrom, txtStockRemainSearch});

        javax.swing.GroupLayout pnStockRemainRightLayout = new javax.swing.GroupLayout(pnStockRemainRight);
        pnStockRemainRight.setLayout(pnStockRemainRightLayout);
        pnStockRemainRightLayout.setHorizontalGroup(
            pnStockRemainRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainRightLayout.createSequentialGroup()
                .addComponent(pnStockRemainSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        pnStockRemainRightLayout.setVerticalGroup(
            pnStockRemainRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockRemainRightLayout.createSequentialGroup()
                .addComponent(pnStockRemainSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 287, Short.MAX_VALUE))
        );

        scpStockRemainRight.setViewportView(pnStockRemainRight);

        pnStockRemain.add(scpStockRemainRight, java.awt.BorderLayout.LINE_END);

        tbpStockMain.addTab("สินค้าคงเหลือ", pnStockRemain);

        pnStockExpire.setLayout(new java.awt.BorderLayout());

        pnInputTbStockExpire.setLayout(new java.awt.BorderLayout());

        tbStockExpire.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbStockExpire.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "เลขที่สต๊อก", "ชื่อสินค้า", "เลขที่ล็อต", "จำนวนคงเหลือ", "วันหมดอายุ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbStockExpire.setRowHeight(25);
        scpTableStockExpire.setViewportView(tbStockExpire);

        pnInputTbStockExpire.add(scpTableStockExpire, java.awt.BorderLayout.CENTER);

        pnStockExpirePagination.setPreferredSize(new java.awt.Dimension(1196, 50));

        btnStockExpireNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireNext.setText("ถัดไป >>");
        btnStockExpireNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireNextMouseClicked(evt);
            }
        });

        btnStockExpirePre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpirePre.setText("<< ก่อนหน้า");
        btnStockExpirePre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnStockExpirePre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpirePreMouseClicked(evt);
            }
        });

        txtStockExpirePage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockExpirePage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStockExpirePage.setText("1");
        txtStockExpirePage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtStockExpirePage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockExpirePageFocusGained(evt);
            }
        });

        btnStockExpireShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireShowSearch.setText("ปิดการค้นหา");
        btnStockExpireShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnStockExpireShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireShowSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStockExpirePaginationLayout = new javax.swing.GroupLayout(pnStockExpirePagination);
        pnStockExpirePagination.setLayout(pnStockExpirePaginationLayout);
        pnStockExpirePaginationLayout.setHorizontalGroup(
            pnStockExpirePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpirePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStockExpirePre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStockExpirePage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockExpireNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 417, Short.MAX_VALUE)
                .addComponent(btnStockExpireShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnStockExpirePaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnStockExpireNext, btnStockExpirePre});

        pnStockExpirePaginationLayout.setVerticalGroup(
            pnStockExpirePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpirePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockExpirePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStockExpirePre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockExpireNext)
                    .addComponent(txtStockExpirePage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStockExpireShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockExpirePaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockExpireNext, btnStockExpirePre, txtStockExpirePage});

        pnInputTbStockExpire.add(pnStockExpirePagination, java.awt.BorderLayout.PAGE_END);

        pnStockExpire.add(pnInputTbStockExpire, java.awt.BorderLayout.CENTER);

        scpStockExpireRight.setBorder(null);
        scpStockExpireRight.setPreferredSize(new java.awt.Dimension(325, 470));

        pnStockExpireRight.setPreferredSize(new java.awt.Dimension(310, 470));

        pnStockExpireManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " เตือนสินค้าหมดอายุ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnStockExpireManage.setPreferredSize(new java.awt.Dimension(310, 213));

        txtStockExpireNumDayWarning.setBackground(new java.awt.Color(255, 255, 0));
        txtStockExpireNumDayWarning.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockExpireNumDayWarning.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockExpireNumDayWarningFocusGained(evt);
            }
        });
        txtStockExpireNumDayWarning.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockExpireNumDayWarningKeyTyped(evt);
            }
        });

        lblStockExpireNumDayWarning.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireNumDayWarning.setText("เตือนก่อนหมดอายุ : ");

        btnStockExpireCancelWarning.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireCancelWarning.setText("ยกเลิกการเตือน");
        btnStockExpireCancelWarning.setPreferredSize(new java.awt.Dimension(51, 25));
        btnStockExpireCancelWarning.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireCancelWarningMouseClicked(evt);
            }
        });

        btnStockExpireCancelWarningAll.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireCancelWarningAll.setText("ยกเลิกการเตือนทั้งหมด");
        btnStockExpireCancelWarningAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireCancelWarningAllMouseClicked(evt);
            }
        });

        btnStockExpireWarningAll.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireWarningAll.setText("เตือนทั้งหมด");
        btnStockExpireWarningAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireWarningAllMouseClicked(evt);
            }
        });

        lblStockExpireDayWarning.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireDayWarning.setText("วัน");

        javax.swing.GroupLayout pnStockExpireManageLayout = new javax.swing.GroupLayout(pnStockExpireManage);
        pnStockExpireManage.setLayout(pnStockExpireManageLayout);
        pnStockExpireManageLayout.setHorizontalGroup(
            pnStockExpireManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireManageLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(pnStockExpireManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnStockExpireManageLayout.createSequentialGroup()
                        .addComponent(lblStockExpireNumDayWarning)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockExpireNumDayWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStockExpireDayWarning))
                    .addComponent(btnStockExpireWarningAll)
                    .addComponent(btnStockExpireCancelWarningAll)
                    .addComponent(btnStockExpireCancelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnStockExpireManageLayout.setVerticalGroup(
            pnStockExpireManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockExpireManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockExpireNumDayWarning)
                    .addComponent(txtStockExpireNumDayWarning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockExpireDayWarning))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockExpireCancelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockExpireCancelWarningAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStockExpireWarningAll)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pnStockExpireManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockExpireCancelWarning, btnStockExpireCancelWarningAll, btnStockExpireWarningAll, txtStockExpireNumDayWarning});

        pnStockExpireSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnStockExpireSearch.setPreferredSize(new java.awt.Dimension(310, 215));

        lbStockExpireSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbStockExpireSearch.setText("ค้นหา : ");

        lblStockExpireSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireSearchFrom.setText("ค้นหาจาก : ");

        lblStockExpireSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireSortBy.setText("เรียงโดย : ");

        lblStockExpireSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireSortForm.setText("เรียงจาก : ");

        lblStockExpireNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpireNumber.setText("จำนวน : ");

        lblStockExpirePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStockExpirePerPage.setText("รายการ / หน้า");

        txtStockExpireSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockExpireSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtStockExpireSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtStockExpireSearchFocusGained(evt);
            }
        });
        txtStockExpireSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockExpireSearchKeyReleased(evt);
            }
        });

        cbxStockExpireSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpireSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "จำนวนคงเหลือ(ต่ำสุด)", "วันหมดอายุ(เริ่มจาก)" }));
        cbxStockExpireSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpireSearchFromItemStateChanged(evt);
            }
        });

        cbxStockExpireSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpireSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อสินค้า", "เลขที่ล็อต", "จำนวนคงเหลือ", "วันหมดอายุ" }));
        cbxStockExpireSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpireSortByItemStateChanged(evt);
            }
        });

        cbxStockExpireSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpireSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxStockExpireSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpireSortFromItemStateChanged(evt);
            }
        });

        cbxStockExpirePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxStockExpirePerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxStockExpirePerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxStockExpirePerPageItemStateChanged(evt);
            }
        });

        btnStockExpireClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnStockExpireClearSearch.setText("ล้าง");
        btnStockExpireClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnStockExpireClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockExpireClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnStockExpireSearchLayout = new javax.swing.GroupLayout(pnStockExpireSearch);
        pnStockExpireSearch.setLayout(pnStockExpireSearchLayout);
        pnStockExpireSearchLayout.setHorizontalGroup(
            pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireSearchLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStockExpireSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockExpireSearchFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockExpireSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockExpireSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblStockExpireNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStockExpireClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockExpireSearchLayout.createSequentialGroup()
                        .addComponent(cbxStockExpirePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStockExpirePerPage))
                    .addComponent(cbxStockExpireSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockExpireSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxStockExpireSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)
                        .addComponent(cbxStockExpireSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        pnStockExpireSearchLayout.setVerticalGroup(
            pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbStockExpireSearch)
                    .addComponent(txtStockExpireSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockExpireSearchFrom)
                    .addComponent(cbxStockExpireSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockExpireSortBy)
                    .addComponent(cbxStockExpireSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockExpireSortForm)
                    .addComponent(cbxStockExpireSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnStockExpireSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockExpireNumber)
                    .addComponent(lblStockExpirePerPage)
                    .addComponent(cbxStockExpirePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStockExpireClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnStockExpireSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnStockExpireClearSearch, cbxStockExpirePerPage, cbxStockExpireSearchFrom, cbxStockExpireSortBy, cbxStockExpireSortFrom, txtStockExpireSearch});

        javax.swing.GroupLayout pnStockExpireRightLayout = new javax.swing.GroupLayout(pnStockExpireRight);
        pnStockExpireRight.setLayout(pnStockExpireRightLayout);
        pnStockExpireRightLayout.setHorizontalGroup(
            pnStockExpireRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireRightLayout.createSequentialGroup()
                .addGroup(pnStockExpireRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnStockExpireManage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnStockExpireSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnStockExpireRightLayout.setVerticalGroup(
            pnStockExpireRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStockExpireRightLayout.createSequentialGroup()
                .addComponent(pnStockExpireManage, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnStockExpireSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        scpStockExpireRight.setViewportView(pnStockExpireRight);

        pnStockExpire.add(scpStockExpireRight, java.awt.BorderLayout.LINE_END);

        tbpStockMain.addTab("ตรวจสอบสินค้าหมดอายุ", pnStockExpire);

        getContentPane().add(tbpStockMain, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStockAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockAmountFocusGained
        // TODO add your handling code here:
        txtStockAmount.selectAll();
    }//GEN-LAST:event_txtStockAmountFocusGained

    private void txtStockAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockAmountKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtStockAmountKeyTyped

    private void cbxStockProductNamePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxStockProductNamePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        setCbxProductName();
    }//GEN-LAST:event_cbxStockProductNamePopupMenuWillBecomeVisible

    private void btnStockInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockInsertMouseClicked
        // TODO add your handling code here:
        stockControl.insertData();
    }//GEN-LAST:event_btnStockInsertMouseClicked

    private void btnStockEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockEditMouseClicked
        // TODO add your handling code here:
        stockControl.updateData();
    }//GEN-LAST:event_btnStockEditMouseClicked

    private void btnStockClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(stockControl);
    }//GEN-LAST:event_btnStockClearMouseClicked

    private void txtStockSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockSearchFocusGained
        // TODO add your handling code here:
        txtStockSearch.selectAll();
    }//GEN-LAST:event_txtStockSearchFocusGained

    private void txtStockSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockSearchKeyReleased
        // TODO add your handling code here:
        stockControl.showData(true);
    }//GEN-LAST:event_txtStockSearchKeyReleased

    private void cbxStockSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockSearchFromItemStateChanged
        // TODO add your handling code here:
        stockControl.showData(true);
    }//GEN-LAST:event_cbxStockSearchFromItemStateChanged

    private void cbxStockSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockSortByItemStateChanged
        // TODO add your handling code here:
        stockControl.showData(true);
    }//GEN-LAST:event_cbxStockSortByItemStateChanged

    private void cbxStockSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockSortFromItemStateChanged
        // TODO add your handling code here:
        stockControl.showData(false);
    }//GEN-LAST:event_cbxStockSortFromItemStateChanged

    private void cbxStockPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockPerPageItemStateChanged
        // TODO add your handling code here:
        stockControl.showData(false);
    }//GEN-LAST:event_cbxStockPerPageItemStateChanged

    private void btnStockClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(stockControl);
    }//GEN-LAST:event_btnStockClearSearchMouseClicked

    private void btnStockNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", stockControl, btnStockNext, txtStockPage);
    }//GEN-LAST:event_btnStockNextMouseClicked

    private void btnStockPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", stockControl, btnStockPre, txtStockPage);
    }//GEN-LAST:event_btnStockPreMouseClicked

    private void txtStockPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockPageFocusGained
        // TODO add your handling code here:
        txtStockPage.nextFocus();
    }//GEN-LAST:event_txtStockPageFocusGained

    private void btnStockShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnStockSearch.isShowing()) {
            pnStockSearch.setVisible(true);
            btnStockShowSearch.setText("ปิดการค้นหา");
        } else {
            pnStockSearch.setVisible(false);
            btnStockShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnStockShowSearchMouseClicked

    private void cbxStockExpireYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpireYearItemStateChanged
        // TODO add your handling code here:
        setDateChange.setMonthByCurrYear();
        setDateChange.setDateByCurrMonth();
    }//GEN-LAST:event_cbxStockExpireYearItemStateChanged

    private void cbxStockExpireMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpireMonthItemStateChanged
        // TODO add your handling code here:
        setDateChange.setDateByCurrMonth();
    }//GEN-LAST:event_cbxStockExpireMonthItemStateChanged

    private void cbxStockProductNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockProductNameItemStateChanged
        // TODO add your handling code here:
        checkProductHasExpire();
    }//GEN-LAST:event_cbxStockProductNameItemStateChanged

    private void tbStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbStockMouseClicked
        // TODO add your handling code here:
        Util.tableToText(stockControl);
    }//GEN-LAST:event_tbStockMouseClicked

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
        MainHub.main.openStock = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnStockRemainNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockRemainNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", stockRemainControl, btnStockRemainNext, txtStockRemainPage);
    }//GEN-LAST:event_btnStockRemainNextMouseClicked

    private void btnStockRemainPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockRemainPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", stockRemainControl, btnStockRemainPre, txtStockRemainPage);
    }//GEN-LAST:event_btnStockRemainPreMouseClicked

    private void txtStockRemainPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockRemainPageFocusGained
        // TODO add your handling code here:
        txtStockRemainPage.nextFocus();
    }//GEN-LAST:event_txtStockRemainPageFocusGained

    private void txtStockRemainSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockRemainSearchFocusGained
        // TODO add your handling code here:
        txtStockRemainSearch.selectAll();
    }//GEN-LAST:event_txtStockRemainSearchFocusGained

    private void txtStockRemainSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockRemainSearchKeyReleased
        // TODO add your handling code here:
        stockRemainControl.showData(true);
    }//GEN-LAST:event_txtStockRemainSearchKeyReleased

    private void cbxStockRemainSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockRemainSearchFromItemStateChanged
        // TODO add your handling code here:
        stockRemainControl.showData(true);
    }//GEN-LAST:event_cbxStockRemainSearchFromItemStateChanged

    private void cbxStockRemainSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockRemainSortByItemStateChanged
        // TODO add your handling code here:
        stockRemainControl.showData(true);
    }//GEN-LAST:event_cbxStockRemainSortByItemStateChanged

    private void cbxStockRemainSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockRemainSortFromItemStateChanged
        // TODO add your handling code here:
        stockRemainControl.showData(false);
    }//GEN-LAST:event_cbxStockRemainSortFromItemStateChanged

    private void cbxStockRemainPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockRemainPerPageItemStateChanged
        // TODO add your handling code here:
        stockRemainControl.showData(false);
    }//GEN-LAST:event_cbxStockRemainPerPageItemStateChanged

    private void btnStockRemainClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockRemainClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(stockRemainControl);
    }//GEN-LAST:event_btnStockRemainClearSearchMouseClicked

    private void txtStockExpireNumDayWarningFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockExpireNumDayWarningFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockExpireNumDayWarningFocusGained

    private void txtStockExpireNumDayWarningKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockExpireNumDayWarningKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockExpireNumDayWarningKeyTyped

    private void btnStockExpireCancelWarningMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireCancelWarningMouseClicked
        // TODO add your handling code here:
        stockExpireControl.CancelWarning();
    }//GEN-LAST:event_btnStockExpireCancelWarningMouseClicked

    private void btnStockExpireCancelWarningAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireCancelWarningAllMouseClicked
        // TODO add your handling code here:
        stockExpireControl.cancelWarningAll();
    }//GEN-LAST:event_btnStockExpireCancelWarningAllMouseClicked

    private void btnStockExpireWarningAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireWarningAllMouseClicked
        // TODO add your handling code here:
        stockExpireControl.warningAll();
    }//GEN-LAST:event_btnStockExpireWarningAllMouseClicked

    private void txtStockExpireSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockExpireSearchFocusGained
        // TODO add your handling code here:
        txtStockExpireSearch.selectAll();
    }//GEN-LAST:event_txtStockExpireSearchFocusGained

    private void txtStockExpireSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockExpireSearchKeyReleased
        // TODO add your handling code here:
        stockExpireControl.showData(true);
    }//GEN-LAST:event_txtStockExpireSearchKeyReleased

    private void cbxStockExpireSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpireSearchFromItemStateChanged
        // TODO add your handling code here:
        stockExpireControl.showData(true);
    }//GEN-LAST:event_cbxStockExpireSearchFromItemStateChanged

    private void cbxStockExpireSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpireSortByItemStateChanged
        // TODO add your handling code here:
        stockExpireControl.showData(true);
    }//GEN-LAST:event_cbxStockExpireSortByItemStateChanged

    private void cbxStockExpireSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpireSortFromItemStateChanged
        // TODO add your handling code here:
        stockExpireControl.showData(false);
    }//GEN-LAST:event_cbxStockExpireSortFromItemStateChanged

    private void cbxStockExpirePerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxStockExpirePerPageItemStateChanged
        // TODO add your handling code here:
        stockExpireControl.showData(false);
    }//GEN-LAST:event_cbxStockExpirePerPageItemStateChanged

    private void btnStockExpireClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireClearSearchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStockExpireClearSearchMouseClicked

    private void btnStockExpireNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", stockExpireControl, btnStockExpirePre, txtStockExpirePage);
    }//GEN-LAST:event_btnStockExpireNextMouseClicked

    private void btnStockExpirePreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpirePreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", stockExpireControl, btnStockExpirePre, txtStockExpirePage);
    }//GEN-LAST:event_btnStockExpirePreMouseClicked

    private void txtStockExpirePageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockExpirePageFocusGained
        // TODO add your handling code here:
        txtStockExpirePage.nextFocus();
    }//GEN-LAST:event_txtStockExpirePageFocusGained

    private void btnStockExpireShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockExpireShowSearchMouseClicked
        // TODO add your handling code here:
         if (!pnStockExpireSearch.isShowing()) {
            pnStockExpireSearch.setVisible(true);
            btnStockExpireShowSearch.setText("ปิดการค้นหา");
        } else {
            pnStockExpireSearch.setVisible(false);
            btnStockExpireShowSearch.setText("เปิดการค้นหา");
        }
        
    }//GEN-LAST:event_btnStockExpireShowSearchMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStockClear;
    private javax.swing.JButton btnStockClearSearch;
    private javax.swing.JButton btnStockEdit;
    private javax.swing.JButton btnStockExpireCancelWarning;
    private javax.swing.JButton btnStockExpireCancelWarningAll;
    private javax.swing.JButton btnStockExpireClearSearch;
    private javax.swing.JButton btnStockExpireNext;
    private javax.swing.JButton btnStockExpirePre;
    private javax.swing.JButton btnStockExpireShowSearch;
    private javax.swing.JButton btnStockExpireWarningAll;
    private javax.swing.JButton btnStockInsert;
    private javax.swing.JButton btnStockNext;
    private javax.swing.JButton btnStockPre;
    private javax.swing.JButton btnStockRemainClearSearch;
    private javax.swing.JButton btnStockRemainNext;
    private javax.swing.JButton btnStockRemainPre;
    private javax.swing.JButton btnStockShowSearch;
    private javax.swing.JComboBox cbxStockExpireDate;
    private javax.swing.JComboBox cbxStockExpireMonth;
    private javax.swing.JComboBox cbxStockExpirePerPage;
    private javax.swing.JComboBox cbxStockExpireSearchFrom;
    private javax.swing.JComboBox cbxStockExpireSortBy;
    private javax.swing.JComboBox cbxStockExpireSortFrom;
    private javax.swing.JComboBox cbxStockExpireYear;
    private javax.swing.JComboBox cbxStockPerPage;
    private javax.swing.JComboBox cbxStockProductName;
    private javax.swing.JComboBox cbxStockRemainPerPage;
    private javax.swing.JComboBox cbxStockRemainSearchFrom;
    private javax.swing.JComboBox cbxStockRemainSortBy;
    private javax.swing.JComboBox cbxStockRemainSortFrom;
    private javax.swing.JComboBox cbxStockSearchFrom;
    private javax.swing.JComboBox cbxStockSortBy;
    private javax.swing.JComboBox cbxStockSortFrom;
    private javax.swing.JLabel lbStockExpireSearch;
    private javax.swing.JLabel lbStockRemainSearch;
    private javax.swing.JLabel lbStockSearch;
    private javax.swing.JLabel lblStockAmount;
    private javax.swing.JLabel lblStockExpire;
    private javax.swing.JLabel lblStockExpireDayWarning;
    private javax.swing.JLabel lblStockExpireNumDayWarning;
    private javax.swing.JLabel lblStockExpireNumber;
    private javax.swing.JLabel lblStockExpirePerPage;
    private javax.swing.JLabel lblStockExpireSearchFrom;
    private javax.swing.JLabel lblStockExpireSortBy;
    private javax.swing.JLabel lblStockExpireSortForm;
    private javax.swing.JLabel lblStockNumber;
    private javax.swing.JLabel lblStockPerPage;
    private javax.swing.JLabel lblStockProductName;
    private javax.swing.JLabel lblStockRemainNumber;
    private javax.swing.JLabel lblStockRemainPerPage;
    private javax.swing.JLabel lblStockRemainSearchFrom;
    private javax.swing.JLabel lblStockRemainSortBy;
    private javax.swing.JLabel lblStockRemainSortForm;
    private javax.swing.JLabel lblStockSearchFrom;
    private javax.swing.JLabel lblStockSortBy;
    private javax.swing.JLabel lblStockSortForm;
    private javax.swing.JPanel pbInputTbStockRemain;
    private javax.swing.JPanel pnInputTbStock;
    private javax.swing.JPanel pnInputTbStockExpire;
    private javax.swing.JPanel pnStock;
    private javax.swing.JPanel pnStockExpire;
    private javax.swing.JPanel pnStockExpireManage;
    private javax.swing.JPanel pnStockExpirePagination;
    private javax.swing.JPanel pnStockExpireRight;
    private javax.swing.JPanel pnStockExpireSearch;
    private javax.swing.JPanel pnStockManage;
    private javax.swing.JPanel pnStockPagination;
    private javax.swing.JPanel pnStockRemain;
    private javax.swing.JPanel pnStockRemainPagination;
    private javax.swing.JPanel pnStockRemainRight;
    private javax.swing.JPanel pnStockRemainSearch;
    private javax.swing.JPanel pnStockRight;
    private javax.swing.JPanel pnStockSearch;
    private javax.swing.JScrollPane scpStockExpireRight;
    private javax.swing.JScrollPane scpStockRemainRight;
    private javax.swing.JScrollPane scpStockRight;
    private javax.swing.JScrollPane scpTableStock;
    private javax.swing.JScrollPane scpTableStockExpire;
    private javax.swing.JScrollPane scpTableStockRemain;
    private javax.swing.JTable tbStock;
    private javax.swing.JTable tbStockExpire;
    private javax.swing.JTable tbStockRemain;
    private javax.swing.JTabbedPane tbpStockMain;
    private javax.swing.JTextField txtStockAmount;
    private javax.swing.JTextField txtStockExpireNumDayWarning;
    private javax.swing.JTextField txtStockExpirePage;
    private javax.swing.JTextField txtStockExpireSearch;
    private javax.swing.JTextField txtStockPage;
    private javax.swing.JTextField txtStockRemainPage;
    private javax.swing.JTextField txtStockRemainSearch;
    private javax.swing.JTextField txtStockSearch;
    // End of variables declaration//GEN-END:variables

    private void setCbxProductName() {
        
        Vector<String> v = new Vector<>();

        String sql = "SELECT DISTINCT(productName) FROM tbproduct";
    try {        
        ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
        
        while(rs.next()) {
            
            v.add(rs.getString(1));
            
        }
        
        cbxStockProductName.setModel(new DefaultComboBoxModel(v));
        
    } catch (SQLException ex) {
        Logger.getLogger(FormStock.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
    }
    
    private void checkProductHasExpire() {
        
        int expire = 0;
        
        String sql = "SELECT productExpire FROM tbproduct WHERE productName = '"+cbxStockProductName.getSelectedItem().toString()+"'";
        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);
            
            while(rs.next()) {
                
                expire = rs.getInt(1);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(FormStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (expire == 0) {
            cbxStockExpireYear.setEnabled(false);
            cbxStockExpireMonth.setEnabled(false);
            cbxStockExpireDate.setEnabled(false);
        } else {
            cbxStockExpireYear.setEnabled(true);
            cbxStockExpireMonth.setEnabled(true);
            cbxStockExpireDate.setEnabled(true);
        }
        
    }
    
}
