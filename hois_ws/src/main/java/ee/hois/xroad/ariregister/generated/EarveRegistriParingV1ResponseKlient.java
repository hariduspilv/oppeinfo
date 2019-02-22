
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveRegistriParing_v1Response_Klient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveRegistriParing_v1Response_Klient"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="teenusepakkuja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://arireg.x-road.eu/producer/}enum_earveRegistriParing_v1Response_Klient_Staatus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveRegistriParing_v1Response_Klient", propOrder = {
    "registrikood",
    "nimi",
    "teenusepakkuja",
    "staatus"
})
public class EarveRegistriParingV1ResponseKlient {

    @XmlElement(required = true)
    protected BigInteger registrikood;
    protected String nimi;
    protected String teenusepakkuja;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumEarveRegistriParingV1ResponseKlientStaatus staatus;

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
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the teenusepakkuja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeenusepakkuja() {
        return teenusepakkuja;
    }

    /**
     * Sets the value of the teenusepakkuja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeenusepakkuja(String value) {
        this.teenusepakkuja = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link EnumEarveRegistriParingV1ResponseKlientStaatus }
     *     
     */
    public EnumEarveRegistriParingV1ResponseKlientStaatus getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumEarveRegistriParingV1ResponseKlientStaatus }
     *     
     */
    public void setStaatus(EnumEarveRegistriParingV1ResponseKlientStaatus value) {
        this.staatus = value;
    }

}