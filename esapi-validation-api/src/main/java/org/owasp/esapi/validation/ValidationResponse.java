package org.owasp.esapi.validation;

/**
 * The data type returned from a validation check.
 *
 */
public class ValidationResponse {
	/**
	 * Status types which the ValidationResponse may contain.
	 */
	public enum ValidationStatus {
		/** Validation constraints had no issues and the data reference is valid.*/
		PASS (true), 
		/** Validation constraints failed, but are within the bounds of expected use and pose no apparent risk to the system.*/
		FAIL (false),
		/** Validation constraints failed and the data presented poses a potential risk to the system.*/
		RISK (false),
		/** Validation constraint failed due to a processing error.*/
		ERROR (false);
		
		/** The general pass/fail status of this status reference.*/
		private final boolean isValid;
		/** Constructor.*/
		private ValidationStatus(boolean isValid) {
			this.isValid = isValid;
		}
		/**
		 * Returns the validity state of this reference in a flat pass/fail context.
		 * @return {@code true} if the status represents a passable/safe state.
		 */
		public boolean isValid() {
			return isValid;
		}
	}

	/** Reusable PASS reference with no Message.*/
	public static final ValidationResponse OK = new ValidationResponse(ValidationStatus.PASS, null);

	private final ValidationStatus status;
	private final String detail;
	private final Exception ex;

	public ValidationResponse(ValidationStatus status) {
		this (status, null, null);
	}
	
	public ValidationResponse(Exception exception) {
		this(ValidationStatus.ERROR, exception.getMessage(), exception); 
	}

	
	public ValidationResponse(ValidationStatus status, String detail) {
		this(status, detail, null);
	}
	
	
	public ValidationResponse(ValidationStatus status, String detail, Exception exception) {
		if (status == null) {
			throw new IllegalArgumentException("Cannot construct response with a null ValidationStatus");
		}
		this.status = status;
		this.detail = detail == null ? "" : detail;
		this.ex = exception;
	}

	/**
	 * Acquires a more pointed Status for the evaluation of a validation request.
	 * @return ValidationStatus reference.  Never {@code null}
	 */
	public final ValidationStatus getResponseStatus() {
		return status;
	}

	/**
	 * Acquires the contextual description for this response.
	 * @return String.  Never {@code null}
	 */
	public final String getResponseDetail() {
		return detail;
	}
	
	/**
	 * Returns the validity state of the response in a flat pass/fail context.
	 * @return {@code true} if the status represents a passable/safe state.
	 * @see #getResponseStatus()
	 */
	public final boolean isValid() {
		return status.isValid;
	}
	
	/**
	 * Returns any Exceptions which have been nested in this status.
	 * @return {@link Exception} or <code>null</code>.
	 */
	public final Exception getException() {
		return ex;
	}

}
