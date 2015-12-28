<%@page import="model.CTNguoiDung"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.ChucDanh"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-cap-nhat-tai-khoan.css" type="text/css"
	rel="stylesheet">
<!-- <link href="style/style-chia-se.css" type="text/css" -->
<!-- 	rel="stylesheet"> -->
<!-- <link href="style/style-vat-tu.css" type="text/css" -->
<!-- 	rel="stylesheet"> -->
<link rel="stylesheet" type="text/css" href="style/jquery.autocomplete.css" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script src="js/jsapi.js"></script>  
	<script>  
		google.load("jquery", "1");
	</script>
	<script src="js/jquery.autocomplete.js"></script>
	
	<script type="text/javascript">
	
	</script>
</head>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<!-- <script type="text/javascript" src="js/jquery-1.6.3.min.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.min.js"></script> -->
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/nguoidung.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.ndManage + "?action=manageNd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   		String chucDanh = authentication.getChucDanh().getCdMa();
   	%>
	<%
    		ArrayList<ChucDanh> listChucDanh = (ArrayList<ChucDanh>) request.getAttribute("chucDanhList");
			ArrayList<CTNguoiDung> ctlistNguoiDung = (ArrayList<CTNguoiDung>) request.getAttribute("ctnguoiDungList");
			ArrayList<NguoiDung> listNguoiDung = (ArrayList<NguoiDung>) request.getAttribute("nguoiDungList");
			if (listChucDanh ==  null) {
				int index = siteMap.ndManage.lastIndexOf("/");
				String url = siteMap.ndManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageNd");
				dispatcher.forward(request, response);
				return;
			}
			long pageNum = (Long) request.getAttribute("size")/10;
			
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
		<div id= "loading"></div>
		<div id="title-content">Danh sách tài khoản</div>
				<table style="margin:0 auto; margin-bottom: 10px;">		
					<tr>		
					<th  style="text-align: left; color: black; font-size: 19px;">*Tìm kiếm mã</th>
								<td>
									<div class="search_form1" id="search">		
										
										
										<form id="search-nguoidung">												
											<span> &nbsp; <input type="search" id="searchName" class="text-search" name="nguoidung"/>						
														 												
												<td><input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/></td>
												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp;
											</span>
											
												<td> <span class="search-button"> &nbsp; <button type="button" class="btn-search" style="background-color: #00A69B;" onclick="timKiemNguoidung()"><i class="fa fa-search"></i></button></span></td>						
										</form>
										<script>
														$('#searchName').autocomplete("getdataMsnv.jsp");
														$('#searchName').autocomplete("getdataHoten.jsp");	
														</script>
									</div>
									</td>
					</tr>					
				</table>
				
				<form id="main-form">
				
				<div id="view-table">
					<table style="width:1024px;">
						<tr bgcolor= "#199e5e">
						<th style="text-align: center;">Chọn</th>
						<th>Msnv</th><th>Họ tên</th><th>Chức danh</th><th>Email</th><th>Địa chỉ</th><th>Số điện thoại</th>
						</tr>
						<%
						int i = 0;
						for(NguoiDung nguoiDung : listNguoiDung)
						{i++;%>
						<tr class="rowContent"
						 <% if (i % 2 ==0) out.println("style=\"background : #CCFFFF;\"");%> >
							<td style="text-align: center;"><input type = "checkbox" class="checkbox" name ="msnv" value="<%=nguoiDung.getMsnv()%>"></td>
							<td><%=nguoiDung.getMsnv()%></td>
							<td><%=nguoiDung.getHoTen()%></td>
							<td><%=nguoiDung.getChucDanh().getCdTen()%></td>
							<td><%=nguoiDung.getEmail()%></td>
							<td><%=nguoiDung.getDiaChi()%></td>
							<td><%=nguoiDung.getSdt()%></td>
							
						</tr>
						<%}%>
					</table>
					</div>
					<div id ="paging" style="text-align: center;">
					<table style ="border-style: none;">
								<tr>
									<td>Trang</td>
									<td>
							<%
										String str = "";
										String pages = ""; 
										long p = (pageNum < 10 ? pageNum : 10);
									for(int j = 0; j <= p; j++) {
										str += "<input type=\"button\" value=\"" + (j+1) + "\" class=\"page\" onclick=\"loadPageNd(" + j +")\">&nbsp;";
									}
									if (pageNum > 10)
										str += "<input type=\"button\" class=\"pageMove\"  value=\"Sau >>\" onclick= \"loadPageNd(\'Next\');\">";
									out.println(str);	
								%>
								</td>
								</tr>
								</table>
						</div>		
				
					<div class="button-group">
					<input type="hidden" value="save" name="action">
						<button class="button" id="update" type="button" onclick="preUpdateNd('add-form', true)">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
						</button>
						<button type="reset" class="button" type="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						<button type="button" class="button" onclick="location.href='<%=siteMap.homePageManage %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					</div>
				</form>
				</div>
			<form id="update-form">
				<div class="input-table">
					<table>
						<div class="form-title">Cập nhật thông tin</div>
						<tr>
						<td class="input"><label for="msnv">Mã số nhân viên</label></td>
						<td><input type="text" autofocus required size="12" style="background: #D1D1E0;"
							maxlength="10" placeholder="Mã số nhân viên"
							title="Mã số nhân hơn 4 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]{4}[a-zA-Z0-9]*" class="text" id="msnv" name="msnv" onkeypress="changeMsnv();"readonly><div id="requireMsnv" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="hoten">Họ tên</label></td>
						<td><input type="text" required size="20" maxlength="50" placeholder="Họ tên"
							title="Họ tên không được chứa chữ số và ký tự đặc biệt"
							class="text" id="hoTen" name="hoten" onkeypress="changeHoten();"style="margin-top: 5px;"><div id="requireHoten" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="chucdanh">Chức danh</label></td>
						<td><select required title="Chức danh phải được chọn"
							class="select" id="chucDanh" name="chucdanh" placeholder="Chọn chức danh" onchange="changeChucdanh();">
								<option disabled selected value="">--Chọn chức danh--</option>
								<%
							
									int count = 0;
									for(ChucDanh cd : listChucDanh)
									{%>
								<option value=<%=cd.getCdMa()%>><%=cd.getCdTen()%></option>
								<%}
								%>
						</select><div id="requireChucdanh" style="color: red"></div></td>
					</tr>

					<tr>
						<td class="input"><label for="email">Email</label></td>
						<td><input type="text" required size="25" maxlength="50" placeholder="Email"
							title="Email phải được nhập" class="text" id="email" name="email" onkeypress="changeEmail();"><div id="requireEmail" style="color: red"></div></td>
					</tr>


					<tr>
						<td class="input"><label for="diachi">Địa chỉ</label></td>
						<td><input type="text" required size="25" maxlength="50" placeholder="Địa chỉ"
							title="Địa chỉ phải được nhập" class="text" name="diachi"
							id="diachi" onkeypress="changeDiachi();"><div id="requireDiachi" style="color: red"></div></td>
					</tr>
					<tr>
						<td class="input"><label for="sdt">Số điện thoại</label></td>
						<td><input type="text" required size="11" maxlength="11" placeholder="Số điện thoại"
							title="Phải nhập đúng định dạng. Ví dụ: 01234567890"
							pattern="[0-9]{10,11}" class="text" id="sdt" name="sdt" onkeypress="changeSdt();"><div id="requireSdt" style="color: red"></div></td>
					</tr>

					</table>
				</div>
				<div class="button-group">
					<button class="button" type="submit" >
						<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-refresh"></i>&nbsp;Nhập lại
					</button>
					&nbsp;
					<button type="button" class="button" onclick="loadAddNd()">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
				</div>
			</form>
		</div>
</body>
</html>