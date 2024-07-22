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
import java.util.Date;
/**
 *
 * @author BOSS
 */
public class repoNhanVien {
    private  Connection con = null;
    private  PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    public ArrayList<Model_NhanVien> getAll(){
        ArrayList<Model_NhanVien> list_NhanVien = new ArrayList<>();
        sql ="select  MaNV, HoTen,NgaySinh,GioiTinh,DienThoai,CCCD,NgayBatDau,NgayKetThuc,ThoiHan,Anh from NhanVien";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
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
                        .anhNV(rs.getString(10))
                        .build();
                list_NhanVien.add(nv);
            }
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return list_NhanVien;
    }
    public int add(Model_NhanVien QLNV){
        sql ="INSERT INTO [dbo].[NhanVien]\n" +
"           ([MaNV]\n" +
"           ,[HoTen]\n" +
"           ,[NgaySinh]\n" +
"           ,[GioiTinh]\n" +
"           ,[DienThoai]\n" +
"           ,[CCCD]\n" +
"           ,[NgayBatDau]\n" +
"           ,[NgayKetThuc]\n" +
"           ,[ThoiHan]\n" +
"           ,[Anh])\n" +
"     VALUES\n" +
"          (?,?,?,?,?,?,?,?,?,?);";
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
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(String maNV) {
    String sql = "DELETE FROM NhanVien WHERE maNV = ?";
    try {
        con = DBContext.getConnection();
        ps = con.prepareStatement(sql);
        ps.setObject(1, maNV); // Giả sử mã nhân viên được lưu trong thuộc tính maNV của đối tượng Model_NhanVien
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
    String sql = "UPDATE NhanVien SET MaNV=?, HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, CCCD=?, NgayBatDau=?, NgayKetThuc=?, ThoiHan=?, Anh=? WHERE MaNV=?";
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
            ps.setDate(8,new java.sql.Date(m.getNgayKT().getTime()));
        } else {
            ps.setNull(8, java.sql.Types.DATE);
        }
        ps.setInt(9, m.getThoiHan());
        ps.setString(10, m.getAnhNV());
        ps.setString(11, maNV);
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
    public ArrayList<Model_NhanVien> tim(String tenCanTim){
        ArrayList<Model_NhanVien> list_NhanVien = new ArrayList<>();
        sql = """
              SELECT [MaNV]
                    ,[HoTen]
                    ,[NgaySinh]
                    ,[GioiTinh]
                    ,[DienThoai]
                    ,[CCCD]
                    ,[NgayBatDau]
                    ,[NgayKetThuc]
                    ,[ThoiHan]
                    ,[Anh]
                FROM [dbo].[NhanVien]
                where MaNV =?""";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1,'%'+tenCanTim+'%');
            rs = ps.executeQuery();
            while(rs.next()){
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
                        .anhNV(rs.getString(10))
                        .build();
                list_NhanVien.add(nv);
            }
            return list_NhanVien;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
