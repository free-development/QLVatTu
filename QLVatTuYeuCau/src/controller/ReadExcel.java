package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;

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
	String filePath = "File/File/";
	String pathTemp = "File/Temp/";

	 @Autowired
		private   ServletContext context; 
	@RequestMapping("/readExcel")
	protected ModelAndView readExcel(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelTon.readXls(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				if(!ReadExcelTon.readXlsx(file))
					return new ModelAndView("import-excel", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excel", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.ctVatu);
		
	}
	@RequestMapping("/readExcelCt")
	protected ModelAndView readExcelCt(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> objectListError = ReadExcelCT.readXls(file);
				ArrayList<CTVatTu> ctvtListError = (ArrayList<CTVatTu>) objectListError.get(0);
				ArrayList<String> statusError = (ArrayList<String>) objectListError.get(1);
				if(ctvtListError.size() != 0)
				{
					long size = ctvtListError.size();
					multipartRequest.setAttribute("size", size);
					multipartRequest.setAttribute("ctvtListError", ctvtListError);
					multipartRequest.setAttribute("statusError", statusError);
					return new ModelAndView("import-excelError", "status", "formatException");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				file = new File("temp.xlsx");
				if(!ReadExcelCT.readXlsx(file))
					return new ModelAndView("import-excelCt", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelCt", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.vatTu);
	}
	
	@RequestMapping("/readExcelBpsd")
	protected ModelAndView readExcelBpsd(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelBpsd.readXls(file))
					return new ModelAndView("import-excelBpsd", "status", "formatException");
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelBpsd.readXlsx(file))
					return new ModelAndView("import-excelBpsd", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelBpsd", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.boPhanSuDung);
		
	}
	@RequestMapping("/readExcelNsx")
	protected ModelAndView readExcelNsx(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = "";
			extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelNsx.readXls(file)) {
					
				return new ModelAndView("import-excelNsx", "status", "formatException");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelNsx.readXlsx(file))
					return new ModelAndView("import-excelNsx", "status", "formatException");
			}
			else {
//				return new ModelAndView("import-excelNsx", "status", "unknownFile");
				multipartRequest.setAttribute("status", "success");
				return new ModelAndView(siteMap.noiSanXuat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.noiSanXuat);
		
	}
	@RequestMapping("/readExcelCl")
	protected ModelAndView readExcelCl(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelCl.readXls(file))
					return new ModelAndView("import-excelCl", "status", "formatException");
				else {
					multipartRequest.setAttribute("status", "success");
					return new ModelAndView(siteMap.chatLuong);
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				if(!ReadExcelCl.readXlsx(file))
					return new ModelAndView("import-excelCl", "status", "formatException");
			}
			else {
				return new ModelAndView("import-excelCl", "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.chatLuong);
	}
	public java.io.File uploadFile(MultipartHttpServletRequest multipartRequest) {
		try {
			String pathFile = context.getInitParameter("pathTemp");
			MultipartFile fileUpload = multipartRequest.getFile("file");
			String fileName = fileUpload.getOriginalFilename();
			String path = pathFile + fileName;
	    	java.io.File file = new java.io.File(path);
			file.createNewFile();
			fileUpload.transferTo(file);
			return file;
		} catch (IOException e) {
			return null;
		}
	}
}
