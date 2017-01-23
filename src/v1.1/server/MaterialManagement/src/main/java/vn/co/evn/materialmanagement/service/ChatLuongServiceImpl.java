/**
 * 
 */
package vn.co.evn.materialmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import vn.co.evn.materialmanagement.entity.ChatLuong;
import vn.co.evn.materialmanagement.utils.Utils;

/**
 * @author quoip
 *
 */
@SuppressWarnings("unchecked")
@Service
public class ChatLuongServiceImpl implements ChatLuongService {

	private static List<ChatLuong> chatLuongList = new ArrayList<>();
	
	static {
		chatLuongList = (List<ChatLuong>) Utils.readJsonFile("D:/opt/materialmanagement/dataStub/ChatLuongStub.json", new TypeReference<List<ChatLuong>>() {});
	}

	@Override
	public List<ChatLuong> getChatLuong(ChatLuong chatLuong) {
		return chatLuongList;
	}

	@Override
	public ChatLuong getChatLuongById(String chatLuongMa) {
		ChatLuong chatLuong = null;
		if (chatLuongList != null || !chatLuongList.isEmpty()) {
			chatLuong = chatLuongList.get(0);
		}
		return chatLuong;
	}

}
