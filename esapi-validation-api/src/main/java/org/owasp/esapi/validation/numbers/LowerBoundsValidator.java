package org.owasp.esapi.validation.numbers;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class LowerBoundsValidator<T extends Number> implements Validator<T> {

	private final double lowerBoundary;
	private final boolean inclusiveLower;
	
	public LowerBoundsValidator(T min, boolean inclusiveMin) {
		if (min == null) {
			throw new IllegalArgumentException("Lower Bound value must be specified");
		} 
		lowerBoundary = min.doubleValue();
		inclusiveLower = inclusiveMin;
	}
	
	public ValidationResponse validate(T data) {
		ValidationResponse response = ValidationResponse.OK;
		double input = data.doubleValue();
		boolean isValid;
		if (inclusiveLower) {
			isValid = input >= lowerBoundary;
		} else {
			isValid = input > lowerBoundary;
		}
		if (!isValid) {
			response = new ValidationResponse(ValidationStatus.FAIL, "Minimum value exceeded.");
		} 
		
		return response;
	}
}
