package org.owasp.esapi.validation;

import java.util.ArrayList;
import java.util.Collection;

public class SampleValidationConfiguration implements ValidationConfiguration {
	
	private final Collection<ValidationConfiguration> delegates = new ArrayList<>();
	
	public SampleValidationConfiguration() {
		delegates.add(new StringValidation());
		delegates.add(new GeneralObjectValidation());
	}
	
	@Override
	public Validator<?> getValidator(String id) {
		Validator<?> rval = null;
		
		for (ValidationConfiguration delegate: delegates) {
			rval = delegate.getValidator(id);
			if (rval != null) {
				break;
			}
		}
		
		return rval;
	}

}
