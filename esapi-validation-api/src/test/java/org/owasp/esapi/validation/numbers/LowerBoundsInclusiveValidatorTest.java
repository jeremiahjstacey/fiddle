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
public class LowerBoundsInclusiveValidatorTest {

	@Parameters
	public static Collection<Object[]> parameters() {
		Collection<Object[]> params = new ArrayList<>();
		params.add(new Double[]{ new Double(3.47474),new Double(.0006),new Double(78.6)});
		params.add(new Byte[]{ new Byte((byte) 3.47474),new Byte((byte) .0006),new Byte((byte) 78.6)});
		params.add(new Long[]{ new Long(3),new Long(1),new Long(78)});
		params.add(new Integer[]{ new Integer(3),new Integer(1),new Integer(78)});
		params.add(new Short[]{ new Short((short) 3),new Short((short) 1),new Short((short) 78)});
		return params;
	}

	private Number minConfiguration;
	private Number invalidNumber;
	private Number validNumber;
	private LowerBoundsValidator<Number> testInstance;

	public LowerBoundsInclusiveValidatorTest (Number minValue, Number tooLow, Number goodNumber) {
		minConfiguration = minValue;
		invalidNumber = tooLow;
		validNumber = goodNumber;
		testInstance = new LowerBoundsValidator<>(minConfiguration, true);
	}


	@Test
	public void testInclusiveBoundaryLimit() {
		ValidationResponse response = testInstance.validate(minConfiguration);
		Assert.assertEquals(ValidationResponse.OK, response);
	}

	@Test
	public void testInclusiveBoundaryTooLow() {
		ValidationResponse response = testInstance.validate(invalidNumber);
		Assert.assertEquals(ValidationStatus.FAIL, response.getResponseStatus());
		Assert.assertEquals("Minimum value exceeded.", response.getResponseDetail());
	}

	@Test
	public void testInclusiveBoundaryValid() {
		ValidationResponse response = testInstance.validate(validNumber);
		Assert.assertEquals(ValidationResponse.OK, response);
	}

}
