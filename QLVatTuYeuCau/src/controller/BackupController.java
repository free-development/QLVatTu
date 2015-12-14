
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.java_cup.internal.runtime.Symbol;

import dao.BackupDB;
import map.siteMap;
import model.BackupInfo;
import model.DBConnection;
import util.HibernateUtil;
import util.JSonUtil;


@Controller
public class BackupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	
	@RequestMapping(value="/backupData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String backupData(@RequestParam("moTa") String moTa){
		try {
			moTa = moTa.replaceAll("\n", "<br>");
			System.out.println(moTa.indexOf("\n"));
			SimpleDateFormat dateFormater = new SimpleDateFormat("ss-mm-hh-dd-MM-yyyy");
			Date dateCurrent = new Date();
			String thoiGian = dateFormater.format(dateCurrent);
			
			String dbUser = context.getInitParameter("dbUser");
			String dbPassword = context.getInitParameter("dbPassword");
			String dbAddress = context.getInitParameter("dbAddress");
			String dbPort = context.getInitParameter("dbPort");
			String dbName = context.getInitParameter("dbName");
			
			DBConnection connection = new DBConnection(dbUser, dbPassword);
			String pathBackup = context.getInitParameter("pathBackup");
			String fileName = "backup-" + dbName +  "(" + thoiGian + ").sql";
			String filePath = pathBackup + fileName;
			BackupDB backupDb = new BackupDB(connection);
			backupDb.backupDB("mysqldump", dbAddress, dbName, Integer.parseInt(dbPort), filePath);
			
			String filePathLogBackup = context.getInitParameter("fileLogBackup");
			//BackupInfo backupInfo = new BackupInfo(thoiGian, moTa, fileName);
			File fileLogBackup = new File(filePathLogBackup);
			
			if(!fileLogBackup.exists())
				fileLogBackup.createNewFile();
			FileInputStream fileIn = new FileInputStream(fileLogBackup);
			System.out.println(fileIn.available());
			byte[] b = new byte[fileIn.available()]; 
					fileIn.read(b);
			FileWriter fileWriter = new FileWriter(fileLogBackup);
			String content = "";
			int stt = 0;
			if (b.length > 0) {
				content =  new String(b);
				int index1 = content.indexOf("#####");
				int lastBackup = Integer.parseInt(content.substring(0, index1));
				stt = lastBackup + 1;
				content = "\n" + content;
			}
			fileWriter.write(stt + "#####" + thoiGian + "#####" + moTa + "#####" + filePath + content );
			BackupInfo backupInfo =  new BackupInfo(stt, thoiGian, moTa, filePath);
//			System.out.println(backupInfo.get);
			fileIn.close();
			fileWriter.close();
			return JSonUtil.toJson(backupInfo);
		} catch (IOException | NumberFormatException e){
			return JSonUtil.toJson("fail");
		}
	}
	
	@RequestMapping(value="/restoreData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String restoreData(@RequestParam("id") String stt){
		try {
			String filePathLogBackup = context.getInitParameter("fileLogBackup");
//			java.io.File fileInput = new java.io.File(filePathLogBackup);
			String pathFileRestore = "";
			FileReader fileInput = new FileReader(filePathLogBackup);
			BufferedReader buff = new BufferedReader(fileInput);
			String line = "";
			
			while (true) {
				line = buff.readLine();
				if (line ==  null)
					break;
				String[] temp = line.split("\\#####");
				if (temp[0].equals(stt)) {
					pathFileRestore = temp[3];
					break;
				}
			}
			String dbUser = context.getInitParameter("dbUser");
			String dbPassword = context.getInitParameter("dbPassword");
//			String dbAddress = context.getInitParameter("dbAddress");
//			String dbPort = context.getInitParameter("dbPort");
//			String dbName = context.getInitParameter("dbName");
			
			DBConnection connection = new DBConnection(dbUser, dbPassword);
			BackupDB backupDb = new BackupDB(connection);
			backupDb.restoreDB(pathFileRestore);
			buff.close();
			fileInput.close();
			
			return JSonUtil.toJson("success");
		} catch (IOException | NumberFormatException e){
			return JSonUtil.toJson("fail");
		}
	}
	
	/*
	@RequestMapping("/restoreData")
	public ModelAndView restoreData(HttpServletRequest request, HttpServletResponse response){
		try {
			String filePathLogBackup = context.getInitParameter("fileLogBackup");
//			java.io.File fileInput = new java.io.File(filePathLogBackup);
			String pathFileRestore = "";
			FileReader fileInput = new FileReader(filePathLogBackup);
			BufferedReader buff = new BufferedReader(fileInput);
			String line = "";
			String[] a = request.getParameterValues("id");
			int stt = Integer.parseInt(a[0]); 
			while (true) {
				line = buff.readLine();
				if (line ==  null)
					break;
				String[] temp = line.split("\\#####");
				if (temp[0].equals(stt)) {
					pathFileRestore = temp[3];
					break;
				}
			}
			String dbUser = context.getInitParameter("dbUser");
			String dbPassword = context.getInitParameter("dbPassword");
			String dbAddress = context.getInitParameter("dbAddress");
			String dbPort = context.getInitParameter("dbPort");
			String dbName = context.getInitParameter("dbName");
			
			DBConnection connection = new DBConnection(dbUser, dbPassword);
			BackupDB backupDb = new BackupDB(connection);
			backupDb.restoreDB(pathFileRestore);
			buff.close();
			fileInput.close();
			
//			return JSonUtil.toJson("success");
			return getListBackup(request, response);
		} catch (IOException | NumberFormatException e){
			//return JSonUtil.toJson("fail");
			return new ModelAndView("login");
		}
	}
	*/
//	@RequestMapping(value="/backupDBManage", method=RequestMethod.GET, 
//			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody String getListBackup(@RequestParam("moTa") String moTa){
//		try {
//			
//		} catch (IOException | NumberFormatException e){
//			return JSonUtil.toJson("fail");
//		}
//	}
	@RequestMapping("/backupDBManage")
	public ModelAndView getListBackup(HttpServletRequest request, HttpServletResponse response){
		try {
			ArrayList<BackupInfo> backupList = new ArrayList<BackupInfo>();
			String filePathLogBackup = context.getInitParameter("fileLogBackup");
//			java.io.File fileInput = new java.io.File(filePathLogBackup);
			File fileLogBackup = new File(filePathLogBackup);
			
			if(!fileLogBackup.exists())
				fileLogBackup.createNewFile();
			FileReader fileInput = new FileReader(fileLogBackup);
			BufferedReader buff = new BufferedReader(fileInput);
			String line = "";
			int size = 0;
			
			while (true) {
				line = buff.readLine();
				if (line ==  null)
					break;
				String[] temp = line.split("\\#####");
//				StringBuilder time = new StringBuilder(temp[1].substring(0, 10));
//				time.reverse();
				BackupInfo backupInfo = new BackupInfo(Integer.parseInt(temp[0]), temp[1].substring(9), temp[2], temp[3]);
				backupList.add(backupInfo);
				size ++;
			}
			long pageNumber = (size % 10 == 0 ? size/10 : (size / 10) +1 );
			request.setAttribute("backupList", backupList);
			request.setAttribute("pageNumber", pageNumber);
			
			buff.close();
			fileInput.close();
			return new ModelAndView(siteMap.backupDataPage);
		} catch (IndexOutOfBoundsException e2) {
			System.out.println("IndexOutOfBoundsException");
			return new ModelAndView(siteMap.login);
		} catch (NumberFormatException e1) {
			System.out.println("number format exception");
			return new ModelAndView(siteMap.login);
		} catch (IOException e) {
			System.out.println("IO exception");
			return new ModelAndView(siteMap.login);
		}
		
	}
}
