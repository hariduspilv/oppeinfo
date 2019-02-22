
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_v6_isik complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_v6_isik"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fyysilise_isiku_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_perenimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukoodi_riik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_synniaeg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_roll" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_roll_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ainuesindusoigus_olemas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_v6_isik", propOrder = {
    "fyysiliseIsikuEesnimi",
    "fyysiliseIsikuPerenimi",
    "fyysiliseIsikuKood",
    "isikukoodRiik",
    "isikukoodiRiikTekstina",
    "fyysiliseIsikuSynniaeg",
    "fyysiliseIsikuRoll",
    "fyysiliseIsikuRollTekstina",
    "ainuesindusoigusOlemas"
})
public class ParingesindusV6Isik {

    @XmlElement(name = "fyysilise_isiku_eesnimi")
    protected String fyysiliseIsikuEesnimi;
    @XmlElement(name = "fyysilise_isiku_perenimi")
    protected String fyysiliseIsikuPerenimi;
    @XmlElement(name = "fyysilise_isiku_kood")
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "isikukood_riik")
    protected String isikukoodRiik;
    @XmlElement(name = "isikukoodi_riik_tekstina")
    protected String isikukoodiRiikTekstina;
    @XmlElement(name = "fyysilise_isiku_synniaeg")
    protected String fyysiliseIsikuSynniaeg;
    @XmlElement(name = "fyysilise_isiku_roll")
    protected String fyysiliseIsikuRoll;
    @XmlElement(name = "fyysilise_isiku_roll_tekstina")
    protected String fyysiliseIsikuRollTekstina;
    @XmlElement(name = "ainuesindusoigus_olemas")
    protected String ainuesindusoigusOlemas;

    /**
     * Gets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuEesnimi() {
        return fyysiliseIsikuEesnimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuEesnimi(String value) {
        this.fyysiliseIsikuEesnimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuPerenimi() {
        return fyysiliseIsikuPerenimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuPerenimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuPerenimi(String value) {
        this.fyysiliseIsikuPerenimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKood() {
        return fyysiliseIsikuKood;
    }

    /**
     * Sets the value of the fyysiliseIsikuKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKood(String value) {
        this.fyysiliseIsikuKood = value;
    }

    /**
     * Gets the value of the isikukoodRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodRiik() {
        return isikukoodRiik;
    }

    /**
     * Sets the value of the isikukoodRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodRiik(String value) {
        this.isikukoodRiik = value;
    }

    /**
     * Gets the value of the isikukoodiRiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodiRiikTekstina() {
        return isikukoodiRiikTekstina;
    }

    /**
     * Sets the value of the isikukoodiRiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodiRiikTekstina(String value) {
        this.isikukoodiRiikTekstina = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuSynniaeg() {
        return fyysiliseIsikuSynniaeg;
    }

    /**
     * Sets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuSynniaeg(String value) {
        this.fyysiliseIsikuSynniaeg = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuRoll property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuRoll() {
        return fyysiliseIsikuRoll;
    }

    /**
     * Sets the value of the fyysiliseIsikuRoll property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuRoll(String value) {
        this.fyysiliseIsikuRoll = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuRollTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuRollTekstina() {
        return fyysiliseIsikuRollTekstina;
    }

    /**
     * Sets the value of the fyysiliseIsikuRollTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuRollTekstina(String value) {
        this.fyysiliseIsikuRollTekstina = value;
    }

    /**
     * Gets the value of the ainuesindusoigusOlemas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAinuesindusoigusOlemas() {
        return ainuesindusoigusOlemas;
    }

    /**
     * Sets the value of the ainuesindusoigusOlemas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAinuesindusoigusOlemas(String value) {
        this.ainuesindusoigusOlemas = value;
    }

}
