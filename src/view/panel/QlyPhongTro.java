/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PhongTro.ModelPhongTro;
import repository.PhongTro.repoPhongTro;
import view.component.message.MessageFrame;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class QlyPhongTro extends javax.swing.JPanel {

    private ModelPhongTro mdPT = new ModelPhongTro();
    private repoPhongTro repoPT = new repoPhongTro();
    private MessageFrame msg;
    private String path = "D/...";

    /**
     * Creates new form QlPhongTro
     */
    public QlyPhongTro() {
        initComponents();
        this.init();
        this.loadTable(this.repoPT.findAll());
        this.loadTrangThai(repoPT.findAll());
    }

    public void init() {
        lbHinhAnh.setText("");
        this.loadTable(this.repoPT.findAll());
    }

    private void loadTable(ArrayList<ModelPhongTro> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPhongTro.getModel();

        dtm.setRowCount(0);

        for (ModelPhongTro pt : list) {
            Object[] data = {
                pt.getTangSo(),
                pt.getMaPhong(),
                pt.getLoaiPhong(),
                pt.getDienTich(),
                pt.getGiaPhong(),
                pt.getTienNghi(),
                pt.getTrangThai() == 0 ? "Còn trống"
                : pt.getTrangThai() == 1 ? "Đã thuê" : "Đang bảo dưỡng"
            };
            dtm.addRow(data);
        }
    }

    public void fillForm(ModelPhongTro pt) {
        this.txtTang.setText(Integer.toString(pt.getTangSo()));
        this.txtMaPT.setText(pt.getMaPhong());
        this.txtLoaiPhong.setText(pt.getLoaiPhong());
        this.txtDienTich.setText(Float.toString(pt.getDienTich()));
        this.txtGiaPhong.setText(Float.toString(pt.getGiaPhong()));
        this.txtTienNghi.setText(pt.getTienNghi());
        switch (pt.getTrangThai()) {
            case 0 ->
                this.rdoConTrong.setSelected(true);
            case 1 ->
                this.rdoDaThue.setSelected(true);
            default ->
                this.rdoBaoDuong.setSelected(true);
        }
        path = pt.getAnh();
        ImageIcon img = new ImageIcon(path);
        lbHinhAnh.setIcon(img);

    }

    private void loadTrangThai(ArrayList<ModelPhongTro> list) {
        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbTrangThai.getModel();
        for (ModelPhongTro pt : list) {
            cbm.addElement(pt);
        }
    }

    private void loadGia(ArrayList<ModelPhongTro> list) {
        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbGia.getModel();
        for (ModelPhongTro pt : list) {
            cbm.addElement(pt);
        }
    }

    private void loadDienTich(ArrayList<ModelPhongTro> list) {
        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbDienTich.getModel();
        for (ModelPhongTro pt : list) {
            cbm.addElement(pt);
        }
    }

    private void clearForm() {
        this.txtSearch.setText("");
        this.txtTang.setText("");
        this.txtMaPT.setText("");
        this.txtLoaiPhong.setText("");
        this.txtDienTich.setText("");
        this.txtGiaPhong.setText("");
        this.txtTienNghi.setText("");
        this.rdoConTrong.setSelected(true);
        this.lbHinhAnh.setText("                                              Thêm ảnh");
        this.lbHinhAnh.setIcon(null);
    }

    private boolean checkIdInsert() {
        String maPT = this.txtMaPT.getText().trim();
        for (ModelPhongTro pt : this.repoPT.findAll()) {
            msg = new MessageFrame();
            if (pt.getMaPhong().equalsIgnoreCase(maPT)) {
                msg.showMessage("error", "Phòng này đã tồn tại. Vui lòng chọn số phòng khác!");
                return false;
            }
        }
        return true;
    }

    private boolean checkIdUpdate() {
        String maPT = this.txtMaPT.getText().trim();
        for (ModelPhongTro pt : this.repoPT.findAll()) {
            msg = new MessageFrame();
            if (pt.getMaPhong().equalsIgnoreCase(maPT)) {
                return true;
            }
        }
        msg.showMessage("error", "Phòng này không tồn tại. Lưu ý: Không thể sửa số phòng!!");

        return false;
    }

    private boolean check() {
        msg = new MessageFrame();
        if (this.txtMaPT.getText().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Số phòng!");
            return false;
        }

        if (this.txtLoaiPhong.getText().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Loại phòng!");
            return false;
        }

        if (this.txtDienTich.getText().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Diện tích!");
            return false;
        } else {
            try {
                float dienTich = Float.parseFloat(this.txtDienTich.getText());
                if (dienTich <= 10 || dienTich >= 100) {
                    msg.showMessage("error", "Diện tích phòng chỉ trong khoảng từ 10 - 100");
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Vui lòng chỉ nhập số");
                return false;
            }
        }

        if (this.txtGiaPhong.getText().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Giá phòng!");
            return false;
        } else {
            try {
                float giaPhong = Float.parseFloat(this.txtGiaPhong.getText());
                if (giaPhong <= 0) {
                    msg.showMessage("error", "Giá phòng phải lớn hơn 0");
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Vui lòng chỉ nhập số!");
                return false;
            }
        }

        if (this.txtTienNghi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không để trống mục Tiện nghi!");
            return false;
        }

        return true;
    }

    private ModelPhongTro start() {
        int TangSo = Integer.parseInt(this.txtTang.getText().trim());
        String maPhong = this.txtMaPT.getText().trim();
        String loaiPhong = this.txtLoaiPhong.getText().trim();
        float dienTich = Float.parseFloat(this.txtDienTich.getText().trim());
        float giaPhong = Float.parseFloat(this.txtGiaPhong.getText().trim());
        String tienNghi = this.txtTienNghi.getText().trim();
        int trangThai = this.rdoConTrong.isSelected() ? 0 : this.rdoDaThue.isSelected() ? 1 : 2;
        String anh = this.path;

        return new ModelPhongTro(TangSo, maPhong, loaiPhong, dienTich, giaPhong, tienNghi, trangThai, anh);
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
        jPanel1 = new javax.swing.JPanel();
        txtMaPT = new view.component.textfield.TextField();
        txtLoaiPhong = new view.component.textfield.TextField();
        txtGiaPhong = new view.component.textfield.TextField();
        txtTienNghi = new view.component.textfield.TextField();
        txtDienTich = new view.component.textfield.TextField();
        jLabel6 = new javax.swing.JLabel();
        rdoConTrong = new javax.swing.JRadioButton();
        rdoDaThue = new javax.swing.JRadioButton();
        btnRefresh = new view.component.button.Button();
        btnReset = new view.component.button.Button();
        btnInsert = new view.component.button.Button();
        btnUpdate = new view.component.button.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhongTro = new view.component.table.Table();
        cbbTrangThai = new javax.swing.JComboBox<>();
        cbbGia = new javax.swing.JComboBox<>();
        cbbDienTich = new javax.swing.JComboBox<>();
        rdoBaoDuong = new javax.swing.JRadioButton();
        btnSearch = new view.component.button.Button();
        btnDelete = new view.component.button.Button();
        txtSearch = new view.component.textfield.TextField();
        txtTang = new view.component.textfield.TextField();
        lbHinhAnh = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMaPT.setLabelText("Số phòng");

        txtLoaiPhong.setLabelText("Loại phòng");

        txtGiaPhong.setLabelText("Giá phòng");

        txtTienNghi.setLabelText("Tiện nghi");

        txtDienTich.setLabelText("Diện tích");

        jLabel6.setText("Trạng thái");

        buttonGroup1.add(rdoConTrong);
        rdoConTrong.setText("Còn trống");

        buttonGroup1.add(rdoDaThue);
        rdoDaThue.setText("Đã thuê");

        btnRefresh.setBackground(new java.awt.Color(204, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 204, 255));
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(153, 204, 255));
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(153, 204, 255));
        btnUpdate.setText("Cập nhập");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblPhongTro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tầng", "Mã phòng", "Loại phòng", "Diện tích(m²)", "Giá phòng", "Tiện nghi", "Tình trạng"
            }
        ));
        tblPhongTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongTroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhongTro);

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tình trạng" }));
        cbbTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTrangThaiActionPerformed(evt);
            }
        });

        cbbGia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn mức giá", " " }));
        cbbGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGiaActionPerformed(evt);
            }
        });

        cbbDienTich.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn diện tích" }));
        cbbDienTich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDienTichActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoBaoDuong);
        rdoBaoDuong.setText("Đang bảo dưỡng");

        btnSearch.setBackground(new java.awt.Color(204, 204, 204));
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Search...");

        txtTang.setLabelText("Tầng");

        lbHinhAnh.setBackground(new java.awt.Color(255, 0, 102));
        lbHinhAnh.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbHinhAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(cbbDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(31, 31, 31)
                                .addComponent(rdoConTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoDaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoBaoDuong)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGiaPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienNghi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDienTich, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(187, 187, 187)
                        .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(377, 377, 377)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(txtTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rdoConTrong)
                            .addComponent(rdoDaThue)
                            .addComponent(rdoBaoDuong)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Quản lý phòng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        try {
            msg = new MessageFrame();
            if (this.check()) {
                if (this.checkIdUpdate()) {
                    ModelPhongTro pt = start();
                    this.repoPT.update(pt);
                    msg.showMessage("success", "Sửa thành công");
                    this.loadTable(this.repoPT.findAll());
                    this.clearForm();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        this.loadTable(this.repoPT.findAll());
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tblPhongTroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongTroMouseClicked
        // TODO add your handling code here:
        int row = this.tblPhongTro.getSelectedRow();
        fillForm(this.repoPT.findAll().get(row));

//        if (row == -1) {
//            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dữ liệu trên bảng!");
//            return;
//        }
//        String idTang = this.tblPhongTro.getValueAt(row, 0).toString();
//        String maPT = this.tblPhongTro.getValueAt(row, 1).toString();
//        String loaiPhong = this.tblPhongTro.getValueAt(row, 2).toString();
//        String dienTich = this.tblPhongTro.getValueAt(row, 3).toString();
//        String giaPhong = this.tblPhongTro.getValueAt(row, 4).toString();
//        String tienNghi = this.tblPhongTro.getValueAt(row, 5).toString();
//        String trangThai = this.tblPhongTro.getValueAt(row, 6).toString();
//        
//
//        this.txtTang.setText(idTang);
//        this.txtMaPT.setText(maPT);
//        this.txtLoaiPhong.setText(loaiPhong);
//        this.txtDienTich.setText(dienTich);
//        this.txtGiaPhong.setText(giaPhong);
//        this.txtTienNghi.setText(tienNghi);
//
//        switch (trangThai) {
//            case "Còn trống" ->
//                this.rdoConTrong.setSelected(true);
//            case "Đã thuê" ->
//                this.rdoDaThue.setSelected(true);
//            default ->
//                this.rdoBaoDuong.setSelected(true);
//        }
//        ImageIcon img = new ImageIcon(path);
//        this.lbHinhAnh.setIcon(img);
//        
//         
    }//GEN-LAST:event_tblPhongTroMouseClicked

    private void cbbDienTichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDienTichActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbDienTichActionPerformed

    private void cbbTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTrangThaiActionPerformed
        // TODO add your handling code here:
        ModelPhongTro pt = (ModelPhongTro) cbbTrangThai.getSelectedItem();
        ArrayList<ModelPhongTro> list = repoPT.filterTrangThai(pt.getTrangThai());
        loadTable(list);
    }//GEN-LAST:event_cbbTrangThaiActionPerformed

    private void cbbGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbGiaActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        try {
            if (this.checkIdInsert()) {
                msg = new MessageFrame();
                if (this.check()) {
                    ModelPhongTro pt = start();
                    this.repoPT.insert(pt);
                    msg.showMessage("success", "Thêm thành công!");
                    this.loadTable(this.repoPT.findAll());
                    this.clearForm();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        this.loadTable(this.repoPT.Search(this.txtSearch.getText().trim()));
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lbHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHinhAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser choo = new JFileChooser();
        int ketQua = choo.showOpenDialog(jPanel1);
        if (ketQua == JFileChooser.APPROVE_OPTION) {
            this.path = choo.getSelectedFile().getPath();
            ImageIcon icon = new ImageIcon(path);
            lbHinhAnh.setIcon(icon);
        }
    }//GEN-LAST:event_lbHinhAnhMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button btnDelete;
    private view.component.button.Button btnInsert;
    private view.component.button.Button btnRefresh;
    private view.component.button.Button btnReset;
    private view.component.button.Button btnSearch;
    private view.component.button.Button btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbDienTich;
    private javax.swing.JComboBox<String> cbbGia;
    private javax.swing.JComboBox<String> cbbTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbHinhAnh;
    private javax.swing.JRadioButton rdoBaoDuong;
    private javax.swing.JRadioButton rdoConTrong;
    private javax.swing.JRadioButton rdoDaThue;
    private view.component.table.Table tblPhongTro;
    private view.component.textfield.TextField txtDienTich;
    private view.component.textfield.TextField txtGiaPhong;
    private view.component.textfield.TextField txtLoaiPhong;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtSearch;
    private view.component.textfield.TextField txtTang;
    private view.component.textfield.TextField txtTienNghi;
    // End of variables declaration//GEN-END:variables
}
