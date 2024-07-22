/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.NhanVien;
import model.NhanVien.Model_ChamCong;
import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author BOSS
 */
public class repoChamCong {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;
    
    public ArrayList<Model_ChamCong> getAll2(){
        ArrayList<Model_ChamCong> list_ChamCong = new ArrayList<>();
        sql ="select MaNV,NgayCong,DiemDanh from ChamCong";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                Model_ChamCong ChamC = Model_ChamCong.builder()
                        .maNV(rs.getString(1))
                        .NgayCong(rs.getDate(2))
                        .diemDanh(rs.getInt(3))
                        .build();
                list_ChamCong.add(ChamC);
            }
            
        } catch (Exception e) {
             e.printStackTrace();
        }
        return list_ChamCong;
    }
}
