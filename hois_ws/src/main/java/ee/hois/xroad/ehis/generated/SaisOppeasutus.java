
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for saisOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kooliNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kooliLiik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="indeks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kooliLiikNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="sulgemiseKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisOppeasutus", propOrder = {
    "registrikood",
    "kooliNimi",
    "kooliLiik",
    "aadress",
    "ehak",
    "indeks",
    "kooliLiikNimi",
    "koolId",
    "sulgemiseKp"
})
public class SaisOppeasutus {

    @XmlElement(required = true, nillable = true)
    protected String registrikood;
    @XmlElement(required = true, nillable = true)
    protected String kooliNimi;
    @XmlElement(required = true, nillable = true)
    protected BigInteger kooliLiik;
    @XmlElement(required = true, nillable = true)
    protected String aadress;
    @XmlElement(required = true, nillable = true)
    protected String ehak;
    @XmlElement(required = true, nillable = true)
    protected String indeks;
    @XmlElement(required = true, nillable = true)
    protected String kooliLiikNimi;
    @XmlElement(required = true, nillable = true)
    protected BigInteger koolId;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sulgemiseKp;

    /**
     * Gets the value of the registrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrikood() {
        return registrikood;
    }

    /**
     * Sets the value of the registrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrikood(String value) {
        this.registrikood = value;
    }

    /**
     * Gets the value of the kooliNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliNimi() {
        return kooliNimi;
    }

    /**
     * Sets the value of the kooliNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliNimi(String value) {
        this.kooliNimi = value;
    }

    /**
     * Gets the value of the kooliLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKooliLiik() {
        return kooliLiik;
    }

    /**
     * Sets the value of the kooliLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKooliLiik(BigInteger value) {
        this.kooliLiik = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadress(String value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the ehak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEhak() {
        return ehak;
    }

    /**
     * Sets the value of the ehak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEhak(String value) {
        this.ehak = value;
    }

    /**
     * Gets the value of the indeks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndeks() {
        return indeks;
    }

    /**
     * Sets the value of the indeks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndeks(String value) {
        this.indeks = value;
    }

    /**
     * Gets the value of the kooliLiikNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliLiikNimi() {
        return kooliLiikNimi;
    }

    /**
     * Sets the value of the kooliLiikNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliLiikNimi(String value) {
        this.kooliLiikNimi = value;
    }

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the sulgemiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSulgemiseKp() {
        return sulgemiseKp;
    }

    /**
     * Sets the value of the sulgemiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSulgemiseKp(XMLGregorianCalendar value) {
        this.sulgemiseKp = value;
    }

}
