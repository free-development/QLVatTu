<%@page import="model.NguoiDung"%>
<%@page import="model.ChucDanh"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset= UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
<!-- <link href="style/style-chat-luong.css" type="text/css" rel="stylesheet"> -->
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script >
    $(document).ready(function() {
        $('.checkAll').click(function(event) {  //on click 
            if(this.checked) { // check select status
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = true;  //select all checkboxes with class "checkbox1"               
                });
            }else{
                $('.checkbox').each(function() { //loop through each checkbox
                    this.checked = false; //deselect all checkboxes with class "checkbox1"                       
                });         
            }
        });
        
    });
	</script>
<script type="text/javascript" src="js/chucdanh.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.cdManage + "?action=manageCd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<ChucDanh> listChucDanh = (ArrayList<ChucDanh>) request.getAttribute("chucDanhList");
			if (listChucDanh ==  null) {
				int index = siteMap.cdManage.lastIndexOf("/");
				String url = siteMap.cdManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url +  "?action=manageCd");
				dispatcher.forward(request, response);
				return;
			}
			Long size = (Long) request.getAttribute("size");
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
		<div id="title-content">Danh mục chức danh</div>
		<div id="main-content">

			<form id="main-form">
				<div id="view-table" style="margin: 0 auto;">
					<table>
						<tr style="background-color: #199e5e;">
							<td class="left-column"><input type="checkbox" name=""
								class="checkAll"></td>
							<th class="mid-column">Mã chức danh</th>
							<th class="right-column">Tên chức danh</th>

						</tr>
						<%
							if(listChucDanh != null) {
							int count = 0;
							for(ChucDanh cd : listChucDanh) { count++;%>
						<tr class="rowContent"
							<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
							<td class="left-column"><input type="checkbox" name="cdMa"
								value="<%=cd.getCdMa() %>" class="checkbox"></td>
							<td class="col"><%=cd.getCdMa() %></td>
							<td class="col"><%=cd.getCdTen() %></td>
						</tr>
						<%} }%>
					</table>
				</div>
				
				<div id = "paging" >
							<table style ="border-style: none;">
								<tr>
								<%long pageNum = size / 10;
								long du = size % 10;
								if(pageNum >0){ %>
								<td>Trang</td>
									<td>
										<%
											
											for(int i = 0; i <= pageNum; i++) { %>
												<input type="button" value="<%=i+1%>" class="page">
										<%} }%>
									</td>
								</tr>
							</table>
						</div>
				
				<div class="group-button">
					<input type="hidden" name="action" value="deleteCd">
					<button type="button" class="button"
						onclick="showForm('add-form', true)">
						<i class="fa fa-plus-circle"></i>&nbsp;Thêm
					</button>
					<button type="button" onclick="preUpdateCd('update-form', true)"
						class="button" title="Chọn 1 chất lượng để thay đổi">
						<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
					</button>
					<button class="button" type="button" onclick="confirmDeleteCd();">
						<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
					</button>
					&nbsp;
					<button class="button" type="reset">
						<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
					</button>
					&nbsp;
					<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			</form>

			

			<form id="add-form" method="get"
				action="<%=siteMap.cdManage + "?action=manageCd" %>">
				<div class="input-table">
					<table>
						<div class="form-title">Thêm chức danh</div>
						<tr>
							<td class="input"><label for="MCD">Mã chức danh</label></td>
							<td><input name="cdMa" type="text" class="text" required
								autofocus size="2" maxlength="3" onkeypress="changeCdMa();" pattern="[a-zA-Z0-9]{3}"
								title="Mã chất lượng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requireCdMa" style="color: red"></div></td>
						</tr>
						<tr>
							<th class="input"><label for="TCD">Tên chức danh</label>
							</td>
							<td><input name="cdTen" size="30px" align=left type="text"
								class="text" onkeypress="changeCdTen();"required title="Tên chất lượng không được để trống"><div id="requireCdTen" style="color: red"></div></td>
						</tr>
					</table>
				</div>
				<div class="button-group">
					<!-- 				<input type="hidden" name="action" value = "AddCd">   -->
					<button class="button" onclick="addCd()" type="button">
						<i class="fa fa-plus-circle"></i>&nbsp;Thêm
					</button>
					<button type="reset" class="button">
						<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
					</button>
					<button type="button" class="button"
						onclick="loadAddCd();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			</form>

			<!-- ---------------Update form-------------- -->
			<form id="update-form">
				<div class="input-table">
					<table>
						<div class="form-title">Cập nhật chức danh</div>
						<tr>
							<td class="input"><label for="MCD">Mã chức danh</label></td>
							<td><input name="cdMaUpdate" type="text" class="text"
								required autofocus size="2" maxlength="3" value="A80"
								pattern="[a-zA-Z0-9]{3}"
								title="Mã chất lượng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"
								value="MCD" readonly style="background-color: #D1D1E0;"></td>
						</tr>
						<tr>
							<td class="input"><label for="TCD">Tên chức danh</label></td>
							<td><input name="cdTenUpdate" size="30px" align=left
								type="text" class="text" onkeypress="changeCdTenUpdate();"
								value="Hàng thu hồi có thể sử dụng được" required
								title="Tên chất lượng không được để trống"><div id="requireCdTenUpdate" style="color: red"></div></td>
						</tr>
					</table>
				</div>
				<div class="group-button">
					<input type="hidden" name="action" value="UpdateCd">
					<button class="button" onclick="confirmUpdateCd()" type="button">
						<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
					</button>
					<button class="button" onclick="resetUpdateCD()" type="button">
						<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
					</button>
					<button type="button" class="button"
						onclick="loadUpdateCd();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			</form>
		</div>
	</div>

	</div>
</body>
</html>