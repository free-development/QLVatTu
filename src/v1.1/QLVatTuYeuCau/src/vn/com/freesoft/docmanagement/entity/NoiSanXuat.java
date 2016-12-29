package vn.com.freesoft.docmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class NoiSanXuat.
 */
@Getter
@Setter
@Entity
@Table(name = "noisanxuat")
public class NoiSanXuat implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6827796178145954171L;

	/** The nsx ma. */
	@Id
	@Column(name = "NSXMA")
	private String nsxMa;

	/** The nsx ten. */
	@Column(name = "NSXTEN")
	private String nsxTen;

	/** The da xoa. */
	@Column(name = "DAXOA")
	private int daXoa;

	/**
	 * Instantiates a new noi san xuat.
	 */
	public NoiSanXuat() {
		this.nsxMa = "";
		this.nsxTen = "";
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new noi san xuat.
	 *
	 * @param nsxMa
	 *            the nsx ma
	 * @param nsxTen
	 *            the nsx ten
	 * @param daXoa
	 *            the da xoa
	 */
	public NoiSanXuat(String nsxMa, String nsxTen, int daXoa) {
		this.nsxMa = nsxMa;
		this.nsxTen = nsxTen;
		this.daXoa = daXoa;
	}

	/**
	 * Instantiates a new noi san xuat.
	 *
	 * @param nsxMa
	 *            the nsx ma
	 * @param nsxTen
	 *            the nsx ten
	 */
	public NoiSanXuat(String nsxMa, String nsxTen) {
		this.nsxMa = nsxMa;
		this.nsxTen = nsxTen;
	}

	/**
	 * Instantiates a new noi san xuat.
	 *
	 * @param nsxMa
	 *            the nsx ma
	 */
	public NoiSanXuat(String nsxMa) {
		this.nsxMa = nsxMa;
	}

}
