<%@page import="org.apache.poi.hssf.usermodel.HSSFPrintSetup"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFPrintSetup"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFSheet"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFWorkbook"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	HSSFWorkbook workbook = new HSSFWorkbook(); 
	HSSFSheet spreadsheet = workbook
    .createSheet("Print Area");
    //set print area with indexes
    workbook.setPrintArea(
    0, //sheet index
    0, //start column
    5, //end column
    0, //start row
    5 //end row
    );
    //set paper size
    spreadsheet.getPrintSetup().setPaperSize(
    		HSSFPrintSetup.A4_PAPERSIZE);
    //set display grid lines or not
    spreadsheet.setDisplayGridlines(true);
   //set print grid lines or not
   spreadsheet.setPrintGridlines(true);
   FileOutputStream out1 = new FileOutputStream(
   new File("/home/quoioln/vattu1.xls"));
   workbook.write(out1);
   out.close();
   System.out.println("printarea.xlsx written successfully"); 
	%>
</body>
</html>