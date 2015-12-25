package controller;

import java.io.IOException;
import java.sql.Date;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dao.CTNguoiDungDAO;
import dao.CongVanDAO;
import dao.NguoiDungDAO;
import dao.NhatKyDAO;
import dao.VTCongVanDAO;
import dao.VaiTroDAO;
import map.siteMap;
import model.CongVan;
//import model.Mailer;
import model.NguoiDung;
import model.NhatKy;
import model.TrangThai;
import model.VTCongVan;
import model.VaiTro;
import util.DateUtil;
import util.JSonUtil;
import util.Mail;
import util.SendMail;

@Controller
public class ChiaSeCvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String truongPhongMa  = "";
	String phoPhongMa = "";
	String adminMa = "";
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(ChiaSeCvController.class);
	@RequestMapping("/cscvManage")
	protected ModelAndView cscvManage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
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
			String id = request.getParameter("congVan");
			int cvId = Integer.parseInt(id);
			CongVanDAO congVanDAO = new CongVanDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			truongPhongMa =  context.getInitParameter("truongPhongMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			adminMa = context.getInitParameter("adminMa");
			CongVan congVan = congVanDAO.getCongVan(cvId);
			ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(truongPhongMa);
			ignoreList.add(adminMa);
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.getAllNguoiDung(ignoreList);
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();

			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<String, VaiTro>> vaiTroHash = new HashMap<String, HashMap<String, VaiTro>>();
			for (String msnv : vtNguoiDungHash.keySet()) {
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<String, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
				vaiTroHash.put(msnv, vtHash);
			}
			request.setAttribute("vaiTroHash", vaiTroHash);
			request.setAttribute("vtNguoiDungHash", vtNguoiDungHash);
			session.setAttribute("vaiTroList", vaiTroList);
			session.setAttribute("nguoiDungList", nguoiDungList);
			session.setAttribute("congVan", congVan);

			congVanDAO.disconnect();
			vaiTroDAO.disconnect();
			nguoiDungDAO.disconnect();
			return new ModelAndView(siteMap.chiaSeCv);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi truy cập chia sẻ công văn: " + e.getStackTrace());
			return new ModelAndView(siteMap.login);
		} catch (NumberFormatException e2){
			logger.error("NumberFormat Exception khi truy cập chia sẻ công văn: " + e2.getStackTrace());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/chiaSeCv")
	protected ModelAndView chiaSeCv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return new ModelAndView(siteMap.login);
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
				return new ModelAndView(siteMap.login);
			}
			request.getCharacterEncoding();
			response.getCharacterEncoding();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			// session = request.getSession(false);
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			String[] vaiTro = request.getParameterValues("vaiTro");
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			int cvId = congVan.getCvId();
			vtCongVanDAO.deleteByCvId(cvId);
			for (String vtMa : vaiTro) {
				String[] str = vtMa.split("\\#");
				VTCongVan vtCongVan = new VTCongVan();
				vtCongVan.setCvId(cvId);
				vtCongVan.setMsnv(str[0]);
				vtCongVan.setTrangThai(new TrangThai("CGQ"));
				vtCongVan.setVtMa(str[1]);
				vtCongVanDAO.addOrUpdateVTCongVan(vtCongVan);
			}
			CongVanDAO congVanDAO = new CongVanDAO();
			CongVan congVanUpdate = congVanDAO.getCongVan(cvId);
			congVanUpdate.setTrangThai(new TrangThai("DGQ"));
			congVanDAO.updateCongVan(congVanUpdate);
			congVanDAO.disconnect();
			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<String, VaiTro>> vaiTroHash = new HashMap<String, HashMap<String, VaiTro>>();
			StringBuilder hotens = new StringBuilder("");
			for (String msnv : vtNguoiDungHash.keySet()) {
				
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<String, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
				vaiTroHash.put(msnv, vtHash);
				String str1 = "";
				VaiTro vt = new VaiTro();
				String account = context.getInitParameter("account");
				String password = context.getInitParameter("password");
				String host = context.getInitParameter("hosting");
				SendMail sendMail = new SendMail(account, password);
				vtHash = vaiTroHash.get(msnv);
				NguoiDung nguoiDung = vtNguoiDungHash.get(msnv);
				StringBuilder str2 = new StringBuilder("");
				for(String vtMa : vtHash.keySet()) {
					vt = vtHash.get(vtMa);
					str1 += "\t+" + vt.getVtTen() + ".\n ";
					str2.append(vt.getVtTen() + ", ");
				}
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
//				mail.setSubject("Công việc được chia sẻ");
				mail.setSubject("Cong viec duoc chia se");
				String content = "Bạn đã được chia sẻ công văn. Vui lòng vào hệ thống làm việc để kiểm tra.\n";
				content += "Công việc được chia sẻ là: \n" + str1 + "\n" ;
				content += host + siteMap.searchCongVan + "?congVan=" + cvId + "\nThân mến!";
				mail.setContent(content);
				sendMail.send(mail);
				str2.delete(str2.length()-2, str2.length());
				hotens.append("  <br>&nbsp;&nbsp;+ " +nguoiDung.getHoTen() + ": " + str2 +".");
				
			}
			if(hotens.length() > 0)
				hotens.delete(hotens.length()-1, hotens.length());
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
			NhatKy nhatKy = new NhatKy(nguoiDung.getMsnv(), congVan.getCvId() + "#Chia sẻ công văn số " + congVan.getSoDen() + " nhận ngày " + DateUtil.toString(congVan.getCvNgayNhan()), currentDate,  hotens.toString());
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			nhatKyDAO.addNhatKy(nhatKy);
			
			request.setAttribute("vaiTroHash", vaiTroHash);
			request.setAttribute("vtNguoiDungHash", vtNguoiDungHash);
			vtCongVanDAO.disconnect();
			nguoiDungDAO.disconnect();
			vaiTroDAO.disconnect();
			return new ModelAndView(siteMap.chiaSeCv);
		} catch (NullPointerException e){
			logger.error("NullPointer Exception khi chia sẻ công văn: "  + e.getStackTrace());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/preUpdateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateYeuCau(@RequestParam("msnv") String msnv, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			}
			session.removeAttribute("congVanList");
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
	
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			session.setAttribute("msnvUpdate", msnv);
			ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) vaiTroDAO.getAllVaiTro();
			ArrayList<VTCongVan> vtCongVanList = vtCongVanDAO.getVTCongVan(congVan.getCvId(), msnv);
			ArrayList<Object> objectList = new ArrayList<Object>();
			objectList.add(msnv);
			objectList.add(vaiTroList);
			objectList.add(vtCongVanList);
			nguoiDungDAO.disconnect();
			vtCongVanDAO.disconnect();
			vaiTroDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi show cập nhật chia sẻ công văn: "  + e.getStackTrace());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/updateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateYeuCau(@RequestParam("vaiTroList") String vaiTroList, HttpServletRequest request, HttpServletResponse res) throws IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
	
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			String msnvUpdate = (String) session.getAttribute("msnvUpdate");
			int cvId = congVan.getCvId();
			vtCongVanDAO.delete(cvId, msnvUpdate);
			if (msnvUpdate == null || congVan == null)
				res.sendRedirect(siteMap.cvManage + "?action=manageCv");
			String[] vtList = vaiTroList.split("\\, ");
			ArrayList<Object> objectList = new ArrayList<Object>();
			ArrayList<VaiTro> list = new ArrayList<VaiTro>();
			if (vaiTroList.length() != 0) {
				for (String s : vtList) {
					String vtMa = s;
					vtCongVanDAO.addVTCongVan(new VTCongVan(cvId, vtMa, msnvUpdate, new TrangThai("CGQ"), 0));
					VaiTro vt = vaiTroDAO.getVaiTro(vtMa);
					list.add(vt);
				}
			}
			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<String, VaiTro>> vaiTroHash = new HashMap<String, HashMap<String, VaiTro>>();
			StringBuilder hotens = new StringBuilder("");
			for (String msnv : vtNguoiDungHash.keySet()) {
				
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<String, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
				vaiTroHash.put(msnv, vtHash);
				String str1 = "";
				VaiTro vt = new VaiTro();
				String account = context.getInitParameter("account");
				String password = context.getInitParameter("password");
				String host = context.getInitParameter("hosting");
				SendMail sendMail = new SendMail(account, password);
				vtHash = vaiTroHash.get(msnv);
				NguoiDung nguoiDung = vtNguoiDungHash.get(msnv);
				StringBuilder str2 = new StringBuilder("");
				for(String vtMa : vtHash.keySet()) {
					vt = vtHash.get(vtMa);
					str1 += "\t+" + vt.getVtTen() + ".\n ";
					str2.append(vt.getVtTen() + ", ");
				}
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
	//			mail.setSubject("Công việc được chia sẻ");
				mail.setSubject("Cong viec duoc chia se");
				String content = "Bạn đã được chia sẻ công văn. Vui lòng vào hệ thống làm việc để kiểm tra.\n";
				content += "Công việc được chia sẻ là: \n" + str1 + "\n" ;
				content += host + siteMap.searchCongVan + "?congVan=" + cvId + "\nThân mến!";
				mail.setContent(content);
				sendMail.send(mail);
				str2.delete(str2.length()-2, str2.length());
				hotens.append("  <br>&nbsp;&nbsp;+ " +nguoiDung.getHoTen() + ": " + str2 +".");
				
			}
			if(hotens.length() > 0)
				hotens.delete(hotens.length()-1, hotens.length());
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
			NhatKy nhatKy = new NhatKy(nguoiDung.getMsnv(), "chia sẻ công văn số " + congVan.getSoDen() + " nhận ngày " + DateUtil.toString(congVan.getCvNgayNhan()), currentDate,  hotens.toString());
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			
			session.setAttribute("nhatKy", nhatKy);
			
			objectList.add(list);
			objectList.add(msnvUpdate);
			// vtCongVanDAO.close();
			nguoiDungDAO.disconnect();
			vtCongVanDAO.disconnect();
			vaiTroDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi cập nhật chia sẻ công văn: " + e.getStackTrace());
			return JSonUtil.toJson("authentication error");
		} catch (IndexOutOfBoundsException e2) {
			logger.error("IndexOutOfBounds Exception khi cập nhật chia sẻ công văn: " + e2.getStackTrace());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageCscv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageCscv(@RequestParam("pageNumber") String pageNumber, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			}
			CTNguoiDungDAO ndDAO = new CTNguoiDungDAO();
			int page = Integer.parseInt(pageNumber);
			ArrayList<Object> objectList = new ArrayList<Object>();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(phoPhongMa);
			ignoreList.add(truongPhongMa);
			ignoreList.add(adminMa);
			long sizeNd = ndDAO.size();
			ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ndDAO.limit(ignoreList, (page - 1) * 10, 10);
			objectList.add(ndList);
			objectList.add((sizeNd - 1) / 10);
			// return JSonUtil.toJson(objectList);
			ndDAO.disconnect();
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân trang chia sẻ công văn: " + e.getStackTrace());
			return JSonUtil.toJson("authentication error");
		} catch (NumberFormatException e2) {
			logger.error("NumberFormat Exception khi phân trang chia sẻ công văn: " + e2.getStackTrace());
			return JSonUtil.toJson("authentication error");
		}
	}
	@RequestMapping(value="/timKiemNguoidungCs", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidungCs(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String adminMa = context.getInitParameter("adminMa");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			if (authentication == null) { 
				logger.error("Không chứng thực chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			} else if (!authentication.getChucDanh().getCdMa().equals(adminMa)
					&& !authentication.getChucDanh().getCdMa().equals(truongPhongMa)
					&& !authentication.getChucDanh().getCdMa().equals(phoPhongMa)) {
				logger.error("Không có quyền chia sẻ công văn");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>)vaiTroDAO.getAllVaiTro();
			ArrayList<NguoiDung> nguoiDungList = new ArrayList<NguoiDung>();
			ArrayList<VTCongVan> vtNguoiDungList = new ArrayList<VTCongVan>();
			
			ArrayList<Object> objectList = new ArrayList<Object>();
			//ArrayList<VaiTro> list = new ArrayList<VaiTro>();
			ArrayList<String> ignoreList = new ArrayList<String>();
			ignoreList.add(adminMa);
			ignoreList.add(truongPhongMa);
//			ignoreList.add(phoPhongMa);
			if(msnv.length() > 0)
				nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.searchMsnv(msnv, ignoreList);
			else
				nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.searchHoten(hoTen, ignoreList);
			for (NguoiDung nguoiDung : nguoiDungList) {
				ArrayList<VTCongVan> vtNguoiDung = vtCongVanDAO.getVTCongVan(congVan.getCvId(), nguoiDung.getMsnv());
				vtNguoiDungList.addAll(vtNguoiDung);
			}
			
			nguoiDungDAO.disconnect();
			vtCongVanDAO.disconnect();
			vaiTroDAO.disconnect();
			objectList.add(vaiTroList);
			objectList.add(nguoiDungList);
			objectList.add(vtNguoiDungList);
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("NullPointer Exception khi phân tìm người chia sẻ: " + e.getStackTrace());
			return JSonUtil.toJson("authentication error");
		}
	}
	
}
