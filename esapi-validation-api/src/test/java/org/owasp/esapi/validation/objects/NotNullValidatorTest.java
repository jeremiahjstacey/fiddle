package org.owasp.esapi.validation.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class NotNullValidatorTest {

	private NotNullValidator<Object> testInstance;
	
	@Before
	public void setup() {
		testInstance = new NotNullValidator<>();
	}
	
	@Test
	public void testNullInvalid() {
		ValidationResponse response = testInstance.validate(null);
		Assert.assertFalse(response.isValid());
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());		
	}
	
	@Test
	public void testNotNullIsValid() {
		ValidationResponse response = testInstance.validate(new Object());
		Assert.assertTrue(response.isValid());
		Assert.assertEquals(ValidationStatus.PASS, response.getResponseStatus());
	}
}
