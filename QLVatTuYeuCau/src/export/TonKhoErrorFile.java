package export;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;


import model.CTVatTu;
import model.DonVi;
import util.DateUtil;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class TonKhoErrorFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<Object> objectListError = (List<Object>) model.get("objectListError");
		List<String> vtMaError = (List<String>) objectListError.get(0);
		List<String> vtTenError = (List<String>) objectListError.get(1);
		List<String> dvtTenError = (List<String>) objectListError.get(2);
		List<String> nsxTenError = (List<String>) objectListError.get(3);
		List<String> clTenError = (List<String>) objectListError.get(4);
		List<Integer> soLuongError = (List<Integer>) objectListError.get(5);
		List<String> statusError = (List<String>) objectListError.get(6);
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Vật tư tồn kho");
		//sheet.setDefaultColumnWidth(30);
	//	sheet.setColumnWidth(0, 30);
//		sheet.setColumnWidth(1, 200);
//		sheet.setColumnWidth(2, 30);
//		sheet.setColumnWidth(3, 100);
//		sheet.setColumnWidth(4,50);
//		sheet.setColumnWidth(5, 100); 
		
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
//		style.setFillForegroundColor(HSSFColor.WHITE.index);
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		// create title row
		HSSFRow title = sheet.createRow(0);
		title.createCell(0).setCellValue("Vật tư tồn khi bị lỗi import");
		title.getCell(0).setCellStyle(style); 
		Date dayCurrent = new Date();
		int month = dayCurrent.getMonth()+1;
		title.createCell(1).setCellValue("Tháng "+ month);
		title.getCell(1).setCellStyle(style);
		
		title.createCell(2).setCellValue("Ngày import:");
		title.getCell(2).setCellStyle(style);
		
		title.createCell(3).setCellValue(DateUtil.toString(dayCurrent));
		title.getCell(3).setCellStyle(style);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		
		// create header row
		HSSFRow header = sheet.createRow(1);
//		row.setRowStyle(style);
		response.setHeader("Content-Disposition", "inline; filename=" + "vatTuTonKhoError.xls");
		header.createCell(0).setCellValue("STT");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Mã Vật Tư");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Tên Vật Tư");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("ĐVT");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Nơi Sản Xuất");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Mã chất lượng");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("Số lượng");
		header.getCell(6).setCellStyle(style);
		
		header.createCell(7).setCellValue("Lỗi");
		header.getCell(7).setCellStyle(style);
		//create row 2
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("D01");
		row2.getCell(0).setCellStyle(style);
		
		row2.createCell(1).setCellValue("Kho Công ty Điện Lực Cần Thơ");
		row2.getCell(1).setCellStyle(style);
		
		int rowCount = 3;
		int count = 0;
		for (String vtMa : vtMaError) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(count + 1);
			aRow.createCell(1).setCellValue(vtMa);
			aRow.createCell(2).setCellValue(vtTenError.get(count));
			aRow.createCell(3).setCellValue(vtTenError.get(count));
			aRow.createCell(4).setCellValue(nsxTenError.get(count));
			aRow.createCell(5).setCellValue(clTenError.get(count));
			aRow.createCell(6).setCellValue(soLuongError.get(count));
			aRow.createCell(7).setCellValue(statusError.get(count));
			count ++;
		}
	}

}