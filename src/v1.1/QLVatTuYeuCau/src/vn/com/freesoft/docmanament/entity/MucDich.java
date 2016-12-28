/**
 * 
 */
package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MucDich.
 *
 * @author QUOI
 */

@Getter
@Setter
@Entity
@Table(name = "mucdich")
public class MucDich implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6737671805960160984L;

	/** The md ma. */
	private String mdMa;

	/** The md ten. */
	private String mdTen;

	/** The da xoa. */
	private int daXoa;

	/**
	 * Instantiates a new muc dich.
	 */
	public MucDich() {
		this.mdMa = "";
		this.mdTen = "";
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new muc dich.
	 *
	 * @param mdMa
	 *            the md ma
	 */
	public MucDich(String mdMa) {
		this.mdMa = mdMa;
	}

	/**
	 * Instantiates a new muc dich.
	 *
	 * @param mdMa
	 *            the md ma
	 * @param mdTen
	 *            the md ten
	 * @param daXoa
	 *            the da xoa
	 */
	public MucDich(String mdMa, String mdTen, int daXoa) {
		this.mdMa = mdMa;
		this.mdTen = mdTen;
	}

}
