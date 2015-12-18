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
import model.DonViTinh;
import model.NoiSanXuat;
import model.VatTu;
import model.YeuCau;

/**
 * @author quoioln
 *
 */
public class ReadExcelCongVan {
	public static ArrayList<Object> readXlsx(int cvId, File file) {
		ArrayList<String> nsxMaError = new ArrayList<String>();
		ArrayList<String> clMaError = new ArrayList<String>();
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		ArrayList<Object> objectList = new ArrayList<Object>();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			ArrayList<String> vtMaList = new ArrayList<String>();
			ArrayList<String> nsxMaList = new ArrayList<String>();
			ArrayList<String> clMaList = new ArrayList<String>();
			ArrayList<Integer> soLuongList = new ArrayList<Integer>();
			if (rows.hasNext())
				rows.next();
			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String nsxMa = "";
				String clMa = "";
				double soLuong = -1;
				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							vtMa = cell.getStringCellValue();
							break;
						case 1:
							nsxMa = cell.getStringCellValue();
							break;
						case 2:
							clMa = cell.getStringCellValue();
							break;	
						}
					} else {
						switch (count) {
							case 3:
								soLuong = cell.getNumericCellValue();
								break;
						}
					}
					count++;
				}
				if (vtMa.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 && soLuong <= 0)
					break;
				if (vtMa.length() == 0 || soLuong <= 0 )
				{
					vtMaError.add(vtMa);
					nsxMaError.add(nsxMa);
					clMaError.add(clMa);
					soLuongError.add((int)soLuong);
					statusError.add("Lỗi dữ liệu");
				}
				else
				{
					if(nsxMa.length() == 0)
					{
						nsxMa = "VIE";
					}
					if(clMa.length() == 0)
					{
						clMa = "000";
					}
					CTVatTuDAO ctvtDAO = new CTVatTuDAO();
					CTVatTu ctVatTu = ctvtDAO.getCTVatTu(vtMa, nsxMa, clMa);
					if (ctVatTu != null || ctVatTu.getDaXoa() == 1) {
						vtMaList.add(vtMa);
						nsxMaList.add(nsxMa);
						clMaList.add(clMa);
						soLuongList.add((int)soLuong);
					} else {
						vtMaError.add(vtMa);
						nsxMaError.add(nsxMa);
						clMaError.add(clMa);
						soLuongError.add((int)soLuong);
						statusError.add("Vật tư không tồn tại");
					}
					ctvtDAO.disconnect();
				}
			}
//			if (vtMaError.size() == 0 && nsxMaError.size() == 0 
//					&& clMaError.size() == 0 && soLuongError.size() == 0) {
				ArrayList<Object> importList = new ArrayList<Object>();
				importList.add(vtMaList);
				importList.add(nsxMaList);
				importList.add(clMaList);
				importList.add(soLuongList);
				importList.add(cvId);
				write(importList);
//				return objectList;
				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vtMaError.size() > 0 || nsxMaError.size() > 0 
				|| clMaError.size() > 0 || soLuongError.size() > 0) {
			objectList.add(vtMaError);
			objectList.add(nsxMaError);
			objectList.add(clMaError);
			objectList.add(soLuongError);
			objectList.add(statusError);
		}
		return objectList;
	}
	
	

	// read xls
	public static ArrayList<Object> readXls(int cvId, File file) {
		ArrayList<String> clMaError = new ArrayList<String>();
		ArrayList<String> vtMaError = new ArrayList<String>();
		ArrayList<Integer> soLuongError = new ArrayList<Integer>();
		ArrayList<String> statusError = new ArrayList<String>();
		ArrayList<String> nsxMaError = new ArrayList<String>();
		ArrayList<Object> objectList = new ArrayList<Object>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			ArrayList<String> vtMaList = new ArrayList<String>();
			ArrayList<String> nsxMaList = new ArrayList<String>();
			ArrayList<String> clMaList = new ArrayList<String>();
			ArrayList<Integer> soLuongList = new ArrayList<Integer>();
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String nsxMa = "";
				String clMa = "";
				double soLuong = -1;
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						switch (count) {
						case 0:
							vtMa = cell.getStringCellValue();
							break;
						case 1:
							nsxMa = cell.getStringCellValue();
							break;
						case 2:
							clMa = cell.getStringCellValue();
							break;	
						}
					} else {
						switch (count) {
							case 3:
								soLuong = (int)cell.getNumericCellValue();
								break;
						}
					}
					count++;
				}
				if (vtMa.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 && soLuong <= 0)
					break;
				if (vtMa.length() == 0 || nsxMa.length() == 0 || clMa.length() == 0 || soLuong <= 0 )
				{
					vtMaError.add(vtMa);
					nsxMaError.add(nsxMa);
					clMaError.add(clMa);
					soLuongError.add((int)soLuong);
					statusError.add("Lỗi dữ liệu");
				}
				else
				{
					if(nsxMa.length() == 0)
					{
						nsxMa = "VIE";
					}
					if(clMa.length() == 0)
					{
						clMa = "000";
					}
					CTVatTuDAO ctvtDAO = new CTVatTuDAO();
					CTVatTu ctVatTu = ctvtDAO.getCTVatTu(vtMa, nsxMa, clMa);
					if (ctVatTu != null || ctVatTu.getDaXoa() == 0) {
						vtMaList.add(vtMa);
						nsxMaList.add(nsxMa);
						clMaList.add(clMa);
						soLuongList.add((int)soLuong);
					} else {
						vtMaError.add(vtMa);
						nsxMaError.add(nsxMa);
						clMaError.add(clMa);
						soLuongError.add((int)soLuong);
						statusError.add("Vật tư không tồn tại");
					}
					ctvtDAO.disconnect();
				}
			}
//			if (vtMaError.size() == 0 && nsxMaError.size() == 0 
//					&& clMaError.size() != 0 && soLuongError.size() == 0) {
				ArrayList<Object> importList = new ArrayList<Object>();
				importList.add(vtMaList);
				importList.add(nsxMaList);
				importList.add(clMaList);
				importList.add(soLuongList);
				importList.add(cvId);
				write(importList);
//				return objectList;
				
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vtMaError.size() > 0 || nsxMaError.size() > 0 
				|| clMaError.size() > 0 || soLuongError.size() > 0) {
			objectList.add(vtMaError);
			objectList.add(nsxMaError);
			objectList.add(clMaError);
			objectList.add(soLuongError);
			objectList.add(statusError);
		}
		return objectList;
	}
	// method write yeuCau to database
	private static void write(ArrayList<Object> importList) {
		ArrayList<String> vtMaList = (ArrayList<String>) importList.get(0);
		ArrayList<String> nsxMaList = (ArrayList<String>) importList.get(1);
		ArrayList<String> clMaList = (ArrayList<String>) importList.get(2);
		ArrayList<Integer> soLuongList = (ArrayList<Integer>) importList.get(3);
		int cvId = (Integer) importList.get(4);
		
		int lenght = vtMaList.size();
		for (int i = 0; i < lenght; i++) {
			CTVatTuDAO ctvtDAO = new CTVatTuDAO();
			YeuCauDAO yeuCauDAO = new YeuCauDAO();
			CTVatTu ctVatTu = ctvtDAO.getCTVatTu(vtMaList.get(i), nsxMaList.get(i), clMaList.get(i));
			int ctvtId = ctVatTu.getCtvtId();
			YeuCau yeuCauCheck = yeuCauDAO.getYeuCau(cvId, ctvtId);
			if (yeuCauCheck == null) {
				YeuCau yeuCau = new YeuCau(cvId, ctvtId, soLuongList.get(i), 0, 0);
				yeuCauDAO.addYeuCau(yeuCau);
			} else {
				YeuCauDAO yeuCauDAO2 = new YeuCauDAO();
				yeuCauCheck.setYcSoLuong(soLuongList.get(i));
				yeuCauDAO2.updateYeuCau(yeuCauCheck);
				yeuCauDAO2.disconnect();
				
			}
			ctvtDAO.disconnect();
			yeuCauDAO.disconnect();
		}
	}
}
