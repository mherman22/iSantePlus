package org.openmrs.module.registration.wsclient.wsdl;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

public class BioPluginServiceV8 extends Service {

    private static final QName SERVICE_QNAME = new QName("http://tempuri.org/", "BioPluginServiceV8");

    public BioPluginServiceV8(URL wsdlLocation) {
        super(wsdlLocation, SERVICE_QNAME);
    }

    public BioPluginServiceV8(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVICE_QNAME, features);
    }

    @WebEndpoint(name = "BioPluginServiceV8Soap")
    public BioPluginServiceV8Soap getBioPluginServiceV8Soap() {
        return super.getPort(new QName("http://tempuri.org/", "BioPluginServiceV8Soap"), BioPluginServiceV8Soap.class);
    }

    @WebEndpoint(name = "BioPluginServiceV8Soap")
    public BioPluginServiceV8Soap getBioPluginServiceV8Soap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BioPluginServiceV8Soap"), BioPluginServiceV8Soap.class, features);
    }

    @WebEndpoint(name = "BioPluginServiceV8Soap12")
    public BioPluginServiceV8Soap getBioPluginServiceV8Soap12() {
        return super.getPort(new QName("http://tempuri.org/", "BioPluginServiceV8Soap12"), BioPluginServiceV8Soap.class);
    }

    @WebEndpoint(name = "BioPluginServiceV8Soap12")
    public BioPluginServiceV8Soap getBioPluginServiceV8Soap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "BioPluginServiceV8Soap12"), BioPluginServiceV8Soap.class, features);
    }
}
