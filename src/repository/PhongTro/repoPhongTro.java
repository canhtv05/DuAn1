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

    public repoPhongTro() {
        try {
            this.conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelPhongTro> findAll() {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();

        try {
            String sql = """
                         Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro;
                         """;
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();

            ResultSet rs = ps.getResultSet();

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPT;
    }

    public void insert(ModelPhongTro pt) {
        try {
            String sql = """
                         Insert into PhongTro (TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh) Values (?, ?, ?, ?, ?, ?, ?, ?);
                         """;
            PreparedStatement ps = this.conn.prepareStatement(sql);

            ps.setInt(1, pt.getTangSo());
            ps.setString(2, pt.getMaPhong());
            ps.setString(3, pt.getLoaiPhong());
            ps.setFloat(4, pt.getDienTich());
            ps.setFloat(5, pt.getGiaPhong());
            ps.setString(6, pt.getTienNghi());
            ps.setInt(7, pt.getTrangThai());
            ps.setString(8, pt.getAnh());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update(ModelPhongTro pt) {
        try {
            String sql = "Update PhongTro Set TangSo = ?, LoaiPhong = ?, DienTich = ?, GiaPhong = ?, TienNghi = ?, TrangThai = ?, Anh = ? Where MaPhong = ? ";
            PreparedStatement ps = this.conn.prepareStatement(sql);

            ps.setInt(1, pt.getTangSo());
            ps.setString(2, pt.getLoaiPhong());
            ps.setFloat(3, pt.getDienTich());
            ps.setFloat(4, pt.getGiaPhong());
            ps.setString(5, pt.getTienNghi());
            ps.setInt(6, pt.getTrangThai());
            ps.setString(7, pt.getAnh());
            ps.setString(8, pt.getMaPhong());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(ModelPhongTro pt) {
        try {
            String sql = "";
            PreparedStatement ps = this.conn.prepareStatement(sql);

            ps.setInt(1, pt.getTangSo());
            ps.setString(2, pt.getLoaiPhong());
            ps.setFloat(3, pt.getDienTich());
            ps.setFloat(4, pt.getGiaPhong());
            ps.setString(5, pt.getTienNghi());
            ps.setInt(6, pt.getTrangThai());
            ps.setString(7, pt.getAnh());
            ps.setString(8, pt.getMaPhong());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public ArrayList<ModelPhongTro> Search(String textSearch) {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        String sql = "Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh From PhongTro Where MaPhong LIKE ? Or LoaiPhong LIKE ? Or TienNghi LIKE ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + textSearch + "%");
            ps.setString(2, "%" + textSearch + "%");
            ps.setString(3, "%" + textSearch + "%");
            ps.execute();

            ResultSet rs = ps.getResultSet();

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPT;
    }

    public ArrayList<ModelPhongTro> filterTrangThai(int trangThai) {
        ArrayList<ModelPhongTro> listPT = new ArrayList<>();
        String sql = "Select TangSo, MaPhong, LoaiPhong, DienTich, GiaPhong, TienNghi, TrangThai, Anh Where TrangThai = ?";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, trangThai);
            ps.execute();

            ResultSet rs = ps.getResultSet();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPT;
    }

}
