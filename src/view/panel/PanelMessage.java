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
        if (typeMessage.trim().equalsIgnoreCase("error")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_cancel_90px.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            btnOK.setBackground(new Color(244, 71, 88));
            btnOK.setColorOver(new Color(244, 71, 88));
            btnOK.setColorClick(new Color(224, 51, 68));
            btnOK.setColor(new Color(244, 71, 88));
            btnCancel.setBackground(new Color(244, 71, 88));
            btnCancel.setColorOver(new Color(244, 71, 88));
            btnCancel.setColorClick(new Color(224, 51, 68));
            btnCancel.setColor(new Color(244, 71, 88));
            lblTitle.setText("Error:");
        } else if (typeMessage.trim().equalsIgnoreCase("message")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_box_important_90px_1.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            btnOK.setBackground(new Color(78, 134, 238));
            btnOK.setColorOver(new Color(78, 134, 238));
            btnOK.setColorClick(new Color(58, 114, 218));
            btnOK.setColor(new Color(78, 134, 238));
            btnCancel.setBackground(new Color(78, 134, 238));
            btnCancel.setColorOver(new Color(78, 134, 238));
            btnCancel.setColorClick(new Color(58, 114, 218));
            btnCancel.setColor(new Color(78, 134, 238));
            lblTitle.setText("Message:");
        } else if (typeMessage.trim().equalsIgnoreCase("warning")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_warning_90px.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            btnOK.setBackground(new Color(248, 198, 101));
            btnOK.setColorOver(new Color(248, 198, 101));
            btnOK.setColorClick(new Color(228, 178, 81));
            btnOK.setColor(new Color(248, 198, 101));
            btnCancel.setBackground(new Color(248, 198, 101));
            btnCancel.setColorOver(new Color(248, 198, 101));
            btnCancel.setColorClick(new Color(228, 178, 81));
            btnCancel.setColor(new Color(248, 198, 101));
            lblTitle.setText("Warning:");
        } else if (typeMessage.trim().equalsIgnoreCase("success")) {
            icon = new ImageIcon(getClass().getResource("/icon/icons8_ok_90px_3.png"));
            lblIcon.setIcon(icon);
            txtContent.setText(content.trim());
            btnOK.setBackground(new Color(76, 175, 80));
            btnOK.setColorOver(new Color(76, 175, 80));
            btnOK.setColorClick(new Color(56, 155, 60));
            btnOK.setColor(new Color(76, 175, 80));
            btnCancel.setBackground(new Color(76, 175, 80));
            btnCancel.setColorOver(new Color(76, 175, 80));
            btnCancel.setColorClick(new Color(56, 155, 60));
            btnCancel.setColor(new Color(76, 175, 80));
            lblTitle.setText("Successfully:");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        txtContent = new javax.swing.JLabel();
        btnCancel = new view.component.button.MyButton();
        btnOK = new view.component.button.MyButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_box_important_90px_1.png"))); // NOI18N
        add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 90, 90));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitle.setText("Message:");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, -1));
        add(txtContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, 490, 20));

        btnCancel.setBackground(new java.awt.Color(78, 134, 238));
        btnCancel.setBorder(null);
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setBorderColor(new java.awt.Color(0, 0, 0));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, 80, 30));

        btnOK.setBackground(new java.awt.Color(78, 134, 238));
        btnOK.setBorder(null);
        btnOK.setForeground(new java.awt.Color(255, 255, 255));
        btnOK.setText("OK");
        btnOK.setBorderColor(new java.awt.Color(0, 0, 0));
        btnOK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        add(btnOK, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 80, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        if (listener != null) {
            listener.onOkClicked();
        }
    }// GEN-LAST:event_btnOKActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
        if (listener != null) {
            listener.onCancelClicked();
        }
    }// GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnCancel;
    private view.component.button.MyButton btnOK;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel txtContent;
    // End of variables declaration//GEN-END:variables
}
