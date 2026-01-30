
package org.openmrs.module.registration.wsclient.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Template1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Template2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "template1",
    "template2"
})
@XmlRootElement(name = "VerifyTemplate")
public class VerifyTemplate {

    @XmlElement(name = "Template1")
    protected String template1;
    @XmlElement(name = "Template2")
    protected String template2;

    /**
     * Gets the value of the template1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplate1() {
        return template1;
    }

    /**
     * Sets the value of the template1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplate1(String value) {
        this.template1 = value;
    }

    /**
     * Gets the value of the template2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplate2() {
        return template2;
    }

    /**
     * Sets the value of the template2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplate2(String value) {
        this.template2 = value;
    }

}
