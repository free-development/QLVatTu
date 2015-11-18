package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.BackupDB;
import model.DBConnection;
public class test{
	public static void main(String[] args){
		try {
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
			Date dateCurrent = new Date();
			String thoiGian = dateFormater.format(dateCurrent);
			
			DBConnection connection = new DBConnection("root", "Voquoctuong", "localhost", 3306, "vattu");
			
			String pathBackup = "/home/quoioln/";
			String fileName = "backup-" + connection.getDatabase() +  "(" + thoiGian + ").sql";
			String filePath = pathBackup + fileName;
			BackupDB backupDb = new BackupDB(connection);
			backupDb.backupDB("mysqldump", filePath);
			
			String fileLogBackup = "/home/quoioln/log.txt";
			//BackupInfo backupInfo = new BackupInfo(thoiGian, moTa, fileName);
			File logBackup = new File(fileLogBackup);
			if(!logBackup.exists())
				logBackup.createNewFile();
			FileWriter fileWriter = new FileWriter(logBackup);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.newLine();
			writer.write(thoiGian + "#" + "backup test" + "#" + filePath+fileName);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
}
