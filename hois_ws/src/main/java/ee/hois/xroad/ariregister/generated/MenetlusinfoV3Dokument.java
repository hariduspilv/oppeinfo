
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for menetlusinfo_v3_dokument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menetlusinfo_v3_dokument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id_vana" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="seotud_paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="esit_kp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="dok_liik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dok_olek" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notar_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aasta_nr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="notarnr" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_reg_kood" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ettevotja_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menetlusinfo_v3_dokument", propOrder = {
    "paevikukandeId",
    "paevikukandeIdVana",
    "seotudPaevikukandeId",
    "esitKp",
    "dokLiik",
    "dokOlek",
    "notarId",
    "aastaNr",
    "notarnr",
    "ettevotjaRegKood",
    "ettevotjaNimi"
})
public class MenetlusinfoV3Dokument {

    @XmlElement(name = "paevikukande_id", required = true)
    protected String paevikukandeId;
    @XmlElement(name = "paevikukande_id_vana", required = true)
    protected String paevikukandeIdVana;
    @XmlElementRef(name = "seotud_paevikukande_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> seotudPaevikukandeId;
    @XmlElement(name = "esit_kp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitKp;
    @XmlElementRef(name = "dok_liik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokLiik;
    @XmlElementRef(name = "dok_olek", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dokOlek;
    @XmlElementRef(name = "notar_id", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> notarId;
    @XmlElementRef(name = "aasta_nr", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> aastaNr;
    @XmlElementRef(name = "notarnr", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> notarnr;
    @XmlElementRef(name = "ettevotja_reg_kood", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ettevotjaRegKood;
    @XmlElementRef(name = "ettevotja_nimi", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ettevotjaNimi;

    /**
     * Gets the value of the paevikukandeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaevikukandeId() {
        return paevikukandeId;
    }

    /**
     * Sets the value of the paevikukandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaevikukandeId(String value) {
        this.paevikukandeId = value;
    }

    /**
     * Gets the value of the paevikukandeIdVana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaevikukandeIdVana() {
        return paevikukandeIdVana;
    }

    /**
     * Sets the value of the paevikukandeIdVana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaevikukandeIdVana(String value) {
        this.paevikukandeIdVana = value;
    }

    /**
     * Gets the value of the seotudPaevikukandeId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSeotudPaevikukandeId() {
        return seotudPaevikukandeId;
    }

    /**
     * Sets the value of the seotudPaevikukandeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSeotudPaevikukandeId(JAXBElement<String> value) {
        this.seotudPaevikukandeId = value;
    }

    /**
     * Gets the value of the esitKp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitKp() {
        return esitKp;
    }

    /**
     * Sets the value of the esitKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitKp(XMLGregorianCalendar value) {
        this.esitKp = value;
    }

    /**
     * Gets the value of the dokLiik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokLiik() {
        return dokLiik;
    }

    /**
     * Sets the value of the dokLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokLiik(JAXBElement<String> value) {
        this.dokLiik = value;
    }

    /**
     * Gets the value of the dokOlek property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDokOlek() {
        return dokOlek;
    }

    /**
     * Sets the value of the dokOlek property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDokOlek(JAXBElement<String> value) {
        this.dokOlek = value;
    }

    /**
     * Gets the value of the notarId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNotarId() {
        return notarId;
    }

    /**
     * Sets the value of the notarId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNotarId(JAXBElement<String> value) {
        this.notarId = value;
    }

    /**
     * Gets the value of the aastaNr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAastaNr() {
        return aastaNr;
    }

    /**
     * Sets the value of the aastaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAastaNr(JAXBElement<String> value) {
        this.aastaNr = value;
    }

    /**
     * Gets the value of the notarnr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getNotarnr() {
        return notarnr;
    }

    /**
     * Sets the value of the notarnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setNotarnr(JAXBElement<Integer> value) {
        this.notarnr = value;
    }

    /**
     * Gets the value of the ettevotjaRegKood property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEttevotjaRegKood() {
        return ettevotjaRegKood;
    }

    /**
     * Sets the value of the ettevotjaRegKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEttevotjaRegKood(JAXBElement<String> value) {
        this.ettevotjaRegKood = value;
    }

    /**
     * Gets the value of the ettevotjaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEttevotjaNimi() {
        return ettevotjaNimi;
    }

    /**
     * Sets the value of the ettevotjaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEttevotjaNimi(JAXBElement<String> value) {
        this.ettevotjaNimi = value;
    }

}
