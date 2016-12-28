package vn.com.freesoft.docmanament.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CTVatTu.
 */
@Getter
@Setter
@Entity
@Table(name = "ctvattu")
public class CTVatTu implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6414450643509677652L;

	/** The ctvt id. */
	private int ctvtId;

	/** The dinh muc. */
	private int dinhMuc;

	/** The da xoa. */
	private int daXoa;

	/** The so luong ton. */
	private int soLuongTon;

	/** The vat tu. */
	private VatTu vatTu;

	/** The noi san xuat. */
	private NoiSanXuat noiSanXuat;

	/** The chat luong. */
	private ChatLuong chatLuong;

	/**
	 * Instantiates a new CT vat tu.
	 */
	public CTVatTu() {
		this.ctvtId = 0;
		this.dinhMuc = 0;
		this.soLuongTon = 0;
		this.vatTu = new VatTu();
		this.noiSanXuat = new NoiSanXuat();
		this.chatLuong = new ChatLuong();
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new CT vat tu.
	 *
	 * @param vatTu
	 *            the vat tu
	 * @param noiSanXuat
	 *            the noi san xuat
	 * @param chatLuong
	 *            the chat luong
	 * @param dinhMuc
	 *            the dinh muc
	 * @param soLuongTon
	 *            the so luong ton
	 * @param daXoa
	 *            the da xoa
	 */
	public CTVatTu(VatTu vatTu, NoiSanXuat noiSanXuat, ChatLuong chatLuong, int dinhMuc, int soLuongTon, int daXoa) {
		this.dinhMuc = dinhMuc;
		this.soLuongTon = soLuongTon;
		this.vatTu = vatTu;
		this.noiSanXuat = noiSanXuat;
		this.chatLuong = chatLuong;
		this.daXoa = daXoa;

	}

	/**
	 * Instantiates a new CT vat tu.
	 *
	 * @param ctvtId
	 *            the ctvt id
	 */
	public CTVatTu(int ctvtId) {
		this.ctvtId = ctvtId;
	}

	/**
	 * Instantiates a new CT vat tu.
	 *
	 * @param ctvtId
	 *            the ctvt id
	 * @param vatTu
	 *            the vat tu
	 * @param noiSanXuat
	 *            the noi san xuat
	 * @param chatLuong
	 *            the chat luong
	 * @param dinhMuc
	 *            the dinh muc
	 * @param soLuongTon
	 *            the so luong ton
	 * @param daXoa
	 *            the da xoa
	 */
	public CTVatTu(int ctvtId, VatTu vatTu, NoiSanXuat noiSanXuat, ChatLuong chatLuong, int dinhMuc, int soLuongTon,
			int daXoa) {
		this.ctvtId = ctvtId;
		this.dinhMuc = dinhMuc;
		this.soLuongTon = soLuongTon;
		this.vatTu = vatTu;
		this.noiSanXuat = noiSanXuat;
		this.chatLuong = chatLuong;
		this.daXoa = daXoa;
	}

}
