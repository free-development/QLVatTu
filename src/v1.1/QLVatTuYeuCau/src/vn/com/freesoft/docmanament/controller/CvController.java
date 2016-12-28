package vn.com.freesoft.docmanament.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.LogicalExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import vn.com.freesoft.docmanament.dao.CongVanDAO;
import vn.com.freesoft.docmanament.dao.DonViDAO;
import vn.com.freesoft.docmanament.dao.FileDAO;
import vn.com.freesoft.docmanament.dao.MucDichDAO;
import vn.com.freesoft.docmanament.dao.NguoiDungDAO;
import vn.com.freesoft.docmanament.dao.NhatKyDAO;
import vn.com.freesoft.docmanament.dao.ReadExcelCongVan;
import vn.com.freesoft.docmanament.dao.TrangThaiDAO;
import vn.com.freesoft.docmanament.dao.VTCongVanDAO;
import vn.com.freesoft.docmanament.dao.VaiTroDAO;
import vn.com.freesoft.docmanament.entity.CongVan;
import vn.com.freesoft.docmanament.entity.DonVi;
import vn.com.freesoft.docmanament.entity.File;
import vn.com.freesoft.docmanament.entity.MucDich;
import vn.com.freesoft.docmanament.entity.NguoiDung;
import vn.com.freesoft.docmanament.entity.TrangThai;
import vn.com.freesoft.docmanament.entity.VTCongVan;
import vn.com.freesoft.docmanament.entity.VaiTro;
import vn.com.freesoft.docmanament.mapping.siteMap;
import vn.com.freesoft.docmanament.model.NhatKy;
import vn.com.freesoft.docmanament.util.DateUtil;
import vn.com.freesoft.docmanament.util.FileUtil;
import vn.com.freesoft.docmanament.util.JSonUtil;
import vn.com.freesoft.docmanament.util.Mail;
import vn.com.freesoft.docmanament.util.SendMail;

@Controller
public class CvController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int page = 0;
	@Autowired
	private ServletContext context;
	private static final Logger logger = Logger.getLogger(CvController.class);

	private HashMap<Integer, HashSet<Integer>> month = new HashMap<Integer, HashSet<Integer>>();
	private HashMap<String, HashSet<Integer>> date = new HashMap<String, HashSet<Integer>>();

	private String ttMa = "";
	private String column = "";
	private Object columnValue = "";
	private Integer cvId = 0;

	private String truongPhongMa = "";
	private String phoPhongMa = "";
	private String adminMa = "";
	private String vanThuMa = "";
	private String thuKyMa = "";
	private String vtCapVt = "";

	public ModelAndView getCongvan(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return new ModelAndView(siteMap.login);
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			vtCapVt = context.getInitParameter("capPhatMa");

			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			String msnv = nguoiDung.getMsnv();

			VTCongVanDAO vtcvDAO = new VTCongVanDAO();

			TrangThaiDAO trangThaiDAO = new TrangThaiDAO();
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;

			if (phoPhongMa.equalsIgnoreCase(cdMa) || truongPhongMa.equalsIgnoreCase(cdMa)
					|| vanThuMa.equalsIgnoreCase(cdMa) || adminMa.equalsIgnoreCase(cdMa) || thuKyMa.equals(cdMa)) {
				msnvTemp = null;
			}
			Integer cvIdSearch = (Integer) request.getAttribute("cvId");
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);
			if (cvIdSearch != null && cvIdSearch != 0) {
				conditions.put("cvId", cvIdSearch);
			}
			// else
			// cvId = 0;

			orderBy.put("cvId", true);
			ArrayList<CongVan> congVanList = (ArrayList<CongVan>) congVanDAO.searchLimit(msnvTemp, conditions, orderBy,
					0, 3);
			HashMap<Integer, File> fileHash = new HashMap<Integer, File>();
			if (cdMa.equals(vanThuMa) || cdMa.equals(truongPhongMa) || cdMa.equals(adminMa) || cdMa.equals(phoPhongMa)
					|| cdMa.equals(thuKyMa)) {
				MucDichDAO mucDichDAO = new MucDichDAO();
				DonViDAO donViDAO = new DonViDAO();
				ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
				ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) mucDichDAO.getAllMucDich();
				donViDAO.disconnect();
				mucDichDAO.disconnect();
				request.setAttribute("mucDichList", mucDichList);
				request.setAttribute("donViList", donViList);

			}
			long size = 0;
			if (cvIdSearch != null)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, null);
			// danh sach nguoi xu ly cong van
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			ArrayList<String> ttMaList = new ArrayList<String>();
			if (msnvTemp != null || phoPhongMa.equals(cdMa) || adminMa.equals(cdMa) || vanThuMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
				request.setAttribute("vaiTroList", vaiTroList);
				request.setAttribute("vtCongVanList", vtCongVanList);

			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
					ttMaList.add(congVan.getTrangThai().getTtMa());
				}
				request.setAttribute("nguoiXlCongVan", nguoiXlCongVan);
			}
			for (CongVan congVan : congVanList) {
				int cvId = congVan.getCvId();
				fileHash.put(cvId, fileDAO.getByCongVanId(cvId));
			}
			ArrayList<Integer> yearList = new ArrayList<Integer>();
			if (cvIdSearch != null)
				yearList.add(congVanList.get(0).getCvNgayNhan().getYear() + 1900);
			else
				yearList = congVanDAO.groupByYearLimit(msnvTemp, null, 5);
			request.setAttribute("congVanList", congVanList);
			request.setAttribute("fileHash", fileHash);

			request.setAttribute("yearList", yearList);
			request.setAttribute("size", size);
			request.setAttribute("ttMaList", ttMaList);
			congVanDAO.disconnect();

			trangThaiDAO.disconnect();
			fileDAO.disconnect();

			vtcvDAO.disconnect();

			return new ModelAndView(siteMap.congVan);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi truy cập công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/cvManage")
	public ModelAndView manageCV(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return new ModelAndView(siteMap.login);
			}
			request.getCharacterEncoding();
			response.getCharacterEncoding();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.removeAttribute("congVanList");
			session.removeAttribute("ctVatTuList");
			session.removeAttribute("soLuongList");
			session.removeAttribute("yeuCauHash");
			session.removeAttribute("ctVatTuHash");
			session.removeAttribute("trangThaiList");
			session.removeAttribute("donViList");
			session.removeAttribute("errorList");
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null)
				response.sendRedirect("login.jsp");

			// cvId = 0;
			month = new HashMap<Integer, HashSet<Integer>>();
			date = new HashMap<String, HashSet<Integer>>();
			ttMa = "";
			column = "";
			columnValue = "";
			cvId = 0;

			return getCongvan(request);
		} catch (NullPointerException e) {
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping("/downloadFileMn")
	public void downloadFileMn(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			FileDAO fileDAO = new FileDAO();
			int cvId = Integer.parseInt(request.getParameter("file"));
			vn.com.freesoft.docmanament.entity.File f = fileDAO.getByCongVanId(cvId);
			fileDAO.close();
			RequestDispatcher dispatcher = request.getRequestDispatcher("downloadFile.html");

			request.setAttribute("path", f.getDiaChi());
			dispatcher.forward(request, response);
			return;
		} catch (NullPointerException e) {

		} catch (NumberFormatException e) {
			System.out.println("Cannot convert to int");
		}
	}

	@RequestMapping("/searchCongVan")
	public ModelAndView searchCongVan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return new ModelAndView(siteMap.login);
			}
			int cvId = Integer.parseInt(request.getParameter("congVan"));
			request.setAttribute("cvId", cvId);
			return getCongvan(request);
		} catch (NumberFormatException e) {
			logger.error("Lỗi khi tìm kiếm công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi tìm kiếm công văn: " + e.getMessage());
			return new ModelAndView(siteMap.login);
		}
	}

	@RequestMapping(value = "/addCongVanInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String addCongVanInfo(MultipartHttpServletRequest multipartRequest) {

		try {
			String pathFile = context.getInitParameter("pathFile");
			HttpSession session = multipartRequest.getSession(true);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			} else {
				String cdMa = authentication.getChucDanh().getCdMa();
				String adminMa = context.getInitParameter("adminMa");
				String vanThuMa = context.getInitParameter("vanThuMa");
				String thuKyMa = context.getInitParameter("thuKyMa");
				if (!adminMa.equals(cdMa) && !vanThuMa.equals(cdMa) && !thuKyMa.equals(cdMa)) {
					logger.error("Không có quy�?n thêm công văn");
					return JSonUtil.toJson("authentication error");
				}
			}
			String cvSo = multipartRequest.getParameter("cvSo");
			CongVanDAO congVanDAO = new CongVanDAO();
			CongVan congVanAdd = new CongVan();
			Date cvNgayDi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoi"));
			Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhan"));
			int soDen = congVanDAO.getSoDenAdd(cvNgayNhan);
			String mdMa = multipartRequest.getParameter("mucDich");
			String dvMa = multipartRequest.getParameter("donVi");
			String trichYeu = multipartRequest.getParameter("trichYeu");
			trichYeu = trichYeu.replaceAll("\n", "<br>");
			String butPhe = multipartRequest.getParameter("butPhe");
			butPhe = butPhe.replaceAll("\n", "<br>");
			String moTa = multipartRequest.getParameter("moTa");
			moTa = moTa.replaceAll("\n", "<br>");
			int cvId = congVanDAO.getLastInsert();
			congVanAdd = new CongVan(cvId, soDen, cvSo, cvNgayNhan, cvNgayDi, trichYeu, butPhe, new MucDich(mdMa),
					new TrangThai("CGQ", ""), new DonVi(dvMa), 0);
			congVanDAO.addCongVan(congVanAdd);
			MultipartFile fileUpload = multipartRequest.getFile("file");
			String fileName = fileUpload.getOriginalFilename();
			String fileNameFull = fileName;
			String fileExtension = FileUtil.getExtension(fileName);
			String name = FileUtil.getName(fileName);
			if (fileExtension.length() > 0) {
				fileName = name + "-" + cvId + "." + fileExtension;
			} else {
				fileName = name + "-" + cvId;
			}
			String path = pathFile + fileName;
			java.io.File file = new java.io.File(path);
			file.createNewFile();
			fileUpload.transferTo(file);
			FileDAO fileDAO = new FileDAO();
			File f = new File(path, moTa, cvId);
			fileDAO.addFile(f);
			fileDAO.disconnect();
			congVanDAO.disconnect();
			CongVanDAO congVanDAO2 = new CongVanDAO();
			CongVan congVanResult = congVanDAO2.getCongVan(cvId);
			congVanDAO2.disconnect();

			String account = context.getInitParameter("account");
			String password = context.getInitParameter("password");
			String truongPhongMa = context.getInitParameter("truongPhongMa");
			String host = context.getInitParameter("hosting");
			SendMail sendMail = new SendMail(account, password);
			NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();
			ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) nguoiDungDAO.getTruongPhong(truongPhongMa);
			nguoiDungDAO.disconnect();
			for (NguoiDung nguoiDung : nguoiDungList) {
				Mail mail = new Mail();
				mail.setFrom(account);
				mail.setTo(nguoiDung.getEmail());
				// mail.setSubject("Công văn mới");
				mail.setSubject("Cong van moi");
				String content = "Chào " + nguoiDung.getHoTen() + ",\n";
				content += " Công văn số " + " nhận ngày " + DateUtil.toString(cvNgayNhan)
						+ " mới được cập nhật. Vui lòng vào hệ thống làm việc để kiểm tra.\n";
				content += host + siteMap.searchCongVan + "?congVan=" + cvId;
				mail.setContent(content);
				sendMail.send(mail);
			}
			String content = "";
			content += "&nbsp;&nbsp;+ �?ơn vị: " + congVanResult.getDonVi().getDvTen() + "<br>";
			content += "&nbsp;&nbsp;+ Ngày gởi " + DateUtil.toString(cvNgayDi) + "<br>";
			content += "&nbsp;&nbsp;+ Mục đích: " + congVanResult.getMucDich().getMdTen() + "<br>";
			content += "&nbsp;&nbsp;+ Ngày nhận: " + DateUtil.toString(cvNgayNhan) + "<br>";
			content += "&nbsp;&nbsp;+ Trích yếu: " + trichYeu + "<br>";
			content += "&nbsp;&nbsp;+ Bút phế: " + butPhe + "<br>";
			if (fileNameFull != null) {
				content += "&nbsp;&nbsp;+ Tên tệp: " + fileNameFull;
			}
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			NhatKy nhatKy = new NhatKy(authentication.getMsnv(),
					cvId + "#Thêm công văn số " + soDen + " nhận ngày " + DateUtil.toString(cvNgayNhan), currentDate,
					content);
			nhatKyDAO.addNhatKy(nhatKy);
			nhatKyDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			if (fileExtension.equalsIgnoreCase("xlsx")) {
				ArrayList<Object> errorList = ReadExcelCongVan.readXlsx(cvId, file);
				if (errorList.size() != 0) {
					session.setAttribute("errorList", errorList);
					return "file error";
				}
			} else if (fileExtension.equalsIgnoreCase("xls")) {
				ArrayList<Object> errorList = ReadExcelCongVan.readXls(cvId, file);
				if (errorList.size() != 0) {
					session.setAttribute("errorList", errorList);
					return "file error";
				}
			}
			objectList.add(congVanResult);
			objectList.add(f);
			return JSonUtil.toJson(objectList);
			// } else {
			// return "exist";
			// }
		} catch (IllegalStateException | IOException | NullPointerException e) {
			logger.error("Lỗi khi thêm công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/updateCongVanInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateCongVanInfo(MultipartHttpServletRequest multipartRequest) {
		String pathFile = context.getInitParameter("pathFile");
		int cvId = Integer.parseInt(multipartRequest.getParameter("cvId"));
		try {
			HttpSession session = multipartRequest.getSession(false);
			ArrayList<Object> objectList = new ArrayList<Object>();
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			} else {
				String cdMa = authentication.getChucDanh().getCdMa();
				String adminMa = context.getInitParameter("adminMa");
				String vanThuMa = context.getInitParameter("vanThuMa");
				String thuKyMa = context.getInitParameter("thuKyMa");
				if (!adminMa.equals(cdMa) && !vanThuMa.equals(cdMa) && !thuKyMa.equals(cdMa)) {
					logger.error("Không có quy�?n thay đổi công văn");
					return JSonUtil.toJson("authentication error");
				}
			}
			Date cvNgayDi = DateUtil.parseDate(multipartRequest.getParameter("ngayGoiUpdate"));
			Date cvNgayNhan = DateUtil.parseDate(multipartRequest.getParameter("ngayNhanUpdate"));
			String mdMa = multipartRequest.getParameter("mucDichUpdate");
			int soDen = Integer.parseInt(multipartRequest.getParameter("soDen"));
			String dvMa = multipartRequest.getParameter("donViUpdate");
			String trichYeu = multipartRequest.getParameter("trichYeuUpdate");
			String butPhe = multipartRequest.getParameter("butPheUpdate");
			String moTa = multipartRequest.getParameter("moTa");
			trichYeu = trichYeu.replaceAll("\n", "<br>");
			butPhe = butPhe.replaceAll("\n", "<br>");
			moTa = moTa.replaceAll("\n", "<br>");
			CongVanDAO congVanDAO = new CongVanDAO();
			CongVan congVan = congVanDAO.getCongVan(cvId);
			congVan.setSoDen(soDen);
			congVan.setButPhe(butPhe);
			congVan.setCvNgayDi(cvNgayDi);
			congVan.setCvNgayNhan(cvNgayNhan);
			congVan.setDonVi(new DonVi(dvMa));
			congVan.setMucDich(new MucDich(mdMa));
			congVan.setTrichYeu(trichYeu);
			congVan.setButPhe(butPhe);
			congVan.setDaXoa(0);
			congVanDAO.updateCongVan(congVan);

			cvId = congVan.getCvId();
			MultipartFile fileUpload = multipartRequest.getFile("file");
			String fileName = fileUpload.getOriginalFilename();
			String fileNameFull = "";
			if (fileName.length() != 0) {
				fileNameFull = fileName;
				String fileExtension = FileUtil.getExtension(fileName);
				String name = FileUtil.getName(fileName);
				if (fileExtension.length() > 0) {
					fileName = name + "-" + cvId + "." + fileExtension;
				} else {
					fileName = name + "-" + cvId;
				}
				String path = pathFile + fileName;
				FileDAO fileDAO = new FileDAO();
				File f = fileDAO.getByCongVanId(cvId);
				fileDAO.disconnect();
				java.io.File file = new java.io.File(path);
				file.createNewFile();
				fileUpload.transferTo(file);

				if (f == null) {
					File f1 = new File(path, moTa, cvId);
					FileDAO fileDAO2 = new FileDAO();
					fileDAO2.addFile(f1);
					fileDAO2.disconnect();
				} else {

					f.setMoTa(moTa);
					f.setDiaChi(path);
					FileDAO fileDAO2 = new FileDAO();
					fileDAO2.updateFile(f);
					fileDAO2.disconnect();
					fileDAO.disconnect();
				}

				if (fileExtension.equalsIgnoreCase("xlsx")) {
					ArrayList<Object> errorList = ReadExcelCongVan.readXlsx(cvId, file);
					if (errorList.size() > 0) {
						session.setAttribute("errorList", errorList);
						return "file error";
					}
				} else if (fileExtension.equalsIgnoreCase("xls")) {
					ArrayList<Object> errorList = ReadExcelCongVan.readXls(cvId, file);
					if (errorList.size() > 0) {
						multipartRequest.setAttribute("status", "formatException");
						session.setAttribute("errorList", errorList);
						return "file error";
					}
				}
				// objectList.add(congVanResult);
				objectList.add(f);
			}
			congVanDAO.disconnect();
			CongVanDAO congVanDAO2 = new CongVanDAO();
			CongVan congVanResult = congVanDAO2.getCongVan(cvId);
			congVanDAO2.disconnect();

			String content = "";
			content += "&nbsp;&nbsp;+ �?ơn vị: " + congVanResult.getDonVi().getDvTen() + "<br>";
			content += "&nbsp;&nbsp;+ Ngày gởi " + DateUtil.toString(cvNgayDi) + "<br>";
			content += "&nbsp;&nbsp;+ Mục đích: " + congVanResult.getMucDich().getMdTen() + "<br>";
			content += "&nbsp;&nbsp;+ Ngày nhận: " + DateUtil.toString(cvNgayNhan) + "<br>";
			content += "&nbsp;&nbsp;+ Trích yếu: " + trichYeu + "<br>";
			content += "&nbsp;&nbsp;+ Bút phế: " + butPhe + "<br>";
			if (fileNameFull.length() != 0) {
				content += "&nbsp;&nbsp;+ Tên tệp: " + fileNameFull;
			}
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			NhatKy nhatKy = new NhatKy(authentication.getMsnv(),
					cvId + "#Thay đổi công văn số " + soDen + " nhận ngày " + cvNgayNhan, currentDate, content);
			nhatKyDAO.addNhatKy(nhatKy);
			nhatKyDAO.disconnect();

			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException | IllegalStateException | IOException e) {
			logger.error("Lỗi khi cập nhật công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/deleteCv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String deleteNsx(@RequestParam("cvId") String cvId, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			} else {
				String cdMa = authentication.getChucDanh().getCdMa();
				String adminMa = context.getInitParameter("pathFile");
				String vanThuMa = context.getInitParameter("vanThuMa");
				String thuKyMa = context.getInitParameter("thuKyMa");
				if (!adminMa.equals(cdMa) && !vanThuMa.equals(cdMa) && !thuKyMa.equals(cdMa)) {
					logger.error("Không có quy�?n xóa công văn");
					return JSonUtil.toJson("authentication error");
				}
			}
			String[] congVanList = cvId.split("\\, ");
			CongVanDAO congVanDAO = new CongVanDAO();
			StringBuilder content = new StringBuilder("");
			FileDAO fileDAO = new FileDAO();
			for (String s : congVanList) {
				int id = Integer.parseInt(s);
				CongVan congVan = congVanDAO.getCongVan(id);
				content.append("&nbsp;&nbsp;+ Số đến " + congVan.getSoDen() + " nhận ngày " + congVan.getCvNgayNhan()
						+ "<br>");
				congVanDAO.deleteCongVan(id);
				File file = fileDAO.getByCongVanId(id);

				java.io.File f = new java.io.File(file.getDiaChi());
				f.delete();
				fileDAO.deleteFile(file);
				fileDAO.disconnect();
			}
			content.delete(content.length() - 4, content.length());
			congVanDAO.disconnect();
			NhatKyDAO nhatKyDAO = new NhatKyDAO();
			Date currentDate = DateUtil.convertToSqlDate(new java.util.Date());
			NhatKy nhatKy = new NhatKy(authentication.getMsnv(), "Xóa công văn", currentDate, content.toString());
			nhatKyDAO.addNhatKy(nhatKy);
			nhatKyDAO.disconnect();
			return JSonUtil.toJson(cvId);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi xóa công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}

	}

	@RequestMapping(value = "/preUpdateCv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String preUpdateCv(@RequestParam("congVan") String congVan, HttpServletRequest request,
			HttpServletResponse responses) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			} else {
				String cdMa = authentication.getChucDanh().getCdMa();
				String adminMa = context.getInitParameter("adminMa");
				String vanThuMa = context.getInitParameter("vanThuMa");
				String thuKyMa = context.getInitParameter("thuKyMa");
				if (!adminMa.equals(cdMa) && !vanThuMa.equals(cdMa) && !thuKyMa.equals(cdMa)) {
					logger.error("Không có quy�?n show update công văn");
					return JSonUtil.toJson("authentication error");
				}
			}
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			int id = Integer.parseInt(congVan);
			CongVan cv = congVanDAO.getCongVan(id);
			File file = fileDAO.getByCongVanId(id);

			fileDAO.disconnect();
			congVanDAO.close();
			ArrayList<Object> objectList = new ArrayList<Object>();
			objectList.add(cv);
			objectList.add(file);

			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi show cập nhật công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}

	}

	@RequestMapping(value = "/loadByYear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadByYear(@RequestParam("year") String yearRequest,
			@RequestParam("checked") Boolean checked, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String msnv = nguoiDung.getMsnv();

			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			int y = Integer.parseInt(yearRequest);
			if (checked)
				month.put(y, null);
			else
				month.remove(y);

			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| cdMa.equals(thuKyMa))
				msnvTemp = null;

			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0) {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}
			ArrayList<Integer> monthList = new ArrayList<Integer>();

			monthList = congVanDAO.groupByMonth(msnvTemp, conditions, y);
			orderBy.put("cvId", true);
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);

			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null || phoPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					File file = fileDAO.getByCongVanId(congVan.getCvId());
					fileList.add(file);

					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			objectList.add(count, monthList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			count++;
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi load by year công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadByMonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadByMonth(@RequestParam("year") String yearRequest,
			@RequestParam("month") String monthRequest, @RequestParam("checked") Boolean checked,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String msnv = nguoiDung.getMsnv();
			int y = Integer.parseInt(yearRequest);

			int m = Integer.parseInt(monthRequest);
			if (checked) {
				date.put(y + "#" + m, null);
				HashSet<Integer> monthList = month.get(y);
				if (monthList == null)
					monthList = new HashSet<Integer>();
				monthList.add(m);
				month.put(y, monthList);

			} else {
				date.remove(y + "#" + m);
				HashSet<Integer> monthList = month.get(y);
				monthList.remove(m);
				month.put(y, monthList);
			}
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();

			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0) {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa) || cdMa.equals(phoPhongMa)
					|| thuKyMa.equals(cdMa))
				msnvTemp = null;
			ArrayList<Integer> dateList = new ArrayList<Integer>();
			dateList = congVanDAO.groupByDate(msnvTemp, conditions, y, m);
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
			ArrayList<File> fileList = new ArrayList<File>();

			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);

			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			objectList.add(count, dateList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi load by month công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadByDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadByDate(@RequestParam("year") String yearRequest,
			@RequestParam("month") String monthRequest, @RequestParam("date") String dateRequest,
			@RequestParam("checked") Boolean checked, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String msnv = nguoiDung.getMsnv();
			int y = Integer.parseInt(yearRequest);
			int m = Integer.parseInt(monthRequest);
			int d = Integer.parseInt(dateRequest);

			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0) {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}
			orderBy.put("cvNgayNhan", true);
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa))
				msnvTemp = null;
			if (checked) {
				// date.put(y+"#"+m, null);
				HashSet<Integer> dateList = date.get(y + "#" + m);
				if (dateList == null)
					dateList = new HashSet<Integer>();
				dateList.add(d);
				date.put(y + "#" + m, dateList);
			} else {
				// date.remove(y+"#"+m);
				HashSet<Integer> dateList = date.get(y + "#" + m);
				dateList.remove(d);
				date.put(y + "#" + m, dateList);
			}
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			count++;
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi load by date công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/searchByTrangThai", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String searchByTrangThai(@RequestParam("trangThai") String trangThai,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");

			String msnv = nguoiDung.getMsnv();
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa) || cdMa.equals(phoPhongMa)
					|| thuKyMa.equals(cdMa))
				msnvTemp = null;

			ttMa = trangThai;
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);

			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0) {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}

			orderBy.put("cvNgayNhan", true);
			orderBy.put("soDen", true);
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			count++;
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi tìm kiếm công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String filter(@RequestParam("filter") String filter,
			@RequestParam("filterValue") String filterValue, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String msnv = nguoiDung.getMsnv();
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa) || cdMa.equals(phoPhongMa)
					|| thuKyMa.equals(cdMa))
				msnvTemp = null;

			if (filter.equals("mdMa"))
				column = "mucDich." + "mdMa";
			else if (filter.equals("dvMa"))
				column = "donVi." + "dvMa";
			else
				column = filter;
			columnValue = filterValue;
			if (filter.equals("cvNgayNhan") || filter.equals("cvNgayDi"))
				columnValue = DateUtil.parseDate((String) columnValue);

			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("cvId", true);
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && columnValue.toString().length() > 0) {
				String value = columnValue.toString();
				int index = value.lastIndexOf("/");
				if (column.equals("soDen")) {
					String soDen = value.substring(0, index);
					String year = value.substring(index + 1);
					try {
						conditions.put("soDen", Integer.parseInt(soDen));
						conditions.put("year", Integer.parseInt(year));
					} catch (NumberFormatException e) {
						return JSonUtil.toJson("empty");
					}
				} else
					conditions.put(column, columnValue);
			}
			if (filter.length() == 0) {
				conditions.remove("cvId");
				cvId = 0;
			}
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			// orderBy.put("cvNgayNhan", true);

			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, 0, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();
			VTCongVanDAO vtcvDAO = new VTCongVanDAO();
			if (msnvTemp != null || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {

					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			count++;
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException e) {
			logger.error("Lỗi khi filter công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/loadPageCongVan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String loadPageCongVan(@RequestParam("pageNumber") String pageNumber,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String msnv = nguoiDung.getMsnv();
			String cdMa = nguoiDung.getChucDanh().getCdMa();
			String msnvTemp = msnv;
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || cdMa.equals(adminMa) || cdMa.equals(phoPhongMa)
					|| cdMa.equals(thuKyMa)) {
				msnvTemp = null;
			}
			page = Integer.parseInt(pageNumber);
			CongVanDAO congVanDAO = new CongVanDAO();
			FileDAO fileDAO = new FileDAO();
			HashMap<String, Object> conditions = new HashMap<String, Object>();
			HashMap<String, Boolean> orderBy = new HashMap<String, Boolean>();
			orderBy.put("soDen", true);
			if (ttMa.length() > 0)
				conditions.put("trangThai.ttMa", ttMa);
			if (column.length() > 0 && ((String) columnValue).length() > 0) {
				if (column.equals("soDen"))
					conditions.put(column, Integer.parseInt((String) columnValue));
				else
					conditions.put(column, columnValue);
			}
			if (this.cvId != 0) {
				conditions.put("cvId", cvId);
			}
			// ArrayList<Integer> yearList =
			// congVanDAO.groupByYearLimit(msnvTemp, conditions, 5);

			orderBy.put("cvNgayNhan", true);
			orderBy.put("soDen", true);
			LogicalExpression expression = congVanDAO.addTimeExpression(null, month, date);
			conditions.put("time", expression);
			ArrayList<CongVan> congVanList = congVanDAO.searchLimit(msnvTemp, conditions, orderBy, (page) * 3, 3);
			ArrayList<File> fileList = new ArrayList<File>();
			ArrayList<ArrayList<String>> nguoiXlCongVan = new ArrayList<ArrayList<String>>();
			// array list vai tro nguoi dung
			ArrayList<ArrayList<VaiTro>> vaiTroList = new ArrayList<ArrayList<VaiTro>>();
			ArrayList<ArrayList<VTCongVan>> vtCongVanList = new ArrayList<ArrayList<VTCongVan>>();

			VTCongVanDAO vtcvDAO = new VTCongVanDAO();

			if (msnvTemp != null || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				VaiTroDAO vaiTroDAO = new VaiTroDAO();
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<VTCongVan> vtcvList = vtcvDAO.getVTCongVan(cvId, msnv);
					ArrayList<VaiTro> vaiTro = vaiTroDAO.getVaiTro(vtcvList);
					vaiTroList.add(vaiTro);
					vtCongVanList.add(vtcvList);
				}
				vaiTroDAO.disconnect();
			}
			if (truongPhongMa.equals(cdMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa) || phoPhongMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				for (CongVan congVan : congVanList) {
					int cvId = congVan.getCvId();
					ArrayList<String> nguoiXl = vtcvDAO.getNguoiXl(cvId);
					nguoiXlCongVan.add(nguoiXl);
				}
			}
			for (CongVan congVan : congVanList) {
				File file = fileDAO.getByCongVanId(congVan.getCvId());
				fileList.add(file);
			}
			long size = 0;
			if (this.cvId != 0)
				size = 1;
			else
				size = congVanDAO.size(msnvTemp, conditions);
			congVanDAO.disconnect();
			fileDAO.disconnect();
			ArrayList<Object> objectList = new ArrayList<Object>();
			int count = 0;
			objectList.add(count, congVanList);
			count++;
			objectList.add(count, fileList);
			count++;
			long page = (size % 3 == 0 ? size / 3 : (size / 3) + 1);
			objectList.add(count, page);
			count++;
			if (msnvTemp == null) {
				objectList.add(count, nguoiXlCongVan);
				count++;
			}
			if (cdMa.equals(nhanVienMa) || cdMa.equals(phoPhongMa) || vanThuMa.equals(cdMa) || adminMa.equals(cdMa)
					|| thuKyMa.equals(cdMa)) {
				objectList.add(count, vaiTroList);
				count++;
				objectList.add(count, vtCongVanList);
				count++;
			}
			// session.setMaxInactiveInterval(5000);
			return JSonUtil.toJson(objectList);
		} catch (NullPointerException | NumberFormatException e) {
			logger.error("Lỗi khi phân trang công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/changeTrangThaiVt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changeTrangThai(@RequestParam("trangThai") String trangThai, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
			if (nguoiDung == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			String cdMa = authentication.getChucDanh().getCdMa();
			String nhanVienMa = context.getInitParameter("nhanVienMa");
			String thuKyMa = context.getInitParameter("thuKyMa");
			String vanThuMa = context.getInitParameter("vanThuMa");
			String phoPhongMa = context.getInitParameter("phoPhongMa");
			String[] temp = trangThai.split("\\#");
			if (cdMa.equals(nhanVienMa) || cdMa.equals(vanThuMa) || cdMa.equals(phoPhongMa)) {
				VTCongVanDAO vtCongVanDAO = new VTCongVanDAO();
				String msnv = temp[0];
				int cvId = Integer.parseInt(temp[1]);
				String vtMa = temp[2];
				String ttMa = temp[3];
				VTCongVan vtCongVan = vtCongVanDAO.getVTCongVan(msnv, cvId, vtMa);
				vtCongVan.setTrangThai(new TrangThai(ttMa));
				vtCongVanDAO.updateVTCongVan(vtCongVan);
				int check = vtCongVanDAO.checkTtCongVan(cvId);
				if (check == 1) {
					CongVanDAO congVanDAO = new CongVanDAO();
					CongVan congVan = congVanDAO.getCongVan(cvId);
					congVan.setTrangThai(new TrangThai("DaGQ"));
					congVanDAO.updateCongVan(congVan);
					congVanDAO.disconnect();
					return JSonUtil.toJson("changTtCongVan");
				}
				vtCongVanDAO.disconnect();
			}
			return JSonUtil.toJson("success");
		} catch (NullPointerException | NumberFormatException | HibernateException e) {
			logger.error("Lỗi khi thay đổi trạng thái cv công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/changeTrangThaiCv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String changeTrangThaiCv(@RequestParam("trangThai") String trangThai,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
			if (authentication == null) {
				logger.error("Không chứng thực truy cập vào công văn");
				return JSonUtil.toJson("authentication error");
			}

			String cdMa = authentication.getChucDanh().getCdMa();
			truongPhongMa = context.getInitParameter("truongPhongMa");
			vanThuMa = context.getInitParameter("vanThuMa");
			adminMa = context.getInitParameter("adminMa");
			phoPhongMa = context.getInitParameter("phoPhongMa");
			thuKyMa = context.getInitParameter("thuKyMa");
			String[] temp = trangThai.split("\\#");
			int cvId = Integer.parseInt(temp[0]);
			String ttMa = temp[1];
			if (cdMa.equals(truongPhongMa) || cdMa.equals(vanThuMa) || cdMa.equals(adminMa) || thuKyMa.equals(cdMa)
					|| phoPhongMa.equals(cdMa)) {
				CongVanDAO congVanDAO = new CongVanDAO();

				CongVan congVan = congVanDAO.getCongVan(cvId);

				congVan.setTrangThai(new TrangThai(ttMa));
				congVanDAO.updateCongVan(congVan);
				congVanDAO.disconnect();
				return JSonUtil.toJson("success");
			}
			return JSonUtil.toJson("fail");
		} catch (NullPointerException | IndexOutOfBoundsException | HibernateException e) {
			logger.error("Lỗi khi thay đổi trạng thái vai trò công văn: " + e.getMessage());
			return JSonUtil.toJson("authentication error");
		}
	}

	@RequestMapping(value = "/getDonVi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getDonVi(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
		if (authentication == null) {
			logger.error("Không chứng thực truy cập vào công văn");
			return JSonUtil.toJson("authentication error");
		}
		DonViDAO donViDAO = new DonViDAO();
		ArrayList<DonVi> donViList = (ArrayList<DonVi>) donViDAO.getAllDonVi();
		donViDAO.disconnect();
		return JSonUtil.toJson(donViList);
	}

	@RequestMapping(value = "/getMucDich", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getMucDich(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		MucDichDAO donViDAO = new MucDichDAO();
		ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) donViDAO.getAllMucDich();
		donViDAO.disconnect();
		return JSonUtil.toJson(mucDichList);
	}
}
