package view.component.message;

import javax.swing.JFrame;
import view.panel.PanelMessage;
import service.PanelMessageListenerImpl;

public class MessageFrame extends JFrame implements PanelMessageListenerImpl {

    private boolean ok;
    private boolean cancel;
    private Runnable onOkClicked;

    public MessageFrame() {
        setSize(550, 130);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelMessage panelMessage = new PanelMessage();
        panelMessage.setPanelMessageListener(this);
        add(panelMessage);
    }

    public void setOnOkClicked(Runnable onOkClicked) {
        this.onOkClicked = onOkClicked;
    }

    public void showMessage(String type, String message) {
        PanelMessage panelMessage = (PanelMessage) getContentPane().getComponent(0);
        panelMessage.changeMessage(type, message);
        setVisible(true);
    }

    @Override
    public void onOkClicked() {
        ok = true;
        if (onOkClicked != null) {
            onOkClicked.run();
        }
        this.dispose(); // Đóng cửa sổ khi nhấn OK
    }

    @Override
    public void onCancelClicked() {
        cancel = true;
        this.dispose(); // Đóng cửa sổ khi nhấn Cancel
    }

    public boolean isOk() {
        return ok;
    }

    public boolean isCancel() {
        return cancel;
    }
}
