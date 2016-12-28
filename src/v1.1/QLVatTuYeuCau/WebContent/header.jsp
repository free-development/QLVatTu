<%@page import="model.NguoiDung"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="header">
			<!--
					<img src="img/logo.png" alt="" id="logo" width=80 height=80/><br/>
					<img src="img/textlogo.png" alt="" id="logo" width=80 height=20/>
	-->
			<div id="top_title">Công Ty Điện Lực TP Cần Thơ</div>
			<div id="bottom-title">Phòng Vật Tư</div>
			

		</div>
		<%
		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung"); 
		String adminMa = request.getServletContext().getInitParameter("adminMa");
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
		String nhanVienMa = request.getServletContext().getInitParameter("nhanVienMa");
		%>
<div class="main_menu">
					<ul>
						<li><a class="menu" href="<%=siteMap.homePageManage%>">Trang chủ</a></li>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						
						<li><a class="menu" >Danh mục</a>
							<ul>
										<li><a class="menu" href="<%=siteMap.nsxManage + "?action=manageNsx"%>">Danh
												mục nơi sản xuất</a></li>
										<li><a class="menu" href="<%=siteMap.clManage + "?action=manageCl"%>">Danh
												mục chất lượng</a></li>
										<li><a class="menu" href="<%=siteMap.vattuManage + "?action=manageVattu"%>">Danh
												mục vật tư</a></li>
										<li><a class="menu" href="<%=siteMap.ctvtManage + "?action=manageCtvt"%>">Vật tư tồn kho</a></li>
										<li><a class="menu" href="<%=siteMap.bpsdManage +  "?action=manageBpsd"%>">Danh
												mục bộ phận sử dụng</a></li>
										<li><a class="menu" href="<%=siteMap.mdManage + "?action=manageMd"%>">Danh
												mục mục đích</a></li>
										<li><a class="menu" href="<%=siteMap.vtManage + "?action=manageVt"%>">Danh mục vai trò</a></li>
										<li><a class="menu" href="<%=siteMap.dvtManage + "?action=manageDvt"%>">Danh mục đơn vị tính</a></li>
										<li><a class="menu" href="<%=siteMap.cdManage + "?action=manageCd"%>">Danh
												mục chức danh</a></li>
										
									</ul>
						</li>
						<%} %>
							<li><a class="menu" href="<%=siteMap.cvManage+ "?action=manageCv" %>">Công văn</a></li>
<%-- 							<%if (!chucDanh.equalsIgnoreCase(vanThuMa)){ %> --%>
							<li><a class="menu">Báo cáo vật tư thiếu</a>
								<ul>
									<li><a class="menu" href="<%=siteMap.bcttManage+ "?action=manageBcvtt" %>"/>Báo cáo tổng hợp vật thiếu</li>
									<li><a class="menu" href="<%=siteMap.bcvttManage+ "?action=manageBcbdn" %>"/>Báo cáo chi tiết vật tư thiếu</li>
								</ul>
							</li>
<%-- 							<%} %> --%>
						<%if (adminMa.equalsIgnoreCase(chucDanh)) {%>
						<li><a class="menu">Quản lý người dùng</a>
							<ul>
								<li><a class="menu" href="<%=siteMap.ndManage + "?action=manageNd"%>">Thêm người dùng</li>
								<li><a class="menu" href="<%=siteMap.updateNguoiDung%>"/>Cập nhật thông tin</li>
								<li><a class="menu" href="<%=siteMap.resetPassword%>"/>Khôi phục mật khẩu</li>
								<li><a class="menu" href="<%=siteMap.lockNguoiDung%>"/>Khóa tài khoản</li>
								<li><a class="menu"  href="<%=siteMap.resetNguoiDung%>"/>Khôi phục tài khoản</li>
							</ul>
						</li>
						
						
						<li><a class="menu" href="<%=siteMap.loadBackup%>">Sao lưu dữ liệu</a>
						<%} %>
						<li><a class="menu">Tài khoản</a>
							<ul>
								<li><a class="menu" href="<%=siteMap.changePassPage + ".jsp"%>">Đổi mật khẩu</a></li>
								<li><a class="menu" href="<%=siteMap.logout + "?action=logout"%>">Đăng xuất</a></li>
							</ul>
						</li>		
					</ul>
					<div class="clear"></div>
				</div>
						<div id="greeting"style="color: #6600FF;height:20px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chào:&nbsp;<%=authentication.getHoTen() %></b></div>
