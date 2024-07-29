/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import model.NhanVien.Model_DiemDanh;
import java.util.Date;
import model.NhanVien.Model_NhanVien;
//import model.NhanVien.Model_DiemDanh;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import repository.NhanVien.repoNhanVien;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import repository.NhanVien.repoDiemDanh;
//import service.EventDateChooser;
//import service.SelectedAction;
//import view.component.calendar.SelectedDate;
import view.component.message.MessageFrame;

/**
 *
 * @author CanhPC
 */
public class QlyNhanVien extends javax.swing.JPanel {
    private DefaultTableModel TableNhanVien = new DefaultTableModel();
    private DefaultTableModel TableDiemDanh = new DefaultTableModel();
    private repoNhanVien rpNV = new repoNhanVien();
    private MessageFrame mesF = new MessageFrame();
    private repoDiemDanh rpDD = new repoDiemDanh();
    private List<Model_NhanVien> list = new ArrayList<>();
    private List<Model_DiemDanh> list1 = new ArrayList<>();
    private String path = "D/....";
    private int i = -1;

//    private Date ngayBatDauDate;
//    private Date ngayKetThucDate;
    /**
     * Creates new form QlyNhanVien
     */
    public QlyNhanVien() {
        initComponents();
        TableNhanVien = (DefaultTableModel) tbl_NhanVien.getModel();
        TableDiemDanh = (DefaultTableModel) tbl_ChamCong.getModel();
        this.fillTable(rpNV.getAllNhanVien());
        this.fillTable2(rpDD.getALLDiemDanh());
        init();
        textSearch();
    }
    
    public void init() {
        lb_HinhAnh.setText("");
    }

    private void fillTable(ArrayList<Model_NhanVien> list) {
        TableNhanVien.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(s -> TableNhanVien.addRow(new Object[]{
            index.getAndIncrement(), s.getMaNV(), s.getHoTen(), s.getNgaySinh(), s.getGioiTinh() == 0 ? "Nam" : "Nữ", s.getDienThoai(),
            s.getCCCD(), s.getNgayBD(), s.getNgayKT(), s.getThoiHan(), s.getAnhNV()
        }));
    }

    private void fillTable2(ArrayList<Model_DiemDanh> list) {
        TableDiemDanh.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        // Sử dụng danh sách 'list' đã truyền vào
        list.forEach(s -> TableDiemDanh.addRow(new Object[]{
          index.getAndIncrement(),
            s.getMaDiemDanh(),
            s.getMaNV(),
            s.getNgayLamViec(),
            s.getTrangThai() == 0 ? "Không đi làm" : "Đi làm"
        }));
    }

    public Model_NhanVien readFrom() {
        Model_NhanVien nv = new Model_NhanVien();
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
        nv.setThoiHan(Integer.parseInt(txt_ThoiHan.getText())); // Chuyển đổi giá trị thành kiểu int
        nv.setAnhNV(path);
        if (txt_Ma.getText().trim().isEmpty() ||
        txt_Ten.getText().trim().isEmpty() ||
        txt_CCCD.getText().trim().isEmpty()) {
//      JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã, Tên, CCCD)");
        mesF.showMessage("error", "Vui lòng ko được để trống Mã,Tên, cccd");
        return null; // Hoặc thực hiện các hành động khác khi có lỗi
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
        mesF.showMessage("error", "Mã tỉnh/thành phố không hợp lệ");
        return null;
}
        // Kiểm tra mã thế kỷ và giới tính
        char maGioiTinh = cccd.charAt(3);
        if (maGioiTinh != '0' && maGioiTinh != '1' && maGioiTinh != '2' && maGioiTinh != '3') {
        mesF.showMessage("error", "Mã thế kỷ và giới tính không hợp lệ");
        return null;
        }
        // Kiểm tra năm sinh
        String namSinh = cccd.substring(4, 6);
        try {
             int nam = Integer.parseInt(namSinh);
               if (nam < 00 || nam > 99) {
        mesF.showMessage("error", "Năm sinh không hợp lệ");
        return null;
        }
        } catch (NumberFormatException e) {
        mesF.showMessage("error", "Năm sinh không hợp lệ");
        return null;
        }
        // Nếu tất cả kiểm tra đều thành công
        //mesF.showMessage("success", "Số CCCD hợp lệ");

        // Kiểm tra độ dài của CCCD
//        String cccd = txt_CCCD.getText().trim();
//        if (cccd. length() < 12 || cccd.length() > 12) {
////      JOptionPane.showMessageDialog(null, "Số CCCD phải có đúng 12 chữ số");
//        mesF.showMessage("error", "Số CCCD phải có đúng 12 chữ số");
//        return null;
//        }  
//        if (!cccd.matches("\\d{12}")) {
//        mesF.showMessage("error", "Số CCCD chỉ được chứa chữ số");
//        return null;
//        }
        // Kiểm tra số điện thoại phải bắt đầu bằng số 0
        String dienThoai = txt_DT.getText().trim();
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
//        Date ngayBatDau = txt_NgayBatDau.getDate();
//        Date ngayKetThuc = txt_NgayKT.getDate();
//
//        if (ngayKetThuc.before(ngayBatDau)) {
////        JOptionPane.showMessageDialog(null, "Ngày kết thúc phải lớn hơn ngày bắt đầu!");
//          mesF.showMessage("error", "Ngày kết thúc phải lớn hơn ngày bắt đầu");
//        return null;
//    }
        Date ngaySinhDate = txt_NgSinh.getDate();
        LocalDate ngaySinh = ngaySinhDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHienTai = LocalDate.now();

        Period period = Period.between(ngaySinh, ngayHienTai);
        int tuoi = period.getYears();
        if (tuoi < 18) {
//        JOptionPane.showMessageDialog(null, "Người này chưa đủ 18 tuổi!");
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
        return nv;
        
    }

    private Model_DiemDanh readFrom2() {
    // Tạo đối tượng Model_DiemDanh mới
    Model_DiemDanh chamC = new Model_DiemDanh();
    // Lấy dữ liệu từ các trường nhập liệu
    chamC.setMaNV(txt_MaNV.getText());
    chamC.setNgayLamViec(txt_NgayCong.getDate()); // Giả sử txt_NgayCong là một trường nhập liệu ngày
    // Đặt giá trị cho trường TrangThai dựa trên điều kiện nào đó
    // Ví dụ: nếu checkbox được chọn thì TrangThai = 1 (Đi làm), ngược lại TrangThai = 0 (Nghỉ làm)
    if (rdo_Nghi.isSelected()) {
        chamC.setTrangThai(0); // Đi làm
    } else {
        chamC.setTrangThai(1); // Không đi làm
    }
    // Trả về đối tượng Model_DiemDanh đã được thiết lập
    return chamC;
}

    
    public void clear() {
        txt_Ma.setText("");
        txt_Ten.setText("");
        txt_NgSinh.setDate(null);
        Model_NhanVien nv = new Model_NhanVien();
        rdo_Nam.setSelected(true);
        txt_DT.setText("");
        txt_CCCD.setText("");
        txt_NgayBatDau.setDate(null);
        txt_NgayKT.setDate(null);
        txt_ThoiHan.setText("0"); // Đặt giá trị mặc định cho thoiHan
        lb_HinhAnh.setText("Them hinh anh");
        
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
//    private void dateChooser() {
//        chonNgayBD.addEventDateChooser(new EventDateChooser() {
//            @Override
//            public void dateSelected(SelectedAction action, SelectedDate date) {
//                if (date != null) {
//                    String textDate = date.getDay() + "-" + date.getMonth() + "-" + date.getYear();
//                    ngayBatDauDate = formatDate(textDate);
////                    System.out.println(textDate);
//                    if (action.getAction() == SelectedAction.DAY_SELECTED) {
//                        chonNgayBD.hidePopup();
//                    }
//                } else {
//                    ngayBatDauDate = null;
//                }
//            }
//        });
    private void timKiem() {
        String timkiem = txt_TimKiem.getText().trim();
        fillTable(rpNV.tim(timkiem));
    }
//     public void fillFrom(Model_NhanVien nv){
//         txt_Ma.setText(nv.getMaNV());
//         txt_Ten.setText(nv.getHoTen());
//         txt_NgSinh.setDate(nv.getNgaySinh());
//         if (nv.isGioiTinh()) {
//         rdo_Nam.setSelected(true);
//         } else {
//         rdo_Nu.setSelected(true);
//         }
//         txt_DT.setText(nv.getDienThoai());
//         txt_CCCD.setText(nv.getCCCD());
//         txt_NgayBatDau.setDate(nv.getNgayBD());
//         txt_NgayKT.setDate(nv.getNgayKT());
//         txt_ThoiHan.setText(Integer.toString(nv.getThoiHan()));
//         path = nv.getAnhNV();
//         ImageIcon img = new ImageIcon(nv.getAnhNV());
//         lb_HinhAnh.setIcon(img);
//     }
//    private void showTable(int index){
//        Model_NhanVien nv = rp.getAll().get(index);
//        txt_Ma.setText(nv.getMaNV());
//        txt_Ten.setText(nv.getHoTen());
//        txt_NgSinh.setDate(nv.getNgaySinh());
//        boolean gioiTinh = nv.isGioiTinh();
//        if (gioiTinh) {
//        rdo_Nam.setSelected(true);
//       }else{
//        rdo_Nu.setSelected(true);
//        }
//        txt_DT.setText(nv.getDienThoai());
//        txt_CCCD.setText(nv.getCCCD());
//        txt_NgayBatDau.setDate(nv.getNgayBD());
//        txt_NgayKT.setDate(nv.getNgayKT());
//        txt_ThoiHan.setText(nv.getThoiHan()+"");
//        path = nv.getAnhNV();
//        ImageIcon img = new ImageIcon();
//        lb_HinhAnh.setIcon(img);
//    }
//    public Model_NhanVien getFormData(){
//    boolean gioiTinh = true; // Giới tính mặc định là Nam
//     if (rdo_Nu.isSelected()) {
//        gioiTinh = false;
//     }
//        Model_NhanVien nv = Model_NhanVien.builder()
//                .maNV(txt_Ma.getText())
//                .hoTen(txt_Ten.getText())
//                .ngaySinh(txt_NgSinh.getDate())
//                .gioiTinh(gioiTinh)
//                .dienThoai(txt_DT.getText())
//                .CCCD(txt_CCCD.getText())
//                .ngayBD(txt_NgayBatDau.getDate())
//                .ngayKT(txt_NgayKT.getDate())
//                .thoiHan(Integer.parseInt(txt_ThoiHan.getText()))
//                .anhNV(path)
//                .build();
//        return nv;
//    }

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
        btn_ResetNV = new view.component.button.Button();
        btn_SuaNV = new view.component.button.Button();
        btn_ThemNV = new view.component.button.Button();
        btn_XoaNV = new view.component.button.Button();
        jPanel8 = new javax.swing.JPanel();
        rdo_Lam = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        txt_NgayCong = new com.toedter.calendar.JDateChooser();
        rdo_Nghi = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ChamCong = new view.component.table.Table();
        txt_MaNV = new view.component.textfield.TextField();
        btn_DiemDanh = new javax.swing.JButton();
        btn_XapXepDD = new javax.swing.JButton();
        cbo_Thang = new javax.swing.JComboBox<>();
        cbo_Nam = new javax.swing.JComboBox<>();
        cbo_Ngay = new javax.swing.JComboBox<>();
        btn_XoaDD = new javax.swing.JButton();
        btn_Next = new view.component.button.MyButton();
        btn_Prev = new view.component.button.MyButton();
        jPanel5 = new javax.swing.JPanel();
        txt_NgayBatDau = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_NhanVien = new view.component.table.Table();
        txt_TimKiem = new view.component.textfield.TextField();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
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

        txt_Ma.setBackground(new java.awt.Color(204, 204, 255));
        txt_Ma.setLabelText("Mã nhân viên");
        txt_Ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaActionPerformed(evt);
            }
        });

        txt_Ten.setBackground(new java.awt.Color(204, 204, 255));
        txt_Ten.setLabelText("Họ và Tên");
        txt_Ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TenActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày Sinh", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txt_NgSinh.setBackground(new java.awt.Color(204, 204, 204));
        txt_NgSinh.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txt_NgSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_NgSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày kết thúc", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txt_NgayKT.setBackground(new java.awt.Color(204, 204, 255));
        txt_NgayKT.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txt_DT.setBackground(new java.awt.Color(204, 204, 255));
        txt_DT.setLabelText("Số điện thoại");
        txt_DT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DTActionPerformed(evt);
            }
        });

        txt_CCCD.setBackground(new java.awt.Color(204, 204, 255));
        txt_CCCD.setLabelText("CCCD");
        txt_CCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CCCDActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giới Tính", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        rdo_Nam.setText("Nam");
        rdo_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdo_Nu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdo_Nam)
                .addComponent(rdo_Nu))
        );

        txt_ThoiHan.setBackground(new java.awt.Color(204, 204, 255));
        txt_ThoiHan.setLabelText("Thời Hạn");
        txt_ThoiHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ThoiHanActionPerformed(evt);
            }
        });

        btn_ResetNV.setBackground(new java.awt.Color(204, 204, 255));
        btn_ResetNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btn_ResetNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetNVActionPerformed(evt);
            }
        });

        btn_SuaNV.setBackground(new java.awt.Color(204, 204, 255));
        btn_SuaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/danhgiasua.png"))); // NOI18N
        btn_SuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaNVActionPerformed(evt);
            }
        });

        btn_ThemNV.setBackground(new java.awt.Color(204, 204, 255));
        btn_ThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThemNV.png"))); // NOI18N
        btn_ThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemNVActionPerformed(evt);
            }
        });

        btn_XoaNV.setBackground(new java.awt.Color(204, 204, 255));
        btn_XoaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete_NV.png"))); // NOI18N
        btn_XoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaNVActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Điểm Danh", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));

        buttonGroup2.add(rdo_Lam);
        rdo_Lam.setText("Đi làm");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Ngày"));

        txt_NgayCong.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_NgayCong, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txt_NgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        buttonGroup2.add(rdo_Nghi);
        rdo_Nghi.setText("Nghỉ làm");
        rdo_Nghi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NghiActionPerformed(evt);
            }
        });

        tbl_ChamCong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Điểm Danh", "Mã Nhân viên", "Ngày Làm Việc", "Điểm danh"
            }
        ));
        tbl_ChamCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ChamCongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_ChamCong);

        txt_MaNV.setLabelText("Mã nhân viên");
        txt_MaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaNVActionPerformed(evt);
            }
        });

        btn_DiemDanh.setBackground(new java.awt.Color(255, 51, 51));
        btn_DiemDanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/DiemDanh.png"))); // NOI18N
        btn_DiemDanh.setText("Điểm Danh");
        btn_DiemDanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DiemDanhActionPerformed(evt);
            }
        });

        btn_XapXepDD.setBackground(new java.awt.Color(51, 255, 204));
        btn_XapXepDD.setText("Xắp xếp");
        btn_XapXepDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XapXepDDActionPerformed(evt);
            }
        });

        cbo_Thang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_Nam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_Ngay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_XoaDD.setBackground(new java.awt.Color(51, 255, 204));
        btn_XoaDD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btn_XoaDD.setText("Xóa");
        btn_XoaDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaDDActionPerformed(evt);
            }
        });

        btn_Next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_next_24px.png"))); // NOI18N

        btn_Prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_previous_24px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(rdo_Lam)
                        .addGap(18, 18, 18)
                        .addComponent(rdo_Nghi)
                        .addGap(276, 276, 276)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(cbo_Thang, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbo_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_XapXepDD, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_DiemDanh))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(cbo_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_Prev, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btn_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_XoaDD)
                .addGap(192, 192, 192))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XapXepDD, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XoaDD))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbo_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_Thang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdo_Lam)
                                    .addComponent(rdo_Nghi)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btn_DiemDanh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_Prev, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Next, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        cbo_Ngay.getAccessibleContext().setAccessibleName("");

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ngày bắt đầu", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        txt_NgayBatDau.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Ten, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_DT, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ThoiHan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(204, 204, 204)
                        .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ResetNV, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(txt_Ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(73, 73, 73))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(txt_DT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(24, 24, 24)
                                        .addComponent(txt_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lb_HinhAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(txt_ThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_ThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_XoaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_ResetNV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_SuaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã nhân viên", "Họ Tên", "Ngày sinh", "Giới tính", "Điện Thoại", "CCCD", "Ngày bắt đầu", "Ngày kết thúc", "Thời Hạn", "Ảnh"
            }
        ));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1295, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdo_NghiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NghiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NghiActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
        i = tbl_NhanVien.getSelectedRow();
        txt_Ma.setText(tbl_NhanVien.getValueAt(i, 1).toString());
        txt_Ten.setText(tbl_NhanVien.getValueAt(i, 2).toString());
        Date ngaySinh = (Date) tbl_NhanVien.getValueAt(i, 3);
        txt_NgSinh.setDate(ngaySinh);
        if (tbl_NhanVien.getValueAt(i, 4).toString().equalsIgnoreCase("Nam")) {
            rdo_Nam.setSelected(true);
        } else {
            rdo_Nu.setSelected(true);
        }
        txt_DT.setText(tbl_NhanVien.getValueAt(i, 5).toString());
        txt_CCCD.setText(tbl_NhanVien.getValueAt(i, 6).toString());
        Date ngayBD = (Date) tbl_NhanVien.getValueAt(i, 7);
        txt_NgayBatDau.setDate(ngayBD);
        Date ngayKT = (Date) tbl_NhanVien.getValueAt(i, 8);
        txt_NgayKT.setDate(ngayKT);
        int thoiHan = (int) tbl_NhanVien.getValueAt(i, 9);
        txt_ThoiHan.setText(String.valueOf(thoiHan));
        String imagePath = tbl_NhanVien.getValueAt(i, 10).toString();
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lb_HinhAnh.setIcon(imageIcon);
    }//GEN-LAST:event_tbl_NhanVienMouseClicked

    private void txt_MaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaNVActionPerformed

    private void tbl_ChamCongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ChamCongMouseClicked
        // TODO add your handling code here:
        i = tbl_ChamCong.getSelectedRow();
        txt_MaNV.setText(tbl_ChamCong.getValueAt(i, 2).toString());
        Date NgayCong = (Date) tbl_ChamCong.getValueAt(i, 3);
        txt_NgayCong.setDate(NgayCong);
        if (tbl_ChamCong.getValueAt(i, 4).toString().equalsIgnoreCase("Đi làm")) {
            rdo_Lam.setSelected(true);
        } else {
            rdo_Nghi.setSelected(true);
        }
    }//GEN-LAST:event_tbl_ChamCongMouseClicked

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void btn_XoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaNVActionPerformed
        // TODO add your handling code here:
        i = tbl_NhanVien.getSelectedRow();
        if (i != -1) {
            String maNH = tbl_NhanVien.getValueAt(i, 1).toString();
            if (rpNV.delete(maNH) > 0) {
                //                JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                mesF.showMessage("success", "Xóa thành công");
                this.fillTable(rpNV.getAllNhanVien());
            } else {
                mesF.showMessage("error", "Xóa thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chua chon dung");
        }
        mesF.showMessage("message", "bạn có chắc chắn muốn xóa ko?");
    }//GEN-LAST:event_btn_XoaNVActionPerformed

    private void btn_ThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemNVActionPerformed
        // TODO add your handling code here:
//        if (this.readFrom() != null) {
//          Model_NhanVien nv = rp.checkTrung(txt_Ma.getText(), txt_DT.getText(), txt_CCCD.getText());
//    if (nv != null) {
//        JOptionPane.showMessageDialog(this, "Trùng mã, số điện thoại hoặc CCCD");
//    } else {
//        rp.add(readFrom());
//        JOptionPane.showMessageDialog(this, "Thêm thành công ");
//        fillTable(rp.getAll());
//    }

//        if (this.readFrom() != null) {
//            if (rpNV.add(this.readFrom()) > 0) {
//                //                JOptionPane.showMessageDialog(this,"Thêm thành công");
//                mesF.showMessage("success", "Theem thanh cong");
//                this.fillTable(rpNV.getAllNhanVien());
//            } else {
//                //                JOptionPane.showMessageDialog(this, "Thêm thất bại");
//                mesF.showMessage("error", "Thêm thất bại");
//            }
    Model_NhanVien nv = this.readFrom();
    if (nv != null) {
        // Kiểm tra trùng lặp
        Model_NhanVien nvCheck = rpNV.checkTrung(nv.getMaNV(), nv.getDienThoai(), nv.getCCCD());
        if (nvCheck != null) {
            mesF.showMessage("error","Không được trùng Mã nhân viên, Điện thoai,CCCD");
        } else {
            // Thêm nhân viên mới
            if (rpNV.add(nv) > 0) {
                mesF.showMessage("success", "Thêm thành công");
                this.fillTable(rpNV.getAllNhanVien());
            } else {
                mesF.showMessage("error", "Thêm thất bại");
            }
        }
}
        //    if(this.getFormData()!= null){
            //            if (rp.add(this.getFormData()) > 0) {
                //                JOptionPane.showMessageDialog(this,"Thêm thành công");
                //                this.fillTable(rp.getAll());
                //            }else{
                //                JOptionPane.showMessageDialog(this,"Thêm thất bại");
                //            }
            //        }
    }//GEN-LAST:event_btn_ThemNVActionPerformed

    private void btn_SuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaNVActionPerformed
        // TODO add your handling code here:
        i = tbl_NhanVien.getSelectedRow();
        if (this.readFrom() != null) {
            String maKH = tbl_NhanVien.getValueAt(i, 1).toString();
            if (rpNV.update(maKH, this.readFrom()) > 0) {
                //                JOptionPane.showMessageDialog(this, "Sửa thành công;");
                mesF.showMessage("success", "Sửa thành công");
                this.fillTable(rpNV.getAllNhanVien());
            } else {
                //                JOptionPane.showMessageDialog(this, "Sửa thất bại");
                mesF.showMessage("error", "Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btn_SuaNVActionPerformed

    private void btn_ResetNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetNVActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_ResetNVActionPerformed

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

    private void btn_DiemDanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DiemDanhActionPerformed
        // TODO add your handling code here:
        Model_DiemDanh diemDanh = this.readFrom2();
    if (diemDanh != null) {
        Date ngayLamViecDate = diemDanh.getNgayLamViec();
        LocalDate ngayLamViec = ngayLamViecDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ngayHienTai = LocalDate.now();

        if (ngayLamViec.isAfter(ngayHienTai)) {
            // Hiển thị thông báo lỗi nếu ngày làm việc vượt quá ngày hiện tại
            mesF.showMessage("error", "Không thể điểm danh vì ngày lớn hơn ngày hiện tại.");
        } else if (rpDD.checkTrungNgayLamViec(diemDanh.getNgayLamViec())) {
            // Hiển thị thông báo lỗi nếu ngày đã có nhân viên được điểm danh
            mesF.showMessage("error", "Ngày này đã có nhân viên được điểm danh.");
        } else {
            // Thêm bản ghi điểm danh mới nếu ngày chưa có nhân viên được điểm danh
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn điểm danh không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (rpDD.add(diemDanh) > 0) {
                    mesF.showMessage("success", "Điểm Danh Thành Công");
                    this.fillTable2(rpDD.getALLDiemDanh());
                } else {
                    mesF.showMessage("error", "Điểm danh thất bại, kiểm tra lại");
                }
            }
        }
    }
    }//GEN-LAST:event_btn_DiemDanhActionPerformed

    private void btn_XapXepDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XapXepDDActionPerformed
        // TODO add your handling code here:
        // Gọi phương thức xapXep để lấy danh sách đã được sắp xếp
    ArrayList<Model_DiemDanh> sortedList = rpDD.xapXep();
    
    // Cập nhật bảng hiển thị với dữ liệu đã được sắp xếp
    fillTable2(sortedList);
    }//GEN-LAST:event_btn_XapXepDDActionPerformed

    private void btn_XoaDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaDDActionPerformed
        // TODO add your handling code here:
        int i = tbl_ChamCong.getSelectedRow();
if (i != -1) {
    String maNV = tbl_ChamCong.getValueAt(i, 2).toString();
    String ngayLamViecStr = tbl_ChamCong.getValueAt(i, 3).toString();
    Date ngayLamViec = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    try {
        ngayLamViec = dateFormat.parse(ngayLamViecStr);
        System.out.println("Ngay Lam Viec: " + ngayLamViec);
    } catch (ParseException e) {
        mesF.showMessage("error", "Lỗi khi lấy giá trị ngày làm việc: " + e.getMessage());
        return;
    }

    if (rpDD.deleteDiemDanh(maNV, ngayLamViec) > 0) {
        mesF.showMessage("success", "Xóa thành công");
        this.fillTable2(rpDD.getALLDiemDanh());
    } else {
        mesF.showMessage("error", "Xóa thất bại");
    }
} else {
    mesF.showMessage("error", "Chưa chọn dòng nào");
}

    }//GEN-LAST:event_btn_XoaDDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_DiemDanh;
    private view.component.button.MyButton btn_Next;
    private view.component.button.MyButton btn_Prev;
    private view.component.button.Button btn_ResetNV;
    private view.component.button.Button btn_SuaNV;
    private view.component.button.Button btn_ThemNV;
    private javax.swing.JButton btn_XapXepDD;
    private javax.swing.JButton btn_XoaDD;
    private view.component.button.Button btn_XoaNV;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbo_Nam;
    private javax.swing.JComboBox<String> cbo_Ngay;
    private javax.swing.JComboBox<String> cbo_Thang;
    private view.panel.DateChooser chonNgayBD;
    private view.panel.DateChooser chonNgayKT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lb_HinhAnh;
    private javax.swing.JRadioButton rdo_Lam;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nghi;
    private javax.swing.JRadioButton rdo_Nu;
    private view.component.table.Table tbl_ChamCong;
    private view.component.table.Table tbl_NhanVien;
    private view.component.textfield.TextField txt_CCCD;
    private view.component.textfield.TextField txt_DT;
    private view.component.textfield.TextField txt_Ma;
    private view.component.textfield.TextField txt_MaNV;
    private com.toedter.calendar.JDateChooser txt_NgSinh;
    private com.toedter.calendar.JDateChooser txt_NgayBatDau;
    private com.toedter.calendar.JDateChooser txt_NgayCong;
    private com.toedter.calendar.JDateChooser txt_NgayKT;
    private view.component.textfield.TextField txt_Ten;
    private view.component.textfield.TextField txt_ThoiHan;
    private view.component.textfield.TextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables
}
