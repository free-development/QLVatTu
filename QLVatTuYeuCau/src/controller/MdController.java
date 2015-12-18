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

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import util.JSonUtil;
import dao.ChatLuongDAO;
import dao.MucDichDAO;
import map.siteMap;

@Controller
public class MdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	@RequestMapping("/manageMd")
	public ModelAndView manageMd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		MucDichDAO mucDichDAO = new MucDichDAO();
		String action = request.getParameter("action");
		if("manageMd".equalsIgnoreCase(action)) {
			long size = mucDichDAO.size();
			ArrayList<MucDich> mucDichList =  (ArrayList<MucDich>) mucDichDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			return new ModelAndView("danh-muc-muc-dich", "mucDichList", mucDichList);
		}
		mucDichDAO.disconnect();
		return new ModelAndView("login");
	}
	@RequestMapping(value="/preUpdateMd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preUpdateMd(@RequestParam("mdMa") String mdMa) {
		MucDichDAO mucDichDAO = new MucDichDAO();
		MucDich md = mucDichDAO.getMucDich(mdMa);
		//System.out.println("****" + clMa + "****");
		mucDichDAO.disconnect();
		return JSonUtil.toJson(md);
	}
	@RequestMapping(value="/deleteMd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteMd(@RequestParam("mdList") String mdList) {
		String[] str = mdList.split("\\, ");
		
		MucDichDAO mdDAO =  new MucDichDAO();
		for(String mdMa : str) {
			mdDAO.deleteMucDich(mdMa);
		}
		mdDAO.disconnect();
		return JSonUtil.toJson(mdList);
	}
	
	
	@RequestMapping(value="/addMd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addMd(@RequestParam("mdMa") String mdMa, @RequestParam("mdTen") String mdTen) {
		String result = "success";
		MucDichDAO mucDichDAO = new MucDichDAO();
		MucDich md = mucDichDAO.getMucDich(mdMa);
		if(md == null) 
		{
			mucDichDAO.addMucDich(new MucDich(mdMa, mdTen,0));
			//System.out.println("success");
			result = "success";	
		}
		else if(md !=null && md.getDaXoa()== 1){
			md.setMdMa(mdMa);
			md.setMdTen(mdTen);
			md.setDaXoa(0);
			mucDichDAO.updateMucDich(md);
		}
		else
		{
			//System.out.println("fail");
			result = "fail";
		}
		mucDichDAO.disconnect();
			return JSonUtil.toJson(result);
	}
	
	@RequestMapping(value="/updateMd", method=RequestMethod.GET, 
	produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateMd(@RequestParam("mdMaUpdate") String mdMaUpdate, @RequestParam("mdTenUpdate") String mdTenUpdate) {
		//System.out.println(mdMaUpdate);
		//System.out.println(mdTenUpdate);
		MucDichDAO mucDichDAO = new MucDichDAO();
		MucDich md = new MucDich(mdMaUpdate, mdTenUpdate,0);
		mucDichDAO.updateMucDich(md);
		mucDichDAO.disconnect();
		return JSonUtil.toJson(md);
	}
	@RequestMapping(value="/loadPageMd", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageMd(@RequestParam("pageNumber") String pageNumber) {
		String result = "";
		//System.out.println("MA: " + pageNumber);
		MucDichDAO mdDAO = new MucDichDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<MucDich> mdList = (ArrayList<MucDich>) mdDAO.limit((page -1 ) * 10, 10);
		
		/*
		if(new NoiSanXuatDAO().getNoiSanXuat(nsxMa)==null)
		{
			new NoiSanXuatDAO().addNoiSanXuat(new NoiSanXuat(nsxMa, nsxTen,0));
			System.out.println("success");
			result = "success";	
		}
		else
		{
			System.out.println("fail");
			result = "fail";
		}
		*/
		mdDAO.disconnect();
			return JSonUtil.toJson(mdList);
	}
}
