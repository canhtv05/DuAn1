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

    public ArrayList<ModelTienDien> searchAndPage(String timKiem, int page, int limit, int month, int year) {
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
                + "    ThanhTien "
                + "FROM "
                + "    dbo.TienDien ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdHoaDon LIKE ? OR IdTienDien LIKE ?) ";
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

        sql += "ORDER BY IdTienDien OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
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
                        rs.getDouble(10));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int totalRecords(String timKiem, int month, int year) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.TienDien ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdTienDien LIKE ? OR IdHoaDon LIKE ?) ";
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

    public boolean add(ModelTienDien modelTienDien, String maNV) {
        sql = "DECLARE @maHD INT; "
                + "INSERT INTO HoaDon (MaPhong, MaNhanVien, NgayLap, HanThanhToan, TrangThai, NgayThanhToan, TongTien) "
                + "VALUES (?, ?, GETDATE(), GETDATE(), 1, NULL, 0); "
                + "SET @maHD = SCOPE_IDENTITY(); "
                + "INSERT INTO dbo.TienDien (IdHoaDon, MaPhong, NgayBatDau, NgayKetThuc, ChiSoDau, ChiSoCuoi, GiaTien) "
                + "VALUES (@maHD, ?, ?, ?, ?, ?, ?);";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienDien.getMaPT());
            ps.setObject(2, maNV);
            ps.setObject(3, modelTienDien.getMaPT());
            ps.setObject(4, modelTienDien.getNgayBD());
            ps.setObject(5, modelTienDien.getNgayKT());
            ps.setObject(6, modelTienDien.getChiSoDau());
            ps.setObject(7, modelTienDien.getChiSoCuoi());
            ps.setObject(8, modelTienDien.getGiaTien());
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
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkExistMaTD(String maTD) {
        sql = "SELECT COUNT(*) FROM dbo.TienDien WHERE IdTienDien = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maTD);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ModelTienDien modelTienDien) {
        sql = "UPDATE dbo.TienDien "
                + "SET IdHoaDon = ?, MaPhong = ?, NgayBatDau = ?, NgayKetThuc = ?, ChiSoDau = ?, ChiSoCuoi = ?, GiaTien = ? "
                + "WHERE IdTienDien = ? "
                + "UPDATE dbo.TienDien "
                + "SET GiaTien = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienDien.getMaHD());
            ps.setObject(2, modelTienDien.getMaPT());
            ps.setObject(3, modelTienDien.getNgayBD());
            ps.setObject(4, modelTienDien.getNgayKT());
            ps.setObject(5, modelTienDien.getChiSoDau());
            ps.setObject(6, modelTienDien.getChiSoCuoi());
            ps.setObject(7, modelTienDien.getGiaTien());
            ps.setInt(8, modelTienDien.getMaTD());
            ps.setObject(9, modelTienDien.getGiaTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maTD) {
        sql = "DELETE FROM dbo.TienDien "
                + "WHERE IdTienDien = ?;";
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
