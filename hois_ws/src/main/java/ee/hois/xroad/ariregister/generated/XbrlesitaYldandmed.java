
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
 * <p>Java class for xbrlesita_yldandmed complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlesita_yldandmed"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="majaasta_algus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="majaasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="standard_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="konsolideeritud" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="erandid" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="erandid_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tapsusaste" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="tapsusaste_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aruande_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="xbrl_file_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ddoc_file_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="xbrl_aruande_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlesita_yldandmed", propOrder = {
    "ariregistriKood",
    "majaastaAlgus",
    "majaastaLopp",
    "standard",
    "standardTekstina",
    "konsolideeritud",
    "erandid",
    "erandidTekstina",
    "tapsusaste",
    "tapsusasteTekstina",
    "aruandeLiik",
    "xbrlFileId",
    "ddocFileId",
    "xbrlAruandeId"
})
public class XbrlesitaYldandmed {

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
    @XmlElement(name = "standard_tekstina", required = true)
    protected String standardTekstina;
    protected boolean konsolideeritud;
    @XmlElement(required = true)
    protected BigInteger erandid;
    @XmlElement(name = "erandid_tekstina", required = true)
    protected String erandidTekstina;
    @XmlElement(required = true)
    protected BigInteger tapsusaste;
    @XmlElement(name = "tapsusaste_tekstina", required = true)
    protected String tapsusasteTekstina;
    @XmlElementRef(name = "aruande_liik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> aruandeLiik;
    @XmlElementRef(name = "xbrl_file_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> xbrlFileId;
    @XmlElementRef(name = "ddoc_file_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> ddocFileId;
    @XmlElementRef(name = "xbrl_aruande_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<BigInteger> xbrlAruandeId;

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
     * Gets the value of the standardTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandardTekstina() {
        return standardTekstina;
    }

    /**
     * Sets the value of the standardTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandardTekstina(String value) {
        this.standardTekstina = value;
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
     * Gets the value of the erandidTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErandidTekstina() {
        return erandidTekstina;
    }

    /**
     * Sets the value of the erandidTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErandidTekstina(String value) {
        this.erandidTekstina = value;
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
     * Gets the value of the tapsusasteTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTapsusasteTekstina() {
        return tapsusasteTekstina;
    }

    /**
     * Sets the value of the tapsusasteTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTapsusasteTekstina(String value) {
        this.tapsusasteTekstina = value;
    }

    /**
     * Gets the value of the aruandeLiik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAruandeLiik() {
        return aruandeLiik;
    }

    /**
     * Sets the value of the aruandeLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAruandeLiik(JAXBElement<String> value) {
        this.aruandeLiik = value;
    }

    /**
     * Gets the value of the xbrlFileId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getXbrlFileId() {
        return xbrlFileId;
    }

    /**
     * Sets the value of the xbrlFileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setXbrlFileId(JAXBElement<Integer> value) {
        this.xbrlFileId = value;
    }

    /**
     * Gets the value of the ddocFileId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getDdocFileId() {
        return ddocFileId;
    }

    /**
     * Sets the value of the ddocFileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setDdocFileId(JAXBElement<Integer> value) {
        this.ddocFileId = value;
    }

    /**
     * Gets the value of the xbrlAruandeId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<BigInteger> getXbrlAruandeId() {
        return xbrlAruandeId;
    }

    /**
     * Sets the value of the xbrlAruandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setXbrlAruandeId(JAXBElement<BigInteger> value) {
        this.xbrlAruandeId = value;
    }

}
