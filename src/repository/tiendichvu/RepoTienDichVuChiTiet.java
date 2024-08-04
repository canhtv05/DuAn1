package repository.tiendichvu;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.tiendichvu.ModelTienDichVuChiTiet;

public class RepoTienDichVuChiTiet {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoTienDichVuChiTiet() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTienDichVuChiTiet> searchAndPage(String timKiem, int page, int limit, int month, int year) {
        ArrayList<ModelTienDichVuChiTiet> list = new ArrayList<>();
        sql = "DECLARE @sum FLOAT; "
                + "SELECT @sum = SUM(GiaTien) FROM dbo.TienDichVu; "
                + "SELECT dvct.Id, dvct.IdHoaDon, CONVERT(VARCHAR, dvct.NgayBatDau, 105) AS NgayBatDauFormatted, "
                + "CONVERT(VARCHAR, dvct.NgayKetThuc, 105) AS NgayKetThucFormatted, dvct.MaPhong, dvct.DauNguoi, "
                + "@sum as TongTien, "
                + "(@sum * dvct.DauNguoi) AS ThanhTien "
                + "FROM dbo.DichVuChiTiet dvct ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(dvct.MaPhong LIKE ? OR dvct.IdHoaDon LIKE ? OR dvct.Id LIKE ?) ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                if (hasMonth) {
                    sql += "(DATEPART(month, dvct.NgayBatDau) = ? OR DATEPART(month, dvct.NgayKetThuc) = ?) ";
                }
                if (hasYear) {
                    if (hasMonth) {
                        sql += "AND ";
                    }
                    sql += "(DATEPART(year, dvct.NgayBatDau) = ? OR DATEPART(year, dvct.NgayKetThuc) = ?) ";
                }
            }
        }

        sql += "GROUP BY dvct.Id, dvct.IdHoaDon, dvct.NgayBatDau, dvct.NgayKetThuc, dvct.MaPhong, dvct.DauNguoi "
                + "ORDER BY dvct.Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

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
            if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            }
            if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);

            rs = ps.executeQuery();
            while (rs.next()) {
                ModelTienDichVuChiTiet model = new ModelTienDichVuChiTiet(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int totalRecords(String timKiem, int month, int year) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.DichVuChiTiet ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR Id LIKE ? OR IdHoaDon LIKE ?) ";
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
        sql = "DELETE FROM dbo.DichVuChiTiet "
                + "WHERE Id = ?;";
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

    public boolean add(ModelTienDichVuChiTiet modelTienDichVuChiTiet, String maNV) {
        sql = "DECLARE @maHD INT; "
                + "INSERT INTO HoaDon (MaPhong, MaNhanVien, NgayLap, HanThanhToan, TrangThai, NgayThanhToan, TongTien) "
                + "VALUES (?, ?, GETDATE(), GETDATE(), 1, NULL, 0); "
                + "SET @maHD = SCOPE_IDENTITY(); "
                + "INSERT INTO dbo.DichVuChiTiet (IdHoaDon, MaPhong, NgayBatDau, NgayKetThuc, DauNguoi, IdDichVu) "
                + "VALUES (@maHD, ?, ?, ?, ?, 1);";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienDichVuChiTiet.getMaPT());
            ps.setObject(2, maNV);
            ps.setObject(3, modelTienDichVuChiTiet.getMaPT());
            ps.setObject(4, modelTienDichVuChiTiet.getNgayBD());
            ps.setObject(5, modelTienDichVuChiTiet.getNgayKT());
            ps.setObject(6, modelTienDichVuChiTiet.getDauNguoi());
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

    public boolean checkExistMaTDV(int maTDV) {
        sql = "SELECT COUNT(*) FROM dbo.DichVuChiTiet WHERE Id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maTDV);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ModelTienDichVuChiTiet modelTienDichVuChiTiet) {
        sql = "UPDATE dbo.DichVuChiTiet "
                + "SET IdHoaDon = ?, MaPhong = ?, NgayBatDau = ?, NgayKetThuc = ?, DauNguoi = ? "
                + "WHERE Id = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1, modelTienDichVuChiTiet.getMaHD());
            ps.setObject(2, modelTienDichVuChiTiet.getMaPT());
            ps.setObject(3, modelTienDichVuChiTiet.getNgayBD());
            ps.setObject(4, modelTienDichVuChiTiet.getNgayKT());
            ps.setObject(5, modelTienDichVuChiTiet.getDauNguoi());
            ps.setObject(6, modelTienDichVuChiTiet.getMaTDV());
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
