
package ee.hois.xroad.ehis.generated;

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
 * <p>Java class for tegevuslubaDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tegevuslubaDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="taotlusId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="tyyp" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="klLiik" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="klStaatus" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="laagriNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kohtadeArvLaagris" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klTkkLiik" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klEkTase" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="klSoidukiKategooria" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="loaNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisainfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kaskkirjaNr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kaskkirjaKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loomiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="peatatudKuniKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyhistamiseKp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivAlates" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivAlatesDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtivKuni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kehtivKuniDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppetasemed" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oppekavaOppetasemed" minOccurs="0"/&gt;
 *         &lt;element name="opperyhmad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}opperyhmad" minOccurs="0"/&gt;
 *         &lt;element name="aadressid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}aadressid" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tegevuslubaDetail", propOrder = {
    "id",
    "taotlusId",
    "tyyp",
    "klLiik",
    "klStaatus",
    "nimetus",
    "laagriNimetus",
    "kohtadeArvLaagris",
    "klTkkLiik",
    "klEkTase",
    "klSoidukiKategooria",
    "loaNumber",
    "lisainfo",
    "kaskkirjaNr",
    "kaskkirjaKp",
    "loomiseKp",
    "peatatudKuniKp",
    "tyhistamiseKp",
    "kehtivAlates",
    "kehtivAlatesDate",
    "kehtivKuni",
    "kehtivKuniDate",
    "oppetasemed",
    "opperyhmad",
    "aadressid"
})
public class TegevuslubaDetail {

    protected BigInteger id;
    @XmlElementRef(name = "taotlusId", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> taotlusId;
    protected int tyyp;
    @XmlElement(required = true)
    protected BigInteger klLiik;
    protected BigInteger klStaatus;
    protected String nimetus;
    protected String laagriNimetus;
    @XmlElementRef(name = "kohtadeArvLaagris", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> kohtadeArvLaagris;
    protected BigInteger klTkkLiik;
    protected BigInteger klEkTase;
    protected BigInteger klSoidukiKategooria;
    protected String loaNumber;
    protected String lisainfo;
    protected String kaskkirjaNr;
    protected String kaskkirjaKp;
    protected String loomiseKp;
    protected String peatatudKuniKp;
    protected String tyhistamiseKp;
    protected String kehtivAlates;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kehtivAlatesDate;
    protected String kehtivKuni;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kehtivKuniDate;
    protected OppekavaOppetasemed oppetasemed;
    protected Opperyhmad opperyhmad;
    protected Aadressid aadressid;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the taotlusId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getTaotlusId() {
        return taotlusId;
    }

    /**
     * Sets the value of the taotlusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setTaotlusId(JAXBElement<BigInteger> value) {
        this.taotlusId = value;
    }

    /**
     * Gets the value of the tyyp property.
     * 
     */
    public int getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     */
    public void setTyyp(int value) {
        this.tyyp = value;
    }

    /**
     * Gets the value of the klLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlLiik() {
        return klLiik;
    }

    /**
     * Sets the value of the klLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlLiik(BigInteger value) {
        this.klLiik = value;
    }

    /**
     * Gets the value of the klStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlStaatus() {
        return klStaatus;
    }

    /**
     * Sets the value of the klStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlStaatus(BigInteger value) {
        this.klStaatus = value;
    }

    /**
     * Gets the value of the nimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimetus() {
        return nimetus;
    }

    /**
     * Sets the value of the nimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimetus(String value) {
        this.nimetus = value;
    }

    /**
     * Gets the value of the laagriNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLaagriNimetus() {
        return laagriNimetus;
    }

    /**
     * Sets the value of the laagriNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLaagriNimetus(String value) {
        this.laagriNimetus = value;
    }

    /**
     * Gets the value of the kohtadeArvLaagris property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getKohtadeArvLaagris() {
        return kohtadeArvLaagris;
    }

    /**
     * Sets the value of the kohtadeArvLaagris property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setKohtadeArvLaagris(JAXBElement<BigInteger> value) {
        this.kohtadeArvLaagris = value;
    }

    /**
     * Gets the value of the klTkkLiik property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlTkkLiik() {
        return klTkkLiik;
    }

    /**
     * Sets the value of the klTkkLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlTkkLiik(BigInteger value) {
        this.klTkkLiik = value;
    }

    /**
     * Gets the value of the klEkTase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlEkTase() {
        return klEkTase;
    }

    /**
     * Sets the value of the klEkTase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlEkTase(BigInteger value) {
        this.klEkTase = value;
    }

    /**
     * Gets the value of the klSoidukiKategooria property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKlSoidukiKategooria() {
        return klSoidukiKategooria;
    }

    /**
     * Sets the value of the klSoidukiKategooria property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKlSoidukiKategooria(BigInteger value) {
        this.klSoidukiKategooria = value;
    }

    /**
     * Gets the value of the loaNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoaNumber() {
        return loaNumber;
    }

    /**
     * Sets the value of the loaNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoaNumber(String value) {
        this.loaNumber = value;
    }

    /**
     * Gets the value of the lisainfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisainfo() {
        return lisainfo;
    }

    /**
     * Sets the value of the lisainfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisainfo(String value) {
        this.lisainfo = value;
    }

    /**
     * Gets the value of the kaskkirjaNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaskkirjaNr() {
        return kaskkirjaNr;
    }

    /**
     * Sets the value of the kaskkirjaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaskkirjaNr(String value) {
        this.kaskkirjaNr = value;
    }

    /**
     * Gets the value of the kaskkirjaKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKaskkirjaKp() {
        return kaskkirjaKp;
    }

    /**
     * Sets the value of the kaskkirjaKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKaskkirjaKp(String value) {
        this.kaskkirjaKp = value;
    }

    /**
     * Gets the value of the loomiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoomiseKp() {
        return loomiseKp;
    }

    /**
     * Sets the value of the loomiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoomiseKp(String value) {
        this.loomiseKp = value;
    }

    /**
     * Gets the value of the peatatudKuniKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeatatudKuniKp() {
        return peatatudKuniKp;
    }

    /**
     * Sets the value of the peatatudKuniKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeatatudKuniKp(String value) {
        this.peatatudKuniKp = value;
    }

    /**
     * Gets the value of the tyhistamiseKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyhistamiseKp() {
        return tyhistamiseKp;
    }

    /**
     * Sets the value of the tyhistamiseKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyhistamiseKp(String value) {
        this.tyhistamiseKp = value;
    }

    /**
     * Gets the value of the kehtivAlates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivAlates() {
        return kehtivAlates;
    }

    /**
     * Sets the value of the kehtivAlates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivAlates(String value) {
        this.kehtivAlates = value;
    }

    /**
     * Gets the value of the kehtivAlatesDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKehtivAlatesDate() {
        return kehtivAlatesDate;
    }

    /**
     * Sets the value of the kehtivAlatesDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKehtivAlatesDate(XMLGregorianCalendar value) {
        this.kehtivAlatesDate = value;
    }

    /**
     * Gets the value of the kehtivKuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKehtivKuni() {
        return kehtivKuni;
    }

    /**
     * Sets the value of the kehtivKuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtivKuni(String value) {
        this.kehtivKuni = value;
    }

    /**
     * Gets the value of the kehtivKuniDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKehtivKuniDate() {
        return kehtivKuniDate;
    }

    /**
     * Sets the value of the kehtivKuniDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKehtivKuniDate(XMLGregorianCalendar value) {
        this.kehtivKuniDate = value;
    }

    /**
     * Gets the value of the oppetasemed property.
     * 
     * @return
     *     possible object is
     *     {@link OppekavaOppetasemed }
     *     
     */
    public OppekavaOppetasemed getOppetasemed() {
        return oppetasemed;
    }

    /**
     * Sets the value of the oppetasemed property.
     * 
     * @param value
     *     allowed object is
     *     {@link OppekavaOppetasemed }
     *     
     */
    public void setOppetasemed(OppekavaOppetasemed value) {
        this.oppetasemed = value;
    }

    /**
     * Gets the value of the opperyhmad property.
     * 
     * @return
     *     possible object is
     *     {@link Opperyhmad }
     *     
     */
    public Opperyhmad getOpperyhmad() {
        return opperyhmad;
    }

    /**
     * Sets the value of the opperyhmad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Opperyhmad }
     *     
     */
    public void setOpperyhmad(Opperyhmad value) {
        this.opperyhmad = value;
    }

    /**
     * Gets the value of the aadressid property.
     * 
     * @return
     *     possible object is
     *     {@link Aadressid }
     *     
     */
    public Aadressid getAadressid() {
        return aadressid;
    }

    /**
     * Sets the value of the aadressid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Aadressid }
     *     
     */
    public void setAadressid(Aadressid value) {
        this.aadressid = value;
    }

}
