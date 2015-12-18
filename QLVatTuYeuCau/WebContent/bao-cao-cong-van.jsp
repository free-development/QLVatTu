
﻿<%@page import="model.NguoiDung"%>
<%@page import="util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javax.swing.text.DateFormatter"%>
<%@page import="java.util.logging.SimpleFormatter"%>
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
<%@page import="model.DonVi"%>
<%@page import="model.TrangThai"%>
<%@page import="model.File"%>
<%@page import="model.MucDich"%>
<%@page import="model.VaiTro"%>
<%@page import="model.VTCongVan"%>
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
<script type="text/javascript" src="js/date-util.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/bao-cao-cong-van.js"></script>

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
    			request.setAttribute("url", siteMap.bccvManage+ "?action=manageBccv");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String chucDanh = nguoiDung.getChucDanh().getCdMa();
    	%>
	<%
	
	
	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
	if (congVanList ==  null) {
		int index = siteMap.bccvManage.lastIndexOf("/");
		String url = siteMap.bccvManage.substring(index);
		RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=baocaocv");
		dispatcher.forward(request, response);
		return;
	}
	ArrayList<TrangThai> listTrangThai = (ArrayList<TrangThai>) request.getAttribute("trangThaiList");
	ArrayList<DonVi> listDonVi = (ArrayList<DonVi>) request.getAttribute("donViList");
	ArrayList<MucDich> listMucDich = (ArrayList<MucDich>) request.getAttribute("mucDichList");
	
	
    %>
  
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<div id="title-content"style="margin-bottom: 10px;">Báo cáo công văn</div>
			<div id="content">
			<form id="option-form" method="get" action ="<%=siteMap.bccvManage %>?action=baocaocv">
			<fieldset style="background-color:#dceaf5;width:750px;margin:0 auto;">
			
				<table style="margin:0 auto; margin-top: 30px;">
					<tr>
						<th style="text-align: left;margin-top: 10px;padding-right:10px;" colspan="2">Số công văn đến:</th>
						<td style="text-align: left">
						<select 
							title="" class="select" id="cvSo" name="cvSo" style="margin-top: 10px;">
								<option value="" style = "padding: 0 auto;">-- Tất cả--</option>
								<%						  
 								for (CongVan congVan : congVanList)
 								{%>  
 								<option value=<%=congVan.getCvSo()%>><%=congVan.getCvSo()%></option> 
 								<%}  
  								%>  
						</select>
						</td>
					</tr>
					
					<tr>
						<th style="text-align: left;margin-top: 10px;padding-right:10px;" colspan="2">Đơn vị:</th>
						<td style="text-align: left">
						<select 
							title="" class="select" id="donVi" name="donVi" style="margin-top: 10px;">
								<option selected value="" style = "padding: 0 auto;">-- Tất cả--</option>
								<%						  
 								int count = 0;
 								for (DonVi donVi : listDonVi)
 								{%>  
 								<option value=<%=donVi.getDvMa()%>><%=donVi.getDvTen()%></option> 
 								<%}  
  								%>  
						</select>
						</td>
					</tr>
					<tr>
						<th style="text-align: left;margin-top: 10px;padding-right:10px;" colspan="2">Mục đích:</th>
						<td style="text-align: left">
						<select 
							title="" class="select" id="mucDich" name="mucDich" style="margin-top: 10px;">
								<option value="" style = "padding: 0 auto;">-- Tất cả--</option>
								<%						  
 								for (MucDich mucDich : listMucDich)
 								{%>  
 								<option value=<%=mucDich.getMdMa()%>><%=mucDich.getMdTen()%></option> 
 								<%}  
  								%>  
						</select>
						</td>
					</tr>
					<tr style="margin-top: 30px;">
                            <th style="text-align: left;margin-top: 10px;padding-right:10px;" colspan="1" >Ngày công văn nhận:</th>
                            <td style="text-align: left;margin-top: 10px;"  >Từ ngày &nbsp;</td/>
                            <td><input type="date" class="text"name="sCvNgayNhan" id="sCvNgayNhan">
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text" name="eCvNgayNhan" id = "eCvNgayNhan" ></td>
                    </tr>
                    <tr style="margin-top: 30px;">
                            <th style="text-align: left;margin-top: 10px;padding-right:10px;" colspan="1"  >Ngày công văn đến:</th>
                            <td style="text-align: left;margin-top: 10px;" >Từ ngày &nbsp;</td>
                            <td><input type="date" class="text"name="sCvNgayDi" id="sCvNgayDi">
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text"name="eCvNgayDi" id="eCvNgayDi"></td>
                    </tr>
<!-- 				<table class="radio" style="margin-top: 20px;margin-left: 40px;"> -->
					<tr>
					<th style="text-align: left;margin-top: 20px;padding-right:50px;" colspan="2">Trạng thái:</th>				  
 								
 								<td style="text-align: left;"><input type="radio" name="trangThai" id = "CGQ" value="CGQ">
								<label class="lable1" for="CGQ">Chưa giải quyết</label>
								<input type="radio" name="trangThai" id = "DGQ" value="DGQ">
								<label class="lable1" for="CGQ">Đang giải quyết</label>
								<input type="radio" name="trangThai" id = "DaGQ" value="DaGQ">
								<label class="lable1" for="DaGQ">Đã giải quyết</label>
 						
  								<input type="radio" name="trangThai" value="" id = "All">
								<label class="lable1" for="All">Tất cả</label></td>
								</tr>
				</table>
				<input type="hidden" name="action" value="baocaobdn">
				<input style="margin-top: 15px;" class="button" type="button" id="xem" value="Xem">
<!-- 					<i class="fa fa-eye"></i>&nbsp;&nbsp;</> -->
				<br>
				<br>
				</fieldset>
				</form>
			</div>
			<div id="view-table" style="height: 500px;width: 1250px;display: auto;border: 1px solid #CCCCCC;margin: 0 auto;margin-top: 20px;overflow: scroll;">
				<table >
					<tr bgcolor="lightgreen">
						<th style="width: 100px;">Số công văn nhận</th>
						<th style="width: 100px;">Số công văn đến</th>
						<th style="width: 50px;">Ngày công văn nhận</th>
						<th style="width: 50px;">Ngày công văn đến</th>
						<th style="width: 250px;">Mục đích</th>
						<th style="width: 200px;">Nơi gửi</th>
						<th style="width: 200px;">Trích yếu</th>
						<th style="width: 200px;">Bút phê</th>
						<th style="width: 100px;">Trạng thái</th>
						
					</tr>
							<%
					
			                     	int cnt = 0;
			                     	for(CongVan congVan : congVanList) {
			                     		cnt ++;
			                     %>
					<tr class="rowContent"
						<%if (cnt % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
						<td style="width: 50px; text-align: center;"><%=congVan.getSoDen() %></td>
						<td style="width: 50px; text-align: center;"><%=congVan.getCvSo()%></td>
						<td style="width: 100px; text-align: center;"><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
<%-- 						<td style="width: 50px; text-align: center;"><%=congVan.getCvSo() %></td> --%>
						
						<td style="width: 50px; text-align: center;"><%=DateUtil.toString(congVan.getCvNgayDi()) %></td>
						<td style="text-align: left; width: 300px;"><%=congVan.getMucDich().getMdTen() %></td>
						<td style="text-align: left; width: 100px;"><%=congVan.getDonVi().getDvTen() %></td>
						<td style="width: 50px;"><%=congVan.getTrichYeu() %></td>
						<td style="text-align: left; width: 100px;"><%=congVan.getButPhe() %></td>
						<td style="text-align: left; width: 150px;"><%=congVan.getTrangThai().getTtTen() %></td>

					</tr>
					<%} %>
				</table>
				</div>
				<div class="group-button">
					&nbsp;&nbsp;
					<a href="<%=siteMap.exportBccv%>" target="_blank"><button class="button" type="button">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
					</button></a>
					&nbsp;&nbsp;
					<button type="button" class="button" onclick="window.close();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
		</div>
		</div>
		</table>
	
</body>
</html>
