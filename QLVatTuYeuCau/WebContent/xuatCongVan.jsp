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
<%@page import="model.File"%>
<%@page import="model.MucDich"%>
<%@page import="model.VaiTro"%>
<%@page import="model.VTCongVan"%>
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
<title>Xuất công văn</title>

<style type="text/css" media="print">
#header{
page-break-after: always;
}
#print_button{
display:none;
}
.button{
display:none;
}
#footer {
bottom: 0;
position: relative;
left: 46%;
}

@page { size : landscape; }
@page rotated { size : landscape }
           	
table 
{ 
	font-size: 100%;
	 page : landscape;
 }
#print-footer{
	display: block;
    color:red; 
    font-family:Arial; 
    font-size: 16px; 
    text-transform: uppercase; 
    
}	   		
thead {display: table-header-group;}
</style>

</head>
<body>
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		} else {
   	%>
		<% 
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("objectList");
	       %>
	     <% 
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Baocaocongvan.xls");
	            
	        }
		%>
		<div class="group-button"style="position: fixed; right: 10px;">
					<%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In báo cáo
					</button>
					&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.exportCongVan + "?exportToExel=YES" %>'">
						<i class="fa fa-download"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;
					<button type="button" id="exit_button"  class="button"  onclick="window.close();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<%}%>
				</div>
		<table style = "margin: 0 auto;width:960px;">
		<tr>
			<td style="text-align: center;font-size: 17px; width: 50%;" colspan="5">CÔNG TY ĐIỆN LỰC TP CẦN THƠ</td>
			<td style="text-align: center;font-size: 17px; width: 50%;" colspan="4">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</td>
		</tr>
		<tr>
			<td style="font-size: 17px; text-align: center;" colspan="5">PHÒNG VẬT TƯ</td>
			<td style="font-size: 17px; text-align: center;" colspan="4">Độc lập - Tự do - Hạnh phúc</td>
		</tr>
		<tr>
		<td style="text-align: center;" colspan="5">-----------------------</td>
		<td style="text-align: center;" colspan="4">-----------------------</td>
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 		<td></td> -->
<!-- 		<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td> -->
<!-- 		<td></td> -->
		</tr>
		</table>
		<br>
		<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">Báo cáo công văn</div>
		<div style="margin-right: 10px;padding-left: 750px;font-size: 17px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp;  <%=DateUtil.toString(new java.util.Date())%></div>
			
			
			
			<div id="view-table-bao-cao">
					<table style="text-align: center;margin: 0 auto; color: black;border: solid 1px black;width:1224px;">
					<thead>
						<tr bgcolor="#199e5e"  style= "border-style: solid;border-color:black;" >
<!-- 							<th style="border: 1px solid black;font-size: 17px;width: 50px;" >Số đến</th> -->
							<th style="border: 1px solid black;font-size: 17px;width: 50px; width: 50px;">Số P.VT</th>
							
							<th style="border: 1px solid black;font-size: 17px;width: 50px;">Ngày P.VT nhận</th>
							<th style="border: 1px solid black;font-size: 17px;width: 50px;">Số công văn đến</th>
<!-- 							<th style="border: 1px solid black;font-size: 17px;width: 50px;">Số công văn đến</th> -->
							
							<th style="border: 1px solid black;font-size: 17px;width: 50px;">Ngày công văn đến</th>
							<th style="border: 1px solid black;font-size: 17px;width: 150px;">Mục đích</th>
							<th style="border: 1px solid black;font-size: 17px;width: 200px;">Nơi gửi</th>
							<th style="border: 1px solid black;font-size: 17px;width: 300px;">Nôi dung công tác</th>
							<th style="border: 1px solid black;font-size: 17px;width: 200px;">Bút phê</th>
							<th style="border: 1px solid black;font-size: 17px;width: 100px;">Trạng thái</th>
							
						</tr>
					</thead>
					<tbody>
								<%
					
			                     	int cnt = 0;
			                     	for(CongVan congVan : congVanList) {
			                     		cnt ++;
			                     %>
								<tr 
									<%if (cnt % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>
									style= "border-style: solid;border-color:black black black black;">
<%-- 									<td style="border: 1px solid black;font-size: 17px;"><%=congVan.getSoDen() %></td> --%>
									<td style="border: 1px solid black;font-size: 17px;" class="a-column"><%=congVan.getSoDen() + "/" + (congVan.getCvNgayNhan().getYear() + 1900)%></td>
									
									<td style="border: 1px solid black;font-size: 17px;" ><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
									<td style="border: 1px solid black;font-size: 17px;" ><%=congVan.getCvSo() %></td>
<%-- 									<td style="border: 1px solid black;font-size: 17px;" ><%=congVan.getCvSo() %></td> --%>
									
									<td style="border: 1px solid black;font-size: 17px;" ><%=DateUtil.toString(congVan.getCvNgayDi()) %></td>
									<td style="border: 1px solid black;font-size: 17px;text-align: left;" ><%=congVan.getMucDich().getMdTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;text-align: left;"><%=congVan.getDonVi().getDvTen() %></td>
									<td style="border: 1px solid black;font-size: 17px;text-align: left;" class="b-column"><%=congVan.getTrichYeu() %></td>
									<td style="border: 1px solid black;font-size: 17px;text-align: left;" class="b-column"><%=congVan.getButPhe() %></td>
									<td style="border: 1px solid black;font-size: 17px;text-align: left;" class="b-column"><%=congVan.getTrangThai().getTtTen() %></td>
 										
								</tr>
							</tbody>
							<%} %>
				</table>
				</div>
				<br>
				<br>
				<br>
				<div style="width:800px;font-size: 18px;margin: auto;">
						<table style="width:800px;font-size: 18px;">
								<tr>
									<td style="padding-left: 50px;font-weight: bold;">Người lập biểu</td>
									<td style="text-align: center;font-weight: bold;">Trưởng Phòng Vật Tư</td>
								</tr>
						</table>
				</div>	
				<div id="footer">Báo cáo công văn</div>		
		</body>
		<%} %>
		</html>