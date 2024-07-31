package model.hoadon;

public class ModelHoaDon {
    private int maHD;
    private String maPT;
    private String maNguoiTao;
    private String ngayLap;
    private String hanThanhToan;
    private int trangThai;
    private String ngayThanhToan;
    private double tienDien;
    private double tienNuoc;
    private double tienDichVu;
    private double tongTien;

    public ModelHoaDon() {
    }

    public ModelHoaDon(int maHD, String maPT, String maNguoiTao, String ngayLap, String hanThanhToan, int trangThai, String ngayThanhToan, double tienDien, double tienNuoc, double tienDichVu, double tongTien) {
        this.maHD = maHD;
        this.maPT = maPT;
        this.maNguoiTao = maNguoiTao;
        this.ngayLap = ngayLap;
        this.hanThanhToan = hanThanhToan;
        this.trangThai = trangThai;
        this.ngayThanhToan = ngayThanhToan;
        this.tienDien = tienDien;
        this.tienNuoc = tienNuoc;
        this.tienDichVu = tienDichVu;
        this.tongTien = tongTien;
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

    public String getMaNguoiTao() {
        return maNguoiTao;
    }

    public void setMaNguoiTao(String maNguoiTao) {
        this.maNguoiTao = maNguoiTao;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getHanThanhToan() {
        return hanThanhToan;
    }

    public void setHanThanhToan(String hanThanhToan) {
        this.hanThanhToan = hanThanhToan;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public double getTienDien() {
        return tienDien;
    }

    public void setTienDien(double tienDien) {
        this.tienDien = tienDien;
    }

    public double getTienNuoc() {
        return tienNuoc;
    }

    public void setTienNuoc(double tienNuoc) {
        this.tienNuoc = tienNuoc;
    }

    public double getTienDichVu() {
        return tienDichVu;
    }

    public void setTienDichVu(double tienDichVu) {
        this.tienDichVu = tienDichVu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
}
