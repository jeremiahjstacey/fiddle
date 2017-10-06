package org.owasp.esapi.validation.io;

import java.io.File;

import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.Validator;

public class FileExtensionValidator implements Validator<File> {
	
	private final String extension;
	private final boolean caseSensitive;
	
	public FileExtensionValidator(String extension, boolean caseSensitive) {
		this.extension = extension;
		this.caseSensitive = caseSensitive;
	}
	
	public ValidationResponse validate(File data) {
		ValidationResponse response = ValidationResponse.OK;
		String fName = data.getName();
		String checkType = extension;
		if (!caseSensitive) {
			fName = fName.toLowerCase();
			checkType = checkType.toLowerCase();
		}
		if (!fName.endsWith(checkType)) {
			response = new ValidationResponse(ValidationStatus.FAIL, "Specified file does not match the desired extension.");
		}
		return response;
	}
}
