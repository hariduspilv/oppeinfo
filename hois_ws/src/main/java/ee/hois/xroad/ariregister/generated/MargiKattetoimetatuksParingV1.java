
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MargiKattetoimetatuksParing_v1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MargiKattetoimetatuksParing_v1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maaruse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="esitaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_auth" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MargiKattetoimetatuksParing_v1", propOrder = {
    "ettevotjaId",
    "maaruseId",
    "esitajaNimi",
    "esitajaEesnimi",
    "esitajaKood",
    "esitajaAuth"
})
public class MargiKattetoimetatuksParingV1 {

    @XmlElement(name = "ettevotja_id", required = true)
    protected BigInteger ettevotjaId;
    @XmlElement(name = "maaruse_id", required = true)
    protected BigInteger maaruseId;
    @XmlElement(name = "esitaja_nimi", required = true)
    protected String esitajaNimi;
    @XmlElement(name = "esitaja_eesnimi", required = true)
    protected String esitajaEesnimi;
    @XmlElement(name = "esitaja_kood", required = true)
    protected String esitajaKood;
    @XmlElement(name = "esitaja_auth", required = true)
    protected String esitajaAuth;

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttevotjaId(BigInteger value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the maaruseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaaruseId() {
        return maaruseId;
    }

    /**
     * Sets the value of the maaruseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaaruseId(BigInteger value) {
        this.maaruseId = value;
    }

    /**
     * Gets the value of the esitajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaNimi() {
        return esitajaNimi;
    }

    /**
     * Sets the value of the esitajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaNimi(String value) {
        this.esitajaNimi = value;
    }

    /**
     * Gets the value of the esitajaEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaEesnimi() {
        return esitajaEesnimi;
    }

    /**
     * Sets the value of the esitajaEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaEesnimi(String value) {
        this.esitajaEesnimi = value;
    }

    /**
     * Gets the value of the esitajaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaKood() {
        return esitajaKood;
    }

    /**
     * Sets the value of the esitajaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaKood(String value) {
        this.esitajaKood = value;
    }

    /**
     * Gets the value of the esitajaAuth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaAuth() {
        return esitajaAuth;
    }

    /**
     * Sets the value of the esitajaAuth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaAuth(String value) {
        this.esitajaAuth = value;
    }

}
