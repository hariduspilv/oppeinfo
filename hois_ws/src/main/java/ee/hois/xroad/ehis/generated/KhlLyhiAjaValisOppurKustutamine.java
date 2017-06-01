
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlLyhiAjaValisOppurKustutamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlLyhiAjaValisOppurKustutamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKirjeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klKoduoppeasutuseRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perioodAlates" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlLyhiAjaValisOppurKustutamine", propOrder = {
    "oppeasutuseKirjeId",
    "synniKp",
    "klKoduoppeasutuseRiik",
    "perioodAlates"
})
public class KhlLyhiAjaValisOppurKustutamine {

    protected String oppeasutuseKirjeId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    @XmlElement(required = true)
    protected String klKoduoppeasutuseRiik;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodAlates;

    /**
     * Gets the value of the oppeasutuseKirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKirjeId() {
        return oppeasutuseKirjeId;
    }

    /**
     * Sets the value of the oppeasutuseKirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKirjeId(String value) {
        this.oppeasutuseKirjeId = value;
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
     * Gets the value of the klKoduoppeasutuseRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKoduoppeasutuseRiik() {
        return klKoduoppeasutuseRiik;
    }

    /**
     * Sets the value of the klKoduoppeasutuseRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKoduoppeasutuseRiik(String value) {
        this.klKoduoppeasutuseRiik = value;
    }

    /**
     * Gets the value of the perioodAlates property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodAlates() {
        return perioodAlates;
    }

    /**
     * Sets the value of the perioodAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodAlates(XMLGregorianCalendar value) {
        this.perioodAlates = value;
    }

}
