
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paringliht_v5_ettevote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paringliht_v5_ettevote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="evnimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oiguslik_vorm_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oigusliku_vormi_alaliik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtetud_nimed" type="{http://arireg.x-road.eu/producer/}paringliht_v5_kehtetudnimed" minOccurs="0"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="staatus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tegutseb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tegutseb_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="registrist_kustutamise_aeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="piirkond_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="evkapitalid" type="{http://arireg.x-road.eu/producer/}paringliht_v5_evkapital" minOccurs="0"/&gt;
 *         &lt;element name="evaadressid" type="{http://arireg.x-road.eu/producer/}paringliht_v5_evaadress" minOccurs="0"/&gt;
 *         &lt;element name="esmakande_aeg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paringliht_v5_ettevote", propOrder = {
    "evnimi",
    "oiguslikVorm",
    "oiguslikVormTekstina",
    "oiguslikuVormiAlaliik",
    "oiguslikuVormiAlaliikTekstina",
    "kehtetudNimed",
    "ariregistriKood",
    "staatus",
    "staatusTekstina",
    "tegutseb",
    "tegutsebTekstina",
    "registristKustutamiseAeg",
    "piirkond",
    "piirkondTekstina",
    "evkapitalid",
    "evaadressid",
    "esmakandeAeg"
})
public class ParinglihtV5Ettevote {

    @XmlElement(required = true)
    protected String evnimi;
    @XmlElement(name = "oiguslik_vorm")
    protected String oiguslikVorm;
    @XmlElement(name = "oiguslik_vorm_tekstina")
    protected String oiguslikVormTekstina;
    @XmlElement(name = "oigusliku_vormi_alaliik")
    protected String oiguslikuVormiAlaliik;
    @XmlElement(name = "oigusliku_vormi_alaliik_tekstina")
    protected String oiguslikuVormiAlaliikTekstina;
    @XmlElement(name = "kehtetud_nimed")
    protected ParinglihtV5Kehtetudnimed kehtetudNimed;
    @XmlElement(name = "ariregistri_kood")
    protected String ariregistriKood;
    @XmlElement(required = true)
    protected String staatus;
    @XmlElement(name = "staatus_tekstina", required = true)
    protected String staatusTekstina;
    protected String tegutseb;
    @XmlElement(name = "tegutseb_tekstina")
    protected String tegutsebTekstina;
    @XmlElement(name = "registrist_kustutamise_aeg", required = true, nillable = true)
    protected String registristKustutamiseAeg;
    @XmlElement(required = true)
    protected String piirkond;
    @XmlElement(name = "piirkond_tekstina")
    protected String piirkondTekstina;
    @XmlElementRef(name = "evkapitalid", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<ParinglihtV5Evkapital> evkapitalid;
    @XmlElementRef(name = "evaadressid", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<ParinglihtV5Evaadress> evaadressid;
    @XmlElement(name = "esmakande_aeg", required = true, nillable = true)
    protected String esmakandeAeg;

    /**
     * Gets the value of the evnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvnimi() {
        return evnimi;
    }

    /**
     * Sets the value of the evnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvnimi(String value) {
        this.evnimi = value;
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

    /**
     * Gets the value of the oiguslikuVormiAlaliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiAlaliik() {
        return oiguslikuVormiAlaliik;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiAlaliik(String value) {
        this.oiguslikuVormiAlaliik = value;
    }

    /**
     * Gets the value of the oiguslikuVormiAlaliikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikuVormiAlaliikTekstina() {
        return oiguslikuVormiAlaliikTekstina;
    }

    /**
     * Sets the value of the oiguslikuVormiAlaliikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikuVormiAlaliikTekstina(String value) {
        this.oiguslikuVormiAlaliikTekstina = value;
    }

    /**
     * Gets the value of the kehtetudNimed property.
     * 
     * @return
     *     possible object is
     *     {@link ParinglihtV5Kehtetudnimed }
     *     
     */
    public ParinglihtV5Kehtetudnimed getKehtetudNimed() {
        return kehtetudNimed;
    }

    /**
     * Sets the value of the kehtetudNimed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParinglihtV5Kehtetudnimed }
     *     
     */
    public void setKehtetudNimed(ParinglihtV5Kehtetudnimed value) {
        this.kehtetudNimed = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAriregistriKood(String value) {
        this.ariregistriKood = value;
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
     * Gets the value of the tegutseb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegutseb() {
        return tegutseb;
    }

    /**
     * Sets the value of the tegutseb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegutseb(String value) {
        this.tegutseb = value;
    }

    /**
     * Gets the value of the tegutsebTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTegutsebTekstina() {
        return tegutsebTekstina;
    }

    /**
     * Sets the value of the tegutsebTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTegutsebTekstina(String value) {
        this.tegutsebTekstina = value;
    }

    /**
     * Gets the value of the registristKustutamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistristKustutamiseAeg() {
        return registristKustutamiseAeg;
    }

    /**
     * Sets the value of the registristKustutamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistristKustutamiseAeg(String value) {
        this.registristKustutamiseAeg = value;
    }

    /**
     * Gets the value of the piirkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkond() {
        return piirkond;
    }

    /**
     * Sets the value of the piirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkond(String value) {
        this.piirkond = value;
    }

    /**
     * Gets the value of the piirkondTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiirkondTekstina() {
        return piirkondTekstina;
    }

    /**
     * Sets the value of the piirkondTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiirkondTekstina(String value) {
        this.piirkondTekstina = value;
    }

    /**
     * Gets the value of the evkapitalid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ParinglihtV5Evkapital }{@code >}
     *     
     */
    public JAXBElement<ParinglihtV5Evkapital> getEvkapitalid() {
        return evkapitalid;
    }

    /**
     * Sets the value of the evkapitalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ParinglihtV5Evkapital }{@code >}
     *     
     */
    public void setEvkapitalid(JAXBElement<ParinglihtV5Evkapital> value) {
        this.evkapitalid = value;
    }

    /**
     * Gets the value of the evaadressid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ParinglihtV5Evaadress }{@code >}
     *     
     */
    public JAXBElement<ParinglihtV5Evaadress> getEvaadressid() {
        return evaadressid;
    }

    /**
     * Sets the value of the evaadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ParinglihtV5Evaadress }{@code >}
     *     
     */
    public void setEvaadressid(JAXBElement<ParinglihtV5Evaadress> value) {
        this.evaadressid = value;
    }

    /**
     * Gets the value of the esmakandeAeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsmakandeAeg() {
        return esmakandeAeg;
    }

    /**
     * Sets the value of the esmakandeAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsmakandeAeg(String value) {
        this.esmakandeAeg = value;
    }

}
