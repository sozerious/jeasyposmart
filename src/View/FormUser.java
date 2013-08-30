/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.EmployeeControl;
import Control.UserControl;
import Model.Hub.MainHub;
import Model.Hub.UserHub;
import Utility.DBFactory;
import Utility.JTextFieldLimit;
import Utility.MyPasswordCellRenderer;
import Utility.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author JAVA DEV
 */
public class FormUser extends javax.swing.JInternalFrame {

    UserControl userControl;
    EmployeeControl employeeControl;
    Vector<String> v;
    /**
     * Creates new form FormRegister
     */
    private DefaultTableModel modelUser, modelEmployee;
    //======================== vector
    public static Vector vectorUser, vectorUserInverse;
    public static Vector vectorEmployee, vectorEmployeeInverse;
    private JComboBox<String> combo;

    public FormUser() {
        initComponents();
        UserHub.setUser(this);
        v = new Vector();

        setCbxEmployeeName();

        userControl = new UserControl();
        employeeControl = new EmployeeControl();

        vectorUser = new Vector();
        vectorUserInverse = new Vector();
        vectorEmployee = new Vector();
        vectorEmployeeInverse = new Vector();

        modelUser = (DefaultTableModel) tbUser.getModel();
        modelEmployee = (DefaultTableModel) tbEmployee.getModel();

        tbUser.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        TableColumn column = tbUser.getColumnModel().getColumn(4);
        column.setCellRenderer(new MyPasswordCellRenderer());
        tbEmployee.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtEmployeeIdCardNumber.setDocument(new JTextFieldLimit(13));
        txtEmployeeTel.setDocument(new JTextFieldLimit(10));

        combo = new JComboBox<>(new String[]{"นาย", "นาง", "นางสาว"});

        //<editor-fold defaultstate="collapsed" desc="Combo Listener">
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                cbxEmployeePrefix.setSelectedItem(combo.getSelectedItem());

            }
        });

        combo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                int rowSel = tbEmployee.getSelectedRow();

                UserHub.user.getTxtEmployeeFirstname().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 2).toString());
                UserHub.user.getTxtEmployeeLastname().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 3).toString());
                UserHub.user.getCbxEmployeePosition().setSelectedItem(UserHub.user.getTbEmployee().getValueAt(rowSel, 4));
                UserHub.user.getTxtEmployeeIdCardNumber().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 5).toString());
                UserHub.user.getTxtEmployeeAddress().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 6).toString());
                UserHub.user.getTxtEmployeeTel().setText(UserHub.user.getTbEmployee().getValueAt(rowSel, 7).toString());

            }
        });

        //</editor-fold>

        tbEmployee.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(combo));

        userControl.showData(true);
        employeeControl.showData(true);

        if (FormMain.user.getUserType().equals("Admin")) {
            rdbAdminType.setEnabled(false);
//            btnUserDelete.setEnabled(false);
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Getter User">
    public JTabbedPane getTbpRegisterMain() {
        return tbpRegisterMain;
    }

    public JButton getBtnUserDelete() {
        return btnUserDelete;
    }

    public DefaultTableModel getModelUser() {
        return modelUser;
    }

    public JButton getBtnUserNext() {
        return btnUserNext;
    }

    public JButton getBtnUserPre() {
        return btnUserPre;
    }

    public JComboBox getCbxEmployeeName() {
        return cbxEmployeeName;
    }

    public JComboBox getCbxUserPerPage() {
        return cbxUserPerPage;
    }

    public JComboBox getCbxUserSearchFrom() {
        return cbxUserSearchFrom;
    }

    public JComboBox getCbxUserSortBy() {
        return cbxUserSortBy;
    }

    public JComboBox getCbxUserSortFrom() {
        return cbxUserSortFrom;
    }

    public JRadioButton getRdbAdminType() {
        return rdbAdminType;
    }

    public JRadioButton getRdbUserType() {
        return rdbUserType;
    }

    public JTable getTbUser() {
        return tbUser;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JTextField getTxtUserPage() {
        return txtUserPage;
    }

    public JTextField getTxtUserSearch() {
        return txtUserSearch;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtPasswordAgain() {
        return txtPasswordAgain;
    }

    public JLabel getLblCheckPassword() {
        return lblCheckPassword;
    }

    public UserControl getUserControl() {
        return userControl;
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter Employee">
    public JComboBox getCbxEmployeePerPage() {
        return cbxEmployeePerPage;
    }

    public JComboBox getCbxEmployeePosition() {
        return cbxEmployeePosition;
    }

    public JComboBox getCbxEmployeePrefix() {
        return cbxEmployeePrefix;
    }

    public JComboBox getCbxEmployeeSearchFrom() {
        return cbxEmployeeSearchFrom;
    }

    public JComboBox getCbxEmployeeSortBy() {
        return cbxEmployeeSortBy;
    }

    public JComboBox getCbxEmployeeSortFrom() {
        return cbxEmployeeSortFrom;
    }

    public DefaultTableModel getModelEmployee() {
        return modelEmployee;
    }

    public JTable getTbEmployee() {
        return tbEmployee;
    }

    public JTextField getTxtEmployeeAddress() {
        return txtEmployeeAddress;
    }

    public JTextField getTxtEmployeeFirstname() {
        return txtEmployeeFirstname;
    }

    public JTextField getTxtEmployeeIdCardNumber() {
        return txtEmployeeIdCardNumber;
    }

    public JTextField getTxtEmployeeLastname() {
        return txtEmployeeLastname;
    }

    public JTextField getTxtEmployeePage() {
        return txtEmployeePage;
    }

    public JTextField getTxtEmployeeSearch() {
        return txtEmployeeSearch;
    }

    public JTextField getTxtEmployeeTel() {
        return txtEmployeeTel;
    }

    public JButton getBtnEmployeeNext() {
        return btnEmployeeNext;
    }

    public JButton getBtnEmployeePre() {
        return btnEmployeePre;
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

        btngUserType = new javax.swing.ButtonGroup();
        tbpRegisterMain = new javax.swing.JTabbedPane();
        pnUserMain = new javax.swing.JPanel();
        pnInputTbUser = new javax.swing.JPanel();
        scpUser = new javax.swing.JScrollPane();
        tbUser = new javax.swing.JTable();
        pnUserPagination = new javax.swing.JPanel();
        btnUserShowSearch = new javax.swing.JButton();
        btnUserNext = new javax.swing.JButton();
        btnUserPre = new javax.swing.JButton();
        txtUserPage = new javax.swing.JTextField();
        scpUserRight = new javax.swing.JScrollPane();
        pnUserRight = new javax.swing.JPanel();
        pnUserManage = new javax.swing.JPanel();
        lblPassword = new javax.swing.JLabel();
        lblEmployeename = new javax.swing.JLabel();
        rdbUserType = new javax.swing.JRadioButton();
        txtPassword = new javax.swing.JPasswordField();
        btnUser_AddEmplyee = new javax.swing.JButton();
        lblUserType = new javax.swing.JLabel();
        cbxEmployeeName = new javax.swing.JComboBox();
        lblUsername = new javax.swing.JLabel();
        rdbAdminType = new javax.swing.JRadioButton();
        txtUsername = new javax.swing.JTextField();
        btnUserAdd = new javax.swing.JButton();
        btnUserUpdate = new javax.swing.JButton();
        btnUserDelete = new javax.swing.JButton();
        btnUserClear = new javax.swing.JButton();
        lblPasswordAgian = new javax.swing.JLabel();
        txtPasswordAgain = new javax.swing.JPasswordField();
        lblCheckPassword = new javax.swing.JLabel();
        pnUserSearch = new javax.swing.JPanel();
        lblUserSearch = new javax.swing.JLabel();
        lblUserSearchForm = new javax.swing.JLabel();
        lblUserSortBy = new javax.swing.JLabel();
        lblUserSortForm = new javax.swing.JLabel();
        lblUserNumber = new javax.swing.JLabel();
        lblUserPerPage = new javax.swing.JLabel();
        txtUserSearch = new javax.swing.JTextField();
        cbxUserSearchFrom = new javax.swing.JComboBox();
        cbxUserSortBy = new javax.swing.JComboBox();
        cbxUserSortFrom = new javax.swing.JComboBox();
        cbxUserPerPage = new javax.swing.JComboBox();
        btnUserClearSearch = new javax.swing.JButton();
        pnEmployeeMain = new javax.swing.JPanel();
        pnInputTbEmployee = new javax.swing.JPanel();
        scpEmployee = new javax.swing.JScrollPane();
        tbEmployee = new javax.swing.JTable();
        pnEmployeePagination = new javax.swing.JPanel();
        btnEmployeeShowSearch = new javax.swing.JButton();
        btnEmployeeNext = new javax.swing.JButton();
        btnEmployeePre = new javax.swing.JButton();
        txtEmployeePage = new javax.swing.JTextField();
        scpEmployeeRight = new javax.swing.JScrollPane();
        pnEmployeeRight = new javax.swing.JPanel();
        pnEmployeeManage = new javax.swing.JPanel();
        lblEmployeeLastname = new javax.swing.JLabel();
        lblEmployeePrefix = new javax.swing.JLabel();
        cbxEmployeePrefix = new javax.swing.JComboBox();
        lblEmployeeFirstname = new javax.swing.JLabel();
        txtEmployeeFirstname = new javax.swing.JTextField();
        btnEmployeeAdd = new javax.swing.JButton();
        btnEmployeeUpdate = new javax.swing.JButton();
        btnEmployeeDelete = new javax.swing.JButton();
        btnEmployeeClear = new javax.swing.JButton();
        lblEmployeePosition = new javax.swing.JLabel();
        txtEmployeeLastname = new javax.swing.JTextField();
        cbxEmployeePosition = new javax.swing.JComboBox();
        txtEmployeeIdCardNumber = new javax.swing.JTextField();
        lblEmployeeIdCardNumber = new javax.swing.JLabel();
        lblEmployeeAddress = new javax.swing.JLabel();
        txtEmployeeTel = new javax.swing.JTextField();
        lblEmployeeTel = new javax.swing.JLabel();
        txtEmployeeAddress = new javax.swing.JTextField();
        pnEmployeeSearch = new javax.swing.JPanel();
        lblEmployeeSearch = new javax.swing.JLabel();
        lblEmployeeSearchForm = new javax.swing.JLabel();
        lblEmployeeSortBy = new javax.swing.JLabel();
        lblEmployeeSortForm = new javax.swing.JLabel();
        lblEmployeeNumber = new javax.swing.JLabel();
        lblEmployeePerPage = new javax.swing.JLabel();
        txtEmployeeSearch = new javax.swing.JTextField();
        cbxEmployeeSearchFrom = new javax.swing.JComboBox();
        cbxEmployeeSortBy = new javax.swing.JComboBox();
        cbxEmployeeSortFrom = new javax.swing.JComboBox();
        cbxEmployeePerPage = new javax.swing.JComboBox();
        btnEmployeeClearSearch = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("จัดการผู้ใช้ระบบ / พนักงาน");
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        setMinimumSize(new java.awt.Dimension(950, 450));
        setPreferredSize(new java.awt.Dimension(950, 450));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        tbpRegisterMain.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        pnUserMain.setPreferredSize(new java.awt.Dimension(500, 175));
        pnUserMain.setLayout(new java.awt.BorderLayout());

        pnInputTbUser.setLayout(new java.awt.BorderLayout());

        tbUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "หมายเลขผู้ใช้", "ชื่อพนักงาน", "นามสกุล", "ชื่อผู้ใช้", "รหัสผ่าน", "ประเภทผู้ใช้"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUser.setRowHeight(25);
        tbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUserMouseClicked(evt);
            }
        });
        scpUser.setViewportView(tbUser);
        tbUser.getColumnModel().getColumn(0).setPreferredWidth(70);
        tbUser.getColumnModel().getColumn(1).setPreferredWidth(150);
        tbUser.getColumnModel().getColumn(2).setPreferredWidth(150);
        tbUser.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbUser.getColumnModel().getColumn(4).setPreferredWidth(150);
        tbUser.getColumnModel().getColumn(5).setPreferredWidth(100);

        pnInputTbUser.add(scpUser, java.awt.BorderLayout.CENTER);

        pnUserPagination.setPreferredSize(new java.awt.Dimension(517, 50));

        btnUserShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserShowSearch.setText("ปิดการค้นหา");
        btnUserShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnUserShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserShowSearchMouseClicked(evt);
            }
        });

        btnUserNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserNext.setText("ถัดไป >>");
        btnUserNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserNextMouseClicked(evt);
            }
        });

        btnUserPre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserPre.setText("<< ก่อนหน้า");
        btnUserPre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnUserPre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserPreMouseClicked(evt);
            }
        });

        txtUserPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUserPage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUserPage.setText("1");
        txtUserPage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtUserPage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserPageFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnUserPaginationLayout = new javax.swing.GroupLayout(pnUserPagination);
        pnUserPagination.setLayout(pnUserPaginationLayout);
        pnUserPaginationLayout.setHorizontalGroup(
            pnUserPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnUserPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUserPre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserPage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(btnUserShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnUserPaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUserNext, btnUserPre});

        pnUserPaginationLayout.setVerticalGroup(
            pnUserPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserPaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnUserPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnUserPaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUserPre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUserNext)
                        .addComponent(txtUserPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUserShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnUserPaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUserNext, btnUserPre, txtUserPage});

        pnInputTbUser.add(pnUserPagination, java.awt.BorderLayout.PAGE_END);

        pnUserMain.add(pnInputTbUser, java.awt.BorderLayout.CENTER);

        scpUserRight.setBorder(null);
        scpUserRight.setPreferredSize(new java.awt.Dimension(375, 498));

        pnUserRight.setPreferredSize(new java.awt.Dimension(360, 498));

        pnUserManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการผู้ใช้ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnUserManage.setPreferredSize(new java.awt.Dimension(360, 221));

        lblPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPassword.setText("รหัสผ่าน : ");

        lblEmployeename.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeename.setText("ชื่อพนักงาน : ");

        btngUserType.add(rdbUserType);
        rdbUserType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdbUserType.setSelected(true);
        rdbUserType.setText("User");
        rdbUserType.setName("User"); // NOI18N

        txtPassword.setBackground(new java.awt.Color(255, 255, 0));
        txtPassword.setPreferredSize(new java.awt.Dimension(144, 25));
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordKeyReleased(evt);
            }
        });

        btnUser_AddEmplyee.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUser_AddEmplyee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_add.png"))); // NOI18N
        btnUser_AddEmplyee.setText("เพิ่มพนักงาน");
        btnUser_AddEmplyee.setPreferredSize(new java.awt.Dimension(89, 25));
        btnUser_AddEmplyee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUser_AddEmplyeeMouseClicked(evt);
            }
        });

        lblUserType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserType.setText("ระดับ : ");

        cbxEmployeeName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeeName.setPreferredSize(new java.awt.Dimension(30, 25));
        cbxEmployeeName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxEmployeeNamePopupMenuWillBecomeVisible(evt);
            }
        });

        lblUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUsername.setText("ชื่อผู้ใช้ : ");

        btngUserType.add(rdbAdminType);
        rdbAdminType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdbAdminType.setText("Admin");
        rdbAdminType.setName("Admin"); // NOI18N

        txtUsername.setBackground(new java.awt.Color(255, 255, 0));
        txtUsername.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsernameFocusGained(evt);
            }
        });

        btnUserAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserAdd.setText("เพิ่ม");
        btnUserAdd.setPreferredSize(new java.awt.Dimension(51, 25));
        btnUserAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserAddMouseClicked(evt);
            }
        });

        btnUserUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserUpdate.setText("แก้ไข");
        btnUserUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserUpdateMouseClicked(evt);
            }
        });

        btnUserDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserDelete.setText("ลบ");
        btnUserDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserDeleteMouseClicked(evt);
            }
        });

        btnUserClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserClear.setText("ล้าง");
        btnUserClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserClearMouseClicked(evt);
            }
        });

        lblPasswordAgian.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPasswordAgian.setText("รหัสผ่าน : ");

        txtPasswordAgain.setBackground(new java.awt.Color(255, 255, 0));
        txtPasswordAgain.setPreferredSize(new java.awt.Dimension(144, 25));
        txtPasswordAgain.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordAgainFocusGained(evt);
            }
        });
        txtPasswordAgain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPasswordAgainKeyReleased(evt);
            }
        });

        lblCheckPassword.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCheckPassword.setForeground(new java.awt.Color(255, 153, 0));
        lblCheckPassword.setText("ตรวจสอบรหัสผ่าน");

        javax.swing.GroupLayout pnUserManageLayout = new javax.swing.GroupLayout(pnUserManage);
        pnUserManage.setLayout(pnUserManageLayout);
        pnUserManageLayout.setHorizontalGroup(
            pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserManageLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnUserManageLayout.createSequentialGroup()
                        .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmployeename, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPasswordAgian, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUserType, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnUserManageLayout.createSequentialGroup()
                                .addComponent(rdbAdminType)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdbUserType))
                            .addComponent(txtPasswordAgain, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(txtUsername)
                            .addComponent(cbxEmployeeName, 0, 130, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUser_AddEmplyee, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCheckPassword)))
                    .addGroup(pnUserManageLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnUserAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUserUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUserDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUserClear)))
                .addGap(55, 55, 55))
        );

        pnUserManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUserAdd, btnUserClear, btnUserDelete, btnUserUpdate});

        pnUserManageLayout.setVerticalGroup(
            pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeename)
                    .addComponent(cbxEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUser_AddEmplyee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPasswordAgian)
                    .addComponent(txtPasswordAgain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheckPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserType)
                    .addComponent(rdbAdminType)
                    .addComponent(rdbUserType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnUserManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUserAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserUpdate)
                    .addComponent(btnUserDelete)
                    .addComponent(btnUserClear))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pnUserManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUser_AddEmplyee, cbxEmployeeName, txtPassword, txtPasswordAgain, txtUsername});

        pnUserManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUserAdd, btnUserClear, btnUserDelete, btnUserUpdate});

        pnUserSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnUserSearch.setPreferredSize(new java.awt.Dimension(360, 226));

        lblUserSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserSearch.setText("ค้นหา : ");

        lblUserSearchForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserSearchForm.setText("ค้นหาจาก : ");

        lblUserSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserSortBy.setText("เรียงโดย : ");

        lblUserSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserSortForm.setText("เรียงจาก : ");

        lblUserNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserNumber.setText("จำนวน : ");

        lblUserPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblUserPerPage.setText("รายการ / หน้า");

        txtUserSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUserSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtUserSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserSearchFocusGained(evt);
            }
        });
        txtUserSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUserSearchKeyReleased(evt);
            }
        });

        cbxUserSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxUserSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อพนักงาน", "นามสกุล", "ชื่อผู้ใช้" }));
        cbxUserSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUserSearchFromItemStateChanged(evt);
            }
        });

        cbxUserSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxUserSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "หมายเลขผู้ใช้", "ชื่อพนักงาน", "นามสกุล", "ชื่อผู้ใช้" }));
        cbxUserSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUserSortByItemStateChanged(evt);
            }
        });

        cbxUserSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxUserSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxUserSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUserSortFromItemStateChanged(evt);
            }
        });

        cbxUserPerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxUserPerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxUserPerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxUserPerPageItemStateChanged(evt);
            }
        });

        btnUserClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUserClearSearch.setText("ล้าง");
        btnUserClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnUserClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnUserSearchLayout = new javax.swing.GroupLayout(pnUserSearch);
        pnUserSearch.setLayout(pnUserSearchLayout);
        pnUserSearchLayout.setHorizontalGroup(
            pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserSearchLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUserSearch, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserSearchForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserSortBy, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserSortForm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUserNumber, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUserClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnUserSearchLayout.createSequentialGroup()
                        .addComponent(cbxUserPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUserPerPage))
                    .addComponent(cbxUserSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxUserSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)
                        .addComponent(cbxUserSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtUserSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnUserSearchLayout.setVerticalGroup(
            pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserSearch)
                    .addComponent(txtUserSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserSearchForm)
                    .addComponent(cbxUserSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserSortBy)
                    .addComponent(cbxUserSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserSortForm)
                    .addComponent(cbxUserSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnUserSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserNumber)
                    .addComponent(lblUserPerPage)
                    .addComponent(cbxUserPerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUserClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnUserSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnUserClearSearch, cbxUserPerPage, cbxUserSearchFrom, cbxUserSortBy, cbxUserSortFrom, txtUserSearch});

        javax.swing.GroupLayout pnUserRightLayout = new javax.swing.GroupLayout(pnUserRight);
        pnUserRight.setLayout(pnUserRightLayout);
        pnUserRightLayout.setHorizontalGroup(
            pnUserRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserRightLayout.createSequentialGroup()
                .addGroup(pnUserRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnUserManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnUserSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        pnUserRightLayout.setVerticalGroup(
            pnUserRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserRightLayout.createSequentialGroup()
                .addComponent(pnUserManage, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnUserSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scpUserRight.setViewportView(pnUserRight);

        pnUserMain.add(scpUserRight, java.awt.BorderLayout.LINE_END);

        tbpRegisterMain.addTab("จัดการผู้ใช้ระบบ", pnUserMain);

        pnEmployeeMain.setLayout(new java.awt.BorderLayout());

        pnInputTbEmployee.setLayout(new java.awt.BorderLayout());

        tbEmployee.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "หมายเลขหนักงาน", "คำนำหน้า", "ชื่อพนักงาน", "นามสกุล", "ตำแหน่ง", "รหัสบัตรประชาชน", "ที่อยู่", "เบอร์โทรศัพท์"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbEmployee.setRowHeight(25);
        tbEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEmployeeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbEmployeeMousePressed(evt);
            }
        });
        scpEmployee.setViewportView(tbEmployee);
        tbEmployee.getColumnModel().getColumn(0).setPreferredWidth(70);
        tbEmployee.getColumnModel().getColumn(1).setPreferredWidth(70);
        tbEmployee.getColumnModel().getColumn(2).setPreferredWidth(150);
        tbEmployee.getColumnModel().getColumn(3).setPreferredWidth(150);
        tbEmployee.getColumnModel().getColumn(4).setPreferredWidth(100);
        tbEmployee.getColumnModel().getColumn(5).setPreferredWidth(150);
        tbEmployee.getColumnModel().getColumn(6).setPreferredWidth(250);
        tbEmployee.getColumnModel().getColumn(7).setPreferredWidth(100);

        pnInputTbEmployee.add(scpEmployee, java.awt.BorderLayout.CENTER);

        pnEmployeePagination.setPreferredSize(new java.awt.Dimension(517, 50));

        btnEmployeeShowSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeShowSearch.setText("ปิดการค้นหา");
        btnEmployeeShowSearch.setPreferredSize(new java.awt.Dimension(97, 25));
        btnEmployeeShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeShowSearchMouseClicked(evt);
            }
        });

        btnEmployeeNext.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeNext.setText("ถัดไป >>");
        btnEmployeeNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeNextMouseClicked(evt);
            }
        });

        btnEmployeePre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeePre.setText("<< ก่อนหน้า");
        btnEmployeePre.setPreferredSize(new java.awt.Dimension(73, 30));
        btnEmployeePre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeePreMouseClicked(evt);
            }
        });

        txtEmployeePage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeePage.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmployeePage.setText("1");
        txtEmployeePage.setPreferredSize(new java.awt.Dimension(40, 20));
        txtEmployeePage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeePageFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnEmployeePaginationLayout = new javax.swing.GroupLayout(pnEmployeePagination);
        pnEmployeePagination.setLayout(pnEmployeePaginationLayout);
        pnEmployeePaginationLayout.setHorizontalGroup(
            pnEmployeePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnEmployeePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEmployeePre, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmployeePage, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEmployeeNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(btnEmployeeShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnEmployeePaginationLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEmployeeNext, btnEmployeePre});

        pnEmployeePaginationLayout.setVerticalGroup(
            pnEmployeePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeePaginationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnEmployeePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnEmployeePaginationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEmployeePre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEmployeeNext)
                        .addComponent(txtEmployeePage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEmployeeShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnEmployeePaginationLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEmployeeNext, btnEmployeePre, txtEmployeePage});

        pnInputTbEmployee.add(pnEmployeePagination, java.awt.BorderLayout.PAGE_END);

        pnEmployeeMain.add(pnInputTbEmployee, java.awt.BorderLayout.CENTER);

        scpEmployeeRight.setBorder(null);
        scpEmployeeRight.setPreferredSize(new java.awt.Dimension(435, 526));

        pnEmployeeRight.setPreferredSize(new java.awt.Dimension(420, 526));

        pnEmployeeManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " จัดการพนักงาน ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        pnEmployeeManage.setPreferredSize(new java.awt.Dimension(420, 293));

        lblEmployeeLastname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeLastname.setText("นามสกุล : ");

        lblEmployeePrefix.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeePrefix.setText("คำนำหน้า : ");

        cbxEmployeePrefix.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeePrefix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "นาย", "นาง", "นางสาว" }));
        cbxEmployeePrefix.setPreferredSize(new java.awt.Dimension(30, 25));
        cbxEmployeePrefix.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxEmployeePrefixPopupMenuWillBecomeVisible(evt);
            }
        });

        lblEmployeeFirstname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeFirstname.setText("ชื่อพนักงาน : ");

        txtEmployeeFirstname.setBackground(new java.awt.Color(255, 255, 0));
        txtEmployeeFirstname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeFirstname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeFirstnameFocusGained(evt);
            }
        });

        btnEmployeeAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeAdd.setText("เพิ่ม");
        btnEmployeeAdd.setPreferredSize(new java.awt.Dimension(51, 25));
        btnEmployeeAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeAddMouseClicked(evt);
            }
        });

        btnEmployeeUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeUpdate.setText("แก้ไข");
        btnEmployeeUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeUpdateMouseClicked(evt);
            }
        });

        btnEmployeeDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeDelete.setText("ลบ");
        btnEmployeeDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeDeleteMouseClicked(evt);
            }
        });

        btnEmployeeClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeClear.setText("ล้าง");
        btnEmployeeClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeClearMouseClicked(evt);
            }
        });

        lblEmployeePosition.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeePosition.setText("ตำแหน่งงาน : ");

        txtEmployeeLastname.setBackground(new java.awt.Color(255, 255, 0));
        txtEmployeeLastname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeLastname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeLastnameFocusGained(evt);
            }
        });

        cbxEmployeePosition.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeePosition.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ผู้จัดการ", "พนักงานขาย" }));
        cbxEmployeePosition.setPreferredSize(new java.awt.Dimension(30, 25));
        cbxEmployeePosition.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                cbxEmployeePositionPopupMenuWillBecomeVisible(evt);
            }
        });

        txtEmployeeIdCardNumber.setBackground(new java.awt.Color(51, 204, 0));
        txtEmployeeIdCardNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeIdCardNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeIdCardNumberFocusGained(evt);
            }
        });
        txtEmployeeIdCardNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmployeeIdCardNumberKeyTyped(evt);
            }
        });

        lblEmployeeIdCardNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeIdCardNumber.setText("เลขบัตรประชาชน : ");

        lblEmployeeAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeAddress.setText("ืที่อยู่ : ");

        txtEmployeeTel.setBackground(new java.awt.Color(51, 204, 0));
        txtEmployeeTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeTel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeTelFocusGained(evt);
            }
        });
        txtEmployeeTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmployeeTelKeyTyped(evt);
            }
        });

        lblEmployeeTel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeTel.setText("เบอร์โทรศัพท์ : ");

        txtEmployeeAddress.setBackground(new java.awt.Color(51, 204, 0));
        txtEmployeeAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeAddressFocusGained(evt);
            }
        });

        javax.swing.GroupLayout pnEmployeeManageLayout = new javax.swing.GroupLayout(pnEmployeeManage);
        pnEmployeeManage.setLayout(pnEmployeeManageLayout);
        pnEmployeeManageLayout.setHorizontalGroup(
            pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmployeePrefix)
                            .addComponent(lblEmployeeLastname)
                            .addComponent(lblEmployeeFirstname)
                            .addComponent(lblEmployeePosition)
                            .addComponent(lblEmployeeIdCardNumber)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnEmployeeManageLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblEmployeeAddress)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmployeeAddress)
                    .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                        .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxEmployeePrefix, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtEmployeeFirstname, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEmployeeLastname, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbxEmployeePosition, javax.swing.GroupLayout.Alignment.LEADING, 0, 130, Short.MAX_VALUE))
                            .addComponent(txtEmployeeIdCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
            .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                        .addComponent(lblEmployeeTel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmployeeTel, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnEmployeeAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmployeeUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmployeeDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEmployeeClear)))
                .addContainerGap(91, Short.MAX_VALUE))
        );

        pnEmployeeManageLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEmployeeAdd, btnEmployeeClear, btnEmployeeDelete, btnEmployeeUpdate});

        pnEmployeeManageLayout.setVerticalGroup(
            pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeManageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeePrefix)
                    .addComponent(cbxEmployeePrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeFirstname)
                    .addComponent(txtEmployeeFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeLastname)
                    .addComponent(txtEmployeeLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEmployeePosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeePosition))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeIdCardNumber)
                    .addComponent(txtEmployeeIdCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeAddress)
                    .addComponent(txtEmployeeAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeTel)
                    .addComponent(txtEmployeeTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnEmployeeManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEmployeeAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmployeeUpdate)
                    .addComponent(btnEmployeeDelete)
                    .addComponent(btnEmployeeClear))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pnEmployeeManageLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEmployeeAdd, btnEmployeeClear, btnEmployeeDelete, btnEmployeeUpdate, cbxEmployeePosition, cbxEmployeePrefix, txtEmployeeAddress, txtEmployeeFirstname, txtEmployeeIdCardNumber, txtEmployeeLastname, txtEmployeeTel});

        pnEmployeeSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, " ค้นหา ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lblEmployeeSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeSearch.setText("ค้นหา : ");

        lblEmployeeSearchForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeSearchForm.setText("ค้นหาจาก : ");

        lblEmployeeSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeSortBy.setText("เรียงโดย : ");

        lblEmployeeSortForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeSortForm.setText("เรียงจาก : ");

        lblEmployeeNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeeNumber.setText("จำนวน : ");

        lblEmployeePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmployeePerPage.setText("รายการ / หน้า");

        txtEmployeeSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmployeeSearch.setPreferredSize(new java.awt.Dimension(59, 25));
        txtEmployeeSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeSearchFocusGained(evt);
            }
        });
        txtEmployeeSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmployeeSearchKeyReleased(evt);
            }
        });

        cbxEmployeeSearchFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeeSearchFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ชื่อพนักงาน", "นามสกุล" }));
        cbxEmployeeSearchFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEmployeeSearchFromItemStateChanged(evt);
            }
        });

        cbxEmployeeSortBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeeSortBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "หมายเลขพนักงาน", "ชื่อพนักงาน", "นามสกุล" }));
        cbxEmployeeSortBy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEmployeeSortByItemStateChanged(evt);
            }
        });

        cbxEmployeeSortFrom.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeeSortFrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "น้อยไปมาก", "มากไปน้อย" }));
        cbxEmployeeSortFrom.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEmployeeSortFromItemStateChanged(evt);
            }
        });

        cbxEmployeePerPage.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbxEmployeePerPage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15", "30", "45" }));
        cbxEmployeePerPage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxEmployeePerPageItemStateChanged(evt);
            }
        });

        btnEmployeeClearSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEmployeeClearSearch.setText("ล้าง");
        btnEmployeeClearSearch.setPreferredSize(new java.awt.Dimension(59, 23));
        btnEmployeeClearSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEmployeeClearSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnEmployeeSearchLayout = new javax.swing.GroupLayout(pnEmployeeSearch);
        pnEmployeeSearch.setLayout(pnEmployeeSearchLayout);
        pnEmployeeSearchLayout.setHorizontalGroup(
            pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeSearchLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblEmployeeNumber)
                    .addComponent(lblEmployeeSortForm)
                    .addComponent(lblEmployeeSortBy)
                    .addComponent(lblEmployeeSearchForm)
                    .addComponent(lblEmployeeSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnEmployeeSearchLayout.createSequentialGroup()
                        .addComponent(cbxEmployeePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEmployeePerPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEmployeeClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxEmployeeSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxEmployeeSortBy, javax.swing.GroupLayout.Alignment.LEADING, 0, 128, Short.MAX_VALUE)
                        .addComponent(cbxEmployeeSearchFrom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtEmployeeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnEmployeeSearchLayout.setVerticalGroup(
            pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeSearch)
                    .addComponent(txtEmployeeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeSearchForm)
                    .addComponent(cbxEmployeeSearchFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeSortBy)
                    .addComponent(cbxEmployeeSortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeSortForm)
                    .addComponent(cbxEmployeeSortFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnEmployeeSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeNumber)
                    .addComponent(lblEmployeePerPage)
                    .addComponent(cbxEmployeePerPage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEmployeeClearSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnEmployeeSearchLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEmployeeClearSearch, cbxEmployeePerPage, cbxEmployeeSearchFrom, cbxEmployeeSortBy, cbxEmployeeSortFrom, txtEmployeeSearch});

        javax.swing.GroupLayout pnEmployeeRightLayout = new javax.swing.GroupLayout(pnEmployeeRight);
        pnEmployeeRight.setLayout(pnEmployeeRightLayout);
        pnEmployeeRightLayout.setHorizontalGroup(
            pnEmployeeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeRightLayout.createSequentialGroup()
                .addGroup(pnEmployeeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnEmployeeSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnEmployeeManage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnEmployeeRightLayout.setVerticalGroup(
            pnEmployeeRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEmployeeRightLayout.createSequentialGroup()
                .addComponent(pnEmployeeManage, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnEmployeeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scpEmployeeRight.setViewportView(pnEmployeeRight);

        pnEmployeeMain.add(scpEmployeeRight, java.awt.BorderLayout.LINE_END);

        tbpRegisterMain.addTab("จัดการพนักงาน", pnEmployeeMain);

        getContentPane().add(tbpRegisterMain, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // TODO add your handling code here:
        MainHub.main.openUser = false;
    }//GEN-LAST:event_formInternalFrameClosed

    private void btnUserShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnUserSearch.isShowing()) {
            pnUserSearch.setVisible(true);
            btnUserShowSearch.setText("ปิดการค้นหา");
        } else {
            pnUserSearch.setVisible(false);
            btnUserShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnUserShowSearchMouseClicked

    private void btnUserPreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserPreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", userControl, btnUserPre, txtUserPage);
    }//GEN-LAST:event_btnUserPreMouseClicked

    private void txtUserPageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserPageFocusGained
        // TODO add your handling code here:
        txtUserPage.nextFocus();
    }//GEN-LAST:event_txtUserPageFocusGained

    private void btnUserNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", userControl, btnUserNext, txtUserPage);
    }//GEN-LAST:event_btnUserNextMouseClicked

    private void btnUserClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserClearSearchMouseClicked
        // TODO add your handling code here:
        Util.clearSearchText(userControl);
    }//GEN-LAST:event_btnUserClearSearchMouseClicked

    private void cbxEmployeeNamePopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxEmployeeNamePopupMenuWillBecomeVisible
        // TODO add your handling code here:
        setCbxEmployeeName();
    }//GEN-LAST:event_cbxEmployeeNamePopupMenuWillBecomeVisible

    private void txtPasswordAgainKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordAgainKeyReleased
        // TODO add your handling code here:
        checkPassword();
    }//GEN-LAST:event_txtPasswordAgainKeyReleased

    private void btnUserAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserAddMouseClicked
        // TODO add your handling code here:
        userControl.insertData();
    }//GEN-LAST:event_btnUserAddMouseClicked

    private void txtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsernameFocusGained
        // TODO add your handling code here:
        txtUsername.selectAll();
    }//GEN-LAST:event_txtUsernameFocusGained

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        // TODO add your handling code here:
        txtPassword.selectAll();
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordAgainFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordAgainFocusGained
        // TODO add your handling code here:
        txtPasswordAgain.selectAll();
    }//GEN-LAST:event_txtPasswordAgainFocusGained

    private void btnUserUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserUpdateMouseClicked
        // TODO add your handling code here:
        userControl.updateData();
    }//GEN-LAST:event_btnUserUpdateMouseClicked

    private void btnUserDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserDeleteMouseClicked
        // TODO add your handling code here:
        userControl.deleteData();
    }//GEN-LAST:event_btnUserDeleteMouseClicked

    private void btnUserClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(userControl);
    }//GEN-LAST:event_btnUserClearMouseClicked

    private void txtUserSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserSearchFocusGained
        // TODO add your handling code here:
        txtUserSearch.selectAll();
    }//GEN-LAST:event_txtUserSearchFocusGained

    private void txtUserSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserSearchKeyReleased
        // TODO add your handling code here:
        userControl.showData(true);
    }//GEN-LAST:event_txtUserSearchKeyReleased

    private void cbxUserSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUserSearchFromItemStateChanged
        // TODO add your handling code here:
        userControl.showData(true);
    }//GEN-LAST:event_cbxUserSearchFromItemStateChanged

    private void cbxUserSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUserSortByItemStateChanged
        // TODO add your handling code here:
        userControl.showData(true);
    }//GEN-LAST:event_cbxUserSortByItemStateChanged

    private void cbxUserSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUserSortFromItemStateChanged
        // TODO add your handling code here:
        userControl.showData(false);
    }//GEN-LAST:event_cbxUserSortFromItemStateChanged

    private void cbxUserPerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxUserPerPageItemStateChanged
        // TODO add your handling code here:
        userControl.showData(false);
    }//GEN-LAST:event_cbxUserPerPageItemStateChanged

    private void tbUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUserMouseClicked
        // TODO add your handling code here:
        Util.tableToText(userControl);
        checkPassword();
    }//GEN-LAST:event_tbUserMouseClicked

    private void txtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyReleased
        // TODO add your handling code here:
        checkPassword();
    }//GEN-LAST:event_txtPasswordKeyReleased

    private void tbEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeeMouseClicked
        // TODO add your handling code here:
        Util.tableToText(employeeControl);
    }//GEN-LAST:event_tbEmployeeMouseClicked

    private void btnEmployeeShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeShowSearchMouseClicked
        // TODO add your handling code here:
        if (!pnEmployeeSearch.isShowing()) {
            pnEmployeeSearch.setVisible(true);
            btnEmployeeShowSearch.setText("ปิดการค้นหา");
        } else {
            pnEmployeeSearch.setVisible(false);
            btnEmployeeShowSearch.setText("เปิดการค้นหา");
        }
    }//GEN-LAST:event_btnEmployeeShowSearchMouseClicked

    private void btnEmployeeNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeNextMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("next", employeeControl, btnEmployeeNext, txtEmployeePage);
    }//GEN-LAST:event_btnEmployeeNextMouseClicked

    private void btnEmployeePreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeePreMouseClicked
        // TODO add your handling code here:
        Util.controlPageNum("pre", employeeControl, btnEmployeePre, txtEmployeePage);
    }//GEN-LAST:event_btnEmployeePreMouseClicked

    private void txtEmployeePageFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeePageFocusGained
        // TODO add your handling code here:
        txtEmployeePage.nextFocus();
    }//GEN-LAST:event_txtEmployeePageFocusGained

    private void cbxEmployeePrefixPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxEmployeePrefixPopupMenuWillBecomeVisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEmployeePrefixPopupMenuWillBecomeVisible

    private void txtEmployeeFirstnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeFirstnameFocusGained
        // TODO add your handling code here:
        txtEmployeeFirstname.selectAll();
    }//GEN-LAST:event_txtEmployeeFirstnameFocusGained

    private void btnEmployeeAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeAddMouseClicked
        // TODO add your handling code here:
        employeeControl.insertData();
    }//GEN-LAST:event_btnEmployeeAddMouseClicked

    private void btnEmployeeUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeUpdateMouseClicked
        // TODO add your handling code here:
        employeeControl.updateData();
    }//GEN-LAST:event_btnEmployeeUpdateMouseClicked

    private void btnEmployeeDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeDeleteMouseClicked
        // TODO add your handling code here:
        employeeControl.deleteData();
    }//GEN-LAST:event_btnEmployeeDeleteMouseClicked

    private void btnEmployeeClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeClearMouseClicked
        // TODO add your handling code here:
        Util.clearText(employeeControl);
    }//GEN-LAST:event_btnEmployeeClearMouseClicked

    private void txtEmployeeSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeSearchFocusGained
        // TODO add your handling code here:
        txtEmployeeSearch.selectAll();
    }//GEN-LAST:event_txtEmployeeSearchFocusGained

    private void txtEmployeeSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeSearchKeyReleased
        // TODO add your handling code here:
        employeeControl.showData(true);
    }//GEN-LAST:event_txtEmployeeSearchKeyReleased

    private void cbxEmployeeSearchFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEmployeeSearchFromItemStateChanged
        // TODO add your handling code here:
        employeeControl.showData(true);
    }//GEN-LAST:event_cbxEmployeeSearchFromItemStateChanged

    private void cbxEmployeeSortByItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEmployeeSortByItemStateChanged
        // TODO add your handling code here:
        employeeControl.showData(true);
    }//GEN-LAST:event_cbxEmployeeSortByItemStateChanged

    private void cbxEmployeeSortFromItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEmployeeSortFromItemStateChanged
        // TODO add your handling code here:
        employeeControl.showData(false);
    }//GEN-LAST:event_cbxEmployeeSortFromItemStateChanged

    private void cbxEmployeePerPageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxEmployeePerPageItemStateChanged
        // TODO add your handling code here:
        employeeControl.showData(false);
    }//GEN-LAST:event_cbxEmployeePerPageItemStateChanged

    private void btnEmployeeClearSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmployeeClearSearchMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEmployeeClearSearchMouseClicked

    private void txtEmployeeLastnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeLastnameFocusGained
        // TODO add your handling code here:
        txtEmployeeLastname.selectAll();
    }//GEN-LAST:event_txtEmployeeLastnameFocusGained

    private void cbxEmployeePositionPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbxEmployeePositionPopupMenuWillBecomeVisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEmployeePositionPopupMenuWillBecomeVisible

    private void txtEmployeeIdCardNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeIdCardNumberFocusGained
        // TODO add your handling code here:
        txtEmployeeIdCardNumber.selectAll();
    }//GEN-LAST:event_txtEmployeeIdCardNumberFocusGained

    private void txtEmployeeTelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeTelFocusGained
        // TODO add your handling code here:
        txtEmployeeTel.selectAll();
    }//GEN-LAST:event_txtEmployeeTelFocusGained

    private void btnUser_AddEmplyeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUser_AddEmplyeeMouseClicked
        // TODO add your handling code here:
        tbpRegisterMain.setSelectedIndex(1);
    }//GEN-LAST:event_btnUser_AddEmplyeeMouseClicked

    private void txtEmployeeAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeAddressFocusGained
        // TODO add your handling code here:
        txtEmployeeAddress.selectAll();
    }//GEN-LAST:event_txtEmployeeAddressFocusGained

    private void tbEmployeeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmployeeMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbEmployeeMousePressed

    private void txtEmployeeIdCardNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeIdCardNumberKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmployeeIdCardNumberKeyTyped

    private void txtEmployeeTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeTelKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmployeeTelKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmployeeAdd;
    private javax.swing.JButton btnEmployeeClear;
    private javax.swing.JButton btnEmployeeClearSearch;
    private javax.swing.JButton btnEmployeeDelete;
    private javax.swing.JButton btnEmployeeNext;
    private javax.swing.JButton btnEmployeePre;
    private javax.swing.JButton btnEmployeeShowSearch;
    private javax.swing.JButton btnEmployeeUpdate;
    private javax.swing.JButton btnUserAdd;
    private javax.swing.JButton btnUserClear;
    private javax.swing.JButton btnUserClearSearch;
    private javax.swing.JButton btnUserDelete;
    private javax.swing.JButton btnUserNext;
    private javax.swing.JButton btnUserPre;
    private javax.swing.JButton btnUserShowSearch;
    private javax.swing.JButton btnUserUpdate;
    private javax.swing.JButton btnUser_AddEmplyee;
    private javax.swing.ButtonGroup btngUserType;
    private javax.swing.JComboBox cbxEmployeeName;
    private javax.swing.JComboBox cbxEmployeePerPage;
    private javax.swing.JComboBox cbxEmployeePosition;
    private javax.swing.JComboBox cbxEmployeePrefix;
    private javax.swing.JComboBox cbxEmployeeSearchFrom;
    private javax.swing.JComboBox cbxEmployeeSortBy;
    private javax.swing.JComboBox cbxEmployeeSortFrom;
    private javax.swing.JComboBox cbxUserPerPage;
    private javax.swing.JComboBox cbxUserSearchFrom;
    private javax.swing.JComboBox cbxUserSortBy;
    private javax.swing.JComboBox cbxUserSortFrom;
    private javax.swing.JLabel lblCheckPassword;
    private javax.swing.JLabel lblEmployeeAddress;
    private javax.swing.JLabel lblEmployeeFirstname;
    private javax.swing.JLabel lblEmployeeIdCardNumber;
    private javax.swing.JLabel lblEmployeeLastname;
    private javax.swing.JLabel lblEmployeeNumber;
    private javax.swing.JLabel lblEmployeePerPage;
    private javax.swing.JLabel lblEmployeePosition;
    private javax.swing.JLabel lblEmployeePrefix;
    private javax.swing.JLabel lblEmployeeSearch;
    private javax.swing.JLabel lblEmployeeSearchForm;
    private javax.swing.JLabel lblEmployeeSortBy;
    private javax.swing.JLabel lblEmployeeSortForm;
    private javax.swing.JLabel lblEmployeeTel;
    private javax.swing.JLabel lblEmployeename;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordAgian;
    private javax.swing.JLabel lblUserNumber;
    private javax.swing.JLabel lblUserPerPage;
    private javax.swing.JLabel lblUserSearch;
    private javax.swing.JLabel lblUserSearchForm;
    private javax.swing.JLabel lblUserSortBy;
    private javax.swing.JLabel lblUserSortForm;
    private javax.swing.JLabel lblUserType;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pnEmployeeMain;
    private javax.swing.JPanel pnEmployeeManage;
    private javax.swing.JPanel pnEmployeePagination;
    private javax.swing.JPanel pnEmployeeRight;
    private javax.swing.JPanel pnEmployeeSearch;
    private javax.swing.JPanel pnInputTbEmployee;
    private javax.swing.JPanel pnInputTbUser;
    private javax.swing.JPanel pnUserMain;
    private javax.swing.JPanel pnUserManage;
    private javax.swing.JPanel pnUserPagination;
    private javax.swing.JPanel pnUserRight;
    private javax.swing.JPanel pnUserSearch;
    private javax.swing.JRadioButton rdbAdminType;
    private javax.swing.JRadioButton rdbUserType;
    private javax.swing.JScrollPane scpEmployee;
    private javax.swing.JScrollPane scpEmployeeRight;
    private javax.swing.JScrollPane scpUser;
    private javax.swing.JScrollPane scpUserRight;
    private javax.swing.JTable tbEmployee;
    private javax.swing.JTable tbUser;
    private javax.swing.JTabbedPane tbpRegisterMain;
    private javax.swing.JTextField txtEmployeeAddress;
    private javax.swing.JTextField txtEmployeeFirstname;
    private javax.swing.JTextField txtEmployeeIdCardNumber;
    private javax.swing.JTextField txtEmployeeLastname;
    private javax.swing.JTextField txtEmployeePage;
    private javax.swing.JTextField txtEmployeeSearch;
    private javax.swing.JTextField txtEmployeeTel;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPasswordAgain;
    private javax.swing.JTextField txtUserPage;
    private javax.swing.JTextField txtUserSearch;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    private void setCbxEmployeeName() {

        String sql = "SELECT employeeFirstname, employeeLastname FROM tbemployee WHERE userActivated = '0'";

        v.clear();
        v.add("รายชื่อพนักงาน");

        try {
            ResultSet rs = DBFactory.getConnection().createStatement().executeQuery(sql);

            while (rs.next()) {

                v.add(rs.getString(1) + "  " + rs.getString(2));

            }

            cbxEmployeeName.setModel(new DefaultComboBoxModel(v));

        } catch (SQLException ex) {
            Logger.getLogger(FormUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void checkPassword() {

        if (txtPassword.getText().isEmpty() && txtPasswordAgain.getText().isEmpty()) {

            lblCheckPassword.setForeground(new Color(255, 153, 0));
            lblCheckPassword.setText("ตรวจสอบรหัสผ่าน");

        } else if (txtPassword.getText().isEmpty() || txtPasswordAgain.getText().isEmpty()) {

            lblCheckPassword.setForeground(new Color(255, 0, 0));
            lblCheckPassword.setText("รหัสผ่านไม่ตรงกัน");

        } else {
            if (txtPassword.getText().equals(txtPasswordAgain.getText())) {

                lblCheckPassword.setForeground(new Color(0, 153, 0));
                lblCheckPassword.setText("รหัสผ่านตรงกัน");

            } else {

                lblCheckPassword.setForeground(new Color(255, 0, 0));
                lblCheckPassword.setText("รหัสผ่านไม่ตรงกัน");

            }
        }

    }
}
