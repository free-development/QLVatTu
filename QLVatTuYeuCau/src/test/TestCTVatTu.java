package test;

import model.CTVatTu;
import model.ChatLuong;
import model.NoiSanXuat;
import model.VatTu;
import dao.CTVatTuDAO;
import dao.ChatLuongDAO;
import dao.NoiSanXuatDAO;
import dao.VatTuDAO;

public class TestCTVatTu {
	
	public TestCTVatTu() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CTVatTuDAO ctVatTuDAO = new CTVatTuDAO();
		ChatLuongDAO chatLuongDAO = new ChatLuongDAO();
		NoiSanXuatDAO noiSanXuatDAO = new NoiSanXuatDAO();
		VatTuDAO vatTuDAO = new VatTuDAO();
//
		ChatLuong chatLuong = new ChatLuong("G1", "Tot",0);
		NoiSanXuat nsx = new NoiSanXuat("TL", "Thai Lan",0);

		//VatTu vatTu = new VatTu("VT1", "Tru dien", "cai",0);


		
		chatLuongDAO.addChatLuong(chatLuong);
		noiSanXuatDAO.addNoiSanXuat(nsx);
		//vatTuDAO.addVatTu(vatTu);
		
		//ctVatTuDAO.addCTVatTu(new CTVatTu(vatTu, nsx, chatLuong, 0, 0,0));
		
	}
	
}
