package repository.login;

import view.component.message.MessageFrame;

public class CheckEmpty {

    private final MessageFrame message = new MessageFrame();
    
    public boolean checkUsernameEmpty(String username) {
        if (username.isEmpty()) {
            message.showMessage("error", "You must enter your username.");
            return true;
        }
        return false;
    }

    public boolean checkPasswordEmpty(String password) {
        if (password.isEmpty()) {
            message.showMessage("error", "You must enter your password.");
            return true;
        }
        return  false;
    }

    public boolean checkUsernameAndPasswordEmpty(String username, String password) {
        if (username.isEmpty() && password.isEmpty()) {
            message.showMessage("error", "Username and password cannot be empty.");
            return true;
        }
        return false;
    }

    public void loginFailed() {
        message.showMessage("warning", "Incorrect username or password.");
    }
    
    public void successCreatedAccount() {
        message.showMessage("message", "User account created successfully!");
    }
    
    public void errorDuplicate(String username) {
        message.showMessage("error", "Username already exists: " + username);
    }
}
