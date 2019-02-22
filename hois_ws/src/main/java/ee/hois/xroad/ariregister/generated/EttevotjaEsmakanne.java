
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ettevotja_esmakanne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_esmakanne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_esmakanne", propOrder = {
    "ariregistriKood",
    "oiguslikVorm"
})
public class EttevotjaEsmakanne {

    @XmlElement(name = "ariregistri_kood")
    protected Integer ariregistriKood;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAriregistriKood(Integer value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
    }

}
