package org.owasp.esapi.validation.strings;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.numbers.RangeValidator;

public class StringLengthValidator implements Validator<String>{

	private final RangeValidator<Integer> sizeCheck;
	public StringLengthValidator(int minSize, int maxSize, boolean inclusiveMin,boolean inclusiveMax) {
		if (minSize < 0) {
			throw new IllegalArgumentException("Minimum required String lengths cannot be set less than zero.");
		}
		sizeCheck = new RangeValidator<>(minSize, maxSize, inclusiveMin, inclusiveMax);
	}
	
	@Override
	public ValidationResponse validate(String data) {
		return sizeCheck.validate(data.length());
	}

}
