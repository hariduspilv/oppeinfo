
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlOppejoudType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppejoudType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="isikuandmed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}khlOppejoudIsikuandmedType"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="juhendamiseAlgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="juhendamiseLopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppejoudType", propOrder = {
    "isikukood",
    "isikuandmed",
    "juhendamiseAlgus",
    "juhendamiseLopp"
})
public class KhlOppejoudType {

    protected String isikukood;
    protected KhlOppejoudIsikuandmedType isikuandmed;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar juhendamiseAlgus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar juhendamiseLopp;

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the isikuandmed property.
     * 
     * @return
     *     possible object is
     *     {@link KhlOppejoudIsikuandmedType }
     *     
     */
    public KhlOppejoudIsikuandmedType getIsikuandmed() {
        return isikuandmed;
    }

    /**
     * Sets the value of the isikuandmed property.
     * 
     * @param value
     *     allowed object is
     *     {@link KhlOppejoudIsikuandmedType }
     *     
     */
    public void setIsikuandmed(KhlOppejoudIsikuandmedType value) {
        this.isikuandmed = value;
    }

    /**
     * Gets the value of the juhendamiseAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJuhendamiseAlgus() {
        return juhendamiseAlgus;
    }

    /**
     * Sets the value of the juhendamiseAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJuhendamiseAlgus(XMLGregorianCalendar value) {
        this.juhendamiseAlgus = value;
    }

    /**
     * Gets the value of the juhendamiseLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJuhendamiseLopp() {
        return juhendamiseLopp;
    }

    /**
     * Sets the value of the juhendamiseLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJuhendamiseLopp(XMLGregorianCalendar value) {
        this.juhendamiseLopp = value;
    }

}
