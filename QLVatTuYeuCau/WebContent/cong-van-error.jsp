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
</head>
<body>
	<%
	ArrayList<Object> errorList = (ArrayList<Object>) session.getAttribute("errorList");
	ArrayList<String> vtMaError = (ArrayList<String>) errorList.get(0);
	ArrayList<String> nsxMaError = (ArrayList<String>) errorList.get(1);
	ArrayList<String> clMaError = (ArrayList<String>) errorList.get(2);
	ArrayList<Integer> soLuongError = (ArrayList<Integer>) errorList.get(3);
	ArrayList<String> statusError = (ArrayList<String>) errorList.get(4);
    %>
	<div class="wrapper">
		<div id="main-content">
			<div id="title-content">Danh sách vât tư thiếu bị lỗi</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="height: 500px; margin: 0 auto; overflow: auto;" class="scroll_content">
						<table>
							<tr style="background: #199e5e">
							<th style="width: 50px;">Số TT</th>
								<th style="width: 200px;">Mã vật tư</th>
								<th style="width: 150px;">Mã nơi sản xuất</th>
								<th style="width: 150px;">Mã chất lượng</th>
								<th style="width: 150px;">Số lượng thiếu</th>
								<th style="width: 300px;">Lỗi</th>
							</tr>
							<%
									if(vtMaError != null) {
									int count = 0;
									int i = 1;
									for(String vtMa : vtMaError) { %>
		
							<tr class="rowContent"
								<%if (count % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
								<td><%=i++ %></td>
								<td style="text-align: center;"><%=vtMa%></td>
								<td style="text-align: center;"><%=nsxMaError.get(count)%></td>
								<td style="text-align: center;"><%=clMaError.get(count) %></td>
								<td style="text-align: center;"><%=soLuongError.get(count)%></td>
								<td style="text-align: center;"><%=statusError.get(count) %></td>
							</tr>
							<%count++;} }%>
		
						</table>
					</div>
						
					<div class="group-button" style="text-align: center;">		
						<button type="button" class="button" onclick="location.href='<%=siteMap.downloadCvError%>'">
							<i class="fa fa-download"></i>&nbsp;&nbsp;Tải xuống
						</button>
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					
				</div>			
				</form>
				</div>
	</div>
</body>
</html>