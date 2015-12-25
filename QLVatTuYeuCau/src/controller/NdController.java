package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import map.siteMap;
import model.CTNguoiDung;
import model.CTVatTu;
import model.ChatLuong;
import model.ChucDanh;
import model.DonViTinh;
import model.NguoiDung;
import model.NoiSanXuat;
import model.VatTu;

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
import util.Mail;
import util.SendMail;
import util.StringUtil;
import dao.CTNguoiDungDAO;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.ChucDanhDAO;
import dao.NguoiDungDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;



@Controller
public class NdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(NdController.class);
	
	@RequestMapping("/ndManage")
	public ModelAndView ndManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập quản lý người dùng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập quản lý người dùng");
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
			request.getCharacterEncoding();
			response.getCharacterEncoding();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			if (session.getAttribute("nguoiDung") == null)
				response.sendRedirect("login.jsp");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
					
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
			String action = request.getParameter("action");
			if ("manageNd".equalsIgnoreCase(action)) {
				ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
				return new ModelAndView("them-nguoi-dung","chucDanhList", chucDanhList);
			}
			if("AddNd".equalsIgnoreCase(action)) {
				String msnv = request.getParameter("msnv");
				String chucdanh = request.getParameter("chucdanh");
				String matkhau = request.getParameter("matkhau");
				String hoten = request.getParameter("hoten");
				String sdt = request.getParameter("sdt");
				String email = request.getParameter("email");
				String diachi = request.getParameter("diachi");
				nguoiDungDAO.addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
				ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau),0));
				
				ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
				return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
				
			}
			if("manageNd".equalsIgnoreCase(action)) {
				long size = ctNguoiDungDAO.size();
				ArrayList<String> ignoreList = new ArrayList<String>();
				ignoreList.add(truongPhongMa);
				ignoreList.add(adminMa);
				ArrayList<NguoiDung> ctndList =  (ArrayList<NguoiDung>) ctNguoiDungDAO.limit(ignoreList, page - 1, 10);
				request.setAttribute("size", size);
				ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) new ChucDanhDAO().getAllChucDanh();
				ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(new ArrayList<String>());
				request.setAttribute("chucDanhList", chucDanhList);
				return new ModelAndView("them-nguoi-dung", "nguoiDungList", nguoiDungList);
			}
			nguoiDungDAO.disconnect();
			chucDanhDAO.disconnect();
			ctNguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.ndManage);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi truy cập người dùng: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	
	@RequestMapping(value="/addNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addNd(@RequestParam("msnv") String msnv, @RequestParam("chucdanh") String chucdanh, @RequestParam("matkhau") String matkhau
	 , @RequestParam("hoten") String hoten, @RequestParam("sdt") String sdt,@RequestParam("email") String email, @RequestParam("diachi") String diachi, HttpServletRequest request){
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực khi thêm người dùng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền thêm người dùng");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
			String result = "";
			//System.out.println("MA: "+msnv);
			if((nguoiDungDAO.getNguoiDung(msnv)==null) && (ctNguoiDungDAO.getCTNguoiDung(msnv)==null))
			{
				nguoiDungDAO.addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
				ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau),0));
				result = "success";	
			} else {
				result = "fail";
			}
			nguoiDungDAO.disconnect();
			ctNguoiDungDAO.disconnect();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi thêm người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
			
	}
	
	@RequestMapping(value="/preUpdateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateNd(@RequestParam("msnv") String msnv, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực khi show cập nhật người dùng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền show cập nhật người dùng");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			NguoiDung nd = nguoiDungDAO.getNguoiDung(msnv);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(nd);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi truy cập người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/updateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateNd(@RequestParam("msnv") String msnv, @RequestParam("hoten") String hoten, @RequestParam("chucdanh") String chucdanh, @RequestParam("email") String email, @RequestParam("diachi") String diachi, @RequestParam("sdt") String sdt, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực cập nhật người dùng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền cập nhật người dùng");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDung nd = new NguoiDung(msnv, hoten,diachi,email,sdt,new ChucDanh(chucdanh));
			NguoiDungDAO nguoiDungDAO=new NguoiDungDAO();
			nguoiDungDAO.updateNguoiDung(nd);
			//System.out.println(nd.getChucDanh().getCdTen());
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(nd);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi cập nhật người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/changePass", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("msnv") String msnv, @RequestParam("passOld") String passOld
			, @RequestParam("passNew") String passNew, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập đổi mật khẩu");
				return JSonUtil.toJson("authentication error");
			} 
			CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
			String result = "";
			CTNguoiDung ctNguoiDung = ctNguoiDungDAO.getCTNguoiDung(msnv);
			if (ctNguoiDung == null || !ctNguoiDung.getMatKhau().equals(passOld)) {
				ctNguoiDung.setMatKhau(StringUtil.encryptMD5(passNew));
				ctNguoiDungDAO.updateCTNguoiDung(ctNguoiDung);
				result = "success";
			} else {
				result = "fail";
				//System.out.println(result);
			}
			ctNguoiDungDAO.disconnect();
			ctNguoiDungDAO.close();
			return JSonUtil.toJson(result);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi đổi mật khẩu người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.removeAttribute("nguoiDung");
		session.removeAttribute("nhatKy");
		session.removeAttribute("url");
		response.sendRedirect("login.jsp");
	}
	
	@RequestMapping(value="/lockNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String lockNd(@RequestParam("ndList") String ndList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực khóa người dùng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền khóa người dùng");
				return JSonUtil.toJson("authentication error");
			}
			String[] str =ndList.split("\\, ");
			NguoiDungDAO ndDAO =  new NguoiDungDAO();
			for(String msnv : str) {
				ndDAO.lockNguoiDung(msnv);
			}
			ndDAO.disconnect();
			return JSonUtil.toJson(ndList);
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			logger.error("Lỗi khi khóa người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/resetNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String resetNd(@RequestParam("ndList") String ndList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực reset người dùng");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền reset người dùng");
				return JSonUtil.toJson("authentication error");
			}
			String[] str =ndList.split("\\, ");
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			for(String msnv : str) {
				nguoiDungDAO.resetNguoiDung(msnv);
			}
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			logger.error("Lỗi khi reset người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
@RequestMapping(value="/resetMK", method=RequestMethod.GET, 
produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String resetMK(@RequestParam("ndList") String ndList, HttpServletRequest request) {
	try {
		HttpSession session = request.getSession(false);
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		String adminMa = context.getInitParameter("adminMa");
		if (authentication == null) { 
			logger.error("Không chứng thực reset mật khẩu người dùng");
			return JSonUtil.toJson("authentication error");
		} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
			logger.error("Không có quyền reset mật khẩu người dùng");
			return JSonUtil.toJson("authentication error");
		}
		String[] str =ndList.split("\\, ");
		CTNguoiDungDAO ctnguoiDungDAO = new CTNguoiDungDAO();
		for(String msnv : str) {
			String mk = ctnguoiDungDAO.resetMK(msnv);
			String account = context.getInitParameter("account");
			String password = context.getInitParameter("password");
			String host = context.getInitParameter("hosting");
			SendMail sendMail = new SendMail(account, password);
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(msnv);
			Mail mail = new Mail();
			mail.setFrom(account);
			mail.setTo(nguoiDung.getEmail());
			mail.setSubject("Mật khẩu mới");
			String content = "Bạn đã được khôi phục mật khẩu.\n";
			content += "Mật khẩu mới của bạn là: \n" + mk + "\n Vui lòng đăng nhập vào hệ thống làm việc để kiểm tra.\n Thân mến!";
			mail.setContent(content);
			sendMail.send(mail);
			nguoiDungDAO.disconnect();
		}
		ctnguoiDungDAO.disconnect();
		return JSonUtil.toJson(ndList);
	} catch (NullPointerException | IndexOutOfBoundsException e) {
		logger.error("Lỗi khi reset mật khẩu người dùng: " + e.getMessage());
		return JSonUtil.toJson("authentication error");
	}
}
	@RequestMapping("/login")
	public void login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String msnv = request.getParameter("msnv");
			String matKhau = request.getParameter("matkhau");
			CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
			NguoiDungDAO ndDAO = new NguoiDungDAO();
			int check = ctndDAO.login(msnv, StringUtil.encryptMD5(matKhau));
			ctndDAO.disconnect();
			if (check==1) {
				NguoiDung nguoiDung =  ndDAO. getNguoiDung(msnv);
				session.setAttribute("nguoiDung", nguoiDung);
				session.setMaxInactiveInterval(86400000);
				response.sendRedirect("home.html");
			} else {
				request.setAttribute("status", "fail");
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NullPointerException e) {
			logger.error("Lỗi khi login: " + e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	@RequestMapping("lockNguoiDung")
	public ModelAndView lockNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực khóa người dùng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền khóa người dùng");
				return new ModelAndView(siteMap.login);
			}
			ArrayList<String> ignoreList = new ArrayList<String>();
			
			ignoreList.add(adminMa);
			CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
			long size = nguoiDungDAO.size();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(ignoreList, page - 1, 10);
			request.setAttribute("size", size);
			//ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
			request.setAttribute("nguoiDungList", nguoiDungList);
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.lockNguoiDungPage, "nguoiDungList", nguoiDungList);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi khóa người dùng: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping("resetNguoiDung")
	public ModelAndView resetNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực reset người dùng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền reset người dùng");
				return new ModelAndView(siteMap.login);
			}
			CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
			long size = nguoiDungDAO.sizeReset();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limitReset(page - 1, 10);
			request.setAttribute("size", size);
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(adminMa);
			request.setAttribute("nguoiDungList", nguoiDungList);
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.resetNguoiDungPage, "nguoiDungList", nguoiDungList);
		} catch (NullPointerException e){
			logger.error("Lỗi khi reset người dùng: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping("resetPassword")
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực reset mật khẩu người dùng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền reset mật khẩu người dùng");
				return new ModelAndView(siteMap.login);
			}
			CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(adminMa);
			long size = nguoiDungDAO.size();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(ignoreList , page - 1, 10);
			request.setAttribute("size", size);
			NguoiDungDAO ndDAO = new NguoiDungDAO();
			request.setAttribute("nguoiDungList", nguoiDungList);
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.resetPasswordPage, "nguoiDungList", nguoiDungList);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi reset mật khẩu người dùng: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	/*
	@RequestMapping("updateNguoiDung")
	public ModelAndView updateNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập quản lý người dùng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập quản lý người dùng");
				return new ModelAndView(siteMap.login);
			}
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(truongPhongMa);
			ignoreList.add(adminMa);
			long size = nguoiDungDAO.size();
			ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(ignoreList ,page - 1, 10);
			request.setAttribute("size", size);
			ignoreList.add(adminMa);
		//	ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
			request.setAttribute("nguoiDungList", nguoiDungList);
			ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) chucDanhDAO.getAllChucDanh();
			request.setAttribute("chucDanhList", chucDanhList);
			chucDanhDAO.disconnect();
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.updateNguoiDungPage, "chucDanhList", chucDanhList);
		} catch (NullPointerException e) {
			
		}
	}
	*/
	@RequestMapping(value="/loadHoten", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadHoten(@RequestParam("msnv") String msnv, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập quản lý người dùng");
				return JSonUtil.toJson("authentication error"); 
			}
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			NguoiDung nguoiDung =  nguoiDungDAO.getNguoiDung(msnv);
			nguoiDungDAO.disconnect();
			String hoTen=nguoiDung.getHoTen();
			return JSonUtil.toJson(hoTen);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi truy cập quản lý người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error"); 
		}
	}
//	@RequestMapping(value="/loadPageNd", method=RequestMethod.GET, 
//			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	 public @ResponseBody String loadPageNd(@RequestParam("pageNumber") String pageNumber) {
//		System.out.println("MA: " + pageNumber);
//		CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
//		int page = Integer.parseInt(pageNumber);
//		ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.limit((page -1 ) * 10, 10);
//		nguoiDungDAO.disconnect();
//			return JSonUtil.toJson(ndList);
//	}
	@RequestMapping(value="/loadPageNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageNd(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực phân trang người dùng");
				return JSonUtil.toJson("authentication error"); 
			} 
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			int page = Integer.parseInt(pageNumber);
			ArrayList<String> ignoreList = new ArrayList<String>();
	//		ignoreList.add(truongPhongMa);
	//		ignoreList.add(adminMa);
			ArrayList<Object> objectList = new ArrayList<Object>();
			CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
			long sizeNd = ctndDAO.size();
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ctndDAO.limit(ignoreList, page * 10, 10);
			objectList.add(ndList);
			objectList.add((sizeNd - 1)/10);
		//	nguoiDungDAO.disconnect();
			ctndDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi phân trang người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error"); 
		}
	}
	@RequestMapping(value="/loadPageNdKP", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageNdKP(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập quản lý người dùng");
				return JSonUtil.toJson("authentication error"); 
			} 
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			ArrayList<Object> objectList = new ArrayList<Object>();
			int page = Integer.parseInt(pageNumber);
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(truongPhongMa);
			ignoreList.add(adminMa);
			CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
			long sizeNd = ctndDAO.size();
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ctndDAO.limit(ignoreList, page * 10, 10);
			objectList.add(ndList);
			objectList.add((sizeNd - 1)/10);
		//	nguoiDungDAO.disconnect();
			ctndDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException |NumberFormatException e ) {
			logger.error("Lỗi khi phân trang người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
	
	@RequestMapping(value="/timKiemNguoidung", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidung(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực tìm kiếm người dùng");
				return JSonUtil.toJson("authentication error"); 
			} 
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			ArrayList<NguoiDung> ndList = new ArrayList<NguoiDung> ();
			ArrayList<String> ignoreList = new ArrayList<String>();
			if(msnv.length() > 0)
				ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchMsnv(msnv, ignoreList);
			else
				ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchHoten(hoTen, ignoreList);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi tìm kiếm người dùng: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
}