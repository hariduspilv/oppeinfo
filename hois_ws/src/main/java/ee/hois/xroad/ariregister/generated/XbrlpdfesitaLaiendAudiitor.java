
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xbrlpdfesita_laiend_audiitor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlpdfesita_laiend_audiitor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kutsetunnistuse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="audiitorettevotja_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="audiitorettevotja_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="audiitorettevotja_loa_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="auditit_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlpdfesita_laiend_audiitor", propOrder = {
    "isikukood",
    "nimi",
    "kutsetunnistuseNr",
    "audiitorettevotjaKood",
    "audiitorettevotjaNimi",
    "audiitorettevotjaLoaNr",
    "audititKpv"
})
public class XbrlpdfesitaLaiendAudiitor {

    @XmlElement(required = true, nillable = true)
    protected String isikukood;
    @XmlElement(required = true, nillable = true)
    protected String nimi;
    @XmlElement(name = "kutsetunnistuse_nr", required = true, nillable = true)
    protected String kutsetunnistuseNr;
    @XmlElement(name = "audiitorettevotja_kood", required = true, nillable = true)
    protected String audiitorettevotjaKood;
    @XmlElement(name = "audiitorettevotja_nimi", required = true, nillable = true)
    protected String audiitorettevotjaNimi;
    @XmlElement(name = "audiitorettevotja_loa_nr", required = true, nillable = true)
    protected String audiitorettevotjaLoaNr;
    @XmlElement(name = "auditit_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar audititKpv;

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
     * Gets the value of the kutsetunnistuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKutsetunnistuseNr() {
        return kutsetunnistuseNr;
    }

    /**
     * Sets the value of the kutsetunnistuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKutsetunnistuseNr(String value) {
        this.kutsetunnistuseNr = value;
    }

    /**
     * Gets the value of the audiitorettevotjaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudiitorettevotjaKood() {
        return audiitorettevotjaKood;
    }

    /**
     * Sets the value of the audiitorettevotjaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudiitorettevotjaKood(String value) {
        this.audiitorettevotjaKood = value;
    }

    /**
     * Gets the value of the audiitorettevotjaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudiitorettevotjaNimi() {
        return audiitorettevotjaNimi;
    }

    /**
     * Sets the value of the audiitorettevotjaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudiitorettevotjaNimi(String value) {
        this.audiitorettevotjaNimi = value;
    }

    /**
     * Gets the value of the audiitorettevotjaLoaNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAudiitorettevotjaLoaNr() {
        return audiitorettevotjaLoaNr;
    }

    /**
     * Sets the value of the audiitorettevotjaLoaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudiitorettevotjaLoaNr(String value) {
        this.audiitorettevotjaLoaNr = value;
    }

    /**
     * Gets the value of the audititKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAudititKpv() {
        return audititKpv;
    }

    /**
     * Sets the value of the audititKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAudititKpv(XMLGregorianCalendar value) {
        this.audititKpv = value;
    }

}
