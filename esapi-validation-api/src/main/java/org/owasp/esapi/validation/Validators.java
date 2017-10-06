package org.owasp.esapi.validation;

import java.util.List;

import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

/**
 * Utility class which contains helpful methods for working with the Valdiation
 * api.
 *
 */
public final class Validators {
	/** Private Utility Constructor. */
	private Validators() {
		/* NO OP Util Ctr. */ }

	/**
	 * Creates a single validator reference which iterates the specified List
	 * and validates that the data element is valid to each. <br/>
	 * In the case of a failure, the ValidationResponse of the faulted delegate
	 * will be returned to the caller.
	 * 
	 * @param validators
	 *            List of Validator references to check.
	 * @return Validator reference.
	 */
	public static <R> Validator<R> and(final List<Validator<R>> validators) {
		return new Validator<R>() {

			public ValidationResponse validate(R data) {
				ValidationResponse response = ValidationResponse.OK;
				for (Validator<R> validator : validators) {
					response = validator.validate(data);
					if (!response.isValid()) {
						break;
					}
				}
				return response;
			}
		};
	}

	public static <R> Validator<R> or(final List<Validator<R>> validators) {
		return new Validator<R>() {
			public ValidationResponse validate(R data) {
				StringBuilder msgBuffer = new StringBuilder();
				ValidationResponse response = ValidationResponse.OK;
				for (Validator<R> validator : validators) {
					response = validator.validate(data);
					if (response.isValid()) {
						break;
					}
					msgBuffer.append(response.getResponseStatus());
					msgBuffer.append(" : ");
					msgBuffer.append(response.getResponseDetail());
					msgBuffer.append(System.lineSeparator());
				}

				if (!response.isValid()) {
					response = new ValidationResponse(ValidationStatus.FAIL, msgBuffer.toString());
				}

				return response;
			}
		};
	}
}
