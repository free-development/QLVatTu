package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.ReadExcelBpsd;
import dao.ReadExcelCT;
import dao.ReadExcelCl;
import dao.ReadExcelNsx;
import dao.ReadExcelTon;
import map.siteMap;
import model.CTVatTu;
import util.FileUtil;

/**
 * Servlet implementation class ReadExcel
 */
@Controller
public class ReadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;  
	int BUFFER_SIZE = 4096;
	String filePath = "./";
	String pathTemp = "./";
	private boolean isMultipart;
	private int maxFileSize = 10000 * 1024;
	private int maxMemSize = 5000 * 1024;
	private File file;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping("/readExcel")
	protected ModelAndView readExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				if(!ReadExcelTon.readXls(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
				if(!ReadExcelTon.readXlsx(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excel", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("status", "success");
		return new ModelAndView(siteMap.ctVatu);
		
	}
	@RequestMapping("/readExcelCt")
	protected ModelAndView readExcelCt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				ArrayList<Object> objectListError = ReadExcelCT.readXls(file);
				ArrayList<CTVatTu> ctvtListError = (ArrayList<CTVatTu>) objectListError.get(0);
				ArrayList<String> statusError = (ArrayList<String>) objectListError.get(1);
				if(ctvtListError.size() != 0)
				{
					long size = ctvtListError.size();
					request.setAttribute("size", size);
					request.setAttribute("ctvtListError", ctvtListError);
					request.setAttribute("statusError", statusError);
					return new ModelAndView("import-excelError", "status", "formatException");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
//				if(!ReadExcelCT.readXlsx(file))
//					return new ModelAndView("import-excelCt", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelCt", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("status", "success");
		return new ModelAndView(siteMap.vatTu);
	}
	
	@RequestMapping("/readExcelBpsd")
	protected ModelAndView readExcelBpsd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				if(!ReadExcelBpsd.readXls(file))
					return new ModelAndView("import-excelBpsd", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
				if(!ReadExcelBpsd.readXlsx(file))
					return new ModelAndView("import-excelBpsd", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelBpsd", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("status", "success");
		return new ModelAndView(siteMap.boPhanSuDung);
		
	}
	@RequestMapping("/readExcelNsx")
	protected ModelAndView readExcelNsx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				if(!ReadExcelNsx.readXls(file))
					return new ModelAndView("import-excelNsx", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
				if(!ReadExcelNsx.readXlsx(file))
					return new ModelAndView("import-excelNsx", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelNsx", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("status", "success");
		return new ModelAndView(siteMap.noiSanXuat);
		
	}
	@RequestMapping("/readExcelCl")
	protected ModelAndView readExcelCl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		isMultipart = ServletFileUpload.isMultipartContent(request);
		response.setContentType("text/html");
		java.io.PrintWriter out = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File(pathTemp));
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum file size to be uploaded.
		upload.setSizeMax(maxFileSize);
		try {
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			FileItem fi = (FileItem) i.next();
			String fileName = fi.getName();
			String extenstionFile = FileUtil.getExtensionByPath(fileName);
			File file;
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xls");
				fi.write(file);
				if(!ReadExcelCl.readXls(file))
					return new ModelAndView("import-excelCl", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				fi.write(file);
				if(!ReadExcelCl.readXlsx(file))
					return new ModelAndView("import-excelCl", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelCl", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("status", "success");
		return new ModelAndView(siteMap.chatLuong);
	}
}