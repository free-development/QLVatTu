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
	public static ArrayList<Object> readXlsx(File file) {
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<String> vtTenError = new ArrayList<String>();
		ArrayList<String> dvtTenError = new ArrayList<String>();
		ArrayList<String> nsxTenError = new ArrayList<String>();
		ArrayList<String> clTenError = new ArrayList<String>();
//		ArrayList<DonViTinh> dvtError = new ArrayList<DonViTinh>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		
		ArrayList<String> vtMaList = new ArrayList<String>();
		ArrayList<String> vtTenList = new ArrayList<String>();
		ArrayList<String> dvtTenList = new ArrayList<String>();
		ArrayList<String> nsxList = new ArrayList<String>();
		ArrayList<String> chatLuongList = new ArrayList<String>();
//		ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>();
		ArrayList<Integer> soLuongTonList = new ArrayList<Integer>();
		try {
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
				double soLuong = -1;
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
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
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						switch (count) {
							case 6:
								soLuong = cell.getNumericCellValue();
								break;
						}
					}
					count++;
					System.out.println("nsx Ten = " + nsxTen + "\tcl Ten = " + clTen);
					
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0 && clTen.length() == 0 && soLuong == -1)
					break;
				if (vtMa.length() == 0 || soLuong == -1) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTen);
					dvtTenError.add(dvt);
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add((int)soLuong);
					statusError.add("Lỗi dữ liệu");
				} else {
//				DonViTinh donViTinh = new DonViTinh(dvt, 0);
//				VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
					vtMaList.add(vtMa);
					vtTenList.add(vtTen);
					dvtTenList.add(dvt);
					nsxList.add(nsxTen);
					chatLuongList.add(clTen);
					soLuongTonList.add((int)soLuong);
				}
			}
			int lenght = vtMaList.size();
			
			for (int i = 0; i< lenght; i++) {
				NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
				ChatLuongDAO clDAO = new ChatLuongDAO();
				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				
				String vtMa = vtMaList.get(i);
				String nsxTen = nsxList.get(i);
				String clTen = chatLuongList.get(i);
				int soLuongTon = (int)soLuongTonList.get(i);
				
				NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
				ChatLuong chatLuongTemp = clDAO.getByNameCl(clTen);
				
//				System.out.println("VTMa = "+ctvt.getCtvtId()+ "\n vtTen = "+ vtTen + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
				if (nsxTemp == null || chatLuongTemp == null) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTenList.get(i));
					dvtTenError.add(dvtTenList.get(i));
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add(soLuongTon);
					statusError.add("Vật tư không tồn tại");
					
				} else {
					CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMa , nsxTemp.getNsxMa(), chatLuongTemp.getClMa());
					if (ctvt == null) {
						vtMaError.add(vtMa);
						vtTenError.add(vtTenList.get(i));
						dvtTenError.add(dvtTenList.get(i));
						nsxTenError.add(nsxTen);
						clTenError.add(clTen);
						soLuongError.add(soLuongTon);
						statusError.add("Vật tư không tồn tại");
					} else {
						ctvt.setSoLuongTon(soLuongTon);
						ctvtDAO.updateCTVatTu(ctvt);
					}
				}
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static ArrayList<Object> readXls(File file) {
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<String> vtTenError = new ArrayList<String>();
		ArrayList<String> dvtTenError = new ArrayList<String>();
		ArrayList<String> nsxTenError = new ArrayList<String>();
		ArrayList<String> clTenError = new ArrayList<String>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		
		ArrayList<String> vtMaList = new ArrayList<String>();
		ArrayList<String> vtTenList = new ArrayList<String>();
		ArrayList<String> dvtTenList = new ArrayList<String>();
		ArrayList<String> nsxList = new ArrayList<String>();
		ArrayList<String> chatLuongList = new ArrayList<String>();
		ArrayList<CTVatTu> ctvtList = new ArrayList<CTVatTu>();
//		ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>();
		ArrayList<Integer> soLuongTonList = new ArrayList<Integer>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			while (j < 4 && rows.hasNext()) {
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
							break;	//		ArrayList<DonViTinh> dvtList = new ArrayList<DonViTinh>();

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
					System.out.println("nsx Ten = " + nsxTen + "\tcl Ten = " + clTen);
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxTen.length() == 0 && clTen.length() == 0 && soLuong == -1)
					break;
				if (vtMa.length() == 0 || soLuong == -1) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTen);
					dvtTenError.add(dvt);
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add((int)soLuong);
					statusError.add("Lỗi dữ liệu");
				} else {
//				DonViTinh donViTinh = new DonViTinh(dvt, 0);
//				VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
					vtMaList.add(vtMa);
					vtTenList.add(vtTen);
					dvtTenList.add(dvt);
					if (nsxTen.length() == 0)
						nsxList.add("Việt Nam");
					else	
						nsxList.add(nsxTen);
					if (clTen.length() == 0)
						chatLuongList.add("Không xác định");
					else	
						chatLuongList.add(nsxTen);
					soLuongTonList.add((int)soLuong);
				}
			}
			int lenght = vtMaList.size();
			
			for (int i = 0; i< lenght; i++) {
				NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
				ChatLuongDAO clDAO = new ChatLuongDAO();
				CTVatTuDAO ctvtDAO = new CTVatTuDAO();
				
				String vtMa = vtMaList.get(i);
				String nsxTen = nsxList.get(i);
				String clTen = chatLuongList.get(i);
				int soLuongTon = (int)soLuongTonList.get(i);
				
				NoiSanXuat nsxTemp = nsxDAO.getByNameNsx(nsxTen);
				ChatLuong chatLuongTemp = clDAO.getByNameCl(clTen);
				
//				System.out.println("VTMa = "+ctvt.getCtvtId()+ "\n vtTen = "+ vtTen + "\n nsxMa = "+ nsxMa + "\n clMa = " + clMa);
				if (nsxTemp == null || chatLuongTemp == null) {
					vtMaError.add(vtMa);
					vtTenError.add(vtTenList.get(i));
					dvtTenError.add(dvtTenList.get(i));
					nsxTenError.add(nsxTen);
					clTenError.add(clTen);
					soLuongError.add(soLuongTon);
					statusError.add("Vật tư không tồn tại");
					
				} else {
					CTVatTu ctvt = ctvtDAO.getCTVatTu(vtMa , nsxTemp.getNsxMa(), chatLuongTemp.getClMa());
					if (ctvt == null) {
						vtMaError.add(vtMa);
						vtTenError.add(vtTenList.get(i));
						dvtTenError.add(dvtTenList.get(i));
						nsxTenError.add(nsxTen);
						clTenError.add(clTen);
						soLuongError.add(soLuongTon);
						statusError.add("Vật tư không tồn tại");
					} else {
						ctvt.setSoLuongTon(soLuongTon);
						ctvtDAO.updateCTVatTu(ctvt);
					}
				}
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object> objectListError = new ArrayList<Object>();
		
		if (vtMaError.size() > 0) {
			System.out.println(clTenError);
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
