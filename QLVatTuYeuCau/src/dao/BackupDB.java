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



	public boolean backupDB(String dumpExePath, String path) {
		boolean status = false;
        try {
            Process p = null;
 
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup-" + connection.getDatabase() +  "-(" + dateFormat.format(date) + ").sql";
 
            String batchCommand = "";
            if (connection.getPassword().length() > 0) {
                //Backup with database
                batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
                			  + connection.getPort() + " -u " + connection.getUser() 
                			  + " --password=" + connection.getPassword() + " --add-drop-database -B " 
                			  + connection.getDatabase() + " -r \"" + path + filepath  + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
                			  + connection.getPort() + " -u " + connection.getUser() 
                			  + " --add-drop-database -B " + connection.getDatabase() 
                			  + " -r \"" + path + filepath+ "\"";
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
	
	public boolean restoreDB(String dbUserName, String dbPassword, String source) {
		 
        String[] executeCmd = new String[]{"mysql", "--user=" + dbUserName, "--password=" + dbPassword, "-e", "source " + source};
 
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
	public static void main(String[] args) {
		DBConnection connection = new DBConnection("root", "voquoctuong", "localhost", 3306, "vattu");
		BackupDB backupDB = new BackupDB(connection);
		String path = "/home/quoioln/backup-vattu-(08-09-2015).sql";
		String dumpExePath = "mysqldump";
		//backupDB.restoreDB(dumpExePath, path);
//		if (backupDB.restoreDB("root", "voquoctuong", "D:/backup-vattu-(09-11-2015).sql"))
//			System.out.println("OK");
//		else
//			System.out.println("fail");
		//else
			//System.out.println("FAIL");
//		if (backupDB.backupDB("mysqldump", "D:/"))
//				System.out.println("OK");
	}
}