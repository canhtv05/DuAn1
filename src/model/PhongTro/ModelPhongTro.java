/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.PhongTro;

/**
 *
 * @author chung
 */
public class ModelPhongTro {
    private String maPT;
    private String loaiPhong;
    private float dienTich;
    private float giaPhong;
    private String tienNghi;
    private int trangThai;

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public float getDienTich() {
        return dienTich;
    }

    public void setDienTich(float dienTich) {
        this.dienTich = dienTich;
    }

    public float getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(float giaPhong) {
        this.giaPhong = giaPhong;
    }

    public String getTienNghi() {
        return tienNghi;
    }

    public void setTienNghi(String tienNghi) {
        this.tienNghi = tienNghi;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public ModelPhongTro() {

    }

    public ModelPhongTro(String maPT, String loaiPhong, float dienTich, float giaPhong, String tienNghi, int trangThai) {
        this.maPT = maPT;
        this.loaiPhong = loaiPhong;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.tienNghi = tienNghi;
        this.trangThai = trangThai;
    }
    
}
