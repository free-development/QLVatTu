package vn.com.freesoft.docmanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chatluong")
public class ChatLuong implements Serializable {

	private static final long serialVersionUID = 1324552024816878200L;

	@Id
	@Column(name = "CLMA")
	private String clMa;

	@Column(name = "DAXOA")
	private int daXoa;

	@Column(name = "CLTEN")
	private String clTen;

	public ChatLuong() {
		this.clMa = "";
		this.clTen = "";
		this.daXoa = 0;
	}

	/**
	 * @param clMa
	 * @param clTen
	 * @param daXoa
	 */
	public ChatLuong(String clMa, String clTen) {
		this.clMa = clMa;
		this.clTen = clTen;
	}

	public ChatLuong(String clMa, String clTen, int daXoa) {
		this.clMa = clMa;
		this.clTen = clTen;
		this.daXoa = daXoa;
	}

	public ChatLuong(String clMa) {
		this.clMa = clMa;
	}
}
