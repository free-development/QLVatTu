package export;

import java.awt.print.Book;
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

import model.DonVi;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class DonViFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<DonVi> listDv = (List<DonVi>) model.get("listBooksDv");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Đơn vị");
		//sheet.setDefaultColumnWidth(30);
//		sheet.setColumnWidth(0, 30);
//		sheet.setColumnWidth(1, 100);
//		sheet.setColumnWidth(2, 30);
//		sheet.setColumnWidth(3, 100);
//		sheet.setColumnWidth(4, 50);
//		
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "Bophansudung.xls");
		
		header.createCell(0).setCellValue("Mã Bộ phận sử dụng");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Tên Bộ phận sử dụng");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("Số điện thoại");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Email");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Địa chỉ");
		header.getCell(4).setCellStyle(style);
		
		// create data rows
		int rowCount = 1;
		
		for (DonVi dv : listDv) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(dv.getDvMa());
			aRow.createCell(1).setCellValue(dv.getDvTen());
			aRow.createCell(2).setCellValue(dv.getSdt());
			aRow.createCell(3).setCellValue(dv.getDiaChi());
			aRow.createCell(4).setCellValue(dv.getEmail());
		}
	}

}