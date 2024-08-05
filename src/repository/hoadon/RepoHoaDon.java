package repository.hoadon;

import dao.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.hoadon.ModelHoaDon;
import model.tiendichvu.ModelTienDichVu;
import model.tiendien.ModelTienDien;
import model.tiennuoc.ModelTienNuoc;

public class RepoHoaDon {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoHoaDon() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelHoaDon> searchAndPage(String timKiem, int page, int limit, int month, int year, int trangThai) {
        ArrayList<ModelHoaDon> list = new ArrayList<>();
        sql = "SELECT "
                + "	hd.IdHoaDon, "
                + "	hd.MaPhong, "
                + "	hd.MaNhanVien, "
                + "     CONVERT(VARCHAR, hd.NgayLap, 105) AS NgayLapFormatted, "
                + "     CONVERT(VARCHAR, hd.HanThanhToan, 105) AS HanThanhToanFormatted, "
                + "     CONVERT(VARCHAR, hd.NgayThanhToan, 105) AS NgayThanhToanFormatted, "
                + "     pt.GiaPhong, "
                + "	td.ThanhTien AS TienDien, "
                + "	tn.ThanhTien AS TienNuoc, "
                + "	dv.ThanhTien AS TienDichVu, "
                + "	hd.TongTien, "
                + "     hd.TrangThai "
                + "FROM dbo.HoaDon hd "
                + "JOIN dbo.TienDien td ON td.IdHoaDon = hd.IdHoaDon "
                + "JOIN dbo.TienNuoc tn ON tn.IdHoaDon = hd.IdHoaDon "
                + "JOIN dbo.TienDichVu dv ON dv.IdDichVu = hd.IdHoaDon "
                + "JOIN dbo.PhongTro pt ON pt.MaPhong = hd.MaPhong ";
        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(hd.MaPhong LIKE ? OR hd.IdHoaDon LIKE ?) ";
            }
            if (hasTrangThai) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += "hd.TrangThai = ? ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem || hasTrangThai) {
                    sql += "AND ";
                }
                if (hasMonth && hasYear) {
                    sql += "((DATEPART(month, hd.HanThanhToan) = ? AND DATEPART(year, hd.HanThanhToan) = ?) "
                            + "OR (DATEPART(month, hd.NgayThanhToan) = ? AND DATEPART(year, hd.NgayThanhToan) = ?) "
                            + "OR (DATEPART(month, hd.NgayLap) = ? AND DATEPART(year, hd.NgayLap) = ?)) ";
                } else if (hasMonth) {
                    sql += "(DATEPART(month, hd.HanThanhToan) = ? OR DATEPART(month, hd.NgayThanhToan) = ? OR DATEPART(month, hd.NgayLap) = ?) ";
                } else if (hasYear) {
                    sql += "(DATEPART(year, hd.HanThanhToan) = ? OR DATEPART(year, hd.NgayThanhToan) = ? OR DATEPART(year, hd.NgayLap) = ?) ";
                }
            }
        }
        sql += "ORDER BY hd.IdHoaDon OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
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
            }
            if (hasTrangThai) {
                ps.setInt(paramIndex++, trangThai);
            }
            if (hasMonth && hasYear) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
            } else if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            } else if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                ModelHoaDon model = new ModelHoaDon(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7),
                        rs.getInt(8),
                        rs.getInt(9),
                        rs.getDouble(10),
                        rs.getDouble(11),
                        rs.getInt(12));
                list.add(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int totalRecords(String timKiem, int month, int year, int trangThai) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.HoaDon ";
        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;
        boolean hasTrangThai = trangThai != -1;

        if (hasTimKiem || hasMonth || hasYear || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(MaPhong LIKE ? OR IdHoaDon LIKE ?) ";
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
                    sql += "((DATEPART(month, HanThanhToan) = ? AND DATEPART(year, HanThanhToan) = ?) "
                            + "OR (DATEPART(month, NgayThanhToan) = ? AND DATEPART(year, NgayThanhToan) = ?)  "
                            + "OR (DATEPART(month, NgayLap) = ? AND DATEPART(year, NgayLap) = ?)) ";
                } else if (hasMonth) {
                    sql += "(DATEPART(month, HanThanhToan) = ? OR DATEPART(month, NgayThanhToan) = ? OR DATEPART(month, NgayLap) = ?) ";
                } else if (hasYear) {
                    sql += "(DATEPART(year, HanThanhToan) = ? OR DATEPART(year, NgayThanhToan) = ? OR DATEPART(year, NgayLap) = ?) ";
                }
            }
        }
        try {
            ps = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (hasTimKiem) {
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
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, year);
            } else if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            } else if (hasYear) {
                ps.setInt(paramIndex++, year);
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

    public boolean updateHanTT(String hanTT, String ngayLap, String ngayTT, int maHD) {
        boolean updated = false;

        String sqlUpdateHanTT = "UPDATE dbo.HoaDon "
                + "SET HanThanhToan = ? "
                + "WHERE NgayLap = ?";

        String sqlUpdateNgayTT = "UPDATE dbo.HoaDon "
                + "SET NgayThanhToan = ?, TrangThai = 1 "
                + "WHERE IdHoaDon = ?";

        try {
            ps = conn.prepareStatement(sqlUpdateHanTT);
            ps.setString(1, hanTT);
            ps.setString(2, ngayLap);
            updated = ps.executeUpdate() > 0;

            if (!ngayTT.isEmpty()) {
                ps = conn.prepareStatement(sqlUpdateNgayTT);
                ps.setString(1, ngayTT);
                ps.setInt(2, maHD);
                updated = ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            updated = false;
        }

        return updated;
    }

    public boolean update(String ngayTT, int maHD) {
        sql = "UPDATE dbo.HoaDon "
                + "SET NgayThanhToan = ?, TrangThai = ? "
                + "WHERE IdHoaDon = ?;";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ngayTT);
            ps.setInt(2, 1);
            ps.setInt(3, maHD);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ModelTienDien showTienDien(int maHD) {
        ModelTienDien modelTienDien = new ModelTienDien();
        sql = "SELECT CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, ChiSoDau, ChiSoCuoi, SoDien, GiaTien, ThanhTien "
                + "FROM dbo.TienDien "
                + "WHERE IdHoaDon = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();
            if (rs.next()) {
                modelTienDien = new ModelTienDien(
                        rs.getString("NgayBatDauFormatted"),
                        rs.getString("NgayKetThucFormatted"),
                        rs.getInt("ChiSoDau"),
                        rs.getInt("ChiSoCuoi"),
                        rs.getInt("SoDien"),
                        rs.getDouble("GiaTien"),
                        rs.getDouble("ThanhTien")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelTienDien;
    }

    public ModelTienNuoc showTienNuoc(int maHD) {
        ModelTienNuoc modelTienNuoc = new ModelTienNuoc();
        sql = "SELECT CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, DauNguoi, GiaTien, ThanhTien "
                + "FROM dbo.TienNuoc "
                + "WHERE IdHoaDon = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();
            if (rs.next()) {
                modelTienNuoc = new ModelTienNuoc(
                        rs.getString("NgayBatDauFormatted"),
                        rs.getString("NgayKetThucFormatted"),
                        rs.getInt("DauNguoi"),
                        rs.getDouble("GiaTien"),
                        rs.getDouble("ThanhTien")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelTienNuoc;
    }

    public ModelTienDichVu showTienDV(int maHD) {
        ModelTienDichVu modelTienDichVu = new ModelTienDichVu();
        sql = "SELECT MaPhong, CONVERT(VARCHAR, NgayBatDau, 105) AS NgayBatDauFormatted, CONVERT(VARCHAR, NgayKetThuc, 105) AS NgayKetThucFormatted, TenDichVu, DauNguoi, GiaTien, ThanhTien "
                + "FROM dbo.TienDichVu "
                + "WHERE IdHoaDon = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();
            if (rs.next()) {
                modelTienDichVu = new ModelTienDichVu(
                        rs.getString("MaPhong"),
                        rs.getString("NgayBatDauFormatted"),
                        rs.getString("NgayKetThucFormatted"),
                        rs.getString("TenDichVu"),
                        rs.getInt("DauNguoi"),
                        rs.getDouble("GiaTien"),
                        rs.getDouble("ThanhTien")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelTienDichVu;
    }
    
    public ArrayList<String> getKhachThue(String maPT) {
        ArrayList<String> arr = new ArrayList<>();
        sql = "SELECT HoTen FROM KhachThue WHERE MaPhong = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, maPT);
            rs= ps.executeQuery();
            while(rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

}
