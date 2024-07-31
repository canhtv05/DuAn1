package view.panel;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.tiennuoc.ModelTienNuoc;
import repository.validate.Validate;
import repository.tiennuoc.RepoTienNuoc;
import service.EventDateChooser;
import service.SelectedAction;
import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;
import view.main.viewSignIn;

public class QlyTienNuoc extends javax.swing.JPanel {

    private RepoTienNuoc repo;
    private ArrayList<ModelTienNuoc> list;
    private DefaultTableModel model;
    private int page = 1;
    private final int limit = 10;
    private int totalPages;
    private int totalRecords;
    private String timKiem;
    private Date ngayKetThucDate;
    private Date ngayBatDauDate;
    private SimpleDateFormat formatDate;
    private MessageFrame message;
    private Validate validate;
    private int getDauNguoi;
    private double giaTien;
    private double thanhTien;
    private int maTN;
    private int maHD;
    private BigDecimal formatThanhTien;
    private BigDecimal formatGiaTien;
    private double getGiaTienChung;
    private boolean isUpdating = false;
    private int locTheoThang = 0;
    private int locTheoNam = 0;
    private boolean isClearing = false;
    private String maNV = null;
    private int hd;

    public QlyTienNuoc() {
        initComponents();
        init();
        textSearch();
        loadTable();
        updatePagination();
        dateChooser();
        getTextOnChange();
        if (!list.isEmpty()) {
            display(list.size() - 1);
        }
    }

    private void dateChooser() {
        chonNgayBD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (date != null) {
                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
                    ngayBatDauDate = formatDate(textDate);
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
        isClearing = true;
        txtDauNguoi.setText("");
        txtGiaTien.setText("");
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaTN.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtThanhTien.setText("");
        txtTimKiem.setText("");
        getDauNguoi = 0;
        giaTien = 0;
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        locNam.setSelectedIndex(0);
        locThang.setSelectedIndex(0);
        isClearing = false;
    }

    private void clearAll3() {
        isClearing = true;
        txtDauNguoi.setText("");
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaTN.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtThanhTien.setText("");
        txtGiaTien.setText("");
        giaTien = 0;
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        ngayBatDauDate = null;
        ngayKetThucDate = null;
        isClearing = false;
    }

    private void init() {
        tbl.fixTable(scroll);
        nav.setForeground(new Color(0, 0, 250));
        setBackground(new Color(243, 243, 243));
        repo = new RepoTienNuoc();
        String usernameString = viewSignIn.getUsernameString;
        maNV = repo.timMaNV(usernameString);
        if (maNV == null) {
            maNV = "NV001";
        }
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
        repo = new RepoTienNuoc();
        list = repo.searchAndPage(timKiem, page, limit, locTheoThang, locTheoNam);
    }

    private void addRowsToTable() {
        for (ModelTienNuoc tn : list) {
            Object[] rows = {
                tn.getMaTN(),
                tn.getMaHD(),
                tn.getMaPT(),
                tn.getNgayBD(),
                tn.getNgayKT(),
                tn.getDauNguoi(),
                tn.getGiaTien(),
                tn.getThanhTien()
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

    private void performSearch() {
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem, locTheoThang, locTheoNam);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
        loadTable();
        updatePagination();
        if (list.isEmpty()) {
            clearAll3();
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

    private boolean reValidate() {
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
                return false;
            }
        }
        return true;
    }

    public double formatDouble(double number) {
        return Math.round(number * 10000.0) / 10000.0;
    }

    private void delTienNuoc() {
        validate = new Validate();

        if (maTN != -1) {
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo.delete(maTN) == true) {
                        mess.showMessage("success", "Xóa thành công.");
                    } else {
                        mess.showMessage("error", "Xóa thất bại.");
                    }
                    loadTable();
                    clearAll();
                    updatePagination();

                    if (list.isEmpty()) {
                        initializeTableModel();

                        index.setText(String.valueOf(page - 1));
                        end.setText(String.valueOf(totalPages));

                        prev.setEnabled(page > 1);
                        next.setEnabled(page < totalPages);
                        list = repo.searchAndPage(timKiem, page - 1, limit, locTheoThang, locTheoNam);
                        addRowsToTable();
                    }
                }
            });
            message.showMessage("message", "Bạn có muốn xóa không?");
        }
    }

    private void getTextOnChange() {
        txtDauNguoi.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtDauNguoi, "Đầu người");
                    showThanhTien();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtDauNguoi, "Đầu người");
                    showThanhTien();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        txtMaTN.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaTN, "Mã tiền nước");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaTN, "Mã tiền nước");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        txtMaHD.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaHD, "Mã hóa đơn");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaHD, "Mã hóa đơn");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        tien();
    }

    private void tien() {
        txtGiaTien.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleDocumentChange();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleDocumentChange();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void handleDocumentChange() {
        if (!isUpdating) {
            isUpdating = true;
            SwingUtilities.invokeLater(() -> {
                handleDecimal(txtGiaTien, "Giá tiền");
                showThanhTien();
                isUpdating = false;
            });
        }
    }

    private void handleDecimal(JTextField text, String fieldName) {
        message = new MessageFrame();
        String txt = text.getText().trim();
        try {
            if (isValidateDecimal(txt)) {
                if (!txtGiaTien.getText().trim().isEmpty()) {
                    giaTien = Double.parseDouble(txtGiaTien.getText().trim());
                    String count = giaTien + "";
                    if (countDecimals(count) == 1) {
                        int findDot = count.indexOf(".");
                        String numString = count.substring(0, findDot);
                        int findDot2 = -1;
                        if (numString.matches("\\w+")) {
                            numString = txtGiaTien.getText().trim();
                            if (numString.contains(".")) {
                                findDot2 = numString.indexOf(".") - 1;
                                numString = numString.substring(0, findDot2);
                            }
                        }
                        if (numString.length() > 9) {
                            String setText = numString.substring(0, 9);
                            txtGiaTien.setText(setText);
                            giaTien = Double.parseDouble(setText);
                            message.showMessage("error", "Số quá lớn.");
                            return;
                        }
                    }
                }
            } else {
                String txtSub = txtGiaTien.getText().trim();
                int index = -1;

                Pattern pattern = Pattern.compile("[a-zA-Z]");
                Matcher matcher = pattern.matcher(txtSub);
                if (matcher.find()) {
                    index = matcher.start();
                    txtGiaTien.setText(txtSub.substring(0, index) + txt.substring(index + 1));
                } else {
                    txtGiaTien.setText(txtSub.substring(0, txtSub.length() - 1));
                }
                message.showMessage("error", fieldName + " phải là số.");
            }
        } catch (NumberFormatException e) {
            message.showMessage("error", fieldName + " phải là số.");
        }
    }

    private int countDecimals(String text) {
        int cnt = 0;
        for (char c : text.toCharArray()) {
            if (c == '.') {
                cnt++;
            }
        }
        return cnt;
    }

    private boolean isValidateDecimal(String text) {
        String regex = "^\\d*\\.?\\d*$";
        int countDot = countDecimals(text);
        return text.matches(regex) && countDot <= 1;
    }

    private void showThanhTien() {
        if (getDauNguoi == 0 && giaTien == 0.0) {
            thanhTien = 0;
        }
        if (giaTien == 0.0) {
            thanhTien = 0;
            txtThanhTien.setText("0.0");
        }
        if (getDauNguoi == 0.0) {
            thanhTien = 0;
        }
        if (getDauNguoi != 0 && giaTien != 0.0) {
            thanhTien = getDauNguoi * giaTien;
        }
        formatThanhTien = new BigDecimal(thanhTien);
        BigDecimal round = formatThanhTien.setScale(1, RoundingMode.UP);
        thanhTien = Double.parseDouble(round.toPlainString() + "");
        txtThanhTien.setText(round + "");
    }

    private void handleTextChange(JTextField textField, String fieldName) {
        message = new MessageFrame();
        String txt = textField.getText().trim();
        if (txt.isEmpty()) {
            return;
        }
        int value = convertToInt(txt);
        if (txt.length() > 9) {
            SwingUtilities.invokeLater(() -> {
                String sub = txt.substring(0, 9);
                textField.setText(sub);
                updateValues(textField, sub);
            });
            message.showMessage("error", "Số quá lớn.");
            return;
        }
        if (value == -1) {
            SwingUtilities.invokeLater(() -> {
                Pattern pattern = Pattern.compile("[a-zA-Z]");
                Matcher matcher = pattern.matcher(txt);
                StringBuilder sb = new StringBuilder(txt);
                while (matcher.find()) {
                    int index = matcher.start();
                    sb.deleteCharAt(index);
                    matcher = pattern.matcher(sb.toString());
                }
                String correctedText = sb.toString();
                textField.setText(correctedText);
                updateValues(textField, correctedText);
            });
            textField.requestFocus();
            message.showMessage("error", fieldName + " phải là số.");
            return;
        }

        if (value >= Integer.MAX_VALUE) {
            message.showMessage("error", "Số quá lớn.");
            return;
        }
        updateValues(textField, txt);
    }

    private void updateValues(JTextField textField, String txt) {
        int value = convertToInt(txt);
        if (textField == txtDauNguoi) {
            getDauNguoi = value;
        } else if (textField == txtMaTN) {
            maTN = value;
        } else if (textField == txtMaHD) {
            maHD = value;
        }
    }

    private int convertToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void display(int select) {
        String maTNString = tbl.getValueAt(select, 0).toString();
        String maHDString = tbl.getValueAt(select, 1).toString();
        String maPTString = tbl.getValueAt(select, 2).toString();
        String ngayBDString = tbl.getValueAt(select, 3).toString();
        String ngayKTString = tbl.getValueAt(select, 4).toString();
        String dauNguoiString = tbl.getValueAt(select, 5).toString();
        String giaTienString = tbl.getValueAt(select, 6).toString();
        String thanhTienString = tbl.getValueAt(select, 7).toString();

        txtMaTN.setText(maTNString);
        txtMaHD.setText(maHDString);
        txtMaPT.setText(maPTString);
        txtNgayBD.setText(ngayBDString);
        txtNgayKT.setText(ngayKTString);
        txtDauNguoi.setText(dauNguoiString);
        txtGiaTien.setText(giaTienString);
        txtThanhTien.setText(thanhTienString);
        ngayBatDauDate = formatDate(ngayBDString);
        ngayKetThucDate = formatDate(ngayKTString);
        giaTien = Double.parseDouble(giaTienString);
        thanhTien = Double.parseDouble(thanhTienString);
        getGiaTienChung = Double.parseDouble(giaTienString);

        formatGiaTien = new BigDecimal(Double.parseDouble(giaTienString));
        BigDecimal roundTien = formatGiaTien.setScale(1, RoundingMode.UP);
        txtGiaTien.setText(roundTien.toPlainString() + "");

        formatThanhTien = new BigDecimal(Double.parseDouble(thanhTienString));
        BigDecimal roundThanhTien = formatThanhTien.setScale(1, RoundingMode.UP);
        txtThanhTien.setText(roundThanhTien.toPlainString() + "");
        hd = Integer.parseInt(maHDString);
    }

    @SuppressWarnings("unchecked")
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
        jLabel1 = new javax.swing.JLabel();
        txtTongHD = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtMaTN = new view.component.textfield.TextField();
        txtMaHD = new view.component.textfield.TextField();
        txtMaPT = new view.component.textfield.TextField();
        txtNgayBD = new view.component.textfield.TextField();
        txtNgayKT = new view.component.textfield.TextField();
        txtDauNguoi = new view.component.textfield.TextField();
        txtGiaTien = new view.component.textfield.TextField();
        txtThanhTien = new view.component.textfield.TextField();
        btnXoa = new view.component.button.MyButton();
        btnLamMoi = new view.component.button.MyButton();
        btnThem = new view.component.button.MyButton();
        btnSua = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();
        locThang = new view.component.combobox.Combobox();
        locNam = new view.component.combobox.Combobox();

        chonNgayBD.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayBD.setToolTipText("");
        chonNgayBD.setTextRefernce(txtNgayBD);

        chonNgayKT.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayKT.setToolTipText("");
        chonNgayKT.setTextRefernce(txtNgayKT);

        nav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nav.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nav.setText("Hóa đơn / Tiền nước");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1069, 287));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã tiền nước", "Mã hóa đơn", "Mã phòng trọ", "Ngày bắt đầu", "Ngày kết thúc", "Đầu người", "Giá tiền", "Thành tiền"
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tổng số hóa đơn:");

        txtTongHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTongHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(431, 431, 431)
                .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(index, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongHD, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
            .addGroup(jPanel1Layout.createSequentialGroup()
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
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaTN.setLabelText("Mã tiền nước");
        txtMaTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaTNActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 272, -1));

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

        txtDauNguoi.setLabelText("Đầu người");
        txtDauNguoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtDauNguoiMousePressed(evt);
            }
        });
        txtDauNguoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDauNguoiActionPerformed(evt);
            }
        });
        jPanel2.add(txtDauNguoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 272, -1));

        txtGiaTien.setLabelText("Giá tiền");
        txtGiaTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTienActionPerformed(evt);
            }
        });
        jPanel2.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 272, -1));

        txtThanhTien.setEditable(false);
        txtThanhTien.setLabelText("Thành tiền");
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });
        jPanel2.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 272, -1));

        btnXoa.setBackground(new java.awt.Color(204, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_24px_1.png"))); // NOI18N
        btnXoa.setText("   Xóa");
        btnXoa.setBorderColor(new java.awt.Color(204, 255, 255));
        btnXoa.setColor(new java.awt.Color(204, 255, 255));
        btnXoa.setColorClick(new java.awt.Color(153, 255, 255));
        btnXoa.setColorOver(new java.awt.Color(204, 255, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnXoa.setRadius(20);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 110, 40));

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnLamMoi.setText(" Làm mới");
        btnLamMoi.setBorderColor(new java.awt.Color(204, 255, 255));
        btnLamMoi.setColor(new java.awt.Color(204, 255, 255));
        btnLamMoi.setColorClick(new java.awt.Color(153, 255, 255));
        btnLamMoi.setColorOver(new java.awt.Color(204, 255, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLamMoi.setRadius(20);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 110, 40));

        btnThem.setBackground(new java.awt.Color(204, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnThem.setText("   Thêm");
        btnThem.setBorderColor(new java.awt.Color(204, 255, 255));
        btnThem.setColor(new java.awt.Color(204, 255, 255));
        btnThem.setColorClick(new java.awt.Color(153, 255, 255));
        btnThem.setColorOver(new java.awt.Color(204, 255, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThem.setRadius(20);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 110, 40));

        btnSua.setBackground(new java.awt.Color(204, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnSua.setText("Cập nhật");
        btnSua.setBorderColor(new java.awt.Color(204, 255, 255));
        btnSua.setColor(new java.awt.Color(204, 255, 255));
        btnSua.setColorClick(new java.awt.Color(153, 255, 255));
        btnSua.setColorOver(new java.awt.Color(204, 255, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSua.setRadius(20);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel2.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 110, 40));

        txtTimKiem.setLabelText("Tìm kiếm mã");
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 220, -1));

        locThang.setMaximumRowCount(12);
        locThang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "        ", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));
        locThang.setLabeText("Lọc theo tháng");
        locThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locThangActionPerformed(evt);
            }
        });
        jPanel2.add(locThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 30, 160, -1));

        locNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        locNam.setLabeText("Lọc theo năm");
        locNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locNamActionPerformed(evt);
            }
        });
        jPanel2.add(locNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 160, -1));

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1168, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1168, Short.MAX_VALUE))
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
        if (select == -1) {
            return;
        } else {
            display(select);
        }
    }//GEN-LAST:event_tblMouseClicked

    private void prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevMouseClicked

    }//GEN-LAST:event_prevMouseClicked

    private void prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevActionPerformed
        if (page > 1) {
            page--;
            loadTable();
            updatePagination();
            prev.requestFocusInWindow();
        }
    }//GEN-LAST:event_prevActionPerformed

    private void nextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextMouseClicked
    }//GEN-LAST:event_nextMouseClicked

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        if (page < totalPages) {
            page++;
            loadTable();
            updatePagination();
            next.requestFocusInWindow();
        }
    }//GEN-LAST:event_nextActionPerformed

    private void txtMaTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaTNActionPerformed
    }//GEN-LAST:event_txtMaTNActionPerformed

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void txtMaPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPTActionPerformed
    }//GEN-LAST:event_txtMaPTActionPerformed

    private void txtNgayBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMouseClicked
        chonNgayBD.showPopup();
    }//GEN-LAST:event_txtNgayBDMouseClicked

    private void txtNgayBDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMousePressed
    }//GEN-LAST:event_txtNgayBDMousePressed

    private void txtNgayBDMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMouseReleased
    }//GEN-LAST:event_txtNgayBDMouseReleased

    private void txtNgayBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBDActionPerformed
    }//GEN-LAST:event_txtNgayBDActionPerformed

    private void txtNgayKTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayKTMouseClicked
        chonNgayKT.showPopup();
    }//GEN-LAST:event_txtNgayKTMouseClicked

    private void txtNgayKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayKTActionPerformed
    }//GEN-LAST:event_txtNgayKTActionPerformed

    private void txtDauNguoiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDauNguoiMousePressed
    }//GEN-LAST:event_txtDauNguoiMousePressed

    private void txtDauNguoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDauNguoiActionPerformed
    }//GEN-LAST:event_txtDauNguoiActionPerformed

    private void txtGiaTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTienActionPerformed
    }//GEN-LAST:event_txtGiaTienActionPerformed

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String text = txtMaTN.getText().trim();
        repo = new RepoTienNuoc();
        message = new MessageFrame();
        if (text.isEmpty()) {
            txtMaTN.requestFocus();
            message.showMessage("error", "Bạn cần nhập Mã TN để xóa.");
            return;
        }
        if (!repo.checkExistMaTN(text)) {
            txtMaTN.requestFocus();
            message.showMessage("warning", "Không tồn tại Mã TN: " + text + ".");
            return;
        }
        delTienNuoc();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearAll();
        loadTable();
        updatePagination();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        repo = new RepoTienNuoc();
        message = new MessageFrame();
        validate = new Validate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        JTextField[] fieldsToCheck = {txtMaTN, txtMaHD, txtMaPT, txtNgayBD, txtNgayKT, txtDauNguoi, txtGiaTien, txtThanhTien};
        String[] fieldLabels = {
            "Mã TN", "Mã HD", "Mã PT", "Ngày BD", "Ngày KT", "Đầu người",
            "Giá Tiền", "Thành tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtMaTN, txtMaHD, txtDauNguoi, txtGiaTien, txtThanhTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        maTN = Integer.parseInt(txtMaTN.getText());
        maHD = Integer.parseInt(txtMaHD.getText().trim());
        String maPT = txtMaPT.getText().trim();
        String ngayBDString = txtNgayBD.getText().trim();
        String ngayKTString = txtNgayKT.getText().trim();
        String ngayBD = null;
        String ngayKT = null;
        try {
            Date dateBD = inputFormat.parse(ngayBDString);
            Date dateKT = inputFormat.parse(ngayKTString);

            ngayBD = outputFormat.format(dateBD);
            ngayKT = outputFormat.format(dateKT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!repo.checkMaPhong(maPT)) {
            txtMaPT.requestFocus();
            message.showMessage("error", "Mã PT không tồn tại.");
            return;
        }
        if (!repo.checkMaHD(maHD)) {
            txtMaHD.requestFocus();
            message.showMessage("error", "Mã HĐ không tồn tại.");
            return;
        }

        if (!reValidate()) {
            txtNgayBD.requestFocus();
            message.showMessage("error", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            return;
        }
        if (giaTien != getGiaTienChung) {
            txtGiaTien.setText(getGiaTienChung + "");
            message.showMessage("error", "Không thể thêm giá tiền mới.");
            return;
        }

        ModelTienNuoc modelTienNuoc = new ModelTienNuoc(maHD, maPT, ngayBD, ngayKT, getDauNguoi, giaTien);

        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo.add(modelTienNuoc, maNV)) {
                    mess.showMessage("success", "Thêm thành công.");
                } else {
                    mess.showMessage("error", "Thêm thất bại.");
                }
                loadTable();
                clearAll();
                updatePagination();
            }
        });
        message.showMessage("message", "Bạn có muốn thêm không?");
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        repo = new RepoTienNuoc();
        message = new MessageFrame();
        validate = new Validate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        JTextField[] fieldsToCheck = {txtMaTN, txtMaHD, txtMaPT, txtNgayBD, txtNgayKT, txtDauNguoi, txtGiaTien, txtThanhTien};
        String[] fieldLabels = {
            "Mã TN", "Mã HD", "Mã PT", "Ngày BD", "Ngày KT", "Đầu người",
            "Giá Tiền", "Thành tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtMaTN, txtMaHD, txtDauNguoi, txtGiaTien, txtThanhTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        maTN = Integer.parseInt(txtMaTN.getText().trim());
        maHD = Integer.parseInt(txtMaHD.getText().trim());
        String maPT = txtMaPT.getText().trim();
        String ngayBDString = txtNgayBD.getText().trim();
        String ngayKTString = txtNgayKT.getText().trim();
        String ngayBD = null;
        String ngayKT = null;
        try {
            Date dateBD = inputFormat.parse(ngayBDString);
            Date dateKT = inputFormat.parse(ngayKTString);

            ngayBD = outputFormat.format(dateBD);
            ngayKT = outputFormat.format(dateKT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!repo.checkMaPhong(maPT)) {
            txtMaPT.requestFocus();
            message.showMessage("error", "Mã PT không tồn tại.");
            return;
        }
        if (!repo.checkMaHD(maHD)) {
            txtMaHD.requestFocus();
            message.showMessage("error", "Mã HĐ không tồn tại.");
            return;
        }

        if (!repo.checkExistMaTN(maTN + "")) {
            txtMaTN.requestFocus();
            message.showMessage("error", "Không thể update với Mã TN: " + maTN + " vì không tồn tại.");
            return;
        }

        if (hd != maHD) {
            message.showMessage("error", "Không thể sửa Mã HĐ khác.");
            txtMaHD.setText(hd + "");
            return;
        }

        ModelTienNuoc modelTienNuoc = new ModelTienNuoc(maTN, maHD, maPT, ngayBD, ngayKT, getDauNguoi, giaTien);

        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo.update(modelTienNuoc)) {
                    mess.showMessage("success", "Cập nhật thành công.");
                } else {
                    mess.showMessage("error", "Cập nhật thất bại.");
                }
                loadTable();
                clearAll();
                updatePagination();
            }
        });
        message.showMessage("message", "Bạn có cập nhật không?");
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
            clearAll3();
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
            clearAll3();
        } else if (!list.isEmpty() && !isClearing) {
            display(list.size() - 1);
        }
    }//GEN-LAST:event_locNamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnSua;
    private view.component.button.MyButton btnThem;
    private view.component.button.MyButton btnXoa;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel end;
    private javax.swing.JLabel index;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private view.component.combobox.Combobox locNam;
    private view.component.combobox.Combobox locThang;
    private javax.swing.JLabel nav;
    private view.component.button.MyButton next;
    private view.component.button.MyButton prev;
    private javax.swing.JScrollPane scroll;
    private view.component.table.Table tbl;
    private view.component.textfield.TextField txtDauNguoi;
    private view.component.textfield.TextField txtGiaTien;
    private view.component.textfield.TextField txtMaHD;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtMaTN;
    private view.component.textfield.TextField txtNgayBD;
    private view.component.textfield.TextField txtNgayKT;
    private view.component.textfield.TextField txtThanhTien;
    private view.component.textfield.TextField txtTimKiem;
    private javax.swing.JLabel txtTongHD;
    // End of variables declaration//GEN-END:variables
}
