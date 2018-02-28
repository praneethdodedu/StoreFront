package com.storefront.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ObjectJSONMapper {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static JsonFactory jsonFactory = new JsonFactory();

	/**
	 * 
	 * @param jsonAsString
	 * @param pojoClass
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public <T> Object fromJson(String jsonAsString, Class<T> pojoClass) throws IOException {
		return objectMapper.readValue(jsonAsString, pojoClass);
	}

	/**
	 * 
	 * @param elements
	 * @param prettyPrint
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Map<String, String> elements, boolean prettyPrint) throws IOException {
		objectMapper.getSerializationConfig();
		StringWriter writer = new StringWriter();
		@SuppressWarnings("deprecation")
		JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
		if (prettyPrint) {
			jsonGenerator.useDefaultPrettyPrinter();
		}
		objectMapper.writeValue(jsonGenerator, elements);
		return writer.toString();
	}

	/**
	 * 
	 * @param pojo
	 * @param prettyPrint
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object pojo, boolean prettyPrint) throws IOException {
		StringWriter writer = new StringWriter();
		@SuppressWarnings("deprecation")
		JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(writer);
		if (prettyPrint) {
			jsonGenerator.useDefaultPrettyPrinter();
		}
		objectMapper.writeValue(jsonGenerator, pojo);
		return writer.toString();

	}

	/**
	 * 
	 * @param pojo
	 * @param fw
	 * @param prettyPrint
	 * @throws Exception
	 */
	public static void toJson(Object pojo, FileWriter writer, boolean prettyPrint) throws IOException {
		@SuppressWarnings("deprecation")
		JsonGenerator jg = jsonFactory.createJsonGenerator(writer);
		if (prettyPrint) {
			jg.useDefaultPrettyPrinter();
		}
		objectMapper.writeValue(jg, pojo);

	}

}
