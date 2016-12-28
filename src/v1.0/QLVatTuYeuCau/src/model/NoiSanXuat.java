package model;

import java.io.Serializable;

public class NoiSanXuat implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName CHAR
	 * DataTypeLength/Precision 3
	 */
	private String nsxMa;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 20
	 */
	private String nsxTen;
	private int daXoa;
	public NoiSanXuat() {
		this.nsxMa = "";
		this.nsxTen = "";
		this.daXoa = 0;
	}
	/**
	 * @param nsxMa
	 * @param nsxTen
	 */
	public NoiSanXuat(String nsxMa, String nsxTen,int daXoa) {
		this.nsxMa = nsxMa;
		this.nsxTen = nsxTen;
		this.daXoa = daXoa;
	}
	public NoiSanXuat(String nsxMa, String nsxTen) {
		this.nsxMa = nsxMa;
		this.nsxTen = nsxTen;
	}
	public NoiSanXuat(String nsxMa) {
		this.nsxMa = nsxMa;
	}
	public int getDaXoa() {
		return daXoa;
	}
	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}
	/**
	 * @return the nsxMa
	 */
	public String getNsxMa() {
		return nsxMa;
	}
	/**
	 * @param nsxMa the nsxMa to set
	 */
	public void setNsxMa(String nxsMa) {
		this.nsxMa = nxsMa;
	}
	/**
	 * @return the nsxTen
	 */
	public String getNsxTen() {
		return nsxTen;
	}
	/**
	 * @param nsxTen the nsxTen to set
	 */
	public void setNsxTen(String nsxTen) {
		this.nsxTen = nsxTen;
	}

}
