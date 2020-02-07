
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="algusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="loppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="isikud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kmaEhisIsikList"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "algusKp",
    "loppKp",
    "isikud"
})
@XmlRootElement(name = "kmaEhis")
public class KmaEhis {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKp;
    @XmlElement(required = true)
    protected KmaEhisIsikList isikud;

    /**
     * Gets the value of the algusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKp() {
        return algusKp;
    }

    /**
     * Sets the value of the algusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKp(XMLGregorianCalendar value) {
        this.algusKp = value;
    }

    /**
     * Gets the value of the loppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKp() {
        return loppKp;
    }

    /**
     * Sets the value of the loppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKp(XMLGregorianCalendar value) {
        this.loppKp = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link KmaEhisIsikList }
     *     
     */
    public KmaEhisIsikList getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link KmaEhisIsikList }
     *     
     */
    public void setIsikud(KmaEhisIsikList value) {
        this.isikud = value;
    }

}
