package org.owasp.esapi.validation.numbers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LowerBoundsValidatorTest {
	@Rule
	public ExpectedException exEx = ExpectedException.none();
	
	
	@Test
	public void testNullMinValue() {
		exEx.expect(IllegalArgumentException.class);
		exEx.expectMessage("Lower Bound value must be specified");
		Number nullRef = null;
		new LowerBoundsValidator<Number>(nullRef, false);
	}
}
