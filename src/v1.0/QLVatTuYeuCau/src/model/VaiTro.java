package model;

import java.io.Serializable;

public class VaiTro implements Serializable{
	private String vtMa;
	private String vtTen;
	private int daXoa;
	public VaiTro() {
		this.vtMa = "";
		this.vtTen = "";
		this.daXoa = 0;
	}
	
	public VaiTro(String vtMa, String vtTen, int daXoa) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.daXoa = daXoa;
	}

	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}

	/**
	 * @return the vtMa
	 */
	public final String getVtMa() {
		return vtMa;
	}

	/**
	 * @param vtMa the vtMa to set
	 */
	public final void setVtMa(String vtMa) {
		this.vtMa = vtMa;
	}

	/**
	 * @return the vtTen
	 */
	public final String getVtTen() {
		return vtTen;
	}

	/**
	 * @param vtTen the vtTen to set
	 */
	public final void setVtTen(String vtTen) {
		this.vtTen = vtTen;
	}
	
	
}