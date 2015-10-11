package controller;

import java.io.IOException;
import java.util.ArrayList;

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
	private   ServletContext context;
	
	@RequestMapping("/ndManage")
	public ModelAndView ndManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if (session.getAttribute("nguoiDung") == null)
			response.sendRedirect("login.jsp");
		
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
			ArrayList<NguoiDung> ctndList =  (ArrayList<NguoiDung>) ctNguoiDungDAO.limit(page - 1, 10);
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
	}
	
	@RequestMapping(value="/addNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addNd(@RequestParam("msnv") String msnv, @RequestParam("chucdanh") String chucdanh, @RequestParam("matkhau") String matkhau
	 , @RequestParam("hoten") String hoten, @RequestParam("sdt") String sdt,@RequestParam("email") String email, @RequestParam("diachi") String diachi){
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String result = "";
		//System.out.println("MA: "+msnv);
		if((nguoiDungDAO.getNguoiDung(msnv)==null)&&(ctNguoiDungDAO.getCTNguoiDung(msnv)==null))
		{
			nguoiDungDAO.addNguoiDung(new NguoiDung(msnv, hoten, diachi, email, sdt, new ChucDanh(chucdanh)));
			ctNguoiDungDAO.addCTNguoiDung(new CTNguoiDung(msnv, StringUtil.encryptMD5(matkhau),0));
			
//			System.out.println("success");
			result = "success";	
			
		}
		else
		{
//			
			result = "fail";
		}
		nguoiDungDAO.disconnect();
		ctNguoiDungDAO.disconnect();
			return JSonUtil.toJson(result);
			
	}
	
	@RequestMapping(value="/preUpdateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateNd(@RequestParam("msnv") String msnv) {
		//System.out.println(msnv);
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		NguoiDung nd = nguoiDungDAO.getNguoiDung(msnv);
		nguoiDungDAO.disconnect();
		return JSonUtil.toJson(nd);
	}
	@RequestMapping(value="/updateNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateNd(@RequestParam("msnv") String msnv, @RequestParam("hoten") String hoten, @RequestParam("chucdanh") String chucdanh, @RequestParam("email") String email, @RequestParam("diachi") String diachi, @RequestParam("sdt") String sdt) {
		/*System.out.println(msnv);
		System.out.println(hoten);
		System.out.println(chucdanh);
		System.out.println(email);
		System.out.println(diachi);
		System.out.println(sdt);*/
		NguoiDung nd = new NguoiDung(msnv, hoten,diachi,email,sdt,new ChucDanh(chucdanh));
		NguoiDungDAO nguoiDungDAO=new NguoiDungDAO();
		nguoiDungDAO.updateNguoiDung(nd);
		//System.out.println(nd.getChucDanh().getCdTen());
		nguoiDungDAO.disconnect();
		return JSonUtil.toJson(nd);
	}
	
	@RequestMapping(value="/changePass", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changePass(@RequestParam("msnv") String msnv, @RequestParam("passOld") String passOld
			, @RequestParam("passNew") String passNew) {
		CTNguoiDungDAO ctNguoiDungDAO = new CTNguoiDungDAO();
		String result = "";
		CTNguoiDung ctNguoiDung = ctNguoiDungDAO.getCTNguoiDung(msnv);
		if (ctNguoiDung == null || !ctNguoiDung.getMatKhau().equals(passOld)) {
			ctNguoiDung.setMatKhau(StringUtil.encryptMD5(passNew));
			ctNguoiDungDAO.updateCTNguoiDung(ctNguoiDung);
			result = "success";
		}
		else
		{
			result = "fail";
			//System.out.println(result);
		}
		ctNguoiDungDAO.disconnect();
		ctNguoiDungDAO.close();
		return JSonUtil.toJson(result);
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
	 public @ResponseBody String lockNd(@RequestParam("ndList") String ndList) {
		String[] str =ndList.split("\\, ");
		
		NguoiDungDAO ndDAO =  new NguoiDungDAO();
		for(String msnv : str) {
			ndDAO.lockNguoiDung(msnv);
		}
		ndDAO.disconnect();
		return JSonUtil.toJson(ndList);
	}
	@RequestMapping(value="/resetNd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String resetNd(@RequestParam("ndList") String ndList) {
		String[] str =ndList.split("\\, ");

		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		for(String msnv : str) {
			nguoiDungDAO.resetNguoiDung(msnv);
		}
		nguoiDungDAO.disconnect();
		return JSonUtil.toJson(ndList);
	}
	
	@RequestMapping(value="/resetMK", method=RequestMethod.GET, 
	produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody String resetMK(@RequestParam("ndList") String ndList) {
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
		}
	@RequestMapping("/login")
	public void login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
	}

	@RequestMapping("lockNguoiDung")
	public ModelAndView lockNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
		long size = nguoiDungDAO.size();
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		//ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
	
		request.setAttribute("nguoiDungList", nguoiDungList);
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.lockNguoiDungPage, "nguoiDungList", nguoiDungList);
	}
	@RequestMapping("resetNguoiDung")
	public ModelAndView resetNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
		long size = nguoiDungDAO.sizeReset();
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limitReset(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		request.setAttribute("nguoiDungList", nguoiDungList);
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.resetNguoiDungPage, "nguoiDungList", nguoiDungList);
	}
	@RequestMapping("resetPassword")
	public ModelAndView resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
		long size = nguoiDungDAO.size();
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO ndDAO = new NguoiDungDAO();
		ignoreList.add(adminMa);
		request.setAttribute("nguoiDungList", nguoiDungList);
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.resetPasswordPage, "nguoiDungList", nguoiDungList);
	}
	@RequestMapping("updateNguoiDung")
	public ModelAndView updateNguoiDung(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CTNguoiDungDAO nguoiDungDAO = new CTNguoiDungDAO();
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		long size = nguoiDungDAO.size();
		ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.limit(page - 1, 10);
		request.setAttribute("size", size);
		ArrayList<String> ignoreList = new ArrayList<String>();
		String adminMa = context.getInitParameter("adminMa");
		ignoreList.add(adminMa);
	//	ArrayList<NguoiDung> nguoiDungList =  (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
		request.setAttribute("nguoiDungList", nguoiDungList);
		ArrayList<ChucDanh> chucDanhList = (ArrayList<ChucDanh>) chucDanhDAO.getAllChucDanh();
		request.setAttribute("chucDanhList", chucDanhList);
		chucDanhDAO.disconnect();
		nguoiDungDAO.disconnect();
		return new ModelAndView(siteMap.updateNguoiDungPage, "chucDanhList", chucDanhList);
	}
	
	@RequestMapping(value="/loadHoten", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadHoten(@RequestParam("msnv") String msnv) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		NguoiDung nguoiDung =  nguoiDungDAO.getNguoiDung(msnv);
		nguoiDungDAO.disconnect();
		String hoTen=nguoiDung.getHoTen();
		return JSonUtil.toJson(hoTen);
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
	 public @ResponseBody String loadPageNd(@RequestParam("pageNumber") String pageNumber) {
		//NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
		long sizeNd = ctndDAO.size();
		ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ctndDAO.limit(page * 10, 10);
		objectList.add(ndList);
		objectList.add((sizeNd - 1)/10);
	//	nguoiDungDAO.disconnect();
		ctndDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/loadPageNdKP", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageNdKP(@RequestParam("pageNumber") String pageNumber) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		CTNguoiDungDAO ctndDAO = new CTNguoiDungDAO();
		long sizeNd = ctndDAO.sizeReset();
		ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ctndDAO.limitReset(page * 10, 10);
		//System.out.println("****************" + ndList.size() + "*************");
		objectList.add(ndList);
		objectList.add((sizeNd - 1)/10);
		nguoiDungDAO.disconnect();
		ctndDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}
	
	@RequestMapping(value="/timKiemNguoidung", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidung(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		//System.out.println("Ma goi qua " + msnv);
		//System.out.println("Ten goi qua " + hoTen);
		if(msnv != ""){
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchMsnv(msnv);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		}
		else
		{
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) nguoiDungDAO.searchHoten(hoTen);
			//System.out.println("Ten: "+vtTen);
			nguoiDungDAO.disconnect();
			return JSonUtil.toJson(ndList);
		}
	}
}