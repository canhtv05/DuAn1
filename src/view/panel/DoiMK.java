/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.doimk.ModelDoiMK;
import repository.doimk.RepoDoiMK;
import repository.login.RepoLogin;
import repository.validate.Validate;
import view.component.message.MessageFrame;
import view.jframe.ViewSignIn;

/**
 *
 * @author CanhPC
 */
public class DoiMK extends javax.swing.JPanel {

    private String user;
    private int role;
    private MessageFrame mess;
    private String timKiem = "";

    public DoiMK() {
        initComponents();
        init();
        eventChangeTab();
        tab1();
    }

    private void init() {
        this.setBackground(new Color(242, 242, 242));
        user = ViewSignIn.getUsernameString;
        role = ViewSignIn.role;
        txtTK.setText(user);
    }

    private void eventChangeTab() {
        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int select = tab.getSelectedIndex();
                if (select == 1 && role == 1) {
                    mess = new MessageFrame();
                    mess.showMessage("error", "Nhân viên không có quyền này.");
                    tab.setSelectedIndex(0);
                    return;
                } else {
                    timKiem();
                }
            }
        });
    }

    private void timKiem() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem = txtTimKiem.getText().trim();
                tab1();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem = txtTimKiem.getText().trim();
                tab1();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void tab1() {
        tbl.fixTable(jScrollPane1);
        RepoDoiMK repo = new RepoDoiMK();
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        ArrayList<ModelDoiMK> list = repo.getAll(timKiem);
        for (ModelDoiMK m : list) {
            Object[] row = {
                m.getTk(),
                m.getMaNV(),
                m.getMk(),
                m.getVaiTro() == 0 ? "Chủ trọ" : "Nhân viên"
            };
            model.addRow(row);
        }
    }

    public void Reset() {
        txtMaNV.setText("");
        txtTenTK.setText("");
        txtMK.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tab = new view.component.tab.MaterialTabbed();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTK = new view.component.textfield.TextField();
        txtMkCu = new view.component.textfield.TextField();
        txtMkMoi = new view.component.textfield.TextField();
        txtXacNhan = new view.component.textfield.TextField();
        jLabel1 = new javax.swing.JLabel();
        btn = new view.component.button.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new view.component.table.Table();
        btnXoaTK = new view.component.button.MyButton();
        btnReset = new view.component.button.MyButton();
        jPanel4 = new javax.swing.JPanel();
        txtMaNV = new view.component.textfield.TextField();
        jLabel3 = new javax.swing.JLabel();
        rdo_ChuTro = new view.component.radiobutton.RadioButtonCustom();
        rdo_NV = new view.component.radiobutton.RadioButtonCustom();
        txtTenTK = new view.component.textfield.TextField();
        txtMK = new view.component.textfield.TextField();
        btnTK = new view.component.button.MyButton();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiem = new view.component.textfield.TextField();

        tab.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTK.setLabelText("Tên tài khoản");
        txtTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTKActionPerformed(evt);
            }
        });

        txtMkCu.setLabelText("Mật khẩu cũ");
        txtMkCu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMkCuActionPerformed(evt);
            }
        });

        txtMkMoi.setLabelText("Mật khẩu mới");
        txtMkMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMkMoiActionPerformed(evt);
            }
        });

        txtXacNhan.setLabelText("Nhập lại mật khẩu");
        txtXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtXacNhanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐỔI MẬT KHẨU");

        btn.setText("Đổi");
        btn.setBorderColor(new java.awt.Color(153, 204, 204));
        btn.setColor(new java.awt.Color(204, 255, 255));
        btn.setColorClick(new java.awt.Color(153, 255, 255));
        btn.setColorOver(new java.awt.Color(204, 255, 255));
        btn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn.setRadius(10);
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMkMoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMkCu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtXacNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMkCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMkMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(483, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(484, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        tab.addTab("Đổi mật khẩu", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Mã nhân viên", "Mật khẩu", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);

        btnXoaTK.setText("Xóa tài khoản");
        btnXoaTK.setBorderColor(new java.awt.Color(153, 204, 204));
        btnXoaTK.setColor(new java.awt.Color(204, 255, 255));
        btnXoaTK.setColorClick(new java.awt.Color(153, 255, 255));
        btnXoaTK.setColorOver(new java.awt.Color(204, 255, 255));
        btnXoaTK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaTK.setRadius(10);
        btnXoaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTKActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.setBorderColor(new java.awt.Color(153, 204, 204));
        btnReset.setColor(new java.awt.Color(204, 255, 255));
        btnReset.setColorClick(new java.awt.Color(153, 255, 255));
        btnReset.setColorOver(new java.awt.Color(204, 255, 255));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setRadius(10);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo tài khoản", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        txtMaNV.setLabelText("Mã nhân viên");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel3.setText("Vai trò:");

        buttonGroup1.add(rdo_ChuTro);
        rdo_ChuTro.setForeground(new java.awt.Color(51, 0, 255));
        rdo_ChuTro.setText("Chủ trọ");

        buttonGroup1.add(rdo_NV);
        rdo_NV.setForeground(new java.awt.Color(0, 0, 255));
        rdo_NV.setText("Nhân viên");

        txtTenTK.setLabelText("Tên tài khoản");

        txtMK.setLabelText("Mật khẩu");
        txtMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMKActionPerformed(evt);
            }
        });

        btnTK.setText("Tạo tài khoản");
        btnTK.setBorderColor(new java.awt.Color(153, 204, 204));
        btnTK.setColor(new java.awt.Color(204, 255, 255));
        btnTK.setColorClick(new java.awt.Color(153, 255, 255));
        btnTK.setColorOver(new java.awt.Color(204, 255, 255));
        btnTK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTK.setRadius(10);
        btnTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Chủ trọ ko cần nhập mã nhân viên");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_ChuTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtMK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                .addComponent(txtTenTK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rdo_ChuTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdo_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnTK, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtTimKiem.setLabelText("Tìm kiếm");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1295, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(273, Short.MAX_VALUE))
        );

        tab.addTab("Thêm tài khoản", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed

        RepoDoiMK repoMK = new RepoDoiMK();
        RepoLogin repo = new RepoLogin();
        Validate validate = new Validate();
        mess = new MessageFrame();
        MessageFrame messageFrame = new MessageFrame();

        JTextField[] field = {txtMkCu, txtMkMoi, txtXacNhan};
        String[] label = {"Mật khẩu cũ", "Mật khẩu mới", "Xác nhận mật khẩu"};

        // Kiểm tra các trường không bị bỏ trống
        for (int i = 0; i < field.length; i++) {
            if (!validate.isNull(field[i], label[i])) {
                return;
            }
        }

        String tenDangNhap = txtTK.getText().trim();
        if (tenDangNhap.isEmpty()) {
            mess.showMessage("error", "Tên đăng nhập không được để trống.");
            return;
        }

        // Kiểm tra tên đăng nhập tồn tại
        if (!repoMK.existTK(tenDangNhap)) {
            mess.showMessage("error", "Tên đăng nhập không tồn tại.");
            return;
        }

        String mkCu = txtMkCu.getText().trim();
        String mkMoi = txtMkMoi.getText().trim();
        String xnMkMoi = txtXacNhan.getText().trim();

        // Kiểm tra mật khẩu cũ
        String storedMkCu = repo.xacNhanMK(tenDangNhap);  // Lấy mật khẩu cũ lưu trữ từ DB
        if (storedMkCu == null || !mkCu.equals(storedMkCu)) {
            mess.showMessage("error", "Mật khẩu cũ sai.");
            return;
        }

        // Biểu thức chính quy kiểm tra không có dấu tiếng Việt và không có dấu cách
        String regex = "^(?!.*[ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÛÝàáâãèéêìíòóôõùúûýăđĩũơưẠ-ỹ ])(?=.*[0-9])(?=.*[A-Z]).{3,}$";
        Pattern pattern = Pattern.compile(regex);

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        Matcher matcherMkMoi = pattern.matcher(mkMoi);
        Matcher matcherNxMK = pattern.matcher(xnMkMoi);
        // Kiểm tra độ dài của mật khẩu
        if (mkMoi.length() <= 2 || xnMkMoi.length() <= 2) {
            mess.showMessage("error", "Mật khẩu phải chứa 3 ký tự trở lên.");
            return;
        }
        // Kiểm tra mật khẩu xác nhận có khớp không
        if (!mkMoi.equals(xnMkMoi)) {
            txtXacNhan.requestFocus();
            mess.showMessage("error", "Mật khẩu không khớp.");
            return;
        }
        // Kiểm tra mật khẩu có chứa ít nhất 1 ký tự số, 1 chữ in hoa, không có dấu tiếng Việt và không có dấu cách
        if (!matcherMkMoi.matches() || !matcherNxMK.matches()) {
            mess.showMessage("error", "Mật khẩu phải chứa ít nhất 1 ký tự số, 1 chữ in hoa,"
                    + " không dấu tiếng Việt, không dấu cách.");
            return;
        }

        messageFrame.showMessage("message", "Bạn có muốn đổi mật khẩu không?");
        messageFrame.setOnOkClicked(() -> {
            if (repo.changePassword(user, mkMoi)) {
                mess.showMessage("success", "Đổi mật khẩu thành công.");
                this.tab1();
            } else {
                mess.showMessage("error", "Đổi mật khẩu thất bại.");
            }
        });


    }//GEN-LAST:event_btnActionPerformed

    private void btnTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKActionPerformed
        RepoDoiMK repo = new RepoDoiMK();
        Validate validate = new Validate();
        MessageFrame messageFrame = new MessageFrame();
        mess = new MessageFrame();
        JTextField[] field = {txtMaNV, txtTenTK, txtMK};
        String[] label = {"Mã Nhân Viên", "Tài khoản", "Mật khẩu"};
        // Xác định vai trò trước khi kiểm tra các trường
        int vaiTro = rdo_ChuTro.isSelected() ? 0 : (rdo_NV.isSelected() ? 1 : -1); // Vai trò: 0 là Chủ trọ, 1 là Nhân viên
        // Kiểm tra xem vai trò đã được chọn chưa
        if (vaiTro == -1) {
            mess.showMessage("error", "Bạn phải chọn vai trò (Chủ trọ hoặc Nhân viên).");
            return;
        }
        // Kiểm tra tất cả các trường có bị bỏ trống không
        for (int i = 0; i < field.length; i++) {
            // Bỏ qua kiểm tra nếu vai trò là chủ trọ và trường đang kiểm tra là mã nhân viên
            if (label[i].equals("Mã Nhân Viên") && vaiTro == 0) {
                continue;
            }
            if (field[i].getText().trim().isEmpty()) {
                mess.showMessage("error", "Không được để trống " + label[i] + ".");
                return;
            }
        }
        // Thiết lập vai trò cho ModelDoiMK
        ModelDoiMK Themtk = new ModelDoiMK();
        Themtk.setVaiTro(vaiTro); // Thiết lập vai trò
        // Lấy dữ liệu từ các trường
        String tk = txtTenTK.getText().trim();
        String mk = txtMK.getText().trim();
        String maNV = txtMaNV.getText().trim();
        // Kiểm tra tài khoản đã tồn tại chưa
        if (repo.existTK(tk)) {
            mess.showMessage("error", "Đã tồn tại tài khoản này.");
            return;
        }
        // Kiểm tra mật khẩu
        // Biểu thức chính quy kiểm tra không có dấu tiếng Việt, không có dấu cách, và chứa ít nhất 1 ký tự số và chữ in hoa
        String regex = "^(?!.*[ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÛÝàáâãèéêìíòóôõùúûýăđĩũơưẠ-ỹ ])(?=.*[0-9])(?=.*[A-Z]).{3,}$";
        Pattern pattern = Pattern.compile(regex);
        // Kiểm tra mật khẩu mới
        Matcher matcherMk = pattern.matcher(mk);
        // Kiểm tra độ dài của mật khẩu và các điều kiện khác
        if (mk.length() <= 2 || !matcherMk.matches()) {
            mess.showMessage("error", "Mật khẩu phải chứa ít nhất 3 ký tự và 1 ký tự số,"
                    + " 1 chữ in hoa, không dấu và không cách.");
            return;
        }
        // Kiểm tra mã nhân viên nếu vai trò là Nhân viên
        if (vaiTro == 1) {
            if (maNV.isEmpty()) {
                mess.showMessage("error", "Mã Nhân Viên không được để trống.");
                return;
            }
            // Kiểm tra xem mã nhân viên đã tồn tại hay không
            if (!repo.KiemTraMaNhanVienTonTai(maNV)) {
                mess.showMessage("error", "Mã Nhân Viên này không tồn tại.");
                return;
            }
            if (repo.KiemTraMaNhanVien(maNV)) {
                mess.showMessage("error", "Mã Nhân Viên này đã có tài khoản.");
                return;
            }
        }
        messageFrame.showMessage("message", "Bạn có chắc chắn muốn thêm tài khoản này không?");
        messageFrame.setOnOkClicked(() -> {
            if (repo.add(new ModelDoiMK(tk, maNV, mk, vaiTro))) {
                mess.showMessage("success", "Tạo tài khoản thành công.");
                this.tab1();
            } else {
                mess.showMessage("error", "Tạo tài khoản thất bại, mã nhân viên không đúng hoặc không tồn tại");
            }
        });
    }//GEN-LAST:event_btnTKActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
//        int indenx = tbl.getSelectedRow();
//        txtTenTK.setText(tbl.getValueAt(indenx, 0).toString());
//        txtMaNV.setText(tbl.getValueAt(indenx, 1).toString());
//        txtMK.setText(tbl.getValueAt(indenx, 2).toString());
//        String vaiTro = tbl.getValueAt(indenx, 3).toString();
//        if (vaiTro.equalsIgnoreCase("Chủ trọ")) {
//            rdo_ChuTro.setSelected(true);
//        } else {
//            rdo_NV.setSelected(true);
//        }
    }//GEN-LAST:event_tblMouseClicked

    private void txtTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTKActionPerformed

    private void txtMkCuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMkCuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMkCuActionPerformed

    private void txtMkMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMkMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMkMoiActionPerformed

    private void txtXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtXacNhanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXacNhanActionPerformed

    private void btnXoaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTKActionPerformed
        // TODO add your handling code here:
        int i = tbl.getSelectedRow();
        RepoDoiMK repo = new RepoDoiMK();
        MessageFrame messgae = new MessageFrame();
        Validate validate = new Validate();
        mess = new MessageFrame();
        if (i == -1) {
            mess.showMessage("error", "Vui lòng chọn tài khoản để xóa.");
            return;
        }
        String tenTK = (String) tbl.getValueAt(i, 0);
        messgae.showMessage("message", "Bạn có chắc chắn muốn xóa tài khoản không?");
        messgae.setOnOkClicked(() -> {
            boolean isDeleted = repo.xoaTaiKhoan(tenTK);
            if (isDeleted) {
                mess.showMessage("success", "Xóa tài khoản thành công.");
                ((DefaultTableModel) tbl.getModel()).removeRow(i);
            } else {
                mess.showMessage("error", "Xóa tài khoản thất bại.");
            }
        });
    }//GEN-LAST:event_btnXoaTKActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btn;
    private view.component.button.MyButton btnReset;
    private view.component.button.MyButton btnTK;
    private view.component.button.MyButton btnXoaTK;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private view.component.radiobutton.RadioButtonCustom rdo_ChuTro;
    private view.component.radiobutton.RadioButtonCustom rdo_NV;
    private view.component.tab.MaterialTabbed tab;
    private view.component.table.Table tbl;
    private view.component.textfield.TextField txtMK;
    private view.component.textfield.TextField txtMaNV;
    private view.component.textfield.TextField txtMkCu;
    private view.component.textfield.TextField txtMkMoi;
    private view.component.textfield.TextField txtTK;
    private view.component.textfield.TextField txtTenTK;
    private view.component.textfield.TextField txtTimKiem;
    private view.component.textfield.TextField txtXacNhan;
    // End of variables declaration//GEN-END:variables
}
