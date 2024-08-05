package view.jframe;

import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import repository.themhoadon.RepoThemHoaDon;
import repository.validate.Validate;
import view.component.message.MessageFrame;

public class ThemHoaDon extends javax.swing.JFrame {

    private RepoThemHoaDon repo;
    private MessageFrame message;
    private Validate validate;
    private String maNV;
    private String ngay1ThangTruoc;
    private String ngayCuoiThangTruoc;
    private boolean isUpdating = false;

    public ThemHoaDon() {
        initComponents();
        init();
        getTextOnChange();
    }

    private void init() {
        repo = new RepoThemHoaDon();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate today = LocalDate.now();

        // Tính toán tháng trước
        LocalDate ngay1ThangHienTai = today.withDayOfMonth(1); // Lấy ngày đầu tiên của tháng hiện tại.
        LocalDate layNgay1ThangTruoc = ngay1ThangHienTai.minusMonths(1); // Lấy ngày đầu tiên của tháng trước.
        LocalDate layNgayCuoiThangTruoc = ngay1ThangHienTai.minusDays(1); // Lấy ngày cuối cùng của tháng trước (bằng cách trừ đi 1 ngày từ ngày đầu tiên của tháng hiện tại).

        // Định dạng ngày
        ngay1ThangTruoc = layNgay1ThangTruoc.format(formatter);
        ngayCuoiThangTruoc = layNgayCuoiThangTruoc.format(formatter);

        txtNgayThangTruoc.setText("Từ " + ngay1ThangTruoc + " tới " + ngayCuoiThangTruoc);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        String usernameString = ViewSignIn.getUsernameString;
        maNV = repo.timMaNV(usernameString);
        if (maNV == null) {
            maNV = "NV001";
        }
    }

    private void getTextOnChange() {
        txtTienDien.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleField(txtTienDien, "Tiền điện");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleField(txtTienDien, "Tiền điện");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        txtTienNuoc.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleField(txtTienNuoc, "Tiền nước");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleField(txtTienNuoc, "Tiền nước");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        txtTienDV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleField(txtTienDV, "Tiền dịch vụ");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleField(txtTienDV, "Tiền dịch vụ");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void handleField(JTextField textField, String fieldName) {
        if (!isUpdating) {
            isUpdating = true;
            SwingUtilities.invokeLater(() -> {
                handleDecimal(textField, fieldName);
                isUpdating = false;
            });
        }
    }

    private void handleDecimal(JTextField text, String fieldName) {
        message = new MessageFrame();
        String txt = text.getText().trim();
        try {
            if (isValidateDecimal(txt)) {
                if (!text.getText().trim().isEmpty()) {
                    double giaTien = Double.parseDouble(text.getText().trim());
                    String count = giaTien + "";
                    if (countDecimals(count) == 1) {
                        int findDot = count.indexOf(".");
                        String numString = count.substring(0, findDot);
                        int findDot2 = -1;
                        if (numString.matches("\\w+")) {
                            numString = text.getText().trim();
                            if (numString.contains(".")) {
                                findDot2 = numString.indexOf(".") - 1;
                                numString = numString.substring(0, findDot2);
                            }
                        }
                        if (numString.length() > 9) {
                            String setText = numString.substring(0, 9);
                            text.setText(setText);
                            giaTien = Double.parseDouble(setText);
                            message.showMessage("error", "Số quá lớn.");
                            return;
                        }
                    }
                }
            } else {
                String txtSub = text.getText().trim();
                int index = -1;

                Pattern pattern = Pattern.compile("[a-zA-Z]");
                Matcher matcher = pattern.matcher(txtSub);
                if (matcher.find()) {
                    index = matcher.start();
                    text.setText(txtSub.substring(0, index) + txt.substring(index + 1));
                } else {
                    text.setText(txtSub.substring(0, txtSub.length() - 1));
                }
                message.showMessage("error", fieldName + " phải là số.");
                return;
            }
        } catch (NumberFormatException e) {
            message.showMessage("error", fieldName + " phải là số.");
        }
    }

    private int countDecimals(String text) {
        int cnt = 0;
        for (char c : text.toCharArray()) {
            if (c == '.') {
                cnt++;
            }
        }
        return cnt;
    }

    private boolean isValidateDecimal(String text) {
        String regex = "^\\d*\\.?\\d*$";
        int countDot = countDecimals(text);
        return text.matches(regex) && countDot <= 1;
    }

    private void clearAll() {
        txtTienDV.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new view.component.roundpanel.RoundedPanel();
        txtNgayThangTruoc = new javax.swing.JLabel();
        txtTienNuoc = new view.component.textfield.TextField();
        txtTienDV = new view.component.textfield.TextField();
        txtTienDien = new view.component.textfield.TextField();
        jLabel1 = new javax.swing.JLabel();
        btnHuy = new view.component.button.MyButton();
        btnThem = new view.component.button.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtNgayThangTruoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNgayThangTruoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtTienNuoc.setLabelText("Tiền nước / 1 người");

        txtTienDV.setLabelText("Tiền dịch vụ / 1 người");

        txtTienDien.setLabelText("Tiền điện / 1 số điện");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TẠO HÓA ĐƠN");

        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_cancel_24px.png"))); // NOI18N
        btnHuy.setText(" Hủy");
        btnHuy.setBorderColor(new java.awt.Color(153, 204, 204));
        btnHuy.setColor(new java.awt.Color(204, 255, 255));
        btnHuy.setColorClick(new java.awt.Color(153, 255, 255));
        btnHuy.setColorOver(new java.awt.Color(204, 255, 255));
        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnHuy.setRadius(10);
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnThem.setText(" Thêm");
        btnThem.setBorderColor(new java.awt.Color(153, 204, 204));
        btnThem.setColor(new java.awt.Color(204, 255, 255));
        btnThem.setColorClick(new java.awt.Color(153, 255, 255));
        btnThem.setColorOver(new java.awt.Color(204, 255, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnThem.setRadius(10);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTienNuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienDV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienDien, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addComponent(txtNgayThangTruoc, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayThangTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(txtTienDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTienNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTienDV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

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

        setSize(new java.awt.Dimension(428, 483));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        repo = new RepoThemHoaDon();
        message = new MessageFrame();
        validate = new Validate();
        ArrayList<Object> arr = repo.layMaPT();

        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

        String ngayDauString = null;
        String ngayCuoiString = null;

        try {
            Date ngayDauDate = input.parse(ngay1ThangTruoc);
            Date ngayCuoiDate = input.parse(ngayCuoiThangTruoc);

            ngayDauString = output.format(ngayDauDate);
            ngayCuoiString = output.format(ngayCuoiDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTextField[] fieldsToCheck = {txtTienDien, txtTienNuoc, txtTienDV};
        String[] fieldLabels = {"Tiền điện", "Tiền nước", "Tiền dịch vụ"};
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtTienDien, txtTienNuoc, txtTienDV};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        if (repo.checkNgayTaoHoaDon(ngayDauString, ngayCuoiString)) {
            message.showMessage("error", "Đã tồn tại hóa đơn từ " + ngay1ThangTruoc + " tới " + ngayCuoiThangTruoc + " không thể thêm.");
            return;
        }

        message.showMessage("message", "Bạn có muốn thêm hóa đơn mới không?");
        message.setOnOkClicked(() -> {
            MessageFrame mess = new MessageFrame();
            if (repo.addHoaDon(arr, maNV, Double.parseDouble(txtTienDien.getText()), Double.parseDouble(txtTienNuoc.getText()), Double.parseDouble(txtTienDV.getText()))) {
                mess.showMessage("success", "Thêm mới hóa đơn thành công.");
                clearAll();
            } else {
                mess.showMessage("error", "Thêm mới hóa đơn thất bại.");
            }
        });

    }//GEN-LAST:event_btnThemActionPerformed

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
            java.util.logging.Logger.getLogger(ThemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThemHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThemHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnHuy;
    private view.component.button.MyButton btnThem;
    private javax.swing.JLabel jLabel1;
    private view.component.roundpanel.RoundedPanel roundedPanel1;
    private javax.swing.JLabel txtNgayThangTruoc;
    private view.component.textfield.TextField txtTienDV;
    private view.component.textfield.TextField txtTienDien;
    private view.component.textfield.TextField txtTienNuoc;
    // End of variables declaration//GEN-END:variables
}
