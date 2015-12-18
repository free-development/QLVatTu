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

import model.VaiTro;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.DonViTinhDAO;
import dao.VaiTroDAO;
import map.siteMap;


@Controller
public class VtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int page = 1;
	private String vtOld = "";
	@RequestMapping("/manageVt")
	public ModelAndView manageVt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		session.removeAttribute("errorList");
		VaiTroDAO vaiTroDAO = new VaiTroDAO();
		String action = request.getParameter("action");
		if("AddVaiTro".equalsIgnoreCase(action)) {
			int vtId = Integer.parseInt(request.getParameter("vtId"));
			String vtTen = request.getParameter("vtTen");
			vaiTroDAO.addOrUpdateVaiTro(new VaiTro(vtTen,0));
			
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			vaiTroDAO.disconnect();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		
		if("deleteVaiTro".equalsIgnoreCase(action)) {
			String[] vtIdList = request.getParameterValues("vtId");
			for(String s : vtIdList) {
				int vtId = Integer.parseInt(s); 	
				vaiTroDAO.deleteVaiTro(vtId);
			}
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			vaiTroDAO.disconnect();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		if("manageVt".equalsIgnoreCase(action)) {
			long size = vaiTroDAO.size();
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.limit(page - 1, 10);
			//System.out.println(size);
			request.setAttribute("size", size);
			vaiTroDAO.disconnect();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		}
		vaiTroDAO.disconnect();
		return new ModelAndView("login");
	}
	@RequestMapping(value="/preEditvt", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditvt(@RequestParam("vtId") String vt) {
			//System.out.println("****" + vt + "****");
			//JOptionPane.showMessageDialog(null, vt);
			vtOld = vt;
			return JSonUtil.toJson(vt);
		}
	@RequestMapping(value="/addvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addvt(@RequestParam("vtTen") String vtTen) {
		String result = "success";
		//System.out.println("Ten: "+ vtTen);
		VaiTroDAO vtDAO = new VaiTroDAO();
		VaiTro vt = vtDAO.getVaiTroByTen(vtTen);
		if(vt == null)
		{
			vtDAO.addVaiTro(new VaiTro(vtTen,0));
			//System.out.println("success");
			result = "success";
		}
		else if(vt.getDaXoa() == 1){
			vt.setVtTen(vtTen);
			vt.setDaXoa(0);
			vtDAO.updateVaiTro(vt);
			result = "success";
		}
		else 
		{
			//System.out.println("fail");
			result = "fail";
		}
		vtDAO.disconnect();
			return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/updatevt", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updatevt(@RequestParam("vtTenUpdate") String vtTenUpdate) {
		VaiTroDAO vtDAO = new VaiTroDAO();
		VaiTro vt = vtDAO.getVaiTroByTen(vtOld);
		vt.setVtTen(vtTenUpdate);
		vt.setDaXoa(0);
		vtDAO.updateVaiTro(vt);
		vtDAO.disconnect();
		return JSonUtil.toJson(vt);
	}
	@RequestMapping(value="/deletevt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deletevt(@RequestParam("vtList") String vtList) {
		String[] str = vtList.split("\\, ");
		
		VaiTroDAO vtDAO =  new VaiTroDAO();
		for(String vtTen : str) {
			vtDAO.deleteVaiTroTen(vtTen);
		}
		vtDAO.disconnect();
		return JSonUtil.toJson(vtList);
	}
	@RequestMapping(value="/loadPagevt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPagevt(@RequestParam("pageNumber") String pageNumber) {
		//System.out.println("MA: " + pageNumber);
		VaiTroDAO vtDAO = new VaiTroDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<VaiTro> vtList = (ArrayList<VaiTro>) vtDAO.limit((page -1 ) * 10, 10);
		vtDAO.disconnect();
			return JSonUtil.toJson(vtList);
	}
	
}