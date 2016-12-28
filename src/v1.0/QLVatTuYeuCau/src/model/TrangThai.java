package model;

import java.io.Serializable;

public class TrangThai implements Serializable{
	private String ttMa;
	private String ttTen;
	
	public TrangThai() {
		this.ttMa = "";
		this.ttTen = "";
	}
	
	/**
	 * @param ttMa
	 * @param ttTen
	 */
	public TrangThai(String ttMa) {
		this.ttMa = ttMa;
	}
	public TrangThai(String ttMa, String ttTen) {
		this.ttMa = ttMa;
		this.ttTen = ttTen;
	}

	/**
	 * @return the ttMa
	 */
	public String getTtMa() {
		return ttMa;
	}

	/**
	 * @param ttMa the ttMa to set
	 */
	public void setTtMa(String ttMa) {
		this.ttMa = ttMa;
	}

	/**
	 * @return the ttTen
	 */
	public String getTtTen() {
		return ttTen;
	}

	/**
	 * @param ttTen the ttTen to set
	 */
	public void setTtTen(String ttTen) {
		this.ttTen = ttTen;
	}
	
	
}
