
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogAmetikohtId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogAmetikohtId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klAmetikoht" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lepingAlgKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogAmetikohtId", propOrder = {
    "klAmetikoht",
    "lepingAlgKp"
})
public class PedagoogAmetikohtId {

    @XmlElement(required = true)
    protected String klAmetikoht;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepingAlgKp;

    /**
     * Gets the value of the klAmetikoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlAmetikoht() {
        return klAmetikoht;
    }

    /**
     * Sets the value of the klAmetikoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlAmetikoht(String value) {
        this.klAmetikoht = value;
    }

    /**
     * Gets the value of the lepingAlgKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepingAlgKp() {
        return lepingAlgKp;
    }

    /**
     * Sets the value of the lepingAlgKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepingAlgKp(XMLGregorianCalendar value) {
        this.lepingAlgKp = value;
    }

}
