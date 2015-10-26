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

/**
 * @author quoioln
 *
 */
public class ReadExcelCT {
	public static ArrayList<Object>  readXlsx(File file) {
		ArrayList<CTVatTu> ctvtListError = new ArrayList<CTVatTu>();
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
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxMa = "";
				String clMa = "";
				double soLuong = 0;
				double dinhMuc = 0;
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
							nsxMa = cell.getStringCellValue();
							break;
						case 5:
							clMa = cell.getStringCellValue();
							break;
						}
					} 
					count++;
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 )
					break;
				if (vtMa.length() == 0 || vtTen.length() == 0 || dvt.length() == 0 )
				{
					VatTu vatTuError = new VatTu(vtMa, vtTen, new DonViTinh(dvt, 0), 0);
					CTVatTu ctvtError = new CTVatTu(vatTuError, new NoiSanXuat(nsxMa), new ChatLuong(clMa),(int) dinhMuc, (int) soLuong, 0);
					ctvtListError.add(ctvtError);
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
					DonViTinh donViTinh = new DonViTinh(dvt, 0);
					VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
					CTVatTu ctvt = new CTVatTu(vatTu, new NoiSanXuat(nsxMa), new ChatLuong(clMa),
							(int) dinhMuc, (int) soLuong, 0);
					VatTuDAO vtDAO = new VatTuDAO();
					CTVatTuDAO ctvtDAO = new CTVatTuDAO();
					DonViTinhDAO dvtDAO = new DonViTinhDAO();
					NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
					ChatLuongDAO clDAO = new ChatLuongDAO();
					
					NoiSanXuat noisx = nsxDAO.getNoiSanXuat(nsxMa);
					ChatLuong chatluong = clDAO.getChatLuong(clMa);
					if ((noisx != null) && (chatluong != null))
					{
						
						VatTu vt = vtDAO.getVatTu(vatTu.getVtMa());
						
						if (vt == null) {
							DonViTinh temp = dvtDAO.getDonViTinhByTen(dvt);
							if (temp == null) {
								dvtDAO.addDonViTinh(donViTinh);
								DonViTinhDAO donViTinhDAO2 = new DonViTinhDAO();
								donViTinh.setDvtId(donViTinhDAO2.lastInsertId());
								donViTinhDAO2.disconnect();
							} else if (temp.getDaXoa() == 1){
								dvtDAO.updateDonViTinh(donViTinh);
							}
							else {
								donViTinh.setDvtId(temp.getDvtId());
							}
							vtDAO.addVatTu(vatTu);
						}
						vatTu.setDvt(donViTinh);
						CTVatTu ctvtTemp = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxMa, clMa);
						if (ctvtTemp == null) {
							ctvtDAO.addCTVatTu(ctvt);
						}
						else if (ctvtTemp.getDaXoa() == 1) {
							CTVatTuDAO ctVatTuDAO2 = new CTVatTuDAO();
							ctVatTuDAO2.updateCTVatTu(ctvt);
							ctVatTuDAO2.disconnect();
						}
						else {
							ctvtListError.add(ctvtTemp);
							statusError.add("Đã tồn tại");
						}
					}
					else 
					{
						ctvtListError.add(ctvt);
						if (noisx == null)
							statusError.add("Nơi sản xuất không tồn tại");
						if (chatluong == null)
							statusError.add("Chất lượng không tồn tại");
					}
					
				vtDAO.close();
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
				dvtDAO.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object> objectList = new ArrayList<Object>();
		if (ctvtListError.size() > 0) {
			objectList.add(ctvtListError);
			objectList.add(statusError);
		}
		return objectList;
	}
	
	// read xls
	public static ArrayList<Object> readXls(File file) {
		ArrayList<CTVatTu> ctvtListError = new ArrayList<CTVatTu>();
		ArrayList<String> statusError = new ArrayList<String>();
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			Row row;
			Cell cell;
			Iterator rows = sheet.rowIterator();
			int j = 0;
			while (rows.hasNext()) {
				row = (HSSFRow) rows.next();
				j++;
				if (j == 1)
					continue;
				Iterator cells = row.cellIterator();
				int count = 0;
				String vtMa = "";
				String vtTen = "";
				String dvt = "";
				String nsxMa = "";
				String clMa = "";
				double soLuong = 0;
				double dinhMuc = 0;
				while (cells.hasNext()) {
					cell = (HSSFCell) cells.next();
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
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
							nsxMa = cell.getStringCellValue();
							break;
						case 5:
							clMa = cell.getStringCellValue();
							break;
						}
					} 
					count++;
				}
				if (vtMa.length() == 0 && vtTen.length() == 0 && dvt.length() == 0 && nsxMa.length() == 0 && clMa.length() == 0 )
					break;
				if (vtMa.length() == 0 || vtTen.length() == 0 || dvt.length() == 0 )
				{
					VatTu vatTuError = new VatTu(vtMa, vtTen, new DonViTinh(dvt, 0), 0);
					CTVatTu ctvtError = new CTVatTu(vatTuError, new NoiSanXuat(nsxMa), new ChatLuong(clMa),(int) dinhMuc, (int) soLuong, 0);
					ctvtListError.add(ctvtError);
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
					DonViTinh donViTinh = new DonViTinh(dvt, 0);
					VatTu vatTu = new VatTu(vtMa, vtTen, donViTinh, 0);
					CTVatTu ctvt = new CTVatTu(vatTu, new NoiSanXuat(nsxMa), new ChatLuong(clMa),
							(int) dinhMuc, (int) soLuong, 0);
					VatTuDAO vtDAO = new VatTuDAO();
					CTVatTuDAO ctvtDAO = new CTVatTuDAO();
					DonViTinhDAO dvtDAO = new DonViTinhDAO();
					NoiSanXuatDAO nsxDAO = new NoiSanXuatDAO();
					ChatLuongDAO clDAO = new ChatLuongDAO();
					
					NoiSanXuat noisx = nsxDAO.getNoiSanXuat(nsxMa);
					ChatLuong chatluong = clDAO.getChatLuong(clMa);
					if ((noisx != null) && (chatluong != null))
					{
						
						VatTu vt = vtDAO.getVatTu(vatTu.getVtMa());
						
						if (vt == null) {
							DonViTinh temp = dvtDAO.getDonViTinhByTen(dvt);
							if (temp ==  null) {
								dvtDAO.addDonViTinh(donViTinh);
								DonViTinhDAO donViTinhDAO2 = new DonViTinhDAO();
								donViTinh.setDvtId(donViTinhDAO2.lastInsertId());
								donViTinhDAO2.disconnect();
							}
							else {
								donViTinh.setDvtId(temp.getDvtId());
							}
							vtDAO.addVatTu(vatTu);
						}
						vatTu.setDvt(donViTinh);
						CTVatTu ctvtTemp = ctvtDAO.getCTVatTu(vatTu.getVtMa(), nsxMa, clMa);
						if (ctvtTemp == null) {
							ctvtDAO.addCTVatTu(ctvt);
						}
						else if (ctvtTemp.getDaXoa() == 1) {
							CTVatTuDAO ctVatTuDAO2 = new CTVatTuDAO();
							ctVatTuDAO2.updateCTVatTu(ctvt);
							ctVatTuDAO2.disconnect();
						}
						else {
							ctvtListError.add(ctvtTemp);
							statusError.add("Đã tồn tại");
						}
					}
					else 
					{
						ctvtListError.add(ctvt);
						if (noisx == null)
							statusError.add("Nơi sản xuất không tồn tại");
						if (chatluong == null)
							statusError.add("Chất lượng không tồn tại");
					}
					
				vtDAO.close();
				nsxDAO.close();
				clDAO.close();
				ctvtDAO.close();
				dvtDAO.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Object> objectList = new ArrayList<Object>();
		if (ctvtListError.size() > 0) {
			objectList.add(ctvtListError);
			objectList.add(statusError);
		}
		return objectList;
	}
}
