package org.openmrs.module.isanteplusreports.conventer;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.openmrs.module.isanteplusreports.pnlsReport.model.PnlsReportSelectedIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPnlsIndicatorListConverter implements Converter<String, List<PnlsReportSelectedIndicator>>{

	private static final Logger LOGGER = LoggerFactory.getLogger(StringToPnlsIndicatorListConverter.class);
	
	@Override
	public List<PnlsReportSelectedIndicator> convert(String json) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, PnlsReportSelectedIndicator.class);
		List<PnlsReportSelectedIndicator> list = null;
		try {
			list = mapper.readValue(json, type);
		}
		catch (IOException ex) {
			LOGGER.error("Cannot convert json to List<PnlsReportSelectedIndicator>>. Null was returned");
		}
		return list;
	}
}
