<%@page import="model.ChucDanh"%>
<%@page import="model.NguoiDung"%>
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
<link href="style/style-tao-tai-khoan.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/nguoidung.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>

	<%
    		ArrayList<ChucDanh> listChucDanh = (ArrayList<ChucDanh>) request.getAttribute("chucDanhList");
			String adminMa = request.getServletContext().getInitParameter("adminMa");
			NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
	if (nguoiDung == null) {
		request.setAttribute("url", "index");
		RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
		dispatcher.forward(request, response);
		return;
	}
	String chucDanh = nguoiDung.getChucDanh().getCdMa();
	String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
	String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<form id="add-form" action="<%=siteMap.ndManage %>?action=addNd" method="post"
				name="taoTaiKhoan" >
				<div class="input-table">
					<table>
						<div class="form-title">Tạo tài khoản</div>
						<tr>
						<td class="input"><label for="msnv">Mã số nhân viên</label></td>
						<td><input type="text" autofocus required size="10"
							maxlength="10"
							title="Mã số nhân viên hơn 4 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]{4}[a-zA-Z0-9]*" class="text" id="msnv" name="msnv"></td>
					</tr>

					<tr>
						<td class="input"><label for="matkhau">Mật khẩu</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>

					<tr>
						<td class="input"><label for="re-matkhau">Nhập lại
								mật khẩu</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="nlmatkhau" name="nlmatkhau"></td>
					</tr>

					<tr>
						<td class="input"><label for="chucdanh">Chức danh</label></td>
						<td><select required title="Chức danh phải được chọn"
							class="select" id="chucdanh" name="chucdanh">
								<option disabled selected value="">-- Chọn chức danh --</option>
								<%
							
									int count = 0;
									for(ChucDanh cd : listChucDanh)
									{%>
								<option value=<%=cd.getCdMa()%>><%=cd.getCdTen()%></option>
								<%}
								%>
						</select></td>
					</tr>

					<tr>
						<td class="input"><label for="hoten">Họ tên</label></td>
						<td><input type="text" required size="20" maxlength="50"
							title="Họ tên không được chứa chữ số và ký tự đặc biệt"
							 class="text" id="hoten" name="hoten"style="margin-top: 5px;"></td>
					</tr>

					<tr>
						<td class="input"><label for="sdt">Số điện thoại</label></td>
						<td><input type="text" required size="11" maxlength="11"
							title="Phải nhập đúng định dạng. Ví dụ: 01234567890"
							pattern="[0-9]{10,11}" class="text" id="sdt" name="sdt"></td>
					</tr>

					<tr>
						<td class="input"><label for="email">Email</label></td>
						<td><input type="text" required size="20" maxlength="50"
							title="Email phải được nhập" class="text" id="email" name="email"></td>
					</tr>

					<tr>
						<td class="input"><label for="diachi">Địa chỉ</label></td>
						<td><input type="text" required size="20" maxlength="50"
							title="Địa chỉ phải được nhập" class="text" name="diachi"
							id="diachi"></td>
					</tr>
					</table>
				</div>
				<div class="button-group">
<!-- 					<input type="hidden" name="action" value="AddNd"> -->
					<button class="button" type="submit">
						<i class="fa fa-plus-circle"></i>&nbsp;Tạo mới
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-refresh"></i>&nbsp;Nhập lại
					</button>
					&nbsp;
					<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>