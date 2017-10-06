package org.owasp.esapi.validation.strings;

import java.text.Format;
import java.text.ParseException;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;

public class StringFormatValidator implements Validator<String> {
	
	private final Format format;
	
	public StringFormatValidator(Format format) {
		this.format = format;
	}

	public ValidationResponse validate(String data) {
		ValidationResponse response = ValidationResponse.OK;
		
		try {
			format.parseObject(data);
		} catch (ParseException parseEx) {
			response = new ValidationResponse(parseEx);
		}
		
		return response;
	}

}
