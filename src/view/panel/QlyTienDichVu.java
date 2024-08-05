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
import model.tiendichvu.ModelTienDichVu;
import repository.tiendichvu.RepoTienDichVu;
import repository.validate.Validate;
import service.EventDateChooser;
import service.SelectedAction;
import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;

public class QlyTienDichVu extends javax.swing.JPanel {

    private RepoTienDichVu repo;
    private ArrayList<ModelTienDichVu> list;
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
    private double thanhTien;
    private BigDecimal formatThanhTien;
    private BigDecimal formatTongTien;
    private BigDecimal formatGiaTien;
    private int locTheoThang = 0;
    private int locTheoNam = 0;
    private boolean isClearing = false;
    private String maNV = null;
    private double tong;
    private boolean isUpdating = false;
    private double giaTien;
    private int selectRow = -1;
    private int state = -1;
    private double oldGiaTien;
    private Date oldNgayDB;
    private Date oldNgayKT;
    String ngayBD = null;
    String ngayKT = null;

    public QlyTienDichVu() {
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
        txtMaDV.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtThanhTien.setText("");
        txtTimKiem.setText("");
        getDauNguoi = 0;
        giaTien = 0;
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        state = -1;
        locNam.setSelectedIndex(0);
        locThang.setSelectedIndex(0);
        ngayBatDauDate = null;
        ngayKetThucDate = null;
        groupbtn.remove(rdoDaChinhSua);
        groupbtn.remove(rdoChuaChinhSua);
        rdoDaChinhSua.setSelected(false);
        rdoChuaChinhSua.setSelected(false);
        groupbtn.add(rdoDaChinhSua);
        groupbtn.add(rdoChuaChinhSua);
        isClearing = false;
    }

    private void clearAllDontHaveSearchAndFilterYearMonth() {
        isClearing = true;
        txtDauNguoi.setText("");
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaDV.setText("");
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
        state = -1;
        groupbtn.remove(rdoDaChinhSua);
        groupbtn.remove(rdoChuaChinhSua);
        rdoDaChinhSua.setSelected(false);
        rdoChuaChinhSua.setSelected(false);
        groupbtn.add(rdoDaChinhSua);
        groupbtn.add(rdoChuaChinhSua);
        isClearing = false;
    }

    private void display(int select) {
        String maDVString = tbl.getValueAt(select, 0).toString().trim();
        String maHDString = tbl.getValueAt(select, 1).toString().trim();
        String maPTString = tbl.getValueAt(select, 2).toString().trim();
        String tenDVString = tbl.getValueAt(select, 3).toString().trim();
        String ngayBDString = tbl.getValueAt(select, 4).toString().trim();
        String ngayKTString = tbl.getValueAt(select, 5).toString().trim();
        String dauNguoiString = tbl.getValueAt(select, 6).toString().trim();
        String giaTienString = tbl.getValueAt(select, 7).toString().trim();
        String thanhTienString = tbl.getValueAt(select, 8).toString().trim();
        String trangThaiString = tbl.getValueAt(select, 9).toString().trim();

        txtMaDV.setText(maDVString);
        txtMaHD.setText(maHDString);
        txtMaPT.setText(maPTString);
        txtTenDV.setText(tenDVString);
        txtNgayBD.setText(ngayBDString);
        txtNgayKT.setText(ngayKTString);
        txtDauNguoi.setText(dauNguoiString);
        txtGiaTien.setText(giaTienString);
        txtThanhTien.setText(thanhTienString);
        ngayBatDauDate = formatDate(ngayBDString);
        ngayKetThucDate = formatDate(ngayKTString);
        giaTien = Double.parseDouble(giaTienString);
        thanhTien = Double.parseDouble(thanhTienString);

        formatGiaTien = new BigDecimal(Double.parseDouble(giaTienString));
        BigDecimal roundTien = formatGiaTien.setScale(1, RoundingMode.UP);
        txtGiaTien.setText(roundTien.toPlainString() + "");

        formatThanhTien = new BigDecimal(Double.parseDouble(thanhTienString));
        BigDecimal roundThanhTien = formatThanhTien.setScale(1, RoundingMode.UP);
        txtThanhTien.setText(roundThanhTien.toPlainString() + "");

        if (trangThaiString.equals("Đã chỉnh sửa")) {
            rdoDaChinhSua.setSelected(true);
        } else {
            rdoChuaChinhSua.setSelected(true);
        }

        oldGiaTien = giaTien;
        oldNgayDB = ngayBatDauDate;
        oldNgayKT = ngayKetThucDate;
    }

    private void init() {
        tbl.fixTable(scroll);
        nav.setForeground(new Color(0, 0, 250));
        setBackground(new Color(243, 243, 243));
        repo = new RepoTienDichVu();
    }

    private void loadTable() {
        initializeTableModel();
        loadRecords();
        addRowsToTable();
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam, state) + "");
    }

    private void initializeTableModel() {
        model = (DefaultTableModel) tbl.getModel();
        model.setRowCount(0);
        timKiem = txtTimKiem.getText().trim();
        totalRecords = repo.totalRecords(timKiem, locTheoThang, locTheoNam, state);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
    }

    private void loadRecords() {
        repo = new RepoTienDichVu();
        list = repo.searchAndPage(timKiem, page, limit, locTheoThang, locTheoNam, state);
    }

    private void addRowsToTable() {
        for (ModelTienDichVu dv : list) {
            Object[] rows = {
                dv.getMaDV(),
                dv.getMaHD(),
                dv.getMaPT(),
                dv.getTenDV(),
                dv.getNgayBD(),
                dv.getNgayKT(),
                dv.getDauNguoi(),
                dv.getGiaTien(),
                dv.getThanhTien(),
                dv.getTrangThai() == 0 ? "Chưa chỉnh sửa" : "Đã chỉnh sửa"
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
        totalRecords = repo.totalRecords(timKiem, locTheoThang, locTheoNam, state);
        totalPages = (int) Math.ceil((double) totalRecords / limit);
        loadTable();
        updatePagination();
        if (list.isEmpty()) {
            clearAllDontHaveSearchAndFilterYearMonth();
            locThang.setSelectedIndex(0);
            locNam.setSelectedIndex(0);
        } else {
            display(list.size() - 1);
        }
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

    private void refreshTable() {
        int currentPage = page;
        performSearch();
        page = currentPage;
        updatePagination();
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
        }
    }

    private int convertToInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chonNgayBD = new view.panel.DateChooser();
        chonNgayKT = new view.panel.DateChooser();
        groupbtn = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        tbl = new view.component.table.Table();
        prev = new view.component.button.MyButton();
        index = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        end = new javax.swing.JLabel();
        next = new view.component.button.MyButton();
        jLabel3 = new javax.swing.JLabel();
        txtTongHD = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtMaDV = new view.component.textfield.TextField();
        txtMaHD = new view.component.textfield.TextField();
        txtMaPT = new view.component.textfield.TextField();
        txtNgayBD = new view.component.textfield.TextField();
        txtNgayKT = new view.component.textfield.TextField();
        txtDauNguoi = new view.component.textfield.TextField();
        txtThanhTien = new view.component.textfield.TextField();
        btnLamMoi = new view.component.button.MyButton();
        btnSua = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();
        locThang = new view.component.combobox.Combobox();
        locNam = new view.component.combobox.Combobox();
        txtGiaTien = new view.component.textfield.TextField();
        txtTenDV = new view.component.textfield.TextField();
        rdoDaChinhSua = new view.component.radiobutton.RadioButtonCustom();
        rdoChuaChinhSua = new view.component.radiobutton.RadioButtonCustom();
        lblTrangThai = new javax.swing.JLabel();
        btnTrangThai = new view.component.button.MyButton();
        lblLocTrangThai = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nav = new javax.swing.JLabel();

        chonNgayBD.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayBD.setTextRefernce(txtNgayBD);

        chonNgayKT.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayKT.setToolTipText("");
        chonNgayKT.setTextRefernce(txtNgayKT);

        setPreferredSize(new java.awt.Dimension(1340, 585));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1069, 287));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Mã hóa đơn", "Mã phòng", "Tên dịch vụ", "Ngày bắt đầu", "Ngày kết thúc", "Đầu người", "Giá tiền", "Thành tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        scroll.setViewportView(tbl);

        prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_back_arrow_24px.png"))); // NOI18N
        prev.setBorderColor(new java.awt.Color(153, 204, 204));
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

        index.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        index.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        index.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("/");

        end.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        end.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end.setText("0");

        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_forward_button_24px.png"))); // NOI18N
        next.setBorderColor(new java.awt.Color(153, 204, 204));
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

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tổng số hóa đơn:");

        txtTongHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTongHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 527, Short.MAX_VALUE)
                        .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(index, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 384, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongHD, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(index, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaDV.setEditable(false);
        txtMaDV.setLabelText("Mã dịch vụ");
        txtMaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaDVActionPerformed(evt);
            }
        });
        jPanel6.add(txtMaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        txtMaHD.setEditable(false);
        txtMaHD.setLabelText("Mã hóa đơn");
        txtMaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDActionPerformed(evt);
            }
        });
        jPanel6.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 200, -1));

        txtMaPT.setEditable(false);
        txtMaPT.setLabelText("Mã phòng trọ");
        txtMaPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPTActionPerformed(evt);
            }
        });
        jPanel6.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 200, -1));

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
        jPanel6.add(txtNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 200, -1));

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
        jPanel6.add(txtNgayKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 200, -1));

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
        jPanel6.add(txtDauNguoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 200, -1));

        txtThanhTien.setEditable(false);
        txtThanhTien.setLabelText("Thành tiền");
        txtThanhTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThanhTienActionPerformed(evt);
            }
        });
        jPanel6.add(txtThanhTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 200, -1));

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnLamMoi.setText(" Làm mới");
        btnLamMoi.setBorderColor(new java.awt.Color(153, 204, 204));
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
        jPanel6.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 100, 110, 40));

        btnSua.setBackground(new java.awt.Color(204, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnSua.setText("Cập nhật");
        btnSua.setBorderColor(new java.awt.Color(153, 204, 204));
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
        jPanel6.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 110, 40));

        txtTimKiem.setLabelText("Tìm kiếm mã");
        jPanel6.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 230, -1));

        locThang.setMaximumRowCount(12);
        locThang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "        ", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));
        locThang.setLabeText("Lọc theo tháng");
        locThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locThangActionPerformed(evt);
            }
        });
        jPanel6.add(locThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 160, -1));

        locNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        locNam.setLabeText("Lọc theo năm");
        locNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locNamActionPerformed(evt);
            }
        });
        jPanel6.add(locNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 160, -1));

        txtGiaTien.setLabelText("Giá tiền");
        txtGiaTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtGiaTienMousePressed(evt);
            }
        });
        txtGiaTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaTienActionPerformed(evt);
            }
        });
        jPanel6.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 200, -1));

        txtTenDV.setEditable(false);
        txtTenDV.setLabelText("Tên dịch vụ");
        txtTenDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDVActionPerformed(evt);
            }
        });
        jPanel6.add(txtTenDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 200, -1));

        rdoDaChinhSua.setBackground(new java.awt.Color(0, 204, 102));
        groupbtn.add(rdoDaChinhSua);
        rdoDaChinhSua.setForeground(new java.awt.Color(150, 150, 150));
        rdoDaChinhSua.setText("Đã chỉnh sửa");
        jPanel6.add(rdoDaChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 190, 100, -1));

        rdoChuaChinhSua.setBackground(new java.awt.Color(255, 0, 51));
        groupbtn.add(rdoChuaChinhSua);
        rdoChuaChinhSua.setForeground(new java.awt.Color(150, 150, 150));
        rdoChuaChinhSua.setText("Chưa chỉnh sửa");
        jPanel6.add(rdoChuaChinhSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, -1, -1));

        lblTrangThai.setForeground(new java.awt.Color(150, 150, 150));
        lblTrangThai.setText("Trạng thái");
        jPanel6.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 70, -1));

        btnTrangThai.setBackground(new java.awt.Color(204, 255, 255));
        btnTrangThai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_filter_30px.png"))); // NOI18N
        btnTrangThai.setBorderColor(new java.awt.Color(153, 204, 204));
        btnTrangThai.setColor(new java.awt.Color(204, 255, 255));
        btnTrangThai.setColorClick(new java.awt.Color(153, 255, 255));
        btnTrangThai.setColorOver(new java.awt.Color(204, 255, 255));
        btnTrangThai.setRadius(10);
        btnTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangThaiActionPerformed(evt);
            }
        });
        jPanel6.add(btnTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 160, 50, 50));

        lblLocTrangThai.setForeground(new java.awt.Color(150, 150, 150));
        lblLocTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLocTrangThai.setText("Lọc trạng thái");
        jPanel6.add(lblLocTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 130, 90, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("*Dịch vụ chung gồm: đèn hành lang, gửi xe, rác");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, -1, -1));

        nav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nav.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nav.setText("Hóa đơn / Tiền dịch vụ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nav)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nav)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, 289, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        int select = tbl.getSelectedRow();
        selectRow = select;
        if (select == -1) {
            return;
        } else {
            display(select);
        }
    }//GEN-LAST:event_tblMouseClicked

    private void txtMaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaDVActionPerformed

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void txtMaPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPTActionPerformed

    private void txtNgayBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMouseClicked
        chonNgayBD.showPopup();
    }//GEN-LAST:event_txtNgayBDMouseClicked

    private void txtNgayBDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBDMousePressed

    private void txtNgayBDMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayBDMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBDMouseReleased

    private void txtNgayBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBDActionPerformed

    private void txtNgayKTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayKTMouseClicked
        chonNgayKT.showPopup();
    }//GEN-LAST:event_txtNgayKTMouseClicked

    private void txtNgayKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayKTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayKTActionPerformed

    private void txtDauNguoiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDauNguoiMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDauNguoiMousePressed

    private void txtDauNguoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDauNguoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDauNguoiActionPerformed

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearAll();
        loadTable();
        if (!list.isEmpty()) {
            display(list.size() - 1);
        }
        updatePagination();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        repo = new RepoTienDichVu();
        if (!list.isEmpty()) {
            selectRow = list.size() - 1;
        } else if (selectRow == -1) {
            message.showMessage("warning", "Vui lòng chọn 1 hàng để cập nhật.");
            return;
        }
        message = new MessageFrame();
        validate = new Validate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        JTextField[] fieldsToCheck = {txtNgayBD, txtNgayKT, txtDauNguoi, txtGiaTien};
        String[] fieldLabels = {"Ngày BD", "Ngày KT", "Đầu người", "Giá Tiền"};
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtDauNguoi, txtGiaTien, txtThanhTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        int maTienDV = Integer.parseInt(txtMaDV.getText().trim());
        String ngayBDString = txtNgayBD.getText().trim();
        String ngayKTString = txtNgayKT.getText().trim();
        ngayBD = null;
        ngayKT = null;
        try {
            Date dateBD = inputFormat.parse(ngayBDString);
            Date dateKT = inputFormat.parse(ngayKTString);

            ngayBD = outputFormat.format(dateBD);
            ngayKT = outputFormat.format(dateKT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!reValidate()) {
            txtNgayBD.requestFocus();
            message.showMessage("error", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            return;
        }

        if (giaTien != oldGiaTien) {
            message.setOnOkClicked(() -> {
                MessageFrame mess = new MessageFrame();
                if (repo.updatePirceAndDate(giaTien, ngayBD, ngayKT)) {
                    mess.showMessage("success", "Cập nhật thành công.");
                } else {
                    mess.showMessage("error", "Cập nhật thất bại.");
                }
                refreshTable();
                if (!list.isEmpty() && !isClearing) {
                    display(list.size() - 1);
                }

            });
            message.showMessage("message", "Bạn có muốn thay đổi giá tiền: " + giaTien + " trong khoảng: " + inputFormat.format(oldNgayDB) + " tới " + inputFormat.format(oldNgayKT) + " không?");
            return;
        } else {
            ModelTienDichVu modelTienDichVu = new ModelTienDichVu(maTienDV, ngayBD, ngayKT, getDauNguoi, giaTien, 1);
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo.update(modelTienDichVu)) {
                        mess.showMessage("success", "Cập nhật thành công.");
                    } else {
                        mess.showMessage("error", "Cập nhật thất bại.");
                    }
                    refreshTable();
                    if (!list.isEmpty() && !isClearing) {
                        display(list.size() - 1);
                    }
                }
            });
            message.showMessage("message", "Bạn có cập nhật không?");
        }
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
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam, state) + "");
        if (list.isEmpty()) {
            clearAllDontHaveSearchAndFilterYearMonth();
        } else {
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
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam, state) + "");
        if (list.isEmpty()) {
            clearAllDontHaveSearchAndFilterYearMonth();
        } else {
            display(list.size() - 1);
        }
    }//GEN-LAST:event_locNamActionPerformed

    private void txtGiaTienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGiaTienMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTienMousePressed

    private void txtGiaTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTienActionPerformed

    private void txtTenDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDVActionPerformed

    private void btnTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangThaiActionPerformed
        if (rdoDaChinhSua.isSelected()) {
            state = 1;
        } else {
            state = 0;
        }
        page = 1;
        loadTable();
        updatePagination();
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam, state) + "");
        if (!list.isEmpty() && !isClearing) {
            display(list.size() - 1);
        } else {
            clearAllDontHaveSearchAndFilterYearMonth();
        }
    }//GEN-LAST:event_btnTrangThaiActionPerformed

    private void prevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prevMouseClicked
        // TODO add your handling code here:
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
        // TODO add your handling code here:
    }//GEN-LAST:event_nextMouseClicked

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        if (page < totalPages) {
            page++;
            loadTable();
            updatePagination();
            next.requestFocusInWindow();
        }
    }//GEN-LAST:event_nextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnSua;
    private view.component.button.MyButton btnTrangThai;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel end;
    private javax.swing.ButtonGroup groupbtn;
    private javax.swing.JLabel index;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblLocTrangThai;
    private javax.swing.JLabel lblTrangThai;
    private view.component.combobox.Combobox locNam;
    private view.component.combobox.Combobox locThang;
    private javax.swing.JLabel nav;
    private view.component.button.MyButton next;
    private view.component.button.MyButton prev;
    private view.component.radiobutton.RadioButtonCustom rdoChuaChinhSua;
    private view.component.radiobutton.RadioButtonCustom rdoDaChinhSua;
    private javax.swing.JScrollPane scroll;
    private view.component.table.Table tbl;
    private view.component.textfield.TextField txtDauNguoi;
    private view.component.textfield.TextField txtGiaTien;
    private view.component.textfield.TextField txtMaDV;
    private view.component.textfield.TextField txtMaHD;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtNgayBD;
    private view.component.textfield.TextField txtNgayKT;
    private view.component.textfield.TextField txtTenDV;
    private view.component.textfield.TextField txtThanhTien;
    private view.component.textfield.TextField txtTimKiem;
    private javax.swing.JLabel txtTongHD;
    // End of variables declaration//GEN-END:variables
}
