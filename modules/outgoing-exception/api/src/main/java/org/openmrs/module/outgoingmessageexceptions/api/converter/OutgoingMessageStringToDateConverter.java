package org.openmrs.module.outgoingmessageexceptions.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Converter which is changing date String in ISO format (yyyy-MM-dd) to LocalDate object.
 */
@Component("outgoingmessageexceptions.OutgoingMessageStringToDateConverter")
public class OutgoingMessageStringToDateConverter implements Converter<String, LocalDate> {
	
	@Override
	public LocalDate convert(String source) {
		if (null != source) {
			return LocalDate.parse(source.trim());
		} else {
			return null;
		}
	}
}
