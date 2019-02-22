
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for isikesindajastorkType_v4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isikesindajastorkType_v4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="e_isikud_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="isikukood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="isikukood_riik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="givenname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="synniaeg" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="mandatecontent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stork_autenditud" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="stork_autenditud_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="stork_qaa_level" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stork_data" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isikesindajastorkType_v4", propOrder = {
    "eIsikudId",
    "isikukood",
    "isikukoodRiik",
    "givenname",
    "surname",
    "synniaeg",
    "mandatecontent",
    "storkAutenditud",
    "storkAutenditudKpv",
    "storkQaaLevel",
    "storkData"
})
public class IsikesindajastorkTypeV4 {

    @XmlElement(name = "e_isikud_id", required = true)
    protected BigInteger eIsikudId;
    @XmlElement(required = true)
    protected String isikukood;
    @XmlElement(name = "isikukood_riik", required = true)
    protected String isikukoodRiik;
    @XmlElement(required = true)
    protected String givenname;
    @XmlElement(required = true)
    protected String surname;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar synniaeg;
    @XmlElement(required = true)
    protected String mandatecontent;
    @XmlElement(name = "stork_autenditud", required = true)
    protected BigInteger storkAutenditud;
    @XmlElement(name = "stork_autenditud_kpv", required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar storkAutenditudKpv;
    @XmlElement(name = "stork_qaa_level", required = true, nillable = true)
    protected String storkQaaLevel;
    @XmlElement(name = "stork_data", required = true)
    protected String storkData;

    /**
     * Gets the value of the eIsikudId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEIsikudId() {
        return eIsikudId;
    }

    /**
     * Sets the value of the eIsikudId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEIsikudId(BigInteger value) {
        this.eIsikudId = value;
    }

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
     * Gets the value of the isikukoodRiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsikukoodRiik() {
        return isikukoodRiik;
    }

    /**
     * Sets the value of the isikukoodRiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsikukoodRiik(String value) {
        this.isikukoodRiik = value;
    }

    /**
     * Gets the value of the givenname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenname() {
        return givenname;
    }

    /**
     * Sets the value of the givenname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenname(String value) {
        this.givenname = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the synniaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSynniaeg() {
        return synniaeg;
    }

    /**
     * Sets the value of the synniaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSynniaeg(XMLGregorianCalendar value) {
        this.synniaeg = value;
    }

    /**
     * Gets the value of the mandatecontent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMandatecontent() {
        return mandatecontent;
    }

    /**
     * Sets the value of the mandatecontent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMandatecontent(String value) {
        this.mandatecontent = value;
    }

    /**
     * Gets the value of the storkAutenditud property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStorkAutenditud() {
        return storkAutenditud;
    }

    /**
     * Sets the value of the storkAutenditud property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStorkAutenditud(BigInteger value) {
        this.storkAutenditud = value;
    }

    /**
     * Gets the value of the storkAutenditudKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStorkAutenditudKpv() {
        return storkAutenditudKpv;
    }

    /**
     * Sets the value of the storkAutenditudKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStorkAutenditudKpv(XMLGregorianCalendar value) {
        this.storkAutenditudKpv = value;
    }

    /**
     * Gets the value of the storkQaaLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorkQaaLevel() {
        return storkQaaLevel;
    }

    /**
     * Sets the value of the storkQaaLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorkQaaLevel(String value) {
        this.storkQaaLevel = value;
    }

    /**
     * Gets the value of the storkData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorkData() {
        return storkData;
    }

    /**
     * Sets the value of the storkData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorkData(String value) {
        this.storkData = value;
    }

}
