<%@page import="model.NguoiDung"%>
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
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
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/sessionManagement.js"></script>
<title>Xuất báo cáo</title>
<style type="text/css" media="print">
#print_button{
display:none;
}
.button{
display:none;
}
@page 
        {
            {
            size: auto A4 landscape;
        	color: black; background: white; 
        	font-family: "Times New Roman";
        	
        	
        	} }
	   table 
	   { 
/* 	   		font-size: 70%;  */
	   			 }
</style>
</head>
<body style="font-family: \"Times New Roman\"">
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.bcvttManage+ "?action=manageBcbdn");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		}
   	%>
		<%
		java.sql.Date ngaybd = (java.sql.Date)session.getAttribute("ngaybd");
		java.sql.Date ngaykt = (java.sql.Date)session.getAttribute("ngaykt");
			String loaiBc = (String) session.getAttribute("action"); 
	        String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Baocaovattuthieu.xls");
	            
	        }
		%>
	
					
				<%
			
				ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) session.getAttribute("ctVatTuList");
		   		ArrayList<Long> soLuongList = (ArrayList<Long>) session.getAttribute("soLuongList");
	   		%>
	   						 <%
        			if (exportToExcel == null) {
   				 %>
   				 <div class="group-button" style="position: fixed; right: 10px;">
				<button class="button" type="button" id="print_button" onclick="window.print()">
					<i class="fa fa-print"></i>&nbsp;&nbsp;In báo cáo
				</button>
				&nbsp;
<!--     <a href="excel.jsp?exportToExcel=YES">Export to Excel</a> -->

				<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatFile+".jsp"+ "?exportToExel=YES" %>'">
					<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
				</button>
				    
				&nbsp;
				<button type="button" id="exit_button" class="button" >
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
				<%}%>
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
		
		</table>
		<br>
	   		<div style="text-align: center;font-size: 20px;font-weight: bold;color: #199e5e;">Báo cáo tổng hợp vật tư thiếu</div>
		<% if((ngaybd!=null)&&(ngaykt!=null)){%>
		<div style="text-align: center;">Từ ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaybd)%>&nbsp;&nbsp;đến ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaykt)%></div>
		<% }%>
		<div style="margin-right: 10px;padding-left: 800px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp; <%=DateUtil.toString(new java.util.Date())%></div>
			<div id="view-table-bao-cao" >
				<table style="border: solid 1px black;width:1224px;">
					<tr bgcolor="#199e5e"  style= "border-style: solid;border-color:black;">
						<th style="border: 1px solid black;width: 50px; text-align: center;" class="three-column" style="text-align: center;">Mã vật tư</th>
						<th style="border: 1px solid black;width: 450px; text-align: center;" class="two-column">Tên vật tư</th>
						<th style="border: 1px solid black;width: 100px; text-align: center;" class="three-column">Nơi sản xuất</th>
						<th style="border: 1px solid black;width: 100px; text-align: center;" class="three-column">Chất lượng</th>
						<th style="border: 1px solid black;width: 50px;text-align: center;" class="six-column">Đvt</th>
						<th style="border: 1px solid black;width: 50px; text-align: center;" class="one-column">Tổng số lượng thiếu</th>	
						<th style="border: 1px solid black;width: 50px; text-align: center;" class="one-column">Số lượng tồn</th>
					</tr>
								<%
								int count = 0;
							if(ctVatTuList != null){
							for(CTVatTu ctVatTu  : ctVatTuList) { 
								CTVatTu ctvt = ctVatTuList.get(count);
								long soLuong = soLuongList.get(count);
// 							for (YeuCau yeuCau : yeuCauList) {
// 							CongVan congVan = congVanList.get(count); 	
							%>
									
					<tr <%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> 
					style= "border-style: solid;border-color:black black black black;">
						<td style="border: 1px solid black;text-align: center;" class="a-column"><%=ctvt.getVatTu().getVtMa() %></td>
						<td style="border: 1px solid black;" class="b-column"><%=ctvt.getVatTu().getVtTen() %></td>
						<td style="border: 1px solid black;" class="c-column"><%=ctvt.getNoiSanXuat().getNsxTen() %></td>
						<td style="border: 1px solid black;" class="d-column"><%=ctvt.getChatLuong().getClTen() %></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=ctvt.getVatTu().getDvt().getDvtTen() %></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=soLuong %></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=ctvt.getSoLuongTon()%></td>
						
					</tr>
					<%count++;} %>
				</table>
			</div>		
			<br>
				<br>
				<br>
				<div style="width:800px;font-size: 18px;margin: auto;">
						<table style="width:800px;font-size: 18px;;">
<!-- 								<tr> -->
<!-- 								<td></td> -->
<!-- 								<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td> -->
<!-- 								</tr> -->
								<tr>
									<td style="padding-left: 50px;font-weight: bold;">Người lập biểu</td>
									<td style="text-align: center;font-weight: bold;">Trưởng Phòng Vật Tư</td>
								</tr>	
						</table>
				</div>
<%}%>
<%-- 				<% if(exportToExcel != null) --%>
<%-- 					response.sendRedirect("xuatFile.jsp");%> --%>
</body>
</html>