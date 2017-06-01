
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eeIsikukaartOppekavaTaitmine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeIsikukaartOppekavaTaitmine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="protsent" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="otsusKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeIsikukaartOppekavaTaitmine", propOrder = {
    "protsent",
    "otsusKp"
})
public class EeIsikukaartOppekavaTaitmine {

    protected Double protsent;
    protected String otsusKp;

    /**
     * Gets the value of the protsent property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProtsent() {
        return protsent;
    }

    /**
     * Sets the value of the protsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProtsent(Double value) {
        this.protsent = value;
    }

    /**
     * Gets the value of the otsusKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsusKp() {
        return otsusKp;
    }

    /**
     * Sets the value of the otsusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsusKp(String value) {
        this.otsusKp = value;
    }

}
