package model;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize
public class YeuCau implements Serializable{
	
	private int ycId;
	
	private int cvId;
	private int ctvtId;
	

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 10
	 */
	private int ycSoLuong;
	private int capSoLuong;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName BIT
	 * DataTypeLength/Precision 0
	 */
	private int daXoa;
	
	public YeuCau() {
		this.cvId = 0;
		this.ctvtId = 0;
		this.ycSoLuong = 0;
		this.daXoa = 0;
		this.ycId = 0;
		this.capSoLuong = 0;
	}
	/**
	 * @param cvId
	 * @param ctvtId
	 * @param ycSoLuong
	 * @param daXoa
	 */
	public YeuCau(int ycId) {
		this.ycId = ycId;
	}

	/**
	 * @param cvId
	 * @param ctvtId
	 * @param ycSoLuong
	 * @param daXoa
	 */
	public YeuCau(int cvId, int ctvtId, int ycSoLuong, int capSoLuong, int daXoa) {
		this.cvId = cvId;
		this.ctvtId = ctvtId;
		this.ycSoLuong = ycSoLuong;
		this.daXoa = daXoa;
		this.capSoLuong =  capSoLuong;
	}
	
	/**
	 * @param cvId
	 * @param ctvtId
	 * @param ycSoLuong
	 * @param daXoa
	 */
	public YeuCau(int ycId, int cvId, int ctvtId, int ycSoLuong, int capSoLuong, int daXoa) {
		this.ycId = ycId;
		this.cvId = cvId;
		this.ctvtId = ctvtId;
		this.ycSoLuong = ycSoLuong;
		this.daXoa = daXoa;
		this.capSoLuong =  capSoLuong;
	}

	/**
	 * @return the cvId
	 */
	public  int getCvId() {
		return cvId;
	}

	/**
	 * @param cvId the cvId to set
	 */
	public  void setCvId(int cvId) {
		this.cvId = cvId;
	}

	public int getYcId() {
		return ycId;
	}

	public int getCapSoLuong() {
		return capSoLuong;
	}

	public void setCapSoLuong(int capSoLuong) {
		this.capSoLuong = capSoLuong;
	}

	public void setYcId(int ycId) {
		this.ycId = ycId;
	}

	public int getDaXoa() {
		return daXoa;
	}

	/**
	 * @return the ctvtId
	 */
	public  int getCtvtId() {
		return ctvtId;
	}

	/**
	 * @param ctvtId the ctvtId to set
	 */
	public  void setCtvtId(int ctvtId) {
		this.ctvtId = ctvtId;
	}

	/**
	 * @return the ycSoLuong
	 */
	public  int getYcSoLuong() {
		return ycSoLuong;
	}

	/**
	 * @param ycSoLuong the ycSoLuong to set
	 */
	public  void setYcSoLuong(int ycSoLuong) {
		this.ycSoLuong = ycSoLuong;
	}

	
	/**
	 * @param daXoa the daXoa to set
	 */
	public  void setDaXoa(int daXoa) {
		this.daXoa = daXoa;
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    MappingJackson2HttpMessageConverter converter = 
	        new MappingJackson2HttpMessageConverter(mapper);
	    return converter;
	}
}
