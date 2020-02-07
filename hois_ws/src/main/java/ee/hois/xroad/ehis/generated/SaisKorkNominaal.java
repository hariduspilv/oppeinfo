
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saisKorkNominaal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisKorkNominaal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nomPaevadKokku" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nomSuvaope" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nomValisriigis" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisKorkNominaal", propOrder = {
    "nomPaevadKokku",
    "nomSuvaope",
    "nomValisriigis"
})
public class SaisKorkNominaal {

    @XmlElement(required = true, nillable = true)
    protected BigInteger nomPaevadKokku;
    @XmlElement(required = true, nillable = true)
    protected BigInteger nomSuvaope;
    @XmlElement(required = true, nillable = true)
    protected BigInteger nomValisriigis;

    /**
     * Gets the value of the nomPaevadKokku property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNomPaevadKokku() {
        return nomPaevadKokku;
    }

    /**
     * Sets the value of the nomPaevadKokku property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNomPaevadKokku(BigInteger value) {
        this.nomPaevadKokku = value;
    }

    /**
     * Gets the value of the nomSuvaope property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNomSuvaope() {
        return nomSuvaope;
    }

    /**
     * Sets the value of the nomSuvaope property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNomSuvaope(BigInteger value) {
        this.nomSuvaope = value;
    }

    /**
     * Gets the value of the nomValisriigis property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNomValisriigis() {
        return nomValisriigis;
    }

    /**
     * Sets the value of the nomValisriigis property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNomValisriigis(BigInteger value) {
        this.nomValisriigis = value;
    }

}
