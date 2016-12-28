package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vattu")
public class VatTu implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8232506623290397713L;

	/** The vt ma. */
	private String vtMa;

	/** The da xoa. */
	private int daXoa;

	/** The vt ten. */
	private String vtTen;

	/** The dvt. */
	private DonViTinh dvt;

	/**
	 * Instantiates a new vat tu.
	 */
	public VatTu() {
		this.vtMa = "";
		this.vtTen = "";
		this.dvt = new DonViTinh();
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new vat tu.
	 *
	 * @param vtMa
	 *            the vt ma
	 */
	public VatTu(String vtMa) {
		this.vtMa = vtMa;
	}

	/**
	 * Instantiates a new vat tu.
	 *
	 * @param vtMa
	 *            the vt ma
	 * @param vtTen
	 *            the vt ten
	 */
	public VatTu(String vtMa, String vtTen) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new vat tu.
	 *
	 * @param vtMa
	 *            the vt ma
	 * @param vtTen
	 *            the vt ten
	 * @param dvt2
	 *            the dvt 2
	 * @param daXoa
	 *            the da xoa
	 */
	public VatTu(String vtMa, String vtTen, DonViTinh dvt2, int daXoa) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.dvt = dvt2;
		this.daXoa = daXoa;
	}
}
