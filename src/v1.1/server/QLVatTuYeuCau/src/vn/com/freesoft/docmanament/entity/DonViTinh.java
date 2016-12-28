package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DonViTinh.
 */
@Getter
@Setter
@Entity
@Table(name = "donvitinh")
public class DonViTinh implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1618777965194372420L;

	/** The dvt id. */
	private int dvtId;

	/** The dvt ten. */
	private String dvtTen;

	/** The da xoa. */
	private int daXoa;

	/**
	 * Instantiates a new don vi tinh.
	 */
	public DonViTinh() {
		this.dvtId = 0;
		this.dvtTen = "";
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new don vi tinh.
	 *
	 * @param dvtId
	 *            the dvt id
	 */

	public DonViTinh(int dvtId) {
		this.dvtId = dvtId;
	}

	/**
	 * Instantiates a new don vi tinh.
	 *
	 * @param dvtTen
	 *            the dvt ten
	 * @param daXoa
	 *            the da xoa
	 */
	public DonViTinh(String dvtTen, int daXoa) {
		this.dvtTen = dvtTen;
		this.daXoa = daXoa;
	}

	/**
	 * Instantiates a new don vi tinh.
	 *
	 * @param dvtId
	 *            the dvt id
	 * @param dvtTen
	 *            the dvt ten
	 * @param daXoa
	 *            the da xoa
	 */
	public DonViTinh(int dvtId, String dvtTen, int daXoa) {
		this.dvtId = dvtId;
		this.dvtTen = dvtTen;
		this.daXoa = daXoa;
	}

}