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
<title>Xuất báo cáo</title>
<style type="text/css" media="print">
#print_button{
display:none;
}
@page 
        {
            {
            size: auto A4 landscape;
        	color: black; background: white; } }
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
   			request.setAttribute("url", siteMap.bcbdnManage+ "?action=manageBcbdn");
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
		<% if("chitiet".equalsIgnoreCase(loaiBc)){
			
				ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");			
		   		HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("yeuCau");
		   		HashMap<Integer, ArrayList<YeuCau>> ctvtList = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("ctvtList");
		   		
		    %>
		    				<div class="group-button" style="position: fixed; right: 10px;">
					
					 <%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In
					</button>
					&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatFile+".jsp"+ "?exportToExel=YES" %>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
					</button>
					
					&nbsp;
					<button type="button" id="print_button" class="button" onclick="location.href='<%=siteMap.baoCaoVatTuThieu+".jsp" %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			
					<%} %>
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
			<div style="text-align: center;font-size: 30px;font-weight: bold;color: #199e5e;">Báo cáo chi tiết vật tư thiếu</div>
		<% if((ngaybd!=null)&&(ngaykt!=null)){%>
		<div style="text-align: center;">Từ ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaybd)%>&nbsp;&nbsp;đến ngày:&nbsp;&nbsp;<%=DateUtil.toString(ngaykt)%></div>
		<% }%>
		<div style="margin-right: 10px;padding-left: 750px;">Ngày in:&nbsp;&nbsp;&nbsp;&nbsp; <%=DateUtil.toString(new java.util.Date())%></div>
				<div id="view-table-bao-cao" >
					<table  style="border: solid 1px black;width:1224px;">
						<thead >
							<tr bgcolor="#199e5e" style="border: 1px solid black;" >
<!-- 								<th style="border: 1px solid black;width: 50px;" class="one-column">Số đến</th> -->
								<th style="border: 1px solid black;width: 100px;" class="one-column">Số công văn</th>
								<th style="border: 1px solid black;width: 100px;" class="three-column">Ngày nhận</th>
								<th style="border: 1px solid black;width: 100px;" class="three-column" style="text-align: center;">Mã vật tư</th>
								<th style="border: 1px solid black;width: 350px;" class="two-column">Tên vật tư</th>
								<th style="border: 1px solid black;width: 100px;" class="three-column">Nơi sản xuất</th>
								<th style="border: 1px solid black;width: 100px;" class="three-column">Chất lượng</th>
								<th style="border: 1px solid black;width: 250px;" class="three-column">Đơn vị</th>
<!-- 								<th style="border: 1px solid black;width: 50px;" class="three-column">Đơn vị tính</th> -->
								<th style="border: 1px solid black;width: 50px;" class="three-column">Đvt</th>
								<th style="border: 1px solid black;width: 50px;" class="one-column">Số lượng thiếu</th>
								<th style="border: 1px solid black;width: 50px;" class="one-column">Số lượng tồn</th>
								
							</tr>
						</thead>
						
									<% 								
										if((yeuCauHash != null) && "chitiet".equalsIgnoreCase(loaiBc)){
										int count = 0;
										
									%>
							<tbody>
									<%
										for(CongVan congVan  : congVanList) { 
										ArrayList<YeuCau> yeuCauList = yeuCauHash.get(congVan.getCvId());
										for (YeuCau yeuCau : yeuCauList) {count++;
									%>
												
									<tr style= "border-style: solid;border-color:black;"
										<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
<%-- 										<td style="border: 1px solid black;text-align: center;" class="a-column"><%=congVan.getSoDen() %></td> --%>
										<td style="border: 1px solid black;text-align: center;" class="a-column"><%=congVan.getCvSo() %></td>
										<td style="border: 1px solid black;text-align: center;" class="b-column"><%=congVan.getCvNgayNhan() %></td>
										<td style="border: 1px solid black;text-align: center;" class="a-column"><%=yeuCau.getCtVatTu().getVatTu().getVtMa() %></td>
										<td style="border: 1px solid black;" class="b-column"><%=yeuCau.getCtVatTu().getVatTu().getVtTen() %></td>
										<td style="border: 1px solid black;" class="c-column"><%=yeuCau.getCtVatTu().getNoiSanXuat().getNsxTen() %></td>
										<td style="border: 1px solid black;" class="d-column"><%=yeuCau.getCtVatTu().getChatLuong().getClTen() %></td>
										<td style="border: 1px solid black;" class="d-column"><%=congVan.getDonVi().getDvTen()%></td>
										<td style="border: 1px solid black;text-align: center;" class="e-column"><%=yeuCau.getCtVatTu().getVatTu().getDvt().getDvtTen() %></td>
										<td style="border: 1px solid black;text-align: center;" class="e-column"><%=yeuCau.getYcSoLuong() %></td>
										<td style="border: 1px solid black;text-align: center;" class="e-column"><%=yeuCau.getCtVatTu().getSoLuongTon() %></td>
									</tr>	
								<%}} %>
						</tbody>
						<%}} %>
					</table>
				</div>
				<br>
				<br>
				<br>
				<div style="width:800px;font-size: 18px;margin: auto;">
						<table style="width:800px;font-size: 18px;;">
								<table style="width:960px;font-size: 18px;;">
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

					
				<% if("tonghop".equalsIgnoreCase(loaiBc)){
			
	   		HashMap<Integer, CTVatTu> ctvtHash = (HashMap<Integer, CTVatTu>) session.getAttribute("ctvtHash");
	   		HashMap<Integer, Integer> yeuCauHash = (HashMap<Integer, Integer>) session.getAttribute("yeuCau");
	   		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");
	   		HashMap<Integer, ArrayList<Integer>> cvIdHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("cvIdHash");
	   		HashMap<Integer, ArrayList<Integer>> soDenHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("soDenHash");
	   		%>
	   						 <%
        			if (exportToExcel == null) {
   				 %>
   				 <div class="group-button" style="position: fixed; right: 10px;">
				<button class="button" type="button" id="print_button" onclick="window.print()">
					<i class="fa fa-print"></i>&nbsp;&nbsp;In
				</button>
				&nbsp;
<!--     <a href="excel.jsp?exportToExcel=YES">Export to Excel</a> -->

				<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.xuatFile+".jsp"+ "?exportToExel=YES" %>'">
					<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
				</button>
				    
				&nbsp;
				<button type="button" id="print_button" class="button" onclick="location.href='<%=siteMap.baoCaoVatTuThieu+".jsp" %>'">
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
		<tr>
		<td></td>
		<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td>
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
						<th style="border: 1px solid black;width: 50px;" class="three-column" style="text-align: center;">Mã vật tư</th>
						<th style="border: 1px solid black;width: 450px;" class="two-column">Tên vật tư</th>
						<th style="border: 1px solid black;width: 100px;" class="three-column">Nơi sản xuất</th>
						<th style="border: 1px solid black;width: 100px;" class="three-column">Chất lượng</th>
						<th style="border: 1px solid black;width: 350px;" class="three-column">Đơn vị</th>
						<th style="border: 1px solid black;width: 50px;" class="six-column">Đvt</th>
						<th style="border: 1px solid black;width: 50px;" class="one-column">Tổng số lượng thiếu</th>	
						<th style="border: 1px solid black;width: 50px;" class="one-column">Số lượng tồn</th>
						<th style="border: 1px solid black;width: 150px;" class="one-column">Công văn liên quan (số đến)</th>				
					</tr>
								<%
								int count = 0;
							if(yeuCauHash != null){
							for(Integer key  : yeuCauHash.keySet()) { count++;
							CTVatTu ctvt = ctvtHash.get(key);
// 							for (YeuCau yeuCau : yeuCauList) {
							CongVan congVan = congVanList.get(count); 	
							%>
									
					<tr <%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> 
					style= "border-style: solid;border-color:black black black black;">
						<td style="border: 1px solid black;text-align: center;" class="a-column"><%=ctvt.getVatTu().getVtMa() %></td>
						<td style="border: 1px solid black;" class="b-column"><%=ctvt.getVatTu().getVtTen() %></td>
						<td style="border: 1px solid black;" class="c-column"><%=ctvt.getNoiSanXuat().getNsxTen() %></td>
						<td style="border: 1px solid black;" class="d-column"><%=ctvt.getChatLuong().getClTen() %></td>
						<td style="border: 1px solid black;" class="d-column"><%=congVan.getDonVi().getDvTen()%></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=ctvt.getVatTu().getDvt().getDvtTen() %></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=yeuCauHash.get(key) %></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column"><%=ctvt.getSoLuongTon()%></td>
						<td style="border: 1px solid black;text-align: center;" class="e-column">
							<%
								ArrayList<Integer> cvIdList = cvIdHash.get(key);
								ArrayList<Integer> soDenList = soDenHash.get(key);
								int length = soDenList.size();
								StringBuilder cell = new StringBuilder ("");
								for(int i = 0; i < cvIdList.size(); i++) {
									int soDen = soDenList.get(i);
									int cvId = cvIdList.get(i);
									cell.append("<a style=\"color: red; text-decoration: underline; \" href=" + siteMap.searchCongVan + "?congVan=" + cvId + "> " + soDen + "</a>" + ", ");
								}
								int len = cell.length();
								cell.delete(len - 2, len);
								out.println(cell);
							%>
							 
						</td>
					</tr>
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
<%}}%>
<%-- 				<% if(exportToExcel != null) --%>
<%-- 					response.sendRedirect("xuatFile.jsp");%> --%>
</body>
</html>