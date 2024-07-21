package repository.tiendien;

import javax.swing.JTextField;
import view.component.message.MessageFrame;

public class Validate {

    private MessageFrame message = new MessageFrame();
    private JTextField textFieldFocus = null;

    public void checkEmpty(JTextField textField) {
        if (textField.getText().trim().isEmpty()) {
            message.showMessage("error", "Không được để trống.");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
            return;
        }
    }

    public double isNumber(JTextField textField) {
        String text = textField.getText().trim();
        if (text.isEmpty()) {
            message.showMessage("error", "Không được để trống.");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
            return -1.0;
        }
        double res = 0.0f;
        try {
            res = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            message.showMessage("error", "Trường này phải là số.");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
            return -1.0;
        }
        return res;
    }

    public String checkEmptyAndGetText(JTextField textField) {
        String res = null;
        if (!textField.getText().trim().isEmpty()) {
            res = textField.getText().trim();
        } else {
            message.showMessage("error", "Không được để trống.");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
        }
        return res;
    }
}
