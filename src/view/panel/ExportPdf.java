package view.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ExportPdf extends javax.swing.JFrame {

    public ExportPdf() {
        initComponents();
        init();
    }

    private void init() {
        tbl.fixTable(jScrollPane1);
        jPanel1.setBackground(new Color(255, 255, 255));
        String maPTString = QlyHoaDon.maPhongString;
        double tienDien = QlyHoaDon.tienDien;
        double tienNuoc = QlyHoaDon.tienNuoc;
        double tienDV = QlyHoaDon.tienDichVu;
        double tongTien = QlyHoaDon.tongTien;

        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{maPTString, tienDien, tienNuoc, tienDV, tongTien});
        txtMaPT.setText(maPTString);
        txtTienDien.setText(tienDien + "");
        txtTienNuoc.setText(tienNuoc + "");
        txtTienDV.setText(tienDV + "");
        txtThanhTien.setText(tongTien + "đ");
    }

    private BufferedImage getPanelImage(JPanel panel, Component excludeComponent) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        panel.printAll(g2d);
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(excludeComponent.getX(), excludeComponent.getY(), excludeComponent.getWidth(), excludeComponent.getHeight());
        g2d.dispose();
        return image;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new view.component.table.Table();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaPT = new javax.swing.JLabel();
        txtTienDien = new javax.swing.JLabel();
        txtTienNuoc = new javax.swing.JLabel();
        txtTienDV = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JLabel();
        btnXuat = new view.component.button.MyButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phòng", "Tiền điện", "Tiền nước", "Tiền dịch vụ", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 25, 472, 358));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 1, -1, 408));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã phòng:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 52, 70, 22));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tiền điện:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 86, 70, 22));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tiền nước:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 70, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tiền dịch vụ:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 154, -1, 22));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Thành tiền:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 209, 70, 24));

        txtMaPT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMaPT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 52, 120, 22));

        txtTienDien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTienDien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(txtTienDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 86, 120, 22));

        txtTienNuoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTienNuoc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(txtTienNuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 120, 120, 22));

        txtTienDV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTienDV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(txtTienDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 154, 120, 22));

        txtThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtThanhTien.setForeground(new java.awt.Color(255, 0, 0));
        txtThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 209, 120, 24));

        btnXuat.setBackground(new java.awt.Color(204, 255, 255));
        btnXuat.setText("Xuất PDF");
        btnXuat.setBorderColor(new java.awt.Color(204, 255, 255));
        btnXuat.setColor(new java.awt.Color(204, 255, 255));
        btnXuat.setColorClick(new java.awt.Color(153, 255, 255));
        btnXuat.setColorOver(new java.awt.Color(204, 255, 255));
        btnXuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuat.setRadius(10);
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });
        jPanel1.add(btnXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 314, 120, 41));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_multiply_35px.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(907, 1, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(954, 421));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        ExportPdf.this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        try {
            btnXuat.setVisible(false);
            jLabel6.setVisible(false);
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Print Component");

            job.setPrintable(new Printable() {
                public int print(Graphics2D graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }

                    double scaleX = pageFormat.getImageableWidth() / jPanel2.getWidth();
                    double scaleY = pageFormat.getImageableHeight() / jPanel2.getHeight();
                    double scale = Math.min(scaleX, scaleY);
                    graphics.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    graphics.scale(scale, scale);

                    jPanel2.printAll(graphics);
                    return PAGE_EXISTS;
                }

                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    return print((Graphics2D) graphics, pageFormat, pageIndex);
                }
            });

            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
        }
        ExportPdf.this.dispose();

    }//GEN-LAST:event_btnXuatActionPerformed

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
            java.util.logging.Logger.getLogger(ExportPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExportPdf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnXuat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private view.component.table.Table tbl;
    private javax.swing.JLabel txtMaPT;
    private javax.swing.JLabel txtThanhTien;
    private javax.swing.JLabel txtTienDV;
    private javax.swing.JLabel txtTienDien;
    private javax.swing.JLabel txtTienNuoc;
    // End of variables declaration//GEN-END:variables
}
