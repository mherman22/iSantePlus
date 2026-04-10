package org.openmrs.module.isanteplus.configuration;

import org.openmrs.module.isanteplus.configuration.wsdl.*;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.Map;

public class BioPluginSoapClient {

    private static final int TIMEOUT_MS = 15000;
    private final BioPluginServiceV8Soap port;

    public BioPluginSoapClient() {
        BioPluginServiceV8 service = new BioPluginServiceV8();
        this.port = service.getBioPluginServiceV8Soap();
        configureTimeouts(port);
    }

    private void configureTimeouts(BioPluginServiceV8Soap port) {
        Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();

        ctx.put("com.sun.xml.ws.connect.timeout", TIMEOUT_MS);
        ctx.put("com.sun.xml.ws.request.timeout", TIMEOUT_MS);
        ctx.put("javax.xml.ws.client.connectionTimeout", TIMEOUT_MS);
        ctx.put("javax.xml.ws.client.receiveTimeout", TIMEOUT_MS);
    }

    // -------- APPELS SOAP --------

    public String getInfo() {
        return executeWithRetry(() -> port.getInfo());
    }

    public String identify(String biometricXml, int locationId) {
        return executeWithRetry(() -> port.identify(biometricXml, locationId));
    }

    public String verify(String biometricXml, String id, int locationId) {
        return executeWithRetry(() -> port.verify(biometricXml, id, locationId));
    }

    public String register(String biometricXml, String id, int locationId) {
        return executeWithRetry(() -> port.register(biometricXml, id, locationId));
    }

    // -------- RETRY + FAULT --------

    private String executeWithRetry(SoapCall call) {
        int attempts = 0;
        int maxAttempts = 3;

        while (true) {
            try {
                attempts++;
                return call.call();
            } catch (SOAPFaultException sfe) {
                throw new RuntimeException(
                        "SOAP Fault: " + sfe.getFault().getFaultString(), sfe);
            } catch (Exception e) {
                if (attempts >= maxAttempts) {
                    throw new RuntimeException(
                            "SOAP unreachable after " + attempts + " attempts", e);
                }
                sleep(2000);
            }
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }

    @FunctionalInterface
    private interface SoapCall {
        String call();
    }
}
