package view.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.tiendien.ModelTienDien;
import repository.tiendien.RepoTienDien;
import repository.tiendien.Validate;
import service.EventCallBack;
import service.EventDateChooser;
import service.EventTextField;
import service.SelectedAction;
import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;

public class QlyTienDien extends javax.swing.JPanel {

    private RepoTienDien repo;
    private ArrayList<ModelTienDien> list;
    private int page = 1;
    private final int limit = 5;
    private int totalPages;
    private int totalRecords;
    private String timKiem;
    private Date ngayKetThucDate;
    private Date ngayBatDauDate;
    private SimpleDateFormat formatDate;
    private MessageFrame message;
    private Validate validate;

    public QlyTienDien() {
        initComponents();
        init();
        textSearch();
        loadTable();
        updatePagination();
        dateChooser1();
        dateChooser2();
    }

    private void dateChooser1() {
        chonNgayBD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (date != null) {
                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
                    ngayBatDauDate = formatDate(textDate);
                    System.out.println(textDate);
                    if (action.getAction() == SelectedAction.DAY_SELECTED) {
                        chonNgayBD.hidePopup();
                    }
                } else {
                    ngayBatDauDate = null;
                }
            }
        });
    }

    private void dateChooser2() {
        chonNgayKT.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (date != null) {
                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
                    ngayKetThucDate = formatDate(textDate);
                    System.out.println(textDate);
                    if (action.getAction() == SelectedAction.DAY_SELECTED) {
                        chonNgayKT.hidePopup();
                    }
                } else {
                    ngayKetThucDate = null;
                }
            }
        });
    }

    private void clearAll() {
        txtChiSoCuoi.setText("");
        txtChiSoDau.setText("");
        txtGiaTien.setText("");
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaTD.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtSoDien.setText("");
        txtThanhTien.setText("");
        txtTimKiem.setText("");
    }

    private void init() {
        tbl.fixTable(scroll);
        nav.setForeground(new Color(0, 0, 250));
        setBackground(new Color(243, 243, 243));
        repo = new RepoTienDien();
        clearAll();
    }

    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
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
            }

        });
    }

    private void performSearch() {
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
        loadTable();
        updatePagination();
        if (list.isEmpty()) {
            index.setText("0");
        }
    }

    private void updatePagination() {
        index.setText(String.valueOf(page));
        end.setText(String.valueOf(totalPages));

        prev.setEnabled(page > 1);
        next.setEnabled(page < totalPages);
        if (this.list.isEmpty() || this.list == null) {
            index.setText("0");
            end.setText("0");
        }
    }

    private Date formatDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void reValidate() {
        if (ngayBatDauDate != null && ngayKetThucDate != null) {
            if (ngayBatDauDate.after(ngayKetThucDate)) {
                if (message == null) {
                    message = new MessageFrame();
                }
                message.setOnOkClicked(new Runnable() {
                    @Override
                    public void run() {
                        txtNgayBD.requestFocus();
                    }
                });
                message.showMessage("error", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            }
        }
    }

    private void delTienDien() {
        repo = new RepoTienDien();
        validate = new Validate();
        message = new MessageFrame();

        int maTD = (int) Math.ceil(validate.isNumber(txtMaTD));
        if (maTD != -1) {
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo.delete(page)) {
                        repo.delete(maTD);
                        loadTable();
                        clearAll();
                        updatePagination();
                        mess.showMessage("success", "Xóa thành công.");
                    } else {
                        mess.showMessage("error", "Xóa thất bại.");
                    }
                }
            });
            message.showMessage("message", "Bạn có muốn xóa không?");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chonNgayBD = new view.panel.DateChooser();
        chonNgayKT = new view.panel.DateChooser();
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
        btnXoa = new view.component.button.MyButton();
        btnLamMoi = new view.component.button.MyButton();
        btnThem = new view.component.button.MyButton();
        btnSua = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();

        chonNgayBD.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayBD.setEnabled(false);
        chonNgayBD.setTextRefernce(txtNgayBD);

        chonNgayKT.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayKT.setTextRefernce(txtNgayKT);

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
                        "Mã tiền điện", "Mã hóa đơn", "Mã phòng trọ", "Ngày bắt đầu", "Ngày kết thúc", "Chỉ số đầu",
                        "Chỉ số cuối", "Số điện", "Giá tiền", "Thành tiền"
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
        index.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("/");

        end.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        end.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(470, 470, 470)
                                .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(index, javax.swing.GroupLayout.PREFERRED_SIZE, 13,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 13,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroll)
                                .addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 232,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(next, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(prev, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(index, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(end, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

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

        txtNgayBD.setText("");
        txtNgayBD.setLabelText("Ngày bắt đầu");
        txtNgayBD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNgayBDMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtNgayBDMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNgayBDMouseReleased(evt);
            }
        });
        txtNgayBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayBDActionPerformed(evt);
            }
        });
        jPanel2.add(txtNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 272, -1));

        txtNgayKT.setText("");
        txtNgayKT.setLabelText("Ngày kết thúc");
        txtNgayKT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNgayKTMouseClicked(evt);
            }
        });
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

        txtSoDien.setEditable(false);
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

        txtThanhTien.setEditable(false);
        txtThanhTien.setLabelText("Thành tiền");
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });
        jPanel2.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, 272, -1));

        btnXoa.setBackground(new java.awt.Color(204, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setBorderColor(new java.awt.Color(204, 255, 255));
        btnXoa.setColor(new java.awt.Color(204, 255, 255));
        btnXoa.setColorClick(new java.awt.Color(153, 255, 255));
        btnXoa.setColorOver(new java.awt.Color(204, 255, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setRadius(20);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 210, 90, 40));

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi.setText("Làm mới");
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
        jPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 210, 90, 40));

        btnThem.setBackground(new java.awt.Color(204, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setBorderColor(new java.awt.Color(204, 255, 255));
        btnThem.setColor(new java.awt.Color(204, 255, 255));
        btnThem.setColorClick(new java.awt.Color(153, 255, 255));
        btnThem.setColorOver(new java.awt.Color(204, 255, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setRadius(20);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 150, 90, 40));

        btnSua.setBackground(new java.awt.Color(204, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setBorderColor(new java.awt.Color(204, 255, 255));
        btnSua.setColor(new java.awt.Color(204, 255, 255));
        btnSua.setColorClick(new java.awt.Color(153, 255, 255));
        btnSua.setColorOver(new java.awt.Color(204, 255, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setRadius(20);
        jPanel2.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 150, 90, 40));

        txtTimKiem.setLabelText("Tìm mã phòng trọ");
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 50, 220, -1));

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
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                1069, Short.MAX_VALUE)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap()))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(nav)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap()));
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {
        clearAll();
        loadTable();
        updatePagination();
    }

    private void txtNgayBDMouseClicked(java.awt.event.MouseEvent evt) {
        chonNgayBD.showPopup();
    }

    private void txtNgayKTMouseClicked(java.awt.event.MouseEvent evt) {
        chonNgayKT.showPopup();
    }

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        validate = new Validate();
        validate.checkEmpty(txtMaTD);
        validate.checkEmpty(txtMaHD);
        validate.checkEmpty(txtMaPT);
        validate.checkEmpty(txtNgayBD);
        validate.checkEmpty(txtNgayKT);
        
        int maTD = (int) Math.ceil(validate.isNumber(txtMaTD));
        String maHD = validate.checkEmptyAndGetText(txtMaHD);
        String maPT = validate.checkEmptyAndGetText(txtMaPT);
        String ngayBDString = validate.checkEmptyAndGetText(txtNgayBD);
        String ngayKTString = validate.checkEmptyAndGetText(txtNgayKT);
        System.out.println("matd: " + maTD);
        System.out.println("maHD: " + maHD);
        System.out.println("mapt: " + maPT);
        System.out.println("nbd: " + ngayBDString);
        System.out.println("nkt: " + ngayKTString);
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        delTienDien();
    }

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {
        if (page > 1) {
            page--;
            loadTable();
            updatePagination();
            prev.requestFocusInWindow();
        }
    }

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {
        if (page < totalPages) {
            page++;
            loadTable();
            updatePagination();
            next.requestFocusInWindow();
        }
    }

    private void txtNgayBDMousePressed(java.awt.event.MouseEvent evt) {

    }

    private void txtNgayBDMouseReleased(java.awt.event.MouseEvent evt) {

    }

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtMaTDActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtMaPTActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtNgayBDActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void prevMouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void txtNgayKTActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtChiSoDauActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtChiSoCuoiActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtSoDienActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtGiaTienActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnSua;
    private view.component.button.MyButton btnThem;
    private view.component.button.MyButton btnXoa;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel end;
    private javax.swing.JLabel index;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private view.component.textfield.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
