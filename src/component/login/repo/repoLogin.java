/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component.login.repo;

/**
 *
 * @author CanhPC
 */

import component.login.model.modelLogin;
import java.sql.*;
import dao.DBContext;

public class repoLogin {
    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public repoLogin() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public modelLogin checkLogin(modelLogin login) {
        sql = "SELECT TenDangNhap, MatKhau, Vaitro FROM dbo.DangNhap WHERE TenDangNhap = ? AND MatKhau = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, login.getUsername());
            ps.setString(2, login.getPassword());
            ps.execute();
            
            rs = ps.getResultSet();
            while(rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                int role = rs.getInt(3);
                
                return new modelLogin(username, password, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
