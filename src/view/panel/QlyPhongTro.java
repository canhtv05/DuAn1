/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import javax.swing.table.DefaultTableModel;
import model.PhongTro.ModelPhongTro;
import repository.PhongTro.repoPhongTro;
import view.component.message.MessageFrame;
import java.util.ArrayList;
//import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class QlyPhongTro extends javax.swing.JPanel {

    private ModelPhongTro mdPT = new ModelPhongTro();
    private repoPhongTro repoPT = new repoPhongTro();
    private MessageFrame msg;
    private MessageFrame confirm;
    private String path = "D/...";

    /**
     * Creates new form QlPhongTro
     */
    public QlyPhongTro() {
        initComponents();
        this.init();
        this.fillTable(this.repoPT.getAll());
        this.fillCbbTang(this.repoPT.getCbbTang());
    }

    public void init() {
        lbHinhAnh.setText("");
        this.fillTable(this.repoPT.getAll());
    }

    private void fillTable(ArrayList<ModelPhongTro> list) {
        DefaultTableModel dtm = (DefaultTableModel) this.tblPhongTro.getModel();

        dtm.setRowCount(0);

        for (ModelPhongTro pt : list) {
            Object[] data = {
                pt.getTangSo(),
                pt.getMaPhong(),
                pt.getLoaiPhong(),
                pt.getDienTich(),
                pt.getGiaPhong(),
                pt.getTienNghi(),
                pt.getTrangThai() == 0 ? "Còn trống"
                : pt.getTrangThai() == 1 ? "Đã thuê" : "Đang bảo dưỡng",
                pt.getAnh()
            };
            dtm.addRow(data);
        }
    }

    private void clearForm() {
        this.txtSearch.setText("");
        fillCbbTang(repoPT.getCbbTang());
        this.txtMaPT.setText("");
        this.txtLoaiPhong.setText("");
        this.txtDienTich.setText("");
        this.txtGiaPhong.setText("");
//        this.txtTienNghi.setText("");
        this.rdoConTrong.setSelected(true);
        this.cbkDieuHoa.setSelected(false);
        this.cbkTuLanh.setSelected(false);
        this.cbkTuQuanAo.setSelected(false);
        this.cbkNongLanh.setSelected(false);
        this.cbkGiuong.setSelected(false);
        this.cbkBanHoc.setSelected(false);
        this.cbkMayGiat.setSelected(false);
        this.cbkBepDien.setSelected(false);
        this.cbbFilterTrangThai.setSelectedItem("Tất cả");
        this.cbbFilterPrice.setSelectedItem("Tất cả");
        this.lbHinhAnh.setText("                             Thêm ảnh");
        this.lbHinhAnh.setIcon(null);
    }

//    public void fillForm(ModelPhongTro pt) {
//        this.cbbTang.setSelectedItem(pt.getTangSo());
//        this.txtMaPT.setText(pt.getMaPhong());
//        this.txtLoaiPhong.setText(pt.getLoaiPhong());
//        this.txtDienTich.setText(Float.toString(pt.getDienTich()));
//        this.txtGiaPhong.setText(Float.toString(pt.getGiaPhong()));
//        this.txtTienNghi.setText(pt.getTienNghi());
//        switch (pt.getTrangThai()) {
//            case 0 ->
//                this.rdoConTrong.setSelected(true);
//            case 1 ->
//                this.rdoDaThue.setSelected(true);
//            default ->
//                this.rdoBaoDuong.setSelected(true);
//        }
//        path = pt.getAnh();
//        ImageIcon img = new ImageIcon(path);
//        lbHinhAnh.setIcon(img);
//
//    }
    private void fillCbbTang(ArrayList<ModelPhongTro> listPT) {
        cbbTang.removeAllItems();
        for (ModelPhongTro pt : listPT) {
            cbbTang.addItem(pt.getTangSo());
        }
    }

//    private void loadTrangThai(ArrayList<ModelPhongTro> list) {
//        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbTrangThai.getModel();
//        for (ModelPhongTro pt : list) {
//            cbm.addElement(pt);
//        }
//    }
//
//    private void loadGia(ArrayList<ModelPhongTro> list) {
//        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbGia.getModel();
//        for (ModelPhongTro pt : list) {
//            cbm.addElement(pt);
//        }
//    }
//
//    private void loadDienTich(ArrayList<ModelPhongTro> list) {
//        DefaultComboBoxModel cbm = (DefaultComboBoxModel) cbbDienTich.getModel();
//        for (ModelPhongTro pt : list) {
//            cbm.addElement(pt);
//        }
//    }
//    private boolean checkIdInsert() {
//        String maPT = this.txtMaPT.getText().trim();
//        for (ModelPhongTro pt : this.repoPT.getAll()) {
//            msg = new MessageFrame();
//            if (pt.getMaPhong().equalsIgnoreCase(maPT)) {
//                msg.showMessage("error", "Phòng này đã tồn tại. Vui lòng chọn số phòng khác!");
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean checkIdUpdate() {
//        String maPT = this.txtMaPT.getText().trim();
//        for (ModelPhongTro pt : this.repoPT.getAll()) {
//            msg = new MessageFrame();
//            if (pt.getMaPhong().equalsIgnoreCase(maPT)) {
//                return true;
//            }
//        }
//        msg.showMessage("error", "Phòng này không tồn tại. Lưu ý: Không thể sửa số phòng!!");
//
//        return false;
//    }
//    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtMaPT = new view.component.textfield.TextField();
        txtLoaiPhong = new view.component.textfield.TextField();
        txtGiaPhong = new view.component.textfield.TextField();
        txtDienTich = new view.component.textfield.TextField();
        jLabel6 = new javax.swing.JLabel();
        btnRefresh = new view.component.button.Button();
        btnReset = new view.component.button.Button();
        btnInsert = new view.component.button.Button();
        btnUpdate = new view.component.button.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhongTro = new view.component.table.Table();
        btnSearch = new view.component.button.Button();
        txtSearch = new view.component.textfield.TextField();
        lbHinhAnh = new javax.swing.JLabel();
        rdoBaoDuong = new view.component.radiobutton.RadioButtonCustom();
        rdoDaThue = new view.component.radiobutton.RadioButtonCustom();
        rdoConTrong = new view.component.radiobutton.RadioButtonCustom();
        cbbTang = new view.component.combobox.Combobox();
        cbbFilterTrangThai = new view.component.combobox.Combobox();
        cbkDieuHoa = new view.component.checkbox.JCheckBoxCustom();
        cbkTuLanh = new view.component.checkbox.JCheckBoxCustom();
        cbkGiuong = new view.component.checkbox.JCheckBoxCustom();
        cbkNongLanh = new view.component.checkbox.JCheckBoxCustom();
        cbkTuQuanAo = new view.component.checkbox.JCheckBoxCustom();
        cbkBanHoc = new view.component.checkbox.JCheckBoxCustom();
        jLabel2 = new javax.swing.JLabel();
        cbkMayGiat = new view.component.checkbox.JCheckBoxCustom();
        cbkBepDien = new view.component.checkbox.JCheckBoxCustom();
        cbbFilterPrice = new view.component.combobox.Combobox();
        btnXoa = new view.component.button.Button();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMaPT.setLabelText("Số phòng");

        txtLoaiPhong.setLabelText("Loại phòng");

        txtGiaPhong.setLabelText("Giá phòng(vnđ)");

        txtDienTich.setLabelText("Diện tích");
        txtDienTich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDienTichActionPerformed(evt);
            }
        });

        jLabel6.setText("Trạng thái:");

        btnRefresh.setBackground(new java.awt.Color(204, 255, 255));
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(153, 204, 255));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnInsert.setBackground(new java.awt.Color(153, 204, 255));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(153, 204, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_downloading_updates_24px.png"))); // NOI18N
        btnUpdate.setText("Cập nhập");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblPhongTro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tầng", "Mã phòng", "Loại phòng", "Diện tích(m²)", "Giá phòng(vnđ)", "Tiện nghi", "Tình trạng", "Ảnh"
            }
        ));
        tblPhongTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhongTroMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhongTro);

        btnSearch.setBackground(new java.awt.Color(153, 204, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtSearch.setLabelText("Search...");

        lbHinhAnh.setBackground(new java.awt.Color(255, 0, 102));
        lbHinhAnh.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbHinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lbHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbHinhAnhMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoBaoDuong);
        rdoBaoDuong.setText("Đang bảo dưỡng");

        buttonGroup1.add(rdoDaThue);
        rdoDaThue.setText("Đã thuê");

        buttonGroup1.add(rdoConTrong);
        rdoConTrong.setText("Còn trống");

        cbbTang.setLabeText("Tầng");

        cbbFilterTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Còn trống", "Đã thuê", "Đang bảo dưỡng", " " }));
        cbbFilterTrangThai.setLabeText("");
        cbbFilterTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterTrangThaiActionPerformed(evt);
            }
        });

        cbkDieuHoa.setText("Điều hòa");

        cbkTuLanh.setText("Tủ lạnh");
        cbkTuLanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkTuLanhActionPerformed(evt);
            }
        });

        cbkGiuong.setText("Giường");

        cbkNongLanh.setText("Bình nóng lạnh");

        cbkTuQuanAo.setText("Tủ quần áo");

        cbkBanHoc.setText("Bàn học");
        cbkBanHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkBanHocActionPerformed(evt);
            }
        });

        jLabel2.setText("Tiện nghi:");

        cbkMayGiat.setText("Máy giặt");

        cbkBepDien.setText("Bếp điện");

        cbbFilterPrice.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "3-4tr", "4-5tr" }));
        cbbFilterPrice.setLabeText("");
        cbbFilterPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbFilterPriceActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 153, 153));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_24px_1.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel3.setText("Trạng thái phòng:");

        jLabel4.setText("Giá phòng:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(cbbFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(21, 21, 21)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cbkBepDien, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkBanHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkGiuong, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkTuQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cbkDieuHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkTuLanh, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkMayGiat, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbkNongLanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rdoConTrong, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdoDaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdoBaoDuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cbbTang, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37)
                                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(30, 30, 30))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbTang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbkDieuHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkTuLanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkMayGiat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkNongLanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbkBepDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkBanHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkGiuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbkTuQuanAo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoConTrong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoDaThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoBaoDuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbbFilterTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Quản lý phòng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        msg = new MessageFrame();
        confirm = new MessageFrame();
        try {
            if (checkNull()) {
                if (this.readForm() != null) {
                    if (!(readForm().getMaPhong()).equalsIgnoreCase(this.repoPT.checkMaPhong(readForm().getMaPhong()))) {
                        msg.showMessage("error", "Mã số phòng không tồn tại. Vui lòng không sửa mã phòng!");
                    } else {
                        confirm.showMessage("message", "Bạn có chắc chắn muốn cập nhập thông tin cho phòng này không?");
                        confirm.setOnOkClicked(() -> {
                            if (this.repoPT.update(this.readForm()) > 0) {
                                msg.showMessage("success", "Cập nhập thành công.");
                                this.fillTable(this.repoPT.getAll());
                            } else {
                                msg.showMessage("error", "Cập nhập thất bại.");
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        confirm.showMessage("message", "Bạn có chắc chắn muốn làm mới form không?");
        confirm.setOnOkClicked(() -> {
            this.clearForm();
            msg.showMessage("success", "Làm mới thành công.");
        });
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        confirm.showMessage("message", "Bạn có chắc chắn muốn làm mới dữ liệu trên bảng không?");
        confirm.setOnOkClicked(() -> {
            this.fillTable(this.repoPT.getAll());
            msg.showMessage("success", "Refresh thành công.");
        });
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void tblPhongTroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhongTroMouseClicked
        // TODO add your handling code here:
        int row = this.tblPhongTro.getSelectedRow();

        int tangSo = Integer.parseInt(this.tblPhongTro.getValueAt(row, 0).toString());
        String maPT = this.tblPhongTro.getValueAt(row, 1).toString();
        String loaiPhong = this.tblPhongTro.getValueAt(row, 2).toString();
        String dienTich = this.tblPhongTro.getValueAt(row, 3).toString();
        String giaPhong = this.tblPhongTro.getValueAt(row, 4).toString();
        String tienNghi = this.tblPhongTro.getValueAt(row, 5).toString();
        String trangThai = this.tblPhongTro.getValueAt(row, 6).toString();
        String anh = this.tblPhongTro.getValueAt(row, 7).toString();

        this.cbbTang.setSelectedItem(tangSo);
        this.txtMaPT.setText(maPT);
        this.txtLoaiPhong.setText(loaiPhong);
        this.txtDienTich.setText(dienTich);
        this.txtGiaPhong.setText(giaPhong);
//        this.txtTienNghi.setText(tienNghi);

        if (tienNghi.contains("Điều hòa")) {
            this.cbkDieuHoa.setSelected(true);
        } else {
            this.cbkDieuHoa.setSelected(false);
        }

        if (tienNghi.contains("Bình nóng lạnh")) {
            this.cbkNongLanh.setSelected(true);
        } else {
            this.cbkNongLanh.setSelected(false);
        }

        if (tienNghi.contains("Tủ lạnh")) {
            this.cbkTuLanh.setSelected(true);
        } else {
            this.cbkTuLanh.setSelected(false);
        }

        if (tienNghi.contains("Tủ quần áo")) {
            this.cbkTuQuanAo.setSelected(true);
        } else {
            this.cbkTuQuanAo.setSelected(false);
        }

        if (tienNghi.contains("Giường")) {
            this.cbkGiuong.setSelected(true);
        } else {
            this.cbkGiuong.setSelected(false);
        }

        if (tienNghi.contains("Bàn học")) {
            this.cbkBanHoc.setSelected(true);
        } else {
            this.cbkBanHoc.setSelected(false);
        }

        if (tienNghi.contains("Máy giặt")) {
            this.cbkMayGiat.setSelected(true);
        } else {
            this.cbkMayGiat.setSelected(false);
        }

        if (tienNghi.contains("Bếp điện")) {
            this.cbkBepDien.setSelected(true);
        } else {
            this.cbkBepDien.setSelected(false);
        }

//        switch (tienNghi) {
//            case "Điều hòa " -> this.cbkDieuHoa.setSelected(true);
//            case "Bình nóng lạnh " -> this.cbkNongLanh.setSelected(true);
//            case "Tủ lạnh " -> this.cbkTuLanh.setSelected(true);
//            case "Tủ quần áo " -> this.cbkTuQuanAo.setSelected(true);
//            case "Giường " -> this.cbkGiuong.setSelected(true);
//            case "Bàn học " -> this.cbkBanHoc.setSelected(true);
//            case "Máy giặt " -> this.cbkMayGiat.setSelected(true);
//            case "Bếp điện " -> this.cbkBepDien.setSelected(true);
//            default -> {
//            }
//        }
        switch (trangThai) {
            case "Còn trống" ->
                this.rdoConTrong.setSelected(true);
            case "Đã thuê" ->
                this.rdoDaThue.setSelected(true);
            default ->
                this.rdoBaoDuong.setSelected(true);
        }

        path = anh;
        ImageIcon img = new ImageIcon(path);
        lbHinhAnh.setIcon(img);

    }//GEN-LAST:event_tblPhongTroMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        confirm = new MessageFrame();
        msg = new MessageFrame();
        try {
            if (this.checkNull()) {
                if (this.readForm() != null) {
                    int soPhong = this.repoPT.getSoPhong(this.readForm().getTangSo());
                    int demPhong = this.repoPT.getDemPhong(this.readForm().getTangSo());

                    if (demPhong >= soPhong) {
                        msg.showMessage("error", "Tầng này đã đầy phòng. Vui lòng chọn tầng khác!");
                    } else {
                        if (readForm().getMaPhong().equalsIgnoreCase(this.repoPT.checkMaPhong(readForm().getMaPhong()))) {
                            msg.showMessage("error", "Mã số phòng đã tồn tại. Vui lòng chọn mã số phòng khác!");
                        } else {
                            confirm.showMessage("message", "Bạn có chắc chắn muốn thêm phòng này không?");
                            confirm.setOnOkClicked(() -> {
                                if (this.repoPT.insert(readForm()) > 0) {
                                    msg.showMessage("success", "Thêm thành công.");
                                    this.fillTable(this.repoPT.getAll());
                                } else {
                                    msg.showMessage("error", "Thêm thất bại.");
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        this.fillTable(this.repoPT.Search(this.txtSearch.getText().trim()));
    }//GEN-LAST:event_btnSearchActionPerformed

    private void lbHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbHinhAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser choo = new JFileChooser();
        int ketQua = choo.showOpenDialog(jPanel1);
        if (ketQua == JFileChooser.APPROVE_OPTION) {
            this.path = choo.getSelectedFile().getPath();
            ImageIcon icon = new ImageIcon(path);
            lbHinhAnh.setIcon(icon);
        }
    }//GEN-LAST:event_lbHinhAnhMouseClicked

    private void cbbFilterTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterTrangThaiActionPerformed
        // TODO add your handling code here:
        if (cbbFilterTrangThai.getSelectedItem().equals("Tất cả")) {
            this.fillTable(this.repoPT.getAll());
        } else if (cbbFilterTrangThai.getSelectedItem().equals("Còn trống")) {
            this.fillTable(this.repoPT.filterTrangThai(0));
        } else if (cbbFilterTrangThai.getSelectedItem().equals("Đã thuê")) {
            this.fillTable(this.repoPT.filterTrangThai(1));
        } else if (cbbFilterTrangThai.getSelectedItem().equals("Đang bảo dưỡng")) {
            this.fillTable(this.repoPT.filterTrangThai(2));
        } else {
            this.fillTable(this.repoPT.getAll());
        }
    }//GEN-LAST:event_cbbFilterTrangThaiActionPerformed

    private void txtDienTichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDienTichActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDienTichActionPerformed

    private void cbkBanHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkBanHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkBanHocActionPerformed

    private void cbkTuLanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkTuLanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkTuLanhActionPerformed

    private void cbbFilterPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbFilterPriceActionPerformed
        // TODO add your handling code here:
        if (cbbFilterPrice.getSelectedItem().equals("Tất cả")) {
            this.fillTable(this.repoPT.getAll());
        } else if (cbbFilterPrice.getSelectedItem().equals("3-4tr")) {
            this.fillTable(this.repoPT.filterPrice(3000000, 4000000));
        } else if (cbbFilterPrice.getSelectedItem().equals("4-5tr")) {
            this.fillTable(this.repoPT.filterPrice(4000000, 5000000));
        } else {
            this.fillTable(this.repoPT.getAll());
        }
    }//GEN-LAST:event_cbbFilterPriceActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        try {
            msg = new MessageFrame();
            if (checkNull()) {
                if (this.readForm() != null) {
                    if (!(readForm().getMaPhong()).equalsIgnoreCase(this.repoPT.checkMaPhong(readForm().getMaPhong()))) {
                        msg.showMessage("error", "Mã số phòng không tồn tại. Vui lòng không sửa mã phòng!");
                    } else {
                        if (this.repoPT.delete(this.readForm()) > 0) {
                            msg.showMessage("success", "Xóa thành công.");
                            this.fillTable(this.repoPT.getAll());
                        } else {
                            msg.showMessage("error", "Xóa thất bại.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private boolean checkNull() {
        String maPhong = this.txtMaPT.getText().trim();
        msg = new MessageFrame();
        if (maPhong.isEmpty()) {
            msg.showMessage("error", "Không để trống mục Số phòng!");
            this.txtMaPT.requestFocus();
            return false;
        } else if (!maPhong.matches("^[A-Z].*")) {
            msg.showMessage("error", "Mã số phòng phải bắt đầu bằng 1 chữ in hoa. VD: P102");
            this.txtMaPT.requestFocus();
            return false;
        }

        if (this.txtLoaiPhong.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Loại phòng!");
            this.txtLoaiPhong.requestFocus();
            return false;
        }

        if (this.txtDienTich.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Diện tích!");
            this.txtDienTich.requestFocus();
            return false;
        } else {
            try {
                float dienTich = Float.parseFloat(this.txtDienTich.getText().trim());
                if (dienTich < 10 || dienTich > 40) {
                    msg.showMessage("error", "Diện tích phòng chỉ trong khoảng từ 10 - 40m²");
                    this.txtDienTich.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Vui lòng chỉ nhập số");
                this.txtDienTich.requestFocus();
                return false;
            }
        }

        if (this.txtGiaPhong.getText().trim().isEmpty()) {
            msg.showMessage("error", "Không để trống mục Giá phòng!");
            this.txtGiaPhong.requestFocus();
            return false;
        } else {
            try {
                float giaPhong = Float.parseFloat(this.txtGiaPhong.getText().trim());
                if (giaPhong < 2500000 || giaPhong > 5000000) {
                    msg.showMessage("error", "Giá phòng chỉ trong khoảng từ 2.5 - 5tr");
                    this.txtGiaPhong.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                msg.showMessage("error", "Vui lòng chỉ nhập số!");
                this.txtGiaPhong.requestFocus();
                return false;
            }
        }

//        if (this.txtTienNghi.getText().trim().isEmpty()) {
//            msg.showMessage("error", "Không để trống mục Tiện nghi!");
//            this.txtTienNghi.requestFocus();
//            return false;
//        }
        return true;
    }

    private ModelPhongTro readForm() {
        int tangSo = Integer.parseInt(this.cbbTang.getSelectedItem().toString());
        String maPhong = this.txtMaPT.getText().trim();
        String loaiPhong = this.txtLoaiPhong.getText().trim();
        float dienTich = Float.parseFloat(this.txtDienTich.getText().trim());
        float giaPhong = Float.parseFloat(this.txtGiaPhong.getText().trim());
        String tienNghi = "";
        if (this.cbkDieuHoa.isSelected()) {
            tienNghi += cbkDieuHoa.getText() + " ";
        }

        if (this.cbkNongLanh.isSelected()) {
            tienNghi += cbkNongLanh.getText() + " ";
        }

        if (this.cbkTuLanh.isSelected()) {
            tienNghi += cbkTuLanh.getText() + " ";
        }

        if (this.cbkTuQuanAo.isSelected()) {
            tienNghi += cbkTuQuanAo.getText() + " ";
        }

        if (this.cbkGiuong.isSelected()) {
            tienNghi += cbkGiuong.getText() + " ";
        }

        if (this.cbkBanHoc.isSelected()) {
            tienNghi += cbkBanHoc.getText() + " ";
        }

        if (this.cbkMayGiat.isSelected()) {
            tienNghi += cbkMayGiat.getText() + " ";
        }

        if (this.cbkBepDien.isSelected()) {
            tienNghi += cbkBepDien.getText() + " ";
        }

        int trangThai = this.rdoConTrong.isSelected() ? 0 : this.rdoDaThue.isSelected() ? 1 : 2;
        String anh = this.path;

        return new ModelPhongTro(tangSo, maPhong, loaiPhong, dienTich, giaPhong, tienNghi, trangThai, anh);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.Button btnInsert;
    private view.component.button.Button btnRefresh;
    private view.component.button.Button btnReset;
    private view.component.button.Button btnSearch;
    private view.component.button.Button btnUpdate;
    private view.component.button.Button btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private view.component.combobox.Combobox cbbFilterPrice;
    private view.component.combobox.Combobox cbbFilterTrangThai;
    private view.component.combobox.Combobox cbbTang;
    private view.component.checkbox.JCheckBoxCustom cbkBanHoc;
    private view.component.checkbox.JCheckBoxCustom cbkBepDien;
    private view.component.checkbox.JCheckBoxCustom cbkDieuHoa;
    private view.component.checkbox.JCheckBoxCustom cbkGiuong;
    private view.component.checkbox.JCheckBoxCustom cbkMayGiat;
    private view.component.checkbox.JCheckBoxCustom cbkNongLanh;
    private view.component.checkbox.JCheckBoxCustom cbkTuLanh;
    private view.component.checkbox.JCheckBoxCustom cbkTuQuanAo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbHinhAnh;
    private view.component.radiobutton.RadioButtonCustom rdoBaoDuong;
    private view.component.radiobutton.RadioButtonCustom rdoConTrong;
    private view.component.radiobutton.RadioButtonCustom rdoDaThue;
    private view.component.table.Table tblPhongTro;
    private view.component.textfield.TextField txtDienTich;
    private view.component.textfield.TextField txtGiaPhong;
    private view.component.textfield.TextField txtLoaiPhong;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
