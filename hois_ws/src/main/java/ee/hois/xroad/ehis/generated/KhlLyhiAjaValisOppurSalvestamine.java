
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlLyhiAjaValisOppurSalvestamine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlLyhiAjaValisOppurSalvestamine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKirjeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klSugu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klKodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="koduOppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKoduoppeasutuseRiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klKoduOppeaste" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klEesmark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perioodAlates" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="perioodKuni" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="ainepunkte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klProgramm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlLyhiAjaValisOppurSalvestamine", propOrder = {
    "oppeasutuseKirjeId",
    "isikukood",
    "synniKp",
    "klSugu",
    "klKodakondsus",
    "koduOppeasutus",
    "klKoduoppeasutuseRiik",
    "klKoduOppeaste",
    "klEesmark",
    "perioodAlates",
    "perioodKuni",
    "ainepunkte",
    "klProgramm"
})
public class KhlLyhiAjaValisOppurSalvestamine {

    protected String oppeasutuseKirjeId;
    protected String isikukood;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    protected String klSugu;
    protected String klKodakondsus;
    @XmlElement(required = true)
    protected String koduOppeasutus;
    @XmlElement(required = true)
    protected String klKoduoppeasutuseRiik;
    @XmlElement(required = true)
    protected String klKoduOppeaste;
    @XmlElement(required = true)
    protected String klEesmark;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodAlates;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodKuni;
    protected String ainepunkte;
    @XmlElement(required = true)
    protected String klProgramm;

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
     * Gets the value of the koduOppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoduOppeasutus() {
        return koduOppeasutus;
    }

    /**
     * Sets the value of the koduOppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoduOppeasutus(String value) {
        this.koduOppeasutus = value;
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
     * Gets the value of the klKoduOppeaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKoduOppeaste() {
        return klKoduOppeaste;
    }

    /**
     * Sets the value of the klKoduOppeaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKoduOppeaste(String value) {
        this.klKoduOppeaste = value;
    }

    /**
     * Gets the value of the klEesmark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlEesmark() {
        return klEesmark;
    }

    /**
     * Sets the value of the klEesmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlEesmark(String value) {
        this.klEesmark = value;
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

    /**
     * Gets the value of the perioodKuni property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodKuni() {
        return perioodKuni;
    }

    /**
     * Sets the value of the perioodKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodKuni(XMLGregorianCalendar value) {
        this.perioodKuni = value;
    }

    /**
     * Gets the value of the ainepunkte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAinepunkte() {
        return ainepunkte;
    }

    /**
     * Sets the value of the ainepunkte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAinepunkte(String value) {
        this.ainepunkte = value;
    }

    /**
     * Gets the value of the klProgramm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlProgramm() {
        return klProgramm;
    }

    /**
     * Sets the value of the klProgramm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlProgramm(String value) {
        this.klProgramm = value;
    }

}
