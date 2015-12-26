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
import javax.swing.JOptionPane;

import model.DonViTinh;
import model.NguoiDung;

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
import dao.DonViTinhDAO;
import dao.VaiTroDAO;
import map.siteMap;


@Controller
public class DvtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(DvtController.class); 
	@RequestMapping("/manageDvt")
	public ModelAndView manageDvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục đơn vị tính");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục đơn vị tính");
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
			session.removeAttribute("errorList");
			DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
			long size = donViTinhDAO.size();
			ArrayList<DonViTinh> donViTinhList =  (ArrayList<DonViTinh>) donViTinhDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			donViTinhDAO.disconnect();
			return new ModelAndView("danh-muc-don-vi-tinh", "donViTinhList", donViTinhList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception truy cập danh mục đơn vị tính");
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping(value="/preEditdvt", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditdvt(@RequestParam("dvtId") int dvt, HttpServletRequest request) {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục bộ đơn vị tính");
				return JSonUtil.toJson("authentication");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục đơn vị tính");
				return JSonUtil.toJson("authentication");
			}	
			//String[] temp = dvt.split("\\##");
			DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
			DonViTinh donViTinh = donViTinhDAO.getDonViTinh(dvt);
			donViTinhDAO.disconnect();
			session.setAttribute("dvtOld", donViTinh);
			return JSonUtil.toJson(donViTinh);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show cập nhật đơn vị tính");
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/adddvt", method=RequestMethod.GET)
	 public @ResponseBody String adddvt(@RequestParam("dvtTen") String dvtTen, HttpServletRequest request) {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục bộ đơn vị tính");
				return JSonUtil.toJson("authentication");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục đơn vị tính");
				return JSonUtil.toJson("authentication");
			}
			Object result = null;
			DonViTinhDAO dvtDAO = new DonViTinhDAO();
			DonViTinh dvt = dvtDAO.getDonViTinhByTen(dvtTen);
			if(dvt == null)
			{
				
				dvt = new DonViTinh(dvtTen,0);
				dvt.setDvtId(dvtDAO.getLastDvtId() + 1);
				dvtDAO.addDonViTinh(dvt);
				result = dvt;
			}
			else if(dvt!=null && dvt.getDaXoa() == 1){
				dvt.setDvtTen(dvtTen);
				dvt.setDaXoa(0);
				dvtDAO.updateDonViTinh(dvt);
				result = dvt;
			}
			else 
			{
				result = "fail";
			}
			dvtDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm đơn vị tính");
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/updatedvt", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updatedvt(@RequestParam("dvtTenUpdate") String dvtTenUpdate, HttpServletRequest request) {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục bộ đơn vị tính");
				return JSonUtil.toJson("authentication");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục đơn vị tính");
				return JSonUtil.toJson("authentication");
			}
			DonViTinhDAO dvtDAO = new DonViTinhDAO();
			DonViTinh dvt = (DonViTinh) session.getAttribute("dvtOld");
			dvt.setDvtTen(dvtTenUpdate);
			System.out.println(dvtTenUpdate);
//			dvt.setDaXoa(0);
			session.removeAttribute("dvtOld");
			dvtDAO.updateDonViTinh(dvt);
			dvtDAO.disconnect();
			return JSonUtil.toJson(dvt);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật đơn vị tính");
			return JSonUtil.toJson("authentication error");
		}
		
	}
	@RequestMapping(value="/deletedvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deletedvt(@RequestParam("dvtList") String dvtList, HttpServletRequest request) {
		try {
			System.out.println(dvtList);
			String[] str = dvtList.split("##");
			DonViTinhDAO dvtDAO =  new DonViTinhDAO();
			for(String dvtId : str) {
				dvtDAO.deleteDonViTinh(Integer.parseInt(dvtId));
			}
			dvtDAO.disconnect();
			return JSonUtil.toJson(dvtList);
		} catch (NumberFormatException e) {
			logger.error("NullPointer Exception khi xóa đơn vị tính");
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBounds Exception khi xóa đơn vị tính");
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/loadPagedvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageDvt(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục bộ đơn vị tính");
				return JSonUtil.toJson("authentication");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục đơn vị tính");
				return JSonUtil.toJson("authentication");
			}
			DonViTinhDAO dvtDAO = new DonViTinhDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<DonViTinh> dvtList = (ArrayList<DonViTinh>) dvtDAO.limit((page -1 ) * 10, 10);
			dvtDAO.disconnect();
			return JSonUtil.toJson(dvtList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang đơn vị tính");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang đơn vị tính");
			return JSonUtil.toJson("authentication error");
		}
	}
}