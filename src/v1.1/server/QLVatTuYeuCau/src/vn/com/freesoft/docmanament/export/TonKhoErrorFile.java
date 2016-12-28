package vn.com.freesoft.docmanament.export;

import java.util.Date;
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

import vn.com.freesoft.docmanament.util.DateUtil;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class TonKhoErrorFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 260);
		style.setFont(font);

		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 7000);
		sheet.setColumnWidth(2, 10000);
		sheet.setColumnWidth(3, 2500);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(5, 6000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 5000);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		sheet.setDefaultColumnStyle(6, style);
		sheet.setDefaultColumnStyle(7, style);
		// create header row
		CellStyle style2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Times New Roman");
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 260);
		style2.setFont(font2);
		style2.setAlignment(CellStyle.ALIGN_CENTER);

		// create title row
		HSSFRow title = sheet.createRow(0);
		title.createCell(0).setCellValue("Vật tư tồn khi bị lỗi import");
		title.getCell(0).setCellStyle(style);
		Date dayCurrent = new Date();
		int month = dayCurrent.getMonth() + 1;
		title.createCell(1).setCellValue("Tháng " + month);
		title.getCell(1).setCellStyle(style);

		title.createCell(2).setCellValue("Ngày import:");
		title.getCell(2).setCellStyle(style);

		title.createCell(3).setCellValue(DateUtil.toString(dayCurrent));
		title.getCell(3).setCellStyle(style);

		// create header row
		HSSFRow header = sheet.createRow(1);
		header.setRowStyle(style2);
		response.setHeader("Content-Disposition", "inline; filename=" + "vatTuTonKhoError.xls");
		header.createCell(0).setCellValue("STT");
		header.getCell(0).setCellStyle(style2);

		header.createCell(1).setCellValue("Mã Vật Tư");
		header.getCell(1).setCellStyle(style2);
		//
		header.createCell(2).setCellValue("Tên Vật Tư");
		header.getCell(2).setCellStyle(style2);

		header.createCell(3).setCellValue("�?VT");
		header.getCell(3).setCellStyle(style2);

		header.createCell(4).setCellValue("Nơi Sản Xuất");
		header.getCell(4).setCellStyle(style2);

		header.createCell(5).setCellValue("Mã chất lượng");
		header.getCell(5).setCellStyle(style2);

		header.createCell(6).setCellValue("Số lượng");
		header.getCell(6).setCellStyle(style2);

		header.createCell(7).setCellValue("Lỗi");
		header.getCell(7).setCellStyle(style2);
		// create row 2
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("D01");
		row2.getCell(0).setCellStyle(style);

		row2.createCell(1).setCellValue("Kho Công ty �?iện Lực Cần Thơ");
		row2.getCell(1).setCellStyle(style);

		int rowCount = 3;
		int count = 0;
		for (String vtMa : vtMaError) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(count + 1);
			aRow.createCell(1).setCellValue(vtMa);
			aRow.createCell(2).setCellValue(vtTenError.get(count));
			aRow.createCell(3).setCellValue(dvtTenError.get(count));
			aRow.createCell(4).setCellValue(nsxTenError.get(count));
			aRow.createCell(5).setCellValue(clTenError.get(count));
			aRow.createCell(6).setCellValue(soLuongError.get(count));
			aRow.createCell(7).setCellValue(statusError.get(count));
			count++;
		}
	}

}