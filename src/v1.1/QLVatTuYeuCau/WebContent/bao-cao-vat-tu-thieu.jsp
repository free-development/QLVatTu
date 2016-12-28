
<%@page import="java.sql.Date"%>
<%@page import="util.DateUtil"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.YeuCau"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="model.CongVan"%>
<%@page import="model.CongVan"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
 <link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-bao-cao-bang-de-nghi.css" type="text/css"
	rel="stylesheet">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>	
<script type="text/javascript">
	$(document).ready(function(){
		$('#reset').click(function(){
			$('#ngaybd').val('');
			$('#ngaykt').val('');
		});	
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
		<%
    		NguoiDung nguoiDung = (NguoiDung) session.getAttribute("nguoiDung");
    		if (nguoiDung == null) {
    			request.setAttribute("url", siteMap.bcvttManage+ "?action=manageBcvtt");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String adminMa = request.getServletContext().getInitParameter("adminMa");
    		String chucDanh = nguoiDung.getChucDanh().getCdMa();
    		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
    		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
    		Date ngayBd = (Date) session.getAttribute("ngaybd");
    		Date ngayKt= (Date) session.getAttribute("ngaykt");
    	%>
		
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<div id="title-content"style="margin-top: 20px;">Báo cáo tổng hợp vật tư thiếu</div>
			<br>
			<form id="option-form"  method="get" action="<%=siteMap.bcttManage%>">
				<fieldset style="background-color:#dceaf5;width:700px;margin:0 auto;">
					<legend style="margin: 0 auto;font-size: 18px">Tùy chọn báo cáo</legend>
					<table style="margin: 0 auto; padding-bottom: 20px; cellspading: 30px;margin-top: 10px;">

                        <tr>
                            <th style="text-align: left">Thời gian:</th>
                            
                            <td style="text-align: left; " colspan="2" >Từ ngày &nbsp;
                            <input type="date" class="text" name="ngaybd" id = "ngaybd" <%if (ngayBd != null) out.print("value = \"" + ngayBd + "\"");%>>
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text" name="ngaykt" id = "ngaykt" <%if (ngayKt != null) out.print("value = \"" + ngayKt + "\""); %>></td>
                        </tr>
                        
                        <tr>
<!-- 							<th style="text-align: left; padding-right: 10px;">Chế độ báo cáo:</th> -->
<!-- 							<td style="font-size: 20px"><input name="action" type="radio" value="chitiet" required title="Bạn phải chọn chế độ báo cáo"/>&nbsp;&nbsp;Chi tiết</td> -->
						<td style="font-size: 20px"><input name="action" type="hidden" value="tonghop"/>&nbsp;&nbsp;</td>
	                    </tr>
					</table>
<!-- 					<input type="hidden" name="action" value="baocaovtt"> -->
					<div class="button-group">
						<input type="submit" value="Xem" class="button"/>
						&nbsp;
						<button type="button" class="button" id="reset">Nhập lại</button> 
					</div>
				</fieldset>
				
			</form>
			<br>
			<br>

					
				<% 
// 				if(loaiBc != null && "tonghop".equalsIgnoreCase(loaiBc)){	
	   		ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) session.getAttribute("ctVatTuList");
	   		ArrayList<Long> soLuongList = (ArrayList<Long>) session.getAttribute("soLuongList");
	   		ArrayList<ArrayList<CongVan>> congVanList = (ArrayList<ArrayList<CongVan>> ) session.getAttribute("congVanList");	
	   		%>
			
				<div style="text-align: center;font-size: 25px;color:firebrick;font-weight: bold;margin-top:10px;">Tổng hợp vật tư thiếu</div>
				<div id="view-table-bao-cao" style="max-height: 420px;width: 1200px;display: auto;border: 1px dotted #CCCCCC;margin: 0 auto;overflow: scroll;">
				<table style="margin: 0 auto;width:1200px;border: 1px dotted black;" >
					<tr bgcolor="#199e5e" style="border: 1px dotted black;">
						<th style="border: 1px dotted black;width: 50px;" class="two-column">Mã vật tư</th>
						<th style="border: 1px dotted black;width: 400px;" class="three-column">Tên vật tư</th>
						<th style="border: 1px dotted black;width: 100px;" class="three-column">Nơi sản xuất</th>
						<th style="border: 1px dottedblack;width: 100px;" class="three-column">Chất lượng</th>
<!-- 						<th style="border: 1px dottedblack;width: 350px;" class="three-column">Đơn vị</th> -->
						<th style="border: 1px dotted black;width: 50px;" class="six-column">Đvt</th>
						<th style="border: 1px dotted black;width: 50px;" class="one-column">Tổng số lượng thiếu</th>
						<th style="border: 1px dotted black;width: 50px;" class="one-column">Số lượng tồn</th>
						<th style="border: 1px dotted black;width: 150px;" class="one-column">Công văn liên quan (số P.VT nhận)</th>
					</tr >
								<%
								int count = 0;
							if(ctVatTuList != null){
							
							
							for(CTVatTu ctVatTu  : ctVatTuList) { 
							
							%>
									
					<tr
						<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\""); else out.println("style=\"background : #FFFFFF;\"");%>
						style="border: 1px solid black;">
						<td class="a-column"style="text-align: center;"><%=ctVatTu.getVatTu().getVtMa() %></td>
						<td class="b-column"style="text-align: left;"><%=ctVatTu.getVatTu().getVtTen() %></td>
						<td class="c-column"style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
						<td class="d-column"style="text-align: left;"><%=ctVatTu.getChatLuong().getClTen() %></td>
<%-- 						<td class="d-column"style="text-align: left;"><%=congVan.getDonVi().getDvTen()%></td> --%>
						<td class="e-column"style="text-align: center;"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
						<td class="e-column"style="text-align: center;"><%=soLuongList.get(count) %></td>
						<td class="d-column"style="text-align: center;"><%=ctVatTu.getSoLuongTon()%></td>
						<td>
							<%
								ArrayList<CongVan> congVans = congVanList.get(count);
								StringBuilder cell = new StringBuilder ("");
								for(CongVan congVan : congVans) {
									if (congVan.getDaXoa() == 0)							
										cell.append("<a style=\"color: red; text-decoration: underline; \" href=" + siteMap.searchCongVan + "?congVan=" + congVan.getCvId() + ">" + congVan.getSoDen() + "/" + (congVan.getCvNgayNhan().getYear() + 1900) + " </a>" + ", ");
									else 
										cell.append(congVan.getSoDen() + " - " + DateUtil.toString(congVan.getCvNgayNhan()) + "(Đã xóa)), ");
								}
								int len = cell.length();
								cell.delete(len - 2, len);
								out.println(cell);
							%>
							 
						</td>
					</tr>
					<%count++; }} %>
				</table>
			</div>

			<div class="group-button"style ="text-align: center;margin-top:10px;">
<!--     <a href="excel.jsp?exportToExcel=YES">Export to Excel</a> -->
			<a href="<%=siteMap.xuatFile + ".jsp"%>" target="_blank">
				<button class="button" type="button">
					<i class="fa fa-download"></i>&nbsp;&nbsp;Xuất file
				</button>
				&nbsp;
				
				<button type="button" class="button" onclick="location.href='<%=siteMap.homePageManage %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
			</div>
	</div>
	</div>
</body>
</html>
