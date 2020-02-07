
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlNominaaliPikendaminePraktika complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlNominaaliPikendaminePraktika"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="perioodAlates" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="perioodKuni" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlNominaaliPikendaminePraktika", propOrder = {
    "muutusKp",
    "perioodAlates",
    "perioodKuni"
})
public class KhlNominaaliPikendaminePraktika {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodAlates;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar perioodKuni;

    /**
     * Gets the value of the muutusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMuutusKp() {
        return muutusKp;
    }

    /**
     * Sets the value of the muutusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMuutusKp(XMLGregorianCalendar value) {
        this.muutusKp = value;
    }

    /**
     * Gets the value of the perioodAlates property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodAlates() {
        return perioodAlates;
    }

    /**
     * Sets the value of the perioodAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodAlates(XMLGregorianCalendar value) {
        this.perioodAlates = value;
    }

    /**
     * Gets the value of the perioodKuni property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPerioodKuni() {
        return perioodKuni;
    }

    /**
     * Sets the value of the perioodKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPerioodKuni(XMLGregorianCalendar value) {
        this.perioodKuni = value;
    }

}
