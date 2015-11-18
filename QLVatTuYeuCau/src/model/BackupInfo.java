/**
 * 
 */
package model;

import java.util.Date;

/**
 * @author quoioln
 *
 */
public class BackupInfo {
	private int stt;
	private String thoiGian;
	private String moTa;
	private String filePath;
	
	public BackupInfo() {
		this.thoiGian = "";
		this.moTa = "";
		this.filePath = "";
		this.stt = 0;
	}

	/**
	 * Contructor with
	 * @param stt
	 * @param thoiGian
	 * @param moTa
	 * @param filePath
	 */
	public BackupInfo(int stt, String thoiGian, String moTa, String filePath) {
		this.thoiGian = thoiGian;
		this.moTa = moTa;
		this.filePath = filePath;
		this.stt = stt;
	}

	/**
	 * @return the thoiGian
	 */
	public String getThoiGian() {
		return thoiGian;
	}

	/**
	 * @param thoiGian the thoiGian to set
	 */
	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	/**
	 * @return the moTa
	 */
	public String getMoTa() {
		return moTa;
	}

	/**
	 * @return the stt
	 */
	public int getStt() {
		return stt;
	}

	/**
	 * @param stt the stt to set
	 */
	public void setStt(int stt) {
		this.stt = stt;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @param moTa the moTa to set
	 */
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	/**
	 * @return the filePath
	 */
	public String getfilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setfilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
