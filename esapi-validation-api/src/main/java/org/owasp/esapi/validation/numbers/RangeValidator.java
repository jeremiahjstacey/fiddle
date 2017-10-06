package org.owasp.esapi.validation.numbers;

import java.util.Arrays;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.Validators;

public class RangeValidator<T extends Number> implements Validator<T> {

	private final UpperBoundsValidator<T> upperValidator;
	private final LowerBoundsValidator<T> lowerValidator;
	
	public RangeValidator(T min, T max, boolean inclusiveMin,boolean inclusiveMax) {
		upperValidator = new UpperBoundsValidator<>(max, inclusiveMax);
		lowerValidator = new LowerBoundsValidator<>(min, inclusiveMin);
	}
	
	public ValidationResponse validate(T data) {
		return Validators.<T>and(Arrays.asList(lowerValidator, upperValidator)).validate(data);
	}
}
