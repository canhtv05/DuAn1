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
import model.NhanVien.Model_Luong;

public class repo_Luong {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    // Retrieve all salary records
    public ArrayList<Model_Luong> getAllLuong() {
        ArrayList<Model_Luong> listLuong = new ArrayList<>();
        sql = "SELECT  MaLuong,MaNhanVien, Thang, Nam, SoNgayDiLam, LuongThang,HoTen FROM Luong";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_Luong luong = Model_Luong.builder()
                        .maLuong(rs.getInt("MaLuong"))
                        .maNV(rs.getString("MaNhanVien"))
                        .thang(rs.getInt("Thang"))
                        .nam(rs.getInt("Nam"))
                        .soNgayDiLam(rs.getInt("SoNgayDiLam"))
                        .luongThang(rs.getDouble("LuongThang"))
                        .hoTen(rs.getString("Hoten"))
                        .build();
                listLuong.add(luong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listLuong;
    }

    // Add a new salary record
    public int add(Model_Luong luong) {
        sql = "INSERT INTO Luong (MaNhanVien, Thang, Nam, SoNgayDiLam, LuongThang) VALUES (?, ?, ?, ?, ?)";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, luong.getMaNV());
            ps.setInt(2, luong.getThang());
            ps.setInt(3, luong.getNam());
            ps.setInt(4, luong.getSoNgayDiLam());
            ps.setDouble(5, luong.getLuongThang());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    // Delete a salary record
    public int delete(int maLuong) {
        sql = "DELETE FROM Luong WHERE MaLuong = ?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, maLuong);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    // Update an existing salary record
    public int update(int maLuong, Model_Luong luong) {
        sql = "UPDATE Luong SET MaNhanVien=?, Thang=?, Nam=?, SoNgayDiLam=?, LuongThang=? WHERE MaLuong=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, luong.getMaNV());
            ps.setInt(2, luong.getThang());
            ps.setInt(3, luong.getNam());
            ps.setInt(4, luong.getSoNgayDiLam());
            ps.setDouble(5, luong.getLuongThang());
            ps.setInt(6, maLuong);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return 0;
    }

    // Search for salary records by employee ID, month, and year
    public ArrayList<Model_Luong> search(String timKiem) {
    ArrayList<Model_Luong> listLuong = new ArrayList<>();
    String sql = "SELECT MaLuong, MaNhanVien, Thang, Nam, SoNgayDiLam, LuongThang, HoTen FROM Luong WHERE CONCAT(Thang, '/', Nam) LIKE ?";
    try {
        con = DBContext.getConnection();
        ps = con.prepareStatement(sql);
        ps.setString(1, '%' + timKiem + '%');
        rs = ps.executeQuery();
        while (rs.next()) {
            Model_Luong luong = Model_Luong.builder()
                    .maLuong(rs.getInt("MaLuong"))
                    .maNV(rs.getString("MaNhanVien"))
                    .thang(rs.getInt("Thang"))
                    .nam(rs.getInt("Nam"))
                    .soNgayDiLam(rs.getInt("SoNgayDiLam"))
                    .luongThang(rs.getDouble("LuongThang"))
                    .hoTen(rs.getString("HoTen"))  // Thêm hoTen nếu cần
                    .build();
            listLuong.add(luong);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        closeResources();
    }
    return listLuong;
}
//    public void updateLuongFromDiemDanh(int thang, int nam) {
//    sql = "UPDATE Luong\n" +
//"SET SoNgayDiLam = (\n" +
//"    SELECT COUNT(*) \n" +
//"    FROM DiemDanh \n" +
//"    WHERE DiemDanh.MaNhanVien = Luong.MaNhanVien \n" +
//"    AND MONTH(DiemDanh.NgayLamViec) = ? \n" +
//"    AND YEAR(DiemDanh.NgayLamViec) = ?\n" +
//"    AND TrangThai = 1\n" +
//"),\n" +
//"LuongThang = SoNgayDiLam * 200000\n" +
//"WHERE Thang = ? AND Nam = ?;";
//    try {
//        con = DBContext.getConnection();
//        ps = con.prepareStatement(sql);
//        ps.setInt(1, thang);
//        ps.setInt(2, nam);
//        ps.setInt(3, thang);
//        ps.setInt(4, nam);
//        int rowsAffected = ps.executeUpdate();
//        System.out.println("Number of rows updated: " + rowsAffected);
//    } catch (Exception e) {
//        e.printStackTrace();
//    } finally {
//        closeResources();
//    }
//}
//    public int getSoNgayDiLam(String maNhanVien, int thang, int nam) {
//    int soNgayDiLam = 0;
//    String sql = "SELECT COUNT(*) AS SoNgayDiLam " +
//                 "FROM DiemDanh " +
//                 "WHERE MaNhanVien = ? " +
//                 "AND MONTH(NgayLamViec) = ? " +
//                 "AND YEAR(NgayLamViec) = ? " +
//                 "AND TrangThai = 1";
//
//    try (Connection con = DBContext.getConnection();
//         PreparedStatement ps = con.prepareStatement(sql)) {
//
//        ps.setString(1, maNhanVien);
//        ps.setInt(2, thang);
//        ps.setInt(3, nam);
//
//        try (ResultSet rs = ps.executeQuery()) {
//            if (rs.next()) {
//                soNgayDiLam = rs.getInt("SoNgayDiLam");
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//    return soNgayDiLam;
//}
      // xắp xếp theo tháng và lương 
        public ArrayList<Model_Luong> xapXepLuong(){
        sql = "SELECT MaLuong, MaNhanVien, Thang, Nam, SoNgayDiLam, LuongThang, HoTen FROM Luong ORDER BY Nam DESC, Thang DESC";
        ArrayList<Model_Luong> list_Luong = new ArrayList<>();
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Model_Luong luong = Model_Luong.builder()
                        .maLuong(rs.getInt("MaLuong"))
                        .maNV(rs.getString("MaNhanVien"))
                        .thang(rs.getInt("Thang"))
                        .nam(rs.getInt("Nam"))
                        .soNgayDiLam(rs.getInt("SoNgayDiLam"))
                        .luongThang(rs.getDouble("LuongThang"))
                        .hoTen(rs.getString("HoTen"))
                        .build();
                list_Luong.add(luong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list_Luong;
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
    