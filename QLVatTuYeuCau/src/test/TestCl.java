package test;

import model.ChatLuong;
import util.JSonUtil;
import dao.ChatLuongDAO;

public class TestCl {

	public static void main(String[] args) {
		String cl1 ="001";
		String cl2 ="002";
		if(new ChatLuongDAO().getChatLuong(cl1)==null)
		{
			new ChatLuongDAO().addChatLuong(new ChatLuong(cl1,"CL1",0));
			
			System.out.println("success Cl1");
		}
		else
			System.out.println("fail Cl1");
		if(new ChatLuongDAO().getChatLuong(cl2)==null)
		{
			new ChatLuongDAO().addChatLuong(new ChatLuong(cl2,"CL2",0));
			
			System.out.println("success Cl2");
		}
		else
			System.out.println("fail Cl2");
	}

}
