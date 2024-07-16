package util;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class MoveJFramekGradient extends MouseAdapter {

    private Point initialClick;
    private JFrame parent;

    public MoveJFramekGradient(JFrame parent) {
        this.parent = parent;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        initialClick = e.getPoint();
        parent.getComponentAt(initialClick);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int thisX = parent.getLocation().x;
        int thisY = parent.getLocation().y;

        int xMoved = e.getX() - initialClick.x;
        int yMoved = e.getY() - initialClick.y;

        int X = thisX + xMoved;
        int Y = thisY + yMoved;
        parent.setLocation(X, Y);
    }
}
