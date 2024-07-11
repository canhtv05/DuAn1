package component.login.repo;

import java.io.*;
import java.util.Properties;

public class FileUtils {
    private static final String LOGIN_FILE = "src/component/login/repo/rememberAccount.txt";

    public static void saveLoginDetails(String username, String password) {
        Properties prop = new Properties();
        prop.setProperty("username", username);
        prop.setProperty("password", password);

        try (OutputStream output = new FileOutputStream(LOGIN_FILE)) {
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static String[] readLoginDetails() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(LOGIN_FILE)) {
            prop.load(input);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            if (username != null && password != null) {
                return new String[] { username, password };
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void clearLoginDetails() {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream(LOGIN_FILE)) {
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
