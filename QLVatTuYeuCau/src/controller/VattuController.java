package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import model.CTVatTu;
import model.ChatLuong;
import model.ChucDanh;
import model.NoiSanXuat;
import model.VaiTro;
import model.VatTu;
import model.DonViTinh;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.ChucDanhDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;
import dao.CTVatTuDAO;
import dao.DonViTinhDAO;

@Controller
public class VattuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;

   @RequestMapping("/manageVattu")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VatTuDAO vatTuDAO = new VatTuDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
		String action = request.getParameter("action");
//		if("addVatTu".equalsIgnoreCase(action)) {
//			
//			String vtMa = request.getParameter("vtMa");
//			String vtTen = request.getParameter("vtTen");
//			String dvt = request.getParameter("dvt");
//
//			if(new VatTuDAO().getVatTu(vtMa) != null){
//				request.setAttribute("error", "Vật tư đã tồn tại");
//				System.out.println("Vat tu da ton tai");
//				return new ModelAndView("danh-muc-vat-tu");
//			}
//			else{
//				vatTuDAO.addVatTu(new VatTu(vtMa,vtTen,dvt,0));
//				ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) new VatTuDAO().getAllVatTu();
//				return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
//			}
//			
//		}
//		if("deleteVatTu".equalsIgnoreCase(action)) {
//			String[] vtMaList = request.getParameterValues("vtMa");
//			for(String s : vtMaList) {
//				
//					vatTuDAO.deleteVatTu(s);
//			}
//			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.getAllVatTu();
//			return new ModelAndView("danh-muc-vat-tu", "vatTuList", vatTuList);
//
//		}
		if("manageVattu".equalsIgnoreCase(action)) {
			long size = vatTuDAO.size();
			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			ArrayList<NoiSanXuat> noiSanXuatList =  (ArrayList<NoiSanXuat>) noiSanXuatDAO.getAllNoiSanXuat();
			ArrayList<ChatLuong> chatLuongList =  (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
			ArrayList<DonViTinh> donViTinhList =  (ArrayList<DonViTinh>) donViTinhDAO.getAllDonViTinh();
			request.setAttribute("noiSanXuatList", noiSanXuatList);
			request.setAttribute("chatLuongList", chatLuongList);
			request.setAttribute("donViTinhList", donViTinhList);
			request.setAttribute("vatTuList", vatTuList);
			vatTuDAO.disconnect();
			noiSanXuatDAO.disconnect();
			chatLuongDAO.disconnect();
			donViTinhDAO.disconnect();
			return new ModelAndView("danh-muc-vat-tu");
		}
		vatTuDAO.disconnect();
		noiSanXuatDAO.disconnect();
		chatLuongDAO.disconnect();
		donViTinhDAO.disconnect();
		return new ModelAndView("login");
	}
   @RequestMapping(value="/preEditVattu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditVattu(@RequestParam("vtMa") String vtMa) {
			//System.out.println("****" + vtMa + "****");
			VatTuDAO vatTuDAO = new VatTuDAO();
			VatTu vt = vatTuDAO.getVatTu(vtMa);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
		}
	@RequestMapping(value="/addVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("dvt") String dvt) {
		String result = "success";
		VatTuDAO vatTuDAO = new VatTuDAO();
		VatTu vt = vatTuDAO.getVatTu(vtMa);
		DonViTinhDAO dvtDAO = new DonViTinhDAO();
		int idDvt = Integer.parseInt(dvt);
		DonViTinh dVT = dvtDAO.getDonViTinh(idDvt);
		if(vt == null) 
		{
			vatTuDAO.addVatTu(new VatTu(vtMa, vtTen,dVT,0));
			VatTuDAO vatTuDAO2 = new VatTuDAO();
			VatTu vatTu = vatTuDAO2.getVatTu(vtMa);
			result = JSonUtil.toJson(vatTu);
			return result;
		}
		else if(vt !=null && vt.getDaXoa()== 1){
			vt.setVtMa(vtMa);
			vt.setVtTen(vtTen);
			vt.setDvt(dVT);
			vt.setDaXoa(0);
			vatTuDAO.updateVatTu(vt);
			result = JSonUtil.toJson(vt);
			vatTuDAO.disconnect();
			return result;
		}
		else
		{
			vatTuDAO.disconnect();
			result = "fail";
			return JSonUtil.toJson(result);
		}
		
//			return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/timKiemVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String timKiemVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen) {
		VatTuDAO vatTuDAO = new VatTuDAO();
		//System.out.println("Ma goi qua " + vtMa);
		//System.out.println("Ten goi qua " + vtTen);
		if(vtMa != ""){
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtMa(vtMa);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vtList);
		}
		else
		{
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtTen(vtTen);
			//System.out.println("Ten: "+vtTen);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vtList);
		}
		
	}
	@RequestMapping(value="/updateVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateVattu(@RequestParam("vtMaUpdate") String vtMaUpdate, @RequestParam("vtTenUpdate") String vtTenUpdate, @RequestParam("dvtUpdate") String dvtUpdate) {
		int id = Integer.parseInt(dvtUpdate);
		DonViTinhDAO dvtDAO = new DonViTinhDAO();
		DonViTinh dvt = dvtDAO.getDonViTinh(id);
		VatTu vt = new VatTu(vtMaUpdate, vtTenUpdate, dvt,0);
		VatTuDAO vatTuDAO = new VatTuDAO();
		vatTuDAO.updateVatTu(vt);
		vatTuDAO.disconnect();
		return JSonUtil.toJson(vt);
	}
	@RequestMapping(value="/deleteVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVattu(@RequestParam("vtList") String vtList) {
		String[] str = vtList.split("\\, ");
		
		VatTuDAO vatTuDAO = new VatTuDAO();
		for(String vtMa : str) {
			vatTuDAO.deleteVatTu(vtMa);
		}
		vatTuDAO.disconnect();
		return JSonUtil.toJson(vtList);
	}
	
	@RequestMapping(value="/loadPageVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVatTu(@RequestParam("pageNumber") String pageNumber) {
//		String result = "";
//		System.out.println("MA: " + pageNumber);
		VatTuDAO vatTuDAO = new VatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		long sizevt = vatTuDAO.size();
		ArrayList<VatTu> vatTuList = (ArrayList<VatTu>) vatTuDAO.limit(page * 10, 10);
		//JOptionPane.showMessageDialog(null, vatTuList.size());
		objectList.add(vatTuList);
		objectList.add((sizevt - 1)/10);
		vatTuDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}

}
