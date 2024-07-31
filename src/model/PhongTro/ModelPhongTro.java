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

    private int TangSo;
    private String maPhong;
    private String loaiPhong;
    private float dienTich;
    private float giaPhong;
    private String tienNghi;
    private int trangThai;
    private String anh;

    public int getTangSo() {
        return TangSo;
    }

    public void setTangSo(int TangSo) {
        this.TangSo = TangSo;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public ModelPhongTro() {

    }

    public ModelPhongTro(int TangSo, String maPhong, String loaiPhong, float dienTich, float giaPhong, String tienNghi, int trangThai, String anh) {
        this.TangSo = TangSo;
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.tienNghi = tienNghi;
        this.trangThai = trangThai;
        this.anh = anh;
    }

}
