/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import model.TaiSan.ModelTaiSan;
import repository.TaiSan.repoTaiSan;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import view.component.message.MessageFrame;

/**
 *
 * @author CanhPC
 */
public class QlyTaiSan extends javax.swing.JPanel {

    private ModelTaiSan mdTs = new ModelTaiSan();
    private repoTaiSan repoTS = new repoTaiSan();
    private MessageFrame msg;
    private MessageFrame confirm;

    public QlyTaiSan() {
        initComponents();
        this.fillTable(this.repoTS.getAll());
    }

    private void fillTable(ArrayList<ModelTaiSan> listTs) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblTaiSan.getModel();

        dtm.setRowCount(0);

        for (ModelTaiSan ts : listTs) {
            Object[] rowData = {
                ts.getIdTaiSan(),
                ts.getTenTaiSan(),
                ts.getGiaTaiSan(),
                ts.getSoLuong(),
                ts.getGhiChu()
            };
            dtm.addRow(rowData);
        }
    }

    private void clearForm() {
        this.txtSearch.setText("");
        this.txtId.setText("");
        this.txtTenTS.setText("");
        this.txtGiaTS.setText("");
        this.txtSoLuong.setText("");
        this.txaGhiChu.setText("");
    }

    private ModelTaiSan readForm() {
        int idTaiSan = Integer.parseInt(this.txtId.getText().trim());
        String tenTaiSan = this.txtTenTS.getText().trim();
        float giaTaiSan = Float.parseFloat(this.txtGiaTS.getText().trim());
        int soLuong = Integer.parseInt(this.txtSoLuong.getText().trim());
        String ghiChu = this.txaGhiChu.getText().trim();

        return new ModelTaiSan(idTaiSan, tenTaiSan, giaTaiSan, soLuong, ghiChu);
    }

    private ModelTaiSan readFormInsert() {
        String tenTaiSan = this.txtTenTS.getText().trim();
        float giaTaiSan = Float.parseFloat(this.txtGiaTS.getText().trim());
        int soLuong = Integer.parseInt(this.txtSoLuong.getText().trim());
        String ghiChu = this.txaGhiChu.getText().trim();

        return new ModelTaiSan(tenTaiSan, giaTaiSan, soLuong, ghiChu);
    }

    private boolean checkNull() {
        msg = new MessageFrame();
        if (this.txtTenTS.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không bỏ trống Tên tài sản!");
            this.txtTenTS.requestFocus();
            return false;
        }

        if (this.txtGiaTS.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không bỏ trống Giá tài sản!");
            this.txtGiaTS.requestFocus();
            return false;
        } else {
            try {
                float giaTaiSan = Float.parseFloat(this.txtGiaTS.getText().trim());
                if (giaTaiSan <= 0 || giaTaiSan > 10000000) {
                    msg.showMessage("error", "Giá tài sản chỉ trong khoảng 0-10tr");
                    this.txtGiaTS.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Giá tài sản chỉ nhập số!");
                this.txtGiaTS.requestFocus();
                return false;
            }
        }

        if (this.txtSoLuong.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không bỏ trống Số lượng tài sản!");
            this.txtSoLuong.requestFocus();
            return false;
        } else {
            try {
                int soLuong = Integer.parseInt(this.txtSoLuong.getText().trim());
                if (soLuong <= 0 || soLuong > 100) {
                    msg.showMessage("error", "Số lượng tài sản chỉ trong khoảng 1-50");
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Số lượng chỉ nhập số!");
                return false;
            }
        }

        return true;
    }

    private boolean checkTenTs() {

        for (ModelTaiSan ts : this.repoTS.getAll()) {
            String tenTs = this.txtTenTS.getText().trim();
            msg = new MessageFrame();
            if (ts.getTenTaiSan().equalsIgnoreCase(tenTs)) {
                msg.showMessage("error", "Tài sản này đã tồn tại!");
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTaiSan = new view.component.table.Table();
        txtId = new view.component.textfield.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaGhiChu = new javax.swing.JTextArea();
        txtTenTS = new view.component.textfield.TextField();
        txtGiaTS = new view.component.textfield.TextField();
        txtSoLuong = new view.component.textfield.TextField();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new view.component.textfield.TextField();
        btnSearch = new view.component.button.Button();
        btnInsert = new view.component.button.Button();
        btnUpdate = new view.component.button.Button();
        btnReset = new view.component.button.Button();
        btnDelete = new view.component.button.Button();
        btnRefresh = new view.component.button.Button();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblTaiSan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên tài sản", "Giá tài sản/Cái", "Số lượng", "Ghi chú"
            }
        ));
        tblTaiSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTaiSanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTaiSan);

        txtId.setEditable(false);
        txtId.setLabelText("ID");

        txaGhiChu.setColumns(20);
        txaGhiChu.setRows(5);
        txaGhiChu.setBorder(null);
        jScrollPane2.setViewportView(txaGhiChu);

        txtTenTS.setLabelText("Tên tài sản");

        txtGiaTS.setLabelText("Giá tài sản");

        txtSoLuong.setLabelText("Số lượng");

        jLabel2.setText("Ghi chú:");

        txtSearch.setLabelText("Tìm kiếm...");

        btnSearch.setBackground(new java.awt.Color(153, 204, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(153, 204, 255));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(153, 204, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_downloading_updates_24px.png"))); // NOI18N
        btnUpdate.setText("Cập nhập");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 204, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 153, 153));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_24px_1.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(204, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtGiaTS, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTenTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaTS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 177, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Tài sản");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblTaiSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiSanMouseClicked
        // TODO add your handling code here:
        int row = this.tblTaiSan.getSelectedRow();

        String id = this.tblTaiSan.getValueAt(row, 0).toString();
        String tenTaiSan = this.tblTaiSan.getValueAt(row, 1).toString();
        String giaTaiSan = this.tblTaiSan.getValueAt(row, 2).toString();
        String soLuong = this.tblTaiSan.getValueAt(row, 3).toString();
        String ghiChu = this.tblTaiSan.getValueAt(row, 4).toString();

        this.txtId.setText(id);
        this.txtTenTS.setText(tenTaiSan);
        this.txtGiaTS.setText(giaTaiSan);
        this.txtSoLuong.setText(soLuong);
        this.txaGhiChu.setText(ghiChu);
    }//GEN-LAST:event_tblTaiSanMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:

        try {
            msg = new MessageFrame();
            confirm = new MessageFrame();
            if (this.checkNull()) {
                if (this.readFormInsert() != null) {
                    if (this.checkTenTs()) {
                        confirm.showMessage("message", "Bạn có chắc chắn muốn thêm tài sản này không?");
                        confirm.setOnOkClicked(() -> {
                            if (this.repoTS.insert(this.readFormInsert()) > 0) {
                                msg.showMessage("success", "Thêm thành công.");
                                this.fillTable(this.repoTS.getAll());
                            } else {
                                msg.showMessage("error", "Thêm thất bại.");
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        msg = new MessageFrame();
        confirm = new MessageFrame();
        try {
            if (this.checkNull()) {
                if (this.readForm() != null) {
                    confirm.showMessage("message", "Bạn có chắc chắn muốn cập nhập thông tin cho tài sản này không?");
                    confirm.setOnOkClicked(() -> {
                        if (this.repoTS.update(this.readForm()) > 0) {
                            msg.showMessage("success", "Cập nhập thành công.");
                            this.fillTable(this.repoTS.getAll());
                        } else {
                            msg.showMessage("error", "Cập nhập thất bại.");
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        msg = new MessageFrame();
        confirm = new MessageFrame();
        confirm.showMessage("message", "Bạn có chắc chắn muốn làm mới form không?");
        confirm.setOnOkClicked(() -> {
            this.clearForm();
            msg.showMessage("success", "Làm mới thành công.");
        });
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        msg = new MessageFrame();
        confirm = new MessageFrame();
        try {
            if (this.checkNull()) {
                if (this.readForm() != null) {
                    confirm.showMessage("message", "Bạn có chắc chắn muốn xóa tài sản này không? Lưu lý: Sau khi xóa sẽ không thể khôi phục!");
                    confirm.setOnOkClicked(() -> {
                        if (this.repoTS.delete(this.readForm()) > 0) {
                            msg.showMessage("success", "Xóa thành công.");
                            this.fillTable(this.repoTS.getAll());
                            this.clearForm();
                        } else {
                            msg.showMessage("error", "Xóa thất bại.");
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        msg = new MessageFrame();
        String Search = this.txtSearch.getText().trim();

        if (Search.equals("")) {
            msg.showMessage("error", "Vui lòng nhập dữ liệu cần tìm!");
            return;
        }
        this.fillTable(this.repoTS.Search(this.txtSearch.getText().trim()));
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        confirm.showMessage("message", "Bạn có chắc chắn muốn làm mới dữ liệu trên bảng không?");
        confirm.setOnOkClicked(() -> {
            this.fillTable(this.repoTS.getAll());
            msg.showMessage("success", "Refresh thành công.");
        });
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button btnDelete;
    private view.component.button.Button btnInsert;
    private view.component.button.Button btnRefresh;
    private view.component.button.Button btnReset;
    private view.component.button.Button btnSearch;
    private view.component.button.Button btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private view.component.table.Table tblTaiSan;
    private javax.swing.JTextArea txaGhiChu;
    private view.component.textfield.TextField txtGiaTS;
    private view.component.textfield.TextField txtId;
    private view.component.textfield.TextField txtSearch;
    private view.component.textfield.TextField txtSoLuong;
    private view.component.textfield.TextField txtTenTS;
    // End of variables declaration//GEN-END:variables
}
