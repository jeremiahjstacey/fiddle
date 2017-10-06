package org.owasp.esapi.validation.strings;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.owasp.esapi.validation.numbers.RangeValidator;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest (StringLengthValidator.class)
public class StringLengthValidatorTest {
	private static String TEST_STRING = "This value does not matter.";
	
	@Rule
	public ExpectedException exEx = ExpectedException.none();
	
	/**
	 * This test bypasses the need to re-verify all of the functionality already tested in the RangeValidator delegate of the StringLengthValidator.
	 * <p>
	 * Using the power mock tooling, this test injects a controlled delegate RangeValidator into a test instance of a StringLengthValidator and ensures that
	 * when the StringLengthValidator is invoked that the delegate recieves a corresponding invocation with the expected data set.
	 * <p>
	 * All Logical checks are then maintained exclusively in the delegate class.
	 * 
	 * @throws Exception thrown from mocking API.
	 */
	@Test
	public void testInvocationWorkflow() throws Exception {		
		Integer minLength = 10;
		Integer maxLength = 25;
		boolean inclusiveLimits=true;
		//Spy matches our expected behavior exactly.
		RangeValidator<Integer> spyRangeValidator = new RangeValidator<Integer>(minLength, maxLength, inclusiveLimits, inclusiveLimits);
		
		spyRangeValidator = Mockito.spy(spyRangeValidator);
		PowerMockito.whenNew(RangeValidator.class).withArguments(minLength, maxLength, inclusiveLimits, inclusiveLimits).thenReturn(spyRangeValidator);
		
		StringLengthValidator testinstance = new StringLengthValidator(minLength, maxLength, inclusiveLimits, inclusiveLimits);
		
		Mockito.doCallRealMethod().when(spyRangeValidator).validate(Matchers.anyInt());
		
		//Since we own the delegate, the return from this doesn't matter.  We're NOT testing the delegate, we're testing that the delegate gets invoked with the expected data
		testinstance.validate(TEST_STRING);
		
		Mockito.verify(spyRangeValidator, Mockito.times(1)).validate(TEST_STRING.length());
		
	}
	
	@Test
	public void testIllegalArgumentOnNegativeMinLength() {
		exEx.expect(IllegalArgumentException.class);
		exEx.expectMessage("Minimum required String lengths cannot be set less than zero.");
		new StringLengthValidator(-1, 20, true, true);
	}
}
