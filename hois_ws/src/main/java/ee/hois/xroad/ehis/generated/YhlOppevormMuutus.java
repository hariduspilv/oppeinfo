
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for yhlOppevormMuutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlOppevormMuutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="klOppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeained" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}yhlOVMOppeained" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlOppevormMuutus", propOrder = {
    "muutusKp",
    "klOppevorm",
    "oppeained"
})
public class YhlOppevormMuutus {

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected String klOppevorm;
    protected YhlOVMOppeained oppeained;

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
     * Gets the value of the klOppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppevorm() {
        return klOppevorm;
    }

    /**
     * Sets the value of the klOppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppevorm(String value) {
        this.klOppevorm = value;
    }

    /**
     * Gets the value of the oppeained property.
     * 
     * @return
     *     possible object is
     *     {@link YhlOVMOppeained }
     *     
     */
    public YhlOVMOppeained getOppeained() {
        return oppeained;
    }

    /**
     * Sets the value of the oppeained property.
     * 
     * @param value
     *     allowed object is
     *     {@link YhlOVMOppeained }
     *     
     */
    public void setOppeained(YhlOVMOppeained value) {
        this.oppeained = value;
    }

}
