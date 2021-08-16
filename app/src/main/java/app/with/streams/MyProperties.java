package app.with.streams;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyProperties {
	InputStream inputStream;

    private static final Logger logger = LogManager.getLogger(MyProperties.class.getName());

 
	public Properties getPropValues() throws IOException 
    {
 
        Properties prop = new Properties();
		try {
			String propFileName = "config.properties";

            logger.info("Loading="+propFileName);
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
 
			// get the property value and print it out
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return prop;
	}
}