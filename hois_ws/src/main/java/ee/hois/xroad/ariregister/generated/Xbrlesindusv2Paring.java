
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlesindusv2_paring complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlesindusv2_paring"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrikoodid" type="{http://arireg.x-road.eu/producer/}xbrlesindusv2_registrikoodid" minOccurs="0"/&gt;
 *         &lt;element name="esindusliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlesindusv2_paring", propOrder = {
    "fyysiliseIsikuKood",
    "fyysiliseIsikuRiik",
    "registrikoodid",
    "esindusliik"
})
public class Xbrlesindusv2Paring {

    @XmlElement(name = "fyysilise_isiku_kood")
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "fyysilise_isiku_riik")
    protected String fyysiliseIsikuRiik;
    protected Xbrlesindusv2Registrikoodid registrikoodid;
    protected String esindusliik;

    /**
     * Gets the value of the fyysiliseIsikuKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKood() {
        return fyysiliseIsikuKood;
    }

    /**
     * Sets the value of the fyysiliseIsikuKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKood(String value) {
        this.fyysiliseIsikuKood = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuRiik() {
        return fyysiliseIsikuRiik;
    }

    /**
     * Sets the value of the fyysiliseIsikuRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuRiik(String value) {
        this.fyysiliseIsikuRiik = value;
    }

    /**
     * Gets the value of the registrikoodid property.
     * 
     * @return
     *     possible object is
     *     {@link Xbrlesindusv2Registrikoodid }
     *     
     */
    public Xbrlesindusv2Registrikoodid getRegistrikoodid() {
        return registrikoodid;
    }

    /**
     * Sets the value of the registrikoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Xbrlesindusv2Registrikoodid }
     *     
     */
    public void setRegistrikoodid(Xbrlesindusv2Registrikoodid value) {
        this.registrikoodid = value;
    }

    /**
     * Gets the value of the esindusliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsindusliik() {
        return esindusliik;
    }

    /**
     * Sets the value of the esindusliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsindusliik(String value) {
        this.esindusliik = value;
    }

}
