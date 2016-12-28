package vn.com.freesoft.docmanament.export;

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

import vn.com.freesoft.docmanament.entity.DonVi;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class ImportDonViError extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Object> errorList = (List<Object>) model.get("errorList");
		List<DonVi> donViError = (List<DonVi>) errorList.get(0);
		List<String> statusError = (List<String>) errorList.get(1);

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Bộ phận sử dụng bị lỗi import");
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 260);
		style.setFont(font);

		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 12000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 5000);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		// create header row
		CellStyle style2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Times New Roman");
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 260);
		style2.setFont(font2);
		style2.setAlignment(CellStyle.ALIGN_CENTER);

		HSSFRow row2 = sheet.createRow(0);
		row2.createCell(0).setCellValue("Công ty điện lực thành phố Cần Thơ");
		row2.getCell(0).setCellStyle(style);

		row2.createCell(1).setCellValue("Kho Công ty �?iện Lực Cần Thơ");
		row2.getCell(1).setCellStyle(style);

		// create header row
		HSSFRow header = sheet.createRow(1);
		response.setHeader("Content-Disposition", "inline; filename=" + "BophansudungError.xls");

		header.createCell(0).setCellValue("Mã BPSD");
		header.getCell(0).setCellStyle(style2);

		header.createCell(1).setCellValue("Tên BPSD");
		header.getCell(1).setCellStyle(style2);

		header.createCell(2).setCellValue("�?ịa chỉ");
		header.getCell(2).setCellStyle(style2);

		header.createCell(3).setCellValue("Email");
		header.getCell(3).setCellStyle(style2);

		header.createCell(4).setCellValue("Số điện thoại");
		header.getCell(4).setCellStyle(style2);

		header.createCell(5).setCellValue("Lỗi");
		header.getCell(5).setCellStyle(style2);

		// create data rows
		int rowCount = 2;
		int i = 0;
		for (DonVi dv : donViError) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(dv.getDvMa());
			aRow.createCell(1).setCellValue(dv.getDvTen());
			aRow.createCell(2).setCellValue(dv.getDiaChi());
			aRow.createCell(3).setCellValue(dv.getEmail());
			aRow.createCell(4).setCellValue(dv.getSdt());

			aRow.createCell(5).setCellValue(statusError.get(i++));
		}
	}

}