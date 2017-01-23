package vn.co.evn.materialmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ChucDanh.
 */
@Getter
@Setter
@Entity
@Table(name =  "chucdanh")
public class ChucDanh implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4119537307932174336L;

	/** The cd ma. */
	@Id
	@Column(name = "CDMA")
	private String cdMa;

	/** The cd ten. */
	@Column(name = "CDTEN")
	private String cdTen;
	
	/** The da xoa. */
	@Column(name = "DAXOA")
	private int daXoa;

	/**
	 * Instantiates a new chuc danh.
	 */
	public ChucDanh() {
		this.cdMa = "";
		this.cdTen = "";
		this.daXoa = 0;
	}

	/**
	 * Instantiates a new chuc danh.
	 *
	 * @param cdMa the cd ma
	 */
	public ChucDanh(String cdMa) {
		this.cdMa = cdMa;
	}

	/**
	 * Instantiates a new chuc danh.
	 *
	 * @param cdMa the cd ma
	 * @param cdTen the cd ten
	 * @param daXoa the da xoa
	 */
	public ChucDanh(String cdMa, String cdTen, int daXoa) {
		this.cdMa = cdMa;
		this.cdTen = cdTen;
		this.daXoa = daXoa;
	}

}
