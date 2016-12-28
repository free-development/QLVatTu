package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DonVi.
 */
@Getter
@Setter
@Entity
@Table(name = "donvi")
public class DonVi implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -134462772138957993L;

	/** The dv ma. */
	private String dvMa;

	/** The dv ten. */
	private String dvTen;

	/** The sdt. */
	private String sdt;

	/** The email. */
	private String email;

	/** The dia chi. */
	private String diaChi;

	/** The da xoa. */
	private int daXoa;

	/**
	 * Instantiates a new don vi.
	 */
	public DonVi() {
		this.dvMa = "";
		this.dvTen = "";
		this.sdt = "";
		this.email = "";
		this.diaChi = "";
		daXoa = 0;
	}

	/**
	 * Instantiates a new don vi.
	 *
	 * @param dvMa
	 *            the dv ma
	 */
	public DonVi(String dvMa) {
		this.dvMa = dvMa;
	}

	/**
	 * Instantiates a new don vi.
	 *
	 * @param dvMa
	 *            the dv ma
	 * @param dvTen
	 *            the dv ten
	 * @param sdt
	 *            the sdt
	 * @param diaChi
	 *            the dia chi
	 * @param email
	 *            the email
	 */
	public DonVi(String dvMa, String dvTen, String sdt, String diaChi, String email) {
		this.dvMa = dvMa;
		this.dvTen = dvTen;
		this.sdt = sdt;
		this.email = email;
		this.diaChi = diaChi;
	}

	/**
	 * Instantiates a new don vi.
	 *
	 * @param dvMa
	 *            the dv ma
	 * @param dvTen
	 *            the dv ten
	 * @param sdt
	 *            the sdt
	 * @param diaChi
	 *            the dia chi
	 * @param email
	 *            the email
	 * @param daXoa
	 *            the da xoa
	 */
	public DonVi(String dvMa, String dvTen, String sdt, String diaChi, String email, int daXoa) {
		this.dvMa = dvMa;
		this.dvTen = dvTen;
		this.sdt = sdt;
		this.email = email;
		this.diaChi = diaChi;
		this.daXoa = daXoa;
	}
}
