package org.owasp.esapi.validation.strings;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class NotEmptyValidator implements Validator<String> {

	public ValidationResponse validate(String data) {
		return data.isEmpty() ? new ValidationResponse(ValidationStatus.FAIL, "Provided String has no content") : ValidationResponse.OK;
	}

}
