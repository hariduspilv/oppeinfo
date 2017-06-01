
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for esrPedagoog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="esrPedagoog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="asutusId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="spordialaKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="distsipliinKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="perenimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synnikp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "esrPedagoog", propOrder = {
    "asutusId",
    "oppekavaKood",
    "spordialaKood",
    "distsipliinKood",
    "isikukood",
    "eesnimi",
    "perenimi",
    "synnikp"
})
public class EsrPedagoog {

    @XmlElement(required = true)
    protected BigInteger asutusId;
    @XmlElement(required = true)
    protected BigInteger oppekavaKood;
    @XmlElement(required = true)
    protected String spordialaKood;
    protected String distsipliinKood;
    protected String isikukood;
    @XmlElement(required = true)
    protected String eesnimi;
    @XmlElement(required = true)
    protected String perenimi;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synnikp;

    /**
     * Gets the value of the asutusId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAsutusId() {
        return asutusId;
    }

    /**
     * Sets the value of the asutusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAsutusId(BigInteger value) {
        this.asutusId = value;
    }

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekavaKood(BigInteger value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the spordialaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpordialaKood() {
        return spordialaKood;
    }

    /**
     * Sets the value of the spordialaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpordialaKood(String value) {
        this.spordialaKood = value;
    }

    /**
     * Gets the value of the distsipliinKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistsipliinKood() {
        return distsipliinKood;
    }

    /**
     * Sets the value of the distsipliinKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistsipliinKood(String value) {
        this.distsipliinKood = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the perenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerenimi() {
        return perenimi;
    }

    /**
     * Sets the value of the perenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerenimi(String value) {
        this.perenimi = value;
    }

    /**
     * Gets the value of the synnikp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynnikp() {
        return synnikp;
    }

    /**
     * Sets the value of the synnikp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynnikp(XMLGregorianCalendar value) {
        this.synnikp = value;
    }

}
