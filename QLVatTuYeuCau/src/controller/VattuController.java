package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.DonViTinhDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;
import map.siteMap;
import model.ChatLuong;
import model.DonViTinh;
import model.NguoiDung;
import model.NoiSanXuat;
import model.VatTu;
import util.JSonUtil;

@Controller
public class VattuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	private String filter = "";
	private String filterValue = "";
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(VattuController.class);
   @RequestMapping("/manageVattu")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   try {
		   	HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
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
			VatTuDAO vatTuDAO = new VatTuDAO();
			NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
			ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
			DonViTinhDAO donViTinhDAO = new DonViTinhDAO();
			this.filter = "";
			this.filterValue = "";
			long size = vatTuDAO.size();
			ArrayList<VatTu> vatTuList =  (ArrayList<VatTu>) vatTuDAO.limit(page - 1, 10);
			request.setAttribute("size", size);
			ArrayList<NoiSanXuat> noiSanXuatList =  (ArrayList<NoiSanXuat>) noiSanXuatDAO.getAllNoiSanXuat();
			ArrayList<ChatLuong> chatLuongList =  (ArrayList<ChatLuong>) chatLuongDAO.getAllChatLuong();
			ArrayList<DonViTinh> donViTinhList =  (ArrayList<DonViTinh>) donViTinhDAO.getAllDonViTinh();
			request.setAttribute("noiSanXuatList", noiSanXuatList);
			request.setAttribute("chatLuongList", chatLuongList);
			request.setAttribute("donViTinhList", donViTinhList);
			request.setAttribute("vatTuList", vatTuList);
			vatTuDAO.disconnect();
			noiSanXuatDAO.disconnect();
			chatLuongDAO.disconnect();
			donViTinhDAO.disconnect();
			return new ModelAndView("danh-muc-vat-tu");
	   } catch (NullPointerException e) {
		   	logger.error("Lỗi Numberformat Exception trên khi load vật tư do sai url");
			return new ModelAndView(siteMap.login);
		}
	}
   @RequestMapping(value="/preEditVattu", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preEditVattu(@RequestParam("vtMa") String vtMa, HttpServletRequest request) {
	   try {
		   HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			VatTuDAO vatTuDAO = new VatTuDAO();
			VatTu vt = vatTuDAO.getVatTu(vtMa);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
	   } catch (NullPointerException e) {
		   logger.error("Lỗi Numberformat Exception trên khi cập nhật vật tư do sai url");
			return JSonUtil.toJson("authentication error");
	   }
	}
	@RequestMapping(value="/addVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String addVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, @RequestParam("dvt") String dvt, HttpServletRequest request) {
		try { 
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			String result = "success";
			VatTuDAO vatTuDAO = new VatTuDAO();
			VatTu vt = vatTuDAO.getVatTu(vtMa);
			DonViTinhDAO dvtDAO = new DonViTinhDAO();
			int idDvt = Integer.parseInt(dvt);
			DonViTinh dVT = dvtDAO.getDonViTinh(idDvt);
			if(vt == null) 
			{
				vatTuDAO.addVatTu(new VatTu(vtMa, vtTen,dVT,0));
				VatTuDAO vatTuDAO2 = new VatTuDAO();
				VatTu vatTu = vatTuDAO2.getVatTu(vtMa);
				result = JSonUtil.toJson(vatTu);
				return result;
			}
			else if(vt !=null && vt.getDaXoa()== 1){
				vt.setVtMa(vtMa);
				vt.setVtTen(vtTen);
				vt.setDvt(dVT);
				vt.setDaXoa(0);
				vatTuDAO.updateVatTu(vt);
				result = JSonUtil.toJson(vt);
				vatTuDAO.disconnect();
				return result;
			}
			else
			{
				vatTuDAO.disconnect();
				result = "fail";
				return JSonUtil.toJson(result);
			}
		} catch (NumberFormatException e2) {
			logger.error("Lỗi Numberformat Exception trên thêm vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		} catch (NullPointerException e) {
			logger.error("Lỗi NullPointer Exception trên khi thêm vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		}
//			return JSonUtil.toJson(result);
	}
	@RequestMapping(value="/timKiemVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String timKiemVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			VatTuDAO vatTuDAO = new VatTuDAO();
			if(vtMa != ""){
				ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtMaLimit(vtMa, 0, 10);
				this.filter = "vtMa";
				this.filterValue = vtMa;
				long size = vatTuDAO.size(filter, filterValue);
				System.out.println(size);
				ArrayList<Object> objectList = new ArrayList<Object>();
				int page = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
				objectList.add(vtList);
				objectList.add(page);
				System.out.println(page);
				vatTuDAO.disconnect();
				return JSonUtil.toJson(objectList);
			}
			else
			{
				ArrayList<VatTu> vtList = (ArrayList<VatTu>) vatTuDAO.searchVtTenLimit(vtTen, 0, 10);
				this.filter = "vtTen";
				this.filterValue = vtTen;
				long size = vatTuDAO.size(filter, filterValue);
				System.out.println(size);
				ArrayList<Object> objectList = new ArrayList<Object>();
				int page = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
				objectList.add(vtList);
				objectList.add(page);
				System.out.println(page);
				vatTuDAO.disconnect();
				return JSonUtil.toJson(objectList);
			}
		} catch (NullPointerException e) {
			logger.error("Lỗi NullPointer Exception trên khi tìm kiếm vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/updateVattu", method=RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String updateVattu(@RequestParam("vtMaUpdate") String vtMaUpdate, @RequestParam("vtTenUpdate") String vtTenUpdate, @RequestParam("dvtUpdate") String dvtUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			int id = Integer.parseInt(dvtUpdate);
			DonViTinhDAO dvtDAO = new DonViTinhDAO();
			DonViTinh dvt = dvtDAO.getDonViTinh(id);
			VatTu vt = new VatTu(vtMaUpdate, vtTenUpdate, dvt,0);
			VatTuDAO vatTuDAO = new VatTuDAO();
			vatTuDAO.updateVatTu(vt);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
		} catch (NumberFormatException e) {
			logger.error("Lỗi Numberformat Exception trên khi cập vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		} catch (NullPointerException e2) {
			logger.error("Lỗi Nullpoiter Exception khi cập nhật vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/deleteVattu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String deleteVattu(@RequestParam("vtList") String vtList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = vtList.split("\\, ");
			VatTuDAO vatTuDAO = new VatTuDAO();
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			for(String vtMa : str) {
				vatTuDAO.deleteVatTu(vtMa);
			}
			vatTuDAO.disconnect();
			return JSonUtil.toJson(vtList);
		} catch (NullPointerException e) {
			logger.error("Lỗi NullPointer Exception trên khi xóa vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/loadPageVatTu", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public @ResponseBody String loadPageVatTu(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) { 
				logger.error("Không chứng thực truy cập danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Truy cập bất hợp pháp vào danh mục nơi sản xuất");
				return JSonUtil.toJson("authentication error");
			}
			VatTuDAO vatTuDAO = new VatTuDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<Object> objectList = new ArrayList<Object>();
			long size = vatTuDAO.size(filter, filterValue);
			ArrayList<VatTu> vatTuList = (ArrayList<VatTu>) vatTuDAO.searchLimit(filter, filterValue, page * 10, 10);
			objectList.add(vatTuList);
			int p = ((int)size % 10 == 0 ? (int)size/10 : ((int)size/10) + 1);
			objectList.add(p);
			vatTuDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NumberFormatException e) {
			logger.error("Lỗi Numberformat Exception trên khi phân trang vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		} catch (NullPointerException e2) {
			logger.error("Lỗi NumberPointer Exception trên khi phân trang vật tư do sai url");
			return JSonUtil.toJson("authentication error");
		}
	}
}
