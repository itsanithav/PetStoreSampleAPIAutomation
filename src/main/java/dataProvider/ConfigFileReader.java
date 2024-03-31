package dataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private static Properties properties;
    private static String propertyFilePath;

    //global property
    private static String environment;
    //config property
    public static String petStoreBaseUrl;


    public static void ReadTestConfigurationProperties() {
        environment = isNotNullOrEmpty(System.getProperty("environment")) ? System.getProperty("environment") : "DEV";

        System.out.println("*****************************************");
        System.out.println("Target environment : " + environment);
        System.out.println("*****************************************");

        BufferedReader reader;
        try {
            propertyFilePath ="src//test//resources//configs//"+environment.toLowerCase()+".properties";
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Property file not found at %s "+propertyFilePath);
        }
        //Get the properties from properties file
        petStoreBaseUrl = properties.getProperty("petStoreBaseUrl");
    }

    private static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}


