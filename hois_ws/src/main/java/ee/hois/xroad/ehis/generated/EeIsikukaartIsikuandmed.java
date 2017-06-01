
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for eeIsikukaartIsikuandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartIsikuandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisIsikukood"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elukohamaa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rrElukoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="elamisluba" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppelaenOigus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartOppelaenOigus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartIsikuandmed", propOrder = {
    "isikukood",
    "synniKp",
    "eesnimi",
    "perenimi",
    "elukohamaa",
    "rrElukoht",
    "kodakondsus",
    "elamisluba",
    "oppelaenOigus"
})
public class EeIsikukaartIsikuandmed {

    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String perenimi;
    protected String elukohamaa;
    protected String rrElukoht;
    protected String kodakondsus;
    protected String elamisluba;
    protected EeIsikukaartOppelaenOigus oppelaenOigus;

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
     * Gets the value of the elukohamaa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElukohamaa() {
        return elukohamaa;
    }

    /**
     * Sets the value of the elukohamaa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElukohamaa(String value) {
        this.elukohamaa = value;
    }

    /**
     * Gets the value of the rrElukoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRrElukoht() {
        return rrElukoht;
    }

    /**
     * Sets the value of the rrElukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRrElukoht(String value) {
        this.rrElukoht = value;
    }

    /**
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the elamisluba property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElamisluba() {
        return elamisluba;
    }

    /**
     * Sets the value of the elamisluba property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElamisluba(String value) {
        this.elamisluba = value;
    }

    /**
     * Gets the value of the oppelaenOigus property.
     * 
     * @return
     *     possible object is
     *     {@link EeIsikukaartOppelaenOigus }
     *     
     */
    public EeIsikukaartOppelaenOigus getOppelaenOigus() {
        return oppelaenOigus;
    }

    /**
     * Sets the value of the oppelaenOigus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeIsikukaartOppelaenOigus }
     *     
     */
    public void setOppelaenOigus(EeIsikukaartOppelaenOigus value) {
        this.oppelaenOigus = value;
    }

}
