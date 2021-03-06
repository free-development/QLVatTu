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

import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.NguoiDung;
import model.TrangThai;
import model.YeuCau;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.DateUtil;
import dao.CongVanDAO;
import dao.DonViDAO;
import dao.TrangThaiDAO;
import dao.YeuCauDAO;

@Controller
public class BcbdnController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BcbdnController.class);
	@RequestMapping("/manageBcbdn")
	protected ModelAndView manageBcbdn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập báo cáo bảng đề nghị");
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
			String action = request.getParameter("action");
			
			YeuCauDAO yeuCauDAO = new YeuCauDAO();
			TrangThaiDAO trangThaiDAO = new TrangThaiDAO();
			DonViDAO donViDAO = new DonViDAO();
			CongVanDAO congVanDAO = new CongVanDAO();
			if ("manageBcbdn".equalsIgnoreCase(action)) {
				
				
				ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
				ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
				ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getAllCongVan();
				HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = new HashMap<Integer, ArrayList<YeuCau>>();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<YeuCau> yeuCau = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
					yeuCauHash.put(cvId, yeuCau);
				}
				
				session.setAttribute("donViList", donViList);
				session.setAttribute("trangThaiList", trangThaiList);
				session.setAttribute("congVanList", congVanList);
				session.setAttribute("yeuCau", yeuCauHash);
				yeuCauDAO.disconnect();
				congVanDAO.disconnect();
				donViDAO.disconnect();
				
				return new ModelAndView(siteMap.baoCaoChiTiet);
			}
			if ("baocaobdn".equalsIgnoreCase(action)) {
				ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
				ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
				String ngaybd = request.getParameter("ngaybd");
				String ngaykt = request.getParameter("ngaykt");
				String donvi = request.getParameter("donvi");
				String trangthai = request.getParameter("trangthai");
				ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getTrangThai(ngaybd, ngaykt, donvi,
						trangthai);
				HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = new HashMap<Integer, ArrayList<YeuCau>>();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<YeuCau> yeuCau = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
					yeuCauHash.put(cvId, yeuCau);
				}
				
				
				session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
				session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
				session.setAttribute("donViList", donViList);
				session.setAttribute("trangThaiList", trangThaiList);
				session.setAttribute("congVanList", congVanList);
				session.setAttribute("yeuCau", yeuCauHash);
				yeuCauDAO.disconnect();
				congVanDAO.disconnect();
				donViDAO.disconnect();
				return new ModelAndView(siteMap.baoCaoChiTiet);
			}
			return new ModelAndView("login");
		} catch (NullPointerException e) {
			logger.error("Lỗi khi cập báo cáo bảng đề nghị");
			return new ModelAndView(siteMap.login);
		}
	}
}
