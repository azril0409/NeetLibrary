package sample.neetoffice.com.neetdaosample;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<E> {
	@JsonProperty("code")
	int code;
	@JsonProperty("message")
	String message;
	@JsonProperty("data")
	E data;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public E getData() {
		return data;
	}
}
