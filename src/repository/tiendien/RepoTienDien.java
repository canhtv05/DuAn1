package repository.tiendien;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.tiendien.ModelTienDien;

public class RepoTienDien {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoTienDien() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTienDien> searchAndPage(String timKiem, int page, int limit, int month, int year,
            int trangThai) {
        ArrayList<ModelTienDien> list = new ArrayList<>();
        sql = "SELECT "
                + "    IdTienDien, "
                + "    IdHoaDon, "
                + "    MaPhong, "
                + "    CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "    CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, "
                + "    ChiSoDau, "
                + "    ChiSoCuoi, "
                + "    SoDien, "
                + "    GiaTien, "
                + "    ThanhTien, "
                + "    TrangThai "
                + "FROM "
                + "    dbo.TienDien ";
        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdHoaDon LIKE ? OR IdTienDien LIKE ?) ";
            }
            if (hasTrangThai) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += "TrangThai = ? ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem || hasTrangThai) {
                    sql += "AND ";
                }
                if (hasMonth && hasYear) {
                    sql += "((DATEPART(month, NgayBatDau) = ? AND DATEPART(year, NgayBatDau) = ?) "
                            + "OR (DATEPART(month, NgayKetThuc) = ? AND DATEPART(year, NgayKetThuc) = ?)) ";
                } else if (hasMonth) {
                    sql += "(DATEPART(month, NgayBatDau) = ? OR DATEPART(month, NgayKetThuc) = ?) ";
                } else if (hasYear) {
                    sql += "(DATEPART(year, NgayBatDau) = ? OR DATEPART(year, NgayKetThuc) = ?) ";
                }
            }
        }
        sql += "ORDER BY IdTienDien OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        int offset = (page - 1) * limit;
        if (page < 0) {
            page = 1;
        }
        try {
            ps = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (hasTimKiem) {
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
            }
            if (hasTrangThai) {
                ps.setInt(paramIndex++, trangThai);
            }
            if (hasMonth && hasYear) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
            } else if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            } else if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelTienDien model = new ModelTienDien(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getInt(11));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalRecords(String timKiem, int month, int year, int trangThai) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienDien ";
        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdTienDien LIKE ? OR IdHoaDon LIKE ?) ";
            }
            if (hasTrangThai) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += "TrangThai = ? ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem || hasTrangThai) {
                    sql += "AND ";
                }
                if (hasMonth && hasYear) {
                    sql += "((DATEPART(month, NgayBatDau) = ? AND DATEPART(year, NgayBatDau) = ?) "
                            + "OR (DATEPART(month, NgayKetThuc) = ? AND DATEPART(year, NgayKetThuc) = ?)) ";
                } else if (hasMonth) {
                    sql += "(DATEPART(month, NgayBatDau) = ? OR DATEPART(month, NgayKetThuc) = ?) ";
                } else if (hasYear) {
                    sql += "(DATEPART(year, NgayBatDau) = ? OR DATEPART(year, NgayKetThuc) = ?) ";
                }
            }
        }
        try {
            ps = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (hasTimKiem) {
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
            }
            if (hasTrangThai) {
                ps.setInt(paramIndex++, trangThai);
            }
            if (hasMonth && hasYear) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
            } else if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            } else if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
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

    public boolean update(ModelTienDien modelTienDien) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        boolean success = false;

        String sql1 = "UPDATE dbo.TienDien "
                + "SET NgayBatDau = ?, NgayKetThuc = ?, ChiSoDau = ?, ChiSoCuoi = ?, GiaTien = ?, TrangThai = ? "
                + "WHERE IdTienDien = ?;";

        String sql2 = "UPDATE dbo.TienNuoc "
                + "SET NgayBatDau = ?, NgayKetThuc = ? "
                + "WHERE IdTienNuoc = ?;";

        String sql3 = "UPDATE dbo.TienDichVu "
                + "SET NgayBatDau = ?, NgayKetThuc = ? "
                + "WHERE IdDichVu = ?;";
        try {
            ps1 = conn.prepareStatement(sql1);
            ps1.setObject(1, modelTienDien.getNgayBD());
            ps1.setObject(2, modelTienDien.getNgayKT());
            ps1.setObject(3, modelTienDien.getChiSoDau());
            ps1.setObject(4, modelTienDien.getChiSoCuoi());
            ps1.setObject(5, modelTienDien.getGiaTien());
            ps1.setObject(6, modelTienDien.getTrangThai());
            ps1.setObject(7, modelTienDien.getMaTD());
            int affectedRows1 = ps1.executeUpdate();

            ps2 = conn.prepareStatement(sql2);
            ps2.setObject(1, modelTienDien.getNgayBD());
            ps2.setObject(2, modelTienDien.getNgayKT());
            ps2.setObject(3, modelTienDien.getMaTD());
            int affectedRows2 = ps2.executeUpdate();

            ps3 = conn.prepareStatement(sql3);
            ps3.setObject(1, modelTienDien.getNgayBD());
            ps3.setObject(2, modelTienDien.getNgayKT());
            ps3.setObject(3, modelTienDien.getMaTD());
            int affectedRows3 = ps3.executeUpdate();

            if (affectedRows1 > 0 && affectedRows2 > 0 && affectedRows3 > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean updatePirceAndDate(double giaTien, String ngayDB, String ngayKT) {
        sql = "UPDATE dbo.TienDien "
                + "SET GiaTien = ? "
                + "WHERE NgayBatDau >= ? AND NgayKetThuc <= ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, giaTien);
            ps.setObject(2, ngayDB);
            ps.setObject(3, ngayKT);

            return ps.executeUpdate() > 0;
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                maNV = rs.getString("MaNhanVien");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNV;
    }

}
