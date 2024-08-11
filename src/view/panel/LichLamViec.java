/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.panel;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.NhanVien.Model_LichLamViec;
import repository.NhanVien.repoLichLamViec;
import view.component.message.MessageFrame;
import view.jframe.ViewSignIn;

/**
 *
 * @author BOSS
 */
public class LichLamViec extends javax.swing.JPanel {

    private repoLichLamViec rp = new repoLichLamViec();
    private DefaultTableModel mollich;
    private MessageFrame mesF = new MessageFrame();
    private int i = -1;
    private MessageFrame message = new MessageFrame();
    private int ngay = -1;
    private int thang = -1;
    private int nam = -1;
    private int trangThai = -1;
    private String timKiem = "";
    private int role = -1;

    /**
     * Creates new form LichLamViec
     */
    public LichLamViec() {
        initComponents();
        mollich = (DefaultTableModel) tbl_Lich.getModel(); // Khởi tạo DefaultTableModel sau khi khởi tạo bảng
        loadTableData();
        textSearch();
        updateTotalTrangThai();
        tbl_Lich.fixTable(jScrollPane1);
        checkNV();
    }

    private void Reset() {
        txt_Ma.setText("NV");
        txt_TenNhanVien.setText("");
        txt_NgayLamViec.setDate(null);
        txt_CongViec.setText("Công việc hôm nay cần hoàn thành: \n"
                + "1.\n"
                + "2.\n"
                + "3.\n"
                + "4.\n"
                + "5.\n");
        txt_GhiChu.setText("1.Lời chủ trọ: \n"
                + "2.Lời nhân viên: \n");

    }

    private void checkNV() {
        role = ViewSignIn.role;
        if (role == 1) {
            btn_ThemLich.setEnabled(false);
            btn_Xoa.setEnabled(false);
            btn_Sua.setEnabled(false);
            btn_Load.setEnabled(false);
            btn_ResetLich.setEnabled(false);
            txt_Ma.setBackground(new Color(204, 204, 204));
            txt_Ma.setEditable(false);
            txt_TenNhanVien.setBackground(new Color(204, 204, 204));
            txt_TenNhanVien.setEditable(false);
            txt_CongViec.setEditable(false);
            txt_NgayLamViec.setEnabled(false);
            txt_NgayLamViec.setForeground(new Color(0, 0, 255));
            txt_NgayLamViec.setFont(new Font("sansserif", 1, 14));
            txt_NgayLamViec.setBackground(new Color(204, 204, 204));
            txt_CongViec.setBackground(new Color(204, 204, 204));
        }
    }

    private void loadTableData() {
        ArrayList<Model_LichLamViec> listLich = rp.getALLLich();
        fillTable(listLich);
    }

    private void fillTable(ArrayList<Model_LichLamViec> list) {
        mollich.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        list.forEach(s -> mollich.addRow(new Object[]{
            index.getAndIncrement(), s.getIdLich(), s.getNgayLam(), s.getMaNV(), s.getTenNV(), s.getCongViec(),
            s.getGhiChu(), s.getTrangThai() == 0 ? "Chưa hoàn thành" : "Đã hoàn thành"
        }));
    }

    public Model_LichLamViec readFrom() {
        // Kiểm tra không được để trống các trường
        if (txt_Ma.getText().trim().isEmpty()
                || txt_CongViec.getText().trim().isEmpty()
                || txt_NgayLamViec.getDate() == null) {
            mesF.showMessage("error", "Vui lòng không để trống các trường trừ ghi chú");
            return null; // Hoặc thực hiện các hành động khác khi có lỗi
        }
        Model_LichLamViec Lich = new Model_LichLamViec();
        Lich.setMaNV(txt_Ma.getText());
        Lich.setCongViec(txt_CongViec.getText());
        Lich.setGhiChu(txt_GhiChu.getText());
        Lich.setNgayLam(txt_NgayLamViec.getDate());
        if (rdo_ChuaHoanThanh.isSelected()) {
            Lich.setTrangThai(0);// chưa hoàn thành
        } else {
            Lich.setTrangThai(1); // Đã hoàn thành
        }
        return Lich;
    }

    private void textSearch() {
        txt_TimKiemLich.getDocument().addDocumentListener(new DocumentListener() {
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
        timKiem = txt_TimKiemLich.getText().trim();
        ArrayList<Model_LichLamViec> lichLamViecList = rp.timKiem(timKiem, ngay, thang, nam, trangThai);
        fillTable(lichLamViecList);
        System.out.println("ngay: " + ngay);
        System.out.println("thang: " + thang);
        System.out.println("nam: " + nam);
    }

    private void updateTotalTrangThai() {
        int totalChuaHoanThanh = rp.getTotalChuaHoanThanh();
        int totalDaHoanThanh = rp.getTotalDaHoanThanh();
        jb_ChuaHoanThanh.setText("Chưa hoàn thành:    " + totalChuaHoanThanh);
        jb_DaHoanThanh.setText("Đã hoàn thành:     " + totalDaHoanThanh);
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
        jPanel1 = new javax.swing.JPanel();
        txt_Ma = new view.component.textfield.TextField();
        txt_TenNhanVien = new view.component.textfield.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_GhiChu = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btn_ThemLich = new view.component.button.MyButton();
        btn_Sua = new view.component.button.MyButton();
        btn_Xoa = new view.component.button.MyButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_CongViec = new javax.swing.JTextArea();
        txt_NgayLamViec = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        rdo_ChuaHoanThanh = new view.component.radiobutton.RadioButtonCustom();
        rdo_DaHoanThanh = new view.component.radiobutton.RadioButtonCustom();
        btn_CapNhat = new view.component.button.MyButton();
        jLabel3 = new javax.swing.JLabel();
        jb_DaHoanThanh = new javax.swing.JLabel();
        jb_ChuaHoanThanh = new javax.swing.JLabel();
        btn_Load = new view.component.button.MyButton();
        btn_HomNay = new view.component.button.MyButton();
        btn_ResetLich = new view.component.button.MyButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Lich = new view.component.table.Table();
        comBo_Thang = new view.component.combobox.Combobox();
        comBo_Nam = new view.component.combobox.Combobox();
        txt_TimKiemLich = new view.component.textfield.TextField();
        combo_Ngay = new view.component.combobox.Combobox();
        combo_TrangThai = new view.component.combobox.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Lịch làm việc"));

        txt_Ma.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_Ma.setLabelText("Mã Nhân Viên");
        txt_Ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MaActionPerformed(evt);
            }
        });

        txt_TenNhanVien.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_TenNhanVien.setLabelText("Tên Nhân Viên");
        txt_TenNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TenNhanVienActionPerformed(evt);
            }
        });

        txt_GhiChu.setColumns(20);
        txt_GhiChu.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_GhiChu.setForeground(new java.awt.Color(255, 0, 0));
        txt_GhiChu.setRows(5);
        jScrollPane2.setViewportView(txt_GhiChu);

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel2.setText("Ghi chú:");

        btn_ThemLich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_plus_24px.png"))); // NOI18N
        btn_ThemLich.setText("Thêm");
        btn_ThemLich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemLichActionPerformed(evt);
            }
        });

        btn_Sua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_update_24px.png"))); // NOI18N
        btn_Sua.setText("Sửa");
        btn_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaActionPerformed(evt);
            }
        });

        btn_Xoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_cancel_24px.png"))); // NOI18N
        btn_Xoa.setText("Xóa");
        btn_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaActionPerformed(evt);
            }
        });

        txt_CongViec.setColumns(20);
        txt_CongViec.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txt_CongViec.setForeground(new java.awt.Color(0, 0, 255));
        txt_CongViec.setRows(5);
        jScrollPane4.setViewportView(txt_CongViec);

        txt_NgayLamViec.setBackground(new java.awt.Color(255, 255, 255));
        txt_NgayLamViec.setDateFormatString("dd-MM-yyyy");
        txt_NgayLamViec.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel1.setText("Công việc ngày:");

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật vào cuối ngày", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        buttonGroup1.add(rdo_ChuaHoanThanh);
        rdo_ChuaHoanThanh.setText("Chưa hoàn thành");
        rdo_ChuaHoanThanh.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        rdo_ChuaHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_ChuaHoanThanhActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdo_DaHoanThanh);
        rdo_DaHoanThanh.setText("Đã hoàn thành");
        rdo_DaHoanThanh.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        rdo_DaHoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_DaHoanThanhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdo_ChuaHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdo_DaHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_ChuaHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdo_DaHoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_CapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/upDate.png"))); // NOI18N
        btn_CapNhat.setText("Cập nhật");
        btn_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CapNhatActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/DiemDanh.png"))); // NOI18N

        jb_DaHoanThanh.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jb_DaHoanThanh.setText("Đã hoàn thành:");

        jb_ChuaHoanThanh.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jb_ChuaHoanThanh.setText("Chưa hoàn thành:");

        btn_Load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_synchronize_24px.png"))); // NOI18N
        btn_Load.setText("Load");
        btn_Load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoadActionPerformed(evt);
            }
        });

        btn_HomNay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/today.png"))); // NOI18N
        btn_HomNay.setText("Hôm nay");
        btn_HomNay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomNayActionPerformed(evt);
            }
        });

        btn_ResetLich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset_48px.png"))); // NOI18N
        btn_ResetLich.setText("Reset");
        btn_ResetLich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetLichActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 255));
        jLabel4.setText("*Tên nhân viên không cần nhập");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_HomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_ThemLich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_Load, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_ResetLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(35, 35, 35))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_TenNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_Ma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGap(47, 47, 47)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jb_DaHoanThanh)
                                    .addComponent(jb_ChuaHoanThanh)))
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_NgayLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_Ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_NgayLamViec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 1, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_TenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_ThemLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Load, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_HomNay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_ResetLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jb_DaHoanThanh)
                                    .addGap(18, 18, 18)
                                    .addComponent(jb_ChuaHoanThanh)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btn_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        tbl_Lich.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "ID lịch Làm việc", "Ngày", "Mã Nhân Viên", "Tên Nhân Viên", "Công Việc", "Ghi chú", "Trạng thái"
            }
        ));
        tbl_Lich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_LichMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Lich);
        if (tbl_Lich.getColumnModel().getColumnCount() > 0) {
            tbl_Lich.getColumnModel().getColumn(0).setResizable(false);
            tbl_Lich.getColumnModel().getColumn(4).setResizable(false);
            tbl_Lich.getColumnModel().getColumn(6).setResizable(false);
            tbl_Lich.getColumnModel().getColumn(7).setResizable(false);
        }

        comBo_Thang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "  ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        comBo_Thang.setLabeText("Tháng");
        comBo_Thang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBo_ThangActionPerformed(evt);
            }
        });

        comBo_Nam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "  ", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028" }));
        comBo_Nam.setLabeText("Năm");
        comBo_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comBo_NamActionPerformed(evt);
            }
        });

        txt_TimKiemLich.setLabelText("Tìm kiếm mã, tên");

        combo_Ngay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "  ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        combo_Ngay.setLabeText("Ngày");
        combo_Ngay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_NgayActionPerformed(evt);
            }
        });

        combo_TrangThai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Đã hoàn thành", "Chưa hoàn thành" }));
        combo_TrangThai.setSelectedIndex(-1);
        combo_TrangThai.setLabeText("Trạng thái");
        combo_TrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_TrangThaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_TimKiemLich, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(combo_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comBo_Thang, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comBo_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(combo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comBo_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_TimKiemLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_Ngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_TrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comBo_Thang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaActionPerformed
        // TODO add your handling code here:
        int i = tbl_Lich.getSelectedRow();
        if (i >= 0) { // Kiểm tra xem có hàng nào được chọn không
            // Hiển thị hộp thoại xác nhận
            message.showMessage("message", "Bạn có chắc chắn muốn sửa không? ");
            message.setOnOkClicked(() -> {
                // Đọc dữ liệu đã được sửa từ form
                Model_LichLamViec lich = this.readFrom();
                if (lich != null) {
                    int id = Integer.parseInt(tbl_Lich.getValueAt(i, 1).toString());
                    if (rp.update(id, lich) > 0) {
                        mesF.showMessage("success", "Sửa thành công");
                        this.fillTable(rp.getALLLich());
                        updateTotalTrangThai();
                    } else {
                        mesF.showMessage("error", "Sửa thất bại");
                    }
                } else {
                    mesF.showMessage("error", "Sửa thất bại do dữ liệu các trường đang bị bỏ trống?"
                            + "  Chỉ ghi chú mới được bỏ trống!");
                }
            });
        } else {
            mesF.showMessage("error", "Vui lòng chọn một hàng để sửa");
        }


    }//GEN-LAST:event_btn_SuaActionPerformed

    private void btn_ThemLichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemLichActionPerformed
        Model_LichLamViec lich = this.readFrom();
        if (lich != null) {
            if (!lich.getMaNV().toUpperCase().startsWith("NV")) {
                mesF.showMessage("error", "Mã nhân viên phải bắt đầu bằng 'NV'!");
                return;
            }
            if (!rp.checkMaNVTonTai(lich.getMaNV())) {
                mesF.showMessage("error", "Mã nhân viên không tồn tại!");
                return;
            }
            // Hiển thị hộp thoại xác nhận
            message.showMessage("message", "Bạn có muốn thêm lịch làm cho nhân viên không?");
            message.setOnOkClicked(() -> {
                // Chuyển đổi java.util.Date thành java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(lich.getNgayLam().getTime());
                if (rp.checkTrungLich(lich.getMaNV(), sqlDate) != null) {
                    mesF.showMessage("error", "Nhân viên đã có lịch làm việc trong ngày này");
                } else {
                    if (rp.add(lich) > 0) {
                        mesF.showMessage("success", "Thêm thành công");
                        this.fillTable(rp.getALLLich());
                        updateTotalTrangThai();
                    } else {
                        mesF.showMessage("error", "Thêm thất bại!");
                    }
                }
            });
        }


    }//GEN-LAST:event_btn_ThemLichActionPerformed

    private void tbl_LichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_LichMouseClicked
        // TODO add your handling code here:
        int i = tbl_Lich.getSelectedRow(); // Sửa lỗi: Đặt `i` trước khi sử dụng
        if (i >= 0) { // Kiểm tra nếu có hàng được chọn
            txt_Ma.setText(tbl_Lich.getValueAt(i, 3).toString());
            txt_CongViec.setText(tbl_Lich.getValueAt(i, 5).toString());
            txt_GhiChu.setText(tbl_Lich.getValueAt(i, 6).toString());
            txt_TenNhanVien.setText(tbl_Lich.getValueAt(i, 4).toString());
            Date ngayLamViec = (Date) tbl_Lich.getValueAt(i, 2);
            txt_NgayLamViec.setDate(ngayLamViec);
            if (tbl_Lich.getValueAt(i, 7).toString().equalsIgnoreCase("Chưa hoàn thành")) {
                rdo_ChuaHoanThanh.setSelected(true);
            } else {
                rdo_DaHoanThanh.setSelected(true);
            }
        }
    }//GEN-LAST:event_tbl_LichMouseClicked

    private void rdo_ChuaHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_ChuaHoanThanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_ChuaHoanThanhActionPerformed

    private void rdo_DaHoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_DaHoanThanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_DaHoanThanhActionPerformed

    private void comBo_ThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBo_ThangActionPerformed
        String text = comBo_Thang.getSelectedItem() + "";
        if (text.trim().isEmpty()) {
            thang = -1;
        } else {
            thang = Integer.parseInt(text);
        }
        timKiem();
    }//GEN-LAST:event_comBo_ThangActionPerformed

    private void txt_MaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MaActionPerformed

    private void btn_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaActionPerformed
        // TODO add your handling code here:
        // Lấy hàng được chọn
        int i = tbl_Lich.getSelectedRow();
        if (i >= 0) {
            // Hiển thị hộp thoại xác nhận
            message.showMessage("message", "Bạn có chắc chắn muốn xóa lịch làm việc này không ");
            message.setOnOkClicked(() -> {
                // Lấy ID từ hàng được chọn
                int id = (int) tbl_Lich.getValueAt(i, 1); // ID là cột thứ hai
                int result = rp.delete(id);
                if (result > 0) {
                    mesF.showMessage("success", "Bạn đã xóa thành công");
                    // Cập nhật lại bảng sau khi xóa
                    fillTable(rp.getALLLich());
                    updateTotalTrangThai();
                } else {
                    mesF.showMessage("error", "Xóa thất bại");
                }
            });
        } else {
            mesF.showMessage("error", "Vui lòng chọn bản ghi để xóa");
        }
    }//GEN-LAST:event_btn_XoaActionPerformed

    private void btn_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CapNhatActionPerformed
        // TODO add your handling code here:
        // Lấy hàng được chọn
        int i = tbl_Lich.getSelectedRow();

        if (i == -1) {
            mesF.showMessage("error", "Vui lòng chọn một hàng để cập nhật.");
            return;
        }

        // Đọc dữ liệu đã được sửa từ form
        Model_LichLamViec lichLamViec = this.readFrom();
        if (lichLamViec != null) {
            // Hiển thị hộp thoại xác nhận
            message.showMessage("message", "Bạn có chắc chắn muốn cập nhật không?");
            message.setOnOkClicked(() -> {
                // Lấy ID từ hàng được chọn
                int id = Integer.parseInt(tbl_Lich.getValueAt(i, 1).toString());
                System.out.println("row: " + i);
                int trangThai = lichLamViec.getTrangThai();
                String ghiChu = lichLamViec.getGhiChu(); // Đọc ghi chú từ model

                if (rp.updateTrangThai(id, trangThai, ghiChu) > 0) { // Cập nhật với ghi chú
                    mesF.showMessage("success", "Bạn đã cập nhật thành công");
                    // Cập nhật lại bảng sau khi cập nhật
                    this.fillTable(rp.getALLLich());
                    updateTotalTrangThai();
                } else {
                    mesF.showMessage("error", "Cập nhật thất bại");
                }
            });
        } else {
            mesF.showMessage("error", "Vui lòng chọn nhân viên cần cập nhật");
        }


    }//GEN-LAST:event_btn_CapNhatActionPerformed

    private void combo_NgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_NgayActionPerformed
        String text = combo_Ngay.getSelectedItem() + "";
        if (text.trim().isEmpty()) {
            ngay = -1;
        } else {
            ngay = Integer.parseInt(text);
        }
        timKiem();
    }//GEN-LAST:event_combo_NgayActionPerformed

    private void comBo_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comBo_NamActionPerformed
        String text = comBo_Nam.getSelectedItem() + "";
        if (text.trim().isEmpty()) {
            nam = -1;
        } else {
            nam = Integer.parseInt(text);
        }
        timKiem();
    }//GEN-LAST:event_comBo_NamActionPerformed

    private void combo_TrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_TrangThaiActionPerformed
        int index = combo_TrangThai.getSelectedIndex();
        if (index == -1) {
            trangThai = -1;
        } else {
            if (index == 0) {
                trangThai = 1;
            } else {
                trangThai = 0;
            }
        }
        timKiem();
    }//GEN-LAST:event_combo_TrangThaiActionPerformed

    private void txt_TenNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TenNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TenNhanVienActionPerformed

    private void btn_LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoadActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_btn_LoadActionPerformed

    private void btn_HomNayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomNayActionPerformed
        // TODO add your handling code here:
        ArrayList<Model_LichLamViec> lichLamViecList = rp.XapXepLichHomNay();
        fillTable(lichLamViecList);
    }//GEN-LAST:event_btn_HomNayActionPerformed

    private void btn_ResetLichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetLichActionPerformed
        // TODO add your handling code here:
        Reset();
    }//GEN-LAST:event_btn_ResetLichActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.component.button.MyButton btn_CapNhat;
    private view.component.button.MyButton btn_HomNay;
    private view.component.button.MyButton btn_Load;
    private view.component.button.MyButton btn_ResetLich;
    private view.component.button.MyButton btn_Sua;
    private view.component.button.MyButton btn_ThemLich;
    private view.component.button.MyButton btn_Xoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private view.component.combobox.Combobox comBo_Nam;
    private view.component.combobox.Combobox comBo_Thang;
    private view.component.combobox.Combobox combo_Ngay;
    private view.component.combobox.Combobox combo_TrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel jb_ChuaHoanThanh;
    private javax.swing.JLabel jb_DaHoanThanh;
    private view.component.radiobutton.RadioButtonCustom rdo_ChuaHoanThanh;
    private view.component.radiobutton.RadioButtonCustom rdo_DaHoanThanh;
    private view.component.table.Table tbl_Lich;
    private javax.swing.JTextArea txt_CongViec;
    private javax.swing.JTextArea txt_GhiChu;
    private view.component.textfield.TextField txt_Ma;
    private com.toedter.calendar.JDateChooser txt_NgayLamViec;
    private view.component.textfield.TextField txt_TenNhanVien;
    private view.component.textfield.TextField txt_TimKiemLich;
    // End of variables declaration//GEN-END:variables
}
