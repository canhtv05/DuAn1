/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.NhanVien;
/**
 *
 * @author BOSS
 */
<<<<<<< HEAD
import repository.NhanVien.repoNhanVien;
=======
>>>>>>> c8a2c028b9dfbd5c309bf6978a0c1a5cc38c228f
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Model_NhanVien {
    private String maNV;
    private String hoTen;
    private Date ngaySinh;
    private int gioiTinh;
    private String dienThoai;
    private String CCCD;
    private Date ngayBD;
    private Date ngayKT;
    private Integer thoiHan;
    private String anhNV;
    private int TrangThai;
}
