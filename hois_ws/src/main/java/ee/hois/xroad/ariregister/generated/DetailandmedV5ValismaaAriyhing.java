
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for detailandmed_v5_valismaa_ariyhing complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_valismaa_ariyhing"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arinimi" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_arinimed"/&gt;
 *         &lt;element name="asukoht" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_asukohad"/&gt;
 *         &lt;element name="sarnane_eesti_oiguslik_vorm" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="sarnane_eesti_oiguslik_vorm_markus" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="maa_mille_seaduse_alusel_tegutseb" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="maa_mille_seaduse_alusel_tegutseb_markus" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="register" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="registreerimisnumber" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *         &lt;element name="majandusaasta_aruande_avalikustamise_kohustus" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_va_sisud"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_valismaa_ariyhing", propOrder = {
    "arinimi",
    "asukoht",
    "sarnaneEestiOiguslikVorm",
    "sarnaneEestiOiguslikVormMarkus",
    "maaMilleSeaduseAluselTegutseb",
    "maaMilleSeaduseAluselTegutsebMarkus",
    "register",
    "registreerimisnumber",
    "majandusaastaAruandeAvalikustamiseKohustus"
})
public class DetailandmedV5ValismaaAriyhing {

    @XmlElement(required = true)
    protected DetailandmedV5VaArinimed arinimi;
    @XmlElement(required = true)
    protected DetailandmedV5VaAsukohad asukoht;
    @XmlElement(name = "sarnane_eesti_oiguslik_vorm", required = true)
    protected DetailandmedV5VaSisud sarnaneEestiOiguslikVorm;
    @XmlElement(name = "sarnane_eesti_oiguslik_vorm_markus", required = true)
    protected DetailandmedV5VaSisud sarnaneEestiOiguslikVormMarkus;
    @XmlElement(name = "maa_mille_seaduse_alusel_tegutseb", required = true)
    protected DetailandmedV5VaSisud maaMilleSeaduseAluselTegutseb;
    @XmlElement(name = "maa_mille_seaduse_alusel_tegutseb_markus", required = true)
    protected DetailandmedV5VaSisud maaMilleSeaduseAluselTegutsebMarkus;
    @XmlElement(required = true)
    protected DetailandmedV5VaSisud register;
    @XmlElement(required = true)
    protected DetailandmedV5VaSisud registreerimisnumber;
    @XmlElement(name = "majandusaasta_aruande_avalikustamise_kohustus", required = true)
    protected DetailandmedV5VaSisud majandusaastaAruandeAvalikustamiseKohustus;

    /**
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaArinimed }
     *     
     */
    public DetailandmedV5VaArinimed getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaArinimed }
     *     
     */
    public void setArinimi(DetailandmedV5VaArinimed value) {
        this.arinimi = value;
    }

    /**
     * Gets the value of the asukoht property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaAsukohad }
     *     
     */
    public DetailandmedV5VaAsukohad getAsukoht() {
        return asukoht;
    }

    /**
     * Sets the value of the asukoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaAsukohad }
     *     
     */
    public void setAsukoht(DetailandmedV5VaAsukohad value) {
        this.asukoht = value;
    }

    /**
     * Gets the value of the sarnaneEestiOiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getSarnaneEestiOiguslikVorm() {
        return sarnaneEestiOiguslikVorm;
    }

    /**
     * Sets the value of the sarnaneEestiOiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setSarnaneEestiOiguslikVorm(DetailandmedV5VaSisud value) {
        this.sarnaneEestiOiguslikVorm = value;
    }

    /**
     * Gets the value of the sarnaneEestiOiguslikVormMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getSarnaneEestiOiguslikVormMarkus() {
        return sarnaneEestiOiguslikVormMarkus;
    }

    /**
     * Sets the value of the sarnaneEestiOiguslikVormMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setSarnaneEestiOiguslikVormMarkus(DetailandmedV5VaSisud value) {
        this.sarnaneEestiOiguslikVormMarkus = value;
    }

    /**
     * Gets the value of the maaMilleSeaduseAluselTegutseb property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getMaaMilleSeaduseAluselTegutseb() {
        return maaMilleSeaduseAluselTegutseb;
    }

    /**
     * Sets the value of the maaMilleSeaduseAluselTegutseb property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setMaaMilleSeaduseAluselTegutseb(DetailandmedV5VaSisud value) {
        this.maaMilleSeaduseAluselTegutseb = value;
    }

    /**
     * Gets the value of the maaMilleSeaduseAluselTegutsebMarkus property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getMaaMilleSeaduseAluselTegutsebMarkus() {
        return maaMilleSeaduseAluselTegutsebMarkus;
    }

    /**
     * Sets the value of the maaMilleSeaduseAluselTegutsebMarkus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setMaaMilleSeaduseAluselTegutsebMarkus(DetailandmedV5VaSisud value) {
        this.maaMilleSeaduseAluselTegutsebMarkus = value;
    }

    /**
     * Gets the value of the register property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getRegister() {
        return register;
    }

    /**
     * Sets the value of the register property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setRegister(DetailandmedV5VaSisud value) {
        this.register = value;
    }

    /**
     * Gets the value of the registreerimisnumber property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getRegistreerimisnumber() {
        return registreerimisnumber;
    }

    /**
     * Sets the value of the registreerimisnumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setRegistreerimisnumber(DetailandmedV5VaSisud value) {
        this.registreerimisnumber = value;
    }

    /**
     * Gets the value of the majandusaastaAruandeAvalikustamiseKohustus property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public DetailandmedV5VaSisud getMajandusaastaAruandeAvalikustamiseKohustus() {
        return majandusaastaAruandeAvalikustamiseKohustus;
    }

    /**
     * Sets the value of the majandusaastaAruandeAvalikustamiseKohustus property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5VaSisud }
     *     
     */
    public void setMajandusaastaAruandeAvalikustamiseKohustus(DetailandmedV5VaSisud value) {
        this.majandusaastaAruandeAvalikustamiseKohustus = value;
    }

}
