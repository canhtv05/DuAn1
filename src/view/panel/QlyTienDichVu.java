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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.tiendichvu.ModelTienDichVu;
import model.tiendichvu.ModelTienDichVuChiTiet;
import repository.tiendichvu.RepoTienDichVu;
import repository.tiendichvu.RepoTienDichVuChiTiet;
import repository.validate.Validate;
import service.EventDateChooser;
import service.SelectedAction;
import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;
import view.main.viewSignIn;

public class QlyTienDichVu extends javax.swing.JPanel {

    private RepoTienDichVuChiTiet repo1;
    private RepoTienDichVu repo2;
    private ArrayList<ModelTienDichVuChiTiet> list1;
    private ArrayList<ModelTienDichVu> list2;
    private ArrayList<String> tenDV = new ArrayList<>();
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private int page = 1;
    private final int limit = 10;
    private int totalPages;
    private int totalPages1;
    private int totalRecords;
    private int totalRecords1;
    private String timKiem;
    private String timKiem2;
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
    private int maTDV;
    private int maDV;
    private int maHD;
    private int locTheoThang = 0;
    private int locTheoNam = 0;
    private boolean isClearing = false;
    private String maNV = null;
    private double tong;
    private int hd;
    private boolean isUpdating = false;
    private double giaTien;
    private int indexTab = 0;

    public QlyTienDichVu() {
        initComponents();
        init();
        changeTab();
        textSearch();
        loadTable();
        updatePagination();
        dateChooser();
        getTextOnChange();
        if (!list2.isEmpty()) {
            display2(list2.size() - 1);
        }
    }

    private void changeTab() {
        materialTabbed1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                indexTab = materialTabbed1.getSelectedIndex();
                page = 1;
                loadTable();
                updatePagination();
                if (indexTab == 1) {
                    if (!list1.isEmpty()) {
                        display(list1.size() - 1);
                    }
                } else if (indexTab == 0) {
                    if (!list2.isEmpty()) {
                        display2(list2.size() - 1);
                    }
                }
            }
        });
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
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaTDV.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtThanhTien.setText("");
        txtTimKiem.setText("");
        txtTongTien.setText("");
        getDauNguoi = 0;
        thanhTien = 0;
        locTheoNam = 0;
        locTheoThang = 0;
        locNam.setSelectedIndex(0);
        locThang.setSelectedIndex(0);
        isClearing = false;
    }

    private void clearAll2() {
        isClearing = true;
        txtTenDV.setText("");
        txtMaDV.setText("");
        txtGiaTien.setText("");
        giaTien = 0;
        isClearing = false;
    }

    private void clearAll3() {
        isClearing = true;
        txtDauNguoi.setText("");
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtMaTDV.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtThanhTien.setText("");
        txtTongTien.setText("");
        getDauNguoi = 0;
        thanhTien = 0;
        locTheoNam = 0;
        tong = 0;
        locTheoThang = 0;
        isClearing = false;
    }

    private void clearAll4() {
        isClearing = true;
        txtTenDV.setText("");
        txtMaDV.setText("");
        txtGiaTien.setText("");
        txtTimKiem1.setText("");
        giaTien = 0;
        isClearing = false;
    }

    private void init() {
        tbl.fixTable(scroll);
        tbl1.fixTable(scroll1);
        panelDVCT.setBackground(new Color(243, 243, 243));
        panelDV.setBackground(new Color(243, 243, 243));
        setBackground(new Color(243, 243, 243));
        repo1 = new RepoTienDichVuChiTiet();
        repo2 = new RepoTienDichVu();
        String usernameString = viewSignIn.getUsernameString;
        maNV = repo1.timMaNV(usernameString);
        if (maNV == null) {
            maNV = "NV001";
        }
    }

    private void loadTable() {
        initializeTableModel();
        loadRecords();
        addRowsToTable();
        if (indexTab == 0) {
            txtTongDV.setText(repo2.totalRecords(timKiem2) + "");
        } else if (indexTab == 1) {
            txtTongHD.setText(repo1.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
        }
    }

    private void initializeTableModel() {
        if (indexTab == 0) {
            model2 = (DefaultTableModel) tbl1.getModel();
            model2.setRowCount(0);
            timKiem2 = txtTimKiem1.getText().trim();
            totalRecords1 = repo2.totalRecords(timKiem2);
            totalPages1 = (int) Math.ceil((double) totalRecords1 / limit);
        } else if (indexTab == 1) {
            model = (DefaultTableModel) tbl.getModel();
            model.setRowCount(0);
            timKiem = txtTimKiem.getText().trim();
            totalRecords = repo1.totalRecords(timKiem, locTheoThang, locTheoNam);
            totalPages = (int) Math.ceil((double) totalRecords / limit);
        }
    }

    private void loadRecords() {
        if (indexTab == 0) {
            repo2 = new RepoTienDichVu();
            list2 = repo2.searchAndPage(timKiem2, page, limit);
        } else if (indexTab == 1) {
            repo1 = new RepoTienDichVuChiTiet();
            list1 = repo1.searchAndPage(timKiem, page, limit, locTheoThang, locTheoNam);
        }
    }

    private void addRowsToTable() {
        if (indexTab == 0) {
            for (ModelTienDichVu dv : list2) {
                Object[] rows = {
                    dv.getIdDV(),
                    dv.getTenDV(),
                    dv.getGiaTien(),};
                model2.addRow(rows);
                tenDV.add(dv.getTenDV());
            }
        } else if (indexTab == 1) {
            for (ModelTienDichVuChiTiet dvct : list1) {
                Object[] rows = {
                    dvct.getMaTDV(),
                    dvct.getMaHD(),
                    dvct.getMaPT(),
                    dvct.getNgayBD(),
                    dvct.getNgayKT(),
                    dvct.getDauNguoi(),
                    dvct.getTongTien(),
                    dvct.getThanhTien()
                };
                model.addRow(rows);
            }
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

        txtTimKiem1.getDocument().addDocumentListener(new DocumentListener() {
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
                if (!isClearing) {
                    handleDocumentChange();
                }
            }
        });
    }

    private void performSearch() {
        timKiem2 = txtTimKiem1.getText().trim();
        totalRecords1 = repo2.totalRecords(timKiem2);
        totalPages1 = (int) Math.ceil((double) totalRecords1 / limit);

        if (indexTab == 1) {
            timKiem = txtTimKiem.getText().trim();
            totalRecords = repo1.totalRecords(timKiem, locTheoThang, locTheoNam);
            totalPages = (int) Math.ceil((double) totalRecords / limit);
        }

        loadTable();

        if (indexTab == 0) {
            if (list2.isEmpty()) {
                clearAll2();
                txtTongDV.setText("0");
            } else {
                display2(list2.size() - 1);
            }
        } else if (indexTab == 1) {
            if (list1.isEmpty()) {
                clearAll3();
            } else {
                display(list1.size() - 1);
            }
            locThang.setSelectedIndex(0);
            locNam.setSelectedIndex(0);
        }
        updatePagination();
    }

    private void updatePagination() {
        if (indexTab == 1) {
            index.setText(String.valueOf(page));
            end.setText(String.valueOf(totalPages));

            prev.setEnabled(page > 1);
            next.setEnabled(page < totalPages);
            if (this.list1.isEmpty()) {
                index.setText("0");
                end.setText("0");
            }
        } else if (indexTab == 0) {
            index1.setText(String.valueOf(page));
            end1.setText(String.valueOf(totalPages1));

            prev1.setEnabled(page > 1);
            next1.setEnabled(page < totalPages1);
            if (this.list2.isEmpty()) {
                index1.setText("0");
                end1.setText("0");
            }
        }
    }

    private void handleDocumentChange() {
        if (!isUpdating) {
            isUpdating = true;
            SwingUtilities.invokeLater(() -> {
                handleDecimal(txtGiaTien, "Giá tiền");
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
                return;
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

    private void delTienDichVuChiTiet() {
        validate = new Validate();

        if (maTDV != -1) {
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo1.delete(maTDV) == true) {
                        mess.showMessage("success", "Xóa thành công.");
                    } else {
                        mess.showMessage("error", "Xóa thất bại.");
                    }
                    loadTable();
                    clearAll();
                    updatePagination();

                    if (list1.isEmpty()) {
                        initializeTableModel();

                        index.setText(String.valueOf(page - 1));
                        end.setText(String.valueOf(totalPages));

                        prev.setEnabled(page > 1);
                        next.setEnabled(page < totalPages);
                        list1 = repo1.searchAndPage(timKiem, page - 1, limit, locTheoThang, locTheoNam);
                        addRowsToTable();
                    }
                }
            });
            message.showMessage("message", "Bạn có muốn xóa không?");
        }
    }

    private void delTienDichVu() {
        validate = new Validate();

        if (maDV != -1) {
            message.setOnOkClicked(new Runnable() {
                @Override
                public void run() {
                    MessageFrame mess = new MessageFrame();
                    if (repo2.delete(maDV)) {
                        mess.showMessage("success", "Xóa thành công.");
                    } else {
                        mess.showMessage("error", "Xóa thất bại.");
                    }
                    loadTable();
                    clearAll4();
                    updatePagination();

                    if (list2.isEmpty()) {
                        initializeTableModel();

                        index1.setText(String.valueOf(page - 1));
                        end1.setText(String.valueOf(totalPages1));

                        prev1.setEnabled(page > 1);
                        next1.setEnabled(page < totalPages1);
                        list2 = repo2.searchAndPage(timKiem2, page - 1, limit);
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

        txtMaTDV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaTDV, "Mã dịch vụ");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaTDV, "Mã dịch vụ");
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
        txtMaDV.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaDV, "Mã dịch vụ");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isClearing) {
                    handleTextChange(txtMaDV, "Mã dịch vụ");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        tien();
    }

    private void showThanhTien() {
        if (getDauNguoi == 0.0) {
            thanhTien = 0;
        } else if (getDauNguoi != 0 && tong != 0) {
            thanhTien = getDauNguoi * tong;
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
        } else if (textField == txtMaTDV) {
            maTDV = value;
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
        String maTDVString = tbl.getValueAt(select, 0).toString();
        String maHDString = tbl.getValueAt(select, 1).toString();
        String ngayBDString = tbl.getValueAt(select, 2).toString();
        String ngayKTString = tbl.getValueAt(select, 3).toString();
        String maPTString = tbl.getValueAt(select, 4).toString();
        String dauNguoiString = tbl.getValueAt(select, 5).toString();
        String tongTienString = tbl.getValueAt(select, 6).toString();
        String thanhTienString = tbl.getValueAt(select, 7).toString();

        txtMaTDV.setText(maTDVString);
        txtMaHD.setText(maHDString);
        txtMaPT.setText(maPTString);
        txtNgayBD.setText(ngayBDString);
        txtNgayKT.setText(ngayKTString);
        txtDauNguoi.setText(dauNguoiString);
        txtThanhTien.setText(thanhTienString);
        txtTongTien.setText(tongTienString);

        ngayBatDauDate = formatDate(ngayBDString);
        ngayKetThucDate = formatDate(ngayKTString);
        thanhTien = Double.parseDouble(thanhTienString);
        tong = Double.parseDouble(tongTienString);
        formatTongTien = new BigDecimal(Double.parseDouble(tongTienString));
        BigDecimal roundTien = formatTongTien.setScale(1, RoundingMode.UP);
        txtTongTien.setText(roundTien.toPlainString() + "");
        formatThanhTien = new BigDecimal(Double.parseDouble(thanhTienString));
        BigDecimal roundThanhTien = formatThanhTien.setScale(1, RoundingMode.UP);
        txtThanhTien.setText(roundThanhTien.toPlainString() + "");
        hd = Integer.parseInt(maHDString);
    }

    private void display2(int select) {
        String maDVString = tbl1.getValueAt(select, 0).toString();
        String tenDVString = tbl1.getValueAt(select, 1).toString();
        String giaTienString = tbl1.getValueAt(select, 2).toString();
        txtMaDV.setText(maDVString);
        txtTenDV.setText(tenDVString);
        formatGiaTien = new BigDecimal(Double.parseDouble(giaTienString));
        BigDecimal roundGiaTien = formatGiaTien.setScale(1, RoundingMode.UP);
        txtGiaTien.setText(roundGiaTien.toPlainString() + "");
        giaTien = Double.parseDouble(giaTienString);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chonNgayBD = new view.panel.DateChooser();
        chonNgayKT = new view.panel.DateChooser();
        materialTabbed1 = new view.component.tab.MaterialTabbed();
        panelDV = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        scroll1 = new javax.swing.JScrollPane();
        tbl1 = new view.component.table.Table();
        prev1 = new view.component.button.MyButton();
        next1 = new view.component.button.MyButton();
        index1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        end1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTongDV = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtMaDV = new view.component.textfield.TextField();
        txtTenDV = new view.component.textfield.TextField();
        btnXoa1 = new view.component.button.MyButton();
        btnLamMoi1 = new view.component.button.MyButton();
        btnThem1 = new view.component.button.MyButton();
        btnSua1 = new view.component.button.MyButton();
        txtTimKiem1 = new view.component.textfield.TextField();
        txtGiaTien = new view.component.textfield.TextField();
        panelDVCT = new javax.swing.JPanel();
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
        txtMaTDV = new view.component.textfield.TextField();
        txtMaHD = new view.component.textfield.TextField();
        txtMaPT = new view.component.textfield.TextField();
        txtNgayBD = new view.component.textfield.TextField();
        txtNgayKT = new view.component.textfield.TextField();
        txtDauNguoi = new view.component.textfield.TextField();
        txtThanhTien = new view.component.textfield.TextField();
        btnXoa = new view.component.button.MyButton();
        btnLamMoi = new view.component.button.MyButton();
        btnThem = new view.component.button.MyButton();
        btnSua = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();
        locThang = new view.component.combobox.Combobox();
        locNam = new view.component.combobox.Combobox();
        txtTongTien = new view.component.textfield.TextField();

        chonNgayBD.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayBD.setTextRefernce(txtNgayBD);

        chonNgayKT.setForeground(new java.awt.Color(164, 95, 241));
        chonNgayKT.setToolTipText("");
        chonNgayKT.setTextRefernce(txtNgayKT);

        setPreferredSize(new java.awt.Dimension(1180, 583));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(1069, 287));

        tbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã dịch vụ", "Tên dịch vụ", "Giá tiền"
            }
        ));
        tbl1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl1MouseClicked(evt);
            }
        });
        scroll1.setViewportView(tbl1);

        prev1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_back_arrow_24px.png"))); // NOI18N
        prev1.setBorderColor(new java.awt.Color(204, 255, 255));
        prev1.setColor(new java.awt.Color(204, 255, 255));
        prev1.setColorClick(new java.awt.Color(153, 255, 255));
        prev1.setColorOver(new java.awt.Color(204, 255, 255));
        prev1.setRadius(10);
        prev1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prev1MouseClicked(evt);
            }
        });
        prev1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev1ActionPerformed(evt);
            }
        });

        next1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_forward_button_24px.png"))); // NOI18N
        next1.setBorderColor(new java.awt.Color(204, 255, 255));
        next1.setColor(new java.awt.Color(204, 255, 255));
        next1.setColorClick(new java.awt.Color(153, 255, 255));
        next1.setColorOver(new java.awt.Color(204, 255, 255));
        next1.setRadius(10);
        next1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                next1MouseClicked(evt);
            }
        });
        next1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next1ActionPerformed(evt);
            }
        });

        index1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        index1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        index1.setText("0");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("/");

        end1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        end1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        end1.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tổng số dịch vụ:");

        txtTongDV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTongDV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(prev1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(index1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(next1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(txtTongDV, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroll1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(next1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prev1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(index1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(end1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongDV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaDV.setLabelText("Mã dịch vụ");
        txtMaDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaDVActionPerformed(evt);
            }
        });
        jPanel4.add(txtMaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 272, -1));

        txtTenDV.setLabelText("Tên dịch vụ");
        txtTenDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDVActionPerformed(evt);
            }
        });
        jPanel4.add(txtTenDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 272, -1));

        btnXoa1.setBackground(new java.awt.Color(204, 255, 255));
        btnXoa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_24px_1.png"))); // NOI18N
        btnXoa1.setText("   Xóa");
        btnXoa1.setBorderColor(new java.awt.Color(204, 255, 255));
        btnXoa1.setColor(new java.awt.Color(204, 255, 255));
        btnXoa1.setColorClick(new java.awt.Color(153, 255, 255));
        btnXoa1.setColorOver(new java.awt.Color(204, 255, 255));
        btnXoa1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnXoa1.setRadius(20);
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, 110, 40));

        btnLamMoi1.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnLamMoi1.setText(" Làm mới");
        btnLamMoi1.setBorderColor(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setColor(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setColorClick(new java.awt.Color(153, 255, 255));
        btnLamMoi1.setColorOver(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLamMoi1.setRadius(20);
        btnLamMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnLamMoi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 110, 40));

        btnThem1.setBackground(new java.awt.Color(204, 255, 255));
        btnThem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnThem1.setText("   Thêm");
        btnThem1.setBorderColor(new java.awt.Color(204, 255, 255));
        btnThem1.setColor(new java.awt.Color(204, 255, 255));
        btnThem1.setColorClick(new java.awt.Color(153, 255, 255));
        btnThem1.setColorOver(new java.awt.Color(204, 255, 255));
        btnThem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThem1.setRadius(20);
        btnThem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnThem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 110, 40));

        btnSua1.setBackground(new java.awt.Color(204, 255, 255));
        btnSua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnSua1.setText("Cập nhật");
        btnSua1.setBorderColor(new java.awt.Color(204, 255, 255));
        btnSua1.setColor(new java.awt.Color(204, 255, 255));
        btnSua1.setColorClick(new java.awt.Color(153, 255, 255));
        btnSua1.setColorOver(new java.awt.Color(204, 255, 255));
        btnSua1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSua1.setRadius(20);
        btnSua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnSua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 110, 40));

        txtTimKiem1.setLabelText("Tìm kiếm tên dịch vụ");
        jPanel4.add(txtTimKiem1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, 220, -1));

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
        jPanel4.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 272, -1));

        javax.swing.GroupLayout panelDVLayout = new javax.swing.GroupLayout(panelDV);
        panelDV.setLayout(panelDVLayout);
        panelDVLayout.setHorizontalGroup(
            panelDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDVLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );
        panelDVLayout.setVerticalGroup(
            panelDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDVLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        materialTabbed1.addTab("Dịch vụ", panelDV);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1069, 287));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã tiền dịch vụ", "Mã hóa đơn", "Ngày bắt đầu", "Ngày kết thúc", "Mã phòng trọ", "Đầu người", "Tổng tiền dịch vụ", "Thành tiền"
            }
        ));
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        scroll.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(6).setResizable(false);
        }

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(437, 437, 437)
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
                        .addGap(12, 12, 12)
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaTDV.setLabelText("Mã tiền dịch vụ");
        txtMaTDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaTDVActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaTDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 272, -1));

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

        txtTongTien.setEditable(false);
        txtTongTien.setLabelText("Tổng tiền");
        txtTongTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtTongTienMousePressed(evt);
            }
        });
        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });
        jPanel2.add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 272, -1));

        javax.swing.GroupLayout panelDVCTLayout = new javax.swing.GroupLayout(panelDVCT);
        panelDVCT.setLayout(panelDVCTLayout);
        panelDVCTLayout.setHorizontalGroup(
            panelDVCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDVCTLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelDVCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );
        panelDVCTLayout.setVerticalGroup(
            panelDVCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDVCTLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        materialTabbed1.addTab("Tiền dịch vụ", panelDVCT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(materialTabbed1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtMaTDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaTDVActionPerformed

    }//GEN-LAST:event_txtMaTDVActionPerformed

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

    private void txtThanhTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThanhTienActionPerformed

    }//GEN-LAST:event_txtThanhTienActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        String text = txtMaTDV.getText().trim();
        repo1 = new RepoTienDichVuChiTiet();
        message = new MessageFrame();
        if (text.isEmpty()) {
            txtMaTDV.requestFocus();
            message.showMessage("error", "Bạn cần nhập Mã TDV để xóa.");
            return;
        }
        maTDV = Integer.parseInt(text);
        if (!repo1.checkExistMaTDV(maTDV)) {
            message.showMessage("warning", "Không tồn tại Mã TDV: " + maTDV + ".");
            return;
        }
        delTienDichVuChiTiet();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearAll();
        loadTable();
        updatePagination();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        repo1 = new RepoTienDichVuChiTiet();
        message = new MessageFrame();
        validate = new Validate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        JTextField[] fieldsToCheck = {txtMaTDV, txtMaHD, txtMaPT, txtNgayBD, txtNgayKT, txtDauNguoi, txtTongTien, txtThanhTien};
        String[] fieldLabels = {
            "Mã TDV", "Mã HD", "Mã PT", "Ngày BD", "Ngày KT", "Đầu người",
            "Tổng tiền", "Thành tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtMaTDV, txtMaHD, txtDauNguoi, txtTongTien, txtThanhTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        maTDV = Integer.parseInt(txtMaTDV.getText().trim());
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

        if (!repo1.checkMaPhong(maPT)) {
            txtMaPT.requestFocus();
            message.showMessage("error", "Mã PT không tồn tại.");
            return;
        }
        if (!repo1.checkMaHD(maHD)) {
            txtMaHD.requestFocus();
            message.showMessage("error", "Mã HĐ không tồn tại.");
            return;
        }

        if (!reValidate()) {
            txtNgayBD.requestFocus();
            message.showMessage("error", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            return;
        }

        ModelTienDichVuChiTiet modelTienDichVuChiTiet = new ModelTienDichVuChiTiet(maHD, maPT, ngayBD, ngayKT, getDauNguoi);

        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo1.add(modelTienDichVuChiTiet, maNV)) {
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
        repo1 = new RepoTienDichVuChiTiet();
        message = new MessageFrame();
        validate = new Validate();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        JTextField[] fieldsToCheck = {txtMaTDV, txtMaHD, txtMaPT, txtNgayBD, txtNgayKT, txtDauNguoi, txtTongTien, txtThanhTien};
        String[] fieldLabels = {
            "Mã TDV", "Mã HD", "Mã PT", "Ngày BD", "Ngày KT", "Đầu người",
            "Tổng tiền", "Thành tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtMaTDV, txtMaHD, txtDauNguoi, txtTongTien, txtThanhTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        maTDV = Integer.parseInt(txtMaTDV.getText().trim());
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

        if (!repo1.checkMaPhong(maPT)) {
            txtMaPT.requestFocus();
            message.showMessage("error", "Mã PT không tồn tại.");
            return;
        }
        if (!repo1.checkMaHD(maHD)) {
            txtMaHD.requestFocus();
            message.showMessage("error", "Mã HĐ không tồn tại.");
            return;
        }

        if (!reValidate()) {
            txtNgayBD.requestFocus();
            message.showMessage("error", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            return;
        }

        if (!repo1.checkExistMaTDV(maTDV)) {
            txtMaTDV.requestFocus();
            message.showMessage("error", "Không thể update với Mã DV: " + maTDV + " vì không tồn tại.");
            return;
        }

        if (hd != maHD) {
            message.showMessage("error", "Không thể sửa Mã HĐ khác.");
            txtMaHD.setText(hd + "");
            return;
        }
        ModelTienDichVuChiTiet modelTienDichVuChiTiet = new ModelTienDichVuChiTiet(maTDV, maHD, maPT, ngayBD, ngayKT, getDauNguoi);
        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo1.update(modelTienDichVuChiTiet)) {
                    mess.showMessage("success", "Cập nhật thành công.");
                } else {
                    mess.showMessage("error", "Cập nhật thất bại.");
                }
                loadTable();
                clearAll();
                updatePagination();
            }
        });
        message.showMessage("message", "Bạn có muốn cập nhật không?");
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
        txtTongHD.setText(repo1.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
        if (list1.isEmpty()) {
            clearAll3();
        } else if (!list1.isEmpty() && !isClearing) {
            display(list1.size() - 1);
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
        txtTongHD.setText(repo1.totalRecords(timKiem, locTheoThang, locTheoNam) + "");
        if (list1.isEmpty()) {
            clearAll3();
        } else if (!list1.isEmpty() && !isClearing) {
            display(list1.size() - 1);
        }
    }//GEN-LAST:event_locNamActionPerformed

    private void txtTongTienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTongTienMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienMousePressed

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

    private void tbl1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl1MouseClicked
        int select = tbl1.getSelectedRow();
        if (select == -1) {
            return;
        }

        display2(select);
    }//GEN-LAST:event_tbl1MouseClicked

    private void prev1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prev1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_prev1MouseClicked

    private void prev1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev1ActionPerformed
        if (page > 1) {
            page--;
            loadTable();
            updatePagination();
            prev1.requestFocusInWindow();
        }
    }//GEN-LAST:event_prev1ActionPerformed

    private void next1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_next1MouseClicked

    private void next1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next1ActionPerformed
        if (page < totalPages1) {
            page++;
            loadTable();
            updatePagination();
            next1.requestFocusInWindow();
        }
    }//GEN-LAST:event_next1ActionPerformed

    private void txtMaDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaDVActionPerformed

    private void txtTenDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDVActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed
        String text = txtMaDV.getText().trim();
        repo2 = new RepoTienDichVu();
        message = new MessageFrame();
        if (text.isEmpty()) {
            txtMaDV.requestFocus();
            message.showMessage("error", "Bạn cần nhập Mã DV để xóa.");
            return;
        }
        maDV = Integer.parseInt(text);
        if (!repo2.checkExistMaDV(maDV)) {
            txtMaDV.requestFocus();
            message.showMessage("error", "Không tồn tại Mã DV: " + maDV + ".");
            return;
        }
        delTienDichVu();
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void btnLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi1ActionPerformed
        clearAll4();
        loadTable();
        updatePagination();
    }//GEN-LAST:event_btnLamMoi1ActionPerformed

    private void btnThem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem1ActionPerformed
        repo2 = new RepoTienDichVu();
        message = new MessageFrame();
        validate = new Validate();

        JTextField[] fieldsToCheck = {txtMaDV, txtTenDV, txtGiaTien};
        String[] fieldLabels = {
            "Mã DV", "Tên DV", "Giá tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }

        JTextField[] numericFields = {txtMaDV, txtGiaTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        String tenDVString = txtTenDV.getText().trim();

        for (String s : tenDV) {
            if (s.equalsIgnoreCase(tenDVString)) {
                message.showMessage("error", "Đã tồn tại " + tenDVString + " .");
                return;
            }
        }

        ModelTienDichVu modelTienDichVu = new ModelTienDichVu(tenDVString, giaTien);

        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo2.add(modelTienDichVu)) {
                    mess.showMessage("success", "Thêm thành công.");
                } else {
                    mess.showMessage("error", "Thêm thất bại.");
                }
                loadTable();
                clearAll2();
                updatePagination();
            }
        });
        message.showMessage("message", "Bạn có muốn thêm không?");
    }//GEN-LAST:event_btnThem1ActionPerformed

    private void btnSua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua1ActionPerformed
        repo2 = new RepoTienDichVu();
        message = new MessageFrame();
        validate = new Validate();

        JTextField[] fieldsToCheck = {txtMaDV, txtTenDV, txtGiaTien};
        String[] fieldLabels = {
            "Mã DV", "Tên DV", "Giá tiền"
        };
        for (int i = 0; i < fieldsToCheck.length; i++) {
            if (!validate.isNull(fieldsToCheck[i], fieldLabels[i])) {
                return;
            }
        }
        maDV = Integer.parseInt(txtMaDV.getText().trim());

        JTextField[] numericFields = {txtMaDV, txtGiaTien};
        for (JTextField field : numericFields) {
            if (validate.isNumber(field) == -1.0) {
                return;
            }
        }

        String tenDVString = txtTenDV.getText().trim();

        if (!repo2.checkExistMaDV(maDV)) {
            txtMaDV.requestFocus();
            message.showMessage("error", "Không tồn tại Mã DV: " + maDV + " không thể sửa.");
            return;
        }

        ModelTienDichVu modelTienDichVu = new ModelTienDichVu(maDV, tenDVString, giaTien);

        message.setOnOkClicked(new Runnable() {
            @Override
            public void run() {
                MessageFrame mess = new MessageFrame();
                if (repo2.update(modelTienDichVu)) {
                    mess.showMessage("success", "Cập nhật thành công.");
                } else {
                    mess.showMessage("error", "Cập nhật thất bại.");
                }
                loadTable();
                clearAll2();
                updatePagination();
            }
        });
        message.showMessage("message", "Bạn có cập nhật không?");
    }//GEN-LAST:event_btnSua1ActionPerformed

    private void txtGiaTienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGiaTienMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTienMousePressed

    private void txtGiaTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaTienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnLamMoi1;
    private view.component.button.MyButton btnSua;
    private view.component.button.MyButton btnSua1;
    private view.component.button.MyButton btnThem;
    private view.component.button.MyButton btnThem1;
    private view.component.button.MyButton btnXoa;
    private view.component.button.MyButton btnXoa1;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel end;
    private javax.swing.JLabel end1;
    private javax.swing.JLabel index;
    private javax.swing.JLabel index1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private view.component.combobox.Combobox locNam;
    private view.component.combobox.Combobox locThang;
    private view.component.tab.MaterialTabbed materialTabbed1;
    private view.component.button.MyButton next;
    private view.component.button.MyButton next1;
    private javax.swing.JPanel panelDV;
    private javax.swing.JPanel panelDVCT;
    private view.component.button.MyButton prev;
    private view.component.button.MyButton prev1;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll1;
    private view.component.table.Table tbl;
    private view.component.table.Table tbl1;
    private view.component.textfield.TextField txtDauNguoi;
    private view.component.textfield.TextField txtGiaTien;
    private view.component.textfield.TextField txtMaDV;
    private view.component.textfield.TextField txtMaHD;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtMaTDV;
    private view.component.textfield.TextField txtNgayBD;
    private view.component.textfield.TextField txtNgayKT;
    private view.component.textfield.TextField txtTenDV;
    private view.component.textfield.TextField txtThanhTien;
    private view.component.textfield.TextField txtTimKiem;
    private view.component.textfield.TextField txtTimKiem1;
    private javax.swing.JLabel txtTongDV;
    private javax.swing.JLabel txtTongHD;
    private view.component.textfield.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
