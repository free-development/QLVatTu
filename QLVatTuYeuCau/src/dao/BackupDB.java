/**
 * 
 */
package dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.DBConnection;

/**
 * @author quoioln
 *
 */
public class BackupDB {
	private DBConnection connection;
//	private String dumpExePath;
//	private String path;
	
	
	public BackupDB() {
		this.connection = new DBConnection();
//		this.dumpExePath = "";
//		this.path = "";
	}
	
	/**
	 * @param connection
	 * @param path
	 */
	public BackupDB(DBConnection connection) {
		this.connection = connection;
//		this.path = path;
//		this.dumpExePath = dumpExePath;
	}



	public boolean backupDB(String dumpExePath, String host, String dbName, int port, String filePath) {
		boolean status = false;
        try {
            Process p = null;
 
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup-" + dbName +  "-(" + dateFormat.format(date) + ").sql";
 
            String batchCommand = "";
            if (connection.getPassword().length() > 0) {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " 
                			  + port + " -u " + connection.getUser() 
                			  + " --password=" + connection.getPassword() + " --add-drop-database -B " 
                			  + dbName + " -r \"" + filePath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " 
                			  + port + " -u " + connection.getUser() 
                			  + " --add-drop-database -B " + dbName
                			  + " -r \"" + filePath + "\"";
            }
            System.out.println(batchCommand);
            Runtime runtime = Runtime.getRuntime();
//            String[] restoreCmd = new String[]{"mysql ", "--user=" + connection.getUser(), "--password=" + connection.getPassword(), "-e", "source " + path};
//            p = runtime.exec(new String[]{"/bin/sh", "-c",batchCommand});
            p = runtime.exec(new String[] { "cmd.exe", "/c", batchCommand });
//            p = runtime.exec(restoreCmd);
//            new String[] { "cmd.exe", "/c", executeCmd }
//            p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.6/bin" + batchCommand);
            int processComplete = p.waitFor();
 
            if (processComplete == 0) {
                status = true;
            } else {
                status = false;
            }
 
        } catch (IOException ioe) {
        } catch (Exception e) {
        }
        return status;
	}
	
	public boolean restoreDB(String source) {
		 
        String[] executeCmd = new String[]{"mysql", "--user=" + connection.getUser(), "--password=" + connection.getPassword(), "-e", "source " + source};
 
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
                System.out.println("Backup restored successfully with " + source);
                return true;
            } else {
            	System.out.println("Could not restore the backup " + source);
            }
        } catch (Exception ex) {
        	System.out.println(ex.getCause());
        }
 
        return false;
 
    }
	
}