package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CongVan.
 */
@Getter
@Setter
@Entity
@Table(name = "congvan")
public class CongVan implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3567577069626637693L;

	/** The cv id. */
	private int cvId;

	/** The so den. */
	private int soDen;

	/** The cv ngay nhan. */
	private Date cvNgayNhan;

	/** The cv so. */
	private String cvSo;

	/** The cv ngay di. */
	private Date cvNgayDi;

	/** The trich yeu. */
	private String trichYeu;

	/** The but phe. */
	private String butPhe;

	/** The muc dich. */
	private MucDich mucDich;

	/** The trang thai. */
	private TrangThai trangThai;

	/** The don vi. */
	private DonVi donVi;

	/** The da xoa. */
	private int daXoa;

	/**
	 * Instantiates a new cong van.
	 */
	public CongVan() {
		this.cvId = 0;
		this.soDen = 0;
		this.cvNgayNhan = new Date();
		this.cvSo = "";
		this.cvNgayDi = new Date();
		this.trichYeu = "";
		this.butPhe = "";
		this.mucDich = new MucDich();
		this.trangThai = new TrangThai();
		this.donVi = new DonVi();
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new cong van.
	 *
	 * @param soDen
	 *            the so den
	 * @param cvSo
	 *            the cv so
	 * @param cvNgayNhan
	 *            the cv ngay nhan
	 * @param cvNgayDi
	 *            the cv ngay di
	 * @param trichYeu
	 *            the trich yeu
	 * @param butPhe
	 *            the but phe
	 * @param mucDich
	 *            the muc dich
	 * @param trangThai
	 *            the trang thai
	 * @param donVi
	 *            the don vi
	 * @param daXoa
	 *            the da xoa
	 */
	public CongVan(int soDen, String cvSo, Date cvNgayNhan, Date cvNgayDi, String trichYeu, String butPhe,
			MucDich mucDich, TrangThai trangThai, DonVi donVi, int daXoa) {
		this.soDen = soDen;
		this.cvNgayNhan = cvNgayNhan;
		this.cvSo = cvSo;
		this.cvNgayDi = cvNgayDi;
		this.trichYeu = trichYeu;
		this.butPhe = butPhe;
		this.mucDich = mucDich;
		this.trangThai = trangThai;
		this.donVi = donVi;
		this.daXoa = daXoa;
	}

	/**
	 * Instantiates a new cong van.
	 *
	 * @param cvId
	 *            the cv id
	 * @param soDen
	 *            the so den
	 * @param cvSo
	 *            the cv so
	 * @param cvNgayNhan
	 *            the cv ngay nhan
	 * @param cvNgayDi
	 *            the cv ngay di
	 * @param trichYeu
	 *            the trich yeu
	 * @param butPhe
	 *            the but phe
	 * @param mucDich
	 *            the muc dich
	 * @param trangThai
	 *            the trang thai
	 * @param donVi
	 *            the don vi
	 * @param daXoa
	 *            the da xoa
	 */
	public CongVan(int cvId, int soDen, String cvSo, Date cvNgayNhan, Date cvNgayDi, String trichYeu, String butPhe,
			MucDich mucDich, TrangThai trangThai, DonVi donVi, int daXoa) {
		this.cvId = cvId;
		this.soDen = soDen;
		this.cvNgayNhan = cvNgayNhan;
		this.cvSo = cvSo;
		this.cvNgayDi = cvNgayDi;
		this.trichYeu = trichYeu;
		this.butPhe = butPhe;
		this.mucDich = mucDich;
		this.trangThai = trangThai;
		this.donVi = donVi;
		this.daXoa = daXoa;
	}

}
