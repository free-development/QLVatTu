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
	private String filter = "";
	private String filterValue = "";
   @RequestMapping("/manageVattu")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VatTuDAO vatTuDAO = new VatTuDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
		HttpSession session = request.getSession(false);
		String action = request.getParameter("action");
		this.filter = "";
		this.filterValue = "";
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
		if(vtMa != ""){
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtMaLimit(vtMa, 0, 10);
			this.filter = "vtMa";
			this.filterValue = vtMa;
			long size = vatTuDAO.size(filter, filterValue);
			System.out.println(size);
			ArrayList<Object> objectList = new ArrayList<Object>();
			int page = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
			objectList.add(vtList);
			objectList.add(page);
			System.out.println(page);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(objectList);
		}
		else
		{
			ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtTenLimit(vtTen, 0, 10);
			this.filter = "vtTen";
			this.filterValue = vtTen;
			long size = vatTuDAO.size(filter, filterValue);
			System.out.println(size);
			ArrayList<Object> objectList = new ArrayList<Object>();
			int page = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
			objectList.add(vtList);
			objectList.add(page);
			System.out.println(page);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(objectList);
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
		VatTuDAO vatTuDAO = new VatTuDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		long size = vatTuDAO.size(filter, filterValue);
		ArrayList<VatTu> vatTuList = (ArrayList<VatTu>) vatTuDAO.searchLimit(filter, filterValue, page * 10, 10);
		objectList.add(vatTuList);
		int p = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
		objectList.add(p);
		vatTuDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}

}
