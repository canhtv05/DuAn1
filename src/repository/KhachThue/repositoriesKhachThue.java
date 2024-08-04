/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.KhachThue;
import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.KhachThue.ModelKhachThue;
/**
 *
 * @author X1
 */
public class repositoriesKhachThue {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public repositoriesKhachThue() {
        try {
            con = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    public ArrayList<model.KhachThue.ModelKhachThue> getAll(){
        ArrayList<model.KhachThue.ModelKhachThue> arrKT = new ArrayList<>();
        sql="select * from KhachThue";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                String maKT, hoTen, dienThoai, email, diaChi, cccd, maPT;
                Date ngaySinh;
                boolean gioiTinh;
                
                maKT=rs.getString(1).trim();
                hoTen=rs.getString(3).trim();
                ngaySinh=rs.getDate(4);
                gioiTinh=rs.getBoolean(5);
                dienThoai=rs.getString(6).trim();
                email=rs.getString(7).trim();
                diaChi=rs.getString(8).trim();
                cccd=rs.getString(9).trim();
                maPT=rs.getString(2).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(maKT,maPT, hoTen, ngaySinh, gioiTinh, dienThoai, email, diaChi, cccd);
                arrKT.add(kt);
            }
            return arrKT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int them(model.KhachThue.ModelKhachThue x) {
        sql="insert into KhachThue(MaKT, MaPT, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi , CCCD) values(?,?,?,?,?,?,?,?,?)";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(2, x.getMaPT());
            ps.setObject(3, x.getHoTen());
            ps.setObject(4, x.getNgaySinh());
            ps.setObject(5, x.isGioiTinh());
            ps.setObject(6, x.getDienThoai());
            ps.setObject(7, x.getEmail());
            ps.setObject(8, x.getDiaChi());
            ps.setObject(9, x.getCCCD());
            ps.setObject(1, x.getMaKT());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int sua(model.KhachThue.ModelKhachThue x){
        sql=" update KhachThue set MaPT=?, HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, DiaChi=?, CCCD=? where MaKT=?";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, x.getMaPT());
            ps.setObject(2, x.getHoTen());
            ps.setObject(3, x.getNgaySinh());
            ps.setObject(4, x.isGioiTinh());
            ps.setObject(5, x.getDienThoai());
            ps.setObject(6, x.getEmail());
            ps.setObject(7, x.getDiaChi());
            ps.setObject(8, x.getCCCD());
            ps.setObject(9, x.getMaKT());
            return ps.executeUpdate();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<model.KhachThue.ModelKhachThue> timKiem(String mapt){
        ArrayList<model.KhachThue.ModelKhachThue> arrKT = new ArrayList<>();
        sql="select * from KhachThue where MaPT LIKE ? or MaKT LIKE ? or HoTen LIKE ?";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, "%"+mapt+"%");
            ps.setObject(2, "%"+mapt+"%");
            ps.setObject(3, "%"+mapt+"%");
            
            
            rs=ps.executeQuery();
            while (rs.next()) {                
                String maKT, hoTen, dienThoai, email, diaChi, cccd, maPT;
                Date ngaySinh;
                boolean gioiTinh;
                
                maKT=rs.getString(1).trim();
                hoTen=rs.getString(3).trim();
                ngaySinh=rs.getDate(4);
                gioiTinh=rs.getBoolean(5);
                dienThoai=rs.getString(6).trim();
                email=rs.getString(7).trim();
                diaChi=rs.getString(8).trim();
                cccd=rs.getString(9).trim();
                maPT=rs.getString(2).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(maKT,maPT, hoTen, ngaySinh, gioiTinh, dienThoai, email, diaChi, cccd);
                arrKT.add(kt);
            }
            return arrKT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
