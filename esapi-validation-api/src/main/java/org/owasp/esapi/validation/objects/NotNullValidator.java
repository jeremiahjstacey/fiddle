package org.owasp.esapi.validation.objects;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class NotNullValidator<T extends Object> implements Validator<T> {
	@Override
	public ValidationResponse validate(T data) {
		ValidationResponse response = ValidationResponse.OK;
		
		if (data == null) {
			response = new ValidationResponse(ValidationStatus.FAIL, "Data reference is null.");
		}
		return response;
	}
}
