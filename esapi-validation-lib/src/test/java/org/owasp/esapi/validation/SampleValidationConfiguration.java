package org.owasp.esapi.validation;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.owasp.esapi.validation.objects.NotNullValidator;
import org.owasp.esapi.validation.objects.NullValidator;
import org.owasp.esapi.validation.strings.NotEmptyValidator;
import org.owasp.esapi.validation.strings.StringPatternValidator;

public class SampleValidationConfiguration implements ValidationConfiguration {
	private final NotNullValidator<Object> notNull = new NotNullValidator<>();
	private final NullValidator<Object> isNull = new NullValidator<>();
	private final Validator<String> notEmpty = Validators.and(Arrays.<Validator<String>>asList(new NotNullValidator(), new NotEmptyValidator()));
	private final Validator<String> matchAny = Validators.and(Arrays.<Validator<String>>asList(new NotNullValidator(), new StringPatternValidator(Pattern.compile(".+"), true)));
	private final Validator<String> matchNone = Validators.and(Arrays.<Validator<String>>asList(new NotNullValidator(), new StringPatternValidator(Pattern.compile(".+"), false)));
	@Override
	public Validator<?> getValidator(String id) {
		switch (id) {
		case "NOT_NULL":
			return notNull;
		case "IS_NULL":
			return isNull;
		case "STR_NOT_EMPTY" :
			return notEmpty;
		case "STR_Match_ANY" :
			return matchAny;
		default: 
			throw new UnsupportedOperationException(id);
		}
	}

}
