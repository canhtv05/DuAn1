/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

/**
 *
 * @author CanhPC
 */
public class HoaDon extends javax.swing.JPanel {

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        materialTabbed1 = new view.component.tab.MaterialTabbed();
        qlyHoaDon1 = new view.panel.QlyHoaDon();
        qlyTienDien1 = new view.panel.QlyTienDien();
        qlyTienNuoc1 = new view.panel.QlyTienNuoc();
        qlyTienDichVu1 = new view.panel.QlyTienDichVu();

        materialTabbed1.addTab("tab1", qlyHoaDon1);
        materialTabbed1.addTab("tab2", qlyTienDien1);
        materialTabbed1.addTab("tab3", qlyTienNuoc1);
        materialTabbed1.addTab("tab4", qlyTienDichVu1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.tab.MaterialTabbed materialTabbed1;
    private view.panel.QlyHoaDon qlyHoaDon1;
    private view.panel.QlyTienDichVu qlyTienDichVu1;
    private view.panel.QlyTienDien qlyTienDien1;
    private view.panel.QlyTienNuoc qlyTienNuoc1;
    // End of variables declaration//GEN-END:variables
}
