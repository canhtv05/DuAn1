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
    
    public ArrayList<model.KhachThue.ModelKhachThue> getCBB(){
        ArrayList<model.KhachThue.ModelKhachThue> arrCBB = new ArrayList<>();
        sql = "select MaPhong from PhongTro order by MaPhong";
        try {
            con= DBContext.getConnection();
            ps= con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                String mapt;
                mapt=rs.getString(1).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(mapt);
                arrCBB.add(kt);
            }
            return arrCBB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<model.KhachThue.ModelKhachThue> getAll(){
        ArrayList<model.KhachThue.ModelKhachThue> arrKT = new ArrayList<>();
        sql="select IdKhach, MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi, CCCD from KhachThue  order by MaPhong";
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
        sql="insert into KhachThue(MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi , CCCD) values(?,?,?,?,?,?,?,?)";
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
            
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int sua(model.KhachThue.ModelKhachThue x){
        sql=" update KhachThue set MaPhong=?, HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, DiaChi=?, CCCD=? where IdKhach=?";
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
        sql="select IdKhach, MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi, CCCD from KhachThue   where MaPhong LIKE ? or IdKhach LIKE ? or HoTen LIKE ? or DienThoai LIKE ? or Email LIKE ? ";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, "%"+mapt+"%");
            ps.setObject(2, "%"+mapt+"%");
            ps.setObject(3, "%"+mapt+"%");
            ps.setObject(4, "%"+mapt+"%");
            ps.setObject(5, "%"+mapt+"%");
            
            
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
    
    public String check(String ma){
        sql="select IdKhach from KhachThue where IdKhach=?";
        try {
            String makt = null;
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, ma);
            
            rs= ps.executeQuery();
            while (rs.next()) {                
                makt=rs.getString(1);
            }
            return makt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
