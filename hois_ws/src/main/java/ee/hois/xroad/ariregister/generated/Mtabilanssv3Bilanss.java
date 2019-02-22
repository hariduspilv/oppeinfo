
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for mtabilanssv3_bilanss complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtabilanssv3_bilanss"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bilansi_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ark" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="maj_algus" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="maj_lopp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="hald_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aadress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pohiteg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pteg_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esitatud" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="sisestatud" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="viim_par" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="muutus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mkuup" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtabilanssv3_bilanss", propOrder = {
    "bilansiId",
    "ark",
    "majAlgus",
    "majLopp",
    "haldId",
    "aadress",
    "pohiteg",
    "ptegKood",
    "olek",
    "esitatud",
    "sisestatud",
    "viimPar",
    "muutus",
    "mkuup",
    "emtakVersioon"
})
public class Mtabilanssv3Bilanss {

    @XmlElement(name = "bilansi_id")
    protected BigInteger bilansiId;
    protected BigInteger ark;
    @XmlElement(name = "maj_algus")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majAlgus;
    @XmlElement(name = "maj_lopp")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar majLopp;
    @XmlElement(name = "hald_id")
    protected String haldId;
    protected String aadress;
    protected String pohiteg;
    @XmlElement(name = "pteg_kood")
    protected BigInteger ptegKood;
    protected String olek;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitatud;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sisestatud;
    @XmlElement(name = "viim_par")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar viimPar;
    protected String muutus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar mkuup;
    @XmlElement(name = "emtak_versioon")
    protected Integer emtakVersioon;

    /**
     * Gets the value of the bilansiId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBilansiId() {
        return bilansiId;
    }

    /**
     * Sets the value of the bilansiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBilansiId(BigInteger value) {
        this.bilansiId = value;
    }

    /**
     * Gets the value of the ark property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getArk() {
        return ark;
    }

    /**
     * Sets the value of the ark property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setArk(BigInteger value) {
        this.ark = value;
    }

    /**
     * Gets the value of the majAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajAlgus() {
        return majAlgus;
    }

    /**
     * Sets the value of the majAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajAlgus(XMLGregorianCalendar value) {
        this.majAlgus = value;
    }

    /**
     * Gets the value of the majLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMajLopp() {
        return majLopp;
    }

    /**
     * Sets the value of the majLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMajLopp(XMLGregorianCalendar value) {
        this.majLopp = value;
    }

    /**
     * Gets the value of the haldId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaldId() {
        return haldId;
    }

    /**
     * Sets the value of the haldId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaldId(String value) {
        this.haldId = value;
    }

    /**
     * Gets the value of the aadress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAadress() {
        return aadress;
    }

    /**
     * Sets the value of the aadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAadress(String value) {
        this.aadress = value;
    }

    /**
     * Gets the value of the pohiteg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPohiteg() {
        return pohiteg;
    }

    /**
     * Sets the value of the pohiteg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPohiteg(String value) {
        this.pohiteg = value;
    }

    /**
     * Gets the value of the ptegKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPtegKood() {
        return ptegKood;
    }

    /**
     * Sets the value of the ptegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPtegKood(BigInteger value) {
        this.ptegKood = value;
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
     * Gets the value of the esitatud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitatud() {
        return esitatud;
    }

    /**
     * Sets the value of the esitatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitatud(XMLGregorianCalendar value) {
        this.esitatud = value;
    }

    /**
     * Gets the value of the sisestatud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSisestatud() {
        return sisestatud;
    }

    /**
     * Sets the value of the sisestatud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSisestatud(XMLGregorianCalendar value) {
        this.sisestatud = value;
    }

    /**
     * Gets the value of the viimPar property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getViimPar() {
        return viimPar;
    }

    /**
     * Sets the value of the viimPar property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setViimPar(XMLGregorianCalendar value) {
        this.viimPar = value;
    }

    /**
     * Gets the value of the muutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMuutus() {
        return muutus;
    }

    /**
     * Sets the value of the muutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMuutus(String value) {
        this.muutus = value;
    }

    /**
     * Gets the value of the mkuup property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMkuup() {
        return mkuup;
    }

    /**
     * Sets the value of the mkuup property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMkuup(XMLGregorianCalendar value) {
        this.mkuup = value;
    }

    /**
     * Gets the value of the emtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEmtakVersioon() {
        return emtakVersioon;
    }

    /**
     * Sets the value of the emtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEmtakVersioon(Integer value) {
        this.emtakVersioon = value;
    }

}
