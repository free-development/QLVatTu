<%@page import="model.NguoiDung"%>
<%@page import="model.DonViTinh"%>
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
<script type="text/javascript" src="js/don-vi-tinh.js"></script>
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
<script type="text/javascript" src="js/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.dvtManage + "?action=manageDvt");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<DonViTinh> listDonViTinh = (ArrayList<DonViTinh>) request.getAttribute("donViTinhList");
			if (listDonViTinh ==  null) {
				int index = siteMap.dvtManage.lastIndexOf("/");
				String url = siteMap.dvtManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url +  "?action=manageDvt");
				dispatcher.forward(request, response);
				return;
			}
			Long size = (Long) request.getAttribute("size");
// 			long pageNum = (Long) request.getAttribute("size")/10;
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<div id="title-content">Danh mục đơn vị tính</div>
				<form id="main-form">
					<div id="view-table" style="margin: 0 auto;">
						<table>
							<tr style="background: #199e5e">
								<th class="left-column"><input type="checkbox"
									class="checkAll"></th>
<!-- 								<th class="mid-column">ID</th> -->
								<th class="right-column">Tên đơn vị tính</th>
							</tr>
							<%
							if(listDonViTinh != null) {
							int count = 0;
							for(DonViTinh donViTinh : listDonViTinh) {count++ ;%>
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td class="left-column"><input type="checkbox" name="dvtId"
									value="<%=donViTinh.getDvtTen() %>" class="checkbox"></td>
<%-- 								<td class="col"><%=donViTinh.getDvtId() %></td> --%>
								<td class="col"><%=donViTinh.getDvtTen() %></td>
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
<!-- 						<input type="hidden" name="action" value="deleteVaiTro"> -->
						<button type="button" class="button"
							onclick="showForm('add-form', true)">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						<button type="button" class="button"
							onclick="preUpdatedvt('update-form', true);">
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
				<form id="add-form" method="get" action="<%=siteMap.dvtManage + "?action=manageDvt"%>">
					<div class="input-table">
						<table >
							<div class="form-title">Thêm đơn vị tính</div>
							<tr>
								<th class="label"><label for="tenvaitro">Tên đơn vị tính</label></th>
								<td><input name="dvtTen" size="30px" type="text" onkeypress="changedvtTen();"
									class="text" required title="Tên đơn vị tính không được để trống"><div id="requiredvtTen" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
				<button class="button" type="button" onclick="adddvt();">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm
				</button>
				<button type="reset" class="button">
					<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
				</button>
				<button type="button" class="button"
					onclick="loadAddDvt();">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
				</form>

				<!-- ---------------Update form-------------- -->
				<form id="update-form">
					<div class="input-table">
						<table>
							<div class="form-title">Cập nhật đơn vị tính</div>
<!-- 							<tr> -->
<!-- 								<th><label for="id">ID</label></th> -->
<!-- 								<td><input name="dvtIdUpdate" type="number" class="text"  -->
<!-- 									required title="ID đơn vị tính không để trống" readonly style="background-color: #D1D1E0;"><div id="requiredvtID" style="color: red"></div></td> -->
<!-- 							</tr> -->
							<tr>
								<th><label for="tenvaitro">Tên đơn vị tính</label></th>
								<td><input name="dvtTenUpdate" size="30px" type="text" onkeypress="changedvtTenUp();"
									class="text" required title="Tên đơn vị tính không được để trống"><div id="requiredvtTenUp" style="color: red"></div></td>
							</tr>
						</table>
					</div>
					<div class="group-button">
						
						<button type="button" class="button" onclick="confirmUpdatedvt();">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button  type="button" class="button" onclick="resetUpdatedvt();">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="loadUpdateDvt();">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
			</div>
			</div>
</body>
</html>