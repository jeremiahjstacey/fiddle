package org.owasp.esapi.validation.strings;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;



public class StringPatternValidatorTest {

	StringPatternValidator expectMatch;
	StringPatternValidator expectNonMatch;
	@Before
	public void setup() {
		Pattern any = Pattern.compile(".+");
		expectMatch = new StringPatternValidator(any, true);
		expectNonMatch = new StringPatternValidator(any, false);
	}
	
	@Test
	public void  testPatternMatch() {
		String testString = "This is a test";
		ValidationResponse response = expectMatch.validate(testString);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	
	@Test
	public void  testPatternFailedMatch() {
		String testString = "";
		ValidationResponse response = expectMatch.validate(testString);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertTrue(response.getResponseDetail().contains("did not match"));
	}
	
	@Test
	public void  testPatternNonMatch() {
		String testString = "";
		ValidationResponse response = expectNonMatch.validate(testString);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	
	@Test
	public void  testPatternFailedNonMatch() {
		String testString = "This is a test";
		ValidationResponse response = expectNonMatch.validate(testString);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertTrue(response.getResponseDetail().contains("matched pattern"));
	}
}
