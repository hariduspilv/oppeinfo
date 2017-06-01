
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogTasemekoolitus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogTasemekoolitus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klKvalDok" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKval" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tasemeOppeas" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klHaridustase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tasemeDokNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tasemeLopetKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="erialaOppekava" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogTasemekoolitus", propOrder = {
    "klKvalDok",
    "klKval",
    "klRiik",
    "tasemeOppeas",
    "klHaridustase",
    "tasemeDokNr",
    "tasemeLopetKp",
    "erialaOppekava",
    "klKeel",
    "kommentaar"
})
public class PedagoogTasemekoolitus {

    @XmlElement(required = true)
    protected String klKvalDok;
    @XmlElement(required = true)
    protected String klKval;
    @XmlElement(required = true)
    protected String klRiik;
    @XmlElement(required = true)
    protected String tasemeOppeas;
    protected String klHaridustase;
    protected String tasemeDokNr;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tasemeLopetKp;
    @XmlElement(required = true)
    protected String erialaOppekava;
    @XmlElement(required = true)
    protected String klKeel;
    protected String kommentaar;

    /**
     * Gets the value of the klKvalDok property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKvalDok() {
        return klKvalDok;
    }

    /**
     * Sets the value of the klKvalDok property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKvalDok(String value) {
        this.klKvalDok = value;
    }

    /**
     * Gets the value of the klKval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKval() {
        return klKval;
    }

    /**
     * Sets the value of the klKval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKval(String value) {
        this.klKval = value;
    }

    /**
     * Gets the value of the klRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlRiik() {
        return klRiik;
    }

    /**
     * Sets the value of the klRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlRiik(String value) {
        this.klRiik = value;
    }

    /**
     * Gets the value of the tasemeOppeas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasemeOppeas() {
        return tasemeOppeas;
    }

    /**
     * Sets the value of the tasemeOppeas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasemeOppeas(String value) {
        this.tasemeOppeas = value;
    }

    /**
     * Gets the value of the klHaridustase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlHaridustase() {
        return klHaridustase;
    }

    /**
     * Sets the value of the klHaridustase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlHaridustase(String value) {
        this.klHaridustase = value;
    }

    /**
     * Gets the value of the tasemeDokNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasemeDokNr() {
        return tasemeDokNr;
    }

    /**
     * Sets the value of the tasemeDokNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasemeDokNr(String value) {
        this.tasemeDokNr = value;
    }

    /**
     * Gets the value of the tasemeLopetKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTasemeLopetKp() {
        return tasemeLopetKp;
    }

    /**
     * Sets the value of the tasemeLopetKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTasemeLopetKp(XMLGregorianCalendar value) {
        this.tasemeLopetKp = value;
    }

    /**
     * Gets the value of the erialaOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErialaOppekava() {
        return erialaOppekava;
    }

    /**
     * Sets the value of the erialaOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErialaOppekava(String value) {
        this.erialaOppekava = value;
    }

    /**
     * Gets the value of the klKeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKeel() {
        return klKeel;
    }

    /**
     * Sets the value of the klKeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKeel(String value) {
        this.klKeel = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

}
