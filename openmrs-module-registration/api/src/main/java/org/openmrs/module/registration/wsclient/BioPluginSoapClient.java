package org.openmrs.module.registration.wsclient;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.registration.wsclient.wsdl.BioPluginServiceV8;
import org.openmrs.module.registration.wsclient.wsdl.BioPluginServiceV8Soap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BioPluginSoapClient {

	private BioPluginServiceV8Soap port;

	private final int connectTimeout;
	private final int requestTimeout;
	private final int maxRetries;
	private final long retrySleepMs;

	public BioPluginSoapClient() {
		AdministrationService admin = Context.getAdministrationService();
		String wsdlUrl = admin.getGlobalProperty("m2sys-biometrics.local-service.url");
        System.out.println("WSDL URL ::::::::::::::;: " + wsdlUrl);

		BioPluginServiceV8 service;
		try {
			service = new BioPluginServiceV8(new URL(wsdlUrl));
		} catch (MalformedURLException e) {
			throw new RuntimeException("WSDL URL incorrecte: " + wsdlUrl, e);
		}

		this.port = service.getBioPluginServiceV8Soap();

		this.connectTimeout = getIntGP(admin, "m2sys-biometrics.captureTimeout", 15000);
		this.requestTimeout = getIntGP(admin, "m2sys-biometrics.captureTimeout", 30000);
		this.maxRetries = getIntGP(admin, "m2sys-biometrics.captureTimeout", 3);
		this.retrySleepMs = getLongGP(admin, "m2sys-biometrics.captureTimeout", 2000L);

		configureTimeouts();
	}

	private void configureTimeouts() {
		BindingProvider bp = (BindingProvider) port;
		Map<String, Object> ctx = bp.getRequestContext();

		ctx.put("com.sun.xml.ws.connect.timeout", connectTimeout);
		ctx.put("com.sun.xml.ws.request.timeout", requestTimeout);

		ctx.put("javax.xml.ws.client.connectionTimeout", connectTimeout);
		ctx.put("javax.xml.ws.client.receiveTimeout", requestTimeout);
	}

	// ================= API SOAP =================

	public String getInfo() {
		return executeWithRetry(() -> port.getInfo());
	}

	public String identify(String biometricXml, int locationId) {
		return executeWithRetry(() -> port.identify(biometricXml, locationId));
	}

	public String verify(String biometricXml, String patientId, int locationId) {
		return executeWithRetry(() -> port.verify(biometricXml, patientId, locationId));
	}

	public String register(String biometricXml, String patientId, int locationId) {
		return executeWithRetry(() -> port.register(biometricXml, patientId, locationId));
	}

	// ================= RETRY =================

	private String executeWithRetry(SoapCall call) {
		int attempts = 0;
		while (true) {
			try {
				attempts++;
				return call.call();
			} catch (SOAPFaultException sfe) {
				throw new RuntimeException("BioPlugin SOAP Fault: " + sfe.getFault().getFaultString(), sfe);
			} catch (Exception e) {
				if (attempts >= maxRetries) {
					throw new RuntimeException("BioPlugin unreachable after " + attempts + " attempts", e);
				}
				sleep(retrySleepMs);
			}
		}
	}

	private void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
	}

	private int getIntGP(AdministrationService admin, String key, int def) {
		try {
			return Integer.parseInt(admin.getGlobalProperty(key, String.valueOf(def)));
		} catch (Exception e) {
			return def;
		}
	}

	private long getLongGP(AdministrationService admin, String key, long def) {
		try {
			return Long.parseLong(admin.getGlobalProperty(key, String.valueOf(def)));
		} catch (Exception e) {
			return def;
		}
	}

	@FunctionalInterface
	private interface SoapCall {
		String call();
	}
}
