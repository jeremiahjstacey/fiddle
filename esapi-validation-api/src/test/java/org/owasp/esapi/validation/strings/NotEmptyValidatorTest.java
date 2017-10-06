package org.owasp.esapi.validation.strings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class NotEmptyValidatorTest {
	@Rule
	public ExpectedException exEx = ExpectedException.none();

	private NotEmptyValidator testInstance;

	@Before
	public void setup() {
		testInstance = new NotEmptyValidator();
	}
	
	@Test
	public void testEmptyReturnsFailure() {
		String empty = "";
		ValidationResponse response = testInstance.validate(empty);
		Assert.assertFalse(response.isValid());
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());	
	}

	@Test
	public void testStringWithContentReturnsSuccess() {
		String notEmpty = "a";
		ValidationResponse response = testInstance.validate(notEmpty);
		Assert.assertTrue(response.isValid());
		Assert.assertEquals(ValidationStatus.PASS, response.getResponseStatus());	
	}

	@Test
	public void testNullThrowsNPE() {
		exEx.expect(NullPointerException.class);
		testInstance.validate(null);	
	}
}
