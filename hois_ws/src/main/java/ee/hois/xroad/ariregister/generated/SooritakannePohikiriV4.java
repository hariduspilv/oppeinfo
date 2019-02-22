
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sooritakanne_pohikiri_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_pohikiri_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kinnitamise_aeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="pohikirja_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sisu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sisaldab_erioigusi" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_pohikiri_v4", propOrder = {
    "kinnitamiseAeg",
    "pohikirjaTyyp",
    "sisu",
    "sisaldabErioigusi"
})
public class SooritakannePohikiriV4 {

    @XmlElement(name = "kinnitamise_aeg", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kinnitamiseAeg;
    @XmlElement(name = "pohikirja_tyyp")
    protected String pohikirjaTyyp;
    protected String sisu;
    @XmlElement(name = "sisaldab_erioigusi")
    protected Boolean sisaldabErioigusi;

    /**
     * Gets the value of the kinnitamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKinnitamiseAeg() {
        return kinnitamiseAeg;
    }

    /**
     * Sets the value of the kinnitamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKinnitamiseAeg(XMLGregorianCalendar value) {
        this.kinnitamiseAeg = value;
    }

    /**
     * Gets the value of the pohikirjaTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohikirjaTyyp() {
        return pohikirjaTyyp;
    }

    /**
     * Sets the value of the pohikirjaTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohikirjaTyyp(String value) {
        this.pohikirjaTyyp = value;
    }

    /**
     * Gets the value of the sisu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSisu() {
        return sisu;
    }

    /**
     * Sets the value of the sisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSisu(String value) {
        this.sisu = value;
    }

    /**
     * Gets the value of the sisaldabErioigusi property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSisaldabErioigusi() {
        return sisaldabErioigusi;
    }

    /**
     * Sets the value of the sisaldabErioigusi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSisaldabErioigusi(Boolean value) {
        this.sisaldabErioigusi = value;
    }

}
