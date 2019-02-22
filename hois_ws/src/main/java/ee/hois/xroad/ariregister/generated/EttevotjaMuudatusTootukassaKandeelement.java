
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ettevotja_muudatus_tootukassa_kandeelement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ettevotja_muudatus_tootukassa_kandeelement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="kandeelemendi_id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="kandeelemendi_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandeelemendi_kehtivus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tyhistatava_kande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="algus_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="lopp_kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="isiku_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nimi_arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isikukood_registrikood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isiku_valis_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="valis_kood_riik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ettevotja_muudatus_tootukassa_kandeelement", propOrder = {
    "kandeelemendiId",
    "kandeelemendiLiik",
    "kandeelemendiKehtivus",
    "tyhistatavaKandeId",
    "algusKuupaev",
    "loppKuupaev",
    "isikuLiik",
    "eesnimi",
    "nimiArinimi",
    "isikukoodRegistrikood",
    "isikuValisKood",
    "valisKoodRiik",
    "synniaeg"
})
public class EttevotjaMuudatusTootukassaKandeelement {

    @XmlElement(name = "kandeelemendi_id")
    protected Integer kandeelemendiId;
    @XmlElement(name = "kandeelemendi_liik")
    protected String kandeelemendiLiik;
    @XmlElement(name = "kandeelemendi_kehtivus")
    protected String kandeelemendiKehtivus;
    @XmlElement(name = "tyhistatava_kande_id")
    protected String tyhistatavaKandeId;
    @XmlElement(name = "algus_kuupaev")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar algusKuupaev;
    @XmlElement(name = "lopp_kuupaev")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar loppKuupaev;
    @XmlElement(name = "isiku_liik")
    protected String isikuLiik;
    protected String eesnimi;
    @XmlElement(name = "nimi_arinimi")
    protected String nimiArinimi;
    @XmlElement(name = "isikukood_registrikood")
    protected String isikukoodRegistrikood;
    @XmlElement(name = "isiku_valis_kood")
    protected String isikuValisKood;
    @XmlElement(name = "valis_kood_riik")
    protected String valisKoodRiik;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniaeg;

    /**
     * Gets the value of the kandeelemendiId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getKandeelemendiId() {
        return kandeelemendiId;
    }

    /**
     * Sets the value of the kandeelemendiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setKandeelemendiId(Integer value) {
        this.kandeelemendiId = value;
    }

    /**
     * Gets the value of the kandeelemendiLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeelemendiLiik() {
        return kandeelemendiLiik;
    }

    /**
     * Sets the value of the kandeelemendiLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeelemendiLiik(String value) {
        this.kandeelemendiLiik = value;
    }

    /**
     * Gets the value of the kandeelemendiKehtivus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeelemendiKehtivus() {
        return kandeelemendiKehtivus;
    }

    /**
     * Sets the value of the kandeelemendiKehtivus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeelemendiKehtivus(String value) {
        this.kandeelemendiKehtivus = value;
    }

    /**
     * Gets the value of the tyhistatavaKandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTyhistatavaKandeId() {
        return tyhistatavaKandeId;
    }

    /**
     * Sets the value of the tyhistatavaKandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTyhistatavaKandeId(String value) {
        this.tyhistatavaKandeId = value;
    }

    /**
     * Gets the value of the algusKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlgusKuupaev() {
        return algusKuupaev;
    }

    /**
     * Sets the value of the algusKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlgusKuupaev(XMLGregorianCalendar value) {
        this.algusKuupaev = value;
    }

    /**
     * Gets the value of the loppKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoppKuupaev() {
        return loppKuupaev;
    }

    /**
     * Sets the value of the loppKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoppKuupaev(XMLGregorianCalendar value) {
        this.loppKuupaev = value;
    }

    /**
     * Gets the value of the isikuLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuLiik() {
        return isikuLiik;
    }

    /**
     * Sets the value of the isikuLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuLiik(String value) {
        this.isikuLiik = value;
    }

    /**
     * Gets the value of the eesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEesnimi() {
        return eesnimi;
    }

    /**
     * Sets the value of the eesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEesnimi(String value) {
        this.eesnimi = value;
    }

    /**
     * Gets the value of the nimiArinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNimiArinimi() {
        return nimiArinimi;
    }

    /**
     * Sets the value of the nimiArinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNimiArinimi(String value) {
        this.nimiArinimi = value;
    }

    /**
     * Gets the value of the isikukoodRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodRegistrikood() {
        return isikukoodRegistrikood;
    }

    /**
     * Sets the value of the isikukoodRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodRegistrikood(String value) {
        this.isikukoodRegistrikood = value;
    }

    /**
     * Gets the value of the isikuValisKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikuValisKood() {
        return isikuValisKood;
    }

    /**
     * Sets the value of the isikuValisKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikuValisKood(String value) {
        this.isikuValisKood = value;
    }

    /**
     * Gets the value of the valisKoodRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValisKoodRiik() {
        return valisKoodRiik;
    }

    /**
     * Sets the value of the valisKoodRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValisKoodRiik(String value) {
        this.valisKoodRiik = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniaeg(XMLGregorianCalendar value) {
        this.synniaeg = value;
    }

}
