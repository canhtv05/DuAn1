package view.main;

import view.panel.FormHome;
import view.panel.Header;
import view.panel.MainForm;
import view.panel.Menu;
import view.panel.MenuItem;
import service.EventMenuSelected;
import service.EventShowPopupMenu;
import view.panel.PopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import view.panel.DoiMK;
import view.panel.Footer;
import view.panel.HoaDon;
import view.panel.QlyHeThong;
import view.panel.QlyHoaDon;
import view.panel.QlyKhachThue;
import view.panel.QlyNhanVien;
import view.panel.QlyPhongTro;
import view.panel.QlyTaiSan;
import view.panel.QlyTienDichVu;
import view.panel.QlyTienDien;
import view.panel.QlyTienNuoc;

public class ViewApp extends javax.swing.JFrame {

    private MigLayout layout;
    private Menu menu;
    private Header header;
    private MainForm main;
    private Animator animator;
    private Footer footer;

    public ViewApp() {
        initComponents();
//        ViewApp.this.setExtendedState(ViewApp.this.MAXIMIZED_BOTH);
        init();
        changeHeader();
    }

    private void changeHeader() {
        // logic: lấy từ bên login được username -> jframe chính lúc này là app xong sau
        // đó thì sửa username ở header vì là hàm static
        String usernameString = viewSignIn.getUsernameString;
        header.setUsername(usernameString);
        int role = viewSignIn.role;
        header.changeImg(role);
    }

    private void init() {
        layout = new MigLayout("fill", "0[]0[100%, fill]0", "0[fill, top]0[fill]0[]0");
        bg.setLayout(layout);
        menu = new Menu();
        header = new Header();
        main = new MainForm();
        footer = new Footer();
        menu.addEvent(new EventMenuSelected() {
            @Override
            public void menuSelected(int menuIndex, int subMenuIndex) {
//                System.out.println("Menu Index : " + menuIndex + " SubMenu Index " + subMenuIndex);
                if (menuIndex == 0) {
                    main.showForm(new QlyHeThong());
                } else if (menuIndex == 1) {
                    main.showForm(new QlyPhongTro());
                } else if (menuIndex == 2) {
                    main.showForm(new QlyKhachThue());
                } else if (menuIndex == 3) {
//                    main.showForm(new HoaDon());
                    if (subMenuIndex == 0) {
                        main.showForm(new QlyHoaDon());
                    } else if (subMenuIndex == 1) {
                        main.showForm(new QlyTienDien());
                    } else if (subMenuIndex == 2) {
                        main.showForm(new QlyTienNuoc());
                    } else if (subMenuIndex == 3) {
                        main.showForm(new QlyTienDichVu());
                    }
                } else if (menuIndex == 4) {
                    main.showForm(new QlyNhanVien());
                } else if (menuIndex == 5) {
                    main.showForm(new QlyTaiSan());
                } else if (menuIndex == 6) {
                    main.showForm(new DoiMK());
                } else if (menuIndex == 7) {
                    new viewSignIn().setVisible(true);
                    dispose();
                } else {
                    // Default case if needed
                }
                               
            }
                       
        });
        menu.addEventShowPopup(new EventShowPopupMenu() {
            @Override
            public void showPopup(Component com) {
                MenuItem item = (MenuItem) com;
                PopupMenu popup = new PopupMenu(ViewApp.this, item.getIndex(), item.getEventSelected(),
                        item.getMenu().getSubMenu());
                int x = ViewApp.this.getX() + 52;
                int y = ViewApp.this.getY() + com.getY() + 86;
                popup.setLocation(x, y);
                popup.setVisible(true);
            }
        });
        menu.initMenuItem();
        bg.add(menu, "w 230!, spany 2");
        bg.add(header, "h 50!, wrap");
        bg.add(main, "w 100%, h 100%, wrap");
//        bg.add(footer, "h 50!, wrap");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menu.isShowMenu()) {
                    width = 60 + (170 * (1f - fraction));
                } else {
                    width = 60 + (170 * fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!, spany2");
                menu.revalidate();
            }

            @Override
            public void end() {
                menu.setShowMenu(!menu.isShowMenu());
                menu.setEnableMenu(true);
            }

        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        header.addMenuEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
                menu.setEnableMenu(false);
                if (menu.isShowMenu()) {
                    menu.hideallMenu();
                }
            }
        });
        // Start with this form
        main.showForm(new FormHome());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1400, 700));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1400, Short.MAX_VALUE));
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 700, Short.MAX_VALUE));

        jLayeredPane1.setLayer(bg, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg));
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        setSize(new java.awt.Dimension(1400, 700));
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
            java.util.logging.Logger.getLogger(ViewApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
