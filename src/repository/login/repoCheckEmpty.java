package repository.login;

import view.component.message.MessageFrame;

public class RepoCheckEmpty {

    public boolean checkUsernameEmpty(String username) {
        if (username.isEmpty()) {
            showMessage("error", "You must enter your username.");
            return true;
        }
        return false;
    }

    public boolean checkPasswordEmpty(String password) {
        if (password.isEmpty()) {
            showMessage("error", "You must enter your password.");
            return true;
        }
        return  false;
    }

    public boolean checkUsernameAndPasswordEmpty(String username, String password) {
        if (username.isEmpty() && password.isEmpty()) {
            showMessage("error", "Username and password cannot be empty.");
            return true;
        }
        return false;
    }

    public void loginFailed() {
        showMessage("warning", "Incorrect username or password.");
    }
    
    public void successCreatedAccount() {
        showMessage("message", "User account created successfully!");
    }
    
    public void errorDuplicate(String username) {
        showMessage("error", "Username already exists: " + username);
    }

    private void showMessage(String type, String message) {
        MessageFrame messageFrame = new MessageFrame();
        messageFrame.showMessageFrame(type, message);
        messageFrame.setVisible(true);
        if (messageFrame.isOk() || messageFrame.isCancel()) {
            messageFrame.dispose();
        }
    }
}
