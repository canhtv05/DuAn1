package model.tiendichvu;

public class ModelTienDichVuChiTiet {

    private int maTDV;
    private int maHD;
    private String maPT;
    private String ngayBD;
    private String ngayKT;
    private int dauNguoi;
    private double thanhTien;
    private double tongTien;

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public ModelTienDichVuChiTiet() {
    }

    public ModelTienDichVuChiTiet(int maTDV, int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi, double tongTien, double thanhTien) {
        this.maTDV = maTDV;
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.thanhTien = thanhTien;
        this.tongTien = tongTien;
    }

    public ModelTienDichVuChiTiet(int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi, double tongTien, double thanhTien) {
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.tongTien = tongTien;
        this.thanhTien = thanhTien;
    }

    public ModelTienDichVuChiTiet(int maTDV,int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi) {
        this.maTDV = maTDV;
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
    }

    public ModelTienDichVuChiTiet(int maHD, String maPT, String ngayBD, String ngayKT, int dauNguoi) {
        this.maHD = maHD;
        this.maPT = maPT;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
    }

    public int getMaTDV() {
        return maTDV;
    }

    public void setMaTDV(int maTDV) {
        this.maTDV = maTDV;
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

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

}
