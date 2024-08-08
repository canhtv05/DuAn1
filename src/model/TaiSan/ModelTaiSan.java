/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.TaiSan;

public class ModelTaiSan {

    private int idTaiSan;
    private String tenTaiSan;
    private float giaTaiSan;
    private int soLuong;
    private String ghiChu;

    public int getIdTaiSan() {
        return idTaiSan;
    }

    public void setIdTaiSan(int idTaiSan) {
        this.idTaiSan = idTaiSan;
    }

    public String getTenTaiSan() {
        return tenTaiSan;
    }

    public void setTenTaiSan(String tenTaiSan) {
        this.tenTaiSan = tenTaiSan;
    }

    public float getGiaTaiSan() {
        return giaTaiSan;
    }

    public void setGiaTaiSan(float giaTaiSan) {
        this.giaTaiSan = giaTaiSan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public ModelTaiSan() {

    }

    public ModelTaiSan(int idTaiSan, String tenTaiSan, float giaTaiSan, int soLuong, String ghiChu) {
        this.idTaiSan = idTaiSan;
        this.tenTaiSan = tenTaiSan;
        this.giaTaiSan = giaTaiSan;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }
    
    public ModelTaiSan(String tenTaiSan, float giaTaiSan, int soLuong, String ghiChu) {
        this.tenTaiSan = tenTaiSan;
        this.giaTaiSan = giaTaiSan;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }

}
