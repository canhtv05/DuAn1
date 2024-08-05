package model.tiendien;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ModelTienDien {

    private int maTD;
    private int maHD;
    private String maPT;
    private String ngayBD;
    private String ngayKT;
    private int chiSoDau;
    private int chiSoCuoi;
    private int soDien;
    private double giaTien;
    private double thanhTien;
    private int trangThai;

    public ModelTienDien(int maTD, String ngayBD, String ngayKT, int chiSoDau, int chiSoCuoi, double giaTien, int trangThai) {
        this.maTD = maTD;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.chiSoDau = chiSoDau;
        this.chiSoCuoi = chiSoCuoi;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public ModelTienDien(String ngayBD, String ngayKT, int chiSoDau, int chiSoCuoi, int soDien, double giaTien, double thanhTien) {
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.chiSoDau = chiSoDau;
        this.chiSoCuoi = chiSoCuoi;
        this.soDien = soDien;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }
    
    
}
