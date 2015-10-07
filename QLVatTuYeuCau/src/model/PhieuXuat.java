package model;

import java.io.Serializable;
import java.sql.Date;

public class PhieuXuat implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int pxId;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName DATE
	 * DataTypeLength/Precision 
	 */
	private Date pxNgay;

	private VTCongVan vtCongVan;

	public PhieuXuat() {
		this.pxId = 0;
		this.pxNgay = new Date(1, 1, 1);
		this.vtCongVan = vtCongVan;
	}
	
	/**
	 * @param pxNgay
	 * @param vtCongVan
	 */
	public PhieuXuat(Date pxNgay, VTCongVan vtCongVan) {
		this.pxNgay = pxNgay;
		this.vtCongVan = vtCongVan;
	}
	
	/**
	 * @param pxId
	 * @param pxNgay
	 * @param vtCongVan
	 */
	public PhieuXuat(int pxId, Date pxNgay, VTCongVan vtCongVan) {
		this.pxId = pxId;
		this.pxNgay = pxNgay;
		this.vtCongVan = vtCongVan;
	}

	/**
	 * @return the pxId
	 */
	public int getPxId() {
		return pxId;
	}

	/**
	 * @param pxId the pxId to set
	 */
	public void setPxId(int pxId) {
		this.pxId = pxId;
	}

	/**
	 * @return the pxNgay
	 */
	public Date getPxNgay() {
		return pxNgay;
	}

	/**
	 * @param pxNgay the pxNgay to set
	 */
	public void setPxNgay(Date pxNgay) {
		this.pxNgay = pxNgay;
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
