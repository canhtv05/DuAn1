package model.tiendichvu;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ModelTienDichVu {

    private int maDV;
    private int maHD;
    private String maPT;
    private String tenDV;
    private String ngayBD;
    private String ngayKT;
    private int dauNguoi;
    private double giaTien;
    private double thanhTien;
    private int trangThai;

    public ModelTienDichVu(int maDV, String ngayBD, String ngayKT, int dauNguoi, double giaTien, int trangThai) {
        this.maDV = maDV;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
        this.trangThai = trangThai;
    }

    public ModelTienDichVu(String maPT,String ngayBD, String ngayKT, String tenDV, int dauNguoi, double giaTien, double thanhTien) {
        this.maPT = maPT;
        this.tenDV = tenDV;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.dauNguoi = dauNguoi;
        this.giaTien = giaTien;
        this.thanhTien = thanhTien;
    }

}
