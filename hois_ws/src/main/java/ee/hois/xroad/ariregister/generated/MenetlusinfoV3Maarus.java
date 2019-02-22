
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
 * <p>Java class for menetlusinfo_v3_maarus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menetlusinfo_v3_maarus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="otsuse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paevikukande_id_vana" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="otsuse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="joust_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="joust" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="lisatahtaeg" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="maaruse_tekst" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menetlusinfo_v3_maarus", propOrder = {
    "otsuseNr",
    "paevikukandeId",
    "paevikukandeIdVana",
    "otsuseLiik",
    "otsuseKpv",
    "joustKpv",
    "joust",
    "kandeliik",
    "lisatahtaeg",
    "maaruseTekst"
})
public class MenetlusinfoV3Maarus {

    @XmlElement(name = "otsuse_nr", required = true)
    protected String otsuseNr;
    @XmlElement(name = "paevikukande_id", required = true)
    protected String paevikukandeId;
    @XmlElement(name = "paevikukande_id_vana", required = true)
    protected String paevikukandeIdVana;
    @XmlElement(name = "otsuse_liik", required = true)
    protected String otsuseLiik;
    @XmlElement(name = "otsuse_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar otsuseKpv;
    @XmlElementRef(name = "joust_kpv", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> joustKpv;
    @XmlElementRef(name = "joust", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> joust;
    @XmlElementRef(name = "kandeliik", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> kandeliik;
    @XmlElementRef(name = "lisatahtaeg", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> lisatahtaeg;
    @XmlElementRef(name = "maaruse_tekst", namespace = "http://arireg.x-road.eu/producer/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> maaruseTekst;

    /**
     * Gets the value of the otsuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseNr() {
        return otsuseNr;
    }

    /**
     * Sets the value of the otsuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseNr(String value) {
        this.otsuseNr = value;
    }

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
     * Gets the value of the otsuseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtsuseLiik() {
        return otsuseLiik;
    }

    /**
     * Sets the value of the otsuseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtsuseLiik(String value) {
        this.otsuseLiik = value;
    }

    /**
     * Gets the value of the otsuseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOtsuseKpv() {
        return otsuseKpv;
    }

    /**
     * Sets the value of the otsuseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOtsuseKpv(XMLGregorianCalendar value) {
        this.otsuseKpv = value;
    }

    /**
     * Gets the value of the joustKpv property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getJoustKpv() {
        return joustKpv;
    }

    /**
     * Sets the value of the joustKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setJoustKpv(JAXBElement<XMLGregorianCalendar> value) {
        this.joustKpv = value;
    }

    /**
     * Gets the value of the joust property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getJoust() {
        return joust;
    }

    /**
     * Sets the value of the joust property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setJoust(JAXBElement<String> value) {
        this.joust = value;
    }

    /**
     * Gets the value of the kandeliik property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getKandeliik() {
        return kandeliik;
    }

    /**
     * Sets the value of the kandeliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setKandeliik(JAXBElement<Integer> value) {
        this.kandeliik = value;
    }

    /**
     * Gets the value of the lisatahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getLisatahtaeg() {
        return lisatahtaeg;
    }

    /**
     * Sets the value of the lisatahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setLisatahtaeg(JAXBElement<XMLGregorianCalendar> value) {
        this.lisatahtaeg = value;
    }

    /**
     * Gets the value of the maaruseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMaaruseTekst() {
        return maaruseTekst;
    }

    /**
     * Sets the value of the maaruseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMaaruseTekst(JAXBElement<String> value) {
        this.maaruseTekst = value;
    }

}
