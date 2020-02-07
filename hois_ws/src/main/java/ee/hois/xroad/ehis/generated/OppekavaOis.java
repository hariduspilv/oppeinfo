
package ee.hois.xroad.ehis.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for oppekavaOis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="oppekavaOis"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaLiik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaNimetusEng" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasKinnitDoc" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasKinnitKp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="oppekeeled" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisOppekeeled"/&gt;
 *         &lt;element name="nomKestusAasta" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="nomKestusKuud" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}monthsInteger" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaMaht" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppekavaRyhm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekavaGrupp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="opetajaKoolitus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oneOrZeroInteger" minOccurs="0"/&gt;
 *         &lt;element name="akadKraad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="praktikaMaht" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="spetsialiseerumised" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisOKSpetsialiseerumised"/&gt;
 *         &lt;element name="vastavusRiikOppekava" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="toimumiseKohad" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisToimumiseKohad" minOccurs="0"/&gt;
 *         &lt;element name="yhisOppekavaOas" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisYhisOppekava" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="kutsestandardid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisKutsestandardid"/&gt;
 *         &lt;element name="failid" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}oisFailid"/&gt;
 *         &lt;element name="kommentaar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="opivaljundid" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4000"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="avaOkFailUrl" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="2048"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "oppekavaOis", propOrder = {
    "oppekavaKood",
    "oppekavaLiik",
    "oppekavaNimetus",
    "oppekavaNimetusEng",
    "tase",
    "oasKinnitDoc",
    "oasKinnitKp",
    "oppekeeled",
    "nomKestusAasta",
    "nomKestusKuud",
    "oppekavaMaht",
    "oppekavaRyhm",
    "oppekavaGrupp",
    "opetajaKoolitus",
    "akadKraad",
    "praktikaMaht",
    "spetsialiseerumised",
    "vastavusRiikOppekava",
    "toimumiseKohad",
    "yhisOppekavaOas",
    "kutsestandardid",
    "failid",
    "kommentaar",
    "opivaljundid",
    "avaOkFailUrl"
})
public class OppekavaOis {

    protected BigInteger oppekavaKood;
    @XmlElement(required = true)
    protected String oppekavaLiik;
    @XmlElement(required = true)
    protected String oppekavaNimetus;
    @XmlElement(required = true)
    protected String oppekavaNimetusEng;
    @XmlElement(required = true)
    protected String tase;
    @XmlElement(required = true)
    protected String oasKinnitDoc;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oasKinnitKp;
    @XmlElement(required = true)
    protected OisOppekeeled oppekeeled;
    @XmlElement(required = true)
    protected BigInteger nomKestusAasta;
    @XmlSchemaType(name = "integer")
    protected Integer nomKestusKuud;
    @XmlElement(required = true)
    protected BigInteger oppekavaMaht;
    @XmlElement(required = true)
    protected String oppekavaRyhm;
    @XmlElement(required = true)
    protected String oppekavaGrupp;
    @XmlSchemaType(name = "integer")
    protected Integer opetajaKoolitus;
    protected String akadKraad;
    @XmlElement(required = true)
    protected BigDecimal praktikaMaht;
    @XmlElement(required = true)
    protected OisOKSpetsialiseerumised spetsialiseerumised;
    protected String vastavusRiikOppekava;
    protected OisToimumiseKohad toimumiseKohad;
    protected List<OisYhisOppekava> yhisOppekavaOas;
    @XmlElement(required = true)
    protected OisKutsestandardid kutsestandardid;
    @XmlElement(required = true)
    protected OisFailid failid;
    protected String kommentaar;
    protected String opivaljundid;
    protected String avaOkFailUrl;

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekavaKood(BigInteger value) {
        this.oppekavaKood = value;
    }

    /**
     * Gets the value of the oppekavaLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaLiik() {
        return oppekavaLiik;
    }

    /**
     * Sets the value of the oppekavaLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaLiik(String value) {
        this.oppekavaLiik = value;
    }

    /**
     * Gets the value of the oppekavaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimetus() {
        return oppekavaNimetus;
    }

    /**
     * Sets the value of the oppekavaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimetus(String value) {
        this.oppekavaNimetus = value;
    }

    /**
     * Gets the value of the oppekavaNimetusEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaNimetusEng() {
        return oppekavaNimetusEng;
    }

    /**
     * Sets the value of the oppekavaNimetusEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaNimetusEng(String value) {
        this.oppekavaNimetusEng = value;
    }

    /**
     * Gets the value of the tase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase() {
        return tase;
    }

    /**
     * Sets the value of the tase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase(String value) {
        this.tase = value;
    }

    /**
     * Gets the value of the oasKinnitDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasKinnitDoc() {
        return oasKinnitDoc;
    }

    /**
     * Sets the value of the oasKinnitDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasKinnitDoc(String value) {
        this.oasKinnitDoc = value;
    }

    /**
     * Gets the value of the oasKinnitKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOasKinnitKp() {
        return oasKinnitKp;
    }

    /**
     * Sets the value of the oasKinnitKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOasKinnitKp(XMLGregorianCalendar value) {
        this.oasKinnitKp = value;
    }

    /**
     * Gets the value of the oppekeeled property.
     * 
     * @return
     *     possible object is
     *     {@link OisOppekeeled }
     *     
     */
    public OisOppekeeled getOppekeeled() {
        return oppekeeled;
    }

    /**
     * Sets the value of the oppekeeled property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisOppekeeled }
     *     
     */
    public void setOppekeeled(OisOppekeeled value) {
        this.oppekeeled = value;
    }

    /**
     * Gets the value of the nomKestusAasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNomKestusAasta() {
        return nomKestusAasta;
    }

    /**
     * Sets the value of the nomKestusAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNomKestusAasta(BigInteger value) {
        this.nomKestusAasta = value;
    }

    /**
     * Gets the value of the nomKestusKuud property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNomKestusKuud() {
        return nomKestusKuud;
    }

    /**
     * Sets the value of the nomKestusKuud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNomKestusKuud(Integer value) {
        this.nomKestusKuud = value;
    }

    /**
     * Gets the value of the oppekavaMaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppekavaMaht() {
        return oppekavaMaht;
    }

    /**
     * Sets the value of the oppekavaMaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppekavaMaht(BigInteger value) {
        this.oppekavaMaht = value;
    }

    /**
     * Gets the value of the oppekavaRyhm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaRyhm() {
        return oppekavaRyhm;
    }

    /**
     * Sets the value of the oppekavaRyhm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaRyhm(String value) {
        this.oppekavaRyhm = value;
    }

    /**
     * Gets the value of the oppekavaGrupp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaGrupp() {
        return oppekavaGrupp;
    }

    /**
     * Sets the value of the oppekavaGrupp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaGrupp(String value) {
        this.oppekavaGrupp = value;
    }

    /**
     * Gets the value of the opetajaKoolitus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOpetajaKoolitus() {
        return opetajaKoolitus;
    }

    /**
     * Sets the value of the opetajaKoolitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOpetajaKoolitus(Integer value) {
        this.opetajaKoolitus = value;
    }

    /**
     * Gets the value of the akadKraad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAkadKraad() {
        return akadKraad;
    }

    /**
     * Sets the value of the akadKraad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAkadKraad(String value) {
        this.akadKraad = value;
    }

    /**
     * Gets the value of the praktikaMaht property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPraktikaMaht() {
        return praktikaMaht;
    }

    /**
     * Sets the value of the praktikaMaht property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPraktikaMaht(BigDecimal value) {
        this.praktikaMaht = value;
    }

    /**
     * Gets the value of the spetsialiseerumised property.
     * 
     * @return
     *     possible object is
     *     {@link OisOKSpetsialiseerumised }
     *     
     */
    public OisOKSpetsialiseerumised getSpetsialiseerumised() {
        return spetsialiseerumised;
    }

    /**
     * Sets the value of the spetsialiseerumised property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisOKSpetsialiseerumised }
     *     
     */
    public void setSpetsialiseerumised(OisOKSpetsialiseerumised value) {
        this.spetsialiseerumised = value;
    }

    /**
     * Gets the value of the vastavusRiikOppekava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVastavusRiikOppekava() {
        return vastavusRiikOppekava;
    }

    /**
     * Sets the value of the vastavusRiikOppekava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVastavusRiikOppekava(String value) {
        this.vastavusRiikOppekava = value;
    }

    /**
     * Gets the value of the toimumiseKohad property.
     * 
     * @return
     *     possible object is
     *     {@link OisToimumiseKohad }
     *     
     */
    public OisToimumiseKohad getToimumiseKohad() {
        return toimumiseKohad;
    }

    /**
     * Sets the value of the toimumiseKohad property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisToimumiseKohad }
     *     
     */
    public void setToimumiseKohad(OisToimumiseKohad value) {
        this.toimumiseKohad = value;
    }

    /**
     * Gets the value of the yhisOppekavaOas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the yhisOppekavaOas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getYhisOppekavaOas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OisYhisOppekava }
     * 
     * 
     */
    public List<OisYhisOppekava> getYhisOppekavaOas() {
        if (yhisOppekavaOas == null) {
            yhisOppekavaOas = new ArrayList<OisYhisOppekava>();
        }
        return this.yhisOppekavaOas;
    }

    /**
     * Gets the value of the kutsestandardid property.
     * 
     * @return
     *     possible object is
     *     {@link OisKutsestandardid }
     *     
     */
    public OisKutsestandardid getKutsestandardid() {
        return kutsestandardid;
    }

    /**
     * Sets the value of the kutsestandardid property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisKutsestandardid }
     *     
     */
    public void setKutsestandardid(OisKutsestandardid value) {
        this.kutsestandardid = value;
    }

    /**
     * Gets the value of the failid property.
     * 
     * @return
     *     possible object is
     *     {@link OisFailid }
     *     
     */
    public OisFailid getFailid() {
        return failid;
    }

    /**
     * Sets the value of the failid property.
     * 
     * @param value
     *     allowed object is
     *     {@link OisFailid }
     *     
     */
    public void setFailid(OisFailid value) {
        this.failid = value;
    }

    /**
     * Gets the value of the kommentaar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommentaar() {
        return kommentaar;
    }

    /**
     * Sets the value of the kommentaar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommentaar(String value) {
        this.kommentaar = value;
    }

    /**
     * Gets the value of the opivaljundid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpivaljundid() {
        return opivaljundid;
    }

    /**
     * Sets the value of the opivaljundid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpivaljundid(String value) {
        this.opivaljundid = value;
    }

    /**
     * Gets the value of the avaOkFailUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvaOkFailUrl() {
        return avaOkFailUrl;
    }

    /**
     * Sets the value of the avaOkFailUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvaOkFailUrl(String value) {
        this.avaOkFailUrl = value;
    }

}
