<%@page import="map.siteMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Import file</title>
</head>
<body>
	<%
		String status = (String) request.getAttribute("status");
		if (status != null && status.equals("unknownFile"))
			out.println("<script>alert('File nhập không được hỗ trợ. Vui lòng chọn file excel')</script>");
		else if (status != null && status.equals("formatException"))
			out.println("<script>alert('Lỗi kiểu dữ liệu. Vui lòng chọn đúng mẫu!!!l')</script>");
	%>
	<form action="<%=siteMap.readExcelBpsd %>" method="post" enctype="multipart/form-data" >
		<input type="file" name="file" accept=".xls, .xlsx">
		<input value="uploadFile" name="action" type="submit">
	</form>
</body>
</html>