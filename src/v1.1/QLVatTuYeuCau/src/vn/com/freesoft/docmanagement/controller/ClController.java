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

import vn.com.freesoft.docmanagement.dao.ChatLuongDAO;
import vn.com.freesoft.docmanagement.entity.ChatLuong;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.JSonUtil;

@Controller
public class ClController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(ClController.class);

	@RequestMapping("/manageCl")
	public ModelAndView manageCl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
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
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			request.getCharacterEncoding();
			response.getCharacterEncoding();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			long size = chatLuongDAO.size();
			ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) chatLuongDAO.limit(page - 1, 10);
			ArrayList<ChatLuong> allChatLuongList = (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
			session.setAttribute("allChatLuongList", allChatLuongList);
			request.setAttribute("size", size);
			chatLuongDAO.disconnect();
			return new ModelAndView("danh-muc-chat-luong", "chatLuongList", chatLuongList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/exportCl")
	public ModelAndView exportCl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				return new ModelAndView(siteMap.login);
			}
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			ArrayList<ChatLuong> allChatLuongList = (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
			session.setAttribute("objectList", allChatLuongList);
			chatLuongDAO.disconnect();
			return new ModelAndView(siteMap.xuatCl);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất chất lượng");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/preUpdateCl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateCl(@RequestParam("clMa") String clMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			}
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			ChatLuong cl = chatLuongDAO.getChatLuong(clMa);
			chatLuongDAO.disconnect();
			return JSonUtil.toJson(cl);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show cập nhật chất lượng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteCl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteCl(@RequestParam("clList") String clList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			}
			String[] str = clList.split("\\, ");

			ChatLuongDAO clDAO = new ChatLuongDAO();
			for (String clMa : str) {
				clDAO.deleteChatLuong(clMa);
			}
			clDAO.disconnect();
			return JSonUtil.toJson("success");
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception xóa xuất chất lượng");
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e) {
			logger.error("IndexOutOfBounds Exception khi xóa chất lượng");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/addCl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addCl(@RequestParam("clMa") String clMa, @RequestParam("clTen") String clTen,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			}
			String result = "success";
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			ChatLuong cl = chatLuongDAO.getChatLuong(clMa);
			if (cl == null) {
				chatLuongDAO.addChatLuong(new ChatLuong(clMa, clTen, 0));
				result = "success";
			} else if (cl.getDaXoa() == 1) {
				cl.setClMa(clMa);
				cl.setClTen(clTen);
				cl.setDaXoa(0);
				chatLuongDAO.updateChatLuong(cl);
			} else {
				result = "fail";
			}
			chatLuongDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm chất lượng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}

	}

	@RequestMapping(value = "/updateCl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateCl(@RequestParam("clMaUpdate") String clMaUpdate,
			@RequestParam("clTenUpdate") String clTenUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			}
			ChatLuong cl = new ChatLuong(clMaUpdate, clTenUpdate, 0);
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			chatLuongDAO.updateChatLuong(cl);
			chatLuongDAO.disconnect();
			return JSonUtil.toJson(cl);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật chất lượng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageCl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageCl(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục chất lượng");
				JSonUtil.toJson("authentication error");
			}
			ChatLuongDAO clDAO = new ChatLuongDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<ChatLuong> clList = (ArrayList<ChatLuong>) clDAO.limit((page - 1) * 10, 10);
			clDAO.disconnect();
			return JSonUtil.toJson(clList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang chất lượng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang chất lượng: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
}
