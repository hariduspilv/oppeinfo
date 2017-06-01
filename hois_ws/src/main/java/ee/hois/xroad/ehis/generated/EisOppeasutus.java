
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eisOppeasutus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eisOppeasutus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kooliNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kooliLiik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kooliAlamliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="omandivorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="klassiKomplArv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="opilasedArv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppekeeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekeeledList" minOccurs="0"/&gt;
 *         &lt;element name="oppetasemed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppetasemedList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eisOppeasutus", propOrder = {
    "koolId",
    "registrikood",
    "kooliNimi",
    "ehak",
    "aadress",
    "kooliLiik",
    "kooliAlamliik",
    "omandivorm",
    "klassiKomplArv",
    "opilasedArv",
    "oppekeeled",
    "oppetasemed"
})
public class EisOppeasutus {

    @XmlElement(required = true)
    protected BigInteger koolId;
    protected String registrikood;
    @XmlElement(required = true)
    protected String kooliNimi;
    protected String ehak;
    protected String aadress;
    protected String kooliLiik;
    protected String kooliAlamliik;
    protected String omandivorm;
    protected BigInteger klassiKomplArv;
    protected BigInteger opilasedArv;
    protected OppekeeledList oppekeeled;
    protected OppetasemedList oppetasemed;

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
     * Gets the value of the kooliLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliLiik() {
        return kooliLiik;
    }

    /**
     * Sets the value of the kooliLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliLiik(String value) {
        this.kooliLiik = value;
    }

    /**
     * Gets the value of the kooliAlamliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliAlamliik() {
        return kooliAlamliik;
    }

    /**
     * Sets the value of the kooliAlamliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliAlamliik(String value) {
        this.kooliAlamliik = value;
    }

    /**
     * Gets the value of the omandivorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmandivorm() {
        return omandivorm;
    }

    /**
     * Sets the value of the omandivorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmandivorm(String value) {
        this.omandivorm = value;
    }

    /**
     * Gets the value of the klassiKomplArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlassiKomplArv() {
        return klassiKomplArv;
    }

    /**
     * Sets the value of the klassiKomplArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlassiKomplArv(BigInteger value) {
        this.klassiKomplArv = value;
    }

    /**
     * Gets the value of the opilasedArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOpilasedArv() {
        return opilasedArv;
    }

    /**
     * Sets the value of the opilasedArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOpilasedArv(BigInteger value) {
        this.opilasedArv = value;
    }

    /**
     * Gets the value of the oppekeeled property.
     * 
     * @return
     *     possible object is
     *     {@link OppekeeledList }
     *     
     */
    public OppekeeledList getOppekeeled() {
        return oppekeeled;
    }

    /**
     * Sets the value of the oppekeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppekeeledList }
     *     
     */
    public void setOppekeeled(OppekeeledList value) {
        this.oppekeeled = value;
    }

    /**
     * Gets the value of the oppetasemed property.
     * 
     * @return
     *     possible object is
     *     {@link OppetasemedList }
     *     
     */
    public OppetasemedList getOppetasemed() {
        return oppetasemed;
    }

    /**
     * Sets the value of the oppetasemed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppetasemedList }
     *     
     */
    public void setOppetasemed(OppetasemedList value) {
        this.oppetasemed = value;
    }

}
