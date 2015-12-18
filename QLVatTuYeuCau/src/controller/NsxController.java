package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CTVatTu;
import model.DonVi;
import model.NoiSanXuat;
import util.JSonUtil;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import map.siteMap;


@Controller
public class NsxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;  
	int page = 1;
	@RequestMapping("/manageNsx")
	public ModelAndView manageNsx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
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
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			request.getCharacterEncoding();
	    	response.getCharacterEncoding();
	    	request.setCharacterEncoding("UTF-8");
	    	response.setCharacterEncoding("UTF-8");  
			long size = noiSanXuatDAO.size();
			ArrayList<NoiSanXuat> noiSanXuatList =  (ArrayList<NoiSanXuat>) noiSanXuatDAO.limit(page - 1, 10);
			
			request.setAttribute("size", size);
			noiSanXuatDAO.disconnect();
			return new ModelAndView("danh-muc-noi-san-xuat", "noiSanXuatList", noiSanXuatList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping("/exportNsx")
	public ModelAndView exportNsx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		ArrayList<NoiSanXuat> allNoiSanXuatList =  (ArrayList<NoiSanXuat>) noiSanXuatDAO.getAllNoiSanXuat();
		HttpSession session = request.getSession(false);
		session.setAttribute("objectList", allNoiSanXuatList);
		noiSanXuatDAO.disconnect();
		return new ModelAndView(siteMap.xuatNsx);
	}
	@RequestMapping(value="/preEditNsx", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditNsx(@RequestParam("nsxMa") String nsxMa) {
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		NoiSanXuat nsx = noiSanXuatDAO.getNoiSanXuat(nsxMa);
		noiSanXuatDAO.disconnect();
		return JSonUtil.toJson(nsx);
	}
	
	@RequestMapping(value="/addNsx", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addNsx(@RequestParam("nsxMa") String nsxMa, @RequestParam("nsxTen") String nsxTen) {
		String result = "success";
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		NoiSanXuat nsx = noiSanXuatDAO.getNoiSanXuat(nsxMa);
		if(nsx == null) {
			noiSanXuatDAO.addNoiSanXuat(new NoiSanXuat(nsxMa, nsxTen,0));
			result = "success";	
		} else if(nsx !=null && nsx.getDaXoa()== 1){
			nsx.setNsxMa(nsxMa);
			nsx.setNsxTen(nsxTen);
			nsx.setDaXoa(0);
			noiSanXuatDAO.updateNoiSanXuat(nsx);
		} else {
			result = "fail";
		}
		noiSanXuatDAO.disconnect();
			return JSonUtil.toJson(result);
	}
	
	@RequestMapping(value="/updateNsx", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateNsx(@RequestParam("nsxMaUpdate") String nsxMaUpdate, @RequestParam("nsxTenUpdate") String nsxTenUpdate) {
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		NoiSanXuat nsx = new NoiSanXuat(nsxMaUpdate, nsxTenUpdate,0);
		noiSanXuatDAO.updateNoiSanXuat(nsx);
		noiSanXuatDAO.disconnect();
		return JSonUtil.toJson(nsx);
	}
	@RequestMapping(value="/deleteNsx", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteNsx(@RequestParam("nsxList") String nsxList) {
		String[] str = nsxList.split("\\, ");
		
		NoiSanXuatDAO noiSanXuatDAO =  new NoiSanXuatDAO();
		for(String nsxMa : str) {
			noiSanXuatDAO.deleteNoiSanXuat(nsxMa);
		}
		noiSanXuatDAO.disconnect();
		return JSonUtil.toJson(nsxList);
	}
	@RequestMapping(value="/loadPageNsx", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageNsx(@RequestParam("pageNumber") String pageNumber) {
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) noiSanXuatDAO.limit((page -1 ) * 10, 10);
		noiSanXuatDAO.disconnect();
		return JSonUtil.toJson(nsxList);
	}
}
