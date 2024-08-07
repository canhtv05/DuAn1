package repository.exportpdf;

import dao.DBContext;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.hoadon.ModelExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
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

    public void exportToExcel(String ngayLap) {
        ArrayList<ModelExcel> list = new ArrayList<>();
        sql = "SELECT hd.IdHoaDon, pt.MaPhong, pt.GiaPhong, td.ThanhTien, tn.ThanhTien, dv.ThanhTien, hd.TongTien "
                + "FROM dbo.HoaDon hd "
                + "JOIN dbo.PhongTro pt ON pt.MaPhong = hd.MaPhong "
                + "JOIN dbo.TienDien td ON td.IdHoaDon = hd.IdHoaDon "
                + "JOIN dbo.TienNuoc tn ON tn.IdHoaDon = hd.IdHoaDon "
                + "JOIN dbo.TienDichVu dv ON dv.IdDichVu = hd.IdHoaDon "
                + "WHERE hd.NgayLap = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ngayLap);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ModelExcel(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Danh sách hóa đơn");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Mã HĐ", "Mã phòng", "Giá phòng", "Tiền điện", "Tiền nước", "Tiền dịch vụ", "Thành tiền"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for (ModelExcel model : list) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(model.getMaHD());
                row.createCell(1).setCellValue(model.getMaPT());
                row.createCell(2).setCellValue(model.getGiaPT());
                row.createCell(3).setCellValue(model.getTienDien());
                row.createCell(4).setCellValue(model.getTienNuoc());
                row.createCell(5).setCellValue(model.getTienDV());
                row.createCell(6).setCellValue(model.getThanhTien());
            }

            File file = new File("C:\\Users\\chung\\Desktop\\hoadon.xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
