/**
 * 
 */
package model;

/**
 * @author quoioln
 *
 */
public class DBConnection {
	private String user;
	private String password;
//	private String host;
//	private int port;
//	private String database;
	/*
	 * Contructor isn't param
	 */
	public DBConnection() {
		this.user = "user1";
		this.password = "user1";
//		this.port = 3306;
//		this.database = "vattu";
//		this.host = "localhost";
	}
	
//
//	public String getHost() {
//		return host;
//	}
//
//
//	public void setHost(String host) {
//		this.host = host;
//	}
//

	/**
	 * @param user
	 * @param password
	 * @param port
	 * @param database
	 */
	public DBConnection(String user, String password) {
		this.user = user;
		this.password = password;
//		this.port = port;
//		this.database = database;
//		this.host = host;
	}
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public int getPort() {
//		return port;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
//	public String getDatabase() {
//		return database;
//	}
//
//	public void setDatabase(String database) {
//		this.database = database;
//	}
}
