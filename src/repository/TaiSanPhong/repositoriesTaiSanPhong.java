/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.TaiSanPhong;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.HopDong.ModelHopDong;
import model.TaiSanPhong.ModelTaiSanPhong;
/**
 *
 * @author X1
 */
public class repositoriesTaiSanPhong {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public repositoriesTaiSanPhong() {
        try {
            con= DBContext.getConnection();
        } catch (Exception e) {
        }
    }
    
    public ArrayList<model.TaiSanPhong.ModelTaiSanPhong> getAll(int tt){
        ArrayList<model.TaiSanPhong.ModelTaiSanPhong> arrTSP = new ArrayList<>();
        sql="select IdTaiSanPhong, tsp.IdTaiSan, ts.TenTaiSan, MaPhong, tsp.SoLuong, TinhTrang, tsp.GhiChu  from TaiSanPhong tsp join TaiSan ts on ts.IdTaiSan=tsp.IdTaiSan where TinhTrang=? order by MaPhong";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, tt);
            rs=ps.executeQuery();
            while (rs.next()) {                
                int idTS, soLuong;
                String maPhong, ghiChu, tenTaiSan, idTSP;
                boolean tinhTrang;
                
                idTSP = rs.getString(1);
                idTS = rs.getInt(2);
                tenTaiSan = rs.getString(3);
                maPhong = rs.getString(4);
                soLuong = rs.getInt(5);
                tinhTrang = rs.getBoolean(6);
                ghiChu = rs.getString(7);
                model.TaiSanPhong.ModelTaiSanPhong tsp = new ModelTaiSanPhong(idTSP, idTS, tenTaiSan, maPhong, soLuong, tinhTrang, ghiChu);
                arrTSP.add(tsp);
            }
            return arrTSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
      public ArrayList<model.HopDong.ModelHopDong> getCbbMaPhong() {
        ArrayList<model.HopDong.ModelHopDong> arrCBBmaphong = new ArrayList<>();
        sql = "select MaPhong from PhongTro order by MaPhong";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String mapt;
                mapt = rs.getString(1).trim();

                ModelHopDong hdg = new ModelHopDong(mapt, mapt);
                arrCBBmaphong.add(hdg);
            }
            return arrCBBmaphong;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
      
      public ArrayList<model.TaiSanPhong.ModelTaiSanPhong> getCbbTaiSan(){
          ArrayList<model.TaiSanPhong.ModelTaiSanPhong> arrCBBTS = new ArrayList<>();
          sql="select IdTaiSan from TaiSan order by IdTaiSan";
          try {
              con=DBContext.getConnection();
              ps = con.prepareStatement(sql);
              rs = ps.executeQuery();
              while (rs.next()) {                  
                  int Id;
                  Id = rs.getInt(1);
                  ModelTaiSanPhong  tsp = new ModelTaiSanPhong(Id);
                  arrCBBTS.add(tsp);
              }
              return arrCBBTS;
          } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
      }
      
    public String getTenTS (int id){
        sql="select TenTaiSan from TaiSan where IdTaiSan=?";
        try {
            String ten = null;
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, id);
            rs=ps.executeQuery();
            while (rs.next()) {                
                
                ten= rs.getString(1);
                
            }
            return ten;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public int them(model.TaiSanPhong.ModelTaiSanPhong x){
        sql="INSERT INTO TaiSanPhong (IdTaiSan, MaPhong, SoLuong, TinhTrang, GhiChu) VALUES (?,?,?,?,?)";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, x.getIdTaiSan());
            ps.setObject(2, x.getMaPhong());
            ps.setObject(3, x.getSoLuong());
            ps.setObject(4, x.isTinhTrang());
            ps.setObject(5, x.getGhiChu());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int sua(model.TaiSanPhong.ModelTaiSanPhong x) {
        sql="update TaiSanPhong set IdTaiSan=?, MaPhong=?, SoLuong=?,TinhTrang=?, GhiChu=? where IdTaiSanPhong=?";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, x.getIdTaiSan());
            ps.setObject(2, x.getMaPhong());
            ps.setObject(3, x.getSoLuong());
            ps.setObject(4, x.isTinhTrang());
            ps.setObject(5, x.getGhiChu());
            ps.setObject(6, x.getIdTaiSanPhong());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int check(String maPhong){
        sql="select IdTaiSan from TaiSanPhong where MaPhong=?";
        try {
            int Id=0;
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1, maPhong);
            rs=ps.executeQuery();
            while (rs.next()) {                
                Id=rs.getInt(1);
            }
            return Id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
        public ArrayList<model.TaiSanPhong.ModelTaiSanPhong> TimKiem(String tk,int tt){
        ArrayList<model.TaiSanPhong.ModelTaiSanPhong> arrTSP = new ArrayList<>();
        sql="select IdTaiSanPhong, tsp.IdTaiSan, ts.TenTaiSan, MaPhong, tsp.SoLuong, TinhTrang, tsp.GhiChu  from TaiSanPhong tsp join TaiSan ts on ts.IdTaiSan=tsp.IdTaiSan "
        +"where (tsp.IdTaiSan LIKE ? or ts.TenTaiSan LIKE ?or MaPhong LIKE ?) and TinhTrang = ?  order by MaPhong";
        try {
            con=DBContext.getConnection();
            ps=con.prepareStatement(sql);
            ps.setObject(1,"%"+tk+"%");
            ps.setObject(2,"%"+tk+"%");
            ps.setObject(3,"%"+tk+"%");
            ps.setObject(4,tt);
            rs=ps.executeQuery();
            while (rs.next()) {                
                int idTS, soLuong;
                String maPhong, ghiChu, tenTaiSan, idTSP;
                boolean tinhTrang;
                
                idTSP = rs.getString(1);
                idTS = rs.getInt(2);
                tenTaiSan = rs.getString(3);
                maPhong = rs.getString(4);
                soLuong = rs.getInt(5);
                tinhTrang = rs.getBoolean(6);
                ghiChu = rs.getString(7);
                model.TaiSanPhong.ModelTaiSanPhong tsp = new ModelTaiSanPhong(idTSP, idTS, tenTaiSan, maPhong, soLuong, tinhTrang, ghiChu);
                arrTSP.add(tsp);
            }
            return arrTSP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
