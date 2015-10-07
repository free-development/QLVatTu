package model;

import java.io.Serializable;


public class NguoiDung implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 10
	 */
	private String msnv;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 50
	 */
	private String hoTen;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 50
	 */
	private String diaChi;
	
	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 50
	 */
	private String email;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 12
	 */
	private String sdt;

	private ChucDanh chucDanh;
	
	public NguoiDung() {
		this.msnv = "";
		this.hoTen = "";
		this.diaChi = "";
		this.email = "";
		this.sdt = "";
		this.chucDanh = new ChucDanh();
	}

	/**
	 * @param msnv
	 * @param hoTen
	 * @param diaChi
	 * @param email
	 * @param sdt
	 * @param chucDanh
	 */
	public NguoiDung(String msnv, String hoTen, String diaChi, String email,
			String sdt, ChucDanh chucDanh) {
		super();
		this.msnv = msnv;
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.email = email;
		this.sdt = sdt;
		this.chucDanh = chucDanh;
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

	/**
	 * @return the hoTen
	 */
	public String getHoTen() {
		return hoTen;
	}

	/**
	 * @param hoTen the hoTen to set
	 */
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
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
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
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
	public void setEmail(String email) {
		this.email = email;
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
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	/**
	 * @return the chucDanh
	 */
	public ChucDanh getChucDanh() {
		return chucDanh;
	}

	/**
	 * @param chucDanh the chucDanh to set
	 */
	public void setChucDanh(ChucDanh chucDanh) {
		this.chucDanh = chucDanh;
	}

}
