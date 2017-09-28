
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
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oisDirectiveId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="wdDirectiveId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="preamble" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="rejectComment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "uid",
    "oisDirectiveId",
    "wdDirectiveId",
    "preamble",
    "rejectComment"
})
@XmlRootElement(name = "rejectDirective")
public class RejectDirective {

    @XmlElement(required = true)
    protected String uid;
    protected long oisDirectiveId;
    protected long wdDirectiveId;
    protected String preamble;
    @XmlElement(required = true)
    protected String rejectComment;

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

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
     * Gets the value of the preamble property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreamble() {
        return preamble;
    }

    /**
     * Sets the value of the preamble property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreamble(String value) {
        this.preamble = value;
    }

    /**
     * Gets the value of the rejectComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRejectComment() {
        return rejectComment;
    }

    /**
     * Sets the value of the rejectComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRejectComment(String value) {
        this.rejectComment = value;
    }

}
