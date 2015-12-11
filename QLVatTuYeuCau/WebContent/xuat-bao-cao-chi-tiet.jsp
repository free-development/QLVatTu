
<%@page import="model.NguoiDung"%>
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.swing.text.DateFormatter"%>
<%@page import="java.util.logging.SimpleFormatter"%>
<%@page import="model.YeuCau"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="model.CongVan"%>
<%@page import="model.CongVan"%>
<%@page import="model.DonVi"%>
<%@page import="model.TrangThai"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-bao-cao-vat-tu-thieu.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<meta charset="utf-8">
<title>Xuất bảng đề nghị</title>
<style type="text/css" media="print">
#print_button{
display:none;
}

@page 
        { 
            size: auto A4 landscape;
        	color: black; background: white; }
	   table 
	   { 
	   		font-size: 70%; 
	   			 }
</style>
</head>
<body>
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.bccvManage+ "?action=manageBcbdn");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		}
   	%>
		<% 
		java.sql.Date ngaybd = (java.sql.Date)session.getAttribute("ngaybd");
		java.sql.Date ngaykt = (java.sql.Date)session.getAttribute("ngaykt");
// 		Date dbd = new SimpleDateFormat ("dd-MM-yyyy").parse(ngaybd);
// 		Date dkt = new SimpleDateFormat ("dd-MM-yyyy").parse(ngaykt);
// 		Date dht = new SimpleDateFormat ("dd-MM-yyyy").parse(new Date().toString());
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");
		
		
		if (congVanList ==  null) {
			int index = siteMap.baoCaoChiTiet.lastIndexOf("/");
			String url = siteMap.cvManage.substring(index);
			RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageBcbdn");
			dispatcher.forward(request, response);
			return;
		}
		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("yeuCauHash");
		HashMap<Integer, ArrayList<CTVatTu>> ctVatTuHash = (HashMap<Integer, ArrayList<CTVatTu>>) session.getAttribute("ctVatTuHash");
	       %>
	     <% 
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Baocaobangdenghi.xls");
	            
	        }
		%>
		<div class="group-button" style="position: fixed; right: 10px;">
					<%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In báo cáo
					</button>
					&nbsp;&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatBcChiTiet+".jsp"+ "?exportToExel=YES" %>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.baoCaoChiTiet+".jsp" %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>
		<table style = "margin: 0 auto;width:960px;">
		<tr>
			<td style="text-align: right;font-size: 17px;width:350px;">CÔNG TY ĐIỆN LỰC TP CẦN THƠ</td>
			<td style="text-align: center;font-size: 17px;">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</td>
		</tr>
		<tr>
			<td style="font-size: 17px; padding-left: 150px;">PHÒNG VẬT TƯ</td>
			<td style="font-size: 17px; text-align: center;">Độc lập - Tự do - Hạnh phúc</td>
		</tr>
		<tr>
		<td style="padding-left: 150px;">-----------------------</td>
		<td style="text-align: center;">-----------------------</td>
		</tr>
		<tr>
		<td></td>
		<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td>
		</tr>
		</table>
		<br>
		<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">Báo cáo bảng đề nghị cấp vật tư</div>
		<% if((ngaybd!=null)&&(ngaykt!=null)){%>
			
			<div style="text-align: center;font-size: 17px;">Từ ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaybd)%>&nbsp;&nbsp;đến ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaykt)%></div>
			
			<% }%>
		<div style="margin-right: 10px;padding-left: 750px;font-size: 17px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp;  <%=DateUtil.toString(new java.util.Date())%></div>
			<div id="view-table-bao-cao">
					<table style="text-align: center;margin: 0 auto; color: black;border: solid 1px black;">
					<thead>
						<tr bgcolor="#199e5e"  style= "border-style: solid;border-color:black;">
<!-- 							<th style="border: 1px solid black;font-size: 17px;width: 50px;" class="a-column">Số đến</th> -->
							<th style="width: 50px;">Ngày nhận</th>
							<th style="width: 50px;">Mã vật tư</th>
							<th style="width: 350px;">Tên vật tư</th>
							<th style="width: 150px;">Nơi sản xuất</th>
							<th style="width: 150px;">Chất lượng</th>
							<th style="width: 50px;">Đvt</th>
							<th style="width: 50px;">Số lượng</th>
							<th style="width: 250px;">Trạng thái</th>
							<th style="width: 400px;">Đơn vị yêu cầu</th>
							<th style="width: 400px;">Nội dung công tác</th>
							
						</tr>
						</thead>
								<tbody>
								<%
								if(yeuCauHash != null) {
									 int cnt = 0;
									for(CongVan congVan  : congVanList) { 
									ArrayList<YeuCau> yeuCauList = yeuCauHash.get(congVan.getCvId());
									ArrayList<CTVatTu> ctVatTuList = ctVatTuHash.get(congVan.getCvId());
									int i = 0;
									for (YeuCau yeuCau : yeuCauList) {
										CTVatTu ctVatTu = ctVatTuList.get(i);
										i++;
									%>
								<tr 
									<%if (cnt % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>
									style= "border-style: solid;border-color:black black black black;">
<%-- 								<td style="width: 50px; text-align: center;"><%=congVan.getSoDen() %></td> --%>
									<td style="text-align: center;"><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
			<%-- 						<td style="width: 50px; text-align: center;"><%=congVan.getSoDen() %></td> --%>
			<%-- 						<td style="width: 100px; text-align: center;"><%=congVan.getCvNgayNhan() %></td> --%>
									<td style="text-align: center;"><%=ctVatTu.getVatTu().getVtMa() %></td>
									<td style="text-align: left;"><%=ctVatTu.getVatTu().getVtTen() %></td>
									<td style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
									<td style="text-align: left;"><%=ctVatTu.getChatLuong().getClTen() %></td>
			<%-- 						<td style="text-align: left; width: 100px;"><%=yeuCau.getCtVatTu().getNoiSanXuat().getNsxTen() %></td> --%>
									<td style="text-align: center;"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
									<td style="text-align: center;"><%=yeuCau.getYcSoLuong() %></td>
									<td style="text-align: left;"><%=congVan.getTrangThai().getTtTen() %></td>
									<td style="text-align: left;"><%=congVan.getDonVi().getDvTen()%></td>
									<td style="text-align: left;"><%=congVan.getTrichYeu()%></td>
	
								</tr>
									<%cnt++;}} %>
							</tbody>
							<%} %>
				</table>
				</div>
				<br>
				<br>
				<br>
				<div style="width:800px;font-size: 18px;margin: auto;">
						<table style="width:800px;font-size: 18px;;">
								<tr>
								<td></td>
								<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td>
								</tr>
								<tr>
									<td style="padding-left: 50px;font-weight: bold;">Người lập biểu</td>
									<td style="text-align: center;font-weight: bold;">Trưởng Phòng Vật Tư</td>
								</tr>
						</table>
				</div>
		</body>
		</html>