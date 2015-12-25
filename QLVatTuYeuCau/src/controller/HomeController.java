package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.CongVanDAO;
import dao.NhatKyDAO;
import dao.VTCongVanDAO;
import map.siteMap;
import model.CTVatTu;
import model.CongVan;
import model.NguoiDung;
import model.NhatKy;
import model.VaiTro;
import util.JSonUtil;


@Controller
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(HomeController.class);
    private int nhatKyPage = 0;
    private int workPage = 0;
    private int alertPage = 0;
   @RequestMapping("/home")
	public ModelAndView manageLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   try {
		   HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy trang chủ");
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
			String truongPhongMa = context.getInitParameter("truongPhongMa");
		   	String vanThuMa = context.getInitParameter("vanThuMa");
		   	String nhanVienMa = context.getInitParameter("nhanVienMa");
		   	nhatKyPage = 0;
		    workPage = 0;
		    alertPage = 0;
		   	NhatKyDAO nhatKyDAO = new NhatKyDAO();
			
			
			ArrayList<NhatKy> nhatKyList = nhatKyDAO.getLimitByMsnv(authentication.getMsnv(), 0, 10);
			nhatKyDAO.disconnect();
			request.setAttribute("nhatKyList", nhatKyList);
			String cdMa = authentication.getChucDanh().getCdMa();
			if (cdMa.equals(truongPhongMa) || cdMa.equals(vanThuMa) || cdMa.equals(adminMa)) {
				CongVanDAO congVanDAO = new CongVanDAO();
				HashMap<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("trangThai.ttMa", "CGQ");
				HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
				ArrayList<CongVan> congVanList = congVanDAO.searchLimit(null, conditions, orderBy, 0, 10);
				request.setAttribute("congVanList", congVanList);
				
				
				congVanDAO.disconnect();
				
				if (cdMa.equals(truongPhongMa) || cdMa.equals(adminMa)) {
					CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
					ArrayList<CTVatTu> ctVatTuListAlert = ctVatTuDAO.getCtVatTuListAlert(0, 10);
					request.setAttribute("ctVatTuListAlert", ctVatTuListAlert);
					ctVatTuDAO.disconnect();
				}
				return new ModelAndView("home");
			}
			else if (cdMa.equals(nhanVienMa)) {
				VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
				CongVanDAO congVanDAO = new CongVanDAO();
				String msnv = authentication.getMsnv();
				ArrayList<Integer> cvIdList = vtCongVanDAO.groupByMsnvLimit(msnv, 0, 10);
				ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
				ArrayList<CongVan> congVanList = new ArrayList<CongVan>();
				ArrayList<ArrayList<String>> trangThaiList = new ArrayList<ArrayList<String>>(); 
				if (cvIdList.size() > 0) { 
					vaiTroList = vtCongVanDAO.getVaiTroList(msnv, cvIdList);
					trangThaiList = vtCongVanDAO.getTrangThai(msnv, cvIdList, vaiTroList);
					for (Integer cvId : cvIdList) {
						CongVan congVan = congVanDAO.getCongVan(cvId);
						congVanList.add(congVan);
					}
				}
				
				vtCongVanDAO.disconnect();
				congVanDAO.disconnect();
				nhatKyDAO.disconnect();
				request.setAttribute("congVanList", congVanList);
				request.setAttribute("trangThaiList", trangThaiList);
				request.setAttribute("vaiTroList", vaiTroList);
				request.setAttribute("nhatKyList", nhatKyList);
				return new ModelAndView("home");
			}
			return new ModelAndView(siteMap.login);
	   } catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
   @RequestMapping(value="/showMoreNhatKy", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody String showMoreNhatKy(HttpServletRequest request,
	            HttpServletResponse response) {
	   	try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực show nhật ký");
				return JSonUtil.toJson("authentication error");
			}
		   	nhatKyPage ++;
		   	NhatKyDAO nhatKyDAO = new NhatKyDAO();
			ArrayList<NhatKy> nhatKyList = nhatKyDAO.getLimitByMsnv(authentication.getMsnv(), nhatKyPage * 10, 10);
			nhatKyDAO.disconnect();
			return JSonUtil.toJson(nhatKyList);
	   	} catch (NullPointerException |  NumberFormatException e) {
	   		logger.error("Lỗi khi show nhật ký");
			return JSonUtil.toJson("authentication error");
	   	}
   }
   @RequestMapping(value="/showMoreWork", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody String showMoreWork(HttpServletRequest request,
	            HttpServletResponse response) {
	   try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực show công việc");
				return JSonUtil.toJson("authentication error");
			}
		   	workPage ++;
		   	String truongPhongMa = context.getInitParameter("truongPhongMa");
		   	String vanThuMa = context.getInitParameter("vanThuMa");
		   	String nhanVienMa = context.getInitParameter("nhanVienMa");
			String cdMa = authentication.getChucDanh().getCdMa();
			if (cdMa.equals(truongPhongMa) || cdMa.equals(vanThuMa) || cdMa.equals(adminMa)) {
				CongVanDAO congVanDAO = new CongVanDAO();
				HashMap<String, Object> conditions = new HashMap<String, Object>();
				conditions.put("trangThai.ttMa", "CGQ");
				HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
				ArrayList<CongVan> congVanList = congVanDAO.searchLimit(null, conditions, orderBy, workPage * 10, 10);
				congVanDAO.disconnect();
				return JSonUtil.toJson(congVanList);
			}
			else if (cdMa.equals(nhanVienMa)) {
				VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
				CongVanDAO congVanDAO = new CongVanDAO();
				String msnv = authentication.getMsnv();
				ArrayList<Integer> cvIdList = vtCongVanDAO.groupByMsnvLimit(msnv, workPage * 10, 10);
				ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
				ArrayList<CongVan> congVanList = new ArrayList<CongVan>();
				ArrayList<ArrayList<String>> trangThaiList = new ArrayList<ArrayList<String>>(); 
				if (cvIdList.size() > 0) { 
					vaiTroList = vtCongVanDAO.getVaiTroList(msnv, cvIdList);
					trangThaiList = vtCongVanDAO.getTrangThai(msnv, cvIdList, vaiTroList);
					for (Integer cvId : cvIdList) {
						CongVan congVan = congVanDAO.getCongVan(cvId);
						congVanList.add(congVan);
					}
				}
				vtCongVanDAO.disconnect();
				congVanDAO.disconnect();
				ArrayList<Object> objectList = new ArrayList<Object>();
				objectList.add(congVanList);
				objectList.add(vaiTroList);
				objectList.add(trangThaiList);
				return JSonUtil.toJson(objectList);
			}
			return JSonUtil.toJson("error");
	   } catch (NullPointerException | NumberFormatException e) {
		   	logger.error("Không chứng thực show công việc");
			return JSonUtil.toJson("authentication error");
	   }
   }
   @RequestMapping(value="/showMoreAlert", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String showMoreAlert(HttpServletRequest request,
	            HttpServletResponse response) {
	   try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực phân trang vật tư dưới định mức");
				return JSonUtil.toJson("authentication error");
			}
		   	alertPage ++;
		   	CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> ctVatTuListAlert = ctVatTuDAO.getCtVatTuListAlert(alertPage * 10, 10);
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(ctVatTuListAlert);
	   } catch (NullPointerException e) {
		   logger.error("Không chứng thực phân trang vật tư dưới định mức");
			return JSonUtil.toJson("authentication error");
	   }
	}
   
	@RequestMapping("/exportAlert")
	public void exportVatTuAlert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) { 
				logger.error("Không chứng thực xuất vật tư dưới định mức");
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> ctVatTuListAlert = ctVatTuDAO.getCtVatTuListAlert(0, Integer.MAX_VALUE);
			session.setAttribute("objectList", ctVatTuListAlert);
			ctVatTuDAO.disconnect();
			RequestDispatcher dispatcher = request.getRequestDispatcher("xuat-vat-tu-canh-bao.jsp");
			dispatcher.forward(request, response);
		} catch (NullPointerException e) {
			logger.error("Không chứng thực xuất vật tư dưới định mức");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
   }
}
