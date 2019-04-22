
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR434RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR434RequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Synniaeg" type="{http://rr.x-road.eu/producer}date" minOccurs="0"/&gt;
 *         &lt;element name="Eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Kehtivad" type="{http://rr.x-road.eu/producer}YeNo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR434RequestType", propOrder = {
    "isikukood",
    "riik",
    "synniaeg",
    "eesnimi",
    "perenimi",
    "kehtivad"
})
public class RR434RequestType {

    @XmlElement(name = "Isikukood")
    protected String isikukood;
    @XmlElement(name = "Riik")
    protected String riik;
    @XmlElement(name = "Synniaeg")
    protected String synniaeg;
    @XmlElement(name = "Eesnimi")
    protected String eesnimi;
    @XmlElement(name = "Perenimi")
    protected String perenimi;
    @XmlElement(name = "Kehtivad", required = true)
    @XmlSchemaType(name = "string")
    protected YeNo kehtivad;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the riik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiik() {
        return riik;
    }

    /**
     * Sets the value of the riik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiik(String value) {
        this.riik = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaeg(String value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the kehtivad property.
     * 
     * @return
     *     possible object is
     *     {@link YeNo }
     *     
     */
    public YeNo getKehtivad() {
        return kehtivad;
    }

    /**
     * Sets the value of the kehtivad property.
     * 
     * @param value
     *     allowed object is
     *     {@link YeNo }
     *     
     */
    public void setKehtivad(YeNo value) {
        this.kehtivad = value;
    }

}
