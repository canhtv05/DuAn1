package model.tiendichvu;

public class ModelTienDichVu {
    private int idDV;
    private String tenDV;
    private double giaTien;

    public ModelTienDichVu() {
    }

    public ModelTienDichVu(int idDV, String tenDV, double giaTien) {
        this.idDV = idDV;
        this.tenDV = tenDV;
        this.giaTien = giaTien;
    }

    public ModelTienDichVu(String tenDV, double giaTien) {
        this.tenDV = tenDV;
        this.giaTien = giaTien;
    }
    

    public int getIdDV() {
        return idDV;
    }

    public void setIdDV(int idDV) {
        this.idDV = idDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }
    
}
