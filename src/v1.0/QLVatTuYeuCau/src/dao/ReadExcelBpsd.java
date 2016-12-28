/**
 * 
 */
package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

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

import model.CTVatTu;
import model.ChatLuong;
import model.DonVi;
import model.DonViTinh;
import model.NoiSanXuat;
import model.VatTu;

/**
 * @author camnhung
 *
 */
public class ReadExcelBpsd {
	public static ArrayList<Object> readXlsx(File file) throws FileNotFoundException, IOException {
		ArrayList<DonVi> donViError = new ArrayList<DonVi>();
		ArrayList<String> statusError = new ArrayList<String>();
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		Iterator rows = sheet.rowIterator();
		int j = 0;
		while (j < 2 && rows.hasNext()) {
			rows.next();
			j++;
		}
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			int count = 0;
			String dvMa = "";
			String dvTen = "";
			String diaChi = "";
			String email = "";
			String sdt = "";
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					switch (count) {
					case 0:
						dvMa = cell.getStringCellValue();
						dvMa = dvMa.replaceAll("\'", "");
						break;
					case 1:
						dvTen = cell.getStringCellValue();
						break;
					case 2:
						diaChi = cell.getStringCellValue();
						break;	
					case 3:
						email = cell.getStringCellValue();
						break;
					case 4:
						sdt = cell.getStringCellValue();
						break;	
					} 
				} else {
					switch (count) {
					case 0:
						dvMa = (int) cell.getNumericCellValue() + "";
						break;
					/*
					case 3:
						sdt = cell.getNumericCellValue() + "";;
						break;
						*/
					}
				}
				count++;
			}
			if (dvMa.length() == 0 && dvTen.length() == 0)
				break;
			DonVi dv = new DonVi(dvMa, dvTen, sdt,diaChi, email, 0);
			if (dvMa.length() == 0 || dvTen.length() == 0 || diaChi.length() == 0) {
				donViError.add(dv);
				statusError.add("Lỗi dữ liệu");
			} else {
				DonViDAO dvDAO = new DonViDAO();
				DonVi temp = dvDAO.getDonVi(dvMa);
				if (temp ==  null) {
					dvDAO.addDonVi(dv);
				}
				else if (temp.getDaXoa() == 1) {
					DonViDAO dvDAO2 = new DonViDAO();
					dvDAO2.updateDonVi(dv);
					dvDAO2.disconnect();
				} else {
					donViError.add(dv);
					statusError.add("Đã tồn tại");
				}
				dvDAO.close();
			}
		}
		ArrayList<Object> errorList = new ArrayList<Object>();
		if (donViError.size() > 0) {
			errorList.add(donViError);
			errorList.add(statusError);
		}
		return errorList;
	}
	
	// read xls
	public static ArrayList<Object> readXls(File file) throws FileNotFoundException, IOException {
		ArrayList<DonVi> donViError = new ArrayList<DonVi>();
		ArrayList<String> statusError = new ArrayList<String>();
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = wb.getSheetAt(0);
		Row row;
		Cell cell;
		Iterator rows = sheet.rowIterator();
		int j = 0;
		while (j < 2 && rows.hasNext()) {
			rows.next();
			j++;
		}
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			j++;
			if (j == 1)
				continue;
			Iterator cells = row.cellIterator();
			int count = 0;
			String dvMa = "";
			String dvTen = "";
			String diaChi = "";
			String email = "";
			String sdt = "";
			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					switch (count) {
					case 0:
						dvMa = cell.getStringCellValue();
						dvMa = dvMa.replaceAll("\'", "");
						break;
					case 1:
						dvTen = cell.getStringCellValue();
						break;
					case 2:
						diaChi = cell.getStringCellValue();
						break;	
					case 3:
						email = cell.getStringCellValue();
						break;
					case 4:
						sdt = cell.getStringCellValue();
						break;	
					} 
				} else {
					switch (count) {
					case 0:
						dvMa = (int) cell.getNumericCellValue() + "";
						break;
					/*
					case 3:
						sdt = cell.getNumericCellValue() + "";;
						break;
						*/
					}
				}
				count++;
			}
			if (dvMa.length() == 0 && dvTen.length() == 0 && diaChi.length() == 0)
				break;
			DonVi dv = new DonVi(dvMa, dvTen, sdt,diaChi, email, 0);
			if (dvMa.length() == 0 || dvTen.length() == 0 || diaChi.length() == 0) {
				donViError.add(dv);
				statusError.add("Lỗi dữ liệu");
			} else {
				DonViDAO dvDAO = new DonViDAO();
				DonVi temp = dvDAO.getDonVi(dvMa);
				if (temp ==  null) {
					dvDAO.addDonVi(dv);
				} else if (temp.getDaXoa() == 1) {
					DonViDAO dvDAO2 = new DonViDAO();
					dvDAO2.updateDonVi(dv);
					dvDAO2.disconnect();
				} else {
					donViError.add(dv);
					statusError.add("Đã tồn tại");
				}
				dvDAO.close();
			}
		}
		ArrayList<Object> errorList = new ArrayList<Object>();
		if (donViError.size() > 0) {
			errorList.add(donViError);
			errorList.add(statusError);
		}
		return errorList;
	}
}
