
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_kp_pandisumma complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_kp_pandisumma"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kirje_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaardi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kande_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="summa" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valuuta_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "detailandmed_v5_kp_pandisumma", propOrder = {
    "kirjeId",
    "kaardiPiirkond",
    "kaardiNr",
    "kaardiTyyp",
    "kandeNr",
    "summa",
    "valuuta",
    "valuutaTekstina",
    "algusKpv",
    "loppKpv"
})
public class DetailandmedV5KpPandisumma {

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
    protected BigDecimal summa;
    protected String valuuta;
    @XmlElement(name = "valuuta_tekstina")
    protected String valuutaTekstina;
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
     * Gets the value of the summa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumma() {
        return summa;
    }

    /**
     * Sets the value of the summa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumma(BigDecimal value) {
        this.summa = value;
    }

    /**
     * Gets the value of the valuuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuuta() {
        return valuuta;
    }

    /**
     * Sets the value of the valuuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuuta(String value) {
        this.valuuta = value;
    }

    /**
     * Gets the value of the valuutaTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuutaTekstina() {
        return valuutaTekstina;
    }

    /**
     * Sets the value of the valuutaTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuutaTekstina(String value) {
        this.valuutaTekstina = value;
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