package vn.co.evn.materialmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.co.evn.materialmanagement.entity.ChatLuong;
import vn.co.evn.materialmanagement.service.ChatLuongService;

@RestController
@RequestMapping("/chatluong")
public class ChatLuongController {

	/** The noi san xuat service. */
	@Autowired
	private ChatLuongService chatLuongService;

	/**
	 * get ChatLuong
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ChatLuong>> getChatLuong() {
		List<ChatLuong> chatLuongList = chatLuongService.getChatLuong(null);
		if (chatLuongList == null || chatLuongList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(chatLuongList, HttpStatus.OK);
	}

	/**
	 * get ChatLuong
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ChatLuong> getChatLuongById(@PathVariable("id") String chatLuongMa) {
		List<ChatLuong> chatLuongList = chatLuongService.getChatLuong(null);
		System.out.println(chatLuongList);
		ChatLuong chatLuong = null;
		if (chatLuongList == null || chatLuongList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		chatLuong = chatLuongList.get(0);
		return new ResponseEntity<>(chatLuong, HttpStatus.OK);
	}
}
