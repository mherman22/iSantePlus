
package org.openmrs.module.isanteplus.configuration.wsdl;

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
 *         &lt;element name="Old_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="New_ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="engineName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "oldID",
    "newID",
    "engineName"
})
@XmlRootElement(name = "ChangeID")
public class ChangeID {

    @XmlElement(name = "Old_ID")
    protected String oldID;
    @XmlElement(name = "New_ID")
    protected String newID;
    protected String engineName;

    /**
     * Gets the value of the oldID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldID() {
        return oldID;
    }

    /**
     * Sets the value of the oldID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldID(String value) {
        this.oldID = value;
    }

    /**
     * Gets the value of the newID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewID() {
        return newID;
    }

    /**
     * Sets the value of the newID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewID(String value) {
        this.newID = value;
    }

    /**
     * Gets the value of the engineName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEngineName() {
        return engineName;
    }

    /**
     * Sets the value of the engineName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEngineName(String value) {
        this.engineName = value;
    }

}
