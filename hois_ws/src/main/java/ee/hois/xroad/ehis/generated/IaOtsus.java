
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for iaOtsus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="iaOtsus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsusNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="soovitusKehtivusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;choice minOccurs="0"&gt;
 *           &lt;element name="vanemaMitteNousolekKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *           &lt;element name="vanemaNousolekKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="nousolekKommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="soovitused" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}iaKirjed" minOccurs="0"/&gt;
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
@XmlType(name = "iaOtsus", propOrder = {
    "otsusNr",
    "otsusKp",
    "soovitusKehtivusKp",
    "vanemaMitteNousolekKp",
    "vanemaNousolekKp",
    "nousolekKommentaar",
    "soovitused",
    "kommentaar"
})
public class IaOtsus {

    @XmlElement(required = true)
    protected String otsusNr;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar soovitusKehtivusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vanemaMitteNousolekKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vanemaNousolekKp;
    protected String nousolekKommentaar;
    protected IaKirjed soovitused;
    protected String kommentaar;

    /**
     * Gets the value of the otsusNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsusNr() {
        return otsusNr;
    }

    /**
     * Sets the value of the otsusNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsusNr(String value) {
        this.otsusNr = value;
    }

    /**
     * Gets the value of the otsusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOtsusKp() {
        return otsusKp;
    }

    /**
     * Sets the value of the otsusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOtsusKp(XMLGregorianCalendar value) {
        this.otsusKp = value;
    }

    /**
     * Gets the value of the soovitusKehtivusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSoovitusKehtivusKp() {
        return soovitusKehtivusKp;
    }

    /**
     * Sets the value of the soovitusKehtivusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSoovitusKehtivusKp(XMLGregorianCalendar value) {
        this.soovitusKehtivusKp = value;
    }

    /**
     * Gets the value of the vanemaMitteNousolekKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVanemaMitteNousolekKp() {
        return vanemaMitteNousolekKp;
    }

    /**
     * Sets the value of the vanemaMitteNousolekKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVanemaMitteNousolekKp(XMLGregorianCalendar value) {
        this.vanemaMitteNousolekKp = value;
    }

    /**
     * Gets the value of the vanemaNousolekKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVanemaNousolekKp() {
        return vanemaNousolekKp;
    }

    /**
     * Sets the value of the vanemaNousolekKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVanemaNousolekKp(XMLGregorianCalendar value) {
        this.vanemaNousolekKp = value;
    }

    /**
     * Gets the value of the nousolekKommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNousolekKommentaar() {
        return nousolekKommentaar;
    }

    /**
     * Sets the value of the nousolekKommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNousolekKommentaar(String value) {
        this.nousolekKommentaar = value;
    }

    /**
     * Gets the value of the soovitused property.
     * 
     * @return
     *     possible object is
     *     {@link IaKirjed }
     *     
     */
    public IaKirjed getSoovitused() {
        return soovitused;
    }

    /**
     * Sets the value of the soovitused property.
     * 
     * @param value
     *     allowed object is
     *     {@link IaKirjed }
     *     
     */
    public void setSoovitused(IaKirjed value) {
        this.soovitused = value;
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
