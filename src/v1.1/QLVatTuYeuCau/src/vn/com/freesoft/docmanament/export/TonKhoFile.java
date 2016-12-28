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

import vn.com.freesoft.docmanament.entity.CTVatTu;
import vn.com.freesoft.docmanament.util.DateUtil;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
public class TonKhoFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<CTVatTu> listTon = (List<CTVatTu>) model.get("listTon");

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
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		sheet.setDefaultColumnStyle(6, style);

		// create title row
		HSSFRow title = sheet.createRow(0);
		title.createCell(0).setCellValue("Báo cáo vật tư tồn kho");
		title.getCell(0).setCellStyle(style);
		Date dayCurrent = new Date();
		int month = dayCurrent.getMonth() + 1;
		title.createCell(1).setCellValue("Tháng " + month);
		title.getCell(1).setCellStyle(style);

		title.createCell(2).setCellValue("Ngày in:");
		title.getCell(2).setCellStyle(style);

		title.createCell(3).setCellValue(DateUtil.toString(dayCurrent));
		title.getCell(3).setCellStyle(style);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		sheet.setDefaultColumnStyle(6, style);
		// sheet.setDefaultColumnStyle(7, style);
		// create header row
		HSSFRow header = sheet.createRow(1);
		CellStyle style2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Times New Roman");
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setFontHeight((short) 260);
		style2.setFont(font2);
		style2.setAlignment(CellStyle.ALIGN_CENTER);

		response.setHeader("Content-Disposition", "inline; filename=" + "vatTuTonKho.xls");
		header.createCell(0).setCellValue("STT");
		header.getCell(0).setCellStyle(style2);

		header.createCell(1).setCellValue("Mã Vật Tư");
		header.getCell(1).setCellStyle(style2);

		header.createCell(2).setCellValue("Tên Vật Tư");
		header.getCell(2).setCellStyle(style2);

		header.createCell(3).setCellValue("�?VT");
		header.getCell(3).setCellStyle(style2);

		header.createCell(4).setCellValue("Nơi Sản Xuất");
		header.getCell(4).setCellStyle(style2);

		header.createCell(5).setCellValue("Chất lượng");
		header.getCell(5).setCellStyle(style2);

		header.createCell(6).setCellValue("Số lượng");
		header.getCell(6).setCellStyle(style2);

		// header.createCell(7).setCellValue("�?ịnh mức");
		// header.getCell(7).setCellStyle(style2);

		// create row 2
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("D01");
		row2.getCell(0).setCellStyle(style);

		row2.createCell(1).setCellValue("Kho Công ty �?iện Lực Cần Thơ");
		row2.getCell(1).setCellStyle(style);

		int rowCount = 3;
		int stt = 1;
		for (CTVatTu ctvtTon : listTon) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(stt);
			aRow.createCell(1).setCellValue(ctvtTon.getVatTu().getVtMa());
			aRow.createCell(2).setCellValue(ctvtTon.getVatTu().getVtTen());
			aRow.createCell(3).setCellValue(ctvtTon.getVatTu().getDvt().getDvtTen());
			aRow.createCell(4).setCellValue(ctvtTon.getNoiSanXuat().getNsxTen());
			aRow.createCell(5).setCellValue(ctvtTon.getChatLuong().getClTen());
			aRow.createCell(6).setCellValue(ctvtTon.getSoLuongTon());
			// aRow.createCell(7).setCellValue(ctvtTon.getDinhMuc());
			stt++;
		}
	}

}