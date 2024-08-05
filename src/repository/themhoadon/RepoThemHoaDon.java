package repository.themhoadon;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;

public class RepoThemHoaDon {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private CallableStatement call = null;
    private ResultSet rs = null;

    public RepoThemHoaDon() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> layMaPT() {
        ArrayList<Object> list = new ArrayList<>();
        sql = "SELECT MaPhong FROM PhongTro";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public boolean addHoaDon(ArrayList<Object> arr, String maNV, double tienDien , double tienNuoc, double tienDV) {
        sql = "{ CALL InsertHoaDon(?, ?, ?, ?, ?) }";
        try {
            ps = conn.prepareStatement(sql);
            for (Object object : arr) {
                ps.setObject(1, object);
                ps.setObject(2, maNV);
                ps.setObject(3, tienDien);
                ps.setObject(4, tienNuoc);
                ps.setObject(5, tienDV);
                if (ps.executeUpdate() != 1) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public String timMaNV(String tenDangNhap) {
        String maNV = null;
        sql = "SELECT MaNhanVien FROM dbo.DangNhap WHERE TenDangNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            rs = ps.executeQuery();
            if (rs.next()) {
                maNV = rs.getString("MaNhanVien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNV;
    }

    public boolean checkNgayTaoHoaDon(String ngayBD, String ngayKT) {
        sql = "{ Call CheckExistHoaDon (?, ?) }";
        try {
            ps = conn.prepareCall(sql);
            ps.setString(1, ngayBD);
            ps.setString(2, ngayKT);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                return rs.getInt("Exist") > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
