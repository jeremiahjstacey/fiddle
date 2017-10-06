package org.owasp.esapi.validation.strings;

import java.text.Format;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

public class StringFormatValidatorTest {
	private static String TEST_STRING = "This value does not matter.";
	private StringFormatValidator testInstance;
	
	private Format format;
	
	@Before
	public void setUp() {
		format = Mockito.spy(Format.class);
		testInstance = new StringFormatValidator(format);
	}
	
	@Test
	public void testValidFormat() throws ParseException {
		Mockito.doReturn("").when(format).parseObject(TEST_STRING);
		ValidationResponse response = testInstance.validate(TEST_STRING);
		Assert.assertEquals(ValidationResponse.OK, response);
	}
	
	@Test
	public void testParseException() throws ParseException {
		ParseException pe = new ParseException("Test Exception", 0);
		Mockito.doThrow(pe).when(format).parseObject(TEST_STRING);
		ValidationResponse response = testInstance.validate(TEST_STRING);
		Assert.assertEquals(ValidationStatus.ERROR, response.getResponseStatus());
		Assert.assertEquals("Test Exception", response.getResponseDetail());
		Assert.assertEquals(pe, response.getException());
	}
}
