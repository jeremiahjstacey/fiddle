package org.owasp.esapi.validation;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;



public class ValidationResponseTest {
	private static final String TEST_MSG = "This is a Test";
	@Rule
	public ExpectedException exEx = ExpectedException.none();
	@Test
	public void testStatusMessageCtr() {
		ValidationResponse response = new ValidationResponse(ValidationStatus.PASS, TEST_MSG);
		Assert.assertEquals(ValidationStatus.PASS, response.getResponseStatus());
		Assert.assertTrue(response.isValid());
		Assert.assertTrue(response.getResponseStatus().isValid());
		Assert.assertEquals(TEST_MSG, response.getResponseDetail());
		Assert.assertNull(response.getException()); 
	}
	
	@Test
	public void testStatusCtr() {
		ValidationResponse response = new ValidationResponse(ValidationStatus.FAIL);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals("", response.getResponseDetail());
		Assert.assertNull(response.getException());
	}
	
	@Test
	public void testExceptionCtr() {
		Exception ex = new Exception(TEST_MSG);
		ValidationResponse response = new ValidationResponse(ex);
		Assert.assertEquals(ValidationStatus.ERROR, response.getResponseStatus());
		Assert.assertFalse(response.isValid());
		Assert.assertFalse(response.getResponseStatus().isValid());
		Assert.assertEquals(TEST_MSG, response.getResponseDetail());
		Assert.assertEquals(ex, response.getException()); 
	}
	
	@Test
	public void testValidationResponseNullStatus() {
		exEx.expect(IllegalArgumentException.class);
		exEx.expectMessage("null ValidationStatus");
		new ValidationResponse(null, null);
	}
}
