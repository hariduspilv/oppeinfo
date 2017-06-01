
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlVOTA complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlVOTA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ainepunkte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="arvestuseTyyp" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="origSooritAeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutuseKirjeId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klRiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlVOTA", propOrder = {
    "muutusKp",
    "ainepunkte",
    "arvestuseTyyp",
    "oppeasutuseNimi",
    "origSooritAeg",
    "oppeasutuseKirjeId",
    "klRiik"
})
public class KhlVOTA {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected String ainepunkte;
    @XmlElement(required = true)
    protected BigInteger arvestuseTyyp;
    protected String oppeasutuseNimi;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar origSooritAeg;
    protected String oppeasutuseKirjeId;
    protected String klRiik;

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
     * Gets the value of the arvestuseTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getArvestuseTyyp() {
        return arvestuseTyyp;
    }

    /**
     * Sets the value of the arvestuseTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setArvestuseTyyp(BigInteger value) {
        this.arvestuseTyyp = value;
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
     * Gets the value of the origSooritAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrigSooritAeg() {
        return origSooritAeg;
    }

    /**
     * Sets the value of the origSooritAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrigSooritAeg(XMLGregorianCalendar value) {
        this.origSooritAeg = value;
    }

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

}
