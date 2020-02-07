
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
 *         &lt;element name="tyyp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="muudatusedAlates" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="contentIn" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
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
    "tyyp",
    "muudatusedAlates",
    "contentIn"
})
@XmlRootElement(name = "pankOppurLaenListV2")
public class PankOppurLaenListV2 {

    @XmlElement(required = true)
    protected String tyyp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muudatusedAlates;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String contentIn;

    /**
     * Gets the value of the tyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyyp(String value) {
        this.tyyp = value;
    }

    /**
     * Gets the value of the muudatusedAlates property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuudatusedAlates() {
        return muudatusedAlates;
    }

    /**
     * Sets the value of the muudatusedAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuudatusedAlates(XMLGregorianCalendar value) {
        this.muudatusedAlates = value;
    }

    /**
     * Gets the value of the contentIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentIn() {
        return contentIn;
    }

    /**
     * Sets the value of the contentIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentIn(String value) {
        this.contentIn = value;
    }

}
