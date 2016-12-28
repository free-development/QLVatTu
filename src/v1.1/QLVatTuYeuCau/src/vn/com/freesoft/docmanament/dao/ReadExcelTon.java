
package vn.com.freesoft.docmanament.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vn.com.freesoft.docmanament.entity.CTVatTu;
import vn.com.freesoft.docmanament.entity.ChatLuong;
import vn.com.freesoft.docmanament.entity.NoiSanXuat;

/**
 * @author quoioln
 *
 */
public class ReadExcelTon {
	public static ArrayList<Object> readXlsx(File file) throws FileNotFoundException, IOException {
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<String> vtTenError = new ArrayList<String>();
		ArrayList<String> dvtTenError = new ArrayList<String>();
		ArrayList<String> nsxTenError = new ArrayList<String>();
		ArrayList<String> clTenError = new ArrayList<String>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		Iterator rows = sheet.rowIterator();
		int j = 0;
		while (j < 4 && rows.hasNext()) {
			rows.next();
			j++;
		}
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			int count = 0;
			String vtMa = "";
			String vtTen = "";
			String dvt = "";
			String nsxTen = "";
			String clTen = "";
			int soLuong = -1;
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					switch (count) {
					case 1:
						vtMa = cell.getStringCellValue();
						vtMa = vtMa.replaceAll("\'", "");
						vtMa = vtMa.replaceAll("\"", "");
						break;
					case 2:
						vtTen = cell.getStringCellValue();
						break;
					case 3:
						dvt = cell.getStringCellValue();
						break;
					case 4:
						nsxTen = cell.getStringCellValue();
						break;
					case 5:
						clTen = cell.getStringCellValue();
						break;
					}
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					switch (count) {
					case 1:
						vtMa = cell.getNumericCellValue() + "";
						break;
					case 6:
						soLuong = (int) cell.getNumericCellValue();
						break;
					}
				}
				count++;

			}
			if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0
					&& clTen.length() == 0 && soLuong == -1)
				break;
			if (vtMa.length() == 0 || soLuong == -1) {
				vtMaError.add(vtMa);
				vtTenError.add(vtTen);
				dvtTenError.add(dvt);
				nsxTenError.add(nsxTen);
				clTenError.add(clTen);
				soLuongError.add((int) soLuong);
				statusError.add("Lỗi dữ liệu");
			} else {
				String nsxMa = "";
				String clMa = "";
				if (nsxTen.length() == 0)
					nsxMa = "VIE";
				else {
					NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
					NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
					if (nsxTemp != null)
						nsxMa = nsxTemp.getNsxMa();
					nsxDAO.close();
				}
				if (clTen.length() == 0)
					nsxMa = "000";
				else {
					ChatLuongDAO clDAO = new ChatLuongDAO();
					ChatLuong clTemp = clDAO.getByNameCl(clTen);
					if (clTemp != null)
						clMa = clTemp.getClMa();
					clDAO.close();
				}

				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				if (nsxMa.length() == 0 || clMa.length() == 0) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTen);
					dvtTenError.add(dvt);
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add(soLuong);
					statusError.add("Vật tư không tồn tại");

				} else {
					CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMa, nsxMa, clMa);
					if (ctvt == null || ctvt.getDaXoa() == 0) {
						vtMaError.add(vtMa);
						vtTenError.add(vtTen);
						dvtTenError.add(dvt);
						nsxTenError.add(nsxTen);
						clTenError.add(clTen);
						soLuongError.add(soLuong);
						statusError.add("Vật tư không tồn tại");
					} else {
						ctvt.setSoLuongTon(soLuong);
						ctvtDAO.updateCTVatTu(ctvt);
					}
				}
				ctvtDAO.close();
			}
		}
		ArrayList<Object> objectListError = new ArrayList<Object>();
		if (vtMaError.size() > 0) {
			objectListError.add(vtMaError);
			objectListError.add(vtTenError);
			objectListError.add(dvtTenError);
			objectListError.add(nsxTenError);
			objectListError.add(clTenError);
			objectListError.add(statusError);
		}
		return objectListError;
	}

	// read xls
	public static ArrayList<Object> readXls(File file) throws FileNotFoundException, IOException {
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<String> vtTenError = new ArrayList<String>();
		ArrayList<String> dvtTenError = new ArrayList<String>();
		ArrayList<String> nsxTenError = new ArrayList<String>();
		ArrayList<String> clTenError = new ArrayList<String>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		Iterator rows = sheet.rowIterator();
		int j = 0;
		while (j < 3 && rows.hasNext()) {
			rows.next();
			j++;
		}
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			int count = 0;
			String vtMa = "";
			String vtTen = "";
			String dvt = "";
			String nsxTen = "";
			String clTen = "";
			int soLuong = -1;
			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					switch (count) {
					case 1:
						vtMa = cell.getStringCellValue();
						vtMa = vtMa.replaceAll("\'", "");
						vtMa = vtMa.replaceAll("\"", "");
						break;
					case 2:
						vtTen = cell.getStringCellValue();
						break;
					case 3:
						dvt = cell.getStringCellValue();
						break; // ArrayList<DonViTinh> dvtList = new
								// ArrayList<DonViTinh>();

					case 4:
						nsxTen = cell.getStringCellValue();
						break;
					case 5:
						clTen = cell.getStringCellValue();
						break;
					}
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					switch (count) {
					case 1:
						vtMa = cell.getNumericCellValue() + "";
						break;
					case 6:
						soLuong = (int) cell.getNumericCellValue();
						break;
					}
				}
				count++;
			}
			if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0
					&& clTen.length() == 0 && soLuong == -1)
				break;
			if (vtMa.length() == 0 || soLuong == -1) {
				vtMaError.add(vtMa);
				vtTenError.add(vtTen);
				dvtTenError.add(dvt);
				nsxTenError.add(nsxTen);
				clTenError.add(clTen);
				soLuongError.add((int) soLuong);
				statusError.add("Lỗi dữ liệu");
			} else {
				String nsxMa = "";
				String clMa = "";
				if (nsxTen.length() == 0)
					nsxMa = "VIE";
				else {
					NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
					NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
					if (nsxTemp != null)
						nsxMa = nsxTemp.getNsxMa();
					nsxDAO.close();
				}
				if (clTen.length() == 0)
					clMa = "000";
				else {
					ChatLuongDAO clDAO = new ChatLuongDAO();
					ChatLuong clTemp = clDAO.getByNameCl(clTen);
					if (clTemp != null)
						clMa = clTemp.getClMa();
					clDAO.close();
				}
				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				if (nsxMa.length() == 0 || clMa.length() == 0) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTen);
					dvtTenError.add(dvt);
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add(soLuong);
					statusError.add("Vật tư không tồn tại");

				} else {
					CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMa, nsxMa, clMa);
					if (ctvt == null || ctvt.getDaXoa() == 1) {
						vtMaError.add(vtMa);
						vtTenError.add(vtTen);
						dvtTenError.add(dvt);
						nsxTenError.add(nsxTen);
						clTenError.add(clTen);
						soLuongError.add(soLuong);
						statusError.add("Vật tư không tồn tại");
					} else {
						ctvt.setSoLuongTon(soLuong);
						ctvtDAO.updateCTVatTu(ctvt);
					}
				}

				ctvtDAO.close();
			}
		}
		ArrayList<Object> objectListError = new ArrayList<Object>();
		if (statusError.size() > 0) {
			objectListError.add(vtMaError);
			objectListError.add(vtTenError);
			objectListError.add(dvtTenError);
			objectListError.add(nsxTenError);
			objectListError.add(clTenError);
			objectListError.add(soLuongError);
			objectListError.add(statusError);
		}
		return objectListError;
	}
}
