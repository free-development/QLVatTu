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
			String filepath = "backup-" + connection.getDatabase()  + "-(" + dateFormat.format(date) + ").sql";
				 
			String batchCommand = "";
			if (connection.getPassword().length() > 0) {
				//only backup the data not included create database
				batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
							   + connection.getPort() + " -u " + connection.getUser() + " --password=" 
							   + connection.getPassword() + " " + connection.getDatabase() + " -r \"" 
							   + path + "" + filepath + "\"";
			} else {
				batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
							   + connection.getHost()  + " -u " + connection.getUser() + " " 
							   + connection.getDatabase() + " -r \"" + path + "" + filepath + "\"";
			}
			System.out.println(batchCommand);
			Runtime runtime = Runtime.getRuntime();
			p = runtime.exec(batchCommand);
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
	
	public boolean restoreDB(String dumpExePath, String path) {
		boolean status = false;
        try {
            Process p = null;
 
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
//            String filepath = "backup-" + connection.getDatabase() + "-" + "-(" + dateFormat.format(date) + ").sql";
 
            String batchCommand = "";
            if (connection.getPassword().length() > 0) {
                //Backup with database
                batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
                			  + connection.getPort() + " -u " + connection.getUser() 
                			  + " --password=" + connection.getPassword() + " --add-drop-database -B " 
                			  + connection.getDatabase() + " -r \"" + path  + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + connection.getHost() + " --port " 
                			  + connection.getPort() + " -u " + connection.getUser() 
                			  + " --add-drop-database -B " + connection.getDatabase() 
                			  + " -r \"" + path + "\"";
            }
            System.out.println(batchCommand);
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
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
	public static void main(String[] args) {
		DBConnection connection = new DBConnection("user1", "user1", "localhost", 3306, "vattu");
		BackupDB backupDB = new BackupDB(connection);
		String path = "/home/quoioln/backup-vattu-(08-09-2015).sql";
		String dumpExePath = "mysqldump";
		//backupDB.restoreDB(dumpExePath, path);
		backupDB.backupDB("mysqldump", "/home/quoioln/");
	}
}