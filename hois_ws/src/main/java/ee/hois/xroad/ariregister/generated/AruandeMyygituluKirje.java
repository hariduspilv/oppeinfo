
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for aruande_myygitulu_kirje complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="aruande_myygitulu_kirje"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ariregistri_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oiguslik_vorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitamise_aeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="aasta" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="myygituluta_tegutsev_ettevote" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="myygitulud" type="{http://arireg.x-road.eu/producer/}aruande_myygitulu_kirje_myygitulud"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "aruande_myygitulu_kirje", propOrder = {
    "ariregistriKood",
    "oiguslikVorm",
    "esitamiseAeg",
    "aasta",
    "myygitulutaTegutsevEttevote",
    "myygitulud"
})
public class AruandeMyygituluKirje {

    @XmlElement(name = "ariregistri_kood", required = true)
    protected String ariregistriKood;
    @XmlElement(name = "oiguslik_vorm", required = true)
    protected String oiguslikVorm;
    @XmlElement(name = "esitamise_aeg", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar esitamiseAeg;
    @XmlElement(required = true)
    protected BigInteger aasta;
    @XmlElement(name = "myygituluta_tegutsev_ettevote")
    protected Boolean myygitulutaTegutsevEttevote;
    @XmlElement(required = true)
    protected AruandeMyygituluKirjeMyygitulud myygitulud;

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
     * Gets the value of the oiguslikVorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOiguslikVorm() {
        return oiguslikVorm;
    }

    /**
     * Sets the value of the oiguslikVorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOiguslikVorm(String value) {
        this.oiguslikVorm = value;
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
     * Gets the value of the myygitulutaTegutsevEttevote property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMyygitulutaTegutsevEttevote() {
        return myygitulutaTegutsevEttevote;
    }

    /**
     * Sets the value of the myygitulutaTegutsevEttevote property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMyygitulutaTegutsevEttevote(Boolean value) {
        this.myygitulutaTegutsevEttevote = value;
    }

    /**
     * Gets the value of the myygitulud property.
     * 
     * @return
     *     possible object is
     *     {@link AruandeMyygituluKirjeMyygitulud }
     *     
     */
    public AruandeMyygituluKirjeMyygitulud getMyygitulud() {
        return myygitulud;
    }

    /**
     * Sets the value of the myygitulud property.
     * 
     * @param value
     *     allowed object is
     *     {@link AruandeMyygituluKirjeMyygitulud }
     *     
     */
    public void setMyygitulud(AruandeMyygituluKirjeMyygitulud value) {
        this.myygitulud = value;
    }

}
