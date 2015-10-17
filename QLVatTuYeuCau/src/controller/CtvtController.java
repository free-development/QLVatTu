package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import map.siteMap;
import model.CTVatTu;
import model.ChatLuong;
import model.MucDich;
import model.NoiSanXuat;
import model.VatTu;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;
import dao.CTVatTuDAO;

@Controller
public class CtvtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;  
	int page =1;
	private int pageCtvt = 1;
	private String searchTen = "";
	private String searchMa = "";
   @RequestMapping("/manageCtvt")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VatTuDAO vatTuDAO = new VatTuDAO();
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		HttpSession session = request.getSession(false);
		String action = request.getParameter("action");
//		if("addVatTu".equalsIgnoreCase(action)) {
//			
//			String vtMa = request.getParameter("vtMa");
//			String vtTen = request.getParameter("vtTen");
//			String dvt = request.getParameter("dvt");
//			String nsxMa = request.getParameter("nsxMa");
//			String clMa = request.getParameter("clMa");
//			int dinhMuc = Integer.parseInt(request.getParameter("dinhMuc"));
//			int soLuongTon = Integer.parseInt(request.getParameter("soLuongTon"));
//
//			if(new CTVatTuDAO().getCTVatTu(vtMa, nsxMa, clMa) != null){
//
//				request.setAttribute("error", "Váº­t tÆ° Ä‘Ã£ tá»“n táº¡i");
//				System.out.println("Vat tu da ton tai");
//				return new ModelAndView("danh-muc-vat-tu");
//			}
//			else{
//				vatTuDAO.addVatTu(new VatTu(vtMa,vtTen,dvt));
//				ctVatTuDAO.addCTVatTu(new CTVatTu(new VatTu(vtMa,vtTen,dvt), new NoiSanXuat(nsxMa), new ChatLuong(clMa), dinhMuc, soLuongTon));
//				ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) new VatTuDAO().getAllVatTu();
//				ArrayList<CTVatTu> ctVatTuList =  (ArrayList<CTVatTu>) new CTVatTuDAO().getAllCTVatTu();
//				return new ModelAndView("danh-muc-vat-tu", "ctVatTuList", ctVatTuList);
//			}
//			
//		}
//		if("deleteVatTu".equalsIgnoreCase(action)) {
//			String[] vtIdList = request.getParameterValues("vtMa");
//			for(String s : vtIdList) {
//				
//					ctVatTuDAO.deleteCTVatTu(Integer.parseInt(s));
//			}
//			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.getAllVatTu();
//			vatTuDAO.disconnect();
//			ctVatTuDAO.disconnect();
//			return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
//		}
			long size = ctVatTuDAO.size();
			ArrayList<CTVatTu> ctVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.limitTonKho(page - 1, 10);
			request.setAttribute("size", size);
			session.setAttribute("ctVatTuList", ctVatTuList);
			ArrayList<CTVatTu> allCTVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.getAllCTVatTu();
			session.setAttribute("allCTVatTuList", allCTVatTuList);
			ArrayList<CTVatTu> tonKhoList =  (ArrayList<CTVatTu>) ctVatTuDAO.TonKho();
			session.setAttribute("tonKhoList", tonKhoList);
			ctVatTuDAO.disconnect();
			vatTuDAO.disconnect();
			return new ModelAndView(siteMap.ctVatu);
		
	}
   
   @RequestMapping(value="/showCTVatTu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String showCTVatTu(@RequestParam("vtMa")  String vtMa) {
			
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			VatTuDAO vatTuDAO = new VatTuDAO();
			ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) ctVatTuDAO.getCTVTu(vtMa);
			if(listCTVatTu.size() == 0) {
				VatTu vatTu = vatTuDAO.getVatTu(vtMa);
				vatTuDAO.disconnect();
				return JSonUtil.toJson(vatTu);
			}
			vatTuDAO.disconnect();
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(listCTVatTu);
		}
   @RequestMapping(value="/preEditCTVattu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditCTVattu(@RequestParam("ctvtId") String ctvtId) {
			//System.out.println("****" + ctvtId + "****");
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			CTVatTu vt = ctVatTuDAO.getCTVatTuById(Integer.parseInt(ctvtId));
			//System.out.println("****" + vt.getVatTu().getVtMa() + "****");
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
		}
   
	@RequestMapping(value="/addCTVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addCTVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("noiSanXuat") String noiSanXuat, @RequestParam("chatLuong") String chatLuong, 
			 @RequestParam("dvt") String dvt, @RequestParam("dinhMuc") String dinhMuc, @RequestParam("soLuongTon") String soLuongTon) {
		String result = "success";
		//System.out.println("MA: " + vtMa);
		//System.out.println("NSX: " + noiSanXuat);
		//System.out.println("CL: " + chatLuong);
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		CTVatTu ctvt = ctVatTuDAO.getCTVatTu(vtMa, noiSanXuat, chatLuong);
		if( ctvt == null)
		{	
			VatTuDAO vtDAO = new VatTuDAO();
			VatTu vt = vtDAO.getVatTu(vtMa);
			NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
			NoiSanXuat nsx = nsxDAO.getNoiSanXuat(noiSanXuat);
			ChatLuongDAO clDAO = new ChatLuongDAO();
			ChatLuong cl = clDAO.getChatLuong(chatLuong);
			ctvt = new CTVatTu( vt, nsx, cl, Integer.parseInt(dinhMuc), Integer.parseInt(soLuongTon),0);
			ctVatTuDAO.addCTVatTu(ctvt);
			//System.out.println("success");

			//int id = ctVatTuDAO.getLastInsert()-1;
			//CTVatTu ctVatTu = ctVatTuDAO.getCTVatTuById(id);
			//ctVatTuDAO.disconnect();
			return JSonUtil.toJson(ctvt);
		
		}
		else if(ctvt.getDaXoa() == 1)
		{
			ctvt.setVatTu(new VatTu(vtMa));
			ctvt.setNoiSanXuat(new NoiSanXuat(noiSanXuat));
			ctvt.setChatLuong(new ChatLuong(chatLuong));
			ctvt.setDinhMuc(Integer.parseInt(dinhMuc));
			ctvt.setSoLuongTon(Integer.parseInt(soLuongTon));
			ctvt.setDaXoa(0);
			ctVatTuDAO.updateCTVatTu(ctvt);
			return JSonUtil.toJson(ctvt);
		}
		else
		{
			//System.out.println("fail");	
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson("");
		}
		
	}
	@RequestMapping(value="/updateCTVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateCTVattu(@RequestParam("vtMaUpdate") String vtMaUpdate,  @RequestParam("nsxUpdate") String nsxUpdate, @RequestParam("clUpdate") String clUpdate, @RequestParam("dinhMucUpdate") String dinhMucUpdate, @RequestParam("soLuongTonUpdate") String soLuongTonUpdate) {
		//System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMaUpdate, nsxUpdate, clUpdate);
		//System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		// CTVatTu ctvt = new CTVatTu(new VatTu(vtMaUpdate) , new
		// NoiSanXuat(nsxUpdate), new ChatLuong(clUpdate),
		// Integer.parseInt(dinhMucUpdate), Integer.parseInt(soLuongTonUpdate));
//		ctvt.setDinhMuc(Integer.parseInt(dinhMucUpdate));
//		ctvt.setNoiSanXuat(new NoiSanXuat(nsxUpdate));
//		ctvt.setChatLuong(new ChatLuong(clUpdate));
//		ctvt.setSoLuongTon(Integer.parseInt(soLuongTonUpdate));
//		System.out.println(ctvt.getCtvtId() + ctvt.getSoLuongTon());
//		ctvtDAO.updateCTVatTu(ctvt);
		// new CTVatTuDAO().updateCTVatTu(ctvt);
		if (ctvt == null)
			System.out.println("Result  = null");
		else 
			System.out.println(ctvt.getCtvtId());
		ctvtDAO.disconnect();
		return JSonUtil.toJson(ctvt);
	}

	@RequestMapping(value = "/deleteCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVattu(@RequestParam("ctvtList") String ctvtList) {
		String[] str = ctvtList.split("\\, ");
		//System.out.println(str[0]);
		CTVatTuDAO ctvtDAO =  new CTVatTuDAO();
		for(String ctvtId : str) {
			ctvtDAO.deleteCTVatTu(Integer.parseInt(ctvtId));
		}
		ctvtDAO.disconnect();
		return JSonUtil.toJson(ctvtList);
	}
	
	@RequestMapping(value="/loadPageCTVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVt(@RequestParam("pageNumber") String pageNumber) {
		CTVatTuDAO ctvtDAO = new CTVatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		long sizevt = ctvtDAO.sizeTon();
		ArrayList<CTVatTu> ctvatTuList = (ArrayList<CTVatTu>) ctvtDAO.limitTonKho(page* 10, 10);
		objectList.add(ctvatTuList);
		objectList.add((sizevt - 1)/10);
		ctvtDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping("/danhMucVatTu")
	protected ModelAndView danhMucVatTu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession(false);
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> allCTVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.getAllCTVatTu();
			session.setAttribute("allCTVatTuList", allCTVatTuList);
			ctVatTuDAO.disconnect();
			return new ModelAndView(siteMap.xuatCTVatTu);
	}
}
