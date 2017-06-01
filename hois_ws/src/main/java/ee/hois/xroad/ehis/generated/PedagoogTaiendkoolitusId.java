
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for pedagoogTaiendkoolitusId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pedagoogTaiendkoolitusId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="klTaiendDoc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="taiendDocKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klTaiendValdkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pedagoogTaiendkoolitusId", propOrder = {
    "klTaiendDoc",
    "taiendDocKp",
    "klTaiendValdkond"
})
public class PedagoogTaiendkoolitusId {

    @XmlElement(required = true)
    protected String klTaiendDoc;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar taiendDocKp;
    @XmlElement(required = true)
    protected String klTaiendValdkond;

    /**
     * Gets the value of the klTaiendDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTaiendDoc() {
        return klTaiendDoc;
    }

    /**
     * Sets the value of the klTaiendDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTaiendDoc(String value) {
        this.klTaiendDoc = value;
    }

    /**
     * Gets the value of the taiendDocKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTaiendDocKp() {
        return taiendDocKp;
    }

    /**
     * Sets the value of the taiendDocKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTaiendDocKp(XMLGregorianCalendar value) {
        this.taiendDocKp = value;
    }

    /**
     * Gets the value of the klTaiendValdkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlTaiendValdkond() {
        return klTaiendValdkond;
    }

    /**
     * Sets the value of the klTaiendValdkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlTaiendValdkond(String value) {
        this.klTaiendValdkond = value;
    }

}
