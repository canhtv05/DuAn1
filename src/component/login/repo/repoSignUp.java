package component.login.repo;
import component.login.model.modelLogin;
import dao.DBContext;
import java.sql.*;

public class repoSignUp {
    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public repoSignUp() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void register(modelLogin login) {
        sql = "Insert Into DangNhap(TenDangNhap, MatKhau, VaiTro) Values (?, ?, 0)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isDuplicate(String username) {
        sql = "SELECT TenDangNhap FROM dbo.DangNhap WHERE TenDangNhap = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            rs = ps.getResultSet();
            while(rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
