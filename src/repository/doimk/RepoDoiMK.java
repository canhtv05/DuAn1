package repository.doimk;

import java.sql.*;
import dao.DBContext;
import java.util.ArrayList;
import model.doimk.ModelDoiMK;

public class RepoDoiMK {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoDoiMK() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelDoiMK> getAll(String timKiem) {
        ArrayList<ModelDoiMK> list = new ArrayList<>();
        sql = "SELECT TenDangNhap, MaNhanVien, MatKhau, VaiTro FROM dbo.DangNhap";

        if (timKiem != null && !timKiem.trim().isEmpty()) {
            sql += " WHERE MaNhanVien LIKE ?";
        }

        try {
            ps = conn.prepareStatement(sql);
            if(timKiem != null && !timKiem.trim().isEmpty()) ps.setString(1, "%" + timKiem + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                ModelDoiMK model = new ModelDoiMK(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean existTK(String tk) {
        sql = "SELECT COUNT(*) FROM dbo.DangNhap WHERE TenDangNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tk);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean add(ModelDoiMK model) {
        sql = "INSERT INTO dbo.DangNhap (TenDangNhap, MaNhanVien, MatKhau, VaiTro) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, model.getTk());

            if (model.getVaiTro() == 0) { // Chủ trọ
                ps.setNull(2, Types.VARCHAR);
            } else { // Nhân viên
                ps.setString(2, model.getMaNV());
            }

            ps.setString(3, model.getMk());
            ps.setInt(4, model.getVaiTro());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public ArrayList<model.NhanVien.Model_NhanVien> getCBBNV() {
//    ArrayList<model.NhanVien.Model_NhanVien> arrCBBNV = new ArrayList<>();
//     sql = "SELECT MaNhanVien FROM NhanVien";
//    try {
//        conn = DBContext.getConnection();
//        ps = conn.prepareStatement(sql);
//        rs = ps.executeQuery();
//        while (rs.next()) {
//            String manv;
//                manv= rs.getString(1).trim();
//                model.NhanVien.Model_NhanVien nv = new Model_NhanVien(manv);
//                arrCBBNV.add(nv);
//        }
//        return arrCBBNV;
//    } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//    }
//}
    public boolean xoaTaiKhoan(String tenDangNhap) {
        sql = "DELETE FROM dbo.DangNhap WHERE TenDangNhap = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean KiemTraMaNhanVien(String maNV) {
        sql = "SELECT COUNT(*) FROM dbo.DangNhap WHERE MaNhanVien = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maNV);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất 1 tài khoản với mã nhân viên này
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
