package model;

import java.io.Serializable;

public class VaiTro implements Serializable{
	private int vtId;
	private String vtTen;
	private int daXoa;
	public VaiTro() {
		this.vtId = 0;
		this.vtTen = "";
		this.daXoa = 0;
	}
	
	/**
	 * @param vtTen
	 */
	public VaiTro(String vtTen) {
		this.vtTen = vtTen;
	}
	
	/**
	 * @param vtId
	 * @param vtTen
	 */
	public VaiTro(int vtId) {
		this.vtId = vtId;
	}
	public VaiTro(int vtId, String vtTen,int daXoa) {
		this.vtId = vtId;
		this.vtTen = vtTen;
		this.daXoa = daXoa;
	}

	public VaiTro(String vtTen,int daXoa) {
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
	 * @return the vtId
	 */
	public final int getVtId() {
		return vtId;
	}

	/**
	 * @param vtId the vtId to set
	 */
	public final void setVtId(int vtId) {
		this.vtId = vtId;
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