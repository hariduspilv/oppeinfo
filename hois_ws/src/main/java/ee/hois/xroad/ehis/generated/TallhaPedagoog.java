
package ee.hois.xroad.ehis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for tallhaPedagoog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tallhaPedagoog"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kool" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kooliNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppetundideArv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koormusnorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klassiaste" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lepinguAlgus" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="lepinguLopp" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="ametikohaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeaine" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tallhaPedagoog", propOrder = {
    "ik",
    "kool",
    "kooliNimi",
    "oppetundideArv",
    "koormusnorm",
    "klassiaste",
    "lepinguAlgus",
    "lepinguLopp",
    "ametikohaNimetus",
    "oppeaine"
})
public class TallhaPedagoog {

    @XmlElement(required = true)
    protected String ik;
    @XmlElement(required = true, nillable = true)
    protected String kool;
    @XmlElement(required = true, nillable = true)
    protected String kooliNimi;
    @XmlElement(required = true, nillable = true)
    protected String oppetundideArv;
    @XmlElement(required = true, nillable = true)
    protected String koormusnorm;
    @XmlElement(required = true, nillable = true)
    protected String klassiaste;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepinguAlgus;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lepinguLopp;
    @XmlElement(required = true, nillable = true)
    protected String ametikohaNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppeaine;

    /**
     * Gets the value of the ik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIk() {
        return ik;
    }

    /**
     * Sets the value of the ik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIk(String value) {
        this.ik = value;
    }

    /**
     * Gets the value of the kool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKool() {
        return kool;
    }

    /**
     * Sets the value of the kool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKool(String value) {
        this.kool = value;
    }

    /**
     * Gets the value of the kooliNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKooliNimi() {
        return kooliNimi;
    }

    /**
     * Sets the value of the kooliNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKooliNimi(String value) {
        this.kooliNimi = value;
    }

    /**
     * Gets the value of the oppetundideArv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppetundideArv() {
        return oppetundideArv;
    }

    /**
     * Sets the value of the oppetundideArv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppetundideArv(String value) {
        this.oppetundideArv = value;
    }

    /**
     * Gets the value of the koormusnorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoormusnorm() {
        return koormusnorm;
    }

    /**
     * Sets the value of the koormusnorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoormusnorm(String value) {
        this.koormusnorm = value;
    }

    /**
     * Gets the value of the klassiaste property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlassiaste() {
        return klassiaste;
    }

    /**
     * Sets the value of the klassiaste property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlassiaste(String value) {
        this.klassiaste = value;
    }

    /**
     * Gets the value of the lepinguAlgus property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepinguAlgus() {
        return lepinguAlgus;
    }

    /**
     * Sets the value of the lepinguAlgus property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepinguAlgus(XMLGregorianCalendar value) {
        this.lepinguAlgus = value;
    }

    /**
     * Gets the value of the lepinguLopp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLepinguLopp() {
        return lepinguLopp;
    }

    /**
     * Sets the value of the lepinguLopp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLepinguLopp(XMLGregorianCalendar value) {
        this.lepinguLopp = value;
    }

    /**
     * Gets the value of the ametikohaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmetikohaNimetus() {
        return ametikohaNimetus;
    }

    /**
     * Sets the value of the ametikohaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmetikohaNimetus(String value) {
        this.ametikohaNimetus = value;
    }

    /**
     * Gets the value of the oppeaine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeaine() {
        return oppeaine;
    }

    /**
     * Sets the value of the oppeaine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeaine(String value) {
        this.oppeaine = value;
    }

}
