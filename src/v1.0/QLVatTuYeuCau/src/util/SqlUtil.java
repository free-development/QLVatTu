/**
 * 
 */
package util;

import java.util.HashMap;

/**
 * @author quoioln
 *
 */
public class SqlUtil {
	private String query;
	private boolean checkCondition;
	public SqlUtil() {
		this.query = "";
//		this.condition = "";
		this.checkCondition = false; 
	}
	public String createQuery(String table) {
		this.query = "from " +table;
		return this.query;
	}
	public String addCondition(String columnValue) {
		if (!this.checkCondition) {
			this.query += " where " + columnValue;
			this.checkCondition =  true;
		}
		else 
			this.query += " and " + columnValue;
		return this.query;
	}
	public String orderBy(HashMap<String, Boolean> columns) {
		
		StringBuilder orberBy = new StringBuilder("");
		for(String column : columns.keySet()) {
			orberBy.append(column);
			if (columns.get(column))
				orberBy.append(" DESC, ");
		}
		int length = orberBy.length();
		orberBy.delete(length - 2 , length);
		this.query += " order by " + orberBy.toString();
		
		
		return this.query;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
