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

    public ArrayList<ModelTienNuoc> searchAndPage(String timKiem, int page, int limit, int month, int year) {
        ArrayList<ModelTienNuoc> list = new ArrayList<>();
        sql = "SELECT "
                + "    IdTienNuoc, "
                + "    IdHoaDon, "
                + "    MaPhong, "
                + "    CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "    CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, "
                + "    DauNguoi, "
                + "    GiaTien, "
                + "    ThanhTien "
                + "FROM "
                + "    dbo.TienNuoc ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdHoaDon LIKE ? OR IdTienNuoc LIKE ?) ";
            }

            if (hasMonth || hasYear) {
                if (hasTimKiem) {
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
            page = 0;
        }

        try {
            ps = conn.prepareStatement(sql);
            int paramIndex = 1;

            if (hasTimKiem) {
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
                ps.setString(paramIndex++, "%" + timKiem + "%");
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
                        Double.parseDouble(rs.getDouble(7) + ""),
                        Double.parseDouble(rs.getDouble(8) + ""));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalRecords(String timKiem, int month, int year) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienNuoc ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdTienNuoc LIKE ? OR IdHoaDon LIKE ?) ";
            }

            if (hasMonth || hasYear) {
                if (hasTimKiem) {
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

            if (hasMonth && hasYear) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
            } else if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month); // Đặt tháng cho cả NgayBatDau và NgayKetThuc
            } else if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year); // Đặt năm cho cả NgayBatDau và NgayKetThuc
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

    public boolean delete(int maTN) {
        sql = "DELETE FROM dbo.TienNuoc "
                + "WHERE IdTienNuoc = ?;";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maTN);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(ModelTienNuoc modelTienNuoc, String maNV) {
        sql = "DECLARE @maHD INT; "
                + "INSERT INTO HoaDon (MaPhong, MaNhanVien, NgayLap, HanThanhToan, TrangThai, NgayThanhToan, TongTien) "
                + "VALUES (?, ?, GETDATE(), GETDATE(), 1, NULL, 0); "
                + "SET @maHD = SCOPE_IDENTITY(); "
                + "INSERT INTO dbo.TienNuoc (IdHoaDon, MaPhong, NgayBatDau, NgayKetThuc, DauNguoi, GiaTien) "
                + "VALUES (@maHD, ?, ?, ?, ?, ?);";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienNuoc.getMaPT());
            ps.setObject(2, maNV);
            ps.setObject(3, modelTienNuoc.getMaPT());
            ps.setObject(4, modelTienNuoc.getNgayBD());
            ps.setObject(5, modelTienNuoc.getNgayKT());
            ps.setObject(6, modelTienNuoc.getDauNguoi());
            ps.setObject(7, modelTienNuoc.getGiaTien());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkMaPhong(String MaPhong) {
        String sqlPhongTro = "SELECT COUNT(*) FROM dbo.PhongTro WHERE MaPhong = ?";
        try {
            ps = conn.prepareStatement(sqlPhongTro);
            ps.setString(1, MaPhong);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkMaHD(int IdHoaDon) {
        sql = "SELECT COUNT(*) FROM dbo.HoaDon WHERE IdHoaDon = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, IdHoaDon);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistMaTN(String maTN) {
        sql = "SELECT COUNT(*) FROM dbo.TienNuoc WHERE IdTienNuoc = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maTN);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ModelTienNuoc modelTienNuoc) {
        sql = "UPDATE dbo.TienNuoc "
                + "SET IdHoaDon = ?, MaPhong = ?, NgayBatDau = ?, NgayKetThuc = ?, DauNguoi = ?, GiaTien = ? "
                + "WHERE IdTienNuoc = ? "
                + "UPDATE dbo.TienNuoc "
                + "SET GiaTien = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienNuoc.getMaHD());
            ps.setObject(2, modelTienNuoc.getMaPT());
            ps.setObject(3, modelTienNuoc.getNgayBD());
            ps.setObject(4, modelTienNuoc.getNgayKT());
            ps.setObject(5, modelTienNuoc.getDauNguoi());
            ps.setObject(6, modelTienNuoc.getGiaTien());
            ps.setObject(7, modelTienNuoc.getMaTN());
            ps.setObject(8, modelTienNuoc.getGiaTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
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
