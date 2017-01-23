/**
 * 
 */
package vn.co.evn.materialmanagement.service;

import java.util.List;

import vn.co.evn.materialmanagement.entity.ChatLuong;

/**
 * The Interface ChatLuongService.
 *
 * @author quoip
 */
public interface ChatLuongService {

	/**
	 * Filter ChatLuong by nsxMa or nsxTen
	 * 
	 * @param nsx<br>
	 *            value need filter
	 * @return
	 */
	List<ChatLuong> getChatLuong(ChatLuong chatLuong);
	
	/**
	 * Get ChatLuong by nsxMa
	 * 
	 * @param nsxMa<br>
	 *            nsxMa of ChatLuong need to find
	 * @return
	 */
	ChatLuong getChatLuongById(String chatLuongMa);
}
