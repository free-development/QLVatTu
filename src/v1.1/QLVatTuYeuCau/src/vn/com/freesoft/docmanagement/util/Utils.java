/**
 * 
 */
package vn.com.freesoft.docmanagement.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import vn.com.freesoft.docmanagement.Constant;

/**
 * @author quoivp
 *
 */
public class Utils {
	private static final Logger logger = Logger.getLogger(Utils.class);

	/**
     * Read properties file.
     *
     * @param filePath the file path
     * @return the properties
     */
    public static Properties readPropertiesFile(String filePath) {
        logger.debug("IN - readPropertiesFile()");
        Properties properties = null;

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            logger.debug("OUT - readPropertiesFile()");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("readPropertiesFile error " + e);
        }
        return properties;
    }

    /**
     * Check hibernate config.
     *
     * @param properties the properties
     * @return true, if successful
     */
    public static boolean checkHibernateConfig(Properties properties) {
        if (!properties.containsKey(Constant.HIBERNATE_CONNECTION_DRIVER)) {
            logger.error(Constant.HIBERNATE_CONNECTION_DRIVER + " proprerty isn't configed");
            return false;
        }

        if (!properties.containsKey(Constant.HIBERNATE_CONNECTION_URL)) {
            logger.error(Constant.HIBERNATE_CONNECTION_URL + " proprerty isn't configed");
            return false;
        }

        if (!properties.containsKey(Constant.HIBERNATE_CONNECTION_USERNAME)) {
            logger.error(Constant.HIBERNATE_CONNECTION_USERNAME + " proprerty isn't configed");
            return false;
        }

        if (!properties.containsKey(Constant.HIBERNATE_CONNECTION_PASSWORD)) {
            logger.error(Constant.HIBERNATE_CONNECTION_PASSWORD + " proprerty isn't configed");
            return false;
        }

        return true;
    }
}
