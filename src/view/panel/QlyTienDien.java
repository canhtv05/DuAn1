package view.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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
    private int getChiSoDau;
    private int getChiSoCuoi;
    private int soDien;
    private double giaTien;
    private double thanhTien;
    private int maTD;

    public QlyTienDien() {
        initComponents();
        init();
        textSearch();
        loadTable();
        updatePagination();
        dateChooser();
        getTextOnChange();
        display(list.size() - 1);
//        SelectedDate date = chonNgayBD.getSelectedDate();
//        ngayBatDauDate = formatDate(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
//        SelectedDate date2 = chonNgayKT.getSelectedDate();
//        ngayKetThucDate = formatDate(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
//        System.out.println(ngayBatDauDate);
//        System.out.println(ngayKetThucDate);
    }

    private void dateChooser() {
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
    }

    private void updatePagination() {
        index.setText(String.valueOf(page));
        end.setText(String.valueOf(totalPages));

        prev.setEnabled(page > 1);
        next.setEnabled(page < totalPages);
        if (this.list.isEmpty() || this.list == null) {
            index.setText("0");
            end.setText("0");
        } else {
            index.setText(String.valueOf(page));
            end.setText(String.valueOf(totalPages));
        }
    }

    private Date formatDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        formatDate = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatDate.parse(date);
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

        if (maTD != -1) {
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo.delete(maTD) == true) {
                        mess.showMessage("success", "Xóa thành công.");
                    } else {
                        mess.showMessage("error", "Xóa thất bại.");
                    }
                    loadTable();
                    clearAll();
                    updatePagination();
                }
            });
            message.showMessage("message", "Bạn có muốn xóa không?");
        }
    }

    private void getTextOnChange() {
        txtChiSoDau.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(txtChiSoDau, "Chỉ số đầu");
                showSoDien();
                showThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(txtChiSoDau, "Chỉ số đầu");
                showSoDien();
                showThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        txtChiSoCuoi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(txtChiSoCuoi, "Chỉ số cuối");
                showSoDien();
                showThanhTien();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(txtChiSoCuoi, "Chỉ số cuối");
                showSoDien();
                showThanhTien();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
//        txtGiaTien.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                handleTextChange(txtGiaTien, "Giá tiền");
//                showThanhTien();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                handleTextChange(txtGiaTien, "Giá tiền");
//                showThanhTien();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });
        txtMaTD.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange(txtMaTD, "Mã tiền điện");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange(txtMaTD, "Mã tiền điện");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void showSoDien() {
        if (getChiSoDau == 0 && getChiSoCuoi == 0) {
            soDien = 0;
        } else if (getChiSoDau == 0) {
            soDien = getChiSoCuoi;
        } else if (getChiSoCuoi == 0) {
            soDien = getChiSoDau;
        } else if (getChiSoDau != 0 && getChiSoCuoi != 0) {
            soDien = getChiSoCuoi - getChiSoDau;
        }
        txtSoDien.setText(soDien + "");
    }

    private void showThanhTien() {
        if (getChiSoDau == 0 && getChiSoCuoi == 0 && giaTien == 0.0) {
            thanhTien = 0;
        } else if (giaTien == 0.0) {
            thanhTien = 0;
        } else if ((getChiSoCuoi - getChiSoDau) <= 0) {
            thanhTien = 0;
        } else if (getChiSoCuoi != 0 && getChiSoDau != 0 && giaTien != 0.0) {
            thanhTien = 1l * ((getChiSoCuoi - getChiSoDau)) * giaTien;
        }
        txtThanhTien.setText(thanhTien + "");
    }

    private void handleTextChange(JTextField textField, String fieldName) {
        message = new MessageFrame();
        String txt = textField.getText().trim();
        if (txt.isEmpty()) {
            return;
        }
        if (txt.length() > 9) {
            message.showMessage("error", "Số quá lớn.");
            return;
        }
        int value = convertToInt(txt);
        if (value == -1) {
            textField.requestFocus();
            message.showMessage("error", fieldName + " phải là số.");
            return;
        }
        if (value >= Integer.MAX_VALUE || txt.length() > 9) {
            message.showMessage("error", "Số quá lớn.");
            return;
        }
        if (textField == txtChiSoDau) {
            getChiSoDau = value;
        } else if (textField == txtChiSoCuoi) {
            getChiSoCuoi = value;
        } else if (textField == txtGiaTien) {
            giaTien = value;
        } else if (textField == txtMaTD) {
            maTD = value;
        }
        if (getChiSoDau != 0 && getChiSoCuoi != 0) {
            if (getChiSoDau > getChiSoCuoi) {
                message.showMessage("error", "Chỉ số đầu phải nhỏ hơn chỉ số cuối.");
                return;
            }
        }
        System.out.println("chiso dau: " + getChiSoDau);
        System.out.println("chiso cuoi: " + getChiSoCuoi);
        System.out.println("gia tien: " + giaTien);
        System.out.println("maTD: " + maTD);
        System.out.println("ngaybd: " +chonNgayBD);
        System.out.println("ngaykt: " +chonNgayKT);
    }

    private int convertToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("loi chuyen doi");
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã tiền điện", "Mã hóa đơn", "Mã phòng trọ", "Ngày bắt đầu", "Ngày kết thúc", "Chỉ số đầu", "Chỉ số cuối", "Số điện", "Giá tiền", "Thành tiền"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
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
                .addGap(451, 451, 451)
                .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(index, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll)
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        txtChiSoDau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtChiSoDauMousePressed(evt);
            }
        });
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
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
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

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        message = new MessageFrame();
        message.showMessage("warning", "Chưa làm.");
        return;
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {
        int select = tbl.getSelectedRow();
        if (select == -1) {
            return;
        } else {
            display(select);
        }
    }

    private void display(int select) {
        String maTDString = tbl.getValueAt(select, 0).toString();
        String maHDString = tbl.getValueAt(select, 1).toString();
        String maPTString = tbl.getValueAt(select, 2).toString();
        String ngayBDString = tbl.getValueAt(select, 3).toString();
        String ngayKTString = tbl.getValueAt(select, 4).toString();
        String chiSoDauString = tbl.getValueAt(select, 5).toString();
        String chiSoCuoiString = tbl.getValueAt(select, 6).toString();
        String soDienString = tbl.getValueAt(select, 7).toString();
        String giaTienString = tbl.getValueAt(select, 8).toString();
        String thanhTienString = tbl.getValueAt(select, 9).toString();

        txtMaTD.setText(maTDString);
        txtMaHD.setText(maHDString);
        txtMaPT.setText(maPTString);
        txtNgayBD.setText(ngayBDString);
        txtNgayKT.setText(ngayKTString);
        txtChiSoDau.setText(chiSoDauString);
        txtChiSoCuoi.setText(chiSoCuoiString);
        txtSoDien.setText(soDienString);
        txtGiaTien.setText(giaTienString);
        txtThanhTien.setText(thanhTienString);
    }

    private void txtChiSoDauMousePressed(java.awt.event.MouseEvent evt) {

    }

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
        repo = new RepoTienDien();
        message = new MessageFrame();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        validate = new Validate();

        if (!validate.isNull(txtMaTD)) {
            return;
        }
        if (validate.isNumber(txtMaTD) == -1.0) {
            return;
        }
        if (!validate.isNull(txtMaHD)) {
            return;
        }
        if (!validate.isNull(txtMaPT)) {
            return;
        }
        if (!validate.isNull(txtNgayBD)) {
            return;
        }
        if (!validate.isNull(txtNgayKT)) {
            return;
        }
        if (!validate.isNull(txtChiSoDau)) {
            return;
        }
        if (!validate.isNull(txtChiSoCuoi)) {
            return;
        }

        if (!validate.isNull(txtChiSoDau)) {
            return;
        }

        if (!validate.isNull(txtChiSoCuoi)) {
            return;
        }

        if (!validate.isNull(txtSoDien)) {
            return;
        }

        if (validate.isNumber(txtGiaTien) == -1.0) {
            return;
        }

        maTD = Integer.parseInt(txtMaTD.getText());
        String maHD = txtMaHD.getText().trim();
        String maPT = txtMaPT.getText().trim();
        String ngayBDString = txtNgayBD.getText().trim();
        String ngayKTString = txtNgayKT.getText().trim();
        String ngayBD = null;
        String ngayKT = null;
        try {
            // Phân tích cú pháp chuỗi ngày tháng đầu vào thành đối tượng Date
            Date dateBD = inputFormat.parse(ngayBDString);
            Date dateKT = inputFormat.parse(ngayKTString);

            // Định dạng lại đối tượng Date thành chuỗi theo định dạng đầu ra
            ngayBD = outputFormat.format(dateBD);
            ngayKT = outputFormat.format(dateKT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        giaTien = Double.parseDouble(txtGiaTien.getText());
        thanhTien = Double.parseDouble(txtThanhTien.getText());

        if (!repo.checkQuanHe(maHD, maPT)) {
            message.showMessage("error", "Mã HD hoặc Mã PT không chính xác.");
            return;
        }
        reValidate();

        System.out.println("matd: " + maTD);
        System.out.println("maHD: " + maHD);
        System.out.println("mapt: " + maPT);
        System.out.println("nbd: " + ngayBDString);
        System.out.println("nkt: " + ngayKTString);
        System.out.println("ngay bd: " + ngayBD);
        System.out.println("ngay kt: " + ngayKT);
        System.out.println("getnagaybd: " + ngayBatDauDate);
        System.out.println("getnagaykt: " + ngayKetThucDate);
        System.out.println("chi so dau: " + getChiSoDau);
        System.out.println("chi so cuoi: " + getChiSoCuoi);
        System.out.println("so dien: " + soDien);
        System.out.println("gia tien: " + giaTien);
        System.out.println("thanhtien: " + thanhTien);

        ModelTienDien modelTienDien = new ModelTienDien(maHD, maPT, ngayBD, ngayKT, getChiSoDau, getChiSoCuoi, giaTien);
        if (repo.add(modelTienDien)) {
            loadTable();
            clearAll();
            updatePagination();
            message.showMessage("success", "Thêm thành công.");
        } else {
            message.showMessage("success", "Thêm thất bại.");
        }
    }

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        String text = txtMaTD.getText().trim();
        if (text.isEmpty()) {
            message = new MessageFrame();
            txtMaTD.requestFocus();
            message.showMessage("error", "Bạn cần nhập Mã TĐ để xóa.");
            return;
        }
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
