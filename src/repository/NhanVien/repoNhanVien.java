/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.NhanVien;

import model.NhanVien.Model_NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.DBContext;
import java.util.ArrayList;

/**
 *
 * @author BOSS
 */
public class repoNhanVien {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public ArrayList<Model_NhanVien> getAllNhanVien(int trangThai) {
        ArrayList<Model_NhanVien> list_NhanVien = new ArrayList<>();
        sql = "SELECT MaNhanVien, HoTen, NgaySinh, GioiTinh, DienThoai, CCCD, NgayBatDau, NgayKetThuc, ThoiHan, Anh, TrangThai FROM NhanVien WHERE TrangThai = ?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, trangThai);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_NhanVien nv = Model_NhanVien.builder()
                        .maNV(rs.getString("MaNhanVien"))
                        .hoTen(rs.getString("HoTen"))
                        .ngaySinh(rs.getDate("NgaySinh"))
                        .gioiTinh(rs.getInt("GioiTinh"))
                        .dienThoai(rs.getString("DienThoai"))
                        .CCCD(rs.getString("CCCD"))
                        .ngayBD(rs.getDate("NgayBatDau"))
                        .ngayKT(rs.getDate("NgayKetThuc"))
                        .thoiHan(rs.getInt("ThoiHan"))
                        .anhNV(rs.getString("Anh"))
                        .TrangThai(rs.getInt("TrangThai"))
                        .build();
                list_NhanVien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list_NhanVien;
    }

    public ArrayList<Model_NhanVien> getAllNhanVien() {
        ArrayList<Model_NhanVien> list_NhanVien = new ArrayList<>();
        sql = "SELECT MaNhanVien, HoTen, NgaySinh, GioiTinh, DienThoai, CCCD, NgayBatDau, NgayKetThuc, ThoiHan, Anh, TrangThai FROM NhanVien";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_NhanVien nv = Model_NhanVien.builder()
                        .maNV(rs.getString("MaNhanVien"))
                        .hoTen(rs.getString("HoTen"))
                        .ngaySinh(rs.getDate("NgaySinh"))
                        .gioiTinh(rs.getInt("GioiTinh"))
                        .dienThoai(rs.getString("DienThoai"))
                        .CCCD(rs.getString("CCCD"))
                        .ngayBD(rs.getDate("NgayBatDau"))
                        .ngayKT(rs.getDate("NgayKetThuc"))
                        .thoiHan(rs.getInt("ThoiHan"))
                        .anhNV(rs.getString("Anh"))
                        .TrangThai(rs.getInt("TrangThai"))
                        .build();
                list_NhanVien.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list_NhanVien;
    }

    public int add(Model_NhanVien QLNV) {

        sql = """
             INSERT INTO [dbo].[NhanVien]
                        ([MaNhanVien]
                        ,[HoTen]
                        ,[NgaySinh]
                        ,[GioiTinh]
                        ,[DienThoai]
                        ,[CCCD]
                        ,[NgayBatDau]
                        ,[NgayKetThuc]
                        ,[ThoiHan]
                        ,[anh]
                        ,[TrangThai])
                  VALUES
                        (?,?,?,?,?,?,?,?,?,?,?)
             """;
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, QLNV.getMaNV());
            ps.setObject(2, QLNV.getHoTen());
            ps.setObject(3, QLNV.getNgaySinh());
            ps.setObject(4, QLNV.getGioiTinh());
            ps.setObject(5, QLNV.getDienThoai());
            ps.setObject(6, QLNV.getCCCD());
            ps.setObject(7, QLNV.getNgayBD());
            ps.setObject(8, QLNV.getNgayKT());
            ps.setObject(9, QLNV.getThoiHan());
            ps.setObject(10, QLNV.getAnhNV());
            ps.setObject(11, QLNV.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int DeleteNV(String maNV) {
        sql = "UPDATE NhanVien SET TrangThai = 0 WHERE MaNhanVien = ?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maNV);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int update(String maNV, Model_NhanVien m) {
        sql = "UPDATE NhanVien SET MaNhanVien=?, HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?,"
                + " CCCD=?, NgayBatDau=?, NgayKetThuc=?, ThoiHan=?, TrangThai =?, Anh=? WHERE MaNhanVien=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getMaNV());
            ps.setString(2, m.getHoTen());
            ps.setDate(3, new java.sql.Date(m.getNgaySinh().getTime()));
            ps.setInt(4, m.getGioiTinh());
            ps.setString(5, m.getDienThoai());
            ps.setString(6, m.getCCCD());
            ps.setDate(7, new java.sql.Date(m.getNgayBD().getTime()));
            if (m.getNgayKT() != null) {
                ps.setDate(8, new java.sql.Date(m.getNgayKT().getTime()));
            } else {
                ps.setNull(8, java.sql.Types.DATE);
            }
            ps.setInt(9, m.getThoiHan());
            ps.setInt(10, m.getTrangThai());
            ps.setString(11, m.getAnhNV());
            ps.setString(12, maNV);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Model_NhanVien> tim(String tenCanTim) {
        ArrayList<Model_NhanVien> list_NhanVien = new ArrayList<>();
        sql = """
          SELECT [MaNhanVien]
                ,[HoTen]
                ,[NgaySinh]
                ,[GioiTinh]
                ,[DienThoai]
                ,[CCCD]
                ,[NgayBatDau]
                ,[NgayKetThuc]
                ,[ThoiHan]
                ,[TrangThai]
                ,[Anh]
            FROM [dbo].[NhanVien]
            WHERE MaNhanVien LIKE ? 
              OR HoTen LIKE ? 
              OR DienThoai LIKE ? 
              OR CCCD LIKE ?""";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            String queryParam = '%' + tenCanTim + '%';
            ps.setObject(1, queryParam);
            ps.setString(2, queryParam);
            ps.setString(3, queryParam);
            ps.setString(4, queryParam);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_NhanVien nv = Model_NhanVien.builder()
                        .maNV(rs.getString(1))
                        .hoTen(rs.getString(2))
                        .ngaySinh(rs.getDate(3))
                        .gioiTinh(rs.getInt(4))
                        .dienThoai(rs.getString(5))
                        .CCCD(rs.getString(6))
                        .ngayBD(rs.getDate(7))
                        .ngayKT(rs.getDate(8))
                        .thoiHan(rs.getInt(9))
                        .TrangThai(rs.getInt(10))
                        .anhNV(rs.getString(11))
                        .build();
                list_NhanVien.add(nv);
            }
            return list_NhanVien;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Model_NhanVien checkTrungMaNV(String maNV) {
        sql = "SELECT * FROM NhanVien WHERE MaNhanVien = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Model_NhanVien.builder()
                            .maNV(rs.getString("MaNhanVien"))
                            .hoTen(rs.getString("HoTen"))
                            .ngaySinh(rs.getDate("NgaySinh"))
                            .gioiTinh(rs.getInt("GioiTinh"))
                            .dienThoai(rs.getString("DienThoai"))
                            .CCCD(rs.getString("CCCD"))
                            .ngayBD(rs.getDate("NgayBatDau"))
                            .ngayKT(rs.getDate("NgayKetThuc"))
                            .thoiHan(rs.getInt("ThoiHan"))
                            .anhNV(rs.getString("Anh"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Model_NhanVien checkTrungDienThoai(String dienThoai) {
        sql = "SELECT * FROM NhanVien WHERE DienThoai = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dienThoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Model_NhanVien.builder()
                            .maNV(rs.getString("MaNhanVien"))
                            .hoTen(rs.getString("HoTen"))
                            .ngaySinh(rs.getDate("NgaySinh"))
                            .gioiTinh(rs.getInt("GioiTinh"))
                            .dienThoai(rs.getString("DienThoai"))
                            .CCCD(rs.getString("CCCD"))
                            .ngayBD(rs.getDate("NgayBatDau"))
                            .ngayKT(rs.getDate("NgayKetThuc"))
                            .thoiHan(rs.getInt("ThoiHan"))
                            .anhNV(rs.getString("Anh"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Model_NhanVien checkTrungCCCD(String cccd) {
        String sql = "SELECT * FROM NhanVien WHERE CCCD = ?";
        try (Connection con = DBContext.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cccd);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Model_NhanVien.builder()
                            .maNV(rs.getString("MaNhanVien"))
                            .hoTen(rs.getString("HoTen"))
                            .ngaySinh(rs.getDate("NgaySinh"))
                            .gioiTinh(rs.getInt("GioiTinh"))
                            .dienThoai(rs.getString("DienThoai"))
                            .CCCD(rs.getString("CCCD"))
                            .ngayBD(rs.getDate("NgayBatDau"))
                            .ngayKT(rs.getDate("NgayKetThuc"))
                            .thoiHan(rs.getInt("ThoiHan"))
                            .anhNV(rs.getString("Anh"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getTotalNhanVien() {
        sql = "SELECT COUNT(*) FROM NhanVien";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalNhanVienDangLam() {
        sql = "SELECT COUNT(*) FROM NhanVien WHERE TrangThai = 1";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalNhanVienDaNghi() {
        sql = "SELECT COUNT(*) FROM NhanVien WHERE TrangThai = 0";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
