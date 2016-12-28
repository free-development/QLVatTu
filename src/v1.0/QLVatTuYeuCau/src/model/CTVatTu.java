package model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
public class CTVatTu implements Serializable {

	private int ctvtId;
	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	
	private int dinhMuc;
	private int daXoa;
	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int soLuongTon;

	private VatTu vatTu;

	private NoiSanXuat noiSanXuat;

	private ChatLuong chatLuong;
	
	public CTVatTu() {
		this.ctvtId = 0;
		this.dinhMuc = 0;
		this.soLuongTon = 0;
		this.vatTu = new VatTu();
		this.noiSanXuat = new NoiSanXuat();
		this.chatLuong = new ChatLuong();
		this.daXoa = 0;
	}
	

	/**
	 * @param dinhMuc
	 * @param soLuongTon
	 * @param vatTu
	 * @param noiSanXuat
	 * @param chaLluong
	 */
	public CTVatTu(VatTu vatTu,NoiSanXuat noiSanXuat, ChatLuong chatLuong, int dinhMuc, int soLuongTon,int daXoa) {
		this.dinhMuc = dinhMuc;
		this.soLuongTon = soLuongTon;
		this.vatTu = vatTu;
		this.noiSanXuat = noiSanXuat;
		this.chatLuong = chatLuong;
		this.daXoa = daXoa;
		
	}

	public CTVatTu(int ctvtId){
		this.ctvtId = ctvtId;
	}

	/**
	 * @param dinhMuc
	 * @param soLuongTon
	 * @param vatTu
	 * @param noiSanXuat
	 * @param chaLluong
	 */
	public CTVatTu(int ctvtId, VatTu vatTu,
			NoiSanXuat noiSanXuat, ChatLuong chatLuong, int dinhMuc, int soLuongTon,int daXoa) {
		this.ctvtId =  ctvtId;
		this.dinhMuc = dinhMuc;
		this.soLuongTon = soLuongTon;
		this.vatTu = vatTu;
		this.noiSanXuat = noiSanXuat;
		this.chatLuong = chatLuong;
		this.daXoa = daXoa;
	}

	
	
	public int getDaXoa() {
		return daXoa;
	}


	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}


	public int getCtvtId() {
		return ctvtId;
	}


	public void setCtvtId(int ctvtId) {
		this.ctvtId = ctvtId;
	}


	/**
	 * @return the dinhMuc
	 */
	public int getDinhMuc() {
		return dinhMuc;
	}

	/**
	 * @param dinhMuc the dinhMuc to set
	 */
	public void setDinhMuc(int dinhMuc) {
		this.dinhMuc = dinhMuc;
	}

	/**
	 * @return the soLuongTon
	 */
	public int getSoLuongTon() {
		return soLuongTon;
	}

	/**
	 * @param soLuongTon the soLuongTon to set
	 */
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}

	/**
	 * @return the vatTu
	 */
	public VatTu getVatTu() {
		return vatTu;
	}

	/**
	 * @param vatTu the vatTu to set
	 */
	public void setVatTu(VatTu vatTu) {
		this.vatTu = vatTu;
	}

	/**
	 * @return the noiSanXuat
	 */
	public NoiSanXuat getNoiSanXuat() {
		return noiSanXuat;
	}

	/**
	 * @param noiSanXuat the noiSanXuat to set
	 */
	public void setNoiSanXuat(NoiSanXuat noiSanXuat) {
		this.noiSanXuat = noiSanXuat;
	}

	/**
	 * @return the chatLuong
	 */
	public ChatLuong getChatLuong() {
		return chatLuong;
	}

	/**
	 * @param chatLuong the chatLuong to set
	 */
	public void setChatLuong(ChatLuong chatLuong) {
		this.chatLuong = chatLuong;
	}
	
}
