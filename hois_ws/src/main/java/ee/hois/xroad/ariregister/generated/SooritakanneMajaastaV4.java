
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sooritakanne_majaasta_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sooritakanne_majaasta_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="majandusaasta_algus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="majandusaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sooritakanne_majaasta_v4", propOrder = {
    "majandusaastaAlgus",
    "majandusaastaLopp"
})
public class SooritakanneMajaastaV4 {

    @XmlElement(name = "majandusaasta_algus", required = true)
    protected String majandusaastaAlgus;
    @XmlElement(name = "majandusaasta_lopp", required = true)
    protected String majandusaastaLopp;

    /**
     * Gets the value of the majandusaastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajandusaastaAlgus() {
        return majandusaastaAlgus;
    }

    /**
     * Sets the value of the majandusaastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajandusaastaAlgus(String value) {
        this.majandusaastaAlgus = value;
    }

    /**
     * Gets the value of the majandusaastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajandusaastaLopp() {
        return majandusaastaLopp;
    }

    /**
     * Sets the value of the majandusaastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajandusaastaLopp(String value) {
        this.majandusaastaLopp = value;
    }

}
