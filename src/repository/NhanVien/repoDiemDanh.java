/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.DBContext;
import java.util.ArrayList;
import java.util.Date;
import model.NhanVien.Model_DiemDanh;
import view.component.message.MessageFrame;
/**
 *
 * @author BOSS
 */
public class repoDiemDanh {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<Model_DiemDanh> getALLDiemDanh() {
        ArrayList<Model_DiemDanh> listDiemDanh = new ArrayList<>();
        sql = "SELECT MaDiemDanh, MaNhanVien, NgayLamViec, TrangThai FROM DiemDanh";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_DiemDanh diemDanh = Model_DiemDanh.builder()
                        .maDiemDanh(rs.getInt("MaDiemDanh"))
                        .maNV(rs.getString("MaNhanVien"))
                        .ngayLamViec(rs.getDate("NgayLamViec"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                listDiemDanh.add(diemDanh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listDiemDanh;
    }

    // Add a new DiemDanh record
    public int add(Model_DiemDanh diemDanh) {
        sql = "INSERT INTO DiemDanh (MaNhanVien, NgayLamViec, TrangThai) VALUES (?, ?, ?)";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, diemDanh.getMaNV());
            ps.setDate(2, new java.sql.Date(diemDanh.getNgayLamViec().getTime()));
            ps.setInt(3, diemDanh.getTrangThai());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    // Delete a DiemDanh record
   public int deleteDiemDanh(String maNhanVien, Date ngayLamViec) {
    sql = "DELETE FROM DiemDanh WHERE MaNhanVien = ? AND NgayLamViec = ?";
    try {
        con = DBContext.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, maNhanVien);
        ps.setDate(2, new java.sql.Date(ngayLamViec.getTime()));
        return ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
        return 0;
    } finally {
        closeResources();
    }
}


    // Update an existing DiemDanh record
    public int update(int maDiemDanh, Model_DiemDanh diemDanh) {
        sql = "UPDATE DiemDanh SET MaNhanVien=?, NgayLamViec=?, TrangThai=? WHERE MaDiemDanh=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, diemDanh.getMaNV());
            ps.setDate(2, new java.sql.Date(diemDanh.getNgayLamViec().getTime()));
            ps.setInt(3, diemDanh.getTrangThai());
            ps.setInt(4, maDiemDanh);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeResources();
        }
    }

    // Search for DiemDanh records by MaNhanVien and date
    public ArrayList<Model_DiemDanh> tim(String maNhanVien, java.util.Date ngayLamViec) {
        ArrayList<Model_DiemDanh> listDiemDanh = new ArrayList<>();
        sql = "SELECT MaDiemDanh, MaNhanVien, NgayLamViec, TrangThai FROM DiemDanh WHERE MaNhanVien LIKE ? AND NgayLamViec = ?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, '%' + maNhanVien + '%');
            ps.setDate(2, new java.sql.Date(ngayLamViec.getTime()));
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_DiemDanh diemDanh = Model_DiemDanh.builder()
                        .maDiemDanh(rs.getInt("MaDiemDanh"))
                        .maNV(rs.getString("MaNhanVien"))
                        .ngayLamViec(rs.getDate("NgayLamViec"))
                        .trangThai(rs.getInt("TrangThai"))
                        .build();
                listDiemDanh.add(diemDanh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listDiemDanh;
    }
    public int getSoNgayDiLam(String maNV, int thang, int nam) {
        int soNgayDiLam = 0;
        sql = "SELECT COUNT(*) AS SoNgayDiLam FROM DiemDanh WHERE MaNhanVien = ? AND MONTH(NgayLamViec) = ? AND YEAR(NgayLamViec) = ? AND TrangThai = 1";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maNV);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            rs = ps.executeQuery();
            if (rs.next()) {
                soNgayDiLam = rs.getInt("SoNgayDiLam");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return soNgayDiLam;
    }
    public ArrayList<Model_DiemDanh> xapXep(){
    // Câu lệnh SQL sắp xếp theo NgayLamViec
    sql = "SELECT  MaDiemDanh,MaNhanVien, NgayLamViec, TrangThai FROM DiemDanh ORDER BY NgayLamViec DESC";
    ArrayList<Model_DiemDanh> list_DD = new ArrayList<>();
    try {
        con = DBContext.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Model_DiemDanh diemDanh = Model_DiemDanh.builder()
                    .maDiemDanh(rs.getInt("MaDiemDanh"))
                    .maNV(rs.getString("MaNhanVien"))
                    .ngayLamViec(rs.getDate("NgayLamViec"))
                    .trangThai(rs.getInt("TrangThai"))
                    .build();
            list_DD.add(diemDanh);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeResources();
    }
    return list_DD;
}
    public boolean checkTrungNgayLamViec(Date ngayLamViec) {
    sql = "SELECT COUNT(*) FROM DiemDanh WHERE NgayLamViec = ?";
    try {
        con = DBContext.getConnection();
        ps = con.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(ngayLamViec.getTime()));
        rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            return true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeResources();
    }
    return false;
}


    // Close database resources
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
