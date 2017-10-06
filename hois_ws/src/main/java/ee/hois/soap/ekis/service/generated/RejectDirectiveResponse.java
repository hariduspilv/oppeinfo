
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
 *         &lt;element name="oisDirectiveId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="wdDirectiveId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
    "oisDirectiveId",
    "wdDirectiveId",
    "status"
})
@XmlRootElement(name = "rejectDirectiveResponse")
public class RejectDirectiveResponse {

    protected long oisDirectiveId;
    protected long wdDirectiveId;
    @XmlElement(required = true)
    protected String status;

    /**
     * Gets the value of the oisDirectiveId property.
     * 
     */
    public long getOisDirectiveId() {
        return oisDirectiveId;
    }

    /**
     * Sets the value of the oisDirectiveId property.
     * 
     */
    public void setOisDirectiveId(long value) {
        this.oisDirectiveId = value;
    }

    /**
     * Gets the value of the wdDirectiveId property.
     * 
     */
    public long getWdDirectiveId() {
        return wdDirectiveId;
    }

    /**
     * Sets the value of the wdDirectiveId property.
     * 
     */
    public void setWdDirectiveId(long value) {
        this.wdDirectiveId = value;
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
