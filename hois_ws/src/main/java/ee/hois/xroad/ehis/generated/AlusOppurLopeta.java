
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for alusOppurLopeta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alusOppurLopeta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="lopetKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="klLopetPohjus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alusOppurLopeta", propOrder = {
    "lopetKp",
    "klLopetPohjus"
})
public class AlusOppurLopeta {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lopetKp;
    @XmlElement(required = true)
    protected String klLopetPohjus;

    /**
     * Gets the value of the lopetKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLopetKp() {
        return lopetKp;
    }

    /**
     * Sets the value of the lopetKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLopetKp(XMLGregorianCalendar value) {
        this.lopetKp = value;
    }

    /**
     * Gets the value of the klLopetPohjus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlLopetPohjus() {
        return klLopetPohjus;
    }

    /**
     * Sets the value of the klLopetPohjus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlLopetPohjus(String value) {
        this.klLopetPohjus = value;
    }

}
