package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class TrangThai.
 */

@Getter
@Setter
@Entity
@Table(name = "trangthai")
public class TrangThai implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6680619897242833445L;

	/** The tt ma. */
	private String ttMa;

	/** The tt ten. */
	private String ttTen;

	/**
	 * Instantiates a new trang thai.
	 */
	public TrangThai() {
		this.ttMa = "";
		this.ttTen = "";
	}

	/**
	 * Instantiates a new trang thai.
	 *
	 * @param ttMa
	 *            the tt ma
	 */
	public TrangThai(String ttMa) {
		this.ttMa = ttMa;
	}

	/**
	 * Instantiates a new trang thai.
	 *
	 * @param ttMa
	 *            the tt ma
	 * @param ttTen
	 *            the tt ten
	 */
	public TrangThai(String ttMa, String ttTen) {
		this.ttMa = ttMa;
		this.ttTen = ttTen;
	}
}
