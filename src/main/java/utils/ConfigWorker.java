package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConfigWorker {
    private static ConfigWorker cWorker;
    private static final Properties props = new Properties();

    private ConfigWorker() throws IOException {
//        InputStream inStream = ConfigWorker.class.getResourceAsStream("C:\\Users\\monolith\\IdeaProjects\\restAssured-homework\\src\\main\\resources\\config.properties");
//        props.load(inStream);

        try (FileInputStream propFile = new FileInputStream("src/main/resources/config.properties")){
            props.load(propFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigWorker getInstance() throws IOException {
        if (cWorker == null) {
//          Allowing only one instance per execution.
            synchronized (ConfigWorker.class){
                cWorker = new ConfigWorker();
            }
        }

        return cWorker;
    }

    public String getString(String key){
        return props.getProperty(key);
    }
}
