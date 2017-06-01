
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saisHinne complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisHinne"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="aineKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aineNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aineHinne" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hindamisskaala" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kohustuslik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tapsustusKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tapsustus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisHinne", propOrder = {
    "aineKood",
    "aineNimi",
    "aineHinne",
    "hindamisskaala",
    "kohustuslik",
    "tapsustusKood",
    "tapsustus"
})
public class SaisHinne {

    @XmlElement(required = true, nillable = true)
    protected String aineKood;
    @XmlElement(required = true, nillable = true)
    protected String aineNimi;
    @XmlElement(required = true, nillable = true)
    protected String aineHinne;
    @XmlElement(required = true, nillable = true)
    protected String hindamisskaala;
    @XmlElement(required = true, nillable = true)
    protected String kohustuslik;
    @XmlElement(required = true, nillable = true)
    protected String tapsustusKood;
    @XmlElement(required = true, nillable = true)
    protected String tapsustus;

    /**
     * Gets the value of the aineKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAineKood() {
        return aineKood;
    }

    /**
     * Sets the value of the aineKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAineKood(String value) {
        this.aineKood = value;
    }

    /**
     * Gets the value of the aineNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAineNimi() {
        return aineNimi;
    }

    /**
     * Sets the value of the aineNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAineNimi(String value) {
        this.aineNimi = value;
    }

    /**
     * Gets the value of the aineHinne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAineHinne() {
        return aineHinne;
    }

    /**
     * Sets the value of the aineHinne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAineHinne(String value) {
        this.aineHinne = value;
    }

    /**
     * Gets the value of the hindamisskaala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHindamisskaala() {
        return hindamisskaala;
    }

    /**
     * Sets the value of the hindamisskaala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHindamisskaala(String value) {
        this.hindamisskaala = value;
    }

    /**
     * Gets the value of the kohustuslik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKohustuslik() {
        return kohustuslik;
    }

    /**
     * Sets the value of the kohustuslik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKohustuslik(String value) {
        this.kohustuslik = value;
    }

    /**
     * Gets the value of the tapsustusKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTapsustusKood() {
        return tapsustusKood;
    }

    /**
     * Sets the value of the tapsustusKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTapsustusKood(String value) {
        this.tapsustusKood = value;
    }

    /**
     * Gets the value of the tapsustus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTapsustus() {
        return tapsustus;
    }

    /**
     * Sets the value of the tapsustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTapsustus(String value) {
        this.tapsustus = value;
    }

}
