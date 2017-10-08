package org.owasp.esapi.validation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.owasp.esapi.validation.objects.NotNullValidator;
import org.owasp.esapi.validation.strings.NotEmptyValidator;
import org.owasp.esapi.validation.strings.StringPatternValidator;

public class StringValidation implements ValidationConfiguration {
	private final String STR_NOT_EMPTY = "STR_NOT_EMPTY";
	private final String STR_MATCH_ANY = "STR_Match_ANY";
	
	
private final Map<String, Validator<?>> validationMap = new HashMap<>();
	
	
	/* package*/ StringValidation() {
		Validator<String> notEmpty = Validators.and(Arrays.<Validator<String>>asList(new NotNullValidator(), new NotEmptyValidator()));
		Validator<String> matchAny = Validators.and(Arrays.<Validator<String>>asList(new NotNullValidator(), new StringPatternValidator(Pattern.compile(".+"), true)));
		
		validationMap.put(STR_NOT_EMPTY, notEmpty);
		validationMap.put(STR_MATCH_ANY, matchAny);
	}
	
	@Override
	public Validator<?> getValidator(String id) {
		return validationMap.get(id);
	}
}
