<%@page import="model.TrangThai"%>
<%@page import="dao.YeuCauDAO"%>
<%@page import="model.YeuCau"%>
<%@page import="dao.CongVanDAO"%>
<%@page import="util.DateUtil"%>
<%@page import="model.CongVan"%>
<%@page import="model.MucDich"%>
<%@page import="model.DonVi"%>
<%@page import="model.ChatLuong"%>
<%@page import="dao.VatTuDAO"%>
<%@page import="model.VatTu"%>
<%@page import="dao.VaiTroDAO"%>
<%@page import="dao.CTVatTuDAO"%>
<%@page import="dao.ChatLuongDAO"%>
<%@page import="model.CTVatTu"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.NoiSanXuatDAO"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
// 	nơi sản xuất
// 		ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) new NoiSanXuatDAO().getAllNoiSanXuat();
// 		System.out.println(nsxList.get(0).getNsxTen());
// 		for (NoiSanXuat nsx : nsxList) {
// 			out.println(nsx.getNsxTen());
// 		}

/* CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		VatTuDAO vatTuDAO = new VatTuDAO();
//
		ChatLuong chatLuong = new ChatLuong("CL2", "Tot");
		NoiSanXuat nsx = new NoiSanXuat("Vn2", "Viet Nam");
		VatTu vatTu = new VatTu("VT2", "Tru dien", "cai");
		CTVatTu ctVatTu = new CTVatTu(vatTu, nsx, chatLuong, 0, 0);
		
		chatLuongDAO.addChatLuong(chatLuong);
		noiSanXuatDAO.addNoiSanXuat(nsx);
		vatTuDAO.addVatTu(vatTu);
		ctVatTuDAO.addCTVatTu(new CTVatTu(vatTu, nsx, chatLuong, 0, 0));
		
		DonVi donVi = new DonVi("SX2", "Don vi 1", "0736864271", "quoi@gmail.com", "Can Tho");
		MucDich mucDich = new MucDich("SC2", "Sua chua lon");
		File file = new File("~/study/linux command", "File hoc linux command can ban", 1);
		CongVan congVan = new CongVan(1, DateUtil.convertToSqlDate(new java.util.Date()), "123", DateUtil.convertToSqlDate(new java.util.Date()), "Khong co trich yeu", "Khong co bu phe", mucDich, new TrangThai("DGQ", "Dang giai quyet"), donVi);
		new CongVanDAO().addCongVan(congVan);
		 */
// 		YeuCau yeuCau = new YeuCau(1, ctVatTu, 50, 0);
// 		new YeuCauDAO().addYeuCau(yeuCau);
// 		out.println(request.getContextPath() + "<br>");
		
		out.println(request.getPathInfo() + "<br>");
		out.println(request.getRealPath("/") + "<br>");
		out.println(request.getServletPath() + "<br>");
		out.println(request.getRequestURI() + "<br>");
		out.println(request.getRequestURI() + "<br>");
		out.println(request.getContextPath() + "<br>");
// 		System.out.println(request.get);
		//NoiSanXuatDAO chatLuongDAO = new NoiSanXuatDAO();
// 		chatLuongDAO.addChatLuong(new ChatLuong("TTT","Tốt",0));
		//NoiSanXuat cl = chatLuongDAO.getNoiSanXuat("AFC");
// 		out.println(cl.getNsxTen());
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		
		
	%>
</body>
</html>