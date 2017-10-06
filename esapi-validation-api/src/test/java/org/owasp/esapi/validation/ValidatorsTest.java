package org.owasp.esapi.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;

@SuppressWarnings("unchecked")
public class ValidatorsTest {
	@Test
	public void testValidatorsAndWithFullPass() {
		Object testData = new Object();
		int howMany = 15;

		List<Validator<Object>> validators = new ArrayList<>();
		for (int count = 0; count < howMany; count++) {
			Validator<Object> mock = Mockito.mock(Validator.class);
			Mockito.when(mock.validate(Matchers.any())).thenReturn(ValidationResponse.OK);
			validators.add(mock);
		}
		Validator<Object> checkAll = Validators.and(validators);

		ValidationResponse response = checkAll.validate(testData);

		Assert.assertTrue(response.isValid());

		for (Validator<Object> mock : validators) {
			Mockito.verify(mock, Mockito.times(1)).validate(testData);
		}
	}

	@Test
	public void testValidatorsAndWithFailure() {
		Object testData = new Object();
		int howMany = 15;

		List<Validator<Object>> validators = new ArrayList<>();
		for (int count = 0; count < howMany; count++) {
			Validator<Object> mock = Mockito.mock(Validator.class);
			Mockito.when(mock.validate(Matchers.any())).thenReturn(ValidationResponse.OK);
			validators.add(mock);
		}

		ValidationResponse failResponse = new ValidationResponse(ValidationStatus.FAIL, "Forced Test Failure");
		Validator<Object> failMock = Mockito.mock(Validator.class);
		Mockito.when(failMock.validate(Matchers.any())).thenReturn(failResponse);

		// Randomly choosing to insert it at index 9
		validators.add(9, failMock);

		Validator<Object> checkAll = Validators.and(validators);
		// Should receive the failure status back
		ValidationResponse response = checkAll.validate(testData);
		Assert.assertEquals(failResponse, response);

		boolean foundFail = false;
		for (Validator<Object> mock : validators) {
			// All mocks up to, and including the failure get called. Remaining
			// references are not called.
			Mockito.verify(mock, Mockito.times(foundFail ? 0 : 1)).validate(testData);
			if (mock.equals(failMock)) {
				foundFail = true;
			}
		}
	}

	@Test
	public void testValidatorsOrWithFullFailure() {
		Object testData = new Object();
		int howMany = 5;

		List<Validator<Object>> validators = new ArrayList<>();
		for (int count = 0; count < howMany; count++) {
			ValidationResponse failResponse = new ValidationResponse(ValidationStatus.FAIL,
					"Forced Test Failure " + count);
			Validator<Object> mock = Mockito.mock(Validator.class);
			Mockito.when(mock.validate(Matchers.any())).thenReturn(failResponse);
			validators.add(mock);
		}
		Validator<Object> checkAny = Validators.or(validators);

		ValidationResponse response = checkAny.validate(testData);

		Assert.assertFalse(response.isValid());

		for (int count = 0; count < howMany; count++) {
			Assert.assertTrue(response.getResponseDetail().contains("Forced Test Failure " + count));
		}

		for (Validator<Object> mock : validators) {
			Mockito.verify(mock, Mockito.times(1)).validate(testData);
		}
	}

	@Test
	public void testValidatorsAndWithPass() {
		Object testData = new Object();
		int howMany = 15;

		List<Validator<Object>> validators = new ArrayList<>();
		for (int count = 0; count < howMany; count++) {
			ValidationResponse failResponse = new ValidationResponse(ValidationStatus.FAIL,
					"Forced Test Failure " + count);
			Validator<Object> mock = Mockito.mock(Validator.class);
			Mockito.when(mock.validate(Matchers.any())).thenReturn(failResponse);
			validators.add(mock);
		}

		Validator<Object> passMock = Mockito.mock(Validator.class);
		Mockito.when(passMock.validate(Matchers.any())).thenReturn(ValidationResponse.OK);

		// Randomly choosing to insert it at index 9
		validators.add(9, passMock);

		Validator<Object> checkAny = Validators.or(validators);
		// Should receive the failure status back
		ValidationResponse response = checkAny.validate(testData);
		Assert.assertEquals(ValidationResponse.OK, response);

		boolean foundPass = false;
		for (Validator<Object> mock : validators) {
			// All mocks up to, and including the pass get called. Remaining
			// references are not called.
			Mockito.verify(mock, Mockito.times(foundPass ? 0 : 1)).validate(testData);
			if (mock.equals(passMock)) {
				foundPass = true;
			}
		}
	}

	@Test
	public void testCompoundAndValidation() {
		Validator<Object> pass = Mockito.mock(Validator.class);
		Mockito.when(pass.validate(Matchers.any())).thenReturn(ValidationResponse.OK);

		ValidationResponse failResponse = new ValidationResponse(ValidationStatus.FAIL, "Forced Test Failure ");
		Validator<Object> failure = Mockito.mock(Validator.class);
		Mockito.when(failure.validate(Matchers.any())).thenReturn(failResponse);

		Object data = new Object();

		// OR pass and fail together.
		Validator<Object> passOrFail = Validators.or(Arrays.asList(failure, pass));
		// AND Pass and passORFail
		Validator<Object> fullCheck = Validators.and(Arrays.asList(pass, passOrFail));

		// Expect PASS
		Assert.assertTrue(fullCheck.validate(data).isValid());

		Mockito.verify(pass, Mockito.times(2)).validate(data);
		Mockito.verify(failure, Mockito.times(1)).validate(data);
	}

	@Test
	public void testCompoundOrValidation() {
		Validator<Object> pass = Mockito.mock(Validator.class);
		Mockito.when(pass.validate(Matchers.any())).thenReturn(ValidationResponse.OK);

		ValidationResponse failResponse = new ValidationResponse(ValidationStatus.FAIL, "Forced Test Failure ");
		Validator<Object> failure = Mockito.mock(Validator.class);
		Mockito.when(failure.validate(Matchers.any())).thenReturn(failResponse);

		Object data = new Object();

		// OR pass and fail together.
		Validator<Object> passOrFail = Validators.or(Arrays.asList(failure, pass));
		Validator<Object> fail1 = Validators.or(Arrays.asList(failure, failure, failure));

		// AND Pass and passORFail
		Validator<Object> fullCheck = Validators.or(Arrays.asList(fail1, passOrFail));

		// Expect PASS
		Assert.assertTrue(fullCheck.validate(data).isValid());

		Mockito.verify(pass, Mockito.times(1)).validate(data);
		Mockito.verify(failure, Mockito.times(4)).validate(data);
	}
}
