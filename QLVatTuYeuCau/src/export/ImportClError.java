package export;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import map.siteMap;
import model.ChatLuong;
import model.NoiSanXuat;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class ImportClError extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
			{
		try {
			
		
		// get data model which is passed by the Spring container
		List<Object> errorList = (List<Object>) model.get("errorList");
		List<ChatLuong> chatLuongList = (List<ChatLuong>) errorList.get(0);
		List<String> statusError = (List<String>) errorList.get(1);
		// create a new Excel sheet
		
		HSSFSheet sheet = workbook.createSheet("Chat luong Error");
		sheet.setDefaultColumnWidth(30);
//		sheet.
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
//		style.setFillForegroundColor(HSSFColor.BLUE.index);
//		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "ChatLuongError.xls");
		header.createCell(0).setCellValue("Mã Nơi sản xuất");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Tên chất lượng");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Lỗi");
		header.getCell(2).setCellStyle(style);
		
		// create data rows
		int rowCount = 1;
		int index = 0;
		for (ChatLuong cl : chatLuongList) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(cl.getClMa());
			aRow.createCell(1).setCellValue(cl.getClTen());
			aRow.createCell(2).setCellValue(statusError.get(index));
			index ++;
		}
		}catch (ClassCastException e) {
			RequestDispatcher dispathcher =  request.getRequestDispatcher(siteMap.clManage);
			dispathcher.forward(request, response);
		}
	}

}