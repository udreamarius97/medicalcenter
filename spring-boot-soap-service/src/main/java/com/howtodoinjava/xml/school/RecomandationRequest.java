//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.01.10 at 03:39:12 AM EET 
//


package com.howtodoinjava.xml.school;

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
 *         &lt;element name="Recomandation" type="{http://www.howtodoinjava.com/xml/school}Recomandation"/>
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
    "recomandation"
})
@XmlRootElement(name = "RecomandationRequest")
public class RecomandationRequest {

    @XmlElement(name = "Recomandation", required = true)
    protected Recomandation recomandation;

    /**
     * Gets the value of the recomandation property.
     * 
     * @return
     *     possible object is
     *     {@link Recomandation }
     *     
     */
    public Recomandation getRecomandation() {
        return recomandation;
    }

    /**
     * Sets the value of the recomandation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recomandation }
     *     
     */
    public void setRecomandation(Recomandation value) {
        this.recomandation = value;
    }

}
