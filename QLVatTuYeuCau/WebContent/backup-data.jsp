
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="model.BackupInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="model.NguoiDung"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link rel="stylesheet" href="style/style-backup-data.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/backup-data.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.backupDataPage);
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
		ArrayList<BackupInfo> backupList = (ArrayList<BackupInfo>) request.getAttribute("backupList");
		Long pageNumber = (Long) request.getAttribute("pageNumber");
   	%>
	<%
    		
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="content">
			<div id="content-wrapper">
				<div id="title-content">Sao lưu dữ liệu</div>
				<div id="main-content">

					<form id="main-form">
						<div id="view-table" style="margin: 0 auto;">
							<table>
								<tr style="background: #199e5e">
									<th class="head">Chọn</th>
									<th class="head">Số thứ tự</th>
									<th class="head">Thời gian</th>
									<th class="head">Mô tả</th>
								</tr>
								<%
							if(backupList != null) {
							int count = 0;
							for(BackupInfo backupInfo : backupList) { count++;%>
								<tr class="rowContent"
									<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
									
									<td class="left-column"><input type="checkbox" name="mdMa"
										value="<%=backupInfo.getStt() %>" id="id" class="checkbox"></td>
									<td class = "col"><%=count %></td>
									<td class="col"><%=backupInfo.getThoiGian() %></td>
									<td class="col"><%=backupInfo.getMoTa() %></td>
								</tr>
								<%} }%>
							</table>
						</div>

						<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
								<%
								
								if(pageNumber > 0){ %>
								<td>Trang</td>
									<td>
										<%
											
											for(int i = 0; i < pageNumber; i++) { %>
												<input type="button" value="<%=i+1%>" class="page">
										<%} }%>
									</td>
								</tr>
 							</table> 
						</div> 

						<div class="group-button">
							<button type="submit" class="button" id="preBackup">
								<i class="fa fa-plus-circle"></i>&nbsp;Sao lưu dữ liệu
							</button>
							<button type="submit" class="button">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Phục hồi dữ liệu
							</button>
							
							&nbsp;
							<button class="button" type="reset">
								<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
							</button>
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>
	
				
	
					<form id="backup-form" method="get"
						action="backupdata.jsp">
						<div class="input-table">
							<table>
								<div class="form-title">Sao lưu dữ liệu</div>
								<tr>
									<th style= "width:100px;">Thời gian:</th>
									<td><%=DateUtil.toString(new Date()) %></td>
								</tr>
								<tr>
									<th style="width:100px;">Mô tả:</th>
									<td><textarea name="moTa" class="text-area" required id = "moTa"
										autofocus 
										title="Bạn phải nhập mô tả trước khi sao lưu dữ liệu"></textarea><div id="requireMdMa" style="color: red"></div></td>
								</tr>
								
							</table>
						</div>
						<div class="group-button">
							<!-- 						<input type="hidden" name="action" value = "AddMd">  -->
							<button class="button" type="submit" id="backupData">
								<i class="fa fa-plus-circle"></i>&nbsp;Sao lưu
							</button>
							<button type="reset" class="button">
								<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
							</button>
							&nbsp;
						<button type="button" class="button" id="exit">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
							
						</div>
					</form>
				</div>
			</div>
</div>
		</div>
</body>
</html>