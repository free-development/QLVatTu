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
<link href="style/style-tao-dang-nhap.css" type="text/css"
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
		<div id="main-content">
			<form id="add-form" action="<%=siteMap.login + "?action=loGin" %>"method="post"
				name="dangNhap" >
				<div class="input-table">
					<table>
						<div class="form-title">Đăng nhập</div>
					<tr>
						<td class="input"><label for="msnv">Mã số nhân viên: </label></td>
						<td><input type="text" autofocus required size="10"
							maxlength="10"
							title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv"></td>
					</tr>

					<tr>
						<td class="input"><label for="matkhau">Mật khẩu: </label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>
					</table>
				</div>
				<div class="button-group">
					<button class="button" type="submit" onclick="loGin();">
						<i class="fa fa-plus-circle"></i>&nbsp;Đăng nhập
					</button>
					&nbsp;
					&nbsp;
					<button class="button" type="button">
						<i class="fa fa-sign-out"></i>&nbsp;Thoát
					</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>