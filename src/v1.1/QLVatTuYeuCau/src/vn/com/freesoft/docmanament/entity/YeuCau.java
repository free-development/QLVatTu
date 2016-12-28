package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class YeuCau.
 */
@Getter
@Setter
@Entity
@Table(name = "yeu_cau")
public class YeuCau implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8991286085720286457L;

	/** The yc id. */
	private int ycId;

	/** The cv id. */
	private int cvId;

	/** The ctvt id. */
	private int ctvtId;

	/**
	 * NOT NULL false DomainName DatatypeName INT DataTypeLength/Precision 10.
	 */
	private int ycSoLuong;

	/** The cap so luong. */
	private int capSoLuong;

	/**
	 * NOT NULL false DomainName DatatypeName BIT DataTypeLength/Precision 0.
	 */
	private int daXoa;

	/**
	 * Instantiates a new yeu cau.
	 */
	public YeuCau() {
		this.cvId = 0;
		this.ctvtId = 0;
		this.ycSoLuong = 0;
		this.daXoa = 0;
		this.ycId = 0;
		this.capSoLuong = 0;
	}

	/**
	 * Instantiates a new yeu cau.
	 *
	 * @param ycId
	 *            the yc id
	 */
	public YeuCau(int ycId) {
		this.ycId = ycId;
	}

	/**
	 * Instantiates a new yeu cau.
	 *
	 * @param cvId
	 *            the cv id
	 * @param ctvtId
	 *            the ctvt id
	 * @param ycSoLuong
	 *            the yc so luong
	 * @param capSoLuong
	 *            the cap so luong
	 * @param daXoa
	 *            the da xoa
	 */
	public YeuCau(int cvId, int ctvtId, int ycSoLuong, int capSoLuong, int daXoa) {
		this.cvId = cvId;
		this.ctvtId = ctvtId;
		this.ycSoLuong = ycSoLuong;
		this.daXoa = daXoa;
		this.capSoLuong = capSoLuong;
	}

	/**
	 * Instantiates a new yeu cau.
	 *
	 * @param ycId
	 *            the yc id
	 * @param cvId
	 *            the cv id
	 * @param ctvtId
	 *            the ctvt id
	 * @param ycSoLuong
	 *            the yc so luong
	 * @param capSoLuong
	 *            the cap so luong
	 * @param daXoa
	 *            the da xoa
	 */
	public YeuCau(int ycId, int cvId, int ctvtId, int ycSoLuong, int capSoLuong, int daXoa) {
		this.ycId = ycId;
		this.cvId = cvId;
		this.ctvtId = ctvtId;
		this.ycSoLuong = ycSoLuong;
		this.daXoa = daXoa;
		this.capSoLuong = capSoLuong;
	}

}
