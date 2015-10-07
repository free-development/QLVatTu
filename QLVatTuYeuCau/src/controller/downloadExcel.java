package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.ChatLuongDAO;
import dao.DonViDAO;
import dao.NoiSanXuatDAO;
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
	        List<NoiSanXuat> listBooks = new ArrayList<NoiSanXuat>();
	        List<NoiSanXuat> listNsx = new ArrayList<NoiSanXuat>();
	        listNsx = nsxDAO.getAllNoiSanXuat();
	        int length = listNsx.size();
	        for ( int i = 0; i < length ; i++)
	        {
	        	listBooks.add(new NoiSanXuat(listNsx.get(i).getNsxMa(), listNsx.get(i).getNsxTen()));
	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelNsx", "listBooks", listBooks);
	    }
	 
	 @RequestMapping(value = "/downloadExcelCl", method = RequestMethod.GET)
	 public ModelAndView downloadExcelCl() {
	        // create some sample data
		 	ChatLuongDAO clDAO = new ChatLuongDAO();
	        List<ChatLuong> listBooksCl = new ArrayList<ChatLuong>();
	        List<ChatLuong> listCl = new ArrayList<ChatLuong>();
	        listCl = clDAO.getAllChatLuong();
	        int length = listCl.size();
	        for ( int i = 0; i < length ; i++)
	        {
	        	listBooksCl.add(new ChatLuong(listCl.get(i).getClMa(), listCl.get(i).getClTen()));
	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelCl", "listBooksCl", listBooksCl);
	    }
	 @RequestMapping(value = "/downloadExcelDv", method = RequestMethod.GET)
	 public ModelAndView downloadExcelDv() {
	        // create some sample data
		 	DonViDAO dvDAO = new DonViDAO();
	        List<DonVi> listBooksDv = new ArrayList<DonVi>();
	        List<DonVi> listDv = new ArrayList<DonVi>();
	        listDv = dvDAO.getAllDonVi();
	        int length = listDv.size();
	        for ( int i = 0; i < length ; i++)
	        {
	        	listBooksDv.add(new DonVi(listDv.get(i).getDvMa(), listDv.get(i).getDvTen(), listDv.get(i).getSdt(), listDv.get(i).getDiaChi(), listDv.get(i).getEmail()));
	        }
	        // return a view which will be resolved by an excel view resolver
	        return new ModelAndView("excelDv", "listBooksDv", listBooksDv);
	    }
}
