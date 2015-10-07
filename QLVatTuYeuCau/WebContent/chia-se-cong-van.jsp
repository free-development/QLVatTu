<%@page import="java.util.HashMap"%>
<%@page import="model.CongVan"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.VaiTro"%>
<%@page import="dao.VaiTroDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css"
	type="text/css">	
<link rel="stylesheet" href="style/style-chia-se.css" type="text/css">
<!-- <link href="style/style-vat-tu.css" type="text/css" -->
<!-- 	rel="stylesheet"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="style/jquery.autocomplete.css" />
	<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script src="js/jsapi.js"></script>  
	<script>  
		google.load("jquery", "1");
	</script>
	<script src="js/jquery.autocomplete.js"></script>
	<style>
		input {
			font-size: 120%;
		}
	</style>
	<script type="text/javascript">
	
	</script>
</head>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<!--		<script type="text/javascript" src="js/check.js"></script>-->
<!-- <script type="text/javascript" src="js/jquery-1.6.3.min.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.min.js"></script> -->
<!-- <script type="text/javascript" src="js/nguoidung.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
<script type="text/javascript" src="js/chia-se-cong-van.js"></script>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!-- <link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" /> -->
<!-- <script type="text/javascript" src="js/jquery.min.js"></script> -->
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
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
	
	ArrayList<VaiTro> vaiTroList = (ArrayList<VaiTro>) session.getAttribute("vaiTroList");
	if (vaiTroList ==  null) {
		int index = siteMap.bcbdnManage.lastIndexOf("/");
		String url = siteMap.cvManage.substring(index);
		RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageCv");
		dispatcher.forward(request, response);
		return;
	}
	ArrayList<NguoiDung> nguoiDungList = (ArrayList<NguoiDung>) session.getAttribute("nguoiDungList");
	CongVan congVan = (CongVan) session.getAttribute("congVan");
	HashMap<String,NguoiDung> vtNguoiDungHash = (HashMap<String,NguoiDung>) request.getAttribute("vtNguoiDungHash");
	HashMap<String, HashMap<Integer, VaiTro>> vaiTroHash = (HashMap<String, HashMap<Integer, VaiTro>>) request.getAttribute("vaiTroHash");
	//Long pageNum = (Long) request.getAttribute("page");
	%>
	<div class="wrapper">
		<div class="header">
			<!--
					<img src="img/logo.png" alt="" id="logo" width=80 height=80/><br/>
					<img src="img/textlogo.png" alt="" id="logo" width=80 height=20/>-->
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

					<span class="search-text"> &nbsp; <input type="search"
						class="search" name="search_box" name="search"
						placeholder="Tìm kiếm" />
					</span> <span class="search-button"> &nbsp;
						<button class="btn-search">
							<i class="fa fa-search"></i>
						</button>
					</span>
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
							<li><a href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
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
				<form id="main-form" action="<%=siteMap.updateChiaSeCv%>" method="get">
				<div id="title-content">Chia sẻ công văn</div>
				
					<div id="input-table" style="width: 1024px; margin: 0 auto;margin-top: 10px;">
						<table style="background-color: #CCFFFF;width: 800px;">
							<tr >
								<th style="text-align: left">Số công văn:</th>
								<td class="b-column"><%=congVan.getCvSo() %></td>
								<th class="c-column">Ngày đến:</th>
								<td class="b-column"><%=congVan.getCvNgayNhan() %></td>
								<th class="e-column">Người lập phiếu:</th>
								<td class="f-column">NV002</td>
							</tr>
						</table>
					</div>
					<table style="margin-bottom: 20px;margin: 0 auto;width:800px;">		
					<tr>		
					<th  style="text-align: center; color: black; font-size: 19px;">*Tìm kiếm mã</th>
								<td>
									<div class="search_form1" id="search">		
										
										
										<form>												
											<span> &nbsp; <input type="search" id="searchName" class="text-search" name="nguoidung"/>						
														 												
												<td><input type="checkbox" value="check" class="checkbox" style="text-align: center;" id="checkTen"/></td>
												<td  style="text-align: center; color: black; font-size: 19px;">Theo tên</td>&nbsp;&nbsp;&nbsp;
											</span>
											
												<td> <span class="search-button"> &nbsp; <button type="button" class="btn-search" style="background-color: #00A69B;" onclick="timKiemNguoidungCs()"><i class="fa fa-search"></i></button></span></td>						
										</form>
										<script>
														$('#searchName').autocomplete("getdataMsnv.jsp");
														$('#searchName').autocomplete("getdataHoten.jsp");	
														</script>
									</div>
									</td>
					</tr>					
				</table>
					<div id="view-table" class="scroll-chia-se">
						<table style="width:1024px;"> 
							<tr style="background-color: #199e5e;">

								<th style="width: 100px;">Mã nhân viên</th>
								<th style="width: 200px;">Tên nhân viên</th>
								<%for (VaiTro vaiTro : vaiTroList) {%>
								<th class="thead-vaitro" style="max-width: 300px;"><%=vaiTro.getVtTen() %></th>
								<%} %>
<!-- 								<th class="four-column">Lập phiếu</th> -->
<!-- 								<th class="five-column">Cập nhật vật tư</th> -->
<!-- 								<th class="six-column">...</th> -->

							</tr>
							<% int count = 0;
							for(NguoiDung nguoiDung : nguoiDungList) { 
								count ++;
								String msnv = nguoiDung.getMsnv();
							%>
							<tr id=<%=nguoiDung.getMsnv() %> class = "rowContent" <% if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%> id="<%=nguoiDung.getMsnv() %>">
								<td class="tbody-nguoidung"><%=nguoiDung.getMsnv() %></td>
								<td class="tbody-nguoidung"><%=nguoiDung.getHoTen() %></td>
								<% for(VaiTro vaiTro : vaiTroList) {
									int vtId = vaiTro.getVtId();
									HashMap<Integer, VaiTro> vtHash = vaiTroHash.get(msnv);
									boolean check = false;
									if (vtNguoiDungHash.get(msnv) != null && vtHash.get(vtId) != null)
										check = true;
								%>
								<td class="checkbox" style="text-align: center;">
									<input type="checkbox" name="vaiTro" <%if (check) out.print("checked "); %> value="<%	out.print(msnv + "#" + vtId); %>" >
								</td>
								<%} %>
							</tr>
							<%} %>
						</table>
					</div>
<!-- 					<div id = "paging" > -->
<%-- 									<% --%>
<!-- // 										String str = ""; -->
<!-- // 										String pages = "";  -->
<!-- // 										long p = (pageNum < 10 ? pageNum : 10); -->
<!-- // 									for(int i = 0; i < p; i++) { -->
<!-- // 										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick= \"loadPageCscv(" + i +")\">&nbsp;"; -->
<!-- // 									} -->
<!-- // 									if (pageNum > 10) -->
<!-- // 								str += "<input type=\"button\" value=\">>\" onclick= \"loadPageCscv(\'Next\');\">"; -->
<!-- // 									out.println(str);	 -->
<%-- 								%> --%>
<!-- 					</div> -->
					<div class="group-button">
					<input type="hidden" value="save" name="action">
						<button class="btn">
							<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
						</button>
						<button type="reset" class="btn">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						<button type="button" id="print_button" class="button"  onclick="location.href='<%=siteMap.congVan+".jsp" %>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
					</div>
				</form>
				
				<div id="view-chia-se">
				<form action="">
				<%
					if (vtNguoiDungHash.size() != 0 || vtNguoiDungHash == null) {
				%>
				<div id="title-content">Công việc đã chia sẻ</div>
				<div id="view-table-chia-se" class="scroll-cs">
					<table style="width:1024px;">
						<tr bgcolor= "#199e5e">
						<th style="text-align: center;">Chọn</th>
						<th>Msnv</th><th>Họ tên</th><th>Vai trò</th>
						</tr>
						<%
							int i = 0;
							for(String msnv :  vtNguoiDungHash.keySet()) {
								HashMap<Integer, VaiTro> vtHash = vaiTroHash.get(msnv);
								NguoiDung nguoiDung =  vtNguoiDungHash.get(msnv);
								String hoTen = nguoiDung.getHoTen();
								i++;
						%>
						<tr  <% if (i % 2 ==0) out.println("style=\"background : #CCFFFF;\"");%> >
							<td style="text-align: center;"><input type = "checkbox" class="checkbox" name = "msnv" value="<%=msnv%>"></td>
							<td><%=msnv %></td>
							<td><%=hoTen %></td>
							<td id="vaiTro<%=msnv%>">
								<%
									StringBuilder str1 = new StringBuilder("");
									for(Integer vtId : vtHash.keySet()) {
										VaiTro vaiTro = vtHash.get(vtId);
										str1.append(vaiTro.getVtTen() + "<br>");
									}
									int end = str1.length();
									str1.delete(end - 4, end);
									out.println(str1.toString());
									
								%>
							</td>
						</tr>
						<%}%>
					</table>
					</div>
					<div class="group-button">
					<input type="hidden" value="save" name="action">
						<button class="button" id="update" type="button">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
						</button>
						<button type="reset" class="button" type="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						
					</div>
				</form>
				</div>
				<div style="color: red; text-align: center;">
				<%}%>
				</div>
			</div>
			<div id="update-form" style="top:30%;position:absolute;width:900px;left:20%;" >
				<div id="view-table-cap-nhat"class="scroll">
				<div id="title-content" style="margin-bottom: 10px;">Cập nhật chia sẻ</div>
				<table style="width:1024px;"></table>
				</div>
				<div class="group-button" id="updateButton">
				<button type="button" class="button" id="updateCs">Lưu lại</button> 
				</div>
			</div>
			</div>
</body>
</html>