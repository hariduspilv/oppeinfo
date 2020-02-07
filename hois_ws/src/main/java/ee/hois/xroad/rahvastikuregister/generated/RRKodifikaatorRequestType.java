
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RRKodifikaatorRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RRKodifikaatorRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cKodifikaatoriKoodid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RRKodifikaatorRequestType", propOrder = {
    "cKodifikaatoriKoodid"
})
public class RRKodifikaatorRequestType {

    @XmlElement(required = true)
    protected String cKodifikaatoriKoodid;

    /**
     * Gets the value of the cKodifikaatoriKoodid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCKodifikaatoriKoodid() {
        return cKodifikaatoriKoodid;
    }

    /**
     * Sets the value of the cKodifikaatoriKoodid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCKodifikaatoriKoodid(String value) {
        this.cKodifikaatoriKoodid = value;
    }

}
