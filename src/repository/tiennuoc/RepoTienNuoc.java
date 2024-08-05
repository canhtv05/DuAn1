package repository.tiennuoc;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.tiennuoc.ModelTienNuoc;

public class RepoTienNuoc {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoTienNuoc() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTienNuoc> searchAndPage(String timKiem, int page, int limit, int month, int year, int trangThai) {
        ArrayList<ModelTienNuoc> list = new ArrayList<>();
        sql = "SELECT "
                + "IdTienNuoc, "
                + "IdHoaDon, "
                + "MaPhong, "
                + "CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, "
                + "DauNguoi, "
                + "GiaTien, "
                + "ThanhTien, "
                + "TrangThai "
                + "FROM "
                + "dbo.TienNuoc ";

        boolean hasTimKiem = timKiem != null && !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += " WHERE ";

            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdHoaDon LIKE ? OR IdTienNuoc LIKE ?) ";
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

        sql += "ORDER BY IdTienNuoc OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

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
                ModelTienNuoc model = new ModelTienNuoc(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getInt(9)
                );
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int totalRecords(String timKiem, int month, int year, int trangThai) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienNuoc ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdTienNuoc LIKE ? OR IdHoaDon LIKE ?) ";
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
            System.out.println(e);
        }
        return sum;
    }

    public boolean update(ModelTienNuoc modelTienNuoc) {
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        boolean success = false;

        String sql1 = "UPDATE dbo.TienNuoc "
                + "SET NgayBatDau = ?, NgayKetThuc = ?, DauNguoi = ?, GiaTien = ?, TrangThai = ? "
                + "WHERE IdTienNuoc = ?;";

        String sql2 = "UPDATE dbo.TienDien "
                + "SET NgayBatDau = ?, NgayKetThuc = ? "
                + "WHERE IdTienDien = ?;";

        String sql3 = "UPDATE dbo.TienDichVu "
                + "SET NgayBatDau = ?, NgayKetThuc = ? "
                + "WHERE IdDichVu = ?;";
        try {
            ps1 = conn.prepareStatement(sql1);
            ps1.setObject(1, modelTienNuoc.getNgayBD());
            ps1.setObject(2, modelTienNuoc.getNgayKT());
            ps1.setObject(3, modelTienNuoc.getDauNguoi());
            ps1.setObject(4, modelTienNuoc.getGiaTien());
            ps1.setObject(5, modelTienNuoc.getTrangThai());
            ps1.setObject(6, modelTienNuoc.getMaTN());
            int affectedRows1 = ps1.executeUpdate();

            ps2 = conn.prepareStatement(sql2);
            ps2.setObject(1, modelTienNuoc.getNgayBD());
            ps2.setObject(2, modelTienNuoc.getNgayKT());
            ps2.setObject(3, modelTienNuoc.getMaTN()); 
            int affectedRows2 = ps2.executeUpdate();

            ps3 = conn.prepareStatement(sql3);
            ps3.setObject(1, modelTienNuoc.getNgayBD());
            ps3.setObject(2, modelTienNuoc.getNgayKT());
            ps3.setObject(3, modelTienNuoc.getMaTN()); 
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
        sql = "UPDATE dbo.TienNuoc "
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
