package export;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
public class VatTuAlert extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<CTVatTu> listCtvt = (List<CTVatTu>) model.get("vatTuAlert");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Vật tư dưới định mức");
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 12000);
		sheet.setColumnWidth(2, 1500);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4500);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		
		font.setFontHeight((short)260);
		style.setFont(font);
		sheet.setDefaultRowHeight((short)400);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		sheet.setDefaultColumnStyle(6, style);
		
		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "VatTuDuoiDinhMuc.xls");
		
		header.createCell(0).setCellValue("Mã Vật Tư");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Tên Vật Tư");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("ĐVT");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Mã Nơi Sản Xuất");
		header.getCell(3).setCellStyle(style);
		
		
		header.createCell(4).setCellValue("Mã chất lượng");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Số lượng tồn");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("Định mức");
		header.getCell(6).setCellStyle(style);
		
		int rowCount = 1;
		for (CTVatTu ctvt : listCtvt) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(ctvt.getVatTu().getVtMa());
			aRow.createCell(1).setCellValue(ctvt.getVatTu().getVtTen());
			aRow.createCell(2).setCellValue(ctvt.getVatTu().getDvt().getDvtTen());
			aRow.createCell(3).setCellValue(ctvt.getNoiSanXuat().getNsxMa());
			aRow.createCell(4).setCellValue(ctvt.getChatLuong().getClMa());
			aRow.createCell(5).setCellValue(ctvt.getSoLuongTon());
			aRow.createCell(6).setCellValue(ctvt.getDinhMuc());
		}
	}

}