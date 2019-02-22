
package ee.hois.xroad.ariregister.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xbrl_aruande_audiitor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrl_aruande_audiitor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="va_isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="va_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aev_registrikood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="aev_nimi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "xbrl_aruande_audiitor", propOrder = {
    "vaIsikukood",
    "vaNimi",
    "aevRegistrikood",
    "aevNimi",
    "auditKpv"
})
public class XbrlAruandeAudiitor {

    @XmlElement(name = "va_isikukood", required = true)
    protected String vaIsikukood;
    @XmlElement(name = "va_nimi")
    protected String vaNimi;
    @XmlElement(name = "aev_registrikood", required = true)
    protected String aevRegistrikood;
    @XmlElement(name = "aev_nimi")
    protected String aevNimi;
    @XmlElement(name = "audit_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar auditKpv;

    /**
     * Gets the value of the vaIsikukood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVaIsikukood() {
        return vaIsikukood;
    }

    /**
     * Sets the value of the vaIsikukood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVaIsikukood(String value) {
        this.vaIsikukood = value;
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
     * Gets the value of the aevRegistrikood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAevRegistrikood() {
        return aevRegistrikood;
    }

    /**
     * Sets the value of the aevRegistrikood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAevRegistrikood(String value) {
        this.aevRegistrikood = value;
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
