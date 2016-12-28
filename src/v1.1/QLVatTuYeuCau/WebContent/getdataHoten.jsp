<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.NguoiDungDAO"%>

<%
	NguoiDungDAO nguoiDungDAO = new NguoiDungDAO();

	String query = request.getParameter("q");
	
	ArrayList<String> nguoiDungList = nguoiDungDAO.startWithTen(null, query);
	/*
	Iterator<String> iterator = countries.iterator();
	while(iterator.hasNext()) {
		String country = (String)iterator.next();
		out.println(country);
	}
	*/
	nguoiDungDAO.disconnect();
	for (String nguoiDungName : nguoiDungList) {
		out.println(nguoiDungName);
	}
%>