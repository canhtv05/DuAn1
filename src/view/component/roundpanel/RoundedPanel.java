package view.component.roundpanel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 35; // Độ cong của góc
    private int borderThickness = 2; // Độ dày của viền
    private Color borderColor = Color.BLACK; // Màu sắc của viền

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Tạo hình dạng góc cong
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, width, height, cornerRadius,
                cornerRadius);

        // Vẽ viền
        g2d.setColor(borderColor);
        g2d.setStroke(new java.awt.BasicStroke(borderThickness));
        g2d.draw(roundedRectangle);

        // Vẽ nội dung của JPanel
        g2d.setClip(roundedRectangle);
        super.paintComponent(g);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setBorderThickness(int thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }
}
