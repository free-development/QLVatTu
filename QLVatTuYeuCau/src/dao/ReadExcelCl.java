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
	public static boolean readXlsx(File file) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<ChatLuong> clList = new ArrayList<ChatLuong>();
			
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				j++;
				if (j == 1)
					continue;
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
				if (clMa.length() == 0 || clTen.length() == 0)
					return false;
			
				ChatLuong cl = new ChatLuong(clMa, clTen , 0);
				
				clList.add(cl);
			}
			int lenght = clList.size();
			
			for (int i = 0; i< lenght; i++) {
				
				ChatLuongDAO clDAO = new ChatLuongDAO();
				
				ChatLuong cl = clList.get(i);

				ChatLuong temp = clDAO.getChatLuong(cl.getClMa());
					if (temp ==  null) {
						clDAO.addChatLuong(cl);
					}
			clDAO.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// read xls
	public static boolean readXls(File file) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<ChatLuong> clList = new ArrayList<ChatLuong>(); 
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				j++;
				if (j == 1)
					continue;
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
					if (clMa.length() == 0 || clTen.length() == 0)
						return false;
				
					ChatLuong cl = new ChatLuong(clMa, clTen , 0);
					
					clList.add(cl);
				}
				int lenght = clList.size();
				
				for (int i = 0; i< lenght; i++) {
					
					ChatLuongDAO clDAO = new ChatLuongDAO();
					
					ChatLuong cl = clList.get(i);

					ChatLuong temp = clDAO.getChatLuong(cl.getClMa());
					if (temp ==  null) {
						clDAO.addChatLuong(cl);
					}
				clDAO.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
	}
}
