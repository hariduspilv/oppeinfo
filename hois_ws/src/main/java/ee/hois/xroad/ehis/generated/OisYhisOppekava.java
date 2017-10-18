
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oisYhisOppekava complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oisYhisOppekava"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="regNumberEestiOAS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *           &lt;element name="valisOAS" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisValisOAS" minOccurs="0"/&gt;
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
@XmlType(name = "oisYhisOppekava", propOrder = {
    "regNumberEestiOAS",
    "valisOAS"
})
public class OisYhisOppekava {

    protected String regNumberEestiOAS;
    protected OisValisOAS valisOAS;

    /**
     * Gets the value of the regNumberEestiOAS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegNumberEestiOAS() {
        return regNumberEestiOAS;
    }

    /**
     * Sets the value of the regNumberEestiOAS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegNumberEestiOAS(String value) {
        this.regNumberEestiOAS = value;
    }

    /**
     * Gets the value of the valisOAS property.
     * 
     * @return
     *     possible object is
     *     {@link OisValisOAS }
     *     
     */
    public OisValisOAS getValisOAS() {
        return valisOAS;
    }

    /**
     * Sets the value of the valisOAS property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisValisOAS }
     *     
     */
    public void setValisOAS(OisValisOAS value) {
        this.valisOAS = value;
    }

}
