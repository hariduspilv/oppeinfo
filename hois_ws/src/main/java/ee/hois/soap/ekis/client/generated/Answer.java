
package ee.hois.soap.ekis.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for answer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="answer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ois_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="wd_id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="regno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "answer", propOrder = {
    "oisId",
    "wdId",
    "regno",
    "status"
})
public class Answer {

    @XmlElement(name = "ois_id", required = true)
    protected String oisId;
    @XmlElement(name = "wd_id")
    protected int wdId;
    protected String regno;
    protected String status;

    /**
     * Gets the value of the oisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOisId() {
        return oisId;
    }

    /**
     * Sets the value of the oisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOisId(String value) {
        this.oisId = value;
    }

    /**
     * Gets the value of the wdId property.
     * 
     */
    public int getWdId() {
        return wdId;
    }

    /**
     * Sets the value of the wdId property.
     * 
     */
    public void setWdId(int value) {
        this.wdId = value;
    }

    /**
     * Gets the value of the regno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegno() {
        return regno;
    }

    /**
     * Sets the value of the regno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegno(String value) {
        this.regno = value;
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
