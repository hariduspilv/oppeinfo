
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eylOppimine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eylOppimine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oasNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oasId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oasRegnr" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="klass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="okKood" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="okNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppevorm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="koormus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="kursus" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eylOppimine", propOrder = {
    "oasNimetus",
    "oasId",
    "oasRegnr",
    "klass",
    "okKood",
    "okNimetus",
    "oppevorm",
    "koormus",
    "kursus"
})
public class EylOppimine {

    @XmlElement(required = true, nillable = true)
    protected String oasNimetus;
    @XmlElement(required = true, nillable = true)
    protected BigInteger oasId;
    @XmlElement(required = true, nillable = true)
    protected String oasRegnr;
    @XmlElement(required = true, nillable = true)
    protected String klass;
    @XmlElement(required = true, nillable = true)
    protected BigInteger okKood;
    @XmlElement(required = true, nillable = true)
    protected String okNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppevorm;
    @XmlElement(required = true, nillable = true)
    protected String koormus;
    @XmlElement(required = true, nillable = true)
    protected BigInteger kursus;

    /**
     * Gets the value of the oasNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasNimetus() {
        return oasNimetus;
    }

    /**
     * Sets the value of the oasNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasNimetus(String value) {
        this.oasNimetus = value;
    }

    /**
     * Gets the value of the oasId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOasId() {
        return oasId;
    }

    /**
     * Sets the value of the oasId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOasId(BigInteger value) {
        this.oasId = value;
    }

    /**
     * Gets the value of the oasRegnr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOasRegnr() {
        return oasRegnr;
    }

    /**
     * Sets the value of the oasRegnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOasRegnr(String value) {
        this.oasRegnr = value;
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

    /**
     * Gets the value of the okKood property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOkKood() {
        return okKood;
    }

    /**
     * Sets the value of the okKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOkKood(BigInteger value) {
        this.okKood = value;
    }

    /**
     * Gets the value of the okNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOkNimetus() {
        return okNimetus;
    }

    /**
     * Sets the value of the okNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOkNimetus(String value) {
        this.okNimetus = value;
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
     * Gets the value of the koormus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoormus() {
        return koormus;
    }

    /**
     * Sets the value of the koormus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoormus(String value) {
        this.koormus = value;
    }

    /**
     * Gets the value of the kursus property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getKursus() {
        return kursus;
    }

    /**
     * Sets the value of the kursus property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setKursus(BigInteger value) {
        this.kursus = value;
    }

}
