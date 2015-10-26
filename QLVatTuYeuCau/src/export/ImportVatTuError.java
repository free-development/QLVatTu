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

import model.CTVatTu;
import model.DonVi;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class ImportVatTuError extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<Object> objectList = (List<Object>) model.get("objectList");
		List<CTVatTu> ctvtList = (List<CTVatTu>) objectList.get(0);
		List<String> statusError = (List<String>) objectList.get(1);
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Vật tư bị lỗi import");
		
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		style.setFont(font);
		
		
		// create header row
		HSSFRow header = sheet.createRow(0);
		response.setHeader("Content-Disposition", "inline; filename=" + "VatTuLoi.xls");
		
		header.createCell(0).setCellValue("Mã Vật Tư");
		header.getCell(0).setCellStyle(style);
		
		header.createCell(1).setCellValue("Tên Vật Tư");
		header.getCell(1).setCellStyle(style);
		
		header.createCell(2).setCellValue("ĐVT");
		header.getCell(2).setCellStyle(style);
		
		header.createCell(3).setCellValue("Mã Nơi Sản Xuất");
		header.getCell(3).setCellStyle(style);
		
		header.createCell(4).setCellValue("Mã linh kiện");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Mã chất lượng");
		header.getCell(5).setCellStyle(style);
		
		header.createCell(6).setCellValue("Lỗi");
		header.getCell(6).setCellStyle(style);
		
		int rowCount = 1;
		int dem = 0;
		for (CTVatTu ctvt : ctvtList) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(ctvt.getVatTu().getVtMa());
			aRow.createCell(1).setCellValue(ctvt.getVatTu().getVtTen());
			aRow.createCell(2).setCellValue(ctvt.getVatTu().getDvt().getDvtTen());
			aRow.createCell(3).setCellValue(ctvt.getNoiSanXuat().getNsxMa());
			aRow.createCell(4).setCellValue("");
			aRow.createCell(5).setCellValue(ctvt.getChatLuong().getClMa());
			aRow.createCell(6).setCellValue(statusError.get(dem));
			dem++;
		}
	}

}