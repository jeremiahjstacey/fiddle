package org.owasp.esapi.validation.io;

import java.io.File;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.Validator;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class IsFileValidator implements Validator<File> {
	public ValidationResponse validate(File data) {
		ValidationResponse response = ValidationResponse.OK;
		if (!data.isFile()) {
			response = new ValidationResponse(ValidationStatus.FAIL, "File specified is not a file reference.");
		}
		return response;
	}
}
