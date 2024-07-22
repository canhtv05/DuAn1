/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.time.Year;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PhongTro.ModelPhongTro;
import repository.PhongTro.repoPhongTro;
import view.component.message.MessageFrame;

/**
 *
 * @author CanhPC
 */
public class QlyPhongTro extends javax.swing.JPanel {

    private repoPhongTro repoPT = new repoPhongTro();
    private MessageFrame msg;

    /**
     * Creates new form QlPhongTro
     */
    public QlyPhongTro() {
        initComponents();
        this.loadTable();
    }

    private void loadTable() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPhongTro.getModel();

        dtm.setRowCount(0);

        for (ModelPhongTro pt : this.repoPT.findAll()) {
            Object[] data = {
                pt.getMaPT(),
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

    private void clearForm() {
        this.txtSearch.setText("");
        this.txtMaPT.setText("");
        this.txtLoaiPhong.setText("");
        this.txtDienTich.setText("");
        this.txtGiaPhong.setText("");
        this.txtTienNghi.setText("");
        this.rdoConTrong.setSelected(true);
    }

    private boolean checkIdInsert() {
        String maPT = this.txtMaPT.getText().trim();
        for (ModelPhongTro pt : this.repoPT.listPT) {
            msg = new MessageFrame();
            if (pt.getMaPT().equalsIgnoreCase(maPT)) {
                msg.showMessage("error", "Mã phòng đã tồn tại.Vui lòng chọn mã khác!");
                return false;
            }
        }
        return true;
    }

    private boolean checkIdUpdate() {
        String maPT = this.txtMaPT.getText().trim();
        for (ModelPhongTro pt : this.repoPT.listPT) {
            msg = new MessageFrame();
            if (!pt.getMaPT().equalsIgnoreCase(maPT)) {
                msg.showMessage("error", "Mã phòng không tồn tại!" + "\nLưu ý: Không thể sửa mã phòng!!");
                return false;
            }
        }
        return true;
    }

    private boolean check() {
        msg = new MessageFrame();
        if (this.txtMaPT.getText().isEmpty()) {
            msg.showMessage("error", "Không để trống mục mã!");
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
        String maPT = this.txtMaPT.getText().trim();
        String loaiPhong = this.txtLoaiPhong.getText().trim();
        float dienTich = Float.parseFloat(this.txtDienTich.getText().trim());
        float giaPhong = Float.parseFloat(this.txtGiaPhong.getText().trim());
        String tienNghi = this.txtTienNghi.getText().trim();

        int trangThai = this.rdoConTrong.isSelected() ? 0 : this.rdoDaThue.isSelected() ? 1 : 2;

        return new ModelPhongTro(maPT, loaiPhong, dienTich, giaPhong, tienNghi, trangThai);
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
        jLabel1 = new javax.swing.JLabel();
        txtMaPT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLoaiPhong = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDienTich = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGiaPhong = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTienNghi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoConTrong = new javax.swing.JRadioButton();
        rdoDaThue = new javax.swing.JRadioButton();
        rdoBaoDuong = new javax.swing.JRadioButton();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhongTro = new javax.swing.JTable();
        btnUpdate = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnResetForm = new javax.swing.JButton();
        btnRefersh = new javax.swing.JButton();

        jLabel1.setText("Mã Phòng");

        jLabel2.setText("Loại Phòng");

        jLabel3.setText("Diện tích");

        jLabel4.setText("Giá phòng");

        jLabel5.setText("Tiện nghi");

        jLabel6.setText("Trạng thái");

        buttonGroup1.add(rdoConTrong);
        rdoConTrong.setText("Còn trống");

        buttonGroup1.add(rdoDaThue);
        rdoDaThue.setText("Đã thuê");

        buttonGroup1.add(rdoBaoDuong);
        rdoBaoDuong.setText("Đang bảo dưỡng");

        btnSearch.setText("Tìm kiếm");

        tblPhongTro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phòng", "Loại phòng", "Diện tích(m²)", "Giá phòng", "Tiện nghi", "Trạng thái"
            }
        ));
        tblPhongTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongTroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhongTro);

        btnUpdate.setText("Cập nhập");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnResetForm.setText("Làm mới");
        btnResetForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetFormActionPerformed(evt);
            }
        });

        btnRefersh.setText("Refresh");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel3))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtDienTich, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                                                    .addComponent(txtLoaiPhong))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(23, 23, 23)
                                                .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(186, 186, 186)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(btnRefersh)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rdoConTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdoDaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnResetForm)
                                                .addGap(29, 29, 29)
                                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(28, 28, 28)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnUpdate)
                                            .addComponent(rdoBaoDuong))))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSearch)
                        .addGap(260, 260, 260))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(rdoConTrong)
                    .addComponent(rdoDaThue)
                    .addComponent(rdoBaoDuong))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnInsert)
                    .addComponent(btnResetForm)
                    .addComponent(btnRefersh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblPhongTroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongTroMouseClicked
        // TODO add your handling code here:
        int row = this.tblPhongTro.getSelectedRow();
        msg = new MessageFrame();

        if (row == -1) {
            msg.showMessage("error", "Bạn chưa chọn dữ liệu trên bảng!");
            return;
        }

        String maPT = this.tblPhongTro.getValueAt(row, 0).toString();
        String loaiPhong = this.tblPhongTro.getValueAt(row, 1).toString();
        String dienTich = this.tblPhongTro.getValueAt(row, 2).toString();
        String giaPhong = this.tblPhongTro.getValueAt(row, 3).toString();
        String tienNghi = this.tblPhongTro.getValueAt(row, 4).toString();
        String trangThai = this.tblPhongTro.getValueAt(row, 5).toString();

        this.txtMaPT.setText(maPT);
        this.txtLoaiPhong.setText(loaiPhong);
        this.txtDienTich.setText(dienTich);
        this.txtGiaPhong.setText(giaPhong);
        this.txtTienNghi.setText(tienNghi);

        switch (trangThai) {
            case "Còn trống" ->
                this.rdoConTrong.setSelected(true);
            case "Đã thuê" ->
                this.rdoDaThue.setSelected(true);
            default ->
                this.rdoBaoDuong.setSelected(true);
        }

    }//GEN-LAST:event_tblPhongTroMouseClicked

    private void btnResetFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetFormActionPerformed
        // TODO add your handling code here:
        this.clearForm();

    }//GEN-LAST:event_btnResetFormActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        try {
            if (this.checkIdInsert()) {
                msg = new MessageFrame();
                if (this.check()) {
                    ModelPhongTro pt = start();
                    this.repoPT.insert(pt);
                    msg.showMessage("success", "Thêm thành công!");
                    this.loadTable();
                    this.clearForm();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        try {
            if (this.check()) {
                msg = new MessageFrame();
                if (this.checkIdUpdate()) {
                    ModelPhongTro pt = start();
                    this.repoPT.update(pt);
                    msg.showMessage("success", "Sửa thành công");
                    this.loadTable();
                    this.clearForm();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnRefersh;
    private javax.swing.JButton btnResetForm;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoBaoDuong;
    private javax.swing.JRadioButton rdoConTrong;
    private javax.swing.JRadioButton rdoDaThue;
    private javax.swing.JTable tblPhongTro;
    private javax.swing.JTextField txtDienTich;
    private javax.swing.JTextField txtGiaPhong;
    private javax.swing.JTextField txtLoaiPhong;
    private javax.swing.JTextField txtMaPT;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTienNghi;
    // End of variables declaration//GEN-END:variables
}
