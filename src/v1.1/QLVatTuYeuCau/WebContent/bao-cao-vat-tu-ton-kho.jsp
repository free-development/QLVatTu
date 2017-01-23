<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import Error</title>
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-ctvt.css"css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
	if (status != null && status.equals("formatException"))
			out.println("<script>alert('Danh sách vật tư bị lỗi khi import!')</script>");
	%>
	<%
	ArrayList<Object> objectListError = (ArrayList<Object>) session.getAttribute("objectListError");
	ArrayList<String> vtMaError = (ArrayList<String>) objectListError.get(0);
	ArrayList<String> vtTenError = (ArrayList<String>) objectListError.get(1);
	ArrayList<String> dvtTenError = (ArrayList<String>) objectListError.get(2);
	ArrayList<String> nsxTenError = (ArrayList<String>) objectListError.get(3);
	ArrayList<String> clTenError = (ArrayList<String>) objectListError.get(4);
	ArrayList<Integer> soLuongError = (ArrayList<Integer>) objectListError.get(5);
    %>
	<div class="wrapper">
		<div id="main-content">
			<div id="title-content">Danh sách chi tiết vật tư bị lỗi</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="height: 500px; margin: 0 auto; overflow: auto;" class="scroll_content">
						<table>
							<tr style="background: #199e5e">
							<th style="width: 50px;">Số TT</th>
								<th style="wi150: 150px;">Mã vật tư</th>
								<th style="width: 400px;">Tên vật tư</th>
								<th style="width: 100px;">Đơn vị tính</th>
								<th style="width: 100px;">Mã nơi sản xuất</th>
								<th style="width: 100px;">Mã chất lượng</th>
								<th style="width: 100px;">Số lượng</th>
							</tr>
							<%
									if(vtMaError != null) {
									int count = 0;
									for(String vtMa : vtMaError) {%>
		
							<tr class="rowContent"
								<%if (count % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
								<td><%=count + 1 %></td>
								<td class="col"><%=vtMa%></td>
								<td class="col" style="text-align: left;"><%=vtTenError.get(count)%></td>
								<td class="col" style="text-align: left;"><%=dvtTenError.get(count) %></td>
								<td class="col" style="text-align: left;"><%=nsxTenError.get(count)%></td>
								<td class="col"><%=clTenError.get(count) %></td>
								<td class="col"><%=soLuongError.get(count) %></td>
							</tr>
							<%count ++;} }%>
		
						</table>
					</div>
						<div class="group-button" style="text-align: center;">		
						<button type="button" class="button" onclick="location.href='<%=siteMap.downloadExcelTon%>'">
							<i class="fa fa-download"></i>&nbsp;&nbsp;Tải xuống
						</button>
						&nbsp;
						<button type="button" class="button" onclick="location.href='<%=siteMap.vatTuTonKho + ".jsp"%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>			
				</form>
				</div>
	</div>
</body>
</html>