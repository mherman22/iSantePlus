package org.openmrs.module.isanteplusreports.conventer;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.openmrs.module.isanteplusreports.alertprecoce.model.AlertPrecoceSelectedIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StringToAlertPrecoceIndicatorListConverter implements Converter<String, List<AlertPrecoceSelectedIndicator>> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StringToAlertPrecoceIndicatorListConverter.class);
	
	@Override
	public List<AlertPrecoceSelectedIndicator> convert(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, AlertPrecoceSelectedIndicator.class);
		List<AlertPrecoceSelectedIndicator> list = null;
		try {
			list = mapper.readValue(json, type);
		}
		catch (IOException ex) {
			LOGGER.error("Cannot convert json to List<AlertPrecoceSelectedIndicator>>. Null was returned");
		}
		return list;
	}
}