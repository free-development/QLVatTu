
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
<link rel="stylesheet" href="style/loading.css" type="text/css">
<link rel="stylesheet" href="style/style-backup-data.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/date-util.js"></script>
<script type="text/javascript" src="js/backup-data.js"></script>
<script type="text/javascript">
	var currentDate = '<%=DateUtil.convertToSqlDate(new Date()) %>';
</script>
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
		Integer pageNumber = (Integer) request.getAttribute("pageNumber");
   	%>
	<%
    		
    	%>
	<div class="wrapper" id="wrapper">
		<jsp:include page="header.jsp" />
		
		<div id="content">
			<div id="content-wrapper">
				<div id="title-content" style="margin-bottom: 20px;">Sao lưu dữ liệu</div>
				<div id="main-content">
					<div id="loading"></div>
					<form id="option-form" method="get" action ="Timkiemsaoluu.jsp">
						<table style="margin:0 auto; margin-top: 30px;">
							<tr>
								<td style="font-weight: bold;">Tìm kiếm &nbsp</td>
								<td style="text-align: left">
								<select 
									title="Tùy chọn tìm kiếm" class="select" id="filter" name="filter" style="font-size: 20px;" >
										<option value="all" style = "padding: 0 auto;" selected>-- Tất cả--</option>
										<option value="description" style = "padding: 0 auto;">Mô tả</option>
										<option value="date" style = "padding: 0 auto;">Thời gian</option>
								</select>
								</td>
								
								<td style="text-align: left; margin-left: 10px;" id="searchContent">&nbsp;&nbsp;<input style="font-size: 20px; background: #D1D1E0;" readonly type="search" id="value1" class = "text" title="Nhập mô tả" placeholder="Nội dung tìm kiếm"></td>
							</tr>
						</table>
					</form>
					<form id="main-form" style="text-align: center;" method="get">
						<div id="view-table">
							<table>
								<tr style="background: #199e5e;">
									<th class="head" style="width: 100px;">Chọn</th>
									
									<th class="head" style="width: 200px;">Thời gian</th>
									<th class="head" style="width: 70%;">Mô tả</th>
								</tr>
								<%
							if(backupList != null) {
							int count = 0;
							for(BackupInfo backupInfo : backupList) { count++;%>
								<tr class="rowContent"
									<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
									
									<td class="left-column"><input type="checkbox" name="id"
										value="<%=backupInfo.getStt() %>" id="id" class="checkbox"></td>
									
									<td class="col"><%=backupInfo.getThoiGian() %></td>
									<td class="col"><%=backupInfo.getMoTa() %></td>
								</tr>
								<%} }%>
							</table>
						</div>
						<div id = "paging" style="margin: 0 auto;">
							<table style ="border-style: none;margin: 0 auto;">
								<tr>
								<%
								
								if(pageNumber > 0){ %>
								<td>Trang</td>
									<td>
										<%
											int p = (pageNumber <=9 ? pageNumber : 9) ;
											for(int i = 0; i <=p; i++) { %>
												<input type="button" value="<%=i+1%>" class="page" onclick="loadPage(<%=i%>)">
										<%} 
											if(pageNumber > 9)
												out.println("<input type=\"button\" value=\"Sau >>\" class=\"pageMove\">");		
											%>
										<!-- <input type="button" value="Sau >>" class="pageMove"> -->		
										<% }%>
									</td>
								</tr>
 							</table> 
						</div> 
						<div class="button-group">
							<button type="submit" class="button" id="preBackup">
								<i class="fa fa-plus-circle"></i>&nbsp;Sao lưu dữ liệu
							</button>
							&nbsp;
							<button type="submit" class="button">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Phục hồi dữ liệu
							</button>
							
							&nbsp;
							<button class="button" type="reset">
								<i class="fa fa-spinner"></i>&nbsp;Bỏ qua
							</button>
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.homePageManage %>'">
								<i class="fa fa-sign-out"></i>&nbsp;Thoát
							</button>
						</div>
					</form>
	
				
	
					<form id="backup-form" method="get"
						action="backupdata.jsp">
						<div class="input-table">
							<table>
								<!-- <div class="form-title">Sao lưu dữ liệu</div> -->
								<tr>
									<th style="width:100px; text-align: left;">Thời gian:</th>
									<td style="width:400px;"><%=DateUtil.toString(new Date()) %></td>
								</tr>
								<tr style= "text-align: center;"> 
									<th style="text-align: left;">Mô tả:</th>
									<td ><textarea class="textarea" name="moTa" class="text-area" required id = "moTa" style="width: 400px;"
										autofocus 
										title="Bạn phải nhập mô tả trước khi sao lưu dữ liệu" ></textarea></td>
								</tr>
								
							</table>
						</div>
						<div class="button-group" style= "text-align: center;">
							<!-- 						<input type="hidden" name="action" value = "AddMd">  -->
							<button class="button" type="submit" id="backupData">
								<i class="fa fa-plus-circle"></i>&nbsp;Sao lưu
							</button>
							<button type="reset" class="button">
								<i class="fa fa-refresh"></i>&nbsp;Nhập lại
							</button>
							&nbsp;
<%-- 						<button type="button" class="button" id="exit" onclick="location.href='<%=siteMap.loadBackup%>'"> --%>
							<button type="button" class="button" id="exitBackup">
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