package view.component.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import view.component.message.MessageFrame;

public class LoadTable<T> {

    public void loadTable(ArrayList<T> arr, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        if (arr.isEmpty()) {
            MessageFrame message = new MessageFrame();
            message.showMessage("error", "Không có dữ liệu để LoadTable.");
            return;
        }

        for (T t : arr) {
            Class<?> clas = t.getClass(); // Lấy đối tượng class hiện tại
            Field[] fields = clas.getDeclaredFields(); // lấy all thuộc tính của đối tượng

            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true); // cho phép truy cập thuộc tính private
                try {
                    rowData[i] = fields[i].get(t); // lấy giá trị của các thuộc tính
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }

    }
}
