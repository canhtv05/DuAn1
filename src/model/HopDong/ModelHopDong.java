/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.HopDong;

import java.util.Date;

/**
 *
 * @author X1
 */
public class ModelHopDong {
    String maHD;
    String maPT;
    String maKT;
    int soNguoi;
    Date NgayBatDau;
    Date NgayKetThuc;
    int thoiHan;
    float giaPhong;
    float soTienCoc;
    String DieuKhoan;
    boolean trangThai;

   
    
    
    
    public String TrangThai(){
        if (trangThai) {
            return "hết hạn";
        } else {
            return "còn hạn";
        }
    }

    public ModelHopDong(String maPT, String maKT) {
        this.maPT = maPT;
        this.maKT = maKT;
    }
    
    
    
    
   
    
    
        
       
    
    
    public ModelHopDong(String maHD, String maPT, String maKT, int soNguoi, Date NgayBatDau, Date NgayKetThuc, int thoiHan, float giaPhong, float soTienCoc, String DieuKhoan, boolean trangThai) {
        this.maHD = maHD;
        this.maPT = maPT;
        this.maKT = maKT;
        this.soNguoi = soNguoi;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
        this.thoiHan = thoiHan;
        this.giaPhong = giaPhong;
        this.soTienCoc = soTienCoc;
        this.DieuKhoan = DieuKhoan;
        this.trangThai = trangThai;
    }

    public ModelHopDong() {
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public String getMaKT() {
        return maKT;
    }

    public void setMaKT(String maKT) {
        this.maKT = maKT;
    }

    public int getSoNguoi() {
        return soNguoi;
    }

    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public int getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(int thoiHan) {
        this.thoiHan = thoiHan;
    }

    public float getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(float giaPhong) {
        this.giaPhong = giaPhong;
    }

    public float getSoTienCoc() {
        return soTienCoc;
    }

    public void setSoTienCoc(float soTienCoc) {
        this.soTienCoc = soTienCoc;
    }

    public String getDieuKhoan() {
        return DieuKhoan;
    }

    public void setDieuKhoan(String DieuKhoan) {
        this.DieuKhoan = DieuKhoan;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
    public Object[] toDataRow(){
        return new Object[]{this.getMaHD(), this.getMaPT(), this.getMaKT(), this.getSoNguoi(), this.getNgayBatDau(), this.getNgayKetThuc(), this.getThoiHan(), this.getGiaPhong(), this.getSoTienCoc(), this.DieuKhoan, this.TrangThai()};
    }
    
    
}
