/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.PhongTro;

import dao.DBContext;
import model.PhongTro.ModelPhongTro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class repoPhongTro {
    private Connection conn;
    public ArrayList<ModelPhongTro> listPT;

    public repoPhongTro() {
        try {
            this.conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<ModelPhongTro> findAll() {
            listPT = new ArrayList<>();
        
        try {
            String sql = "Select MaPT, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai From PhongTro;";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();
            
            ResultSet rs = ps.getResultSet();
            
            while (rs.next()) {                
                String maPT = rs.getString("MaPT");
                String loaiPhong = rs.getString("LoaiPhong");
                float dienTich = rs.getFloat("DienTich");
                float giaPhong = rs.getFloat("GiaPhong");
                String tienNghi = rs.getString("TienNghi");
                int trangThai = rs.getInt("TrangThai");
                
                ModelPhongTro pt = new ModelPhongTro(maPT, loaiPhong, dienTich, giaPhong, tienNghi, trangThai);
                listPT.add(pt);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listPT;
    }
    
    public void insert(ModelPhongTro pt) {
        try {
            String sql = "Insert into PhongTro (MaPT, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai) Values (?, ?, ?, ?, ?, ?);  ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            
            ps.setString(1, pt.getMaPT());
            ps.setString(2, pt.getLoaiPhong());
            ps.setFloat(3, pt.getDienTich());
            ps.setFloat(4, pt.getGiaPhong());
            ps.setString(5, pt.getTienNghi());
            ps.setInt(6, pt.getTrangThai());
            
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(ModelPhongTro pt) {
        try {
            String sql = "Update PhongTro Set LoaiPhong = ?, DienTich = ?, GiaPhong = ?, TienNghi = ?, TrangThai = ? Where MaPT = ? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            
            ps.setString(1, pt.getLoaiPhong());
            ps.setFloat(2, pt.getDienTich());
            ps.setFloat(3, pt.getGiaPhong());
            ps.setString(4, pt.getTienNghi());
            ps.setInt(5, pt.getTrangThai());
            ps.setString(6, pt.getMaPT());
            
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<ModelPhongTro> Search() {
        
        
        return listPT;
    }
    
    
}
