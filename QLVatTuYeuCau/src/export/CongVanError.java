package export;

import java.awt.print.Book;
import java.util.ArrayList;
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

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class CongVanError extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<Object> errorList = (List<Object>) model.get("errorList");
		ArrayList<String> vtMaError = (ArrayList<String>) errorList.get(0);
		ArrayList<String> nsxMaError = (ArrayList<String>) errorList.get(1);
		ArrayList<String> clMaError = (ArrayList<String>) errorList.get(2);
		ArrayList<Integer> soLuongError = (ArrayList<Integer>) errorList.get(3);
		ArrayList<String> statusError = (ArrayList<String>) errorList.get(4);
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Vật tư bị lỗi khi import công văn");
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 6000);
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "VatTuLoi.xls");
		header.createCell(0).setCellValue("Mã Vật Tư");
//		header.getCell(0).setCellStyle(style);
		header.createCell(1).setCellValue("Mã nơi sản xuất");
//		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Mã chất lượng");
//		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Số lượng thiếu");
//		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Lỗi");
//		header.getCell(4).setCellStyle(style);
		
		int count = 0;
		for (String vtMa : vtMaError) {
			HSSFRow aRow = sheet.createRow(count + 1);
			aRow.createCell(0).setCellValue(vtMa);
			aRow.createCell(1).setCellValue(nsxMaError.get(count));
			aRow.createCell(2).setCellValue(clMaError.get(count));
			aRow.createCell(3).setCellValue(soLuongError.get(count));
			aRow.createCell(4).setCellValue(statusError.get(count));
			count ++;
		}
	}

}