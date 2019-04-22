
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kodiff complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kodiff"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Alguskp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *         &lt;element name="Loppkp" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kodiff", propOrder = {
    "kood",
    "nimi",
    "alguskp",
    "loppkp"
})
public class Kodiff {

    @XmlElement(name = "Kood", required = true)
    protected String kood;
    @XmlElement(name = "Nimi")
    protected String nimi;
    @XmlElement(name = "Alguskp")
    protected String alguskp;
    @XmlElement(name = "Loppkp")
    protected String loppkp;

    /**
     * Gets the value of the kood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKood() {
        return kood;
    }

    /**
     * Sets the value of the kood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKood(String value) {
        this.kood = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the alguskp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlguskp() {
        return alguskp;
    }

    /**
     * Sets the value of the alguskp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlguskp(String value) {
        this.alguskp = value;
    }

    /**
     * Gets the value of the loppkp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoppkp() {
        return loppkp;
    }

    /**
     * Sets the value of the loppkp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoppkp(String value) {
        this.loppkp = value;
    }

}
