
package ee.hois.xroad.kutseregister.generated;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for kutsetunnistusV2Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kutsetunnistusV2Type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="registrinumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="tyyp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="standard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ekrtase" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="eqftase" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="spetsialiseerumine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="osakutse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lisavali" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kompetentsid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valdkond" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kutseala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="hariduslikkval" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valjastaja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valjaantud" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibalates" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="kehtibkuni" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="isco" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="duplikaat" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="kehtetu" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="reaid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kustutatud" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kutsetunnistusV2Type", propOrder = {
    "registrinumber",
    "nimi",
    "isikukood",
    "synniaeg",
    "tyyp",
    "standard",
    "ekrtase",
    "eqftase",
    "spetsialiseerumine",
    "osakutse",
    "lisavali",
    "kompetentsid",
    "valdkond",
    "kutseala",
    "hariduslikkval",
    "keel",
    "valjastaja",
    "valjaantud",
    "kehtibalates",
    "kehtibkuni",
    "isco",
    "duplikaat",
    "kehtetu",
    "reaid",
    "kustutatud"
})
public class KutsetunnistusV2Type {

    protected String registrinumber;
    protected String nimi;
    protected String isikukood;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate synniaeg;
    protected String tyyp;
    protected String standard;
    protected BigInteger ekrtase;
    protected BigInteger eqftase;
    protected String spetsialiseerumine;
    protected String osakutse;
    protected String lisavali;
    protected String kompetentsid;
    protected String valdkond;
    protected String kutseala;
    protected String hariduslikkval;
    protected String keel;
    protected String valjastaja;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate valjaantud;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibalates;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "date")
    protected LocalDate kehtibkuni;
    protected String isco;
    protected Boolean duplikaat;
    protected Boolean kehtetu;
    protected String reaid;
    protected Boolean kustutatud;

    /**
     * Gets the value of the registrinumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrinumber() {
        return registrinumber;
    }

    /**
     * Sets the value of the registrinumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrinumber(String value) {
        this.registrinumber = value;
    }

    /**
     * Gets the value of the nimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Sets the value of the nimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimi(String value) {
        this.nimi = value;
    }

    /**
     * Gets the value of the isikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukood() {
        return isikukood;
    }

    /**
     * Sets the value of the isikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukood(String value) {
        this.isikukood = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSynniaeg(LocalDate value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the tyyp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyyp() {
        return tyyp;
    }

    /**
     * Sets the value of the tyyp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyyp(String value) {
        this.tyyp = value;
    }

    /**
     * Gets the value of the standard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStandard(String value) {
        this.standard = value;
    }

    /**
     * Gets the value of the ekrtase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEkrtase() {
        return ekrtase;
    }

    /**
     * Sets the value of the ekrtase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEkrtase(BigInteger value) {
        this.ekrtase = value;
    }

    /**
     * Gets the value of the eqftase property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEqftase() {
        return eqftase;
    }

    /**
     * Sets the value of the eqftase property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEqftase(BigInteger value) {
        this.eqftase = value;
    }

    /**
     * Gets the value of the spetsialiseerumine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpetsialiseerumine() {
        return spetsialiseerumine;
    }

    /**
     * Sets the value of the spetsialiseerumine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpetsialiseerumine(String value) {
        this.spetsialiseerumine = value;
    }

    /**
     * Gets the value of the osakutse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOsakutse() {
        return osakutse;
    }

    /**
     * Sets the value of the osakutse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOsakutse(String value) {
        this.osakutse = value;
    }

    /**
     * Gets the value of the lisavali property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLisavali() {
        return lisavali;
    }

    /**
     * Sets the value of the lisavali property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLisavali(String value) {
        this.lisavali = value;
    }

    /**
     * Gets the value of the kompetentsid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKompetentsid() {
        return kompetentsid;
    }

    /**
     * Sets the value of the kompetentsid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKompetentsid(String value) {
        this.kompetentsid = value;
    }

    /**
     * Gets the value of the valdkond property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValdkond() {
        return valdkond;
    }

    /**
     * Sets the value of the valdkond property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValdkond(String value) {
        this.valdkond = value;
    }

    /**
     * Gets the value of the kutseala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKutseala() {
        return kutseala;
    }

    /**
     * Sets the value of the kutseala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKutseala(String value) {
        this.kutseala = value;
    }

    /**
     * Gets the value of the hariduslikkval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHariduslikkval() {
        return hariduslikkval;
    }

    /**
     * Sets the value of the hariduslikkval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHariduslikkval(String value) {
        this.hariduslikkval = value;
    }

    /**
     * Gets the value of the keel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeel() {
        return keel;
    }

    /**
     * Sets the value of the keel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeel(String value) {
        this.keel = value;
    }

    /**
     * Gets the value of the valjastaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValjastaja() {
        return valjastaja;
    }

    /**
     * Sets the value of the valjastaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjastaja(String value) {
        this.valjastaja = value;
    }

    /**
     * Gets the value of the valjaantud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getValjaantud() {
        return valjaantud;
    }

    /**
     * Sets the value of the valjaantud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValjaantud(LocalDate value) {
        this.valjaantud = value;
    }

    /**
     * Gets the value of the kehtibalates property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibalates() {
        return kehtibalates;
    }

    /**
     * Sets the value of the kehtibalates property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibalates(LocalDate value) {
        this.kehtibalates = value;
    }

    /**
     * Gets the value of the kehtibkuni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public LocalDate getKehtibkuni() {
        return kehtibkuni;
    }

    /**
     * Sets the value of the kehtibkuni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKehtibkuni(LocalDate value) {
        this.kehtibkuni = value;
    }

    /**
     * Gets the value of the isco property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsco() {
        return isco;
    }

    /**
     * Sets the value of the isco property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsco(String value) {
        this.isco = value;
    }

    /**
     * Gets the value of the duplikaat property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDuplikaat() {
        return duplikaat;
    }

    /**
     * Sets the value of the duplikaat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDuplikaat(Boolean value) {
        this.duplikaat = value;
    }

    /**
     * Gets the value of the kehtetu property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKehtetu() {
        return kehtetu;
    }

    /**
     * Sets the value of the kehtetu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKehtetu(Boolean value) {
        this.kehtetu = value;
    }

    /**
     * Gets the value of the reaid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReaid() {
        return reaid;
    }

    /**
     * Sets the value of the reaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReaid(String value) {
        this.reaid = value;
    }

    /**
     * Gets the value of the kustutatud property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKustutatud() {
        return kustutatud;
    }

    /**
     * Sets the value of the kustutatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKustutatud(Boolean value) {
        this.kustutatud = value;
    }

}
