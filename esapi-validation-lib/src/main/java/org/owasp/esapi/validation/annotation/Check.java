package org.owasp.esapi.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface Check {
	/**
	 * Acquires the unique identifier of the configuration to apply to the annotated value.
	 * @return String.
	 */
	public String rule();
}
