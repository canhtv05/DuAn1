package repository.hoadon;

import dao.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.hoadon.ModelHoaDon;

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

    public ArrayList<ModelHoaDon> searchAndPage(String timKiem, int page, int limit, int month, int year) {
        ArrayList<ModelHoaDon> list = new ArrayList<>();
        sql = "DECLARE @sum FLOAT; "
                + "SELECT @sum = SUM(GiaTien) FROM dbo.TienDichVu; "
                + "SELECT "
                + "    hd.IdHoaDon, "
                + "    hd.MaPhong, "
                + "    hd.MaNhanVien, "
                + "    CONVERT(VARCHAR, hd.NgayLap, 105) AS NgayLap, "
                + "    CONVERT(VARCHAR, hd.HanThanhToan, 105) AS HanThanhToan, "
                + "    hd.TrangThai, "
                + "    CONVERT(VARCHAR, hd.NgayThanhToan, 105) AS NgayThanhToan, "
                + "    ISNULL(td.ThanhTien, 0) AS TienDienThanhTien, "
                + "    ISNULL(tn.ThanhTien, 0) AS TienNuocThanhTien, "
                + "    ISNULL(@sum * dv.DauNguoi, 0) AS DichVuThanhTien, "
                + "    (ISNULL(td.ThanhTien, 0) + ISNULL(tn.ThanhTien, 0) + ISNULL(@sum * dv.DauNguoi, 0)) AS TongTien "
                + "FROM dbo.HoaDon hd "
                + "FULL JOIN dbo.DichVuChiTiet dv ON dv.IdHoaDon = hd.IdHoaDon "
                + "FULL JOIN dbo.TienDien td ON td.IdHoaDon = hd.IdHoaDon "
                + "FULL JOIN dbo.TienNuoc tn ON tn.IdHoaDon = hd.IdHoaDon ";

        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(hd.IdHoaDon LIKE ? OR hd.MaPhong LIKE ? OR hd.MaNhanVien LIKE ?) ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += "(";
                if (hasMonth) {
                    sql += "(DATEPART(month, hd.NgayLap) = ? OR DATEPART(month, hd.HanThanhToan) = ? OR DATEPART(month, hd.NgayThanhToan) = ?) ";
                    if (hasYear) {
                        sql += "AND ";
                    }
                }
                if (hasYear) {
                    sql += "(DATEPART(year, hd.NgayLap) = ? OR DATEPART(year, hd.HanThanhToan) = ? OR DATEPART(year, hd.NgayThanhToan) = ?) ";
                }
                sql += ")";
            }
        }

        sql += "ORDER BY hd.IdHoaDon OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
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
                ps.setInt(paramIndex++, month);
            }

            if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);

            rs = ps.executeQuery();
            while (rs.next()) {
                ModelHoaDon modelHoaDon = new ModelHoaDon(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getDouble(8),
                        rs.getDouble(9),
                        rs.getDouble(10),
                        rs.getDouble(11)
                );
                list.add(modelHoaDon);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int totalRecords(String timKiem, int month, int year) {
        int sum = 0;
        sql = "SELECT COUNT(*) FROM dbo.HoaDon hd ";
        boolean hasTimKiem = !timKiem.isEmpty();
        boolean hasMonth = month != 0;
        boolean hasYear = year != 0;

        if (hasTimKiem || hasMonth || hasYear) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += "(hd.IdHoaDon LIKE ? OR hd.MaPhong LIKE ? OR hd.MaNhanVien LIKE ?) ";
            }
            if (hasMonth || hasYear) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += "(";
                if (hasMonth) {
                    sql += "(DATEPART(month, hd.NgayLap) = ? OR DATEPART(month, hd.HanThanhToan) = ? OR DATEPART(month, hd.NgayThanhToan) = ?) ";
                    if (hasYear) {
                        sql += "AND ";
                    }
                }
                if (hasYear) {
                    sql += "(DATEPART(year, hd.NgayLap) = ? OR DATEPART(year, hd.HanThanhToan) = ? OR DATEPART(year, hd.NgayThanhToan) = ?) ";
                }
                sql += ")";
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

            if (hasMonth) {
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
                ps.setInt(paramIndex++, month);
            }

            if (hasYear) {
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
                ps.setInt(paramIndex++, year);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                sum = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sum;
    }

}
