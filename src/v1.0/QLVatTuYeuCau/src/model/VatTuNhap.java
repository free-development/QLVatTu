package model;

import java.io.Serializable;


public class VatTuNhap implements Serializable{

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int soLuong;

	private CTVatTu ctVatTu;

	private int pnId;
	
	public VatTuNhap() {
		this.soLuong = 0;
		this.ctVatTu = new CTVatTu();
		this.pnId = 0;
	}

	/**
	 * @param soLuong
	 * @param ctVatTu
	 * @param pnId
	 */
	public VatTuNhap(int pnId,  CTVatTu ctVatTu, int soLuong) {
		this.soLuong = soLuong;
		this.ctVatTu = ctVatTu;
		this.pnId = pnId;
	}

	/**
	 * @return the soLuong
	 */
	public final int getSoLuong() {
		return soLuong;
	}

	/**
	 * @param soLuong the soLuong to set
	 */
	public final void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	/**
	 * @return the ctVatTu
	 */
	public final CTVatTu getCtVatTu() {
		return ctVatTu;
	}

	/**
	 * @param ctVatTu the ctVatTu to set
	 */
	public final void setCtVatTu(CTVatTu ctVatTu) {
		this.ctVatTu = ctVatTu;
	}

	/**
	 * @return the pnId
	 */
	public final int getPnId() {
		return pnId;
	}

	/**
	 * @param pnId the pnId to set
	 */
	public final void setPnId(int pnId) {
		this.pnId = pnId;
	}

}
