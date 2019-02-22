
package ee.hois.xroad.ariregister.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xbrl_aruande_myygitulu complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xbrl_aruande_myygitulu"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="emtak_kood" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emtak_versioon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="summa" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="summa_protsent" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="summa_koef" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
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
@XmlType(name = "xbrl_aruande_myygitulu", propOrder = {
    "emtakKood",
    "emtakVersioon",
    "summa",
    "summaProtsent",
    "summaKoef",
    "pohitegevus"
})
public class XbrlAruandeMyygitulu {

    @XmlElement(name = "emtak_kood", required = true)
    protected String emtakKood;
    @XmlElement(name = "emtak_versioon", required = true)
    protected String emtakVersioon;
    protected BigDecimal summa;
    @XmlElement(name = "summa_protsent")
    protected BigDecimal summaProtsent;
    @XmlElement(name = "summa_koef", required = true)
    protected BigDecimal summaKoef;
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
     * Gets the value of the summa property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumma() {
        return summa;
    }

    /**
     * Sets the value of the summa property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumma(BigDecimal value) {
        this.summa = value;
    }

    /**
     * Gets the value of the summaProtsent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSummaProtsent() {
        return summaProtsent;
    }

    /**
     * Sets the value of the summaProtsent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSummaProtsent(BigDecimal value) {
        this.summaProtsent = value;
    }

    /**
     * Gets the value of the summaKoef property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSummaKoef() {
        return summaKoef;
    }

    /**
     * Sets the value of the summaKoef property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSummaKoef(BigDecimal value) {
        this.summaKoef = value;
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
