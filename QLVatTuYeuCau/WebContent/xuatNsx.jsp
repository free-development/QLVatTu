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
<title>Xuất danh mục nơi sản xuất</title>
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
   			request.setAttribute("url", siteMap.bcbdnManage+ "?action=manageBcbdn");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   		}
   	%>
		<% 
    	ArrayList<NoiSanXuat> listNoiSanXuat = (ArrayList<NoiSanXuat>) session.getAttribute("allNoiSanXuatList");
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "Noisanxuat.xls");
	            
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
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.downloadExcelNsx%>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.nsxManage + "?action=manageNsx"%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>

		<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">DANH MỤC NƠI SẢN XUẤT</div>
			
		<div style="margin-right: 10px;padding-left: 800px;font-size: 17px;">Ngày in:&nbsp;<%=DateUtil.toString(new java.util.Date())%></div>
			<div id="view-table-bao-cao">
					<table  style="border: solid 1px black;width:960px;font-size: 12px;">
						<thead>
							<tr style="border: 1px solid black;">
							<th style="border: 1px solid black;font-size: 17px;width: 5px;">STT</th>
							<th style="border: 1px solid black;font-size: 17px;width: 50px;">MÃ NSX</th>
							<th style="border: 1px solid black;font-size: 17px;width: 300px;">TÊN NƠI SẢN XUẤT</th>
							</tr>
							</thead>
							<tbody>
										<%
										if(listNoiSanXuat != null) {
											int i = 1;
										for(NoiSanXuat nsx : listNoiSanXuat) { %>
												<tr style= "border-style: solid;border-color:black;" class="rowContent">
													<td style="border: 1px solid black;font-size: 17px;text-align: center"><%=i++%></td>
													<td style="border: 1px solid black;font-size: 17px;"><%=nsx.getNsxMa() %></td>
													<td style="border: 1px solid black;font-size: 17px;text-align: left"><%=nsx.getNsxTen() %></td>
												</tr>
										<%} }%>
							</tbody>
				</table>
				</div>
		</body>
		</html>