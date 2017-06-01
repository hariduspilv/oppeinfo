
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlPuudulikAastahinne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlPuudulikAastahinne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="klOppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlPuudulikAastahinne", propOrder = {
    "aasta",
    "oppeaine",
    "klOppeaine"
})
public class YhlPuudulikAastahinne {

    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger aasta;
    protected String oppeaine;
    protected String klOppeaine;

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAasta(BigInteger value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the oppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeaine() {
        return oppeaine;
    }

    /**
     * Sets the value of the oppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeaine(String value) {
        this.oppeaine = value;
    }

    /**
     * Gets the value of the klOppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlOppeaine() {
        return klOppeaine;
    }

    /**
     * Sets the value of the klOppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlOppeaine(String value) {
        this.klOppeaine = value;
    }

}
