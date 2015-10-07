package model;

import java.io.Serializable;

public class File implements Serializable {
	private int fileId;
	private String diaChi;
	private String moTa;
	private int cvId;
	
	public File() {
		this.fileId = 0;
		this.diaChi = "";
		this.moTa = "";
		this.cvId = 0;
	}
	
	/**
	 * @param fileId
	 * @param diaChi
	 * @param moTa
	 */
	public File(final String diaChi, final String fileMoTa, final int cvId) {
		this.diaChi = diaChi;
		this.moTa = fileMoTa;
		this.cvId = cvId;
	}
	
	/**
	 * @param fileId
	 * @param diaChi
	 * @param moTa
	 */
	public File(int fileId, String diaChi, String fileMoTa, final int cvId) {
		this.fileId = fileId;
		this.diaChi = diaChi;
		this.moTa = fileMoTa;
		this.cvId = cvId;
	}
	public int getCvId() {
		return cvId;
	}

	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}
	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the diaChi
	 */
	public String getDiaChi() {
		return diaChi;
	}
	/**
	 * @param diaChi the diaChi to set
	 */
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	/**
	 * @return the moTa
	 */
	public String getMoTa() {
		return moTa;
	}
	/**
	 * @param moTa the moTa to set
	 */
	public void setMoTa(String fileMoTa) {
		this.moTa = fileMoTa;
	}
	
}
