/**
 * 
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author quoioln
 *
 */
public class TestReadFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {
		byte[] b = new byte[600000];
		File file = new File("~/study/linux command-1.docx");
		FileInputStream f;
		try {
			f = new FileInputStream(file);
			f.read(b);
			System.out.println(b);
			System.out.println("Da mo file");
		} catch (IOException e){
		}
	
	}

}
