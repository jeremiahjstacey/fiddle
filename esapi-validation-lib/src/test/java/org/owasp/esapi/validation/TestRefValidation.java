package org.owasp.esapi.validation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.owasp.esapi.validation.objects.NotNullValidator;
import org.owasp.esapi.validation.strings.NotEmptyValidator;

public class TestRefValidation implements ValidationConfiguration {
	private final String TEST_REF_AS_OBJ = "TEST_REF_OBJ";
	
	private final Map<String, Validator<?>> validationMap = new HashMap<>();
	
	
	/* package*/ TestRefValidation() {
		ValidationDataStage<TestRef, String> getVal1 = new ValidationDataStage<TestRef, String>() {
			@Override
			public String prepareData(TestRef input) {
				return input.value;
			}
		};
		
		Validator<TestRef> valueNotNull = Validators.chain(new NotNullValidator<>(), getVal1);
		Validator<TestRef> valueNotEmpty = Validators.chain(new NotEmptyValidator(), getVal1);
		Validator<TestRef> val1NotNullOrEmpty = Validators.and(Arrays.asList(valueNotNull, valueNotEmpty));
		
		Validator<TestRef> refNotNullAndValid = Validators.and(Arrays.asList(new NotNullValidator<>(), val1NotNullOrEmpty));
		
		validationMap.put(TEST_REF_AS_OBJ, refNotNullAndValid);
	}
	
	@Override
	public Validator<?> getValidator(String id) {
		return validationMap.get(id);
	}

}
