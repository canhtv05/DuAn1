package repository.tiendien;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.tiendien.ModelTienDien;

public class RepoTienDien {

    String sql = null;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public RepoTienDien() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTienDien> search(String maPhongTro) {
        ArrayList<ModelTienDien> list = new ArrayList<>();
        sql = "SELECT MaTD, MaHoaDon, MaPT, NgayBatDau, NgayKetThuc, ChiSoDau, ChiSoCuoi ,SoDien, GiaTien, ThanhTien FROM dbo.TienDien ";

        if (!maPhongTro.isEmpty()) {
            sql += "WHERE MaPT LIKE ?";
        }
        try {
            ps = conn.prepareStatement(sql);
            if (!maPhongTro.isEmpty()) {
                ps.setString(1, "%" + maPhongTro + "%");
            }
            ps.execute();

            rs = ps.getResultSet();
            while (rs.next()) {
                int maTD = rs.getInt(1);
                String maHD = rs.getString(2);
                String maPT = rs.getString(3);
                String ngayBD = rs.getString(4);
                String ngayKT = rs.getString(5);
                int chiSoDau = rs.getInt(6);
                int chiSoCuoi = rs.getInt(7);
                int soDien = rs.getInt(8);
                double giaTien = rs.getDouble(9);
                double thanhTien = rs.getDouble(10);

                ModelTienDien modelTienDien = new ModelTienDien(maTD, maHD, maPT, ngayBD, ngayKT, chiSoDau, chiSoCuoi,
                        soDien, giaTien, thanhTien);
                list.add(modelTienDien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ModelTienDien> searchAndPage(String maPhongTro, int page, int limit) {
        ArrayList<ModelTienDien> list = new ArrayList<>();
        sql = "SELECT MaTD, MaHoaDon, MaPT, NgayBatDau, NgayKetThuc, ChiSoDau, ChiSoCuoi ,SoDien, GiaTien, ThanhTien FROM dbo.TienDien ";

        if (!maPhongTro.isEmpty()) {
            sql += "WHERE MaPT LIKE ? ";
        }

        sql += "ORDER BY MaTD OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        int offset = (page - 1) * limit;

        if (page < 0) {
            page = 0;
        }

        try {
            ps = conn.prepareStatement(sql);
            if (!maPhongTro.isEmpty()) {
                ps.setString(1, "%" + maPhongTro + "%");
                ps.setInt(2, offset);
                ps.setInt(3, limit);
            } else {
                ps.setInt(1, offset);
                ps.setInt(2, limit);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelTienDien model = new ModelTienDien(
                        rs.getInt("MaTD"),
                        rs.getString("MaHoaDon"),
                        rs.getString("MaPT"),
                        rs.getString("NgayBatDau"),
                        rs.getString("NgayKetThuc"),
                        rs.getInt("ChiSoDau"),
                        rs.getInt("ChiSoCuoi"),
                        rs.getInt("SoDien"),
                        rs.getFloat("GiaTien"),
                        rs.getFloat("ThanhTien"));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int totalRecords(String timKiem) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienDien ";
        if (!timKiem.isEmpty()) {
            sql += "WHERE MaPT LIKE ?";
        }
        try {
            ps = conn.prepareStatement(sql);
            if(!timKiem.isEmpty()) {
                ps.setString(1, "%" + timKiem + "%");
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public void delete(int maTD) {
        sql = "{DelTienDien(?)}";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maTD);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
