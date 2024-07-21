package view.panel;

import java.awt.*;
import javax.swing.*;
import service.PanelMessageListenerImpl;

public class PanelMessage extends javax.swing.JPanel {

    private PanelMessageListenerImpl listener;

    private Icon icon = null;

    public PanelMessage() {
        initComponents();
    }

    public void setPanelMessageListener(PanelMessageListenerImpl listener) {
        this.listener = listener;
    }

    public void changeMessage(String typeMessage, String content) {
        if (typeMessage.equalsIgnoreCase("error")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_cancel_90px.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            jPanel2.setBackground(new Color(244, 71, 88));
            jPanel3.setBackground(new Color(244, 71, 88));
            lblTitle.setText("Error:");
        } else if (typeMessage.equalsIgnoreCase("message")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_facebook_messenger_90px.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            jPanel2.setBackground(new Color(78, 134, 238));
            jPanel3.setBackground(new Color(78, 134, 238));
            lblTitle.setText("Message:");
        } else if (typeMessage.equalsIgnoreCase("warning")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_warning_90px.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            jPanel2.setBackground(new Color(248, 198, 101));
            jPanel3.setBackground(new Color(248, 198, 101));
            lblTitle.setText("Warning:");
        } else if (typeMessage.equalsIgnoreCase("success")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_ok_90px_3.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            jPanel2.setBackground(new Color(76,175,80));
            jPanel3.setBackground(new Color(76,175,80));
            lblTitle.setText("Successful:");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        txtContent = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_facebook_messenger_90px.png"))); // NOI18N
        add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 90));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("Message:");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, -1));
        add(txtContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 380, 20));

        jPanel2.setBackground(new java.awt.Color(78, 134, 238));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cancel");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 80, 30));

        jPanel3.setBackground(new java.awt.Color(78, 134, 238));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.setPreferredSize(new java.awt.Dimension(80, 30));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("OK");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 6, -1, -1));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        if (listener != null) {
            listener.onOkClicked();
        }
        // Sử dụng JFrame) SwingUtilities.getWindowAncestor để tìm cửa sổ Jframe hiện tại
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
        if (listener != null) {
            listener.onCancelClicked();
        }
        // Sử dụng JFrame) SwingUtilities.getWindowAncestor để tìm cửa sổ Jframe hiện tại
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.dispose();
        }
    }//GEN-LAST:event_jPanel2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel txtContent;
    // End of variables declaration//GEN-END:variables
}
