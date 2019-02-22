
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_kommertspant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_kommertspant"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="pandi_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_jarjekoht" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_jarjekoht_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_summa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_valuuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pandi_valuuta_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="algus_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="jarjekohad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kp_jarjekohad" minOccurs="0"/&gt;
 *         &lt;element name="pandisummad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kp_pandisummad" minOccurs="0"/&gt;
 *         &lt;element name="pandipidajad" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kp_pandipidajad" minOccurs="0"/&gt;
 *         &lt;element name="markused" type="{http://arireg.x-road.eu/producer/}detailandmed_v5_kp_markused" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_kommertspant", propOrder = {
    "kirjeId",
    "kaardiPiirkond",
    "kaardiNr",
    "kaardiTyyp",
    "kandeNr",
    "pandiId",
    "pandiNumber",
    "pandiOlek",
    "pandiOlekTekstina",
    "pandiJarjekoht",
    "pandiJarjekohtTekstina",
    "pandiSumma",
    "pandiValuuta",
    "pandiValuutaTekstina",
    "algusKpv",
    "loppKpv",
    "jarjekohad",
    "pandisummad",
    "pandipidajad",
    "markused"
})
public class DetailandmedV5Kommertspant {

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
    @XmlElement(name = "pandi_id")
    protected String pandiId;
    @XmlElement(name = "pandi_number")
    protected String pandiNumber;
    @XmlElement(name = "pandi_olek")
    protected String pandiOlek;
    @XmlElement(name = "pandi_olek_tekstina")
    protected String pandiOlekTekstina;
    @XmlElement(name = "pandi_jarjekoht")
    protected String pandiJarjekoht;
    @XmlElement(name = "pandi_jarjekoht_tekstina")
    protected String pandiJarjekohtTekstina;
    @XmlElement(name = "pandi_summa")
    protected String pandiSumma;
    @XmlElement(name = "pandi_valuuta")
    protected String pandiValuuta;
    @XmlElement(name = "pandi_valuuta_tekstina")
    protected String pandiValuutaTekstina;
    @XmlElement(name = "algus_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKpv;
    @XmlElement(name = "lopp_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKpv;
    protected DetailandmedV5KpJarjekohad jarjekohad;
    protected DetailandmedV5KpPandisummad pandisummad;
    protected DetailandmedV5KpPandipidajad pandipidajad;
    protected DetailandmedV5KpMarkused markused;

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
     * Gets the value of the pandiId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiId() {
        return pandiId;
    }

    /**
     * Sets the value of the pandiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiId(String value) {
        this.pandiId = value;
    }

    /**
     * Gets the value of the pandiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiNumber() {
        return pandiNumber;
    }

    /**
     * Sets the value of the pandiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiNumber(String value) {
        this.pandiNumber = value;
    }

    /**
     * Gets the value of the pandiOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiOlek() {
        return pandiOlek;
    }

    /**
     * Sets the value of the pandiOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiOlek(String value) {
        this.pandiOlek = value;
    }

    /**
     * Gets the value of the pandiOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiOlekTekstina() {
        return pandiOlekTekstina;
    }

    /**
     * Sets the value of the pandiOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiOlekTekstina(String value) {
        this.pandiOlekTekstina = value;
    }

    /**
     * Gets the value of the pandiJarjekoht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiJarjekoht() {
        return pandiJarjekoht;
    }

    /**
     * Sets the value of the pandiJarjekoht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiJarjekoht(String value) {
        this.pandiJarjekoht = value;
    }

    /**
     * Gets the value of the pandiJarjekohtTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiJarjekohtTekstina() {
        return pandiJarjekohtTekstina;
    }

    /**
     * Sets the value of the pandiJarjekohtTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiJarjekohtTekstina(String value) {
        this.pandiJarjekohtTekstina = value;
    }

    /**
     * Gets the value of the pandiSumma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiSumma() {
        return pandiSumma;
    }

    /**
     * Sets the value of the pandiSumma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiSumma(String value) {
        this.pandiSumma = value;
    }

    /**
     * Gets the value of the pandiValuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiValuuta() {
        return pandiValuuta;
    }

    /**
     * Sets the value of the pandiValuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiValuuta(String value) {
        this.pandiValuuta = value;
    }

    /**
     * Gets the value of the pandiValuutaTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPandiValuutaTekstina() {
        return pandiValuutaTekstina;
    }

    /**
     * Sets the value of the pandiValuutaTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPandiValuutaTekstina(String value) {
        this.pandiValuutaTekstina = value;
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

    /**
     * Gets the value of the jarjekohad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KpJarjekohad }
     *     
     */
    public DetailandmedV5KpJarjekohad getJarjekohad() {
        return jarjekohad;
    }

    /**
     * Sets the value of the jarjekohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KpJarjekohad }
     *     
     */
    public void setJarjekohad(DetailandmedV5KpJarjekohad value) {
        this.jarjekohad = value;
    }

    /**
     * Gets the value of the pandisummad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KpPandisummad }
     *     
     */
    public DetailandmedV5KpPandisummad getPandisummad() {
        return pandisummad;
    }

    /**
     * Sets the value of the pandisummad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KpPandisummad }
     *     
     */
    public void setPandisummad(DetailandmedV5KpPandisummad value) {
        this.pandisummad = value;
    }

    /**
     * Gets the value of the pandipidajad property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KpPandipidajad }
     *     
     */
    public DetailandmedV5KpPandipidajad getPandipidajad() {
        return pandipidajad;
    }

    /**
     * Sets the value of the pandipidajad property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KpPandipidajad }
     *     
     */
    public void setPandipidajad(DetailandmedV5KpPandipidajad value) {
        this.pandipidajad = value;
    }

    /**
     * Gets the value of the markused property.
     * 
     * @return
     *     possible object is
     *     {@link DetailandmedV5KpMarkused }
     *     
     */
    public DetailandmedV5KpMarkused getMarkused() {
        return markused;
    }

    /**
     * Sets the value of the markused property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailandmedV5KpMarkused }
     *     
     */
    public void setMarkused(DetailandmedV5KpMarkused value) {
        this.markused = value;
    }

}
