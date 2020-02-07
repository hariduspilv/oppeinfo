
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlLyhiajaliseltValismaal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlLyhiajaliseltValismaal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lyhiajaliseltValismaalId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="perioodAlates" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="perioodKuni" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klEesmark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ainepunkte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nominaalajaPikendamine" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klSihtriik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "khlLyhiajaliseltValismaal", propOrder = {
    "lyhiajaliseltValismaalId",
    "muutusKp",
    "perioodAlates",
    "perioodKuni",
    "klEesmark",
    "ainepunkte",
    "nominaalajaPikendamine",
    "oppeasutuseNimi",
    "klSihtriik",
    "klProgramm"
})
public class KhlLyhiajaliseltValismaal {

    protected BigInteger lyhiajaliseltValismaalId;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodAlates;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodKuni;
    @XmlElement(required = true)
    protected String klEesmark;
    @XmlElement(required = true)
    protected String ainepunkte;
    @XmlElement(required = true)
    protected BigInteger nominaalajaPikendamine;
    @XmlElement(required = true)
    protected String oppeasutuseNimi;
    @XmlElement(required = true)
    protected String klSihtriik;
    @XmlElement(required = true)
    protected String klProgramm;

    /**
     * Gets the value of the lyhiajaliseltValismaalId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLyhiajaliseltValismaalId() {
        return lyhiajaliseltValismaalId;
    }

    /**
     * Sets the value of the lyhiajaliseltValismaalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLyhiajaliseltValismaalId(BigInteger value) {
        this.lyhiajaliseltValismaalId = value;
    }

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
     * Gets the value of the nominaalajaPikendamine property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNominaalajaPikendamine() {
        return nominaalajaPikendamine;
    }

    /**
     * Sets the value of the nominaalajaPikendamine property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNominaalajaPikendamine(BigInteger value) {
        this.nominaalajaPikendamine = value;
    }

    /**
     * Gets the value of the oppeasutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimi() {
        return oppeasutuseNimi;
    }

    /**
     * Sets the value of the oppeasutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimi(String value) {
        this.oppeasutuseNimi = value;
    }

    /**
     * Gets the value of the klSihtriik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlSihtriik() {
        return klSihtriik;
    }

    /**
     * Sets the value of the klSihtriik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlSihtriik(String value) {
        this.klSihtriik = value;
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
