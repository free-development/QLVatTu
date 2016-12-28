
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
<link href="style/style-tao-doi-pass.css" type="text/css"
	rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>

<script type="text/javascript">


			function showForm(formId, check){
				if (check)
					document.getElementById(formId).style.display="block";
				else document.getElementById(formId).style.display="none";
				var f = document.getElementById('main-form'), s, opacity;
				s = f.style;
				opacity = check? '10' : '100';
				s.opacity = s.MozOpacity = s.KhtmlOpacity = opacity/100;
				s.filter = 'alpha(opacity='+opacity+')';
				for(var i=0; i<f.length; i++) f[i].disabled = check;
			}
			function confirmDelete(){
				return confirm('Bạn có chắc xóa');
			}
			function checkPassword()
				{
					var password = document.forms["taoTaiKhoan"]["matkhau"].value;
					var confirmPassword = document.forms["taoTaiKhoan"]["nlmatkhau"].value;
					if(password != confirmPassword)
					{
						alert("Mật khẩu nhập lại chưa chính xác. Vui lòng kiểm tra lại!");
						return false;
					}
					return true;
				}
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
		<%
				String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
				String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
				String adminMa = request.getServletContext().getInitParameter("adminMa");
    		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    		if (nguoiDung == null) {
    			request.setAttribute("url", siteMap.changePassPage);
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String chucDanh = nguoiDung.getChucDanh().getCdMa();
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<form id="add-form" action="<%=siteMap.changePass %>" method="post"
				name="taoTaiKhoan" action="/QLVatTuYeuCau/changePass.html">
				<div class="input-table">
					<table>
						<div class="form-title">Đổi mật khẩu</div>
						<tr>
						<td class="input"><label for="msnv">Mã số NV</label></td>
						<td><input type="text" autofocus required size="10"
							maxlength="10"
							title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
							pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv"></td>
					</tr>
					<tr>
						<td class="input"><label for="matkhau">Mật khẩu cũ</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>
					<tr>
						<td class="input"><label for="matkhau">Mật khẩu mới</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="matkhau" name="matkhau"></td>
					</tr>

					<tr>
						<td class="input"><label for="re-matkhau">Nhập lại
								mật khẩu mới</label></td>
						<td><input type="password" required size="20" maxlength="20"
							title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
							class="text" id="re-matkhau" name="re-matkhau"></td>
					</tr>
					</table>
				</div>
				<div class="button-group">
					<button class="button" type="submit"
						onclick="return checkPassword();">
						<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-refresh"></i>&nbsp;Nhập lại
					</button>
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