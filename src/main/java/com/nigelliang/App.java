package com.nigelliang;

import java.io.InputStream;

/**
 * Hello world!
 */
public class App {
    public  static String getVersion() throws Exception {
        try (InputStream stream = App.class.getResourceAsStream("/version")) {
            String version = new String(stream.readAllBytes());
            System.out.println(version);
            return version;
        }
    }
}
