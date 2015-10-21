package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.CTNguoiDungDAO;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
import model.CTVatTu;
import model.ChatLuong;
import model.NoiSanXuat;
import model.DonVi;

/**
 * Servlet implementation class downloadExcel
 */
@Controller
public class downloadExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 @RequestMapping(value = "/downloadExcelNsx", method = RequestMethod.GET)
	 public ModelAndView downloadExcelNsx() {
	        // create some sample data
		 	NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
//	        List<NoiSanXuat> listBooks = new ArrayList<NoiSanXuat>();
	        List<NoiSanXuat> listNsx = new ArrayList<NoiSanXuat>();
	        listNsx = nsxDAO.getAllNoiSanXuat();
//	        int length = listNsx.size();
//	        for ( int i = 0; i < length ; i++)
//	        {
//	        	listBooks.add(new NoiSanXuat(listNsx.get(i).getNsxMa(), listNsx.get(i).getNsxTen()));
//	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelNsx", "listBooks", listNsx);
	    }
	 
	 @RequestMapping(value = "/downloadExcelCl", method = RequestMethod.GET)
	 public ModelAndView downloadExcelCl() {
	        // create some sample data
		 	ChatLuongDAO clDAO = new ChatLuongDAO();
//	        List<ChatLuong> listBooksCl = new ArrayList<ChatLuong>();
	        List<ChatLuong> listCl = new ArrayList<ChatLuong>();
//	        listCl = clDAO.getAllChatLuong();
//	        int length = listCl.size();
//	        for ( int i = 0; i < length ; i++)
//	        {
//	        	listBooksCl.add(new ChatLuong(listCl.get(i).getClMa(), listCl.get(i).getClTen()));
//	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelCl", "listBooksCl", listCl);
	    }
	 @RequestMapping(value = "/downloadExcelDv", method = RequestMethod.GET)
	 public ModelAndView downloadExcelDv(HttpServletRequest request) {
	        // create some sample data
		 HttpSession session = request.getSession(false);
		 List<DonVi> listDonVi = (List<DonVi>) session.getAttribute("allDonViList");
	        return new ModelAndView("excelDv", "listDv", listDonVi);
	    }
	 
	 @RequestMapping(value = "/downloadExcelCtvt", method = RequestMethod.GET)
	 public ModelAndView downloadExcelCtvt() {
	        // create some sample data
		 	CTVatTuDAO ctvtDAO = new CTVatTuDAO(); 
	        List<CTVatTu> listCtvt = new ArrayList<CTVatTu>();
	        listCtvt = ctvtDAO.getAllCTVatTu();
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelCtvt", "listCtvt", listCtvt);
	    }
	 
	 @RequestMapping(value = "/downloadExcelTon", method = RequestMethod.GET)
	 public ModelAndView downloadExcelTon(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
        List<CTVatTu> listTon = (List<CTVatTu>) session.getAttribute("tonKhoList");
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("excelTon", "listTon", listTon);
	 }
	 @RequestMapping(value = "/downloadTonKhoError", method = RequestMethod.GET)
	 public ModelAndView downloadTonKhoError(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
        List<Object> objectListError = (List<Object>) session.getAttribute("objectListError");
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("excelTonError", "objectListError", objectListError);
	 }
	 @RequestMapping(value = "/downloadExcelError", method = RequestMethod.GET)
	 public ModelAndView downloadExcelError(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
	 	ArrayList<Object> errorListVatTu = (ArrayList<Object>) session.getAttribute("errorListVatTu");
		List<CTVatTu> listError = (List<CTVatTu>) errorListVatTu.get(0);
		List<String> statusError = (List<String>) errorListVatTu.get(1);
	 	ArrayList<Object> objectList = new ArrayList<Object>();
	 	objectList.add(listError);
	 	objectList.add(statusError);
        // return a view which will be resolved by an excel view resolver
        return new ModelAndView("excelError", "objectList", objectList);
    }
	 @RequestMapping(value = "/downloadCvError", method = RequestMethod.GET)
	 public ModelAndView downloadCvError(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
	 	List<Object> errorList = (List<Object>) session.getAttribute("errorList");
	 	
        // return a view which will be resolved by an excel view resolvervError
        return new ModelAndView("cvError", "errorList", errorList);
    }
	@RequestMapping(value = "/downloadBpsdError", method = RequestMethod.GET)
	 public ModelAndView downloadVatTuError(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
	 	List<Object> errorList = (List<Object>) session.getAttribute("errorList");
	 	
        // return a view which will be resolved by an excel view resolvervError
        return new ModelAndView("importBpsdError", "errorList", errorList);
    }
	@RequestMapping(value = "/downloadNsxError", method = RequestMethod.GET)
	 public ModelAndView downloadNsxError(HttpServletRequest request) {
       // create some sample data
	 	HttpSession session = request.getSession(false);
	 	List<Object> errorList = (List<Object>) session.getAttribute("errorList");
	 	
       // return a view which will be resolved by an excel view resolvervError
       return new ModelAndView("importNsxError", "errorList", errorList);
   }
	@RequestMapping(value = "/downloadClError", method = RequestMethod.GET)
	 public ModelAndView downloadClError(HttpServletRequest request) {
      // create some sample data
	 	HttpSession session = request.getSession(false);
	 	List<Object> errorList = (List<Object>) session.getAttribute("errorList");
	 	
      // return a view which will be resolved by an excel view resolvervError
      return new ModelAndView("importClError", "errorList", errorList);
  }
}
