package view.jframe;

import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import repository.exportpdf.RepoExportPDF;
import view.component.message.MessageFrame;

public class LuaChonNgayXuatExcel extends javax.swing.JFrame {

    private RepoExportPDF repo;
    private int indexNam = -1;
    private String ngayLocString;
    public static String ngayXuatString;

    public LuaChonNgayXuatExcel() {
        initComponents();
        this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        cbbLocNam.setSelectedIndex(-1);
        init();
    }

    private void init() {
        repo = new RepoExportPDF();
        ArrayList<String> arrChonNgay = repo.getNgayLap(indexNam);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String string : arrChonNgay) {
            model.addElement(string);
        }
        cbbNgayXuatHD.setModel(model);
        ngayLocString = cbbNgayXuatHD.getSelectedItem() + "";
        if (arrChonNgay.isEmpty()) {
            myButton1.setEnabled(false);
        } else {
            myButton1.setEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new view.component.roundpanel.RoundedPanel();
        myButton2 = new view.component.button.MyButton();
        myButton1 = new view.component.button.MyButton();
        cbbLocNam = new view.component.combobox.Combobox();
        cbbNgayXuatHD = new view.component.combobox.Combobox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        myButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_cancel_24px.png"))); // NOI18N
        myButton2.setText("Hủy");
        myButton2.setBorderColor(new java.awt.Color(153, 204, 204));
        myButton2.setColor(new java.awt.Color(204, 255, 255));
        myButton2.setColorClick(new java.awt.Color(153, 255, 255));
        myButton2.setColorOver(new java.awt.Color(204, 255, 255));
        myButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        myButton2.setRadius(10);
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });

        myButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_24px.png"))); // NOI18N
        myButton1.setText("Xuất");
        myButton1.setBorderColor(new java.awt.Color(153, 204, 204));
        myButton1.setColor(new java.awt.Color(204, 255, 255));
        myButton1.setColorClick(new java.awt.Color(153, 255, 255));
        myButton1.setColorOver(new java.awt.Color(204, 255, 255));
        myButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        myButton1.setRadius(10);
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });

        cbbLocNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028" }));
        cbbLocNam.setLabeText("Lọc năm");
        cbbLocNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLocNamActionPerformed(evt);
            }
        });

        cbbNgayXuatHD.setLabeText("Chọn ngày xuất hóa đơn");
        cbbNgayXuatHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNgayXuatHDActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHỌN NGÀY XUẤT HÓA ĐƠN");

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(cbbNgayXuatHD, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(cbbLocNam, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbbNgayXuatHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbLocNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(myButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(400, 272));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_myButton2ActionPerformed

    private void cbbLocNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLocNamActionPerformed
        Object selectedItem = cbbLocNam.getSelectedItem();

        if (selectedItem != null && !selectedItem.toString().trim().isEmpty()) {
            try {
                indexNam = Integer.parseInt(selectedItem.toString());
            } catch (NumberFormatException e) {
                indexNam = -1;
            }
        } else {
            indexNam = -1;
        }
        init();

    }//GEN-LAST:event_cbbLocNamActionPerformed

    private void cbbNgayXuatHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNgayXuatHDActionPerformed
        ngayLocString = cbbNgayXuatHD.getSelectedItem() + "";
    }//GEN-LAST:event_cbbNgayXuatHDActionPerformed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
        ngayXuatString = cbbNgayXuatHD.getSelectedItem() + "";
        if (ngayXuatString.isEmpty()) {
            MessageFrame mess = new MessageFrame();
            mess.showMessage("error", "Ngày xuất hóa đơn không tồn tại.");
            return;
        }
        RepoExportPDF repoExportPDF = new RepoExportPDF();

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

        String ngayLap = null;
        try {
            Date date = input.parse(ngayXuatString);
            ngayLap = output.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MessageFrame mess = new MessageFrame();
        if (repoExportPDF.exportToExcel(ngayLap)) {
            mess.showMessage("success", "Xuất đơn thành công.");
            dispose();
        }else {
            mess.showMessage("error", "Xuất đơn thất bại.");
            dispose();
        }
    }//GEN-LAST:event_myButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LuaChonNgayXuatExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LuaChonNgayXuatExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LuaChonNgayXuatExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LuaChonNgayXuatExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LuaChonNgayXuatExcel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.combobox.Combobox cbbLocNam;
    private view.component.combobox.Combobox cbbNgayXuatHD;
    private javax.swing.JLabel jLabel1;
    private view.component.button.MyButton myButton1;
    private view.component.button.MyButton myButton2;
    private view.component.roundpanel.RoundedPanel roundedPanel1;
    // End of variables declaration//GEN-END:variables
}
