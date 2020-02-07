
package ee.hois.xroad.rahvastikuregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RR71_fail_downloadRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RR71_fail_downloadRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="cFailiNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RR71_fail_downloadRequestType", propOrder = {
    "cFailiNimi"
})
public class RR71FailDownloadRequestType {

    @XmlElement(required = true)
    protected String cFailiNimi;

    /**
     * Gets the value of the cFailiNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCFailiNimi() {
        return cFailiNimi;
    }

    /**
     * Sets the value of the cFailiNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCFailiNimi(String value) {
        this.cFailiNimi = value;
    }

}
