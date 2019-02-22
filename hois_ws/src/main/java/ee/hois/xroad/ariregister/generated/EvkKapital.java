
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for evk_kapital complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evk_kapital"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kapitali_suurus" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="kapitali_valuuta_tahis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evk_kapital", propOrder = {
    "kapitaliSuurus",
    "kapitaliValuutaTahis"
})
public class EvkKapital {

    @XmlElement(name = "kapitali_suurus")
    protected float kapitaliSuurus;
    @XmlElement(name = "kapitali_valuuta_tahis", required = true)
    protected String kapitaliValuutaTahis;

    /**
     * Gets the value of the kapitaliSuurus property.
     * 
     */
    public float getKapitaliSuurus() {
        return kapitaliSuurus;
    }

    /**
     * Sets the value of the kapitaliSuurus property.
     * 
     */
    public void setKapitaliSuurus(float value) {
        this.kapitaliSuurus = value;
    }

    /**
     * Gets the value of the kapitaliValuutaTahis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKapitaliValuutaTahis() {
        return kapitaliValuutaTahis;
    }

    /**
     * Sets the value of the kapitaliValuutaTahis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKapitaliValuutaTahis(String value) {
        this.kapitaliValuutaTahis = value;
    }

}
