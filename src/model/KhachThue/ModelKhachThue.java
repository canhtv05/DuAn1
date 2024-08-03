/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.KhachThue;

import java.util.Date;

/**
 *
 * @author X1
 */
public class ModelKhachThue {
    private String MaKT;
    private String HoTen;
    private Date ngaySinh;
    private boolean gioiTinh;
    private String DienThoai;
    private String email;
    private String DiaChi;
    private String CCCD;
    private String MaPT;

    
    public String GioiTinh(){
        if (gioiTinh) {
            return "Nữ";
        } else {
            return "Nam";
        }
    }
    public ModelKhachThue() {
    }
    
    //arr combo box

    public ModelKhachThue(String MaPT) {
        this.MaPT = MaPT;
    }
    
    
    public ModelKhachThue(String MaKT, String MaPT, String HoTen, Date ngaySinh, boolean gioiTinh, String DienThoai, String email, String DiaChi, String CCCD) {
        this.MaKT = MaKT;
        this.MaPT = MaPT;
        this.HoTen = HoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.DienThoai = DienThoai;
        this.email = email;
        this.DiaChi = DiaChi;
        this.CCCD = CCCD;
        
    }

    public String getMaKT() {
        return MaKT;
    }

    public void setMaKT(String MaKT) {
        this.MaKT = MaKT;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }
    
    public Object[] toDataRow(){
        return new Object[]{this.getMaKT(), this.getMaPT(), this.getHoTen(), this.getNgaySinh(), this.GioiTinh(), this.DienThoai, this.getEmail(), this.getDiaChi(), this.getCCCD()};
    }
    
    public Object[] bangPhu(){
        return new Object[]{this.getMaKT(), this.getHoTen(), this.GioiTinh(), this.getDienThoai(), this.getCCCD()};
    }
}
