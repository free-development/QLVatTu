package export;

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

import model.CTVatTu;
import model.CongVan;
import util.DateUtil;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * @author www.codejava.net
 *
 */
public class CongVanFile extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<CongVan> congVanList = (List<CongVan>) model.get("objectList");
		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Báo cáo công văn");
		
		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short)260);
		style.setFont(font);
		
		sheet.setDefaultRowHeight((short) 400);
		sheet.setColumnWidth(0, 2500);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 6000);
		sheet.setColumnWidth(6, 6000);
		sheet.setColumnWidth(6, 8000);
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 4000);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);
		sheet.setDefaultColumnStyle(2, style);
		sheet.setDefaultColumnStyle(3, style);
		sheet.setDefaultColumnStyle(4, style);
		sheet.setDefaultColumnStyle(5, style);
		sheet.setDefaultColumnStyle(6, style);
		sheet.setDefaultColumnStyle(7, style);
		sheet.setDefaultColumnStyle(8, style);
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
		response.setHeader("Content-Disposition", "inline; filename=" + "BaoCaoCongVan.xls");
		
		header.createCell(0).setCellValue("Số P.VT");
		header.getCell(0).setCellStyle(style2);
		
		header.createCell(1).setCellValue("Ngày P.VT nhận");
		header.getCell(1).setCellStyle(style2);
		
		header.createCell(2).setCellValue("Số công văn đến");
		header.getCell(2).setCellStyle(style2);
		
		header.createCell(3).setCellValue("Ngày công văn đến");
		header.getCell(3).setCellStyle(style2);
		
		header.createCell(4).setCellValue("Mục đích");
		header.getCell(4).setCellStyle(style2);
		
		header.createCell(5).setCellValue("Nơi gửi");
		header.getCell(5).setCellStyle(style2);
		
		header.createCell(6).setCellValue("Nôi dung công tác");
		header.getCell(6).setCellStyle(style2);
		
		header.createCell(7).setCellValue("Bút phê");
		header.getCell(7).setCellStyle(style2);
		
		header.createCell(8).setCellValue("Trạng thái");
		header.getCell(8).setCellStyle(style2);
		
		int rowCount = 1;
		for (CongVan congVan : congVanList) {
			HSSFRow aRow = sheet.createRow(rowCount++);
			java.sql.Date cvNgayNhan = congVan.getCvNgayNhan();
			java.sql.Date cvNgayDi = congVan.getCvNgayDi();
			aRow.createCell(0).setCellValue(congVan.getSoDen() + "/" + (cvNgayNhan.getYear() + 1900) );
			aRow.createCell(1).setCellValue(DateUtil.toString(cvNgayDi));
			aRow.createCell(2).setCellValue(congVan.getCvSo());
			aRow.createCell(3).setCellValue(DateUtil.toString(cvNgayDi));
			aRow.createCell(4).setCellValue(congVan.getMucDich().getMdTen());
			aRow.createCell(5).setCellValue(congVan.getDonVi().getDvTen());
			aRow.createCell(6).setCellValue(congVan.getTrichYeu());
			aRow.createCell(7).setCellValue(congVan.getButPhe());
			aRow.createCell(8).setCellValue(congVan.getTrangThai().getTtTen());
			
		}
	}

}