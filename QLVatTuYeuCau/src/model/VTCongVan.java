package model;

import java.io.Serializable;


public class VTCongVan implements Serializable{

	private int cvId;

	private int vtId;

	private String msnv;
	private TrangThai trangThai;
	private int daXoa;
	public VTCongVan() {
		this.cvId = 0;
		this.vtId = 0;
		this.msnv = "";
		this.trangThai = new TrangThai();
		this.daXoa = 0;
	}

	/**
	 * @param cvId
	 * @param vtId
	 * @param msnv
	 */
	public VTCongVan(int cvId, int vtId, String msnv, TrangThai trangThai) {
		this.cvId = cvId;
		this.vtId = vtId;
		this.msnv = msnv;
		this.trangThai = trangThai;
	}
	public VTCongVan(int cvId, int vtId, String msnv, TrangThai trangThai, int daXoa) {
		this.cvId = cvId;
		this.vtId = vtId;
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
	 * @return the vtId
	 */
	public int getVtId() {
		return vtId;
	}

	/**
	 * @param vtId the vtId to set
	 */
	public void setVtId(int vtId) {
		this.vtId = vtId;
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
