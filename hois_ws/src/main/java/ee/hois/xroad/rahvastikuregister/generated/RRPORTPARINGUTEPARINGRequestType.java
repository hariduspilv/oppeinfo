
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRPORTPARINGUTE_PARINGRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRPORTPARINGUTE_PARINGRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Alates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Kuni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Vahemik" type="{http://rr.x-road.eu/producer}ajavahemik" minOccurs="0"/&gt;
 *         &lt;element name="Viimased" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRPORTPARINGUTE_PARINGRequestType", propOrder = {
    "alates",
    "kuni",
    "vahemik",
    "viimased"
})
public class RRPORTPARINGUTEPARINGRequestType {

    @XmlElement(name = "Alates")
    protected String alates;
    @XmlElement(name = "Kuni")
    protected String kuni;
    @XmlElement(name = "Vahemik")
    protected String vahemik;
    @XmlElement(name = "Viimased", required = true)
    protected String viimased;

    /**
     * Gets the value of the alates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlates() {
        return alates;
    }

    /**
     * Sets the value of the alates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlates(String value) {
        this.alates = value;
    }

    /**
     * Gets the value of the kuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKuni() {
        return kuni;
    }

    /**
     * Sets the value of the kuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKuni(String value) {
        this.kuni = value;
    }

    /**
     * Gets the value of the vahemik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVahemik() {
        return vahemik;
    }

    /**
     * Sets the value of the vahemik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVahemik(String value) {
        this.vahemik = value;
    }

    /**
     * Gets the value of the viimased property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViimased() {
        return viimased;
    }

    /**
     * Sets the value of the viimased property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViimased(String value) {
        this.viimased = value;
    }

}
