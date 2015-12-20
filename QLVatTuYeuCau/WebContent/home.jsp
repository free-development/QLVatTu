<%@page import="util.DateUtil"%>
<%@page import="model.CTVatTu"%>	
<%@page import="model.VaiTro"%>
<%@page import="model.TrangThai"%>
<%@page import="model.NhatKy"%>
<%@page import="model.CongVan"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.NguoiDung"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Công Ty Điện Lực TP.Cần Thơ Phòng Vật Tư</title>
        <link rel="stylesheet" href="style/style-giao-dien-chinh.css" type="text/css">
		<link rel="stylesheet" href="style/style.css" type="text/css">
    <link href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="js/location.js"></script>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/home.js"></script>
    <script type="text/javascript" src="js/date-util.js"></script>
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
		};
		$(window).load(function(){
// 			$("body").css("cursor", "auto");		
// 			Set the cursor ASAP to "Wait"
// 		    document.body.style.cursor='wait';
		    //When the window has finished loading, set it back to default...
// 		    window.onload=function(){document.body.style.cursor='wait';
// 		    } 
		});
	</script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />  
    </head>
    <body style="font-size: 20px;">
    	<%
    		try {
	    		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
	    		if (authentication == null) {
	    			request.setAttribute("url", "index");
	    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
	    			dispatcher.forward(request, response);
	    			return;
	    		}
	    		
	    			
	    		String adminMa = request.getServletContext().getInitParameter("adminMa");
	    		String chucDanh = authentication.getChucDanh().getCdMa();
	    		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
	    		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
	    		String nhanVienMa = request.getServletContext().getInitParameter("nhanVienMa");
    	%>
    	<script type="text/javascript">
chucDanhMa = '<%=chucDanh  %>';
vanThuMa = '<%=vanThuMa  %>';
adminMa = '<%=adminMa  %>';
truongPhongMa = '<%=truongPhongMa  %>';
msnv = '<%=authentication.getMsnv()  %>';
// || capPhatMa.equals(chucDanhMa)
</script>
        <div class="wrapper">
				<jsp:include page="header.jsp" />
<%-- 						<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div> --%>
				<div id="main-content">
				
<!-- 				<table style = "margin: 0 auto;width: 1024spx; vertical-align:top ;"> -->
<!-- 				<tr  style="vertical-align:top ;"> -->
				<table style = "margin: 0 auto;width: 1224spx; vertical-align:top ;">
				<tr >
				<td style=" vertical-align:top ;"">
					<% if (chucDanh.equalsIgnoreCase(truongPhongMa) || chucDanh.equalsIgnoreCase(vanThuMa) || chucDanh.equalsIgnoreCase(adminMa)){
						ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
						ArrayList<CTVatTu> ctVatTuListAlert = (ArrayList<CTVatTu>) request.getAttribute("ctVatTuListAlert");
						ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
					%>
					<div class="view-tbao" >
					<div id = "alert">
					<% if ((chucDanh.equalsIgnoreCase(truongPhongMa) || chucDanh.equalsIgnoreCase(adminMa)) && ctVatTuListAlert != null && ctVatTuListAlert.size() > 0) { %>
						<table style="width:550px;">
						<tr ><th colspan = "5" style="text-align: center; width: 300px; font-size: 20px;color:red;"><i class="fa fa-sign-out"></i>&nbsp;Công việc</th></tr>
						
						<tr style="background-color:white;">
							<th colspan ="5" style="text-align: center; width: 180px;color:red;">Vật tư dưới định mức: </th>
						</tr>
						<tr style="background-color: #199e5e;">
							<th>Mã vật tư</th>
							<th>Mã nơi sản xuất</th>
							<th>Mã chất lượng</th>
							<th>Định mức</th>
							<th>Số lượng tồn</th>
						</tr>
						
						<% int cnt=0; for (CTVatTu ctVatTu : ctVatTuListAlert) {cnt++;%>
						<tr style = "<% if (cnt % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getVatTu().getVtMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getChatLuong().getClMa()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getDinhMuc()%> </td>
							<td colspan ="1" style="text-align: left;"><%=ctVatTu.getSoLuongTon()%> </td>
						</tr>
						<%} %>
						</table>
						<div class="button-group">
							<button class="button" id ="moreAlert">Xem thêm</button>
							<a href="/QLVatTuYeuCau/exportAlert.html" target="_blank"><button class="button">Xuất file</button></a>
						</div>
						<%} %>
						</div>
						<div id = "work">
						<table style="width:100%;">
						<tr style="background-color: #199e5e;">
<!-- 							<td style="text-align: center;" colspan="2"> -->
<!-- 							<i class="fa fa-sign-out"></i>&nbsp;Thông báo -->
<!-- 							</td> -->
							<th colspan = "3" style="text-align: center; width: 180px;">Công văn cần xử lý</th>
							<th colspan = "2">Trạng thái</th>
						</tr>
						<%int count = 0; 
						for (CongVan congVan : congVanList) {
							String style ="";
							
							TrangThai trangThai = congVan.getTrangThai();
							String ttMa = trangThai.getTtMa();
							if (ttMa.equalsIgnoreCase("CGQ"))
								style = "color: red";
							else
								style = "color: yello";
							
						%>
						<tr style = "<% if (count % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td colspan = "3" style="text-align: center;"><a style="color: blue; text-decoration: underline; " href='<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId() %>'> Công văn số <%=congVan.getSoDen()  + " ngày nhận " + DateUtil.toString(congVan.getCvNgayNhan())%></a></td>
							<td colspan = "2" style="text-align: center;"><div style="<%=style%>"><%=trangThai.getTtTen() %></div></td>
						</tr>
						<%count ++;} %>
						</table>
						<script type="text/javascript">
							sttWork = '<%=congVanList.size()%>'
						</script>
						<div class="button-group"><button class="button" id ="moreWork">Xem thêm</button></div>
					</div>
					<%} else if (chucDanh.equalsIgnoreCase(nhanVienMa)){ 
					ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList"); 
					
					ArrayList<ArrayList<VaiTro>> vaiTroList = (ArrayList<ArrayList<VaiTro>>) request.getAttribute("vaiTroList");
					ArrayList<ArrayList<String>> trangThaiList = (ArrayList<ArrayList<String>>) request.getAttribute("trangThaiList");
 				%> 
					<div class="view-tbao"> 
						<div id ="work">
 						<table style="width:100%;"> 
						<tr ><th colspan = "2" style="text-align: center; width: 300px; font-size: 20px;color:red;"><i class="fa fa-sign-out"></i>&nbsp;Công việc</th></tr> 
<!--  						<tr style="background: #CCFFFF">  -->
<!--  							<td style="text-align: center;" colspan="2">  -->
<!-- 							<i class="fa fa-sign-out"></i>&nbsp;Thông báo -->
<!--  							</td>   -->
<!-- <!--  							<th style="text-align: center; width: 180px;">Công văn cần xử lý</th>  --> 
<!--  						</tr>  -->
 						<%int count = 0;  
 						for (CongVan congVan : congVanList) {
 							String style ="";
 							String noiDung = "Vai trò công văn có số đến " + congVan.getSoDen() + " nhận ngày " + DateUtil.toString(congVan.getCvNgayNhan()) + ":";
 							ArrayList<VaiTro> vaiTroCongVanList = vaiTroList.get(count);
 							ArrayList<String> trangThaiCongVanList = trangThaiList.get(count);
 							int i = 0;
 							for (VaiTro vaiTro : vaiTroCongVanList) {
 								String trangThai = trangThaiCongVanList.get(i);
 								noiDung += "<br>&nbsp;&nbsp;+ " + vaiTro.getVtTen() + ": " + trangThai + ".";
 								i++;
 							}
							
							
						%>
						<tr style = " <% if (count % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: left;"><a style="color: blue; text-decoration: underline; " href="<%=siteMap.searchCongVan + "?congVan=" + congVan.getCvId()%>"> Công văn số <%=noiDung %></a></td>
						</tr>
						<%count ++;} %>
						</table>
						<div class="button-group"><button class="button" id ="moreWork">Xem thêm</button></div>
						</div>
					
					<%} %>
					</div>
				</td>
				<td style="vertical-align:top ;">
					<div class="view-nky" id ="nhatKy">
						<table style="width:100%;">
						<tr>
						<th colspan ="3" style="text-align: center; font-size: 20px;color:red;">
							<i class="fa fa-sign-out"></i>&nbsp;Nhật ký hoạt động
						</th>
						</tr>
						<tr style="background-color: #199e5e;">
							<th style="width: 40px;">STT</th>
							<th style="width: 200px;">Hoạt động</th>
							<th style="width: 200px;">Nội dung</th>
							<th style="width: 110px;">Thời gian</th>
						</tr>
						<%
						int count2 = 0;
						ArrayList<NhatKy> nhatKyList = (ArrayList<NhatKy>) request.getAttribute("nhatKyList");
						
						for (NhatKy nhatKy : nhatKyList) {
							String cvId = "";
							String hoatDong = "";
							String hoatDongTemp = nhatKy.getHoatDong();
							String link = "";
							if (hoatDongTemp.indexOf("#") != -1) {
								String[] temp = hoatDongTemp.split("\\#");
								cvId = temp[0];
								hoatDong = temp[1];
								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.searchCongVan + "?congVan=" + cvId + "\">" + hoatDong + "<a>"; 
							} else if (hoatDongTemp.indexOf("#") == -1)
								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.cvManage + "?action=manageCv\">" + hoatDongTemp + "</a>";
// 							else if (hoatDongTemp.indexOf("Chia sẻ") != -1)	{
// 								String[] temp = hoatDongTemp.split("\\#");
// 								cvId = temp[0];
// 								hoatDong = temp[1];
// 								link = "<a style=\"color: blue; text-decoration: underline;\" href=\"" + siteMap.cscvManage + "\"?action=chiaSeCv&congVan=\" + cvId +\">" + hoatDong + "<a>";
// 							}
							
						%>
						
						<tr style = "<% if (count2 % 2 == 1) out.print("background: #CCFFFF; ");%>";>
							<td style="text-align: center;"><%=count2 + 1 %></td>
							
							
							<td ><%out.print(link + " "); %></td>
							<td style="""><%=nhatKy.getNoiDung() %></td>
							<td style="text-align: center;"><%=DateUtil.toString(nhatKy.getThoiGian()) %></td>
						</tr>
						<%count2++;} %>
<!-- 						<tr><th colspan = "4"></th></tr> -->
						</table>
						<script type="text/javascript">
							sttNhatKy = '<%=nhatKyList.size()%>';
						</script>
						<div class="button-group"><button class="button" id ="moreNhatKy">Xem thêm</button></div>
					</div>
					</td>
					</tr>
				</table>
				
				</div>
        </div>
    </body>
    <%} catch (NullPointerException e){
    	RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login);
    	dispatcher.forward(request, response);
    	}%>
    }
</html>