
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for toimiku_dokumendid_dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="toimiku_dokumendid_dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="dokumendi_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_tyyp_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_toimik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_toimik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohi_dokumendi_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="menetluse_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="menetluse_algus_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="menetluse_lopp_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_liik_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_olek_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notari_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notari_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notari_isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notari_tehingu_nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notari_tehingu_aasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_paritolu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_paritolu_tekstina" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_failinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dokumendi_suurus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "toimiku_dokumendid_dokument", propOrder = {
    "dokumendiId",
    "dokumendiNumber",
    "dokumendiTyyp",
    "dokumendiTyypTekstina",
    "dokumendiToimik",
    "dokumendiToimikTekstina",
    "pohiDokumendiId",
    "menetluseNumber",
    "menetluseAlgusKuupaev",
    "menetluseLoppKuupaev",
    "dokumendiLiik",
    "dokumendiLiikTekstina",
    "dokumendiOlek",
    "dokumendiOlekTekstina",
    "notariId",
    "notariNimi",
    "notariIsikukood",
    "notariTehinguNr",
    "notariTehinguAasta",
    "dokumendiParitolu",
    "dokumendiParitoluTekstina",
    "dokumendiFailinimi",
    "dokumendiSuurus"
})
public class ToimikuDokumendidDokument {

    @XmlElement(name = "dokumendi_id", required = true)
    protected BigInteger dokumendiId;
    @XmlElement(name = "dokumendi_number")
    protected String dokumendiNumber;
    @XmlElement(name = "dokumendi_tyyp")
    protected String dokumendiTyyp;
    @XmlElement(name = "dokumendi_tyyp_tekstina")
    protected String dokumendiTyypTekstina;
    @XmlElement(name = "dokumendi_toimik")
    protected String dokumendiToimik;
    @XmlElement(name = "dokumendi_toimik_tekstina")
    protected String dokumendiToimikTekstina;
    @XmlElement(name = "pohi_dokumendi_id")
    protected BigInteger pohiDokumendiId;
    @XmlElement(name = "menetluse_number")
    protected String menetluseNumber;
    @XmlElementRef(name = "menetluse_algus_kuupaev", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> menetluseAlgusKuupaev;
    @XmlElementRef(name = "menetluse_lopp_kuupaev", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> menetluseLoppKuupaev;
    @XmlElementRef(name = "dokumendi_liik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiLiik;
    @XmlElementRef(name = "dokumendi_liik_tekstina", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiLiikTekstina;
    @XmlElementRef(name = "dokumendi_olek", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiOlek;
    @XmlElementRef(name = "dokumendi_olek_tekstina", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiOlekTekstina;
    @XmlElementRef(name = "notari_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notariId;
    @XmlElementRef(name = "notari_nimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notariNimi;
    @XmlElementRef(name = "notari_isikukood", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notariIsikukood;
    @XmlElementRef(name = "notari_tehingu_nr", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notariTehinguNr;
    @XmlElementRef(name = "notari_tehingu_aasta", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notariTehinguAasta;
    @XmlElementRef(name = "dokumendi_paritolu", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiParitolu;
    @XmlElementRef(name = "dokumendi_paritolu_tekstina", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiParitoluTekstina;
    @XmlElementRef(name = "dokumendi_failinimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokumendiFailinimi;
    @XmlElementRef(name = "dokumendi_suurus", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> dokumendiSuurus;

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
     * Gets the value of the dokumendiNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiNumber() {
        return dokumendiNumber;
    }

    /**
     * Sets the value of the dokumendiNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiNumber(String value) {
        this.dokumendiNumber = value;
    }

    /**
     * Gets the value of the dokumendiTyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiTyyp() {
        return dokumendiTyyp;
    }

    /**
     * Sets the value of the dokumendiTyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiTyyp(String value) {
        this.dokumendiTyyp = value;
    }

    /**
     * Gets the value of the dokumendiTyypTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiTyypTekstina() {
        return dokumendiTyypTekstina;
    }

    /**
     * Sets the value of the dokumendiTyypTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiTyypTekstina(String value) {
        this.dokumendiTyypTekstina = value;
    }

    /**
     * Gets the value of the dokumendiToimik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiToimik() {
        return dokumendiToimik;
    }

    /**
     * Sets the value of the dokumendiToimik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiToimik(String value) {
        this.dokumendiToimik = value;
    }

    /**
     * Gets the value of the dokumendiToimikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDokumendiToimikTekstina() {
        return dokumendiToimikTekstina;
    }

    /**
     * Sets the value of the dokumendiToimikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDokumendiToimikTekstina(String value) {
        this.dokumendiToimikTekstina = value;
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
     * Gets the value of the menetluseAlgusKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getMenetluseAlgusKuupaev() {
        return menetluseAlgusKuupaev;
    }

    /**
     * Sets the value of the menetluseAlgusKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setMenetluseAlgusKuupaev(JAXBElement<XMLGregorianCalendar> value) {
        this.menetluseAlgusKuupaev = value;
    }

    /**
     * Gets the value of the menetluseLoppKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getMenetluseLoppKuupaev() {
        return menetluseLoppKuupaev;
    }

    /**
     * Sets the value of the menetluseLoppKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setMenetluseLoppKuupaev(JAXBElement<XMLGregorianCalendar> value) {
        this.menetluseLoppKuupaev = value;
    }

    /**
     * Gets the value of the dokumendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiLiik() {
        return dokumendiLiik;
    }

    /**
     * Sets the value of the dokumendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiLiik(JAXBElement<String> value) {
        this.dokumendiLiik = value;
    }

    /**
     * Gets the value of the dokumendiLiikTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiLiikTekstina() {
        return dokumendiLiikTekstina;
    }

    /**
     * Sets the value of the dokumendiLiikTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiLiikTekstina(JAXBElement<String> value) {
        this.dokumendiLiikTekstina = value;
    }

    /**
     * Gets the value of the dokumendiOlek property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiOlek() {
        return dokumendiOlek;
    }

    /**
     * Sets the value of the dokumendiOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiOlek(JAXBElement<String> value) {
        this.dokumendiOlek = value;
    }

    /**
     * Gets the value of the dokumendiOlekTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiOlekTekstina() {
        return dokumendiOlekTekstina;
    }

    /**
     * Sets the value of the dokumendiOlekTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiOlekTekstina(JAXBElement<String> value) {
        this.dokumendiOlekTekstina = value;
    }

    /**
     * Gets the value of the notariId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotariId() {
        return notariId;
    }

    /**
     * Sets the value of the notariId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotariId(JAXBElement<String> value) {
        this.notariId = value;
    }

    /**
     * Gets the value of the notariNimi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotariNimi() {
        return notariNimi;
    }

    /**
     * Sets the value of the notariNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotariNimi(JAXBElement<String> value) {
        this.notariNimi = value;
    }

    /**
     * Gets the value of the notariIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotariIsikukood() {
        return notariIsikukood;
    }

    /**
     * Sets the value of the notariIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotariIsikukood(JAXBElement<String> value) {
        this.notariIsikukood = value;
    }

    /**
     * Gets the value of the notariTehinguNr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotariTehinguNr() {
        return notariTehinguNr;
    }

    /**
     * Sets the value of the notariTehinguNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotariTehinguNr(JAXBElement<String> value) {
        this.notariTehinguNr = value;
    }

    /**
     * Gets the value of the notariTehinguAasta property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotariTehinguAasta() {
        return notariTehinguAasta;
    }

    /**
     * Sets the value of the notariTehinguAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotariTehinguAasta(JAXBElement<String> value) {
        this.notariTehinguAasta = value;
    }

    /**
     * Gets the value of the dokumendiParitolu property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiParitolu() {
        return dokumendiParitolu;
    }

    /**
     * Sets the value of the dokumendiParitolu property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiParitolu(JAXBElement<String> value) {
        this.dokumendiParitolu = value;
    }

    /**
     * Gets the value of the dokumendiParitoluTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiParitoluTekstina() {
        return dokumendiParitoluTekstina;
    }

    /**
     * Sets the value of the dokumendiParitoluTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiParitoluTekstina(JAXBElement<String> value) {
        this.dokumendiParitoluTekstina = value;
    }

    /**
     * Gets the value of the dokumendiFailinimi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokumendiFailinimi() {
        return dokumendiFailinimi;
    }

    /**
     * Sets the value of the dokumendiFailinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokumendiFailinimi(JAXBElement<String> value) {
        this.dokumendiFailinimi = value;
    }

    /**
     * Gets the value of the dokumendiSuurus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getDokumendiSuurus() {
        return dokumendiSuurus;
    }

    /**
     * Sets the value of the dokumendiSuurus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setDokumendiSuurus(JAXBElement<Integer> value) {
        this.dokumendiSuurus = value;
    }

}
