package controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ChatLuong;
import model.ChucDanh;
import model.MucDich;
import model.NoiSanXuat;

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
import dao.MucDichDAO;
import dao.NoiSanXuatDAO;
import map.siteMap;

@Controller
public class CdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@RequestMapping("/manageCd")
	public ModelAndView manageCd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
			String action = request.getParameter("action");
			long size = chucDanhDAO.size();
			ArrayList<ChucDanh> chucDanhList =  (ArrayList<ChucDanh>) chucDanhDAO.limit(page -1, 10);
			request.setAttribute("size", size);
			chucDanhDAO.disconnect();
			return new ModelAndView("danh-muc-chuc-danh", "chucDanhList", chucDanhList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
	
	@RequestMapping(value="/preUpdateCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateCd(@RequestParam("cdMa") String cdMa) {
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		ChucDanh cd = chucDanhDAO.getChucDanh(cdMa);
		chucDanhDAO.disconnect();
		return JSonUtil.toJson(cd);
	}
	
	@RequestMapping(value="/deleteCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteCd(@RequestParam("cdList") String cdList) {
		String[] str = cdList.split("\\, ");
		
		ChucDanhDAO cdDAO =  new ChucDanhDAO();
		for(String cdMa : str) {
			cdDAO.deleteChucDanh(cdMa);
		}
		cdDAO.disconnect();
		return JSonUtil.toJson(cdList);
	}
	@RequestMapping(value="/addCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addCd(@RequestParam("cdMa") String cdMa, @RequestParam("cdTen") String cdTen) {
		String result = "success";
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		ChucDanh cd = chucDanhDAO.getChucDanh(cdMa);
		if(cd == null) 
		{
			chucDanhDAO.addChucDanh(new ChucDanh(cdMa, cdTen,0));
			result = "success";	
		}
		else if(cd !=null && cd.getDaXoa()== 1){
			cd.setCdMa(cdMa);
			cd.setCdTen(cdTen);
			cd.setDaXoa(0);
			chucDanhDAO.updateChucDanh(cd);
		}
		else
		{
			result = "fail";
		}
		chucDanhDAO.disconnect();
		return JSonUtil.toJson(result);
	}
	
	@RequestMapping(value="/updateCd", method=RequestMethod.GET, 
	produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateCd(@RequestParam("cdMaUpdate") String cdMaUpdate, @RequestParam("cdTenUpdate") String cdTenUpdate) {
		ChucDanhDAO chucDanhDAO = new ChucDanhDAO();
		ChucDanh cd = new ChucDanh(cdMaUpdate, cdTenUpdate,0);
		chucDanhDAO.updateChucDanh(cd);
		chucDanhDAO.disconnect();
		return JSonUtil.toJson(cd);
	}
	
	@RequestMapping(value="/loadPageCd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageCd(@RequestParam("pageNumber") String pageNumber) {
		ChucDanhDAO cdDAO = new ChucDanhDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<ChucDanh> cdList = (ArrayList<ChucDanh>) cdDAO.limit((page -1 ) * 10, 10);
		cdDAO.disconnect();
		return JSonUtil.toJson(cdList);
	}
}
