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
<link href="style/style-yeu-cau.css"css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-menu-tree.css" type="text/css">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/cong-van.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
    	ArrayList<CongVan> congVanList = (ArrayList<CongVan>) request.getAttribute("congVanList");
    	HashMap<Integer, File> fileHash = (HashMap<Integer, File>) request.getAttribute("fileHash");
    	ArrayList<DonVi> donViList = (ArrayList<DonVi>) request.getAttribute("donViList");
    	ArrayList<MucDich> mucDichList = (ArrayList<MucDich>) request.getAttribute("mucDichList");
    	ArrayList<TrangThai> trangThaiList = (ArrayList<TrangThai>) request.getAttribute("trangThaiList");
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
				<li><a href="">Trang chủ</a></li>
				<li><a href="">Danh mục</a>
					<ul>
						<li><a href="danh-muc-noi-san-xuat.html">Danh mục nơi sản
								xuất
								</p>
						</a></li>
						<li><a href="danh-muc-chat-luong.html">Danh mục chất
								lượng</a></li>
						<li><a href="danh-muc-vat-tu.html">Danh mục vật tư</a></li>
						<li><a href="danh-muc-bo-phan.html">Danh mục bộ phận sử
								dụng</a></li>
						<li><a href="danh-muc-muc-dich.html">Danh mục mục đích</a></li>
					</ul></li>
				<li><a href="danh-muc-cong-van.html">Công văn</a></li>
				<li><a href="bao-cao.html">Báo cáo</a></li>
				<!--						<li><a href="danh-muc-chia-se-cong-van.html">Chia sẽ</a></li>-->
				<li><a href="bao-cao.html">Quản lý người dùng</a></li>
				<li><a href="login.html">Đăng xuất</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<div id="greeting">Chào Nguyễn Văn An</div>

		<div id="main-content">
			
				<!--    update-vat-tu    -->
				<form id="update-yc-vat-tu">
					<div class="input-table-vt">
						<table>
							<div class="form-title">Cập nhật yêu cầu vật tư</div>
							<table>
								<tr>
									<th class="a-column">Chọn</th>
									<th class="b-column">Mã vật tư</th>
									<th class="c-column">Tên vật tư</th>

									<th class="e-column">Nơi sản xuất</th>
									<th class="f-column">Chất lượng</th>
									<th class="d-column">Số lượng</th>
									<th class="g-column">Đvt</th>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">315422050</td>
									<td class="c-column">Cáp đòng bọc hạ áp CV 50m...</td>
									<td class="d-column">Viet Nam</td>
									<td class="e-column">Hàng thu hồi</td>
									<td class="f-column">17</td>
									<td class="g-column">m</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">31582025</td>
									<td class="c-column">Cáp đồng bọc 24KV CX(CR)...</td>
									<td class="d-column">5</td>
									<td class="e-column">51091.313</td>
									<td class="f-column">255457</td>
									<td class="g-column">m</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32084025</td>
									<td class="c-column">Đầu cosse ép đồng 25cm2</td>
									<td class="d-column">1</td>
									<td class="e-column">19200</td>
									<td class="f-column">19200</td>
									<td class="g-column">cái</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32084050</td>
									<td class="c-column">Đầu cosse ép đồng 50cm2</td>
									<td class="d-column">4</td>
									<td class="e-column">23500</td>
									<td class="f-column">23500</td>
									<td class="g-column">cái</td>
								</tr>
								<tr>
									<td class="a-column"><input type="checkbox" name=""></td>
									<td class="b-column">32005814</td>
									<td class="c-column">Nối ép WR 259</td>
									<td class="d-column">4</td>
									<td class="e-column">11200</td>
									<td class="f-column">44800</td>
									<td class="g-column">cái</td>
								</tr>
							</table>
							</div>
							<div class="group-button">
								<button type="button" class="button"
									onclick="showForm('update-yc-vat-tu', 'add-form-ycvt', true)">
									<i class="fa fa-plus-circle"></i>&nbsp;Thêm mới
								</button>
								<button type="button" class="button"
									onclick="showForm('update-yc-vat-tu','update-form-ycvt', true)">
									<i class="fa fa-pencil fa-fw"></i>&nbsp;Sửa
								</button>
								<button class="button" onclick="return confirmDelete()">
									<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
								</button>
								<button type="reset" class="button">
									<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
								</button>
								<button type="button" class="button"
									onclick="showForm('main-form', 'update-yc-vat-tu',false)">
									<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
								</button>
							</div>
							</div>
							</div>
							</form>

							<form id="add-form-ycvt">
								<div class="input-table">
									<table>
										<div class="form-title" style="padding: 10px">Thêm yêu
											cầu vật tư</div>
										<tr>
											<th style="text-align: left"><label for="TKM">Tìm
													kiếm mã</label></th>
											<td><input name="" type="text" class="text" required
												autofocus size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
												title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
											<td class="name" style="text-align: right"><input
												type="checkbox" name=""></td>
											<th style="text-align: left"><label for="TT">Theo
													tên</label></th>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Mã
													vật tư</label></th>
											<td><input name="" size="3px" class="text" align=right
												type="text" class="text" required
												title="Mã vật tư không được để trống"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Nơi
													sản xuất</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaaaaaaaaaaaa</option>
											</select></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Chất
													lượng</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
										<tr>
											<th style="text-align: left">Số lượng</th>
											<td><input class="text" type="text" size="2px"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Đơn
													vị tính</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
									</table>
								</div>
								<div class="group-button">
									<button class="button">
										<i class="fa fa-plus-circle"></i>&nbsp;Thêm
									</button>
									<button type="reset" class="button">
										<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
									</button>
									<button type="button" class="button"
										onclick="showForm('update-yc-vat-tu','add-form-ycvt', false)">
										<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
									</button>
								</div>
							</form>
							<form id="update-form-ycvt">
								<div class="input-table">
									<table>
										<div class="form-title" style="padding: 10px">Cập nhật
											yêu cầu vật tư</div>
										<tr>
											<th style="text-align: left"><label for="TKM">Tìm
													kiếm mã</label></th>
											<td><input name="" type="text" class="text" required
												autofocus size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
												title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
											<td class="name" style="text-align: right"><input
												type="checkbox" name=""></td>
											<th style="text-align: left"><label for="TT">Theo
													tên</label></th>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Mã
													vật tư</label></th>
											<td><input name="" size="3px" align=right class="text"
												type="text" class="text" required
												title="Mã vật tư không được để trống"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="MVT">Nơi
													sản xuất</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaaaaaaaaaaaa</option>
											</select></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Chất
													lượng</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
										<tr>
											<th style="text-align: left">Số lượng</th>
											<td><input class="text" type="text" size="2px"></td>
										</tr>
										<tr>
											<th style="text-align: left"><label for="DVT">Đơn
													vị tính</label></th>
											<td><select required
												title="Nơi sản xuất không được để trống">
													<option selected disabled></option>
													<option>aaaaa</option>
											</select></td>

										</tr>
									</table>
								</div>
								<div class="group-button">
									<button class="button">
										<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
									</button>
									<button type="reset" class="button">
										<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
									</button>
									<button type="button" class="button"
										onclick="showForm('update-yc-vat-tu', 'update-form-ycvt',false)">
										<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
									</button>
								</div>
							</form>
							</div>
							</div>

							</div>
</body>
</html>
