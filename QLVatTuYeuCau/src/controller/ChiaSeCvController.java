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
// @WebServlet("/ChiaSeCvController")
public class ChiaSeCvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ServletContext context;
	// private int pageCscv = 1;
	// HttpSession session = null;
	HttpSession session;
	HttpServletResponse res = null;
	String truongPhongMa  = "";
	String phoPhongMa = "";
	String adminMa = "";
	@RequestMapping("/cscvManage")
	protected ModelAndView cscvManage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("chiaSeCv".equalsIgnoreCase(action)) {
			session = request.getSession(false);
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
			HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = new HashMap<String, HashMap<Integer, VaiTro>>();
			for (String msnv : vtNguoiDungHash.keySet()) {
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<Integer, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
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
		}
		return new ModelAndView("login");
	}

	@RequestMapping("/chiaSeCv")
	protected ModelAndView chiaSeCv(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		if ("save".equalsIgnoreCase(action)) {
			// session = request.getSession(false);
			session = request.getSession(false);
			CongVan congVan = (CongVan) session.getAttribute("congVan");
			String[] vaiTro = request.getParameterValues("vaiTro");
			VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			VaiTroDAO vaiTroDAO = new VaiTroDAO();
			int cvId = congVan.getCvId();
			vtCongVanDAO.deleteByCvId(cvId);
			for (String vtMa : vaiTro) {
				String[] str = vtMa.split("\\#");
				NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(str[0]);
				VTCongVan vtCongVan = new VTCongVan();
				vtCongVan.setCvId(cvId);
				vtCongVan.setMsnv(str[0]);
				vtCongVan.setTrangThai(new TrangThai("CGQ"));
				vtCongVan.setVtId(Integer.parseInt(str[1]));
				vtCongVanDAO.addOrUpdateVTCongVan(vtCongVan);
			}
			CongVanDAO congVanDAO = new CongVanDAO();
			CongVan congVanUpdate = congVanDAO.getCongVan(cvId);
			congVanUpdate.setTrangThai(new TrangThai("DGQ"));
			congVanDAO.updateCongVan(congVanUpdate);
			congVanDAO.disconnect();
			HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
			HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = new HashMap<String, HashMap<Integer, VaiTro>>();
			StringBuilder hotens = new StringBuilder("");
			for (String msnv : vtNguoiDungHash.keySet()) {
				
				ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
				HashMap<Integer, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
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
				for(Integer vtId : vtHash.keySet()) {
					vt = vtHash.get(vtId);
					str1 += "\t+" + vt.getVtTen() + ".\n ";
					str2.append(vt.getVtTen() + ", ");
				}
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
				mail.setSubject("Công việc được chia sẻ");
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
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date ());
			NhatKy nhatKy = new NhatKy(nguoiDung.getMsnv(), congVan.getCvId() + "#Chia sẻ công văn số " + congVan.getSoDen() + " nhận ngày " + DateUtil.toString(congVan.getCvNgayNhan()), currentDate,  hotens.toString());
//			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			nhatKyDAO.addNhatKy(nhatKy);
//			HttpSession session = request.getSession(false);
//			session.setAttribute("nhatKy", nhatKy);
			
			request.setAttribute("vaiTroHash", vaiTroHash);
			request.setAttribute("vtNguoiDungHash", vtNguoiDungHash);
			vtCongVanDAO.disconnect();
			nguoiDungDAO.disconnect();
			vaiTroDAO.disconnect();
			return new ModelAndView(siteMap.chiaSeCv);
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/preUpdateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateYeuCau(@RequestParam("msnv") String msnv) {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();

		CongVan congVan = (CongVan) session.getAttribute("congVan");
		session.setAttribute("msnvUpdate", msnv);
		// NguoiDung nguoiDung = nguoiDungDAO.getNguoiDung(msnv);
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
	}

	@RequestMapping(value = "/updateYeuCau", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateYeuCau(HttpSession session, @RequestParam("vaiTroList") String vaiTroList) throws IOException {
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();

		CongVan congVan = (CongVan) session.getAttribute("congVan");
		String msnvUpdate = (String) session.getAttribute("msnvUpdate");
		int cvId = congVan.getCvId();
		vtCongVanDAO.delete(cvId, msnvUpdate);
		//System.out.println(msnvUpdate);
		if (msnvUpdate == null || congVan == null)
			res.sendRedirect(siteMap.cvManage + "?action=manageCv");
		// return JSonUtil.toJson("delete");
		String[] vtList = vaiTroList.split("\\, ");
		// StringBuilder str = new StringBuilder("");
		ArrayList<Object> objectList = new ArrayList<Object>();
		ArrayList<VaiTro> list = new ArrayList<VaiTro>();
		if (vaiTroList.length() != 0) {
			for (String s : vtList) {
				int vtId = Integer.parseInt(s);
				vtCongVanDAO.addVTCongVan(new VTCongVan(cvId, vtId, msnvUpdate, new TrangThai("CGQ")));
				// String vt = vtCongVanDAO.getVaiTro(vtId);
				// str.append(vt + "<br>");
				VaiTro vt = vaiTroDAO.getVaiTro(vtId);
				// JOptionPane.showMessageDialog(null, vt.getVtTen());
				list.add(vt);
			}
			// str.delete(str.length()-4, 4);
		}
		HashMap<String, NguoiDung> vtNguoiDungHash = vtCongVanDAO.getNguoiXuLy(cvId);
		HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = new HashMap<String, HashMap<Integer, VaiTro>>();
		StringBuilder hotens = new StringBuilder("");
		for (String msnv : vtNguoiDungHash.keySet()) {
			
			ArrayList<VTCongVan> vtcvList = vtCongVanDAO.getVTCongVan(cvId, msnv);
			HashMap<Integer, VaiTro> vtHash = vtCongVanDAO.toVaiTro(vtcvList);
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
			for(Integer vtId : vtHash.keySet()) {
				vt = vtHash.get(vtId);
				str1 += "\t+" + vt.getVtTen() + ".\n ";
				str2.append(vt.getVtTen() + ", ");
			}
			Mail mail = new Mail();
			mail.setFrom(account);
			mail.setTo(nguoiDung.getEmail());
			mail.setSubject("Công việc được chia sẻ");
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
		String truongPhongMa = context.getInitParameter("truongPhongMa");
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
	}

	// @RequestMapping(value="/sendMail", method=RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON_VALUE, consumes =
	// MediaType.APPLICATION_JSON_VALUE)
	// public @ResponseBody String sendMail(@RequestParam("email") String
	// email,@RequestParam("chude") String chude,@RequestParam("noidung") String
	// noidung) {
	// Mailer mailer = new Mailer();
	// mailer.send(email, chude, noidung);
	// ArrayList<Object> objectList = new ArrayList<Object>();
	// objectList.add(mailer);
	// return JSonUtil.toJson(objectList);
	// }

	@RequestMapping(value = "/loadPageCscv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageCscv(@RequestParam("pageNumber") String pageNumber) {
		CTNguoiDungDAO ndDAO = new CTNguoiDungDAO();
		int page = Integer.parseInt(pageNumber);
		ArrayList<Object> objectList = new ArrayList<Object>();
		String adminMa = context.getInitParameter("adminMa");
		String truongPhongMa = context.getInitParameter("truongPhongMa");
		ArrayList<String> ignoreList = new ArrayList<String>();
		ignoreList.add(truongPhongMa);
		ignoreList.add(adminMa);
		long sizeNd = ndDAO.size();
		ArrayList<NguoiDung> ndList = (ArrayList<NguoiDung>) ndDAO.limit(ignoreList, (page - 1) * 10, 10);
		objectList.add(ndList);
		objectList.add((sizeNd - 1) / 10);
		// return JSonUtil.toJson(objectList);
		ndDAO.disconnect();
		return JSonUtil.toJson(objectList);
	}
	@RequestMapping(value="/timKiemNguoidungCs", method=RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String timKiemNguoidungCs(@RequestParam("msnv") String msnv, @RequestParam("hoTen") String hoTen) {
		truongPhongMa =  context.getInitParameter("truongPhongMa");
		phoPhongMa = context.getInitParameter("phoPhongMa");
		adminMa = context.getInitParameter("adminMa");
		NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
		VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
		VaiTroDAO vaiTroDAO = new VaiTroDAO();
		CongVan congVan = (CongVan) session.getAttribute("congVan");
		ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>)vaiTroDAO.getAllVaiTro();
		ArrayList<NguoiDung> nguoiDungList = new ArrayList<NguoiDung>();
//		ArrayList<ArrayList<VTCongVan>> vtNguoiDungList = new ArrayList<ArrayList<VTCongVan>>();
		ArrayList<VTCongVan> vtNguoiDungList = new ArrayList<VTCongVan>();
		
		ArrayList<Object> objectList = new ArrayList<Object>();
		//ArrayList<VaiTro> list = new ArrayList<VaiTro>();
		ArrayList<String> ignoreList = new ArrayList<String>();
		ignoreList.add(adminMa);
		ignoreList.add(truongPhongMa);
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
	}
	
}
