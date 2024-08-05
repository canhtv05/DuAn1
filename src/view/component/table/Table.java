package view.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import view.component.scrollbar.ScrollBarCustom;

public class Table extends JTable {

    public Table() {
        setShowHorizontalLines(true);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);

        // Thiết lập màu cho header
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                component.setBackground(new Color(255, 255, 255)); // Màu nền của header
                component.setForeground(new Color(102, 102, 102)); // Màu chữ của header
                component.setFont(component.getFont().deriveFont(Font.BOLD)); // Màu chữ đậm
                return component;
            }
        });

        // Thêm viền bên dưới cho header
        header.setBorder(new LineBorder(new Color(230, 230, 230), 1, true) {
            @Override
            public void paintBorder(Component c, java.awt.Graphics g, int x, int y, int width, int height) {
                // Vẽ đường viền bên dưới
                g.setColor(new Color(230, 230, 230));
                g.drawLine(x, y + height - 1, x + width, y + height - 1);
            }
        });

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean focus,
                    int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, focus, i, i1);
                setBorder(noFocusBorder);
                com.setForeground(new Color(102, 102, 102));
                if (selected) {
                    com.setBackground(new Color(239, 244, 255));
                } else {
                    com.setBackground(Color.WHITE);
                }
                if (o instanceof Number) {
                    BigDecimal number = new BigDecimal(((Number) o).toString());
                    setText(number.toPlainString()); // Sử dụng toPlainString để tránh hiển thị dạng khoa học
                
                }
                return com;
            }
        });
    }

    @Override
    public TableCellEditor getCellEditor(int row, int col) {
        return super.getCellEditor(row, col);
    }

    public void addRow(Object[] row) {
        DefaultTableModel mod = (DefaultTableModel) getModel();
        mod.addRow(row);
    }

    public void fixTable(JScrollPane scroll) {
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
        scroll.setBorder(new EmptyBorder(5, 10, 5, 10));
    }
}
