/**
 * 
 */
package vn.co.evn.materialmanagement.service;

import java.util.List;

import vn.co.evn.materialmanagement.entity.NoiSanXuat;

/**
 * The Interface NoiSanXuatService.
 *
 * @author quoip
 */
public interface NoiSanXuatService {

	/**
	 * Filter NoiSanXuat by nsxMa or nsxTen
	 * 
	 * @param nsx<br>
	 *            value need filter
	 * @return
	 */
	List<NoiSanXuat> getNoiSanXuat(NoiSanXuat nsx);
	
	/**
	 * Get NoiSanXuat by nsxMa
	 * 
	 * @param nsxMa<br>
	 *            nsxMa of NoiSanXuat need to find
	 * @return
	 */
	NoiSanXuat getNoiSanXuatById(String nsxMa);
}
