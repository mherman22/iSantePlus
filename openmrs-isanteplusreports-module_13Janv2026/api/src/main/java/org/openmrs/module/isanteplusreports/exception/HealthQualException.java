package org.openmrs.module.isanteplusreports.exception;

public class HealthQualException extends RuntimeException {
	
	public HealthQualException(Throwable e) {
		super(e);
	}
	
	public HealthQualException(String message) {
		super(message);
	}
	
	public HealthQualException(String message, Throwable e) {
		super(message, e);
	}
}
