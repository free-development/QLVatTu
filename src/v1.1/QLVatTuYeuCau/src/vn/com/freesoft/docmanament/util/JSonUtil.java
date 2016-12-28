package vn.com.freesoft.docmanament.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonUtil {
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String value = mapper.writeValueAsString(object);
			return value;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
