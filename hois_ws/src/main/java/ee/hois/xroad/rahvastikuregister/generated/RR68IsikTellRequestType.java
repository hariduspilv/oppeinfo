
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR68_isik_tellRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR68_isik_tellRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cValjad" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cFailiSisu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR68_isik_tellRequestType", propOrder = {
    "cValjad",
    "cFailiSisu"
})
public class RR68IsikTellRequestType {

    @XmlElement(required = true)
    protected String cValjad;
    @XmlElement(required = true)
    protected String cFailiSisu;

    /**
     * Gets the value of the cValjad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCValjad() {
        return cValjad;
    }

    /**
     * Sets the value of the cValjad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCValjad(String value) {
        this.cValjad = value;
    }

    /**
     * Gets the value of the cFailiSisu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFailiSisu() {
        return cFailiSisu;
    }

    /**
     * Sets the value of the cFailiSisu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFailiSisu(String value) {
        this.cFailiSisu = value;
    }

}
