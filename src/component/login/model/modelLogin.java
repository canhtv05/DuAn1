/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package component.login.model;

/**
 *
 * @author CanhPC
 */
public class modelLogin {
    private String username;
    private String password;
    private int role;

    public modelLogin() {
    }

    public modelLogin(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public modelLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    
}
