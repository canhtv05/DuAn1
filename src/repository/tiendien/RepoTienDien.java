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
        sql = "SELECT "
                + "    MaTD, "
                + "    MaHoaDon, "
                + "    MaPT, "
                + "    CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "    CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, "
                + "    ChiSoDau, "
                + "    ChiSoCuoi, "
                + "    SoDien, "
                + "    GiaTien, "
                + "    ThanhTien "
                + "FROM "
                + "    dbo.TienDien ";

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
        sql = "SELECT "
                + "    MaTD, "
                + "    MaHoaDon, "
                + "    MaPT, "
                + "    CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "    CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, "
                + "    ChiSoDau, "
                + "    ChiSoCuoi, "
                + "    SoDien, "
                + "    GiaTien, "
                + "    ThanhTien "
                + "FROM "
                + "    dbo.TienDien ";

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
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getFloat(9),
                        rs.getFloat(10));
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
            if (!timKiem.isEmpty()) {
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

    public boolean delete(int maTD) {
        sql = "EXEC dbo.DelTienDien @MaTD = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maTD);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(ModelTienDien modelTienDien) {
        sql = "EXEC dbo.insertTienDien @MaHoaDon = ?,"
                + "                    @MaPT = ?,"
                + "                    @NgayBatDau = ?,"
                + "                    @NgayKetThuc = ?,"
                + "                    @ChiSoDau = ?,"
                + "                    @ChiSoCuoi = ?,"
                + "                    @GiaTien = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienDien.getMaHD());
            ps.setObject(2, modelTienDien.getMaPT());
            ps.setObject(3, modelTienDien.getNgayBD());
            ps.setObject(4, modelTienDien.getNgayKT());
            ps.setObject(5, modelTienDien.getChiSoDau());
            ps.setObject(6, modelTienDien.getChiSoCuoi());
            ps.setObject(7, modelTienDien.getGiaTien());
        int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
