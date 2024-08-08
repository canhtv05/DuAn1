package model.tiennuoc;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ModelTienNuoc {

    private int maTN;
    private int maHD;
    private String maPT;
    private String ngayBD;
    private String ngayKT;
    private int dauNguoi;
    private double giaTien;
    private double thanhTien;
    private int trangThai;

    public ModelTienNuoc(int maTN, String ngayBD, String ngayKT, double giaTien, int trangThai) {
        this.maTN = maTN;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public ModelTienNuoc(String ngayBD, String ngayKT, int dauNguoi, double giaTien, double thanhTien) {
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }
    
    
}
