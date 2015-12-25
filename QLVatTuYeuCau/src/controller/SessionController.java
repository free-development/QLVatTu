package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CongVanDAO;
import dao.DonViDAO;
import dao.MucDichDAO;
import dao.YeuCauDAO;
import map.siteMap;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.NguoiDung;
import util.DateUtil;
import util.JSonUtil;

@Controller
public class SessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int page = 1;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(SessionController.class);
	@RequestMapping(value="/removeSession", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadBccv(@RequestParam("name") String sessionName, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) { 
				logger.error("Không chứng thực remove session");
				JSonUtil.toJson("authentication error");
			} 
			session.removeAttribute(sessionName);
			System.out.println("welcome to remove session controller");
			session.removeAttribute(sessionName);
			return JSonUtil.toJson("success");
		} catch (NullPointerException e) {
			logger.error("Lỗi khi remove session: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}
}
