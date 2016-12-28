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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.CongVanDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CTVatTu;
import model.CongVan;
import model.NguoiDung;
import model.YeuCau;
import util.DateUtil;
import util.DateUtil;

@Controller
public class BcvttController extends HttpServlet {
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(BcvttController.class);
	private static final long serialVersionUID = 1L;
    @RequestMapping("/manageBcvtt")
	public ModelAndView manageBcvtt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập báo cáo vật tư thiếu");
				return new ModelAndView(siteMap.login);
			}
//				else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
//				logger.error("Không có quyền truy cập báo cáo vật tư thiếu");
//				return new ModelAndView(siteMap.login);
//			}
			
			session.removeAttribute("congVanList");
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			session.removeAttribute("errorList");
			
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			String cdMa =authentication.getChucDanh().getCdMa();
			
			String msnv =authentication.getMsnv();
			if (cdMa.equals(adminMa) || cdMa.equals(phoPhongMa) || cdMa.equals(truongPhongMa) )
				msnv = null;
	    	CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			YeuCauDAO yeuCauDAO = new YeuCauDAO();
	    	CongVanDAO congVanDAO = new CongVanDAO();
			String ngaybd = request.getParameter("ngaybd");
			String ngaykt = request.getParameter("ngaykt");
			HashMap<String, Object> condtions = new HashMap<String, Object>();
			
			
			if (ngaybd != null && ngaybd.length() > 0) {
				condtions.put("geCvNgayNhan", DateUtil.parseDate(ngaybd));
				session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
			}
			if (ngaykt != null && ngaykt.length() > 0) {
				condtions.put("leCvNgayNhan", DateUtil.parseDate(ngaykt));
				session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
			}
			
			ArrayList<CTVatTu> ctVatTuList = yeuCauDAO.distinctCtvt(condtions, msnv);
			ArrayList<ArrayList<CongVan>> congVanList = new ArrayList<ArrayList<CongVan>>();
			ArrayList<Long> soLuongList = new ArrayList<Long>();
			for (CTVatTu ctVatTu : ctVatTuList) {
				int ctvtId = ctVatTu.getCtvtId();
				ArrayList<CongVan> congVans = yeuCauDAO.getCongVanByCtvtId(ctvtId);
				long soLuong = yeuCauDAO.sumByCtvtId(ctvtId);
				congVanList.add(congVans);
				soLuongList.add(soLuong);
			}
			ctVatTuDAO.disconnect();
			congVanDAO.disconnect();
			yeuCauDAO.disconnect();
			session.setAttribute("ctVatTuList", ctVatTuList);
			session.setAttribute("congVanList", congVanList);
			session.setAttribute("soLuongList", soLuongList);
			return new ModelAndView(siteMap.baoCaoVatTuThieu);
    	} catch (NullPointerException e) {
    		logger.error("Lỗi báo cáo vật tư thiếu: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}

}
