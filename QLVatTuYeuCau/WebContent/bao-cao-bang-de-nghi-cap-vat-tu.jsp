<%@page import="util.DateUtil"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.DonVi"%>
<%@page import="model.CongVan"%>
<%@page import="model.YeuCau"%>
<%@page import="model.TrangThai"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.HashMap"%>
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
	href="style\font-awesome-4.3.0\font-awesome-4.3.0\css\font-awesome.min.css"
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
	<script type="text/javascript" src="js/location.js"></script>
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
    			request.setAttribute("url", siteMap.bcbdnManage+ "?action=manageBcbdn");
    			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
    			dispatcher.forward(request, response);
    			return;
    		}
    		String chucDanh = nguoiDung.getChucDanh().getCdMa();
    	%>
	<%
	
	ArrayList<DonVi> listDonVi = (ArrayList<DonVi>) session.getAttribute("donViList");
	if (listDonVi ==  null) {
		int index = siteMap.bcbdnManage.lastIndexOf("/");
		String url = siteMap.cvManage.substring(index);
		RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageBcbdn");
		dispatcher.forward(request, response);
		return;
	}
	ArrayList<TrangThai> listTrangThai = (ArrayList<TrangThai>) session.getAttribute("trangThaiList");
	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) session.getAttribute("congVanList");
	HashMap<Integer, ArrayList<YeuCau>> yeuCauHash = (HashMap<Integer, ArrayList<YeuCau>>) session.getAttribute("yeuCau");
    %>
  
	<div class="wrapper">
		<div class="header">
			<div id="top_title">Văn phòng điện tử</div>
			<div id="bottom-title">Công ty điện lực cần thơ</div>
			<div class="search_form" id="search">
				<form action="" method="post">
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
							<li><a>Báo cáo vật tư thiếu</a>
								<ul>
									<li><a href="<%=siteMap.bcvttManage+ "?action=manageBcvtt" %>"/>Báo cáo tổng hợp vật tư thiếu</li>
									<li><a href="<%=siteMap.bcbdnManage+ "?action=manageBcbdn" %>"/>Báo cáo chi tiết vật tư thiếu</li>
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
		<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=nguoiDung.getHoTen() %></b></div>
		<div id="main-content">
			<div id="title-content"style="margin-bottom: 10px;">Báo cáo chi tiết vật tư thiếu</div>
			<div id="content">
			<form id="option-form" method="get" action ="<%=siteMap.bcbdnManage %>">
			<fieldset style="background-color:#dceaf5;width:750px;margin:0 auto;">
			
				<table style="margin:0 auto; margin-top: 30px;">
					<tr>
                            <th style="text-align: left;margin-top: 10px;padding-right:10px;" >Thời gian:</th>
                            <td style="text-align: left;margin-top: 10px;" colspan="2" >Từ ngày &nbsp;
                            <input type="date" class="text"name="ngaybd">
                            &nbsp;&nbsp;&nbsp;&nbsp; đến&nbsp;
                            <input type="date" class="text"name="ngaykt"></td>
                    </tr>
					<tr>
						<th style="text-align: left;margin-top: 10px;padding-right:10px;">Đơn vị:</th>
						<td style="text-align: left">
						<select 
							title="" class="select" id="donvi" name="donvi" style="margin-top: 10px;">
								<option disabled selected value="">-- Chọn đơn vị --</option>
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
				<table class="radio" style="margin-top: 20px;margin:0 auto;">
					<th style="text-align: left;margin-top: 20px;padding-right:50px;">Trạng thái:</th>				  
 								
 								<td style="text-align: right;"><input type="radio" name="trangthai" value="CGQ"></td>
								<td style="text-align: left;"><label class="lable1" for="CGQ">Chưa giải quyết</label></td>
								<td style="text-align: right;"><input type="radio" name="trangthai" value="DGQ"></td>
								<td style="text-align: left;"><label class="lable1" for="CGQ">Đang giải quyết</label></td>
								<td style="text-align: right;"><input type="radio" name="trangthai" value="DaGQ"></td>
								<td style="text-align: left;"><label class="lable1" for="CGQ">Đã giải quyết</label></td>
 						
  					<td style="text-align: right;"><input type="radio"name="trangthai" value="all"></td>
								<td style="text-align: left;"><label class="lable1" for="CGQ">Tất cả</label></td>
				</table>
				<input type="hidden" name="action" value="baocaobdn">
				<input style="margin-top: 15px;"class="button" type="submit" value="Xem">
<!-- 					<i class="fa fa-eye"></i>&nbsp;&nbsp;</> -->
				<br>
				<br>
				</fieldset>
				</form>
			</div>
			<div id="view-table" style="smax-height: 420px;width: 1450px;display: auto;border: 1px solid #CCCCCC;margin: 0 auto;margin-top: 20px;overflow: scroll;">
				<table style="width:1400px;" >
					<tr bgcolor="lightgreen">
<!-- 						<th style="width: 50px;">Số đến</th> -->
						<th style="width: 50px;">Ngày nhận</th>
						<th style="width: 50px;">Mã vật tư</th>
						<th style="width: 350px;">Tên vật tư</th>
<!-- 						<th style="width: 100px;">Nơi sản xuất</th> -->
						<th style="width: 50px;">Đvt</th>
						<th style="width: 50px;">Số lượng</th>
						<th style="width: 100px;">Trạng thái</th>
						<th style="width: 100px;">Đơn vị yêu cầu</th>
						<th style="width: 200px;">Nội dung công tác</th>
						
						
					</tr>
							<%
							if(yeuCauHash != null) {
							 int cnt = 0;
							for(CongVan congVan  : congVanList) { 
							ArrayList<YeuCau> yeuCauList = yeuCauHash.get(congVan.getCvId());
							for (YeuCau yeuCau : yeuCauList) {cnt++;
							%>
									
					<tr
						<%if (cnt % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
<%-- 						<td style="width: 50px; text-align: center;"><%=congVan.getSoDen() %></td> --%>
						<td style="width: 50px; text-align: center;"><%=DateUtil.toString(congVan.getCvNgayNhan()) %></td>
<%-- 						<td style="width: 50px; text-align: center;"><%=congVan.getSoDen() %></td> --%>
<%-- 						<td style="width: 100px; text-align: center;"><%=congVan.getCvNgayNhan() %></td> --%>
						<td style="width: 50px; text-align: center;"><%=yeuCau.getCtVatTu().getVatTu().getVtMa() %></td>
						<td style="text-align: left; width: 300px;"><%=yeuCau.getCtVatTu().getVatTu().getVtTen() %></td>
<%-- 						<td style="text-align: left; width: 100px;"><%=yeuCau.getCtVatTu().getNoiSanXuat().getNsxTen() %></td> --%>
						<td style="width: 50px;text-align: center;"><%=yeuCau.getCtVatTu().getVatTu().getDvt().getDvtTen() %></td>
						<td style="width: 50px; text-align: center;"><%=yeuCau.getYcSoLuong() %></td>
						<td style="text-align: left; width: 100px;"><%=congVan.getTrangThai().getTtTen() %></td>
						<td style="text-align: left; width: 150px;"><%=congVan.getDonVi().getDvTen()%></td>
						<td style="text-align: left; width: 100px;"><%=congVan.getTrichYeu()%></td>
						

					</tr>
					<%} }}%>
				</table>
				</div>
				
				<div class="group-button">
					&nbsp;&nbsp;
					<button class="button" type="button" onclick="location.href='<%=siteMap.xuatBangDeNghi+".jsp"%>'">
						<i class="fa fa-print"></i>&nbsp;&nbsp;Xuất file
					</button>
					&nbsp;&nbsp;
					<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
		</div>
		</div>
		</table>
	
</body>
</html>
