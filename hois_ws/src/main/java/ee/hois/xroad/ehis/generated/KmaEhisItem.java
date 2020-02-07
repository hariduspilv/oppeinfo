
package ee.hois.xroad.ehis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for kmaEhisItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kmaEhisItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kodakondsus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeasutus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="alustanud" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kuupaev" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="selgitus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kvalifikatsioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="synniKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="staatus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}ehisBoolean" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaKood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppekavaNimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="nomKestus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oppeLabiviimiseAlgusKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="oppeLabiviimiseLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="nomOppeajaLoppKp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="akadPuhkus" type="{http://producers.ehis.xtee.riik.ee/producer/ehis}kmaEhisAkadPuhkus" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="oppekoormus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ainepunkte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kmaEhisItem", propOrder = {
    "isikukood",
    "perekonnanimi",
    "eesnimi",
    "kodakondsus",
    "oppeasutus",
    "alustanud",
    "kuupaev",
    "selgitus",
    "kvalifikatsioon",
    "synniKp",
    "staatus",
    "oppekavaKood",
    "oppekavaNimetus",
    "nomKestus",
    "oppeLabiviimiseAlgusKp",
    "oppeLabiviimiseLoppKp",
    "nomOppeajaLoppKp",
    "akadPuhkus",
    "oppekoormus",
    "ainepunkte"
})
public class KmaEhisItem {

    protected String isikukood;
    protected String perekonnanimi;
    protected String eesnimi;
    protected String kodakondsus;
    @XmlElement(required = true)
    protected String oppeasutus;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar alustanud;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar kuupaev;
    @XmlElement(required = true)
    protected String selgitus;
    protected String kvalifikatsioon;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniKp;
    protected String staatus;
    protected String oppekavaKood;
    protected String oppekavaNimetus;
    protected String nomKestus;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppeLabiviimiseAlgusKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar oppeLabiviimiseLoppKp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar nomOppeajaLoppKp;
    protected List<KmaEhisAkadPuhkus> akadPuhkus;
    protected String oppekoormus;
    protected String ainepunkte;

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
     * Gets the value of the perekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerekonnanimi() {
        return perekonnanimi;
    }

    /**
     * Sets the value of the perekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerekonnanimi(String value) {
        this.perekonnanimi = value;
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
     * Gets the value of the kodakondsus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKodakondsus() {
        return kodakondsus;
    }

    /**
     * Sets the value of the kodakondsus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKodakondsus(String value) {
        this.kodakondsus = value;
    }

    /**
     * Gets the value of the oppeasutus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutus() {
        return oppeasutus;
    }

    /**
     * Sets the value of the oppeasutus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutus(String value) {
        this.oppeasutus = value;
    }

    /**
     * Gets the value of the alustanud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAlustanud() {
        return alustanud;
    }

    /**
     * Sets the value of the alustanud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAlustanud(XMLGregorianCalendar value) {
        this.alustanud = value;
    }

    /**
     * Gets the value of the kuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getKuupaev() {
        return kuupaev;
    }

    /**
     * Sets the value of the kuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setKuupaev(XMLGregorianCalendar value) {
        this.kuupaev = value;
    }

    /**
     * Gets the value of the selgitus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelgitus() {
        return selgitus;
    }

    /**
     * Sets the value of the selgitus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelgitus(String value) {
        this.selgitus = value;
    }

    /**
     * Gets the value of the kvalifikatsioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKvalifikatsioon() {
        return kvalifikatsioon;
    }

    /**
     * Sets the value of the kvalifikatsioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKvalifikatsioon(String value) {
        this.kvalifikatsioon = value;
    }

    /**
     * Gets the value of the synniKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniKp() {
        return synniKp;
    }

    /**
     * Sets the value of the synniKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniKp(XMLGregorianCalendar value) {
        this.synniKp = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the oppekavaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekavaKood() {
        return oppekavaKood;
    }

    /**
     * Sets the value of the oppekavaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekavaKood(String value) {
        this.oppekavaKood = value;
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
     * Gets the value of the nomKestus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomKestus() {
        return nomKestus;
    }

    /**
     * Sets the value of the nomKestus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomKestus(String value) {
        this.nomKestus = value;
    }

    /**
     * Gets the value of the oppeLabiviimiseAlgusKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppeLabiviimiseAlgusKp() {
        return oppeLabiviimiseAlgusKp;
    }

    /**
     * Sets the value of the oppeLabiviimiseAlgusKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppeLabiviimiseAlgusKp(XMLGregorianCalendar value) {
        this.oppeLabiviimiseAlgusKp = value;
    }

    /**
     * Gets the value of the oppeLabiviimiseLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOppeLabiviimiseLoppKp() {
        return oppeLabiviimiseLoppKp;
    }

    /**
     * Sets the value of the oppeLabiviimiseLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOppeLabiviimiseLoppKp(XMLGregorianCalendar value) {
        this.oppeLabiviimiseLoppKp = value;
    }

    /**
     * Gets the value of the nomOppeajaLoppKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNomOppeajaLoppKp() {
        return nomOppeajaLoppKp;
    }

    /**
     * Sets the value of the nomOppeajaLoppKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNomOppeajaLoppKp(XMLGregorianCalendar value) {
        this.nomOppeajaLoppKp = value;
    }

    /**
     * Gets the value of the akadPuhkus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the akadPuhkus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAkadPuhkus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KmaEhisAkadPuhkus }
     * 
     * 
     */
    public List<KmaEhisAkadPuhkus> getAkadPuhkus() {
        if (akadPuhkus == null) {
            akadPuhkus = new ArrayList<KmaEhisAkadPuhkus>();
        }
        return this.akadPuhkus;
    }

    /**
     * Gets the value of the oppekoormus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekoormus() {
        return oppekoormus;
    }

    /**
     * Sets the value of the oppekoormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekoormus(String value) {
        this.oppekoormus = value;
    }

    /**
     * Gets the value of the ainepunkte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAinepunkte() {
        return ainepunkte;
    }

    /**
     * Sets the value of the ainepunkte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAinepunkte(String value) {
        this.ainepunkte = value;
    }

}
