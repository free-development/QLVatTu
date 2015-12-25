package model;

import java.io.Serializable;


public class VTCongVan implements Serializable{

	private int cvId;

	private String vtMa;

	private String msnv;
	private TrangThai trangThai;
	private int daXoa;
	public VTCongVan() {
		this.cvId = 0;
		this.vtMa = "";
		this.msnv = "";
		this.trangThai = new TrangThai();
		this.daXoa = 0;
	}

	public VTCongVan(int cvId, String vtMa, String msnv, TrangThai trangThai, int daXoa) {
		this.cvId = cvId;
		this.vtMa = vtMa;
		this.msnv = msnv;
		this.trangThai = trangThai;
		this.daXoa = daXoa;
	}
	public TrangThai getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(TrangThai trangThai) {
		this.trangThai = trangThai;
	}

	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}

	/**
	 * @return the cvId
	 */
	public int getCvId() {
		return cvId;
	}

	/**
	 * @param cvId the cvId to set
	 */
	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	/**
	 * @return the vtMa
	 */
	public String getVtMa() {
		return vtMa;
	}

	/**
	 * @param vtMa the vtMa to set
	 */
	public void setVtMa(String vtMa) {
		this.vtMa = vtMa;
	}

	/**
	 * @return the msnv
	 */
	public String getMsnv() {
		return msnv;
	}

	/**
	 * @param msnv the msnv to set
	 */
	public void setMsnv(String msnv) {
		this.msnv = msnv;
	}

	
}
