package view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Header extends javax.swing.JPanel {

    public Header() {
        initComponents();
//        init();
    }

    public void setUsername(String usernameString) {
        txtUsername.setText(usernameString);
    }

//        private void updateTime(DateTimeFormatter format, String[] arrDate) {
//        LocalTime now = LocalTime.now();
//        String formatHours = now.format(format);
//        hour.setText(formatHours);
//
//        Calendar cld = Calendar.getInstance();
//        String textDate = arrDate[cld.get(Calendar.DAY_OF_WEEK) - 1] + ": " + cld.get(Calendar.DATE) + "/" + (cld.get(Calendar.MONTH) + 1) + "/" + cld.get(Calendar.YEAR);
//        date.setText(textDate);
//    }
//    private void init() {
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String[] arrDate = {"CN", "T.Hai", "T.Ba", "T.Tư", "T.Năm", "T.Sáu", "T.Bảy"};
//        
//        updateTime(format, arrDate);
//
//        Timer timer = new Timer(1000, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                updateTime(format, arrDate);
//            }
//
//        });
//
////        bắt đầu chạy
//        timer.start();
//    }
//    public void setRole(int role) {
//        String vaiTro = "";
//        if (role == 0) {
//            vaiTro = "Admin";
//        } else if (role == 1) {
//            vaiTro = "Employee";
//        }
//        txtRole.setText(vaiTro);
//    }

    public void changeImg(int role) {
        Icon icon = null;
        if (role == 0) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_admin_settings_male_52px.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_user_group_52px.png"));
        }
        imageAvatar1.setIcon(icon);
    }

    public void addMenuEvent(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdMenu = new view.component.button.Button();
        imageAvatar1 = new view.component.avatar.ImageAvatar();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));

        cmdMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_menu_28px_2.png"))); // NOI18N

        imageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_admin_settings_male_52px.png"))); // NOI18N

        lblUsername.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsername.setText("User name");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 423, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageAvatar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cmdMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button cmdMenu;
    private view.component.avatar.ImageAvatar imageAvatar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel txtUsername;
    // End of variables declaration//GEN-END:variables
}
