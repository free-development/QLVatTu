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
	 public ModelAndView downloadExcelDv() {
	        // create some sample data
		 	DonViDAO dvDAO = new DonViDAO();
//	        List<DonVi> listBooksDv = new ArrayList<DonVi>();
	        List<DonVi> listDv = new ArrayList<DonVi>();
	        listDv = dvDAO.getAllDonVi();
//	        int length = listDv.size();
//	        for ( int i = 0; i < length ; i++)
//	        {
//	        	listBooksDv.add(new DonVi(listDv.get(i).getDvMa(), listDv.get(i).getDvTen(), listDv.get(i).getSdt(), listDv.get(i).getDiaChi(), listDv.get(i).getEmail()));
//	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelDv", "listBooksDv", listDv);
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
	 public ModelAndView downloadExcelTon() {
	        // create some sample data
		 	CTVatTuDAO ctvtDAO = new CTVatTuDAO(); 
	        List<CTVatTu> listTon = new ArrayList<CTVatTu>();
	        listTon = ctvtDAO.TonKho();
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelTon", "listTon", listTon);
	    }
	 
	 @RequestMapping(value = "/downloadExcelError", method = RequestMethod.GET)
	 public ModelAndView downloadExcelError(HttpServletRequest request) {
        // create some sample data
	 	HttpSession session = request.getSession(false);
	 	List<CTVatTu> listError = (List<CTVatTu>) session.getAttribute("ctvtListError");
	 	List<String> statusError = (List<String>) session.getAttribute("statusError");
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
}
