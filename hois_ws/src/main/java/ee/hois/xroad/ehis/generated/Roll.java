
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for roll complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="roll"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="paralleel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roll", propOrder = {
    "nimetus",
    "klass",
    "paralleel"
})
public class Roll {

    @XmlElement(required = true)
    protected String nimetus;
    protected String klass;
    protected String paralleel;

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the klass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlass() {
        return klass;
    }

    /**
     * Sets the value of the klass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlass(String value) {
        this.klass = value;
    }

    /**
     * Gets the value of the paralleel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParalleel() {
        return paralleel;
    }

    /**
     * Sets the value of the paralleel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParalleel(String value) {
        this.paralleel = value;
    }

}
