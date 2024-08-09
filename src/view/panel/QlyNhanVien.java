/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import model.NhanVien.Model_NhanVien;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import repository.NhanVien.repoNhanVien;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import view.component.message.MessageFrame;


/**
 *
 * @author CanhPC
 */
public class QlyNhanVien extends javax.swing.JPanel {

    private DefaultTableModel TableNhanVien = new DefaultTableModel();
    private repoNhanVien rpNV = new repoNhanVien();
    private MessageFrame mesF = new MessageFrame();
    private MessageFrame message = new MessageFrame();
    private String path = "D/....";
    private int i = -1;

    /**
     * Creates new form QlyNhanVien
     */
    public QlyNhanVien() {
        initComponents();
        TableNhanVien = (DefaultTableModel) tbl_NhanVien.getModel();
        this.fillTable(rpNV.getAllNhanVien(1));
        textSearch();
        init();
        updateTotalNhanVien();
        loadTableData();
        tbl_NhanVien.fixTable(jScrollPane4);
    }
    public void init() {
        lb_HinhAnh.setText("");
    }

    private void loadTableData() {
        ArrayList<Model_NhanVien> listnv = rpNV.getAllNhanVien(1);
        fillTable(listnv);
    }

    private void updateTotalNhanVien() {
        int totalNhanVien = rpNV.getTotalNhanVien();
        int totalDangLam = rpNV.getTotalNhanVienDangLam();
        int totalDaNghi = rpNV.getTotalNhanVienDaNghi();

        jb_NhanVien.setText("Tổng số: " + totalNhanVien);
        jb_Lam.setText("Đang làm: " + totalDangLam);
        jb_Nghi.setText("Đã nghỉ: " + totalDaNghi);
    }

    private void fillTable(ArrayList<Model_NhanVien> list) {
        TableNhanVien.setRowCount(0);
        list.forEach(s -> {

            TableNhanVien.addRow(new Object[]{
                s.getMaNV(), s.getHoTen(), s.getNgaySinh(), s.getGioiTinh() == 0 ? "Nam" : "Nữ", s.getDienThoai(),
                s.getCCCD(), s.getNgayBD(), s.getNgayKT(), s.getThoiHan(), s.getTrangThai() == 0 ? "Đã nghỉ việc" : "Đang làm việc", s.getAnhNV()
            });
        });
    }

    public Model_NhanVien readFrom() {
        Model_NhanVien nv = new Model_NhanVien();
        // check không được để trống
        if (txt_Ma.getText().trim().isEmpty()
                || txt_Ten.getText().trim().isEmpty()
                || txt_CCCD.getText().trim().isEmpty()
                || txt_ThoiHan.getText().trim().isEmpty()
                || txt_NgSinh.getDate() == null
                || txt_NgayBatDau.getDate() == null) {
            mesF.showMessage("error", "Vui lòng không để trống các trường trừ ngày kết thúc và ảnh");
            return null; // Hoặc thực hiện các hành động khác khi có lỗi
        }
        // Gán giá trị cho các thuộc tính của đối tượng
        nv.setMaNV(txt_Ma.getText());
        nv.setHoTen(txt_Ten.getText());
        nv.setNgaySinh(txt_NgSinh.getDate());
        if (rdo_Nam.isSelected()) {
            nv.setGioiTinh(0); // Giới tính Nam
        } else {
            nv.setGioiTinh(1); // Giới tính Nữ
        }
        nv.setDienThoai(txt_DT.getText());
        nv.setCCCD(txt_CCCD.getText());
        nv.setNgayBD(txt_NgayBatDau.getDate());
        nv.setNgayKT(txt_NgayKT.getDate());
        try {
            nv.setThoiHan(Integer.parseInt(txt_ThoiHan.getText())); // Chuyển đổi giá trị thành kiểu int
        } catch (NumberFormatException e) {
            mesF.showMessage("error", "Thời hạn phải là một số nguyên hợp lệ");
            return null; // Hoặc thực hiện các hành động khác khi có lỗi
        }
        nv.setAnhNV(path);
        if (rdo_Nghi.isSelected()) {
            nv.setTrangThai(0); // nghỉ làm
        } else {
            nv.setTrangThai(1);// đi làm
        }
        String cccd = txt_CCCD.getText().trim();
        // Kiểm tra CCCD chỉ chứa đúng 12 chữ số
        if (!cccd.matches("\\d{12}")) {
            mesF.showMessage("error", "Số CCCD phải có đúng 12 chữ số và chỉ chứa chữ số");
            return null;
        }
        // Kiểm tra mã tỉnh/thành phố
        String maTinh = cccd.substring(0, 3);
        int maTinhNum = Integer.parseInt(maTinh);
        if (maTinhNum < 1 || maTinhNum > 96) {
            mesF.showMessage("error", "Mã CCCD không hợp lệ, kiểm tra lại 3 số đầu");
            return null;
        }
        // Kiểm tra mã thế kỷ và giới tính
        char maGioiTinh = cccd.charAt(3);
        if (maGioiTinh != '0' && maGioiTinh != '1' && maGioiTinh != '2' && maGioiTinh != '3') {
            mesF.showMessage("error", "Mã CCCD không hợp lệ, kiểm tra lại số thứ tự thứ 4");
            return null;
        }
        // Kiểm tra năm sinh
        String namSinh = cccd.substring(4, 6);
        try {
            int nam = Integer.parseInt(namSinh);
            if (nam < 00 || nam > 99) {
                mesF.showMessage("error", "Mã CCCD không hợp lệ, kiểm tra lại số thứ tự 5 và 6");
                return null;
            }
        } catch (NumberFormatException e) {
            mesF.showMessage("error", "Mã CCCD không hợp lệ, kiểm tra lại số thứ tự 5 và 6");
            return null;
        }
        // Kiểm tra số điện thoại
        String dienThoai = nv.getDienThoai();
        // Kiểm tra số điện thoại phải bắt đầu bằng số 0
        if (!dienThoai.startsWith("0")) {
            mesF.showMessage("error", "Số điện thoại phải bắt đầu bằng số 0");
            return null;
        }
        // Kiểm tra số điện thoại chỉ chứa chữ số
        if (!dienThoai.matches("\\d+")) {
            mesF.showMessage("error", "Số điện thoại chỉ được chứa chữ số");
            return null;
        }
        // Kiểm tra số điện thoại phải có đúng 10 chữ số
        if (dienThoai.length() != 10) {
            mesF.showMessage("error", "Số điện thoại phải có đúng 10 chữ số");
            return null;
        }
        // Kiểm tra mã vùng hợp lệ (bao gồm các mã vùng phổ biến ở Việt Nam)
        String[] validPrefixes = {"09", "08", "07", "05", "03", "02"};
        boolean isValidPrefix = false;
        for (String prefix : validPrefixes) {
            if (dienThoai.startsWith(prefix)) {
                isValidPrefix = true;
                break;
            }
        }
        if (!isValidPrefix) {
            mesF.showMessage("error", "Có thể hai số đầu SDT không hợp lệ: 00,01,04 ko nên dùng");
            return null;
        }
        Date ngaySinhDate = txt_NgSinh.getDate();
        LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHienTai = LocalDate.now();
        Period period = Period.between(ngaySinh, ngayHienTai);
        int tuoi = period.getYears();
        if (tuoi < 18) {
            mesF.showMessage("error", "Người này chưa đủ 18 tuổi");
            return null;
        }
        Date ngayBatDauDate = txt_NgayBatDau.getDate();
        Date ngayKetThucDate = txt_NgayKT.getDate();
        if (ngayBatDauDate != null && ngayKetThucDate != null) {
            LocalDate ngayBatDau = ngayBatDauDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayKetThuc = ngayKetThucDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Period thoiGianLamViec = Period.between(ngayBatDau, ngayKetThuc);
            if (thoiGianLamViec.getMonths() < 1 && thoiGianLamViec.getYears() == 0) {
                mesF.showMessage("error", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất một tháng");
                return null;
            }
        }
        // Kiểm tra Thời hạn
        if (nv.getThoiHan() == null || nv.getThoiHan() <= 0) {
            mesF.showMessage("error", "Thời hạn hợp đồng ít nhất phải được 1 tháng");
            return null;
        }
        return nv;

    }

    public void clear() {
        txt_Ma.setText("");
        txt_Ten.setText("");
        txt_NgSinh.setDate(null);
        //Model_NhanVien nv = new Model_NhanVien();
        rdo_Nam.setSelected(true);
        txt_DT.setText("");
        txt_CCCD.setText("");
        txt_NgayBatDau.setDate(null);
        txt_NgayKT.setDate(null);
        txt_ThoiHan.setText("0"); // Đặt giá trị mặc định cho thoiHan
        lb_HinhAnh.setIcon(null);
        lb_HinhAnh.setText("    Them hinh anh");

    }

    private void textSearch() {
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiem();
            }

        });
    }

    private void timKiem() {
        String timkiem = txt_TimKiem.getText().trim();
        fillTable(rpNV.tim(timkiem));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        chonNgayBD = new view.panel.DateChooser();
        chonNgayKT = new view.panel.DateChooser();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lb_HinhAnh = new javax.swing.JLabel();
        txt_Ma = new view.component.textfield.TextField();
        txt_Ten = new view.component.textfield.TextField();
        jPanel4 = new javax.swing.JPanel();
        txt_NgSinh = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        txt_NgayKT = new com.toedter.calendar.JDateChooser();
        txt_DT = new view.component.textfield.TextField();
        txt_CCCD = new view.component.textfield.TextField();
        jPanel7 = new javax.swing.JPanel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        txt_ThoiHan = new view.component.textfield.TextField();
        jPanel5 = new javax.swing.JPanel();
        txt_NgayBatDau = new com.toedter.calendar.JDateChooser();
        btn_ThemNV = new view.component.button.MyButton();
        btn_XoaNV = new view.component.button.MyButton();
        btn_SuaNV = new view.component.button.MyButton();
        btn_ResetNV = new view.component.button.MyButton();
        jPanel3 = new javax.swing.JPanel();
        rdo_Lam = new view.component.radiobutton.RadioButtonCustom();
        rdo_Nghi = new view.component.radiobutton.RadioButtonCustom();
        jb_NhanVien = new javax.swing.JLabel();
        jb_Lam = new javax.swing.JLabel();
        jb_Nghi = new javax.swing.JLabel();
        btn_LoadNV = new view.component.button.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_NhanVien = new view.component.table.Table();
        txt_TimKiem = new view.component.textfield.TextField();
        jLabel1 = new javax.swing.JLabel();
        cboLocTrangThai = new view.component.combobox.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(new java.awt.Color(224, 247, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhân Viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 36))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(153, 204, 255));
        jLabel2.setFont(new java.awt.Font("sansserif", 3, 18)); // NOI18N

        lb_HinhAnh.setBackground(new java.awt.Color(0, 0, 255));
        lb_HinhAnh.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lb_HinhAnh.setForeground(new java.awt.Color(0, 0, 255));
        lb_HinhAnh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lb_HinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_HinhAnhMouseClicked(evt);
            }
        });

        txt_Ma.setBackground(new java.awt.Color(224, 247, 250));
        txt_Ma.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_Ma.setLabelText("Mã nhân viên");
        txt_Ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaActionPerformed(evt);
            }
        });

        txt_Ten.setBackground(new java.awt.Color(224, 247, 250));
        txt_Ten.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_Ten.setLabelText("Họ và Tên");
        txt_Ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TenActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(224, 247, 250));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày Sinh", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        jPanel4.setFocusable(false);

        txt_NgSinh.setBackground(new java.awt.Color(224, 247, 250));
        txt_NgSinh.setDateFormatString("dd-MM-yyyy");
        txt_NgSinh.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_NgSinh, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_NgSinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new java.awt.Color(224, 247, 250));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày kết thúc", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txt_NgayKT.setBackground(new java.awt.Color(224, 247, 250));
        txt_NgayKT.setDateFormatString("dd-MM-yyyy");
        txt_NgayKT.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_NgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txt_DT.setBackground(new java.awt.Color(224, 247, 250));
        txt_DT.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_DT.setLabelText("Số điện thoại");
        txt_DT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DTActionPerformed(evt);
            }
        });

        txt_CCCD.setBackground(new java.awt.Color(224, 247, 250));
        txt_CCCD.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_CCCD.setLabelText("CCCD");
        txt_CCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CCCDActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(224, 247, 250));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giới Tính", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        rdo_Nam.setText("Nam");
        rdo_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        rdo_Nu.setText("Nữ");
        rdo_Nu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(rdo_Nam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(rdo_Nu)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdo_Nam)
                .addComponent(rdo_Nu))
        );

        txt_ThoiHan.setBackground(new java.awt.Color(224, 247, 250));
        txt_ThoiHan.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_ThoiHan.setLabelText("Thời Hạn");
        txt_ThoiHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ThoiHanActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(224, 247, 250));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày bắt đầu", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txt_NgayBatDau.setBackground(new java.awt.Color(224, 247, 250));
        txt_NgayBatDau.setDateFormatString("dd-MM-yyyy");
        txt_NgayBatDau.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btn_ThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThemNV.png"))); // NOI18N
        btn_ThemNV.setText("Thêm");
        btn_ThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNVActionPerformed(evt);
            }
        });

        btn_XoaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete_NV.png"))); // NOI18N
        btn_XoaNV.setText("Xóa");
        btn_XoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaNVActionPerformed(evt);
            }
        });

        btn_SuaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/danhgiasua.png"))); // NOI18N
        btn_SuaNV.setText("Cập nhật");
        btn_SuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaNVActionPerformed(evt);
            }
        });

        btn_ResetNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btn_ResetNV.setText("Reset");
        btn_ResetNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetNVActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tình trạng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        buttonGroup2.add(rdo_Lam);
        rdo_Lam.setText("Đang làm việc");
        rdo_Lam.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        rdo_Lam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_LamActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_Nghi);
        rdo_Nghi.setText("Đã nghỉ việc");
        rdo_Nghi.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdo_Lam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(rdo_Nghi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_Lam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdo_Nghi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jb_NhanVien.setBackground(new java.awt.Color(0, 0, 255));
        jb_NhanVien.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jb_NhanVien.setForeground(new java.awt.Color(0, 51, 255));
        jb_NhanVien.setText("Tổng số:");

        jb_Lam.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jb_Lam.setForeground(new java.awt.Color(51, 51, 255));
        jb_Lam.setText("Đang làm:");

        jb_Nghi.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jb_Nghi.setForeground(new java.awt.Color(51, 51, 255));
        jb_Nghi.setText("Đã nghỉ");

        btn_LoadNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btn_LoadNV.setText("Load");
        btn_LoadNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoadNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_ThoiHan, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(txt_CCCD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_DT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_Ten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_ThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_SuaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(btn_LoadNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jb_Lam)
                                        .addGap(18, 18, 18)
                                        .addComponent(jb_Nghi))
                                    .addComponent(jb_NhanVien)
                                    .addComponent(btn_ResetNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_XoaNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(txt_ThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_Ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(txt_DT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txt_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_ResetNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jb_NhanVien)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_Lam)
                            .addComponent(jb_Nghi)))
                    .addComponent(btn_LoadNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lb_HinhAnh.getAccessibleContext().setAccessibleDescription("");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ Tên", "Ngày sinh", "Giới tính", "Điện Thoại", "CCCD", "Ngày bắt đầu", "Ngày kết thúc", "Thời Hạn", "Trạng Thái", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_NhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_NhanVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_NhanVien);

        txt_TimKiem.setLabelText(" Tìm kiếm mã, Tên");
        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/timNV.png"))); // NOI18N

        cboLocTrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất Cả", "Đang làm việc", "Đã nghỉ việc" }));
        cboLocTrangThai.setSelectedIndex(-1);
        cboLocTrangThai.setLabeText("Trạng thái");
        cboLocTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocTrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboLocTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void txt_ThoiHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ThoiHanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ThoiHanActionPerformed

    private void rdo_NuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NuActionPerformed

    private void rdo_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NamActionPerformed

    private void txt_CCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_CCCDActionPerformed

    private void txt_DTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DTActionPerformed

    private void txt_TenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TenActionPerformed

    private void txt_MaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaActionPerformed

    private void lb_HinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_HinhAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int ketQua = chooser.showOpenDialog(null);
        if (ketQua == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
            ImageIcon icon = new ImageIcon(path);
            lb_HinhAnh.setIcon(icon);
        }
    }//GEN-LAST:event_lb_HinhAnhMouseClicked

    private void btn_ThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNVActionPerformed
        // TODO add your handling code here:
        Model_NhanVien nv = this.readFrom();
        if (nv != null) {
            // Kiểm tra trùng lặp Mã nhân viên
            if (rpNV.checkTrungMaNV(nv.getMaNV()) != null) {
                mesF.showMessage("error", "Không được trùng Mã nhân viên");
            } // Kiểm tra trùng lặp Điện thoại
            else if (rpNV.checkTrungDienThoai(nv.getDienThoai()) != null) {
                mesF.showMessage("error", "Không được trùng Điện thoại");
            } // Kiểm tra trùng lặp CCCD
            else if (rpNV.checkTrungCCCD(nv.getCCCD()) != null) {
                mesF.showMessage("error", "Không được trùng CCCD");
            } else {
                message.showMessage("message", "Bạn có chắc chắn muốn thêm nhân viên không:? ");
                message.setOnOkClicked(() -> {
                    // Thêm nhân viên mới
                    if (rpNV.add(nv) > 0) {
                        mesF.showMessage("success", "Thêm thành công");
                        this.fillTable(rpNV.getAllNhanVien(1));
                        updateTotalNhanVien();
                    } else {
                        mesF.showMessage("error", "Thêm thất bại");
                    }
                });
            }
        }
    }//GEN-LAST:event_btn_ThemNVActionPerformed

    private void btn_XoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaNVActionPerformed
        // TODO add your handling code here:
        // Lấy hàng được chọn trong bảng
        int i = tbl_NhanVien.getSelectedRow();
        if (i == -1) {
            mesF.showMessage("error", "Vui lòng chọn nhân viên cần xóa.");
            return;
        }
        // Lấy mã nhân viên từ bảng
        String maNV = tbl_NhanVien.getValueAt(i, 0).toString();
        // Hiển thị hộp thoại xác nhận
        message.showMessage("message", "Nhân viên của bạn đã nghỉ việc (Xóa) ");
        message.setOnOkClicked(() -> {
            // Thực hiện "xóa mềm"
            if (rpNV.DeleteNV(maNV) > 0) {
                mesF.showMessage("success", "Xóa thành công");
                this.fillTable(rpNV.getAllNhanVien(1)); // Cập nhật lại bảng nhân viên
                updateTotalNhanVien();
            } else {
                mesF.showMessage("error", "Xóa thất bại");
            }
        });

    }//GEN-LAST:event_btn_XoaNVActionPerformed

    private void btn_SuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaNVActionPerformed
        int i = tbl_NhanVien.getSelectedRow();
        if (i >= 0) {
            Model_NhanVien nv = this.readFrom();
            if (nv != null) {
                String maNV = tbl_NhanVien.getValueAt(i, 0).toString(); // Mã nhân viên hiện tại trong bảng

                // Kiểm tra mã nhân viên đã nhập có khác với mã nhân viên hiện tại không
                if (!maNV.equals(nv.getMaNV())) {
                    mesF.showMessage("error", "Không được phép sửa mã nhân viên.");
                    return; // Dừng việc sửa
                }

                // Kiểm tra trùng số điện thoại
                Model_NhanVien nvTrungDienThoai = rpNV.checkTrungDienThoai(nv.getDienThoai());
                if (nvTrungDienThoai != null && !nvTrungDienThoai.getMaNV().equals(maNV)) {
                    mesF.showMessage("error", "Số điện thoại đã tồn tại cho nhân viên khác.");
                    return; // Dừng việc sửa
                }

                // Kiểm tra trùng số CCCD
                Model_NhanVien nvTrungCCCD = rpNV.checkTrungCCCD(nv.getCCCD());
                if (nvTrungCCCD != null && !nvTrungCCCD.getMaNV().equals(maNV)) {
                    mesF.showMessage("error", "Số CCCD đã tồn tại cho nhân viên khác.");
                    return; // Dừng việc sửa
                }

                message.showMessage("message", "Bạn có chắc chắn muốn sửa nhân viên này không?");
                message.setOnOkClicked(() -> {
                    if (rpNV.update(maNV, nv) > 0) {
                        mesF.showMessage("success", "Sửa thông tin nhân viên thành công");
                        this.fillTable(rpNV.getAllNhanVien(1)); // Cập nhật bảng sau khi sửa
                        updateTotalNhanVien();
                    } else {
                        mesF.showMessage("error", "Sửa thất bại");
                    }
                });
            }
        } else {
            mesF.showMessage("error", "Vui lòng chọn nhân viên để sửa");
        }


    }//GEN-LAST:event_btn_SuaNVActionPerformed

    private void btn_ResetNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetNVActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_ResetNVActionPerformed

    private void rdo_LamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_LamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_LamActionPerformed

    private void cboLocTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocTrangThaiActionPerformed
        // TODO add your handling code here:
        // Kiểm tra xem giá trị chọn trong combo box có phải là số không
        String selectedItem = cboLocTrangThai.getSelectedItem().toString();
        // Kiểm tra giá trị được chọn và gọi phương thức tương ứng
        if (selectedItem.equals("Tất Cả")) {
            fillTable(rpNV.getAllNhanVien()); // Lấy tất cả nhân viên
        } else if (selectedItem.equals("Đang làm việc")) {
            fillTable(rpNV.getAllNhanVien(1)); // Lấy nhân viên đang làm việc
        } else if (selectedItem.equals("Đã nghỉ việc")) {
            fillTable(rpNV.getAllNhanVien(0)); // Lấy nhân viên đã nghỉ việc
        }
        updateTotalNhanVien();
    }//GEN-LAST:event_cboLocTrangThaiActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
        i = tbl_NhanVien.getSelectedRow();
        txt_Ma.setText(tbl_NhanVien.getValueAt(i, 0).toString());
        txt_Ten.setText(tbl_NhanVien.getValueAt(i, 1).toString());
        Date ngaySinh = (Date) tbl_NhanVien.getValueAt(i, 2);
        txt_NgSinh.setDate(ngaySinh);
        // Kiểm tra và gán giá trị cho radio button
        String gioiTinh = tbl_NhanVien.getValueAt(i, 3).toString();
        if (gioiTinh.equalsIgnoreCase("Nam")) {
            rdo_Nam.setSelected(true);
        } else {
            rdo_Nu.setSelected(true);
        }

        txt_DT.setText(tbl_NhanVien.getValueAt(i, 4).toString());
        txt_CCCD.setText(tbl_NhanVien.getValueAt(i, 5).toString());

        Date ngayBD = (Date) tbl_NhanVien.getValueAt(i, 6);
        txt_NgayBatDau.setDate(ngayBD);

        Date ngayKT = (Date) tbl_NhanVien.getValueAt(i, 7);
        txt_NgayKT.setDate(ngayKT);

        int thoiHan = (int) tbl_NhanVien.getValueAt(i, 8);
        txt_ThoiHan.setText(String.valueOf(thoiHan));

        // Kiểm tra và gán giá trị cho radio button trạng thái
        String trangThai = tbl_NhanVien.getValueAt(i, 9).toString();
        if (trangThai.equalsIgnoreCase("Đã nghỉ việc")) {
            rdo_Nghi.setSelected(true);
        } else {
            rdo_Lam.setSelected(true);
        }
        String imagePath = tbl_NhanVien.getValueAt(i, 10).toString();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lb_HinhAnh.setIcon(imageIcon);
    }//GEN-LAST:event_tbl_NhanVienMouseClicked

    private void btn_LoadNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoadNVActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_btn_LoadNVActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btn_LoadNV;
    private view.component.button.MyButton btn_ResetNV;
    private view.component.button.MyButton btn_SuaNV;
    private view.component.button.MyButton btn_ThemNV;
    private view.component.button.MyButton btn_XoaNV;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private view.component.combobox.Combobox cboLocTrangThai;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jb_Lam;
    private javax.swing.JLabel jb_Nghi;
    private javax.swing.JLabel jb_NhanVien;
    private javax.swing.JLabel lb_HinhAnh;
    private view.component.radiobutton.RadioButtonCustom rdo_Lam;
    private javax.swing.JRadioButton rdo_Nam;
    private view.component.radiobutton.RadioButtonCustom rdo_Nghi;
    private javax.swing.JRadioButton rdo_Nu;
    private view.component.table.Table tbl_NhanVien;
    private view.component.textfield.TextField txt_CCCD;
    private view.component.textfield.TextField txt_DT;
    private view.component.textfield.TextField txt_Ma;
    private com.toedter.calendar.JDateChooser txt_NgSinh;
    private com.toedter.calendar.JDateChooser txt_NgayBatDau;
    private com.toedter.calendar.JDateChooser txt_NgayKT;
    private view.component.textfield.TextField txt_Ten;
    private view.component.textfield.TextField txt_ThoiHan;
    private view.component.textfield.TextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables

}
