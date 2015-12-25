
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
import model.NguoiDung;
import model.VatTu;
import util.HibernateUtil;
import util.JSonUtil;
import util.Log4jSimple;


@Controller
public class BackupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BackupController.class);
	
	@RequestMapping(value="/backupData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String backupData(@RequestParam("moTa") String moTa, HttpServletRequest request){
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực backup data");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền backup data");
				return JSonUtil.toJson("authentication error");
			}
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
			
		} catch (IOException | NumberFormatException | IndexOutOfBoundsException  e){
			logger.error("Lỗi backup database");
			return JSonUtil.toJson("fail");
		}
	}
	
	@RequestMapping(value="/restoreData", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String restoreData(@RequestParam("id") String stt, HttpServletRequest request){
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực restore data");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền restore data");
				return JSonUtil.toJson("authentication error");
			}
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
			logger.error("Lỗi restore data: " + e.getMessage());
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
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập quản lý backup database");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập quản lý database");
				return new ModelAndView(siteMap.login);
			}
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
		} catch (NullPointerException | IndexOutOfBoundsException | NumberFormatException | IOException e) {
			logger.error("Lỗi truy cập quản lý database: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
		
	}
	@RequestMapping(value="/loadPageBackup", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVatTu(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try{
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực phân trang backup data");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền phân trang backup data");
				return JSonUtil.toJson("authentication error");
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
			return JSonUtil.toJson(objectList);
		} catch (FileNotFoundException e){
			logger.error("FileNotFound Exception khi phân trang backup data: " + e.getMessage());
			return JSonUtil.toJson("fail");
		} catch (NumberFormatException e){
			logger.error("NullPointer Exception file khi phân trang backup data: " + e.getMessage());;
			return JSonUtil.toJson("fail");
		} catch (IndexOutOfBoundsException e){
			logger.error("IndexOutOfBounds Exception khi phân trang backup data: " + e.getMessage());
			return JSonUtil.toJson("fail");
		} catch (IOException e){
			logger.error("IO Exception khi phân trang backup data: " + e.getMessage());
			return JSonUtil.toJson("fail");
		}
	}
	
}
