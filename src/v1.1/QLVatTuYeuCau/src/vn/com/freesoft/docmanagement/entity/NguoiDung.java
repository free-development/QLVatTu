package vn.com.freesoft.docmanagement.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NguoiDung.
 */
@Getter
@Setter
@Entity
@Table(name = "nguoidung")
public class NguoiDung implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4702997050934473722L;

	/** The msnv. */
	@Id
	@Column(name = "MSNV")
	private String msnv;

	/** The ho ten. */
	@Column(name = "HOTEN")
	private String hoTen;

	/** The dia chi. */
	@Column(name = "DIACHI")
	private String diaChi;

	/** The email. */
	@Column(name = "EMAIL")
	private String email;

	/** The sdt. */
	@Column(name = "SDT")
	private String sdt;

	/** The chuc danh. */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CDMA")
	private ChucDanh chucDanh;

	/**
	 * Instantiates a new nguoi dung.
	 */
	public NguoiDung() {
		this.msnv = "";
		this.hoTen = "";
		this.diaChi = "";
		this.email = "";
		this.sdt = "";
		this.chucDanh = new ChucDanh();
	}

	/**
	 * Instantiates a new nguoi dung.
	 *
	 * @param msnv
	 *            the msnv
	 * @param hoTen
	 *            the ho ten
	 * @param diaChi
	 *            the dia chi
	 * @param email
	 *            the email
	 * @param sdt
	 *            the sdt
	 * @param chucDanh
	 *            the chuc danh
	 */
	public NguoiDung(String msnv, String hoTen, String diaChi, String email, String sdt, ChucDanh chucDanh) {
		this.msnv = msnv;
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.email = email;
		this.sdt = sdt;
		this.chucDanh = chucDanh;
	}

}
