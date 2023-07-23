package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
    public InputStream getResourceStream(String resourcePath) {
        URL url = getClass().getClassLoader().getResource(resourcePath);
        if (url == null) return null;
        try {
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            return conn.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
