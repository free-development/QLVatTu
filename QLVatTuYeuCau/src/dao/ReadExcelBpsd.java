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
public class ReadExcelBpsd {
	public static boolean readXlsx(File file) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<DonVi> dvList = new ArrayList<DonVi>();
			
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				j++;
				if (j < 2)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String dvMa = "";
				String dvTen = "";
				String sdt = "";
				String diaChi = "";
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							dvMa = cell.getStringCellValue();
							break;
						case 1:
							dvTen = cell.getStringCellValue();
							break;
						case 2:
							diaChi = cell.getStringCellValue();
							break;	
						}
					} 
					count++;
				}
				if (dvMa.length() == 0 && dvTen.length() == 0)
					break;
				if (dvMa.length() == 0 || dvTen.length() == 0)
					return false;
			
				DonVi dv = new DonVi(dvMa, dvTen,"",diaChi,"",0);
				
				dvList.add(dv);
			}
			int lenght = dvList.size();
			
			for (int i = 0; i< lenght; i++) {
				
				DonViDAO dvDAO = new DonViDAO();
				
				DonVi dv = dvList.get(i);

					DonVi temp = dvDAO.getDonVi(dv.getDvMa());
					if (temp ==  null) {
						dvDAO.addDonVi(dv);
					}
			dvDAO.close();
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
			ArrayList<DonVi> dvList = new ArrayList<DonVi>(); 
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
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							switch (count) {
							case 0:
								dvMa = cell.getStringCellValue();
								break;
							case 1:
								dvTen = cell.getStringCellValue();
								break;
							case 2:
								diaChi = cell.getStringCellValue();
								break;	
							} 
						}
						count++;
					}
					if (dvMa.length() == 0 && dvTen.length() == 0 && diaChi.length() == 0)
						break;
					if (dvMa.length() == 0 || dvTen.length() == 0)
						return false;
				
					DonVi dv = new DonVi(dvMa, dvTen,"",diaChi,"",0);
					
					dvList.add(dv);
				}
				int lenght = dvList.size();
				System.out.print(lenght);
				for (int i = 0; i< lenght; i++) {
					
					DonViDAO dvDAO = new DonViDAO();
					
					DonVi dv = dvList.get(i);

						DonVi temp = dvDAO.getDonVi(dv.getDvMa());
						if (temp ==  null) {
							dvDAO.addDonVi(dv);
						}
				dvDAO.close();
				}
		}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
	}
}
