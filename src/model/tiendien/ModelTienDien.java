package model.tiendien;

public class ModelTienDien {

    private int maTD;
    private String maHD;
    private String maPT;
    private String ngayBD;
    private String ngayKT;
    private int chiSoDau;
    private int chiSoCuoi;
    private int soDien;
    private double giaTien;
    private double thanhTien;

    public ModelTienDien() {
    }

    public ModelTienDien(int maTD, String maHD, String maPT, String ngayBD, String ngayKT, int chiSoDau, int chiSoCuoi, int soDien, double giaTien, double thanhTien) {
        this.maTD = maTD;
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.chiSoDau = chiSoDau;
        this.chiSoCuoi = chiSoCuoi;
        this.soDien = soDien;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public int getMaTD() {
        return maTD;
    }

    public void setMaTD(int maTD) {
        this.maTD = maTD;
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

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public int getChiSoDau() {
        return chiSoDau;
    }

    public void setChiSoDau(int chiSoDau) {
        this.chiSoDau = chiSoDau;
    }

    public int getChiSoCuoi() {
        return chiSoCuoi;
    }

    public void setChiSoCuoi(int chiSoCuoi) {
        this.chiSoCuoi = chiSoCuoi;
    }

    public int getSoDien() {
        return soDien;
    }

    public void setSoDien(int soDien) {
        this.soDien = soDien;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
