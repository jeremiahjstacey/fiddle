package org.owasp.esapi.validation.numbers;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class UpperBoundsValidator<T extends Number> implements Validator<T> {

	private final double upperBoundary;
	private final boolean inclusiveUpper;
	
	public UpperBoundsValidator(T max,boolean inclusiveMax) {
		if (max == null) {
			throw new IllegalArgumentException("Upper Bound value must be specified");
		} 

		upperBoundary = max.doubleValue();
		inclusiveUpper = inclusiveMax;
	}
	
	public ValidationResponse validate(T data) {
		ValidationResponse response = ValidationResponse.OK;
		double input = data.doubleValue();
		boolean isValid;
		if (inclusiveUpper) {
			isValid = input <= upperBoundary;
		} else {
			isValid = input < upperBoundary;
		}
		if (!isValid) {
			response = new ValidationResponse(ValidationStatus.FAIL, "Maximum value exceeded.");
		} 
		
		return response;
	}

}
