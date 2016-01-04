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

import model.NoiSanXuat;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class NoiSanXuatFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<NoiSanXuat> listNsx = (List<NoiSanXuat>) model.get("listNoiSanXuat");
		
		// create a new Excel sheet
		
		HSSFSheet sheet = workbook.createSheet("Noi san xuat");
		sheet.setDefaultColumnWidth(30);
//		sheet.
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short)260);
		style.setFont(font);
		
		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 7000);
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
		response.setHeader("Content-Disposition", "inline; filename=" + "Noisanxuat.xls");
		header.createCell(0).setCellValue("Mã NSX");
		header.getCell(0).setCellStyle(style2);
		
		header.createCell(1).setCellValue("Tên NSX");
		header.getCell(1).setCellStyle(style2);
		
		// create data rows
		int rowCount = 1;
		
		for (NoiSanXuat nsx : listNsx) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(nsx.getNsxMa());
			aRow.createCell(1).setCellValue(nsx.getNsxTen());
		}
	}

}