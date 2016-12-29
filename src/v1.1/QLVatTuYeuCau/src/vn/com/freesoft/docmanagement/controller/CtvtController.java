package vn.com.freesoft.docmanagement.controller;

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

import vn.com.freesoft.docmanagement.dao.CTVatTuDAO;
import vn.com.freesoft.docmanagement.dao.ChatLuongDAO;
import vn.com.freesoft.docmanagement.dao.NoiSanXuatDAO;
import vn.com.freesoft.docmanagement.dao.VatTuDAO;
import vn.com.freesoft.docmanagement.entity.CTVatTu;
import vn.com.freesoft.docmanagement.entity.ChatLuong;
import vn.com.freesoft.docmanagement.entity.NguoiDung;
import vn.com.freesoft.docmanagement.entity.NoiSanXuat;
import vn.com.freesoft.docmanagement.entity.VatTu;
import vn.com.freesoft.docmanagement.mapping.siteMap;
import vn.com.freesoft.docmanagement.util.JSonUtil;

@Controller
public class CtvtController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 1;
	private int pageCtvt = 1;
	private String searchTen = "";
	private String searchMa = "";
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(CtvtController.class);

	@RequestMapping("/manageCtvt")
	protected ModelAndView manageCtvt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
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
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();

			long size = ctVatTuDAO.sizeTon();
			ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) ctVatTuDAO.limitTonKho(page - 1, 10);
			session.setAttribute("size", size);
			session.setAttribute("objectList", ctVatTuList);
			ctVatTuDAO.disconnect();
			vatTuDAO.disconnect();
			return new ModelAndView(siteMap.vatTuTonKho);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}

	}

	@RequestMapping("/exportCTVatTu")
	protected ModelAndView exportCTVatTu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> allCTVatTuList = (ArrayList<CTVatTu>) ctVatTuDAO.getAllCTVatTu();
			session.setAttribute("objectList", allCTVatTuList);
			ctVatTuDAO.disconnect();
			return new ModelAndView(siteMap.xuatCTVatTu);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất chi tiết vật tư");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/manageXuatTonKho")
	protected ModelAndView manageXuatTonKho(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> tonKhoList = (ArrayList<CTVatTu>) ctVatTuDAO.tonKho();
			session.setAttribute("objectList", tonKhoList);
			ctVatTuDAO.disconnect();
			return new ModelAndView(siteMap.xuatTonKho);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất quản lý tồn kho");
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/showCTVatTu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String showCTVatTu(@RequestParam("vtMa") String vtMa, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			VatTuDAO vatTuDAO = new VatTuDAO();
			ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) ctVatTuDAO.getCTVTu(vtMa);
			if (listCTVatTu.size() == 0) {
				VatTu vatTu = vatTuDAO.getVatTu(vtMa);
				vatTuDAO.disconnect();
				ctVatTuDAO.disconnect();
				return JSonUtil.toJson(vatTu);
			}
			vatTuDAO.disconnect();
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(listCTVatTu);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/preEditCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preEditCTVattu(@RequestParam("ctvtId") String ctvtId, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			CTVatTu vt = ctVatTuDAO.getCTVatTuById(Integer.parseInt(ctvtId));
			ctVatTuDAO.disconnect();
			return JSonUtil.toJson(vt);
		} catch (NullPointerException e) {
			logger.error("NullPointr Exception khi show cập nhật chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi show cập nhật chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/addCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addCTVattu(@RequestParam("vtMa") String vtMa, @RequestParam("vtTen") String vtTen,
			@RequestParam("noiSanXuat") String noiSanXuat, @RequestParam("chatLuong") String chatLuong,
			@RequestParam("dvt") String dvt, @RequestParam("dinhMuc") String dinhMuc,
			@RequestParam("soLuongTon") String soLuongTon, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			CTVatTu ctvt = ctVatTuDAO.getCTVatTu(vtMa, noiSanXuat, chatLuong);
			if (ctvt == null) {
				VatTuDAO vtDAO = new VatTuDAO();
				VatTu vt = vtDAO.getVatTu(vtMa);
				NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
				NoiSanXuat nsx = nsxDAO.getNoiSanXuat(noiSanXuat);
				ChatLuongDAO clDAO = new ChatLuongDAO();
				ChatLuong cl = clDAO.getChatLuong(chatLuong);
				ctvt = new CTVatTu(vt, nsx, cl, Integer.parseInt(dinhMuc), Integer.parseInt(soLuongTon), 0);
				ctVatTuDAO.addCTVatTu(ctvt);
				ctVatTuDAO.disconnect();
				return JSonUtil.toJson(ctvt);

			} else if (ctvt.getDaXoa() == 1) {
				ctvt.setVatTu(new VatTu(vtMa));
				ctvt.setNoiSanXuat(new NoiSanXuat(noiSanXuat));
				ctvt.setChatLuong(new ChatLuong(chatLuong));
				ctvt.setDinhMuc(Integer.parseInt(dinhMuc));
				ctvt.setSoLuongTon(Integer.parseInt(soLuongTon));
				ctvt.setDaXoa(0);
				ctVatTuDAO.updateCTVatTu(ctvt);
				ctVatTuDAO.disconnect();
				return JSonUtil.toJson(ctvt);
			} else {
				ctVatTuDAO.disconnect();
				return JSonUtil.toJson("");
			}
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi thêm chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi thêm chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		}

	}

	@RequestMapping(value = "/updateCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateCTVattu(@RequestParam("vtMaUpdate") String vtMaUpdate,
			@RequestParam("nsxUpdate") String nsxUpdate, @RequestParam("clUpdate") String clUpdate,
			@RequestParam("dinhMucUpdate") String dinhMucUpdate,
			@RequestParam("soLuongTonUpdate") String soLuongTonUpdate, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMaUpdate, nsxUpdate, clUpdate);

			if (ctvt != null) {
				ctvt.setDinhMuc(Integer.parseInt(dinhMucUpdate));
				ctvt.setSoLuongTon(Integer.parseInt(soLuongTonUpdate));
				ctvtDAO.updateCTVatTu(ctvt);
			} else {
				ctvtDAO.disconnect();
				return JSonUtil.toJson("fail");
			}
			ctvtDAO.disconnect();
			return JSonUtil.toJson(ctvt);
		} catch (NumberFormatException e) {
			logger.error("NumberFormat Exception khi cập nhật chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		} catch (NullPointerException e2) {
			logger.error("NullPointer Exception khi cập nhật chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteCTVattu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteVattu(@RequestParam("ctvtList") String ctvtList, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			String[] str = ctvtList.split("\\, ");
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			for (String ctvtId : str) {
				ctvtDAO.deleteCTVatTu(Integer.parseInt(ctvtId));
			}
			ctvtDAO.disconnect();
			return JSonUtil.toJson(ctvtList);
		} catch (NullPointerException e2) {
			logger.error("NumberFormat Exception khi xóa chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e) {
			logger.error("NumberFormat Exception khi xóa chi tiết vật tư");
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBoundsException khi xóa chi tiết vật tư: " + e2.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageCTVatTu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageVt(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
				return JSonUtil.toJson("authentication error");
			}
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<Object> objectList = new ArrayList<Object>();
			long sizevt = ctvtDAO.sizeTon();
			ArrayList<CTVatTu> ctvatTuList = (ArrayList<CTVatTu>) ctvtDAO.limitTonKho(page * 10, 10);
			objectList.add(ctvatTuList);
			objectList.add((sizevt - 1) / 10);
			ctvtDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang tồn kho");
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang tồn kho");
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping("/danhMucVatTu")
	protected ModelAndView danhMucVatTu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập danh mục chi tiết vật tư");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)) {
				logger.error("Không có quy�?n truy cập vào danh mục chi tiết vật tư");
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
			CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
			ArrayList<CTVatTu> allCTVatTuList = (ArrayList<CTVatTu>) ctVatTuDAO.getAllCTVatTu();
			session.setAttribute("objectList", allCTVatTuList);
			ctVatTuDAO.disconnect();
			return new ModelAndView(siteMap.xuatCTVatTu);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi xuất chi tiết vật tư");
			return new ModelAndView(siteMap.login);
		}
	}
}
