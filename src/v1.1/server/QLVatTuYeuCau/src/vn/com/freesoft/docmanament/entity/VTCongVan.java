package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class VTCongVan.
 */

@Getter
@Setter
@Entity
@Table(name = "vtcongvan")
public class VTCongVan implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5499222460788239375L;

	/** The cv id. */
	private int cvId;

	/** The vt ma. */
	private String vtMa;

	/** The msnv. */
	private String msnv;

	/** The trang thai. */
	private TrangThai trangThai;

	/** The da xoa. */
	private int daXoa;

	/**
	 * Instantiates a new VT cong van.
	 */
	public VTCongVan() {
		this.cvId = 0;
		this.vtMa = "";
		this.msnv = "";
		this.trangThai = new TrangThai();
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new VT cong van.
	 *
	 * @param cvId
	 *            the cv id
	 * @param vtMa
	 *            the vt ma
	 * @param msnv
	 *            the msnv
	 * @param trangThai
	 *            the trang thai
	 * @param daXoa
	 *            the da xoa
	 */
	public VTCongVan(int cvId, String vtMa, String msnv, TrangThai trangThai, int daXoa) {
		this.cvId = cvId;
		this.vtMa = vtMa;
		this.msnv = msnv;
		this.trangThai = trangThai;
		this.daXoa = daXoa;
	}
}
