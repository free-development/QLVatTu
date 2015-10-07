package model;

import java.io.Serializable;
// Lớp đơn vị
public class DonVi implements Serializable{
	private String dvMa;
	private String dvTen;
	private String sdt;
	private String email;
	private String diaChi;
	private int daXoa;
	

	public DonVi() {
		this.dvMa = "";
		this.dvTen = "";
		this.sdt = "";
		this.email = "";
		this.diaChi = "";
		daXoa = 0;
	}
	
	/**
	 * @param dvMa
	 */
	public DonVi(String dvMa) {
		this.dvMa = dvMa;
	}
	
	/**
	 * @param dvMa
	 * @param dvTen
	 * @param sdt
	 * @param email
	 * @param diaChi
	 * @param daXoa
	 */
	public DonVi(String dvMa, String dvTen, String sdt, String diaChi, String email) {
		this.dvMa = dvMa;
		this.dvTen = dvTen;
		this.sdt = sdt;
		this.email = email;
		this.diaChi = diaChi;
	}
	public DonVi(String dvMa, String dvTen, String sdt, String diaChi, String email, int daXoa) {
		this.dvMa = dvMa;
		this.dvTen = dvTen;
		this.sdt = sdt;
		this.email = email;
		this.diaChi = diaChi;
		this.daXoa = daXoa;
	}

	/**
	 * @return the dvMa
	 */
	public String getDvMa() {
		return dvMa;
	}

	/**
	 * @param dvMa the dvMa to set
	 */
	public void setDvMa(String dvMa) {
		this.dvMa = dvMa;
	}

	/**
	 * @return the dvTen
	 */
	public String getDvTen() {
		return dvTen;
	}

	/**
	 * @param dvTen the dvTen to set
	 */
	public void setDvTen(String dvTen) {
		this.dvTen = dvTen;
	}

	/**
	 * @return the sdt
	 */
	public String getSdt() {
		return sdt;
	}

	/**
	 * @param sdt the sdt to set
	 */
	public void setSdt(String dvSdt) {
		this.sdt = dvSdt;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String dvEmail) {
		this.email = dvEmail;
	}

	/**
	 * @return the diaChi
	 */
	public String getDiaChi() {
		return diaChi;
	}

	/**
	 * @param diaChi the diaChi to set
	 */
	public void setDiaChi(String dvDiaChi) {
		this.diaChi = dvDiaChi;
	}
	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}
}
