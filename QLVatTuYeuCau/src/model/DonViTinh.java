package model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
	public class DonViTinh implements Serializable{
	private int dvtId;	
	private String dvtTen;
	private int daXoa;
	public DonViTinh() {
		this.dvtId = 0;
		this.dvtTen = "";
		this.daXoa = 0;
	}
	
	/**
	 * @param dvtId
	 * @param dvtTen
	 * @param daXoa
	 */

	public DonViTinh(int dvtId) {
		this.dvtId = dvtId;
	}
	public DonViTinh(String dvtTen,int daXoa) {
		this.dvtTen = dvtTen;
		this.daXoa = daXoa;
	}
	public DonViTinh(int dvtId, String dvtTen, int daXoa) {
		this.dvtId = dvtId;
		this.dvtTen = dvtTen;
		this.daXoa = daXoa;
	}
	public int getDvtId() {
		return dvtId;
	}

	public void setDvtId(int dvtId) {
		this.dvtId = dvtId;
	}

	public String getDvtTen() {
		return dvtTen;
	}

	public void setDvtTen(String dvtTen) {
		this.dvtTen = dvtTen;
	}


	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}

	
	
}