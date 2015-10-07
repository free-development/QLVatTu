package model;

import java.io.Serializable;
import java.sql.Date;

public class PhieuNhap implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int pnId;

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName DATE
	 * DataTypeLength/Precision 
	 */
	private Date pnNgay;

	private VTCongVan vtCongVan;
	
	public PhieuNhap() {
		this.pnId = 0;
		this.pnNgay = new Date(1, 1, 1);
		this.vtCongVan = new VTCongVan();
	}

	/**
	 * @param pnNgay
	 * @param vtCongVan
	 */
	public PhieuNhap(Date pnNgay, VTCongVan vtCongVan) {
		this.pnNgay = pnNgay;
		this.vtCongVan = vtCongVan;
	}
	
	/**
	 * @param pnId
	 * @param pnNgay
	 * @param vtCongVan
	 */
	public PhieuNhap(int pnId, Date pnNgay, VTCongVan vtCongVan) {
		this.pnId = pnId;
		this.pnNgay = pnNgay;
		this.vtCongVan = vtCongVan;
	}

	/**
	 * @return the pnId
	 */
	public int getPnId() {
		return pnId;
	}

	/**
	 * @param pnId the pnId to set
	 */
	public void setPnId(int pnId) {
		this.pnId = pnId;
	}

	/**
	 * @return the pnNgay
	 */
	public Date getPnNgay() {
		return pnNgay;
	}

	/**
	 * @param pnNgay the pnNgay to set
	 */
	public void setPnNgay(Date pnNgay) {
		this.pnNgay = pnNgay;
	}

	/**
	 * @return the vtCongVan
	 */
	public VTCongVan getVtCongVan() {
		return vtCongVan;
	}

	/**
	 * @param vtCongVan the vtCongVan to set
	 */
	public void setVtCongVan(VTCongVan vtCongVan) {
		this.vtCongVan = vtCongVan;
	}
}
