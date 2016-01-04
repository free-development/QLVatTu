<%@page import="model.NguoiDung"%>
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.swing.text.DateFormatter"%>
<%@page import="java.util.logging.SimpleFormatter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
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
<title>Xuất chi tiết vật tư</title>
<style type="text/css" media="print">
#print_button{
display:none;
}
@page 
        {
            size: auto A4 landscape;
        	color: black; background: white; }
		
</style>
</head>
<body>
	<%
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.bcvttManage+ "?action=manageBcbdn");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		}
   	%>
		<% 
    	ArrayList<CTVatTu> ctVatTuListAlert = (ArrayList<CTVatTu>) session.getAttribute("objectList");
		if (ctVatTuListAlert ==  null) {
			int index = siteMap.exportCTVatTuMn.lastIndexOf("/");
			String url = siteMap.nsxManage.substring(index);
			RequestDispatcher dispatcher =  request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			return;
		}
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Chitietvattu.xls");
	            
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
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.downloadVatTuAlert%>'">
						<i class="fa fa-download"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="exit_button" class="button"  onclick="window.close();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>
<!-- 		<table style = "margin: 0 auto;width:960px;"> -->
<!-- 		<tr> -->
<!-- 			<td style="text-align: right;font-size: 17px;width:350px;">CÔNG TY ĐIỆN LỰC TP CẦN THƠ</td> -->
<!-- 			<td style="text-align: center;font-size: 17px;">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td style="font-size: 17px; padding-left: 150px;">PHÒNG VẬT TƯ</td> -->
<!-- 			<td style="font-size: 17px; text-align: center;">Độc lập - Tự do - Hạnh phúc</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 		<td style="padding-left: 150px;">-----------------------</td> -->
<!-- 		<td style="text-align: center;">-----------------------</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 		<td></td> -->
<!-- 		<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td> -->
<!-- 		</tr> -->
<!-- 		</table> -->
		<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">DANH MỤC VẬT TƯ</div>
			
<%-- 		<div style="margin-right: 10px;padding-left: 800px;font-size: 17px;">Ngày in:&nbsp;<%=DateUtil.toString(new java.util.Date())%></div> --%>
			<div id="view-table-bao-cao">
					<table  style="border: solid 1px black;width:960px;font-size: 12px;">
					<thead>
						<tr style="border: 1px solid black;">
						<th style="border: 1px solid black;font-size: 17px;width: 5px;">STT</th>
						<th style="border: 1px solid black;font-size: 17px;width: 10px;">Mã VT</th>
						<th style="border: 1px solid black;font-size: 17px;width: 300px;">Tên vật tư</th>
						<th style="border: 1px solid black;font-size: 17px;width: 5px;">ĐVT</th>
						<th style="border: 1px solid black;font-size: 17px;width: 140px;">Nơi sản xuất</th>
						<th style="border: 1px solid black;font-size: 17px;width: 140px;">Chất lượng</th>
						<th style="border: 1px solid black;font-size: 17px;width: 80px;">Định mức</th>
						<th style="border: 1px solid black;font-size: 17px;width: 110px;">Số lượng tồn</th>
						</tr>
						</thead>
									<tbody>
											<%
										if(ctVatTuListAlert != null) {
											int i = 1;
										for(CTVatTu ctVatTu : ctVatTuListAlert) { %>
			
												<tr style= "border-style: solid;border-color:black;" class="rowContent">
													<td style="border: 1px solid black;font-size: 17px;"><%=i++%></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: center;"><%=ctVatTu.getVatTu().getVtMa() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: left"><%=ctVatTu.getVatTu().getVtTen() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: center;"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
													<td style="border: 1px solid black;font-size: 17px;width: 40px;text-align: center;"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: center;"><%=ctVatTu.getChatLuong().getClTen() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: center;"><%=ctVatTu.getDinhMuc() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: center;"><%=ctVatTu.getSoLuongTon() %></td>
												</tr>
												<%} }%>
									</tbody>
				</table>
				</div>
<!-- 				<div style="width:800px;font-size: 18px;margin: auto;"> -->
<!-- 						<table style="width:800px;font-size: 18px;;"> -->
<!-- 								<tr> -->
<!-- 								<td></td> -->
<!-- 								<td style="font-size: 17px; text-align: center;">Cần Thơ, ngày...tháng...năm...</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td style="padding-left: 50px;font-weight: bold;">Người lập biểu</td> -->
<!-- 									<td style="text-align: center;font-weight: bold;">Trưởng Phòng Vật Tư</td> -->
<!-- 								</tr> -->
<!-- 						</table> -->
<!-- 				</div> -->
		</body>
		</html>