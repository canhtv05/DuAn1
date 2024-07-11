package component.login.repo;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static void writeToJsonFile(String filePath, Object data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            gson.toJson(data, writer);
        }
    }

    public static <T> T readFromJsonFile(String filePath, Class<T> classOfT) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return gson.fromJson(reader, classOfT);
        }
    }
}
