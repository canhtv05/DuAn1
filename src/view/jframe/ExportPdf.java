package view.jframe;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import model.tiendichvu.ModelTienDichVu;
import model.tiendien.ModelTienDien;
import model.tiennuoc.ModelTienNuoc;
import repository.exportpdf.RepoExportPDF;
import repository.hoadon.RepoHoaDon;
import view.panel.QlyHoaDon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import view.component.message.MessageFrame;
import view.component.qrcode.DeleteAllFile;

public class ExportPdf extends javax.swing.JFrame {

    private RepoExportPDF repo = new RepoExportPDF();
    private RepoHoaDon repoHoaDon;
    private int maHD = QlyHoaDon.maHD;
    private ArrayList<Integer> listMaHD;
    public static String qrPath;
    public static Map<Integer, String> showIcon = new HashMap<>();

    public ExportPdf() {
        initComponents();
        showIcon = new HashMap<>();
        this.setVisible(true);
        this.setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        tblTienDien.fixTable(jScrollPane1);
        tblTienNuoc.fixTable(jScrollPane2);
        tblTienDV.fixTable(jScrollPane3);
        if (!QlyHoaDon.show) {
            init();
            printAllHoaDon();
        } else {
            showHoaDon(maHD);
        }
        DeleteAllFile.main(new String[]{});
    }

    private void printAllHoaDon() {
        for (Integer maHD : listMaHD) {
            showHoaDon(maHD);
            chupAnhVaLuu(maHD);
        }

        SwingUtilities.invokeLater(() -> {
            MessageFrame mess = new MessageFrame();
            mess.showMessage("success", "Xuất hóa đơn thành công.");
            ExportPdf.this.dispose();
        });

    }

    private void chupAnhVaLuu(int maHD) {
        jLabel11.setVisible(false);
        try {
            BufferedImage bi = new BufferedImage(roundedPanel1.getWidth(), roundedPanel1.getHeight(), BufferedImage.TYPE_INT_RGB);
            roundedPanel1.paint(bi.getGraphics());
            String path = "C:\\Users\\PC\\OneDrive\\Desktop\\hoadon\\hoadon_" + maHD + ".jpg";
            ImageIO.write(bi, "jpg", new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jLabel11.setVisible(true);
    }

    private void init() {
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

        String ngayLap = null;
        if (LuaChonNgayXuatPDF.ngayXuatString != null) {
            try {
                Date date = input.parse(LuaChonNgayXuatPDF.ngayXuatString);
                ngayLap = output.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        listMaHD = repo.getMaHD(ngayLap);
        for (Integer integer : listMaHD) {
            qrPath = view.component.qrcode.RenderQrCode.renderQRCode(integer);
            showIcon.put(integer, qrPath);
        }
        lblMaQr.setIcon(new ImageIcon(showIcon.get(maHD)));
        showHoaDon(maHD);
    }

    private void showHoaDon(int maHD) {
        if (QlyHoaDon.show) {
            String path2 = QlyHoaDon.showIcon2.get(maHD);
            lblMaQr.setIcon(new ImageIcon(path2));
        } else {
            String path = showIcon.get(maHD);
            lblMaQr.setIcon(new ImageIcon(path));
        }

        if (showIcon.isEmpty()) {
            String path2 = view.component.qrcode.RenderQrCode.renderQRCode(maHD);
            lblMaQr.setIcon(new ImageIcon(path2));
        }

        repoHoaDon = new RepoHoaDon();
        ModelTienDien modelTienDien = repoHoaDon.showTienDien(maHD);
        ModelTienNuoc modelTienNuoc = repoHoaDon.showTienNuoc(maHD);
        ModelTienDichVu modelTienDichVu = repoHoaDon.showTienDV(maHD);
        txtMaPhong.setText(modelTienDichVu.getMaPT());
        ArrayList<String> khachThue = repoHoaDon.getKhachThue(modelTienDichVu.getMaPT());
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < khachThue.size(); i++) {
            str.append(khachThue.get(i));
            if (i < khachThue.size() - 1) {
                str.append(", ");
            }
        }
        txtKhachThue.setText(str.toString());
        txtNguoiTao.setText(repo.getTenNV(maHD));

        DefaultTableModel model1 = (DefaultTableModel) tblTienDien.getModel();
        model1.setRowCount(0);
        DefaultTableModel model2 = (DefaultTableModel) tblTienNuoc.getModel();
        model2.setRowCount(0);
        DefaultTableModel model3 = (DefaultTableModel) tblTienDV.getModel();
        model3.setRowCount(0);
        if (modelTienDien != null) {
            model1.addRow(new Object[]{
                modelTienDien.getNgayBD(),
                modelTienDien.getNgayKT(),
                modelTienDien.getChiSoDau(),
                modelTienDien.getChiSoCuoi(),
                modelTienDien.getSoDien(),
                modelTienDien.getGiaTien(),
                modelTienDien.getThanhTien()
            });
        }

        if (modelTienNuoc != null) {
            model2.addRow(new Object[]{
                modelTienNuoc.getNgayBD(),
                modelTienNuoc.getNgayKT(),
                modelTienNuoc.getDauNguoi(),
                modelTienNuoc.getGiaTien(),
                modelTienNuoc.getThanhTien()
            });
        }

        if (modelTienDichVu != null) {
            model3.addRow(new Object[]{
                modelTienDichVu.getNgayBD(),
                modelTienDichVu.getNgayKT(),
                modelTienDichVu.getTenDV(),
                modelTienDichVu.getDauNguoi(),
                modelTienDichVu.getGiaTien(),
                modelTienDichVu.getThanhTien()
            });

        }

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        double giaPhong = QlyHoaDon.giaTienPhong;
        String formatGiaPhong = currencyFormatter.format(giaPhong);
        txtGiaPhong.setText(formatGiaPhong);
        double thanhTien = modelTienDichVu.getThanhTien() + modelTienDien.getThanhTien() + modelTienNuoc.getThanhTien()
                + giaPhong;
        String formatted = currencyFormatter.format(thanhTien);
        txtThanhTien.setText(formatted);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new view.component.roundpanel.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTienNuoc = new view.component.table.Table();
        jLabel4 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTienDien = new view.component.table.Table();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTienDV = new view.component.table.Table();
        jLabel9 = new javax.swing.JLabel();
        txtGiaPhong = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKhachThue = new javax.swing.JLabel();
        lblMaQr = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        roundedPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HÓA ĐƠN NHÀ TRỌ");
        roundedPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 7, 437, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Địa chỉ: 23/72/49 Đức Diễn, Phúc Diễn, Bắc Từ Liêm");
        roundedPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 43, 437, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Khách thuê:");
        roundedPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, 26));

        txtMaPhong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMaPhong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roundedPanel1.add(txtMaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 65, 113, 26));

        tblTienNuoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Ngày bắt đầu", "Ngày kết thúc", "Đầu người", "Giá tiền", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblTienNuoc);

        roundedPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 693, 69));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Thành tiền:");
        roundedPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, -1, 30));

        txtThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtThanhTien.setForeground(new java.awt.Color(255, 0, 0));
        roundedPanel1.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 470, 212, 30));

        jLabel5.setText("Tiền dịch vụ");
        roundedPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 90, -1));

        jLabel6.setText("Tiền điện");
        roundedPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 60, -1));

        jLabel7.setText("Tiền nước");
        roundedPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 60, -1));

        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("*Dịch vụ chung gồm: đèn hành lang, gửi xe, rác");
        roundedPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, 296, -1));

        tblTienDien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Ngày bắt đầu", "Ngày kết thúc", "Chỉ số đầu", "Chỉ số cuối", "Số điện", "Giá tiền", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTienDien);

        roundedPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 693, 69));

        tblTienDV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ngày bắt đầu", "Ngày kết thúc", "Tên dịch vụ", "Đầu người", "Giá tiền", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblTienDV);

        roundedPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 693, 69));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Giá phòng:");
        roundedPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(396, 65, -1, 26));

        txtGiaPhong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtGiaPhong.setForeground(new java.awt.Color(255, 0, 0));
        txtGiaPhong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roundedPanel1.add(txtGiaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 65, 198, 26));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Mã phòng:");
        roundedPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 65, -1, 26));

        txtKhachThue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtKhachThue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roundedPanel1.add(txtKhachThue, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 280, 26));

        lblMaQr.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roundedPanel1.add(lblMaQr, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 430, 110, 110));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cảm ơn đã sử dụng!");
        roundedPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 540, 170, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_multiply_24px.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        roundedPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 24, 24));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Người tạo:");
        roundedPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, 26));

        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNguoiTao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roundedPanel1.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 210, 26));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(795, 577));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        ExportPdf.this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExportPdf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMaQr;
    private view.component.roundpanel.RoundedPanel roundedPanel1;
    private view.component.table.Table tblTienDV;
    private view.component.table.Table tblTienDien;
    private view.component.table.Table tblTienNuoc;
    private javax.swing.JLabel txtGiaPhong;
    private javax.swing.JLabel txtKhachThue;
    private javax.swing.JLabel txtMaPhong;
    private javax.swing.JLabel txtNguoiTao;
    private javax.swing.JLabel txtThanhTien;
    // End of variables declaration//GEN-END:variables
}
