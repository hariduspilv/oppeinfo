
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for saisYldhOping complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saisYldhOping"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseNimi" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tase" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lopetamiseAasta" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="lopetamiseKuupaev" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="keeleKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tunnustuseKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppesuund" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koolId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="staatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saisYldhOping", propOrder = {
    "oppeasutuseKood",
    "oppeasutuseNimi",
    "tase",
    "lopetamiseAasta",
    "lopetamiseKuupaev",
    "keeleKood",
    "tunnustuseKood",
    "oppevorm",
    "oppesuund",
    "koolId",
    "staatus",
    "klass"
})
public class SaisYldhOping {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseKood;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseNimi;
    @XmlElement(required = true, nillable = true)
    protected String tase;
    @XmlElement(required = true, nillable = true)
    protected BigInteger lopetamiseAasta;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lopetamiseKuupaev;
    @XmlElement(required = true, nillable = true)
    protected String keeleKood;
    @XmlElement(required = true, nillable = true)
    protected BigInteger tunnustuseKood;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String oppesuund;
    @XmlElement(required = true, nillable = true)
    protected BigInteger koolId;
    @XmlElement(required = true, nillable = true)
    protected String staatus;
    @XmlElement(required = true, nillable = true)
    protected String klass;

    /**
     * Gets the value of the oppeasutuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseKood() {
        return oppeasutuseKood;
    }

    /**
     * Sets the value of the oppeasutuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseKood(String value) {
        this.oppeasutuseKood = value;
    }

    /**
     * Gets the value of the oppeasutuseNimi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimi() {
        return oppeasutuseNimi;
    }

    /**
     * Sets the value of the oppeasutuseNimi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimi(String value) {
        this.oppeasutuseNimi = value;
    }

    /**
     * Gets the value of the tase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTase() {
        return tase;
    }

    /**
     * Sets the value of the tase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTase(String value) {
        this.tase = value;
    }

    /**
     * Gets the value of the lopetamiseAasta property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLopetamiseAasta() {
        return lopetamiseAasta;
    }

    /**
     * Sets the value of the lopetamiseAasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLopetamiseAasta(BigInteger value) {
        this.lopetamiseAasta = value;
    }

    /**
     * Gets the value of the lopetamiseKuupaev property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLopetamiseKuupaev() {
        return lopetamiseKuupaev;
    }

    /**
     * Sets the value of the lopetamiseKuupaev property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLopetamiseKuupaev(XMLGregorianCalendar value) {
        this.lopetamiseKuupaev = value;
    }

    /**
     * Gets the value of the keeleKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeeleKood() {
        return keeleKood;
    }

    /**
     * Sets the value of the keeleKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeeleKood(String value) {
        this.keeleKood = value;
    }

    /**
     * Gets the value of the tunnustuseKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTunnustuseKood() {
        return tunnustuseKood;
    }

    /**
     * Sets the value of the tunnustuseKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTunnustuseKood(BigInteger value) {
        this.tunnustuseKood = value;
    }

    /**
     * Gets the value of the oppevorm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppevorm() {
        return oppevorm;
    }

    /**
     * Sets the value of the oppevorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppevorm(String value) {
        this.oppevorm = value;
    }

    /**
     * Gets the value of the oppesuund property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppesuund() {
        return oppesuund;
    }

    /**
     * Sets the value of the oppesuund property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppesuund(String value) {
        this.oppesuund = value;
    }

    /**
     * Gets the value of the koolId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKoolId() {
        return koolId;
    }

    /**
     * Sets the value of the koolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKoolId(BigInteger value) {
        this.koolId = value;
    }

    /**
     * Gets the value of the staatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaatus() {
        return staatus;
    }

    /**
     * Sets the value of the staatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaatus(String value) {
        this.staatus = value;
    }

    /**
     * Gets the value of the klass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlass() {
        return klass;
    }

    /**
     * Sets the value of the klass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlass(String value) {
        this.klass = value;
    }

}
