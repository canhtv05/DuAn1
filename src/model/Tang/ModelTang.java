/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tang;

public class ModelTang {

    private int TangSo;
    private int soPhong;
    private String ghiChu;

    public int getTangSo() {
        return TangSo;
    }

    public void setTangSo(int TangSo) {
        this.TangSo = TangSo;
    }

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public ModelTang() {

    }

    public ModelTang(int TangSo, int soPhong, String ghiChu) {
        this.TangSo = TangSo;
        this.soPhong = soPhong;
        this.ghiChu = ghiChu;
    }

    public ModelTang(int soPhong) {
        this.soPhong = soPhong;
    }

    public ModelTang(int soPhong, String ghiChu) {
        this.soPhong = soPhong;
        this.ghiChu = ghiChu;
    }
    
    

}
