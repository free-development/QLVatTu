<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.VatTuDAO"%>

<%
	VatTuDAO vatTuDAO = new VatTuDAO();

	String query = request.getParameter("q");
	
	ArrayList<String> vatTuList = vatTuDAO.startWithTen(query);
	/*
	Iterator<String> iterator = countries.iterator();
	while(iterator.hasNext()) {
		String country = (String)iterator.next();
		out.println(country);
	}
	*/
	vatTuDAO.disconnect();
	for (String vatTuName : vatTuList) {
		out.println(vatTuName);
	}
%>