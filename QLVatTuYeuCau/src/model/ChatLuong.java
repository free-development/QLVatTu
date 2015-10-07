package model;

import java.io.Serializable;



public class ChatLuong implements Serializable {

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName CHAR
	 * DataTypeLength/Precision 3
	 */
	private String clMa;
	private int daXoa;
	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 20
	 */
	private String clTen;
	
	public ChatLuong() {
		this.clMa = "";
		this.clTen = "";
		this.daXoa = 0;
	}
	/**
	 * @param clMa
	 * @param clTen
	 * @param daXoa
	 */
	public ChatLuong(String clMa, String clTen) {
		this.clMa = clMa;
		this.clTen = clTen;
	}
	public ChatLuong(String clMa, String clTen,int daXoa) {
		this.clMa = clMa;
		this.clTen = clTen;
		this.daXoa = daXoa;
	}
	public int getDaXoa() {
		return daXoa;
	}
	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}
	public ChatLuong(String clMa) {
		this.clMa = clMa;
	}
	/**
	 * @return the clMa
	 */
	public String getClMa() {
		return clMa;
	}
	/**
	 * @param clMa the clMa to set
	 */
	public void setClMa(String clMa) {
		this.clMa = clMa;
	}
	/**
	 * @return the clTen
	 */
	public String getClTen() {
		return clTen;
	}
	/**
	 * @param clTen the clTen to set
	 */
	public void setClTen(String clTen) {
		this.clTen = clTen;
	}
}
