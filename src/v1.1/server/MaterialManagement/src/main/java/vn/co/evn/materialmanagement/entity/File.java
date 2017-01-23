package vn.co.evn.materialmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class File.
 */
@Getter
@Setter
@Entity
@Table(name = "file")
public class File implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6801046533692410406L;

	/** The file id. */
	@Id
	@Column(name = "FILEID")
	private int fileId;

	/** The dia chi. */
	@Column(name = "DIACHI")
	private String diaChi;

	/** The mo ta. */
	@Column(name = "MOTA")
	private String moTa;

	/** The cv id. */
	@Column(name = "CVID")
	private int cvId;

	/**
	 * Instantiates a new file.
	 */
	public File() {
		this.fileId = 0;
		this.diaChi = "";
		this.moTa = "";
		this.cvId = 0;
	}

	/**
	 * Instantiates a new file.
	 *
	 * @param diaChi
	 *            the dia chi
	 * @param fileMoTa
	 *            the file mo ta
	 * @param cvId
	 *            the cv id
	 */
	public File(final String diaChi, final String fileMoTa, final int cvId) {
		this.diaChi = diaChi;
		this.moTa = fileMoTa;
		this.cvId = cvId;
	}

	/**
	 * Instantiates a new file.
	 *
	 * @param fileId
	 *            the file id
	 * @param diaChi
	 *            the dia chi
	 * @param fileMoTa
	 *            the file mo ta
	 * @param cvId
	 *            the cv id
	 */
	public File(int fileId, String diaChi, String fileMoTa, final int cvId) {
		this.fileId = fileId;
		this.diaChi = diaChi;
		this.moTa = fileMoTa;
		this.cvId = cvId;
	}

}
