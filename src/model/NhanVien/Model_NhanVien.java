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
>>>>>>> 81d41c70021f6fd0cc5efcefb022e8fc23d7e876
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
    
}
