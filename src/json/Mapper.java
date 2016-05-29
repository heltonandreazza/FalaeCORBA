package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Mapper {
	private static ObjectMapper instance = null;

	protected Mapper() {
		// Exists only to defeat instantiation.
	}

	public static ObjectMapper getInstance() {
		if (instance == null) {
			instance = new ObjectMapper();
			
			configInstance();
		}
		return instance;
	}

	private static void configInstance() {
		// SerializationFeature for changing how JSON is written

		// to enable standard indentation ("pretty-printing"):
		instance.enable(SerializationFeature.INDENT_OUTPUT);
		// to allow serialization of "empty" POJOs (no properties to
		// serialize)
		// (without this setting, an exception is thrown in those cases)
		instance.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// to write java.util.Date, Calendar as number (timestamp):
		instance.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// DeserializationFeature for changing how JSON is read as POJOs:

		// to prevent exception when encountering unknown property:
		instance.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// to allow coercion of JSON empty String ("") to null Object value:
		instance.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		// JsonParser.Feature for configuring parsing settings:

		// to allow C/C++ style comments in JSON (non-standard, disabled by default)
		// (note: with Jackson 2.5, there is also `mapper.enable(feature)` / `mapper.disable(feature)`)
		instance.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		// to allow (non-standard) unquoted field names in JSON:
		instance.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// to allow use of apostrophes (single quotes), non standard
		instance.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		// JsonGenerator.Feature for configuring low-level JSON generation:

		// to force escaping of non-ASCII characters:
		instance.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);		
	}
}
