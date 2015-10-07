package model;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class VatTu implements Serializable{

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName CHAR
	 * DataTypeLength/Precision 16
	 */
	private String vtMa;
	private int daXoa;
	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 50
	 */
	private String vtTen;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private DonViTinh dvt;
	
	public VatTu() {
		this.vtMa = "";
		this.vtTen = "";
		this.dvt = new DonViTinh();
		this.daXoa =0;
	}
	/**
	 * @param vtMa
	 */
	public VatTu(String vtMa) {
		this.vtMa = vtMa;
	}
	
	
	/**
	 * @param vtMa
	 * @param vtTen
	 */
	public VatTu(String vtMa, String vtTen) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.daXoa = 0;
	}
	
	/**
	 * @param vtMa
	 * @param vtTen
	 * @param dvt2
	 */
	public VatTu(String vtMa, String vtTen, DonViTinh dvt2,int daXoa) {
		this.vtMa = vtMa;
		this.vtTen = vtTen;
		this.dvt = dvt2;
		this.daXoa = daXoa;
	}

	/**
	 * @return the vtMa
	 */
	public  String getVtMa() {
		return vtMa;
	}

	/**
	 * @param vtMa the vtMa to set
	 */
	public  void setVtMa(String vtMa) {
		this.vtMa = vtMa;
	}

	/**
	 * @return the vtTen
	 */
	public  String getVtTen() {
		return vtTen;
	}

	public int getDaXoa() {
		return daXoa;
	}
	public void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}
	/**
	 * @param vtTen the vtTen to set
	 */
	public  void setVtTen(String vtTen) {
		this.vtTen = vtTen;
	}

	public DonViTinh getDvt() {
		return dvt;
	}
	public void setDvt(DonViTinh dvt) {
		this.dvt = dvt;
	}
}
