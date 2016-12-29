/**
 * 
 */
package vn.com.freesoft.docmanagement.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author quoioln
 *
 */
@Getter
@Setter
public class DBConnection {
	private String user;
	private String password;

	/**
	 * Instantiates a new DB connection.
	 */
	public DBConnection() {
		this.user = "user1";
		this.password = "user1";
	}

	/**
	 * @param user
	 * @param password
	 * @param port
	 * @param database
	 */
	public DBConnection(String user, String password) {
		this.user = user;
		this.password = password;
	}

}
