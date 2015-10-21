package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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

	@Autowired
		private ServletContext context; 
	@RequestMapping("/readExcelTonkho")
	protected ModelAndView readExcelTonkho(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = multipartRequest.getSession();
		session.removeAttribute("ctVatTuList");
		session.removeAttribute("size");
		try {
			
			java.io.File file = uploadFile(multipartRequest);
			System.out.println(file.getName());
			String extenstionFile = FileUtil.getExtension(file);
			ArrayList<Object> objectListError = new ArrayList<Object>();
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				objectListError = ReadExcelTon.readXls(file);
				if(objectListError.size() > 0) {
					session.setAttribute("objectListError", objectListError);
					return new ModelAndView(siteMap.importTonKhoError, "status", "formatException");
				}
				
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				objectListError = ReadExcelTon.readXlsx(file);
				if(objectListError.size() > 0) {
					session.setAttribute("objectListError", objectListError);
					return new ModelAndView(siteMap.importTonKhoError, "status", "formatException");
				}
			}
			else {
				return new ModelAndView(siteMap.vatTuTonKho, "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		multipartRequest.setAttribute("status", "success");
		
		return new ModelAndView(siteMap.vatTuTonKho);
		
	}
	@RequestMapping("/readExcelCt")
	protected ModelAndView readExcelCt(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = multipartRequest.getSession(false);
		session.removeAttribute("ctVatTuList");
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> objectListError = ReadExcelCT.readXls(file);
				ArrayList<CTVatTu> ctvtListError = (ArrayList<CTVatTu>) objectListError.get(0);
				ArrayList<String> statusError = (ArrayList<String>) objectListError.get(1);
				if(ctvtListError.size() > 0)
				{
					
//					session.setAttribute("ctvtListError", ctvtListError);
//					session.setAttribute("statusError", statusError);
					session.setAttribute("errorListVatTu", objectListError);
					return new ModelAndView(siteMap.importVatTuError, "statusError", "list import error");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> objectListError = ReadExcelCT.readXlsx(file);
//				ArrayList<CTVatTu> ctvtListError = (ArrayList<CTVatTu>) objectListError.get(0);
//				ArrayList<String> statusError = (ArrayList<String>) objectListError.get(1);
				if(objectListError.size() > 0)
				{
//					long size = ctvtListError.size();
//					multipartRequest.setAttribute("size", size);
//					session.setAttribute("ctvtListError", ctvtListError);
//					session.setAttribute("statusError", statusError);
					session.setAttribute("errorListVatTu", objectListError);
					return new ModelAndView(siteMap.importVatTuError, "statusError", "list import error");
				}
			}
			else {
				return new ModelAndView(siteMap.vatTu, "status", "formatException");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(siteMap.login);
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.vatTu);
	}
	
	@RequestMapping("/readExcelBpsd")
	protected ModelAndView readExcelBpsd(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = multipartRequest.getSession(false);
		session.removeAttribute("donViList");
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelBpsd.readXls(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importBpsdError, "status", "formatException");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelBpsd.readXlsx(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importBpsdError, "status", "formatException");
				}
			}
			else {
				return new ModelAndView(siteMap.boPhanSuDung, "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(siteMap.login);
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.boPhanSuDung);
		
	}
	@RequestMapping("/readExcelNsx")
	protected ModelAndView readExcelNsx(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = multipartRequest.getSession(false);
		session.removeAttribute("allNoiSanXuatList");
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = "";
			extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelNsx.readXls(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importErrorNsx, "status", "formatException");
				}
			}
			else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelNsx.readXlsx(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importErrorNsx, "status", "formatException");
				}
			}
			else {
				return new ModelAndView(siteMap.noiSanXuat, "status", "unknownFile");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(siteMap.login);
			
		}
		multipartRequest.setAttribute("status", "success");
		return new ModelAndView(siteMap.noiSanXuat);
		
	}
	@RequestMapping("/readExcelCl")
	protected ModelAndView readExcelCl(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException {
		HttpSession session = multipartRequest.getSession(false);
		session.removeAttribute("chatLuongList");
		try {
			java.io.File file = uploadFile(multipartRequest);
			String extenstionFile = FileUtil.getExtension(file);
			if ("xls".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelCl.readXls(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importErrorCl, "status", "formatException");
				}
			} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
				ArrayList<Object> errorList = new ArrayList<Object>();
				errorList = ReadExcelCl.readXlsx(file);
				if(errorList.size() > 0) {
					session.setAttribute("errorList", errorList);
					return new ModelAndView(siteMap.importErrorCl, "status", "formatException");
				}
			} else {
				return new ModelAndView(siteMap.chatLuong, "status", "unknownFile");
			}
		} catch (Exception e) {
			return new ModelAndView(siteMap.login);
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

