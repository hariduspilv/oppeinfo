
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for aruande_auditeerimiseandmed_kirje complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_auditeerimiseandmed_kirje"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitamise_aeg" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="aruande_aasta_algus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="aruande_aasta_lopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="kohustuslik_audit" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="audiitorid" type="{http://arireg.x-road.eu/producer/}aruande_auditeerimiseandmed_kirje_audiitorid"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_auditeerimiseandmed_kirje", propOrder = {
    "ariregistriKood",
    "esitamiseAeg",
    "aasta",
    "aruandeAastaAlgus",
    "aruandeAastaLopp",
    "kohustuslikAudit",
    "audiitorid"
})
public class AruandeAuditeerimiseandmedKirje {

    @XmlElement(name = "ariregistri_kood", required = true)
    protected String ariregistriKood;
    @XmlElement(name = "esitamise_aeg", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar esitamiseAeg;
    @XmlElement(required = true)
    protected BigInteger aasta;
    @XmlElement(name = "aruande_aasta_algus", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar aruandeAastaAlgus;
    @XmlElement(name = "aruande_aasta_lopp", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar aruandeAastaLopp;
    @XmlElement(name = "kohustuslik_audit")
    protected Boolean kohustuslikAudit;
    @XmlElement(required = true)
    protected AruandeAuditeerimiseandmedKirjeAudiitorid audiitorid;

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
     * Gets the value of the esitamiseAeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEsitamiseAeg() {
        return esitamiseAeg;
    }

    /**
     * Sets the value of the esitamiseAeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEsitamiseAeg(XMLGregorianCalendar value) {
        this.esitamiseAeg = value;
    }

    /**
     * Gets the value of the aasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAasta() {
        return aasta;
    }

    /**
     * Sets the value of the aasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAasta(BigInteger value) {
        this.aasta = value;
    }

    /**
     * Gets the value of the aruandeAastaAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAruandeAastaAlgus() {
        return aruandeAastaAlgus;
    }

    /**
     * Sets the value of the aruandeAastaAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAruandeAastaAlgus(XMLGregorianCalendar value) {
        this.aruandeAastaAlgus = value;
    }

    /**
     * Gets the value of the aruandeAastaLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAruandeAastaLopp() {
        return aruandeAastaLopp;
    }

    /**
     * Sets the value of the aruandeAastaLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAruandeAastaLopp(XMLGregorianCalendar value) {
        this.aruandeAastaLopp = value;
    }

    /**
     * Gets the value of the kohustuslikAudit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKohustuslikAudit() {
        return kohustuslikAudit;
    }

    /**
     * Sets the value of the kohustuslikAudit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKohustuslikAudit(Boolean value) {
        this.kohustuslikAudit = value;
    }

    /**
     * Gets the value of the audiitorid property.
     * 
     * @return
     *     possible object is
     *     {@link AruandeAuditeerimiseandmedKirjeAudiitorid }
     *     
     */
    public AruandeAuditeerimiseandmedKirjeAudiitorid getAudiitorid() {
        return audiitorid;
    }

    /**
     * Sets the value of the audiitorid property.
     * 
     * @param value
     *     allowed object is
     *     {@link AruandeAuditeerimiseandmedKirjeAudiitorid }
     *     
     */
    public void setAudiitorid(AruandeAuditeerimiseandmedKirjeAudiitorid value) {
        this.audiitorid = value;
    }

}
