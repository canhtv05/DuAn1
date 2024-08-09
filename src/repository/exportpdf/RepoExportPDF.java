package repository.exportpdf;

import dao.DBContext;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import model.hoadon.ModelExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RepoExportPDF {

    private String sql = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public RepoExportPDF() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNgayLap(int nam) {
        ArrayList<String> arr = new ArrayList<>();
        boolean isHasNam = nam != -1;
        sql = "SELECT DISTINCT CONVERT(VARCHAR, NgayLap, 105) AS NgayLap FROM dbo.HoaDon ";
        if (isHasNam) {
            sql += "WHERE DATEPART(YEAR, NgayLap) = ?";
        }
        try {
            ps = conn.prepareStatement(sql);
            if (isHasNam) {
                ps.setInt(1, nam);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString("NgayLap"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<Integer> getMaHD(String ngayLap) {
        ArrayList<Integer> arr = new ArrayList<>();
        sql = "SELECT IdHoaDon FROM HoaDon WHERE NgayLap = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ngayLap);
            rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public String getTenNV(int maHD) {
        sql = """
                 SELECT nv.HoTen FROM dbo.HoaDon hd
                 JOIN dbo.NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien
                 WHERE hd.IdHoaDon = ?
                """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maHD);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean exportToExcel(String ngayLap) {
        ArrayList<ModelExcel> list = new ArrayList<>();

        sql = """
                SELECT
                    hd.IdHoaDon,
                    pt.MaPhong,
                    pt.GiaPhong,
                    nv.HoTen,
                    td.ThanhTien AS TienDien,
                    tn.ThanhTien AS TienNuoc,
                    dv.ThanhTien AS TienDichVu,
                    hd.TongTien
                FROM
                    dbo.HoaDon hd
                JOIN
                    dbo.PhongTro pt ON pt.MaPhong = hd.MaPhong
                JOIN
                    dbo.TienDien td ON td.IdHoaDon = hd.IdHoaDon
                JOIN
                    dbo.TienNuoc tn ON tn.IdHoaDon = hd.IdHoaDon
                JOIN
                    dbo.TienDichVu dv ON dv.IdDichVu = hd.IdHoaDon
                JOIN
                    dbo.NhanVien nv ON nv.MaNhanVien = hd.MaNhanVien
                WHERE
                    hd.NgayLap = ?;""";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ngayLap);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ModelExcel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getDouble(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();

        if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
            fileToSave = new File(fileToSave + ".xlsx");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Danh sách hóa đơn");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = { "Mã HĐ", "Mã phòng", "Giá phòng", "Người tạo", "Tiền điện", "Tiền nước",
                    "Tiền dịch vụ", "Thành tiền" };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (ModelExcel model : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(model.getMaHD());
                row.createCell(1).setCellValue(model.getMaPT());
                row.createCell(2).setCellValue(model.getGiaPT());
                row.createCell(3).setCellValue(model.getNguoiTao());
                row.createCell(4).setCellValue(model.getTienDien());
                row.createCell(5).setCellValue(model.getTienNuoc());
                row.createCell(6).setCellValue(model.getTienDV());
                row.createCell(7).setCellValue(model.getThanhTien());
            }
<<<<<<< HEAD

            File file = new File("C:\\Users\\chung\\Desktop\\hoadon.xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
=======
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave)) {
>>>>>>> e6f4820a98b8c999a2dd74292ec852d6a50c4ff5
                workbook.write(fileOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
