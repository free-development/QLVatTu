<%@page import="model.NguoiDung"%>
<%@page import="model.ChatLuong"%>
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
<link href="style/style-chat-luong.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
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
<script type="text/javascript" src="js/chatluong.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.clManage + "?action=manageCl");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
   		
   		String status = (String) request.getAttribute("status");
   		if (status != null && status.equals("success"))
   			out.println("<script>alert('Import dữ liệu thành công!')</script>");
   		else if (status != null && status.equals("unknownFile"))
   			out.println("<script>alert('Định dạng import không hỗ trợ!')</script>");
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    		ArrayList<ChatLuong> listChatLuong = (ArrayList<ChatLuong>) request.getAttribute("chatLuongList");
			if (listChatLuong ==  null) {
				int index = siteMap.clManage.lastIndexOf("/");
				String url = siteMap.clManage.substring(index);
				
				RequestDispatcher dispatcher =  request.getRequestDispatcher(url + "?action=manageCl");
				dispatcher.forward(request, response);
				return;
			}
			Long size = (Long) request.getAttribute("size");
    	%>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="title-content">Danh mục chất lượng</div>
		<div id="main-content">

			<form id="main-form">
				<div id="view-table" style="margin: 0 auto;">
					<table>
						<tr style="background-color: #199e5e;">
							<td class="left-column"><input type="checkbox" name=""
								class="checkAll"></td>
							<th class="mid-column">Mã CL</th>
							<th class="right-column">Tên chất lượng</th>

						</tr>
						<%
							if(listChatLuong != null) {
							int count = 0;
							for(ChatLuong chatLuong : listChatLuong) { count++;%>
						<tr class="rowContent"
							<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
							<td class="left-column"><input type="checkbox" name="clMa"
								value="<%=chatLuong.getClMa() %>" class="checkbox"></td>
							<td class="col"><%=chatLuong.getClMa() %></td>
							<td class="col"><%=chatLuong.getClTen() %></td>
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
					<input type="hidden" name="action" value="deleteCl">
					<button type="button" class="button"
						onclick="showForm('add-form', true);$('#clMaFocus').focus();">
						<i class="fa fa-plus-circle"></i>&nbsp;Thêm
					</button>
					&nbsp;
					<button type="button" onclick="preUpdateCl('update-form', true);"
						class="button" title="Chọn 1 chất lượng để thay đổi">
						<i class="fa fa-pencil fa-fw"></i>&nbsp;Thay đổi
					</button>
					&nbsp;
					<button class="button" type="button" onclick="confirmDeleteCl();">
						<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Xóa
					</button>
					&nbsp;
					<button type="button" class="button" 
							onclick="showForm2('main-form','import-formct', true);"> 
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Import 
					</button>
					&nbsp;
					<a href="<%=siteMap.exporClMn%>" target="_blank">
						<button class="button" type="button" >
							<i class="fa fa-book"></i>&nbsp;&nbsp;Xuất File
						</button>
					</a>
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
				action="<%=siteMap.clManage + "?action=manageCl" %>" style="background-color: #DCEAF5;">
				<div class="input-table">
					<table>
						<div class="form-title">Thêm chất lượng</div>
						<tr>
							<td class="input"><label for="MCL">Mã chất lượng:</label></td>
							<td><input id="clMaFocus" name="clMa" type="text" class="text" required
								autofocus size="2" onkeypress="changeClMa();" maxlength="3" pattern="[a-zA-Z0-9]{3}"
								title="Mã chất lượng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"><div id="requireClMa" style="color: red"></div></td>
						</tr>
						<tr>
							<th class="input"><label for="MMD">Tên chất lượng</label>
							</td>
							<td><input name="clTen" size="30px" align=left type="text"
								class="text" onkeypress="changeClTen();" required title="Tên chất lượng không được để trống"><div id="requireClTen" style="color: red"></div></td>
						</tr>
					</table>
				</div>
				<div class="button-group">
					<!-- 				<input type="hidden" name="action" value = "AddCl">   -->
					<button class="button" onclick="addCl();" type="button">
						<i class="fa fa-plus-circle"></i>&nbsp;Thêm
					</button>
					&nbsp;
					<button type="reset" class="button">
						<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
					</button>
					&nbsp;
					<button type="button" class="button"
						onclick="showForm2('main-form','add-form', false);;">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			</form>

			<!-- ---------------Update form-------------- -->
			<form id="update-form">
				<div class="input-table">
					<table>
						<div class="form-title">Cập nhật chất lượng</div>
						<tr>
							<td class="input"><label for="MCL">Mã chất lượng</label></td>
							<td><input name="clMaUpdate" type="text" class="text"
								required autofocus size="2" maxlength="3" value="A80" style="background-color: #D1D1E0;"
								pattern="[a-zA-Z0-9]{3}"
								title="Mã chất lượng chỉ gồm 3 ký tự, không chứ khoảng trắng và ký tự đặc biệt"
								value="MCL" readonly></td>
						</tr>
						<tr>
							<td class="input"><label for="MCL">Tên chất lượng</label></td>
							<td><input id="clTenFocus" name="clTenUpdate" size="30px" align=left
								type="text" class="text" onkeypress="changeClTenUpdate();"
								value="Hàng thu hồi có thể sử dụng được" required
								title="Tên chất lượng không được để trống"><div id="requireClTenUpdate" style="color: red"></div></td>
						</tr>
					</table>
				</div>
				<div class="group-button">
					<input type="hidden" name="action" value="UpdateCl">
					<button class="button" onclick="confirmUpdateCl()" type="button">
						<i class="fa fa-floppy-o"></i>&nbsp;Lưu lại
					</button>
					&nbsp;
					<button type="button" class="button" onclick="resetUpdateCL();">
						<i class="fa fa-refresh"></i>&nbsp;&nbsp;Nhập lại
					</button>
					&nbsp;
					<button type="button" class="button"
						onclick="loadUpdateCl();">
						<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
					</button>
				</div>
			</form>
			<form onsubmit="showForm2('main-form','import-formct', false);" target="_blank" id="import-formct" action="<%=siteMap.readExcelCl %>" method="post" enctype="multipart/form-data" style="text-align: center;">
									<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
									<div class="group-button">
										<input value="uploadFile" name="action" type="submit" class="button" style="font-size: 17px;text-align: center;">
										<input value="Thoát" onclick="showForm2('main-form','import-formct', false);" type="button" class="button"  style="text-align: center;font-size: 17px;">
									</div>
						</form>
		</div>
	</div>
	
</body>
</html>