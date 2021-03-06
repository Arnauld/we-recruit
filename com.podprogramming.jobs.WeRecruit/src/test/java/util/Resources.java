package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class Resources {
    
    public static Properties loadXMLProperties(String resourcePath) throws InvalidPropertiesFormatException, IOException {
        InputStream stream=null;
        try {
            stream = Resources.class.getResourceAsStream(resourcePath);
            Properties resources = new Properties();
            resources.loadFromXML(stream);
            return resources;
        }
        finally {
            if(stream!=null)
                stream.close();
        }
    }
    
    public static Properties loadProperties(String resourcePath) throws InvalidPropertiesFormatException, IOException {
        InputStream stream=null;
        try {
            URL url = Resources.class.getResource(resourcePath);
            System.out.println(">>>> Loading properties at " + url);
            stream = url.openStream();
            Properties resources = new Properties();
            resources.load(stream);
            return resources;
        }
        finally {
            if(stream!=null)
                stream.close();
        }
    }

}
