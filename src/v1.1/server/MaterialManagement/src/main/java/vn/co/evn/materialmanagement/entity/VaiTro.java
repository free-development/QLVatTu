package vn.co.evn.materialmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class VaiTro.
 */
@Getter
@Setter
@Entity
@Table(name = "vaitro")
public class VaiTro implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5140554425877010719L;

	/** The vt ma. */
	@Id
	@Column(name = "VTMA")
	private String vtMa;

	/** The vt ten. */
	@Column(name = "VTTEN")
	private String vtTen;

	/** The da xoa. */
	@Column(name = "DAXOA")
	private int daXoa;

	/**
	 * Instantiates a new vai tro.
	 */
	public VaiTro() {
		this.vtMa = "";
		this.vtTen = "";
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new vai tro.
	 *
	 * @param vtMa
	 *            the vt ma
	 * @param vtTen
	 *            the vt ten
	 * @param daXoa
	 *            the da xoa
	 */
	public VaiTro(String vtMa, String vtTen, int daXoa) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.daXoa = daXoa;
	}

}