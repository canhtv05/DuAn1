/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.KhachThue.ModelKhachThue;
import repository.KhachThue.repositoriesKhachThue;
import view.component.message.MessageFrame;

/**
 *
 * @author CanhPC
 */
public class QlyKhachThue extends javax.swing.JPanel {
    private repository.KhachThue.repositoriesKhachThue rpKT = new repositoriesKhachThue();
    private DefaultTableModel mol = new DefaultTableModel();
    private MessageFrame mesg;
    private int index = -1;
    /**
     * Creates new form QliKhachThue
     */
    public void fillTable(ArrayList<model.KhachThue.ModelKhachThue> list){
        mol = (DefaultTableModel) tblKhachThue.getModel();
        mol.setNumRows(0);
        for (ModelKhachThue x : list) {
            mol.addRow(x.toDataRow());
        }
    }
    
    public QlyKhachThue() {
        initComponents();
        this.fillTable(rpKT.getAll());
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
        txtTimKhachThue = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        txtHoTen = new view.component.textfield.TextField();
        jPanel2 = new javax.swing.JPanel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtMaPT = new view.component.textfield.TextField();
        txtDienThoai = new view.component.textfield.TextField();
        txtEmail = new view.component.textfield.TextField();
        txtCCCD = new view.component.textfield.TextField();
        txtMaKT = new view.component.textfield.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachThue = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnThem = new view.component.button.Button();
        btnSua = new view.component.button.Button();
        btnLamMoi = new view.component.button.Button();
        btnTimKiem = new view.component.button.Button();

        txtTimKhachThue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKhachThueActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(51, 153, 255));

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        txtDiaChi.setBorder(javax.swing.BorderFactory.createTitledBorder("Địa chỉ"));
        jScrollPane2.setViewportView(txtDiaChi);

        txtHoTen.setLabelText("Họ tên");

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày sinh"));

        txtNgaySinh.setToolTipText("");
        txtNgaySinh.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(153, 204, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Giới tính"));

        buttonGroup1.add(rdoNam);
        rdoNam.setText(" Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(rdoNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoNu)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdoNam)
                .addComponent(rdoNu))
        );

        txtMaPT.setLabelText("Mã phòng trọ");

        txtDienThoai.setLabelText("Điện thoại");

        txtEmail.setLabelText("Email");

        txtCCCD.setLabelText("CCCD");
        txtCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCCCDActionPerformed(evt);
            }
        });

        txtMaKT.setLabelText("Mã khách thuê");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCCCD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        tblKhachThue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã KT", "Mã PT", "Họ tên", "Ngày sinh", "Giới tính", "Điện thoại", "Email", "Địa chỉ", "CCCD"
            }
        ));
        tblKhachThue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachThueMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachThue);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 255));
        jLabel11.setText("Quản lý khách thuê");

        btnThem.setBackground(new java.awt.Color(153, 255, 0));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 255, 204));
        btnSua.setText("Cập nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(txtTimKhachThue, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(104, 104, 104)
                                        .addComponent(jLabel1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKhachThue, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        txtTimKhachThue.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKhachThueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKhachThueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKhachThueActionPerformed

    private void tblKhachThueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachThueMouseClicked
        // TODO add your handling code here:
        index=tblKhachThue.getSelectedRow();
        txtMaKT.setText(tblKhachThue.getValueAt(index, 0).toString());
        txtMaPT.setText(tblKhachThue.getValueAt(index, 1).toString());
        txtHoTen.setText(tblKhachThue.getValueAt(index, 2).toString());
        txtNgaySinh.setDate( (Date)tblKhachThue.getValueAt(index, 3));
        if (tblKhachThue.getValueAt(index, 4).toString().equalsIgnoreCase("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        
        txtDienThoai.setText(tblKhachThue.getValueAt(index, 5).toString());
        txtEmail.setText(tblKhachThue.getValueAt(index, 6).toString());
        txtDiaChi.setText(tblKhachThue.getValueAt(index, 7).toString());
        txtCCCD.setText(tblKhachThue.getValueAt(index, 8).toString());
    }//GEN-LAST:event_tblKhachThueMouseClicked

    private void txtCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCCCDActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (readForm()!=null) {
            mesg = new MessageFrame();
            if (rpKT.them(readForm())>0) {
                
//                JOptionPane.showMessageDialog(this, "Thêm thành công")
                mesg.showMessage("success", "Thêm thành công");
                fillTable(rpKT.getAll());
            } else {
                mesg.showMessage("error", "Thêm thất bại");
                
                
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        mesg = new MessageFrame();
        if (readForm()!=null) {
            if (rpKT.sua(readForm())>0) {
                mesg.showMessage("success", "cập nhật thành công");
//                JOptionPane.showConfirmDialog(this, "cập nhật thành công");
                fillTable(rpKT.getAll());
            } else {
                mesg.showMessage("error", "cập nhật thất bại");
//                JOptionPane.showConfirmDialog(this, "cập nhật thất bại");
            }
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        txtTimKhachThue.setText("");
        txtMaKT.setText("");
        txtMaPT.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setDate(null);
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        txtCCCD.setText("");
        fillTable(rpKT.getAll());
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        fillTable(rpKT.timKiem(txtTimKhachThue.getText()));
    }//GEN-LAST:event_btnTimKiemActionPerformed

    public model.KhachThue.ModelKhachThue readForm(){
        mesg=new MessageFrame();
        String maKT, hoTen, dienThoai, email, diaChi, cccd, maPT;
        Date ngaySinh;
        boolean gioiTinh;
        maKT=txtMaKT.getText().trim();
        maPT=txtMaPT.getText().trim();
        hoTen=txtHoTen.getText().trim();
        ngaySinh= txtNgaySinh.getDate(); 
        gioiTinh=true;
        if (rdoNu.isSelected()) {
            gioiTinh=false;
        }
        dienThoai=txtDienThoai.getText().trim();
        email=txtEmail.getText().trim();
        diaChi=txtDiaChi.getText().trim();
        cccd=txtCCCD.getText().trim();
        if (maKT.isEmpty()) {
            mesg.showMessage("error", "chưa nhập mã Khách thuê");
//            JOptionPane.showMessageDialog(this, "chưa nhập mã KT");
            txtMaKT.requestFocus();
            return null;
        }
        if (maPT.isEmpty()) {
            mesg.showMessage("error", "chưa nhập mã Phòng trọ");
//            JOptionPane.showMessageDialog(this, "chưa nhập mã PT");
            txtMaPT.requestFocus();
            return null;
        }
        if (hoTen.isEmpty()) {
            mesg.showMessage("error", "chưa nhập Họ tên");
//            JOptionPane.showMessageDialog(this, "chưa nhập Họ tên");
            txtHoTen.requestFocus();
            return null;
        }
        if (ngaySinh.equals("")) {
            mesg.showMessage("error", "chưa nhập Ngày sinh");
//            JOptionPane.showMessageDialog(this, "chưa nhập ngày sinh");
            return null;
        }
        if (rdoNam.isSelected()==false && rdoNu.isSelected()==false) {
            mesg.showMessage("error", "chưa lựa chọn giới tính");
//            JOptionPane.showMessageDialog(this, "chưa lựa chọn giới tính");
            return null;
        }
        if (dienThoai.isEmpty()) {
            mesg.showMessage("error", "chưa nhập số điện thoại");
//            JOptionPane.showMessageDialog(this, "chưa nhập số điện thoại");
            txtDienThoai.requestFocus();
            return null;
        }
        if (email.isEmpty()) {
            mesg.showMessage("error", "chưa nhập Email");
//            JOptionPane.showMessageDialog(this, "chưa nhập email");
            txtEmail.requestFocus();
            return null;
        }
        if (email.endsWith("@gmail.com")==false) {
            mesg.showMessage("error", "Email chưa đúng định dạng");
//            JOptionPane.showMessageDialog(this, "email chưa đúng định dạng");
            txtEmail.requestFocus();
            return null;
        }
        if (cccd.isEmpty()) {
            mesg.showMessage("error", "chưa nhập CCCD");

//            JOptionPane.showMessageDialog(this, "chưa nhập CCCD");
            txtCCCD.requestFocus();
            return null;
        }
        if (diaChi.isEmpty()) {
            mesg.showMessage("error", "chưa nhập địa chỉ");

//            JOptionPane.showMessageDialog(this, "chưa nhập địa chỉ");
            txtDiaChi.requestFocus();
            return null;
        }
        return new model.KhachThue.ModelKhachThue(maKT, maPT, hoTen, ngaySinh, gioiTinh, dienThoai, email, diaChi, cccd);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button btnLamMoi;
    private view.component.button.Button btnSua;
    private view.component.button.Button btnThem;
    private view.component.button.Button btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblKhachThue;
    private view.component.textfield.TextField txtCCCD;
    private javax.swing.JTextArea txtDiaChi;
    private view.component.textfield.TextField txtDienThoai;
    private view.component.textfield.TextField txtEmail;
    private view.component.textfield.TextField txtHoTen;
    private view.component.textfield.TextField txtMaKT;
    private view.component.textfield.TextField txtMaPT;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtTimKhachThue;
    // End of variables declaration//GEN-END:variables
}
