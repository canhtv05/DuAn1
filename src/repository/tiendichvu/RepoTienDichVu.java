package repository.tiendichvu;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.tiendichvu.ModelTienDichVu;

public class RepoTienDichVu {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoTienDichVu() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTienDichVu> searchAndPage(String timKiem, int page, int limit) {
        ArrayList<ModelTienDichVu> list = new ArrayList<>();
        sql = "SELECT IdDichVu, TenDichVu, GiaTien "
                + "FROM dbo.TienDichVu ";

        if (!timKiem.isEmpty()) {
            sql += "WHERE TenDichVu LIKE ? ";
        }

        sql += "ORDER BY IdDichVu "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        int offset = (page - 1) * limit;
        if (page < 1) {
            page = 1;
        }

        try {
            ps = conn.prepareStatement(sql);
            int param = 1;
            if (!timKiem.isEmpty()) {
                ps.setString(param++, "%" + timKiem + "%");
            }

            ps.setInt(param++, offset);
            ps.setInt(param++, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelTienDichVu modelTienDichVu = new ModelTienDichVu(
                            rs.getInt("IdDichVu"),
                            rs.getString("TenDichVu"),
                            rs.getDouble("GiaTien")
                    );
                    list.add(modelTienDichVu);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalRecords(String timKiem) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienDichVu ";

        if (!timKiem.isEmpty()) {
            sql += "WHERE IdDichVu LIKE ? OR TenDichVu LIKE ?";
        }

        try {
            ps = conn.prepareStatement(sql);
            if (!timKiem.isEmpty()) {
                ps.setString(1, "%" + timKiem + "%");
                ps.setString(2, "%" + timKiem + "%");
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

    public boolean delete(int maDV) {
        sql = "DELETE FROM dbo.TienDichVu "
                + "WHERE IdDichVu = ?;";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maDV);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(ModelTienDichVu modelTienDichVu) {
        sql = "INSERT INTO dbo.TienDichVu(TenDichVu, GiaTien)"
                + "VALUES (?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, modelTienDichVu.getTenDV());
            ps.setDouble(2, modelTienDichVu.getGiaTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ModelTienDichVu modelTienDichVu) {
        sql = "UPDATE dbo.TienDichVu "
                + "SET TenDichVu = ?, GiaTien = ? "
                + "WHERE IdDichVu = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, modelTienDichVu.getTenDV());
            ps.setDouble(2, modelTienDichVu.getGiaTien());
            ps.setInt(3, modelTienDichVu.getIdDV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistMaDV(int maDV) {
        sql = "SELECT COUNT(*) FROM dbo.TienDichVu WHERE IdDichVu = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maDV);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
