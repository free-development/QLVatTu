package controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ChatLuong;
import model.ChucDanh;
import model.MucDich;
import model.NguoiDung;
import model.NoiSanXuat;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.ChucDanhDAO;
import dao.MucDichDAO;
import dao.NoiSanXuatDAO;
import map.siteMap;

@Controller
public class CdController extends HttpServlet {
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(CdController.class);
	private static final long serialVersionUID = 1L;
	int page = 1;
	@RequestMapping("/manageCd")
	public ModelAndView manageCd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			session.removeAttribute("errorList");
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			String action = request.getParameter("action");
			long size = chucDanhDAO.size();
			ArrayList<ChucDanh> chucDanhList =  (ArrayList<ChucDanh>) chucDanhDAO.limit(page -1, 10);
			request.setAttribute("size", size);
			chucDanhDAO.disconnect();
			return new ModelAndView("danh-muc-chuc-danh", "chucDanhList", chucDanhList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
	
	@RequestMapping(value="/preUpdateCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateCd(@RequestParam("cdMa") String cdMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			ChucDanh cd = chucDanhDAO.getChucDanh(cdMa);
			chucDanhDAO.disconnect();
			return JSonUtil.toJson(cd);
		} catch (NullPointerException e) {
			logger.error("NullPointer khi show cập nhật chức danh: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/deleteCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteCd(@RequestParam("cdList") String cdList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = cdList.split("\\, ");
			ChucDanhDAO cdDAO =  new ChucDanhDAO();
			for(String cdMa : str) {
				cdDAO.deleteChucDanh(cdMa);
			}
			cdDAO.disconnect();
			return JSonUtil.toJson(cdList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xóa chức danh: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		} catch  (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBounda Exception khi xóa chức danh: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/addCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addCd(@RequestParam("cdMa") String cdMa, @RequestParam("cdTen") String cdTen, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			String result = "success";
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			ChucDanh cd = chucDanhDAO.getChucDanh(cdMa);
			if(cd == null) {
				chucDanhDAO.addChucDanh(new ChucDanh(cdMa, cdTen,0));
				result = "success";	
			}
			else if (cd !=null && cd.getDaXoa()== 1){
				cd.setCdMa(cdMa);
				cd.setCdTen(cdTen);
				cd.setDaXoa(0);
				chucDanhDAO.updateChucDanh(cd);
			}
			else {
				result = "fail";
			}
			chucDanhDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm chức danh: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/updateCd", method=RequestMethod.GET, 
	produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateCd(@RequestParam("cdMaUpdate") String cdMaUpdate, @RequestParam("cdTenUpdate") String cdTenUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			ChucDanh cd = new ChucDanh(cdMaUpdate, cdTenUpdate,0);
			chucDanhDAO.updateChucDanh(cd);
			chucDanhDAO.disconnect();
			return JSonUtil.toJson(cd);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật chức danh: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/loadPageCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCd(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			ChucDanhDAO cdDAO = new ChucDanhDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<ChucDanh> cdList = (ArrayList<ChucDanh>) cdDAO.limit((page -1 ) * 10, 10);
			cdDAO.disconnect();
			return JSonUtil.toJson(cdList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang chức danh: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang chức danh: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
}
