package org.openmrs.module.outgoingmessageexceptions.api.exceptions;

public class OutgoingMessageException extends RuntimeException {

	public OutgoingMessageException(Throwable e) {
		super(e);
	}

	public OutgoingMessageException(String message) {
		super(message);
	}

	public OutgoingMessageException(String message, Throwable e) {
		super(message, e);
	}
}
