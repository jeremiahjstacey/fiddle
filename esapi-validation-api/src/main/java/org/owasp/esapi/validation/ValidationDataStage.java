package org.owasp.esapi.validation;

@FunctionalInterface
public interface ValidationDataStage <F, T> {
	T prepareData(F input);
}
