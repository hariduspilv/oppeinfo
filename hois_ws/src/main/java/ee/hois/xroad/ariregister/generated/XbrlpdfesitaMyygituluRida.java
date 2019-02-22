
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrlpdfesita_myygitulu_rida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrlpdfesita_myygitulu_rida"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="emtak_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tulu" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="tulu_protsent" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="pohitegevus" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xbrlpdfesita_myygitulu_rida", propOrder = {
    "emtakKood",
    "emtakVersioon",
    "tulu",
    "tuluProtsent",
    "pohitegevus"
})
public class XbrlpdfesitaMyygituluRida {

    @XmlElement(name = "emtak_kood", required = true)
    protected String emtakKood;
    @XmlElement(name = "emtak_versioon", required = true)
    protected String emtakVersioon;
    @XmlElement(required = true)
    protected BigDecimal tulu;
    @XmlElement(name = "tulu_protsent", required = true)
    protected BigDecimal tuluProtsent;
    protected boolean pohitegevus;

    /**
     * Gets the value of the emtakKood property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakKood() {
        return emtakKood;
    }

    /**
     * Sets the value of the emtakKood property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakKood(String value) {
        this.emtakKood = value;
    }

    /**
     * Gets the value of the emtakVersioon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmtakVersioon() {
        return emtakVersioon;
    }

    /**
     * Sets the value of the emtakVersioon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmtakVersioon(String value) {
        this.emtakVersioon = value;
    }

    /**
     * Gets the value of the tulu property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTulu() {
        return tulu;
    }

    /**
     * Sets the value of the tulu property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTulu(BigDecimal value) {
        this.tulu = value;
    }

    /**
     * Gets the value of the tuluProtsent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTuluProtsent() {
        return tuluProtsent;
    }

    /**
     * Sets the value of the tuluProtsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTuluProtsent(BigDecimal value) {
        this.tuluProtsent = value;
    }

    /**
     * Gets the value of the pohitegevus property.
     * 
     */
    public boolean isPohitegevus() {
        return pohitegevus;
    }

    /**
     * Sets the value of the pohitegevus property.
     * 
     */
    public void setPohitegevus(boolean value) {
        this.pohitegevus = value;
    }

}
