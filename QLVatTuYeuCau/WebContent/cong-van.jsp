<%@page import="model.VTCongVan"%>
<%@page import="model.VaiTro"%>
<%@page import="dao.VaiTroDAO"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.TrangThai"%>
<%@page import="model.MucDich"%>
<%@page import="model.DonVi"%>
<%@page import="util.DateUtil"%>
<%@page import="model.CongVan"%>
<%@page import="model.File"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>

<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-cong-van.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-menu-tree.css" type="text/css">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/date-util.js"></script>

<%
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String phoPhongMa = request.getServletContext().getInitParameter("phoPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
		String nhanVienMa = request.getServletContext().getInitParameter("nhanVienMa");
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String thuKyMa = request.getServletContext().getInitParameter("thuKyMa");
		String hosting = request.getServletContext().getInitParameter("hosting");
		int capPhatMa = Integer.parseInt(request.getServletContext().getInitParameter("capPhatId"));
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			session.setAttribute("url", siteMap.cvManage+ "?action=manageCv");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   	%>
   	<%
		request.getCharacterEncoding();
		response.getCharacterEncoding();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println(request.getCharacterEncoding());
		response.setContentType ("text/html;charset=UTF-8");
		String error = (String) request.getAttribute("error");
		if(error != null)
			out.println("<script>alert('Số công văn đã tồn tại. Vui lòng nhập lại!!!')</script>");
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
    	if (congVanList ==  null) {
    		int index = siteMap.cvManage.lastIndexOf("/");
    		String url = siteMap.cvManage.substring(index);
    		RequestDispatcher dispatcher =  request.getRequestDispatcher(url);
    		dispatcher.forward(request, response);
    		return;
    	}
    		
    	HashMap<Integer, File> fileHash = (HashMap<Integer, File>) request.getAttribute("fileHash");
    	ArrayList<DonVi> donViList = (ArrayList<DonVi>) request.getAttribute("donViList");
    	ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) request.getAttribute("mucDichList");
    	ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) request.getAttribute("trangThaiList");
    	ArrayList<Integer> yearList = (ArrayList<Integer>) request.getAttribute("yearList");
    	Long size = (Long) request.getAttribute("size");
    	ArrayList<ArrayList<VaiTro>> vaiTroList = (ArrayList<ArrayList<VaiTro>>) request.getAttribute("vaiTroList");
    	ArrayList<ArrayList<VTCongVan>> vtCongVanList = (ArrayList<ArrayList<VTCongVan>>) request.getAttribute("vtCongVanList");
    	ArrayList<ArrayList<String>> nguoiXlCongVan = (ArrayList<ArrayList<String>>) request.getAttribute("nguoiXlCongVan");
//     	ArrayList<String> ttMaList = (ArrayList<String>) request.getAttribute("ttMaList");
    	
    	try {
    %>
<script type="text/javascript">
<% 
String chucDanh = authentication.getChucDanh().getCdMa();
String chucDanhMa = chucDanh;
%>
check = <% if (vanThuMa.equals(chucDanhMa) ) out.print("false"); else out.print("true");%>;
capVatTuId = '<%=capPhatMa  %>';
chucDanhMa = '<%=chucDanhMa  %>';
vanThuMa = '<%=vanThuMa  %>';
nhanVienMa = '<%=nhanVienMa  %>';
adminMa = '<%=adminMa  %>';
thuKyMa = '<%=thuKyMa  %>';
truongPhongMa = '<%=truongPhongMa  %>';
phoPhongMa = '<%=phoPhongMa  %>';
hosting = '<%=hosting  %>';
msnv = '<%=authentication.getMsnv()  %>';
countAdd = '0';
// || capPhatMa.equals(chucDanhMa)
</script>
<script type="text/javascript" src="js/cong-van.js" charset="utf-8"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />

</head>
<body>
	
	
    
	<div class="wrapper">
		<jsp:include page="header.jsp" /> 
		

		<div id="main-content">
			<div id="content-form">
			<div id="title-content">Công văn</div>
			
				<table style="margin: 0 auto;width: 100%;">
				<tr>
				<td>
				 <form id="time-form">
				<div class="left-content">
				<table style="margin-bottom: 300px;">
				<tr style="width: 150px;">
				<td>
					<div id="scroll_time">
						<ol class="tree">
						<% for (Integer year : yearList) {%>
							<li id="year<%=year%>"><label for="y<%=year%>"><%=year %></label> <input
									type="checkbox" id="y<%=year %>" value=<%=year %> class="year" name="year" />
<%-- 									onchange="propCheckYear('y<%=year %>');" --%>
									<ol>
									</ol>
<!-- 								<div class="month">	 -->
								
							</li>
							
							<%} %>
						</ol>		
					</div>
					
					</td>
					</tr>
					<tr style="width: 150px;">
							<th style="text-align: center;">
								--Văn bản đến--
							</th>
							</tr>
							<tr>
							<td style="text-align: center;">
							<select id = "ttFilter" class="select" name="trangThai">
								<option value = "" style="text-align: center;font-weight: bold;">Tất cả</option>
								<option value = "CGQ" style="text-align: center;font-weight: bold;">Chưa giải quyết</option>
								<option value = "DGQ" style="text-align: center;font-weight: bold;">Đang giải quyết</option>
								<option value = "DaGQ" style="text-align: center;font-weight: bold;">Đã giải quyết</option>
							</select>
							</td>
						</tr>
					</table>
					
			</div>
			</form>
			</td>
			<td>
				<div class="right-content">
					<form id="search-form">
						<div id="title-table">
						<table>
							<tr>
								<th class="column-loc">Tìm kiếm: </th>
								<td id = "type"><select class="select" name="filter" id="filter">
										<option selected disabled> Chọn </option>
										<option value =""> Tất cả </option>
<!-- 										<option>Ngày đến</option> -->
										<option value="soDen">Số nhận</option>
										<option value="cvSo">Số công văn đến</option>
										<option value="mdMa">Mục đích nhận</option>
										<option value="cvNgayDi">Ngày nhận</option>
										<option value="cvNgayNhan">Ngày đến</option>
										<option value="dvMa">Đơn vị gửi</option>
<!-- 										<option>Nơi gửi</option> -->
										<option value="trichYeu">Nội dung công tác</option>
										<option value="butPhe">Bút phê</option>
										
<!-- 										<option>Nơi GQ chính</option> -->

								</select>
								<div id="requireFilter"></div>
								</td>
								<td>&nbsp;&nbsp;</td>
								<!--<td>
                                 <select class="select" >
                                <option selected disabled>--Tìm kiếm--</option>
                                <option>Thời gian</option>
                                <option>Số CV</option>
                                <option>Đơn vị</option>
                            </select>
                            </td>-->
								<td>
									<!--                                 <div class="search-form">-->
									<span class="search-text" id="searchContent"> <!--								&nbsp;--> 
									<input type="search" class="text" name="filterValue" id="filterValue" readonly style="background: #D1D1E0;"
										placeholder="Nội dung tìm kiếm" />
								</span> <span class="search-button">
										<button class="btn-search" id = "buttonSearch" type="button">
											<i class="fa fa-search"></i>
										</button>
								</span> <!--                                 </did="test"iv>-->
								<div id="requireFilterValue"></div>
								</td>
								<!--
                            <td>
                                <span class="search-button">
							&nbsp;
							<button class="btn-search"><i class="fa fa-search" ></i></button>
							</span>
                            </td>
-->
							</tr>
						</table>
						</div>
					</form>	


                     <form name="main-form" id = "main-form" method="get" action="<%=siteMap.ycvtManage%>">
                     <div style="width: 100%; overflow:auto" class="scroll_content " id="scroll_content">
                     <table>
						<%
					
                     	int count = 0;
                     	for(CongVan congVan : congVanList) {
                     		count ++;
                     %>
                     <tr><td> 
					<table class="tableContent" <%if (count % 2 == 1){ out.println("style=\"background : #CCFFFF; width: 100%; font-size: 18px;  \"");}else{out.println("style=\"background : #FFFFFF; width: 100%; font-size: 18px;\"");}%> class="border-congvan">
						<tr >
						<% if (chucDanhMa.equals(vanThuMa) || chucDanhMa.equals(adminMa) || chucDanhMa.equals(thuKyMa)) {%>
							<td class="column-check" <%if (chucDanh.equals(adminMa)) out.println("rowspan=\"11\""); out.println("rowspan=\"10\"");%>  style="margin-right: 30px;">
								Chọn <input title="Click để chọn công văn"type="checkbox" name="cvId" value="<%=congVan.getCvId()%>"> 
							</td>
							
							<%} %>
							<td class="left-column-soden" style="font-weight: bold;">Số nhận: &nbsp;&nbsp;</td>	
							<td class="column-so-den" style="text-align: left;"><%=congVan.getSoDen() %></td>
							<td class="left-column-first" style="font-weight: bold;">Ngày nhận: &nbsp;&nbsp;</td>
							<td class="column-date" style="text-align: left;color:blue;"><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
<!-- 							<td colspan="1" style="font-weight: bold;">Trạng thái:</td> -->
<%-- 							<td colspan="1" style="color: red;font-weight: bold;font-style: oblique;"><%=congVan.getTrangThai().getTtTen() %></td> --%>
						</tr>
						<tr>	
							<td  class="left-column-socv" style="font-weight: bold;">Số công văn đến: &nbsp;&nbsp;</td>
							<td colspan ="3" class="column-socv" style="text-align: left;color:red;font-weight: bold;"><%=congVan.getCvSo() %></td>
							<td class="left-column-ngdi" style="font-weight: bold;">Ngày công văn đến:&nbsp;&nbsp;</td>
							<td class="column-date" style="text-align: left;color:blue;"><%=DateUtil.toString(congVan.getCvNgayDi())%></td>
							
						</tr>
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Mục đích: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="3" style="text-align: left"><%=congVan.getMucDich().getMdTen() %></td>
							
<!-- 						</tr> -->
<!-- 						<tr> -->
							
							<td class="left-column-first" style="font-weight: bold;">Nơi gửi: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="1" style="text-align: left"><%= congVan.getDonVi().getDvTen()%></td>
							
						</tr>
						<tr>
						
							<td class="left-column-first" style="font-weight: bold;">Nội dung công tác: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="6" style="text-align: left;font-weight: bold;"><%= congVan.getTrichYeu()%></td>
						</tr>
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Bút phê: &nbsp;&nbsp;</td>
							<td class="column-color" colspan="6"><%= congVan.getButPhe()%></td>
						</tr>
						<tr>
							
							<%
								if (chucDanh.equals(truongPhongMa) || chucDanh.equals(vanThuMa)  || chucDanh.equals(adminMa) || chucDanh.equals(phoPhongMa) || thuKyMa.equals(chucDanhMa)) { %>
									<td class="left-column-first" style="font-weight: bold;">Người xử lý</td>
									<td class="column-color"colspan="3">
									<%
										ArrayList<String> nguoiXlList = nguoiXlCongVan.get(count - 1);
										if (nguoiXlList.size() > 0) {
											System.out.println("size ngXlCv = " + nguoiXlList.size());
											StringBuilder cellHoTen = new StringBuilder("");   
											for (String hoTen : nguoiXlList) {
												cellHoTen.	append(hoTen + ", ");
											}
											int len = cellHoTen.length();
											cellHoTen.delete(len -2, len);
											out.println(cellHoTen.toString());
										}%>
									
<%-- 									<%if (chucDanh.equals(truongPhongMa)) { %> --%>
									
<%-- 									<%} %> --%>
								</td>
								<%}%>
								
								</tr>
								
								<% if (chucDanh.equals(nhanVienMa) || chucDanh.equals(adminMa) || chucDanh.equals(phoPhongMa) || chucDanh.equals(vanThuMa) || thuKyMa.equals(chucDanhMa)) {%>
								<tr>
									<td class="left-column-first" style="font-weight: bold;">Vai trò</td>
									<td class="column-color"colspan="5">
									<table>
									<%
									boolean capPhat = false;
									if (!chucDanh.equals(adminMa)) {
										ArrayList<VaiTro> vaiTro = vaiTroList.get(count - 1);
										ArrayList<VTCongVan> vtCongVan = vtCongVanList.get(count - 1);									
										if (vaiTro.size() > 0) {
											int i = 0;
											for (VaiTro vt : vaiTro) {
												VTCongVan vtcv = vtCongVan.get(i);
												if (vt.getVtId() == capPhatMa || chucDanh.equals(adminMa))
													capPhat = true;
												i++;
									%>
										<tr>
										<td><%=vt.getVtTen() %>: &nbsp;&nbsp;&nbsp;</td>
										<td>
											<input type="radio" <%if ("CGQ".equals(vtcv.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() %>"  value="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+"CGQ"%>"  class="ttMaVtUpdate"> 	
											<label for="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+"CGQ"%>">Chưa giải quyết</label>&nbsp;&nbsp;&nbsp;
										</td>
										<td>
											<input type="radio" <%if ("DGQ".equals(vtcv.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId()  %>"  value="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+"DGQ"%>" class="ttMaVtUpdate" >
											<label for="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+"DGQ"%>">Còn thiếu hàng</label>&nbsp;&nbsp;&nbsp;
										</td>
										<td>
											<input type="radio" <%if ("DaGQ".equals(vtcv.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId()  %>"  value="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+"DaGQ"%>" class="ttMaVtUpdate">
											<label for="<%=vtcv.getMsnv() + "#" + vtcv.getCvId() + "#" + vtcv.getVtId() + "#"+""%>">Đã cấp đủ hàng</label>&nbsp;&nbsp;&nbsp;
										</td>
											<div id="requireTrangThaiUp" style="color: red"></div>
										</tr>
										<%}}}%>
										</table>
									</td>
									<%if (capPhat || chucDanhMa.equals(adminMa)) { %>
										<td colspan="1" style="float: right;">
											<button  class="button" type="button" style="width: 200px; height: 37px;" onclick="location.href='<%=siteMap.ycvtManage + "?cvId=" + congVan.getCvId()%>'">
												<i class="fa fa-spinner"></i>&nbsp;&nbsp;Cập nhật vật tư thiếu
											</button>									
										</td>
								<% 	} %>
								</tr>
									<%}%>
									
						
						<tr>
							<td class="left-column-first" style="font-weight: bold;">Xem công văn: </td>
							<td colspan="6">
							<%
							File file = fileHash.get(congVan.getCvId());
							String fileName = "";
							if (file != null) {
								String path = file.getDiaChi();
								int index = path.lastIndexOf("/");
								int index2 = path.lastIndexOf("-");
								int index3 = path.lastIndexOf(".");
								fileName = path.substring(index + 1, index2);
								if (index3 != -1)
									fileName += path.substring(index3);
							} else {
								fileName = "Không tồn tại file";
							}
							%>
							
								<a target="_black" href="<%if (file != null) out.print("/QLVatTuYeuCau/downloadFileMn.html" + "?action=download&file=" + congVan.getCvId());%>">
									<div class="mo-ta"><%=fileName %></div>
								</a>
							</td>
							
							
						</tr>
						<tr>
						<td class="left-column-first" style="font-weight: bold;">Ghi chú: </td>
						<td colspan="5">
								<%if (file != null) out.println(file.getMoTa()); %>
							</td>
						</tr>
						<tr>
							<th style="text-align: left"><label>Trạng
									thái</label></th>
							<td style="text-align: left; padding-left: 10px;" colspan = "5" id = "<%=congVan.getCvId() %>ttMaCongvan">
							<% if(chucDanh.equals(truongPhongMa) || chucDanh.equals(vanThuMa)  || chucDanh.equals(adminMa) || chucDanh.equals(phoPhongMa)) { %>
								<input type="radio" <%if ("CGQ".equals(congVan.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=congVan.getCvId() %>"  value="<%=congVan.getCvId()+"#"+"CGQ"%>"  class="ttMaUpdate"> 	
								<label for="<%=congVan.getCvId()+"#"+"CGQ"%>">Chưa giải quyết</label>&nbsp;&nbsp;&nbsp;
								<input type="radio" <%if ("DGQ".equals(congVan.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=congVan.getCvId() %>"  value="<%=congVan.getCvId()+"#"+"DGQ"%>" class="ttMaUpdate" >
								<label for="<%=congVan.getCvId()+"#"+"DGQ"%>">Còn thiếu hàng</label>&nbsp;&nbsp;&nbsp;
								<input type="radio" <%if ("DaGQ".equals(congVan.getTrangThai().getTtMa())) out.println(" checked ");%> name="<%=congVan.getCvId() %>"  value="<%=congVan.getCvId()+"#"+"DaGQ"%>" class="ttMaUpdate">
								<label for="<%=congVan.getCvId()+"#"+"DaGQ"%>">Đã cấp đủ hàng</label>&nbsp;&nbsp;&nbsp;
							<%} else out.print(congVan.getTrangThai().getTtTen());%>
							</td>
							<% if(chucDanh.equals(truongPhongMa) || chucDanh.equals(vanThuMa)  || chucDanh.equals(adminMa)  || chucDanh.equals(phoPhongMa)) { %>
							<td colspan="1" style="float: right;">
										<button  class="button" type="button" style="width: 200px; height: 30px;" onclick="location.href='<%=siteMap.cscvManage + "?action=chiaSeCv&congVan=" + congVan.getCvId()%>'">
											<i class="fa fa-spinner"></i>&nbsp;&nbsp;Chia sẻ công văn
										</button>
									</td>
									<%} %>
						</tr>	
					</table>
				
					<br>
					<hr>
					</td></tr>
							<%} %>
							</table>
					<script type="text/javascript">
						
					</script>	


						</div>
					
						<div id="paging">
						<table style ="border-style: none;">
								<tr>
									<td>Trang</td>
									<td>
								<%
									long pageNum = size / 3;
									long p = (pageNum <= 10 ? pageNum : 10);	
									for (int i = 0; i <= p; i++) {
								%>
									<input type="button" name = "page" class="page" value="<%=i+1 %>" onclick="loadPage(<%=i%>)">
								<%}
									if(pageNum > 10) {
								%>
									<input type="button"  class="pageMove" value = "Sau >>" onclick = "loadPage('Next');">
								<%}%>	
								</td>
								</tr>
								</table>
						</div>
						<script type="text/javascript">$('.page')[0].focus();</script>
						<div class="group-button">
							<input type="hidden" name="action" value="update-yeu-cau">
							<%
								if (vanThuMa.equalsIgnoreCase(chucDanh) || chucDanh.equals(adminMa) || chucDanh.equals(thuKyMa)) {
							%>
							<button type="button" class="button" onclick="loadDataCv();">
								<i class="fa fa-plus-circle"></i>&nbsp;Thêm mới
							</button>
							<button type="button" class="button"title="Chỉ chọn một công văn để sửa"
								onclick="checkUpdate()">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
							</button>
							<button class="button" type="button" onclick="confirmDelete();">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
							</button>
							<%} %>
							<% if(chucDanh.equals(phoPhongMa) ||  chucDanh.equals(truongPhongMa) || chucDanh.equals(adminMa)) { %>
							<a href="<%=siteMap.bccvManage%>" target="_blank"><button class="button" type="button"">
							<i class="fa fa-print"></i>&nbsp;&nbsp;Báo cáo
							</button></a>
							<% } %>
<!-- 							<button class="button" "> -->
<!-- 								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa -->
<!-- 							</button> -->
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
						</div>
				</form>
				</div>
				</td>
				</tr>
				</table>
				<script type="text/javascript">
											$('.ttMaVtUpdate').bind('change', function() {
												var trangThaiVt = $(this).val(); 
												changeTrangThaiVt(trangThaiVt) ;
											}); 
											$('.ttMaUpdate').bind('change', function() {
												var trangThaiCv = $(this).val(); 
												changeTrangThaiCv(trangThaiCv) ;
											}); 
											
												$('.month').bind('change', function() {
													var checked = $( this ).is( ":checked" );
													var str = $(this).val();
													alert(str);
													var temp = str.split("#");
													alert(temp[0]);
														loadByYear(year, checked);
												});
										</script>
<!-- 				</div> -->

				<!--    		</form>  -->
				<!--                add-form-->
				<%if (chucDanh.equals(truongPhongMa) || chucDanh.equals(vanThuMa)  || chucDanh.equals(adminMa) || chucDanh.equals(thuKyMa)) { %>
				<form id="add-form" action="<%=siteMap.addCv %>" enctype="multipart/form-data" method="post">

					<div class="form-title">Thêm công văn</div>
					<div class="input-table">
						<table>
<!-- 							<tr style="margin-bottom: 20px;"> -->
<!-- 								<th colspan="1" style="text-align: left"><label for="soDen" style="text-align: left">Số đến</label></th> -->
<!-- 								<td colspan="3"><input type = "text" class="text" readonly value="123" style="background: #D1D1E0;" sise="5" name="soDen"></td> -->
<!-- 							</tr> -->
							<tr style="margin-bottom: 20px;">
								<th style="text-align: left" colspan="1"> <label for="cvSo" style="text-align: left">Số công văn đến: </label></th>
								<td colspan="1"><input type="text" class="text" name="cvSo" id="cvSo" onkeypress="changeSoCv();"><div id="requireSoCv" style="color: red"></div></td>
								<th style="text-align: left"><label for="ngayGoi" class="input">Ngày công văn đến: </label></th>
								<td><input type="date" class="text" name="ngayGoi" id="ngayGoi" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %> ></td>
							</tr>	
							<tr style="margin-bottom: 20px;">	
								
								<th style="text-align: left"><label for="ngayNhan" class="input">Ngày nhận: </label></th>
								<td><input type="date" class="text" name="ngayNhan" id="ngayNhan" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %> onkeypress="changeNgayNhan();"><div id="requireNgayNhan" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="mucDich" class="input">Mục
										đích</label></th>
								<td><select class="select" name="mucDich" id="mucDich" onchange="changeMucDich();">
										<option disabled selected value="">Chọn mục đích</option>
										<%for(MucDich mucDich : mucDichList) {%>
										<option value="<%=mucDich.getMdMa()%>" name="mucDich"><%=mucDich.getMdTen()%></option>
										<%} %>
								</select><div id="requireMucDich" style="color: red"></div></td>
								<th style="text-align: left;"><label
									for="noiGoi" class="input">Nơi gửi</label></th>
								<td><select class="select" name="donVi" id="noiGoi" onchange="changeDonVi();">
										<option selected disabled value="">Chọn nơi gởi</option>
										<%for(DonVi donVi : donViList) {%>
										<option value="<%=donVi.getDvMa()%>" ><%=donVi.getDvTen() %></option>
										<%} %>
								</select><div id="requireDonVi" style="color: red"></div></td>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="trichYeu" class="input">Nội dung công tác</label>
								<td colspan="3"><textarea class="txtarea" name="trichYeu"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="butPhe" class="input">Bút phê</label>
								<td colspan="3"><textarea class="txtarea" name="butPhe"></textarea></td>
							</tr>
							</tr>
								<th  style="text-align: left;"><label
										for="file" class="input" name="file">Đính kèm công văn: </label></th>
								<td colspan="3"><input type="file" id="file" name="file" onchange="changeFile();"><div id="requireFile" style="color: red"></div></td>
							<tr>	
							</tr>
								<th  style="text-align: left;"><label
										for="moTa" class="input" >Ghi chú: </label></th>
								<td colspan="3"><textarea class="txtarea" name="moTa" onkeypress="changeMoTa();"></textarea><div id="requireMoTa" style="color: red"></div></td>
							<tr>
						</table>
					</div>
					<div class="button-group">
						<input type="hidden" name="action" value="addCongVan">
						<button class="button" type="button"
							onclick="checkAdd();">
							<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="hideAddForm();">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>

				</form>
				<!--            update-form-->
				<form id="update-form" method="post" action="<%=siteMap.updateCv %>" enctype="multipart/form-data" acceptcharset="UTF-8">
				<div class="form-title">Sửa công văn</div>
					<div class="input-table">
						<div class="input-table">
						<table>
							<tr style="margin-bottom: 20px;">
								<th colspan="1" style="text-align: left"><label for="soDen" style="text-align: left">Số nhận</label></th>
								<td colspan="3"><input type = "text" class="text" value="123" readonly style="background: #D1D1E0;" sise="5" name="soDen"><input type = "hidden" value=""  name="cvId"></td>
							</tr>
							<tr style="margin-bottom: 20px;">
								<th style="text-align: left" colspan="1"> <label for="cvSo" style="text-align: left">Số công văn đến: </label></th>
								<td colspan="3"><input type="text" class="text" name="cvSo" id="cvSo" readonly style="background: #D1D1E0;"></td>
							</tr>	
							<tr style="margin-bottom: 20px;">	
								<th style="text-align: left"><label for="ngayGoi" class="input">Ngày công văn đến: </label></th>
								<td><input type="date" class="text" name="ngayGoiUpdate" id="ngayGoi" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %>></td>
								<th style="text-align: left"><label for="ngayNhan" class="input">Ngày nhận: </label></th>
								<td><input onchange="changeNgayNhanUp();" type="date" class="text" name="ngayNhanUpdate" id="ngayNhan" value=<%=DateUtil.convertToSqlDate(new java.util.Date()) %>>
								<div id="requireNgayNhanUp" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left"><label for="mucDich" class="input">Mục
										đích</label></th>
								<td><select class="select" name="mucDichUpdate" id="mucDichUpdate" onchange="changeMucDichUp();">
										<option disabled selected value="">Chọn mục đích</option>
										<%for(MucDich mucDich : mucDichList) {%>
										<option value="<%=mucDich.getMdMa()%>" name="mucDich"><%=mucDich.getMdTen()%></option>
										<%} %>
								</select><div id="requireMucDichUp" style="color: red"></div></td>
								<th style="text-align: left;"><label
									for="noiGoi" class="input">Nơi gửi</label></th>
								<td><select class="select" name="donViUpdate" id="noiGoi" onchange="changeDonViUp();">
										<option selected disabled value="">Chọn nơi gởi</option>
										<%for(DonVi donVi : donViList) {%>
										<option value="<%=donVi.getDvMa()%>" ><%=donVi.getDvTen() %></option>
										<%} %>
								</select><div id="requireDonViUp" style="color: red"></div></td>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="trichYeu" class="input">Nội dung công tác</label>
								<td colspan="3"><textarea class="txtarea" name="trichYeuUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;" colspan="1"><label id="butPhe" class="input">Bút phê</label>
								<td colspan="3"><textarea class="txtarea" name="butPheUpdate"></textarea></td>
							</tr>
							<tr>
								<th style="text-align: left;"><label for="file" class="input" name="file">Tệp đính kèm: </label></th>
								<td colspan="1"><a id="linkCv" style="text-decoration: underline; color: blue; cursor: pointer;"></a></td>
								<th >Tải tệp mới</th>
								<td><input type="file" id="file" name="file" onchange="changeFileUp();"><div id="requireFileUp" style="color: red"></div></td>
							</tr>
							<tr>
								<th style="text-align: left;"><label for="moTa" class="input" name="moTa">Ghi chú: </label></th>
								<td colspan="5"><textarea class="txtarea" name="moTa" ></textarea></td>
							</tr>
							
						</table>
						
					</div>
						
					</div>
					<div class="group-button">
						<input type="hidden" name="action" value="updateCv">
						<button class="button" type="button"
							onclick="checkUp();">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="hideUpdateForm();">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
				<%} %>
						</div>
			</div>
		</div>		
		<%} catch (NullPointerException e){
			response.sendRedirect("login.jsp");
		}
			%>
</body>
</html>