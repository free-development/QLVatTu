package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.MucDich;
import model.TrangThai;
import model.YeuCau;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.DateUtil;
import dao.CongVanDAO;
import dao.DonViDAO;
import dao.MucDichDAO;
import dao.TrangThaiDAO;
import dao.YeuCauDAO;

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
		System.out.println(action);
	if ("baocaocv".equalsIgnoreCase(action)) {
//		ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
//		String ngaybd = request.getParameter("ngaybd");
//		String ngaykt = request.getParameter("ngaykt");
//		String donvi = request.getParameter("donvi");
//		String trangthai = request.getParameter("trangthai");
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(null, conditions, orderBy, 0, Integer.MAX_VALUE);
		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = new HashMap<Integer, ArrayList<YeuCau>>();
		for (CongVan congVan : congVanList) {
			int cvId = congVan.getCvId();
			ArrayList<YeuCau> yeuCau = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
			yeuCauHash.put(cvId, yeuCau);
		}
		
		
//		session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
//		session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
		session.setAttribute("donViList", donViList);
		session.setAttribute("mucDichList", mucDichList);
//		session.setAttribute("trangThaiList", trangThaiList);
		session.setAttribute("congVanList", congVanList);
		session.setAttribute("yeuCau", yeuCauHash);
		
		yeuCauDAO.disconnect();
		congVanDAO.disconnect();
		donViDAO.disconnect();
		mucDichDAO.disconnect();
		return new ModelAndView(siteMap.bCCongVan);
		
	}
	return new ModelAndView("login");
	}
}
