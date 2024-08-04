/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.NhanVien;

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
/**
 *
 * @author BOSS
 */
public class Model_LichLamViec {
    private int idLich;
    private java.util.Date ngayLam;
    private String maNV;
    private String tenNV;
    private String congViec;
    private String ghiChu;
    private int trangThai;
}
