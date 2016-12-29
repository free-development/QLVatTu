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

import vn.com.freesoft.docmanagement.dao.NoiSanXuatDAO;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.entity.NoiSanXuat;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.JSonUtil;

@Controller
public class NsxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	int page = 1;
	private static final Logger logger = Logger.getLogger(NsxController.class);

	@RequestMapping("/manageNsx")
	public ModelAndView manageNsx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
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
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			long size = noiSanXuatDAO.size();
			ArrayList<NoiSanXuat> noiSanXuatList = (ArrayList<NoiSanXuat>) noiSanXuatDAO.limit(page - 1, 10);

			request.setAttribute("size", size);
			noiSanXuatDAO.disconnect();

			return new ModelAndView("danh-muc-noi-san-xuat", "noiSanXuatList", noiSanXuatList);

		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi truy cập danh mục nơi sản xuát");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/exportNsx")
	public ModelAndView exportNsx(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return new ModelAndView(siteMap.login);
			}
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			ArrayList<NoiSanXuat> allNoiSanXuatList = (ArrayList<NoiSanXuat>) noiSanXuatDAO.getAllNoiSanXuat();
			session.setAttribute("objectList", allNoiSanXuatList);
			noiSanXuatDAO.disconnect();
			return new ModelAndView(siteMap.xuatNsx);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất Nơi sản xuất");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/preEditNsx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preEditNsx(@RequestParam("nsxMa") String nsxMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			NoiSanXuat nsx = noiSanXuatDAO.getNoiSanXuat(nsxMa);
			noiSanXuatDAO.disconnect();
			return JSonUtil.toJson(nsx);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi truy cập danh mục nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/addNsx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addNsx(@RequestParam("nsxMa") String nsxMa, @RequestParam("nsxTen") String nsxTen,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			String result = "success";
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			NoiSanXuat nsx = noiSanXuatDAO.getNoiSanXuat(nsxMa);
			if (nsx == null) {
				noiSanXuatDAO.addNoiSanXuat(new NoiSanXuat(nsxMa, nsxTen, 0));
				result = "success";
			} else if (nsx != null && nsx.getDaXoa() == 1) {
				nsx.setNsxMa(nsxMa);
				nsx.setNsxTen(nsxTen);
				nsx.setDaXoa(0);
				noiSanXuatDAO.updateNoiSanXuat(nsx);
			} else {
				result = "fail";
			}
			noiSanXuatDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/updateNsx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateNsx(@RequestParam("nsxMaUpdate") String nsxMaUpdate,
			@RequestParam("nsxTenUpdate") String nsxTenUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			NoiSanXuat nsx = new NoiSanXuat(nsxMaUpdate, nsxTenUpdate, 0);
			noiSanXuatDAO.updateNoiSanXuat(nsx);
			noiSanXuatDAO.disconnect();
			return JSonUtil.toJson("success");
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteNsx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteNsx(@RequestParam("nsxList") String nsxList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = nsxList.split("\\, ");

			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			for (String nsxMa : str) {
				noiSanXuatDAO.deleteNoiSanXuat(nsxMa);
			}
			noiSanXuatDAO.disconnect();
			return JSonUtil.toJson("success");
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xóa nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBoundsException khi xóa nơi sản xuất: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageNsx", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageNsx(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) noiSanXuatDAO.limit((page - 1) * 10, 10);
			noiSanXuatDAO.disconnect();
			return JSonUtil.toJson(nsxList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e) {
			logger.error("NumberFormat Exception khi phân trang nơi sản xuát");
			return JSonUtil.toJson("authentication error");
		}
	}
}
