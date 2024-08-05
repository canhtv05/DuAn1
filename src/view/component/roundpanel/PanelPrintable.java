package view.component.roundpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JPanel;

public class PanelPrintable implements Printable {
    private JPanel panel;

    public PanelPrintable(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public int print(Graphics g, PageFormat format, int pageIndex) throws PrinterException {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(format.getImageableX(), format.getImageableY());

        // Kích thước của panel
        double width = format.getImageableWidth();
        double height = format.getImageableHeight();

        // Chuyển kích thước của panel sang kích thước in
        double scaleX = width / panel.getWidth();
        double scaleY = height / panel.getHeight();
        double scale = Math.min(scaleX, scaleY);
        g2d.scale(scale, scale);

        // Vẽ nội dung của panel
        panel.paint(g2d);

        return PAGE_EXISTS;
    }
}
