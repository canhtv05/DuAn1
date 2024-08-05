package view.component.qrcode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.nio.file.Paths;
import java.util.Hashtable;

public class RenderQrCode {

    public static String renderQRCode(int maHD) {
        String filePath = null;
        try {
            String qrCode = "HD" + maHD;
            filePath = Paths.get("src", "img", "qrcode", RandomStringGenerator.generateRandomString("QRCode", 10) + ".png").toString();
            String charset = "UTF-8";
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCode.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 100, 100, hintMap);
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
            System.out.println("Qr code has been generated at the location " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static void main(String[] args) {

    }
}
