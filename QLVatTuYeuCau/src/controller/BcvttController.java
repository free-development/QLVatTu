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
    	
    	String action = request.getParameter("action");
    	CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		YeuCauDAO yeuCauDAO = new YeuCauDAO();
    	CongVanDAO congVanDAO = new CongVanDAO();
		String ngaybd = request.getParameter("ngaybd");
		String ngaykt = request.getParameter("ngaykt");
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>)congVanDAO.getAllCongVan();
		
		HashMap<Integer, Integer> yeuCauHash = new HashMap<Integer, Integer>();
		
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getVTThieu();
//    		HashMap<Integer, CTVatTu> ctvtHash = ctVatTuDAO.getHashMap();
		
		HashMap<Integer, ArrayList<Integer>> soDenHash = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> cvIdHash = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, CTVatTu> ctVatTuHash = new HashMap<Integer, CTVatTu>();
		
		for(YeuCau yeuCau: yeuCauList){
			int ctVtId = yeuCau.getCtvtId();
			int cvId = yeuCau.getCvId();
			Integer slCu = yeuCauHash.get(ctVtId);
			Integer soluong = yeuCau.getYcSoLuong();
			if (slCu != null){
				soluong += slCu;
			}

			
			ArrayList<Integer> cvList = new ArrayList<Integer>();
			ArrayList<Integer> cvListCu = cvIdHash.get(ctVtId);
			
			ArrayList<Integer> soDenList = new ArrayList<Integer>();
			ArrayList<Integer> soDenListCu = soDenHash.get(ctVtId);
			int soDen = congVanDAO.getSoDen(cvId);
			//String ngayden = congVanDAO.g
			if (cvListCu != null) {
				soDenList = soDenListCu;
				cvList = cvListCu;
			}
			
			CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(ctVtId);
			soDenList.add(soDen);
			soDenHash.put(ctVtId, soDenList);
			//
			cvList.add(cvId);
			cvIdHash.put(ctVtId, cvList);
			//
			ctVatTuHash.put(ctVtId, ctVatTu);
			
			//cvIdHash.put(ctVtId, cvList);
			yeuCauHash.put(ctVtId,soluong);
		}
		if (ngaybd != null)
			session.setAttribute("ngaybd", DateUtil.parseDate(ngaybd));
		if (ngaykt != null)
			session.setAttribute("ngaykt", DateUtil.parseDate(ngaykt));
		session.setAttribute("ctVatTuHash", ctVatTuHash);
		session.setAttribute("action", action);
		session.setAttribute("cvIdHash", cvIdHash);
		session.setAttribute("soDenHash", soDenHash);
		session.setAttribute("congVanList", congVanList);
		session.setAttribute("yeuCauHash", yeuCauHash);
		congVanDAO.disconnect();
		yeuCauDAO.disconnect();
		return new ModelAndView(siteMap.baoCaoVatTuThieu);
    	
	}

}
