package vn.co.evn.materialmanagement.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class Utils.
 */
public class Utils {
	
	/** The Constant mapper. */
    private static final ObjectMapper mapper = new ObjectMapper();

	static {
        mapper.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }
	
	/**
     * Read json from file.
     *
     * @param filePath String
     * @param typeReference the type reference
     * @return Object
     */
    public static Object getObjectFromJson(String jsonData, TypeReference<?> typeReference) {
        // get data from file
        Object object = null;
        try {
            // read json to object
            object = mapper.readValue(jsonData, typeReference);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return object;
    }
    
    /**
     * Read json from file.
     *
     * @param filePath String
     * @param typeReference the type reference
     * @return Object
     */
    public static Object readJsonFile(String filePath, TypeReference<?> typeReference) {
        // get data from file
        Object object = null;
        byte[] jsonData;
        try {
            jsonData = Files.readAllBytes(Paths.get(filePath));
            // read json to object
            object = mapper.readValue(jsonData, typeReference);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return object;
    }
}
