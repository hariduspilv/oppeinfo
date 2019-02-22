
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ariregistri_toimik_dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ariregistri_toimik_dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pohi_dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="e_dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="menetluse_number" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esit_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dokumendi_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_olek" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="notari_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="notari_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitaja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="notari_tehingu_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="notari_tehingu_aasta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_paritolu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_paritolu_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_failinimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dokumendi_suurus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ariregistri_toimik_dokument", propOrder = {
    "dokumendiId",
    "pohiDokumendiId",
    "eDokumendiId",
    "menetluseNumber",
    "esitKpv",
    "dokumendiLiik",
    "dokumendiLiikTekstina",
    "dokumendiOlek",
    "dokumendiOlekTekstina",
    "notariId",
    "notariNimi",
    "esitajaId",
    "esitajaNimi",
    "notariTehinguNr",
    "notariTehinguAasta",
    "dokumendiParitolu",
    "dokumendiParitoluTekstina",
    "dokumendiFailinimi",
    "dokumendiSuurus"
})
public class AriregistriToimikDokument {

    @XmlElement(name = "dokumendi_id", required = true, nillable = true)
    protected BigInteger dokumendiId;
    @XmlElement(name = "pohi_dokumendi_id", required = true, nillable = true)
    protected BigInteger pohiDokumendiId;
    @XmlElementRef(name = "e_dokumendi_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> eDokumendiId;
    @XmlElement(name = "menetluse_number", required = true, nillable = true)
    protected String menetluseNumber;
    @XmlElement(name = "esit_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitKpv;
    @XmlElement(name = "dokumendi_liik", required = true, nillable = true)
    protected String dokumendiLiik;
    @XmlElement(name = "dokumendi_liik_tekstina", required = true, nillable = true)
    protected String dokumendiLiikTekstina;
    @XmlElement(name = "dokumendi_olek", required = true, nillable = true)
    protected String dokumendiOlek;
    @XmlElement(name = "dokumendi_olek_tekstina", required = true, nillable = true)
    protected String dokumendiOlekTekstina;
    @XmlElement(name = "notari_id", required = true, nillable = true)
    protected String notariId;
    @XmlElement(name = "notari_nimi", required = true, nillable = true)
    protected String notariNimi;
    @XmlElement(name = "esitaja_id", required = true, nillable = true)
    protected String esitajaId;
    @XmlElement(name = "esitaja_nimi", required = true, nillable = true)
    protected String esitajaNimi;
    @XmlElement(name = "notari_tehingu_nr", required = true, nillable = true)
    protected String notariTehinguNr;
    @XmlElement(name = "notari_tehingu_aasta", required = true, nillable = true)
    protected String notariTehinguAasta;
    @XmlElement(name = "dokumendi_paritolu", required = true, nillable = true)
    protected String dokumendiParitolu;
    @XmlElement(name = "dokumendi_paritolu_tekstina", required = true, nillable = true)
    protected String dokumendiParitoluTekstina;
    @XmlElement(name = "dokumendi_failinimi", required = true, nillable = true)
    protected String dokumendiFailinimi;
    @XmlElement(name = "dokumendi_suurus", required = true, type = Integer.class, nillable = true)
    protected Integer dokumendiSuurus;

    /**
     * Gets the value of the dokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDokumendiId() {
        return dokumendiId;
    }

    /**
     * Sets the value of the dokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDokumendiId(BigInteger value) {
        this.dokumendiId = value;
    }

    /**
     * Gets the value of the pohiDokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPohiDokumendiId() {
        return pohiDokumendiId;
    }

    /**
     * Sets the value of the pohiDokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPohiDokumendiId(BigInteger value) {
        this.pohiDokumendiId = value;
    }

    /**
     * Gets the value of the eDokumendiId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getEDokumendiId() {
        return eDokumendiId;
    }

    /**
     * Sets the value of the eDokumendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setEDokumendiId(JAXBElement<BigInteger> value) {
        this.eDokumendiId = value;
    }

    /**
     * Gets the value of the menetluseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenetluseNumber() {
        return menetluseNumber;
    }

    /**
     * Sets the value of the menetluseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenetluseNumber(String value) {
        this.menetluseNumber = value;
    }

    /**
     * Gets the value of the esitKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitKpv() {
        return esitKpv;
    }

    /**
     * Sets the value of the esitKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitKpv(XMLGregorianCalendar value) {
        this.esitKpv = value;
    }

    /**
     * Gets the value of the dokumendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiLiik() {
        return dokumendiLiik;
    }

    /**
     * Sets the value of the dokumendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiLiik(String value) {
        this.dokumendiLiik = value;
    }

    /**
     * Gets the value of the dokumendiLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiLiikTekstina() {
        return dokumendiLiikTekstina;
    }

    /**
     * Sets the value of the dokumendiLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiLiikTekstina(String value) {
        this.dokumendiLiikTekstina = value;
    }

    /**
     * Gets the value of the dokumendiOlek property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiOlek() {
        return dokumendiOlek;
    }

    /**
     * Sets the value of the dokumendiOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiOlek(String value) {
        this.dokumendiOlek = value;
    }

    /**
     * Gets the value of the dokumendiOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiOlekTekstina() {
        return dokumendiOlekTekstina;
    }

    /**
     * Sets the value of the dokumendiOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiOlekTekstina(String value) {
        this.dokumendiOlekTekstina = value;
    }

    /**
     * Gets the value of the notariId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariId() {
        return notariId;
    }

    /**
     * Sets the value of the notariId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariId(String value) {
        this.notariId = value;
    }

    /**
     * Gets the value of the notariNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariNimi() {
        return notariNimi;
    }

    /**
     * Sets the value of the notariNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariNimi(String value) {
        this.notariNimi = value;
    }

    /**
     * Gets the value of the esitajaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaId() {
        return esitajaId;
    }

    /**
     * Sets the value of the esitajaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaId(String value) {
        this.esitajaId = value;
    }

    /**
     * Gets the value of the esitajaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitajaNimi() {
        return esitajaNimi;
    }

    /**
     * Sets the value of the esitajaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitajaNimi(String value) {
        this.esitajaNimi = value;
    }

    /**
     * Gets the value of the notariTehinguNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariTehinguNr() {
        return notariTehinguNr;
    }

    /**
     * Sets the value of the notariTehinguNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariTehinguNr(String value) {
        this.notariTehinguNr = value;
    }

    /**
     * Gets the value of the notariTehinguAasta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotariTehinguAasta() {
        return notariTehinguAasta;
    }

    /**
     * Sets the value of the notariTehinguAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotariTehinguAasta(String value) {
        this.notariTehinguAasta = value;
    }

    /**
     * Gets the value of the dokumendiParitolu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiParitolu() {
        return dokumendiParitolu;
    }

    /**
     * Sets the value of the dokumendiParitolu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiParitolu(String value) {
        this.dokumendiParitolu = value;
    }

    /**
     * Gets the value of the dokumendiParitoluTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiParitoluTekstina() {
        return dokumendiParitoluTekstina;
    }

    /**
     * Sets the value of the dokumendiParitoluTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiParitoluTekstina(String value) {
        this.dokumendiParitoluTekstina = value;
    }

    /**
     * Gets the value of the dokumendiFailinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiFailinimi() {
        return dokumendiFailinimi;
    }

    /**
     * Sets the value of the dokumendiFailinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiFailinimi(String value) {
        this.dokumendiFailinimi = value;
    }

    /**
     * Gets the value of the dokumendiSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDokumendiSuurus() {
        return dokumendiSuurus;
    }

    /**
     * Sets the value of the dokumendiSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDokumendiSuurus(Integer value) {
        this.dokumendiSuurus = value;
    }

}
