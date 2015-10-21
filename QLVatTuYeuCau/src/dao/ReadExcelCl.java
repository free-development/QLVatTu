/**
 * 
 */
package dao;

import java.io.File;
import java.io.FileInputStream;
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
public class ReadExcelCl {
	public static ArrayList<Object> readXlsx(File file) {
		ArrayList<ChatLuong> clError =  new ArrayList<ChatLuong>();
		ArrayList<String> statusError = new ArrayList<String>();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			if (rows.hasNext())
				rows.next();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				int count = 0;
				String clMa = "";
				String clTen = "";
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							clMa = cell.getStringCellValue();
							break;
						case 1:
							clTen = cell.getStringCellValue();
							break;
						}
					} 
					count++;
				}
				if (clMa.length() == 0 && clTen.length() == 0)
					break;
				ChatLuong cl = new ChatLuong(clMa, clTen , 0);
				if (clMa.length() == 0 || clTen.length() == 0) {
					clError.add(cl);
					statusError.add("Lỗi dữ liệu");
				} else {
					ChatLuongDAO clDAO = new ChatLuongDAO();
					ChatLuong clTemp = clDAO.getChatLuong(clMa);
					if (clTemp == null) {
						clDAO.addChatLuong(cl);
					} else if (clTemp.getDaXoa() == 1) {
						ChatLuongDAO clDAO2 = new ChatLuongDAO();
						clDAO2.updateChatLuong(cl);
						clDAO2.disconnect();
					} else {
						clError.add(cl);
						statusError.add("Đã tồn tại");
					}
					clDAO.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object> errorList = new ArrayList<Object>();
		if (clError.size() > 0) {
			errorList.add(clError);
			errorList.add(statusError);
		}
		return errorList;
	}
	
	// read xls
	public static ArrayList<Object> readXls(File file) {
		ArrayList<ChatLuong> clError =  new ArrayList<ChatLuong>();
		ArrayList<String> statusError = new ArrayList<String>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			if (rows.hasNext())
				rows.next();
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				int count = 0;
				String clMa = "";
				String clTen = "";
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							clMa = cell.getStringCellValue();
							break;
						case 1:
							clTen = cell.getStringCellValue();
							break;
						}
					} 
					count++;
				}
				if (clMa.length() == 0 && clTen.length() == 0)
					break;
				ChatLuong cl = new ChatLuong(clMa, clTen , 0);
				if (clMa.length() == 0 || clTen.length() == 0) {
					clError.add(cl);
					statusError.add("Lỗi dữ liệu");
				} else {
					ChatLuongDAO clDAO = new ChatLuongDAO();
					ChatLuong clTemp = clDAO.getChatLuong(clMa);
					if (clTemp == null) {
						clDAO.addChatLuong(cl);
					} else if (clTemp.getDaXoa() == 1) {
						ChatLuongDAO clDAO2 = new ChatLuongDAO();
						clDAO2.updateChatLuong(cl);
						clDAO2.disconnect();
					} else {
						clError.add(cl);
						statusError.add("Đã tồn tại");
					}
					clDAO.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object> errorList = new ArrayList<Object>();
		if (clError.size() > 0) {
			errorList.add(clError);
			errorList.add(statusError);
		}
		return errorList;
	}
}
