
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringesindus_v6_ettevote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringesindus_v6_ettevote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikud" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_isikud"/&gt;
 *         &lt;element name="esindusoiguse_eritingimused" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_eritingimused"/&gt;
 *         &lt;element name="esindusoiguse_grupid" type="{http://arireg.x-road.eu/producer/}paringesindus_v6_grupid"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oiguslik_vorm_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringesindus_v6_ettevote", propOrder = {
    "ariregistriKood",
    "arinimi",
    "staatus",
    "staatusTekstina",
    "isikud",
    "esindusoiguseEritingimused",
    "esindusoiguseGrupid",
    "oiguslikVorm",
    "oiguslikVormTekstina"
})
public class ParingesindusV6Ettevote {

    @XmlElement(name = "ariregistri_kood", required = true, type = Integer.class, nillable = true)
    protected Integer ariregistriKood;
    @XmlElement(required = true)
    protected String arinimi;
    @XmlElement(required = true)
    protected String staatus;
    @XmlElement(name = "staatus_tekstina", required = true)
    protected String staatusTekstina;
    @XmlElement(required = true)
    protected ParingesindusV6Isikud isikud;
    @XmlElement(name = "esindusoiguse_eritingimused", required = true)
    protected ParingesindusV6Eritingimused esindusoiguseEritingimused;
    @XmlElement(name = "esindusoiguse_grupid", required = true)
    protected ParingesindusV6Grupid esindusoiguseGrupid;
    @XmlElement(name = "oiguslik_vorm", required = true)
    protected String oiguslikVorm;
    @XmlElement(name = "oiguslik_vorm_tekstina", required = true)
    protected String oiguslikVormTekstina;

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAriregistriKood(Integer value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimi(String value) {
        this.arinimi = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the staatusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatusTekstina() {
        return staatusTekstina;
    }

    /**
     * Sets the value of the staatusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatusTekstina(String value) {
        this.staatusTekstina = value;
    }

    /**
     * Gets the value of the isikud property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Isikud }
     *     
     */
    public ParingesindusV6Isikud getIsikud() {
        return isikud;
    }

    /**
     * Sets the value of the isikud property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Isikud }
     *     
     */
    public void setIsikud(ParingesindusV6Isikud value) {
        this.isikud = value;
    }

    /**
     * Gets the value of the esindusoiguseEritingimused property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Eritingimused }
     *     
     */
    public ParingesindusV6Eritingimused getEsindusoiguseEritingimused() {
        return esindusoiguseEritingimused;
    }

    /**
     * Sets the value of the esindusoiguseEritingimused property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Eritingimused }
     *     
     */
    public void setEsindusoiguseEritingimused(ParingesindusV6Eritingimused value) {
        this.esindusoiguseEritingimused = value;
    }

    /**
     * Gets the value of the esindusoiguseGrupid property.
     * 
     * @return
     *     possible object is
     *     {@link ParingesindusV6Grupid }
     *     
     */
    public ParingesindusV6Grupid getEsindusoiguseGrupid() {
        return esindusoiguseGrupid;
    }

    /**
     * Sets the value of the esindusoiguseGrupid property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParingesindusV6Grupid }
     *     
     */
    public void setEsindusoiguseGrupid(ParingesindusV6Grupid value) {
        this.esindusoiguseGrupid = value;
    }

    /**
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
    }

    /**
     * Gets the value of the oiguslikVormTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVormTekstina() {
        return oiguslikVormTekstina;
    }

    /**
     * Sets the value of the oiguslikVormTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVormTekstina(String value) {
        this.oiguslikVormTekstina = value;
    }

}
