/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author QUOI
 *
 */
public class CTNguoiDung implements Serializable{
	
	private String msnv;
	private String matKhau;
	private int khoa;
	public CTNguoiDung(String msnv, String matKhau, int khoa) {
		super();
		this.msnv = msnv;
		this.matKhau = matKhau;
		this.khoa = khoa;
	}
	public CTNguoiDung() {
		this.msnv = "";
		this.matKhau = "";
		this.khoa = 0;
	}
	/**
	 * @param msnv
	 * @param matKhau
	 */
	
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
	/**
	 * @return the matKhau
	 */
	public String getMatKhau() {
		return matKhau;
	}
	/**
	 * @param matKhau the matKhau to set
	 */
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	/**
	 * @return the khoa
	 */
	public int getKhoa() {
		return khoa;
	}
	/**
	 * @param khoa the khoa to set
	 */
	public void setKhoa(int khoa) {
		this.khoa = khoa;
	}
	
}
