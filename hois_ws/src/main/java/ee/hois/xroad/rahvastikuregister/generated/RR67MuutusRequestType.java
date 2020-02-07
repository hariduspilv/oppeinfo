
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR67_muutusRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR67_muutusRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cAlgKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cAlgKell" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cLoppKpv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cLoppKell" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cMuutused" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR67_muutusRequestType", propOrder = {
    "cAlgKpv",
    "cAlgKell",
    "cLoppKpv",
    "cLoppKell",
    "cMuutused"
})
public class RR67MuutusRequestType {

    @XmlElement(required = true)
    protected String cAlgKpv;
    @XmlElement(required = true)
    protected String cAlgKell;
    @XmlElement(required = true)
    protected String cLoppKpv;
    @XmlElement(required = true)
    protected String cLoppKell;
    @XmlElement(required = true)
    protected String cMuutused;

    /**
     * Gets the value of the cAlgKpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAlgKpv() {
        return cAlgKpv;
    }

    /**
     * Sets the value of the cAlgKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAlgKpv(String value) {
        this.cAlgKpv = value;
    }

    /**
     * Gets the value of the cAlgKell property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAlgKell() {
        return cAlgKell;
    }

    /**
     * Sets the value of the cAlgKell property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAlgKell(String value) {
        this.cAlgKell = value;
    }

    /**
     * Gets the value of the cLoppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLoppKpv() {
        return cLoppKpv;
    }

    /**
     * Sets the value of the cLoppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLoppKpv(String value) {
        this.cLoppKpv = value;
    }

    /**
     * Gets the value of the cLoppKell property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLoppKell() {
        return cLoppKell;
    }

    /**
     * Sets the value of the cLoppKell property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLoppKell(String value) {
        this.cLoppKell = value;
    }

    /**
     * Gets the value of the cMuutused property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMuutused() {
        return cMuutused;
    }

    /**
     * Sets the value of the cMuutused property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMuutused(String value) {
        this.cMuutused = value;
    }

}
