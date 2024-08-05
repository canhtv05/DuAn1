package repository.doimk;

import java.sql.*;
import dao.DBContext;
import java.util.ArrayList;
import model.doimk.ModelDoiMK;

public class RepoDoiMK {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoDoiMK() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<ModelDoiMK> getAll() {
        ArrayList<ModelDoiMK> list = new ArrayList<>();
        sql = "SELECT TenDangNhap, MaNhanVien, MatKhau, VaiTro FROM dbo.DangNhap";
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                ModelDoiMK model = new ModelDoiMK(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean existTK(String tk) {
        sql = "SELECT COUNT(*) FROM dbo.DangNhap WHERE TenTaiKhoan = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tk);
            rs = ps.executeQuery();
            if(rs.next()) return rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//    
//    public boolean add(String tk, String mk) {
//        sql = ""
//    }
}
