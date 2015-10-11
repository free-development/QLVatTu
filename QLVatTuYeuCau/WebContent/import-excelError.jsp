<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import Error</title>
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-ctvt.css"css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
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
		String status = (String) request.getAttribute("status");
	if (status != null && status.equals("formatException"))
			out.println("<script>alert('Danh sách chi tiết vật tư bị lỗi khi thêm!')</script>");
	%>
	<%
	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) session.getAttribute("ctvtListError");
	ArrayList<String> statusError = (ArrayList<String>) session.getAttribute("statusError");
   		
		String exportToExcel = request.getParameter("exportToExel");
	        response.setCharacterEncoding("UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "inline; filename=" + "vatTuLoi.xls");
	            
	        }
		%>
		<div class="group-button" style="position: fixed; right: 10px;">
					<%
        				if (exportToExcel == null) {
   				 	 %>
   				 	 <button class="button" id="print_button" type="button" onclick="window.print();">
						<i class="fa fa-print"></i>&nbsp;&nbsp;In
					</button>
					&nbsp;&nbsp;
					<button class="button" id="print_button" type="button" onclick="location.href='<%=siteMap.downloadExcelError%>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Tải file
					</button>
					&nbsp;&nbsp;
					<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.vattuManage + "?action=manageVattu"%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					<% } %>
					 
				</div>
<div style="text-align: center;font-size: 30px;font-weight: bold;color: solid black;margin-top:20px;">Danh sách vật tư không thêm được</div>
					<div id="view-table-chi-tiet" style="height: 500px; margin: 0 auto; overflow: auto;" class="scroll_content">
						<table>
						<thead>
							<tr style="background: #199e5e">
							<th>Số TT</th>
								<th class="four-column">Mã vật tư</th>
								<th class="three-column">Tên vật tư</th>
								<th class="four-column">Đơn vị tính</th>
								<th class="six-column">Mã nơi sản xuất</th>
								<th class="six-column">Mã chất lượng</th>
								<th class="four-column">Lỗi</th>
							</tr>
							</thead>
							<tbody>
							<%
									if(listCTVatTu != null) {
									int count = 0;
									int i = 1;
									for(CTVatTu ctVatTu : listCTVatTu) { count++;%>
		
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td><%=i++ %></td>
								<td class="col"><%=ctVatTu.getVatTu().getVtMa()%></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getVatTu().getVtTen()%></td>
								<td class="col"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxMa() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getChatLuong().getClMa()%></td>
								<td class="col"><%=statusError.get(count - 1) %></td>
							</tr>
							<%} }%>
		</tbody> 	
						</table>
					</div>		
</body>
</html>