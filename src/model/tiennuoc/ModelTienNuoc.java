package model.tiennuoc;

public class ModelTienNuoc {
    private int maTN;
    private int maHD;
    private String maPT;
    private String ngayBD;
    private String ngayKT;
    private int dauNguoi;
    private double giaTien;
    private double thanhTien;

    public ModelTienNuoc() {
    }

    public ModelTienNuoc(int maTN, int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi, double giaTien, double thanhTien) {
        this.maTN = maTN;
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

    public ModelTienNuoc(int maTN, int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi, double giaTien) {
        this.maTN = maTN;
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
    }

    public ModelTienNuoc(int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi, double giaTien) {
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
    }

    public int getMaTN() {
        return maTN;
    }

    public void setMaTN(int maTN) {
        this.maTN = maTN;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
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

    public int getDauNguoi() {
        return dauNguoi;
    }

    public void setDauNguoi(int dauNguoi) {
        this.dauNguoi = dauNguoi;
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
