/**
 * 
 */
package vn.com.freesoft.docmanagement.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NhatKy.
 *
 * @author quoioln
 */
@Getter
@Setter
public class NhatKy {

	/** The nk id. */
	private int nkId;

	/** The msnv. */
	private String msnv;

	/** The thoi gian. */
	private Date thoiGian;

	/** The noi dung. */
	private String noiDung;

	/** The hoat dong. */
	private String hoatDong;

	/**
	 * Instantiates a new nhat ky.
	 */
	public NhatKy() {
		this.nkId = 0;
		this.msnv = "";
		this.thoiGian = new Date();
		this.hoatDong = "";
		this.noiDung = "";
	}

	/**
	 * Instantiates a new nhat ky.
	 *
	 * @param msnv
	 *            the msnv
	 * @param hoatDong
	 *            the hoat dong
	 * @param thoiGian
	 *            the thoi gian
	 * @param noiDung
	 *            the noi dung
	 */
	public NhatKy(String msnv, String hoatDong, Date thoiGian, String noiDung) {
		this.msnv = msnv;
		this.thoiGian = thoiGian;
		this.noiDung = noiDung;
		this.hoatDong = hoatDong;
	}

	/**
	 * Instantiates a new nhat ky.
	 *
	 * @param nkId
	 *            the nk id
	 * @param msnv
	 *            the msnv
	 * @param hoatDong
	 *            the hoat dong
	 * @param thoiGian
	 *            the thoi gian
	 * @param noiDung
	 *            the noi dung
	 */
	public NhatKy(int nkId, String msnv, String hoatDong, Date thoiGian, String noiDung) {
		this.nkId = nkId;
		this.msnv = msnv;
		this.thoiGian = thoiGian;
		this.noiDung = noiDung;
		this.hoatDong = hoatDong;
	}

}
