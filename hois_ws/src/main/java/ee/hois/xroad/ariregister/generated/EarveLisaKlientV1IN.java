
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for earveLisaKlient_v1_IN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="earveLisaKlient_v1_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="earve_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="earve_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kliendid" type="{http://arireg.x-road.eu/producer/}earveLisaKlient_v1_Kliendid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "earveLisaKlient_v1_IN", propOrder = {
    "earveKasutajanimi",
    "earveParool",
    "kliendid"
})
public class EarveLisaKlientV1IN {

    @XmlElement(name = "earve_kasutajanimi")
    protected String earveKasutajanimi;
    @XmlElement(name = "earve_parool")
    protected String earveParool;
    @XmlElement(required = true)
    protected EarveLisaKlientV1Kliendid kliendid;

    /**
     * Gets the value of the earveKasutajanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarveKasutajanimi() {
        return earveKasutajanimi;
    }

    /**
     * Sets the value of the earveKasutajanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarveKasutajanimi(String value) {
        this.earveKasutajanimi = value;
    }

    /**
     * Gets the value of the earveParool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarveParool() {
        return earveParool;
    }

    /**
     * Sets the value of the earveParool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarveParool(String value) {
        this.earveParool = value;
    }

    /**
     * Gets the value of the kliendid property.
     * 
     * @return
     *     possible object is
     *     {@link EarveLisaKlientV1Kliendid }
     *     
     */
    public EarveLisaKlientV1Kliendid getKliendid() {
        return kliendid;
    }

    /**
     * Sets the value of the kliendid property.
     * 
     * @param value
     *     allowed object is
     *     {@link EarveLisaKlientV1Kliendid }
     *     
     */
    public void setKliendid(EarveLisaKlientV1Kliendid value) {
        this.kliendid = value;
    }

}
