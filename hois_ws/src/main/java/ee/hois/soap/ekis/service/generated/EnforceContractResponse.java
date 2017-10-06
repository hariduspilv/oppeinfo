
package ee.hois.soap.ekis.service.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="oisContractId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="wdContractId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "oisContractId",
    "wdContractId",
    "status"
})
@XmlRootElement(name = "enforceContractResponse")
public class EnforceContractResponse {

    protected long oisContractId;
    protected long wdContractId;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the oisContractId property.
     * 
     */
    public long getOisContractId() {
        return oisContractId;
    }

    /**
     * Sets the value of the oisContractId property.
     * 
     */
    public void setOisContractId(long value) {
        this.oisContractId = value;
    }

    /**
     * Gets the value of the wdContractId property.
     * 
     */
    public long getWdContractId() {
        return wdContractId;
    }

    /**
     * Sets the value of the wdContractId property.
     * 
     */
    public void setWdContractId(long value) {
        this.wdContractId = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
