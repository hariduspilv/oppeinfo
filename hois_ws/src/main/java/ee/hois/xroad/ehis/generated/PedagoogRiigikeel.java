
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogRiigikeel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogRiigikeel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="klTase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="klKategooria" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogRiigikeel", propOrder = {
    "kuupaev",
    "klTase",
    "klKategooria"
})
public class PedagoogRiigikeel {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;
    protected String klTase;
    protected String klKategooria;

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
     * Gets the value of the klTase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTase() {
        return klTase;
    }

    /**
     * Sets the value of the klTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTase(String value) {
        this.klTase = value;
    }

    /**
     * Gets the value of the klKategooria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlKategooria() {
        return klKategooria;
    }

    /**
     * Sets the value of the klKategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlKategooria(String value) {
        this.klKategooria = value;
    }

}
