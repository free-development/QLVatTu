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
			long size = ctVatTuDAO.sizeTon();
			ArrayList<CTVatTu> ctVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.limitTonKho(page - 1, 10);
			session.setAttribute("size", size);
			session.setAttribute("ctVatTuList", ctVatTuList);
			ctVatTuDAO.disconnect();
			vatTuDAO.disconnect();
			return new ModelAndView(siteMap.vatTuTonKho);
		
	}
   @RequestMapping("/exportCTVatTu")
	protected ModelAndView exportCTVatTu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		HttpSession session = request.getSession(false);
		ArrayList<CTVatTu> allCTVatTuList =  (ArrayList<CTVatTu>) ctVatTuDAO.getAllCTVatTu();
		session.setAttribute("allCTVatTuList", allCTVatTuList);
		ctVatTuDAO.disconnect();
		return new ModelAndView(siteMap.xuatCTVatTu);
	}
   @RequestMapping("/manageXuatTonKho")
	protected ModelAndView manageXuatTonKho(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		HttpSession session = request.getSession(false);
		ArrayList<CTVatTu> tonKhoList =  (ArrayList<CTVatTu>) ctVatTuDAO.tonKho();
		session.setAttribute("tonKhoList", tonKhoList);
		ctVatTuDAO.disconnect();
		return new ModelAndView(siteMap.xuatTonKho);
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
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			CTVatTu vt = ctVatTuDAO.getCTVatTuById(Integer.parseInt(ctvtId));
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
		}
   
	@RequestMapping(value="/addCTVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addCTVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("noiSanXuat") String noiSanXuat, @RequestParam("chatLuong") String chatLuong, 
			 @RequestParam("dvt") String dvt, @RequestParam("dinhMuc") String dinhMuc, @RequestParam("soLuongTon") String soLuongTon) {
		String result = "success";
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
			ctVatTuDAO.disconnect();
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
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(ctvt);
		}
		else
		{
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson("");
		}
		
	}
	@RequestMapping(value="/updateCTVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateCTVattu(@RequestParam("vtMaUpdate") String vtMaUpdate,  @RequestParam("nsxUpdate") String nsxUpdate, @RequestParam("clUpdate") String clUpdate, @RequestParam("dinhMucUpdate") String dinhMucUpdate, @RequestParam("soLuongTonUpdate") String soLuongTonUpdate) {
		//System.out.println(vtMaUpdate + "&" + nsxUpdate + "&" + clUpdate + "&" + dinhMucUpdate + "&" + soLuongTonUpdate);
		try {
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMaUpdate, nsxUpdate, clUpdate);
			
			if (ctvt != null) {
				ctvt.setDinhMuc(Integer.parseInt(dinhMucUpdate));
				ctvt.setSoLuongTon(Integer.parseInt(soLuongTonUpdate));
				ctvtDAO.updateCTVatTu(ctvt);
			} else 
				return "fail";
			ctvtDAO.disconnect();
			return JSonUtil.toJson(ctvt);
		} catch (NumberFormatException e) {
			return "fail";
		}
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
