
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for oppekavaOisAktiivne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppekavaOisAktiivne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="vastuvott" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="opivaljundid" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4000"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="avaOkFailUrl" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2048"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppekavaOisAktiivne", propOrder = {
    "oppekavaKood",
    "vastuvott",
    "opivaljundid",
    "avaOkFailUrl"
})
public class OppekavaOisAktiivne {

    @XmlElement(required = true)
    protected BigInteger oppekavaKood;
    protected String vastuvott;
    protected String opivaljundid;
    protected String avaOkFailUrl;

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
     * Gets the value of the vastuvott property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastuvott() {
        return vastuvott;
    }

    /**
     * Sets the value of the vastuvott property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastuvott(String value) {
        this.vastuvott = value;
    }

    /**
     * Gets the value of the opivaljundid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpivaljundid() {
        return opivaljundid;
    }

    /**
     * Sets the value of the opivaljundid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpivaljundid(String value) {
        this.opivaljundid = value;
    }

    /**
     * Gets the value of the avaOkFailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvaOkFailUrl() {
        return avaOkFailUrl;
    }

    /**
     * Sets the value of the avaOkFailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvaOkFailUrl(String value) {
        this.avaOkFailUrl = value;
    }

}
