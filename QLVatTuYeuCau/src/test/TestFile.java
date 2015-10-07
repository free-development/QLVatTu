/**
 * 
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dao.FileDAO;

/**
 * @author quoioln
 *
 */
public class TestFile {
	public static void main(String[] args) {
		//System.out.println(new FileDAO().getByCongVanId(1).getDiaChi());
		byte[] b = new byte[600000];
		try {
			//File file = new File("../change-pass.jsp");
			//file.ge
			//FileInputStream fi = new FileInputStream(file);
			//fi.read(b);
		FileOutputStream file = new FileOutputStream("test");
			//System.out.println();
		file.write(new byte[]{1,2});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}

}
