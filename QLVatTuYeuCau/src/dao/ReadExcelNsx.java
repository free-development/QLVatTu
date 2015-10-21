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
 * @author quoioln
 *
 */
public class ReadExcelNsx {
	public static ArrayList<Object> readXlsx(File file) {
		ArrayList<NoiSanXuat> nsxError = new ArrayList<NoiSanXuat>();
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
				String nsxMa = "";
				String nsxTen = "";
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();
						if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
							switch (count) {
							case 0:
								nsxMa = cell.getStringCellValue();
								break;
							case 1:
								nsxTen = cell.getStringCellValue();
								break;
							}
						} 
						count++;
					}
					if (nsxMa.length() == 0 && nsxTen.length() == 0)
						break;
					NoiSanXuat nsx = new NoiSanXuat(nsxMa, nsxTen,0);
					if (nsxMa.length() == 0 || nsxTen.length() == 0) {
						nsxError.add(nsx);
						statusError.add("Lỗi dữ liệu");
					} else {
						NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
						NoiSanXuat temp = nsxDAO.getNoiSanXuat(nsxMa);
						if (temp ==  null) {
							nsxDAO.addNoiSanXuat(nsx);
						} else {
							nsxError.add(nsx);
							statusError.add("Đã tồn tại");
						}
						nsxDAO.close();
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<Object> errorList = new ArrayList<Object>();
			if (nsxError.size() > 0) {
				errorList.add(nsxError);
				errorList.add(statusError);
			}
			return errorList;
	}
	
	// read xls
	public static ArrayList<Object> readXls(File file) {
		ArrayList<NoiSanXuat> nsxError = new ArrayList<NoiSanXuat>();
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
				String nsxMa = "";
				String nsxTen = "";
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							switch (count) {
							case 0:
								nsxMa = cell.getStringCellValue();
								break;
							case 1:
								nsxTen = cell.getStringCellValue();
								break;
							}
						} 
						count++;
					}
					System.out.println("******" + nsxMa + "**" + nsxTen);
					if (nsxMa.length() == 0 && nsxTen.length() == 0)
						break;
					NoiSanXuat nsx = new NoiSanXuat(nsxMa, nsxTen,0);
					if (nsxMa.length() == 0 || nsxTen.length() == 0) {
						nsxError.add(nsx);
						statusError.add("Lỗi dữ liệu");
					} else {
//						System.out.println("******" + nsxMa + "**" + nsxTen);
						NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
						NoiSanXuat temp = nsxDAO.getNoiSanXuat(nsxMa);
						
						if (temp ==  null) {
							System.out.println("not exists ******" + nsxMa + "**" + nsxTen);
							nsxDAO.addNoiSanXuat(nsx);
						} else if (temp.getDaXoa() == 1){
							NoiSanXuatDAO nsxDAO2 = new NoiSanXuatDAO();
							System.out.println("exists ******" + nsxMa + "**" + nsxTen);
							nsxDAO2.updateNoiSanXuat(nsx);
							nsxDAO2.disconnect();
						} else {
							nsxError.add(nsx);
							statusError.add("Đã tồn tại");
						}
						nsxDAO.close();
					}
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<Object> errorList = new ArrayList<Object>();
			if (nsxError.size() > 0) {
				errorList.add(nsxError);
				errorList.add(statusError);
			}
			return errorList;
	}
}
