package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.CongVanDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CTVatTu;
import model.CongVan;
import model.YeuCau;
import util.DateUtil;
import util.DateUtil;

@Controller
public class BcvttController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @RequestMapping("/manageBcvtt")
	public ModelAndView manageBcvtt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
		if (session.getAttribute("nguoiDung") == null)
			return new ModelAndView(siteMap.login);
		session.removeAttribute("congVanList");
		session.removeAttribute("ctVatTuList");
		session.removeAttribute("soLuongList");
		session.removeAttribute("yeuCauHash");
		session.removeAttribute("ctVatTuHash");
		session.removeAttribute("trangThaiList");
		session.removeAttribute("donViList");
    	
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
		
		ArrayList<CTVatTu> ctVatTuList = yeuCauDAO.distinctCtvt(condtions);
		ArrayList<ArrayList<CongVan>> congVanList = new ArrayList<ArrayList<CongVan>>();
		ArrayList<Long> soLuongList = new ArrayList<Long>();
		for (CTVatTu ctVatTu : ctVatTuList) {
			int ctvtId = ctVatTu.getCtvtId();
			ArrayList<CongVan> congVans = yeuCauDAO.getCongVanByCtvtId(ctvtId);
			long soLuong = yeuCauDAO.sumByCtvtId(ctvtId);
			congVanList.add(congVans);
			soLuongList.add(soLuong);
		}
		System.out.println("ngay bd = " + ngaybd);
		ctVatTuDAO.disconnect();
		congVanDAO.disconnect();
		yeuCauDAO.disconnect();

		
		session.setAttribute("ctVatTuList", ctVatTuList);
		session.setAttribute("congVanList", congVanList);
		session.setAttribute("soLuongList", soLuongList);
		
		return new ModelAndView(siteMap.baoCaoVatTuThieu);
    	
	}

}
