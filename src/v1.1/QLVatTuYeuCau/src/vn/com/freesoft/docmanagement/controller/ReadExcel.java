package vn.com.freesoft.docmanagement.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import vn.com.freesoft.docmanagement.dao.ReadExcelBpsd;
import vn.com.freesoft.docmanagement.dao.ReadExcelCT;
import vn.com.freesoft.docmanagement.dao.ReadExcelCl;
import vn.com.freesoft.docmanagement.dao.ReadExcelNsx;
import vn.com.freesoft.docmanagement.dao.ReadExcelTon;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.FileUtil;

/**
 * Servlet implementation class ReadExcel
 */
@Controller
public class ReadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(ReadExcel.class);

	@RequestMapping("/readExcelTonkho")
	protected ModelAndView readExcelTonkho(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = multipartRequest.getSession();
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy upload tồn kho");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("size");
			try {
				java.io.File file = uploadFile(multipartRequest);
				String extenstionFile = FileUtil.getExtension(file);
				ArrayList<Object> errorList = new ArrayList<Object>();
				if ("xls".equalsIgnoreCase(extenstionFile)) {
					errorList = ReadExcelTon.readXls(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importTonKhoError, "status", "formatException");
					}

				} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
					errorList = ReadExcelTon.readXlsx(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importTonKhoError, "status", "formatException");
					}
				} else {
					return new ModelAndView(siteMap.vatTuTonKho, "status", "unknownFile");
				}
				file.delete();
			} catch (Exception e) {
				if (session.getAttribute("errorList") != null)
					return new ModelAndView(siteMap.importTonKhoError, "status", "formatException");
			}

			multipartRequest.setAttribute("status", "success");

			return new ModelAndView(siteMap.vatTuTonKho);
		} catch (NullPointerException e) {
			logger.error("Lỗi upload tồn kho: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}

	}

	@RequestMapping("/readExcelCt")
	protected ModelAndView readExcelCt(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = multipartRequest.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực import chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("ctVatTuList");
			try {
				java.io.File file = uploadFile(multipartRequest);
				String extenstionFile = FileUtil.getExtension(file);
				if ("xls".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = ReadExcelCT.readXls(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importVatTuError, "status", "formatException");
					}
				} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = ReadExcelCT.readXlsx(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importVatTuError, "status", "formatException");
					}
				} else {
					return new ModelAndView(siteMap.vatTu, "status", "formatException");
				}
				file.delete();
			} catch (Exception e) {
				if (session.getAttribute("errorList") != null)
					return new ModelAndView(siteMap.importVatTuError, "status", "formatException");
			}
			multipartRequest.setAttribute("status", "success");
			return new ModelAndView(siteMap.vatTu);
		} catch (NullPointerException e) {
			logger.error("Lỗi upload chi tiết vật tư: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/readExcelBpsd")
	protected ModelAndView readExcelBpsd(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = multipartRequest.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực khi import bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("donViList");
			try {
				java.io.File file = uploadFile(multipartRequest);
				String extenstionFile = FileUtil.getExtension(file);
				if ("xls".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelBpsd.readXls(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importBpsdError, "status", "formatException");
					}
				} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelBpsd.readXlsx(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importBpsdError, "status", "formatException");
					}
				} else {
					return new ModelAndView(siteMap.boPhanSuDung, "status", "unknownFile");
				}
				file.delete();
			} catch (Exception e) {
				if (session.getAttribute("errorList") != null)
					return new ModelAndView(siteMap.importBpsdError, "status", "formatException");
			}
			multipartRequest.setAttribute("status", "success");
			return new ModelAndView(siteMap.boPhanSuDung);
		} catch (NullPointerException e) {
			logger.error("Lỗi upload bo phan su dung: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}

	}

	@RequestMapping("/readExcelNsx")
	protected ModelAndView readExcelNsx(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = multipartRequest.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực import nơi sản xuất");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("allNoiSanXuatList");
			try {
				java.io.File file = uploadFile(multipartRequest);
				String extenstionFile = "";
				extenstionFile = FileUtil.getExtension(file);
				if ("xls".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelNsx.readXls(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importErrorNsx, "status", "formatException");
					}
				} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelNsx.readXlsx(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importErrorNsx, "status", "formatException");
					}
				} else {
					return new ModelAndView(siteMap.noiSanXuat, "status", "unknownFile");
				}
				multipartRequest.setAttribute("status", "success");
				file.delete();
			} catch (Exception e) {
				if (session.getAttribute("errorList") != null)
					return new ModelAndView(siteMap.importErrorNsx, "status", "formatException");
			}
			return new ModelAndView(siteMap.noiSanXuat);
		} catch (NullPointerException e) {
			logger.error("Lỗi upload nơi sản xuất: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}

	}

	@RequestMapping("/readExcelCl")
	protected ModelAndView readExcelCl(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws ServletException {
		try {
			HttpSession session = multipartRequest.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực import chất lượng");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("chatLuongList");
			try {
				response.setContentType("text/html");
				java.io.File file = uploadFile(multipartRequest);
				String extenstionFile = FileUtil.getExtension(file);
				if ("xls".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelCl.readXls(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importErrorCl, "status", "formatException");
					}
				} else if ("xlsx".equalsIgnoreCase(extenstionFile)) {
					ArrayList<Object> errorList = new ArrayList<Object>();
					errorList = ReadExcelCl.readXlsx(file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return new ModelAndView(siteMap.importErrorCl, "status", "formatException");
					}

				} else {
					return new ModelAndView(siteMap.chatLuong, "status", "unknownFile");
				}
				file.delete();
			} catch (Exception e) {
				if (session.getAttribute("errorList") != null)
					return new ModelAndView(siteMap.importErrorCl, "status", "formatException");
			}
			multipartRequest.setAttribute("status", "success");
			return new ModelAndView(siteMap.chatLuong);
		} catch (NullPointerException e) {
			logger.error("Lỗi upload chất lượng: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
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
