/**
 * 
 */
package dao;

import java.io.File;
import java.io.FileInputStream;
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

import model.CTVatTu;
import model.ChatLuong;
import model.DonViTinh;
import model.NoiSanXuat;
import model.VatTu;

/**
 * @author quoioln
 *
 */
public class ReadExcelTon {
	public static boolean readXlsx(File file) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			ArrayList<VatTu> vatTuList = new ArrayList<VatTu>();
			ArrayList<String> nsxList = new ArrayList<String>();
			ArrayList<String> chatLuongList = new ArrayList<String>();
			ArrayList<CTVatTu> ctvtList = new ArrayList<CTVatTu>();
			ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>();
			ArrayList<Integer> soLuongTonList = new ArrayList<Integer>();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				j++;
				if (j < 4)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxTen = "";
				String clTen = "";
				double soLuong = -1;
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							vtMa = cell.getStringCellValue();
							break;
						case 1:
							vtTen = cell.getStringCellValue();
							break;
						case 2:
							dvt = cell.getStringCellValue();
							break;	
						case 3:
							nsxTen = cell.getStringCellValue();
							break;
						case 4:
							clTen = cell.getStringCellValue();
							break;
						}
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						switch (count) {
							case 5:
								soLuong = cell.getNumericCellValue();
								break;
						}
					}
					count++;
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0 && clTen.length() == 0 && soLuong == -1)
					break;
				if (vtMa.length() == 0 || vtTen.length() == 0 || dvt.length() == 0 || nsxTen.length() == 0 || clTen.length() == 0 || soLuong == -1)
					return false;
				DonViTinh donViTinh = new DonViTinh(dvt, 0);
				VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
				vatTuList.add(vatTu);
				nsxList.add(nsxTen);
				chatLuongList.add(clTen);
				dvtList.add(donViTinh);
				soLuongTonList.add((int)soLuong);
			}
			int lenght = vatTuList.size();
			
			for (int i = 0; i< lenght; i++) {
				VatTuDAO vtDAO = new VatTuDAO();
				NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
				ChatLuongDAO clDAO = new ChatLuongDAO();
				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				DonViTinhDAO dvtDAO = new DonViTinhDAO();
				
				VatTu vatTu = vatTuList.get(i);
				String nsxTen = nsxList.get(i);
				String clTen = chatLuongList.get(i);
				int soLuongTon = (int)soLuongTonList.get(i);
				
				NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
				ChatLuong chatLuongTemp = clDAO.getByNameCl(clTen);
				
				CTVatTu ctvt = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxTemp.getNsxMa(), chatLuongTemp.getClMa());
//				System.out.println("VTMa = "+ctvt.getCtvtId()+ "\n vtTen = "+ vtTen + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
				if (ctvt != null && nsxTemp !=  null && nsxTemp !=  null) {
					
					ctvt.setSoLuongTon(soLuongTon);
					ctvtDAO.updateCTVatTu(ctvt);
					System.out.println(ctvt);
				}
				vtDAO.close();
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
				dvtDAO.close();
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
			ArrayList<VatTu> vatTuList = new ArrayList<VatTu>();
			ArrayList<String> nsxList = new ArrayList<String>();
			ArrayList<String> chatLuongList = new ArrayList<String>();
			ArrayList<CTVatTu> ctvtList = new ArrayList<CTVatTu>();
			ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>();
			ArrayList<Integer> soLuongTonList = new ArrayList<Integer>();
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				j++;
				if (j < 4)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxTen = "";
				String clTen = "";
				double soLuong = -1;
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 1:
							vtMa = cell.getStringCellValue();
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
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						switch (count) {
						case 6:
							soLuong = cell.getNumericCellValue();
							break;
					}
				}
				count++;
			}
				System.out.println("VTMa = "+vtMa+ "\n vtTen = "+ vtTen + "\n nsxTen= "+ nsxTen + "\n clTen = " + clTen + "\n soLuong = " + soLuong);
			if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0 && clTen.length() == 0 && soLuong == -1)
				break;
			if (vtMa.length() == 0 || vtTen.length() == 0 || dvt.length() == 0 || nsxTen.length() == 0 || clTen.length() == 0 || soLuong == -1)
				return false;
			DonViTinh donViTinh = new DonViTinh(dvt, 0);

			VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
			vatTuList.add(vatTu);
			nsxList.add(nsxTen);
			chatLuongList.add(clTen);
			dvtList.add(donViTinh);
			soLuongTonList.add((int)soLuong);
		}
		int lenght = vatTuList.size();
		
		for (int i = 0; i< lenght; i++) {
			VatTuDAO vtDAO = new VatTuDAO();
			NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
			ChatLuongDAO clDAO = new ChatLuongDAO();
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			
			VatTu vatTu = vatTuList.get(i);
			String nsxTen = nsxList.get(i);
			String clTen = chatLuongList.get(i);
			int soLuongTon = (int)soLuongTonList.get(i);
			
			NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
			ChatLuong chatLuongTemp = clDAO.getByNameCl(clTen);
			System.out.println("VTMa = "+vatTu.getVtMa()+ "\n vtTen = "+ vatTu.getVtTen() + "\n nsxTen= "+ nsxTen + "\n clTen = " + clTen);
			if (nsxTemp !=  null && chatLuongTemp !=  null) {
				CTVatTu ctvt = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxTemp.getNsxMa(), chatLuongTemp.getClMa());
				if (ctvt != null) {
					System.out.println(nsxTemp.getNsxTen());
					ctvt.setSoLuongTon(soLuongTon);
					ctvtDAO.updateCTVatTu(ctvt);
					System.out.println(ctvt);
				}
			}
			vtDAO.close();
			nsxDAO.close();
			clDAO.close();
			ctvtDAO.close();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return true;
	}
}
