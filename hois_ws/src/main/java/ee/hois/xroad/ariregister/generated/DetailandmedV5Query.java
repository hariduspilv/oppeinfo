
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for detailandmed_v5_Query complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="detailandmed_v5_Query"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregister_kasutajanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_parool" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_sessioon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregister_valjundi_formaat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="arinimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_id" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="asukoht_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="haldyksus_ettevotja_aadressis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_eesnimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_perekonnanimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_synniaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fyysilise_isiku_roll_jada" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="jurisik_nimetus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jurisik_ark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="jurisik_roll_jada" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="yandmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="iandmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="kandmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="dandmed" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="maarused" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ainult_kehtivad" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="staatused" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="keel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="evarv" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailandmed_v5_Query", propOrder = {
    "ariregisterKasutajanimi",
    "ariregisterParool",
    "ariregisterSessioon",
    "ariregisterValjundiFormaat",
    "arinimi",
    "ariregistriKood",
    "ettevotjaId",
    "asukohtEttevotjaAadressis",
    "haldyksusEttevotjaAadressis",
    "fyysiliseIsikuEesnimi",
    "fyysiliseIsikuPerekonnanimi",
    "fyysiliseIsikuSynniaeg",
    "fyysiliseIsikuKood",
    "fyysiliseIsikuRollJada",
    "jurisikNimetus",
    "jurisikArk",
    "jurisikRollJada",
    "yandmed",
    "iandmed",
    "kandmed",
    "dandmed",
    "maarused",
    "ainultKehtivad",
    "staatused",
    "keel",
    "evarv"
})
public class DetailandmedV5Query {

    @XmlElementRef(name = "ariregister_kasutajanimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterKasutajanimi;
    @XmlElementRef(name = "ariregister_parool", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterParool;
    @XmlElementRef(name = "ariregister_sessioon", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterSessioon;
    @XmlElementRef(name = "ariregister_valjundi_formaat", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ariregisterValjundiFormaat;
    protected String arinimi;
    @XmlElement(name = "ariregistri_kood")
    protected BigInteger ariregistriKood;
    @XmlElement(name = "ettevotja_id")
    protected BigInteger ettevotjaId;
    @XmlElement(name = "asukoht_ettevotja_aadressis")
    protected String asukohtEttevotjaAadressis;
    @XmlElement(name = "haldyksus_ettevotja_aadressis")
    protected String haldyksusEttevotjaAadressis;
    @XmlElement(name = "fyysilise_isiku_eesnimi")
    protected String fyysiliseIsikuEesnimi;
    @XmlElement(name = "fyysilise_isiku_perekonnanimi")
    protected String fyysiliseIsikuPerekonnanimi;
    @XmlElement(name = "fyysilise_isiku_synniaeg")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fyysiliseIsikuSynniaeg;
    @XmlElement(name = "fyysilise_isiku_kood")
    protected String fyysiliseIsikuKood;
    @XmlElement(name = "fyysilise_isiku_roll_jada")
    protected List<String> fyysiliseIsikuRollJada;
    @XmlElement(name = "jurisik_nimetus")
    protected String jurisikNimetus;
    @XmlElement(name = "jurisik_ark")
    protected String jurisikArk;
    @XmlElement(name = "jurisik_roll_jada")
    protected List<String> jurisikRollJada;
    protected boolean yandmed;
    protected boolean iandmed;
    protected boolean kandmed;
    protected boolean dandmed;
    protected boolean maarused;
    @XmlElement(name = "ainult_kehtivad")
    protected Boolean ainultKehtivad;
    protected List<String> staatused;
    protected String keel;
    protected BigInteger evarv;

    /**
     * Gets the value of the ariregisterKasutajanimi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterKasutajanimi() {
        return ariregisterKasutajanimi;
    }

    /**
     * Sets the value of the ariregisterKasutajanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterKasutajanimi(JAXBElement<String> value) {
        this.ariregisterKasutajanimi = value;
    }

    /**
     * Gets the value of the ariregisterParool property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterParool() {
        return ariregisterParool;
    }

    /**
     * Sets the value of the ariregisterParool property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterParool(JAXBElement<String> value) {
        this.ariregisterParool = value;
    }

    /**
     * Gets the value of the ariregisterSessioon property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterSessioon() {
        return ariregisterSessioon;
    }

    /**
     * Sets the value of the ariregisterSessioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterSessioon(JAXBElement<String> value) {
        this.ariregisterSessioon = value;
    }

    /**
     * Gets the value of the ariregisterValjundiFormaat property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAriregisterValjundiFormaat() {
        return ariregisterValjundiFormaat;
    }

    /**
     * Sets the value of the ariregisterValjundiFormaat property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAriregisterValjundiFormaat(JAXBElement<String> value) {
        this.ariregisterValjundiFormaat = value;
    }

    /**
     * Gets the value of the arinimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArinimi() {
        return arinimi;
    }

    /**
     * Sets the value of the arinimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArinimi(String value) {
        this.arinimi = value;
    }

    /**
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAriregistriKood(BigInteger value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the ettevotjaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEttevotjaId() {
        return ettevotjaId;
    }

    /**
     * Sets the value of the ettevotjaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEttevotjaId(BigInteger value) {
        this.ettevotjaId = value;
    }

    /**
     * Gets the value of the asukohtEttevotjaAadressis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsukohtEttevotjaAadressis() {
        return asukohtEttevotjaAadressis;
    }

    /**
     * Sets the value of the asukohtEttevotjaAadressis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsukohtEttevotjaAadressis(String value) {
        this.asukohtEttevotjaAadressis = value;
    }

    /**
     * Gets the value of the haldyksusEttevotjaAadressis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaldyksusEttevotjaAadressis() {
        return haldyksusEttevotjaAadressis;
    }

    /**
     * Sets the value of the haldyksusEttevotjaAadressis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaldyksusEttevotjaAadressis(String value) {
        this.haldyksusEttevotjaAadressis = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuEesnimi() {
        return fyysiliseIsikuEesnimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuEesnimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuEesnimi(String value) {
        this.fyysiliseIsikuEesnimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuPerekonnanimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuPerekonnanimi() {
        return fyysiliseIsikuPerekonnanimi;
    }

    /**
     * Sets the value of the fyysiliseIsikuPerekonnanimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuPerekonnanimi(String value) {
        this.fyysiliseIsikuPerekonnanimi = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFyysiliseIsikuSynniaeg() {
        return fyysiliseIsikuSynniaeg;
    }

    /**
     * Sets the value of the fyysiliseIsikuSynniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFyysiliseIsikuSynniaeg(XMLGregorianCalendar value) {
        this.fyysiliseIsikuSynniaeg = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFyysiliseIsikuKood() {
        return fyysiliseIsikuKood;
    }

    /**
     * Sets the value of the fyysiliseIsikuKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFyysiliseIsikuKood(String value) {
        this.fyysiliseIsikuKood = value;
    }

    /**
     * Gets the value of the fyysiliseIsikuRollJada property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fyysiliseIsikuRollJada property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFyysiliseIsikuRollJada().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFyysiliseIsikuRollJada() {
        if (fyysiliseIsikuRollJada == null) {
            fyysiliseIsikuRollJada = new ArrayList<String>();
        }
        return this.fyysiliseIsikuRollJada;
    }

    /**
     * Gets the value of the jurisikNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurisikNimetus() {
        return jurisikNimetus;
    }

    /**
     * Sets the value of the jurisikNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurisikNimetus(String value) {
        this.jurisikNimetus = value;
    }

    /**
     * Gets the value of the jurisikArk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJurisikArk() {
        return jurisikArk;
    }

    /**
     * Sets the value of the jurisikArk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJurisikArk(String value) {
        this.jurisikArk = value;
    }

    /**
     * Gets the value of the jurisikRollJada property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jurisikRollJada property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJurisikRollJada().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getJurisikRollJada() {
        if (jurisikRollJada == null) {
            jurisikRollJada = new ArrayList<String>();
        }
        return this.jurisikRollJada;
    }

    /**
     * Gets the value of the yandmed property.
     * 
     */
    public boolean isYandmed() {
        return yandmed;
    }

    /**
     * Sets the value of the yandmed property.
     * 
     */
    public void setYandmed(boolean value) {
        this.yandmed = value;
    }

    /**
     * Gets the value of the iandmed property.
     * 
     */
    public boolean isIandmed() {
        return iandmed;
    }

    /**
     * Sets the value of the iandmed property.
     * 
     */
    public void setIandmed(boolean value) {
        this.iandmed = value;
    }

    /**
     * Gets the value of the kandmed property.
     * 
     */
    public boolean isKandmed() {
        return kandmed;
    }

    /**
     * Sets the value of the kandmed property.
     * 
     */
    public void setKandmed(boolean value) {
        this.kandmed = value;
    }

    /**
     * Gets the value of the dandmed property.
     * 
     */
    public boolean isDandmed() {
        return dandmed;
    }

    /**
     * Sets the value of the dandmed property.
     * 
     */
    public void setDandmed(boolean value) {
        this.dandmed = value;
    }

    /**
     * Gets the value of the maarused property.
     * 
     */
    public boolean isMaarused() {
        return maarused;
    }

    /**
     * Sets the value of the maarused property.
     * 
     */
    public void setMaarused(boolean value) {
        this.maarused = value;
    }

    /**
     * Gets the value of the ainultKehtivad property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAinultKehtivad() {
        return ainultKehtivad;
    }

    /**
     * Sets the value of the ainultKehtivad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAinultKehtivad(Boolean value) {
        this.ainultKehtivad = value;
    }

    /**
     * Gets the value of the staatused property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the staatused property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStaatused().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStaatused() {
        if (staatused == null) {
            staatused = new ArrayList<String>();
        }
        return this.staatused;
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
     * Gets the value of the evarv property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEvarv() {
        return evarv;
    }

    /**
     * Sets the value of the evarv property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEvarv(BigInteger value) {
        this.evarv = value;
    }

}
