package vn.com.freesoft.docmanagement.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

import vn.com.freesoft.docmanagement.dao.MucDichDAO;
import vn.com.freesoft.docmanagement.entity.MucDich;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.JSonUtil;

@Controller
public class MdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(MdController.class);

	@RequestMapping("/manageMd")
	public ModelAndView manageMd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
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
			MucDichDAO mucDichDAO = new MucDichDAO();
			String action = request.getParameter("action");
			if ("manageMd".equalsIgnoreCase(action)) {
				long size = mucDichDAO.size();
				ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.limit(page - 1, 10);
				request.setAttribute("size", size);
				return new ModelAndView("danh-muc-muc-dich", "mucDichList", mucDichList);
			}
			mucDichDAO.disconnect();
			return new ModelAndView("login");

		} catch (NullPointerException e) {

			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/preUpdateMd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateMd(@RequestParam("mdMa") String mdMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			MucDichDAO mucDichDAO = new MucDichDAO();
			MucDich md = mucDichDAO.getMucDich(mdMa);
			mucDichDAO.disconnect();
			return JSonUtil.toJson(md);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show cập nhật mục đích: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteMd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteMd(@RequestParam("mdList") String mdList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = mdList.split("\\, ");

			MucDichDAO mdDAO = new MucDichDAO();
			for (String mdMa : str) {
				mdDAO.deleteMucDich(mdMa);
			}
			mdDAO.disconnect();
			return JSonUtil.toJson(mdList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xóa mục đích: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBoundsException khi xóa mục đích: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/addMd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addMd(@RequestParam("mdMa") String mdMa, @RequestParam("mdTen") String mdTen,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			String result = "success";
			MucDichDAO mucDichDAO = new MucDichDAO();
			MucDich md = mucDichDAO.getMucDich(mdMa);
			if (md == null) {
				mucDichDAO.addMucDich(new MucDich(mdMa, mdTen, 0));
				// System.out.println("success");
				result = "success";
			} else if (md != null && md.getDaXoa() == 1) {
				md.setMdMa(mdMa);
				md.setMdTen(mdTen);
				md.setDaXoa(0);
				mucDichDAO.updateMucDich(md);
			} else {
				// System.out.println("fail");
				result = "fail";
			}
			mucDichDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm mục đích: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/updateMd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateMd(@RequestParam("mdMaUpdate") String mdMaUpdate,
			@RequestParam("mdTenUpdate") String mdTenUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			MucDichDAO mucDichDAO = new MucDichDAO();
			MucDich md = new MucDich(mdMaUpdate, mdTenUpdate, 0);
			mucDichDAO.updateMucDich(md);
			mucDichDAO.disconnect();
			return JSonUtil.toJson(md);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật mục đích: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageMd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageMd(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục mục đích");
				return JSonUtil.toJson("authentication error");
			}
			MucDichDAO mdDAO = new MucDichDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<MucDich> mdList = (ArrayList<MucDich>) mdDAO.limit((page - 1) * 10, 10);
			mdDAO.disconnect();
			return JSonUtil.toJson(mdList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang mục đích: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang mục đích: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
}
