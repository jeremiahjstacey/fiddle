package org.owasp.esapi.validation;

import org.owasp.esapi.validation.annotation.Check;

@Check(rule = "TEST_REF_OBJ")
public class TestRef {
	@Check(rule = "NOT_NULL")
	String value;

	@Check(rule = "IS_NULL")
	String value2;

	@Check(rule = "STR_NOT_EMPTY")
	String value3;

	@Check(rule = "STR_Match_ANY")
	String hasContent = "hello world";
}
