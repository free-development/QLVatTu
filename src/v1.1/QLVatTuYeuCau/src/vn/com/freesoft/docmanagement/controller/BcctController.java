package vn.com.freesoft.docmanagement.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.freesoft.docmanagement.dao.CTVatTuDAO;
import vn.com.freesoft.docmanagement.dao.CongVanDAO;
import vn.com.freesoft.docmanagement.dao.DonViDAO;
import vn.com.freesoft.docmanagement.dao.TrangThaiDAO;
import vn.com.freesoft.docmanagement.dao.YeuCauDAO;
import vn.com.freesoft.docmanagement.entity.CTVatTu;
import vn.com.freesoft.docmanagement.entity.CongVan;
import vn.com.freesoft.docmanagement.entity.DonVi;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.entity.TrangThai;
import vn.com.freesoft.docmanagement.entity.YeuCau;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.DateUtil;

@Controller
public class BcctController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BcctController.class);

	@RequestMapping("/manageBcct")
	protected ModelAndView manageBcbdn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập báo cáo chi tiết");
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

			String msnv = authentication.getMsnv();
			String cdMa = authentication.getChucDanh().getCdMa();
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (cdMa.equals(adminMa) || cdMa.equals(phoPhongMa) || cdMa.equals(truongPhongMa))
				msnv = null;
			if ("manageBcbdn".equalsIgnoreCase(action)) {
				ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) trangThaiDAO.getAllTrangThai();
				ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
				ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.getAllCongVan(msnv);
				HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = new HashMap<Integer, ArrayList<YeuCau>>();
				HashMap<Integer, ArrayList<CTVatTu>> ctVatTuHash = new HashMap<Integer, ArrayList<CTVatTu>>();
				CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
					ArrayList<CTVatTu> ctVatTuList = new ArrayList<CTVatTu>();
					for (YeuCau yeuCau : yeuCauList) {
						CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCtvtId());
						ctVatTuList.add(ctVatTu);
					}
					ctVatTuHash.put(cvId, ctVatTuList);
					yeuCauHash.put(cvId, yeuCauList);
				}

				session.setAttribute("donViList", donViList);
				session.setAttribute("trangThaiList", trangThaiList);
				session.setAttribute("congVanList", congVanList);
				session.setAttribute("yeuCauHash", yeuCauHash);
				session.setAttribute("ctVatTuHash", ctVatTuHash);
				yeuCauDAO.disconnect();
				congVanDAO.disconnect();
				donViDAO.disconnect();
				ctVatTuDAO.disconnect();

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
				HashMap<Integer, ArrayList<CTVatTu>> ctVatTuHash = new HashMap<Integer, ArrayList<CTVatTu>>();
				CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
					ArrayList<CTVatTu> ctVatTuList = new ArrayList<CTVatTu>();
					for (YeuCau yeuCau : yeuCauList) {
						CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCtvtId());
						ctVatTuList.add(ctVatTu);
					}
					ctVatTuHash.put(cvId, ctVatTuList);
					yeuCauHash.put(cvId, yeuCauList);
				}
				session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
				session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
				session.setAttribute("donViList", donViList);
				session.setAttribute("trangThaiList", trangThaiList);
				session.setAttribute("congVanList", congVanList);
				session.setAttribute("yeuCauHash", yeuCauHash);
				session.setAttribute("ctVatTuHash", ctVatTuHash);
				yeuCauDAO.disconnect();
				congVanDAO.disconnect();
				donViDAO.disconnect();
				return new ModelAndView(siteMap.baoCaoChiTiet);
			}
			return new ModelAndView("login");
		} catch (NullPointerException e) {
			logger.error("Lỗi khi báo cáo chi tiết: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}
}
