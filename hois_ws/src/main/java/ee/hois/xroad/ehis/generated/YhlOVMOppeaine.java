
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for yhlOVMOppeaine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="yhlOVMOppeaine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="aine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="klOppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="tunde" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "yhlOVMOppeaine", propOrder = {
    "aine",
    "klOppeaine",
    "tunde"
})
public class YhlOVMOppeaine {

    protected String aine;
    protected String klOppeaine;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger tunde;

    /**
     * Gets the value of the aine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAine() {
        return aine;
    }

    /**
     * Sets the value of the aine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAine(String value) {
        this.aine = value;
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

    /**
     * Gets the value of the tunde property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTunde() {
        return tunde;
    }

    /**
     * Sets the value of the tunde property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTunde(BigInteger value) {
        this.tunde = value;
    }

}
