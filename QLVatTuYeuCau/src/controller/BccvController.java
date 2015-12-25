package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CongVanDAO;
import dao.DonViDAO;
import dao.MucDichDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.NguoiDung;
import util.DateUtil;
import util.JSonUtil;

@Controller
public class BccvController extends HttpServlet {
	
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BccvController.class);
	private static final long serialVersionUID = 1L;
	private int page = 1;
	@RequestMapping("/manageBccv")
	protected ModelAndView manageBccv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập báo công văn");
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
			MucDichDAO mucDichDAO = new MucDichDAO();
			CongVanDAO congVanDAO = new CongVanDAO();
			ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
			ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("cvId", true);
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, null, orderBy, 0, Integer.MAX_VALUE);
			request.setAttribute("donViList", donViList);
			request.setAttribute("mucDichList", mucDichList);
			request.setAttribute("congVanList", congVanList);
			congVanDAO.disconnect();
			donViDAO.disconnect();
			mucDichDAO.disconnect();
			return new ModelAndView(siteMap.bCCongVan);
		} catch (NullPointerException e) {
			logger.error("Lỗi truy cập báo cáo công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	
	@RequestMapping("/exportBccv")
	protected ModelAndView exportBccv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) { 
				logger.error("Không chứng thực xuất báo cáo công văn");
				return new ModelAndView(siteMap.login);
			}
		
			CongVanDAO congVanDAO = new CongVanDAO();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("cvId", true);
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, null, orderBy, 0, Integer.MAX_VALUE);
			session.setAttribute("objectList", congVanList);
			congVanDAO.disconnect();
			return new ModelAndView(siteMap.xuatCongVan);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi xuất báo cáo công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping(value="/loadBcCongVan", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadBccv(@RequestParam("eCvNgayNhan") String eCvNgayNhan, @RequestParam("sCvNgayNhan") String sCvNgayNhan 
			, @RequestParam("eCvNgayDi") String eCvNgayDi, @RequestParam("sCvNgayDi") String sCvNgayDi
			, @RequestParam("mucDich") String mdMa, @RequestParam("trangThai") String ttMa 
			, @RequestParam("donVi") String dvMa, @RequestParam("cvSo") String cvSo, HttpServletRequest request
			 ) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực tìm kiếm công văn");
				return JSonUtil.toJson("authentication error");
			}
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			if (eCvNgayNhan != null && eCvNgayNhan.length() > 0)
				conditions.put("lecvNgayNhan", DateUtil.parseDate(eCvNgayNhan));
			if (sCvNgayNhan != null && sCvNgayNhan.length() > 0)
				conditions.put("gecvNgayNhan", DateUtil.parseDate(sCvNgayNhan));
			
			if (eCvNgayDi != null && eCvNgayDi.length() > 0)
				conditions.put("lecvNgayDi", DateUtil.parseDate(eCvNgayDi));
			if (sCvNgayDi != null && sCvNgayDi.length() > 0)
				conditions.put("gecvNgayDi", DateUtil.parseDate(sCvNgayDi));
			
			if (mdMa != null && mdMa.length() > 0)
				conditions.put("mucDich.mdMa", mdMa);
			if (dvMa != null && dvMa.length() > 0)
				conditions.put("donVi.dvMa", dvMa);
			if (cvSo != null && cvSo.length() > 0)
				conditions.put("cvSo", cvSo);
			if (ttMa != null && ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			orderBy.put("cvId", true);
			CongVanDAO congVanDAO = new CongVanDAO();
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, conditions, orderBy, 0, Integer.MAX_VALUE);
			session.setAttribute("congVanList", congVanList);
			congVanDAO.disconnect();
			return JSonUtil.toJson(congVanList);
		} catch (NullPointerException e ) {
			logger.error("Lỗi tìm kiếm báo cáo công văn: " + e.getMessage()); 
			return JSonUtil.toJson("authentication error");
		}
		
	}
}
