<%@page import="model.NguoiDung"%>
<%@page import="model.YeuCau"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="model.VatTu"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.TrangThai"%>
<%@page import="model.MucDich"%>
<%@page import="model.DonVi"%>
<%@page import="util.DateUtil"%>
<%@page import="model.CongVan"%>
<%@page import="model.File"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>

<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link rel="stylesheet" href="style/style-vat-tu.css" type="text/css">
<link rel="stylesheet" href="style/jquery.autocomplete.css" type="text/css">
<link href="style/style-yeu-cau.css"type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-menu-tree.css" type="text/css">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script src="js/jsapi.js"></script>  
	<script>  
		google.load("jquery", "1");
	</script>
	<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/yeu-cau-vat-tu.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String soCv = request.getServletContext().getInitParameter("soCv");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		CongVan congVan = (CongVan) session.getAttribute("congVan");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.cvManage+ "?action=manageCv");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    	ArrayList<CTVatTu> ctVatTuList = (ArrayList<CTVatTu>) request.getAttribute("ctVatTuList");
		if (ctVatTuList ==  null) {
			int index = siteMap.cvManage.lastIndexOf("/");
    		String url = siteMap.cvManage.substring(index);
    		RequestDispatcher dispatcher =  request.getRequestDispatcher(url+"?action=manageCv");
    		dispatcher.forward(request, response);
			return;
		}
		ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) request.getAttribute("yeuCauList");
		ArrayList<NoiSanXuat> nsxList = (ArrayList<NoiSanXuat>) request.getAttribute("nsxList");
		ArrayList<ChatLuong> chatLuongList = (ArrayList<ChatLuong>) request.getAttribute("chatLuongList");
		ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
		Long pageNum = (Long) request.getAttribute("page");
		Integer cvId = (Integer) session.getAttribute("cvId");
		if (cvId == null)
			response.sendRedirect(siteMap.congVan);
    %>
	<div class="wrapper">
		<div class="header">
			<!--
					<img src="img/logo.png" alt="" id="logo" width=80 height=80/><br/>
					<img src="img/textlogo.png" alt="" id="logo" width=80 height=20/>
	-->
			<div id="top_title">Văn phòng điện tử</div>
			<div id="bottom-title">Công ty điện lực cần thơ</div>
			<div class="search_form" id="search">
				<form action="" method="post">
					<!--
							<span class="search-select">
								<select name="" ><option disabled selected>--Tùy chọn kiếm kiềm--</option></select>
								<option value=""></option>
							</span>
-->
					<!--							<span class="bg-search">-->
					<span class="search-text"> &nbsp; <input type="search"
						class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" />
					</span> <span class="search-button"> &nbsp;
						<button class="btn-search">
							<i class="fa fa-search"></i>
						</button>
					</span>
					<!--                            </span>-->
				</form>
			</div>

		</div>
		<div class="main_menu">
					<ul>
						<li><a href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						
						<li><a>Danh mục</a>
							<ul>
										<li><a href="<%=siteMap.nsxManage + "?action=manageNsx"%>">Danh
												mục nơi sản xuất</a></li>
										<li><a href="<%=siteMap.clManage + "?action=manageCl"%>">Danh
												mục chất lượng</a></li>
										<li><a href="<%=siteMap.vattuManage + "?action=manageVattu"%>">Danh
												mục vật tư</a></li>
										<li><a href="<%=siteMap.ctvtManage + "?action=manageCtvt"%>">Vật tư tồn kho</a></li>
										<li><a href="<%=siteMap.bpsdManage +  "?action=manageBpsd"%>">Danh
												mục bộ phận sử dụng</a></li>
										<li><a href="<%=siteMap.mdManage + "?action=manageMd"%>">Danh
												mục mục đích</a></li>
										<li><a href="<%=siteMap.vtManage + "?action=manageVt"%>">Danh mục vai trò</a></li>
										<li><a href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
										<li><a href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
												mục chức danh</a></li>
										
									</ul>
						</li>
						<%} %>
							<li><a href="<%=siteMap.cvManage+ "?action=manaxgeCv" %>">Công văn</a></li>
							<%if (!chucDanh.equalsIgnoreCase(vanThuMa)){ %>
							<li><a>Báo cáo</a>
								<ul>
									<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo vật tư thiếu</li>
									<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo bảng đề nghị cấp vật tư</li>
								</ul>
							</li>
							<%} %>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						<li><a>Quản lý người dùng</a>
							<ul>
								<li><a href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
								<li><a href="<%=siteMap.updateNguoiDung%>"/>Cập nhật thông tin</li>
								<li><a href="<%=siteMap.resetPassword%>"/>Khôi phục mật khẩu</li>
								<li><a href="<%=siteMap.lockNguoiDung%>"/>Khóa tài khoản</li>
								<li><a href="<%=siteMap.resetNguoiDung%>"/>Khôi phục tài khoản</li>
							</ul>
						</li>
						<%} %>
						<li><a>Tài khoản</a>
							<ul>
								<li><a href="<%=siteMap.changePassPage + ".jsp"%>">Đổi mật khẩu</a></li>
								<li><a href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
							</ul>
						</li>		
					</ul>
					<div class="clear"></div>
				</div>
		<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div>

		<div id="main-content">
			<form id="add-yeu-cau-form">
<%--  			<div style="color: #CC3333;height:20px;text-align: right;margin-right: 40px;">Số công văn:&nbsp;<%=congVan.getCvSo() %></div> --%>
			
			<div class="form-title-vat-tu">Danh sách vật tư</div>
			<div id="yc-table">
				<table style="margin-bottom: 10px;">		
					<tr>		
					<td  style="text-align: center; font-size: 19px;color:#6600FF;">* Tìm kiếm mã</td>
								<td>
									<div class="search_form1" id="search">		
										<form id ="searchForm" onsubmit="return false;">	
<!-- 											<span class="search-text"> &nbsp; <input type="search" class="text" name="search_box" name="search" placeholder="Tìm kiếm" /> 												 -->
<!-- 												<td><input type="checkbox" class="checkbox" style="text-align: center;"/></td> -->
<!-- 												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp; -->
<!-- 											</span> -->
											
											<span> &nbsp; <input type="search" id="searchName" class="text-search" name="vattu"/>						
														<script>
														$("#searchName").autocomplete("getdataMa.jsp");
														$("#searchName").autocomplete("getdata.jsp");	
														</script> 												
												<td><input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/></td>
												<td  style="text-align: center; color:#6600FF; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp;
											</span>
												<td> <span class="search-button"> &nbsp; <button type="button" id="search-button" class="btn-search" style="background-color: #00A69B;" ><i class="fa fa-search"></i></button></span></td>						
										</form>
									</div>
					</tr>					
				</table>
				</div>
				<form id="danh-sach-vat-tu">
				<div id="view-search">
				<div id="view-table-ds">
				<table style="width:1024px;margin:0 auto;">
<!-- 					<tr><th >Ma vat tu</th><th >Ten vat tu</th><th >Noi san xuat</th><th >Chat luong</th><th >Don vi tinh</th><th ></th></tr> -->
					<tr style="background-color: #199e5e">
						<th style="text-align: center;" >Mã vật tư</th>
						<th style="text-align: center;" >Tên vật tư</th>
						<th style="text-align: center;">Nơi sản xuất</th>
						<th style="text-align: center;" >Chất lượng</th>
						<th style="text-align: center;" >Đơn vị tính</th>
						<th style="text-align: center;" >Số lượng tồn</th>
						<th style="text-align: center;">Thêm</th></tr>
					<tr></tr>
					<%
						int countCtvt = 0;
						for(CTVatTu ctVatTu : ctVatTuList) { 
							countCtvt++;
							VatTu vatTu = ctVatTu.getVatTu();
							NoiSanXuat nsx = ctVatTu.getNoiSanXuat();
							ChatLuong chatLuong = ctVatTu.getChatLuong();
						%>
						<tr id="row" class = "rowContent" <%if (countCtvt % 2 == 1) out.println("style=\"background : #CCFFFF;\"");%>>
							<td style="text-align: center;"><%=vatTu.getVtMa() %></td>
							<td><%=vatTu.getVtTen() %></td>
							<td style="text-align: center;"><%=nsx.getNsxTen() %></td>
							<td style="text-align: center;"><%=chatLuong.getClTen() %></td>
							<td style="text-align: center;"><%=vatTu.getDvt().getDvtTen() %></td>
							<td style="text-align: center;"><%=ctVatTu.getSoLuongTon() %></td>
							<td style="text-align: center;"><input class="radio"  type="radio" id="a" name="ctvtId" value="<%=ctVatTu.getCtvtId() %>" onchange="preAddSoLuong();"> </td>
							
						</tr>
					<%}%>
				</table>
				</div>
				</div>
					<div id = "paging" >
				<table style ="border-style: none;">
								<tr>
									<td>Trang</td>
									<td>
									<%
										String str = "";
										long p = (pageNum < 10 ? pageNum : 10);
									for(int i = 0; i < p; i++) {
										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick= \"loadPageCtvtYc(" + i +")\">&nbsp;";
									}
									if (pageNum > 10)
								str += "<input type=\"button\" value=\">>\" onclick= \"loadPageCtvtYc(\'Next\');\">";
									out.println(str);	
								%>
								</td>
								</tr>
								</table>
					</div>
			</form>				
			</form>
			<form id="main-form">
			<div class="form-title-vat-tu" style="padding-top: 10px;">Yêu cầu vật tư còn thiếu cho công văn số <%=congVan.getSoDen() %> nhận ngày <%=congVan.getCvNgayNhan() %></div> 
					<div id="view-table-yc" class="scroll-vat-tu">
							<table style= "width:1024px; margin: 0 auto;" >
								<tr>
									<th class="a-column"style= "text-align: center;">Chọn</th>
									<th class="b-column" style="text-align: center;">Mã vật tư</th>
									<th class="c-column"style="text-align: center;">Tên vật tư</th>
									<th class="e-column"style="text-align: center;">Nơi sản xuất</th>
									<th class="f-column"style="text-align: center;">Chất lượng</th>
									<th class="g-column"style="text-align: center;">Đơn vị tính</th>
									<th class="d-column"style="text-align: center;">Số lượng tồn</th>
									<th class="d-column"style="text-align: center;">Số lượng thiếu</th>
									<th >Đã cấp</th>
								</tr>
								<%
									int count = 0;
									for(YeuCau yeuCau : yeuCauList) {count++;
									CTVatTu ctVatTu = yeuCau.getCtVatTu();
									NoiSanXuat nsx = ctVatTu.getNoiSanXuat();
									VatTu vatTu = ctVatTu.getVatTu();
									ChatLuong chatLuong = ctVatTu.getChatLuong();
								%>
								<tr <%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> id="<%=yeuCau.getYcId()	%>">
									<td><input id="<%=yeuCau.getYcId() %>" type="checkbox" class="checkbox" name = "yeuCau" value=<%=yeuCau.getYcId()%>> </td>
									<td><%=vatTu.getVtMa()%></td>
									<td><%=vatTu.getVtTen()%></td>
									<td><%=nsx.getNsxTen()%></td>
									<td><%=chatLuong.getClTen()%></td>
									<td><%=vatTu.getDvt().getDvtTen()%></td>
									<td><%=yeuCau.getCtVatTu().getSoLuongTon()%></td>
									<td id="soLuong<%=yeuCau.getYcId()%>"><%=yeuCau.getYcSoLuong()%></td>
									<td id="soLuongCap<%=yeuCau.getYcId()%>"><%=yeuCau.getCapSoLuong()%></td>
								<%} %>
							</table>
							</div>
							<div class="group-button">
								<button type="button" class="button" id="pre-update-yc">
									<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
								</button>
								<button type="button" class="button" id="preCapVatTu">
									<i class="fa fa-refresh"></i>&nbsp;&nbsp;Cấp phát
								</button>
								<button class="button" type="button" onclick="return confirmDelete();">
									<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
								</button>
								
								<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.congVan+".jsp" %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
							</div>
				</form>
			<form id="add-so-luong-form" onsubmit="return false">
			<div class="form-title" style="margin-top: 10px;">Thêm yêu cầu vật tư</div>
			<div id="view-table-them">
			<table style= "width:900px; margin: 0 auto;margin-top: 10px;"  >
				<tr><th >Mã vật tư</th><th >Tên vật tư</th><th >Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng tồn</th><th >Số lượng thiếu</th></tr>
				<tr>
					<td><div id="vtMaAdd"></div></td>
					<td><div id="vtTenAdd"></div></td>
					<td><div id="clTenAdd"></div></td>
					<td><div id="nsxTenAdd"></div></td>
					<td><div id="dvtAdd"></div></td>
					<td><div id="soLuongTonAdd"></div></td>
<!-- 					<td><div id="vtMaAdd"></div></td> -->

					<td><input type="number" min=0 autofocus  name="soLuongAdd" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" onclick="addSoLuong();">Thêm</button></td>
				</tr>
			</table>
			</div>
			
			</form>
			<br>
			<br>
			<br>
			<br>
			<form id="update-so-luong-form" onsubmit="return false">
			<div class = "form-title" style="margin-top: 10px;">Thay đổi số lượng thiếu</div>
			<div id="view-table-doi" class="scroll-vat-tu">
			<table style= "width:900px; margin: 0 auto;" >
				<tr><th >Mã vật tư</th><th >Tên vật tư</th><th >Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng tồn</th><th >Số lượng thiếu</th></tr>
				<tr>
					<td><div id="vtMaUpdate"></div></td>
					<td><div id="vtTenUpdate"></div></td>
					<td><div id="clTenUpdate"></div></td>
					<td><div id="nsxTenUpdate"></div></td>
					<td><div id="dvtUpdate"></div></td>
					<td><div id="soLuongTonUpdate"></div></td>
					<td><input type="number" min=0 autofocus  name="soLuongUpdate" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" id="updateYc">Lưu lại</button></td>
				</tr>
			</table>
			</div>
			</form>
			<form id="cap-so-luong-form" onsubmit="return false">
			<div class = "form-title"style="margin-top: 10px;">Cấp phát vật tư</div>
			<div id="view-table-cap" class="scroll-vat-tu">
			<table style= "width:900px; margin: 0 auto;" >
				<tr><th >Mã vật tư</th><th>Tên vật tư</th><th>Nơi sản xuất</th><th >Chất lượng</th><th >Đơn vị tính</th><th >Số lượng tồn</th><th>Số lượng thiếu</th></tr>
				<tr>
					<td><div id="vtMaCap"></div></td>
					<td><div id="vtTenCap"></div></td>
					<td><div id="clTenCap"></div></td>
					<td><div id="nsxTenCap"></div></td>
					<td><div id="dvtCap"></div></td>
					<td><div id="soLuongTonCap"></div></td>
					<td><input type="number" min=0 autofocus  name="soLuongCap" title="So luong phai la so!!!"  class="text" style="width: 80px;"></td>
					<td><button class="button" type="button" id="capVatTu">Lưu lại</button></td>
				</tr>
			</table>
			</div>
			</form>
			</div>
			</div>
</body>
</html>
