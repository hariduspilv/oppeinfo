
package ee.hois.xroad.ehis.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for khlOppekavaTaitmine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="khlOppekavaTaitmine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="muutusKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="taitmiseProtsent" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ainepunkte" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="eelminePeriood" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "khlOppekavaTaitmine", propOrder = {
    "muutusKp",
    "taitmiseProtsent",
    "ainepunkte",
    "eelminePeriood"
})
public class KhlOppekavaTaitmine {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar muutusKp;
    @XmlElement(required = true)
    protected BigDecimal taitmiseProtsent;
    @XmlElement(required = true)
    protected BigDecimal ainepunkte;
    protected String eelminePeriood;

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
     * Gets the value of the taitmiseProtsent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaitmiseProtsent() {
        return taitmiseProtsent;
    }

    /**
     * Sets the value of the taitmiseProtsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaitmiseProtsent(BigDecimal value) {
        this.taitmiseProtsent = value;
    }

    /**
     * Gets the value of the ainepunkte property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAinepunkte() {
        return ainepunkte;
    }

    /**
     * Sets the value of the ainepunkte property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAinepunkte(BigDecimal value) {
        this.ainepunkte = value;
    }

    /**
     * Gets the value of the eelminePeriood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEelminePeriood() {
        return eelminePeriood;
    }

    /**
     * Sets the value of the eelminePeriood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEelminePeriood(String value) {
        this.eelminePeriood = value;
    }

}
