/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.TaiSanPhong;

/**
 *
 * @author X1
 */
public class ModelTaiSanPhong {
    private String IdTaiSanPhong;
    private int IdTaiSan;
    private String MaPhong;
    private int SoLuong;
    private boolean TinhTrang;
    private String GhiChu;
    
    private String tenTS;

    public String getTenTS() {
        return tenTS;
    }

    public void setTenTS(String tenTS) {
        this.tenTS = tenTS;
    }
    public String tinhTrang(){
        if (TinhTrang) {
            return "Đang bảo dưỡng";
        } else {
            return "Còn hoạt động";
        }
    }

    public ModelTaiSanPhong(int IdTaiSan) {
        this.IdTaiSan = IdTaiSan;
    }
    
    
    

    public ModelTaiSanPhong(String IdTaiSanPhong, int IdTaiSan,String tenTS, String MaPhong, int SoLuong, boolean TinhTrang, String GhiChu) {
        this.IdTaiSanPhong = IdTaiSanPhong;
        this.IdTaiSan = IdTaiSan;
        this.tenTS = tenTS;
        this.MaPhong = MaPhong;
        this.SoLuong = SoLuong;
        this.TinhTrang = TinhTrang;
        this.GhiChu = GhiChu;
    }

    public ModelTaiSanPhong() {
    }

    public String getIdTaiSanPhong() {
        return IdTaiSanPhong;
    }

    public void setIdTaiSanPhong(String IdTaiSanPhong) {
        this.IdTaiSanPhong = IdTaiSanPhong;
    }

    public int getIdTaiSan() {
        return IdTaiSan;
    }

    public void setIdTaiSan(int IdTaiSan) {
        this.IdTaiSan = IdTaiSan;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String MaPhong) {
        this.MaPhong = MaPhong;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public boolean isTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(boolean TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
    public Object[] toDataRow(){
        return new Object[]{this.getIdTaiSanPhong(), this.getIdTaiSan(), this.getTenTS(),this.getMaPhong(),this.getSoLuong(), this.tinhTrang(), this.getGhiChu()};
    }
}
