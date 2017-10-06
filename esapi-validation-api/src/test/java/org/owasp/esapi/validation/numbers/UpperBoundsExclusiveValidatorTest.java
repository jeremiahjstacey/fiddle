package org.owasp.esapi.validation.numbers;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.owasp.esapi.validation.ValidationResponse;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

@RunWith(Parameterized.class)
public class UpperBoundsExclusiveValidatorTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Double[] { new Double(3.47474), new Double(78.6), new Double(.0006) });
		params.add(new Byte[] { new Byte((byte) 3.47474), new Byte((byte) 78.6), new Byte((byte) .0006) });
		params.add(new Long[] { new Long(3), new Long(78), new Long(1) });
		params.add(new Integer[] { new Integer(3), new Integer(78), new Integer(1) });
		params.add(new Short[] { new Short((short) 3), new Short((short) 78), new Short((short) 1) });
		return params;
	}

	private Number maxConfiguration;
	private Number invalidNumber;
	private Number validNumber;
	private UpperBoundsValidator<Number> testInstance;

	public UpperBoundsExclusiveValidatorTest(Number minValue, Number tooHigh, Number goodNumber) {
		maxConfiguration = minValue;
		invalidNumber = tooHigh;
		validNumber = goodNumber;
		testInstance = new UpperBoundsValidator<>(maxConfiguration, false);
	}

	@Test
	public void testExclusiveBoundaryLimit() {
		ValidationResponse response = testInstance.validate(maxConfiguration);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertEquals("Maximum value exceeded.", response.getResponseDetail());
	}

	@Test
	public void testExclusiveBoundaryTooHigh() {
		ValidationResponse response = testInstance.validate(invalidNumber);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertEquals("Maximum value exceeded.", response.getResponseDetail());
	}

	@Test
	public void testExclusiveBoundaryValid() {
		ValidationResponse response = testInstance.validate(validNumber);
		Assert.assertEquals(ValidationResponse.OK, response);
	}

}
