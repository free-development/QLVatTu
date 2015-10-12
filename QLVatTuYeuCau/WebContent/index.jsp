
<%@page import="model.CongVan"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.NguoiDung"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
        <link rel="stylesheet" href="style/style-giao-dien-chinh.css" type="text/css">
		<link rel="stylesheet" href="style/style.css" type="text/css">
		 <link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
    <link href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
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
	</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />  
    </head>
    <body>
    	<%
    		
    		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
    		if (authentication == null) {
    			request.setAttribute("url", "index");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String congVanList = (String) request.getAttribute("string");
    		if (congVanList == null)
    			response.sendRedirect("home.html");
    		System.out.println("view cong van = " + congVanList);
    		String adminMa = request.getServletContext().getInitParameter("adminMa");
    		String chucDanh = authentication.getChucDanh().getCdMa();
    		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
    		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
    	%>
        <div class="wrapper">
				<jsp:include page="header.jsp" /> 
				<div id="main-content">
				<table style = "margin: 0 auto;width: 900px;">
				<tr>
				<td>
					<div class="view-tbao">
						<table>
						<tr >
							<td>
							<i class="fa fa-sign-out"></i>&nbsp;Thông báo
							</td>
						</tr>
						</table>
					</div>
				</td>
				<td>
					<div class="view-nky">
						<table>
						<tr>
						<td>
							<a href=""><i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động</a>
						</td>
						</tr>
						</table>
					</div>
					</td>
					</tr>
				</table>
				</div>
				
        </div>
    </body>
</html>
