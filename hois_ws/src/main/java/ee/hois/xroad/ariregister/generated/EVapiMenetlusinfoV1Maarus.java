
package ee.hois.xroad.ariregister.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for EVapiMenetlusinfo_v1_Maarus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EVapiMenetlusinfo_v1_Maarus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="maaruse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maaruse_nr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="menetluse_id" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="maaruse_liik" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="maaruse_kpv" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="joust_kpv" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="joust" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kandeliik" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "EVapiMenetlusinfo_v1_Maarus", propOrder = {
    "maaruseId",
    "maaruseNr",
    "menetluseId",
    "maaruseLiik",
    "maaruseKpv",
    "joustKpv",
    "joust",
    "kandeliik",
    "lisatahtaeg",
    "maaruseTekst"
})
public class EVapiMenetlusinfoV1Maarus {

    @XmlElement(name = "maaruse_id", required = true)
    protected BigInteger maaruseId;
    @XmlElement(name = "maaruse_nr", required = true)
    protected String maaruseNr;
    @XmlElement(name = "menetluse_id", required = true)
    protected BigInteger menetluseId;
    @XmlElement(name = "maaruse_liik", required = true)
    protected String maaruseLiik;
    @XmlElement(name = "maaruse_kpv", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar maaruseKpv;
    @XmlElement(name = "joust_kpv")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar joustKpv;
    protected String joust;
    protected String kandeliik;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lisatahtaeg;
    @XmlElement(name = "maaruse_tekst")
    protected String maaruseTekst;

    /**
     * Gets the value of the maaruseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaaruseId() {
        return maaruseId;
    }

    /**
     * Sets the value of the maaruseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaaruseId(BigInteger value) {
        this.maaruseId = value;
    }

    /**
     * Gets the value of the maaruseNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseNr() {
        return maaruseNr;
    }

    /**
     * Sets the value of the maaruseNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseNr(String value) {
        this.maaruseNr = value;
    }

    /**
     * Gets the value of the menetluseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMenetluseId() {
        return menetluseId;
    }

    /**
     * Sets the value of the menetluseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMenetluseId(BigInteger value) {
        this.menetluseId = value;
    }

    /**
     * Gets the value of the maaruseLiik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseLiik() {
        return maaruseLiik;
    }

    /**
     * Sets the value of the maaruseLiik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseLiik(String value) {
        this.maaruseLiik = value;
    }

    /**
     * Gets the value of the maaruseKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaaruseKpv() {
        return maaruseKpv;
    }

    /**
     * Sets the value of the maaruseKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaaruseKpv(XMLGregorianCalendar value) {
        this.maaruseKpv = value;
    }

    /**
     * Gets the value of the joustKpv property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getJoustKpv() {
        return joustKpv;
    }

    /**
     * Sets the value of the joustKpv property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setJoustKpv(XMLGregorianCalendar value) {
        this.joustKpv = value;
    }

    /**
     * Gets the value of the joust property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJoust() {
        return joust;
    }

    /**
     * Sets the value of the joust property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJoust(String value) {
        this.joust = value;
    }

    /**
     * Gets the value of the kandeliik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKandeliik() {
        return kandeliik;
    }

    /**
     * Sets the value of the kandeliik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKandeliik(String value) {
        this.kandeliik = value;
    }

    /**
     * Gets the value of the lisatahtaeg property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLisatahtaeg() {
        return lisatahtaeg;
    }

    /**
     * Sets the value of the lisatahtaeg property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLisatahtaeg(XMLGregorianCalendar value) {
        this.lisatahtaeg = value;
    }

    /**
     * Gets the value of the maaruseTekst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaaruseTekst() {
        return maaruseTekst;
    }

    /**
     * Sets the value of the maaruseTekst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaaruseTekst(String value) {
        this.maaruseTekst = value;
    }

}
