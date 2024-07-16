package view.component.message;

import javax.swing.JFrame;
import view.panel.PanelMessage;
import service.PanelMessageListenerImpl;

public class MessageFrame extends JFrame implements PanelMessageListenerImpl{

    private boolean ok;
    private boolean cancel;

    public MessageFrame() {
    }

    public MessageFrame(boolean ok) {
        this.ok = ok;
    }

    public void showMessageFrame(String typeMessage, String content) {
        setSize(550, 130);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelMessage panelMessage = new PanelMessage();
        panelMessage.changeMessage(typeMessage, content);
        add(panelMessage);
    }

    @Override
    public void onOkClicked() {
        ok = true;
    }

    @Override
    public void onCancelClicked() {
        cancel = true;
    }
    
    public boolean isOk() {
        return ok;
    } 
    
    public boolean isCancel() {
        return cancel;
    }
}
