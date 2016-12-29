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

import vn.com.freesoft.docmanagement.entity.NoiSanXuat;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class ImportNsxError extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Object> errorList = (List<Object>) model.get("errorList");
		List<NoiSanXuat> nsxError = (List<NoiSanXuat>) errorList.get(0);
		List<String> statusError = (List<String>) errorList.get(1);
		// create a new Excel sheet

		HSSFSheet sheet = workbook.createSheet("Noi san xuat Error");
		sheet.setDefaultColumnWidth(30);
		// sheet.
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 260);
		style.setFont(font);

		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 5000);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
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
		response.setHeader("Content-Disposition", "inline; filename=" + "NsxError.xls");
		header.createCell(0).setCellValue("Mã Nsx");
		header.getCell(0).setCellStyle(style2);

		header.createCell(1).setCellValue("Tên Nsx");
		header.getCell(1).setCellStyle(style2);

		header.createCell(2).setCellValue("Lỗi");
		header.getCell(2).setCellStyle(style2);

		// create data rows
		int rowCount = 1;
		int index = 0;
		for (NoiSanXuat nsx : nsxError) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(nsx.getNsxMa());
			aRow.createCell(1).setCellValue(nsx.getNsxTen());
			aRow.createCell(2).setCellValue(statusError.get(index));
			index++;
		}
	}

}