package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.CongVanDAO;
import dao.NhatKyDAO;
import dao.NoiSanXuatDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CTVatTu;
import model.ChatLuong;
import model.CongVan;
import model.NguoiDung;
import model.NhatKy;
import model.NoiSanXuat;
import model.YeuCau;
import util.DateUtil;
import util.JSonUtil;

@Controller
public class YcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;   
	private int pageCtvt = 1;
	private String searchTen = "";
	private String searchMa = "";
//	private NhatKy nhatKy = null;
	@RequestMapping("ycvtManage")
    public ModelAndView updateYeuCau(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    	congVan
		session = request.getSession(false);
		
		//sString[] s = request.getParameterValues("cvId");
		String s = request.getParameter("cvId");
		String cvSo = request.getParameter("cvSo");
		//if(s[0] == null)
		
		if(s == null)
			return new ModelAndView(siteMap.cvManage + "?action=manageCv");
		int cvId =  Integer.parseInt(s);
		session.setAttribute("cvId", cvId);
    	CTVatTuDAO ctvtDAO =  new CTVatTuDAO();
    	YeuCauDAO yeuCauDAO = new YeuCauDAO();
    	NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
    	ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
    	CongVanDAO congVanDAO = new CongVanDAO();
    	ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) ctvtDAO.limit((pageCtvt - 1)*10, 10);
    	ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getByCvId(cvId);
    	ArrayList<CTVatTu> ctVatTuYc = new ArrayList<CTVatTu>();
    	for (YeuCau yeuCau : yeuCauList) {
    		CTVatTu ctVatTu = ctvtDAO.getCTVatTu(yeuCau.getCtvtId());
    		ctVatTuYc.add(ctVatTu);
    	}
    	ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) nsxDAO.getAllNoiSanXuat();
    	ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
    	CongVan congVan = (CongVan)congVanDAO.getCongVan(cvId);
    	//System.out.print(congVan);
    	long sizeCtvt = ctvtDAO.size();
    	request.setAttribute("page", sizeCtvt / 10);	
    	request.setAttribute("ctVatTuList", ctVatTuList);
    	request.setAttribute("ctVatTuYc", ctVatTuYc);
    	request.setAttribute("yeuCauList", yeuCauList);
    	request.setAttribute("nsxList", nsxList);
    	request.setAttribute("chatLuongList", chatLuongList);
    	session.setAttribute("congVan", congVan);
    	//request.setAttribute("congVanList", congVanList);
    	chatLuongDAO.disconnect();
    	ctvtDAO.disconnect();
    	yeuCauDAO.disconnect();
    	nsxDAO.disconnect();
    	chatLuongDAO.disconnect();
    	congVanDAO.disconnect();
    	
    	return new ModelAndView(siteMap.ycVatTu);
    	//return new ModelAndView(siteMap.login);
    }
	@RequestMapping(value="/searchCtvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String searchCtvt(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("nsx") String nsx, @RequestParam("chatLuong") String chatLuong) {
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		ArrayList<CTVatTu> ctVatTuList = ctvtDAO.search(vtMa, vtTen, nsx, chatLuong);
		ctvtDAO.disconnect();
		return JSonUtil.toJson(ctVatTuList);
	}
	@RequestMapping(value="/preAddSoLuong", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preAddSoLuong(@RequestParam("ctvtId") String ctvtId) {
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		int ctVatTuId = Integer.parseInt(ctvtId); 
		CTVatTu ctvt = ctvtDAO.getCTVatTuById(ctVatTuId);
		session.setAttribute("ctvtId", ctVatTuId);
		ctvtDAO.disconnect();
		return JSonUtil.toJson(ctvt);
	}
	@RequestMapping(value="/addSoLuong", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addSoLuong(HttpSession session, @RequestParam("soLuong") String soLuong) {
		YeuCauDAO ycDAO = new YeuCauDAO();
		int cvId = (Integer) session.getAttribute("cvId");
		int ctvtId = (Integer) session.getAttribute("ctvtId");
		int sl = Integer.parseInt(soLuong);
		YeuCau yeuCau = ycDAO.addSoLuong(cvId, ctvtId, sl);
		ycDAO.disconnect();
		CongVanDAO congVanDAO = new CongVanDAO();
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
//		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTuById(ctVatTuId);
		CTVatTu ctVatTu = ctvtDAO.getCTVatTu(yeuCau.getCtvtId());
		CongVan congVan = congVanDAO.getCongVan(cvId);
		congVanDAO.disconnect();
    	NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		java.sql.Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
		String content = "Vât tư có mã " + ctVatTu.getVatTu().getVtMa() + ", mã nơi sản xuất " + ctVatTu.getNoiSanXuat().getNsxMa() + " và mã chất lượng "  + ctVatTu.getChatLuong().getClMa() + " được thêm tất cả " + yeuCau.getYcSoLuong();
		
		String dvt = ctVatTu.getVatTu().getDvt().getDvtTen();
		if (dvt.length() > 0)
			content += "/" + dvt +".";
		else
			content += ".";
		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), cvId + "#Thêm số lượng vật tư thiếu của công văn  có số đến " + congVan.getSoDen() + " nhận ngày "+ congVan.getCvNgayNhan(), currentDate, content);
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
//    	NhatKy nhatKy = (NhatKy) session.getAttribute("nhatKy");
//    	if (nhatKy == null) {
//    		java.sql.Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
//    		nhatKy = new NhatKy(authentication.getMsnv(), currentDate, cvId + "#Bạn đã cập nhật vật tư thiếu cho công văn có số đến " + congVan.getSoDen() + " nhận ngày " + congVan.getCvNgayNhan() + ":<br> ");
//    	}
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(yeuCau);
		objectList.add(ctVatTu);
		return JSonUtil.toJson(objectList);
	}
	
	@RequestMapping(value="/deleteYc", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteYc(@RequestParam("ycList") String ycList, HttpSession session) {
//		int id = Integer.parseInt(ycId);
		String[] ycIdList = ycList.split("\\, ");
		int cvId = (Integer) session.getAttribute("cvId");
		
		CongVanDAO congVanDAO = new CongVanDAO(); 
		CongVan congVan = congVanDAO.getCongVan(cvId);
		StringBuilder content = new StringBuilder("Vật tư được đã xóa ra danh sách thiếu: ");
		YeuCauDAO ycDAO = new YeuCauDAO();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		for (String s : ycIdList) {
			int id = Integer.parseInt(s);
			YeuCau yeuCau = ycDAO.getYeuCau(id);
			CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCtvtId());
			content.append("<br>&nbsp;&nbsp;+ Mã vật tư" + ctVatTu.getVatTu().getVtMa() + ", mã nơi sản xuất" + ctVatTu.getNoiSanXuat().getNsxMa() + ", mã chất lượng " + ctVatTu.getChatLuong().getClMa() + ".");
			ycDAO.deleteYeuCau(id);
		}
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		java.sql.Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), cvId + "#" + "#Xóa vật tư thiếu của công văn  có số đến " + congVan.getSoDen() + " nhận ngày "+ congVan.getCvNgayNhan(), currentDate, content.toString());
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
		ycDAO.disconnect();
		
//		CongVanDAO congVanDAO = new CongVanDAO();
//		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
//		CTVatTu ctvt = ctvtDAO.getCTVatTuById(ctvtId);		
//		CongVan congVan = congVanDAO.getCongVan(cvId);
//		congVanDAO.disconnect();
//    	NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
//		NhatKyDAO nhatKyDAO = new NhatKyDAO();
//		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), 0, "Bạn đã cập nhật số lượng cho vât tư có mã " + ctvt.getVatTu().getVtMa() + ", mã nơi sản xuất " + ctvt.getNoiSanXuat().getNsxMa() + " và mã chất lượng "  + ctvt.getChatLuong().getClMa() + "của công văn  " + congVan.getSoDen());
//		nhatKyDAO.addNhatKy(nhatKy);
//		nhatKyDAO.disconnect();
		
		return JSonUtil.toJson("success");
	}
	@RequestMapping(value="/preUpdateYc", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateYc(@RequestParam("yeuCau") String yeuCau) {
		int id = Integer.parseInt(yeuCau);
//		session.setAttribute("ycIdUpdate", id);
		YeuCauDAO ycDAO = new YeuCauDAO();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		YeuCau yc = ycDAO.getYeuCau(id);
		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yc.getCtvtId());
		session.setAttribute("yeuCauUpdate", yc);
		ycDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(yeuCau);
		objectList.add(ctVatTu);
		return JSonUtil.toJson(objectList); 
	}
	@RequestMapping(value="/updateSoLuong", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateSoLuong(@RequestParam("soLuong") String soLuong) {
		YeuCauDAO ycDAO = new YeuCauDAO();
		YeuCau yeuCau = (YeuCau) session.getAttribute("yeuCauUpdate");
		int sl = Integer.parseInt(soLuong);
		if (!ycDAO.checkUpdateSoLuong(yeuCau.getYcId(), sl)) {
			ycDAO.disconnect();
			return JSonUtil.toJson("fail");
		}
		YeuCauDAO ycDAO2 = new YeuCauDAO();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		int cvId = (Integer) session.getAttribute("cvId");
		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCvId());
		ctVatTuDAO.disconnect();
		CongVanDAO congVanDAO = new CongVanDAO(); 
		CongVan congVan = congVanDAO.getCongVan(cvId);
		StringBuilder content = new StringBuilder("Vật tư có mã " + ctVatTu.getVatTu().getVtMa() + ", mã nơi sản xuất " + ctVatTu.getNoiSanXuat().getNsxMa() + ", mã chất lượng " + ctVatTu.getChatLuong().getClMa() + "của công văn có số đến " + congVan.getSoDen() +  " nhận ngày "+ congVan.getCvNgayNhan() +  " được đã được cập nhật " + yeuCau.getYcSoLuong());
		String dvt = ctVatTu.getVatTu().getDvt().getDvtTen(); 
		if (dvt.length() > 0)
			content.append("/" + dvt);
		yeuCau.setYcSoLuong(sl);
		ycDAO2.updateYeuCau(yeuCau);
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		NhatKyDAO nhatKyDAO = new NhatKyDAO();
		java.sql.Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
		NhatKy nhatKy = new NhatKy(authentication.getMsnv(), cvId + "#Thay đổi số lượng vật tư thiếu của công văn có số đến " + congVan.getSoDen() + " nhận ngày " + congVan.getCvNgayNhan(), currentDate, content.toString());
		nhatKyDAO.addNhatKy(nhatKy);
		nhatKyDAO.disconnect();
		ycDAO.disconnect();
		ycDAO2.disconnect();
		return JSonUtil.toJson(yeuCau.getYcId());
	}
	@RequestMapping(value="/preCapVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preCapVatTu(@RequestParam("yeuCau") String yeuCau) {
		int id = Integer.parseInt(yeuCau);
//		session.setAttribute("ycIdUpdate", id);
		YeuCauDAO ycDAO = new YeuCauDAO();
		YeuCau yc = ycDAO.getYeuCau(id);
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yc.getCtvtId());
		
		session.setAttribute("vatTuCap", yc);
		ycDAO.disconnect();
		ctVatTuDAO.disconnect();
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(yeuCau);
		objectList.add(ctVatTu);
		return JSonUtil.toJson(objectList); 
	}
	@RequestMapping(value="/capVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String capVatTu(@RequestParam("soLuong") String soLuong) {
		YeuCauDAO ycDAO = new YeuCauDAO();
		YeuCau yeuCau = (YeuCau) session.getAttribute("vatTuCap");
		ycDAO.disconnect();
		int sl = Integer.parseInt(soLuong);
		int check = ycDAO.checkCapSoLuong(yeuCau.getYcId(), sl);
		if (check == -1) {
			ycDAO.disconnect();
			return JSonUtil.toJson("-1");
		} else if (check == -2) {
			ycDAO.disconnect();
			return JSonUtil.toJson("-2");
		}
//		yeuCau.setYcSoLuong(sl);
		YeuCauDAO ycDAO2 = new YeuCauDAO();
		ycDAO2.capVatTu(yeuCau, sl);
		ycDAO2.disconnect();
//		CTVatTu ctVatTu = yeuCau.getCtVatTu();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		CTVatTu ctVatTu = ctVatTuDAO.getCTVatTu(yeuCau.getCtvtId());
		ctVatTu.setSoLuongTon(ctVatTu.getSoLuongTon() - sl);
		ctVatTuDAO.updateCTVatTu(ctVatTu);
		ctVatTuDAO.disconnect();
		
		ArrayList<Object> objectList = new ArrayList<Object>();
		objectList.add(yeuCau);
		objectList.add(ctVatTu);
		return JSonUtil.toJson(objectList);
	}
//	@RequestMapping(value="/loadPageCtvtYc", method=RequestMethod.GET, 
//			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	 public @ResponseBody String loadPageCtvt(@RequestParam("pageNumber") String pageNumber) {
//		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
//		int page = Integer.parseInt(pageNumber);
//		ArrayList<CTVatTu> ctvtList = (ArrayList<CTVatTu>) ctvtDAO.limit((page -1 ) * 10, 10);
//			return JSonUtil.toJson(ctvtList);
//	}
	@RequestMapping(value="/loadPageCtvtYc", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCtvtYc(@RequestParam("pageNumber") String pageNumber) {
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		if(searchMa.length() != 0){
			long size = ctvtDAO.sizeOfSearchCtvtMa(searchMa); 
			ArrayList<CTVatTu> ctvtList = ctvtDAO.searchByCtvtMaLimit(searchMa, (page - 1) * 10, 10);
			objectList.add(ctvtList);
			objectList.add((size - 1) / 10);
			//return JSonUtil.toJson(objectList);
		} else if (searchTen.length() != 0) {
			long size = ctvtDAO.sizeOfSearchCtvtTen(searchTen); 
			ArrayList<CTVatTu> ctvtList = ctvtDAO.searchByCtvtTenLimit(searchTen, (page - 1) *10, 10);
			objectList.add(ctvtList);
			objectList.add((size - 1) / 10);
			//return JSonUtil.toJson(objectList);
		} else {
			long sizeCtvt = ctvtDAO.size();
			ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) ctvtDAO.limit((page - 1) * 10, 10);
			objectList.add(ctVatTuList);
			objectList.add((sizeCtvt - 1)/10);
			//return JSonUtil.toJson(objectList);
		}
		ctvtDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}						
	@RequestMapping(value="/searchCtvtYc", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String searchCtvtYc(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen) {
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		ArrayList<Object> objectList = new ArrayList<Object>();
		if(vtMa.length() != 0){
			searchMa = vtMa;
			searchTen = "";
			long size = ctvtDAO.sizeOfSearchCtvtMa(vtMa); 
			ArrayList<CTVatTu> ctvtList = ctvtDAO.searchByCtvtMaLimit(searchMa, pageCtvt - 1, 10);
			objectList.add(ctvtList);
			objectList.add((size -1) / 	10);
		} else if(vtTen.length() != 0){
			searchTen = vtTen;
			searchMa = "";
			long size = ctvtDAO.sizeOfSearchCtvtTen(vtTen); 
			ArrayList<CTVatTu> ctvtList = ctvtDAO.searchByCtvtTenLimit(searchTen, pageCtvt - 1, 10);
			objectList.add(ctvtList);
			objectList.add(size/10);
		} else {
			searchTen = "";
			searchMa = "";
			long sizeCtvt = ctvtDAO.size();
			ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) ctvtDAO.limit((pageCtvt - 1) * 10, 10);
			objectList.add(ctVatTuList);
			objectList.add(sizeCtvt/10);
			
		}
		ctvtDAO.disconnect();
		return JSonUtil.toJson(objectList);
		/*return JSonUtil.toJson(objectList);*/
	}
}
