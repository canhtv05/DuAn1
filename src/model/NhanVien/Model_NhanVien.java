/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.NhanVien;
/**
 *
 * @author BOSS
 */
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
