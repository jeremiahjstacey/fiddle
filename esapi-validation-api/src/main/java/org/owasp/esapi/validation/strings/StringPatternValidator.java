package org.owasp.esapi.validation.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class StringPatternValidator implements Validator<String> {

	private final Pattern pattern;

	private final boolean shouldMatch;

	public StringPatternValidator(Pattern pattern, boolean shouldMatch) {
		this.pattern = pattern;
		this.shouldMatch = shouldMatch;
	}

	@Override
	public ValidationResponse validate(String data) {
		ValidationResponse response = ValidationResponse.OK;
		Matcher matcher = pattern.matcher(data);

		if (matcher.matches() ^ shouldMatch) {
			String message = shouldMatch ? "Input did not match expected Pattern." : "Input matched pattern";
			response = new ValidationResponse(ValidationStatus.FAIL, message);
		}
		return response;
	}
}
