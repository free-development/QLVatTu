<%@page import="model.NguoiDung"%>
<%@page import="model.VaiTro"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">

<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/don-vi-tinh.css" type="text/css" rel="stylesheet">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/vaitro.js"></script>
<script>
    $(document).ready(function() {
        $('.checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });         
            }
        });
        
    });
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/location.js"></script>
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.vtManage + "?action=manageVt");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<VaiTro> listVaiTro = (ArrayList<VaiTro>) request.getAttribute("vaiTroList");
			if (listVaiTro ==  null) {
				int index = siteMap.vtManage.lastIndexOf("/");
				String url = siteMap.vtManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url +  "?action=manageVt");
				dispatcher.forward(request, response);
				return;
			}
// 			Long size = (Long) request.getAttribute("size");
			long size = (Long) request.getAttribute("size");
    	%>	
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
<%-- 				<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div> --%>
			<div id="title-content">Danh mục vai trò</div>
				<form id="main-form">
					<div id="view-table" style="margin: 0 auto; width: 80%;">
						<table style="margin: 0 auto;">
							<tr style="background: #199e5e">
								<th class="left-column"><input type="checkbox"
									class="checkAll"></th>
								<th class="col">Mã vai trò</th>
								<th class="right-column">Tên vai trò</th>
							</tr>
							<%
							if(listVaiTro != null) {
							int count = 0;
							for(VaiTro vaiTro : listVaiTro) {count++ ;%>
							<tr class="rowContent"
								<%if (count % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
								<td class="left-column"><input type="checkbox" name="vtMa"
									value="<%=vaiTro.getVtMa() %>" class="checkbox"></td>
								<td class="col"><%=vaiTro.getVtMa() %></td>
								<td class="col"><%=vaiTro.getVtTen() %></td>
							</tr>
							<%} }%>
						</table>
					</div>
					
					<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
								<%long pageNum = size / 10;
								long du = size % 10;
								if(pageNum >0){ %>
								<td>Trang</td>
									<td>
										<%
											
											for(int i = 0; i <= pageNum; i++) { %>
												<input type="button" value="<%=i+1%>" class="page">
										<%} }%>
									</td>
								</tr>
							</table>
						</div>
					
					<div class="group-button">
						<button type="button" class="button"
							onclick="showForm('add-form', true); $('#vtFocus').focus();">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						<button type="button" class="button"
							onclick="preUpdatevt('update-form', true);">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
						</button>
						<button type="button" class="button" onclick="confirmDelete();">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
						</button>
						&nbsp;
						<button class="button" type="reset">
							<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						&nbsp;
						<button type="button" class="btn" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
				<!-------------- --add-form-------------- -->
				<form id="add-form" method="get" action="<%=siteMap.vtManage + "?action=manageVt"%>">
					<div class="input-table">
						<table >
							<div class="form-title">Thêm vai trò</div>
							<tr>
								<th class="label"><label for="vtMa">Mã vai trò: </label></th>
								<td><input id="vtMa" name="vtMa" size="5" type="text" onkeypress="changevtTen();"
									class="text" required title="Tên vai trò không được để trống"></td>
							</tr>
							<tr><td></td><td><div id="requirevtMa" style="color: red"></div></td></tr>
							<tr>
								<th class="label"><label for="tenvaitro">Tên vai trò: </label></th>
								<td><input id="tenvaitro" name="vtTen" size="30px" type="text" onkeypress="changevtTen();"
									class="text" required title="Tên vai trò không được để trống"><div id="requirevtTen" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
				<button class="button" type="button" onclick="addvt();">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm
				</button>
				<button type="reset" class="button">
					<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
				</button>
				<button type="button" class="button"
					onclick="loadAddVt();">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
				</form>

				<!-- ---------------Update form-------------- -->
				<form id="update-form">
					<div class="input-table">
						<table>
							<div class="form-title">Cập nhật vai trò</div>
							<tr>
								<th><label for="vtMaUpdate">Mã vai trò: </label></th>
								<td><input id="vtMa" name="vtMaUpdate" size="5px" type="text" onkeypress="changevtTenUp();"
									class="text" required readonly style="background-color: #D1D1E0;" title="Tên vai trò không được để trống"><div id="requirevtTenUp" style="color: red"></div></td>
							</tr>
							<tr>
								<th><label for="tenvaitro">Tên vai trò</label></th>
								<td><input id="vtUpFocus" name="vtTenUpdate" size="30px" type="text" onkeypress="changevtTenUp();"
									class="text" required title="Tên vai trò không được để trống"><div id="requirevtTenUp" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
						
						<button type="button" class="button" onclick="confirmUpdatevt();">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button  type="reset" class="button" >
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="loadUpdateVt();">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
			</div>
			</div>
</body>
</html>