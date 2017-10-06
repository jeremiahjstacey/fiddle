package org.owasp.esapi.validation.numbers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UpperBoundsValidatorTest {
	@Rule
	public ExpectedException exEx = ExpectedException.none();
	
	
	@Test
	public void testNullMinValue() {
		exEx.expect(IllegalArgumentException.class);
		exEx.expectMessage("Upper Bound value must be specified");
		Number nullRef = null;
		new UpperBoundsValidator<Number>(nullRef, false);
	}
}
