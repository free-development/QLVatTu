<%@page import="model.NguoiDung"%>
<%@page import="model.MucDich"%>
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
<link href="style/style-muc-dich.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link
	href="style\font-awesome-4.3.0\font-awesome-4.3.0\css\font-awesome.min.css"
	type="text/css" rel="stylesheet">
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
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/mucdich.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.mdManage + "?action=manageMd");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<MucDich> listMucDich = (ArrayList<MucDich>) request.getAttribute("mucDichList");
			if (listMucDich ==  null) {
				int index = siteMap.mdManage.lastIndexOf("/");
				String url = siteMap.mdManage.substring(index);
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageMd");
				dispatcher.forward(request, response);
				return;
			}
			long size = (Long) request.getAttribute("size");
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="content">
			<div id="content-wrapper">
				<div id="title-content">Danh mục mục đích</div>
				<div id="main-content">

					<form id="main-form">
						<div id="view-table" style="margin: 0 auto;">
							<table>
								<tr style="background: #199e5e">
									<td class="left-column"><input type="checkbox" name=""
										class="checkAll"></td>
									<th class="mid-column">Mã mục đích</th>
									<th class="right-column">Tên mục đích</th>
								</tr>
								<%
							if(listMucDich != null) {
							int count = 0;
							for(MucDich mucDich : listMucDich) { count++;%>
								<tr class="rowContent"
									<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
									<td class="left-column"><input type="checkbox" name="mdMa"
										value="<%=mucDich.getMdMa() %>" class="checkbox"></td>
									<td class="col"><%=mucDich.getMdMa() %></td>
									<td class="col"><%=mucDich.getMdTen() %></td>
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
							<input type="hidden" name="action" value="deleteMd">
							<button type="button" class="button"
								onclick="showForm('add-form', true)">
								<i class="fa fa-plus-circle"></i>&nbsp;Thêm
							</button>
							&nbsp;
							<button type="button" class="button"
								onclick="preUpdateMd('update-form', true); $('#mdTenFocus').focus();">
								<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
							</button>
							&nbsp;
							<button class="button" type="button" onclick="confirmDeleteMd();">
								<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
							</button>
							&nbsp;
							<button class="button" type="reset">
								<i class="fa fa-spinner"></i>&nbsp;&nbsp;Bỏ qua
							</button>
							&nbsp;
							<button type="button" class="button" onclick="location.href='<%=siteMap.homePageManage %>'">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>
	
				
	
					<form id="add-form" method="get"
						action="<%=siteMap.mdManage + "?action=manageMd" %>">
						<div class="input-table">
							<table>
								<div class="form-title">Thêm mục đích</div>
								<tr>
									<th >Mã mục đích:</th>
									<td><input name="mdMa" type="text" class="text" required
										autofocus size="2" maxlength="3" onkeypress="changeMdMa();" pattern="[a-zA-Z0-9]{3}"
										title="Mã mục đích chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requireMdMa" style="color: red"></div></td>
								</tr>
								<tr>
									<th >Tên mục đích:</th>
									<td><input name="mdTen" size="30px" align=left type="text"
										class="text" onkeypress="changeMdTen();" required title="Tên mục đích không được để trống"><div id="requireMdTen" style="color: red"></div></td>
								</tr>
							</table>
						</div>
						<div class="group-button">
							<!-- 						<input type="hidden" name="action" value = "AddMd">  -->
							<button class="button" type="button" onclick="addMd()">
								<i class="fa fa-plus-circle"></i>&nbsp;Thêm
							</button>
							&nbsp;
							<button type="reset" class="button">
								<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
							</button>
							&nbsp;
							<button type="button" class="button"
								onclick="loadAddMd();">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>

					<!-- ---------------Update form-------------- -->
					<form id="update-form">
						<div class="input-table">
							<table>
								<div class="form-title">Cập nhật mục đích</div>
								<tr>
									<th ><label for="MMD">Mã mục đích: </label></th>
									<td><input name="mdMaUpdate" type="text" class="text"
										required size="2" maxlength="3" pattern="[a-zA-Z0-9]{3}"
										title="Mã mục đích chỉ gồm 3 ký tự, không chứa khoảng trắng và ký tự đặc biệt"
										value="MMA" readonly style="background-color: #D1D1E0;"></td>
								</tr>
								<tr>
									<th><label for="MMD">Tên mục đích: </label></th>
									<td><input id="mdTenFocus" name="mdTenUpdate" autofocus size="30px"
										 type="text" class="text" onkeypress="changeMdTen();" required
										title="Tên mục đích không được để trống"><div id="requireMdTenUpdate" style="color: red"></div></td>
								</tr>
							</table>
						</div>
						<div class="group-button">
							<input type="hidden" name="action" value="UpdateMd">
							<button class="button" type="button" onclick="confirmUpdateMd()">
								<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
							</button>
							&nbsp;
							<button class="button" type="button" onclick="resetUpdateMd()">
								<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
							</button>
							&nbsp;
							<button type="button" class="button"
								onclick="loadUpdateMd();">
								<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
							</button>
						</div>
					</form>
				</div>
			</div>
</div>
		</div>
</body>
</html>