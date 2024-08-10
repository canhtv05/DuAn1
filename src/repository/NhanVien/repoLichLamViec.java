/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.NhanVien;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.NhanVien.Model_LichLamViec;

/**
 *
 * @author BOSS
 */
public class repoLichLamViec {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<Model_LichLamViec> getALLLich() {
        ArrayList<Model_LichLamViec> listLich = new ArrayList<>();
        sql = "SELECT lv.IDLichLamViec, lv.NgayLamViec, lv.MaNhanVien, nv.HoTen, lv.CongViec, lv.GhiChu, lv.TrangThai\n"
                + "    FROM LichLamViec lv \n"
                + "    INNER JOIN NhanVien nv ON lv.MaNhanVien = nv.MaNhanVien";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_LichLamViec lich = Model_LichLamViec.builder()
                        .idLich(rs.getInt("IDLichLamViec"))
                        .ngayLam(rs.getDate("NgayLamViec"))
                        .maNV(rs.getString("MaNhanVien"))
                        .tenNV(rs.getString("HoTen"))
                        .congViec(rs.getString("CongViec"))
                        .ghiChu(rs.getString("GhiChu"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                listLich.add(lich);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listLich;
    }

    public int add(Model_LichLamViec LLV) {
        sql = "INSERT INTO [dbo].[LichLamViec]\n"
                + "           ([NgayLamViec]\n"
                + "           ,[MaNhanVien]\n"
                + "           ,[CongViec]\n"
                + "           ,[GhiChu]\n"
                + "           ,[TrangThai])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?)";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, LLV.getNgayLam());
            ps.setObject(2, LLV.getMaNV());
            ps.setObject(3, LLV.getCongViec());
            ps.setObject(4, LLV.getGhiChu());
            ps.setObject(5, LLV.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResources();
        }
        return 0;
    }

    public int update(int id, Model_LichLamViec lich) {
        sql = "update LichLamViec set NgayLamViec =?, MaNhanVien =?,CongViec=?, GhiChu=? where idLichLamViec =?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(lich.getNgayLam().getTime()));
            ps.setString(2, lich.getMaNV());
            ps.setString(3, lich.getCongViec());
            ps.setString(4, lich.getGhiChu());
            ps.setInt(5, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public int updateTrangThai(int id, int trangThai, String ghiChu) {
        sql = "UPDATE LichLamViec SET TrangThai=?, GhiChu=? WHERE idLichLamViec=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, trangThai);
            ps.setString(2, ghiChu);
            ps.setInt(3, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public int delete(int id) {
        sql = "DELETE FROM LichLamViec WHERE idLichLamViec=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    public Model_LichLamViec checkTrungLich(String maNV, java.sql.Date ngayLam) {
        sql = "SELECT * FROM LichLamViec WHERE MaNhanVien=? AND NgayLamViec=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.setDate(2, ngayLam);
            rs = ps.executeQuery();
            if (rs.next()) {
                Model_LichLamViec lich = Model_LichLamViec.builder()
                        .idLich(rs.getInt("IDLichLamViec"))
                        .ngayLam(rs.getDate("NgayLamViec"))
                        .maNV(rs.getString("MaNhanVien"))
                        //.tenNV(rs.getString("HoTen"))
                        .congViec(rs.getString("CongViec"))
                        .ghiChu(rs.getString("GhiChu"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                return lich;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    public ArrayList<Model_LichLamViec> XapXepLichHomNay() {
        ArrayList<Model_LichLamViec> listLich = new ArrayList<>();
        sql = "SELECT lv.IDLichLamViec, lv.NgayLamViec, lv.MaNhanVien, nv.HoTen, lv.CongViec, lv.GhiChu, lv.TrangThai "
                + "FROM LichLamViec lv "
                + "INNER JOIN NhanVien nv ON lv.MaNhanVien = nv.MaNhanVien "
                + "ORDER BY CASE WHEN lv.NgayLamViec = ? THEN 0 ELSE 1 END, lv.NgayLamViec DESC";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(new java.util.Date().getTime())); // Đặt ngày hôm nay
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_LichLamViec lich = Model_LichLamViec.builder()
                        .idLich(rs.getInt("IDLichLamViec"))
                        .ngayLam(rs.getDate("NgayLamViec"))
                        .maNV(rs.getString("MaNhanVien"))
                        .tenNV(rs.getString("HoTen"))
                        .congViec(rs.getString("CongViec"))
                        .ghiChu(rs.getString("GhiChu"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                listLich.add(lich);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listLich;
    }

    public ArrayList<Model_LichLamViec> timKiem(String canTim, int ngay, int thang, int nam, int trangThai) {
        ArrayList<Model_LichLamViec> listLich = new ArrayList<>();
        boolean hasTimKiem = !canTim.isEmpty();
        boolean hasNgay = ngay != -1;
        boolean hasThang = thang != -1;
        boolean hasNam = nam != -1;
        boolean hasTrangThai = trangThai != -1;
        sql = "SELECT lv.IDLichLamViec, lv.NgayLamViec, lv.MaNhanVien, nv.HoTen, lv.CongViec, lv.GhiChu, lv.TrangThai "
                + "FROM LichLamViec lv "
                + "INNER JOIN NhanVien nv ON lv.MaNhanVien = nv.MaNhanVien ";

        if (hasTimKiem || hasNgay || hasThang || hasNam || hasTrangThai) {
            sql += "WHERE ";
            if (hasTimKiem) {
                sql += " lv.MaNhanVien LIKE ? OR nv.HoTen LIKE ? ";
            }

            if (hasTrangThai) {
                if (hasTimKiem) {
                    sql += "AND ";
                }
                sql += " lv.TrangThai = ? ";
            }

            if (hasNgay || hasThang || hasNam) {
                if (hasTimKiem || hasTrangThai) {
                    sql += "AND ";
                }
                if (hasNgay && hasThang && hasNam) {
                    sql += "DATEPART(DAY, lv.NgayLamViec) = ? AND DATEPART(month, lv.NgayLamViec) = ? AND  DATEPART(YEAR, lv.NgayLamViec) = ? ";
                } else if (hasNgay) {
                    sql += "DATEPART(DAY, lv.NgayLamViec) = ? ";
                } else if (hasThang) {
                    sql += "DATEPART(MONTH, lv.NgayLamViec) = ? ";
                } else if (hasNam) {
                    sql += "DATEPART(YEAR, lv.NgayLamViec) = ? ";
                }
            }
        }
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            int param = 1;
//            ps.setString(param++, "%" + canTim + "%");
//            ps.setString(param++, "%" + canTim + "%");
//
//            if (has) {
//                rs = ps.executeQuery();
//            }
            if (hasTimKiem) {
                ps.setString(param++, "%" + canTim + "%");
                ps.setString(param++, "%" + canTim + "%");
            }

            if (hasTrangThai) {
                ps.setInt(param++, trangThai);
            }
            if (hasNgay && hasThang && hasNam) {
                ps.setInt(param++, ngay);
                ps.setInt(param++, thang);
                ps.setInt(param++, nam);
            } else if (hasNgay) {
                ps.setInt(param++, ngay);
            } else if (hasThang) {
                ps.setInt(param++, thang);
            } else if (hasNam) {
                ps.setInt(param++, nam);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_LichLamViec lich = Model_LichLamViec.builder()
                        .idLich(rs.getInt("IDLichLamViec"))
                        .ngayLam(rs.getDate("NgayLamViec"))
                        .maNV(rs.getString("MaNhanVien"))
                        .tenNV(rs.getString("HoTen"))
                        .congViec(rs.getString("CongViec"))
                        .ghiChu(rs.getString("GhiChu"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                listLich.add(lich);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listLich;
    }

    public int getTotalDaHoanThanh() {
        sql = "SELECT COUNT(*) FROM LichLamViec WHERE TrangThai = 1";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    public int getTotalChuaHoanThanh() {
        sql = "SELECT COUNT(*) FROM LichLamViec WHERE TrangThai = 0";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
