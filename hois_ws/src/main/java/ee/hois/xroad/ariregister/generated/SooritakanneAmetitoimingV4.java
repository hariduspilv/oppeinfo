
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for sooritakanne_ametitoiming_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_ametitoiming_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="tehingu_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ametitoimingu_nr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="notari_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="notari_isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_ametitoiming_v4", propOrder = {
    "tehinguKuupaev",
    "ametitoiminguNr",
    "notariNimi",
    "notariIsikukood"
})
public class SooritakanneAmetitoimingV4 {

    @XmlElement(name = "tehingu_kuupaev", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar tehinguKuupaev;
    @XmlElement(name = "ametitoimingu_nr")
    protected Integer ametitoiminguNr;
    @XmlElement(name = "notari_nimi", required = true)
    protected String notariNimi;
    @XmlElement(name = "notari_isikukood", required = true)
    protected String notariIsikukood;

    /**
     * Gets the value of the tehinguKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTehinguKuupaev() {
        return tehinguKuupaev;
    }

    /**
     * Sets the value of the tehinguKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTehinguKuupaev(XMLGregorianCalendar value) {
        this.tehinguKuupaev = value;
    }

    /**
     * Gets the value of the ametitoiminguNr property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAmetitoiminguNr() {
        return ametitoiminguNr;
    }

    /**
     * Sets the value of the ametitoiminguNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAmetitoiminguNr(Integer value) {
        this.ametitoiminguNr = value;
    }

    /**
     * Gets the value of the notariNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariNimi() {
        return notariNimi;
    }

    /**
     * Sets the value of the notariNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariNimi(String value) {
        this.notariNimi = value;
    }

    /**
     * Gets the value of the notariIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariIsikukood() {
        return notariIsikukood;
    }

    /**
     * Sets the value of the notariIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariIsikukood(String value) {
        this.notariIsikukood = value;
    }

}
