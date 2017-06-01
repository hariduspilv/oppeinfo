
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for kasutatudEsfRahastus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kasutatudEsfRahastus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esfRahastus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esfRahastusTekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kasutatudEsfRahastus", propOrder = {
    "esfRahastus",
    "esfRahastusTekst"
})
public class KasutatudEsfRahastus {

    protected String esfRahastus;
    protected String esfRahastusTekst;

    /**
     * Gets the value of the esfRahastus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsfRahastus() {
        return esfRahastus;
    }

    /**
     * Sets the value of the esfRahastus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsfRahastus(String value) {
        this.esfRahastus = value;
    }

    /**
     * Gets the value of the esfRahastusTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsfRahastusTekst() {
        return esfRahastusTekst;
    }

    /**
     * Sets the value of the esfRahastusTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsfRahastusTekst(String value) {
        this.esfRahastusTekst = value;
    }

}
