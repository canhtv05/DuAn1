package repository.tiendien;

import view.component.message.MessageFrame;

public class Validate {
    private MessageFrame message = new MessageFrame();
    
    public void checkEmpty(String s) {
        if (s.isEmpty()) {
            message.showMessage("error", "Không được để trống trường này.");
            return;
        }
    }
    
}
