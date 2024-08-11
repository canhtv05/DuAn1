/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.KhachThue;

import dao.DBContext;
import java.sql.*;
import java.util.ArrayList;
import model.KhachThue.ModelKhachThue;

/**
 *
 * @author X1
 */
public class repositoriesKhachThue {

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql = null;

    public repositoriesKhachThue() {
        try {
            con = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<model.KhachThue.ModelKhachThue> getCBB() {
        ArrayList<model.KhachThue.ModelKhachThue> arrCBB = new ArrayList<>();
        sql = "select MaPhong from PhongTro order by MaPhong";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String mapt;
                mapt = rs.getString(1).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(mapt);
                arrCBB.add(kt);
            }
            return arrCBB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<model.KhachThue.ModelKhachThue> getAll(int tt) {
        ArrayList<model.KhachThue.ModelKhachThue> arrKT = new ArrayList<>();
        sql = "select IdKhach, MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi, CCCD from KhachThue where TrangThai=?  order by MaPhong";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tt);
            rs = ps.executeQuery();
            while (rs.next()) {
                String maKT, hoTen, dienThoai, email, diaChi, cccd, maPT;
                Date ngaySinh;
                boolean gioiTinh;

                maKT = rs.getString(1).trim();
                hoTen = rs.getString(3).trim();
                ngaySinh = rs.getDate(4);
                gioiTinh = rs.getBoolean(5);
                dienThoai = rs.getString(6).trim();
                email = rs.getString(7).trim();
                diaChi = rs.getString(8).trim();
                cccd = rs.getString(9).trim();
                maPT = rs.getString(2).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(maKT, maPT, hoTen, ngaySinh, gioiTinh, dienThoai, email, diaChi, cccd);
                arrKT.add(kt);
            }
            return arrKT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int them(model.KhachThue.ModelKhachThue x) {
        sql = "insert into KhachThue(MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi , CCCD, TrangThai) values(?,?,?,?,?,?,?,?,0)";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, x.getMaPT());
            ps.setObject(2, x.getHoTen());
            ps.setObject(3, x.getNgaySinh());
            ps.setObject(4, x.isGioiTinh());
            ps.setObject(5, x.getDienThoai());
            ps.setObject(6, x.getEmail());
            ps.setObject(7, x.getDiaChi());
            ps.setObject(8, x.getCCCD());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int sua(model.KhachThue.ModelKhachThue x) {
        sql = " update KhachThue set MaPhong=?, HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, DiaChi=?, CCCD=? where IdKhach=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, x.getMaPT());
            ps.setObject(2, x.getHoTen());
            ps.setObject(3, x.getNgaySinh());
            ps.setObject(4, x.isGioiTinh());
            ps.setObject(5, x.getDienThoai());
            ps.setObject(6, x.getEmail());
            ps.setObject(7, x.getDiaChi());
            ps.setObject(8, x.getCCCD());
            ps.setObject(9, x.getMaKT());
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int xoa(int x) {
        sql = "update KhachThue set TrangThai=1 where IdKhach=?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, x);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<model.KhachThue.ModelKhachThue> timKiem(String mapt, int TrangThai) {
        ArrayList<model.KhachThue.ModelKhachThue> arrKT = new ArrayList<>();
        sql = "select IdKhach, MaPhong, HoTen, NgaySinh, GioiTinh, DienThoai, Email, DiaChi, CCCD from KhachThue   where (MaPhong LIKE ? or IdKhach LIKE ? or HoTen LIKE ? or DienThoai LIKE ? or Email LIKE ?) and TrangThai = ?";
        try {
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, "%" + mapt + "%");
            ps.setObject(2, "%" + mapt + "%");
            ps.setObject(3, "%" + mapt + "%");
            ps.setObject(4, "%" + mapt + "%");
            ps.setObject(5, "%" + mapt + "%");
            ps.setObject(6, TrangThai);

            rs = ps.executeQuery();
            while (rs.next()) {
                String maKT, hoTen, dienThoai, email, diaChi, cccd, maPT;
                Date ngaySinh;
                boolean gioiTinh;

                maKT = rs.getString(1).trim();
                hoTen = rs.getString(3).trim();
                ngaySinh = rs.getDate(4);
                gioiTinh = rs.getBoolean(5);
                dienThoai = rs.getString(6).trim();
                email = rs.getString(7).trim();
                diaChi = rs.getString(8).trim();
                cccd = rs.getString(9).trim();
                maPT = rs.getString(2).trim();
                model.KhachThue.ModelKhachThue kt = new ModelKhachThue(maKT, maPT, hoTen, ngaySinh, gioiTinh, dienThoai, email, diaChi, cccd);
                arrKT.add(kt);
            }
            return arrKT;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String check(String ma) {
        sql = "select IdKhach from KhachThue where IdKhach=?";
        try {
            String makt = null;
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, ma);

            rs = ps.executeQuery();
            while (rs.next()) {
                makt = rs.getString(1);
            }
            return makt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String CheckSDT(String MaKT) {
        sql = "select DienThoai from KhachThue where DienThoai=?";
        try {
            String dt = null;
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, MaKT);
            rs = ps.executeQuery();
            while (rs.next()) {

                dt = rs.getString(1);

            }
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String CheckEmail(String email) {
        sql = "select Email from KhachThue where Email=?";
        try {
            String dt = null;
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {

                dt = rs.getString(1);

            }
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String CheckCCCD(String CC) {
        sql = "select CCCD from KhachThue where CCCD=?";
        try {
            String dt = null;
            con = DBContext.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, CC);
            rs = ps.executeQuery();
            while (rs.next()) {

                dt = rs.getString(1);

            }
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkTen(String ten) {
        return !ten.matches(".*\\d.*");
    }

    public boolean checkSoDT(String sdt) {
//        return sdt.matches("^\\d{10}$");
        return sdt.matches("^(09|03|08|02)\\d{8}$");
    }

    public boolean isNumber(String sdt) {
//        try {
//            Long.valueOf(sdt);
//        } catch (NumberFormatException e) {
//            return false;
//        }
//        return true;
        return sdt.matches(".*\\d.*");
    }

    public boolean checkemailNC(String email) {
        String bieuThuc = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(bieuThuc);
    }

    public boolean checkCC(String cccd) {
        return cccd.matches("^\\d{12}$");
    }
}
