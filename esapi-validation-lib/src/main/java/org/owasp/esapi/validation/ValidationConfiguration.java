package org.owasp.esapi.validation;

public interface ValidationConfiguration {

	Validator<?> getValidator(String id);
}
