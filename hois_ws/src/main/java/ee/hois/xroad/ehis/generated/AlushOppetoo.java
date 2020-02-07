
package ee.hois.xroad.ehis.generated;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for alushOppetoo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alushOppetoo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oppeasutuseNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppeasutuseKood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppimiseId" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="oppimaKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="oppekeel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lopetKp" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ryhmaNimetus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alushOppetoo", propOrder = {
    "oppeasutuseNimetus",
    "oppeasutuseKood",
    "oppimiseId",
    "oppimaKp",
    "oppekeel",
    "lopetKp",
    "ryhmaNimetus"
})
public class AlushOppetoo {

    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseNimetus;
    @XmlElement(required = true, nillable = true)
    protected String oppeasutuseKood;
    @XmlElement(required = true, nillable = true)
    protected BigInteger oppimiseId;
    @XmlElement(required = true, nillable = true)
    protected String oppimaKp;
    @XmlElement(required = true, nillable = true)
    protected String oppekeel;
    @XmlElement(required = true, nillable = true)
    protected String lopetKp;
    @XmlElement(required = true, nillable = true)
    protected String ryhmaNimetus;

    /**
     * Gets the value of the oppeasutuseNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppeasutuseNimetus() {
        return oppeasutuseNimetus;
    }

    /**
     * Sets the value of the oppeasutuseNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppeasutuseNimetus(String value) {
        this.oppeasutuseNimetus = value;
    }

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
     * Gets the value of the oppimiseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOppimiseId() {
        return oppimiseId;
    }

    /**
     * Sets the value of the oppimiseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOppimiseId(BigInteger value) {
        this.oppimiseId = value;
    }

    /**
     * Gets the value of the oppimaKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppimaKp() {
        return oppimaKp;
    }

    /**
     * Sets the value of the oppimaKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppimaKp(String value) {
        this.oppimaKp = value;
    }

    /**
     * Gets the value of the oppekeel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOppekeel() {
        return oppekeel;
    }

    /**
     * Sets the value of the oppekeel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOppekeel(String value) {
        this.oppekeel = value;
    }

    /**
     * Gets the value of the lopetKp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLopetKp() {
        return lopetKp;
    }

    /**
     * Sets the value of the lopetKp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLopetKp(String value) {
        this.lopetKp = value;
    }

    /**
     * Gets the value of the ryhmaNimetus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRyhmaNimetus() {
        return ryhmaNimetus;
    }

    /**
     * Sets the value of the ryhmaNimetus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRyhmaNimetus(String value) {
        this.ryhmaNimetus = value;
    }

}
