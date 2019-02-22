
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveLisaKlient_v1Response_Klient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveLisaKlient_v1Response_Klient"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="staatus" type="{http://arireg.x-road.eu/producer/}enum_earveLisaKlient_v1Response_Klient_Staatus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveLisaKlient_v1Response_Klient", propOrder = {
    "registrikood",
    "staatus"
})
public class EarveLisaKlientV1ResponseKlient {

    @XmlElement(required = true)
    protected BigInteger registrikood;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumEarveLisaKlientV1ResponseKlientStaatus staatus;

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
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link EnumEarveLisaKlientV1ResponseKlientStaatus }
     *     
     */
    public EnumEarveLisaKlientV1ResponseKlientStaatus getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumEarveLisaKlientV1ResponseKlientStaatus }
     *     
     */
    public void setStaatus(EnumEarveLisaKlientV1ResponseKlientStaatus value) {
        this.staatus = value;
    }

}
