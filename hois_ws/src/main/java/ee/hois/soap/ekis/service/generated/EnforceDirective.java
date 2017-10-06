
package ee.hois.soap.ekis.service.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="preamble" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="directiveNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="directiveDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="signerIDCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="signerName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "directiveNumber",
    "directiveDate",
    "signerIDCode",
    "signerName"
})
@XmlRootElement(name = "enforceDirective")
public class EnforceDirective {

    @XmlElement(required = true)
    protected String uid;
    protected long oisDirectiveId;
    protected long wdDirectiveId;
    @XmlElement(required = true)
    protected String preamble;
    @XmlElement(required = true)
    protected String directiveNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar directiveDate;
    @XmlElement(required = true)
    protected String signerIDCode;
    @XmlElement(required = true)
    protected String signerName;

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
     * Gets the value of the directiveNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectiveNumber() {
        return directiveNumber;
    }

    /**
     * Sets the value of the directiveNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectiveNumber(String value) {
        this.directiveNumber = value;
    }

    /**
     * Gets the value of the directiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDirectiveDate() {
        return directiveDate;
    }

    /**
     * Sets the value of the directiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDirectiveDate(XMLGregorianCalendar value) {
        this.directiveDate = value;
    }

    /**
     * Gets the value of the signerIDCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerIDCode() {
        return signerIDCode;
    }

    /**
     * Sets the value of the signerIDCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerIDCode(String value) {
        this.signerIDCode = value;
    }

    /**
     * Gets the value of the signerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerName() {
        return signerName;
    }

    /**
     * Sets the value of the signerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerName(String value) {
        this.signerName = value;
    }

}
