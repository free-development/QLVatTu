package vn.com.freesoft.docmanament.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import vn.com.freesoft.docmanament.dao.CongVanDAO;
import vn.com.freesoft.docmanament.dao.DonViDAO;
import vn.com.freesoft.docmanament.dao.MucDichDAO;
import vn.com.freesoft.docmanament.entity.CongVan;
import vn.com.freesoft.docmanament.entity.DonVi;
import vn.com.freesoft.docmanament.entity.MucDich;
import vn.com.freesoft.docmanament.entity.NguoiDung;
import vn.com.freesoft.docmanament.mapping.siteMap;
import vn.com.freesoft.docmanament.util.DateUtil;
import vn.com.freesoft.docmanament.util.JSonUtil;

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
			// String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập báo công văn");
				return new ModelAndView(siteMap.login);
			}
			String cdMa = authentication.getChucDanh().getCdMa();
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String adminMa = context.getInitParameter("adminMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");

			String msnv = authentication.getMsnv();
			if (cdMa.equals(adminMa) || cdMa.equals(truongPhongMa) || cdMa.equals(phoPhongMa))
				msnv = null;
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
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnv, null, orderBy, 0,
					Integer.MAX_VALUE);
			request.setAttribute("donViList", donViList);
			request.setAttribute("mucDichList", mucDichList);
			session.setAttribute("objectList", congVanList);
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

			// CongVanDAO congVanDAO = new CongVanDAO();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			// orderBy.put("cvId", true);
			// ArrayList<CongVan> congVanList = (ArrayList<CongVan>)
			// congVanDAO.searchLimit(null, null, orderBy, 0,
			// Integer.MAX_VALUE);
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("objectList");
			session.setAttribute("objectList", congVanList);
			// congVanDAO.disconnect();
			return new ModelAndView(siteMap.xuatCongVan);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi xuất báo cáo công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/loadBcCongVan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadBccv(@RequestParam("eCvNgayNhan") String eCvNgayNhan,
			@RequestParam("sCvNgayNhan") String sCvNgayNhan, @RequestParam("eCvNgayDi") String eCvNgayDi,
			@RequestParam("sCvNgayDi") String sCvNgayDi, @RequestParam("mucDich") String mdMa,
			@RequestParam("trangThai") String ttMa, @RequestParam("donVi") String dvMa,
			@RequestParam("cvSo") String cvSo, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực tìm kiếm công văn");
				return JSonUtil.toJson("authentication error");
			}
			String cdMa = authentication.getChucDanh().getCdMa();
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String adminMa = context.getInitParameter("adminMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");

			String msnv = authentication.getMsnv();
			if (cdMa.equals(adminMa) || cdMa.equals(truongPhongMa) || cdMa.equals(phoPhongMa))
				msnv = null;
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
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnv, conditions, orderBy, 0,
					Integer.MAX_VALUE);
			session.setAttribute("objectList", congVanList);
			congVanDAO.disconnect();
			return JSonUtil.toJson(congVanList);
		} catch (NullPointerException e) {
			logger.error("Lỗi tìm kiếm báo cáo công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}

	}

	@RequestMapping(value = "/exportCongVan", method = RequestMethod.GET)
	public ModelAndView downloadExcelCl(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			List<CongVan> congVanList = (List<CongVan>) session.getAttribute("objectList");
			return new ModelAndView(siteMap.excelCongVan, "objectList", congVanList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
}
