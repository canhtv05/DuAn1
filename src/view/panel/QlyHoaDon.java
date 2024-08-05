package view.panel;

import java.awt.BorderLayout;
import view.jframe.ThemHoaDon;
import view.jframe.ExportPdf;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.hoadon.ModelHoaDon;
import repository.exportpdf.RepoExportPDF;
import repository.hoadon.RepoHoaDon;
import service.EventDateChooser;
import service.QrCodeListener;
import service.SelectedAction;
import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;
import view.component.qrcode.DeleteAllFile;
import view.jframe.HoaDonChiTiet;
import view.jframe.LuaChonNgayXuatExcel;
import view.jframe.LuaChonNgayXuatPDF;
import view.jframe.QrCodeHoaDon;

public class QlyHoaDon extends javax.swing.JPanel implements QrCodeListener {

    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private RepoHoaDon repo;
    private ArrayList<ModelHoaDon> list;
    private DefaultTableModel model;
    private int page = 1;
    private final int limit = 10;
    private int totalPages;
    private int totalRecords;
    private String timKiem;
    private Date ngayTTDate;
    private Date hanTTDate;
    public static int maHD;
    private SimpleDateFormat formatDate;
    private MessageFrame message;
    private int locTheoThang;
    private int locTheoNam;
    private boolean isClearing = false;
    private BigDecimal formatThanhTien;
    public static String maPhongString;
    public static double tienDien;
    public static double tienNuoc;
    public static double tienDichVu;
    public static double tongTien;
    public static int maHoaDon;
    public static double giaTienPhong;
    private int selectRow = -1;
    private int state = -1;
    private String hanTT;
    private String ngayTT;
    private boolean isSelected = false;
    private String ngayLapString = null;
    public static boolean show = false;
    private String path;
    public static  Map<Integer, String> showIcon2 =new HashMap<>();

    public QlyHoaDon() {
        initComponents();
        init();
        textSearch();
        loadTable();
        updatePagination();
        dateChooser();
        if (!list.isEmpty()) {
            display(list.size() - 1);
        }
        showChiTietHoaDon();
    }

    private void dateChooser() {
        chonHanTT.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (date != null) {
                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
                    hanTT = textDate;
                    hanTTDate = formatDate(textDate);
                    if (action.getAction() == SelectedAction.DAY_SELECTED) {
                        chonHanTT.hidePopup();
                    }
                } else {
                    hanTTDate = null;
                }
            }
        });
        chonNgayTT.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (date != null) {
                    isSelected = true;
                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
                    ngayTT = textDate;
                    ngayTTDate = formatDate(textDate);
                    if (isSelected) {
                        cbbTrangThai.setSelectedIndex(1);
                    }
                    if (action.getAction() == SelectedAction.DAY_SELECTED) {
                        chonNgayTT.hidePopup();
                        isSelected = false;
                    }
                } else {
                    ngayTTDate = null;
                }
            }
        });
    }

    private void clearAll() {
        isClearing = true;
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtNgayLap.setText("");
        txtHanTT.setText("");
        cbbTrangThai.setSelectedIndex(-1);
        txtMaNT.setText("");
        txtTimKiem.setText("");
        txtTienDV.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        txtGiaPhong.setText("");
        locTheoNam = 0;
        locTheoThang = 0;
        state = -1;
        locNam.setSelectedIndex(0);
        locThang.setSelectedIndex(0);
        hanTTDate = null;
        ngayTTDate = null;
        isClearing = false;
    }

    private void clearAllDontHaveSearchAndFilterYearMonth() {
        isClearing = true;
        txtMaHD.setText("");
        txtMaPT.setText("");
        txtNgayLap.setText("");
        txtNgayTT.setText("");
        txtHanTT.setText("");
        cbbTrangThai.setSelectedIndex(-1);
        txtMaNT.setText("");
        txtTienDV.setText("");
        txtTienDien.setText("");
        txtTienNuoc.setText("");
        locTheoNam = 0;
        locTheoThang = 0;
        txtGiaPhong.setText("");
        hanTTDate = null;
        ngayTTDate = null;
        state = -1;
        isClearing = false;
    }

    private void display(int select) {
        String maHDString = tbl.getValueAt(select, 0).toString();
        String maPTString = tbl.getValueAt(select, 1).toString();
        String maNTString = tbl.getValueAt(select, 2).toString();
        String ngayLapHDString = tbl.getValueAt(select, 3).toString();
        String hanTTString = tbl.getValueAt(select, 4).toString();
        Object ngayTTObj = tbl.getValueAt(select, 5);
        String giaPhongString = tbl.getValueAt(select, 6).toString();
        String tienDienString = tbl.getValueAt(select, 7).toString();
        String tienNuocString = tbl.getValueAt(select, 8).toString();
        String tienDVString = tbl.getValueAt(select, 9).toString();
        String thanhTienString = tbl.getValueAt(select, 10).toString();
        String trangThaiString = tbl.getValueAt(select, 11).toString();

        txtMaHD.setText(maHDString);
        maHD = Integer.parseInt(txtMaHD.getText().trim());
        txtMaPT.setText(maPTString);
        txtMaNT.setText(maNTString);
        txtNgayLap.setText(ngayLapHDString);
        txtHanTT.setText(hanTTString);

        if (ngayTTObj != null) {
            txtNgayTT.setText(ngayTTObj.toString());
            ngayTT = ngayTTObj.toString().trim();
        } else {
            txtNgayTT.setText("");
            ngayTT = "";
        }

        txtGiaPhong.setText(giaPhongString);
        txtTienDien.setText(tienDienString);
        txtTienNuoc.setText(tienNuocString);
        txtTienDV.setText(tienDVString);

        formatThanhTien = new BigDecimal(Double.parseDouble(thanhTienString));
        BigDecimal roundThanhTien = formatThanhTien.setScale(1, RoundingMode.UP);

        hanTT = hanTTString;
        if (trangThaiString.equals("Đã thanh toán")) {
            cbbTrangThai.setSelectedIndex(1);
        } else {
            cbbTrangThai.setSelectedIndex(0);
        }
        giaTienPhong = Double.parseDouble(tbl.getValueAt(select, 6).toString());
    }

    private void init() {
        tbl.fixTable(scroll);
        nav.setForeground(new Color(0, 0, 250));
        setBackground(new Color(243, 243, 243));
        repo = new RepoHoaDon();
        cbbTrangThai.setSelectedIndex(-1);
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
        repo = new RepoHoaDon();
        list = repo.searchAndPage(timKiem, page, limit, locTheoThang, locTheoNam, state);
    }

    private void addRowsToTable() {
        for (ModelHoaDon hd : list) {
            Object[] rows = {
                hd.getMaHD(),
                hd.getMaPT(),
                hd.getMaNguoiTao(),
                hd.getNgayLap(),
                hd.getHanThanhToan(),
                hd.getNgayThanhToan(),
                hd.getGiaPhong(),
                hd.getTienDien(),
                hd.getTienNuoc(),
                hd.getTienDichVu(),
                hd.getTongTien(),
                hd.getTrangThai() == 0 ? "Chưa thanh toán" : "Đã thanh toán"
            };
            model.addRow(rows);
        }
    }

    private void textSearch() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleDocumentChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleDocumentChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });
    }

    private void handleDocumentChange() {
        if (!isClearing) {
            SwingUtilities.invokeLater(() -> {
                performSearch();
            });
        }
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

    public double formatDouble(double number) {
        return Math.round(number * 10000.0) / 10000.0;
    }

    private void showChiTietHoaDon() {
        tbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tbl.getSelectedRow() != -1) {
                    int row = tbl.getSelectedRow();
                    maHoaDon = Integer.parseInt(tbl.getValueAt(row, 0).toString());
                    giaTienPhong = Double.parseDouble(tbl.getValueAt(row, 6).toString());
                    new HoaDonChiTiet().setVisible(true);
                }
            }
        });
    }

    @Override
    public void onQrCodeRead(String qrCodetext) {
        show = true;
        if (show) {
            if (qrCodetext != null || !qrCodetext.isEmpty()) {
                maHD = Integer.parseInt(qrCodetext.substring(2));
                ExportPdf.qrPath = ExportPdf.showIcon.get(maHD);
                showIcon2 = new HashMap<>(ExportPdf.showIcon);
                new ExportPdf().setVisible(true);
            }
        }
        show = false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chonNgayTT = new view.panel.DateChooser();
        chonHanTT = new view.panel.DateChooser();
        nav = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        tbl = new view.component.table.Table();
        prev = new view.component.button.MyButton();
        index = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        end = new javax.swing.JLabel();
        next = new view.component.button.MyButton();
        jLabel1 = new javax.swing.JLabel();
        txtTongHD = new javax.swing.JLabel();
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
        btnCapNhat = new view.component.button.MyButton();
        txtTimKiem = new view.component.textfield.TextField();
        locThang = new view.component.combobox.Combobox();
        locNam = new view.component.combobox.Combobox();
        btnExcel = new view.component.button.MyButton();
        btnPdf = new view.component.button.MyButton();
        cbbTrangThai = new view.component.combobox.Combobox();
        btnThemHoaDon = new view.component.button.MyButton();
        btnTrangThai = new view.component.button.MyButton();
        lblLocTrangThai = new javax.swing.JLabel();
        txtGiaPhong = new view.component.textfield.TextField();
        btnLamMoi1 = new view.component.button.MyButton();

        chonNgayTT.setTextRefernce(txtNgayTT);

        chonHanTT.setTextRefernce(txtHanTT);

        nav.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nav.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nav.setText("Hóa đơn");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã phòng trọ", "Mã người tạo", "Ngày lập", "Hạn thanh toán", "Ngày thanh toán", "Giá phòng", "Tiền điện", "Tiền nước", "Tiền dịch vụ", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tổng số hóa đơn:");

        txtTongHD.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtTongHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 527, Short.MAX_VALUE)
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
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongHD, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(index, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(end, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(prev, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        jPanel2.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        txtMaPT.setEditable(false);
        txtMaPT.setLabelText("Mã phòng trọ");
        txtMaPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPTActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 200, -1));

        txtMaNT.setEditable(false);
        txtMaNT.setLabelText("Mã người tạo");
        txtMaNT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNTActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaNT, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 60, 200, -1));

        txtNgayLap.setEditable(false);
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
        jPanel2.add(txtNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 200, -1));

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
        jPanel2.add(txtHanTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 200, -1));

        txtNgayTT.setLabelText("Ngày thanh toán");
        txtNgayTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNgayTTMouseClicked(evt);
            }
        });
        txtNgayTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTTActionPerformed(evt);
            }
        });
        jPanel2.add(txtNgayTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 200, -1));

        txtTienDien.setEditable(false);
        txtTienDien.setLabelText("Tiền điện");
        txtTienDien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDienActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienDien, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 200, -1));

        txtTienNuoc.setEditable(false);
        txtTienNuoc.setLabelText("Tiền nước");
        txtTienNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienNuocActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienNuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 200, -1));

        txtTienDV.setEditable(false);
        txtTienDV.setLabelText("Tiền dịch vụ");
        txtTienDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDVActionPerformed(evt);
            }
        });
        jPanel2.add(txtTienDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 200, -1));

        btnLamMoi.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btnLamMoi.setText(" Làm mới");
        btnLamMoi.setBorderColor(new java.awt.Color(153, 204, 204));
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
        jPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, 120, 40));

        btnCapNhat.setBackground(new java.awt.Color(204, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setBorderColor(new java.awt.Color(153, 204, 204));
        btnCapNhat.setColor(new java.awt.Color(204, 255, 255));
        btnCapNhat.setColorClick(new java.awt.Color(153, 255, 255));
        btnCapNhat.setColorOver(new java.awt.Color(204, 255, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setRadius(20);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 170, 120, 40));

        txtTimKiem.setLabelText("Tìm kiếm mã");
        jPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 60, 250, -1));

        locThang.setMaximumRowCount(12);
        locThang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "        ", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" }));
        locThang.setLabeText("Lọc theo tháng");
        locThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locThangActionPerformed(evt);
            }
        });
        jPanel2.add(locThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, 160, -1));

        locNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "2020", "2021", "2022", "2023", "2024", "2025", "2026" }));
        locNam.setLabeText("Lọc theo năm");
        locNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locNamActionPerformed(evt);
            }
        });
        jPanel2.add(locNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 160, 160, -1));

        btnExcel.setBackground(new java.awt.Color(204, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_microsoft_excel_2019_24px.png"))); // NOI18N
        btnExcel.setText("Xuất Excel");
        btnExcel.setBorderColor(new java.awt.Color(153, 204, 204));
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
        jPanel2.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 220, 120, 40));

        btnPdf.setBackground(new java.awt.Color(204, 255, 255));
        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_pdf_24px.png"))); // NOI18N
        btnPdf.setText("Xuất PDF");
        btnPdf.setBorderColor(new java.awt.Color(153, 204, 204));
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
        jPanel2.add(btnPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 220, 120, 40));

        cbbTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chưa thanh toán", "Đã thanh toán" }));
        cbbTrangThai.setLabeText("Trạng thái");
        jPanel2.add(cbbTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 200, -1));

        btnThemHoaDon.setBackground(new java.awt.Color(204, 255, 255));
        btnThemHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btnThemHoaDon.setText("Thêm HĐ");
        btnThemHoaDon.setBorderColor(new java.awt.Color(153, 204, 204));
        btnThemHoaDon.setColor(new java.awt.Color(204, 255, 255));
        btnThemHoaDon.setColorClick(new java.awt.Color(153, 255, 255));
        btnThemHoaDon.setColorOver(new java.awt.Color(204, 255, 255));
        btnThemHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemHoaDon.setRadius(20);
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });
        jPanel2.add(btnThemHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 120, 40));

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
        jPanel2.add(btnTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, 50, 50));

        lblLocTrangThai.setForeground(new java.awt.Color(150, 150, 150));
        lblLocTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLocTrangThai.setText("Lọc trạng thái");
        jPanel2.add(lblLocTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 60, 90, -1));

        txtGiaPhong.setEditable(false);
        txtGiaPhong.setLabelText("Tiền phòng");
        txtGiaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaPhongActionPerformed(evt);
            }
        });
        jPanel2.add(txtGiaPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 200, -1));

        btnLamMoi1.setBackground(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_qr_code_24px_1.png"))); // NOI18N
        btnLamMoi1.setText("  QR Code");
        btnLamMoi1.setBorderColor(new java.awt.Color(153, 204, 204));
        btnLamMoi1.setColor(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setColorClick(new java.awt.Color(153, 255, 255));
        btnLamMoi1.setColorOver(new java.awt.Color(204, 255, 255));
        btnLamMoi1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLamMoi1.setRadius(20);
        btnLamMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoi1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnLamMoi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 120, 40));

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
        chonHanTT.showPopup();
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
        clearAll();
        loadTable();
        if (!list.isEmpty()) {
            display(list.size() - 1);
        }
        updatePagination();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        message = new MessageFrame();
        repo = new RepoHoaDon();

        String hanTTString = txtHanTT.getText().trim();
        String ngayTTString = txtNgayTT.getText().trim();
        ngayLapString = txtNgayLap.getText().trim();
        int selected = cbbTrangThai.getSelectedIndex();
        SimpleDateFormat input = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date hanTTDate = null;
        Date ngayLapDate = null;
        Date ngayTTDate = null;

        if (!hanTT.equals(hanTTString)) {
            try {
                hanTTDate = input.parse(hanTTString);
                ngayLapDate = input.parse(ngayLapString);
                if (!ngayTTString.isEmpty()) {
                    ngayTTDate = input.parse(ngayTTString);
                }

                hanTT = output.format(hanTTDate);
                ngayLapString = output.format(ngayLapDate);
                if (!ngayTTString.isEmpty() && ngayTTDate != null) {
                    ngayTT = output.format(ngayTTDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            if (hanTTDate.after(ngayLapDate)) {
                if (selected == 1 && ngayTT.isEmpty()) {
                    txtNgayTT.requestFocus();
                    message.showMessage("error", "Vui lòng chọn ngày thanh toán.");
                    return;
                } else if (!ngayTT.isEmpty() && selected == 0) {
                    cbbTrangThai.requestFocus();
                    message.showMessage("error", "Vui lòng chọn đã thanh toán khi có ngày thanh toán.");
                    return;
                }
                message.showMessage("message", "Bạn có muốn thay đổi Hạn TT thành " + hanTTString + " không?");
                message.setOnOkClicked(() -> {
                    MessageFrame mess = new MessageFrame();
                    if (repo.updateHanTT(hanTT, ngayLapString, ngayTT, maHD)) {
                        mess.showMessage("success", "Cập nhật Hạn TT thành công.");
                    } else {
                        mess.showMessage("success", "Cập nhật Hạn TT thành công.");
                    }
                    refreshTable();
                });
                try {
                    hanTTDate = input.parse(hanTT);
                } catch (Exception e) {
                }
            } else {
                txtHanTT.requestFocus();
                message.showMessage("error", "Hạn thanh toán phải sau ngày lập hóa đơn.");
                return;
            }
        } else {
            if (ngayTT.isEmpty()) {
                txtNgayTT.requestFocus();
                message.showMessage("error", "Không được để trống Ngày thanh toán.");
                return;
            }
            try {
                ngayLapDate = input.parse(ngayLapString);
                hanTTDate = input.parse(hanTTString);
                ngayTTDate = input.parse(txtNgayTT.getText().trim());
                ngayTT = output.format(ngayTTDate);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            if (selected == 1 && ngayTT.isEmpty()) {
                txtNgayTT.requestFocus();
                message.showMessage("error", "Vui lòng chọn ngày thanh toán.");
                return;
            } else if (!ngayTT.isEmpty() && selected == 0) {
                cbbTrangThai.requestFocus();
                message.showMessage("error", "Vui lòng chọn đã thanh toán khi có ngày thanh toán.");
                return;
            }

            if (ngayTTDate.after(ngayLapDate)) {
                message.showMessage("message", "Bạn có muốn cập nhật không?");
                message.setOnOkClicked(() -> {
                    MessageFrame mess = new MessageFrame();
                    if (repo.update(ngayTT, maHD)) {
                        mess.showMessage("success", "Cập nhật thành công.");
                    } else {
                        mess.showMessage("success", "Cập nhật thất bại.");
                    }
                    refreshTable();
                });
            } else {
                txtNgayTT.requestFocus();
                message.showMessage("error", "Ngày thanh toán phải sau ngày lập hóa đơn.");
                return;
            }
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

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
        txtTongHD.setText(repo.totalRecords(timKiem, locTheoThang, locTheoNam, state) + "");
        if (list.isEmpty()) {
            clearAllDontHaveSearchAndFilterYearMonth();
        } else if (!list.isEmpty() && !isClearing) {
            display(list.size() - 1);
        }
    }//GEN-LAST:event_locNamActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        new LuaChonNgayXuatExcel().setVisible(true);
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        new LuaChonNgayXuatPDF().setVisible(true);
    }//GEN-LAST:event_btnPdfActionPerformed

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

    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        new ThemHoaDon().setVisible(true);
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void btnTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangThaiActionPerformed
        state = cbbTrangThai.getSelectedIndex();
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

    private void txtNgayTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNgayTTMouseClicked
        chonNgayTT.showPopup();
    }//GEN-LAST:event_txtNgayTTMouseClicked

    private void txtGiaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaPhongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaPhongActionPerformed

    private void btnLamMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoi1ActionPerformed
        QrCodeHoaDon qrCodeHoaDon = new QrCodeHoaDon();
        qrCodeHoaDon.setQrCodeListener(this);
        qrCodeHoaDon.setVisible(true);
    }//GEN-LAST:event_btnLamMoi1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btnCapNhat;
    private view.component.button.MyButton btnExcel;
    private view.component.button.MyButton btnLamMoi;
    private view.component.button.MyButton btnLamMoi1;
    private view.component.button.MyButton btnPdf;
    private view.component.button.MyButton btnThemHoaDon;
    private view.component.button.MyButton btnTrangThai;
    private view.component.combobox.Combobox cbbTrangThai;
    private view.panel.DateChooser chonHanTT;
    private view.panel.DateChooser chonNgayTT;
    private javax.swing.JLabel end;
    private javax.swing.JLabel index;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblLocTrangThai;
    private view.component.combobox.Combobox locNam;
    private view.component.combobox.Combobox locThang;
    private javax.swing.JLabel nav;
    private view.component.button.MyButton next;
    private view.component.button.MyButton prev;
    private javax.swing.JScrollPane scroll;
    private view.component.table.Table tbl;
    private view.component.textfield.TextField txtGiaPhong;
    private view.component.textfield.TextField txtHanTT;
    private view.component.textfield.TextField txtMaHD;
    private view.component.textfield.TextField txtMaNT;
    private view.component.textfield.TextField txtMaPT;
    private view.component.textfield.TextField txtNgayLap;
    private view.component.textfield.TextField txtNgayTT;
    private view.component.textfield.TextField txtTienDV;
    private view.component.textfield.TextField txtTienDien;
    private view.component.textfield.TextField txtTienNuoc;
    private view.component.textfield.TextField txtTimKiem;
    private javax.swing.JLabel txtTongHD;
    // End of variables declaration//GEN-END:variables
}
