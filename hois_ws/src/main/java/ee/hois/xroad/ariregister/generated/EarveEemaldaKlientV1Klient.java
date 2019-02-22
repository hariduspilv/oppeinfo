
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveEemaldaKlient_v1_Klient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveEemaldaKlient_v1_Klient"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="kontakt_epost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveEemaldaKlient_v1_Klient", propOrder = {
    "registrikood",
    "kontaktEpost"
})
public class EarveEemaldaKlientV1Klient {

    @XmlElement(required = true)
    protected BigInteger registrikood;
    @XmlElement(name = "kontakt_epost")
    protected String kontaktEpost;

    /**
     * Gets the value of the registrikood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRegistrikood() {
        return registrikood;
    }

    /**
     * Sets the value of the registrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRegistrikood(BigInteger value) {
        this.registrikood = value;
    }

    /**
     * Gets the value of the kontaktEpost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKontaktEpost() {
        return kontaktEpost;
    }

    /**
     * Sets the value of the kontaktEpost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKontaktEpost(String value) {
        this.kontaktEpost = value;
    }

}
