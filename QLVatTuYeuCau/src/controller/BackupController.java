
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.BackupDB;
import dao.VatTuDAO;
import map.siteMap;
import model.BackupInfo;
import model.DBConnection;
import model.VatTu;
import util.HibernateUtil;
import util.JSonUtil;
import util.Log4jSimple;


@Controller
public class BackupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	
	@RequestMapping(value="/backupData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String backupData(@RequestParam("moTa") String moTa){
//		Logger log = Logger.getLogger(log4jExample.class.getName());
		try {
			moTa = moTa.replaceAll("\n", "<br>");
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
			String pathLogBackup = context.getInitParameter("pathLogBackup");
			String fileNameIdBackup = "numberBackup.sysInfo";
			File fileIdBackup = new File(pathLogBackup + fileNameIdBackup);
			
			if(!fileIdBackup.exists())
				fileIdBackup.createNewFile();
			// get page id
			BufferedReader bufferFileId = new BufferedReader(new FileReader(fileIdBackup));
			String content = bufferFileId.readLine();
			
			bufferFileId.close();
			
			int idTemp = 0;
			
			if (content !=null && content.length() > 0) 
				idTemp = Integer.parseInt(content) - 1;
			
			File fileId = new File(pathLogBackup + "idInfo" + (idTemp / 10) + ".info");
			idTemp ++;
			if (fileId.exists()) {
				FileInputStream fileIdInput = new FileInputStream(fileId);
				byte[] b1 = new byte[fileIdInput.available()]; 
				fileIdInput.read(b1);
				fileIdInput.close();
				
				String contentId = new String(b1);
				String contentBackup = "";
				int temp = idTemp % 10;
				if (temp == 0) {
					fileId = new File(pathLogBackup + "idInfo" + (idTemp / 10) + ".info");
				} else {
					contentBackup = "\n" + contentId;
				}
				FileWriter fileWriterId = new FileWriter(fileId);
				fileWriterId.write(temp + "#####" + thoiGian + "#####" + moTa + "#####" + filePath + contentBackup);
				fileWriterId.close();
			} else {
				if (!fileId.exists())
					fileId.createNewFile();
				FileWriter fileWriterId = new FileWriter(fileId);
				fileWriterId.write(0 + "#####" + thoiGian + "#####" + moTa + "#####" + filePath);
				fileWriterId.close();
			}
			FileWriter fileWriter = new FileWriter(fileIdBackup);
			fileWriter.write(new String("" + (idTemp + 1)));
			fileWriter.close();
			
			BackupInfo backupInfo =  new BackupInfo(idTemp, thoiGian, moTa, filePath);
			return JSonUtil.toJson(backupInfo);
			
		} catch (IOException e){
			Log4jSimple.debug("Lỗi nhập xuất file backup");
			System.out.println("Lỗi nhập xuất file backup");
			return JSonUtil.toJson("fail");
		} catch (NumberFormatException e1){
//			log("Lỗi định dạng số  backup");
			Log4jSimple.debug("Lỗi định dạng số  backup");
			System.out.println("Lỗi định dạng số  backup");
			return JSonUtil.toJson("fail");
		} catch (IndexOutOfBoundsException e2){
//			log("Lỗi chỉ số backup");
			Log4jSimple.debug("Lỗi chỉ số backup");
			System.out.println("Lỗi chỉ số backup");
			return JSonUtil.toJson("fail");
		}
	}
	
	@RequestMapping(value="/restoreData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String restoreData(@RequestParam("id") String stt){
		try {
			
			String pathLogBackup = context.getInitParameter("pathLogBackup");
			int id = Integer.parseInt(stt);
			int idPage  = id / 10;
			int idBackup = id % 10;
			String pathFileRestore = "";
			System.out.println(pathLogBackup + "idInfo" + idPage + ".info");
			FileReader fileInput = new FileReader(pathLogBackup + "idInfo" + idPage + ".info");
			BufferedReader buff = new BufferedReader(fileInput);
			
			String line = "";
			
			while (true) {
				line = buff.readLine();
				if (line ==  null)
					break;
				String[] temp = line.split("\\#####");
				if (temp[0].equals(""+idBackup)) {
					System.out.println(temp[3]);
					pathFileRestore = temp[3];
					break;
				}
			}
			String dbUser = context.getInitParameter("dbUser");
			String dbPassword = context.getInitParameter("dbPassword");
			
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
		ArrayList<BackupInfo> backupList = new ArrayList<BackupInfo>();
//		int size = 0;
		int pageNumber = 0;
		try {
			HttpSession session = request.getSession(false);
			session.removeAttribute("congVanList");
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			session.removeAttribute("errorList");
			
			String pathLogBackup = context.getInitParameter("pathLogBackup");
			File fileId = new File(pathLogBackup + "numberBackup.sysInfo");
			
			FileReader fileInput = new FileReader(fileId);
			BufferedReader buff = new BufferedReader(fileInput);
			pageNumber = (Integer.parseInt(buff.readLine()) - 1)/ 10;
			buff.close();
//			for (int i = pageNumber; i >= 0 ; i--) {
				FileReader fileIdInput = new FileReader(pathLogBackup + "idInfo" + pageNumber + ".info");
				BufferedReader buff2 = new BufferedReader(fileIdInput);
				String line = "";
				while (true) {
					line = buff2.readLine();
					if (line ==  null)
						break;
					String[] temp = line.split("\\#####");
					BackupInfo backupInfo = new BackupInfo(Integer.parseInt(temp[0]), temp[1].substring(9), temp[2], temp[3]);
					backupList.add(backupInfo);
				}
				buff2.close();
//			}
			request.setAttribute("backupList", backupList);
			request.setAttribute("pageNumber", pageNumber);
			
			buff.close();
			fileInput.close();
			return new ModelAndView(siteMap.backupDataPage);
		} catch (FileNotFoundException e4) {
			request.setAttribute("backupList", backupList);
			request.setAttribute("pageNumber", pageNumber);
			return new ModelAndView(siteMap.backupDataPage);
		} catch (NullPointerException e3) {
			return new ModelAndView(siteMap.login);
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
	@RequestMapping(value="/loadPageBackup", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVatTu(@RequestParam("pageNumber") String pageNumber) {
		try{
			int page = Integer.parseInt(pageNumber);
			ArrayList<Object> objectList = new ArrayList<Object>();
			String pathLogBackup = context.getInitParameter("pathLogBackup");
			FileReader fileIn = new FileReader(pathLogBackup + "numberBackup.sysInfo");
			BufferedReader buffFileIn = new BufferedReader(fileIn);
			int size = Integer.parseInt(buffFileIn.readLine());
			buffFileIn.close();
			page = (size - 1)/ 10 - page;
//			int idPage = page / 10;
//			int idBackup = page % 10;
			FileReader fileIdInput = new FileReader(pathLogBackup + "idInfo" + page + ".info");
			BufferedReader buffFileIdInput = new BufferedReader(fileIdInput);
			ArrayList<BackupInfo> vatTuList = new ArrayList<BackupInfo>() ;
			String line = "";
			while((line = buffFileIdInput.readLine()) != null) {
				String[] temp = line.split("\\#####");
				BackupInfo backupInfo = new BackupInfo(Integer.parseInt(temp[0]), temp[1].substring(9), temp[2], temp[3]);
				vatTuList.add(backupInfo);
			}
			buffFileIdInput.close();
			objectList.add(vatTuList);
			objectList.add(size);
			return JSonUtil.toJson(objectList);
		} catch (FileNotFoundException e){
			Log4jSimple.debug("Lỗi nhập xuất file backup");
			System.out.println("Lỗi nhập xuất file backup");
			return JSonUtil.toJson("fail");
		} catch (NumberFormatException e1){
//			log("Lỗi định dạng số  backup");
			Log4jSimple.debug("Lỗi định dạng số  backup");
			System.out.println("Lỗi định dạng số  backup");
			return JSonUtil.toJson("fail");
		} catch (IndexOutOfBoundsException e2){
//			log("Lỗi chỉ số backup");
			Log4jSimple.debug("Lỗi chỉ số backup");
			System.out.println("Lỗi chỉ số backup");
			return JSonUtil.toJson("fail");
		} catch (IOException e5){
	//		log("Lỗi chỉ số backup");
			Log4jSimple.debug("Lỗi nhập xuất");
			System.out.println("Lỗi nhập xuất");
			return JSonUtil.toJson("fail");
		}
	}
	/*
	@RequestMapping(value="/filterDataBackup", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String filterDataBackup(@RequestParam("filter") String filter, @RequestParam("value1") String value1, @RequestParam("value2") String value2, HttpServletRequest request) {
		
		try{
			ArrayList<BackupInfo> backupList = new ArrayList<BackupInfo>();
			if ("all".equals(filter)) {
				
			} else if ("description".equals(filter)){
				
			} else if ("date".equals(filter)){
				
			}
			
			int page = Integer.parseInt(pageNumber);
			ArrayList<Object> objectList = new ArrayList<Object>();
			String pathLogBackup = context.getInitParameter("pathLogBackup");
			FileReader fileIn = new FileReader(pathLogBackup + "numberBackup.sysInfo");
			BufferedReader buffFileIn = new BufferedReader(fileIn);
			int size = Integer.parseInt(buffFileIn.readLine());
			buffFileIn.close();
			page = (size - 1)/ 10 - page;
//			int idPage = page / 10;
//			int idBackup = page % 10;
			FileReader fileIdInput = new FileReader(pathLogBackup + "idInfo" + page + ".info");
			BufferedReader buffFileIdInput = new BufferedReader(fileIdInput);
			ArrayList<BackupInfo> vatTuList = new ArrayList<BackupInfo>() ;
			String line = "";
			while((line = buffFileIdInput.readLine()) != null) {
				String[] temp = line.split("\\#####");
				BackupInfo backupInfo = new BackupInfo(Integer.parseInt(temp[0]), temp[1].substring(9), temp[2], temp[3]);
				vatTuList.add(backupInfo);
			}
			buffFileIdInput.close();
			objectList.add(vatTuList);
			objectList.add(size);
			return JSonUtil.toJson(backupList);
		} catch (FileNotFoundException e){
			Log4jSimple.debug("Lỗi nhập xuất file backup");
			System.out.println("Lỗi nhập xuất file backup");
			return JSonUtil.toJson("fail");
		} catch (NumberFormatException e1){
//			log("Lỗi định dạng số  backup");
			Log4jSimple.debug("Lỗi định dạng số  backup");
			System.out.println("Lỗi định dạng số  backup");
			return JSonUtil.toJson("fail");
		} catch (IndexOutOfBoundsException e2){
//			log("Lỗi chỉ số backup");
			Log4jSimple.debug("Lỗi chỉ số backup");
			System.out.println("Lỗi chỉ số backup");
			return JSonUtil.toJson("fail");
		} catch (IOException e5){
	//		log("Lỗi chỉ số backup");
			Log4jSimple.debug("Lỗi nhập xuất");
			System.out.println("Lỗi nhập xuất");
			return JSonUtil.toJson("fail");
		}
	}
	*/
}
