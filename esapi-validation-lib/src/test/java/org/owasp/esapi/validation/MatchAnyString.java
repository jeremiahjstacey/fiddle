package org.owasp.esapi.validation;

import java.util.regex.Pattern;

import org.owasp.esapi.validation.strings.StringPatternValidator;

public class MatchAnyString extends StringPatternValidator {
	public MatchAnyString() {
		super(Pattern.compile(".+"), true);
	}
}
