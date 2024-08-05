package view.component.qrcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteAllFile {

    public static void deleteAllFileInPath(String filePath) {
        String directoryPath = filePath;

        // Tạo đối tượng File từ đường dẫn
        File directory = new File(directoryPath);

        // Kiểm tra xem đường dẫn có phải là một thư mục không
        if (directory.isDirectory()) {
            // Lấy danh sách các file trong thư mục
            File[] files = directory.listFiles();

            // Duyệt qua từng file và xóa
            if (files != null) {
                for (File file : files) {
                    try {
                        // Kiểm tra nếu file là một thư mục, thì thực hiện đệ quy
                        if (file.isDirectory()) {
                            deleteAllFileInPath(file.getAbsolutePath());
                        }
                        // Sử dụng Files.delete để xóa file
                        Files.delete(file.toPath());
                        System.out.println("Đã xóa file: " + file.getName());
                    } catch (IOException e) {
                        System.err.println("Không thể xóa file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Đây không phải là một thư mục hợp lệ: " + directoryPath);
        }
    }
    public static void main(String[] args) {
        String path = "src/img/qrcode"; // Thay thế bằng đường dẫn đến thư mục của bạn
        deleteAllFileInPath(path);
    }
}
