/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.Tang;

import dao.DBContext;
import model.Tang.ModelTang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class repoTang {

    private Connection conn;

    public repoTang() {
        try {
            this.conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTang> findAll() {
        ArrayList<ModelTang> listT = new ArrayList<>();

        String sql = """
                     Select TangSo, SoPhong, GhiChu From Tang;
                     """;

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                int TangSo = rs.getInt("TangSo");
                int soPhong = rs.getInt("SoPhong");
                String GhiChu = rs.getString("GhiChu");

                ModelTang mdt = new ModelTang(TangSo, soPhong, GhiChu);
                listT.add(mdt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listT;
    }

    public void insert(ModelTang mdt) {
        String sql = """
                     Insert into Tang(SoPhong, GhiChu) Values (?, ?);
                     """;

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, mdt.getSoPhong());
            ps.setString(2, mdt.getGhiChu());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(ModelTang mdt) {
        String sql = """
                     Update Tang Set SoPhong = ?, GhiChu = ? Where TangSo = ?;
                     """;

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, mdt.getSoPhong());
            ps.setString(2, mdt.getGhiChu());
            ps.setInt(3, mdt.getTangSo());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
