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

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public repoPhongTro() {
        try {
            this.conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelPhongTro> getAll() {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        sql = """
                    Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro order by TangSo;
                  """;
        try {
            ps = this.conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int TangSo = rs.getInt("TangSo");
                String maPhong = rs.getString("MaPhong");
                String loaiPhong = rs.getString("LoaiPhong");
                float dienTich = rs.getFloat("DienTich");
                float giaPhong = rs.getFloat("GiaPhong");
                String tienNghi = rs.getString("TienNghi");
                int trangThai = rs.getInt("TrangThai");
                String Anh = rs.getString("Anh");

                ModelPhongTro pt = new ModelPhongTro(TangSo, maPhong, loaiPhong, dienTich, giaPhong, tienNghi, trangThai, Anh);
                listPT.add(pt);
            }
            return listPT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ModelPhongTro> getCbbTang() {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        sql = "Select TangSo From Tang order by TangSo";

        try {
            ps = this.conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int tangSo = rs.getInt(1);

                ModelPhongTro pt = new ModelPhongTro(tangSo);
                listPT.add(pt);
            }
            return listPT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insert(ModelPhongTro pt) {
        sql = """
                Insert into PhongTro (TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh) Values (?, ?, ?, ?, ?, ?, ?, ?);
              """;
        try {
            ps = this.conn.prepareStatement(sql);

            ps.setInt(1, pt.getTangSo());
            ps.setString(2, pt.getMaPhong());
            ps.setString(3, pt.getLoaiPhong());
            ps.setFloat(4, pt.getDienTich());
            ps.setFloat(5, pt.getGiaPhong());
            ps.setString(6, pt.getTienNghi());
            ps.setInt(7, pt.getTrangThai());
            ps.setString(8, pt.getAnh());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(ModelPhongTro pt) {
        sql = "Update PhongTro Set TangSo = ?, LoaiPhong = ?, DienTich = ?, GiaPhong = ?, TienNghi = ?, TrangThai = ?, Anh = ? Where MaPhong = ? ";

        try {
            ps = this.conn.prepareStatement(sql);

            ps.setInt(1, pt.getTangSo());
            ps.setString(2, pt.getLoaiPhong());
            ps.setFloat(3, pt.getDienTich());
            ps.setFloat(4, pt.getGiaPhong());
            ps.setString(5, pt.getTienNghi());
            ps.setInt(6, pt.getTrangThai());
            ps.setString(7, pt.getAnh());
            ps.setString(8, pt.getMaPhong());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(ModelPhongTro pt) {
        sql = "Update PhongTro Set TrangThai = 2 Where MaPhong = ? ";

        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, pt.getMaPhong());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<ModelPhongTro> Search(String textSearch) {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        sql = "Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro Where MaPhong LIKE ? Or LoaiPhong LIKE ? Or TienNghi LIKE ?";
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + textSearch + "%");
            ps.setString(2, "%" + textSearch + "%");
            ps.setString(3, "%" + textSearch + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                listPT.add(new ModelPhongTro(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getFloat(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8)
                ));
            }
            return listPT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ModelPhongTro> filterTrangThai(int tt) {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        sql = "Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro Where TrangThai = ?";

        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, tt);
            rs = ps.executeQuery();

            while (rs.next()) {
                listPT.add(new ModelPhongTro(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getFloat(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8)
                ));
            }
            return listPT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ModelPhongTro> filterPrice(float min, float max) {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        sql = "Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro Where GiaPhong >= ? And GiaPhong <= ?";

        try {
            ps = this.conn.prepareStatement(sql);
            ps.setFloat(1, min);
            ps.setFloat(2, max);
            rs = ps.executeQuery();

            while (rs.next()) {
                listPT.add(new ModelPhongTro(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getFloat(4),
                        rs.getFloat(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8)
                ));
            }
            return listPT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String checkMaPhong(String maPhong) {
        sql = """
                Select MaPhong From Phongtro Where MaPhong = ?
              """;
        String maCheck = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, maPhong);
            rs = ps.executeQuery();

            while (rs.next()) {
                maCheck = rs.getString(1);
            }
            return maCheck;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public int checkSoPhong() {
//        MessageFrame msg = new MessageFrame();
//        String sql1 = """
//                        Select COUNT(*) AS 'phongAll' From PhongTro Where TangSo = ?
//                      """;
//        
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
    public int getSoPhong(int soPhong) {
        int allPhong = 0;
        sql = """
                Select SoPhong From Tang Where TangSo = ?
              """;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, soPhong);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                allPhong = rs.getInt("SoPhong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
                
        return allPhong;
    }
    
    //Hàm đếm số phòng hiện có trong tầng
    public int getDemPhong(int soPhong) {
        int demPhong = 0;

        sql = """
              Select COUNT(*) AS DemPhong From PhongTro Where TangSo = (Select TangSo From Tang Where TangSo = ?)
              """;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, soPhong);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                demPhong = rs.getInt("DemPhong");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return demPhong;
    }

//    public int checkSoPhong2(int soPhong) {
//        sql = """
//              Select SoPhong From Tang Where TangSo = ?;
//              """;
//        int checkSoPhong2 = 0;
//        try {
//            ps = this.conn.prepareStatement(sql);
//            ps.setInt(1, soPhong);
//            rs = ps.executeQuery();
//            
//            while (rs.next()) {                
//                checkSoPhong2 = rs.getInt(1);
//            }
//            return checkSoPhong2;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
}
