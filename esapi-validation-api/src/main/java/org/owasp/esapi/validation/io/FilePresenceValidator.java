package org.owasp.esapi.validation.io;

import java.io.File;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class FilePresenceValidator implements Validator<File> {

	public ValidationResponse validate(File data) {
		ValidationResponse response = ValidationResponse.OK;
		if (!data.exists()) {
			response = new ValidationResponse(ValidationStatus.FAIL, "File at location does not exist.");
		}
		return response;
	}

}
