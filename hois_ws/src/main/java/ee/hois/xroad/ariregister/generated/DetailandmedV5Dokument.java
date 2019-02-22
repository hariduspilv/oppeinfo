
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="esitamise_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="dok_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dok_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notar_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notar_nr" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="piirkond" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="piirkond_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_dokument", propOrder = {
    "number",
    "esitamiseKpv",
    "dokLiik",
    "dokLiikTekstina",
    "aasta",
    "notarId",
    "notarNr",
    "piirkond",
    "piirkondTekstina",
    "olek",
    "olekTekstina"
})
public class DetailandmedV5Dokument {

    protected BigInteger number;
    @XmlElement(name = "esitamise_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitamiseKpv;
    @XmlElement(name = "dok_liik")
    protected String dokLiik;
    @XmlElement(name = "dok_liik_tekstina")
    protected String dokLiikTekstina;
    protected String aasta;
    @XmlElement(name = "notar_id")
    protected String notarId;
    @XmlElement(name = "notar_nr")
    protected BigInteger notarNr;
    protected BigInteger piirkond;
    @XmlElement(name = "piirkond_tekstina")
    protected String piirkondTekstina;
    protected String olek;
    @XmlElement(name = "olek_tekstina")
    protected String olekTekstina;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumber(BigInteger value) {
        this.number = value;
    }

    /**
     * Gets the value of the esitamiseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitamiseKpv() {
        return esitamiseKpv;
    }

    /**
     * Sets the value of the esitamiseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitamiseKpv(XMLGregorianCalendar value) {
        this.esitamiseKpv = value;
    }

    /**
     * Gets the value of the dokLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokLiik() {
        return dokLiik;
    }

    /**
     * Sets the value of the dokLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokLiik(String value) {
        this.dokLiik = value;
    }

    /**
     * Gets the value of the dokLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokLiikTekstina() {
        return dokLiikTekstina;
    }

    /**
     * Sets the value of the dokLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokLiikTekstina(String value) {
        this.dokLiikTekstina = value;
    }

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAasta(String value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the notarId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotarId() {
        return notarId;
    }

    /**
     * Sets the value of the notarId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotarId(String value) {
        this.notarId = value;
    }

    /**
     * Gets the value of the notarNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNotarNr() {
        return notarNr;
    }

    /**
     * Sets the value of the notarNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNotarNr(BigInteger value) {
        this.notarNr = value;
    }

    /**
     * Gets the value of the piirkond property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPiirkond() {
        return piirkond;
    }

    /**
     * Sets the value of the piirkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPiirkond(BigInteger value) {
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
     * Gets the value of the olek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlek() {
        return olek;
    }

    /**
     * Sets the value of the olek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlek(String value) {
        this.olek = value;
    }

    /**
     * Gets the value of the olekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOlekTekstina() {
        return olekTekstina;
    }

    /**
     * Sets the value of the olekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOlekTekstina(String value) {
        this.olekTekstina = value;
    }

}
