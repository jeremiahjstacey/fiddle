package org.owasp.esapi.validation;

import java.util.HashMap;
import java.util.Map;

import org.owasp.esapi.validation.objects.NotNullValidator;
import org.owasp.esapi.validation.objects.NullValidator;

public class GeneralObjectValidation implements ValidationConfiguration {
	private final String NOT_NULL_KEY = "NOT_NULL";
	private final String IS_NULL_KEY = "IS_NULL";
	
	private final Map<String, Validator<Object>> validationMap = new HashMap<>();
	
	
	/* package*/ GeneralObjectValidation() {
		validationMap.put(IS_NULL_KEY, new NullValidator<>());
		validationMap.put(NOT_NULL_KEY, new NotNullValidator<>());
	}
	
	@Override
	public Validator<?> getValidator(String id) {
		return validationMap.get(id);
	}

}
