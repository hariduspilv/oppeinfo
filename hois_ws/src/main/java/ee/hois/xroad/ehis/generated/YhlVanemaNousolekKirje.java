
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlVanemaNousolekKirje complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlVanemaNousolekKirje"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsusNr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
@XmlType(name = "yhlVanemaNousolekKirje", propOrder = {
    "otsusNr",
    "kuupaev",
    "kommentaar"
})
public class YhlVanemaNousolekKirje {

    @XmlElement(required = true)
    protected String otsusNr;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;
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
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuupaev(XMLGregorianCalendar value) {
        this.kuupaev = value;
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
