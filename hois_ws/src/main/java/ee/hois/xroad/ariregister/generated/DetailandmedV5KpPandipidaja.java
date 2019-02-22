
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_kp_pandipidaja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_kp_pandipidaja"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_isikuliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_riik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_asukoht_ehak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_asukoht_ehak_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pidaja_murdosa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_kp_pandipidaja", propOrder = {
    "kirjeId",
    "kaardiPiirkond",
    "kaardiNr",
    "kaardiTyyp",
    "kandeNr",
    "pidajaIsikuliik",
    "pidajaNimi",
    "pidajaKood",
    "pidajaRiik",
    "pidajaRiikTekstina",
    "pidajaAsukohtEhak",
    "pidajaAsukohtEhakTekstina",
    "pidajaMurdosa",
    "algusKpv",
    "loppKpv"
})
public class DetailandmedV5KpPandipidaja {

    @XmlElement(name = "kirje_id")
    protected BigInteger kirjeId;
    @XmlElement(name = "kaardi_piirkond")
    protected BigInteger kaardiPiirkond;
    @XmlElement(name = "kaardi_nr")
    protected BigInteger kaardiNr;
    @XmlElement(name = "kaardi_tyyp")
    protected String kaardiTyyp;
    @XmlElement(name = "kande_nr")
    protected BigInteger kandeNr;
    @XmlElement(name = "pidaja_isikuliik")
    protected String pidajaIsikuliik;
    @XmlElement(name = "pidaja_nimi")
    protected String pidajaNimi;
    @XmlElement(name = "pidaja_kood")
    protected String pidajaKood;
    @XmlElement(name = "pidaja_riik")
    protected String pidajaRiik;
    @XmlElement(name = "pidaja_riik_tekstina")
    protected String pidajaRiikTekstina;
    @XmlElement(name = "pidaja_asukoht_ehak")
    protected String pidajaAsukohtEhak;
    @XmlElement(name = "pidaja_asukoht_ehak_tekstina")
    protected String pidajaAsukohtEhakTekstina;
    @XmlElement(name = "pidaja_murdosa")
    protected String pidajaMurdosa;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;

    /**
     * Gets the value of the kirjeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKirjeId() {
        return kirjeId;
    }

    /**
     * Sets the value of the kirjeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKirjeId(BigInteger value) {
        this.kirjeId = value;
    }

    /**
     * Gets the value of the kaardiPiirkond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaardiPiirkond() {
        return kaardiPiirkond;
    }

    /**
     * Sets the value of the kaardiPiirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaardiPiirkond(BigInteger value) {
        this.kaardiPiirkond = value;
    }

    /**
     * Gets the value of the kaardiNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaardiNr() {
        return kaardiNr;
    }

    /**
     * Sets the value of the kaardiNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaardiNr(BigInteger value) {
        this.kaardiNr = value;
    }

    /**
     * Gets the value of the kaardiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaardiTyyp() {
        return kaardiTyyp;
    }

    /**
     * Sets the value of the kaardiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaardiTyyp(String value) {
        this.kaardiTyyp = value;
    }

    /**
     * Gets the value of the kandeNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKandeNr() {
        return kandeNr;
    }

    /**
     * Sets the value of the kandeNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKandeNr(BigInteger value) {
        this.kandeNr = value;
    }

    /**
     * Gets the value of the pidajaIsikuliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaIsikuliik() {
        return pidajaIsikuliik;
    }

    /**
     * Sets the value of the pidajaIsikuliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaIsikuliik(String value) {
        this.pidajaIsikuliik = value;
    }

    /**
     * Gets the value of the pidajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaNimi() {
        return pidajaNimi;
    }

    /**
     * Sets the value of the pidajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaNimi(String value) {
        this.pidajaNimi = value;
    }

    /**
     * Gets the value of the pidajaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaKood() {
        return pidajaKood;
    }

    /**
     * Sets the value of the pidajaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaKood(String value) {
        this.pidajaKood = value;
    }

    /**
     * Gets the value of the pidajaRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaRiik() {
        return pidajaRiik;
    }

    /**
     * Sets the value of the pidajaRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaRiik(String value) {
        this.pidajaRiik = value;
    }

    /**
     * Gets the value of the pidajaRiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaRiikTekstina() {
        return pidajaRiikTekstina;
    }

    /**
     * Sets the value of the pidajaRiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaRiikTekstina(String value) {
        this.pidajaRiikTekstina = value;
    }

    /**
     * Gets the value of the pidajaAsukohtEhak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaAsukohtEhak() {
        return pidajaAsukohtEhak;
    }

    /**
     * Sets the value of the pidajaAsukohtEhak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaAsukohtEhak(String value) {
        this.pidajaAsukohtEhak = value;
    }

    /**
     * Gets the value of the pidajaAsukohtEhakTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaAsukohtEhakTekstina() {
        return pidajaAsukohtEhakTekstina;
    }

    /**
     * Sets the value of the pidajaAsukohtEhakTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaAsukohtEhakTekstina(String value) {
        this.pidajaAsukohtEhakTekstina = value;
    }

    /**
     * Gets the value of the pidajaMurdosa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPidajaMurdosa() {
        return pidajaMurdosa;
    }

    /**
     * Sets the value of the pidajaMurdosa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPidajaMurdosa(String value) {
        this.pidajaMurdosa = value;
    }

    /**
     * Gets the value of the algusKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKpv() {
        return algusKpv;
    }

    /**
     * Sets the value of the algusKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKpv(XMLGregorianCalendar value) {
        this.algusKpv = value;
    }

    /**
     * Gets the value of the loppKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKpv() {
        return loppKpv;
    }

    /**
     * Sets the value of the loppKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKpv(XMLGregorianCalendar value) {
        this.loppKpv = value;
    }

}
