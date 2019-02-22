
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringliht_v5_evkapital complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringliht_v5_evkapital"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kapitali_suurus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kapitali_valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="asutatud_sissemakset_tegemata" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringliht_v5_evkapital", propOrder = {
    "kapitaliSuurus",
    "kapitaliValuuta",
    "asutatudSissemaksetTegemata"
})
public class ParinglihtV5Evkapital {

    @XmlElement(name = "kapitali_suurus", required = true, nillable = true)
    protected String kapitaliSuurus;
    @XmlElement(name = "kapitali_valuuta", required = true, nillable = true)
    protected String kapitaliValuuta;
    @XmlElement(name = "asutatud_sissemakset_tegemata", required = true, type = Boolean.class, nillable = true)
    protected Boolean asutatudSissemaksetTegemata;

    /**
     * Gets the value of the kapitaliSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKapitaliSuurus() {
        return kapitaliSuurus;
    }

    /**
     * Sets the value of the kapitaliSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKapitaliSuurus(String value) {
        this.kapitaliSuurus = value;
    }

    /**
     * Gets the value of the kapitaliValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKapitaliValuuta() {
        return kapitaliValuuta;
    }

    /**
     * Sets the value of the kapitaliValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKapitaliValuuta(String value) {
        this.kapitaliValuuta = value;
    }

    /**
     * Gets the value of the asutatudSissemaksetTegemata property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAsutatudSissemaksetTegemata() {
        return asutatudSissemaksetTegemata;
    }

    /**
     * Sets the value of the asutatudSissemaksetTegemata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAsutatudSissemaksetTegemata(Boolean value) {
        this.asutatudSissemaksetTegemata = value;
    }

}
