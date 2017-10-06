package org.owasp.esapi.validation.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class NullValidatorTest {

	private NullValidator<Object> testInstance;
	
	@Before
	public void setup() {
		testInstance = new NullValidator<>();
	}
	
	@Test
	public void testNullIsValid() {
		ValidationResponse response = testInstance.validate(null);
		Assert.assertTrue(response.isValid());
		Assert.assertEquals(ValidationStatus.PASS, response.getResponseStatus());
	}
	
	@Test
	public void testNotNullInvalid() {
		ValidationResponse response = testInstance.validate(new Object());
		Assert.assertFalse(response.isValid());
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
	}
}
