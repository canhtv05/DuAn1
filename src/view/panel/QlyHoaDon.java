package view.panel;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.hoadon.ModelHoaDon;
import repository.hoadon.RepoHoaDon;
import repository.validate.Validate;
import view.component.message.MessageFrame;

public class QlyHoaDon extends javax.swing.JPanel {

    private RepoHoaDon repo;
    private ArrayList<ModelHoaDon> list;
    private DefaultTableModel model;
    private int page = 1;
    private final int limit = 20;
    private int totalPages;
    private int totalRecords;
    private String timKiem;
    private Date ngayKetThucDate;
    private Date ngayBatDauDate;
    private SimpleDateFormat formatDate;
    private MessageFrame message;
    private Validate validate;
    private double thanhTien;
    private int locTheoThang;
    private int locTheoNam;
    private boolean isClearing = false;
    private BigDecimal formatThanhTien;
    private int selectRow = -1;
    public static String maPhongString;
    public static double tienDien;
    public static double tienNuoc;
    public static double tienDichVu;
    public static double tongTien;

    public QlyHoaDon() {
        initComponents();
        init();
        textSearch();
        loadTable();
        if (!list.isEmpty()) {
            display(0);
            selectRow = 0;
        }
        btnExcel.setVisible(false);
    }

    private void init() {
        tbl.fixTable(scroll);
        nav.setForeground(new Color(0, 0, 250));
        setBackground(new Color(243, 243, 243));
        repo = new RepoHoaDon();
    }

    private void loadTable() {
        initializeTableModel();
        loadRecords();
        addRowsToTable();
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
    }

    private void initializeTableModel() {
        model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem, locTheoThang, locTheoNam);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
    }

    private void loadRecords() {
        repo = new RepoHoaDon();
        list = repo.searchAndPage(timKiem, page, limit, locTheoThang, locTheoNam);
    }

    private void addRowsToTable() {
        for (ModelHoaDon hd : list) {
            Object[] rows = {
                hd.getMaHD(),
                hd.getMaPT(),
                hd.getMaNguoiTao(),
                hd.getNgayLap(),
                hd.getHanThanhToan(),
                hd.getTrangThai() == 0 ? "Đã thanh toán" : "Chưa thanh toán",
                hd.getNgayThanhToan(),
                hd.getTienDien(),
                hd.getTienNuoc(),
                hd.getTienDichVu(),
                hd.getTongTien()
            };
            model.addRow(rows);
        }
    }

    private void textSearch() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }

        });
    }

    private void clearAll1() {
        isClearing = true;
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaNT.setText("");
        txtNgayLap.setText("");
        txtHanTT.setText("");
        txtNgayTT.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        txtTienDV.setText("");
        txtThanhTien.setText("");
        txtTimKiem.setText("");
        rdoChuaTT.setSelected(false);
        rdoDaTT.setSelected(false);
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        ngayBatDauDate = null;
        ngayKetThucDate = null;
        isClearing = false;
    }

    private void clearAll2() {
        isClearing = true;
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaNT.setText("");
        txtNgayLap.setText("");
        txtHanTT.setText("");
        txtNgayTT.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        txtTienDV.setText("");
        txtThanhTien.setText("");
        rdoChuaTT.setSelected(false);
        rdoDaTT.setSelected(false);
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        ngayBatDauDate = null;
        ngayKetThucDate = null;
        isClearing = false;
    }

    private void display(int select) {
        String maHDString = tbl.getValueAt(select, 0).toString();
        String maPTString = tbl.getValueAt(select, 1).toString();
        String maNTString = tbl.getValueAt(select, 2).toString();
        String ngayLapString = tbl.getValueAt(select, 3).toString();
        String hanTTString = tbl.getValueAt(select, 4).toString();
        String trangThaiString = tbl.getValueAt(select, 5).toString();
        Object ngayTTObj = tbl.getValueAt(select, 6);
        String tienDienString = tbl.getValueAt(select, 7).toString();
        String tienNuocString = tbl.getValueAt(select, 8).toString();
        String tienDVString = tbl.getValueAt(select, 9).toString();
        String thanhTienString = tbl.getValueAt(select, 10).toString();

        txtMaHD.setText(maHDString);
        txtMaPT.setText(maPTString);
        txtMaNT.setText(maNTString);
        txtNgayLap.setText(ngayLapString);
        txtHanTT.setText(hanTTString);
        
        if (ngayTTObj != null) {
            txtNgayTT.setText(ngayTTObj.toString());
        } else {
            txtNgayTT.setText("");
        }
        
        txtTienDien.setText(tienDienString);
        txtTienNuoc.setText(tienNuocString);
        txtTienDV.setText(tienDVString);
        formatThanhTien = new BigDecimal(Double.parseDouble(thanhTienString));
        BigDecimal roundThanhTien = formatThanhTien.setScale(1, RoundingMode.UP);
        txtThanhTien.setText(roundThanhTien.toPlainString() + "đ");
        rdoDaTT.setSelected(true);
        if (trangThaiString.equalsIgnoreCase("Chưa thanh toán")) {
            rdoChuaTT.setSelected(true);
        }
    }

    private void performSearch() {
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem, locTheoThang, locTheoNam);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
        loadTable();
        updatePagination();
        if (list.isEmpty()) {
            clearAll2();
        } else {
            display(list.size() - 1);
        }
        locThang.setSelectedIndex(0);
        locNam.setSelectedIndex(0);
    }

    private void updatePagination() {
        index.setText(String.valueOf(page));
        end.setText(String.valueOf(totalPages));

        prev.setEnabled(page > 1);
        next.setEnabled(page < totalPages);
        if (this.list.isEmpty()) {
            index.setText("0");
            end.setText("0");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        nav = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        tbl = new view.component.table.Table();
        prev = new view.component.button.MyButton();
        next = new view.component.button.MyButton();
        index = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        end = new javax.swing.JLabel();
        txtTongHD = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtMaHD = new view.component.textfield.TextField();
        txtMaPT = new view.component.textfield.TextField();
        txtMaNT = new view.component.textfield.TextField();
        txtNgayLap = new view.component.textfield.TextField();
        txtHanTT = new view.component.textfield.TextField();
        txtNgayTT = new view.component.textfield.TextField();
        txtTienDien = new view.component.textfield.TextField();
        txtTienNuoc = new view.component.textfield.TextField();
        txtTienDV = new view.component.textfield.TextField();
        btnLamMoi = new view.component.button.MyButton();
        btnSua = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();
        rdoChuaTT = new view.component.radiobutton.RadioButtonCustom();
        rdoDaTT = new view.component.radiobutton.RadioButtonCustom();
        locThang = new view.component.combobox.Combobox();
        locNam = new view.component.combobox.Combobox();
        btnExcel = new view.component.button.MyButton();
        btnPdf = new view.component.button.MyButton();
        btnLoc = new view.component.button.MyButton();
        txtThanhTien = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        nav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nav.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nav.setText("Hóa đơn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã phòng trọ", "Mã người tạo", "Ngày lập", "Hạn thanh toán", "Trạng thái", "Ngày thanh toán", "Tiền điện", "Tiền nước", "Tiền dịch vụ", "Tổng tiền"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        scroll.setViewportView(tbl);

        prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_back_arrow_24px.png"))); // NOI18N
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

        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_forward_button_24px.png"))); // NOI18N
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
        index.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("/");

        end.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        end.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end.setText("0");

        txtTongHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTongHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tổng số hóa đơn:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(446, 446, 446)
                        .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(index, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongHD, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(next, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prev, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(index, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(end, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaHD.setEditable(false);
        txtMaHD.setLabelText("Mã hóa đơn");
        txtMaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 272, -1));

        txtMaPT.setEditable(false);
        txtMaPT.setLabelText("Mã phòng trọ");
        txtMaPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPTActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 272, -1));

        txtMaNT.setEditable(false);
        txtMaNT.setLabelText("Mã người tạo");
        txtMaNT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNTActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaNT, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 272, -1));

        txtNgayLap.setLabelText("Ngày lập");
        txtNgayLap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNgayLapMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNgayLapMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNgayLapMouseReleased(evt);
            }
        });
        txtNgayLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayLapActionPerformed(evt);
            }
        });
        jPanel2.add(txtNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 272, -1));

        txtHanTT.setLabelText("Hạn thanh toán");
        txtHanTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtHanTTMouseClicked(evt);
            }
        });
        txtHanTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHanTTActionPerformed(evt);
            }
        });
        jPanel2.add(txtHanTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 272, -1));

        txtNgayTT.setLabelText("Ngày thanh toán");
        txtNgayTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTTActionPerformed(evt);
            }
        });
        jPanel2.add(txtNgayTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 272, -1));

        txtTienDien.setEditable(false);
        txtTienDien.setLabelText("Tiền điện");
        txtTienDien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDienActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 272, -1));

        txtTienNuoc.setEditable(false);
        txtTienNuoc.setLabelText("Tiền nước");
        txtTienNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienNuocActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienNuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 272, -1));

        txtTienDV.setEditable(false);
        txtTienDV.setLabelText("Tiền dịch vụ");
        txtTienDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDVActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 272, -1));

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnLamMoi.setText(" Làm mới");
        btnLamMoi.setBorderColor(new java.awt.Color(204, 255, 255));
        btnLamMoi.setColor(new java.awt.Color(204, 255, 255));
        btnLamMoi.setColorClick(new java.awt.Color(153, 255, 255));
        btnLamMoi.setColorOver(new java.awt.Color(204, 255, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setRadius(20);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 80, 110, 40));

        btnSua.setBackground(new java.awt.Color(204, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnSua.setText("Cập nhật");
        btnSua.setBorderColor(new java.awt.Color(204, 255, 255));
        btnSua.setColor(new java.awt.Color(204, 255, 255));
        btnSua.setColorClick(new java.awt.Color(153, 255, 255));
        btnSua.setColorOver(new java.awt.Color(204, 255, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setRadius(20);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel2.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 110, 40));

        txtTimKiem.setLabelText("Tìm kiếm\n");
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 220, -1));

        rdoChuaTT.setBackground(new java.awt.Color(255, 0, 51));
        buttonGroup1.add(rdoChuaTT);
        rdoChuaTT.setForeground(new java.awt.Color(150, 150, 150));
        rdoChuaTT.setText("Chưa thanh toán");
        jPanel2.add(rdoChuaTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, -1));

        rdoDaTT.setBackground(new java.awt.Color(0, 204, 102));
        buttonGroup1.add(rdoDaTT);
        rdoDaTT.setForeground(new java.awt.Color(150, 150, 150));
        rdoDaTT.setText("Đã thanh toán");
        jPanel2.add(rdoDaTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 100, -1));

        locThang.setMaximumRowCount(12);
        locThang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "        ", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));
        locThang.setLabeText("Lọc theo tháng");
        locThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locThangActionPerformed(evt);
            }
        });
        jPanel2.add(locThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 160, -1));

        locNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        locNam.setLabeText("Lọc theo năm");
        locNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locNamActionPerformed(evt);
            }
        });
        jPanel2.add(locNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, 160, -1));

        btnExcel.setBackground(new java.awt.Color(204, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_2019_24px.png"))); // NOI18N
        btnExcel.setText("Xuất Excel");
        btnExcel.setBorderColor(new java.awt.Color(204, 255, 255));
        btnExcel.setColor(new java.awt.Color(204, 255, 255));
        btnExcel.setColorClick(new java.awt.Color(153, 255, 255));
        btnExcel.setColorOver(new java.awt.Color(204, 255, 255));
        btnExcel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExcel.setRadius(20);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel2.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 140, -1, 40));

        btnPdf.setBackground(new java.awt.Color(204, 255, 255));
        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_pdf_24px.png"))); // NOI18N
        btnPdf.setText("Xuất PDF");
        btnPdf.setBorderColor(new java.awt.Color(204, 255, 255));
        btnPdf.setColor(new java.awt.Color(204, 255, 255));
        btnPdf.setColorClick(new java.awt.Color(153, 255, 255));
        btnPdf.setColorOver(new java.awt.Color(204, 255, 255));
        btnPdf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPdf.setRadius(20);
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        jPanel2.add(btnPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 110, 40));

        btnLoc.setBackground(new java.awt.Color(204, 255, 255));
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_filter_24px.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.setBorderColor(new java.awt.Color(204, 255, 255));
        btnLoc.setColor(new java.awt.Color(204, 255, 255));
        btnLoc.setColorClick(new java.awt.Color(153, 255, 255));
        btnLoc.setColorOver(new java.awt.Color(204, 255, 255));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setRadius(20);
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });
        jPanel2.add(btnLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 150, 110, 40));

        txtThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtThanhTien.setForeground(new java.awt.Color(255, 0, 51));
        txtThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel2.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 200, 200, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Thành tiền:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 200, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nav)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nav)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        int select = tbl.getSelectedRow();
        selectRow = select;
        if (select == -1) {
            return;
        }
        display(select);
    }//GEN-LAST:event_tblMouseClicked

    private void prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_prevMouseClicked

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prevActionPerformed

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_nextMouseClicked

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nextActionPerformed

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void txtMaPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPTActionPerformed

    private void txtMaNTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNTActionPerformed

    private void txtNgayLapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayLapMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapMouseClicked

    private void txtNgayLapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayLapMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapMousePressed

    private void txtNgayLapMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayLapMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapMouseReleased

    private void txtNgayLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayLapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayLapActionPerformed

    private void txtHanTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHanTTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHanTTMouseClicked

    private void txtHanTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHanTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHanTTActionPerformed

    private void txtNgayTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTTActionPerformed

    private void txtTienDienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienDienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienDienActionPerformed

    private void txtTienNuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienNuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienNuocActionPerformed

    private void txtTienDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienDVActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void locThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locThangActionPerformed
        int month = locThang.getSelectedIndex();
        locTheoThang = month;
        if (locNam.getSelectedIndex() == 0) {
            locTheoNam = 0;
        }
        String text = locNam.getSelectedItem() + "";
        if (text.trim().isEmpty()) {
            locTheoNam = 0;
        } else {
            int year = Integer.parseInt(text);
            locTheoNam = year;
        }
        page = 1;
        loadTable();
        updatePagination();
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
        if (list.isEmpty()) {
            clearAll2();
        } else if (!list.isEmpty() && !isClearing) {
            display(list.size() - 1);
        }
    }//GEN-LAST:event_locThangActionPerformed

    private void locNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locNamActionPerformed
        int month = locThang.getSelectedIndex();
        locTheoThang = month;
        if (locNam.getSelectedIndex() == 0) {
            locTheoNam = 0;
        }
        String text = locNam.getSelectedItem() + "";
        if (text.trim().isEmpty()) {
            locTheoNam = 0;
        } else {
            int year = Integer.parseInt(text);
            locTheoNam = year;
        }
        page = 1;
        loadTable();
        updatePagination();
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
        if (list.isEmpty()) {
            clearAll2();
        } else if (!list.isEmpty() && !isClearing) {
            display(list.size() - 1);
        }
    }//GEN-LAST:event_locNamActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        if (selectRow == -1) {
            message = new MessageFrame();
            message.showMessage("error", "Vui lòng chọn 1 hàng trên bảng.");
            return;
        }
        else {
            maPhongString = txtMaPT.getText().trim();
            tienDien = Double.parseDouble(txtTienDien.getText().trim());
            tienNuoc = Double.parseDouble(txtTienNuoc.getText().trim());
            tienDichVu = Double.parseDouble(txtTienDV.getText().trim());
            tongTien = Double.parseDouble(txtThanhTien.getText().trim().substring(0, txtThanhTien.getText().trim().length() - 2));
            new ExportPdf().setVisible(true);
        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnExcel;
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnLoc;
    private view.component.button.MyButton btnPdf;
    private view.component.button.MyButton btnSua;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel end;
    private javax.swing.JLabel index;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private view.component.combobox.Combobox locNam;
    private view.component.combobox.Combobox locThang;
    private javax.swing.JLabel nav;
    private view.component.button.MyButton next;
    private view.component.button.MyButton prev;
    private view.component.radiobutton.RadioButtonCustom rdoChuaTT;
    private view.component.radiobutton.RadioButtonCustom rdoDaTT;
    private javax.swing.JScrollPane scroll;
    private view.component.table.Table tbl;
    private view.component.textfield.TextField txtHanTT;
    private view.component.textfield.TextField txtMaHD;
    private view.component.textfield.TextField txtMaNT;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtNgayLap;
    private view.component.textfield.TextField txtNgayTT;
    private javax.swing.JLabel txtThanhTien;
    private view.component.textfield.TextField txtTienDV;
    private view.component.textfield.TextField txtTienDien;
    private view.component.textfield.TextField txtTienNuoc;
    private view.component.textfield.TextField txtTimKiem;
    private javax.swing.JLabel txtTongHD;
    // End of variables declaration//GEN-END:variables
}
