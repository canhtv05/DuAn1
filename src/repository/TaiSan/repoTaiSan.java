/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.TaiSan;

import dao.DBContext;
import model.TaiSan.ModelTaiSan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class repoTaiSan {

    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public repoTaiSan() {
        try {
            this.conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ModelTaiSan> getAll() {
        ArrayList<ModelTaiSan> listTS = new ArrayList<>();
        sql = """
              Select IdTaiSan, TenTaiSan, GiaTaiSan ,SoLuong, GhiChu from TaiSan
              """;

        try {
            ps = this.conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                listTS.add(new ModelTaiSan(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getInt(4),
                        rs.getString(5)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTS;
    }

    public int insert(ModelTaiSan ts) {
        sql = """
                Insert into TaiSan(TenTaiSan, GiaTaiSan, SoLuong, GhiChu) Values (?, ?, ?, ?);
              """;

        try {
            ps = this.conn.prepareStatement(sql);

            ps.setString(1, ts.getTenTaiSan());
            ps.setFloat(2, ts.getGiaTaiSan());
            ps.setInt(3, ts.getSoLuong());
            ps.setString(4, ts.getGhiChu());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(ModelTaiSan ts) {
        sql = """
                Update TaiSan Set TenTaiSan = ?, GiaTaiSan = ?, SoLuong = ?, GhiChu = ? Where IdTaiSan = ?
              """;

        try {
            ps = this.conn.prepareStatement(sql);

            ps.setString(1, ts.getTenTaiSan());
            ps.setFloat(2, ts.getGiaTaiSan());
            ps.setInt(3, ts.getSoLuong());
            ps.setString(4, ts.getGhiChu());
            ps.setInt(5, ts.getIdTaiSan());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int delete(ModelTaiSan ts) {
        sql = "Delete From TaiSanPhong Where IdTaiSan = ?;"
                + "Delete From TaiSan Where IdTaiSan = ?;";

        try {
            ps = this.conn.prepareStatement(sql);

            ps.setInt(1, ts.getIdTaiSan());
            ps.setInt(2, ts.getIdTaiSan());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<ModelTaiSan> Search(String result) {
        ArrayList<ModelTaiSan> listTS = new ArrayList<>();
        sql = """
              Select IdTaiSan, TenTaiSan, GiaTaiSan ,SoLuong, GhiChu from TaiSan Where TenTaiSan Like ?
              """;

        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + result + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                listTS.add(new ModelTaiSan(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getInt(4),
                        rs.getString(5)
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTS;
    }

}
