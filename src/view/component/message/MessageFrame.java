package view.component.message;

import javax.swing.JFrame;
import view.panel.PanelMessage;
import service.PanelMessageListenerImpl;

public class MessageFrame extends JFrame implements PanelMessageListenerImpl {

    private boolean ok;
    private boolean cancel;
    private boolean interactionCompleted;

    public MessageFrame() {
        interactionCompleted = false;
    }

    public void showMessage(String type, String message) {
        MessageFrame messageFrame = new MessageFrame();
        messageFrame.setSize(550, 130);
        messageFrame.setUndecorated(true);
        messageFrame.setLocationRelativeTo(null);
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PanelMessage panelMessage = new PanelMessage();
        panelMessage.changeMessage(type, message);
        messageFrame.add(panelMessage);
        messageFrame.setVisible(true);

        if (messageFrame.isOk() || messageFrame.isCancel()) {
            messageFrame.dispose();
        }
    }

    @Override
    public void onOkClicked() {
        ok = true;
        interactionCompleted = true;
    }

    @Override
    public void onCancelClicked() {
        cancel = true;
        interactionCompleted = true;
    }

    public boolean isOk() {
        return ok;
    }

    public boolean isCancel() {
        return cancel;
    }
}
