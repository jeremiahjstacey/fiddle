package org.owasp.esapi.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.owasp.esapi.validation.ValidationResponse.ValidationStatus;
import org.owasp.esapi.validation.annotation.Check;
import org.owasp.esapi.validation.jaxb.generated.Esapi;
import org.owasp.esapi.validation.jaxb.generated.Esapi.Validators;
import org.owasp.esapi.validation.jaxb.generated.Esapi.Validators.Validator;
import org.owasp.esapi.validation.jaxb.generated.Esapi.Validators.Validator.Args;
import org.owasp.esapi.validation.jaxb.generated.Esapi.Validators.Validator.Args.Arg;
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
		Validators allRules = validationConfig.getValidators();
		Class<?> validatorClass = null;
		Class<?>[] validatorCtrTypes = new Class<?>[] {};
		Object[] validatorCtrArgs = new Object[] {};

		ValidationResponse result = null;
		try {
			for (Validator validator : allRules.getValidator()) {
				if (validator.getId().equals(rule)) {
					validatorClass = Class.forName(validator.getType());
					Args args = validator.getArgs();
					if (args != null) {
						List<Arg> arguments = args.getArg();
						int ttlArgCount = arguments.size();
						validatorCtrTypes = new Class<?>[ttlArgCount];
						validatorCtrArgs = new Object[ttlArgCount];
						for (int index = 0; index < ttlArgCount; index++) {
							Arg arg = arguments.get(index);
							validatorCtrTypes[index] = Class.forName(arg.getType());
							validatorCtrArgs[index] = arg.getValue();
						}
					}
					break;
				}
			}

			if (validatorClass != null) {
				if (validatorCtrTypes.length > 0) {
					result = new ValidationResponse(ValidationStatus.ERROR,
							"Unimplemented Feature: Validator with Constructor Arguments presently not supported.  Refactor code to provide a no-argument constructor: "
									+ String.format("%s [%s]", rule, validatorClass.getSimpleName()));
				} else {
					Constructor<?> ctr = validatorClass.getConstructor(validatorCtrTypes);
					@SuppressWarnings("unchecked")
					org.owasp.esapi.validation.Validator<Object> check = (org.owasp.esapi.validation.Validator<Object>) ctr
					.newInstance(validatorCtrArgs);
					result = check.validate(ref);
				}
			} else {
				result = new ValidationResponse(ValidationStatus.ERROR,
						"Unable to validate data.  No rule defined for specified ID: " + rule);
			}

		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
			result = new ValidationResponse(ValidationStatus.ERROR,
					"Unable to validate data. Exceptions occurred during processing.", exception);
		}
		return result;
	}
}
