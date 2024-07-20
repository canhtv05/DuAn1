package view.panel;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.tiendien.ModelTienDien;
import repository.tiendien.RepoTienDien;

public class QlyTienDien extends javax.swing.JPanel {

        private RepoTienDien repo;
        private ArrayList<ModelTienDien> list;
        private int page = 1;
        private final int limit = 5;
        private int totalPages;
        private int totalRecords;

        public QlyTienDien() {
                initComponents();
                init();
                loadTable();
                updatePagination();
        }

        private void init() {
                tbl.fixTable(scroll);
                nav.setForeground(new Color(0, 0, 250));
                setBackground(new Color(243, 243, 243));
                repo = new RepoTienDien();
                totalRecords = repo.totalRecords();
                totalPages = (int) Math.ceil((double) totalRecords / limit);
        }

        private void loadTable() {
                DefaultTableModel model = (DefaultTableModel) tbl.getModel();
                model.setRowCount(0);
                String timKiem = "";
                list = new ArrayList<>();
                repo = new RepoTienDien();
                list = repo.searchAndPage(timKiem, page, limit);
                for (ModelTienDien td : list) {
                        Object[] rows = {
                                        td.getMaTD(),
                                        td.getMaHD(),
                                        td.getMaPT(),
                                        td.getNgayBD(),
                                        td.getNgayKT(),
                                        td.getChiSoDau(),
                                        td.getChiSoCuoi(),
                                        td.getSoDien(),
                                        td.getGiaTien(),
                                        td.getThanhTien()
                        };
                        model.addRow(rows);
                }
        }

        private void updatePagination() {
                index.setText(String.valueOf(page));
                end.setText(String.valueOf(totalPages));

                prev.setEnabled(page > 1);
                next.setEnabled(page < totalPages);
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                nav = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                scroll = new javax.swing.JScrollPane();
                tbl = new view.component.table.Table();
                prev = new view.component.button.MyButton();
                next = new view.component.button.MyButton();
                index = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                end = new javax.swing.JLabel();
                jPanel2 = new javax.swing.JPanel();
                txtMaTD = new view.component.textfield.TextField();
                txtMaHD = new view.component.textfield.TextField();
                txtMaPT = new view.component.textfield.TextField();
                txtNgayBD = new view.component.textfield.TextField();
                txtNgayKT = new view.component.textfield.TextField();
                txtChiSoDau = new view.component.textfield.TextField();
                txtChiSoCuoi = new view.component.textfield.TextField();
                txtSoDien = new view.component.textfield.TextField();
                txtGiaTien = new view.component.textfield.TextField();
                txtThanhTien = new view.component.textfield.TextField();
                myButton5 = new view.component.button.MyButton();
                myButton7 = new view.component.button.MyButton();
                myButton8 = new view.component.button.MyButton();
                myButton9 = new view.component.button.MyButton();

                nav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                nav.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                nav.setText("Hóa đơn / Tiền điện");

                jPanel1.setBackground(new java.awt.Color(255, 255, 255));

                tbl.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null },
                                                { null, null, null, null, null, null, null, null, null, null }
                                },
                                new String[] {
                                                "Mã tiền điện", "Mã hóa đơn", "Mã phòng trọ", "Ngày bắt đầu",
                                                "Ngày kết thúc", "Chỉ số đầu", "Chỉ số cuối", "Số điện", "Giá tiền",
                                                "Thành tiền"
                                }));
                scroll.setViewportView(tbl);

                prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_previous_24px.png"))); // NOI18N
                prev.setBorderColor(new java.awt.Color(204, 255, 255));
                prev.setColor(new java.awt.Color(204, 255, 255));
                prev.setColorClick(new java.awt.Color(153, 255, 255));
                prev.setColorOver(new java.awt.Color(204, 255, 255));
                prev.setRadius(10);
                prev.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                prevMouseClicked(evt);
                        }
                });
                prev.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                prevActionPerformed(evt);
                        }
                });

                next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_next_24px.png"))); // NOI18N
                next.setBorderColor(new java.awt.Color(204, 255, 255));
                next.setColor(new java.awt.Color(204, 255, 255));
                next.setColorClick(new java.awt.Color(153, 255, 255));
                next.setColorOver(new java.awt.Color(204, 255, 255));
                next.setRadius(10);
                next.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                nextMouseClicked(evt);
                        }
                });
                next.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                nextActionPerformed(evt);
                        }
                });

                index.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                index.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                index.setText("1");

                jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                jLabel2.setText("/");

                end.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                end.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                end.setText("1");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(470, 470, 470)
                                                                .addComponent(prev,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(index,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                13,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(end,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                13,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(next,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                jPanel1Layout.createSequentialGroup()
                                                                                .addContainerGap()
                                                                                .addComponent(scroll)
                                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(scroll,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                232,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                                .addComponent(next,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(prev,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(index,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(end,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jLabel2,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel2.setBackground(new java.awt.Color(255, 255, 255));
                jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                txtMaTD.setLabelText("Mã tiền điện");
                txtMaTD.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaTDActionPerformed(evt);
                        }
                });
                jPanel2.add(txtMaTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 272, -1));

                txtMaHD.setLabelText("Mã hóa đơn");
                txtMaHD.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaHDActionPerformed(evt);
                        }
                });
                jPanel2.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 272, -1));

                txtMaPT.setLabelText("Mã phòng trọ");
                txtMaPT.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMaPTActionPerformed(evt);
                        }
                });
                jPanel2.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 272, -1));

                txtNgayBD.setLabelText("Ngày bắt đầu");
                txtNgayBD.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtNgayBDActionPerformed(evt);
                        }
                });
                jPanel2.add(txtNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 272, -1));

                txtNgayKT.setLabelText("Ngày kết thúc");
                txtNgayKT.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtNgayKTActionPerformed(evt);
                        }
                });
                jPanel2.add(txtNgayKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 272, -1));

                txtChiSoDau.setLabelText("Chỉ số đầu");
                txtChiSoDau.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtChiSoDauActionPerformed(evt);
                        }
                });
                jPanel2.add(txtChiSoDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 272, -1));

                txtChiSoCuoi.setLabelText("Chỉ số cuối");
                txtChiSoCuoi.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtChiSoCuoiActionPerformed(evt);
                        }
                });
                jPanel2.add(txtChiSoCuoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 272, -1));

                txtSoDien.setLabelText("Số điện");
                txtSoDien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtSoDienActionPerformed(evt);
                        }
                });
                jPanel2.add(txtSoDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 110, 272, -1));

                txtGiaTien.setLabelText("Giá tiền");
                txtGiaTien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtGiaTienActionPerformed(evt);
                        }
                });
                jPanel2.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 272, -1));

                txtThanhTien.setLabelText("Thành tiền");
                txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtThanhTienActionPerformed(evt);
                        }
                });
                jPanel2.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, 272, -1));

                myButton5.setText("myButton5");
                myButton5.setRadius(20);
                jPanel2.add(myButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 150, 90, 40));

                myButton7.setText("myButton5");
                myButton7.setRadius(20);
                jPanel2.add(myButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 210, 90, 40));

                myButton8.setText("myButton5");
                myButton8.setRadius(20);
                jPanel2.add(myButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, 90, 40));

                myButton9.setText("myButton5");
                myButton9.setRadius(20);
                jPanel2.add(myButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 150, 90, 40));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(nav)
                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jPanel2,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                1069,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jPanel1,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addContainerGap()))));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(nav)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                260, Short.MAX_VALUE)
                                                                .addContainerGap()));
        }// </editor-fold>//GEN-END:initComponents

        private void txtMaTDActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtMaTD khi người dùng nhấn Enter
        }

        private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtMaHD khi người dùng nhấn Enter
        }

        private void txtMaPTActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtMaPT khi người dùng nhấn Enter
        }

        private void txtNgayBDActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtNgayBD khi người dùng nhấn Enter
        }

        private void txtNgayKTActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtNgayKT khi người dùng nhấn Enter
        }

        private void txtChiSoDauActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtChiSoDau khi người dùng nhấn Enter
        }

        private void txtChiSoCuoiActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtChiSoCuoi khi người dùng nhấn Enter
        }

        private void txtSoDienActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtSoDien khi người dùng nhấn Enter
        }

        private void txtGiaTienActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtGiaTien khi người dùng nhấn Enter
        }

        private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: Xử lý sự kiện cho txtThanhTien khi người dùng nhấn Enter
        }

        private void prevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_prevActionPerformed
                if (page > 1) {
                        page--;
                        loadTable();
                        updatePagination();
                        prev.requestFocusInWindow();
                }
        }// GEN-LAST:event_prevActionPerformed

        private void nextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_nextActionPerformed
                if (page < totalPages) {
                        page++;
                        loadTable();
                        updatePagination();
                        next.requestFocusInWindow();
                }
        }// GEN-LAST:event_nextActionPerformed

        private void prevMouseClicked(java.awt.event.MouseEvent evt) {

        }

        private void nextMouseClicked(java.awt.event.MouseEvent evt) {

        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel end;
        private javax.swing.JLabel index;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private view.component.button.MyButton myButton5;
        private view.component.button.MyButton myButton7;
        private view.component.button.MyButton myButton8;
        private view.component.button.MyButton myButton9;
        private javax.swing.JLabel nav;
        private view.component.button.MyButton next;
        private view.component.button.MyButton prev;
        private javax.swing.JScrollPane scroll;
        private view.component.table.Table tbl;
        private view.component.textfield.TextField txtChiSoCuoi;
        private view.component.textfield.TextField txtChiSoDau;
        private view.component.textfield.TextField txtGiaTien;
        private view.component.textfield.TextField txtMaHD;
        private view.component.textfield.TextField txtMaPT;
        private view.component.textfield.TextField txtMaTD;
        private view.component.textfield.TextField txtNgayBD;
        private view.component.textfield.TextField txtNgayKT;
        private view.component.textfield.TextField txtSoDien;
        private view.component.textfield.TextField txtThanhTien;
        // End of variables declaration//GEN-END:variables
}
