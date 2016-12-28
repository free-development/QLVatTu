<%@page import="model.DonVi"%>
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
<link href="style/style-ctvt.css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
<script type="text/javascript" src="js/sessionManagement.js"></script>
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
	if (status != null && status.equals("formatException"))
			out.println("<script>alert('Danh sách bộ phận sử dụng bị lỗi khi import!')</script>");
	%>
	<%
	ArrayList<Object> errorList = (ArrayList<Object>) session.getAttribute("errorList");
	ArrayList<DonVi> donViError = (ArrayList<DonVi>) errorList.get(0);
	ArrayList<String> statusError = (ArrayList<String>) errorList.get(1);
// 		Long size = (Long) request.getAttribute("size");
// 		Long pageNum = size/10;
   		
    %>
	<div class="wrapper">
		<div id="main-content">
			<div id="title-content">Danh sách bộ phận sử dụng bị lỗi</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="height: 500px; margin: 0 auto; overflow: auto;" class="scroll_content">
						<table>
							<tr style="background: #199e5e">
							<th>Số TT</th>
								<th class="four-column">Mã BPSD</th>
								<th class="three-column">Tên BPSD</th>
								<th class="six-column">Địa chỉ</th>
								
								<th class="four-column">Email</th>
								<th class="six-column">Số điện thoại</th>
								<th class="four-column">Lỗi</th>
							</tr>
							<%
									if(donViError != null) {
									int count = 0;
									int i = 1;
									for(DonVi donVi : donViError) { %>
		
							<tr class="rowContent"
								<%if (count % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
								<td><%=i++ %></td>
								<td class="col"><%=donVi.getDvMa()%></td>
								<td class="col" style="text-align: left;"><%=donVi.getDvTen()%></td>
								<td class="col" style="text-align: left;"><%=donVi.getDiaChi() %></td>
								<td class="col"><%=donVi.getEmail() %></td>
								<td class="col" style="text-align: left;"><%=donVi.getSdt()%></td>
								
								
								
								<td class="col"><%=statusError.get(count) %></td>
							</tr>
							<%count++;} }%>
		
						</table>
					</div>
					<div class="group-button" style="text-align: center;">		
						<button type="button" class="button" onclick="location.href='<%=siteMap.downloadBpsdError%>'">
							<i class="fa fa-download"></i>&nbsp;&nbsp;Tải xuống
						</button>
						<button type="button" id="exitError_button" class="button">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>			
				</form>
				</div>
	</div>
</body>
</html>