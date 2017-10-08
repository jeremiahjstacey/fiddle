package org.owasp.esapi.validation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.annotation.Check;
import org.owasp.esapi.validation.jaxb.generated.Esapi;
import org.owasp.esapi.validation.jaxb.generated.ObjectFactory;

public class ESAPIValidation {

	public static ESAPIValidation forConfiguration(String filename) throws JAXBException {
		return new ESAPIValidation(filename);
	}

	private final Esapi validationConfig;

	private ESAPIValidation(String filename) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		validationConfig = (Esapi) unmarshaller.unmarshal(ESAPIValidation.class.getResourceAsStream(filename));

	}

	public ValidationResponse check(Check configuration, Object ref) {
		return check(configuration.rule(), ref);
	}

	public ValidationResponse check(String rule, Object ref) {
		ValidationResponse result = null;
		
		String validationConfigClass = validationConfig.getValidationProvider().getClazz();
		
		ValidationConfiguration vc  = null;
		//Expect a no-arg constructor.
		Class<?> classRef = null;
		try {
			classRef = Class.forName(validationConfigClass);
			if (!ValidationConfiguration.class.isAssignableFrom(classRef)) {
				result = new ValidationResponse(ValidationStatus.ERROR, "Configured ValidationProvider class does not match expected API for ValidationConfiguration!");
				classRef = null;
			} else {
				vc = (ValidationConfiguration) classRef.newInstance();
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			result = new ValidationResponse(ValidationStatus.ERROR, "Failed to initialize configured ValidationProvider class reference", e);
		} 
		
		if (vc != null) {
			Validator<Object> validator = (Validator<Object>) vc.getValidator(rule);
			if (validator == null) {
				result = new ValidationResponse(ValidationStatus.ERROR, "Unable to locate Validator by Id: " + rule);	
			} else {
				result = validator.validate(ref);
			}
		}
			
		return result;
	}
}
