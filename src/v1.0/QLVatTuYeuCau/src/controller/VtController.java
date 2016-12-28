package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import model.NguoiDung;
import model.VaiTro;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(VtController.class); 
	@RequestMapping("/manageVt")
	public ModelAndView manageVt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục bộ phận sử dụng");
				return new ModelAndView(siteMap.login);
			}
			session.removeAttribute("congVanList");
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			session.removeAttribute("errorList");
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			long size = vaiTroDAO.size();
			ArrayList<VaiTro> vaiTroList =  (ArrayList<VaiTro>) vaiTroDAO.limit(page - 1, 10);
			//System.out.println(size);
			request.setAttribute("size", size);
			vaiTroDAO.disconnect();
			return new ModelAndView("danh-muc-vai-tro", "vaiTroList", vaiTroList);
		} catch (NullPointerException e) {
			
			return new ModelAndView(siteMap.login);
		}
	}
	@RequestMapping(value="/preEditvt", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String preEditvt(@RequestParam("vtMa") String vtMa, HttpServletRequest request) {
		try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục vai trò");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quyền truy cập danh mục bộ phận sử dụng");
				return JSonUtil.toJson("authentication error");
			}
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			VaiTro vaiTro = vaiTroDAO.getVaiTro(vtMa);
			session.setAttribute("vtOld", vaiTro);
			return JSonUtil.toJson(vaiTro);
		} catch (NullPointerException e) {
			
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/addvt", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addvt(@RequestParam("vtMa") String vtMa ,@RequestParam("vtTen") String vtTen, HttpServletRequest request) {
//		try
		String result = "success";
		//System.out.println("Ten: "+ vtTen);
		VaiTroDAO vtDAO = new VaiTroDAO();
		VaiTro vt = vtDAO.getVaiTro(vtMa);
		if(vt == null)
		{
			vtDAO.addVaiTro(new VaiTro(vtMa, vtTen, 0));
			//System.out.println("success");
			result = "success";
			vtDAO.disconnect();
		}
		else if(vt.getDaXoa() == 1){
			vt.setVtTen(vtTen);
			vt.setDaXoa(0);
//			vtDAO.updateVaiTro(vt);
			vtDAO.commit();
			result = "success";
		}
		else 
		{
			//System.out.println("fail");
			result = "fail";
		}
		
			return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/updatevt", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updatevt(@RequestParam("vtMaUpdate") String vtMaUpdate, @RequestParam("vtTenUpdate") String vtTenUpdate, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		VaiTroDAO vtDAO = new VaiTroDAO();
		VaiTro vaiTro = new VaiTro();
		vaiTro.setVtMa(vtMaUpdate);
		vaiTro.setVtTen(vtTenUpdate);
		vaiTro.setDaXoa(0);
		vtDAO.updateVaiTro(vaiTro);
		vtDAO.disconnect();
		return JSonUtil.toJson(vaiTro);
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