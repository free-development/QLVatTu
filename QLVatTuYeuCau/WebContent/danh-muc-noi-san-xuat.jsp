<?xml version="1.0" encoding="UTF-8"?>
<%@page import="model.NguoiDung"%>
<%@page import="map.siteMap"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style-noi-san-xuat.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
	<script >
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
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/noi-san-xuat.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if (status != null && status.equals("success"))
			out.println("<script>alert('Import dữ liệu thành công!')</script>");
		String adminMa = request.getServletContext().getInitParameter("adminMa");

   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.nsxManage + "?action=manageNsx");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    	request.getCharacterEncoding();
    	response.getCharacterEncoding();
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");

    		ArrayList<NoiSanXuat> listNoiSanXuat = (ArrayList<NoiSanXuat>) request.getAttribute("noiSanXuatList");
    		if (listNoiSanXuat ==  null) {
				int index = siteMap.nsxManage.lastIndexOf("/");
				String url = siteMap.nsxManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageNsx");
				dispatcher.forward(request, response);
				return;
			}
     		Long size = (Long) request.getAttribute("size");
//     		long pageNum = (Long) request.getAttribute("size")/10;
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
			<div id="main-content">
					<div id="title-content">Danh mục nơi sản xuất</div>
			<form id="main-form">
				<div id="view-table" style=" margin: 0 auto;">
					<table>
						<tr style="background:#199e5e">
							<th class="left-column"><input type="checkbox" class="checkAll"></th>
							<th class="mid-column"> Mã NSX</th>
							<th class="right-column">Tên nơi sản xuất</th>
						</tr>
						<%
							if(listNoiSanXuat != null) {
							int count = 0;
							for(NoiSanXuat noiSanXuat : listNoiSanXuat) { count++ ;%>
						<tr class="rowContent" 
							<%if (count % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
							<td class="left-column"><input type="checkbox" name="nsxMa" value="<%=noiSanXuat.getNsxMa() %>" class="checkbox"></td>
							<td class="col"><%=noiSanXuat.getNsxMa() %></td>
							<td class="col"><%=noiSanXuat.getNsxTen() %></td>
						</tr>
						<%} }%>
					</table>	
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
				
				
				</div>				
				
				<div class="group-button">
					<input type="hidden" name="action" value="deleteNsx">
					<button type="button" class="button"  onclick="showForm('add-form', true); $('#nsxFocus').focus();"><i class="fa fa-plus-circle"></i>&nbsp;Thêm</button>
					<button type="button" onclick="preUpdateNsx('update-form', true);$('#nsxTenFocus').focus();"
							class="button">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
					</button> <!-- onclick="return confirmDelete()" -->
						<button class="button" type="button" onclick="confirmDelete();">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
						</button>&nbsp;
						<button type="button" class="button" 
							onclick="showForm2('main-form','import-formct', true)"> 
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Import 
						</button>&nbsp;
						<button class="button" type="button" onclick="location.href='<%=siteMap.xuatNsx+".jsp"%>'">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xuất File
						</button>
						&nbsp;
						<button class="button" type="reset">
							<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
						</button>&nbsp;
						<button type="button" class="btn" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
				</div>	
		</form>
		
		<!-------------- --add-form-------------- -->
		<form id="add-form" onsubmit="addNsx();">
			<div class="input-table">
				<table>
					<div class="form-title">Thêm nơi sản xuất</div>
					<tr>
						<th><label for="MNSX">Mã NSX</label></th>
						<td><input id="nsxFocus" name="nsxMa" type="text" class="text" required onkeypress="changensxMa();"
							autofocus size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
							title="Mã nơi sản xuất chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requirensxMa" style="color: red"></td>
					</tr>
					<tr>
						<th class="label"><label for="MNSX">Tên NSX</label></th>
						<td><input name="nsxTen" id = "a" size="30px" align=left type="text" onkeypress="changensxTen();"
							class="text" required
							title="Tên nơi sản xuất không được để trống"><div id="requirensxTen" style="color: red"></td>
					</tr>
				</table>
			</div>
			<div class="group-button">
				<button class="button" type="button" onclick="addNsx();">
					<i class="fa fa-plus-circle"></i>&nbsp;Thêm
				</button>
				<button type="reset" class="button">
					<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
				</button>
				<button type="button" class="button"
					onclick="loadAddNsx();">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
		</form>

		<!-- ---------------Update form-------------- -->
		<form id="update-form">
			<div class="input-table">
				<table>
					<div class="form-title">Cập nhật nơi sản xuất</div>
					<tr>
						<th><label for="MNSX">Mã NSX</label></th>
						<td><input name="nsxMaUpdate" type="text" class="text" 
							required  size="2" maxlength="3" readonly style="background-color: #D1D1E0;"
							pattern="[a-zA-Z0-9]{3}"
							title="Mã nơi sản xuất chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
					</tr>
					<tr>
						<th><label for="MNSX">Tên NSX</label></th>
						<td><input id= "nsxTenFocus" name="nsxTenUpdate" size="30px" type="text" onkeypress="changensxTenUp();"
							class="text" required autofocus
							title="Tên nơi sản xuất không được để trống"><div id="requirensxTenUp" style="color: red"></div></td>
					</tr>
				</table>
			</div>
			<div class="group-button">
				<input type="hidden" name="action" value="UpdateNsx">
				<button class="button" type="button" onclick="confirmUpdateNsx();">
					<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
				</button>
				<button class="button" type="button" onclick="resetUpdateNsx();">
					<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
				</button>
				<button type="button" class="button"
					onclick="loadUpdateNsx()">
					<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
				</button>
			</div>
		</form>
	<form id="import-formct" action="<%=siteMap.readExcelNsx %>" method="post" enctype="multipart/form-data" style="height: 200px;text-align: center;" onsubmit="document.body.style.cursor='wait'; return true;">
									<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
									<div class="group-button">
										<input value="uploadFile" name="action" type="submit" class="button" style="width: 100px;font-size: 17px;text-align: center;" onclick="document.body.style.cursor='wait'; return true;">
										<input value="Thoát" onclick="showForm2('main-form','import-formct', false);" type="button" class="button"  style="width: 70px;text-align: center;font-size: 17px;">
									</div>
	</form>
	</div>

	</div>
</body>
</html>