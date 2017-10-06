package org.owasp.esapi.validation;

@FunctionalInterface
public interface Validator <T> {
	/**
	 * Performs a context sensitive validation on the specified piece of data.
	 * @param data Reference to validate.
	 * @return {@link ValidationResponse} defining the result of the process.
	 */
	ValidationResponse validate(T data);

}
