
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlIsikuandmeteMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlIsikuandmeteMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klSugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klElukohamaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="onOrb" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="onValisyliopilane" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlIsikuandmeteMuutus", propOrder = {
    "muutusKp",
    "eesnimi",
    "perenimi",
    "synniKp",
    "klSugu",
    "klKodakondsus",
    "klElukohamaa",
    "onOrb",
    "onValisyliopilane"
})
public class KhlIsikuandmeteMuutus {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    protected String eesnimi;
    protected String perenimi;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    protected String klSugu;
    protected String klKodakondsus;
    protected String klElukohamaa;
    protected String onOrb;
    protected String onValisyliopilane;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
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
     * Gets the value of the synniKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniKp() {
        return synniKp;
    }

    /**
     * Sets the value of the synniKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniKp(XMLGregorianCalendar value) {
        this.synniKp = value;
    }

    /**
     * Gets the value of the klSugu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlSugu() {
        return klSugu;
    }

    /**
     * Sets the value of the klSugu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlSugu(String value) {
        this.klSugu = value;
    }

    /**
     * Gets the value of the klKodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKodakondsus() {
        return klKodakondsus;
    }

    /**
     * Sets the value of the klKodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKodakondsus(String value) {
        this.klKodakondsus = value;
    }

    /**
     * Gets the value of the klElukohamaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlElukohamaa() {
        return klElukohamaa;
    }

    /**
     * Sets the value of the klElukohamaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlElukohamaa(String value) {
        this.klElukohamaa = value;
    }

    /**
     * Gets the value of the onOrb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnOrb() {
        return onOrb;
    }

    /**
     * Sets the value of the onOrb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnOrb(String value) {
        this.onOrb = value;
    }

    /**
     * Gets the value of the onValisyliopilane property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnValisyliopilane() {
        return onValisyliopilane;
    }

    /**
     * Sets the value of the onValisyliopilane property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnValisyliopilane(String value) {
        this.onValisyliopilane = value;
    }

}
