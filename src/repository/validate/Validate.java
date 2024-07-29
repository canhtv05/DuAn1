package repository.validate;

import javax.swing.JTextField;
import view.component.message.MessageFrame;

public class Validate {

    private MessageFrame message = new MessageFrame();
    private JTextField textFieldFocus = null;

    public boolean isNull(JTextField textField, String field) {
        if (textField.getText().trim().isEmpty()) {
            message.showMessage("error", "Không được để trống " + field + ".");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
            return false;
        }
        return true;
    }

    public double isNumber(JTextField textField) {
        String text = textField.getText().trim();
        double res = 0.0;
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
        if (!textField.getText().trim().isEmpty()) {
            return textField.getText().trim();
        } else {
            message.showMessage("error", "Không được để trống.");
            if (textFieldFocus == null) {
                textFieldFocus = textField;
                textFieldFocus.requestFocus();
            }
            return null;
        }
    }
}
