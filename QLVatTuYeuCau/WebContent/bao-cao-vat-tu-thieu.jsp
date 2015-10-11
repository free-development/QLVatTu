
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
    	%>
		<%
			String loaiBc = (String) session.getAttribute("action"); 
// 	        String exportToExcel = request.getParameter("exportToExel");
// 	        if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) {
// 	            response.setContentType("application/vnd.ms-excel");
// 	            response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls");
// 	        }
		%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<div id="title-content"style="margin-top: 20px;">Báo cáo tổng hợp vật tư thiếu</div>
			<br>
			<form id="option-form"  method="get" action="<%=siteMap.bcvttManage%>">
				<fieldset style="background-color:#dceaf5;width:700px;margin:0 auto;">
					<legend style="margin: 0 auto;font-size: 18px">Tùy chọn báo cáo</legend>
					<table style="margin: 0 auto; padding-bottom: 20px; cellspading: 30px;margin-top: 10px;">

                        <tr>
                            <th style="text-align: left">Thời gian:</th>
                            <td style="text-align: left; " colspan="2" >Từ ngày &nbsp;
                            <input type="date" class="text" name="ngaybd" >
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text" name="ngaykt"></td>
                        </tr>
                        
                        <tr>
<!-- 							<th style="text-align: left; padding-right: 10px;">Chế độ báo cáo:</th> -->
<!-- 							<td style="font-size: 20px"><input name="action" type="radio" value="chitiet" required title="Bạn phải chọn chế độ báo cáo"/>&nbsp;&nbsp;Chi tiết</td> -->
						<td style="font-size: 20px"><input name="action" type="hidden" value="tonghop"/>&nbsp;&nbsp;</td>
	                    </tr>
					</table>
<!-- 					<input type="hidden" name="action" value="baocaovtt"> -->
					<input style="margin-left: 300px;margin-bottom: 20px;" type="submit" value="Xem" class="button"/>
				</fieldset>
				
			</form>
			<br>
			<br>

					
				<% 
// 				if(loaiBc != null && "tonghop".equalsIgnoreCase(loaiBc)){	
	   		HashMap<Integer, CTVatTu> ctvtHash = (HashMap<Integer, CTVatTu>) session.getAttribute("ctVatTuHash");
	   		HashMap<Integer, Integer> yeuCauHash = (HashMap<Integer, Integer>) session.getAttribute("yeuCauHash");
	   		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");	
	   		HashMap<Integer, ArrayList<Integer>> cvIdHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("cvIdHash");
	   		HashMap<Integer, ArrayList<Integer>> soDenHash = (HashMap<Integer, ArrayList<Integer>>) session.getAttribute("soDenHash");
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
						<th style="border: 1px dotted black;width: 150px;" class="one-column">Công văn liên quan (số đến)</th>
					</tr >
								<%
								int count = 0;
							if(yeuCauHash != null){
							
							
							for(Integer key  : yeuCauHash.keySet()) { count++;
							CTVatTu ctvt = ctvtHash.get(key);
							CongVan congVan = congVanList.get(count); 	
							%>
									
					<tr
						<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\""); else out.println("style=\"background : #FFFFFF;\"");%>
						style="border: 1px solid black;">
						<td class="a-column"style="text-align: center;"><%=ctvt.getVatTu().getVtMa() %></td>
						<td class="b-column"style="text-align: left;"><%=ctvt.getVatTu().getVtTen() %></td>
						<td class="c-column"style="text-align: left;"><%=ctvt.getNoiSanXuat().getNsxTen() %></td>
						<td class="d-column"style="text-align: left;"><%=ctvt.getChatLuong().getClTen() %></td>
<%-- 						<td class="d-column"style="text-align: left;"><%=congVan.getDonVi().getDvTen()%></td> --%>
						<td class="e-column"style="text-align: center;"><%=ctvt.getVatTu().getDvt().getDvtTen() %></td>
						<td class="e-column"style="text-align: center;"><%=yeuCauHash.get(key) %></td>
						<td class="d-column"style="text-align: center;"><%=ctvt.getSoLuongTon()%></td>
						<td>
							<%
								ArrayList<Integer> cvIdList = cvIdHash.get(key);
								ArrayList<Integer> soDenList = soDenHash.get(key);
								int length = soDenList.size();
								System.out.print(length);
								StringBuilder cell = new StringBuilder ("");
								for(int i = 0; i < cvIdList.size(); i++) {
									int soDen = soDenList.get(i);
									int cvId = cvIdList.get(i);
									cell.append("<a style=\"color: red; text-decoration: underline; \" href=" + siteMap.searchCongVan + "?congVan=" + cvId + "> " + soDen + "</a>" + ", ");
								}
								int len = cell.length();
								cell.delete(len - 2, len);
								out.println(cell);
							%>
							 
						</td>
					</tr>
					<%} }%>
				</table>
			</div>

			<div class="group-button"style ="text-align: center;margin-top:10px;">
<!--     <a href="excel.jsp?exportToExcel=YES">Export to Excel</a> -->

				<button class="button" type="button" onclick="location.href='<%=siteMap.xuatFile+ ".jsp"%>'">
					<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
				</button>
				&nbsp;
				<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
			</div>
	</div>
	</div>
</body>
</html>
