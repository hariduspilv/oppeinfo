
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukaart" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}eeIsikukaartBean"/&gt;
 *         &lt;element name="contentOut" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isikukaart",
    "contentOut"
})
@XmlRootElement(name = "eeIsikukaartResponse")
public class EeIsikukaartResponse {

    @XmlElement(required = true)
    protected EeIsikukaartBean isikukaart;
    @XmlSchemaType(name = "anyURI")
    protected String contentOut;

    /**
     * Gets the value of the isikukaart property.
     * 
     * @return
     *     possible object is
     *     {@link EeIsikukaartBean }
     *     
     */
    public EeIsikukaartBean getIsikukaart() {
        return isikukaart;
    }

    /**
     * Sets the value of the isikukaart property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeIsikukaartBean }
     *     
     */
    public void setIsikukaart(EeIsikukaartBean value) {
        this.isikukaart = value;
    }

    /**
     * Gets the value of the contentOut property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentOut() {
        return contentOut;
    }

    /**
     * Sets the value of the contentOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentOut(String value) {
        this.contentOut = value;
    }

}
