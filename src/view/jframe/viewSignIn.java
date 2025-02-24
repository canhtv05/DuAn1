package view.jframe;

import javax.swing.*;
import model.login.ModelLogin;
import util.FileUtils;
import repository.login.CheckEmpty;
import repository.login.RepoLogin;
import util.MoveJFramekGradient;
import view.component.message.MessageFrame;

public class ViewSignIn extends javax.swing.JFrame {

    /**
     * Creates new form ViewSignIn
     */
    private RepoLogin repo = new RepoLogin();
    private CheckEmpty repoCheckEmpty = new CheckEmpty();
    private MoveJFramekGradient move;
    public static String getUsernameString;
    public static int role;

    public ViewSignIn() {
        initComponents();
        setBackground();
        loadLoginDetails();
        makeFrameDraggable();
        rdoRemember.setSelected(false);
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
    }

    private void makeFrameDraggable() {
        move = new MoveJFramekGradient(this);
        kGradientPanel2.addMouseListener(move);
        kGradientPanel2.addMouseMotionListener(move);
    }

    private void setBackground() {
        txtUsername.setBackground(new java.awt.Color(0, 0, 0, 1));
        txtPassword.setBackground(new java.awt.Color(0, 0, 0, 1));
    }

    private void loadLoginDetails() {
        String[] loginDetails = FileUtils.readLoginDetails();
        if (loginDetails != null && loginDetails.length == 2) {
            txtUsername.setText(loginDetails[0]);
            txtPassword.setText(loginDetails[1]);
            rdoRemember.setSelected(true);
            rdoRemember.setIcon(new ImageIcon(getClass().getResource("/icon/icons8_ok_24px.png")));
        } else {
            rdoRemember.setSelected(false);
            rdoRemember.setIcon(new ImageIcon(getClass().getResource("/icon/icons8_cancel_24px.png")));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        disable = new javax.swing.JLabel();
        enable = new javax.swing.JLabel();
        rdoRemember = new javax.swing.JRadioButton();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 450));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel2.setkEndColor(new java.awt.Color(0, 204, 204));
        kGradientPanel2.setkGradientFocus(550);
        kGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_multiply_24px_3.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        kGradientPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 6, 30, 30));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome to Team 5");
        kGradientPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 42, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Please login to your account!");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        kGradientPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 91, 300, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Username");
        kGradientPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("_______________________________________________");
        kGradientPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 298, 20));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_user_32px.png"))); // NOI18N
        jLabel6.setText("           ");
        kGradientPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 168, 37, 35));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Password");
        kGradientPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 209, -1, -1));

        disable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_hide_32px.png"))); // NOI18N
        disable.setText("           ");
        disable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        disable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                disableMouseClicked(evt);
            }
        });
        kGradientPanel2.add(disable, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 37, 35));

        enable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_32px.png"))); // NOI18N
        enable.setText("           ");
        enable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enableMouseClicked(evt);
            }
        });
        kGradientPanel2.add(enable, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 37, 35));

        rdoRemember.setForeground(new java.awt.Color(255, 255, 255));
        rdoRemember.setText("Remember password");
        rdoRemember.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rdoRemember.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_ok_24px.png"))); // NOI18N
        rdoRemember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoRememberMouseClicked(evt);
            }
        });
        rdoRemember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoRememberActionPerformed(evt);
            }
        });
        kGradientPanel2.add(rdoRemember, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        kGradientPanel1.setkEndColor(new java.awt.Color(153, 255, 153));
        kGradientPanel1.setkGradientFocus(300);
        kGradientPanel1.setkStartColor(new java.awt.Color(12, 224, 225));
        kGradientPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kGradientPanel1MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Sign in");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jLabel7)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        kGradientPanel2.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 310, 40));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("_______________________________________________");
        kGradientPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 173, 298, 30));

        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(null);
        kGradientPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 232, 300, 30));

        txtUsername.setForeground(new java.awt.Color(255, 255, 255));
        txtUsername.setBorder(null);
        kGradientPanel2.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 300, 30));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Don't have an account?");
        kGradientPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sign up");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        kGradientPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, -1, -1));

        jPanel1.add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 450));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 450, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/freepik-export-20240711094852nOFd.png"))); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 450));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 450));

        setSize(new java.awt.Dimension(950, 450));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        for (double i = 0.0; i < 1.0; i += 0.1) {
            String variable = i + "";
            float opacity = Float.parseFloat(variable);
            this.setOpacity(opacity);

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }// GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (rdoRemember.isSelected()) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            FileUtils.saveLoginDetails(username, password);
        } else {
            FileUtils.clearLoginDetails();
        }
        System.exit(0);
    }// GEN-LAST:event_formWindowClosing

    private void rdoRememberMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_rdoRememberMouseClicked

    }// GEN-LAST:event_rdoRememberMouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        new ViewSignUp().setVisible(true);
        this.dispose();
    }// GEN-LAST:event_jLabel10MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:

    }// GEN-LAST:event_jLabel3MouseClicked

    private void kGradientPanel1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_kGradientPanel1MouseClicked
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        if (repoCheckEmpty.checkUsernameAndPasswordEmpty(username, password)) {
            return;
        }

        if (repoCheckEmpty.checkPasswordEmpty(password)) {
            return;
        }

        if (repoCheckEmpty.checkUsernameEmpty(username)) {
            return;
        }

        ModelLogin login = repo.checkLogin(new ModelLogin(username, password));

        if (login != null) {
            this.dispose();
            if (login.getRole() == 0) {
                getUsernameString = login.getUsername();
                role = login.getRole();
                new ViewApp().setVisible(true);
                MessageFrame popup = new MessageFrame();
                popup.showMessage("message", "Bạn đang đăng nhập dưới quyền Admin.");
            } else {
                getUsernameString = login.getUsername();
                role = login.getRole();
                new ViewApp().setVisible(true);
                MessageFrame popup = new MessageFrame();
                popup.showMessage("message", "Bạn đang đăng nhập dưới quyền Nhân viên.");
            }
        } else {
            repoCheckEmpty.loginFailed();
        }
    }// GEN-LAST:event_kGradientPanel1MouseClicked

    private void disableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_disableMouseClicked
        // TODO add your handling code here:
        txtPassword.setEchoChar((char) 0);
        disable.setVisible(false);
        disable.setEnabled(false);
        enable.setVisible(true);
        enable.setEnabled(true);
    }// GEN-LAST:event_disableMouseClicked

    private void enableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_enableMouseClicked
        // 8226 mã unicode của "•"
        txtPassword.setEchoChar((char) 8226);
        disable.setVisible(true);
        disable.setEnabled(true);
        enable.setVisible(false);
        enable.setEnabled(false);
    }// GEN-LAST:event_enableMouseClicked

    private void rdoRememberActionPerformed(java.awt.event.ActionEvent evt) {
        Icon selectedIcon = new ImageIcon(getClass().getResource("/icon/icons8_ok_24px.png"));
        Icon unselectedIcon = new ImageIcon(getClass().getResource("/icon/icons8_cancel_24px.png"));

        if (rdoRemember.isSelected()) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            FileUtils.saveLoginDetails(username, password);
            rdoRemember.setIcon(selectedIcon);
        } else {
            FileUtils.clearLoginDetails();
            rdoRemember.setIcon(unselectedIcon);
        }
    }

    // GEN-LAST:event_rdoRememberActionPerformed
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel1MouseClicked
        this.dispose();
    }// GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ViewSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSignIn.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewSignIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel disable;
    private javax.swing.JLabel enable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JRadioButton rdoRemember;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
