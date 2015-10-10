package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import util.DateUtil;
import util.JSonUtil;

@Controller
public class BccvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int page = 1;
	@RequestMapping("/manageBccv")
	protected ModelAndView manageBccv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session.getAttribute("nguoiDung") == null)
			response.sendRedirect("login.jsp");

		String action = request.getParameter("action");
		
		YeuCauDAO yeuCauDAO = new YeuCauDAO();
//		TrangThaiDAO trangThaiDAO = new TrangThaiDAO();
		DonViDAO donViDAO = new DonViDAO();
		MucDichDAO mucDichDAO = new MucDichDAO();
		CongVanDAO congVanDAO = new CongVanDAO();
		//System.out.println(action);
		if ("baocaocv".equalsIgnoreCase(action)) {
	//		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
			ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
			ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("cvId", true);
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, null, orderBy, 0, Integer.MAX_VALUE);
			
	//		session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
	//		session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
			session.setAttribute("donViList", donViList);
			session.setAttribute("mucDichList", mucDichList);
	//		session.setAttribute("trangThaiList", trangThaiList);
			session.setAttribute("congVanList", congVanList);
			
	//		yeuCauDAO.disconnect();
			congVanDAO.disconnect();
			donViDAO.disconnect();
			mucDichDAO.disconnect();
			return new ModelAndView(siteMap.bCCongVan);
			
		}
		return new ModelAndView("login");
	}
	@RequestMapping(value="/loadBcCongVan", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadBccv(@RequestParam("eCvNgayNhan") String eCvNgayNhan, @RequestParam("sCvNgayNhan") String sCvNgayNhan 
			, @RequestParam("eCvNgayDi") String eCvNgayDi, @RequestParam("sCvNgayDi") String sCvNgayDi
			, @RequestParam("mucDich") String mdMa, @RequestParam("trangThai") String ttMa 
			, @RequestParam("donVi") String dvMa, @RequestParam("cvSo") String cvSo, HttpServletRequest request
			 ) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		//System.out.println(eCvNgayDi);
		//System.out.println(sCvNgayDi);
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
		HttpSession session = request.getSession(false);
		session.setAttribute("congVanList", congVanList);
		congVanDAO.disconnect();
		return JSonUtil.toJson(congVanList);
	}
}
