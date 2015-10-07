package model;

import java.io.Serializable;

public class VatTuXuat implements Serializable{

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int soLuong;

	private int pxId;

	private YeuCau yeuCau;
	
	public VatTuXuat() {
		this.soLuong = 0;
		this.pxId = 0;
		this.yeuCau = new YeuCau();
	}

	/**
	 * @param soLuong
	 * @param pxId
	 * @param yeuCau
	 */
	public VatTuXuat(int soLuong, int pxId, YeuCau yeuCau) {
		this.soLuong = soLuong;
		this.pxId = pxId;
		this.yeuCau = yeuCau;
	}

}
