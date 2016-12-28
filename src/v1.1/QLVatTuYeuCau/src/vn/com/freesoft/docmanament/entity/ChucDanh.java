package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

public class ChucDanh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4119537307932174336L;

	/**
	 * NOT NULL true DomainName DatatypeName VARCHAR DataTypeLength/Precision 10
	 */
	private String cdMa;

	/**
	 * NOT NULL false DomainName DatatypeName VARCHAR DataTypeLength/Precision
	 * 30
	 */
	private String cdTen;
	private int daXoa;

	public ChucDanh() {
		this.cdMa = "";
		this.cdTen = "";
		this.daXoa = 0;
	}

	/**
	 * @param cdMa
	 * @param cdTen
	 */
	public ChucDanh(String cdMa) {
		this.cdMa = cdMa;
	}

	public ChucDanh(String cdMa, String cdTen, int daXoa) {
		this.cdMa = cdMa;
		this.cdTen = cdTen;
		this.daXoa = daXoa;
	}

	/**
	 * @return the cdMa
	 */
	public String getCdMa() {
		return cdMa;
	}

	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}

	/**
	 * @param cdMa
	 *            the cdMa to set
	 */
	public void setCdMa(String cdMa) {
		this.cdMa = cdMa;
	}

	/**
	 * @return the cdTen
	 */
	public String getCdTen() {
		return cdTen;
	}

	/**
	 * @param cdTen
	 *            the cdTen to set
	 */
	public void setCdTen(String cdTen) {
		this.cdTen = cdTen;
	}

}
