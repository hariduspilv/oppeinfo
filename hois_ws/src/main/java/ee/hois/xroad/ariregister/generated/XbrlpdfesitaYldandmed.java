
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xbrlpdfesita_yldandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlpdfesita_yldandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vigane_aruanne" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="majaasta_algus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="majaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="konsolideeritud" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="erandid" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="tapsusaste" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="aruande_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="xbrl_aruande_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="paev_nr" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="pteg_emtak" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pteg_emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tootajate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kaive" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="kasum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="bilansimaht" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_tootajate_arv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_kaive" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_kasum" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="konsol_bilansimaht" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="valuuta" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="auditeeritus" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlpdfesita_yldandmed", propOrder = {
    "viganeAruanne",
    "ariregistriKood",
    "majaastaAlgus",
    "majaastaLopp",
    "standard",
    "konsolideeritud",
    "erandid",
    "tapsusaste",
    "aruandeLiik",
    "xbrlAruandeId",
    "paevNr",
    "ptegEmtak",
    "ptegEmtakVersioon",
    "tootajateArv",
    "kaive",
    "kasum",
    "bilansimaht",
    "konsolTootajateArv",
    "konsolKaive",
    "konsolKasum",
    "konsolBilansimaht",
    "valuuta",
    "auditeeritus"
})
public class XbrlpdfesitaYldandmed {

    @XmlElement(name = "vigane_aruanne")
    protected boolean viganeAruanne;
    @XmlElement(name = "ariregistri_kood")
    protected int ariregistriKood;
    @XmlElement(name = "majaasta_algus", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majaastaAlgus;
    @XmlElement(name = "majaasta_lopp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majaastaLopp;
    @XmlElement(required = true)
    protected BigInteger standard;
    protected boolean konsolideeritud;
    @XmlElement(required = true)
    protected BigInteger erandid;
    @XmlElement(required = true)
    protected BigInteger tapsusaste;
    @XmlElement(name = "aruande_liik", required = true)
    protected String aruandeLiik;
    @XmlElement(name = "xbrl_aruande_id", required = true)
    protected BigInteger xbrlAruandeId;
    @XmlElement(name = "paev_nr", required = true)
    protected BigInteger paevNr;
    @XmlElement(name = "pteg_emtak")
    protected String ptegEmtak;
    @XmlElement(name = "pteg_emtak_versioon")
    protected String ptegEmtakVersioon;
    @XmlElement(name = "tootajate_arv")
    protected BigInteger tootajateArv;
    protected BigInteger kaive;
    protected BigInteger kasum;
    protected BigInteger bilansimaht;
    @XmlElement(name = "konsol_tootajate_arv")
    protected BigInteger konsolTootajateArv;
    @XmlElement(name = "konsol_kaive")
    protected BigInteger konsolKaive;
    @XmlElement(name = "konsol_kasum")
    protected BigInteger konsolKasum;
    @XmlElement(name = "konsol_bilansimaht")
    protected BigInteger konsolBilansimaht;
    @XmlElement(required = true)
    protected String valuuta;
    protected Boolean auditeeritus;

    /**
     * Gets the value of the viganeAruanne property.
     * 
     */
    public boolean isViganeAruanne() {
        return viganeAruanne;
    }

    /**
     * Sets the value of the viganeAruanne property.
     * 
     */
    public void setViganeAruanne(boolean value) {
        this.viganeAruanne = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     */
    public int getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     */
    public void setAriregistriKood(int value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the majaastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajaastaAlgus() {
        return majaastaAlgus;
    }

    /**
     * Sets the value of the majaastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajaastaAlgus(XMLGregorianCalendar value) {
        this.majaastaAlgus = value;
    }

    /**
     * Gets the value of the majaastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajaastaLopp() {
        return majaastaLopp;
    }

    /**
     * Sets the value of the majaastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajaastaLopp(XMLGregorianCalendar value) {
        this.majaastaLopp = value;
    }

    /**
     * Gets the value of the standard property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStandard(BigInteger value) {
        this.standard = value;
    }

    /**
     * Gets the value of the konsolideeritud property.
     * 
     */
    public boolean isKonsolideeritud() {
        return konsolideeritud;
    }

    /**
     * Sets the value of the konsolideeritud property.
     * 
     */
    public void setKonsolideeritud(boolean value) {
        this.konsolideeritud = value;
    }

    /**
     * Gets the value of the erandid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getErandid() {
        return erandid;
    }

    /**
     * Sets the value of the erandid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setErandid(BigInteger value) {
        this.erandid = value;
    }

    /**
     * Gets the value of the tapsusaste property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTapsusaste() {
        return tapsusaste;
    }

    /**
     * Sets the value of the tapsusaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTapsusaste(BigInteger value) {
        this.tapsusaste = value;
    }

    /**
     * Gets the value of the aruandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAruandeLiik() {
        return aruandeLiik;
    }

    /**
     * Sets the value of the aruandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAruandeLiik(String value) {
        this.aruandeLiik = value;
    }

    /**
     * Gets the value of the xbrlAruandeId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getXbrlAruandeId() {
        return xbrlAruandeId;
    }

    /**
     * Sets the value of the xbrlAruandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setXbrlAruandeId(BigInteger value) {
        this.xbrlAruandeId = value;
    }

    /**
     * Gets the value of the paevNr property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPaevNr() {
        return paevNr;
    }

    /**
     * Sets the value of the paevNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPaevNr(BigInteger value) {
        this.paevNr = value;
    }

    /**
     * Gets the value of the ptegEmtak property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtegEmtak() {
        return ptegEmtak;
    }

    /**
     * Sets the value of the ptegEmtak property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtegEmtak(String value) {
        this.ptegEmtak = value;
    }

    /**
     * Gets the value of the ptegEmtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtegEmtakVersioon() {
        return ptegEmtakVersioon;
    }

    /**
     * Sets the value of the ptegEmtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtegEmtakVersioon(String value) {
        this.ptegEmtakVersioon = value;
    }

    /**
     * Gets the value of the tootajateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTootajateArv() {
        return tootajateArv;
    }

    /**
     * Sets the value of the tootajateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTootajateArv(BigInteger value) {
        this.tootajateArv = value;
    }

    /**
     * Gets the value of the kaive property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKaive() {
        return kaive;
    }

    /**
     * Sets the value of the kaive property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKaive(BigInteger value) {
        this.kaive = value;
    }

    /**
     * Gets the value of the kasum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKasum() {
        return kasum;
    }

    /**
     * Sets the value of the kasum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKasum(BigInteger value) {
        this.kasum = value;
    }

    /**
     * Gets the value of the bilansimaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBilansimaht() {
        return bilansimaht;
    }

    /**
     * Sets the value of the bilansimaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBilansimaht(BigInteger value) {
        this.bilansimaht = value;
    }

    /**
     * Gets the value of the konsolTootajateArv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolTootajateArv() {
        return konsolTootajateArv;
    }

    /**
     * Sets the value of the konsolTootajateArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolTootajateArv(BigInteger value) {
        this.konsolTootajateArv = value;
    }

    /**
     * Gets the value of the konsolKaive property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolKaive() {
        return konsolKaive;
    }

    /**
     * Sets the value of the konsolKaive property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolKaive(BigInteger value) {
        this.konsolKaive = value;
    }

    /**
     * Gets the value of the konsolKasum property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolKasum() {
        return konsolKasum;
    }

    /**
     * Sets the value of the konsolKasum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolKasum(BigInteger value) {
        this.konsolKasum = value;
    }

    /**
     * Gets the value of the konsolBilansimaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKonsolBilansimaht() {
        return konsolBilansimaht;
    }

    /**
     * Sets the value of the konsolBilansimaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKonsolBilansimaht(BigInteger value) {
        this.konsolBilansimaht = value;
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
     * Gets the value of the auditeeritus property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAuditeeritus() {
        return auditeeritus;
    }

    /**
     * Sets the value of the auditeeritus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAuditeeritus(Boolean value) {
        this.auditeeritus = value;
    }

}
