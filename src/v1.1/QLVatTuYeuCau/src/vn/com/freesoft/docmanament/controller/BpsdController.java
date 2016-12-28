package vn.com.freesoft.docmanament.controller;

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

import vn.com.freesoft.docmanament.dao.DonViDAO;
import vn.com.freesoft.docmanament.entity.DonVi;
import vn.com.freesoft.docmanament.entity.NguoiDung;
import vn.com.freesoft.docmanament.mapping.siteMap;
import vn.com.freesoft.docmanament.util.JSonUtil;

@Controller
public class BpsdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BpsdController.class);

	@RequestMapping("/manageBpsd")
	public ModelAndView manageBpsd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
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
			DonViDAO donViDAO = new DonViDAO();
			long size = donViDAO.size();
			ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.limit(page - 1, 10);
			ArrayList<DonVi> allDonViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
			session.setAttribute("allDonViList", allDonViList);
			session.setAttribute("size", size);
			donViDAO.disconnect();
			return new ModelAndView("danh-muc-bo-phan", "donViList", donViList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/exportBpsd")
	public ModelAndView exportBpsd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			}
			DonViDAO donViDAO = new DonViDAO();
			ArrayList<DonVi> allDonViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
			session.setAttribute("objectList", allDonViList);
			donViDAO.disconnect();
			return new ModelAndView(siteMap.xuatDv);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất danh mục bộ phận sử dụng");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/preEditBp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preEditBp(@RequestParam("dvMa") String dvMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			DonViDAO donViDAO = new DonViDAO();
			DonVi dv = donViDAO.getDonVi(dvMa);
			donViDAO.disconnect();
			return JSonUtil.toJson(dv);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show update bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/addBp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addBp(@RequestParam("dvMa") String dvMa, @RequestParam("dvTen") String dvTen,
			@RequestParam("sdt") String sdt, @RequestParam("diaChi") String diaChi, @RequestParam("email") String email,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			String result = "";
			DonViDAO donViDAO = new DonViDAO();
			DonVi donVi = new DonVi(dvMa, dvTen, sdt, diaChi, email, 0);
			DonVi donViCheck = donViDAO.getDonVi(dvMa);
			if (donViCheck == null) {
				donViDAO.addDonVi(donVi);
				result = "success";
			} else if (donViCheck.getDaXoa() == 1) {
				donViDAO.updateDonVi(donVi);
				result = "success";
			} else {
				result = "fail";
			}
			donViDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/updateBp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateBp(@RequestParam("dvMaUpdate") String dvMaUpdate,
			@RequestParam("dvTenUpdate") String dvTenUpdate, @RequestParam("sdtUpdate") String sdtUpdate,
			@RequestParam("diaChiUpdate") String diaChiUpdate, @RequestParam("emailUpdate") String emailUpdate,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			DonViDAO donViDAO = new DonViDAO();
			DonVi dv = new DonVi(dvMaUpdate, dvTenUpdate, sdtUpdate, diaChiUpdate, emailUpdate, 0);
			donViDAO.updateDonVi(dv);
			donViDAO.disconnect();
			return JSonUtil.toJson(dv);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thay đổi bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteBp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteBp(@RequestParam("dvList") String dvList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = dvList.split("\\, ");

			DonViDAO dvDAO = new DonViDAO();
			for (String dvMa : str) {
				dvDAO.deleteDonVi(dvMa);
			}
			dvDAO.disconnect();
			return JSonUtil.toJson(dvList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xóa bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageDv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageDv(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			String result = "";
			DonViDAO dvDAO = new DonViDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<DonVi> dvList = (ArrayList<DonVi>) dvDAO.limit((page - 1) * 10, 10);
			dvDAO.disconnect();
			return JSonUtil.toJson(dvList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e) {
			logger.error("NumberFormat Exception khi phân trang bộ phận sử dụng");
			return JSonUtil.toJson("authentication error");
		}
	}
}
