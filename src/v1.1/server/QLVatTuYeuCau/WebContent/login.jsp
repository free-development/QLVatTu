<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
	<!DOCTYPE html>
<html>
<head>
<link href="style/style-giao-dien-chinh.css" type="text/css"rel="stylesheet">
<link href="style/style-login.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/nguoidung.js"></script>
<script type="text/javascript" src="js/location.js"></script>

</head>
<body>

					<div style="text-align: center;margin: 0 auto;"><img src="./img/login.jpg" /></div>
				<div class="main-content">
					<div style="width: 800px; height: 630px;background-image: url('./img/content.jpg');background-repeat: no-repeat;text-align: center;margin: 0 auto;">
							<div class="module form-module">
				  				<div class="toggle"><i class="fa fa-times fa-pencil"></i></div>
						  			<div class="form">
									  	<%
									  		String status = (String)request.getAttribute("status");
									  		if (status != null)
									  			out.println("<script>alert('Mã số nhân viên và mật khẩu chưa đúng vui lòng kiểm tra lại')</script>");
									  	%>
						    		<h2>Đăng nhập</h2>
								    <form method="post" action="<%=siteMap.loginAction%>" id="loginForm">
								      <input type="text" autofocus required size="10" maxlength="10" title="Mã số nhân viên đủ 10 ký tự, không chứa ký tự đặc biệt"
									pattern="[a-zA-Z0-9]*" class="text" id="msnv" name="msnv" placeholder="Tên tài khoản"/>
								      <input type="password" required size="20" maxlength="20"title="Mật khẩu phải hơn 7 ký tự và nhỏ hơn 21" pattern=".{8,20}"
									class="text" id="matkhau" name="matkhau" placeholder="Mật khẩu"/>
								      <button class="button" type="submit" id="login" >Đăng nhập</button>
								    </form>
						  			</div>
				 			 </div>
		 			 </div>
		  		</div>
</body>
</html>