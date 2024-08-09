/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import model.Tang.ModelTang;
import repository.Tang.repoTang;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import view.component.message.MessageFrame;
import view.jframe.ViewSignIn;

public class QlyTang extends javax.swing.JPanel {

    private ModelTang mdt = new ModelTang();
    private repoTang repoT = new repoTang();
    private MessageFrame msg;
    private MessageFrame confirm;
    private int role = -1;

    public QlyTang() {
        initComponents();
        this.fillTable(this.repoT.findAll());
<<<<<<< HEAD
        this.tblTang.fixTable(jScrollPane1);
        this.tblTang.fixTable(jScrollPane1);
=======
        checkNV();
    }
    
    private void checkNV() {
        role = ViewSignIn.role;
        if(role == 1) {
            btnInsert.setEnabled(false);
            btnReSetForm.setEnabled(false);
            btnUpdate.setEnabled(false);
            txtTangSo.setEditable(false);
            txtSoPhong.setEditable(false);
            txtGhiChu.setEditable(false);
        }
>>>>>>> 3eba4c5aa005c0c27234ad72d805fa68e1c5c9a4
    }

    private void fillTable(ArrayList<ModelTang> listT) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblTang.getModel();

        dtm.setRowCount(0);

        for (ModelTang modelTang : listT) {
            Object[] row = {
                modelTang.getTangSo(),
                modelTang.getSoPhong(),
                modelTang.getGhiChu()
            };
            dtm.addRow(row);
        }
    }

    private void fillForm(ModelTang tang) {
        this.txtTangSo.setText(Integer.toString(tang.getTangSo()));
        this.txtSoPhong.setText(Integer.toString(tang.getSoPhong()));
        this.txaGhiChu.setText(tang.getGhiChu());
    }

    private void clearForm() {
        this.txtTangSo.setText("");
        this.txtSoPhong.setText("");
        this.txaGhiChu.setText("");
    }

    private boolean checkIdInsert() {
        int tangSo = Integer.parseInt(this.txtTangSo.getText());
        for (ModelTang mdt : this.repoT.findAll()) {
            msg = new MessageFrame();
            if (mdt.getTangSo() == tangSo) {
                msg.showMessage("error", "Tầng này đã tồn tại");
                return false;
            }
        }

        return true;
    }

    private boolean checkIdUpdate() {
        int tangSo = Integer.parseInt(this.txtTangSo.getText());
        for (ModelTang mdt : this.repoT.findAll()) {
            msg = new MessageFrame();
            if (mdt.getTangSo() == tangSo) {
                return true;
            }
        }
        msg.showMessage("error", "Tầng này không tồn tại. Lưu ý: Không thể sửa số tầng!");

        return false;
    }

    private boolean check() {
        msg = new MessageFrame();  
        
        if (this.txtSoPhong.getText().isEmpty()) {
            msg.showMessage("error", "Không bỏ trống mục số phòng!");
            return false;
        } else {
            int soPhong = Integer.parseInt(this.txtSoPhong.getText());
            try {
                if (soPhong <= 0 || soPhong > 10) {
                    msg.showMessage("error", "Số phòng chỉ từ 1-10!");
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Chỉ nhập số!");
            }
        }

        return true;
    }

    private ModelTang start() {
        int soPhong = Integer.parseInt(this.txtSoPhong.getText().trim());
        String ghiChu = this.txaGhiChu.getText().trim();

        return new ModelTang(soPhong, ghiChu);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTangSo = new view.component.textfield.TextField();
        txtSoPhong = new view.component.textfield.TextField();
        btnInsert = new view.component.button.Button();
        btnUpdate = new view.component.button.Button();
        btnReSetForm = new view.component.button.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTang = new view.component.table.Table();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaGhiChu = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtTangSo.setEditable(false);
        txtTangSo.setLabelText("Tầng");

        txtSoPhong.setLabelText("Số phòng");

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

        btnReSetForm.setBackground(new java.awt.Color(153, 204, 255));
        btnReSetForm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnReSetForm.setText("Làm mới");
        btnReSetForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReSetFormActionPerformed(evt);
            }
        });

        tblTang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tầng", "Số phòng", "Ghi chú"
            }
        ));
        tblTang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTang);

        txaGhiChu.setColumns(20);
        txaGhiChu.setRows(5);
        jScrollPane2.setViewportView(txaGhiChu);

        jLabel2.setText("Ghi chú:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(btnReSetForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTangSo, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(txtSoPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReSetForm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(512, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTangSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(562, 562, 562))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("QL Tầng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(1200, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblTangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTangMouseClicked
        // TODO add your handling code here:
        int row = this.tblTang.getSelectedRow();
        this.fillForm(this.repoT.findAll().get(row));

    }//GEN-LAST:event_tblTangMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        try {
            if (check()) {
                confirm.showMessage("message", "Bạn có chắc chắn muốn thêm tầng này không?");
                confirm.setOnOkClicked(() -> {
                    ModelTang mdt = start();
                    this.repoT.insert(mdt);
                    msg.showMessage("success", "Thêm thành công");
                    this.fillTable(this.repoT.findAll());
                    this.clearForm();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        try {
            if (check()) {
                confirm.showMessage("message", "Bạn có chắc chắn muốn cập nhập thông tin cho tầng này không?");
                confirm.setOnOkClicked(() -> {
                    ModelTang mdt = start();
                    this.repoT.update(mdt);
                    msg.showMessage("success", "Sửa thành công");
                    this.fillTable(this.repoT.findAll());
                    this.clearForm();
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnReSetFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReSetFormActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        confirm.showMessage("message", "Bạn có chắc chắn muốn làm mới form không?");
        confirm.setOnOkClicked(() -> {
            this.clearForm();
            msg.showMessage("success", "Làm mới thành công");
        });
    }//GEN-LAST:event_btnReSetFormActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button btnInsert;
    private view.component.button.Button btnReSetForm;
    private view.component.button.Button btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private view.component.table.Table tblTang;
    private javax.swing.JTextArea txaGhiChu;
    private view.component.textfield.TextField txtSoPhong;
    private view.component.textfield.TextField txtTangSo;
    // End of variables declaration//GEN-END:variables
}
