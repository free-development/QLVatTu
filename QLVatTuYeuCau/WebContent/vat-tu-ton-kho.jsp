<%@page import="javax.swing.JOptionPane"%>
<%@page import="model.NguoiDung"%>
<%@page import="model.CTVatTu"%>
<%@page import="model.VatTu"%>
<%@page import="model.NoiSanXuat"%>
<%@page import="model.ChatLuong"%>
<%@page import="map.siteMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Văn phòng điện tử công ty điện lực Cần Thơ</title>
<link rel="stylesheet" href="style/style-giao-dien-chinh.css"
	type="text/css">
<link rel="stylesheet" href="style/style.css" type="text/css">
<link href="style/style-ctvt.css"css" type="text/css" rel="stylesheet">
<link
	href="style/font-awesome-4.3.0/font-awesome-4.3.0/css/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/location.js"></script>
<script type="text/javascript" src="js/chi-tiet-vat-tu.js"></script>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="Shortcut Icon" href="img/logo16.png" type="image/x-icon" />
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if (status != null && status.equals("success"))
			out.println("<script>alert('Import dữ liệu thành công!')</script>");
		else if (status != null && status.equals("unknownFile"))
			out.println("<script>alert('Tệp import không đúng đinh dạng!')</script>");
		String adminMa = request.getServletContext().getInitParameter("adminMa");
   		NguoiDung authentication = (NguoiDung) session.getAttribute("nguoiDung");
   		if (authentication == null) {
   			request.setAttribute("url", siteMap.ctvtManage + "?action=manageCtvt");
   			RequestDispatcher dispatcher = request.getRequestDispatcher(siteMap.login + ".jsp");
   			dispatcher.forward(request, response);
   			return;
   		}
		String chucDanh = authentication.getChucDanh().getCdMa();
		String truongPhongMa = request.getServletContext().getInitParameter("truongPhongMa");
		String vanThuMa = request.getServletContext().getInitParameter("vanThuMa");
   	%>
	<%
    
    	ArrayList<CTVatTu> listCTVatTu = (ArrayList<CTVatTu>) session.getAttribute("ctVatTuList");
		
		if (listCTVatTu ==  null) {
			int index = siteMap.ctvtManage.lastIndexOf("/");
			String url = siteMap.ctvtManage.substring(index);
			RequestDispatcher dispatcher =  request.getRequestDispatcher(url +  "?action=manageCtvt");
			System.out.println(url +  "?action=manageCtvt");
			dispatcher.forward(request, response);
			return;
		}
		Long size = (Long) request.getAttribute("size");
		Long pageNum = size/10;
   		
    %>
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<div id="main-content">
			<div id="title-content">Vật tư tồn kho</div>
			<form id="main-form">
					<div id="view-table-chi-tiet" style="margin: 0 auto; overflow: auto;font-family: Tahoma,.vntime;" class="scroll_content">
						<table>
							<tr style="background: #199e5e">
<!-- 								<th class="left-column"><input type="checkbox" -->
<!-- 									class="checkAll"></th> -->
								<th class="four-column">Mã vật tư</th>
								<th class="three-column">Tên vật tư</th>
								<th class="six-column">Nơi sản xuất</th>
								<th class="six-column">Chất lượng</th>
								<th class="four-column">Đơn vị tính</th>
								<th class="five-column">Định mức</th>
								<th class="seven-column">Số lượng tồn</th>
							</tr>
							<%
									if(listCTVatTu != null) {
									int count = 0;
									for(CTVatTu ctVatTu : listCTVatTu) { count++;%>
		
							<tr class="rowContent"
								<%if (count % 2 == 0) out.println("style=\"background : #CCFFFF;\"");%>>
<!-- 								<td class="left-column"><input type="checkbox" name="vtMa" -->
<%-- 									value="<%=ctVatTu.getVatTu().getVtMa() %>" class="checkbox"></td> --%>
								<td class="col"><%=ctVatTu.getVatTu().getVtMa() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getVatTu().getVtTen() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getNoiSanXuat().getNsxTen() %></td>
								<td class="col" style="text-align: left;"><%=ctVatTu.getChatLuong().getClTen() %></td>
								<td class="col"><%=ctVatTu.getVatTu().getDvt().getDvtTen() %></td>
								<td class="col"><%=ctVatTu.getDinhMuc() %></td>
								<td class="col"><%=ctVatTu.getSoLuongTon() %></td>
		
							</tr>
							<%} }%>
		
						</table>
					</div>
			<div id = "paging" >
								<%
										String str = "";
										String pages = ""; 
										long p = (pageNum < 10 ? pageNum : 10);
									for(int i = 0; i < p; i++) {
										str += "<input type=\"button\" value=\"" + (i+1) + "\" class=\"page\" onclick=\"loadPageCTVatTu(" + i +")\">&nbsp;";
									}
									if (pageNum > 10)
										str += "<input type=\"button\" class=\"pageMove\" value=\"Sau >>\" onclick= \"loadPageCTVatTu(\'Next\');\">";
									out.println(str);	
								%>
					
			</div>
						<div class="group-button" style="text-align: center;">		
 						<button type="button" class="button" 
							onclick="showForm('import-form', true)"> 
							<i class="fa fa-pencil fa-fw"></i>&nbsp;Import
						</button>
						&nbsp;
						<button class="button" type="button" onclick="location.href='<%=siteMap.xuatTonKhoMn%>'">
							<i class="fa fa-trash-o"></i>&nbsp;&nbsp;Báo cáo tồn kho
						</button>
						&nbsp;
						<button type="button" class="button" onclick="location.href='<%=siteMap.home%>'">
							<i class="fa fa-sign-out"></i>&nbsp;&nbsp;Thoát
						</button>
					</div>			
				</form>
				</div>
						<form id="import-form" action="<%=siteMap.readExcelTonkho %>" method="post" enctype="multipart/form-data" >
								<input type="file" name="file" accept=".xls, .xlsx" class="text" style="padding-left: 0px;">
<div class="button-group" style="margin-top: -40px;"><input value="uploadFile" name="action" type="submit" class="button">
								<input value="Thoát" onclick="showForm('import-form',false);" type="button" class="button">
								</div>
						</form>
	
	</div>
</body>
</html>