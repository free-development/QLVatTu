package vn.com.freesoft.docmanament.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.freesoft.docmanament.entity.CTVatTu;
import vn.com.freesoft.docmanament.entity.ChatLuong;
import vn.com.freesoft.docmanament.entity.DonVi;
import vn.com.freesoft.docmanament.entity.NoiSanXuat;
import vn.com.freesoft.docmanament.mapping.siteMap;

/**
 * Servlet implementation class downloadExcel
 */
@Controller
public class downloadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/downloadExcelNsx", method = RequestMethod.GET)
	public ModelAndView downloadExcelNsx(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<NoiSanXuat> listNoiSanXuat = (List<NoiSanXuat>) session.getAttribute("objectList");
			return new ModelAndView("excelNsx", "listNoiSanXuat", listNoiSanXuat);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadExcelCl", method = RequestMethod.GET)
	public ModelAndView downloadExcelCl(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			List<ChatLuong> listCl = (List<ChatLuong>) session.getAttribute("objectList");
			return new ModelAndView("excelCl", "listBooksCl", listCl);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadExcelDv", method = RequestMethod.GET)
	public ModelAndView downloadExcelDv(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<DonVi> listDonVi = (List<DonVi>) session.getAttribute("objectList");
			return new ModelAndView("excelDv", "listDv", listDonVi);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadExcelCtvt", method = RequestMethod.GET)
	public ModelAndView downloadExcelCtvt(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<CTVatTu> listCTVatTu = (List<CTVatTu>) session.getAttribute("objectList");
			return new ModelAndView("excelCtvt", "listCtvt", listCTVatTu);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadVatTuAlert", method = RequestMethod.GET)
	public ModelAndView downloadVatTuAlert(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<CTVatTu> vatTuAlert = (List<CTVatTu>) session.getAttribute("objectList");
			return new ModelAndView("vatTuAlert", "vatTuAlert", vatTuAlert);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadExcelTon", method = RequestMethod.GET)
	public ModelAndView downloadExcelTon(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<CTVatTu> listTon = (List<CTVatTu>) session.getAttribute("objectList");
			// return a view which will be resolved by an excel view resolver
			return new ModelAndView("excelTon", "listTon", listTon);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadTonKhoError", method = RequestMethod.GET)
	public ModelAndView downloadTonKhoError(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<Object> objectListError = (List<Object>) session.getAttribute("errorList");
			// return a view which will be resolved by an excel view resolver
			return new ModelAndView("excelTonError", "objectListError", objectListError);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadExcelError", method = RequestMethod.GET)
	public ModelAndView downloadExcelError(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			ArrayList<Object> errorListVatTu = (ArrayList<Object>) session.getAttribute("errorList");
			// return a view which will be resolved by an excel view resolver
			return new ModelAndView("excelError", "objectList", errorListVatTu);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadCvError", method = RequestMethod.GET)
	public ModelAndView downloadCvError(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<Object> errorList = (List<Object>) session.getAttribute("errorList");

			// return a view which will be resolved by an excel view
			// resolvervError
			return new ModelAndView("cvError", "errorList", errorList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadBpsdError", method = RequestMethod.GET)
	public ModelAndView downloadVatTuError(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<Object> errorList = (List<Object>) session.getAttribute("errorList");

			// return a view which will be resolved by an excel view
			// resolvervError
			return new ModelAndView("importBpsdError", "errorList", errorList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadNsxError", method = RequestMethod.GET)
	public ModelAndView downloadNsxError(HttpServletRequest request) {
		// create some sample data
		try {
			HttpSession session = request.getSession(false);
			List<Object> errorList = (List<Object>) session.getAttribute("errorList");
			// return a view which will be resolved by an excel view
			// resolvervError
			return new ModelAndView("importNsxError", "errorList", errorList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/downloadClError", method = RequestMethod.GET)
	public ModelAndView downloadClError(HttpServletRequest request) {
		try {
			// create some sample data
			HttpSession session = request.getSession(false);
			List<Object> errorList = (List<Object>) session.getAttribute("errorList");

			// return a view which will be resolved by an excel view
			// resolvervError
			return new ModelAndView("importClError", "errorList", errorList);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}
}
