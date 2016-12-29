/**
 * 
 */
package vn.com.freesoft.docmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CTNguoiDung.
 *
 * @author QUOI
 */

@Getter
@Setter
@Entity
@Table(name = "ctnguoidung")
public class CTNguoiDung implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4713568491393996428L;

	/** The msnv. */
	@Id
	@Column(name = "MSNV")
	private String msnv;

	/** The mat khau. */
	@Column(name = "MATKHAU")
	private String matKhau;

	/** The khoa. */
	@Column(name = "KHOA")
	private int khoa;

	/**
	 * Instantiates a new CT nguoi dung.
	 *
	 * @param msnv
	 *            the msnv
	 * @param matKhau
	 *            the mat khau
	 * @param khoa
	 *            the khoa
	 */
	public CTNguoiDung(String msnv, String matKhau, int khoa) {
		super();
		this.msnv = msnv;
		this.matKhau = matKhau;
		this.khoa = khoa;
	}

	/**
	 * Instantiates a new CT nguoi dung.
	 */
	public CTNguoiDung() {
		this.msnv = "";
		this.matKhau = "";
		this.khoa = 0;
	}

}
