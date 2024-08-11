package repository.login;

import model.login.ModelLogin;
import java.sql.*;
import dao.DBContext;

public class RepoLogin {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoLogin() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ModelLogin checkLogin(ModelLogin login) {
        sql = "SELECT TenDangNhap, MatKhau, Vaitro FROM dbo.DangNhap WHERE TenDangNhap = ? AND MatKhau = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.execute();

            rs = ps.getResultSet();
            while (rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                int role = rs.getInt(3);

                return new ModelLogin(username, password, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String xacNhanMK(String tenDangNhap) {
        sql = "SELECT MatKhau FROM dbo.DangNhap WHERE TenDangNhap = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(String tkString, String mkNew) {
        sql = "UPDATE dbo.DangNhap "
                + "SET MatKhau = ? "
                + "WHERE TenDangNhap = ? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mkNew);
            ps.setString(2, tkString);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existTK(String tenDangNhap) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
