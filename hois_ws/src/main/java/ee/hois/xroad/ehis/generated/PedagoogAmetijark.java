
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogAmetijark complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogAmetijark"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klAmetijark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ametijarkKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dokNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "pedagoogAmetijark", propOrder = {
    "klAmetijark",
    "ametijarkKp",
    "dokNumber",
    "kommentaar"
})
public class PedagoogAmetijark {

    @XmlElement(required = true)
    protected String klAmetijark;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar ametijarkKp;
    protected String dokNumber;
    protected String kommentaar;

    /**
     * Gets the value of the klAmetijark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAmetijark() {
        return klAmetijark;
    }

    /**
     * Sets the value of the klAmetijark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAmetijark(String value) {
        this.klAmetijark = value;
    }

    /**
     * Gets the value of the ametijarkKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAmetijarkKp() {
        return ametijarkKp;
    }

    /**
     * Sets the value of the ametijarkKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAmetijarkKp(XMLGregorianCalendar value) {
        this.ametijarkKp = value;
    }

    /**
     * Gets the value of the dokNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokNumber() {
        return dokNumber;
    }

    /**
     * Sets the value of the dokNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokNumber(String value) {
        this.dokNumber = value;
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
