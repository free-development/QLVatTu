package vn.com.freesoft.docmanagement.export;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import vn.com.freesoft.docmanagement.entity.ChatLuong;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class ChatLuongFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<ChatLuong> listCl = (List<ChatLuong>) model.get("listBooksCl");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Chất Lượng");
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 260);
		style.setFont(font);

		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 10000);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		// create header row
		CellStyle style2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Times New Roman");
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 260);
		style2.setFont(font2);
		style2.setAlignment(CellStyle.ALIGN_CENTER);

		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "ChatLuong.xls");

		header.createCell(0).setCellValue("Mã CL");
		header.getCell(0).setCellStyle(style2);

		header.createCell(1).setCellValue("Tên CL");
		header.getCell(1).setCellStyle(style2);

		// create data rows
		int rowCount = 1;

		for (ChatLuong cl : listCl) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(cl.getClMa());
			aRow.createCell(1).setCellValue(cl.getClTen());
		}
	}
}