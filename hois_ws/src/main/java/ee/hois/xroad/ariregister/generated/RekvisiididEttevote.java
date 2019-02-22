
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rekvisiidid_ettevote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rekvisiidid_ettevote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kmkr_nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ettevotja_staatus_tekstina" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ettevotja_aadress" type="{http://arireg.x-road.eu/producer/}rekvisiidid_evaadress" minOccurs="0"/&gt;
 *         &lt;element name="info_kohustuse_taitmise_kohta" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="majandusaasta_aruanded" type="{http://arireg.x-road.eu/producer/}rekvisiidid_maj_aruanded" minOccurs="0"/&gt;
 *         &lt;element name="maksuvola_info" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="teabesysteemi_link" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rekvisiidid_ettevote", propOrder = {
    "nimi",
    "ariregistriKood",
    "kmkrNr",
    "ettevotjaStaatus",
    "ettevotjaStaatusTekstina",
    "ettevotjaAadress",
    "infoKohustuseTaitmiseKohta",
    "majandusaastaAruanded",
    "maksuvolaInfo",
    "teabesysteemiLink"
})
public class RekvisiididEttevote {

    @XmlElement(required = true)
    protected String nimi;
    @XmlElement(name = "ariregistri_kood")
    protected String ariregistriKood;
    @XmlElement(name = "kmkr_nr")
    protected String kmkrNr;
    @XmlElement(name = "ettevotja_staatus", required = true)
    protected String ettevotjaStaatus;
    @XmlElement(name = "ettevotja_staatus_tekstina", required = true)
    protected String ettevotjaStaatusTekstina;
    @XmlElementRef(name = "ettevotja_aadress", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<RekvisiididEvaadress> ettevotjaAadress;
    @XmlElement(name = "info_kohustuse_taitmise_kohta")
    protected Boolean infoKohustuseTaitmiseKohta;
    @XmlElement(name = "majandusaasta_aruanded")
    protected RekvisiididMajAruanded majandusaastaAruanded;
    @XmlElement(name = "maksuvola_info")
    protected Boolean maksuvolaInfo;
    @XmlElement(name = "teabesysteemi_link")
    protected String teabesysteemiLink;

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
     * Gets the value of the ariregistriKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAriregistriKood() {
        return ariregistriKood;
    }

    /**
     * Sets the value of the ariregistriKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAriregistriKood(String value) {
        this.ariregistriKood = value;
    }

    /**
     * Gets the value of the kmkrNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKmkrNr() {
        return kmkrNr;
    }

    /**
     * Sets the value of the kmkrNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKmkrNr(String value) {
        this.kmkrNr = value;
    }

    /**
     * Gets the value of the ettevotjaStaatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaStaatus() {
        return ettevotjaStaatus;
    }

    /**
     * Sets the value of the ettevotjaStaatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaStaatus(String value) {
        this.ettevotjaStaatus = value;
    }

    /**
     * Gets the value of the ettevotjaStaatusTekstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEttevotjaStaatusTekstina() {
        return ettevotjaStaatusTekstina;
    }

    /**
     * Sets the value of the ettevotjaStaatusTekstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEttevotjaStaatusTekstina(String value) {
        this.ettevotjaStaatusTekstina = value;
    }

    /**
     * Gets the value of the ettevotjaAadress property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RekvisiididEvaadress }{@code >}
     *     
     */
    public JAXBElement<RekvisiididEvaadress> getEttevotjaAadress() {
        return ettevotjaAadress;
    }

    /**
     * Sets the value of the ettevotjaAadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RekvisiididEvaadress }{@code >}
     *     
     */
    public void setEttevotjaAadress(JAXBElement<RekvisiididEvaadress> value) {
        this.ettevotjaAadress = value;
    }

    /**
     * Gets the value of the infoKohustuseTaitmiseKohta property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInfoKohustuseTaitmiseKohta() {
        return infoKohustuseTaitmiseKohta;
    }

    /**
     * Sets the value of the infoKohustuseTaitmiseKohta property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInfoKohustuseTaitmiseKohta(Boolean value) {
        this.infoKohustuseTaitmiseKohta = value;
    }

    /**
     * Gets the value of the majandusaastaAruanded property.
     * 
     * @return
     *     possible object is
     *     {@link RekvisiididMajAruanded }
     *     
     */
    public RekvisiididMajAruanded getMajandusaastaAruanded() {
        return majandusaastaAruanded;
    }

    /**
     * Sets the value of the majandusaastaAruanded property.
     * 
     * @param value
     *     allowed object is
     *     {@link RekvisiididMajAruanded }
     *     
     */
    public void setMajandusaastaAruanded(RekvisiididMajAruanded value) {
        this.majandusaastaAruanded = value;
    }

    /**
     * Gets the value of the maksuvolaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMaksuvolaInfo() {
        return maksuvolaInfo;
    }

    /**
     * Sets the value of the maksuvolaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMaksuvolaInfo(Boolean value) {
        this.maksuvolaInfo = value;
    }

    /**
     * Gets the value of the teabesysteemiLink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeabesysteemiLink() {
        return teabesysteemiLink;
    }

    /**
     * Sets the value of the teabesysteemiLink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeabesysteemiLink(String value) {
        this.teabesysteemiLink = value;
    }

}
