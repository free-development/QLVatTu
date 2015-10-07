/**
 * 
 */
package test;

import java.sql.Date;

import controller.CvController;
import model.CTVatTu;
import model.ChatLuong;
import model.CongVan;
import model.DonVi;
import model.File;
import model.MucDich;
import model.NoiSanXuat;
import model.TrangThai;
import model.VatTu;
import model.YeuCau;
import util.DateUtil;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.CongVanDAO;
import dao.DonViDAO;
import dao.FileDAO;
import dao.MucDichDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;
import dao.YeuCauDAO;

/**
 * @author camnh_000
 *
 */
public class TestYeuCAU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		VatTuDAO vatTuDAO = new VatTuDAO();
		MucDichDAO mucDichDAO = new MucDichDAO();
		DonViDAO donViDAO = new DonViDAO();
		FileDAO fileDAO = new FileDAO();
		
		
		int id = ctVatTuDAO.getLastInsert(); 	
//		int idyc = ctVatTuDAO.getLastInsert(); 
		int idCv = new CongVanDAO().getLastInsert(); 


		ChatLuong chatLuong = new ChatLuong("cl2", "Tốt",0);
//		ChatLuong chatLuong5 = new ChatLuong("CL5", "Tốt",0);
//		ChatLuong chatLuong8 = new ChatLuong("CL8", "Trung bình",0);
//		ChatLuong chatLuong0 = new ChatLuong("CL0", "Xấu",0);
		

		NoiSanXuat nsx = new NoiSanXuat("Vn2", "Việt Nam",0);
//		NoiSanXuat nsx5 = new NoiSanXuat("Tq1", "Trung Quốc",0);
//		NoiSanXuat nsx9 = new NoiSanXuat("Lao", "Lào",0);
//		NoiSanXuat nsx0 = new NoiSanXuat("Tl1", "Thái Lan",0);
		
		
		//VatTu vatTu = new VatTu("VT2", "Thép", "cuộn",0);
//		VatTu vatTu5 = new VatTu("VT5", "Trụ điện", "cái",0);
//		VatTu vatTu9 = new VatTu("VT9", "Sắc", "cuộn",0);
//		VatTu vatTu0 = new VatTu("VT0", "Xăng", "lít",0);

		
		//CTVatTu ctVatTu = new CTVatTu(vatTu, nsx, chatLuong,9, 8,0);
//		id = ctVatTuDAO.getLastInsert(); 
//		CTVatTu ctVatTu5 = new CTVatTu(vatTu9, nsx5, chatLuong5,6, 2,0);
//		id = ctVatTuDAO.getLastInsert(); 
//		CTVatTu ctVatTu9 = new CTVatTu(vatTu0, nsx9, chatLuong8,9, 8,0);
//		id = ctVatTuDAO.getLastInsert(); 
//		CTVatTu ctVatTu3 = new CTVatTu(vatTu, nsx0, chatLuong0,3, 4, 0);

		chatLuongDAO.addChatLuong(chatLuong);
//		chatLuongDAO.addChatLuong(chatLuong5);
//		chatLuongDAO.addChatLuong(chatLuong8);
//		chatLuongDAO.addChatLuong(chatLuong0);



		

		noiSanXuatDAO.addNoiSanXuat(nsx);
//		noiSanXuatDAO.addNoiSanXuat(nsx5);
//		noiSanXuatDAO.addNoiSanXuat(nsx9);
//		noiSanXuatDAO.addNoiSanXuat(nsx0);
		
		//vatTuDAO.addVatTu(vatTu);
//		vatTuDAO.addVatTu(vatTu5);
//		vatTuDAO.addVatTu(vatTu9);
//		vatTuDAO.addVatTu(vatTu0);
		
		//ctVatTuDAO.addCTVatTu(ctVatTu);
//		ctVatTuDAO.addCTVatTu(ctVatTu5);
//		ctVatTuDAO.addCTVatTu(ctVatTu9);
//		ctVatTuDAO.addCTVatTu(ctVatTu3);
		

		

//		MucDich mucDich = new MucDich("SC5", "Sửa chữa lớn",0);
//
//		
//
//
//		
//		
//
		MucDich mucDich = new MucDich("SC2", "Sửa chữa lớn",0);
//		MucDich mucDich4 = new MucDich("SC4", "Sua chua lon",0);
//		MucDich mucDich2 = new MucDich("SC7", "Sua chua lon",0);
		mucDichDAO.addMucDich(mucDich);
//		mucDichDAO.addMucDich(mucDich4);
//		mucDichDAO.addMucDich(mucDich5);

		
		

//		File file = new File("~/study/linux command", "File học linux command căn bản", idCv);
//		new FileDAO().addFile(file);
		
//		File file1 = new File("~/study/linux command", "File hoc linux command can ban", idCv);
//		new FileDAO().addFile(file1);
//		
//		File file2 = new File("~/study/linux command", "File hoc linux command can ban", idCv);
//		new FileDAO().addFile(file2);
//		
//	
//		File file3 = new File("~/study/linux command", "File hoc linux command can ban", idCv);
//		new FileDAO().addFile(file2);
//		
//		
//		
//		File file4 = new File("~/study/linux command", "File học linux command căn bản", idCv);
//		new FileDAO().addFile(file4);
		
		
		
		

		//YeuCau yeuCau = new YeuCau(id, ctVatTu, 50, 50, 0);
//		YeuCau yeuCau2 = new YeuCau(id, ctVatTu,80, 40, 0);
//		YeuCau yeuCau3 = new YeuCau(id, ctVatTu9, 70, 40, 0);
//		YeuCau yeuCau4 = new YeuCau(id, ctVatTu3, 30, 20, 0);
		


		//new YeuCauDAO().addYeuCau(yeuCau);
//		new YeuCauDAO().addYeuCau(yeuCau2);
//		new YeuCauDAO().addYeuCau(yeuCau3);
//		new YeuCauDAO().addYeuCau(yeuCau4);
		
		DonVi donVi = new DonVi("SX2", "Đơn vị 3", "0977874271", "tien@gmail.com", "Can Tho",0);
		new DonViDAO().addDonVi(donVi);
		
		
		

	//	CongVan congVan = new CongVan(1, DateUtil.convertToSqlDate(new java.util.Date()), "123", DateUtil.convertToSqlDate(new java.util.Date()), "Khong co trich yeu", "Khong co bu phe", mucDich, new TrangThai("CGQ", "Chua giai quyet"), donVi,0);
		//CongVan congVan5 = new CongVan(5, DateUtil.convertToSqlDate(new java.util.Date()), "123", DateUtil.convertToSqlDate(new java.util.Date()), "Khong co trich yeu", "Khong co bu phe", mucDich2, new TrangThai("DGQ", "Dang giai quyet"), donVi,0);
		
		
		
		//new CongVanDAO().addCongVan(congVan);
		//new CongVanDAO().addCongVan(congVan5);
	
		
		
		
		

	

		
	}

}
