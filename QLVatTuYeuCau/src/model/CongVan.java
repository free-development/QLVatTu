package model;

import java.io.Serializable;
import java.sql.Date;

public class CongVan implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int cvId;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int soDen;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName DATE
	 * DataTypeLength/Precision 
	 */
	private Date cvNgayNhan;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 10
	 */
	private String cvSo;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName DATE
	 * DataTypeLength/Precision 
	 */
	private Date cvNgayDi;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName TEXT
	 * DataTypeLength/Precision 
	 */
	private String trichYeu;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName TEXT
	 * DataTypeLength/Precision 
	 */
	private String butPhe;

	private MucDich mucDich;

	private TrangThai trangThai;

	private DonVi donVi;
	private int daXoa; 

	public CongVan() {
		this.cvId = 0;
		this.soDen = 0;
		this.cvNgayNhan = new Date(1,1,1);
		this.cvSo = "";
		this.cvNgayDi = new Date(1,1,1);;
		this.trichYeu = "";
		this.butPhe = "";
		this.mucDich = new MucDich();
		this.trangThai = new TrangThai();
		this.donVi = new DonVi();
		this.daXoa = 0;
	}
	
	/**
	 * @param cvId
	 * @param soDen
	 * @param cvNgayNhan
	 * @param cvSo
	 * @param cvNgayDi
	 * @param trichYeu
	 * @param butPhe
	 * @param mucDich
	 * @param trangThai
	 * @param donVi
	 * @param file
	 */
	public CongVan(int soDen, String cvSo, Date cvNgayNhan, 
			Date cvNgayDi, String trichYeu, String butPhe, MucDich mucDich,
			TrangThai trangThai, DonVi donVi, int daXoa) {
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
	 * @param cvId
	 * @param soDen
	 * @param cvNgayNhan
	 * @param cvSo
	 * @param cvNgayDi
	 * @param trichYeu
	 * @param butPhe
	 * @param mucDich
	 * @param trangThai
	 * @param donVi
	 * @param file
	 */
	public CongVan(int cvId, int soDen, String cvSo, Date cvNgayNhan,Date cvNgayDi, 
			 String trichYeu, String butPhe, MucDich mucDich,
			TrangThai trangThai, DonVi donVi, int daXoa) {
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

	public int getDaXoa() {
		return daXoa;
	}

	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}

	/**
	 * @return the cvId
	 */
	public int getCvId() {
		return cvId;
	}

	/**
	 * @param cvId the cvId to set
	 */
	public void setCvId(int cvId) {
		this.cvId = cvId;
	}

	/**
	 * @return the soDen
	 */
	public int getSoDen() {
		return soDen;
	}

	/**
	 * @param soDen the soDen to set
	 */
	public void setSoDen(int soDen) {
		this.soDen = soDen;
	}

	/**
	 * @return the cvNgayNhan
	 */
	public Date getCvNgayNhan() {
		return cvNgayNhan;
	}

	/**
	 * @param cvNgayNhan the cvNgayNhan to set
	 */
	public void setCvNgayNhan(Date cvNgayNhan) {
		this.cvNgayNhan = cvNgayNhan;
	}

	/**
	 * @return the cvSo
	 */
	public String getCvSo() {
		return cvSo;
	}

	/**
	 * @param cvSo the cvSo to set
	 */
	public void setCvSo(String cvSo) {
		this.cvSo = cvSo;
	}

	/**
	 * @return the cvNgayDi
	 */
	public Date getCvNgayDi() {
		return cvNgayDi;
	}

	/**
	 * @param cvNgayDi the cvNgayDi to set
	 */
	public void setCvNgayDi(Date cvNgayDi) {
		this.cvNgayDi = cvNgayDi;
	}

	/**
	 * @return the trichYeu
	 */
	public String getTrichYeu() {
		return trichYeu;
	}

	/**
	 * @param trichYeu the trichYeu to set
	 */
	public void setTrichYeu(String trichYeu) {
		this.trichYeu = trichYeu;
	}

	/**
	 * @return the butPhe
	 */
	public String getButPhe() {
		return butPhe;
	}

	/**
	 * @param butPhe the butPhe to set
	 */
	public void setButPhe(String butPhe) {
		this.butPhe = butPhe;
	}

	/**
	 * @return the mucDich
	 */
	public MucDich getMucDich() {
		return mucDich;
	}

	/**
	 * @param mucDich the mucDich to set
	 */
	public void setMucDich(MucDich mucDich) {
		this.mucDich = mucDich;
	}

	/**
	 * @return the trangThai
	 */
	public TrangThai getTrangThai() {
		return trangThai;
	}

	/**
	 * @param trangThai the trangThai to set
	 */
	public void setTrangThai(TrangThai trangThai) {
		this.trangThai = trangThai;
	}

	/**
	 * @return the donVi
	 */
	public DonVi getDonVi() {
		return donVi;
	}

	/**
	 * @param donVi the donVi to set
	 */
	public void setDonVi(DonVi donVi) {
		this.donVi = donVi;
	}

}
