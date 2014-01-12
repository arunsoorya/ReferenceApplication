/*@(#)AppConfigParser.java
 */
package parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataParser<T> {

	/**
	 * 
	 * <p>This is the method for converting Response string to Object.</p>
	 * @param className - object class name
	 * @param jsonString
	 * @return
	 */
	public T convertToObject(Class<? extends T> className, String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, className);
	}

	/**
	 * 
	 * <p>This is the method for convert to json format that need to sent to the serverF.</p>
	 * @param object
	 * @return
	 */
	public String convertToJson(Class<? extends T> className, T object) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//		Gson gson = new Gson();
		return gson.toJson(object, className);
	}
}
