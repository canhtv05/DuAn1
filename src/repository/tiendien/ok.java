/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.tiendien;

//import view.component.menu.CustomMessageFrame;
import view.component.message.MessageFrame;

/**
 *
 * @author CanhPC
 */
public class ok {
    public static void main(String[] args) {
        MessageFrame customFrame = new MessageFrame(){
            @Override
            public void onOkClicked() {
                super.onOkClicked();
                System.out.println("ok");
            }

            @Override
            public void onCancelClicked() {
                System.out.println("cancel");
                super.onCancelClicked();
            }
        };
        customFrame.showMessage("error", "loi");
    }
}
