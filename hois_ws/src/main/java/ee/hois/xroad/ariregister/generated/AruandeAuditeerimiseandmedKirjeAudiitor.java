
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for aruande_auditeerimiseandmed_kirje_audiitor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_auditeerimiseandmed_kirje_audiitor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="va_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="va_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="va_kutsetunnistuse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aev_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aev_nimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aev_loa_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="audit_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_auditeerimiseandmed_kirje_audiitor", propOrder = {
    "vaKood",
    "vaNimi",
    "vaKutsetunnistuseNr",
    "aevKood",
    "aevNimi",
    "aevLoaNr",
    "auditKpv"
})
public class AruandeAuditeerimiseandmedKirjeAudiitor {

    @XmlElement(name = "va_kood", required = true)
    protected String vaKood;
    @XmlElement(name = "va_nimi", required = true)
    protected String vaNimi;
    @XmlElement(name = "va_kutsetunnistuse_nr", required = true)
    protected String vaKutsetunnistuseNr;
    @XmlElement(name = "aev_kood", required = true)
    protected String aevKood;
    @XmlElement(name = "aev_nimi", required = true)
    protected String aevNimi;
    @XmlElement(name = "aev_loa_nr", required = true)
    protected String aevLoaNr;
    @XmlElement(name = "audit_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar auditKpv;

    /**
     * Gets the value of the vaKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaKood() {
        return vaKood;
    }

    /**
     * Sets the value of the vaKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaKood(String value) {
        this.vaKood = value;
    }

    /**
     * Gets the value of the vaNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaNimi() {
        return vaNimi;
    }

    /**
     * Sets the value of the vaNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaNimi(String value) {
        this.vaNimi = value;
    }

    /**
     * Gets the value of the vaKutsetunnistuseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaKutsetunnistuseNr() {
        return vaKutsetunnistuseNr;
    }

    /**
     * Sets the value of the vaKutsetunnistuseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaKutsetunnistuseNr(String value) {
        this.vaKutsetunnistuseNr = value;
    }

    /**
     * Gets the value of the aevKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAevKood() {
        return aevKood;
    }

    /**
     * Sets the value of the aevKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAevKood(String value) {
        this.aevKood = value;
    }

    /**
     * Gets the value of the aevNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAevNimi() {
        return aevNimi;
    }

    /**
     * Sets the value of the aevNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAevNimi(String value) {
        this.aevNimi = value;
    }

    /**
     * Gets the value of the aevLoaNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAevLoaNr() {
        return aevLoaNr;
    }

    /**
     * Sets the value of the aevLoaNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAevLoaNr(String value) {
        this.aevLoaNr = value;
    }

    /**
     * Gets the value of the auditKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuditKpv() {
        return auditKpv;
    }

    /**
     * Sets the value of the auditKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuditKpv(XMLGregorianCalendar value) {
        this.auditKpv = value;
    }

}
