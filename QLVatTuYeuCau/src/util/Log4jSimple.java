/**
 * 
 */
package util;

import org.apache.log4j.Logger;

/**
 * @author QUOI
 *
 */
public class Log4jSimple {
	
	 private static final Logger log = Logger.getLogger(Log4jSimple.class);
	 
	 public static void dubug(String msg) {
		 log.debug(msg);
	 }
	 public static void info(String msg) {
		 log.info(msg);
	 }
}
