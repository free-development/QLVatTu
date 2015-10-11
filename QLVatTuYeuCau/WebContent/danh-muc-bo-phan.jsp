<%@page import="model.NguoiDung"%>
<%@page import="model.DonVi"%>
<%@page import="java.util.ArrayList"%>
<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-bo-phan.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="style/style-bo-phan.css"
	type="text/css">

<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bophan.js"></script>
<script>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
	String status = (String) request.getAttribute("status");
	if (status != null && status.equals("success"))
		out.println("<script>alert('Import dữ liệu thành công!')</script>");
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.baoCaoChiTiet + "?action=manageBpsd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<DonVi> listDonVi = (ArrayList<DonVi>) request.getAttribute("donViList");
			if (listDonVi ==  null) {
				int index = siteMap.bpsdManage.lastIndexOf("/");
				String url = siteMap.bpsdManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageBpsd");
				dispatcher.forward(request, response);
				return;
			}
			Long size = (Long) request.getAttribute("size");
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" /> 
		
		<div id="main-content">
			<div id="title-content">Danh mục bộ phận sử dụng</div>

				<form id="main-form">
					<div id="view-table-bo-phan" style="margin: 0 auto;overflow: auto;" class="scroll_content">
						<table>
							<tr>
								<th class="left-column"><input type="checkbox"
									class="checkAll"></th>
								<th class="mid-column">Mã BPSD</th>
								<th class="column-2">Tên bộ phận</th>
								<th class="column-3">Số điện thoại</th>
								<th class="column-4">Địa chỉ</th>
								<th class="column-5">Email</th>
							</tr>
							<%
							if(listDonVi != null) {
							int count = 0;
							for(DonVi donVi : listDonVi) {count++ ;%>
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
								<td class="left-column"><input type="checkbox" name="dvMa"
									value="<%=donVi.getDvMa() %>" class="checkbox"></td>
								<td class="mid-column" style="text-align: left;"><%=donVi.getDvMa() %></td>
								<td class="column-2" style="text-align: left;"><%=donVi.getDvTen()%></td>
								<td class="column-3" style="text-align: left;"><%=donVi.getSdt()%></td>
								<td class="column-4" style="text-align: left;"><%=donVi.getDiaChi()%></td>
								<td class="column-5" style="text-align: left;"><%=donVi.getEmail()%></td>
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
					<div class="group-button-bo-phan">
						<input type="hidden" name="action" value="deleteBpsd">
						<button type="button" class="button"
							onclick="showForm('add-form', true)">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						&nbsp;&nbsp;
						<button type="button" class="button"
							onclick="preUpdateBp('update-form', true);">
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
						</button>
						&nbsp;&nbsp;
						<button class="button" type="button" onclick="confirmDelete()">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
						</button>
						&nbsp;&nbsp;
						<button type="button" class="button" 
							onclick="showForm2('main-form','import-formct', true)"> 
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Import 
						</button>
						&nbsp;&nbsp;
						<button class="button" type="button" onclick="location.href='<%=siteMap.xuatDv+".jsp"%>'">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xuất File
						</button>
						&nbsp;
						<button class="button" type="reset">
							<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
						</button>
						&nbsp;&nbsp;
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>Thoát
						</button>
					</div>
				</form>
				
				<!-------------- --add-form-------------- -->
				<form id="add-form" method="get" action="<%=siteMap.bpsdManage + "?action=manageBpsd"%>">
					<div class="input-table-bo-phan">
						<table>
							<div class="form-title">Thêm bộ phận sử dụng</div>
							<tr>
								<td class="input"><label for="MBPSD" class="input" >Mã
										BPSD</label></td>
								<td><input name="dvMa" type="text" class="text" required onkeypress="changedvMa();"
									autofocus size="3" maxlength="3" pattern="[a-zA-Z0-9]{3}"
									title="Mã bộ phận sử dụng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requiredvMa" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label for="MBPSD">Tên BPSD</label></td>
								<td><input name="dvTen" size="30px" align=left type="text"
									class="text" required onkeypress="changedvTen();"
									title="Tên bộ phận sử dụng không được để trống"><div id="requiredvTen" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Số điện thoại</label></td>
								<td><input name="sdt" size="15px" maxlength="11" onkeypress="changeSdt();"
									type="text" class="text"><div id="requireSdt" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Địa chỉ</label></td>
								<td><input name="diaChi" size="30px" align=left type="text" onkeypress="changeDiachi();"
									class="text"><div id="requireDiachi" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Email</label></td>
								<td><input name="email" size="30px" align=left type="text" onkeypress="changeEmail();"
									class="text"><div id="requireEmail" style="color: red"></div></td>
							</tr>

							<!--
                        <tr>
                            <td class="input"><label>Số TK</label></td>
							<td><input name="" size="15px" align=left type="text" class="text"></td>                            
                        </tr>
                        <tr>
                        <td class="input"><label>Mã số thuế</label></td>
                            <td><input name="" align=left type="text" class="text"></td>
                        </tr>
                        <tr>
                            <td class="input"><label>Loại BP</label></td>
							<td><select name = "" required class="select">
                                    <option class="option" value=""></option>
                                    <option value="" class="option">AAAAAAAAAA</option>
                                    <option>AAAAAAAAAA</option>
                                </select>
                            </td>
                        </tr>
-->
						</table>
					</div>
					<div class="button-group">
<!-- 						<input type="hidden" name="action" value="AddBpsd"> -->
						<button type="button" class="button" onclick="addBp();">
							<i class="fa fa-plus-circle"></i>&nbsp;Thêm
						</button>
						<button type="reset" class="button">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="loadAddBp()">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>

				<!-- ---------------Update form-------------- -->
				<form id="update-form">
					<div class="input-table">
						<table>
							<div class="form-title">Cập nhật bộ phận sử dụng</div>
							<tr>
								<td class="input"><label for="MBPSD" class="input">Mã
										BPSD</label></td>
								<td><input name="dvMaUpdate" type="text" class="text"
									required  size="2" maxlength="3" readonly style="background-color: #D1D1E0;"
									pattern="[a-zA-Z0-9]{3}"
									title="Mã bộ phận sử dụng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"></td>
							</tr>
							<tr>
								<td class="input"><label for="MBPSD">Tên BPSD</label></td>
								<td><input name="dvTenUpdate" size="30px" align=left
									type="text" class="text" required autofocus onkeypress="changedvTenUp();"
									title="Tên bộ phận sử dụng không được để trống"><div id="requiredvTenUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Số điện thoại</label></td>
								<td><input name="sdtUpdate" size="15px" align=left onkeypress="changeSdtUp();"
									type="text" class="text" maxlength="12"><div id="requireSdtUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Địa chỉ</label></td>
								<td><input name="diaChiUpdate" size="30px" align=left onkeypress="changeDiachiUp();"
									type="text" class="text"><div id="requireDiachiUp" style="color: red"></div></td>
							</tr>
							<tr>
								<td class="input"><label>Email</label></td>
								<td><input name="emailUpdate" size="30px" align=left onkeypress="changeEmailUp();"
									type="text" class="text"><div id="requireEmailUp" style="color: red"></div></td>
							</tr>

							<!--
                        <tr>
                            <td class="input"><label>Số TK</label></td>
							<td><input name="" size="15px" align=left type="text" class="text"></td>                            
                        </tr>
                        <tr>
                        <td class="input"><label>Mã số thuế</label></td>
                            <td><input name="" align=left type="text" class="text"></td>
                        </tr>
                        <tr>
                            <td class="input"><label>Loại BP</label></td>
							<td><select name = "" required class="select">
                                    <option class="option" value=""></option>
                                    <option value="" class="option">AAAAAAAAAA</option>
                                    <option>AAAAAAAAAA</option>
                                </select>
                            </td>
                        </tr>
-->
						</table>
					</div>
					<div class="button-group">
						<button type="button" class="button" onclick="confirmUpdateBp();">
							<i class="fa fa-plus-circle"></i>&nbsp;Lưu lại
						</button>
						<button type="button" class="button" onclick="resetUpdateBP();">
							<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
						</button>
						<button type="button" class="button"
							onclick="loadUpdateBp()">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>
				</form>
				
				<form id="import-formct" action="<%=siteMap.readExcelBpsd %>" method="post" enctype="multipart/form-data" style="height: 200px;width:300px;text-align: center;" onsubmit="document.body.style.cursor='wait'; return true;" >
									<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
									<div class="group-button">
										<input value="uploadFile" name="action" type="submit" class="button" style="width: 120px;font-size: 17px;text-align: center;" onclick="document.body.style.cursor='wait'; return true;">
										<input value="Thoát" onclick="showForm2('main-form','import-formct', false);" type="button" class="button"  style="width: 100px;text-align: center;font-size: 17px;">
									</div>
						</form>
			</div>
		</div>
</body>
</html>