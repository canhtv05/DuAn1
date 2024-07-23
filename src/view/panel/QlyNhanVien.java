/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;
import model.NhanVien.Model_ChamCong;
import java.util.Date;
import model.NhanVien.Model_NhanVien;
//import QuanLyNhanVien.repoChamCong;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import repository.NhanVien.repoNhanVien;
import java.util.List;

/**
 *
 * @author CanhPC
 */
public class QlyNhanVien extends javax.swing.JPanel {
    private DefaultTableModel mol = new DefaultTableModel();
    private repoNhanVien rp = new repoNhanVien();
    //private repoChamCong rp2  = new repoChamCong();
    private List<Model_NhanVien> list  = new ArrayList<>();
    private List<Model_ChamCong> list1 = new ArrayList<>();
    private String path = "D/....";
    private int i = -1;
    

    /**
     * Creates new form QlyNhanVien
     */
    public QlyNhanVien() {
        initComponents();
        mol = (DefaultTableModel) tbl_NhanVien.getModel();
//        mol = (DefaultTableModel) tbl_ChamCong.getModel();
        this.fillTable(rp.getAll());
        //this.fillTable2(rp2.getAll2());
        init();
    }
    public void init(){
        lb_HinhAnh.setText("");
    }
     private void fillTable(ArrayList<Model_NhanVien> list){
           mol.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(s -> mol.addRow(new Object[]{
            index.getAndIncrement(), s.getMaNV(), s.getHoTen(), s.getNgaySinh(),s.getGioiTinh()== 0?"Nam":"Nữ", s.getDienThoai(),
            s.getCCCD(), s.getNgayBD(), s.getNgayKT(), s.getThoiHan(),s.getAnhNV()
        }));
    }
//     private void fillTable2(ArrayList<Model_ChamCong> list){
//           mol.setRowCount(0);
//        AtomicInteger index = new AtomicInteger(1);
//        list.forEach(s -> mol.addRow(new Object[]{
//            index.getAndIncrement(), s.getMaNV(), s.getNgayCong(),s.getDiemDanh()== 0?"Đi làm":"Không đi làm"
//        }));
//    }
     public Model_NhanVien readFrom(){
         Model_NhanVien nv = new Model_NhanVien();
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
         return nv;
     }
     public void clear(){
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
         lb_HinhAnh.setText("Thêm hình ảnh");
         
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
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lb_HinhAnh = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_Ma = new javax.swing.JTextField();
        txt_Ten = new javax.swing.JTextField();
        txt_ThoiHan = new javax.swing.JTextField();
        txt_DT = new javax.swing.JTextField();
        txt_CCCD = new javax.swing.JTextField();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        btn_ADD = new javax.swing.JButton();
        btn_timKiem = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txt_NgayBatDau = new com.toedter.calendar.JDateChooser();
        txt_NgayKT = new com.toedter.calendar.JDateChooser();
        txt_NgSinh = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_maNV = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        txt_NgayCong = new com.toedter.calendar.JDateChooser();
        rdo_DiLam = new javax.swing.JRadioButton();
        rdo_NghiLam = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_NhanVien = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ChamCong = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nhân Viên", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("sansserif", 1, 36))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(153, 204, 255));
        jLabel2.setFont(new java.awt.Font("sansserif", 3, 18)); // NOI18N

        lb_HinhAnh.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        lb_HinhAnh.setText("    Thêm ảnh");
        lb_HinhAnh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lb_HinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_HinhAnhMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel3.setText("Mã Nhân Viên:");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setText("Họ và tên:");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel5.setText("Ngày sinh:");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setText("Giới Tính:");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel7.setText("Điện thoại:");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setText("CCCD:");

        jLabel9.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel9.setText("Ngày bắt đầu:");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel10.setText("Ngày kết thúc:");

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel11.setText("Thời Hạn:");

        txt_Ma.setBackground(new java.awt.Color(204, 204, 255));
        txt_Ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaActionPerformed(evt);
            }
        });

        txt_Ten.setBackground(new java.awt.Color(204, 204, 255));
        txt_Ten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TenActionPerformed(evt);
            }
        });

        txt_ThoiHan.setBackground(new java.awt.Color(204, 204, 255));

        txt_DT.setBackground(new java.awt.Color(204, 204, 255));
        txt_DT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DTActionPerformed(evt);
            }
        });

        txt_CCCD.setBackground(new java.awt.Color(204, 204, 255));
        txt_CCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CCCDActionPerformed(evt);
            }
        });

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

        btn_ADD.setBackground(new java.awt.Color(102, 153, 255));
        btn_ADD.setText("Thêm");
        btn_ADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ADDActionPerformed(evt);
            }
        });

        btn_timKiem.setBackground(new java.awt.Color(102, 153, 255));
        btn_timKiem.setText("Sửa");
        btn_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_timKiemActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 153, 255));
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 153, 255));
        jButton4.setText("Tìm kiếm");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txt_NgayBatDau.setDateFormatString("dd-MM-yyyy");

        txt_NgayKT.setDateFormatString("dd-MM-yyyy");

        txt_NgSinh.setBackground(new java.awt.Color(204, 204, 204));
        txt_NgSinh.setDateFormatString("dd-MM-yyyy");

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setText("Reset");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(227, 227, 227)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdo_Nam)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_Nu))
                            .addComponent(txt_Ma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Ten, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel9))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addComponent(txt_DT, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(lb_HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_ThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(txt_NgSinh, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btn_timKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                        .addComponent(btn_ADD)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton1)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_ADD))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txt_Ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_NgSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btn_timKiem)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdo_Nam)
                            .addComponent(rdo_Nu)
                            .addComponent(jButton3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lb_HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jButton4)
                        .addGap(28, 28, 28)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_DT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_CCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txt_ThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(txt_NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chấm Công", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));

        jLabel13.setText("Mã nhân viên:");

        jLabel15.setText("Ngày công:");

        jLabel16.setText("Điểm danh:");

        txt_maNV.setBackground(new java.awt.Color(204, 204, 255));

        jButton5.setText("Thêm");

        jButton6.setText("Sửa");

        jButton7.setText("Tìm kiếm");

        rdo_DiLam.setText("Đi làm");

        rdo_NghiLam.setText("Nghỉ làm");
        rdo_NghiLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_NghiLamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jButton5)
                .addGap(32, 32, 32)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_NgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(8, 8, 8)
                        .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(115, 115, 115))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(rdo_DiLam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdo_NghiLam)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_maNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(rdo_DiLam)
                            .addComponent(rdo_NghiLam))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(txt_NgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(7, 7, 7))
        );

        tbl_NhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã NV", "Họ Tên", "Ngày sinh", "Giới tính", "Điện thoại", "CCCD", "Ngày bắt đầu", "Ngày kết thúc", "Thời hạn", "Ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, true
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
        jScrollPane1.setViewportView(tbl_NhanVien);
        if (tbl_NhanVien.getColumnModel().getColumnCount() > 0) {
            tbl_NhanVien.getColumnModel().getColumn(0).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(1).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(2).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(3).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(4).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(5).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(6).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(7).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(8).setResizable(false);
            tbl_NhanVien.getColumnModel().getColumn(9).setResizable(false);
        }

        tbl_ChamCong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã chấm công", "Mã nhân viên", "Điểm danh", "Ngày công"
            }
        ));
        tbl_ChamCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ChamCongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_ChamCong);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("LƯƠNG"));

        jLabel17.setText("Mã lương:");

        jLabel18.setText("Mã nhân viên:");

        jLabel19.setText("Lương cơ bản:");

        jLabel20.setText("Lương thưởng");

        jLabel21.setText("Lương phụ cấp:");

        jLabel22.setText("Tháng: ");

        jLabel23.setText("Năm");

        jLabel24.setText("Tổng lương:");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã lương", "Mã nhân viên", "Lương cơ bản", "Lương thưởng", "Lương phụ cấp", "Tháng", "Năm", "Tổng lương"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jButton9.setText("Thêm");

        jButton10.setText("Sửa");

        jButton11.setText("Tìm kiếm");

        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });

        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField17))
                            .addComponent(jLabel17)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton11)
                        .addGap(85, 85, 85))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10)
                            .addComponent(jButton9)
                            .addComponent(jButton11)
                            .addComponent(jLabel24)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void rdo_NuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NuActionPerformed

    private void rdo_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NamActionPerformed

    private void btn_ADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ADDActionPerformed
        // TODO add your handling code here:
        
     if(this.readFrom()!= null){
            if (rp.add(this.readFrom()) > 0) {
                JOptionPane.showMessageDialog(this,"Thêm thành công");
                this.fillTable(rp.getAll());
            }else{
                JOptionPane.showMessageDialog(this,"Thêm thất bại");
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
    }//GEN-LAST:event_btn_ADDActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void tbl_NhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_NhanVienMouseClicked
        // TODO add your handling code here:
//        showTable(tbl_NhanVien.getSelectedRow());
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

    private void lb_HinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_HinhAnhMouseClicked
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int ketQua = chooser.showOpenDialog(null);
        if (ketQua== JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
            ImageIcon icon = new ImageIcon(path);
            lb_HinhAnh.setIcon(icon);
        }
        
    }//GEN-LAST:event_lb_HinhAnhMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        i =tbl_NhanVien.getSelectedRow();
        if (i != -1){
            String maNH = tbl_NhanVien.getValueAt(i, 1).toString();
            if (rp.delete(maNH) >0 ) {
                JOptionPane.showMessageDialog(this, "Xoa thanh cong");
                this.fillTable(rp.getAll());
            }else{
                JOptionPane.showMessageDialog(this,"Xoa that bai");
            }
        }else{
            JOptionPane.showMessageDialog(this,"Chua chon dung");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_timKiemActionPerformed
        // TODO add your handling code here:
        i =tbl_NhanVien.getSelectedRow();
        if(this.readFrom() != null){
            String maKH = tbl_NhanVien.getValueAt(i, 1).toString();
            if(rp.update(maKH,this.readFrom()) > 0){
                JOptionPane.showMessageDialog(this,"Sửa thành công;");
                this.fillTable(rp.getAll());
            }else{
                JOptionPane.showMessageDialog(this,"Sửa thất bại");
            }
        }
    }//GEN-LAST:event_btn_timKiemActionPerformed

    private void tbl_ChamCongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ChamCongMouseClicked
        // TODO add your handling code here:
        i = tbl_ChamCong.getSelectedRow();
        txt_maNV.setText(tbl_ChamCong.getValueAt(i, 1).toString());
        Date NgayCong = (Date) tbl_ChamCong.getValueAt(i, 2);
        txt_NgayCong.setDate(NgayCong);
        if (tbl_ChamCong.getValueAt(i, 3).toString().equalsIgnoreCase("Đi làm")) {
            rdo_DiLam.setSelected(true);
        } else {
           rdo_NghiLam.setSelected(true);
        }
    }//GEN-LAST:event_tbl_ChamCongMouseClicked

    private void rdo_NghiLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_NghiLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_NghiLamActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_ADD;
    private javax.swing.JButton btn_timKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JLabel lb_HinhAnh;
    private javax.swing.JRadioButton rdo_DiLam;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_NghiLam;
    private javax.swing.JRadioButton rdo_Nu;
    private javax.swing.JTable tbl_ChamCong;
    private javax.swing.JTable tbl_NhanVien;
    private javax.swing.JTextField txt_CCCD;
    private javax.swing.JTextField txt_DT;
    private javax.swing.JTextField txt_Ma;
    private com.toedter.calendar.JDateChooser txt_NgSinh;
    private com.toedter.calendar.JDateChooser txt_NgayBatDau;
    private com.toedter.calendar.JDateChooser txt_NgayCong;
    private com.toedter.calendar.JDateChooser txt_NgayKT;
    private javax.swing.JTextField txt_Ten;
    private javax.swing.JTextField txt_ThoiHan;
    private javax.swing.JTextField txt_maNV;
    // End of variables declaration//GEN-END:variables
}
