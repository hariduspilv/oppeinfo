
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for evk_pohikiri complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="evk_pohikiri"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pohikirja_kinnitamise_aeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="pohikirja_muutmise_aeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="pohikirja_selgitus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "evk_pohikiri", propOrder = {
    "pohikirjaKinnitamiseAeg",
    "pohikirjaMuutmiseAeg",
    "pohikirjaSelgitus"
})
public class EvkPohikiri {

    @XmlElement(name = "pohikirja_kinnitamise_aeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pohikirjaKinnitamiseAeg;
    @XmlElement(name = "pohikirja_muutmise_aeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar pohikirjaMuutmiseAeg;
    @XmlElement(name = "pohikirja_selgitus")
    protected String pohikirjaSelgitus;

    /**
     * Gets the value of the pohikirjaKinnitamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPohikirjaKinnitamiseAeg() {
        return pohikirjaKinnitamiseAeg;
    }

    /**
     * Sets the value of the pohikirjaKinnitamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPohikirjaKinnitamiseAeg(XMLGregorianCalendar value) {
        this.pohikirjaKinnitamiseAeg = value;
    }

    /**
     * Gets the value of the pohikirjaMuutmiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPohikirjaMuutmiseAeg() {
        return pohikirjaMuutmiseAeg;
    }

    /**
     * Sets the value of the pohikirjaMuutmiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPohikirjaMuutmiseAeg(XMLGregorianCalendar value) {
        this.pohikirjaMuutmiseAeg = value;
    }

    /**
     * Gets the value of the pohikirjaSelgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohikirjaSelgitus() {
        return pohikirjaSelgitus;
    }

    /**
     * Sets the value of the pohikirjaSelgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohikirjaSelgitus(String value) {
        this.pohikirjaSelgitus = value;
    }

}
