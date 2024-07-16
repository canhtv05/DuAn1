/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.login;

/**
 *
 * @author CanhPC
 */
public class ModelLogin {
    private String username;
    private String password;
    private int role;

    public ModelLogin() {
    }

    public ModelLogin(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public ModelLogin(String username, String password) {
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
